/**
 * The MVC clock example from the MVC lecture.
 * This class sets up the main GUI, which contains the Views.
 */

package views;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.CurrentQueue;

public class DisplayGUI extends JFrame {

	public DisplayGUI(CurrentQueue model) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		// add queue display in the middle
		add(new DisplayQueue(model), BorderLayout.NORTH);
		// add server display at the bottom
		add(BorderLayout.CENTER, new DisplayServer(model));
				
		setSize(600, 600);
		setVisible(true);
	}

	// add listener to update button
	public void addSetListener(ActionListener al) {
		
	}
}
