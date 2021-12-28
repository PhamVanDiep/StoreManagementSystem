package warranty;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;

import buttonClicked.ButtonDelete;
import buttonClicked.ButtonEditor;
import buttonClicked.ButtonRenderer;
import database.Execute;
import image_icon.CreateImageIcon;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Cursor;

public class WarrantyPane extends JPanel {
    
    private JPanel panel_1;
	private JTable table;
	private DefaultTableModel defaultTableModel;
	private Vector<String> cols;
	private ButtonDelete delButton;
	private ButtonRenderer delButtonRenderer;
	private ButtonEditor editButtonEditor;
	private ButtonRenderer editButtonRenderer;
	private ArrayList<Integer> warrabtyIDs;
	private ResultSet resultSet;

    public WarrantyPane() {
        
        setLayout(new BorderLayout(0, 0));

        initWestPane();

        panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        initNorthPane();
        initCenterPane();
    }

    private void initCenterPane() {
        JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));

        table = new JTable();
		defaultTableModel = new DefaultTableModel();
		
		cols = new Vector<>();
		cols.add("Tên khách");
		cols.add("SĐT");
		cols.add("MSP");
		cols.add("Tên SP");
		cols.add("Tên ĐVBH");
		cols.add("SĐT");
		cols.add("TTBH");
		cols.add("Tình trạng SP");
		cols.add("");
		cols.add(" ");
		defaultTableModel.setColumnIdentifiers(cols);
		
		warrabtyIDs = new ArrayList<>();
		
		delButton = new ButtonDelete(new JCheckBox(), table, defaultTableModel, warrabtyIDs, this);
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
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(130);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(140);
		table.getColumnModel().getColumn(5).setPreferredWidth(130);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		table.getColumnModel().getColumn(7).setPreferredWidth(200);

		table.getColumnModel().getColumn(8).setPreferredWidth(80);
		table.getColumnModel().getColumn(8).setMaxWidth(80);

		table.getColumnModel().getColumn(9).setPreferredWidth(80);
		table.getColumnModel().getColumn(9).setMaxWidth(80);

		table.setRowHeight(table.getRowHeight() + 20);	
		
		table.getColumn("").setCellRenderer(editButtonRenderer);
		table.getColumn("").setCellEditor(editButtonEditor);

		table.getColumn(" ").setCellRenderer(delButtonRenderer);
		table.getColumn(" ").setCellEditor(delButton);

		table.setFont(new Font("Times New Romans", Font.PLAIN, 18));
		
		insertIntoTable();
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		panel_3.add(scrollPane);
    }

	private void insertIntoTable() {
		try {
			resultSet = Execute.executeQuery("select warranty.id, warranty.name, warranty.phone_number, warranty.warranty_status, product.id, "
					+ "product.product_name, supplier.name, supplier.phone_number, warranty.product_status "
					+ "from warranty, product, supplier where warranty.product_id = product.id and "
					+ "product.supplier_id = supplier.id");
			while(resultSet.next()) {
				
				warrabtyIDs.add(resultSet.getInt("id"));
				
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
			}
			Execute.closeConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    private void initNorthPane() {
        JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setPreferredSize(new Dimension(getWidth(),60));
		panel_1.add(panel_2, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Danh sách sản phẩm bảo hành");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		
		JButton btnNewButton = new JButton("Bảo hành mới");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 204, 0));
		btnNewButton.setIcon(CreateImageIcon.createImageIcon("/res/shield_icon.png"));
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnNewButton.setFocusPainted(false);

		btnNewButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new WarrantyInfoFrame(null, null, null, null, null, null, null, null, defaultTableModel, warrabtyIDs, -1, true);
			}
		});

		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				btnNewButton.setBackground(new Color(238, 123, 76));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setBackground(new Color(0, 204, 0));
			}
		});

		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
					.addGap(199)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
    }

    private void initWestPane() {
        JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		add(panel, BorderLayout.WEST);
    }

	public DefaultTableModel getDefaultTableModel() {
		return defaultTableModel;
	}

	public ArrayList<Integer> getWarrantyIDs() {
		return warrabtyIDs;
	}
}
