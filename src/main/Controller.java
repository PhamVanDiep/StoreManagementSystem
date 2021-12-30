package main;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.Color;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import database.Connect;
import database.Execute;
import database.Query;
import security.LoginFrame;
import security.RegisterFrame;

public class Controller {
	
	public static String store_name;
	public static String phone_number;
	public static String address;
	public static String type;
	
	public Controller() {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
			UIManager.put("TableHeader.font",new Font("Times New Romans", Font.BOLD, 18) );
			UIManager.put("TableHeader.foreground",new Color(255,255,255));
			UIManager.put("TableHeader.background",new Color(24, 48, 119));
			UIManager.put("ProgressBar.background",new Color(88, 165, 214));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Connect connect = new Connect();
		if (connect.getConnected()) {
			try {
				ResultSet resultSet = Execute.executeQuery(Query.getSelectAll("admin"));
				if (resultSet.next()) {
					store_name = resultSet.getString("store_name");
					phone_number = resultSet.getString("phone_number");
					address = resultSet.getString("address");
					type = resultSet.getString("type");
					
					new LoginFrame(resultSet.getString("email"), resultSet.getString("password"));
				}else {
					new RegisterFrame();
				}
				Execute.closeConnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
    public static void refreshTextField(ArrayList<JTextField> jTextFields) {
		for (int i = 0; i < jTextFields.size(); i++) {
			refresh(jTextFields.get(i));
		}
	}
	
	private static void refresh(JTextField jTextField) {
		jTextField.setText(null);
	}
	
	public static boolean hasNull(ArrayList<JTextField> jTextFields) {
		for (int i = 0; i < jTextFields.size(); i++) {
			if (jTextFields.get(i).getText().length() == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static String priceWithDecimal (int price) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(price);
    }

	public static DefaultTableCellRenderer centerDefaultTableCellRenderer(){
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		return centerRenderer;
	}

	public static DefaultTableCellRenderer rightDefaultTableCellRenderer() {
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		return rightRenderer;
	}
	
	public static int calculatePrice(int price, Number quantity) {
		int result = 0;
		if (quantity instanceof Integer){
			result = price * quantity.intValue();
		}else if (quantity instanceof Double) {
			result = (int)(price * quantity.doubleValue());
		}
		return result;
	}
	
	public static Number convertQuantity(double quantity, String unit) {
        if (unit.equals("kg") || unit.equals("m") || unit.equals("lít") || unit.equals("m\u00B2")) {
            return quantity;
        }else{
            return (int)quantity;
        }
    }
	
	public static double addQuantity(double q1, Number q2) {
		return q1 + q2.doubleValue();
	}
	
	public static int removeDot(String s){
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') {
                s = s.substring(0, i) + s.substring(i+1, s.length());
            }
        }
        return Integer.parseInt(s);
    }
	
	public static String getImportPrice(String productID) {
		int import_price = 0;
		try {
			ResultSet resultSet = Execute.executeQuery(Query.getSelectAllWithCondition("product", "id", productID));
			if (resultSet.next()) {
				import_price = resultSet.getInt("import_price");
			}
			Execute.closeConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return priceWithDecimal(import_price);
	}
	
	public static void showImportPrice(DefaultTableModel defaultTableModel, String productID, int n) {
		String import_price = getImportPrice(productID);
		defaultTableModel.setValueAt(import_price, n, 4);
	}
}
