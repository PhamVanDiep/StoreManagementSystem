package debt;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;

import image_icon.CreateImageIcon;
import main.Controller;

import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import buttonClicked.ButtonDelete;
import buttonClicked.ButtonEditor;
import buttonClicked.ButtonEditorToChangeFrame;
import buttonClicked.ButtonRenderer;
import database.Execute;
import database.Query;
import detail_frame.DebtDetailController;
import detail_frame.DetailDebtPane;
import detail_frame.DetailFrame;

import javax.swing.JCheckBox;
import java.util.Vector;
import javax.swing.JTable;

import java.awt.event.*;
public class DebtPane extends JPanel {
    
    private JTextField searchByPhoneNumberTf;
	private JTextField searchByNameTf;
    private JButton newDebtBtn;

    private JTable table;
    private DefaultTableModel defaultTableModel;
    private Vector<String> cols;
    private ButtonEditorToChangeFrame detailButton;
    private ButtonRenderer detailButtonRenderer;
    private ButtonRenderer delButtonRenderer;

	private TableRowSorter<DefaultTableModel> tableRowSorter;
	
    private ResultSet resultSet;
	private int selectedRowNumber;
	private ButtonEditor editButtonEditor;
	private ButtonRenderer editButtonRenderer;
	private ButtonDelete delButton;

    public DebtPane() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		add(panel, BorderLayout.WEST);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setPreferredSize(new Dimension(getWidth(), 60));
		panel_1.add(panel_2, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Danh sách dư nợ");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(CreateImageIcon.createImageIcon("/res/download.png"));
		
		searchByPhoneNumberTf = new JTextField();
		searchByPhoneNumberTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		searchByPhoneNumberTf.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(CreateImageIcon.createImageIcon("/res/download.png"));
		
		searchByNameTf = new JTextField();
		searchByNameTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		searchByNameTf.setColumns(10);
		
		newDebtBtn = new JButton("Dư nợ mới");
		newDebtBtn.setIcon(CreateImageIcon.createImageIcon("/res/new_debt_icon.png"));
        newDebtBtn.setFocusPainted(false);
		newDebtBtn.setBackground(new Color(0, 204, 51));
		newDebtBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(searchByPhoneNumberTf, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(searchByNameTf, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(45)
					.addComponent(newDebtBtn)
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(newDebtBtn, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 35, Short.MAX_VALUE)
						.addComponent(searchByNameTf, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
						.addComponent(searchByPhoneNumberTf, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
						.addComponent(lblNewLabel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
        table = new JTable();
		defaultTableModel = new DefaultTableModel();
		
		cols = new Vector<>();
        cols.add("ID");
		cols.add("Tên khách");
		cols.add("SĐT");
		cols.add("Địa chỉ");
		cols.add("Tổng tiền");
		cols.add("Đã trả");
		cols.add("Còn nợ");
		cols.add("");
		cols.add("  ");
		cols.add(" ");
		defaultTableModel.setColumnIdentifiers(cols);
		
		detailButton = new ButtonEditorToChangeFrame(new JCheckBox(), "Chi tiết", table, this);
		detailButtonRenderer = new ButtonRenderer("Chi tiết", '1');
		
		editButtonEditor = new ButtonEditor(new JCheckBox(), "Sửa", table, this);
		editButtonRenderer = new ButtonRenderer("Sửa", '2');

		delButton = new ButtonDelete(new JCheckBox(), table, defaultTableModel, null, this);
		delButtonRenderer = new ButtonRenderer("Xóa", '3');
		
		table.setModel(defaultTableModel);
		
		for (int c = 0; c < table.getColumnCount() - 3; c++)
		{	
			table.setDefaultEditor(table.getColumnClass(c), null);        // remove editor
		}
		table.getTableHeader().setPreferredSize(new Dimension(100, 30));
		table.getTableHeader().setFont(new Font("Times New Romans", 1, 20));
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(140);
		table.getColumnModel().getColumn(2).setPreferredWidth(130);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(120);
		table.getColumnModel().getColumn(5).setPreferredWidth(120);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);

		table.getColumnModel().getColumn(7).setPreferredWidth(120);
		table.getColumnModel().getColumn(7).setMaxWidth(120);

		table.getColumnModel().getColumn(8).setPreferredWidth(80);
		table.getColumnModel().getColumn(8).setMaxWidth(80);

		table.getColumnModel().getColumn(9).setPreferredWidth(80);
		table.getColumnModel().getColumn(9).setMaxWidth(80);

		table.setRowHeight(table.getRowHeight() + 20);	
		
		table.getColumn(" ").setCellRenderer(delButtonRenderer);
		table.getColumn(" ").setCellEditor(delButton);

		table.getColumn("").setCellRenderer(detailButtonRenderer);
		table.getColumn("").setCellEditor(detailButton);

		table.getColumn("  ").setCellRenderer(editButtonRenderer);
		table.getColumn("  ").setCellEditor(editButtonEditor);

		table.setFont(new Font("Times New Romans", Font.PLAIN, 18));
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		
		insertIntoTable();

		JScrollPane newDebtBtn = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		panel_3.add(newDebtBtn);

		placeHolderOfSearchByPhoneNumber();
		placeHolderOfSearchByName();
		catchSearchByPhoneNumber();
		catchSearchByName();
		createNewDebt();
	}

    private void createNewDebt() {
		newDebtBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				DetailDebtPane detailDebtPane = new DetailDebtPane(null, null, null, null, "0", "0", defaultTableModel, -1, true);
				new DebtDetailController(detailDebtPane);
				new DetailFrame(detailDebtPane, '1');	
			}
		});
	}

	private void insertIntoTable() {
        try {
            resultSet = Execute.executeQuery(Query.getSelectAll("debt_customer"));
            while (resultSet.next()) {
            	String temp1 = Controller.priceWithDecimal(getTotalPrice(resultSet.getString("id")));
            	String temp2 = Controller.priceWithDecimal(resultSet.getInt("paid"));
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("name"));
                vector.add(resultSet.getString("phone_number"));
                vector.add(resultSet.getString("address"));
                vector.add(temp1);
                vector.add(temp2);
                vector.add(Controller.priceWithDecimal(Controller.removeDot(temp1) - Controller.removeDot(temp2)));
                defaultTableModel.addRow(vector);
            }
            Execute.closeConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getTotalPrice(String id) {
        int totalPrice = 0;
        try {
            ResultSet resultSet = Execute.executeQuery(Query.getSelectAllWithCondition("debt_bill", "debt_id", id));
            while (resultSet.next()) {
            	Number quantity = resultSet.getDouble("quantity");
                totalPrice += Controller.calculatePrice(resultSet.getInt("price"), quantity);
            }
            Execute.closeConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPrice;
    }

	private void placeHolderOfSearchByPhoneNumber() {
		searchByPhoneNumberTf.setText("Nhập số điện thoại");
		searchByPhoneNumberTf.setForeground(new Color(153,153,153));

		searchByPhoneNumberTf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (searchByPhoneNumberTf.getText().equals("Nhập số điện thoại")) {
					searchByPhoneNumberTf.setText("");
					searchByPhoneNumberTf.setForeground(new Color(0,0,0));
				}
			}
			public void focusLost(FocusEvent e) {
				if (searchByPhoneNumberTf.getText().equals("")) {
					searchByPhoneNumberTf.setText("Nhập số điện thoại");
					searchByPhoneNumberTf.setForeground(new Color(153,153,153));
				}
			}
		});
	}


