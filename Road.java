import java.awt.Point;
import java.awt.geom.Point2D;

public abstract class Road {
	
	public abstract Point2D.Double getPara(double position);
	
	public abstract Point2D.Double getParaDeriv(double position);
	
	public double getShift(Point2D.Double a, Point2D.Double b, double position) {
		//System.out.print(Math.atan2(a.x-b.x, a.y-b.y)-tanAng + " ");
//		return a.distance(b)*Math.sin(Math.atan2(a.x-b.x, a.y-b.y)-tanAng);
		//if (Math.cos(Math.atan2(a.x-b.x, a.y-b.y)-tanAng) >= 0) {
		return a.distance(b)*Math.sin(Math.atan2(a.x-b.x, a.y-b.y)-this.getTanAngle(position));
		//}
		//return 1000000;
	}
	
	public double getTanAngle(double position) {
		return Math.atan2(this.getParaDeriv(position).x, this.getParaDeriv(position).y);
	}
}





