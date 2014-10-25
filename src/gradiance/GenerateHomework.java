package gradiance;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OraclePreparedStatement;

/**
 * Servlet implementation class GenerateHomework
 */
@WebServlet(description = "Generate questions for a given homework", urlPatterns = { "/GenerateHomework" })
public class GenerateHomework extends HttpServlet {
	
	private Connection connection = null;
	private int homeworkNumber = -1;
	private Homework homework; 
	
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateHomework() {
        super();
        // TODO Auto-generated constructor stub
    }

    private void generateHomeworkDo(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
    	Map<String, String[]> requestMap = request.getParameterMap();
    	
    	//int hwNumber = Integer.parseInt(requestMap.get("hwId")[0]);
    	homeworkNumber = 1;
    	
    	MyConnectionManager connManager = new MyConnectionManager();
    	connection = connManager.createConnection();
    	ResultSet homeworkResultSet = null;
    	
    	homeworkResultSet = getHomeworkResultSet();
    	try {
    		homeworkResultSet.next();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		homework = createHomeworkObjectFromTuple(homeworkResultSet);
		
		//validate homework conditions...
		
		//check if homework is accessed before start date
		Date when = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = formatter.parse(homework.getStart_date());
			System.out.println(date.toString());
			if(when.before(date)){
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				out.println("cannot access before start date");
				out.close(); 
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//check if homework is accessed after end date
		
		try {
			date = formatter.parse(homework.getEnd_date());
			System.out.println("end date:" + date.toString());
			if(!when.after(date)){
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				out.println("cannot access after end date");
				out.close();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//check if attempts are remaining for this homework
		int maxRetries = homework.getNo_of_retries();
		int currentAttempts = 0;
		ResultSet noOfAttemptsResultSet = getNoOfAttemptsResultSet();
		try {
			if (noOfAttemptsResultSet.next()) {
				System.out.println(noOfAttemptsResultSet.getInt(0));
				currentAttempts = noOfAttemptsResultSet.getInt(0);
			} else{
				currentAttempts = 0;
			}
			
			if (maxRetries - currentAttempts > 0){
				// attempts remaining...
				RandomizedHomeworkGenerator generator = new RandomizedHomeworkGenerator(connection, homework);
				ResultSet questionSet = generator.getQuestions();
				if (questionSet.next()) {
					do{
						System.out.println(questionSet.getString(MyConstants.QUESTIONS_COLS[2]));
					}while(questionSet.next());
				} else{
					System.out.println("No questions generated");
				}
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
    
//    private String getHomeworkString = "select * from " + "homework "
//			+ " where homework_id = ?" ;
    private String getHomeworkString = "select * from " + MyConstants.HOMEWORK_TABLE_NAME 
    		+ " where " + MyConstants.HOMEWORK_COLS[0] + " = ?";
    private String getNoOfAttemptsString = "select count(*) as count from " + MyConstants.ATTEMPTS_TABLE_NAME 
    		+ " where " + MyConstants.ATTEMPTS_COLS[1] + " = ? and " + MyConstants.ATTEMPTS_COLS[2] 
    				+ " = ?";
	
	private Homework createHomeworkObjectFromTuple(ResultSet rs){
		Homework homework = null;
		
		try {
			homework = new Homework(rs.getInt(MyConstants.HOMEWORK_COLS[0]),						 
					rs.getInt(MyConstants.HOMEWORK_COLS[1]), 
					rs.getString(MyConstants.HOMEWORK_COLS[2]), 
					rs.getString(MyConstants.HOMEWORK_COLS[3]), 
					rs.getInt(MyConstants.HOMEWORK_COLS[4]), 
					rs.getInt(MyConstants.HOMEWORK_COLS[5]), 
					rs.getInt(MyConstants.HOMEWORK_COLS[6]), 
					rs.getString(MyConstants.HOMEWORK_COLS[7]), 
					rs.getInt(MyConstants.HOMEWORK_COLS[8]), 
					rs.getInt(MyConstants.HOMEWORK_COLS[9]),
					rs.getInt(MyConstants.HOMEWORK_COLS[10]));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return homework;
	}
	
	private ResultSet getHomeworkResultSet() {
		ResultSet resultSet = null;
		try {
			if(homeworkNumber < 0){
				throw new Exception("homeworkNumber < 0");
			}
			OraclePreparedStatement getHomeworkStatement = (OraclePreparedStatement) connection.prepareStatement(getHomeworkString);
			getHomeworkStatement.setInt(1, homeworkNumber);
			
			resultSet = getHomeworkStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultSet;
	}
	
	private String user_id = "jmick";
	
	private ResultSet getNoOfAttemptsResultSet(){
		ResultSet resultSet = null;
		try{
			if(homeworkNumber < 0){
				throw new Exception("homework number < 0");
			}
			OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement(getNoOfAttemptsString);
			stmt.setString(1, user_id);
			stmt.setInt(2, homework.getHomework_id());
			
			System.out.println(getNoOfAttemptsString);
			System.out.println(user_id + " " + homework.getHomework_id());
			
			resultSet = stmt.executeQuery();
			resultSet.next();
			
//			////////
//			System.out.println("count:");
//			System.out.println(resultSet.getInt("count"));
//			///////
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultSet;
	}

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		generateHomeworkDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		generateHomeworkDo(request, response);
	}

}
