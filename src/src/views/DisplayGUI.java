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
		add(new DisplayWaitingQueue(model), BorderLayout.NORTH);
		add(new DisplayCollectionQueue(model), BorderLayout.SOUTH);
		// add server display at the bottom
		add(BorderLayout.CENTER, new DisplayServer(model));

		setSize(1500, 1000);
		setVisible(true);
	}

	// add listener to update button
	public void addSetListener(ActionListener al) {

	}
}
