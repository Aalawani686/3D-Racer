
/**
 * Driver.java
 * Actual Gameplay. Includes:
 * The road creation, the movement of the car
 *	Includes rendering of opponents
 * @author Sahith Konakalla
 * @author Aniruddh Khanwale
 * @author Aniruddha Alawani
 *
 *
 *
 * @date January 22, 2019
 *
 */
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import javax.swing.*;

public class Driver extends JPanel implements ActionListener, KeyListener {
	// classes to be implemented or used
	PlayerCar player = new PlayerCar();
	Road road = new Track1();

	// frame dimensiions
	int t_width = 800;
	int t_height = 800;

	// update control
	boolean pintar = true;
	long start = System.currentTimeMillis() + 4200;
	long traffic = System.currentTimeMillis();
	boolean r2go = false;

	// Networking data
	static String ip = "";
	static String test = "";
	static String side = "";
	static Server n;

	static ArrayList<Double> enemyPos = new ArrayList<Double>();
	static ArrayList<Double> enemyLat = new ArrayList<Double>();
	static ArrayList<String> enemyNames = new ArrayList<String>();

	HashMap positions = new HashMap();
	HashMap laterals = new HashMap();
	HashMap times = new HashMap();

	ArrayList<Image> opps = new ArrayList<Image>();

	// GameType Control
	static String gameType;

	// road & image variables & datum
	int trackWidth = t_width * 2;
	boolean onTheRoad = true;
	double forwardPosition = 0;
	double lateralPosition = 0;
	int length;
	int height;
	int lapcount = 1;
	int width = 5;
	int flagH = 310;
	Point2D.Double playerPoint;
	Point2D.Double drawingPoint;
	double tangentAngle;
	double shift;
	int tiempo = 3;
	int finTime = 0;
	int alertFlasher = 0;

	// direction controls
	boolean left = false;
	boolean right = false;
	boolean up = false;
	boolean down = false;

	// image data
	String car = "Bike.png";
	String fin = "FinishGraphic.png";
	String oppo = "Opponent.png";
	int xBG = -100;
	int yBG = -230;
	int xU = 312;
	int yU = 660;
	int BGw = 1000;
	int BGh = 1000;
	int xW = 175;
	int xH = 116;
	int maxView = 100;
	int lW = xW - 15;
	int lH = xH;
	int oW = 100;
	int oH = 100;
	int gH;

	// text and fonts
	Font f = new Font("Helvetica", 15, 20);
	Font name = new Font("Helvetica", 30, 30);
	Font cDown = new Font("Helvetica", 50, 50);

