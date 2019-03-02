/**
 * The MVC clock example from the MVC lecture.
 * This class is the Controller, which responds to user interaction.
 */

package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import model.Display;
import views.DisplayGUI;


public class DisplayController {

	private DisplayGUI view; // GUI to allow user to set the time

	private Display clock; // clock model stores the time

	public DisplayController(DisplayGUI view, Display clock) {
		this.clock = clock;
		this.view = view;
		// specify the listener for the view
		view.addSetListener(new SetListener());
	}

	// inner class SetListener responds when user sets the time
	public class SetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}

}
