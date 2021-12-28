package construction;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;

import image_icon.CreateImageIcon;
import main.Controller;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import buttonClicked.ButtonDelete;
import buttonClicked.ButtonEditor;
import buttonClicked.ButtonEditorToChangeFrame;
import buttonClicked.ButtonRenderer;
import database.Execute;
import database.Query;
import detail_frame.ConstructionDetailController;
import detail_frame.DetailConstructionPane;
import detail_frame.DetailFrame;

import javax.swing.JCheckBox;
import java.util.Vector;
import javax.swing.JTable;

import java.awt.event.*;

public class ConstructionPane extends JPanel{
    
    private JTable table;
    private DefaultTableModel defaultTableModel;
    private Vector<String> cols;
    private ButtonEditorToChangeFrame detailButton;
    private ButtonRenderer detailButtonRenderer;
    private ButtonRenderer delButtonRenderer;

    private ResultSet resultSet;
	private int selectedRowNumber;
	private ButtonEditor editButtonEditor;
	private ButtonRenderer editButtonRenderer;
	private ButtonDelete delButton;
    private JButton newConstructionBtn;

    public ConstructionPane() {
        
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
		
		JLabel lblNewLabel = new JLabel("Danh sách dự án");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));

        newConstructionBtn = new JButton("Dự án mới");
		newConstructionBtn.setIcon(CreateImageIcon.createImageIcon("/res/new_debt_icon.png"));
        newConstructionBtn.setFocusPainted(false);
		newConstructionBtn.setBackground(new Color(0, 204, 51));
		newConstructionBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
					.addGap(142)
					.addComponent(newConstructionBtn, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(newConstructionBtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
					.addGap(10))
		);
		panel_2.setLayout(gl_panel_2);

        JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
        table = new JTable();
		defaultTableModel = new DefaultTableModel();
		
		cols = new Vector<>();
        cols.add("ID");
		cols.add("Tên dự án");
		cols.add("SĐT");
		cols.add("Địa chỉ");
		cols.add("Tổng tiền");
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
		table.getColumnModel().getColumn(5).setMaxWidth(120);

		table.getColumnModel().getColumn(6).setPreferredWidth(80);
		table.getColumnModel().getColumn(6).setMaxWidth(80);

		table.getColumnModel().getColumn(7).setPreferredWidth(80);
		table.getColumnModel().getColumn(7).setMaxWidth(80);

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

		JScrollPane newConstructionBtn = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		panel_3.add(newConstructionBtn);

        createNewConstruction();
    }

    private void createNewConstruction() {
		newConstructionBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				DetailConstructionPane detailConstructionPane = new DetailConstructionPane(null, null, null, null, defaultTableModel, -1, true);
				new ConstructionDetailController(detailConstructionPane);
				new DetailFrame(detailConstructionPane, '3');	
			}
		});
	}

    private void insertIntoTable() {
        try {
            resultSet = Execute.executeQuery(Query.getSelectAll("construction"));
            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));
                vector.add(resultSet.getString("name"));
                vector.add(resultSet.getString("phone_number"));
                vector.add(resultSet.getString("address"));
                vector.add(Controller.priceWithDecimal(Integer.parseInt(getTotalPrice(resultSet.getString("id")))));
                defaultTableModel.addRow(vector);
            }
            Execute.closeConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTotalPrice(String id) {
        int totalPrice = 0;
        try {
            ResultSet resultSet = Execute.executeQuery(Query.getSelectAllWithCondition("construction_bill", "construction_id", id));
            while (resultSet.next()) {
                totalPrice += (int)(resultSet.getDouble("quantity") * resultSet.getInt("price"));
            }
            Execute.closeConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Integer.toString(totalPrice);
    }

    public int getSelectedRowNumber() {
		return selectedRowNumber;
	}

	public void setSelectedRowNumber(int selectedRowNumber) {
		this.selectedRowNumber = selectedRowNumber;
	}

	public JTable getTable() {
		return table;
	}

	public DefaultTableModel getDefaultTableModel() {
		return defaultTableModel;
	}
}