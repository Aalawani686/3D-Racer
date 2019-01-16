import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Driver extends JPanel implements ActionListener, KeyListener {
	int t_width = 800;
	int t_height = 800;
	static boolean ready2go = false;
	String test;
	PlayerCar player = new PlayerCar();
	Road road = new Track1();
	TextDemo user = new TextDemo();

	int trackWidth = t_width * 2;
	int length;
	int height;
	int width = 5;

	double forwardPosition = 0;
	double lateralPosition = 0;

	Point2D.Double playerPoint;
	Point2D.Double drawingPoint;

	double tangentAngle;

	double shift;

	boolean left = false;
	boolean right = false;

	boolean up = false;
	boolean down = false;

	String background = "Background.jpg";
	String car = "Bike.png";

	int xBG = -100;
	int yBG = -230;
	int xU = 312;
	int yU = 660;
	int BGw = 1000;
	int BGh = 1000;
	int xW = 175;
	int xH = 116;
	int maxView = 100;
	Font f = new Font("Helvetica", 15, 20);
	long start = System.currentTimeMillis();

	public void paint(Graphics g) {
		super.paintComponent(g);
		String src = new File("").getAbsolutePath() + "/src/";
		// background
		Image backg = Toolkit.getDefaultToolkit().getImage("DeathValley.jpg");

		// user
		Image userV = Toolkit.getDefaultToolkit().getImage("Bike.png");

//		scaling
		Image backg1 = backg.getScaledInstance(BGw, BGh, Image.SCALE_DEFAULT); // scale background
		Image userV1 = userV.getScaledInstance(xW, xH, Image.SCALE_DEFAULT); // scale motorcycle
		MediaTracker tracker = new MediaTracker(new java.awt.Container());
		tracker.addImage(backg1, 0);
		tracker.addImage(userV1, 0);
		try {
			tracker.waitForAll();
		} catch (InterruptedException ex) {
			throw new RuntimeException("Image loading interrupted", ex);
		}
		g.drawImage(backg1, xBG, yBG, this);

		playerPoint = road.getPara(forwardPosition);
		tangentAngle = road.getTanAngle(forwardPosition);

		// System.out.println("angle difference: " + player.getPlayerAngle() + " " +
		// tangentAngle);

		for (int i = (int) (forwardPosition); i < forwardPosition + 500; i++) {

			g.setColor(Color.black);
			g.drawLine((int) (road.getPara(i).x + 700), (int) (-road.getPara(i).y + 600),
					(int) (road.getPara(i + 1).x + 700), (int) (-road.getPara(i + 1).y + 600));
			g.setColor(Color.red);
			g.drawRect((int) (road.getPara(forwardPosition).x + 700), (int) (-road.getPara(forwardPosition).y + 600),
					30, 30);

			drawingPoint = road.getPara(i);

			shift = road.getShift(playerPoint, drawingPoint, forwardPosition);

			// forwardPosition = (int)(forwardPosition);

			length = (int) (trackWidth - Math.abs(i - forwardPosition) * 15);
			height = (int) (t_height - (i - forwardPosition) * (width - 1));
			g.setColor(Color.gray);
			g.fillRect(
					(int) ((t_width - length) / 2 - shift - lateralPosition * (maxView - (i - forwardPosition))
							- ((player.getPlayerAngle() - tangentAngle) * (i - forwardPosition) * 10)),
					height, length, 10);

			g.setColor(Color.black);
//			g.drawLine((int) (t_width / 2), t_height,
//					t_width / 2 + (int) (100 * Math.sin((player.getPlayerAngle() - tangentAngle))),
//					t_height + (int) (-100 * Math.cos((player.getPlayerAngle() - tangentAngle))));

			g.setColor(Color.yellow);

			// System.out.println((i-forwardPosition)%20 + " " + (19-(forwardPosition%18)) +
			// " " + (16-(forwardPosition%18)));
			if ((i - forwardPosition) % 20 <= 19 - (forwardPosition % 18)
					&& (i - forwardPosition) % 20 >= 16 - (forwardPosition % 18)) {
				g.fillRect(
						(int) ((t_width / 2 - shift - lateralPosition * (maxView - (i - forwardPosition))
								- ((player.getPlayerAngle() - tangentAngle) * (i - forwardPosition) * 10))
								- (50 - (i - forwardPosition) / 2) / 2),
						height, (int) (50 - (i - forwardPosition) / 2), 10);
			}

		}
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 200, 100);
		g.setColor(Color.GREEN);
		g.setFont(f);
		g.drawString("@" + getTest(), 10, 50);
		g.drawString(timePrint(), 10, 70);
		g.drawString(Double.toString(player.getSpeed()).substring(0, 3), 10, 90);

		Graphics2D g2d = (Graphics2D) g; // Create a Java2D version of g.
		g2d.translate(xU + xW / 2, yU - xH / 2); // Translate the center of our coordinates.
		// System.out.println("pos: " + forwardPosition);
		if (right) {
			g2d.rotate(0.5); // Rotate the image by 1 radian.
			g2d.drawImage(userV1, 0, xH / 2, this);

		} else if (left) {
			g2d.rotate(-0.5); // Rotate the image by 1 radian.
			g2d.drawImage(userV1, -xW, xH / 2, this);

		} else {
			g.drawImage(userV1, -xW / 2, xH / 2, this);
		}

	}

	public void update() {

		if(up) {
			player.accelerate();
			//forwardPosition += 100;
		}
		if(!up) {
			player.deccelerate();
		}
		
		forwardPosition += player.getForwardSpeed(road, forwardPosition);
		lateralPosition += player.getLateralSpeed(road, forwardPosition);
		
		if(lateralPosition*maxView > trackWidth/2) {
			player.onGrass();
		}
		else if(lateralPosition*maxView < -trackWidth/2) {
			player.onGrass();
		}
		else {
			player.onRoad();
		}
		
		if(left) {
			player.subtractPlayerAngle();
		}
		if(right) {
			player.addPlayerAngle();
		}
		System.out.println(player.getSpeed());

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
		repaint();
	}

	public static void main(String[] arg) {
		Driver d = new Driver();

	}

	public Driver() {

		JFrame f = new JFrame();
		f.setTitle("Driving Game");
		f.setSize(t_width, t_height);
		f.setBackground(Color.BLACK);
		f.add(this);
		// setups icon image

		f.setResizable(false);
		f.addKeyListener(this);
//		f.add(this);

		t = new Timer(17, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

	}

	Timer t;

	@Override
	public void keyPressed(KeyEvent e) {

		// System.out.println(e.getKeyCode());
		if (e.getKeyCode() == 39) {
			right = true;

			if (xBG <= -10) {
				xBG += 10;
			}
		}
		if (e.getKeyCode() == 37) {
			left = true;
			if (xBG >= -190) {
				xBG -= 10;
			}
		}

		if (e.getKeyCode() == 38) {
			up = true;

		}
		if (e.getKeyCode() == 40) {
			down = true;

		}
		if (e.getKeyCode() == 13) {
			System.out.println("hello");

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 39 || e.getKeyCode() == 68) {
			right = false;

		}
		if (e.getKeyCode() == 37 || e.getKeyCode() == 65) {
			left = false;

		}
		if (e.getKeyCode() == 38 || e.getKeyCode() == 87) {
			up = false;

		}
		if (e.getKeyCode() == 40 || e.getKeyCode() == 83) {
			down = false;

		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String timePrint() {
		if (((System.currentTimeMillis() - start) / 1000) <= 9) {
			return ("Time: 00:0" + ((System.currentTimeMillis() - start) / 1000));
		} else if (((System.currentTimeMillis() - start) / 1000) <= 59) {
			return ("Time: 00:" + ((System.currentTimeMillis() - start) / 1000));
		} else if (((System.currentTimeMillis() - start) / 1000) <= 599) {
			if ((((System.currentTimeMillis() - start) / 1000)) % 60 <= 9) {
				return ("Time: 0" + ((System.currentTimeMillis() - start) / 60000) + ":0"
						+ (((System.currentTimeMillis() - start) / 1000)) % 60);
			} else {
				return ("Time: 0" + ((System.currentTimeMillis() - start) / 60000) + ":"
						+ (((System.currentTimeMillis() - start) / 1000)) % 60);

			}
		} else {
			return ("Time: " + ((System.currentTimeMillis() - start) / 60000) + ":"
					+ (((System.currentTimeMillis() - start) / 1000)) % 60);
		}

	}
//	else if (((System.currentTimeMillis() - start) / 1000) <= 3599)

}
