import java.awt.Point;
import java.awt.geom.Point2D;

public abstract class Road {
	
	private double shiftAngle = 0;
	
	private double endPosition = 10;
	
	public abstract double getEndPosition();
	
	public abstract Point2D.Double getPara(double position);
	
	public abstract Point2D.Double getParaDeriv(double position);
	
	public abstract boolean checkIfLap(double position);
	
	public double calculateShiftAngle(Point2D.Double a, Point2D.Double b, double position) {
		shiftAngle = Math.atan2(a.x-b.x, a.y-b.y)-this.getTanAngle(position);
		if (shiftAngle > Math.PI) {
			shiftAngle -= Math.PI*2;
		}
		if (shiftAngle < -Math.PI) {
			shiftAngle += Math.PI*2;
			
		}
		return shiftAngle;
	}
	
	public double getShiftAngle() {
		return shiftAngle;
	}
	
	public double getShift(Point2D.Double a, Point2D.Double b, double position) {
		
		return a.distance(b)*Math.sin(this.calculateShiftAngle(a, b, position));
	}
	
	public double getTanAngle(double position) {
		
		return Math.atan2(this.getParaDeriv(position).x, this.getParaDeriv(position).y);
	}
	
	
}



