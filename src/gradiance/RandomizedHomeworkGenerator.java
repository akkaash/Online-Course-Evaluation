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
public class RandomizedHomeworkGenerator {
	
	private Homework homework;
	private Connection connection;

	public RandomizedHomeworkGenerator(Connection connection, Homework homework) {
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
		
		String randomQuestionString = "SELECT * FROM (SELECT * FROM " + MyConstants.QUESTIONS_TABLE_NAME +""
				+ 		" ORDER BY dbms_random.value) t WHERE"+ " "
						+"t." + MyConstants.QUESTIONS_COLS[5] + " >= ?" + " "
				+"and "	+"t." + MyConstants.QUESTIONS_COLS[5] + " <= ?" + " "
				+"and " +"t." + MyConstants.QUESTIONS_COLS[1] + " = ?" + " "
				+"and " +"rownum < ? ";
		try {
			OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement(randomQuestionString);
			System.out.println(randomQuestionString);
			
			stmt.setInt(1, homework.getDifficulty_level_start());
			stmt.setInt(2, homework.getDifficulty_level_end());
			stmt.setInt(3, homework.getChapter_id());
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
