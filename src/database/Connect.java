package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Connect {
	public static String DB_URL = "jdbc:mysql://localhost:3306/";
    public static String DB_NAME = "store";
	public static String USER_NAME = "root";
	public static String PASSWORD = "";

	private boolean connectSuccesful;
	
	public Connect() {
		connectSuccesful = false;
		try (Connection conn = (Connection) DriverManager.getConnection(DB_URL+DB_NAME, USER_NAME, PASSWORD)){
            conn.close();
            connectSuccesful = true;
        } catch (SQLException e) {
            catchSQLException(e.getMessage());
        }
	}
	
    public void catchSQLException(String message){
    	if (message.equals("Unknown database 'store'")) {
            createDatabase();
            createTable();
        }else if (message.equals("Access denied for user 'root'@'localhost' (using password: YES)")) {
            JOptionPane.showMessageDialog(null, "Kiểm tra lại password của cơ sở dữ liệu.");
        }else if (message.equals("Access denied for user " + "'" + USER_NAME + "'" + "@'localhost' (using password: YES)")) {
            JOptionPane.showMessageDialog(null, "Kiểm tra lại username và password của cơ sở dữ liệu.");
        }else if (message.equals("Access denied for user " + "'" + USER_NAME + "'" + "@'localhost' (using password: NO)")) {
            JOptionPane.showMessageDialog(null, "Kiểm tra lại username của cơ sở dữ liệu.");
        } else{
            JOptionPane.showMessageDialog(null, "Lỗi kết nối tới cơ sở dữ liệu. \nGửi email tới phamdiepa1k55@gmail.com để được giải đáp.\nTrân trọng!");
        }
    }

    private void createTable() {
   
        try (Connection conn = (Connection) DriverManager.getConnection(DB_URL+DB_NAME, USER_NAME, PASSWORD)){
            Statement statement = conn.createStatement();
            statement.executeUpdate(CreateTableQuery.ADMIN);
            statement.executeUpdate(CreateTableQuery.CONSTRUCTION);
            statement.executeUpdate(CreateTableQuery.DEBT);
            statement.executeUpdate(CreateTableQuery.STAFF);
            statement.executeUpdate(CreateTableQuery.SUPPLIER);
            statement.executeUpdate(CreateTableQuery.PRODUCT);
            statement.executeUpdate(CreateTableQuery.FK_PRODUCT_SUPPLIER);
            statement.executeUpdate(CreateTableQuery.STAFF_HOUR);
            statement.executeUpdate(CreateTableQuery.FK_STAFF_HOUR_STAFF);
            statement.executeUpdate(CreateTableQuery.DEBT_BILL);
            statement.executeUpdate(CreateTableQuery.FK_DEBT_BILL_DEBT_CUSTOMER);
            statement.executeUpdate(CreateTableQuery.FK_DEBT_BILL_DEBT_PRODUCT);
            statement.executeUpdate(CreateTableQuery.CONSTRUCTION_BILL);
            statement.executeUpdate(CreateTableQuery.FK_CONSTRUCTION_BILL_CONSTRUCTION);
            statement.executeUpdate(CreateTableQuery.FK_CONSTRUCTION_BILL_PRODUCT);
            statement.executeUpdate(CreateTableQuery.WARRANTY);
            statement.executeUpdate(CreateTableQuery.FK_WARRANTY_PRODUCT);
            connectSuccesful = true;
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createDatabase() {
        try (Connection conn = (Connection) DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD)){
            Statement statement = conn.createStatement();
            statement.executeUpdate("create database " + DB_NAME + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean getConnected() {
		return connectSuccesful;
	}
}
