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
			
			String userID = "jmick";
			
			int homeworkID = Integer.parseInt(requestMap.get("hwID")[0]);
			
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
				
				String answerID = requestMap.get("ans"+question)[0];
				
				if (answerID != null) {
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
				
				String updateString = "insert into " + MyConstants.ATTEMPTS_TABLE_NAME + " "
									+ " (" + MyConstants.ATTEMPTS_COLS[1] + "," + MyConstants.ATTEMPTS_COLS[2] + ","
										   + MyConstants.ATTEMPTS_COLS[3] + "," + MyConstants.ATTEMPTS_COLS[4] + ")" + " "
									+ " values " + "(?,?,?,?)";
				
				updateExecutor.setUpdateString(updateString);
				
				java.util.Date now = new java.util.Date();
				java.sql.Timestamp tsNow = new java.sql.Timestamp(now.getTime());
				
				int ret = updateExecutor.executeUpdate(new String[]{
					userID,
					Integer.toString(homeworkID),
					tsNow.toString(),
					Integer.toString(pointsScored)
				});
				
				System.out.println("ret:" + ret);
				
			}
			
			System.out.println("Points Scored:" + pointsScored);
			
			
			
			
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
