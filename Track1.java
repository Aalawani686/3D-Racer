import java.awt.Point;
import java.awt.geom.Point2D;

public class Track1 extends Road{
	public Point2D.Double getPara(double position) {
//		if(position <= 200) {
//			return new Point2D.Double(0, position);
//		}
//		else if (position <= 400) {
//			return new Point2D.Double(300*Math.cos((position-200)*Math.PI/400)-300, 300*Math.sin((position-200)*Math.PI/400)+200);
//		}
//		else {
//			return new Point2D.Double(-position, 500);
//		}
		
		return new Point2D.Double(0, position);
//		
		
	}
	
	public Point2D.Double getParaDeriv(double position) {
//		if(position < 200) {
//			return new Point2D.Double(0, 1);
//		}
//		else if (position <= 400) {
//			return new Point2D.Double(-300*Math.sin((position-200)*Math.PI/400)*Math.PI/400, 300*Math.cos((position-200)*Math.PI/400)*Math.PI/400);
//		}
//		else {
//			return new Point2D.Double(1, 0);
//		}

		return new Point2D.Double(0, 1);
		//return new Point(1, 1);
//		return new Point((int)(-300*Math.sin(position*Math.PI/180)*Math.PI/180), (int)(300*Math.cos(position*Math.PI/180)*Math.PI/180));
//		return new Point(1, (int)(300*Math.sin(s*Math.PI/180)*Math.PI/180));
		

		
		
	}
}




