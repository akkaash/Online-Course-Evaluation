package gradiance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OraclePreparedStatement;

public class QueryExecutor {
	
	private String queryString;
	private Connection connection;
	public QueryExecutor(Connection connection) {
		super();
		this.connection = connection;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public ResultSet execute(String...strings ){
		ResultSet resultSet = null;
		
		try {
			OraclePreparedStatement stmt = (OraclePreparedStatement) connection.prepareStatement(queryString);
			int i = 0;
			
			System.out.println(queryString);
			
			for(String string: strings){
				System.out.println((i+1) + ":" + string);
				stmt.setString(++i, string);
			}
			
			resultSet = stmt.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return resultSet;
		
	}
	
	

}
