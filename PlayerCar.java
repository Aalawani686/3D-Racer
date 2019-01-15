import java.awt.Point;

public class PlayerCar {
	private double playerAngle = 0;
	
	private double turnSpeed = Math.PI/50;
	private int speed = 2;
	
	public PlayerCar() {

	}
	
	public double getForwardSpeed(Road road, double position) {
		return speed*Math.cos(getPlayerAngle()-road.getTanAngle(position));
	}
	
	public double getLateralSpeed(Road road, double position) {
		return speed*Math.sin(getPlayerAngle()-road.getTanAngle(position));
	}

	public double getPlayerAngle() {
		return playerAngle;
	}

	public void addPlayerAngle() {
		playerAngle += turnSpeed;
	}
	
	public void subtractPlayerAngle() {
		playerAngle -= turnSpeed;
	}
	
}





