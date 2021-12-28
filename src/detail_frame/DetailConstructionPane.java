package detail_frame;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import buttonClicked.ButtonDelete;
import buttonClicked.ButtonEditor;
import buttonClicked.ButtonRenderer;
import database.Execute;
import database.Query;
import image_icon.CreateImageIcon;
import main.Controller;

import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class DetailConstructionPane extends JPanel{
    
    private JTextField customerNameTf;
	private JTextField customerPhoneNumberTf;
	private JTextField customerAddressTf;
	private JTextField searchByProductIDTf;
	private JTextField searchByProductNameTf;
	private JTextField productIDTf;
	private JTextField quantityTf;
    private JLabel productNameLb;
	private JLabel unitLb;
	private static JLabel totalPriceLb;
	private JButton exportBillBtn;
	private JButton addProductBtn;

    private String customerID;
	private int n;

	private boolean isAdd;
	private static int totalPrice;
    private DefaultTableModel defaultConstructionTableModel;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    private Vector<String> cols;
    private ArrayList<Integer> constructionBillIDs;

	private TableRowSorter<DefaultTableModel> tableRowSorter;
	private int selectedRowNumber;
	
    public DetailConstructionPane(String customerID, String customerName, String customerPhoneNumber,String customerAddress,
                                	DefaultTableModel defaultConstructionTableModel,  int n, boolean isAdd) {

        setLayout(new BorderLayout(0, 0));
        
        this.customerID = customerID;
		this.n = n;
		this.defaultConstructionTableModel = defaultConstructionTableModel;
		this.isAdd = isAdd;         

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(250, getWidth()));
		add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel.add(panel_3, BorderLayout.EAST);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(255, 255, 255));
		panel_5.setPreferredSize(new Dimension(getWidth(), 100));
		panel_4.add(panel_5, BorderLayout.NORTH);
		
		exportBillBtn = new JButton("In hóa đơn");
		exportBillBtn.setBackground(new Color(255, 255, 0));
		exportBillBtn.setIcon(CreateImageIcon.createImageIcon("/res/bill_icon.png"));
		exportBillBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addGap(36)
					.addComponent(exportBillBtn, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(37, Short.MAX_VALUE))
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addGap(29)
					.addComponent(exportBillBtn, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(32, Short.MAX_VALUE))
		);
		panel_5.setLayout(gl_panel_5);
		
		JPanel panel_9 = new JPanel();
		panel_9.setPreferredSize(new Dimension(250, getHeight()));
		panel_9.setMaximumSize(new Dimension(250, getHeight()));
		panel_9.setBackground(new Color(255, 255, 255));
		panel_4.add(panel_9, BorderLayout.CENTER);
		panel_9.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("Mã sản phẩm:");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_5.setBounds(10, 11, 120, 35);
		panel_9.add(lblNewLabel_5);
		
		productIDTf = new JTextField();
		productIDTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		productIDTf.setBounds(140, 11, 100, 35);
		panel_9.add(productIDTf);
		productIDTf.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Tên sản phẩm:");
		lblNewLabel_6.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_6.setBounds(10, 57, 120, 35);
		panel_9.add(lblNewLabel_6);
		
		productNameLb = new JLabel("");
		productNameLb.setBackground(new Color(102, 255, 255));
		productNameLb.setOpaque(true);
		productNameLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		productNameLb.setBounds(10, 103, 230, 35);
		panel_9.add(productNameLb);
		
		JLabel lblNewLabel_8 = new JLabel("Số lượng:");
		lblNewLabel_8.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_8.setBounds(10, 149, 85, 35);
		panel_9.add(lblNewLabel_8);
		
		quantityTf = new JTextField();
		quantityTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		quantityTf.setBounds(105, 149, 65, 35);
		panel_9.add(quantityTf);
		quantityTf.setColumns(10);
		
		unitLb = new JLabel("");
		unitLb.setOpaque(true);
		unitLb.setBackground(new Color(255, 255, 51));
		unitLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		unitLb.setBounds(180, 149, 60, 35);
		panel_9.add(unitLb);
		
		addProductBtn = new JButton("Thêm sản phẩm");
		addProductBtn.setIcon(CreateImageIcon.createImageIcon("/res/add_icon.png"));
		addProductBtn.setBackground(new Color(0, 204, 51));
		addProductBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		addProductBtn.setBounds(30, 205, 190, 40);
		panel_9.add(addProductBtn);
		
		JLabel lblNewLabel_10 = new JLabel("THÀNH TIỀN");
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_10.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel_10.setBounds(10, 270, 230, 35);
		panel_9.add(lblNewLabel_10);
		
		totalPrice = 0;
        try {
            ResultSet resultSet = Execute.executeQuery(Query.getSelectAllWithCondition("construction_bill", "construction_id", customerID));
            while (resultSet.next()) {
                totalPrice += resultSet.getInt("quantity") * resultSet.getInt("price");
            }
            Execute.closeConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

		totalPriceLb = new JLabel(Controller.priceWithDecimal(totalPrice));
		totalPriceLb.setOpaque(true);
		totalPriceLb.setForeground(new Color(255, 255, 255));
		totalPriceLb.setBackground(new Color(255, 153, 51));
		totalPriceLb.setFont(new Font("Times New Roman", Font.BOLD, 30));
		totalPriceLb.setHorizontalAlignment(SwingConstants.CENTER);
		totalPriceLb.setBounds(10, 316, 230, 55);
		panel_9.add(totalPriceLb);
		
		JLabel lblNewLabel_12 = new JLabel("(VNĐ)");
		lblNewLabel_12.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_12.setBounds(10, 382, 230, 35);
		panel_9.add(lblNewLabel_12);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setPreferredSize(new Dimension(getWidth(), 100));
		panel_1.add(panel_2, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Tên khách hàng:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		customerNameTf = new JTextField(customerName);
		customerNameTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		customerNameTf.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Số điện thoại:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		customerPhoneNumberTf = new JTextField(customerPhoneNumber);
		customerPhoneNumberTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		customerPhoneNumberTf.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Địa chỉ:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		customerAddressTf = new JTextField(customerAddress);
		customerAddressTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		customerAddressTf.setColumns(10);

		if (isAdd == false) {
			customerNameTf.setEditable(false);
			customerAddressTf.setEditable(false);
			customerPhoneNumberTf.setEditable(false);
		}

		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(customerAddressTf, GroupLayout.PREFERRED_SIZE, 391, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(customerNameTf, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(customerPhoneNumberTf, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(customerPhoneNumberTf, Alignment.LEADING)
						.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(customerNameTf, Alignment.LEADING)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(customerAddressTf, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_6 = new JPanel();
		panel_1.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		panel_7.setPreferredSize(new Dimension(getWidth(), 50));
		panel_6.add(panel_7, BorderLayout.NORTH);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(CreateImageIcon.createImageIcon("/res/download.png"));
		
		searchByProductIDTf = new JTextField();
		searchByProductIDTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		searchByProductIDTf.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(CreateImageIcon.createImageIcon("/res/download.png"));
		
		searchByProductNameTf = new JTextField();
		searchByProductNameTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		searchByProductNameTf.setColumns(10);
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addGap(89)
					.addComponent(lblNewLabel_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(searchByProductIDTf, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
					.addGap(98)
					.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(searchByProductNameTf, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
					.addGap(48))
		);
		gl_panel_7.setVerticalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_7.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_7.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_4, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 28, Short.MAX_VALUE)
						.addComponent(searchByProductNameTf, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
						.addComponent(searchByProductIDTf, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
						.addComponent(lblNewLabel_3, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 28, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel_7.setLayout(gl_panel_7);
		
		JPanel panel_8 = new JPanel();
		panel_6.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new GridLayout(1, 0, 0, 0));
		
        table = new JTable();
		defaultTableModel = new DefaultTableModel();
		
		cols = new Vector<>();
		cols.add("STT");
		cols.add("Mã SP");
		cols.add("Tên sản phẩm");
		cols.add("SL");
		cols.add("Đơn vị");
		cols.add("Đơn giá");
		cols.add("Thành tiền");
		cols.add(" ");
		cols.add("");

		defaultTableModel.setColumnIdentifiers(cols);
		
		constructionBillIDs = new ArrayList<>();

		ButtonDelete delButton = new ButtonDelete(new JCheckBox(), table, defaultTableModel, constructionBillIDs, this);
		ButtonRenderer delButtonRenderer = new ButtonRenderer("Xóa", '3');
		
        ButtonEditor editButtonEditor = new ButtonEditor(new JCheckBox(), "Sửa", table, this);
		ButtonRenderer editButtonRenderer = new ButtonRenderer("Sửa", '2');
        
		table.setModel(defaultTableModel);
		
		for (int c = 0; c < table.getColumnCount() - 1; c++)
		{	
			table.setDefaultEditor(table.getColumnClass(c), null);        // remove editor
		}
		table.getTableHeader().setPreferredSize(new Dimension(100, 30));
		table.getTableHeader().setFont(new Font("Times New Romans", 1, 20));
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(120);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);

		table.getColumnModel().getColumn(7).setPreferredWidth(80);
        table.getColumnModel().getColumn(7).setMaxWidth(80);
		table.getColumnModel().getColumn(8).setPreferredWidth(80);
		table.getColumnModel().getColumn(8).setMaxWidth(80);

		table.setRowHeight(table.getRowHeight() + 20);	

		table.getColumn("").setCellRenderer(delButtonRenderer);
		table.getColumn("").setCellEditor(delButton);

        table.getColumn(" ").setCellRenderer(editButtonRenderer);
		table.getColumn(" ").setCellEditor(editButtonEditor);

		table.setFont(new Font("Times New Romans", Font.PLAIN, 18));
		table.getColumnModel().getColumn(0).setCellRenderer(Controller.centerDefaultTableCellRenderer());	
		table.getColumnModel().getColumn(3).setCellRenderer(Controller.centerDefaultTableCellRenderer());
		table.getColumnModel().getColumn(4).setCellRenderer(Controller.centerDefaultTableCellRenderer());
		table.getColumnModel().getColumn(6).setCellRenderer(Controller.rightDefaultTableCellRenderer());
		
		if (isAdd == false) {
			insertIntoTable();
		}

		JScrollPane scrollPane = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		panel_8.add(scrollPane);

		tableRowSorter = new TableRowSorter<>(defaultTableModel);
		table.setRowSorter(tableRowSorter);
    }

    private void insertIntoTable() {
		try {
			ResultSet resultSet = Execute.executeQuery(Query.getSelectAllWithCondition("construction_bill", "construction_id", customerID));
			while (resultSet.next()) {
				int cons_bill_id = resultSet.getInt("id");
				String product_id = resultSet.getString("product_id");
				String unit = null;
				String product_name = null;

				ResultSet resultSet2 = Execute.executeQuery(Query.getSelectAllWithCondition("product", "id", product_id));
				if (resultSet2.next()) {
					unit = resultSet2.getString("unit");
					product_name = resultSet2.getString("product_name");
				}
				constructionBillIDs.add(cons_bill_id);
				
				double quantity = resultSet.getDouble("quantity");
				Number temp = Controller.convertQuantity(quantity, unit);
				int price = resultSet.getInt("price");
				
				Vector<String> vector = new Vector<>();
				vector.add(Integer.toString(defaultTableModel.getRowCount() + 1));
                vector.add(product_id);
                vector.add(product_name);
                vector.add(temp.toString());
                vector.add(unit);
                vector.add(Controller.priceWithDecimal(price));
                vector.add(Controller.priceWithDecimal(Controller.calculatePrice(price, temp)));
                defaultTableModel.addRow(vector);
			}
			Execute.closeConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public static void setTotalPrice(int n) {
		totalPrice = n;
		totalPriceLb.setText(Controller.priceWithDecimal(totalPrice));
	}

	public static int getTotalPrice(){
		return totalPrice;
	}
	public JButton getExportBillBtn() {
		return exportBillBtn;
	}

	public int getN() {
		return n;
	}

	public boolean getIsAdd() {
		return isAdd;
	}

	public JLabel getUnitLb() {
		return unitLb;
	}

	public void setN(int n) {
		this.n = n;
	}

    public DefaultTableModel getDefaultConstructionTableModel() {
        return defaultConstructionTableModel;
    }

	public JTable getTable() {
		return table;
	}

	public DefaultTableModel getDefaultTableModel() {
		return defaultTableModel;
	}

	public JButton getAddBtn() {
		return addProductBtn;
	}

	public JTextField getProductIDTf() {
		return productIDTf;
	}

	public JTextField getQuantityTf() {
		return quantityTf;
	}

	public JLabel getProductNameLb() {
		return productNameLb;
	}

	public JTextField getCustomerNameTf() {
		return customerNameTf;
	}

	public JTextField getCustomerAddressTf() {
		return customerAddressTf;
	}

	public JTextField getCustomerPhoneNumberTf() {
		return customerPhoneNumberTf;
	}

	public ArrayList<Integer> getConstructionBillIDs() {
		return constructionBillIDs;
	}

	public JTextField getSearchByProductIDTf() {
		return searchByProductIDTf;
	}

	public JTextField getSearchByProductNameTf() {
		return searchByProductNameTf;
	}

	public TableRowSorter<DefaultTableModel> getTableRowSorter() {
		return tableRowSorter;
	}

	public int getSelectedRowNumber() {
		return selectedRowNumber;
	}

	public void setSelectedRowNumber(int selectedRowNumber) {
		if (table.getRowSorter() == null) {
			this.selectedRowNumber = selectedRowNumber;
		}else {
			this.selectedRowNumber = tableRowSorter.convertRowIndexToModel(selectedRowNumber);
		}
	}

	public static JLabel getTotalPriceLb() {
		return totalPriceLb;
	}
}
