package gradiance;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.RequestDispatcher;
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
	private final int numberOfOptions = 4;
	private final int numberOfCorrectOptions = 1;
	private final int numberOfIncorrectOptions = numberOfOptions - numberOfCorrectOptions;
	
	
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
    	connection = connManager.getConnection();
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
				
				System.out.println(noOfAttemptsResultSet.getInt("count"));
				currentAttempts = noOfAttemptsResultSet.getInt("count");
			} else{
				currentAttempts = 0;
			}
			
			if (maxRetries - currentAttempts > 0){
				// attempts remaining...
				RandomizedQuestionGenerator generator = new RandomizedQuestionGenerator(connection, homework);
				ResultSet questionSet = generator.getQuestions();
				
				if (questionSet.next()) {
					HashMap<Question, ArrayList<Answer>> map = new HashMap<Question, ArrayList<Answer>>();
					
					do{
						Question question = new Question(
								questionSet.getInt(MyConstants.QUESTIONS_COLS[0]),
								questionSet.getInt(MyConstants.QUESTIONS_COLS[1]),
								questionSet.getString(MyConstants.QUESTIONS_COLS[2]),
								questionSet.getString(MyConstants.QUESTIONS_COLS[3]),
								questionSet.getString(MyConstants.QUESTIONS_COLS[4]),
								questionSet.getInt(MyConstants.QUESTIONS_COLS[5]),
								questionSet.getInt(MyConstants.QUESTIONS_COLS[6]));
						
						ResultSet correctAnswerSet = null;
						ResultSet incorrectAnswerSet = null;
						ArrayList<Answer> options = new ArrayList<Answer>();
						
						//check if parameterized question...
						
						if(question.getFlag() == 0){
							//regular question...
							System.out.println("regular question... " + question.getQuesionID());
							RandomAnswerGenerator answerGenerator = new RandomAnswerGenerator(question, connection);
							
							
							//regular question
							correctAnswerSet = answerGenerator.getAnswerSet(1, numberOfCorrectOptions);
							incorrectAnswerSet = answerGenerator.getAnswerSet(0, numberOfIncorrectOptions);
							
//							System.out.println(correctAnswerSet.next());
							
							if(!correctAnswerSet.next()){
								throw new Exception("no correct answers to question with ID = " + question.getQuesionID());
							} else if(!incorrectAnswerSet.next()){
								throw new Exception("no incorrect answers to question with ID = " + question.getQuesionID());
							}
							
							
							
							
						} else{
							//parameterized question...
							
							System.out.println("parameterized question..." + question.getQuesionID());
							RandomParameterGenerator parameterGenerator = new RandomParameterGenerator(question, connection);
//							System.out.println("parameterGeneratorString \n" + parameterGenerator.getParameterQueryString());
							
							int numberOfParameters = 1;
							// if ever increase the parameters then fix the method..
							
							int parameterID = parameterGenerator.getRandomParameter(numberOfParameters);
							if(parameterID <= 0){
								throw new Exception("parameterID " + parameterID + "returned <= 0");
							}
							
							//replace parameters in the root question..
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
							
//							System.out.println("parameterID: "+ parameterID + "finalQuestion: " + finalQuestion);
							
							question.setText(finalQuestion);
							
							
							RandomParamAnsGen paramAnsGenerator = new RandomParamAnsGen(question, connection);
							correctAnswerSet = paramAnsGenerator.getAnswerSet(1, numberOfCorrectOptions, parameterID);
							incorrectAnswerSet = paramAnsGenerator.getAnswerSet(0, numberOfIncorrectOptions, parameterID);
							
							if(!correctAnswerSet.next()){
								throw new Exception("no correct answers to question with ID = " + question.getQuesionID());
							} else if(!incorrectAnswerSet.next()){
								throw new Exception("no incorrect answers to question with ID = " + question.getQuesionID());
							}
							
							
							
						}
						
						Answer correctAnswer = new Answer(
								correctAnswerSet.getInt(MyConstants.ANSWERS_COLS[0]),
								correctAnswerSet.getInt(MyConstants.ANSWERS_COLS[1]),
								correctAnswerSet.getString(MyConstants.ANSWERS_COLS[2]),
								correctAnswerSet.getInt(MyConstants.ANSWERS_COLS[3]),
								correctAnswerSet.getString(MyConstants.ANSWERS_COLS[4]),
								correctAnswerSet.getInt(MyConstants.ANSWERS_COLS[5]));
						
						
						Random random = new Random();
						int correctAnswerIndex = random.nextInt(numberOfOptions);
						
						for(int i = 0; i < numberOfOptions; i++){
							if (i == correctAnswerIndex) {
								options.add(correctAnswer);
							} else {
								Answer incorrectAnswer = new Answer(
										incorrectAnswerSet.getInt(MyConstants.ANSWERS_COLS[0]),
										incorrectAnswerSet.getInt(MyConstants.ANSWERS_COLS[1]),
										incorrectAnswerSet.getString(MyConstants.ANSWERS_COLS[2]),
										incorrectAnswerSet.getInt(MyConstants.ANSWERS_COLS[3]),
										incorrectAnswerSet.getString(MyConstants.ANSWERS_COLS[4]),
										incorrectAnswerSet.getInt(MyConstants.ANSWERS_COLS[5]));
								
								options.add(incorrectAnswer);
								incorrectAnswerSet.next();
							}
						}
						
						map.put(question, options);
						
					}while(questionSet.next());
					
					display(map, response);
					
//					request.setAttribute("questionOptions", map);
//					RequestDispatcher disptacher = request.getRequestDispatcher("/displayQuestionsForHW.jsp");
//					disptacher.forward(request, response);
				} else{
					System.out.println("No questions generated");
				}
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				connection.close();
				System.out.println("closed connection");
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
				writer.println(question.getQuesionID() + " " + question.getText());
				
				for(Answer answer: map.get(question)){
					writer.println(answer.getAnswer());
				}
				writer.println();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			writer.close();
		}
		
		
	}

	//    private String getHomeworkString = "select * from " + "homework "
//			+ " where homework_id = ?" ;
    private String getHomeworkString = "select * from " + MyConstants.HOMEWORK_TABLE_NAME 
    		+ " where " + MyConstants.HOMEWORK_COLS[0] + " = ?";
    private String getNoOfAttemptsString = "select count(*) as count from " + MyConstants.ATTEMPTS_TABLE_NAME + " t"
    		+ " where " +  MyConstants.ATTEMPTS_COLS[1] + " = ?"+ ""
    		+" and " 	+  MyConstants.ATTEMPTS_COLS[2] + " = ? ";
	
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
			System.out.println(getNoOfAttemptsString);
			System.out.println(user_id + " " + homework.getHomework_id());
			stmt.clearParameters();
			
			stmt.setString(1, user_id);
			System.out.println("getNoOfAttemptsResultSet:" + homework.getHomework_id());
			stmt.setInt(2, homework.getHomework_id());
			
			
			
			resultSet = stmt.executeQuery();
			
			
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
