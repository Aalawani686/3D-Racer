import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

public class Credits {
	public Credits() {
		JFrame f = new JFrame("hello");
		JLabel Sahith = new JLabel("Sahith: Road Creation + Mechanics/Math");
		JLabel Rudy = new JLabel("Rudy: GUI");
		JLabel Andy = new JLabel("Andy: Networking + Mechanics/Math");
		JPanel p = new JPanel();
		p.add(Sahith);
		p.add(Rudy);
		p.add(Andy);

		f.add(p);
		f.setTitle("Driving Game");
		f.setSize(200, 100);
		f.setBackground(Color.BLACK);
		// setups icon image

		f.setResizable(false);

//		f.add(this);


		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);
	}

	public static void main(String[] args) {

		Credits c = new Credits();
	}

}
