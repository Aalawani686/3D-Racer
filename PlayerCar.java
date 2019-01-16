import java.awt.Point;

public class PlayerCar {
	private double playerAngle = 0;
	
	private double turnSpeed = Math.PI/50;
	private double speed = 0;
	
	private double maxSpeed = 3;
	
	public PlayerCar() {

	}
	
	public double getForwardSpeed(Road road, double position) {
		return speed*Math.cos(getPlayerAngle()-road.getTanAngle(position));
	}
	
	public double getLateralSpeed(Road road, double position) {
		return speed*Math.sin(getPlayerAngle()-road.getTanAngle(position))*0.1;
	}

	public double getPlayerAngle() {
		return playerAngle;
	}

	public void addPlayerAngle() {
		playerAngle += turnSpeed;
		if(playerAngle > Math.PI) {
			playerAngle = -Math.PI;
		}
	}
	
	public void subtractPlayerAngle() {
		playerAngle -= turnSpeed;
		if(playerAngle < -Math.PI) {
			playerAngle = Math.PI;
		}
	}
	public void accelerate() {
		if (speed<maxSpeed) {
			speed += 0.05;
		}
		if (speed>maxSpeed) {
			speed -= 0.05;
			
		}
	}
	public void deccelerate() {
		if (speed>0) {
			speed -= 0.05;
		}
		if (speed < 0) {
			speed = 0;
		}
	}
	public void onGrass() {
		maxSpeed = 0.2;
	}
	public void onRoad() {
		maxSpeed = 3;
	}

	public double getSpeed() {
		return speed;
	}
}
