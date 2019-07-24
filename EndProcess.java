
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
/**
 * EndProcess.java
 * Final Screen at the conclusion of a game.
 * Shows the player the leaderboard and updates in realtime.
 *
 * @author Aniruddh Khanwale
 * @author Sahith Konakalla
 * @author Aniruddha Alawani
 *
 *
 *
 * @date January 22, 2019
 *
 */
public class EndProcess extends JPanel implements ActionListener {
	String name = ""; // username

	Leaderboard lb = new Leaderboard(); // creates leaderboard object

	Font title = new Font("Times New Roman", 50, 50); // font for the title page

	Font f = new Font("Helvetica", 15, 20); // default font

	Font leader = new Font("Courier New", 30, 30); // leader font set

	int time = 0; // time to be set


	/*
	 * Getter for username
	 */
	public String getName() {
		return name;
	}
	/*
	 * Setter for username
	 */
	public void setName(String name) {
		this.name = name;
	}
	/*
	 * Graphics method. Creates a grid within which to display the leaders.
	 * Also creates the background image.
	 * Displays the playertime
	 * Calls the leaderboard class and then prints results within the grid.
	 */
	public void paint(Graphics g) {
		//starts by updating the actual leaderboard
		try {
			updateLeaderboard();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//instantiates and scales background image
		Image backg = Toolkit.getDefaultToolkit().getImage("DeathValley.jpg");
		Image backg1 = backg.getScaledInstance(1000, 1000, Image.SCALE_DEFAULT);

		//tracker to verify that scaling works as intended
		MediaTracker tracker = new MediaTracker(new java.awt.Container());
		tracker.addImage(backg1, 0);
		try {
			tracker.waitForAll();
		} catch (InterruptedException ex) {
			throw new RuntimeException("Image loading interrupted", ex);
		}


		g.drawImage(backg1, -100, -200, this); // draws background

		g.setFont(title);
		g.setColor(Color.yellow);
		g.drawString("RACER GAME", 230, 120); // draws title
		g.setFont(f);
		g.setColor(Color.black);

		g.drawString("Good Job ! Your " + tiempoPrint(0, getTime()), 250, 150); // prints out user time

		g.setColor(Color.yellow);
		g.fillRect(185, 160, 400, 40);
		g.setColor(Color.black);
		Graphics2D g2D = (Graphics2D) g;

		// creates the grid within which to display leaderboard data
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

		// code to actually fill the leaderboard grid
		for (int i = 0; i < 9; i++) {
			if (i + 1 == 9) {
				if(lb.lboardnames[i]!= null) {
					g.drawString(lb.lboardnames[i], 270,
							270 + 50 * i);
					g.drawString(tiempoPrint(0, lb.lboardtimes[i]), 450,
							270 + 50 * i);
				}
				g.drawString(Integer.toString(i + 1), 210, 270 + 50 * i);


			} else {
				if(lb.lboardnames[i]!= null) {
					g.drawString(lb.lboardnames[i], 270,
							270 + 50 * i);
					g.drawString(tiempoPrint(0, lb.lboardtimes[i]), 450,
							270 + 50 * i);
				}
				g2D.drawLine(185, 290 + 50 * i, 585, 290 + 50 * i);
				g.drawString(Integer.toString(i + 1), 215, 270 + 50 * i);

			}

			g2D.setStroke(new BasicStroke(5));
			g2D.drawRect(185, 160, 400, 575);
			g2D.drawRect(185, 160, 400, 40);

		}
	}


	public EndProcess() throws IOException {

		JFrame f = new JFrame("hello");
		f.setBackground(Color.BLACK);
		f.add(this);
		f.setTitle("Congratulations!");
		f.setSize(800, 800);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		Timer t = new Timer(17, (ActionListener) this);
		t.start();

	}
	/*
	 * Method to continuously update the leaderboard as players finish
	 */
	public void update() {

		try {
			lb.addToBoard(getName(), getTime());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}




	public static void main(String[] args) throws IOException {

		EndProcess e = new EndProcess();
	}

	public int getTime() {
		return time;
	}

	public int setTime(int finTime) {
		this.time = finTime;
		return finTime;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		update();
		repaint();
	}
	/*
	 * Prints out the actual time in a readable format
	 * Converts to digital view from milliseconds.
	 */

	public String tiempoPrint(long start, int time) {
		if (((time - start) / 1000) <= 9) {
			return ("Time: 00:0" + ((time - start) / 1000));
		} else if (((time - start) / 1000) <= 59) {
			return ("Time: 00:" + ((time - start) / 1000));
		} else if (((time - start) / 1000) <= 599) {
			if ((((time - start) / 1000)) % 60 <= 9) {
				return ("Time: 0" + ((time - start) / 60000) + ":0" + (((time - start) / 1000)) % 60);
			} else {
				return ("Time: 0" + ((time - start) / 60000) + ":" + (((time - start) / 1000)) % 60);

			}
		} else {
			return ("Time: " + ((time - start) / 60000) + ":" + (((time - start) / 1000)) % 60);
		}
	}

	public void updateLeaderboard() throws IOException {
		lb.addToBoard(getName(), getTime());
	}

}
