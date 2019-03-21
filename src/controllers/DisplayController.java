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
	/**
	 * model displays the waiting queue, which customers are being served and the collection queue
	 **/
	private DisplayGUI view; 
	/**
	 * model stores info about which customers are in the waiting queue, which customers are being served and 
	which customers are in the collection queue
	 **/
	private CurrentQueue model; 

	public DisplayController(DisplayGUI view, CurrentQueue model) {
		this.model = model;
		this.view = view;
	}

	/**
	 * The below methods control the model. The updateView method (which notifies observers) is only 
	 * called in certain methods. This is so the GUI is updated slowly enough that the wanted 
	 * information is displayed for long enough.
	 **/
	public void updateView(){				
		model.notifyObservers();
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
	}
	
	public void addCollectionQueue(Customer cust) {
		model.addCollectionQueue(cust);
	}
	
	public int getSizeCollectionQueue() {
		return model.getSizeCollectionQueue();
	}

	public void removeTopCollectionQueue(){
		model.removeTopCollectionQueue();
	}

	public void emptyCollectionQueue() {
		model.emptyCollectionQueue();
		updateView();
	}
}
