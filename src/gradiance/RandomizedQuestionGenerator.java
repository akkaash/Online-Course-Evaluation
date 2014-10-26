package gradiance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OraclePreparedStatement;

/**
 * 
 */

/**
 * @author akkaashgoel
 *
 */
public class RandomizedQuestionGenerator {
	
	private Homework homework;
	private Connection connection;

	public RandomizedQuestionGenerator(Connection connection, Homework homework) {
		super();
		this.homework = homework;
		this.connection = connection;
	}

//	public int getHomeworkNumber() {
//		return homeworkNumber;
//	}
//
//	public void setHomeworkNumber(int homeworkNumber) {
//		this.homeworkNumber = homeworkNumber;
//	}
	
	private ResultSet homeworkResultSet = null;
	
	public ResultSet getQuestions(){
		
		String randomQuestionString = "select * from (select" + " " 
				+ MyConstants.QUESTIONS_COLS[0] + "," 
				+ MyConstants.QUESTIONS_COLS[1] + ","
				+ MyConstants.QUESTIONS_COLS[2] + ","
				+ MyConstants.QUESTIONS_COLS[3] + ","
				+ MyConstants.QUESTIONS_COLS[4] + ","
				+ MyConstants.QUESTIONS_COLS[5] + ","
				+ MyConstants.QUESTIONS_COLS[6] + " "
				+ "from " + MyConstants.QTN_HW_TABLE_NAME + " join " + MyConstants.QUESTIONS_TABLE_NAME + " "
				+ "on " + MyConstants.QTN_HW_COLS[1] + " = " + MyConstants.QUESTIONS_COLS[0] + " "
				+ "where " + MyConstants.QTN_HW_COLS[0] + " = ?"+ " "
				+ "order by dbms_random.value)" + " "
				+ "where " + " "
				+ MyConstants.QUESTIONS_COLS[5] + " >= ? " + " and "
				+ MyConstants.QUESTIONS_COLS[5] + " <= ? "
				+ " and rownum < ? ";
		
		try {
			OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement(randomQuestionString);
			System.out.println(randomQuestionString);
			
			stmt.setInt(1, homework.getHomework_id());
			stmt.setInt(2, homework.getDifficulty_level_start());
			stmt.setInt(3, homework.getDifficulty_level_end());
			stmt.setInt(4, homework.getNumberOfQuestions()+1);
			System.out.println(homework.getNumberOfQuestions());
			
			ResultSet resultSet = stmt.executeQuery();
			return resultSet;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		
	

}
