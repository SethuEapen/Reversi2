import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

// Sets the main menu image and gets and sets different heights and withs for cetrain buttons on the main value. 
public class MenuState extends JPanel{
	
	Image myImage;
	int width = 669;
	int height = 528;
	
	public MenuState(){
		try {
			myImage = ImageIO.read(new File("launcher.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		width = myImage.getWidth(null);
		height = myImage.getHeight(null);
	}
	
	
	public void paintComponent(Graphics g) {
		g.drawImage(myImage, 0, 0, width, height, null);
	}

	//Getters and Setters
	
	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}
	
	
}