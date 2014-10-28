package gradiance;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ShowHomeworkQuestions
 */
@WebServlet("/ShowHomeworkQuestions")
public class ShowHomeworkQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	private int homeworkNumber = -1;
	private Homework homework = null;
		
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowHomeworkQuestions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		myDo(request, response);
	}

	private void myDo(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("View homework get");
		String hid=request.getParameter("hid");
		System.out.println("Homework id :"+hid);
		request.getSession().setAttribute("hid", hid);
		
		Map<String, String[]> requestMap = request.getParameterMap();
    	
    	// int hwNumber = Integer.parseInt(requestMap.get("hwId")[0]);
		//homeworkNumber=1;
    	homeworkNumber = Integer.parseInt(hid);
    	System.out.println("Homework for comparison : "+homeworkNumber);
		
    	MyConnectionManager connectionManager = new MyConnectionManager();
		connection = connectionManager.getConnection();
		
		QueryExecutor queryExecutor = new QueryExecutor(connection);
		String queryString = "select *" + " "
							+ " from " + MyConstants.HOMEWORK_TABLE_NAME + " t " + " "
							+ " where " + "t." + MyConstants.HOMEWORK_COLS[0] + " = ?" + " ";
		
		queryExecutor.setQueryString(queryString);
		
		ResultSet homeworkResultSet = queryExecutor.execute(new String[]{Integer.toString(homeworkNumber)});
		try {
			if(homeworkResultSet.next()){
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
							homeworkResultSet.getInt(MyConstants.HOMEWORK_COLS[10]));
				
				request.setAttribute("homework", homework);
				
				String chapterQueryString = "select *" + " "
											+ " from " + MyConstants.CHAPTERS_TABLE_NAME + " "
											+ " where " + MyConstants.CHAPTERS_COLS[0] + " = ?";
				queryExecutor.setQueryString(chapterQueryString);
				ResultSet chapterResultSet = queryExecutor.execute(new String[]{
					Integer.toString(homework.getChapter_id())	
				});
				
				if(!chapterResultSet.next()){
					request.setAttribute("errorMessage", "No Chapter assigned for this homework");
					request.setAttribute("backLink", request.getHeader("referer"));
					
					RequestDispatcher errorDispacther = request.getRequestDispatcher("/error.jsp");
					errorDispacther.forward(request, response);
					
//					throw new Exception("no chapter with id:" + homework.getChapter_id());
				} else{
					Chapter chapter = new Chapter(
								chapterResultSet.getInt(MyConstants.CHAPTERS_COLS[0]),
								chapterResultSet.getString(MyConstants.CHAPTERS_COLS[1]),
								chapterResultSet.getInt(MyConstants.CHAPTERS_COLS[2]));
					
					request.setAttribute("chapter", chapter);
				}
				
				
				queryString = "select *" + " "
							+ " from " + MyConstants.QTN_HW_TABLE_NAME + " join " + MyConstants.QUESTIONS_TABLE_NAME + " t " + " "
							+ " on " + MyConstants.QTN_HW_TABLE_NAME + "." + MyConstants.QTN_HW_COLS[1] + " = "
									 + " t" + "." + MyConstants.QUESTIONS_COLS[0] +" "
							+ " where " + MyConstants.QTN_HW_TABLE_NAME + "." + MyConstants.QTN_HW_COLS[0] + " = ?" + " "
							+ " order by " + " t" +"." + MyConstants.QUESTIONS_COLS[0] + " asc ";
				
				queryExecutor.setQueryString(queryString);
				
				ResultSet questionResultSet = queryExecutor.execute(new String[] {
					Integer.toString(homework.getHomework_id())
					
				});
				
				if(questionResultSet.next()){
					ArrayList<Question> questionList = new ArrayList<Question>();
					HashMap<Integer, ArrayList<Answer>> questionAnswerMap = new LinkedHashMap<Integer, ArrayList<Answer>>();
					HashMap<Integer, ArrayList<Params>> questionParamMap = new LinkedHashMap<Integer, ArrayList<Params>>();
					HashMap<Integer, ArrayList<Answer>> paramAnswerMap = new LinkedHashMap<Integer, ArrayList<Answer>>();
					
					do{
						Question question = new Question(
								questionResultSet.getInt(MyConstants.QUESTIONS_COLS[0]),
								questionResultSet.getInt(MyConstants.QUESTIONS_COLS[1]),
								questionResultSet.getString(MyConstants.QUESTIONS_COLS[2]),
								questionResultSet.getString(MyConstants.QUESTIONS_COLS[3]),
								questionResultSet.getString(MyConstants.QUESTIONS_COLS[4]),
								questionResultSet.getInt(MyConstants.QUESTIONS_COLS[5]),
								questionResultSet.getInt(MyConstants.QUESTIONS_COLS[6]));
						
						questionList.add(question);
						
						if (question.getFlag() == 0) {
							String queryCorrectAnswersString = "select *" + " "
									+ " from " + MyConstants.ANSWERS_TABLE_NAME
									+ " " + " where "
									+ MyConstants.ANSWERS_COLS[3] + " = ?"
									+ " " + " and "
									+ MyConstants.ANSWERS_COLS[1] + " = ?"
									+ " ";
							queryExecutor
									.setQueryString(queryCorrectAnswersString);
							ResultSet correctAnswerSet = queryExecutor
									.execute(new String[] {
											Integer.toString(1),
											Integer.toString(question
													.getQuestionID()) });
							if (!correctAnswerSet.next()) {
								request.setAttribute("errorMessage", "No Correct Answer for Question: " + question.getText());
								request.setAttribute("backLink", request.getHeader("referer"));
								
								RequestDispatcher errorDispacther = request.getRequestDispatcher("/error.jsp");
								errorDispacther.forward(request, response);
//								throw new Exception("no correct answer for question: "+ question.getQuestionID());
							} else {
								ArrayList<Answer> correctAnswerList = new ArrayList<Answer>();
								do {
									Answer correctAnswer = new Answer(
											correctAnswerSet
													.getInt(MyConstants.ANSWERS_COLS[0]),
											correctAnswerSet
													.getInt(MyConstants.ANSWERS_COLS[1]),
											correctAnswerSet
													.getString(MyConstants.ANSWERS_COLS[2]),
											correctAnswerSet
													.getInt(MyConstants.ANSWERS_COLS[3]),
											correctAnswerSet
													.getString(MyConstants.ANSWERS_COLS[4]),
											correctAnswerSet
													.getInt(MyConstants.ANSWERS_COLS[5]));
									correctAnswerList.add(correctAnswer);

								} while (correctAnswerSet.next());
								questionAnswerMap.put(question.getQuestionID(), correctAnswerList);
							}
						} else{
							// parameterized question...
							String queryParamsString = "select *" + " " 
													+ " from " + MyConstants.PARAMS_TABLE_NAME + " "
													+ " where " + MyConstants.PARAMS_COLS[1] + " = ?";
							
							queryExecutor.setQueryString(queryParamsString);
							ResultSet paramsResultSet = queryExecutor.execute(new String[]{
								Integer.toString(question.getQuestionID())	
							});
							
							if(!paramsResultSet.next()){
								throw new Exception("no params specified for parameteried question: " + question.getQuestionID());
							} else{
								ArrayList<Params> parameterList = new ArrayList<Params>();
								
								do{
									Params parameter = new Params(paramsResultSet.getInt(MyConstants.PARAMS_COLS[0]), 
																paramsResultSet.getInt(MyConstants.PARAMS_COLS[1]), 
																paramsResultSet.getString(MyConstants.PARAMS_COLS[2]));
									
									parameterList.add(parameter);
									
									String paramAnswerQueryString = "select *" + " "
															+ " from " + MyConstants.ANSWERS_TABLE_NAME + " "
															+ " where " + MyConstants.ANSWERS_COLS[1] + " = ?" + " "
															+ " and " + MyConstants.ANSWERS_COLS[3] + " = ?" + " "
															+ " and " + MyConstants.ANSWERS_COLS[5] + " = ?";
									
									
 									queryExecutor.setQueryString(paramAnswerQueryString);
 									ResultSet paramCorrectAnswerSet = queryExecutor.execute(new String[]{
 										Integer.toString(question.getQuestionID()),
 										Integer.toString(1),
 										Integer.toString(parameter.getParameterID())
 									});
 									
 									ArrayList<Answer> paramCorrectAnswerList = new ArrayList<Answer>();
 									if(!paramCorrectAnswerSet.next()){
 										throw new Exception("no correct answers for parameter id: " + 
 																	parameter.getParameterID());
 									} else{
 										
 										
 										do {
											Answer paramCorrectAnswer = new Answer(
													paramCorrectAnswerSet
															.getInt(MyConstants.ANSWERS_COLS[0]),
													paramCorrectAnswerSet
															.getInt(MyConstants.ANSWERS_COLS[1]),
													paramCorrectAnswerSet
															.getString(MyConstants.ANSWERS_COLS[2]),
													paramCorrectAnswerSet
															.getInt(MyConstants.ANSWERS_COLS[3]),
													paramCorrectAnswerSet
															.getString(MyConstants.ANSWERS_COLS[4]),
													paramCorrectAnswerSet
															.getInt(MyConstants.ANSWERS_COLS[5]));
											paramCorrectAnswerList
													.add(paramCorrectAnswer);
										} while (paramCorrectAnswerSet.next());
 										
 									}
 									paramAnswerMap.put(parameter.getParameterID(), paramCorrectAnswerList);
 									
								}while(paramsResultSet.next());
								questionParamMap.put(question.getQuestionID(), parameterList);
							}
							
 						}
					} while(questionResultSet.next());
					
					request.setAttribute("questionList", questionList);
					request.setAttribute("questionAnswerMap", questionAnswerMap);
					request.setAttribute("questionParamMap", questionParamMap);
					request.setAttribute("paramAnswerMap", paramAnswerMap);
					
//					display(map, response);
					
					RequestDispatcher rd = request.getRequestDispatcher("/Example-displayHWInfo.jsp");
					rd.forward(request, response);
					
					
				}else{
					request.setAttribute("errorMessage", "No Question for Homework:" + homework.getHomework_id());
					request.setAttribute("backLink", request.getHeader("referer"));
					
					RequestDispatcher errorDispacther = request.getRequestDispatcher("/error.jsp");
					errorDispacther.forward(request, response);
//					throw new Exception("no questions for this homework");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
		finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void display(HashMap<Question, ArrayList<Answer>> map, HttpServletResponse response) {
    	PrintWriter writer = null;
    	
		try {
			writer = response.getWriter();
			for(Question question : map.keySet()){
				if (question.getFlag() == 0) {
					writer.println(question.getQuestionID() + " "
							+ question.getText());
					for (Answer answer : map.get(question)) {
						writer.println(answer.getAnswer());
					}
					writer.println();
				} else{
					
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			writer.close();
		}
		
		
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		myDo(request, response);
	}

}
