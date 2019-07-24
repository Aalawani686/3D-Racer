/**
 * Track1.java
 * Contains the Polar coordinates and derivatives of a track

 * @author Sahith Konakalla
 * @author Aniruddh Khanwale
 * @author Aniruddha Alawani
 *
 * @date January 22, 2019
 *
 */

import java.awt.geom.Point2D;

public class Track1 extends Road{

	private double endPosition = 4600;

	public double getEndPosition() {
		return endPosition;
	}

	public Point2D.Double getPara(double position) {
		if(position <= 200) {
			return new Point2D.Double(0, position);
		}
		else if (position <= 400) {
			return new Point2D.Double(300*Math.cos((position-200)*Math.PI/400)-300, 300*Math.sin((position-200)*Math.PI/400)+200);
		}
		else if (position <= 800) {
			return new Point2D.Double(300*Math.cos(-(position-400)*Math.PI/400-Math.PI/2)-300, 300*Math.sin(-(position-400)*Math.PI/400-Math.PI/2)+800);
		}
		else if (position <= 1000) {
			return new Point2D.Double(300*Math.cos((position-800)*Math.PI/400-Math.PI/2)-300, 300*Math.sin((position-800)*Math.PI/400-Math.PI/2)+1400);
		}
		else if (position <= 1200) {
			return new Point2D.Double(0, position+400);
		}
		else if (position <= 1400) {
			return new Point2D.Double(300*Math.cos((position-1200)*Math.PI/400)-300, 300*Math.sin((position-1200)*Math.PI/400)+1600);
		}
		else if (position <= 1900) {
			return new Point2D.Double(-position+1100, 1900);
		}
		else if (position <= 2100) {
			return new Point2D.Double(300*Math.cos((position-1900)*Math.PI/400+Math.PI/2)-800, 300*Math.sin((position-1900)*Math.PI/400+Math.PI/2)+1600);
		}
		else if (position <= 3700) {
			return new Point2D.Double(-1100, -position+1600+2100);
		}
		else if (position <= 3900) {
			return new Point2D.Double(300*Math.cos((position-3700)*Math.PI/400+Math.PI)-800, 300*Math.sin((position-3700)*Math.PI/400+Math.PI));
		}
		else if (position <= 4400) {
			return new Point2D.Double(position-3900-800, -300);
		}
		else if (position <= 4600) {
			return new Point2D.Double(300*Math.cos((position-4400)*Math.PI/400-Math.PI/2)-300, 300*Math.sin((position-4400)*Math.PI/400-Math.PI/2));
		}
		else {
			return new Point2D.Double(0, 0);
		}
	}

	public Point2D.Double getParaDeriv(double position) {
		if(position <= 200) {
			return new Point2D.Double(0, 1);
		}
		else if (position <= 400) {
			return new Point2D.Double(300*-Math.sin((position-200)*Math.PI/400), 300*Math.cos((position-200)*Math.PI/400));
		}
		else if (position <= 800) {
			return new Point2D.Double(300*Math.sin(-(position-400)*Math.PI/400-Math.PI/2), 300*-Math.cos(-(position-400)*Math.PI/400-Math.PI/2));
		}
		else if (position <= 1000) {
			return new Point2D.Double(300*-Math.sin((position-800)*Math.PI/400-Math.PI/2), 300*Math.cos((position-800)*Math.PI/400-Math.PI/2));
		}
		else if (position <= 1200) {
			return new Point2D.Double(0, 1);
		}
		else if (position <= 1400) {
			return new Point2D.Double(300*-Math.sin((position-1200)*Math.PI/400), 300*Math.cos((position-1200)*Math.PI/400));
		}
		else if (position <= 1900) {
			return new Point2D.Double(-1, 0);
		}
		else if (position <= 2100) {
			return new Point2D.Double(300*-Math.sin((position-1900)*Math.PI/400+Math.PI/2), 300*Math.cos((position-1900)*Math.PI/400+Math.PI/2));
		}
		else if (position <= 3700) {
			return new Point2D.Double(0, -1);
		}
		else if (position <= 3900) {
			return new Point2D.Double(300*-Math.sin((position-3700)*Math.PI/400+Math.PI), 300*Math.cos((position-3700)*Math.PI/400+Math.PI));
		}
		else if (position <= 4400) {
			return new Point2D.Double(1, 0);
		}
		else if (position <= 4600) {
			return new Point2D.Double(300*-Math.sin((position-4400)*Math.PI/400-Math.PI/2), 300*Math.cos((position-4400)*Math.PI/400-Math.PI/2));
		}
		else {
			return new Point2D.Double(0, 0);
		}
	}

	public boolean checkIfLap(double position) {
		return position >= 4600;
	}
}
