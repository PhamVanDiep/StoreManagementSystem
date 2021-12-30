package security;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import database.Execute;
import database.Query;
import image_icon.CreateImageIcon;
import main.View;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ConfirmFrame extends JFrame {

	private JPanel contentPane;
	private JTextField confirmTf;
	private JPasswordField newPasswordTf;
	private JPasswordField confirmNewPasswordTf;
	private JButton showBtn1;
	private JButton showBtn;
	private JButton completeBtn;
	private JLabel errorLb;
	private String code;
	private JButton confirmBtn;
	private boolean isHide0;
	private boolean isHide1;
	private boolean isConfirmed;
	private char passwordChar;
	private String email;
	
	public ConfirmFrame(String email, String code) {
		
		setIconImage(CreateImageIcon.createImageIcon("/res/cubes.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((View.MAX_WIDTH - 450)/2, (View.MAX_HEIGHT - 320)/2, 450, 320);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Đổi mật khẩu");
		setResizable(false);
		setVisible(true);
		
		this.email = email;
		this.code = code;
		isHide0 = true;
		isHide1 = true;
		isConfirmed = false;
		
		JLabel lblNewLabel = new JLabel("<html><p>Mã xác nhận đã được gửi đến "+email+"</p></html>");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		lblNewLabel.setBounds(10, 11, 414, 50);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nhập mã xác nhận:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(10, 72, 150, 30);
		contentPane.add(lblNewLabel_1);
		
		confirmTf = new JTextField();
		confirmTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		confirmTf.setBounds(170, 72, 120, 30);
		contentPane.add(confirmTf);
		confirmTf.setColumns(10);
		
		confirmBtn = new JButton("Xác nhận");
		confirmBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		confirmBtn.setBounds(300, 72, 120, 30);
		contentPane.add(confirmBtn);
		
		JLabel lblNewLabel_2 = new JLabel("Mật khẩu mới:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(10, 113, 150, 30);
		contentPane.add(lblNewLabel_2);
		
		newPasswordTf = new JPasswordField();
		newPasswordTf.setEditable(false);
		newPasswordTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		newPasswordTf.setBounds(170, 113, 140, 30);
		contentPane.add(newPasswordTf);
		passwordChar = newPasswordTf.getEchoChar();
		
		JLabel lblNewLabel_3 = new JLabel("Xác nhận mật khẩu:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(10, 154, 150, 30);
		contentPane.add(lblNewLabel_3);
		
		confirmNewPasswordTf = new JPasswordField();
		confirmNewPasswordTf.setEditable(false);
		confirmNewPasswordTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		confirmNewPasswordTf.setBounds(170, 154, 140, 30);
		contentPane.add(confirmNewPasswordTf);
		
		showBtn1 = new JButton("");
		showBtn1.setIcon(CreateImageIcon.createImageIcon("/res/hide.png"));
		showBtn1.setBounds(320, 157, 25, 25);
		contentPane.add(showBtn1);
		
		showBtn = new JButton("");
		showBtn.setIcon(CreateImageIcon.createImageIcon("/res/hide.png"));
		showBtn.setBounds(320, 116, 25, 25);
		contentPane.add(showBtn);
		
		errorLb = new JLabel("");
		errorLb.setBackground(Color.WHITE);
		errorLb.setForeground(Color.RED);
		errorLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		errorLb.setBounds(10, 195, 300, 25);
		contentPane.add(errorLb);
		
		completeBtn = new JButton("Hoàn thành");
		completeBtn.setBackground(new Color(0, 204, 0));
		completeBtn.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		completeBtn.setBounds(155, 235, 120, 35);
		contentPane.add(completeBtn);
		
		confirmEvent();
		showEvent();
		completeEvent();
		catchTFOnMouseClick();
	}

	private void completeEvent() {
		completeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isConfirmed) {
					String newP = String.valueOf(newPasswordTf.getPassword());
					String confNewP = String.valueOf(confirmNewPasswordTf.getPassword());
					if (newP.equals(confNewP)) {
						try {
							Execute.updateQuery(Query.getUpdateQuery("admin", "password = " + "'" + newP + "'", "email", email));
							Execute.closeConnect();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						new LoginFrame(email, newP);
						setVisible(false);
					}else {
						errorLb.setText("Mật khẩu mới không giống nhau!");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Bạn hãy xác nhận mã!");
				}
			}
		});
	}

	private void showEvent() {
		showBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isHide0) {
					showBtn.setIcon(CreateImageIcon.createImageIcon("/res/show.png"));
					newPasswordTf.setEchoChar((char)0);
					isHide0 = false;
				}else {
					showBtn.setIcon(CreateImageIcon.createImageIcon("/res/hide.png"));
					newPasswordTf.setEchoChar(passwordChar);
					isHide0 = true;
				}
			}
		});
		
		showBtn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isHide1) {
					showBtn1.setIcon(CreateImageIcon.createImageIcon("/res/show.png"));
					confirmNewPasswordTf.setEchoChar((char)0);
					isHide1 = false;
				}else {
					showBtn1.setIcon(CreateImageIcon.createImageIcon("/res/hide.png"));
					confirmNewPasswordTf.setEchoChar(passwordChar);
					isHide1 = true;
				}
			}
		});
	}

	private void confirmEvent() {
		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (confirmTf.getText().equals(code)) {
					newPasswordTf.setEditable(true);
					confirmNewPasswordTf.setEditable(true);
					confirmTf.setEditable(false);
					isConfirmed = true;
				}else {
					JOptionPane.showMessageDialog(null, "Mã xác nhận không chính xác!");
				}
			}
		});
	}
	
	private void catchTFOnMouseClick() {
		newPasswordTf.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
                errorLb.setText("");;
            }
		});
		
		confirmNewPasswordTf.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
                errorLb.setText("");;
            }
		});
	}
}
