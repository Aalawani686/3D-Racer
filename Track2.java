/**
 * Track2.java
 * Contains the Polar coordinates and derivatives of a track

 * @author Sahith Konakalla
 * @author Aniruddh Khanwale
 * @author Aniruddha Alawani
 *
 * @date January 22, 2019
 *
 */

 import java.awt.geom.Point2D;

public class Track2 extends Road{

	private double endPosition = 4600;

	public double getEndPosition() {
		return endPosition;
	}

	public Point2D.Double getPara(double position) {
		if(position <= 800) {
			return new Point2D.Double(0, position);
		}
		else if (position <= 2056) {
			return new Point2D.Double(400*Math.cos((position-800)/400)-400, 400*Math.sin((position-800)/400)+800);
		}
		else if (position <= 2370) {
			return new Point2D.Double(100*Math.cos((position-2056)/100)-900, 100*Math.sin(-(position-2056)/100)+800);
		}
		else if (position <= 3312) {
			return new Point2D.Double(200*Math.cos((position-2370)/200)-1200, 200*Math.sin((position-2370)/200)+800);
		}
		else if (position <= 3912) {
			return new Point2D.Double(position-3312-1200, 600);
		}
		else if (position <= 4226) {
			return new Point2D.Double(100*Math.cos(-(position-3912)/100 + Math.PI/2)-600, 100*Math.sin((position-3912)/100 + Math.PI/2)+500);
		}
		else if (position <= 4726) {
			return new Point2D.Double(-position+4226-600, 400);
		}
		else if (position <= 4961) {
			return new Point2D.Double(300*Math.cos((position-4726)/150+Math.PI/2)-1100, 300*Math.sin((position-4726)/150+Math.PI/2)+100);
		}
		else if (position <= 5275) {
			return new Point2D.Double(200*Math.cos((position-4961)/200+Math.PI)-1200, 200*Math.sin((position-4961)/200+Math.PI)+100);
		}
		else if (position <= 5475) {
			return new Point2D.Double(position-5275-1200, -100);
		}
		else if (position <= 6275) {
			return new Point2D.Double(position-5475-1000, -100*Math.cos((position-5475)*Math.PI/400));
		}
		else if (position <= 6375) {
			return new Point2D.Double(-position+6275-100, -100);
		}
		else if (position <= 6532) {
			return new Point2D.Double(100*Math.cos((position-6375)/100-Math.PI/2)-100, 100*Math.sin((position-6375)/200-Math.PI/2));
		}
		else {
			return new Point2D.Double(0, 0);
		}
	}

	public Point2D.Double getParaDeriv(double position) {
		if(position <= 800) {
			return new Point2D.Double(0, 1);
		}
		else if (position <= 2056) {
			return new Point2D.Double(400*-Math.sin((position-800)/400), 400*Math.cos((position-800)/400));
		}
		else if (position <= 2370) {
			return new Point2D.Double(100*-Math.sin((position-2056)/100), -100*Math.cos(-(position-2056)/100));
		}
		else if (position <= 3312) {
			return new Point2D.Double(200*-Math.sin((position-2370)/200), 200*Math.cos((position-2370)/200));
		}
		else if (position <= 3912) {
			return new Point2D.Double(1, 0);
		}
		else if (position <= 4226) {
			return new Point2D.Double(100*Math.sin(-(position-3912)/100 + Math.PI/2), 100*Math.cos((position-3912)/100 + Math.PI/2));
		}
		else if (position <= 4726) {
			return new Point2D.Double(-1,0);
		}
		else if (position <= 4961) {
			return new Point2D.Double(300*-Math.sin((position-4726)/150+Math.PI/2), 300*Math.cos((position-4726)/150+Math.PI/2));
		}
		else if (position <= 5275) {
			return new Point2D.Double(200*-Math.sin((position-4961)/200+Math.PI), 200*Math.cos((position-4961)/200+Math.PI));
		}
		else if (position <= 5475) {
			return new Point2D.Double(1, 0);
		}
		else if (position <= 6275) {
			return new Point2D.Double(1, -100*-Math.sin((position-5475)*Math.PI/400)*Math.PI/400);
		}
		else if (position <= 6375) {
			return new Point2D.Double(-1, 0);
		}
		else if (position <= 6532) {
			return new Point2D.Double(100*-Math.sin((position-6375)/100-Math.PI/2), 100*Math.cos((position-6375)/200-Math.PI/2));
		}
		else {
			return new Point2D.Double(0, 0);
		}
	}

	public boolean checkIfLap(double position) {
		return position >= 6532;
	}
}
