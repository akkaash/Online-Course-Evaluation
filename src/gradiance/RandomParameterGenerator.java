package gradiance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OraclePreparedStatement;

public class RandomParameterGenerator {

	private Question question;
	private Connection connection;
	
	public RandomParameterGenerator(Question question, Connection connection) {
		super();
		// TODO Auto-generated constructor stub
		this.question = question;
		this.connection = connection;
		
	}
	
	private String parameterQueryString = "select " + "t" + "." + MyConstants.PARAMS_COLS[0] + " "
			+ " from ( select " + MyConstants.PARAMS_TABLE_NAME + "." + MyConstants.PARAMS_COLS[0] + "," + " "
			+ MyConstants.PARAMS_TABLE_NAME + "." + MyConstants.PARAMS_COLS[1] + " "
			+ "from " + MyConstants.PARAMS_TABLE_NAME + " join " + MyConstants.QUESTIONS_TABLE_NAME + " "
			+ " on " + MyConstants.PARAMS_TABLE_NAME + "." + MyConstants.PARAMS_COLS[1] + " = " + MyConstants.QUESTIONS_TABLE_NAME + "." + MyConstants.QUESTIONS_COLS[0] + " "
			+ " order by dbms_random.value)" + " t " 
			+ " where " + "t" + "." + MyConstants.PARAMS_COLS[1] + " = ?" + " "
			+ " and " + "rownum < ?";

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getParameterQueryString() {
		return parameterQueryString;
	}

	public void setParameterQueryString(String parameterQueryString) {
		this.parameterQueryString = parameterQueryString;
	}
	
	
	public int getRandomParameter(int num){
		int parameterID = -1;
		System.out.println("parameterQueryString " + this.getParameterQueryString());
		
		try {
			OraclePreparedStatement stmt = (OraclePreparedStatement) this.connection.prepareStatement(parameterQueryString);
			
			stmt.clearParameters();
			stmt.setInt(1, question.getQuesionID());
			stmt.setInt(2, num + 1);
			
			ResultSet parameterResultSet = stmt.executeQuery();
			
			if(parameterResultSet.next()){
				parameterID = parameterResultSet.getInt(MyConstants.PARAMS_COLS[0]);				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return parameterID;
	}
	

}
