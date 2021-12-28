package warranty;

import database.Execute;
import database.Query;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import image_icon.CreateImageIcon;
import main.Controller;
import main.View;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WarrantyInfoFrame extends JFrame{

    private JTextField cusNameTF;
	private JTextField phoneNumberTF;
	private JTextField productIDTF;
	private JTextField productNameTF;
	private JTextField warrantyPlaceTF;
	private JTextField product_statusTF;
	private JTextField warrantyPlacePhoneNumberTF;
    private JComboBox<String> warranty_status_comboBox;
    private JPanel contentPane;

    
	private boolean isAdd;
	private boolean isPressedEnter;
	private String preProductID;
    private JButton warrantyBtn;

    private ArrayList<Integer> warrantyIDs;
    private DefaultTableModel defaultTableModel;
    private int n;

    private ArrayList<JTextField> jTextFields;

    public WarrantyInfoFrame(String customer_name, String customer_phonenumber, String product_id, String product_name, 
                                String supplier_name, String supplier_phonenumber, String product_status, 
                                String warranty_status, DefaultTableModel defaultTableModel, 
                                ArrayList<Integer> warrantyIDs, int n, boolean isAdd) {

        setIconImage(CreateImageIcon.createImageIcon("/res/cubes.png").getImage());
		setBounds(0, 0, 450, 450);
		setResizable(false);
		setLocation((View.MAX_WIDTH - 450)/2, (View.MAX_HEIGHT - 450)/2);
		if (isAdd) {
			setTitle("Thêm bảo hành");
		}else {
			setTitle("Sửa thông tin bảo hành");
		}
		setVisible(true);
        
        this.isAdd = isAdd;
		isPressedEnter = false;
        this.warrantyIDs = warrantyIDs;
        this.defaultTableModel = defaultTableModel;
        this.n = n;

        jTextFields = new ArrayList<>();

        contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Tên khách gửi:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel.setBounds(50, 22, 110, 35);
		contentPane.add(lblNewLabel);
		
		cusNameTF = new JTextField(customer_name);
		cusNameTF.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		cusNameTF.setBounds(170, 22, 205, 35);
		contentPane.add(cusNameTF);
		cusNameTF.setColumns(10);
		jTextFields.add(cusNameTF);

		JLabel lblNewLabel_1 = new JLabel("Số điện thoại:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(50, 68, 110, 35);
		contentPane.add(lblNewLabel_1);
		
		phoneNumberTF = new JTextField(customer_phonenumber);
		phoneNumberTF.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		phoneNumberTF.setBounds(170, 68, 205, 35);
		contentPane.add(phoneNumberTF);
		phoneNumberTF.setColumns(10);
		jTextFields.add(phoneNumberTF);

		JLabel lblNewLabel_2 = new JLabel("Mã SP:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(50, 114, 80, 35);
		contentPane.add(lblNewLabel_2);
		
        preProductID = product_id;
		productIDTF = new JTextField(product_id);
		productIDTF.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		productIDTF.setBounds(140, 114, 102, 35);
		contentPane.add(productIDTF);
		productIDTF.setColumns(10);
		jTextFields.add(productIDTF);

		JLabel lblNewLabel_3 = new JLabel("Tên sản phẩm:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(50, 160, 110, 35);
		contentPane.add(lblNewLabel_3);
		
		productNameTF = new JTextField(product_name);
		productNameTF.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		productNameTF.setEditable(false);
		productNameTF.setBounds(170, 160, 205, 35);
		contentPane.add(productNameTF);
		productNameTF.setColumns(10);
		jTextFields.add(productNameTF);

		JLabel lblNewLabel_4 = new JLabel("Tên ĐVBH:");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_4.setBounds(50, 206, 110, 35);
		contentPane.add(lblNewLabel_4);
		
		warrantyPlaceTF = new JTextField(supplier_name);
		warrantyPlaceTF.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		warrantyPlaceTF.setEditable(false);
		warrantyPlaceTF.setBounds(170, 206, 205, 35);
		contentPane.add(warrantyPlaceTF);
		warrantyPlaceTF.setColumns(10);
		jTextFields.add(warrantyPlaceTF);

		JLabel lblNewLabel_5 = new JLabel("SĐT ĐVBH:");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_5.setBounds(50, 252, 110, 35);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_7 = new JLabel("Tình trạng SP:");
		lblNewLabel_7.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_7.setBounds(50, 298, 110, 35);
		contentPane.add(lblNewLabel_7);
		
		product_statusTF= new JTextField(product_status);
		product_statusTF.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		product_statusTF.setBounds(170, 298, 205, 35);
		contentPane.add(product_statusTF);
		product_statusTF.setColumns(10);
		jTextFields.add(product_statusTF);

		warrantyPlacePhoneNumberTF = new JTextField(supplier_phonenumber);
		warrantyPlacePhoneNumberTF.setEditable(false);
		warrantyPlacePhoneNumberTF.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		warrantyPlacePhoneNumberTF.setBounds(170, 252, 205, 35);
		contentPane.add(warrantyPlacePhoneNumberTF);
		warrantyPlacePhoneNumberTF.setColumns(10);
		jTextFields.add(warrantyPlacePhoneNumberTF);

		warranty_status_comboBox = new JComboBox<>();
		warranty_status_comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		warranty_status_comboBox.setBounds(265, 114, 110, 35);
        warranty_status_comboBox.addItem("Chưa gửi");
		warranty_status_comboBox.addItem("Đã gửi");
		warranty_status_comboBox.addItem("Đã BH");
        if (isAdd == false) {
            warranty_status_comboBox.setSelectedItem(warranty_status);
        }
		contentPane.add(warranty_status_comboBox);
		
		warrantyBtn = new JButton("");
        if (isAdd) {
            warrantyBtn.setText("Thêm");
            warrantyBtn.setIcon(CreateImageIcon.createImageIcon("/res/plus_icon.png"));
        } else {
            warrantyBtn.setText("Lưu");
            warrantyBtn.setIcon(CreateImageIcon.createImageIcon("/res/save_icon.png"));
        }

		warrantyBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		warrantyBtn.setBackground(new Color(0, 204, 51));
		warrantyBtn.setBounds(170, 355, 110, 45);
		contentPane.add(warrantyBtn);

        catchConfirmProductID();
        catchAddOrSaveWarranty();
    }

    private void catchConfirmProductID() {
		productIDTF.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e){
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						ResultSet resultSet = Execute.executeQuery("select product.product_name, supplier.name, supplier.phone_number "
								+ "from product, supplier where product.supplier_id = supplier.id and product.id = " + "'"+ productIDTF.getText() + "'");
						while (resultSet.next()) {
							productNameTF.setText(resultSet.getString("product.product_name"));
							warrantyPlaceTF.setText(resultSet.getString("supplier.name"));
							warrantyPlacePhoneNumberTF.setText(resultSet.getString("supplier.phone_number"));
						}
						preProductID = productIDTF.getText();
						isPressedEnter = true;
						Execute.closeConnect();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

    private void catchAddOrSaveWarranty() {
		
		warrantyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isAdd) {
					if (isPressedEnter) {
						try {
							Execute.addQuery(Query.getInsertQuery("warranty", " NULL" + ",'"+cusNameTF.getText()+"'"
									+",'"+phoneNumberTF.getText()+"'" +",'"+productIDTF.getText()+"'"
									+ ",'"+warranty_status_comboBox.getSelectedItem().toString()+"'" 
									+ ",'"+product_statusTF.getText()+"'" ));
							Execute.closeConnect();
							insertNewRowIntoTable();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Hãy xác nhận mã sản phẩm", null, JOptionPane.WARNING_MESSAGE);
					}
				}else {
					if (preProductID.equals(productIDTF.getText().toString())) {
						String update = "name = " + "'"+cusNameTF.getText()+"'"
								+",phone_number = '"+phoneNumberTF.getText()+"'" 
								+",product_id = '"+productIDTF.getText()+"'"
								+ ",warranty_status = '"+warranty_status_comboBox.getSelectedItem().toString()+"'" 
								+ ",product_status = '"+product_statusTF.getText()+"'";
						try {
							Execute.updateQuery(Query.getUpdateQuery("warranty", update, "id", warrantyIDs.get(n).toString()));
							Execute.closeConnect();
							updateRowIntoTable();
							setVisible(false);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Hãy xác nhận mã sản phẩm", null, JOptionPane.WARNING_MESSAGE);
					}
				}	
			}
		});
	}

    private void updateRowIntoTable() {
		try {
			ResultSet resultSet = Execute.executeQuery("select warranty.name, warranty.phone_number, warranty.warranty_status, product.id, "
						+ "product.product_name, supplier.name, supplier.phone_number, warranty.product_status "
						+ "from warranty, product, supplier where warranty.product_id = product.id and "
						+ "product.supplier_id = supplier.id and warranty.id = " + "'"+ warrantyIDs.get(n) +"'");
			while(resultSet.next()) {
				
				defaultTableModel.setValueAt(resultSet.getString("name"), n, 0);
				defaultTableModel.setValueAt(resultSet.getString("phone_number"), n, 1);
				defaultTableModel.setValueAt(resultSet.getString("product.id"), n, 2);
				defaultTableModel.setValueAt(resultSet.getString("product_name"), n, 3);
				defaultTableModel.setValueAt(resultSet.getString("supplier.name"), n, 4);
				defaultTableModel.setValueAt(resultSet.getString("supplier.phone_number"), n, 5);
				defaultTableModel.setValueAt(resultSet.getString("warranty_status"), n, 6);
				defaultTableModel.setValueAt(resultSet.getString("product_status"), n, 7);
				
			}
			Execute.closeConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    private void insertNewRowIntoTable() {
		try {
			ResultSet resultSet = Execute.executeQuery("select warranty.id, warranty.name, warranty.phone_number, product.id, warranty.warranty_status,"
					+ "product.product_name, supplier.name, supplier.phone_number, warranty.product_status "
					+ "from warranty, product, supplier where warranty.product_id = product.id and "
					+ "product.supplier_id = supplier.id ORDER BY warranty.id DESC LIMIT 1");
			while(resultSet.next()) {
				warrantyIDs.add(resultSet.getInt("id"));
				
				Vector<String> vector = new Vector<>();
				
				vector.add(resultSet.getString("name"));
				vector.add(resultSet.getString("phone_number"));
				vector.add(resultSet.getString("product.id"));
				vector.add(resultSet.getString("product_name"));
				vector.add(resultSet.getString("supplier.name"));
				vector.add(resultSet.getString("supplier.phone_number"));
				vector.add(resultSet.getString("warranty_status"));
				vector.add(resultSet.getString("product_status"));
				
				defaultTableModel.addRow(vector);
				Controller.refreshTextField(getTextFields());
				isPressedEnter = false;
			}
			Execute.closeConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public ArrayList<JTextField> getTextFields() {
        return jTextFields;
    }
}
