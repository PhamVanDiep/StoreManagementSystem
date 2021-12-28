package detail_frame;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import buttonClicked.ButtonDelete;
import buttonClicked.ButtonRenderer;
import database.Execute;
import database.Query;
import image_icon.CreateImageIcon;
import main.Controller;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JProgressBar;
import javax.swing.JComboBox;

public class DetailStaffPane extends JPanel {

    private JTextField dateTf;
	private JTextField hourTf;
	private JTextField startDate;
    private JLabel total_hourLb;
    private JLabel total_salaryLb;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    private Vector<String> cols;
    private ButtonDelete delButtonEditor;
    private ButtonRenderer delButtonRenderer;
    private ResultSet resultSet;

    private String cccd;
    private int hour;
    private int total_salary;
    private int salary_per_hour;
    private JButton addBtn;
    private DefaultTableModel defaultTableModelOfStaff;
    private int n;
    private JButton clearBtn;

	private JComboBox<String> monthJCB;

	private String sortString;
	private String startDateString = "";
	private TableRowSorter<DefaultTableModel> tableRowSorter;
	private int selectedRowNumber;
	
    public DetailStaffPane(String cccd, String name, String phone_number, String address, 
        String salary_per_hour, DefaultTableModel defaultTableModelOfStaff, int n) {

        setLayout(new BorderLayout(0, 0));

        this.cccd = cccd;
        this.salary_per_hour = Controller.removeDot(salary_per_hour);
		this.n = n;
		this.defaultTableModelOfStaff = defaultTableModelOfStaff;
		selectedRowNumber = 0;
		hour = 0;
		
        initLeftPane(cccd, name, phone_number, address, salary_per_hour);
        initCenterPane();
        initRightPane();
        catchAddStaffHour();
        catchClearStaffHour();
		dataFilter();
		catchDateFilter();
    }

