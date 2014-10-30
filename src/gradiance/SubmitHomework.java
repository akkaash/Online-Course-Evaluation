package gradiance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.jdbc.OraclePreparedStatement;

/**
 * Servlet implementation class SubmitHomework
 */
@WebServlet("/SubmitHomework")
public class SubmitHomework extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Connection connection = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitHomework() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		submitHomeworkDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		submitHomeworkDo(request, response);
	}

	private void submitHomeworkDo(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println(request.toString());
		MyConnectionManager connectionManager = new MyConnectionManager();
		connection = connectionManager.getConnection();
		QueryExecutor queryExecutor = new QueryExecutor(connection);
		UpdateExecutor updateExecutor = new UpdateExecutor(connection);
		
		Map<String, String[]> requestMap = request.getParameterMap();
		
		
		try {
			HttpSession currentSession = request.getSession(true);
			
			//String userID = "jmick";
			String userID=(String) currentSession.getAttribute("username");
			
			int homeworkID = (Integer) currentSession.getAttribute("currHw");
			
			System.out.println("homeworkID:" + homeworkID);
			
			String queryString = "select *" + " "
								+ " from " + MyConstants.HOMEWORK_TABLE_NAME + " "
								+ " where " + MyConstants.HOMEWORK_COLS[0] + " = ?";
			
			queryExecutor.setQueryString(queryString);
			ResultSet homeworkResultSet = queryExecutor.execute(new String[]{
				Integer.toString(homeworkID)	
			});
			Homework homework = null;
			if(!homeworkResultSet.next()){
				throw new Exception("homework id:" + homeworkID + " does not exist");
			} else{
				homework = new Homework(homeworkResultSet.getInt(MyConstants.HOMEWORK_COLS[0]),						 
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
			}
			
			List<String> questionList = new ArrayList<String>();
			questionList = Arrays.asList(requestMap.get("question"));
//			ArrayList<String> questionList = (ArrayList<String>) Arrays.asList(requestMap.get("question"));
			
			
			
			
			int pointsScored = 0;
			for(String question: questionList){
				System.out.println("question id:" + question);
				queryString = "select *" + " "
									+ " from " + MyConstants.QUESTIONS_TABLE_NAME + " "
									+ " where " + MyConstants.QUESTIONS_COLS[0] + " = ?";
				
				queryExecutor.setQueryString(queryString);
				
				ResultSet questionResultSet = queryExecutor.execute(new String[]{
					question	
				});
				
				Question questionObj = null;
				
				if(!questionResultSet.next()){
					throw new Exception("question id: " + question + " does not exist");
				} else{
					questionObj = new Question(
							questionResultSet.getInt(MyConstants.QUESTIONS_COLS[0]),
							questionResultSet.getInt(MyConstants.QUESTIONS_COLS[1]),
							questionResultSet.getString(MyConstants.QUESTIONS_COLS[2]),
							questionResultSet.getString(MyConstants.QUESTIONS_COLS[3]),
							questionResultSet.getString(MyConstants.QUESTIONS_COLS[4]),
							questionResultSet.getInt(MyConstants.QUESTIONS_COLS[5]),
							questionResultSet.getInt(MyConstants.QUESTIONS_COLS[6]));
				}
				
				
				
				queryString = "select " + MyConstants.ANSWERS_COLS[3] + " "
						+ " from " + MyConstants.ANSWERS_TABLE_NAME + " "
						+ " where " + MyConstants.ANSWERS_COLS[0] + " = ?";
	
				queryExecutor.setQueryString(queryString);
				
				String answerID = requestMap.get(question)[0];
				
				if (!answerID.equalsIgnoreCase("0")) {
					ResultSet answerFlagResultSet = queryExecutor
							.execute(new String[] { answerID });
					if (!answerFlagResultSet.next()) {
						throw new Exception("answer id: " + answerID
								+ " does not exist");
					} else {
						int flag = answerFlagResultSet
								.getInt(MyConstants.ANSWERS_COLS[3]);

						if (flag == 1) {
							pointsScored += homework.getPoints_correct();
						} else if (flag == 0) {
							pointsScored -= homework.getPoints_incorrect();
						}
					}
				} else{
					pointsScored += 0;
				}
				
				
				
			}
			
			System.out.println("Points Scored:" + pointsScored);
			
			String updateString = "insert into " + MyConstants.ATTEMPTS_TABLE_NAME + " "
					+ " (" + MyConstants.ATTEMPTS_COLS[1] + "," + MyConstants.ATTEMPTS_COLS[2] + ","
						   + MyConstants.ATTEMPTS_COLS[3] + "," + MyConstants.ATTEMPTS_COLS[4] + ")" + " "
					+ " values " + "(?,?,CURRENT_TIMESTAMP,?)";

			updateExecutor.setUpdateString(updateString);
			
			java.util.Date now = new java.util.Date();
			java.sql.Date sqlNow = new java.sql.Date(now.getTime());
			System.out.println("sqlnow:" + sqlNow.toString());
			int ret = updateExecutor.executeUpdate(new String[]{
					MyConstants.ATTEMPTS_COLS[0]
			}, 	new String[]{
				userID,
				Integer.toString(homeworkID),
				Integer.toString(pointsScored)
			});
			
			System.out.println("ret:" + ret);
			
			ResultSet generatedKeysResultSet = updateExecutor.getGeneratedKeysResultSet();
			
			if(generatedKeysResultSet != null){
				if(!generatedKeysResultSet.next()){
					throw new Exception("no gen keys");
				} else{
					System.out.println(generatedKeysResultSet.getLong(1));
					long generatedKey = generatedKeysResultSet.getLong(1);
					
					String attemptDetailsUpdate = "insert into " + MyConstants.ATTEMPTS_DETAILS_TABLE_NAME + " "
									+ " (" + MyConstants.ATTEMPT_DETAILS_COLS[0] + "," 
										   + MyConstants.ATTEMPT_DETAILS_COLS[1] + ","
										   + MyConstants.ATTEMPT_DETAILS_COLS[2] + ","
										   + MyConstants.ATTEMPT_DETAILS_COLS[3] + ""
									+ ") " + " "
									+ " values " + "(?,?,?,?)";
					
					OraclePreparedStatement attemptDetailsUpdateStmt = 
							(OraclePreparedStatement) connection.prepareStatement(attemptDetailsUpdate);
					System.out.println("storing attempt details");
					for(String question: questionList){
						System.out.println("question:" + question);
						attemptDetailsUpdateStmt.setLong(1, generatedKey);
						attemptDetailsUpdateStmt.setInt(2, Integer.valueOf(question));
						
						String selectedAnswerID = requestMap.get(question)[0];
						
						List<String> optionList = new ArrayList<String>();
						optionList = Arrays.asList(requestMap.get("opt_" +question));
						
						for(String option: optionList){
							attemptDetailsUpdateStmt.setInt(3, Integer.valueOf(option));
							
							if(option.equalsIgnoreCase(selectedAnswerID)){
								attemptDetailsUpdateStmt.setInt(4, 1);
							} else{
								attemptDetailsUpdateStmt.setInt(4, 0);
							}
							
							attemptDetailsUpdateStmt.addBatch();
						}
						
						attemptDetailsUpdateStmt.executeBatch();
						
					}
 					
					response.sendRedirect("/DBMS/ViewAttemptDetails?backLink=StudViewHw.jsp&attemptID="+Long.toString(generatedKey)+"&hwID="+Integer.toString(homeworkID));
				}
			} else{
				throw new Exception("generated keys result set is null");
			}	
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
	}

}
