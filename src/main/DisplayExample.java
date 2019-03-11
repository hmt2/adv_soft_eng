/**
 * The MVC clock example from the MVC lecture.
 * This class sets up the Model, View and Controller.
 */

package main;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import coffeShop.Customer;
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
		
		ArrayList<String> itemIds = new ArrayList<String>();
	    itemIds.add("HOT001");
	    itemIds.add("HOT003");
	    itemIds.add("HOT005");
		Customer cust1 = new Customer(101, 2, 2, itemIds);
		Customer cust2 = new Customer(102, 3, 2, itemIds);
		
		controller.add(cust1);
		controller.add(cust2);
		controller.updateView();
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		controller.updateView();
		
		// now all is displayed, wait for user to interact
	}
}