	private void dataFilter() {
		monthJCB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				sortString = "";
				if (monthJCB.getSelectedIndex() > 0) {
					if (monthJCB.getSelectedIndex() < 10 ) {
						sortString = "/0"+ monthJCB.getSelectedIndex()+"/";
					}else{
						sortString = "/"+ monthJCB.getSelectedIndex()+"/";
					}
					tableRowSorter = new TableRowSorter<>(defaultTableModel);
					table.setRowSorter(tableRowSorter);
					tableRowSorter.setRowFilter(RowFilter.regexFilter(sortString.trim(),1));
				}else{
					tableRowSorter = new TableRowSorter<>(defaultTableModel);
					table.setRowSorter(tableRowSorter);
					tableRowSorter.setRowFilter(RowFilter.regexFilter(sortString.trim(),1));
				}
			}
		});
	}

	private void catchDateFilter(){
		
		startDate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (startDate.getText().length() == 1) {
					startDateString = "0" + startDate.getText() + sortString;
				}else{
					startDateString = startDate.getText() + sortString;
				}
				tableRowSorter = new TableRowSorter<>(defaultTableModel);
				table.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(startDateString.trim(),1));
			}
			
			public void keyReleased(KeyEvent e) {
				if (startDate.getText().length() == 1) {
					startDateString = "0" + startDate.getText() + sortString;
				}else{
					startDateString = startDate.getText() + sortString;
				}
				tableRowSorter = new TableRowSorter<>(defaultTableModel);
				table.setRowSorter(tableRowSorter);
				tableRowSorter.setRowFilter(RowFilter.regexFilter(startDateString.trim(),1));
			}
		});
	}

	
    private void catchClearStaffHour() {
        
        clearBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Execute.delQuery(Query.getDelQuery("staff_hour", "cccd", cccd));
                    int j = table.getModel().getRowCount();
                    for (int i = j-1; i >= 0; i--) {
                        defaultTableModel.removeRow(i);
                    }
                    total_hourLb.setText("0");
                    total_salaryLb.setText("0");
                    total_salary = 0;
                    hour = 0;
                    refresh(hour);
                    Execute.closeConnect();
                } catch (SQLException sql) {
                    sql.printStackTrace();
                }
            }
        });
    }

    private void initRightPane() {
        JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setPreferredSize(new Dimension(200, getHeight()));
		panel_2.setMaximumSize(new Dimension(200, getHeight()));
		add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Chấm công");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel_1.setBounds(10, 11, 180, 40);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Ngày:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(10, 62, 60, 35);
		panel_2.add(lblNewLabel_3);
		
		dateTf = new JTextField((new SimpleDateFormat("dd/MM/yyyy")).format(new Date()));
		dateTf.setBackground(new Color(153, 255, 255));
		dateTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		dateTf.setBounds(80, 62, 110, 35);
		panel_2.add(dateTf);
		dateTf.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Số giờ:");
		lblNewLabel_7.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_7.setBounds(10, 108, 60, 35);
		panel_2.add(lblNewLabel_7);
		
		hourTf = new JTextField();
		hourTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		hourTf.setBounds(80, 108, 45, 35);
		panel_2.add(hourTf);
		hourTf.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("(giờ)");
		lblNewLabel_9.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_9.setBounds(135, 108, 55, 35);
		panel_2.add(lblNewLabel_9);
		
		addBtn = new JButton("Thêm");
		addBtn.setBackground(new Color(0, 204, 51));
		addBtn.setIcon(CreateImageIcon.createImageIcon("/res/add_icon.png"));
		addBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		addBtn.setBounds(40, 154, 120, 40);
		panel_2.add(addBtn);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(0, 205, 200, 10);
		panel_2.add(progressBar);
		
		JLabel lblNewLabel_12 = new JLabel("Lọc thông tin");
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_12.setIcon(CreateImageIcon.createImageIcon("/res/filter_icon.png"));
		lblNewLabel_12.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_12.setBounds(10, 226, 180, 50);
		panel_2.add(lblNewLabel_12);
		
		JLabel lblNewLabel_14 = new JLabel("Tháng:");
		lblNewLabel_14.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_14.setBounds(10, 287, 80, 35);
		panel_2.add(lblNewLabel_14);
		
		monthJCB = new JComboBox<>();
		monthJCB.addItem("--");
		monthJCB.addItem("1");
		monthJCB.addItem("2");
		monthJCB.addItem("3");
		monthJCB.addItem("4");
		monthJCB.addItem("5");
		monthJCB.addItem("6");
		monthJCB.addItem("7");
		monthJCB.addItem("8");
		monthJCB.addItem("9");
		monthJCB.addItem("10");
		monthJCB.addItem("11");
		monthJCB.addItem("12");

		monthJCB.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		monthJCB.setBounds(10, 333, 80, 35);
		panel_2.add(monthJCB);
		
		JLabel lblNewLabel_17 = new JLabel("Ngày:");
		lblNewLabel_17.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_17.setBounds(115, 287, 75, 35);
		panel_2.add(lblNewLabel_17);
		
		startDate = new JTextField();
		startDate.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		startDate.setBounds(115, 333, 75, 35);
		panel_2.add(startDate);
		startDate.setColumns(10);
		
		clearBtn = new JButton("Xóa tất cả");
		clearBtn.setForeground(new Color(255, 255, 255));
		clearBtn.setBackground(new Color(255, 0, 0));
		clearBtn.setIcon(CreateImageIcon.createImageIcon("/res/clear_icon.png"));
		clearBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		clearBtn.setBounds(25, 400, 150, 40);
		panel_2.add(clearBtn);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setBounds(0, 379, 200, 10);
		panel_2.add(progressBar_1);
    }

    private void initCenterPane() {
        JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setPreferredSize(new Dimension(getWidth(), 60));
		panel_1.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel_16 = new JLabel("Bảng chấm công");
		lblNewLabel_16.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_16.setVerticalAlignment(SwingConstants.CENTER);
		lblNewLabel_16.setFont(new Font("Times New Roman", Font.BOLD, 25));
		panel_3.add(lblNewLabel_16);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new GridLayout(1, 0, 0, 0));
		
        table = new JTable();
		defaultTableModel = new DefaultTableModel();
		
		cols = new Vector<String>();
        cols.add("STT");
        cols.add("Ngày");
        cols.add("Số giờ làm");
        cols.add(" ");
        
        defaultTableModel.setColumnIdentifiers(cols);
		
		delButtonEditor = new ButtonDelete(new JCheckBox(), table, defaultTableModel, null, this);
		delButtonRenderer = new ButtonRenderer("Xóa",'3') ;
		
		table.setModel(defaultTableModel);
		for (int c = 0; c < table.getColumnCount() - 2; c++)
		{	
		    table.setDefaultEditor(table.getColumnClass(c), null);        // remove editor
		}
		table.getTableHeader().setPreferredSize(new Dimension(100, 30));
		table.getTableHeader().setFont(new Font("Times New Romans", 1, 20));
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(170);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setMaxWidth(80);

		table.setRowHeight(table.getRowHeight() + 20);	
		
		table.getColumn(" ").setCellRenderer(delButtonRenderer);
		table.getColumn(" ").setCellEditor(delButtonEditor);

		table.setFont(new Font("Times New Romans", Font.PLAIN, 18));
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		
		try {
            insertStaff_HourToTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
		JScrollPane scrollPane = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		panel_4.add(scrollPane);    
    }

    public void insertStaff_HourToTable() throws SQLException {
		resultSet = Execute.executeQuery(Query.getSelectAllWithCondition("staff_hour", "cccd", cccd));

		while (resultSet.next()) {
			Vector<String> vector = new Vector<String>();
			vector.add(Integer.toString(defaultTableModel.getRowCount() + 1));
			vector.add(resultSet.getString("date"));
			vector.add(resultSet.getString("hour"));
			hour += resultSet.getInt("hour");
			defaultTableModel.addRow(vector);
		}
		total_hourLb.setText(Integer.toString(hour));
		total_salaryLb.setText(Controller.priceWithDecimal(hour * salary_per_hour));
		Execute.closeConnect();
	}

    private void initLeftPane(String cccd, String name, String phone_number, String address, String salary_per_hour) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
		panel.setPreferredSize(new Dimension(300, getHeight()));
		add(panel, BorderLayout.WEST);
		
		JLabel lblNewLabel = new JLabel("Họ tên:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JLabel nameLb = new JLabel(name);
		nameLb.setBackground(Color.WHITE);
		nameLb.setOpaque(true);
		nameLb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		JLabel lblNewLabel_2 = new JLabel("SĐT:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JLabel phone_numberLb = new JLabel(phone_number);
		phone_numberLb.setBackground(Color.WHITE);
		phone_numberLb.setOpaque(true);
		phone_numberLb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		JLabel lblNewLabel_4 = new JLabel("Địa chỉ:");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JLabel addressLb = new JLabel(address);
		addressLb.setBackground(Color.WHITE);
		addressLb.setOpaque(true);
		addressLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JLabel lblNewLabel_6 = new JLabel("Số CCCD:");
		lblNewLabel_6.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JLabel cccdLb = new JLabel(cccd);
		cccdLb.setBackground(Color.WHITE);
		cccdLb.setOpaque(true);
		cccdLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JLabel lblNewLabel_8 = new JLabel("Lương/giờ:");
		lblNewLabel_8.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JLabel salary_per_hourLb = new JLabel(salary_per_hour);
		salary_per_hourLb.setBackground(Color.WHITE);
		salary_per_hourLb.setOpaque(true);
		salary_per_hourLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JLabel lblNewLabel_10 = new JLabel("(VNĐ)");
		lblNewLabel_10.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JLabel lblNewLabel_11 = new JLabel("Tổng giờ làm:");
		lblNewLabel_11.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		total_hourLb = new JLabel();
		total_hourLb.setBackground(Color.WHITE);
		total_hourLb.setOpaque(true);
		total_hourLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JLabel lblNewLabel_13 = new JLabel("TỔNG LƯƠNG");
		lblNewLabel_13.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_13.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		total_salaryLb = new JLabel();
		total_salaryLb.setBackground(new Color(255, 153, 102));
		total_salaryLb.setOpaque(true);
		total_salaryLb.setHorizontalAlignment(SwingConstants.CENTER);
		total_salaryLb.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		
		JLabel lblNewLabel_15 = new JLabel("(VNĐ)");
		lblNewLabel_15.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_15.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		JLabel lblNewLabel_5 = new JLabel("(giờ)");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_15, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addComponent(total_salaryLb, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addComponent(lblNewLabel_13, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_6, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(cccdLb, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
						.addComponent(addressLb, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(lblNewLabel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)))
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(phone_numberLb, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
								.addComponent(nameLb, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblNewLabel_11, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(total_hourLb, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
								.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
									.addComponent(lblNewLabel_8, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(salary_per_hourLb, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_5)
								.addComponent(lblNewLabel_10, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(nameLb, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(phone_numberLb, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(addressLb, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(cccdLb, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_6, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_8, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(salary_per_hourLb, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_10, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(total_hourLb, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(lblNewLabel_11, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(lblNewLabel_5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(41)
					.addComponent(lblNewLabel_13, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(total_salaryLb, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabel_15, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(46))
		);
		panel.setLayout(gl_panel);
    }

    private void catchAddStaffHour() {
		addBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (getAddHourQuery() != null) {
                    try {
                        Execute.addQuery(getAddHourQuery());
                        Execute.closeConnect();
                        hour += Integer.parseInt(hourTf.getText()); 
                        refresh(hour);
                        Vector<String> vector = new Vector<>();
                        vector.add(Integer.toString(defaultTableModel.getRowCount() + 1));
                        vector.add(dateTf.getText());
                        vector.add(hourTf.getText());
                        defaultTableModel.addRow(vector);
                        hourTf.setText(null);
                    } catch (SQLException sql) {
                        sql.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Hãy điền đầy đủ thông tin.", null, JOptionPane.WARNING_MESSAGE);
                }
            }
            
        });
	}
	
	private String getAddHourQuery() {
		if (dateTf.getText().length() != 0 && hourTf.getText().length() != 0) {
			return "insert into staff_hour(cccd, date, hour) values (" + "'" + cccd + "'," + "'" + dateTf.getText() + "'," + "'" 
					+ hourTf.getText() + "')" ;
		}else {
			return null;
		}
	}

	public void setHour(int hour) {
		this.hour = hour;
	}
	
	public int getHour() {
		return hour;
	}
	
    public String getCccd() {
        return cccd;
    }

    public void refresh(int hour) {
    	    
        total_salary = hour*salary_per_hour;
        total_hourLb.setText(Integer.toString(hour));
        defaultTableModelOfStaff.setValueAt(Integer.toString(hour), n, 5);
        total_salaryLb.setText(Controller.priceWithDecimal(total_salary));
        defaultTableModelOfStaff.setValueAt(Controller.priceWithDecimal(total_salary), n, 6);
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

	public TableRowSorter<DefaultTableModel> getTableRowSorter() {
		return tableRowSorter;
	}

	public JTable getTable() {
		return table;
	}
	
	public int getN() {
		return n;
	}
}
