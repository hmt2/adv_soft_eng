/**
 * The MVC clock example from the MVC lecture.
 * This class sets up the main GUI, which contains the Views.
 */

package views;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.Display;

public class DisplayGUI extends JFrame {
	// gui components
	private JTextField hours = new JTextField();
	private JTextField mins = new JTextField();
	private JButton updateButton = new JButton("Update");

	public DisplayGUI(Display model) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// add update panel at the top (doesn't need to know about the clock
		// object)
		add(BorderLayout.NORTH, createSetClockPanel());

		// the display classes DO need to know about the Clock object
		// (to register as observers and to get the data)
		// add analog display in the middle

		setSize(250, 300);
		setVisible(true);
	}

	public JPanel createSetClockPanel() {
		JPanel setClockPanel = new JPanel(new BorderLayout());

		JPanel panel = new JPanel(new GridLayout(2, 2));
		panel.add(new JLabel("New Hours (0 - 23)"));
		panel.add(hours);
		panel.add(new JLabel("New Mins"));
		panel.add(mins);
		setClockPanel.add(BorderLayout.CENTER, panel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(updateButton);

		setClockPanel.add(BorderLayout.SOUTH, buttonPanel);

		return setClockPanel;
	}

	// add listener to update button
	public void addSetListener(ActionListener al) {
		updateButton.addActionListener(al);
	}
}
