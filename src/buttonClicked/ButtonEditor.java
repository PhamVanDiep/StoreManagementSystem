package buttonClicked;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import construction.ConstructionPane;
import construction.ConstructionProductFrame;
import debt.DebtInfoFrame;
import debt.DebtPane;
import detail_frame.DetailConstructionPane;
import main.Controller;
import product.ProductInfoFrame;
import product.ProductPane;
import staff.StaffInfoFrame;
import staff.StaffPane;
import supplier.SupplierInfoFrame;
import supplier.SupplierPane;
import warranty.WarrantyInfoFrame;
import warranty.WarrantyPane;


public class ButtonEditor extends DefaultCellEditor {
	private String label;
	JButton button ;
	private String string;
	
	private int n;
	private TableModel tableModel;
	private JPanel object;
	
	public ButtonEditor(JCheckBox checkBox, String s, JTable table, JPanel object){
		super(checkBox);
		this.object = object;
		
		button = new JButton();
		button.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {

				n =table.getSelectedRow();
				tableModel = table.getModel();

				if (object instanceof SupplierPane) {
					editSupplierInfo();
				}else if (object instanceof StaffPane) {
					editStaffInfo();
				}else if (object instanceof ProductPane) {
					editProductInfo();
				}else if (object instanceof WarrantyPane) {
					editWarrantyInfo();
				}else if (object instanceof DebtPane) {
					editDebtInfo();
				}else if(object instanceof DetailConstructionPane){
					editConstructionProductInfo();
				}else if (object instanceof ConstructionPane) {
					editConstructionInfo();
				}
			}
		});
		string = s;
	}
	
  	private void editConstructionInfo() {
		new DebtInfoFrame(tableModel.getValueAt(n, 0).toString(), tableModel.getValueAt(n, 1).toString(), 
		tableModel.getValueAt(n, 2).toString(), tableModel.getValueAt(n, 3).toString(), tableModel, n, "construction", false);
	}

	private void editConstructionProductInfo(){
		((DetailConstructionPane)object).setSelectedRowNumber(n);
		n = ((DetailConstructionPane)object).getSelectedRowNumber();
		
		new ConstructionProductFrame(Integer.toString(((DetailConstructionPane)object).getConstructionBillIDs().get(n)), 
		tableModel.getValueAt(n, 1).toString(), tableModel.getValueAt(n, 2).toString(), tableModel.getValueAt(n, 3).toString(), 
		tableModel.getValueAt(n, 4).toString(), tableModel.getValueAt(n, 5).toString(), tableModel.getValueAt(n, 6).toString(), 
		((DetailConstructionPane)object).getDefaultTableModel(), ((DetailConstructionPane)object).getDefaultConstructionTableModel(),
		((DetailConstructionPane)object).getN(), n);
	}

	private void editDebtInfo() {
		((DebtPane)object).setSelectedRowNumber(n);
		n = ((DebtPane)object).getSelectedRowNumber();
		new DebtInfoFrame(tableModel.getValueAt(n, 0).toString(), tableModel.getValueAt(n, 1).toString(), 
							tableModel.getValueAt(n, 2).toString(), tableModel.getValueAt(n, 3).toString(), tableModel, n, "debt_customer", true);
	}

	private void editWarrantyInfo() {
		new WarrantyInfoFrame(tableModel.getValueAt(n, 0).toString(), tableModel.getValueAt(n, 1).toString(), 
							tableModel.getValueAt(n, 2).toString(), tableModel.getValueAt(n, 3).toString(), 
							tableModel.getValueAt(n, 4).toString(), tableModel.getValueAt(n, 5).toString(), 
							tableModel.getValueAt(n, 7).toString(), tableModel.getValueAt(n, 6).toString(), 
							((WarrantyPane)object).getDefaultTableModel(), ((WarrantyPane)object).getWarrantyIDs(), n, false);
	}
	private void editProductInfo(){
		((ProductPane)object).setSelectedRowNumber(n);
		n = ((ProductPane)object).getSelectedRowNumber();
		try {
			new ProductInfoFrame(tableModel.getValueAt(n, 0).toString(), 
					tableModel.getValueAt(n, 1).toString(), tableModel.getValueAt(n, 2).toString(),
					tableModel.getValueAt(n, 3).toString(),tableModel.getValueAt(n, 6).toString(),
					Controller.getImportPrice(tableModel.getValueAt(n, 0).toString()), 
					tableModel.getValueAt(n, 5).toString(), ((ProductPane)object).getSupplierIDs().get(n),tableModel,
					((ProductPane)object).getDefaultTableModel(), n, false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void editStaffInfo() {
		new StaffInfoFrame(tableModel.getValueAt(n, 0).toString(), 
				tableModel.getValueAt(n, 1).toString(), tableModel.getValueAt(n, 2).toString(), 
				tableModel.getValueAt(n, 3).toString(), tableModel.getValueAt(n, 4).toString(), tableModel, null, n, false);
	}

	private void editSupplierInfo() {

		new SupplierInfoFrame(tableModel.getValueAt(n, 0).toString(), 
					tableModel.getValueAt(n, 1).toString(), tableModel.getValueAt(n, 2).toString(), 
					tableModel.getValueAt(n, 3).toString(), tableModel.getValueAt(n, 4).toString(), tableModel, 
					((SupplierPane)object).getDefaultTableModel(), n, false);
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		label = (value == null) ? string : value.toString();
		button.setFont(new Font("Times New Romans", 1, 20));
		button.setBackground(new Color(244, 209, 13));
		button.setFocusPainted(false);
		button.setText(label);
		return button;
	}
	public Object getCellEditorValue() {
		return new String(label);
	}
  
	public JButton getButton() {
		return button;
	}
}