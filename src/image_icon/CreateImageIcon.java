package image_icon;

import javax.swing.ImageIcon;

public class CreateImageIcon {
	public static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = CreateImageIcon.class.getResource(path);
		if (imgURL != null) {
		     return new ImageIcon(imgURL, "Java");
		} else {            
		     System.err.println("Couldn't find file: " + path);
		     return null;
		}
	}
}
