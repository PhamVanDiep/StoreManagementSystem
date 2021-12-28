package buttonClicked;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import construction.ConstructionPane;
import debt.DebtPane;
import detail_frame.ConstructionDetailController;
import detail_frame.DebtDetailController;
import detail_frame.DetailConstructionPane;
import detail_frame.DetailDebtPane;
import detail_frame.DetailFrame;
import detail_frame.DetailStaffPane;
import staff.StaffPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ButtonEditorToChangeFrame extends DefaultCellEditor {

	private String label;
	JButton button ;
	private String string;
	
	private int n;
	private TableModel tableModel;
	private DefaultTableModel defaultTableModelOfStaff;

	private Object object;

	public ButtonEditorToChangeFrame(JCheckBox checkBox, String s, JTable table, Object object){
		super(checkBox);
		button = new JButton();
		string = s;
		this.object = object;

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				n = table.getSelectedRow();
				tableModel = table.getModel();

				if (object instanceof StaffPane) {
					defaultTableModelOfStaff = ((StaffPane)object).getDefaultTableModel();
					catchDetailStaffInfo();
					table.selectAll();
					table.clearSelection();
				}else if(object instanceof DebtPane){
					defaultTableModelOfStaff = ((DebtPane)object).getDefaultTableModel();
					catchDetailDebtInfo();
					table.selectAll();
					table.clearSelection();
				}else if (object instanceof ConstructionPane) {
					defaultTableModelOfStaff = ((ConstructionPane)object).getDefaultTableModel();
					catchDetailConstructionInfo();
					table.selectAll();
					table.clearSelection();
				}
			}
			
		});
	
	}

	private void catchDetailConstructionInfo() {
		DetailConstructionPane detailConstructionPane = new DetailConstructionPane(tableModel.getValueAt(n, 0).toString(), 
										tableModel.getValueAt(n, 1).toString(), tableModel.getValueAt(n, 2).toString(), 
										tableModel.getValueAt(n, 3).toString(),
										((ConstructionPane)object).getDefaultTableModel(), n, false);
		new ConstructionDetailController(detailConstructionPane);
		new DetailFrame(detailConstructionPane, '4');
	}

	private void catchDetailDebtInfo() {
		((DebtPane)object).setSelectedRowNumber(n);
		n = ((DebtPane)object).getSelectedRowNumber();
		DetailDebtPane detailDebtPane = new DetailDebtPane(tableModel.getValueAt(n, 0).toString(), tableModel.getValueAt(n, 1).toString(),
										tableModel.getValueAt(n, 2).toString(), tableModel.getValueAt(n, 3).toString(),
										tableModel.getValueAt(n, 4).toString(), tableModel.getValueAt(n, 5).toString(),
										((DebtPane)object).getDefaultTableModel(), n, false);
		new DebtDetailController(detailDebtPane);
		new DetailFrame(detailDebtPane, '2');
	}

	private void catchDetailStaffInfo() {
			new DetailFrame(new DetailStaffPane((String)tableModel.getValueAt(n, 0), (String)tableModel.getValueAt(n, 1),
			(String)tableModel.getValueAt(n, 2), (String)tableModel.getValueAt(n, 3), (String)tableModel.getValueAt(n, 4),
			defaultTableModelOfStaff, n), '5');
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		label = (value == null) ? string : value.toString();
		button.setFont(new Font("Times New Romans", 1, 20));
		button.setBackground(new Color(142, 68, 173));
		button.setForeground(Color.WHITE);
		button.setText(label);
		button.setFocusPainted(false);
		return button;
	}

	public Object getCellEditorValue() {
		return new String(label);
	}
  
	public JButton getButton() {
		return button;
	}
}
