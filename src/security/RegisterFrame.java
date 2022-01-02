package security;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.SwingConstants;

import database.Execute;
import database.Query;
import image_icon.CreateImageIcon;
import main.Controller;
import main.View;

import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class RegisterFrame extends JFrame {

	private JPanel contentPane;
	private JTextField nameTf;
	private JTextField addressTf;
	private JTextField phone_numberTf;
	private JTextField emailTf;
	private JTextField typeTf;
	private JPasswordField passwordTf;
	private JPasswordField confirmPasswordTf;
	private JLabel errorLb;
	private JButton registerBtn;
	private JButton showBtn1;
	private JButton showBtn0;
	private boolean isHide0;
	private boolean isHide1;
	private char passwordChar;
	
	public RegisterFrame() {
		
		setIconImage(CreateImageIcon.createImageIcon("/res/cubes.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((View.MAX_WIDTH - 450)/2, (View.MAX_HEIGHT - 500)/2, 450, 500);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setTitle("Đăng ký");
		setResizable(false);
		setVisible(true);
		
		isHide0 = true;
		isHide1 = true;
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setPreferredSize(new Dimension(50, getHeight()));
		contentPane.add(panel, BorderLayout.WEST);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setPreferredSize(new Dimension(50, getHeight()));
		contentPane.add(panel_1, BorderLayout.EAST);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		contentPane.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Đăng ký tài khoản");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setBounds(10, 11, 314, 40);
		panel_2.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tên CH:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(10, 62, 80, 30);
		panel_2.add(lblNewLabel_1);
		
		nameTf = new JTextField();
		nameTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		nameTf.setBounds(100, 62, 224, 30);
		panel_2.add(nameTf);
		nameTf.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Địa chỉ:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(10, 103, 80, 30);
		panel_2.add(lblNewLabel_2);
		
		addressTf = new JTextField();
		addressTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		addressTf.setBounds(100, 103, 224, 30);
		panel_2.add(addressTf);
		addressTf.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("SĐT:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(10, 144, 80, 30);
		panel_2.add(lblNewLabel_3);
		
		phone_numberTf = new JTextField();
		phone_numberTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		phone_numberTf.setBounds(100, 144, 224, 30);
		panel_2.add(phone_numberTf);
		phone_numberTf.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Email:");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_4.setBounds(10, 185, 80, 30);
		panel_2.add(lblNewLabel_4);
		
		emailTf = new JTextField();
		emailTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		emailTf.setBounds(100, 185, 224, 30);
		panel_2.add(emailTf);
		emailTf.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Mặt hàng:");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_5.setBounds(10, 226, 80, 30);
		panel_2.add(lblNewLabel_5);
		
		typeTf = new JTextField();
		typeTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		typeTf.setBounds(100, 226, 224, 30);
		panel_2.add(typeTf);
		typeTf.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Mật khẩu:");
		lblNewLabel_6.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_6.setBounds(10, 267, 125, 30);
		panel_2.add(lblNewLabel_6);
		
		passwordTf = new JPasswordField();
		passwordTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		passwordTf.setBounds(160, 267, 125, 30);
		passwordChar = passwordTf.getEchoChar();
		panel_2.add(passwordTf);
		
		JLabel lblNewLabel_7 = new JLabel("Nhập lại mật khẩu:");
		lblNewLabel_7.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_7.setBounds(10, 308, 140, 30);
		panel_2.add(lblNewLabel_7);
		
		confirmPasswordTf = new JPasswordField();
		confirmPasswordTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		confirmPasswordTf.setBounds(160, 308, 125, 30);
		panel_2.add(confirmPasswordTf);
		
		errorLb = new JLabel();
		errorLb.setForeground(new Color(255, 0, 0));
		errorLb.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		errorLb.setBounds(10, 349, 314, 20);
		panel_2.add(errorLb);
		
		registerBtn = new JButton("Đăng ký");
		registerBtn.setBackground(new Color(0, 204, 0));
		registerBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		registerBtn.setBounds(125, 380, 100, 35);
		panel_2.add(registerBtn);
		
		showBtn0 = new JButton("");
		showBtn0.setIcon(CreateImageIcon.createImageIcon("/res/hide.png"));
		showBtn0.setBounds(295, 270, 25, 25);
		panel_2.add(showBtn0);
		
		showBtn1 = new JButton("");
		showBtn1.setIcon(CreateImageIcon.createImageIcon("/res/hide.png"));
		showBtn1.setBounds(295, 310, 25, 25);
		panel_2.add(showBtn1);
		
		showEvent();
		registerEvent();
		catchTFOnMouseClick();
	}
	
	private void registerEvent() {
		registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newP = String.valueOf(passwordTf.getPassword());
				String confNewP = String.valueOf(confirmPasswordTf.getPassword());
				if (newP.equals(confNewP)) {
					try {
						Execute.addQuery(Query.getInsertQuery("admin(email, password, store_name, phone_number, address, type)",
								"'" + emailTf.getText() + "',"+ "'" + newP + "'," + "'" + nameTf.getText() + "'," 
								+ "'" + phone_numberTf.getText() + "'," + "'" + addressTf.getText() + "'," 
								+ "'" + typeTf.getText() + "'"));
						Execute.closeConnect();
						Controller.store_name = nameTf.getText();
						Controller.phone_number = phone_numberTf.getText();
						Controller.address = addressTf.getText();
						Controller.type = typeTf.getText();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					new LoginFrame(emailTf.getText(), newP);
					setVisible(false);
				}else {
					errorLb.setText("Hai mật khẩu không giống nhau!");
				}
			}
		});
	}

	private void showEvent() {
		showBtn0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isHide0) {
					showBtn0.setIcon(CreateImageIcon.createImageIcon("/res/show.png"));
					passwordTf.setEchoChar((char)0);
					isHide0 = false;
				}else {
					showBtn0.setIcon(CreateImageIcon.createImageIcon("/res/hide.png"));
					passwordTf.setEchoChar(passwordChar);
					isHide0 = true;
				}
			}
		});
		
		showBtn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isHide1) {
					showBtn1.setIcon(CreateImageIcon.createImageIcon("/res/show.png"));
					confirmPasswordTf.setEchoChar((char)0);
					isHide1 = false;
				}else {
					showBtn1.setIcon(CreateImageIcon.createImageIcon("/res/hide.png"));
					confirmPasswordTf.setEchoChar(passwordChar);
					isHide1 = true;
				}
			}
		});
	}
	
	private void catchTFOnMouseClick() {
		passwordTf.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
                errorLb.setText("");;
            }
		});
		
		confirmPasswordTf.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
                errorLb.setText("");;
            }
		});
	}
	
}
