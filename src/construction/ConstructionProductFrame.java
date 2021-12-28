package construction;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import database.Execute;
import database.Query;
import detail_frame.DetailConstructionPane;

import java.awt.Color;
import java.awt.Font;
import image_icon.CreateImageIcon;
import main.Controller;
import main.View;

import java.awt.event.*;
import java.sql.SQLException;

public class ConstructionProductFrame extends JFrame {
    
    private JLabel productIDLb;
    private JLabel productNameLb;
    private JTextField quantityTf;
    private JTextField priceTf;
    private JButton saveBtn;
    private JLabel totalPriceOfProductLb;

    private DefaultTableModel defaultTableModel;
    private DefaultTableModel defaultConstructionTableModel;

    private int n1;
    private int n2;

    private int totalPriceOfProduct;
    private int preTotalPriceOfProduct;

    private String bill_id;
	private JLabel unitLb;

    public ConstructionProductFrame(String bill_id, String productID, String productName, String quantity, String unit, 
                                    String price, String totalPriceOfProduct, DefaultTableModel defaultTableModel, 
									DefaultTableModel defaultConstructionTableModel, int n1, int n2){

        setIconImage(CreateImageIcon.createImageIcon("/res/cubes.png").getImage());
		setBounds(0, 0, 450, 350);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocation((View.MAX_WIDTH -450)/2, (View.MAX_HEIGHT - 350)/2);
        setVisible(true);
        
        this.defaultConstructionTableModel = defaultConstructionTableModel;
        this.n1 = n1;
        this.defaultTableModel = defaultTableModel;
        this.n2 = n2;
        this.preTotalPriceOfProduct = Controller.removeDot(totalPriceOfProduct);
        this.bill_id = bill_id;

        JLabel lblNewLabel = new JLabel("Mã sản phẩm:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel.setBounds(40, 11, 114, 32);
		getContentPane().add(lblNewLabel);
		
		productIDLb = new JLabel(productID);
		productIDLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		productIDLb.setBounds(164, 11, 130, 32);
		getContentPane().add(productIDLb);
		
		JLabel lblNewLabel_1 = new JLabel("Tên sản phẩm:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(40, 54, 114, 32);
		getContentPane().add(lblNewLabel_1);
		
		productNameLb = new JLabel(productName);
		productNameLb.setBackground(new Color(102, 255, 255));
		productNameLb.setOpaque(true);
		productNameLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		productNameLb.setBounds(164, 54, 230, 32);
		getContentPane().add(productNameLb);
		
		JLabel lblNewLabel_3 = new JLabel("Số lượng:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(40, 97, 114, 32);
		getContentPane().add(lblNewLabel_3);
		
		quantityTf = new JTextField(quantity);
		quantityTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		quantityTf.setBounds(164, 97, 100, 32);
		getContentPane().add(quantityTf);
		quantityTf.setColumns(10);
		
		unitLb = new JLabel(unit);
		unitLb.setOpaque(true);
		unitLb.setBackground(new Color(255, 255, 51));
		unitLb.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		unitLb.setBounds(274, 97, 70, 32);
		getContentPane().add(unitLb);
		
		JLabel lblNewLabel_5 = new JLabel("Đơn giá:");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_5.setBounds(40, 140, 114, 32);
		getContentPane().add(lblNewLabel_5);
		
		priceTf = new JTextField(price);
		priceTf.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		priceTf.setBounds(164, 140, 160, 32);
		getContentPane().add(priceTf);
		priceTf.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("(VNĐ)");
		lblNewLabel_6.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_6.setBounds(334, 140, 60, 32);
		getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Thành tiền:");
		lblNewLabel_7.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_7.setBounds(40, 183, 114, 32);
		getContentPane().add(lblNewLabel_7);
		
		totalPriceOfProductLb = new JLabel();
        totalPriceOfProductLb.setText(totalPriceOfProduct);
		totalPriceOfProductLb.setForeground(new Color(255, 255, 255));
		totalPriceOfProductLb.setOpaque(true);
		totalPriceOfProductLb.setBackground(new Color(255, 153, 51));
		totalPriceOfProductLb.setHorizontalAlignment(SwingConstants.CENTER);
		totalPriceOfProductLb.setFont(new Font("Times New Roman", Font.BOLD, 18));
		totalPriceOfProductLb.setBounds(164, 183, 160, 32);
		getContentPane().add(totalPriceOfProductLb);
		
		JLabel lblNewLabel_9 = new JLabel("(VNĐ)");
		lblNewLabel_9.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel_9.setBounds(334, 183, 60, 32);
		getContentPane().add(lblNewLabel_9);
		
		saveBtn = new JButton("Lưu");
		saveBtn.setBackground(new Color(0, 204, 51));
		saveBtn.setIcon(CreateImageIcon.createImageIcon("/res/save_icon.png"));
		saveBtn.setFont(new Font("Times New Roman", Font.BOLD, 20));
		saveBtn.setBounds(164, 242, 114, 40);
		getContentPane().add(saveBtn);
        catchSaveBtnClicked();
        catchUpdate();
    }

    private void catchSaveBtnClicked() {
        saveBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                updateNewPrice();
                setVisible(false);
            }
        });
    }

    private void catchUpdate(){
        quantityTf.addKeyListener(new KeyAdapter() {
            @Override
			public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    updateNewPrice();
                }
			}
		});

        priceTf.addKeyListener(new KeyAdapter() {
            @Override
			public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    updateNewPrice();
                }
			}
		});
    }

    private void updateNewPrice(){

		DetailConstructionPane.setTotalPrice(DetailConstructionPane.getTotalPrice() - preTotalPriceOfProduct);
		int price = Controller.removeDot(priceTf.getText());
		Number quantity_tmp = Controller.convertQuantity(Double.parseDouble(quantityTf.getText()), unitLb.getText());
		
        try {
            Execute.updateQuery(Query.getUpdateQuery("construction_bill", "quantity = " + "'" +  quantityTf.getText() 
            + "', price = " + "'" + price + "'", "id", bill_id));
            Execute.closeConnect();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        
        totalPriceOfProduct = Controller.calculatePrice(price, quantity_tmp);
        totalPriceOfProductLb.setText(Controller.priceWithDecimal(totalPriceOfProduct));
        defaultTableModel.setValueAt(Controller.priceWithDecimal(totalPriceOfProduct), n2, 6);
		defaultTableModel.setValueAt(quantity_tmp.toString(), n2, 3);
		defaultTableModel.setValueAt(Controller.priceWithDecimal(price), n2, 5);
        
        DetailConstructionPane.setTotalPrice(DetailConstructionPane.getTotalPrice() + totalPriceOfProduct);

        DetailConstructionPane.getTotalPriceLb().setText(Controller.priceWithDecimal(DetailConstructionPane.getTotalPrice()));
        defaultConstructionTableModel.setValueAt(Controller.priceWithDecimal(DetailConstructionPane.getTotalPrice()), n1, 4);

        preTotalPriceOfProduct = totalPriceOfProduct;
    }
}