	/*
	 * Paint method. Called repeatedly to create actual things
	 */
	public void paint(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(00, 00);
		MediaTracker tracker = new MediaTracker(new java.awt.Container());

		// image initialization
		Image backg = Toolkit.getDefaultToolkit().getImage("DeathValley.jpg");
		Image userV = Toolkit.getDefaultToolkit().getImage("Bike.png");
		Image finLine = Toolkit.getDefaultToolkit().getImage(fin);
		Image opp = Toolkit.getDefaultToolkit().getImage(oppo);

		// scaling
		Image adversary = opp.getScaledInstance(oW, oH, Image.SCALE_DEFAULT);
		if (gameType.equals("multi") || gameType.equals("admin")) {
			opps.add(adversary);
			tracker.addImage(adversary, 0);
		}
		Image backg1 = backg.getScaledInstance(BGw, BGh, Image.SCALE_DEFAULT); // scale background
		Image userV1 = userV.getScaledInstance(xW, xH, Image.SCALE_DEFAULT); // scale motorcycle
		Image finalLine = finLine.getScaledInstance(lW, lH, Image.SCALE_DEFAULT); // scale motorcycle

		// image modulation

		tracker.addImage(backg1, 0);
		tracker.addImage(userV1, 0);
		tracker.addImage(finalLine, 0);

		try {
			tracker.waitForAll();
		} catch (InterruptedException ex) {
			throw new RuntimeException("Image loading interrupted", ex);
		}

		g.drawImage(backg1, xBG, yBG, this); // draws background

		if (r2go) { // verifies that the waiting period is over

			playerPoint = road.getPara(forwardPosition);
			tangentAngle = road.getTanAngle(forwardPosition);

			// Minimap code

			g.setColor(Color.WHITE);
			g.fillRect(648, 8, 150, 100);
			g.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(4));
			g.drawRect(646, 6, 150, 100);
			g.drawString("Lap " + Integer.toString(lapcount) + " of" + " 3", 676, 100);
			g2d.setStroke(new BasicStroke());
			g.setFont(f);
			g.setColor(Color.green);
			g.drawRect((int) (748), (int) (65), 10, 10);
			g.setColor(Color.red);

			// adversaries on minimap

			if (gameType.equals("multi") || gameType.equals("admin")) {
				for (int i = 0; i < enemyPos.size(); i++) {
					if (Math.abs(enemyPos.get(i) - forwardPosition) < 200) {
						g.drawRect(
								(int) (748 + (road.getPara(enemyPos.get(i)).x - road.getPara(forwardPosition).x) / 10),
								(int) (65 - (road.getPara(enemyPos.get(i)).y - road.getPara(forwardPosition).y) / 10),
								10, 10);
					}
				}
			}

			player.setAngleToRoad(road, forwardPosition);
			for (int i = (int) (forwardPosition); i < forwardPosition + 105; i++) { // this loop is responsible for
																					// drawing the road
				// draws road

				g.setColor(Color.black);
				drawingPoint = road.getPara(i);
				shift = road.getShift(playerPoint, drawingPoint, forwardPosition) * 5;
				length = (int) (trackWidth - Math.abs(i - forwardPosition) * 15);
				height = (int) (t_height - (i - forwardPosition) * (width - 1));

				g.setColor(Color.gray);
				g.fillRect((int) ((t_width - length) / 2 - shift - lateralPosition * (maxView - (i - forwardPosition))
						- ((player.getAngleToRoad()) * (i - forwardPosition) * 10)), height, length, 10);

				// draws finish line

				if (i == road.getEndPosition()) {
					lW = length;
					lH = length * 2 / 3;
					flagH = height - lH;
					g.drawImage(finalLine,
							(int) ((t_width - length) / 2 - shift - lateralPosition * (maxView - (i - forwardPosition))
									- ((player.getAngleToRoad()) * (i - forwardPosition) * 10)),
							flagH, this);

				}

				// creates middle lines

				g.setColor(Color.yellow);
				if ((i - forwardPosition) % 20 <= 19 - (forwardPosition % 18)
						&& (i - forwardPosition) % 20 >= 16 - (forwardPosition % 18)) {
					g.fillRect(
							(int) ((t_width / 2 - shift - lateralPosition * (maxView - (i - forwardPosition))
									- ((player.getAngleToRoad()) * (i - forwardPosition) * 10))
									- (50 - (i - forwardPosition) / 2) / 2),
							height, (int) (50 - (i - forwardPosition) / 2), 10);
				}

			}

			// creates the actual minimap lines

			for (int i = (int) (forwardPosition); i < forwardPosition + 8000; i++) {
				g.setColor(Color.BLACK);
				g.drawLine((int) ((road.getPara(i).x / 10 + 748) - (int) road.getPara(forwardPosition).x / 10),
						(int) (-road.getPara(i).y / 10 + 65 + (int) road.getPara(forwardPosition).y / 10),
						(int) ((road.getPara(i + 1).x / 10 + 748) - (int) road.getPara(forwardPosition).x / 10),
						(int) (-road.getPara(i + 1).y / 10 + 65) + (int) road.getPara(forwardPosition).y / 10);
			}

			player.setAngleToRoad(road, forwardPosition);

			// loop to draw finish line

			for (int i = (int) (forwardPosition); i < forwardPosition + 105; i++) {
				g.setColor(Color.black);
				drawingPoint = road.getPara(i);
				shift = road.getShift(playerPoint, drawingPoint, forwardPosition) * 5;
				length = (int) (trackWidth - Math.abs(i - forwardPosition) * 15);
				height = (int) (t_height - (i - forwardPosition) * (width - 1));

				if (i == road.getEndPosition()) {
					lW = length;
					lH = length * 2 / 3;
					flagH = height - lH;
					g.drawImage(finalLine,
							(int) ((t_width - length) / 2 - shift - lateralPosition * (maxView - (i - forwardPosition))
									- ((player.getAngleToRoad()) * (i - forwardPosition) * 10)),
							flagH, this);

				}
				g.setColor(Color.yellow);
				if ((i - forwardPosition) % 20 <= 19 - (forwardPosition % 18)
						&& (i - forwardPosition) % 20 >= 16 - (forwardPosition % 18)) {
					g.fillRect(
							(int) ((t_width / 2 - shift - lateralPosition * (maxView - (i - forwardPosition))
									- ((player.getAngleToRoad()) * (i - forwardPosition) * 10))
									- (50 - (i - forwardPosition) / 2) / 2),
							height, (int) (50 - (i - forwardPosition) / 2), 10);
				}

			}

			for (int i = (int) (forwardPosition); i < forwardPosition + 105; i++) {

				drawingPoint = road.getPara(i);

				shift = road.getShift(playerPoint, drawingPoint, forwardPosition) * 5;

				length = (int) (trackWidth - Math.abs(i - forwardPosition) * 15);
				height = (int) (t_height - (i - forwardPosition) * (width - 1));

				if (i == road.getEndPosition()) {
					lW = length;
					lH = length * 2 / 3;
					flagH = height - lH;

					g.drawImage(finalLine,
							(int) ((t_width - length) / 2 - shift - lateralPosition * (maxView - (i - forwardPosition))
									- ((player.getAngleToRoad()) * (i - forwardPosition) * 10)),
							flagH, this);

				}

				// creates enemy images, contingent on existence of server

				if (gameType.equals("multi") || gameType.equals("admin")) {
					for (int q = 0; q < enemyPos.size(); q++) {
						if (i == enemyPos.get(q).intValue()) {
							oW = length / 10;
							oH = length / 7;
							gH = height - oH;
							// draws opponent(s)
							if(enemyLat.size()==enemyPos.size()) {
								g.drawImage(adversary, (int) ((t_width / 2 - shift - lateralPosition * (maxView - (i - forwardPosition))
										- ((player.getAngleToRoad()) * (i - forwardPosition) * 10) + (enemyLat.get(q)/trackWidth)*length*100)
										- (oW/2 - (i - forwardPosition) / 20) ),
										gH, (int) (oW - (i - forwardPosition)/10), (int) ( oH - (i - forwardPosition)/7), this);
							}
						}
					}

				}
			}

			g.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(4));
			g.drawRect(4, 4, 175, 75);

