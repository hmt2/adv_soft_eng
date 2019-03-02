/**
 * The MVC clock example from the MVC lecture.
 * This class sets up the Model, View and Controller.
 */

package main;

import controllers.DisplayController;
import model.CurrentQueue;
import views.DisplayGUI;

public class DisplayExample {
	public static void main(String[] args) {
		
		// ClockModel maintains the time and broadcasts changes
		CurrentQueue model = new CurrentQueue();

		// This view displays the time, updating whenever changed
		// AND incorporates the GUI to change the time
		DisplayGUI view = new DisplayGUI(model);

		// this controller responds when the time is changed in the view
		// it needs to know about the view and the model
		DisplayController controller = new DisplayController(view, model);

		// now all is displayed, wait for user to interact
	}
}