import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class NetworkDriver extends JPanel implements ActionListener, KeyListener {
	int t_width = 800;
	int t_height = 600;
	
	PlayerCar player = new PlayerCar();
	Road road = new Track1();
	
	int trackWidth = t_width*2;
	int length;
	int height;
	int width = 5;
	static String side = "";
	static Network n;

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

	public void paint(Graphics g) {
		super.paintComponent(g);
		playerPoint = road.getPara(forwardPosition);
		tangentAngle = road.getTanAngle(forwardPosition);
		
		//System.out.println("angle difference: " + player.getPlayerAngle() + " " + tangentAngle);
		
		for (int i = (int) (forwardPosition); i < forwardPosition+500; i++) {
			
			g.setColor(Color.black);
			g.drawLine((int)(road.getPara(i).x+700), (int)(-road.getPara(i).y+600), (int)(road.getPara(i+1).x+700), (int)(-road.getPara(i+1).y+600));
			g.setColor(Color.red);
			g.drawRect((int)(road.getPara(forwardPosition).x+700), (int)(-road.getPara(forwardPosition).y+600), 30, 30);
			
			drawingPoint = road.getPara(i);
			
			shift = road.getShift(playerPoint, drawingPoint, forwardPosition);
			
			//forwardPosition = (int)(forwardPosition);
			
			length = (int) (trackWidth - Math.abs(i-forwardPosition)*15);
			height = (int) (t_height - (i-forwardPosition)*(width-1));
			g.setColor(Color.gray);
			g.fillRect((int)((t_width-length)/2-shift-lateralPosition-((player.getPlayerAngle()-tangentAngle)*(i-forwardPosition)*10)), height, length, 10);
			
			
			g.setColor(Color.black);
			g.drawLine((int)(t_width/2), t_height, t_width/2+(int)(100*Math.sin((player.getPlayerAngle()-tangentAngle))), t_height + (int)(-100*Math.cos((player.getPlayerAngle()-tangentAngle))));
			
			g.setColor(Color.yellow);
			
			
			
			//System.out.println((i-forwardPosition)%20 + " " + (19-(forwardPosition%18)) + " " + (16-(forwardPosition%18)));
			if((i-forwardPosition)%20 <= 19-(forwardPosition%18) && (i-forwardPosition)%20 >= 16-(forwardPosition%18)) {
				g.fillRect((int)((t_width/2-shift-lateralPosition-((player.getPlayerAngle()-tangentAngle)*(i-forwardPosition)*10))-(50-(i-forwardPosition)/2)/2), height, (int) (50-(i-forwardPosition)/2), 10);
			}
			
			
		}
		
		//System.out.println("pos: " + forwardPosition);
		
	}
	
	public void update() {
		if(up) {
			forwardPosition += player.getForwardSpeed(road, forwardPosition);
			lateralPosition += player.getLateralSpeed(road, forwardPosition);
			if(lateralPosition > trackWidth/2) {
				
				lateralPosition -= player.getLateralSpeed(road, forwardPosition);
			}
			if(lateralPosition < -trackWidth/2) {
				
				lateralPosition -= player.getLateralSpeed(road, forwardPosition);
			}
		}
		if(down) {
			forwardPosition -= player.getForwardSpeed(road, forwardPosition);
			lateralPosition -= player.getLateralSpeed(road, forwardPosition);
			if(lateralPosition > trackWidth/2) {
				
				lateralPosition += player.getLateralSpeed(road, forwardPosition);
			}
			if(lateralPosition < -trackWidth/2) {
				
				lateralPosition += player.getLateralSpeed(road, forwardPosition);
			}
		}
		if(left) {
			player.subtractPlayerAngle();
		}
		if(right) {
			player.addPlayerAngle();
		}		
		
		
				
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();
		repaint();
	}
	
	public static void main(String[] arg) throws IOException {
		System.out.println("client or server");
		Scanner s = new Scanner(System.in);
		while(true){
			if(side.equals("client") || side.equals("server")){
				break;
			}
			side = s.nextLine();
		}
		n = new Network(side);
		Thread t1 = new Thread(n);
		t1.start();
		
		Driver d = new Driver();
	}
	public NetworkDriver(){
		
		JFrame f = new JFrame();
		f.setTitle("Driving Game");
		f.setSize(t_width,t_height);
		f.setBackground(Color.BLACK);
		f.setResizable(false);
		f.addKeyListener(this);
		f.add(this);
		t = new Timer(17,this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	Timer t;

	@Override
	public void keyPressed(KeyEvent e) {

		
		//System.out.println(e.getKeyCode());
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
		if (e.getKeyCode() == 32){
			n.send("HII");
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 39) {
			right = false;
		}
		if (e.getKeyCode() == 37) {
			left = false;
		}
		if (e.getKeyCode() == 38) {
			up = false;	
		}
		if (e.getKeyCode() == 40) {
			down = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}






