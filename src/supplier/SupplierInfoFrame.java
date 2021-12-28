package supplier;

import javax.swing.JFrame;

import image_icon.CreateImageIcon;
import main.Controller;
import main.View;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import database.Execute;
import database.Query;

import javax.swing.JButton;

public class SupplierInfoFrame extends JFrame{
    private JTextField nameTF;
	private JTextField phone_numberTF;
	private JTextField addressTF;
	private JTextField typeTF;
	private JButton saveBtn;
	private JLabel idLB;

	private ArrayList<JTextField> jTextFields;

    private ResultSet resultSet;

	public SupplierInfoFrame(String id, String name, String phone_number, String address, String type, 
        TableModel tableModel, DefaultTableModel defaultTableModel, int n, boolean isAdd) {
		setIconImage(CreateImageIcon.createImageIcon("/res/cubes.png").getImage());
		setBounds(0, 0, 400, 300);
		getContentPane().setLayout(null);
		setResizable(false);
		if (isAdd) {
			setTitle("Thêm nhà cung cấp");
		}else {
			setTitle("Sửa thông tin NCC");
		}
		setLocation((View.MAX_WIDTH - 400)/2, (View.MAX_HEIGHT - 300)/2);
		setVisible(true);

        jTextFields = new ArrayList<>();

		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(25, 15, 40, 30);
		getContentPane().add(lblNewLabel);
		
		idLB = new JLabel();
        if (isAdd) {
            autoCreateSupplierID();
        } else {
            idLB.setText(id);
        }
		idLB.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		idLB.setBounds(124, 15, 127, 30);
		getContentPane().add(idLB);
		
		JLabel lblNewLabel_2 = new JLabel("Tên NCC:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(25, 55, 87, 30);
		getContentPane().add(lblNewLabel_2);
		
		nameTF = new JTextField(name);
		nameTF.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		nameTF.setBounds(124, 55, 220, 30);
		getContentPane().add(nameTF);
		nameTF.setColumns(10);
		jTextFields.add(nameTF);

		JLabel lblNewLabel_3 = new JLabel("SĐT:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(25, 95, 87, 30);
		getContentPane().add(lblNewLabel_3);
		
		phone_numberTF = new JTextField(phone_number);
		phone_numberTF.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		phone_numberTF.setBounds(124, 95, 220, 30);
		getContentPane().add(phone_numberTF);
		phone_numberTF.setColumns(10);
		jTextFields.add(phone_numberTF);

		JLabel lblNewLabel_4 = new JLabel("Địa chỉ:");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_4.setBounds(25, 135, 87, 30);
		getContentPane().add(lblNewLabel_4);
		
		addressTF = new JTextField(address);
		addressTF.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		addressTF.setBounds(124, 135, 220, 30);
		getContentPane().add(addressTF);
		addressTF.setColumns(10);
		jTextFields.add(addressTF);

		JLabel lblNewLabel_5 = new JLabel("Mặt hàng:");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_5.setBounds(25, 175, 87, 30);
		getContentPane().add(lblNewLabel_5);
		
		typeTF = new JTextField(type);
		typeTF.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		typeTF.setBounds(124, 175, 220, 30);
		getContentPane().add(typeTF);
		typeTF.setColumns(10);
		jTextFields.add(typeTF);

		saveBtn = new JButton();
        if (isAdd) {
            saveBtn.setText("Thêm");
            saveBtn.setIcon(CreateImageIcon.createImageIcon("/res/plus_icon.png"));
        } else {
            saveBtn.setText("Lưu");
            saveBtn.setIcon(CreateImageIcon.createImageIcon("/res/save_icon.png"));
        }
		saveBtn.setFont(new Font("Times New Roman", Font.BOLD, 20));
		saveBtn.setBounds(150, 215, 100, 45);
        saveBtn.setBackground(new Color(41, 180, 22));
        saveBtn.setForeground(new Color(255, 255, 255));
		getContentPane().add(saveBtn);
		
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isAdd) {
                    if (getAddSupplierQuery() != null) {
                        try {
                            Execute.addQuery(getAddSupplierQuery());
                            Vector<String> vector = new Vector<>();
                            vector.add(idLB.getText());
                            vector.add(nameTF.getText());
                            vector.add(phone_numberTF.getText());
                            vector.add(addressTF.getText());
                            vector.add(typeTF.getText());
                            defaultTableModel.addRow(vector);

                            Controller.refreshTextField(getTextFields());
                            autoCreateSupplierID();
                            Execute.closeConnect();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "Hãy điền đầy đủ thông tin.", null, JOptionPane.WARNING_MESSAGE);
                    }
                }else{
                    String update = "name = " + "'" + nameTF.getText()+ "'" + ", phone_number = " 
						+ "'" + phone_numberTF.getText() + "'" + ", address = " + "'" + addressTF.getText() + "'" + ", type = "
						+ "'" + typeTF.getText() + "'";
                    try {
                        Execute.updateQuery(Query.getUpdateQuery("supplier", update, "id", idLB.getText()));
                        Execute.closeConnect();
                        tableModel.setValueAt(nameTF.getText(), n, 1);
                        tableModel.setValueAt(phone_numberTF.getText(), n, 2);
                        tableModel.setValueAt(addressTF.getText(), n, 3);
                        tableModel.setValueAt(typeTF.getText(), n, 4);
                        setVisible(false);
                        JOptionPane.showMessageDialog(null, "Sửa thành công");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
			}
		});
		
	}

    public String getAddSupplierQuery() {
		
		if (nameTF.getText().length() != 0 && addressTF.getText().length() != 0
						&& phone_numberTF.getText().length() != 0 && typeTF.getText().length() != 0) {
			return Query.getInsertQuery("supplier(name, phone_number, address, type)", "'" + nameTF.getText() + "'" + ","  + "'" + 
					phone_numberTF.getText() + "'" + "," + "'" + addressTF.getText() + "'" + "," + "'" + typeTF.getText() + "'");
		}else {
			return null;
		}
	}

    private void autoCreateSupplierID() {
        String s = null;
		try {
			resultSet = Execute.executeQuery(Query.getLastRow("supplier", "id"));
			if(resultSet.next()) {
				int i = Integer.parseInt(resultSet.getString("id")) + 1;
				s = Integer.toString(i);
			}else{
				s = "1";
			}
			Execute.closeConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		idLB.setText(s);
    }
    public ArrayList<JTextField> getTextFields() {
        return jTextFields;
    }

	public JFrame getFrame() {
		return this;
	}
}
