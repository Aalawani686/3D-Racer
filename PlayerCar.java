
public class PlayerCar {
	private double playerAngle = 0;
	
	private double angleToRoad = 0;
	
	private double turnSpeed = Math.PI/100;
	
	private double speed = 0;
	
	private double maxSpeed = 3;
	
	public PlayerCar() {

	}
	
	public void setAngleToRoad(Road road, double position) {
		angleToRoad = playerAngle-road.getTanAngle(position);
		if (angleToRoad > Math.PI) {
			angleToRoad -= Math.PI*2;
		}
		if (angleToRoad < -Math.PI) {
			angleToRoad += Math.PI*2;
		}
	}
	
	public double getAngleToRoad() {
		return angleToRoad;
	}
	
	public double getForwardSpeed() {
		return speed*Math.cos(angleToRoad);
	}
	
	public double getLateralSpeed() {
		return speed*Math.sin(angleToRoad);
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



