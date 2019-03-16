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

	// GUI to allow user to set the time
	private DisplayGUI view; 

	// display model stores the time
	private CurrentQueue model; 

	public DisplayController(DisplayGUI vieww, CurrentQueue model) {
		this.model = model;
		this.view = vieww;
		// specify the listener for the view
	}

	public Customer getTopOfWaitingQueue(){
		return model.getTopOfWaitingQueue();
	}
	
	public void setServerCustomer(int i, Customer cust) {
		model.setServerCustomer(i, cust); 
		updateView();
	}
	

	public void removeTopWaitingQueue(){
		model.removeTopWaitingQueue();
		updateView();
	}
	
	public void clearServer(int i) {
		model.clearServer(i);
	}

	public boolean isEmpty(){
		return model.isWaitingQueueEmpty();
	}

	public void addWaitingQueue(Customer cust) {
		model.addWaitingQueue(cust);
		updateView();
	}
	
	public void addFirstWaitingQueue(Customer cust) {
		model.addFirstWaitingQueue(cust);
		updateView();
		
	}

	public void updateView(){				
		model.notifyObservers();
	}
	
	public void addCollectionQueue(Customer cust) {
		model.addCollectionQueue(cust);
		updateView();
	}
	
	public int getSizeCollectionQueue() {
		return model.getSizeCollectionQueue();
	}

	public void removeTopCollectionQueue(){
		model.removeTopCollectionQueue();
		updateView();
	}


}
