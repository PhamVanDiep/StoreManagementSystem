package staff;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import buttonClicked.ButtonDelete;
import buttonClicked.ButtonEditor;
import buttonClicked.ButtonEditorToChangeFrame;
import buttonClicked.ButtonRenderer;
import database.Execute;
import database.Query;
import image_icon.CreateImageIcon;
import main.Controller;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffPane extends JPanel {

    private JPanel panel_1;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    private ButtonDelete delButton;
    private ButtonRenderer delButtonRenderer;
    private ButtonRenderer detailButtonRenderer;
    private ButtonEditorToChangeFrame detailButtonEditor;
    private ButtonEditor editButtonEditor;
    private ButtonRenderer editButtonRenderer;
    private ResultSet resultSet;
    private ResultSet resultSetCondition;
    private int hour;

    public StaffPane() {
        
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
		
		Vector<String> cols = new Vector<>();

		cols = new Vector<String>();
        cols.add("Số CCCD");
        cols.add("Tên nhân viên");
        cols.add("SĐT");
        cols.add("Địa chỉ");
        cols.add("Lương/giờ");
        cols.add("Số giờ");
        cols.add("Tổng lương");
        cols.add("");
        cols.add(" ");
        cols.add("  ");

		defaultTableModel.setColumnIdentifiers(cols);
		
		delButton = new ButtonDelete(new JCheckBox(), table, defaultTableModel, null, this);
		delButtonRenderer = new ButtonRenderer("Xóa", '3');
        
        detailButtonRenderer = new ButtonRenderer("Chi tiết", '1');
		detailButtonEditor = new ButtonEditorToChangeFrame(new JCheckBox(), "Chi tiết",table, this);
		
		editButtonEditor = new ButtonEditor(new JCheckBox(), "Sửa", table, this);
		editButtonRenderer = new ButtonRenderer("Sửa", '2');
		
		table.setModel(defaultTableModel);
		
		for (int c = 0; c < table.getColumnCount() - 3; c++)
		{	
		    table.setDefaultEditor(table.getColumnClass(c), null);        // remove editor
		}
		table.getTableHeader().setPreferredSize(new Dimension(100, 30));
		table.getTableHeader().setFont(new Font("Times New Romans", 1, 20));
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(80);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		table.getColumnModel().getColumn(7).setPreferredWidth(120);
		table.getColumnModel().getColumn(7).setMaxWidth(120);
		table.getColumnModel().getColumn(8).setPreferredWidth(80);
		table.getColumnModel().getColumn(8).setMaxWidth(80);
		table.getColumnModel().getColumn(9).setPreferredWidth(80);
		table.getColumnModel().getColumn(9).setMaxWidth(80);
        table.getColumn("").setCellRenderer(detailButtonRenderer);
		table.getColumn("").setCellEditor(detailButtonEditor);
        table.getColumn(" ").setCellRenderer(editButtonRenderer);
		table.getColumn(" ").setCellEditor(editButtonEditor);
		table.getColumn("  ").setCellRenderer(delButtonRenderer);
		table.getColumn("  ").setCellEditor(delButton);
		table.setRowHeight(table.getRowHeight() + 20);	
		
		table.setFont(new Font("Times New Romans", Font.PLAIN, 18));
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		
		try {
            insertStaffsToTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		JScrollPane scrollPane = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		panel_3.add(scrollPane);
    }

    public void insertStaffsToTable() throws SQLException {
    	resultSet = Execute.executeQuery(Query.getSelectAll("staff"));

		while (resultSet.next()) {
	    	String cccdCondition = resultSet.getString("cccd");
	    	cccdCondition = "cccd = " + "'" + cccdCondition + "'";
	    	resultSetCondition = Execute.executeQuery(Query.getSumAllWithCondition("staff_hour", "hour", cccdCondition));
	    	int h = 0;
	    	while (resultSetCondition.next()) {
				h += resultSetCondition.getInt(1);
			}
	    	hour = h;
			Vector<String> vector = new Vector<String>();
			vector.add(resultSet.getString("cccd"));
			vector.add(resultSet.getString("name"));
			vector.add(resultSet.getString("phone_number"));
			vector.add(resultSet.getString("address"));
			vector.add(Controller.priceWithDecimal(resultSet.getInt("salary_per_hour")));
			vector.add(Integer.toString(h));
			int totalSalary = resultSet.getInt("salary_per_hour")*h;
			vector.add(Controller.priceWithDecimal(totalSalary));
			defaultTableModel.addRow(vector);
		}
		Execute.closeConnect();
	}
    
    private void initNorthPane() {
        JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setPreferredSize(new Dimension(getWidth(),60));
		panel_1.add(panel_2, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Danh sách nhân viên");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		
		JButton newStaffBtn = new JButton("Nhân viên mới");
		newStaffBtn.setBackground(new Color(0, 204, 0));
		newStaffBtn.setForeground(new Color(255, 255, 255));
		newStaffBtn.setIcon(CreateImageIcon.createImageIcon("/res/add_staff_icon.png"));
		newStaffBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        newStaffBtn.setFocusPainted(false);
        
		newStaffBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				newStaffBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				newStaffBtn.setBackground(new Color(238, 123, 76));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				newStaffBtn.setBackground(new Color(0, 204, 0));
			}
		});

        newStaffBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                new StaffInfoFrame(null, null, null, null, null, table.getModel(), defaultTableModel, -1, true);
            }

        });
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
					.addGap(137)
					.addComponent(newStaffBtn, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(newStaffBtn, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
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

    public int getHour() {
		return hour;
	}

	public DefaultTableModel getDefaultTableModel() {
		return defaultTableModel;
	}
}
