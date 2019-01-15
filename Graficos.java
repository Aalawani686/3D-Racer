import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Graficos extends JPanel {

	// properties of this class - the panel that shows up
	Color c = new Color(0, 0, 0);
	String background = "DeathValley.jpg";
	String car = "UserView.png";

	// 1) Declare an array of Square objects here

	// 2) declare an array of Circle objects here

	// 3) Declare the game character here

	// only do drawing for paint
	public void paint(Graphics g) {
		super.paintComponent(g);
//		String src = new File("").getAbsolutePath() + "/src/";
//		// background
//		ImageIcon bg = new ImageIcon(src + background);
//		Image temp1 = bg.getImage();
//		
//		// user
//		ImageIcon user = new ImageIcon(src + car);
//		Image temp2 = user.getImage();
//		
////		scaling
//		Image backg = temp1.getScaledInstance(1000, 1000, Image.SCALE_DEFAULT); //scale background
//		Image userV = temp2.getScaledInstance(175, 116, Image.SCALE_DEFAULT); //scale motorcycle
//		MediaTracker tracker = new MediaTracker(new java.awt.Container());
//		tracker.addImage(backg,0);
//		tracker.addImage(userV,0);
//		try {
//		    tracker.waitForAll();
//		} catch (InterruptedException ex) {
//		    throw new RuntimeException("Image loading interrupted", ex);
//		}
		int xBG = -100;
		int yBG = -100;
		int xU = 312;
		int yU = 660;
		 Image backg = Toolkit.getDefaultToolkit().getImage("DeathValley.jpg");
		 Image userV = Toolkit.getDefaultToolkit().getImage("UserView.png");
		    
		g.drawImage(backg, xBG, yBG, this);
		g.drawImage(userV, xU, yU, this);

	}// end of paint method - put code above for anything dealing with drawing -

	Font font = new Font("Courier New", 1, 50);

	/* do not draw here */

	// ==================code above ===========================

	public static void main(String[] arg) {
		JFrame frame= new JFrame("JavaTutorial.net");	
		frame.getContentPane().add(new Graficos());
		frame.setSize(800, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);		
	}
	

	Timer t;
}
