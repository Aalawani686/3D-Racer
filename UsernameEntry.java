/**
 * UsernameEntry.java
 * Derived from an Oracle Example, takes in the user's username.
 * If the game mode is admin, it confirms that the admin username is being used.
 *
 * @author Aniruddh Khanwale
 *
 *
 * @date Jan 22, 2019
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UsernameEntry extends JPanel implements ActionListener {
	protected JTextField textField;
	protected JTextArea textArea;
	private final static String newline = "\n";
	private static String gameType;
	/*
	 * Constructor
	 * Takes in no parameters, creates the field within which users will type name and where they will see it
	 */
	public UsernameEntry() {
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
	/*
	 * This is the update method. Checks to see if user has entered a name.
	 * If the game mode is admin, verifies that the correct passkey is being used
	 */
	public void actionPerformed(ActionEvent evt) {
		String text;
		// sets a limit on the character length, and initializes the text string
		if(textField.getText().length() >= 8) {
			text = textField.getText().substring(0, 8);
		}else {
			text = textField.getText();
		}
		textArea.append(text + newline); // adds the text to viewbox
		textField.selectAll();
		// Make sure the new text is visible, even if there
		// was a selection in the text area.
		textArea.setCaretPosition(textArea.getDocument().getLength());

		//checks game type and initializes driver based on the information
		if(gameType.equals("single")) {
			Driver.setGameType("single");
			Driver d = new Driver();
			d.setTest(text);
		}else if(gameType.equals("multi")) {
			Driver.setGameType("multi");
			Driver d = new Driver();
			d.setTest(text);
		}else if(gameType.equals("admin") && (text.equalsIgnoreCase("anidude2") || text.equalsIgnoreCase("chaosrud") || text.equalsIgnoreCase("Canadian"))) {
			Driver.setGameType("admin");
			Driver d = new Driver();
			d.setTest(text);
		}else {
			 textArea.append("Sorry, try again!" + newline);
		}



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

		frame.add(new UsernameEntry(), BorderLayout.LINE_START);




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
	public String setGameType(String txt) {
		gameType= txt;
		return gameType;
	}
	public String getGameType() {
		return gameType;
	}
}
