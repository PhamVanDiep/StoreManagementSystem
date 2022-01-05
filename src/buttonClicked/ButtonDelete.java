package buttonClicked;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import construction.ConstructionPane;
import detail_frame.*;
import main.Controller;
import daily_payment.DailyPaymentPane;
import database.Execute;
import database.Query;
import debt.DebtPane;
import product.ProductPane;
import staff.StaffPane;
import supplier.SupplierPane;
import warranty.WarrantyPane;

public class ButtonDelete extends DefaultCellEditor{

	JButton button ;

	private int n;
	private JTable table;
	private TableModel tableModel;
	private DefaultTableModel defaultTableModel;
	private ArrayList<?> infos;
	
	private JPanel object;
	

	public ButtonDelete(JCheckBox checkBox, JTable table, DefaultTableModel defaultTableModel, ArrayList<?> infos, JPanel object){
		super(checkBox);
		button = new JButton();
		this.object = object;

		
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int selectOption = JOptionPane.showConfirmDialog(null, "Bạn có thực sự muốn xóa?", null, JOptionPane.YES_NO_OPTION);
				if (selectOption == JOptionPane.YES_OPTION) {
					n = table.getSelectedRow();
					tableModel = table.getModel();
					
					ButtonDelete.this.table = table;
					ButtonDelete.this.defaultTableModel = defaultTableModel;
					ButtonDelete.this.infos = infos;
					
					if (object instanceof StaffPane) {
						catchDelOfStaffTable();
					}else if (object instanceof SupplierPane) {
						catchDelOfSupplierTable();
					}else if (object instanceof DetailStaffPane) {
						catchDelOfStaffHourTable();
					}else if (object instanceof ProductPane) {
						catchDelOfImportProductTable();
					}else if (object instanceof DailyPaymentPane) {
						catchDelOfDailyPaymentTable();
					}else if (object instanceof WarrantyPane) {
						catchDelOfWarrantyTable();
					}else if (object instanceof DebtPane) {
						catchDelOfDebtTable();
					}else if (object instanceof DetailDebtPane) {
						catchDelOfDetailDebtTable();
					}else if (object instanceof DetailConstructionPane) {
						catchDelOfDetailConstructionTable();
					}else if (object instanceof ConstructionPane) {
						catchDelOfConstructionTable();
					}
				}
			}
		});
	}
	
	protected void catchDelOfConstructionTable() {
		try {
			Execute.delQuery(Query.getDelQuery("construction_bill", "construction_id", tableModel.getValueAt(n, 0).toString()));
			Execute.closeConnect();
			Execute.delQuery(Query.getDelQuery("construction", "id", tableModel.getValueAt(n, 0).toString()));
			Execute.closeConnect();
			defaultTableModel.removeRow(n);
			refreshTable();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void catchDelOfDetailConstructionTable() {
		((DetailConstructionPane)object).setSelectedRowNumber(n);
		n = ((DetailConstructionPane)object).getSelectedRowNumber();
		try {
			Execute.delQuery(Query.getDelQuery("construction_bill", "id", 
					((DetailConstructionPane)object).getConstructionBillIDs().get(n).toString()));
			
			double quantity = Double.parseDouble(((DetailConstructionPane)object).getDefaultTableModel().getValueAt(n, 3).toString());
			int price = Controller.removeDot(((DetailConstructionPane)object).getDefaultTableModel().getValueAt(n, 5).toString());

			DetailConstructionPane.setTotalPrice(DetailConstructionPane.getTotalPrice() - (int)(quantity * price));
			((DetailConstructionPane)object).getDefaultConstructionTableModel().setValueAt(
				Controller.priceWithDecimal(DetailConstructionPane.getTotalPrice()), ((DetailConstructionPane)object).getN(), 4);

			infos.remove(n);
			defaultTableModel.removeRow(n);
			for (int i = n; i < infos.size(); i++) {
				defaultTableModel.setValueAt(i+1, i, 0);
			}
			refreshTable();
			Execute.closeConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void catchDelOfDetailDebtTable() {
		try {
			Execute.delQuery(Query.getDelQuery("debt_bill", "id", ((DetailDebtPane)object).getDebtBillIDs().get(n).toString()));
			recalculatePrice();
			infos.remove(n);
			defaultTableModel.removeRow(n);
			for (int i = n; i < infos.size(); i++) {
				defaultTableModel.setValueAt(i+1, i, 0);
			}
			refreshTable();
			Execute.closeConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void recalculatePrice() {
		double quantity_tmp = Double.parseDouble(((DetailDebtPane)object).getDefaultTableModel().getValueAt(n, 3).toString());
		int price_tmp = Controller.removeDot(((DetailDebtPane)object).getDefaultTableModel().getValueAt(n, 5).toString());
		int price_of_product_temp = (int)(quantity_tmp*price_tmp);
									
		((DetailDebtPane)object).setTotalPrice(((DetailDebtPane)object).getTotalPrice() - price_of_product_temp);

		int temp2 = ((DetailDebtPane)object).getTotalPrice() - ((DetailDebtPane)object).getPaid();
		
		((DetailDebtPane)object).setDebtLb(Integer.toString(temp2));

		((DetailDebtPane)object).getDefaultDebtTableModel().setValueAt(Controller.priceWithDecimal(((DetailDebtPane)object).getTotalPrice()), ((DetailDebtPane)object).getN(), 4);
		((DetailDebtPane)object).getDefaultDebtTableModel().setValueAt(((DetailDebtPane)object).getDebtLb().getText(), ((DetailDebtPane)object)	.getN(), 6);
	}

	private void catchDelOfDebtTable() {
		
		((DebtPane)object).setSelectedRowNumber(n);
		n = ((DebtPane)object).getSelectedRowNumber();
		
		try {
			Execute.delQuery(Query.getDelQuery("debt_bill", "debt_id", tableModel.getValueAt(n, 0).toString()));
			Execute.closeConnect();
			Execute.delQuery(Query.getDelQuery("debt_customer", "id", tableModel.getValueAt(n, 0).toString()));
			Execute.closeConnect();
			defaultTableModel.removeRow(n);
			refreshTable();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void catchDelOfWarrantyTable() {
		try {
			Execute.delQuery(Query.getDelQuery("warranty", "id", ((WarrantyPane)object).getWarrantyIDs().get(n).toString()));
			infos.remove(n);
			defaultTableModel.removeRow(n);
			refreshTable();
			Execute.closeConnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void catchDelOfDailyPaymentTable() {
		Number quantity = Controller.convertQuantity(Double.parseDouble(table.getValueAt(n, 3).toString()), table.getValueAt(n, 4).toString());
		int priceOfProduct = Controller.calculatePrice(Controller.removeDot(table.getValueAt(n, 5).toString()), quantity);
		((DailyPaymentPane)object).setTotalPrice(((DailyPaymentPane)object).getTotalPrice() - priceOfProduct);
		defaultTableModel.removeRow(n);
		for (int i = n; i < defaultTableModel.getRowCount(); i++) {
			defaultTableModel.setValueAt(i+1, i, 0);
		}
		refreshTable();
	}
	
	private void catchDelOfImportProductTable() {
		((ProductPane)object).setSelectedRowNumber(n);
		n = ((ProductPane)object).getSelectedRowNumber();
		try {
			Execute.delQuery(Query.getDelQuery("product", "id", tableModel.getValueAt(n, 0).toString()));
			Execute.closeConnect();
			defaultTableModel.removeRow(n);
			infos.remove(n);
			refreshTable();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Bạn không thể xóa do sản phẩm hiện đang sử dụng tại bảo hành/dư nợ/dự án!");
			e.printStackTrace();
		}
	}

	private void catchDelOfStaffHourTable() {
		((DetailStaffPane)object).setSelectedRowNumber(n);
		n = ((DetailStaffPane)object).getSelectedRowNumber();
		try {
			 Execute.delQuery("delete from staff_hour where cccd = " + "'" + ((DetailStaffPane)object).getCccd() + "'"
					 + " and date = " + "'" + (String)tableModel.getValueAt(n,1) + "'");
			 Execute.closeConnect();
			((DetailStaffPane)object).setHour(((DetailStaffPane)object).getHour() - Integer.parseInt(tableModel.getValueAt(n,2).toString()));
			((DetailStaffPane)object).refresh(((DetailStaffPane)object).getHour());
			defaultTableModel.removeRow(n);
			for (int i = n; i < defaultTableModel.getRowCount(); i++) {
				defaultTableModel.setValueAt(i+1, i, 0);
			}
			refreshTable();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void catchDelOfStaffTable() {
		try {
			Execute.delQuery(Query.getDelQuery("staff_hour", "cccd", tableModel.getValueAt(n, 0).toString()));
			Execute.closeConnect();
			Execute.delQuery(Query.getDelQuery("staff", "cccd", tableModel.getValueAt(n, 0).toString()));
			Execute.closeConnect();
			defaultTableModel.removeRow(n);
			refreshTable();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void catchDelOfSupplierTable(){
		try {
			Execute.delQuery(Query.getDelQuery("supplier", "id", tableModel.getValueAt(n, 0).toString() ));
			Execute.closeConnect();
			defaultTableModel.removeRow(n);
			refreshTable();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Bạn không thể xóa do danh mục sản phẩm có sản phẩm của nhà cung cấp này!");
			e1.printStackTrace();
		}
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		button.setFont(new Font("Times New Romans", 1, 20));
		button.setText("Xóa");
		button.setBackground(new Color(231, 33, 10));
		button.setFocusPainted(false);
		return button;
	}
	
	private void refreshTable() {
		table.selectAll();
		table.clearSelection();
		JOptionPane.showMessageDialog(null, "Xóa thành công");
	}
	
	public Object getCellEditorValue() {
		return new String("xóa");
	}
  
	public JButton getButton() {
		return button;
	}
}
