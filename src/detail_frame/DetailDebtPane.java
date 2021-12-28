package detail_frame;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import buttonClicked.ButtonDelete;
import buttonClicked.ButtonRenderer;
import database.Execute;
import database.Query;
import image_icon.CreateImageIcon;
import main.Controller;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DetailDebtPane extends JPanel {
    private JTextField productIDTf;
	private JTextField quantityTf;
	private JTextField paidTf;
	private JTextField customerNameTf;
	private JTextField customerPhoneNumberTf;
	private JTextField customerAddressTf;
	private JTextField dateTf;
    private JLabel productNameLb;
    private JButton addBtn;
    private JLabel totalPriceLb;
    private JLabel debtLb;
    private JTable table;
    private DefaultTableModel defaultTableModel;
	private DefaultTableModel defaultDebtTableModel;
    private Vector<String> cols;
    private ButtonRenderer delButtonRenderer;

	private String customerID;
	private JButton exportBillBtn;
	private int n;

	private boolean isAdd;
	private JLabel unitLb;
	private int totalPrice;
	private int paid;
	private JButton clearBtn;

	private ArrayList<Integer> debtBillIDs;
	
    public DetailDebtPane(String customerID, String customerName, String customerPhoneNumber,String customerAddress,
						 String totalPrice, String paid, DefaultTableModel defaultDebtTableModel,  int n, boolean isAdd) {
        setLayout(new BorderLayout(0, 0));
		
		this.customerID = customerID;
		this.n = n;
		this.defaultDebtTableModel = defaultDebtTableModel;
		this.isAdd = isAdd;
		this.paid = Controller.removeDot(paid);
		this.totalPrice = Controller.removeDot(totalPrice);
		debtBillIDs = new ArrayList<>();
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, getHeight()));
		panel.setMaximumSize(new Dimension(300, getHeight()));
		add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.EAST);
		
		JPanel panel_11 = new JPanel();
		panel.add(panel_11, BorderLayout.CENTER);
		panel_11.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Mã sản phẩm:");
		lblNewLabel_4.setBounds(10, 11, 120, 35);
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		productIDTf = new JTextField();
		productIDTf.setBounds(135, 11, 135, 35);
		productIDTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		productIDTf.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Tên sản phẩm:");
		lblNewLabel_5.setBounds(10, 58, 107, 35);
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		productNameLb = new JLabel("");
		productNameLb.setBounds(10, 100, 260, 35);
		productNameLb.setBackground(new Color(102, 255, 255));
		productNameLb.setOpaque(true);
		productNameLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JLabel lblNewLabel_7 = new JLabel("Số lượng:");
		lblNewLabel_7.setBounds(10, 141, 82, 35);
		lblNewLabel_7.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		addBtn = new JButton("Thêm");
		addBtn.setBounds(76, 198, 127, 39);
		addBtn.setIcon(CreateImageIcon.createImageIcon("/res/add_icon.png"));
		addBtn.setBackground(new Color(0, 204, 51));
		addBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JLabel lblNewLabel_6 = new JLabel("THÀNH TIỀN");
		lblNewLabel_6.setBounds(10, 255, 260, 36);
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setFont(new Font("Times New Roman", Font.BOLD, 20));
		
		totalPriceLb = new JLabel(totalPrice);
		totalPriceLb.setBounds(10, 297, 260, 45);
		totalPriceLb.setFont(new Font("Times New Roman", Font.BOLD, 25));
		totalPriceLb.setHorizontalAlignment(SwingConstants.CENTER);
		totalPriceLb.setForeground(new Color(0, 0, 0));
		totalPriceLb.setBackground(new Color(255, 153, 51));
		totalPriceLb.setOpaque(true);
		
		JLabel lblNewLabel_9 = new JLabel("Đã trả:");
		lblNewLabel_9.setBounds(10, 390, 60, 35);
		lblNewLabel_9.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JLabel lblNewLabel_10 = new JLabel("Còn nợ:");
		lblNewLabel_10.setBounds(10, 440, 60, 35);
		lblNewLabel_10.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		paidTf = new JTextField(this.paid);
		if (isAdd == false) {
			paidTf.setText(Controller.priceWithDecimal(this.paid));
		}
		paidTf.setBounds(76, 390, 129, 35);
		paidTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		paidTf.setColumns(10);
		
		debtLb = new JLabel();
		if (isAdd == false) {
			debtLb.setText(Controller.priceWithDecimal(this.totalPrice - this.paid));
		}
		debtLb.setBounds(76, 440, 127, 35);
		debtLb.setOpaque(true);
		debtLb.setBackground(new Color(204, 255, 51));
		debtLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		quantityTf = new JTextField();
		quantityTf.setBounds(96, 141, 75, 35);
		quantityTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		quantityTf.setColumns(10);
		
		unitLb = new JLabel("");
		unitLb.setOpaque(true);
		unitLb.setBackground(new Color(255, 255, 51));
		unitLb.setHorizontalAlignment(SwingConstants.CENTER);
		unitLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		unitLb.setBounds(181, 141, 89, 35);
		
		panel_11.add(unitLb);
		panel_11.add(lblNewLabel_4);
		panel_11.add(lblNewLabel_5);
		panel_11.add(productIDTf);
		panel_11.add(lblNewLabel_7);
		panel_11.add(quantityTf);
		panel_11.add(productNameLb);
		panel_11.add(addBtn);
		panel_11.add(lblNewLabel_6);
		panel_11.add(totalPriceLb);
		panel_11.add(lblNewLabel_10);
		panel_11.add(debtLb);
		panel_11.add(lblNewLabel_9);
		panel_11.add(paidTf);
		
		exportBillBtn = new JButton("In hóa đơn");
		exportBillBtn.setIcon(CreateImageIcon.createImageIcon("/res/bill_icon.png"));
		exportBillBtn.setBackground(new Color(255, 255, 51));
		exportBillBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		exportBillBtn.setBounds(61, 500, 160, 40);
		panel_11.add(exportBillBtn);
		
		clearBtn = new JButton("Xóa tất cả");
		clearBtn.setForeground(new Color(255, 255, 255));
		clearBtn.setBackground(new Color(204, 0, 0));
		clearBtn.setIcon(CreateImageIcon.createImageIcon("/res/clear_icon.png"));
		clearBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		clearBtn.setBounds(60, 560, 161, 40);
		panel_11.add(clearBtn);
		
		JLabel lblNewLabel_11 = new JLabel("(VNĐ)");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_11.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_11.setBounds(215, 390, 55, 35);
		panel_11.add(lblNewLabel_11);
		
		JLabel lblNewLabel_12 = new JLabel("(VNĐ)");
		lblNewLabel_12.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_12.setBounds(213, 440, 57, 35);
		panel_11.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("(VNĐ)");
		lblNewLabel_13.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_13.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_13.setBounds(10, 350, 260, 25);
		panel_11.add(lblNewLabel_13);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_6.setPreferredSize(new Dimension(getWidth(), 100));
		panel_1.add(panel_6, BorderLayout.NORTH);
		
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
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		customerAddressTf = new JTextField(customerAddress);
		customerAddressTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		customerAddressTf.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Ngày:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		if (isAdd == false) {
			customerNameTf.setEditable(false);
			customerAddressTf.setEditable(false);
			customerPhoneNumberTf.setEditable(false);
		}

		dateTf = new JTextField((new SimpleDateFormat("dd/MM/yyyy")).format(new Date()));
		dateTf.setEditable(false);
		dateTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		dateTf.setColumns(10);
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_6.createSequentialGroup()
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(customerAddressTf, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
						.addGroup(gl_panel_6.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(customerNameTf, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_6.createSequentialGroup()
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(customerPhoneNumberTf, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
						.addGroup(gl_panel_6.createSequentialGroup()
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dateTf, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(customerPhoneNumberTf, Alignment.LEADING)
						.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(customerNameTf, Alignment.LEADING)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING, false)
						.addComponent(dateTf)
						.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(customerAddressTf)
						.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_6.setLayout(gl_panel_6);
		
		JPanel panel_8 = new JPanel();
		panel_1.add(panel_8, BorderLayout.CENTER);
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
		cols.add("Ngày mua");
		cols.add("");

		defaultTableModel.setColumnIdentifiers(cols);

		ButtonDelete delButton = new ButtonDelete(new JCheckBox(), table, defaultTableModel, debtBillIDs, this);
		delButtonRenderer = new ButtonRenderer("Xóa", '3');
		
		table.setModel(defaultTableModel);
		
		for (int c = 0; c < table.getColumnCount() - 1; c++)
		{	
			table.setDefaultEditor(table.getColumnClass(c), null);        // remove editor
		}
		table.getTableHeader().setPreferredSize(new Dimension(100, 30));
		table.getTableHeader().setFont(new Font("Times New Romans", 1, 20));
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(190);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(120);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		table.getColumnModel().getColumn(7).setPreferredWidth(120);
		table.getColumnModel().getColumn(8).setPreferredWidth(80);
		table.getColumnModel().getColumn(8).setMaxWidth(80);

		table.setRowHeight(table.getRowHeight() + 20);	

		table.getColumn("").setCellRenderer(delButtonRenderer);
		table.getColumn("").setCellEditor(delButton);

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
		
		clearEvent();
    }

    private void clearEvent() {
		clearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Execute.delQuery(Query.getDelQuery("debt_bill", "debt_id", customerID));
					Execute.closeConnect();
					int j = table.getModel().getRowCount();
                    for (int i = j-1; i >= 0; i--) {
                        defaultTableModel.removeRow(i);
                    }
                    setTotalPrice(0);
                    setDebtLb(Integer.toString(getTotalPrice() - getPaid()));
                    
                    defaultDebtTableModel.setValueAt(0, n, 4);
                    defaultDebtTableModel.setValueAt(debtLb.getText(), n, 6);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void insertIntoTable() {
		try {
			ResultSet resultSet = Execute.executeQuery(Query.getSelectAllWithCondition("debt_bill", "debt_id", customerID));
			while (resultSet.next()) {
				int debt_bill_id = resultSet.getInt("id");
				String product_id = resultSet.getString("product_id");
				String unit = null;
				String product_name = null;

				ResultSet resultSet2 = Execute.executeQuery(Query.getSelectAllWithCondition("product", "id", product_id));
				if (resultSet2.next()) {
					unit = resultSet2.getString("unit");
					product_name = resultSet2.getString("product_name");
				}
				
				debtBillIDs.add(debt_bill_id);
				
				double quantity = resultSet.getDouble("quantity");
				Number temp = Controller.convertQuantity(quantity, unit);
				
				Vector<String> vector = new Vector<>();
				vector.add(Integer.toString(defaultTableModel.getRowCount() + 1));
                vector.add(product_id);
                vector.add(product_name);
                vector.add(temp.toString());
                vector.add(unit);
                vector.add(Controller.priceWithDecimal(resultSet.getInt("price")));
                vector.add(Controller.priceWithDecimal(Controller.calculatePrice(resultSet.getInt("price"), temp)));
				vector.add(resultSet.getString("date"));
                defaultTableModel.addRow(vector);
			}	
			Execute.closeConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public JTextField getProductIDTf() {
		return productIDTf;
	}

	public JTextField getQuantityTf() {
		return quantityTf;
	}

	public JTextField getPaidTf() {
		return paidTf;
	}

	public JTextField getCustomerNameTf() {
		return customerNameTf;
	}

	public JTextField getCustomerPhoneNumberTf() {
		return customerPhoneNumberTf;
	}

	public JTextField getCustomerAddressTf() {
		return customerAddressTf;
	}

	public JTextField getDateTf() {
		return dateTf;
	}

	public JLabel getProductNameLb() {
		return productNameLb;
	}

	public JButton getAddBtn() {
		return addBtn;
	}

	public JLabel getTotalPriceLb() {
		return totalPriceLb;
	}

	public JLabel getDebtLb() {
		return debtLb;
	}

	public JTable getTable() {
		return table;
	}

	public DefaultTableModel getDefaultTableModel() {
		return defaultTableModel;
	}

	public DefaultTableModel getDefaultDebtTableModel() {
		return defaultDebtTableModel;
	}

	public ArrayList<Integer> getDebtBillIDs() {
		return debtBillIDs;
	}

	public String getCustomerID() {
		return customerID;
	}

	public JButton getExportBillBtn() {
		return exportBillBtn;
	}

	public int getN() {
		return n;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public boolean getIsAdd() {
		return isAdd;
	}

	public JLabel getUnitLb() {
		return unitLb;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
		changeTotalPrice();
	}
	

	public void changeTotalPrice() {
		totalPriceLb.setText(Controller.priceWithDecimal(totalPrice));
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void setDebtLb(String s) {
		debtLb.setText(Controller.priceWithDecimal(Integer.parseInt(s)));
	}

	public int getPaid() {
		return paid;
	}

	public void setPaid(int paid) {
		this.paid = paid;
	}
}
