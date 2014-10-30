package gradiance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OraclePreparedStatement;

/**
 * Servlet implementation class ViewAttemptDetails
 */
@WebServlet("/ViewAttemptDetails")
public class ViewAttemptDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection connection;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAttemptDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		myDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		myDo(request, response);
	}

	private void myDo(HttpServletRequest request, HttpServletResponse response) {
//		int attemptID = Integer.parseInt(request.getParameter("attemptID").toString());
//		int hwID = Integer.parseInt(request.getParameter("hwID").toString());
		
		int attemptID = 70;
		int hwID = 1;
		
		MyConnectionManager connectionManager = new MyConnectionManager();
		connection = connectionManager.getConnection();
		
		QueryExecutor queryExecutor = new QueryExecutor(connection);
		
		String queryString = "select *" + " "
						+ " from " + MyConstants.HOMEWORK_TABLE_NAME + " "
						+ " where " + MyConstants.HOMEWORK_COLS[0] + " = ?";
		
		queryExecutor.setQueryString(queryString);
		
		ResultSet homeworkResultSet = queryExecutor.execute(new String[]{
			Integer.toString(hwID)
		});
		
		try {
			if(!homeworkResultSet.next()){
				request.setAttribute("errorMessage", "No homework "+ hwID);
				request.setAttribute("backLink", request.getHeader("referer"));
				RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
			} else{
				Homework homework = new Homework(homeworkResultSet.getInt(MyConstants.HOMEWORK_COLS[0]),						 
						homeworkResultSet.getInt(MyConstants.HOMEWORK_COLS[1]), 
						homeworkResultSet.getString(MyConstants.HOMEWORK_COLS[2]), 
						homeworkResultSet.getString(MyConstants.HOMEWORK_COLS[3]), 
						homeworkResultSet.getInt(MyConstants.HOMEWORK_COLS[4]), 
						homeworkResultSet.getInt(MyConstants.HOMEWORK_COLS[5]), 
						homeworkResultSet.getInt(MyConstants.HOMEWORK_COLS[6]), 
						homeworkResultSet.getString(MyConstants.HOMEWORK_COLS[7]), 
						homeworkResultSet.getInt(MyConstants.HOMEWORK_COLS[8]), 
						homeworkResultSet.getInt(MyConstants.HOMEWORK_COLS[9]),
						homeworkResultSet.getInt(MyConstants.HOMEWORK_COLS[10]),
						homeworkResultSet.getString(MyConstants.HOMEWORK_COLS[11]));
				
				request.setAttribute("homework", homework);
				
				String end = homework.getEnd_date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date;
				date = formatter.parse(homework.getEnd_date());
				Date now = new Date();
				System.out.println("now " + now);
				System.out.println("date " + date);
				
				if(now.after(date)){
					request.setAttribute("dueDateFlag", 1);
				}
				else
					request.setAttribute("dueDateFlag", 0);
				
				queryString = " select ATTEMPT_DETAILS.ATTEMPT_ID, QUESTIONS.QUESTION_ID,QUESTIONS.CHAPTER_ID,QUESTIONS.DET_EXPLANATION,QUESTIONS.DIFFICULTY,QUESTIONS.FLAG AS QFLAG,QUESTIONS.HINT,QUESTIONS.TEXT,ANSWERS.ANSWER,ANSWERS.FLAG AS AFLAG,ANSWERS.ANSWER_ID,ANSWERS.PARAMETER_ID,ANSWERS.QTN_ID,ANSWERS.SHORT_EXP,ATTEMPT_DETAILS.SELECTED" + ""
						+ " from " 	+ MyConstants.ATTEMPTS_DETAILS_TABLE_NAME + ","
									+ MyConstants.QUESTIONS_TABLE_NAME + ","
									+ MyConstants.ANSWERS_TABLE_NAME + " "
						+ " where " + MyConstants.ATTEMPTS_DETAILS_TABLE_NAME + "." + MyConstants.ATTEMPT_DETAILS_COLS[1] + " = " + MyConstants.QUESTIONS_TABLE_NAME + "." + MyConstants.QUESTIONS_COLS[0] + ""
						+ " and "	+ MyConstants.ATTEMPTS_DETAILS_TABLE_NAME + "." + MyConstants.ATTEMPT_DETAILS_COLS[1] + " = " + MyConstants.ANSWERS_TABLE_NAME + "." + MyConstants.ANSWERS_COLS[1] + ""
						+ " and " 	+ MyConstants.ATTEMPTS_DETAILS_TABLE_NAME + "." + MyConstants.ATTEMPT_DETAILS_COLS[2] + " = " +MyConstants.ANSWERS_TABLE_NAME + "." + MyConstants.ANSWERS_COLS[0] + ""
						+ " and " 	+ MyConstants.ATTEMPTS_DETAILS_TABLE_NAME + "." + MyConstants.ATTEMPT_DETAILS_COLS[0] + " = ?" + ""
						+ " order by QUESTIONS.QUESTION_ID";
				
				queryExecutor.setQueryString(queryString);
				
				ResultSet rs = queryExecutor.execute(new String[]{
					Integer.toString(attemptID)
				});
				
				if(!rs.next()){
					
				} else{
					ArrayList<Question> questionList = new ArrayList<Question>();
					ArrayList<Answer> answerList = null;
					HashMap<Integer, ArrayList<Answer>> questionAnswerMap = new HashMap<Integer, ArrayList<Answer>>();
					HashMap<Integer, Integer> answerSelectMap = new HashMap<Integer, Integer>();
					int i=0;
					Question question = null;
					do {
						
						System.out.println("i=" + i);
						
						Answer answer = new Answer(
								rs.getInt(MyConstants.ANSWERS_COLS[0]),
								rs.getInt(MyConstants.ANSWERS_COLS[1]),
								rs.getString(MyConstants.ANSWERS_COLS[2]),
								rs.getInt("AFLAG"),
								rs.getString(MyConstants.ANSWERS_COLS[4]),
								rs.getInt(MyConstants.ANSWERS_COLS[5]));
						
						if (i % 4 == 0) {
							answerList = new ArrayList<Answer>();
							question = new Question(
									rs.getInt(MyConstants.QUESTIONS_COLS[0]),
									rs.getInt(MyConstants.QUESTIONS_COLS[1]),
									rs.getString(MyConstants.QUESTIONS_COLS[2]),
									rs.getString(MyConstants.QUESTIONS_COLS[3]),
									rs.getString(MyConstants.QUESTIONS_COLS[4]),
									rs.getInt(MyConstants.QUESTIONS_COLS[5]),
									rs.getInt("QFLAG"));
							
							if(question.getFlag() == 1){
								int parameterID = answer.getParameterID();
								
								String fetchParams = "select *" + " "
										+ "from " + MyConstants.PARAMS_TABLE_NAME + " "
										+ "where " + MyConstants.PARAMS_COLS[0] + " = ?";
					
								OraclePreparedStatement fetchParamsStmt = (OraclePreparedStatement) connection.prepareStatement(fetchParams);
								
								fetchParamsStmt.clearParameters();
								fetchParamsStmt.setInt(1, parameterID);
								
								ResultSet paramResultSet = fetchParamsStmt.executeQuery();
								String params = null;
								if(paramResultSet.next()){
									params = paramResultSet.getString(MyConstants.PARAMS_COLS[2]);
								}
								
								String[] paramsArr = params.split(",");
								String rootQuestion = question.getText();
								String finalQuestion = String.format(rootQuestion, paramsArr);
								
//								System.out.println("parameterID: "+ parameterID + "finalQuestion: " + finalQuestion);
								
								question.setText(finalQuestion);
								
							}
							questionList.add(question);
						}
						
						
						
						answerList.add(answer);
						
						int selectFlag = rs.getInt(MyConstants.ATTEMPT_DETAILS_COLS[3]);
						
						answerSelectMap.put(answer.getAnswerID(), selectFlag);
						
						
						
						if(i % 4 == 3){
							questionAnswerMap.put(question.getQuestionID(), answerList);
						}
						
						i++;
					} while (rs.next());
					
					
//					System.out.println(questionList);
					
					request.setAttribute("questionList", questionList);
					request.setAttribute("questionAnswerMap", questionAnswerMap);
					request.setAttribute("answerSelectMap", answerSelectMap);
					
					request.setAttribute("backLink", request.getHeader("referer"));
					
					RequestDispatcher rd = request.getRequestDispatcher("/pastSubmissions.jsp");
					rd.forward(request, response);
					
				}
									   
							
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
