package gradiance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OraclePreparedStatement;

public class RandomAnswerGenerator {
	
	private Question question;
	private Connection connection;
	private OraclePreparedStatement stmt;
	public RandomAnswerGenerator(Question question, Connection connection) {
		super();
		this.question = question;
		this.connection = connection;
	}
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
	
	private String answerQueryString = "SELECT * FROM (SELECT * FROM " + MyConstants.ANSWERS_TABLE_NAME + " "
			+ "ORDER BY dbms_random.value) t" + " "
			+ "WHERE t." + MyConstants.ANSWERS_COLS[1] + " = ?" + " "
			+ "AND t." + MyConstants.ANSWERS_COLS[3] + " = ?" +" "
			+ "AND " + "rownum < ?";
	
	public ResultSet getAnswerSet(int flag, int num){
		ResultSet answerSet = null;
		try {
			this.stmt = (OraclePreparedStatement) connection.prepareStatement(answerQueryString);
			System.out.println(answerQueryString);
			System.out.println(this.question.getQuestionID() + " " + flag + " " + (num+1));
			
			stmt.setInt(1, this.question.getQuestionID());
			stmt.setInt(2, flag);
			stmt.setInt(3, num + 1);
						
			answerSet = stmt.executeQuery();
			System.out.println(answerSet.toString());
			stmt.clearParameters();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return answerSet;
		
		
	}
	
}
