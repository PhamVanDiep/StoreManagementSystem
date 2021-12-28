package main;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import buttonClicked.MainButton;
import construction.ConstructionPane;
import daily_payment.DailyPaymentPane;
import debt.DebtPane;
import image_icon.CreateImageIcon;
import product.ProductPane;
import staff.StaffPane;
import supplier.SupplierPane;
import warranty.WarrantyPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.GridLayout;
public class View {
    private JFrame frame;
	private static int PRE_WIDTH = 1000;
	private static int PRE_HEIGHT = 700;

	static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int MAX_WIDTH = (int)size.getWidth();
	public static final int MAX_HEIGHT = (int)size.getHeight();
	
	private JPanel prePanel;
	private JButton preButton;
	private DailyPaymentPane dailyPaymentPane;
	private static ProductPane productPane;
	private StaffPane staffPane;
	private SupplierPane supplierPane;
	private WarrantyPane warrantyPane;
	private DebtPane debtPane;
	private ConstructionPane constructionPane;
	private String password;
	
    public View(String password) {
        prePanel = new JPanel();
		preButton = new JButton();
		preButton.setBackground(new Color(238, 123, 76));
		
		this.password = password;
		
        initFrame();
        initInfoPane();
		initCenterPane();
        initMainBtnPane();
    }

    private void initCenterPane() {

		dailyPaymentPane = new DailyPaymentPane();
		prePanel = dailyPaymentPane;
		frame.getContentPane().add(prePanel, BorderLayout.CENTER);

		productPane = new ProductPane(password);
		
		debtPane = new DebtPane();

		constructionPane = new ConstructionPane();

		staffPane = new StaffPane();

		supplierPane = new SupplierPane();

		warrantyPane = new WarrantyPane();
	}

	private void initMainBtnPane() {
        JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, PRE_HEIGHT));
		panel.setBackground(new Color(255, 255, 255));
		frame.getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new GridLayout(7, 1, 0, 0));

        MainButton paymentBtn = new MainButton("  Bán hàng ",CreateImageIcon.createImageIcon("/res/payment_icon.png"));
		preButton = paymentBtn;
        addMouseExit(paymentBtn);
        paymentBtn.setBackground(new Color(238, 123, 76));
		catchClickMainButton(dailyPaymentPane, paymentBtn);
		panel.add(paymentBtn);
		
		MainButton productBtn = new MainButton("  Sản phẩm ",CreateImageIcon.createImageIcon("/res/product_icon.png"));
        addMouseExit(productBtn);
		catchClickMainButton(productPane, productBtn);
		panel.add(productBtn);
		
		MainButton debtBtn = new MainButton("  Dư nợ      ",CreateImageIcon.createImageIcon("/res/debt_icon.png"));
        addMouseExit(debtBtn);
		catchClickMainButton(debtPane, debtBtn);
		panel.add(debtBtn);
		
		MainButton constructionBtn = new MainButton("  Dự án      ",CreateImageIcon.createImageIcon("/res/construction_icon.png"));
        addMouseExit(constructionBtn);
		catchClickMainButton(constructionPane, constructionBtn);
		panel.add(constructionBtn);
		
		MainButton warrantyBtn = new MainButton("  Bảo hành ",CreateImageIcon.createImageIcon("/res/warranty_icon.png"));
        addMouseExit(warrantyBtn);
		catchClickMainButton(warrantyPane, warrantyBtn);
		panel.add(warrantyBtn);
		
		MainButton staffBtn = new MainButton("  Nhân viên ",CreateImageIcon.createImageIcon("/res/staff_icon.png"));
        addMouseExit(staffBtn);
		catchClickMainButton(staffPane, staffBtn);
		panel.add(staffBtn);
		
		MainButton supplierBtn = new MainButton("  Cung ứng  ",CreateImageIcon.createImageIcon("/res/supplier_icon.png"));
        addMouseExit(supplierBtn);
		catchClickMainButton(supplierPane, supplierBtn);
		panel.add(supplierBtn);
	}

    private void initInfoPane() {
    	
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(PRE_WIDTH, 100));
		panel_1.setBackground(new Color(0, 102, 204));
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new GridLayout(3, 0, 0, 0));
		
		JLabel storeNameLb = new JLabel("Cửa hàng " + Controller.store_name);
		storeNameLb.setForeground(new Color(255, 255, 255));
		storeNameLb.setFont(new Font("Times New Roman", Font.BOLD, 25));
		storeNameLb.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(storeNameLb);
		
		JLabel storePhoneNumberLb = new JLabel("Hotline: " + Controller.phone_number);
		storePhoneNumberLb.setForeground(new Color(255, 255, 255));
		storePhoneNumberLb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		storePhoneNumberLb.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(storePhoneNumberLb);
		
		JLabel storeAddressLb = new JLabel(Controller.address);
		storeAddressLb.setForeground(new Color(255, 255, 255));
		storeAddressLb.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		storeAddressLb.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(storeAddressLb);
    }

    private void initFrame() {

		frame = new JFrame();
		frame.setBounds((MAX_WIDTH-PRE_WIDTH)/2,0,PRE_WIDTH, PRE_HEIGHT);
		frame.setMinimumSize(new Dimension(PRE_WIDTH, PRE_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setIconImage(CreateImageIcon.createImageIcon("/res/cubes.png").getImage());
		frame.setTitle("Quản lý cửa hàng");
    }

    private void addMouseExit(JButton button) {
        button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				if (preButton != button) {
					button.setBackground(new Color(0, 153, 255));
				}
			}
		});
    }

    private void catchClickMainButton(JPanel panel, JButton button){
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (prePanel != panel && preButton != button) {
					preButton.setBackground(new Color(0, 153, 255));
					frame.getContentPane().remove(prePanel);
					frame.getContentPane().repaint();
					frame.getContentPane().add(panel, BorderLayout.CENTER);
					frame.invalidate();
					frame.validate();
					prePanel = panel;
					preButton = button;
					button.setBackground(new Color(238, 123, 76));
				}
    		}
		});
	}

	public static ProductPane getProductPane() {
		return productPane;
	}
}
