package product;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import buttonClicked.ButtonDelete;
import buttonClicked.ButtonEditor;
import buttonClicked.ButtonRenderer;
import database.Execute;
import image_icon.CreateImageIcon;
import main.Controller;
import security.CheckOwnerPassword;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Cursor;

public class ProductPane extends JPanel{
    private JTextField searchByProductIDTf;
	private JTextField searchByProductNameTf;
    private JPanel panel_1;
	private JTable table;
	private DefaultTableModel defaultTableModel;
	private Vector<String> cols;
	private ButtonDelete delButton;
	private ButtonRenderer delButtonRenderer;
	private ButtonEditor editButtonEditor;
	private ButtonRenderer editButtonRenderer;
	private ResultSet resultSet;
	
	private TableRowSorter<DefaultTableModel> tableRowSorter;
	private JButton newProductBtn;
	private int selectedRowNumber;
	
	private String password;
	private static ArrayList<Integer> supplierIDs;
	
    public ProductPane(String password) {
        setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0,0));
		add(panel_1, BorderLayout.CENTER);

		this.password = password;
		
        initCenterPane();
        initNorthPane();
		initWestPane();
		placeHolderOfSearchByID();
		placeHolderOfSearchByName();
		catchSearchByID();
		catchSearchByName();
    }

    private void initWestPane() {
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		add(panel_2, BorderLayout.WEST);
	}

	private void initNorthPane() {
        JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setPreferredSize(new Dimension(getWidth(),60));
		panel_1.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Danh mục sản phẩm");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setForeground(Color.BLACK);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(CreateImageIcon.createImageIcon("/res/download.png"));
		
		searchByProductIDTf = new JTextField();
		searchByProductIDTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		searchByProductIDTf.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(CreateImageIcon.createImageIcon("/res/download.png"));
		
		searchByProductNameTf = new JTextField();
		searchByProductNameTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		searchByProductNameTf.setColumns(10);
		
		newProductBtn = new JButton("Sản phẩm mới");
		newProductBtn.setForeground(new Color(255, 255, 255));
		newProductBtn.setBackground(new Color(0, 204, 0));
		newProductBtn.setIcon(CreateImageIcon.createImageIcon("/res/plus_icon.png"));
		newProductBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		newProductBtn.setFocusPainted(false);

		newProductBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new ProductInfoFrame(null, null, null, null, null, null, null, 0, table.getModel(), defaultTableModel, -1, true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		newProductBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				newProductBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				newProductBtn.setBackground(new Color(238, 123, 76));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				newProductBtn.setBackground(new Color(0, 204, 0));
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(searchByProductIDTf, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(searchByProductNameTf, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(newProductBtn, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(newProductBtn, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 35, Short.MAX_VALUE)
						.addComponent(searchByProductNameTf, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
						.addComponent(searchByProductIDTf, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
						.addComponent(lblNewLabel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
    }

    private void initCenterPane() {
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));

		table = new JTable();
		defaultTableModel = new DefaultTableModel();
		
		cols = new Vector<String>();
		cols.add("Mã SP");
		cols.add("Tên SP");
		cols.add("SL");
		cols.add("Đơn vị");
		cols.add("Giá nhập/SP");
		cols.add("Giá bán/SP");
		cols.add("Ngày nhập");
		cols.add("NCC");
		cols.add("SĐT");
		cols.add("");
		cols.add(" ");
		
		defaultTableModel.setColumnIdentifiers(cols);
		
		supplierIDs = new ArrayList<>();
		
		delButton = new ButtonDelete(new JCheckBox(), table,defaultTableModel, supplierIDs,  this);
		delButtonRenderer = new ButtonRenderer("Xóa", '3');
		
		editButtonEditor = new ButtonEditor(new JCheckBox(), "Sửa", table, this);
		editButtonRenderer = new ButtonRenderer("Sửa", '2');
		
		table.setModel(defaultTableModel);
		
		for (int c = 0; c < table.getColumnCount() - 2; c++)
		{	
		    table.setDefaultEditor(table.getColumnClass(c), null);        // remove editor
		}
		table.getTableHeader().setPreferredSize(new Dimension(100, 30));
		table.getTableHeader().setFont(new Font("Times New Romans", 1, 20));
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(130);
		table.getColumnModel().getColumn(5).setPreferredWidth(120);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		table.getColumnModel().getColumn(7).setPreferredWidth(180);
		table.getColumnModel().getColumn(8).setPreferredWidth(150);
		table.getColumnModel().getColumn(9).setPreferredWidth(80);
		table.getColumnModel().getColumn(9).setMaxWidth(80);
		table.getColumnModel().getColumn(10).setPreferredWidth(80);
		table.getColumnModel().getColumn(10).setMaxWidth(80);

		table.setRowHeight(table.getRowHeight() + 20);	
		table.getColumn("").setCellRenderer(editButtonRenderer);
		table.getColumn("").setCellEditor(editButtonEditor);
		table.getColumn(" ").setCellRenderer(delButtonRenderer);
		table.getColumn(" ").setCellEditor(delButton);
		
		table.setFont(new Font("Times New Romans", Font.PLAIN, 18));
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		
		try {
			insetIntoTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		hideImportPrice();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				hideImportPrice();
				if (me.getClickCount() == 2) {
					setSelectedRowNumber(table.getSelectedRow());
					new CheckOwnerPassword(password, defaultTableModel, selectedRowNumber);
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		panel_3.add(scrollPane);
    }

	private void insetIntoTable() throws SQLException {
		resultSet = Execute.executeQuery("select product.*, supplier.phone_number, supplier.name  from product, supplier "
				+ "where product.supplier_id = supplier.id");
		
		while (resultSet.next()) {
			Number quantity = Controller.convertQuantity(resultSet.getDouble("quantity"), resultSet.getString("unit"));
			supplierIDs.add(resultSet.getInt("supplier_id"));
			
			Vector<String> vector = new Vector<>();
			vector.add(resultSet.getString("id"));
			vector.add(resultSet.getString("product_name"));
			vector.add(quantity.toString());
			vector.add(resultSet.getString("unit"));
			vector.add("");
			vector.add(Controller.priceWithDecimal(resultSet.getInt("export_price")));
			vector.add(resultSet.getString("date"));
			vector.add(resultSet.getString("name"));
			vector.add(resultSet.getString("phone_number"));
			
			defaultTableModel.addRow(vector);
		}
		Execute.closeConnect();
	}

	public String getProductID() {
		setSelectedRowNumber(table.getSelectedRow());
		return defaultTableModel.getValueAt(selectedRowNumber, 0).toString();
	}
	
	public void hideImportPrice() {
		for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
			defaultTableModel.setValueAt("",i, 4);
		}
	}

	private void placeHolderOfSearchByID() {
		searchByProductIDTf.setText("Nhập mã sản phẩm");
		searchByProductIDTf.setForeground(new Color(153,153,153));

		searchByProductIDTf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (searchByProductIDTf.getText().equals("Nhập mã sản phẩm")) {
					searchByProductIDTf.setText("");
					searchByProductIDTf.setForeground(new Color(0,0,0));
				}
			}
			public void focusLost(FocusEvent e) {
				if (searchByProductIDTf.getText().equals("")) {
					searchByProductIDTf.setText("Nhập mã sản phẩm");
					searchByProductIDTf.setForeground(new Color(153,153,153));
				}
			}
		});
	}


	private void placeHolderOfSearchByName() {
		searchByProductNameTf.setText("Nhập tên sản phẩm");
		searchByProductNameTf.setForeground(new Color(153,153,153));

		searchByProductNameTf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (searchByProductNameTf.getText().equals("Nhập tên sản phẩm")) {
					searchByProductNameTf.setText("");
					searchByProductNameTf.setForeground(new Color(0,0,0));
				}
			}
			public void focusLost(FocusEvent e) {
				if (searchByProductNameTf.getText().equals("")) {
					searchByProductNameTf.setText("Nhập tên sản phẩm");
					searchByProductNameTf.setForeground(new Color(153,153,153));
				}
			}
		});
	}

	private void catchSearchByID() {
		searchByProductIDTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				tableRowSorter = new TableRowSorter<>(defaultTableModel);
				table.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchByProductIDTf.getText().trim(),0));
			}
			
			public void keyReleased(KeyEvent e) {
				tableRowSorter = new TableRowSorter<>(defaultTableModel);
				table.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchByProductIDTf.getText().trim(),0));
			}
		});
	}


	private void catchSearchByName() {
		searchByProductNameTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				tableRowSorter = new TableRowSorter<>(defaultTableModel);
				table.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchByProductNameTf.getText().trim(),1));
			}
			
			public void keyReleased(KeyEvent e) {
				tableRowSorter = new TableRowSorter<>(defaultTableModel);
				table.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchByProductNameTf.getText().trim(),1));
			}
		});
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

	public JTable getTable() {
		return table;
	}

	public DefaultTableModel getDefaultTableModel() {
		return defaultTableModel;
	}
	
	public static ArrayList<Integer> getSupplierIDs() {
		return supplierIDs;
	}
	
	public TableRowSorter<DefaultTableModel> getTableRowSorter() {
		return tableRowSorter;
	}
}

