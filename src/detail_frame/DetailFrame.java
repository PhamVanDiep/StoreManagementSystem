package detail_frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import image_icon.CreateImageIcon;
import main.View;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;

public class DetailFrame extends JFrame{

    public static final int PRE_WIDTH = 1200;
	public static final int PRE_HEIGHT = 650;

    public DetailFrame(JPanel panel, char c) {
        try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
			UIManager.put("TableHeader.font",new Font("Times New Romans", Font.BOLD, 18) );
			UIManager.put("TableHeader.foreground",new Color(255,255,255));
			UIManager.put("TableHeader.background",new Color(24, 48, 119));
			UIManager.put("ProgressBar.background",new Color(88, 165, 214));

		} catch (Exception e) {
			e.printStackTrace();
		}
		setBounds((View.MAX_WIDTH-PRE_WIDTH)/2,(View.MAX_HEIGHT - PRE_HEIGHT)/2,PRE_WIDTH, PRE_HEIGHT);
		setResizable(false);
		if (c == '1') {
			setTitle("Thêm dư nợ");
		}else if (c == '2') {
			setTitle("Dư nợ chi tiết");
		}else if (c == '3') {
			setTitle("Thêm dự án");
		}else if (c == '4') {
			setTitle("Chi tiết dự án");
		}else if (c == '5') {
			setTitle("Chi tiết giờ làm");
		}
        setIconImage(CreateImageIcon.createImageIcon("/res/cubes.png").getImage());
		getContentPane().add(panel, BorderLayout.CENTER);
		
		setVisible(true);
	}
}
