package product;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class ProductInfoFrame extends JFrame{
    private JTextField productIDTf;
	private JTextField product_nameTF;
	private JTextField quantityTF;
	private JTextField dateTF;
	private JTextField import_priceTF;
	private JTextField export_priceTF;
	private JComboBox<String> comboBox;
	private JButton save;
	
	private String supplier_name;
	private String phone_number;
	
	private ArrayList<JTextField> jTextFields;
	
	private TableModel tableModel;
	private int n;
	private JComboBox<String> unitBox;

	private String unitBoxSelected;

	private DefaultTableModel defaultTableModel;
	
	private ArrayList<Integer> supplierIDs;
	private int ID_supllierSelected;
	
    public ProductInfoFrame(String id, String name, String quantity,String unit, String date, String import_price, 
            String export_price, int ID_supllierSelected, TableModel tableModel, 
            DefaultTableModel defaultTableModel, int n, boolean isAdd) throws SQLException {
		
		setIconImage(CreateImageIcon.createImageIcon("/res/cubes.png").getImage());
		setBounds(0, 0, 500, 500);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocation((View.MAX_WIDTH -500)/2, (View.MAX_HEIGHT - 500)/2);
		if (isAdd) {
			setTitle("Thêm sản phẩm");
		}else {
			setTitle("Sửa thông tin sản phẩm");
		}
		setVisible(true);

		this.tableModel = tableModel;
		this.n = n;
		this.defaultTableModel = defaultTableModel;
		supplierIDs = new ArrayList<>();
		this.ID_supllierSelected = ID_supllierSelected;
		
		jTextFields = new ArrayList<>();
		
		JLabel lblNewLabel = new JLabel("Mã SP:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(62, 23, 91, 35);
		getContentPane().add(lblNewLabel);
	
		productIDTf = new JTextField();
		if (isAdd == false) {
			productIDTf.setText(id);
			productIDTf.setEditable(false);
		}
		productIDTf.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		productIDTf.setBounds(163, 23, 143, 35);
		getContentPane().add(productIDTf);
		jTextFields.add(productIDTf);
		
		JLabel lblNewLabel_2 = new JLabel("Tên SP:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(62, 83, 91, 35);
		getContentPane().add(lblNewLabel_2);
		
		product_nameTF = new JTextField(name);
		product_nameTF.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		product_nameTF.setBounds(163, 83, 246, 35);
		getContentPane().add(product_nameTF);
		product_nameTF.setColumns(10);
		jTextFields.add(product_nameTF);
		
		JLabel lblNewLabel_3 = new JLabel("SL:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(61, 142, 35, 35);
		getContentPane().add(lblNewLabel_3);
		
		quantityTF = new JTextField(quantity);
		quantityTF.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		quantityTF.setBounds(106, 144, 79, 35);
		getContentPane().add(quantityTF);
		quantityTF.setColumns(10);
		jTextFields.add(quantityTF);
		
		JLabel lblNewLabel_4 = new JLabel("Ngày nhập:");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_4.setBounds(62, 194, 91, 35);
		getContentPane().add(lblNewLabel_4);
		
		dateTF = new JTextField();
		if (isAdd) {
			dateTF.setText((new SimpleDateFormat("dd/MM/yyyy")).format(new Date()));
		} else {
			dateTF.setText(date);
		}
		dateTF.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		dateTF.setBounds(163, 194, 218, 35);
		getContentPane().add(dateTF);
		dateTF.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Giá nhập:");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_5.setBounds(62, 240, 79, 35);
		getContentPane().add(lblNewLabel_5);
		
		import_priceTF = new JTextField();
		if (isAdd == false) {
			import_priceTF.setText(import_price);
		}
		import_priceTF.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		import_priceTF.setBounds(151, 240, 145, 35);
		getContentPane().add(import_priceTF);
		import_priceTF.setColumns(10);
		jTextFields.add(import_priceTF);
		
		JLabel lblNewLabel_6 = new JLabel("(VNĐ)");
		lblNewLabel_6.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_6.setBounds(306, 240, 103, 35);
		getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Giá bán:");
		lblNewLabel_7.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_7.setBounds(62, 286, 79, 35);
		getContentPane().add(lblNewLabel_7);
		
		export_priceTF = new JTextField(export_price);
		export_priceTF.setBounds(151, 286, 145, 35);
		export_priceTF.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		getContentPane().add(export_priceTF);
		export_priceTF.setColumns(10);
		jTextFields.add(export_priceTF);
		
		JLabel lblNewLabel_6_1 = new JLabel("(VNĐ)");
		lblNewLabel_6_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_6_1.setBounds(306, 286, 103, 35);
		getContentPane().add(lblNewLabel_6_1);
		
		comboBox  = new JComboBox<>();
		comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		comboBox.setBounds(151, 343, 230, 35);
		getContentPane().add(comboBox);
		insertSupplierList();
		
		if (ID_supllierSelected != 0) {
			int i;
			for (i = 0; i < supplierIDs.size(); i++) {
				if (supplierIDs.get(i) == ID_supllierSelected) break;
			}
			comboBox.setSelectedItem(comboBox.getItemAt(i));
			supplier_name = comboBox.getSelectedItem().toString();
		}
		
		JLabel lblNewLabel_8 = new JLabel("NCC:");
		lblNewLabel_8.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_8.setBounds(62, 343, 79, 35);
		getContentPane().add(lblNewLabel_8);
		
		save = new JButton();
		if (isAdd) {
			save.setIcon(CreateImageIcon.createImageIcon("/res/plus_icon.png"));
			save.setText("Thêm");
		} else {
			save.setIcon(CreateImageIcon.createImageIcon("/res/save_icon.png"));
			save.setText("Lưu");
		}
		save.setFont(new Font("Times New Roman", Font.BOLD, 20));
		save.setBounds(200, 397, 100, 45);
		save.setBackground(new Color(41, 180, 22));
		getContentPane().add(save);
		
		JLabel lblNewLabel_1 = new JLabel("Đơn vị:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(228, 142, 78, 35);
		getContentPane().add(lblNewLabel_1);
		
		unitBox = new JComboBox<>();
		unitBox.addItem("chiếc");
		unitBox.addItem("m");
		unitBox.addItem("kg");
		unitBox.addItem("lít");
		unitBox.addItem("m\u00B2");
		unitBox.addItem("cuộn");
		unitBox.addItem("bộ");		
		unitBox.setBounds(316, 142, 93, 35);
		if (isAdd == false) {
			unitBox.setSelectedItem(unit);
			unitBoxSelected = unit;
		}else{
			unitBoxSelected = unitBox.getItemAt(0);
			unitBox.setSelectedItem(unitBoxSelected);
		}

		unitBox.setFont(new Font("Times New Romans",Font.PLAIN, 20));
		getContentPane().add(unitBox);
		
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isAdd) {
					catchAddClicked();
				}else{
					catchSaveClicked();
				}
			}
		});
		supplierComboBoxSelected();
		unitComboBoxSelected();
	}
    
	private void unitComboBoxSelected() {
		unitBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				unitBoxSelected = unitBox.getSelectedItem().toString();
			}
		});
	}
	
	private void supplierComboBoxSelected() {
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				supplier_name = comboBox.getSelectedItem().toString();
				ID_supllierSelected = supplierIDs.get(comboBox.getSelectedIndex());
				setPhoneNumber();
			}
		});
	}
	
	private void insertSupplierList() throws SQLException {
		ResultSet resultSetSupplierList = Execute.executeQuery(Query.getSelectAll("supplier"));
		while (resultSetSupplierList.next()) {
			comboBox.addItem(resultSetSupplierList.getString("name"));
			supplierIDs.add(resultSetSupplierList.getInt("id"));
		}
		Execute.closeConnect();
		setPhoneNumber();
	}
	
	private void setPhoneNumber() {
		try {
			ResultSet resultSetSupplierID = Execute.executeQuery(Query.getSelectAllWithCondition("supplier", "id", Integer.toString(ID_supllierSelected)));
			while(resultSetSupplierID.next()) {
				phone_number = resultSetSupplierID.getString("phone_number");
			}
			Execute.closeConnect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void catchSaveClicked() {
		if (Controller.hasNull(jTextFields)) {
			JOptionPane.showMessageDialog(null, "Hãy điền đầy đủ thông tin.", null, JOptionPane.WARNING_MESSAGE);
		}else {
			String update = "product_name = " + "'" + product_nameTF.getText()+ "'" + ", quantity = " 
					+ "'" + quantityTF.getText() + "'" + ", date = " + "'" + dateTF.getText() + "'" + ", import_price = "
					+ "'" + Controller.removeDot(import_priceTF.getText()) + "'"+ ", export_price = "+ "'" + Controller.removeDot(export_priceTF.getText()) + "'" 
					+ ", supplier_id = "+ "'" + ID_supllierSelected + "'"+ ", unit = "+ "'" + unitBoxSelected + "'";
			try {
				Execute.updateQuery(Query.getUpdateQuery("product", update, "id", productIDTf.getText()));
				Execute.closeConnect();
				
				Number quantity = Controller.convertQuantity(Double.parseDouble(quantityTF.getText()), unitBoxSelected);
				ProductPane.getSupplierIDs().set(n, ID_supllierSelected);
				
				tableModel.setValueAt(product_nameTF.getText(), n, 1);
				tableModel.setValueAt(quantity.toString(), n, 2);
				tableModel.setValueAt(unitBoxSelected, n, 3);
				tableModel.setValueAt(Controller.priceWithDecimal(Controller.removeDot(import_priceTF.getText())), n, 4);
				tableModel.setValueAt(Controller.priceWithDecimal(Controller.removeDot(export_priceTF.getText())), n, 5);
				tableModel.setValueAt(dateTF.getText(), n, 6);
				tableModel.setValueAt(supplier_name, n, 7);
				tableModel.setValueAt(phone_number, n, 8);
				setVisible(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}	
	}

	private void catchAddClicked() {
		if (getAddProductQuery() != null) {
			try {
				Execute.addQuery(getAddProductQuery());
				Execute.closeConnect();
				addNewRecord();
				Controller.refreshTextField(jTextFields);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}else {
			JOptionPane.showMessageDialog(null, "Hãy điền đầy đủ thông tin.", null, JOptionPane.WARNING_MESSAGE);
		}
	}

	private void addNewRecord() {
		Vector<String> vector = new Vector<>();
		ProductPane.getSupplierIDs().add(ID_supllierSelected);
		
		vector.add(productIDTf.getText());
		vector.add(product_nameTF.getText());
		vector.add(quantityTF.getText());
		vector.add(unitBoxSelected);
		vector.add("");
		vector.add(Controller.priceWithDecimal(Integer.parseInt(export_priceTF.getText())));
		vector.add(dateTF.getText());
		vector.add(supplier_name);
		vector.add(phone_number);
		defaultTableModel.addRow(vector);
	}
	
	private String getAddProductQuery() {
		if (Controller.hasNull(jTextFields)) {
			System.out.println("null");
			return null;
		}
		else {
			 return Query.getInsertQuery("product(id, product_name, quantity, date, import_price, export_price, supplier_id, unit)", 
					 "'" + productIDTf.getText() + "','" + product_nameTF.getText() + "','" + quantityTF.getText() + "','"
					 + dateTF.getText() + "','" + import_priceTF.getText() + "','"+ export_priceTF.getText()  + "','"
							 + ID_supllierSelected + "','"+ unitBoxSelected + "'");
		}
	}
	public JFrame getFrame() {
		return this;
	}
}
