package gradiance;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.OraclePreparedStatement;

public class UpdateExecutor {
	
	private Connection connection;
	private String updateString;

	public UpdateExecutor(Connection connection) {
		super();
		this.connection = connection;
	}

	public String getUpdateString() {
		return updateString;
	}

	public void setUpdateString(String updateString) {
		this.updateString = updateString;
	}
	
	public int executeUpdate(String...strings){
		int numRows = -1;
		try{
			OraclePreparedStatement stmt = (OraclePreparedStatement) this.connection.prepareStatement(updateString);
			System.out.println(this.updateString);
			
			int i=0;
			for(String string: strings){
				System.out.println((i+1) + ":" + string);
				stmt.setString(++i, string);
			}
			
			numRows = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return numRows;
	}

}
