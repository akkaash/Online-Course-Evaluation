import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.OracleDriver;
import oracle.jdbc.driver.OracleConnection;


public class CreateConnection {
	
	private Connection connection;
	
	public CreateConnection() {
		try {
			Class.forName(MyConstants.oracleDriverName);
			this.connection = (OracleConnection) DriverManager.getConnection(MyConstants.connectionUrl, MyConstants.oracleUsername, MyConstants.oraclePassword);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public Connection getConnection() {
		return connection;
	}
	
	

}
