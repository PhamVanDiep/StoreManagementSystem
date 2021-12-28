package security;

import javax.swing.JFrame;
import javax.swing.JPasswordField;

import image_icon.CreateImageIcon;
import main.Controller;
import main.View;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class CheckOwnerPassword extends JFrame {

	private JPasswordField passwordTF;
	private JLabel errorLB;
	private JButton showPassword;
	private boolean isHide;
	private char passwordChar;
	private String password;
	private DefaultTableModel defaultTableModel;
	private int n;
	
	public CheckOwnerPassword(String password, DefaultTableModel defaultTableModel, int n) {
		this.password = password;
		this.defaultTableModel = defaultTableModel;
		this.n = n;
		
		setIconImage(CreateImageIcon.createImageIcon("/res/cubes.png").getImage());
		setBounds(0, 0, 300, 200);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocation((View.MAX_WIDTH -300)/2, (View.MAX_HEIGHT - 200)/2);
		setVisible(true);
        
		JLabel lblNewLabel = new JLabel("Chỉ dành cho chủ cửa hàng!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 264, 28);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mật khẩu:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(10, 66, 87, 28);
		getContentPane().add(lblNewLabel_1);
		
		passwordTF = new JPasswordField();
		passwordTF.setBounds(96, 63, 140, 35);
		passwordTF.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		getContentPane().add(passwordTF);
		passwordTF.setColumns(10);
		passwordChar = passwordTF.getEchoChar();
		
		errorLB = new JLabel();
		errorLB.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		errorLB.setForeground(Color.RED);
		errorLB.setBounds(10, 105, 264, 36);
		getContentPane().add(errorLB);
		
		showPassword = new JButton();
		isHide = true;
		showPassword.setIcon(CreateImageIcon.createImageIcon("/res/hide.png"));
		showPassword.setBounds(249, 68, 25, 25);
		getContentPane().add(showPassword);

		catchTFOnMouseClick();
		catchShowPassword();
		catchTFOnEnterClick();
	}
	
	private void catchShowPassword() {
		showPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isHide) {
					showPassword.setIcon(CreateImageIcon.createImageIcon("/res/show.png"));
					passwordTF.setEchoChar((char)0);
					isHide = false;
				}else {
					showPassword.setIcon(CreateImageIcon.createImageIcon("/res/hide.png"));
					passwordTF.setEchoChar(passwordChar);
					isHide = true;
				}
			}
		});
	}

	private void catchTFOnEnterClick() {
		passwordTF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String passwordString = String.valueOf(passwordTF.getPassword());
				if (passwordString.equals(password)) {
					setVisible(false);
					Controller.showImportPrice(defaultTableModel, defaultTableModel.getValueAt(n, 0).toString(), n);
				}else {
					errorLB.setText("Mật khẩu không đúng. Thử lại!");
				}
			}
			
		});
	}

	private void catchTFOnMouseClick() {
		passwordTF.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
                errorLB.setText("");
            }
		});
	}

	public JFrame getFrame() {
		return this;
	}
}
