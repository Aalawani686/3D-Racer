import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.*;

public class EndProcess extends JPanel {
	private String time;
	Font title = new Font("Times New Roman", 50, 50);
	Font f = new Font("Helvetica", 15, 20);
	Font leader = new Font("Courier New", 30, 30);
	
	boolean yee;
	public boolean isYee() {
		return yee;
	}

	public void setYee(boolean yee) {
		this.yee = yee;
	}

	//	LeaderData d = new LeaderData();
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
		g.drawString("RACER GAME", 230, 120);
		g.setFont(f);
		g.setColor(Color.black);
		if(!yee) {
			g.drawString("Good Job ! Your Time was: " + time, 250, 150);
		}else {
			g.drawString("Good Job ! Your Time was: " + time, 250, 150);

			g.setColor(Color.yellow);
			g.fillRect(185, 160, 400, 40);
			g.setColor(Color.black);
			Graphics2D g2D = (Graphics2D) g;

			//		g2D.setStroke(new BasicStroke(50));
			g.setFont(leader);
			g.drawString("Leaderboard", 275, 190);
			g.setFont(f);
			g2D.setStroke(new BasicStroke(3));
			g.drawString("Place", 195, 225);
			g2D.drawLine(260, 200, 260, 735);
			g.drawString("Username", 300, 225);
			g2D.drawLine(440, 200, 440, 735);
			g.drawString("Time", 485, 225);

			g2D.drawLine(185, 240, 585, 240);
			for (int i = 0; i < 10; i++) {
				if (i + 1 == 10) {
					g.drawString(Integer.toString(i + 1), 210, 270 + 50 * i);
					g.drawString("hello", 270, 270+50*i);
					g.drawString("he", 450, 270+50*i);

				} else {
					g2D.drawLine(185, 290 + 50 * i, 585, 290 + 50 * i);
					g.drawString(Integer.toString(i + 1), 215, 270 + 50 * i);
					g.drawString("hello", 270, 270+50*i);
					g.drawString("he", 450, 270+50*i);
				}


			}
			g2D.setStroke(new BasicStroke(5));
			g2D.drawRect(185, 160, 400, 575);
			g2D.drawRect(185, 160, 400, 40);
			//		for(int i = 0; i < leaderboard.size();i++){
			//			
			//		g.fillRect(x, y, width, height);
		}
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

	public String getTime() {
		return time;
	}

	public String setTime(String time) {
		this.time = time;
		return time;
	}

}