			g.setColor(Color.GREEN);

			g.setFont(name);
			g.drawString("@" + getTest(), 10, 30);
			g.setFont(f);
			g.drawString(timePrint(), 10, 50);
			g.drawString("Current Speed:" + Double.toString(player.getSpeed()).substring(0, 3), 10, 70);
			g2d.setStroke(new BasicStroke());

			// flashes alert to put you on the road again

			if (!onTheRoad) {

				g.setColor(Color.red);
				g.fillRect(275, 00, 250, 100);
				if ((alertFlasher % 2) % 2 == 0) {
					g.setColor(Color.YELLOW);
					g.drawString("Alert!!!", 375, 20);
					g.drawString("Get back on the road!", 305, 70);
				}
				alertFlasher++;
			}

			// flashes alert that you are almost done

			if (forwardPosition >= road.getEndPosition() - 200 && forwardPosition <= road.getEndPosition()
					&& lapcount == 2) {
				g.setColor(Color.GREEN);
				g.drawString("Almost There!", 305, 70);
			}

			// ending process
			if (lapcount == 4) {
				finTime = (int) ((System.currentTimeMillis() - start));
				if ((gameType.equals("multi") || gameType.equals("admin")) && enemyPos.size() >= 2) {
					n.send("t" + Integer.toString(finTime) + "&" + test);
				}
				EndProcess e = null;
				try {
					e = new EndProcess();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				e.setName(getTest());

				e.setTime(finTime);

				t.stop();
			}
			// Create a Java2D version of g.
			g2d.translate(xU + xW / 2, yU - xH / 2); // Translate the center of our coordinates.

			if (right) {
				g2d.rotate(0.5); // Rotate the image by 0.5 radian.
				g2d.drawImage(userV1, 0, xH / 2, this);

			} else if (left) {
				g2d.rotate(-0.5); // Rotate the image by 0.5 radian.
				g2d.drawImage(userV1, -xW, xH / 2, this);

			} else {
				g.drawImage(userV1, -xW / 2, xH / 2, this);
			}

		} else {
			// timer
			g.setFont(cDown);
			if (tiempo == 3) {
				g.setColor(Color.red);
				g.drawString(Integer.toString(tiempo), 375, 375);
			} else if (tiempo == 2) {
				g.setColor(Color.red);
				g.drawString(Integer.toString(3), 375, 375);
			} else if (tiempo == 1) {
				g.setColor(Color.yellow);
				g.drawString(Integer.toString(2), 375, 375);
			} else if (tiempo == 0) {
				g.setColor(Color.yellow);
				g.drawString(Integer.toString(1), 375, 375);
			} else {
				g.setColor(Color.GREEN);
				g.drawString("GO!", 375, 375);
				if ((double) (System.currentTimeMillis() - traffic) / 1000 >= 0.2) {
					r2go = true;
				}

			}

			if ((System.currentTimeMillis() - traffic) / 1000 >= 1) {
				traffic = System.currentTimeMillis();
				tiempo--;
			}

		}

	}

	public void update() {

		// updates data by sending information to opponents
		boolean isIn;
		n.send("p"+forwardPosition+"&"+getTest());
		positions = n.getPositions();

		n.send("l"+lateralPosition+"&"+getTest());
		laterals = n.getLaterals();

		Iterator it = positions.entrySet().iterator();
		Iterator it2 = laterals.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Map.Entry pair2 = (Map.Entry) it2.next();
			isIn = false;
			for(int i=0; i<enemyNames.size(); i++){
				if(pair.getKey().equals(getTest())){
					isIn = true;
					continue;
				}
				if(pair.getKey().equals(enemyNames.get(i))){
					enemyPos.set(i, (Double) pair.getValue());
					enemyLat.set(i, (Double) pair2.getValue());
					isIn = true;
				}

			}
			if(isIn == false){
				enemyPos.add((Double)pair.getValue());
				enemyLat.add((Double)pair2.getValue());
				enemyNames.add((String) pair.getKey());
			}
			isIn = false;
		}

		times = n.getTimes();

		if (up) {
			player.accelerate();

		}
		if (!up) {
			player.deccelerate();
		}

		forwardPosition += player.getForwardSpeed();
		lateralPosition += player.getLateralSpeed();

		if (lateralPosition * maxView > trackWidth / 2) {
			player.onGrass();
			onTheRoad = false;
		} else if (lateralPosition * maxView < -trackWidth / 2) {
			player.onGrass();
			onTheRoad = false;
		} else {
			player.onRoad();
			onTheRoad = true;

		}

		if (left) {
			player.subtractPlayerAngle();
		}
		if (right) {
			player.addPlayerAngle();
		}
		if (road.checkIfLap(forwardPosition)) {
			forwardPosition = 0.0;
			lapcount++;

		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
		if (pintar) {
			repaint();
		}

	}

	public static void main(String[] arg) throws IOException {

	}

	public Driver() {
		// constructor creates and initializes variables based on the gametype
		System.out.println(gameType);
		if (gameType.equals("single")) {
			n = new Server("server", "localhost");
			Thread t1 = new Thread(n);
			t1.start();
		} else if (gameType.equals("multi")) {
			Scanner s = new Scanner(System.in);
			System.out.println("What is the IP Address");
			while (true) {
				if (ip.length() > 0) {
					break;
				}
				ip = s.nextLine();
			}
			System.out.println(ip);
			n = new Server("client", ip);
			Thread t1 = new Thread(n);
			t1.start();
		} else if (gameType.equals("admin")) {
			System.out.println("client or server");
			Scanner s = new Scanner(System.in);
			while (true) {
				if (side.equals("client") || side.equals("server")) {
					break;
				}
				side = s.nextLine();
			}
			System.out.println("What is the IP Address");
			while (true) {
				if (ip.length() > 0) {
					break;
				}
				ip = s.nextLine();
			}
			System.out.println(ip);
			n = new Server(side, ip);
			Thread t1 = new Thread(n);
			t1.start();
		}
		JFrame f = new JFrame();
		f.setTitle("Driving Game");
		f.setSize(t_width, t_height);
		f.setBackground(Color.BLACK);
		f.add(this);

		f.setResizable(false);
		f.addKeyListener(this);

		t = new Timer(17, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);

	}

	Timer t;

	@Override
	public void keyPressed(KeyEvent e) {


		if (e.getKeyCode() == 39 || e.getKeyCode() == 68) {
			right = true;

		}
		if (e.getKeyCode() == 37 || e.getKeyCode() == 65) {
			left = true;

		}

		if (e.getKeyCode() == 38 || e.getKeyCode() == 87) {
			up = true;

		}
		if (e.getKeyCode() == 40 || e.getKeyCode() == 83) {
			down = true;

		}

		if (e.getKeyCode() == 32) {
			player.deccelerate();;

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

	public static String isGameType() {
		return gameType;
	}

	public static void setGameType(String gameType) {
		Driver.gameType = gameType;
	}

}
