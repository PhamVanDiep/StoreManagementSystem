package security;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.View;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import image_icon.CreateImageIcon;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JPasswordField;
import java.util.concurrent.ThreadLocalRandom;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JLabel emailTf;
	private JPasswordField passwordTf;
	private String password;
	private JLabel errorLb;
	private JButton showBtn;
	private JLabel forgotPasswordLb;
	private JButton loginBtn;
	private boolean isHide;
	private char passwordChar;
	
	public LoginFrame(String email, String password) {

		setIconImage(CreateImageIcon.createImageIcon("/res/cubes.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((View.MAX_WIDTH - 450)/2, (View.MAX_HEIGHT - 320)/2 , 450, 320);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Đăng nhập");
		setVisible(true);
		
		this.password = password;
		isHide = true;
		
		JLabel lblNewLabel = new JLabel("Đăng nhập hệ thống");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 410, 40);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Email:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(60, 80, 80, 35);
		contentPane.add(lblNewLabel_1);
		
		emailTf = new JLabel(email);
		emailTf.setBorder(BorderFactory.createLineBorder(Color.black));
		emailTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		emailTf.setBounds(150, 80, 220, 35);
		contentPane.add(emailTf);
		
		JLabel lblNewLabel_2 = new JLabel("Mật khẩu:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(60, 126, 80, 35);
		contentPane.add(lblNewLabel_2);
		
		showBtn = new JButton("");
		showBtn.setIcon(CreateImageIcon.createImageIcon("/res/hide.png"));
		showBtn.setBounds(380, 130, 25, 25);
		contentPane.add(showBtn);
		
		errorLb = new JLabel();
		errorLb.setForeground(Color.RED);
		errorLb.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		errorLb.setBounds(60, 172, 180, 25);
		contentPane.add(errorLb);
		
		forgotPasswordLb = new JLabel("<html><u>Quên mật khẩu?</u></html>");
		forgotPasswordLb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				forgotPasswordLb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				forgotPassword();
			}
		});
		forgotPasswordLb.setForeground(Color.BLUE);
		forgotPasswordLb.setHorizontalAlignment(SwingConstants.CENTER);
		forgotPasswordLb.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		forgotPasswordLb.setBounds(280, 172, 120, 25);
		contentPane.add(forgotPasswordLb);
		
		loginBtn = new JButton("Login");
		loginBtn.setBackground(new Color(0, 204, 51));
		loginBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		loginBtn.setBounds(175, 208, 100, 35);
		contentPane.add(loginBtn);
		
		passwordTf = new JPasswordField();
		passwordTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		passwordTf.setBounds(150, 126, 220, 35);
		passwordChar = passwordTf.getEchoChar();
		contentPane.add(passwordTf);
		
		catchTFOnMouseClick();
		showAndHidePassword();
		loginEvent();
		
	}

	private void loginEvent() {
		passwordTf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String passwordString = String.valueOf(passwordTf.getPassword());
				if (passwordString.equals(password)) {
					setVisible(false);
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								new View(password);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}else {
					errorLb.setText("Mật khẩu không đúng. Thử lại!");
				}
			}
		});
		
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String passwordString = String.valueOf(passwordTf.getPassword());
				if (passwordString.equals(password)) {
					setVisible(false);
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								new View(password);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}else {
					errorLb.setText("Mật khẩu không đúng. Thử lại!");
				}
			}
		});
	}

	private void forgotPassword() {
		char octet1 = (char)ThreadLocalRandom.current().nextInt(65, 90 + 1);
		char octet2 = (char)ThreadLocalRandom.current().nextInt(65, 90 + 1);
		char octet3 = (char)ThreadLocalRandom.current().nextInt(97, 122 + 1);
		char octet4 = (char)ThreadLocalRandom.current().nextInt(97, 122 + 1);
		int octet5 = ThreadLocalRandom.current().nextInt(10, 99 + 1);
		
		String code = "" + octet1 + octet2 + octet3 + octet4 + octet5;
		new SendEmail(emailTf.getText(), code);
		new ConfirmFrame(emailTf.getText(), code);
		setVisible(false);
	}

	private void showAndHidePassword() {
		showBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isHide) {
					showBtn.setIcon(CreateImageIcon.createImageIcon("/res/show.png"));
					passwordTf.setEchoChar((char)0);
					isHide = false;
				}else {
					showBtn.setIcon(CreateImageIcon.createImageIcon("/res/hide.png"));
					passwordTf.setEchoChar(passwordChar);
					isHide = true;
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
	}
}

