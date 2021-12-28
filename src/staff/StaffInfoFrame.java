package staff;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import database.Execute;
import database.Query;
import image_icon.CreateImageIcon;
import main.Controller;
import main.View;

public class StaffInfoFrame extends JFrame{
    
    private JTextField nameTF;
	private JTextField phone_numberTF;
	private JTextField addressTF;
	private JTextField salary_per_hourTF;
	private JButton saveBtn;
	private JTextField cccdTf;

    private TableModel tableModel;
    private int n;
    private DefaultTableModel defaultTableModel;

    private ArrayList<JTextField> jTextFields;

	public StaffInfoFrame(String cccd, String name, String phone_number, String address, String salary_per_hour, 
    TableModel tableModel, DefaultTableModel defaultTableModel, int n, boolean isNew) {

		setIconImage(CreateImageIcon.createImageIcon("/res/cubes.png").getImage());
		setBounds(0, 0, 400, 300);
		getContentPane().setLayout(null);
		setResizable(false);
		if (isNew) {
			setTitle("Thêm nhân viên");
		}else {
			setTitle("Sửa thông tin nhân viên");
		}
		setLocation((View.MAX_WIDTH -400)/2, (View.MAX_HEIGHT - 300)/2);
        setVisible(true);

		JLabel lblNewLabel = new JLabel("CCCD:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(25, 15, 107, 30);
		getContentPane().add(lblNewLabel);
		
        jTextFields = new ArrayList<>();

		cccdTf = new JTextField(cccd);
		cccdTf.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cccdTf.setBounds(144, 15, 200, 30);
        if (isNew) {
            cccdTf.setEditable(true);
        }else{
            cccdTf.setEditable(false);
        }
		getContentPane().add(cccdTf);
		jTextFields.add(cccdTf);

		JLabel lblNewLabel_2 = new JLabel("Tên NV:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(25, 55, 107, 30);
		getContentPane().add(lblNewLabel_2);
		
		nameTF = new JTextField(name);
		nameTF.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		nameTF.setBounds(144, 55, 200, 30);
		getContentPane().add(nameTF);
		nameTF.setColumns(10);
		jTextFields.add(nameTF);

		JLabel lblNewLabel_3 = new JLabel("SĐT:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(25, 95, 107, 30);
		getContentPane().add(lblNewLabel_3);
		
		phone_numberTF = new JTextField(phone_number);
		phone_numberTF.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		phone_numberTF.setBounds(144, 95, 200, 30);
		getContentPane().add(phone_numberTF);
		phone_numberTF.setColumns(10);
		jTextFields.add(phone_numberTF);

		JLabel lblNewLabel_4 = new JLabel("Địa chỉ:");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_4.setBounds(25, 135, 107, 30);
		getContentPane().add(lblNewLabel_4);
		
		addressTF = new JTextField(address);
		addressTF.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		addressTF.setBounds(144, 135, 200, 30);
		getContentPane().add(addressTF);
		addressTF.setColumns(10);
		jTextFields.add(addressTF);

		JLabel lblNewLabel_5 = new JLabel("Lương/giờ:");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_5.setBounds(25, 175, 107, 30);
		getContentPane().add(lblNewLabel_5);
		
		salary_per_hourTF = new JTextField(salary_per_hour);
		salary_per_hourTF.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		salary_per_hourTF.setBounds(144, 175, 125, 30);
		getContentPane().add(salary_per_hourTF);
		salary_per_hourTF.setColumns(10);
		jTextFields.add(salary_per_hourTF);

        JLabel lblNewLabel_6 = new JLabel("(VNĐ)");
        lblNewLabel_6.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_6.setBounds(280, 175, 60, 30);
		getContentPane().add(lblNewLabel_6);

		saveBtn = new JButton("Lưu");
		saveBtn.setFont(new Font("Times New Roman", Font.BOLD, 20));
		saveBtn.setBounds(150, 215, 101, 35);
        saveBtn.setBackground(new Color(33, 197, 25));
		getContentPane().add(saveBtn);
		
        this.tableModel = tableModel;
        this.n = n;
        this.defaultTableModel = defaultTableModel;

		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isNew) {
                    catchAddNewStaff();
                }else{
                    catchUpdate();
                }
			}
		});
		
	}
	
	private void catchAddNewStaff() {
        if (getAddStaffQuery() != null) {
            try {
                Execute.addQuery(getAddStaffQuery());
                Execute.closeConnect();
                addNewRecordIntoTable();
                Controller.refreshTextField(getjTextFields());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }else {
            JOptionPane.showMessageDialog(null, "Hãy điền đầy đủ thông tin.", null, JOptionPane.WARNING_MESSAGE);
        }
    }

    public JFrame getFrame() {
		return this;
	}

    public ArrayList<JTextField> getjTextFields() {
        return jTextFields;
    }

    private void catchUpdate() {
        String update = "name = " + "'" + nameTF.getText()+ "'" + ", phone_number = " 
						+ "'" + phone_numberTF.getText() + "'" + ", address = " + "'" + addressTF.getText() + "'" + ", salary_per_hour = "
						+ "'" + Controller.removeDot(salary_per_hourTF.getText()) + "'";
        try {
            Execute.updateQuery(Query.getUpdateQuery("staff", update, "cccd", cccdTf.getText()));
            Execute.closeConnect();
            tableModel.setValueAt(nameTF.getText(), n, 1);
            tableModel.setValueAt(phone_numberTF.getText(), n, 2);
            tableModel.setValueAt(addressTF.getText(), n, 3);
            tableModel.setValueAt(Controller.priceWithDecimal(Controller.removeDot(salary_per_hourTF.getText())), n, 4);
            if ((String)tableModel.getValueAt(n, 5) != "") {
                String s = (String)tableModel.getValueAt(n, 5);
                int h = Integer.parseInt(s);
                h *= Controller.removeDot(salary_per_hourTF.getText());
                s = Controller.priceWithDecimal(h);
                tableModel.setValueAt(s , n, 6);
            }
            setVisible(false);
            JOptionPane.showMessageDialog(null, "Sửa thành công");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public String getAddStaffQuery() {
		if (nameTF.getText().length() != 0 && addressTF.getText().length() != 0
				&& cccdTf.getText().length() != 0 && phone_numberTF.getText().length() != 0 && salary_per_hourTF.getText().length() != 0) {
					return Query.getInsertQuery("staff(name, phone_number, address, salary_per_hour, cccd)", "'" + nameTF.getText() + "'" + ","  + "'" + 
												phone_numberTF.getText() + "'" + "," + "'" + addressTF.getText() + "'" + "," + "'" + salary_per_hourTF.getText() + "'"
												+ "," + "'" + cccdTf.getText() + "'");
		}else {
			return null;
		}

	}

    private void addNewRecordIntoTable() throws SQLException {
    
        Vector<String> vector = new Vector<String>();
        vector.add(cccdTf.getText());
        vector.add(nameTF.getText());
        vector.add(phone_numberTF.getText());
        vector.add(addressTF.getText());
        vector.add(Controller.priceWithDecimal(Integer.parseInt(salary_per_hourTF.getText())));
        vector.add("0");
        vector.add("0");
        defaultTableModel.addRow(vector);
    }
}
