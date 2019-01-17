

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TextDemo extends JPanel implements ActionListener {
	protected JTextField textField;
	protected JTextArea textArea;
	private final static String newline = "\n";
	private static boolean isMany = false;
	public TextDemo() {
		super(new GridBagLayout());

		textField = new JTextField(20);
		textField.addActionListener(this);

		textArea = new JTextArea(5, 20);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);

		// Add Components to this panel.
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;

		c.fill = GridBagConstraints.HORIZONTAL;
		add(textField, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scrollPane, c);
	}

	public void actionPerformed(ActionEvent evt) {
		String text;
		System.out.println("hi");
		if(textField.getText().length() >= 8) {
			text = textField.getText().substring(0, 8);
		}else {
			text = textField.getText();
		}
		textArea.append(text + newline);
		textField.selectAll();
		// Make sure the new text is visible, even if there
		// was a selection in the text area.
		textArea.setCaretPosition(textArea.getDocument().getLength());
		if (textArea.getDocument().getLength() > 0) {
			Driver.ready2go = true;
		}
		Driver d = new Driver();
		d.setTest(text);
		d.setVari(isMany);

		//      

		/**
		 * Create the GUI and show it. For thread safety, this method should be invoked
		 * from the event dispatch thread.
		 */
	}

	static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Please Enter Your Username");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add contents to the window.

		// Make the center component big, since that's the
		// typical usage of BorderLayout.

		frame.add(new TextDemo(), BorderLayout.LINE_START);




		// Display the window.
		frame.pack();frame.setVisible(true);

	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	public boolean setisMany(boolean multi) {
		isMany= multi;
		return isMany;
	}

}
