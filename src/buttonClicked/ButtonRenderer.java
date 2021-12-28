package buttonClicked;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JButton implements TableCellRenderer  {

	private String string;
	private char type;

	public ButtonRenderer(String s, char type) {
		setOpaque(true);
		string = s;
		this.type = type;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setText((value == null) ? string : value.toString());
		setFont(new Font("Times New Romans", 1, 20));
		setFocusPainted(false);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				setBackground(new Color(238, 123, 76));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (type == '1') {
					setStyle(new Color(142, 68, 173), new Color(255,255,255));
				}else if (type == '2') {
					setStyle(new Color(244, 209, 13), new Color(0,0,0));
				}else if (type == '3') {
					setStyle(new Color(231, 33, 10), new Color(255,255,255));
				}
			}
		});
		if (type == '1') {
			setStyle(new Color(142, 68, 173), new Color(255,255,255));
		}else if (type == '2') {
			setStyle(new Color(244, 209, 13), new Color(0,0,0));
		}else if (type == '3') {
			setStyle(new Color(231, 33, 10), new Color(255,255,255));
		}
		return this;
	}

	private void setStyle(Color backgroundColor, Color foregroundColor) {
		setBackground(backgroundColor);
		setForeground(foregroundColor);
	}
}
