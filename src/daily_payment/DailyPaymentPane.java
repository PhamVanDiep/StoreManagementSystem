package daily_payment;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import buttonClicked.ButtonDelete;
import buttonClicked.ButtonRenderer;
import database.Execute;
import database.Query;
import export_bill.Bill;
import image_icon.CreateImageIcon;
import main.Controller;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DailyPaymentPane extends JPanel {
     
    private JTextField quantityTf;
	private JTextField productIDTf;
	private JTable table;
	private DefaultTableModel defaultTableModel;
	private Vector<String> cols;
	private ButtonDelete delButton;
	private ButtonRenderer delButtonRenderer;

	private ResultSet resultSet;
	private String id;
	private String name;
	private String unit;
	private String export_price;
	private boolean isPressed;
	private JButton addToTableBtn;
	private JButton exportBillBtn;
	private JButton newPageBtn;
	
	private int totalPrice;
	private JLabel productNameLb;
	private JLabel totalPriceLb;
	private JLabel unitLb;

	private int n;
	private Number deltaQuantity;
	
    public  DailyPaymentPane() {
        setLayout(new BorderLayout(0, 0));
        initEastPane();
        initCenterPane();
		isPressed = false;

		catchSearchNameEvent();
		catchAddEvent();
		catchRefreshEvent();
		catchExportBillEvent();
    }

    private void initCenterPane() {
        JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(325, getHeight()));
		add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(325, 60));
		panel_2.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 255, 255));
		panel.add(panel_4, BorderLayout.WEST);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(255, 255, 255));
		panel.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Danh sách sản phẩm đã chọn");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		panel_5.add(lblNewLabel);
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(325, getHeight()-50));
		panel_2.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(255, 255, 255));
		panel_3.add(panel_7, BorderLayout.WEST);
		
		JPanel panel_8 = new JPanel();
		panel_3.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new GridLayout(1, 0, 0, 0));
		
		table = new JTable();
		defaultTableModel = new DefaultTableModel();
		
		cols = new Vector<>();
		cols.add("STT");
		cols.add("Mã SP");
		cols.add("Tên SP");
		cols.add("SL");
		cols.add("Đơn vị");
		cols.add("Giá/SP (VNĐ)");
		cols.add("Thành tiền (VNĐ)");
		cols.add("");
		
		defaultTableModel.setColumnIdentifiers(cols);
		
		delButton = new ButtonDelete(new JCheckBox(), table, defaultTableModel, null,this);
		delButtonRenderer = new ButtonRenderer("Xóa", '3');
		
		table.setModel(defaultTableModel);
		
		table.getTableHeader().setPreferredSize(new Dimension(100, 30));
		table.getTableHeader().setFont(new Font("Times New Romans", 1, 20));
		for (int c = 0; c < table.getColumnCount() - 1; c++)
		{	
		    table.setDefaultEditor(table.getColumnClass(c), null);
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(200);
		table.getColumnModel().getColumn(6).setPreferredWidth(200);
		table.getColumnModel().getColumn(7).setPreferredWidth(80);
		table.getColumnModel().getColumn(7).setMaxWidth(80);
		table.setRowHeight(table.getRowHeight() + 20);	
		table.getColumn("").setCellRenderer(delButtonRenderer);
		table.getColumn("").setCellEditor(delButton);
		
		table.setFont(new Font("Times New Romans", Font.PLAIN, 18));
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		panel_8.add(scrollPane);
    }

    private void initEastPane() {
        JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setPreferredSize(new Dimension(250, getHeight()));
		panel_1.setMaximumSize(new Dimension(250, getHeight()));
		add(panel_1, BorderLayout.EAST);
		
		JLabel lblNewLabel_2 = new JLabel("Tên sản phẩm:");
		lblNewLabel_2.setBounds(10, 62, 112, 33);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		JLabel lblNewLabel_3 = new JLabel("Số lượng:");
		lblNewLabel_3.setBounds(12, 164, 69, 33);
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		quantityTf = new JTextField();
		quantityTf.setBounds(85, 165, 51, 32);
		quantityTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		quantityTf.setColumns(10);
		
		productNameLb = new JLabel("");
		productNameLb.setBounds(12, 106, 225, 34);
		productNameLb.setFont(new Font("Times New Roman", Font.ITALIC, 20));
		productNameLb.setBackground(new Color(204, 255, 204));
		productNameLb.setOpaque(true);
		
		addToTableBtn = new JButton("Thêm sản phẩm");
		addToTableBtn.setBounds(28, 215, 195, 37);
		addToTableBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				addToTableBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				addToTableBtn.setBackground(new Color(238, 123, 76));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				addToTableBtn.setBackground(new Color(102,204,51));
			}
		});
		addToTableBtn.setForeground(Color.BLACK);
		addToTableBtn.setBackground(new Color(102, 204, 51));
		addToTableBtn.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		addToTableBtn.setIcon(CreateImageIcon.createImageIcon("/res/add_icon.png"));
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(0, 263, 252, 14);
		
		JLabel lblNewLabel_5 = new JLabel("THÀNH TIỀN");
		lblNewLabel_5.setBounds(10, 288, 226, 37);
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		
		totalPriceLb = new JLabel("0");
		totalPriceLb.setBounds(10, 343, 226, 53);
		totalPriceLb.setBackground(new Color(255, 153, 102));
		totalPriceLb.setHorizontalAlignment(SwingConstants.CENTER);
		totalPriceLb.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		totalPriceLb.setOpaque(true);
		totalPriceLb.setForeground(Color.WHITE);
		
		JLabel lblNewLabel_7 = new JLabel("(VNĐ)");
		lblNewLabel_7.setBounds(10, 414, 226, 34);
		lblNewLabel_7.setBackground(new Color(255, 255, 255));
		lblNewLabel_7.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		
		exportBillBtn = new JButton("In hóa đơn");
		exportBillBtn.setBounds(53, 466, 151, 37);
		exportBillBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exportBillBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				exportBillBtn.setBackground(new Color(238, 123, 76));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				exportBillBtn.setBackground(new Color(255, 255, 51));
			}
		});
		exportBillBtn.setBackground(new Color(255, 255, 51));
		exportBillBtn.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		exportBillBtn.setIcon(CreateImageIcon.createImageIcon("/res/bill_icon.png"));
		
		newPageBtn = new JButton("Trang mới");
		newPageBtn.setBounds(53, 521, 151, 37);
		newPageBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				newPageBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				newPageBtn.setBackground(new Color(238, 123, 76));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				newPageBtn.setBackground(new Color(102, 255, 255));
			}
		});
		newPageBtn.setBackground(new Color(102, 255, 255));
		newPageBtn.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		newPageBtn.setIcon(CreateImageIcon.createImageIcon("/res/refresh_icon.png"));
		
		JLabel lblNewLabel_1 = new JLabel("Mã sản phẩm:");
		lblNewLabel_1.setBounds(12, 11, 110, 31);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		productIDTf = new JTextField();
		productIDTf.setBounds(132, 11, 105, 31);
		productIDTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		productIDTf.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("ĐV:");
		lblNewLabel_8.setBounds(146, 164, 29, 33);
		lblNewLabel_8.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		
		unitLb = new JLabel();
		unitLb.setBounds(181, 166, 56, 31);
		unitLb.setOpaque(true);
		unitLb.setBackground(new Color(255, 255, 102));
		unitLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		panel_1.setLayout(null);
		panel_1.add(progressBar);
		panel_1.add(lblNewLabel_2);
		panel_1.add(addToTableBtn);
		panel_1.add(lblNewLabel_7);
		panel_1.add(totalPriceLb);
		panel_1.add(lblNewLabel_5);
		panel_1.add(newPageBtn);
		panel_1.add(exportBillBtn);
		panel_1.add(productNameLb);
		panel_1.add(lblNewLabel_1);
		panel_1.add(productIDTf);
		panel_1.add(lblNewLabel_3);
		panel_1.add(quantityTf);
		panel_1.add(lblNewLabel_8);
		panel_1.add(unitLb);
    }

    private void catchExportBillEvent() {
		exportBillBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new Bill(true, table, 1,new float[] {30f, 80f, 40f, 50f, 75f, 90f} , totalPrice, 0,"", "", "") ;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void catchRefreshEvent() {
		newPageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int j = table.getModel().getRowCount();
				for (int i = j-1; i >= 0; i--) {
					defaultTableModel.removeRow(i);
				}
				setTotalPrice(0);
			}
		});
	}
	
	private void catchSearchNameEvent() {
		productIDTf.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					id = productIDTf.getText();
					try {
						resultSet = Execute.executeQuery(Query.getSelectAllWithCondition("product", "id", id));
						if (resultSet.next()) {
							name = resultSet.getString("product_name");
							export_price = Integer.toString(resultSet.getInt("export_price"));
							unit = resultSet.getString("unit");
							productNameLb.setText(name);
							unitLb.setText("(" + unit +")");
							isPressed = true;
						}else {
							JOptionPane.showMessageDialog(null, "Mã sản phẩm không tồn tại.", null, JOptionPane.WARNING_MESSAGE);
						}
						Execute.closeConnect();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	private void catchAddEvent() {
		addToTableBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (id != "" && isPressed) {
					insertIntoList();
				}else if (id != "" && isPressed == false) {
					JOptionPane.showMessageDialog(null, "Hãy xác nhận tên sản phẩm.", null, JOptionPane.WARNING_MESSAGE);
				}else if (id == "" && productIDTf.getText() != "") {
					JOptionPane.showMessageDialog(null, "Hãy xác nhận tên sản phẩm.", null, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	private void insertIntoList() {
		if (hadProduct(id)) {
            update();
        }else {
			insertIntoTable();
        }
		refreshTFAndLB();
	}
	
	private void update(){
		
		Number temp = Double.parseDouble(defaultTableModel.getValueAt(n, 3).toString());
	    String unit = defaultTableModel.getValueAt(n, 4).toString();
	    deltaQuantity = Controller.convertQuantity(Double.parseDouble(quantityTf.getText()), unit);
	    int deltaPriceOfProduct = Controller.calculatePrice(Controller.removeDot(defaultTableModel.getValueAt(n, 5).toString()), deltaQuantity);
	    setTotalPrice(getTotalPrice() + deltaPriceOfProduct);
	    temp = Controller.addQuantity(temp.doubleValue(), deltaQuantity);
	    defaultTableModel.setValueAt(Controller.priceWithDecimal(Controller.removeDot(defaultTableModel.getValueAt(n, 6).toString()) + deltaPriceOfProduct), n, 6);
	
	    temp = Controller.convertQuantity(temp.doubleValue(), unit);
	
	    defaultTableModel.setValueAt(temp, n, 3);
    }
	
	private void insertIntoTable() {
		if (isPressed) {
			Vector<String> vector = new Vector<>();
			vector.add(Integer.toString(defaultTableModel.getRowCount() + 1));
			vector.add(id);
			vector.add(name);
			vector.add(quantityTf.getText());
			vector.add(unit);
			vector.add(Controller.priceWithDecimal(Integer.parseInt(export_price)));
			deltaQuantity = Controller.convertQuantity(Double.parseDouble(quantityTf.getText()), unit);
			int temp = Controller.calculatePrice(Integer.parseInt(export_price), deltaQuantity);
			vector.add(Controller.priceWithDecimal(temp));
			setTotalPrice(getTotalPrice() + temp);
			defaultTableModel.addRow(vector);
		}
	}
	
	private void refreshTFAndLB() {
		if (isPressed) {
			productIDTf.setText("");
			productNameLb.setText("");
			quantityTf.setText("");
			unitLb.setText("");
			isPressed = false;
			id = "";
		}
	}

	private boolean hadProduct(String productID) {
        for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
            if(defaultTableModel.getValueAt(i, 1).toString().equals(productID)){
                n = i; 
                return true;
            }
        }
        return false;
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
}