	private void placeHolderOfSearchByName() {
		searchByNameTf.setText("Nhập tên khách hàng");
		searchByNameTf.setForeground(new Color(153,153,153));

		searchByNameTf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (searchByNameTf.getText().equals("Nhập tên khách hàng")) {
					searchByNameTf.setText("");
					searchByNameTf.setForeground(new Color(0,0,0));
				}
			}
			public void focusLost(FocusEvent e) {
				if (searchByNameTf.getText().equals("")) {
					searchByNameTf.setText("Nhập tên khách hàng");
					searchByNameTf.setForeground(new Color(153,153,153));
				}
			}
		});
	}

	private void catchSearchByPhoneNumber() {
		searchByPhoneNumberTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				tableRowSorter = new TableRowSorter<>(defaultTableModel);
				table.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchByPhoneNumberTf.getText().trim(),2));
			}
			
			public void keyReleased(KeyEvent e) {
				tableRowSorter = new TableRowSorter<>(defaultTableModel);
				table.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchByPhoneNumberTf.getText().trim(),2));
			}
		});
	}


	private void catchSearchByName() {
		searchByNameTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				tableRowSorter = new TableRowSorter<>(defaultTableModel);
				table.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchByNameTf.getText().trim(),1));
			}
			
			public void keyReleased(KeyEvent e) {
				tableRowSorter = new TableRowSorter<>(defaultTableModel);
				table.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchByNameTf.getText().trim(),1));
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
	
	public TableRowSorter<DefaultTableModel> getTableRowSorter() {
		return tableRowSorter;
	}
}
