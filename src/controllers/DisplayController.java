/**
 * The MVC display example from the MVC lecture.
 * This class is the Controller, which responds to user interaction.
 */

package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Deque;
import java.util.function.BooleanSupplier;

import coffeShop.Customer;
import model.CurrentQueue;
import views.DisplayGUI;


public class DisplayController {

	private DisplayGUI view; 

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

	public void updateView(){				
		model.notifyObservers();
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
		model.notifyObservers();
		
	}
	
	/** getServerCustomer
	 *  created for testing purposes
	 * 
	 * @param i
	 * @return Customer
	 */
	public Customer getServerCustomer(int i) {
		return model.getServerCustomer(i);
	}

	/** peekWaitingQueue : returns the first customer of WaitingQueue
	 *  created for testing purposes
	 * 
	 * @return Customer
	 */
	public Customer peekWaitingQueue() {
		return model.peekWaitingQueue();
	}
	
	/** peekCollectionQueue : returns the first customer of CollectionQueue
	 *  created for testing purposes
	 *  
	 * @return Customer
	 */
	public Customer peekCollectionQueue() {
		return model.peekCollectionQueue();
	}
	
	/** getWaitingQueue : returns the waitingQueue
	 * 
	 * @return Deque<Customer>
	 */
	public Deque<Customer> getWaitingQueue() {
		return model.getWaitingQueue();
	}
	
	/** getCollectionQueue : returns the collectionQueue
	 * 
	 * @return Deque<Customer>
	 */
	public Deque<Customer> getCollectionQueue() {
		return model.getCollectionQueue();
	}

	/** isWaitingQueueEmpty : checks if the waitingQueue is empty
	 *  created for testing purposes
	 * 
	 * @return Boolean
	 */
	public Boolean isWaitingQueueEmpty() {
		return model.isWaitingQueueEmpty();
	}

	/** getSizeWaitingQueue : gets the size of the waitingQueue
	 *  created for testing purposes
	 * 
	 * @return int
	 */
	public int getSizeWaitingQueue() {
		return model.getSizeWaitingQueue();
	}

}
