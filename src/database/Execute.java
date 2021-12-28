package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Execute {
	
	private static ResultSet result;
	private static Connection conn;
	private static Statement statement;
	
	public static ResultSet executeQuery(String query) throws SQLException {
		conn = (Connection) DriverManager.getConnection(Connect.DB_URL, Connect.USER_NAME, Connect.PASSWORD);
		statement = conn.createStatement();
		result = statement.executeQuery(query);	
		
     	return result;

	}
	
	public static void addQuery(String query) throws SQLException {
		conn = (Connection) DriverManager.getConnection(Connect.DB_URL, Connect.USER_NAME, Connect.PASSWORD);
		statement = conn.createStatement();
		statement.executeUpdate(query);
	}
	
	public static void updateQuery(String query) throws SQLException {
		conn = (Connection) DriverManager.getConnection(Connect.DB_URL, Connect.USER_NAME, Connect.PASSWORD);
		statement = conn.createStatement();
		statement.executeUpdate(query);
	}
	
	public static void delQuery(String query) throws SQLException {
		conn = (Connection) DriverManager.getConnection(Connect.DB_URL, Connect.USER_NAME, Connect.PASSWORD);
		statement = conn.createStatement();
		statement.executeUpdate(query);
	}
	
	public static void closeConnect() throws SQLException {
		result.close();
		statement.close();
     	conn.close();
	}

}
