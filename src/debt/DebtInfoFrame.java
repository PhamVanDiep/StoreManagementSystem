package debt;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

import database.Execute;
import database.Query;
import image_icon.CreateImageIcon;
import main.View;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

public class DebtInfoFrame extends JFrame {

    private JPanel contentPane;
	private JTextField customerNameTf;
	private JTextField customerPhoneNumberTf;
	private JTextField customerAddressTf;
    private JLabel customerIDLb;
    private JButton saveBtn;

    private TableModel tableModel;
    private int n;

	private String tableName;

    public DebtInfoFrame(String customerID, String customerName,String customerPhoneNumber,String customerAddress, 
                        TableModel tableModel, int n, String tableName, boolean isDebt) {

        setIconImage(CreateImageIcon.createImageIcon("/res/cubes.png").getImage());
		setBounds(100, 100, 450, 300);
		setResizable(false);
		setLocation((View.MAX_WIDTH -500)/2, (View.MAX_HEIGHT - 500)/2);
        contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		if(isDebt) {
			setTitle("Sửa thông tin khách nợ");
		}else {
			setTitle("Sửa thông tin dự án");
		}
		setVisible(true);

        this.tableModel = tableModel;
        this.n = n;
		this.tableName = tableName;

        JLabel lblNewLabel = new JLabel("Mã khách hàng:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel.setBounds(55, 11, 125, 35);
		contentPane.add(lblNewLabel);
		
		customerIDLb = new JLabel(customerID);
		customerIDLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		customerIDLb.setBounds(190, 11, 180, 35);
		contentPane.add(customerIDLb);
		
		JLabel lblNewLabel_1 = new JLabel("Tên khách hàng:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(55, 57, 125, 35);
		contentPane.add(lblNewLabel_1);
		
		customerNameTf = new JTextField(customerName);
		customerNameTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		customerNameTf.setBounds(190, 57, 180, 35);
		contentPane.add(customerNameTf);
		customerNameTf.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Số điện thoại:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(55, 103, 125, 35);
		contentPane.add(lblNewLabel_2);
		
		customerPhoneNumberTf = new JTextField(customerPhoneNumber);
		customerPhoneNumberTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		customerPhoneNumberTf.setBounds(190, 103, 180, 35);
		contentPane.add(customerPhoneNumberTf);
		customerPhoneNumberTf.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Địa chỉ:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(55, 149, 125, 35);
		contentPane.add(lblNewLabel_3);
		
		customerAddressTf = new JTextField(customerAddress);
		customerAddressTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		customerAddressTf.setBounds(190, 149, 180, 35);
		contentPane.add(customerAddressTf);
		customerAddressTf.setColumns(10);
		
		saveBtn = new JButton("Lưu");
		saveBtn.setFocusPainted(false);
		saveBtn.setIcon(CreateImageIcon.createImageIcon("/res/save_icon.png"));
		saveBtn.setBackground(new Color(0, 204, 51));
		saveBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		saveBtn.setBounds(165, 200, 120, 45);
		contentPane.add(saveBtn);

        catchSaveClicked();
    }

    private void catchSaveClicked() {
        saveBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Execute.updateQuery(Query.getUpdateQuery(tableName, "name = " + "'" + customerNameTf.getText() + "'" + 
                            ", phone_number = " + "'" + customerPhoneNumberTf.getText() + "'" + 
                            ", address = " + "'" + customerAddressTf.getText() + "'", "id", customerIDLb.getText()));
                    Execute.closeConnect();

                    tableModel.setValueAt(customerNameTf.getText(), n, 1);
                    tableModel.setValueAt(customerPhoneNumberTf.getText(), n, 2);
                    tableModel.setValueAt(customerAddressTf.getText(), n, 3);

                    setVisible(false);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }
}
