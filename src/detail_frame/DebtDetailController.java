package detail_frame;

import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import database.Execute;
import database.Query;
import export_bill.Bill;
import main.Controller;

public class DebtDetailController {
    
    private DetailDebtPane detailDebtPane;
    private boolean isConfirm;
    private boolean isPressed;
    private ResultSet resultSet;
    private String debtId;

    private String productId;
    private String name;
    private double quantity;
    private String unit;
    private String date;
    private int export_price;

    public DebtDetailController(DetailDebtPane detailDebtPane) {
        this.detailDebtPane = detailDebtPane;
        if (detailDebtPane.getIsAdd()) {
            isConfirm = false;
        }else isConfirm = true;
        isPressed = false;
        date = detailDebtPane.getDateTf().getText();
        debtId = detailDebtPane.getCustomerID();

        newDebtConfirm();
        catchAddEvent();
        confirmProduct();
        changePaid();
        exportBill();
    }

    private void exportBill() {
        detailDebtPane.getExportBillBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new Bill(false, detailDebtPane.getTable(), 1,new float[] {30f, 80f, 40f, 50f, 75f, 90f, 70f} , detailDebtPane.getTotalPrice(),
                             detailDebtPane.getPaid(),detailDebtPane.getDefaultDebtTableModel().getValueAt(detailDebtPane.getN(), 1).toString(), 
                             detailDebtPane.getDefaultDebtTableModel().getValueAt(detailDebtPane.getN(), 2).toString(),
                             detailDebtPane.getDefaultDebtTableModel().getValueAt(detailDebtPane.getN(), 3).toString()) ;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
    }

    private void changePaid() {
        detailDebtPane.getPaidTf().addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    if (isConfirm) {

                        int paid = Controller.removeDot(detailDebtPane.getPaidTf().getText());
                        detailDebtPane.setPaid(paid);

                        try {
                            Execute.updateQuery(Query.getUpdateQuery("debt_customer", "paid = " + "'" + paid + "'", "id", debtId) );
                            Execute.closeConnect();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                        detailDebtPane.setDebtLb(Integer.toString(detailDebtPane.getTotalPrice() - paid));

                        detailDebtPane.getDefaultDebtTableModel().setValueAt(Controller.priceWithDecimal(paid), detailDebtPane.getN(), 5);
                        detailDebtPane.getDefaultDebtTableModel().setValueAt(detailDebtPane.getDebtLb().getText(), detailDebtPane.getN(), 6);

                        JOptionPane.showMessageDialog(null, "Cập nhật số tiền đã trả thành công!", null, JOptionPane.WARNING_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "Hãy xác nhận thông tin khách hàng!", null, JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
    }

    private void catchAddEvent() {
        detailDebtPane.getAddBtn().addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isConfirm) {
                    if (productId != "" && isPressed) {
                        insertProductToTable();
                    }else if (productId != "" && isPressed == false) {
                        JOptionPane.showMessageDialog(null, "Hãy xác nhận tên sản phẩm.", null, JOptionPane.WARNING_MESSAGE);
                    }else if (productId == "" && detailDebtPane.getProductIDTf().getText() != "") {
                        JOptionPane.showMessageDialog(null, "Hãy xác nhận tên sản phẩm.", null, JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Hãy xác nhận thông tin khách hàng!", null, JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void insertProductToTable() {
        quantity = Double.parseDouble(detailDebtPane.getQuantityTf().getText());

        try {
            Execute.addQuery(Query.getInsertQuery("debt_bill(debt_id, product_id, quantity, price, date)", "'"+ debtId + "',"
                    + "'"+ productId + "'," + "'"+ quantity + "'," + "'"+ export_price + "'," + "'"+ date + "'"));
            Execute.closeConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int debt_bill_id = 0;
        try {
        	ResultSet resultSet = Execute.executeQuery(Query.getLastRow("debt_bill", "id"));
            if (resultSet.next()) {
                debt_bill_id = resultSet.getInt("id");
            }
            Execute.closeConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        detailDebtPane.getDebtBillIDs().add(debt_bill_id);
        
        Number temp = Controller.convertQuantity(quantity, unit);
        
		Vector<String> vector = new Vector<>();
		vector.add(Integer.toString(detailDebtPane.getDefaultTableModel().getRowCount() + 1));
		vector.add(productId);
		vector.add(name);
		vector.add(temp.toString());
		vector.add(unit);
		vector.add(Controller.priceWithDecimal(export_price));
		int total_price_tmp = Controller.calculatePrice(export_price, temp);
		vector.add(Controller.priceWithDecimal(total_price_tmp));
        vector.add(date);
		
		detailDebtPane.getDefaultTableModel().addRow(vector);

        detailDebtPane.setTotalPrice(detailDebtPane.getTotalPrice() + total_price_tmp);
        detailDebtPane.setDebtLb(Integer.toString(detailDebtPane.getTotalPrice() - detailDebtPane.getPaid()));

        detailDebtPane.getDefaultDebtTableModel().setValueAt(Controller.priceWithDecimal(detailDebtPane.getTotalPrice()), detailDebtPane.getN(), 4);
		detailDebtPane.getDefaultDebtTableModel().setValueAt(detailDebtPane.getDebtLb().getText(), detailDebtPane.getN(), 6);

        detailDebtPane.getProductIDTf().setText("");
		detailDebtPane.getProductNameLb().setText("");
		detailDebtPane.getQuantityTf().setText("");
        detailDebtPane.getUnitLb().setText("");

		isPressed = false;
		productId = "";
    }

    private void confirmProduct() {
        detailDebtPane.getProductIDTf().addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					
					productId = detailDebtPane.getProductIDTf().getText();
                    try {
                        resultSet = Execute.executeQuery(Query.getSelectAllWithCondition("product", "id", productId));
                        if (resultSet.next()) {
                            name = resultSet.getString("product_name");
                            export_price = resultSet.getInt("export_price");
                            unit = resultSet.getString("unit");
                            detailDebtPane.getProductNameLb().setText(name);
                            detailDebtPane.getUnitLb().setText("(" + unit +")");
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

    private void newDebtConfirm() {
        detailDebtPane.getCustomerAddressTf().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    isConfirm = true;
                    String name = detailDebtPane.getCustomerNameTf().getText();
                    String phone_number = detailDebtPane.getCustomerPhoneNumberTf().getText();
                    String address = detailDebtPane.getCustomerAddressTf().getText();
                    try {
                        Execute.addQuery(Query.getInsertQuery("debt_customer(name, phone_number, address, paid)", 
                        		"'" + name + "',"+ "'" + phone_number + "'," + "'" + address + "'," + "0"));
                        Execute.closeConnect();
                        detailDebtPane.getCustomerNameTf().setEditable(false);
                        detailDebtPane.getCustomerPhoneNumberTf().setEditable(false);
                        detailDebtPane.getCustomerAddressTf().setEditable(false);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    
                    try {
                        ResultSet resultSet = Execute.executeQuery(Query.getLastRow("debt_customer", "id"));
                        while (resultSet.next()) {
                            detailDebtPane.setCustomerID(resultSet.getString("id"));
                        }
                        
                        debtId = detailDebtPane.getCustomerID();
                        Vector<String> vector = new Vector<>();
                        vector.add(debtId);
                        vector.add(name);
                        vector.add(phone_number);
                        vector.add(address);
                        vector.add("0");
                        vector.add("0");
                        vector.add("0");
                        detailDebtPane.getDefaultDebtTableModel().addRow(vector);

                        detailDebtPane.setN(detailDebtPane.getDefaultDebtTableModel().getRowCount()-1);
                        Execute.closeConnect();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
}
