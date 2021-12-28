package detail_frame;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import database.Execute;
import database.Query;
import export_bill.Bill;
import main.Controller;

import java.awt.Color;
import java.awt.event.*;
public class ConstructionDetailController {
    
    private DetailConstructionPane detailConstructionPane;

    private boolean isConfirm;
    private boolean isPressed;
    private ResultSet resultSet;
    private String constructionId;

    private String productId;
    private String name;
    private double quantity;
    private String unit;
    private int export_price;
    
    private JTextField searchByProductIDTf;
    private JTextField searchByProductNameTf;

    private TableRowSorter<DefaultTableModel> tableRowSorter;

    private int n;

    public ConstructionDetailController(DetailConstructionPane detailConstructionPane) {

        this.detailConstructionPane = detailConstructionPane;

        if (detailConstructionPane.getIsAdd()) {
            isConfirm = false;
        }else isConfirm = true;
        isPressed = false;
        
        constructionId = detailConstructionPane.getCustomerID();
        this.searchByProductIDTf = detailConstructionPane.getSearchByProductIDTf();
        this.searchByProductNameTf = detailConstructionPane.getSearchByProductNameTf();
        this.tableRowSorter = detailConstructionPane.getTableRowSorter();
        
        newConstructionConfirm();
        catchAddEvent();
        confirmProduct();
        exportBill();

        placeHolderOfSearchByProductID();
		placeHolderOfSearchByProductName();
		catchSearchByProductID();
		catchSearchByProductName();
    }
    
    private void placeHolderOfSearchByProductID() {
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


	private void placeHolderOfSearchByProductName() {
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

	private void catchSearchByProductID() {
		searchByProductIDTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchByProductIDTf.getText().trim(),1));
			}
			
