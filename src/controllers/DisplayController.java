/**
 * The MVC display example from the MVC lecture.
 * This class is the Controller, which responds to user interaction.
 */

package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import coffeShop.Customer;
import model.CurrentQueue;
import views.DisplayGUI;


public class DisplayController {

	private DisplayGUI view; // GUI to allow user to set the time

	private CurrentQueue model; // display model stores the time

	public DisplayController(DisplayGUI vieww, CurrentQueue model) {
		this.model = model;
		this.view = vieww;
		// specify the listener for the view
		view.addSetListener(new SetListener());
	}

	//may not need as user input isn't updating gui....
	// inner class SetListener responds when change
	public class SetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}
	
	public Customer getTopOfQueue(){
		return model.getTopOfQueue();
	}
	
	public void removeTop(){
		model.removeTop();
	}
	
	public void add(Customer cust) {
		model.add(cust);
	}
	
	public void updateView(){				
	    model.notifyObservers();
	}
	
}
