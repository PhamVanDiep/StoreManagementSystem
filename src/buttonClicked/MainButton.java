package buttonClicked;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Font;

public class MainButton extends JButton {

    public MainButton(String title, ImageIcon icon) {
        setIcon(icon);
        setText(title);
		setBackground(new Color(0, 153, 255));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				setBackground(new Color(238, 123, 76));
			}
		});
		setForeground(Color.WHITE);
		setFont(new Font("Times New Roman", Font.PLAIN, 20));
		setBorder(new LineBorder(new Color(255,255,255)));
		setFocusPainted(false);
    }
}
