package gradiance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OraclePreparedStatement;

public class RandomParamAnsGen {
	
	private Question question;
	private Connection connection;
	private OraclePreparedStatement stmt;
	
	public RandomParamAnsGen(Question question, Connection connection) {
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
	
	private String answerQueryString = "select *" + " "
			+ "from " + " "
			+ "(select * from " + MyConstants.ANSWERS_TABLE_NAME + " "
			+ "order by dbms_random.value) t" + " "
			+ "where " 	+ "t" + "." + MyConstants.ANSWERS_COLS[1] + " = ?" + " "
			+ "and " 	+ "t" + "." + MyConstants.ANSWERS_COLS[5] + " = ?" + " "
			+ "and " 	+ "t" + "." + MyConstants.ANSWERS_COLS[3] + " = ?" + " "
			+ "and " 	+ "rownum < ?";
	
	public ResultSet getAnswerSet(int flag, int num, int parameterID){
		ResultSet answerSet = null;
		try {
			this.stmt = (OraclePreparedStatement) connection.prepareStatement(answerQueryString);
			System.out.println(answerQueryString);
			System.out.println(this.question.getQuesionID() + " " + flag + " " + (num+1));
			
			stmt.clearParameters();
			stmt.setInt(1, this.question.getQuesionID());
			stmt.setInt(2, parameterID);
			stmt.setInt(3, flag);
			stmt.setInt(4, num + 1);
						
			answerSet = stmt.executeQuery();
//			System.out.println(answerSet.toString());
			stmt.clearParameters();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return answerSet;
		
		
	}

}
