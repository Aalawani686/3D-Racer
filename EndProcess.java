import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.*;

public class EndProcess extends JPanel {
	Font title = new Font("Times New Roman", 50, 50);
	Font f = new Font("Helvetica", 15, 20);
	public void paint(Graphics g) {
		Image backg = Toolkit.getDefaultToolkit().getImage("DeathValley.jpg");
		Image backg1 = backg.getScaledInstance(1000, 1000, Image.SCALE_DEFAULT); // scale background
		MediaTracker tracker = new MediaTracker(new java.awt.Container());
		tracker.addImage(backg1, 0);
		try {
			tracker.waitForAll();
		} catch (InterruptedException ex) {
			throw new RuntimeException("Image loading interrupted", ex);
		}
		g.drawImage(backg1, -100, -230, this);
		g.setFont(title);
		g.setColor(Color.yellow);
		g.drawString("APCS4LIFE", 300, 120);
		g.setFont(f);
		g.setColor(Color.black);
		g.drawString("Good Job ! Your Time was:", 325, 150);
		g.drawRect(200, 160, 400, 40);
		g.drawString("Leaderboard", 325, 190);
//		for(int i = 0; i < leaderboard.size();i++){
//			
//		g.fillRect(x, y, width, height);

	}

	public EndProcess() {
		JFrame f = new JFrame("hello");
		f.setBackground(Color.BLACK);

//		JLabel Sahith = new JLabel("Sahith: Road Creation + Math");
//		JLabel Rudy = new JLabel("Rudy: GUI");
//		JLabel Andy = new JLabel("Andy: Networking");
//		JPanel p = new JPanel();
//		p.add(Sahith);
//		p.add(Rudy);
//		p.add(Andy);
//		f.add(p);

		f.add(this);
		f.setTitle("NEGRO");
		f.setSize(800, 800);

		// setups icon image

		f.setResizable(false);

//		f.add(this);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public static void main(String[] args) {

		EndProcess e = new EndProcess();
	}

}
