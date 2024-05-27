package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDataBase {

	private static String username = "root";
	private static String dbpassword = "root123";
	public static Connection connection;
	
	public void connectDB() throws ClassNotFoundException, SQLException {
		
		java.util.Properties p = new java.util.Properties();
		p.setProperty("user", username);
		p.setProperty("password", dbpassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");

		
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBproject", username, dbpassword);
	}
	
	public void ExecuteStatement(String SQL) {
		try {
			Statement s = ConnectionDataBase.connection.createStatement();
			s.executeUpdate(SQL);
			s.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL statmenet isn't executed");
		}
	}
}
