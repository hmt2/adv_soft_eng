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
import exceptions.IdNotContainedException;

public class CurrentQueue implements Subject {

	private Queue<Customer> queue;
	private Customer topQueue;
	private Menu menu;

	public CurrentQueue() {
		queue = new LinkedList<>(); 
		menu = new Menu();
	}

	public void add(Customer cust) {
		if(queue.isEmpty()) {
			topQueue = cust;
		}
		queue.add(cust);
	}

	public void removeTop(){
		if(!queue.isEmpty()) {
			Customer cust = queue.remove();
			topQueue = cust;
		}
	}

	public Customer getTopOfQueue(){
		return topQueue;
	}

	public boolean isEmpty(){
		return queue.isEmpty();
	}

	public String printQueue() {
		String text = "";
		int count = 0;
		if(!queue.isEmpty()) {
			for(Customer cust: queue) {
				text = text + "id: " + cust.getCustomerId() + "\n";
				count++;
				if(count > 7)
					return text;
			}
		}
		return text;
	}

	public String showCustomerBeingServed() {
		String custString = "";
		if(!queue.isEmpty()) {
			Customer cust = topQueue;
			custString =  custString + "id: " + cust.getCustomerId() + "\n"
					+ "items: ";
			ArrayList<String> itemIds = cust.getItemIds();
			for(String item: itemIds) {
				try {
					custString += menu.findItemId(item).getName() + "\n";
				} catch (IdNotContainedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			custString += "\n"
					+ "\n total before discount" + cust.getBillBeforeDiscount() + 
					"\n total after discount: " + cust.getBillAfterDiscount() + "\n";
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
