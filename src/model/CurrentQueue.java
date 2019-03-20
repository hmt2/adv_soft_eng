/**
 * The MVC clock example from the MVC lecture.
 * This class is the Model, which holds the program's state.
 */

package model;

import interfaces.Observer;
import interfaces.Subject;

import java.util.*;

import coffeShop.Customer;

import coffeShop.Menu;

public class CurrentQueue implements Subject {

	/** waitingQueue and collectionQueue are Deque (Double-Ended Queue) 
	 *  a Deque allows us to add Customers at the tail and at the head of the queue
	 *  Adding a customer at the head is necessary for adding an order as a priority
	 * 
	 */
	private Deque<Customer> waitingQueue;
	private Deque<Customer> collectionQueue;
	private Customer topQueue;
	Customer[] serverCust = new Customer[4];
	
	private Menu menu;

	public CurrentQueue() {
		waitingQueue = new LinkedList<>(); 
		collectionQueue = new LinkedList<>();
		menu = new Menu();
	}

	public void addWaitingQueue(Customer cust) {
		if(waitingQueue.isEmpty()) {
			topQueue = cust;
		}
		waitingQueue.add(cust);
	}
	
	/** addFirstWaitingQueue : adds a customer in priority at the head of the waitingQueue
	 * 
	 * @param cust
	 */
	public void addFirstWaitingQueue(Customer cust) {
		waitingQueue.addFirst(cust);
	}
	
	
	public void addCollectionQueue(Customer cust) {
		collectionQueue.add(cust);
	}
	
	public int getSizeCollectionQueue() {
		return collectionQueue.size();
	}
	
	public void removeTopCollectionQueue(){
		if(!collectionQueue.isEmpty()) {
			collectionQueue.remove();
		}
	}
	
	public void setServerCustomer(int i, Customer cust) {
		serverCust[i] = cust;
	}
	
	public Customer getServerCustomer(int i) {
		return serverCust[i];
	}
	
	

	public void removeTopWaitingQueue(){
		if(!waitingQueue.isEmpty()) {
			Customer cust = waitingQueue.remove();
			topQueue = cust;
		}
	}

	public Customer getTopOfWaitingQueue(){
		return topQueue;
	}

	public boolean isWaitingQueueEmpty(){
		return waitingQueue.isEmpty();
	}
	
	public String printWaitingQueue() {
		return printQueue(waitingQueue);
	}
	
	public String printCollectionQueue() {
		return printQueue(collectionQueue);
	}
	
	public void clearServer(int i) {
		serverCust[i] = null;
	}

	public String printQueue(Queue<Customer> queue) {
		String text = "";
		int count = 0;
		if(!queue.isEmpty()) {
			for(Customer cust: queue) {
				if(cust != null) {
					text = text + "Customer id: " + cust.getCustomerId() + "\n";
					count++;
					if(count > 7)
						return text;
				}
			}
		}
		return text;
	}

	public String showCustomer(Customer custPres) {
		String custString = "";
		if(!waitingQueue.isEmpty()) {
			Customer cust = custPres;
			if(cust != null) {
				custString =  custString + "id: " + cust.getCustomerId() + "\n"
						+ "items: ";
				ArrayList<String> itemIds = cust.getItemIds();
				for(String item: itemIds) {
						try {
							custString += menu.findItemId(item).getName() + "\n";
						} catch (exceptions.IdNotContainedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}
				custString += "\n"
						+ "\n total before discount: £" + cust.getBillBeforeDiscount() + 
						"\n total after discount: £" + cust.getBillAfterDiscount() + "\n";
			}
			
		}
		return custString;
	}

	// OBSERVER PATTERN
	// SUBJECT must be able to register, remove and notify observers
	// list to hold any observers
	private List<Observer> registeredObservers = new LinkedList<Observer>();

	// methods to register, remove and notify observers
	public void registerObserver(Observer obs) {
		registeredObservers.add(obs);
	}

	public void removeObserver(Observer obs) {
		registeredObservers.remove(obs);
	}

	public void notifyObservers() {

		for (Observer obs : registeredObservers)
			obs.update();
	}
}
