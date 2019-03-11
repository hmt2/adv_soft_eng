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
	}
	
	public Customer getTopOfQueue(){
		return model.getTopOfQueue();
	}
	
	public void removeTop(){
		model.removeTop();
		updateView();
	}
	
	public boolean isEmpty(){
		return model.isEmpty();
	}
	
	public void add(Customer cust) {
		model.add(cust);
		updateView();
	}
	
	public void updateView(){				
	    model.notifyObservers();
	}
	
}