			public void keyReleased(KeyEvent e) {
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchByProductIDTf.getText().trim(),1));
			}
		});
	}


	private void catchSearchByProductName() {
		searchByProductNameTf.addKeyListener(new KeyAdapter() {
            @Override
			public void keyPressed(KeyEvent e) {
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchByProductNameTf.getText().trim(),2));
			}
			
			public void keyReleased(KeyEvent e) {
				tableRowSorter.setRowFilter(RowFilter.regexFilter(searchByProductNameTf.getText().trim(),2));
			}
		});
	}


    private void catchAddEvent() {
        detailConstructionPane.getAddBtn().addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isConfirm) {
                    if (productId != "" && isPressed) {
                        insertProductToTable();
                    }else if (productId != "" && isPressed == false) {
                        JOptionPane.showMessageDialog(null, "Hãy xác nhận tên sản phẩm.", null, JOptionPane.WARNING_MESSAGE);
                    }else if (productId == "" && detailConstructionPane.getProductIDTf().getText() != "") {
                        JOptionPane.showMessageDialog(null, "Hãy xác nhận tên sản phẩm.", null, JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Hãy xác nhận thông tin khách hàng!", null, JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void insertProductToTable() {   
        quantity = Double.parseDouble(detailConstructionPane.getQuantityTf().getText());
        if (hadProduct(productId)) {
            update();
        }else add();
    }

    private boolean hadProduct(String productID) {
        for (int i = 0; i < detailConstructionPane.getDefaultTableModel().getRowCount(); i++) {
            if(detailConstructionPane.getDefaultTableModel().getValueAt(i, 1).toString().equals(productID)){
                n = i; 
                return true;
            }
        }
        return false;
    }

    private void update(){
        int id = detailConstructionPane.getConstructionBillIDs().get(n);
        double temp = Double.parseDouble(detailConstructionPane.getDefaultTableModel().getValueAt(n, 3).toString());
        temp += quantity;
        Number quantity_tmp = Controller.convertQuantity(temp, unit);
        
        try {
            Execute.updateQuery(Query.getUpdateQuery("construction_bill", "quantity = " + "'" + temp + "'", "id", Integer.toString(id)));
            Execute.closeConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        int price_tmp = Controller.removeDot(detailConstructionPane.getDefaultTableModel().getValueAt(n, 5).toString());
        int totalPriceOfProduct = Controller.calculatePrice(price_tmp, quantity_tmp);

        DetailConstructionPane.setTotalPrice(DetailConstructionPane.getTotalPrice() 
        - Controller.removeDot(detailConstructionPane.getDefaultTableModel().getValueAt(n, 6).toString()) + totalPriceOfProduct);

        detailConstructionPane.getDefaultTableModel().setValueAt(Controller.priceWithDecimal(totalPriceOfProduct), n, 6);
        detailConstructionPane.getDefaultTableModel().setValueAt(quantity_tmp, n, 3);
        detailConstructionPane.getDefaultConstructionTableModel().setValueAt(
            Controller.priceWithDecimal(DetailConstructionPane.getTotalPrice()), detailConstructionPane.getN(), 4);
        
        detailConstructionPane.getProductIDTf().setText("");
        detailConstructionPane.getProductNameLb().setText("");
        detailConstructionPane.getQuantityTf().setText("");
        detailConstructionPane.getUnitLb().setText("");

        isPressed = false;
        productId = "";
    }

    private void add(){
        try {
            Execute.addQuery(Query.getInsertQuery("construction_bill(construction_id, product_id, quantity, price)", 
            		"'"+ constructionId + "'," + "'"+ productId + "'," + "'"+ quantity + "'," + "'"+ export_price + "'"));
            Execute.closeConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        try {
			ResultSet resultSet = Execute.executeQuery(Query.getLastRow("construction_bill", "id"));
			if (resultSet.next()) {
				int id = resultSet.getInt("id");
				detailConstructionPane.getConstructionBillIDs().add(id);
			}
			Execute.closeConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        Number quantity_tmp = Controller.convertQuantity(quantity, unit);

        Vector<String> vector = new Vector<>();
        vector.add(Integer.toString(detailConstructionPane.getDefaultTableModel().getRowCount() + 1));
        vector.add(productId);
        vector.add(name);
        vector.add(quantity_tmp.toString());
        vector.add(unit);
        vector.add(Controller.priceWithDecimal(export_price));
        int price_temp = Controller.calculatePrice(export_price, quantity_tmp);
        vector.add(Controller.priceWithDecimal(price_temp));
        
        detailConstructionPane.getDefaultTableModel().addRow(vector);

        DetailConstructionPane.setTotalPrice(DetailConstructionPane.getTotalPrice() + price_temp);

        detailConstructionPane.getDefaultConstructionTableModel().setValueAt(Controller.priceWithDecimal(DetailConstructionPane.getTotalPrice()), detailConstructionPane.getN(), 4);

        detailConstructionPane.getProductIDTf().setText("");
        detailConstructionPane.getProductNameLb().setText("");
        detailConstructionPane.getQuantityTf().setText("");
        detailConstructionPane.getUnitLb().setText("");

        isPressed = false;
        productId = "";
    }

    private void confirmProduct() {
        detailConstructionPane.getProductIDTf().addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					
                    productId = detailConstructionPane.getProductIDTf().getText();
                    try {
                        resultSet = Execute.executeQuery(Query.getSelectAllWithCondition("product", "id", productId));
                        if (resultSet.next()) {
                            name = resultSet.getString("product_name");
                            export_price = resultSet.getInt("export_price");
                            unit = resultSet.getString("unit");
                            detailConstructionPane.getProductNameLb().setText(name);
                            detailConstructionPane.getUnitLb().setText("(" + unit +")");
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

    private void newConstructionConfirm() {
        detailConstructionPane.getCustomerAddressTf().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    isConfirm = true;
                    String name = detailConstructionPane.getCustomerNameTf().getText();
                    String phone_number = detailConstructionPane.getCustomerPhoneNumberTf().getText();
                    String address = detailConstructionPane.getCustomerAddressTf().getText();
                    try {
                        Execute.addQuery(Query.getInsertQuery("construction(name, phone_number, address)",
                        				"'" + name + "',"+ "'" + phone_number + "'," + "'" + address + "'"));
                        Execute.closeConnect();
                        detailConstructionPane.getCustomerNameTf().setEditable(false);
                        detailConstructionPane.getCustomerPhoneNumberTf().setEditable(false);
                        detailConstructionPane.getCustomerAddressTf().setEditable(false);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    
                    try {
                        ResultSet resultSet = Execute.executeQuery(Query.getLastRow("construction", "id"));
                        while (resultSet.next()) {
                            detailConstructionPane.setCustomerID(resultSet.getString("id"));
                        }
                        
                        constructionId = detailConstructionPane.getCustomerID();
                        Vector<String> vector = new Vector<>();
                        vector.add(constructionId);
                        vector.add(name);
                        vector.add(phone_number);
                        vector.add(address);
                        vector.add("0");
                        detailConstructionPane.getDefaultConstructionTableModel().addRow(vector);

                        detailConstructionPane.setN(detailConstructionPane.getDefaultConstructionTableModel().getRowCount()-1);
                        Execute.closeConnect();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
    
    private void exportBill() {
        detailConstructionPane.getExportBillBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new Bill(true, detailConstructionPane.getTable(), 2,new float[] {30f, 100f, 40f, 50f, 75f, 80f} , DetailConstructionPane.getTotalPrice(),
                        0,detailConstructionPane.getDefaultConstructionTableModel().getValueAt(detailConstructionPane.getN(), 1).toString(), 
                        detailConstructionPane.getDefaultConstructionTableModel().getValueAt(detailConstructionPane.getN(), 2).toString(),
                        detailConstructionPane.getDefaultConstructionTableModel().getValueAt(detailConstructionPane.getN(), 3).toString()) ;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
    }
}
