
package model;

import interfaces.Observer;
import interfaces.Subject;

import java.util.*;

import coffeShop.Customer;
import coffeShop.Menu;

public class CurrentQueue implements Subject {

	/**
	 * Within the display model there are 2 queues which are the waitingQueue (represents customers waiting 
	 * to be served) and the collectionQueue (represents customers whose orders have been completed).
	 * 
	 *  Both waitingQueue and collectionQueue are Deque (Double-Ended Queue) so
	 *  it's possible to add Customers to the tail and head of the queue. This allows a customer to be given
	 *  priority by adding them to the head of waitingQueue.
	 **/
	private Deque<Customer> waitingQueue;
	private Deque<Customer> collectionQueue;
	
	//topQueue is used to keep track of the Customer which was just removed from the waitingQueue 
	private Customer topQueue;
	//serverCust is used to store what customer each server is currently serving 
	Customer[] serverCust = new Customer[4];
	
	private Menu menu;

	public CurrentQueue() {
		waitingQueue = new LinkedList<>(); 
		collectionQueue = new LinkedList<>();
		menu = new Menu();
	}

	//Adds a customer to the waitingQueue
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
	
	//Adds a customer to the collectionQueue
	public void addCollectionQueue(Customer cust) {
		collectionQueue.add(cust);
	}
	
	/** getSizeWaitingQueue : returns the size of waitingQueue
	 *  created for testing purposes
	 * 
	 * @return int
	 */
	public int getSizeWaitingQueue() {
		return waitingQueue.size();
	}
	
	//Returns the size of the collectionQueue
	public int getSizeCollectionQueue() {
		return collectionQueue.size();
	}
	
	//Removes the top element of the collectionQueue
	public void removeTopCollectionQueue(){
		if(!collectionQueue.isEmpty()) {
			collectionQueue.remove();
		}
	}
	
	//Assigns a customer to a server
	public void setServerCustomer(int i, Customer cust) {
		serverCust[i] = cust;
	}
	
	//Return the Customer being served by a specific server
	public Customer getServerCustomer(int i) {
		return serverCust[i];
	}
	
	//Empty the collectionQueue
	public void emptyCollectionQueue(){
		while(!collectionQueue.isEmpty()) {
			collectionQueue.remove(); 
		}
	}
	
	//Remove the top element of the waitingQueue
	public void removeTopWaitingQueue(){
		if(!waitingQueue.isEmpty()) {
			Customer cust = waitingQueue.remove();
			topQueue = cust;
		}
	}

	//return the value which is top of Queue
	public Customer getTopOfWaitingQueue(){
		return topQueue;
	}

	//Check if waitingQueue is empty
	public boolean isWaitingQueueEmpty(){
		return waitingQueue.isEmpty();
	}
	
	//Print respective queues
	public String printWaitingQueue() {
		return printQueue(waitingQueue);
	}
	
	public String printCollectionQueue() {
		return printQueue(collectionQueue);
	}
	
	//Remove customer from a specific server
	public void clearServer(int i) {
		serverCust[i] = null;
	}

	public String printQueue(Deque<Customer> queue) {
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

	//Get Customer info in a set format
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
	
	/** peekWaitingQueue : returns the first customer of WaitingQueue
	 *  created for testing purposes
	 * 
	 * @return Customer
	 */
	public Customer peekWaitingQueue() {
		return waitingQueue.peek();
	}
	
	/** peekCollectionQueue : returns the first customer of CollectionQueue
	 *  created for testing purposes
	 *  
	 * @return Customer
	 */
	public Customer peekCollectionQueue() {
		return collectionQueue.peek();
	}
	
	/** getWaitingQueue : returns the waitingQueue
	 * 
	 * @return Deque<Customer>
	 */
	public Deque<Customer> getWaitingQueue() {
		return waitingQueue;
	}
	
	/** getCollectionQueue : returns the collectionQueue
	 * 
	 * @return Deque<Customer>
	 */
	public Deque<Customer> getCollectionQueue() {
		return collectionQueue;
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
