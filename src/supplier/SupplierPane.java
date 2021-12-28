package supplier;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import buttonClicked.ButtonDelete;
import buttonClicked.ButtonEditor;
import buttonClicked.ButtonRenderer;
import database.Execute;
import database.Query;
import image_icon.CreateImageIcon;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierPane extends JPanel {
    
    private JPanel panel_1;
	private JTable table;
	private DefaultTableModel defaultTableModel;
	private Vector<String> cols;

	private ResultSet resultSet;
	
    public SupplierPane() {

        setLayout(new BorderLayout(0, 0));

        initWestPane();

        panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));
        
        initNorthPane();
        initCenterPane();
    }

    private void initNorthPane() {
        JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setPreferredSize(new Dimension(getWidth(),60));
		panel_1.add(panel_2, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Danh sách nhà cung cấp");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setBackground(Color.WHITE);
		
		JButton newSupplierBtn = new JButton("NCC mới");
		newSupplierBtn.setBackground(new Color(0, 204, 0));
		newSupplierBtn.setForeground(new Color(255, 255, 255));
		newSupplierBtn.setIcon(CreateImageIcon.createImageIcon("/res/add_supplier_icon.png"));
		newSupplierBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		newSupplierBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				newSupplierBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				newSupplierBtn.setBackground(new Color(238, 123, 76));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				newSupplierBtn.setBackground(new Color(0, 204, 0));
			}
		});
		newSupplierBtn.setFocusPainted(false);
		newSupplierBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new SupplierInfoFrame(null, null, null, null, null, table.getModel(), defaultTableModel, -1, true);
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
					.addGap(142)
					.addComponent(newSupplierBtn, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(newSupplierBtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
					.addGap(10))
		);
		panel_2.setLayout(gl_panel_2);
    }

    private void initCenterPane() {

        JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));

        table = new JTable();
		
		defaultTableModel = new DefaultTableModel();
		
		cols = new Vector<String>();
        cols.add("ID");
        cols.add("Tên NCC");
        cols.add("SĐT");
        cols.add("Địa chỉ");
        cols.add("Mặt hàng");
        cols.add("");
        cols.add(" ");
		defaultTableModel.setColumnIdentifiers(cols);
		
		ButtonDelete delButtonEditor = new ButtonDelete(new JCheckBox(), table, defaultTableModel, null, this);
		ButtonRenderer delButtonRenderer = new ButtonRenderer("Xóa", '3');
		
		ButtonEditor editButtonEditor = new ButtonEditor(new JCheckBox(), "Sửa", table, this);
		ButtonRenderer editButtonRenderer = new ButtonRenderer("Sửa",'2');

		table.setModel(defaultTableModel);
		for (int c = 0; c < table.getColumnCount() - 2; c++)
		{	
		    table.setDefaultEditor(table.getColumnClass(c), null);        // remove editor
		}
		table.getTableHeader().setPreferredSize(new Dimension(100, 30));
		table.getTableHeader().setFont(new Font("Times New Romans", 1, 20));
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(400);
		table.getColumnModel().getColumn(4).setPreferredWidth(300);
		table.getColumnModel().getColumn(5).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setMaxWidth(80);
		table.getColumnModel().getColumn(6).setPreferredWidth(80);
		table.getColumnModel().getColumn(6).setMaxWidth(80);
		table.setRowHeight(table.getRowHeight() + 20);	
		table.getColumn("").setCellRenderer(editButtonRenderer);
		table.getColumn("").setCellEditor(editButtonEditor);
		table.getColumn(" ").setCellRenderer(delButtonRenderer);
		table.getColumn(" ").setCellEditor(delButtonEditor);
		
		table.setFont(new Font("Times New Romans", Font.PLAIN, 18));
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		
		try {
			insertSuppliersToTable();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		panel_3.add(scrollPane);
    }

    private void initWestPane() {
        JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		add(panel, BorderLayout.WEST);
    }

	public void insertSuppliersToTable() throws SQLException {
		resultSet = Execute.executeQuery(Query.getSelectAll("supplier"));
		while (resultSet.next()) {
			Vector<String> vector = new Vector<String>();
			vector.add(Integer.toString(resultSet.getInt("id")));
			vector.add(resultSet.getString("name"));
			vector.add(resultSet.getString("phone_number"));
			vector.add(resultSet.getString("address"));
			vector.add(resultSet.getString("type"));
			defaultTableModel.addRow(vector);
		}
		Execute.closeConnect();
	}

	public DefaultTableModel getDefaultTableModel() {
		return defaultTableModel;
	}
}
