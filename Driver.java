import java.awt.BasicStroke;
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
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Driver extends JPanel implements ActionListener, KeyListener {
	int t_width = 800;
	int t_height = 800;
	float width1 = 3;
	
	static boolean ready2go = false;
	String test;
	PlayerCar player = new PlayerCar();
	Road road = new Track1();
	static String side = "";
	static Server n;
	int trackWidth = t_width * 2;
	int length;
	int height;
	int lapcount = 1;
	int width = 5;
	int flagH = 310;
	boolean onTheRoad = true;
	double forwardPosition = 0;
	double lateralPosition = 0;
	boolean ender = true;
	Point2D.Double playerPoint;
	Point2D.Double drawingPoint;

	double tangentAngle;

	double shift;
	int tiempo = 3;
	int finTime = 0;

	boolean left = false;
	boolean right = false;

	boolean up = false;
	boolean down = false;

	String car = "Bike.png";
	String fin = "FinishGraphic.png";
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
	Font name = new Font("Helvetica", 30, 30);
	long start = System.currentTimeMillis()+4200;
	long traffic = System.currentTimeMillis();
	int lW = xW - 15;
	int lH = xH;
	int alertFlasher = 0;
	Font cDown = new Font("Helvetica", 50, 50);
	boolean r2go = false;
	int enemyPos = 600;
	public void paint(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(00, 00);

//		String src = new File("").getAbsolutePath() + "/src/";
		// background
		Image backg = Toolkit.getDefaultToolkit().getImage("DeathValley.jpg");

		// userx
		Image userV = Toolkit.getDefaultToolkit().getImage("Bike.png");
		Image finLine = Toolkit.getDefaultToolkit().getImage(fin);
//		scaling
		Image backg1 = backg.getScaledInstance(BGw, BGh, Image.SCALE_DEFAULT); // scale background
		Image userV1 = userV.getScaledInstance(xW, xH, Image.SCALE_DEFAULT); // scale motorcycle
		Image finalLine = finLine.getScaledInstance(lW, lH, Image.SCALE_DEFAULT); // scale motorcycle

		MediaTracker tracker = new MediaTracker(new java.awt.Container());
		tracker.addImage(backg1, 0);
		tracker.addImage(userV1, 0);
		tracker.addImage(finalLine, 0);
		try {
			tracker.waitForAll();
		} catch (InterruptedException ex) {
			throw new RuntimeException("Image loading interrupted", ex);
		}
		g.drawImage(backg1, xBG, yBG, this);
		if (r2go) {
			
			playerPoint = road.getPara(forwardPosition);
			tangentAngle = road.getTanAngle(forwardPosition);

			// System.out.println("angle difference: " + player.getPlayerAngle() + " " +
			// tangentAngle);

//		g.drawString("Minimap", 600, 30);
			g.setColor(Color.WHITE);
			g.fillRect(648, 8, 150, 100);
			g.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(4));
			g.drawRect(646, 6, 150, 100);
			g.drawString("Lap " + Integer.toString(lapcount) + " of" + " 3", 676, 100);
			g2d.setStroke(new BasicStroke());
			g.setFont(f);
			g.setColor(Color.red);
			g.drawRect((int) (748), (int) (65), 10, 10);
			g.setColor(Color.green);
			if(Math.abs(enemyPos - forwardPosition) < 200){
			g.drawRect((int) (748 + (road.getPara(enemyPos).x - road.getPara(forwardPosition).x) / 10), (int)(65 - (road.getPara(enemyPos).y - road.getPara(forwardPosition).y) / 10), 10, 10);
			}
			player.setAngleToRoad(road, forwardPosition);
			for (int i = (int) (forwardPosition); i < forwardPosition + 500; i++) {

				g.setColor(Color.black);
				g.drawLine((int) ((road.getPara(i).x / 10 + 748) - (int) road.getPara(forwardPosition).x / 10),
						(int) (-road.getPara(i).y / 10 + 65 + (int) road.getPara(forwardPosition).y / 10),
						(int) ((road.getPara(i + 1).x / 10 + 748) - (int) road.getPara(forwardPosition).x / 10),
						(int) (-road.getPara(i + 1).y / 10 + 65) + (int) road.getPara(forwardPosition).y / 10);
				drawingPoint = road.getPara(i);

				shift = road.getShift(playerPoint, drawingPoint, forwardPosition)*5;

				// forwardPosition = (int)(forwardPosition);

				length = (int) (trackWidth - Math.abs(i - forwardPosition) * 15);
				height = (int) (t_height - (i - forwardPosition) * (width - 1));
				g.setColor(Color.gray);
				if (i == road.getEndPosition()) {
					lW = length;
					lH = length*2/3;
					flagH = height - lH;
					g.fillRect(
							(int) ((t_width - length) / 2 - shift - lateralPosition * (maxView - (i - forwardPosition))
									- ((player.getAngleToRoad()) * (i - forwardPosition) * 10)),
							height, length, 10);

					g.drawImage(finalLine,
							(int) ((t_width - length) / 2 - shift - lateralPosition * (maxView - (i - forwardPosition))
									- ((player.getAngleToRoad()) * (i - forwardPosition) * 10)),
							flagH, this);

				} else {
					g.fillRect(
							(int) ((t_width - length) / 2 - shift - lateralPosition * (maxView - (i - forwardPosition))
									- ((player.getAngleToRoad()) * (i - forwardPosition) * 10)),
							height, length, 10);
				}

				g.setColor(Color.black);
//			g.drawLine((int) (t_width / 2), t_height,
//					t_width / 2 + (int) (100 * Math.sin((player.getAngleToRoad()))),
//					t_height + (int) (-100 * Math.cos((player.getAngleToRoad()))));

				g.setColor(Color.yellow);

				// System.out.println((i-forwardPosition)%20 + " " + (19-(forwardPosition%18)) +
				// " " + (16-(forwardPosition%18)));
				if ((i - forwardPosition) % 20 <= 19 - (forwardPosition % 18)
						&& (i - forwardPosition) % 20 >= 16 - (forwardPosition % 18)) {
					g.fillRect(
							(int) ((t_width / 2 - shift - lateralPosition * (maxView - (i - forwardPosition))
									- ((player.getAngleToRoad()) * (i - forwardPosition) * 10))
									- (50 - (i - forwardPosition) / 2) / 2),
							height, (int) (50 - (i - forwardPosition) / 2), 10);
				}

			}

			g.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(4));
			g.drawRect(4, 4, 175, 75);

			g.setColor(Color.GREEN);

			g.setFont(name);
			g.drawString("@" + getTest(), 10, 30); // .substring(0, 8) ADDDDDDDD
			g.setFont(f);
			g.drawString(timePrint(), 10, 50);
			g.drawString("Current Speed:" + Double.toString(player.getSpeed()).substring(0, 3), 10, 70);
			g2d.setStroke(new BasicStroke());
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
			if (forwardPosition >= road.getEndPosition()-200 && forwardPosition <= road.getEndPosition() && lapcount == 3) {
				g.setColor(Color.GREEN);
				g.drawString("Almost There!", 305, 70);
			}
//				System.exit(0);
			if(lapcount == 4) {
				finTime = (int) ((System.currentTimeMillis()-start/1000));
				EndProcess e = new EndProcess();
				e.setTime(timePrint());
				t.stop();
			}
			// Create a Java2D version of g.
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

		} else {
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
			}else if(tiempo == 0){
				g.setColor(Color.yellow);
				g.drawString(Integer.toString(1), 375, 375);
			}else {
				g.setColor(Color.GREEN);
				g.drawString("GO!", 375, 375);
				if ((double)(System.currentTimeMillis() - traffic) / 1000 >= 0.2) {
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

		if (up) {
			player.accelerate();
			// forwardPosition += 100;
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
		if(road.checkIfLap(forwardPosition)) {
			forwardPosition = 0.0;
			lapcount++;
			
		}
		
//		System.out.println(player.getSpeed());

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
		repaint();
	}

	public static void main(String[] arg) throws IOException {
//		System.out.println("client or server");
//		Scanner s = new Scanner(System.in);
//		while (true) {
//			if (side.equals("client") || side.equals("server")) {
//				break;
//			}
//			side = s.nextLine();
//		}
//		n = new Server(side, "hello");
//		Thread t1 = new Thread(n);
//		t1.start();
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
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);

	}

	Timer t;

	@Override
	public void keyPressed(KeyEvent e) {

		// System.out.println(e.getKeyCode());
		if (e.getKeyCode() == 39) {
			right = true;

		}
		if (e.getKeyCode() == 37) {
			left = true;

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
		if (e.getKeyCode() == 32) {
			n.send("pwoot");
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
