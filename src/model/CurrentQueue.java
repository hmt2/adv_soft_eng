/**
 * The MVC clock example from the MVC lecture.
 * This class is the Model, which holds the program's state.
 */

package model;

import interfaces.Observer;
import interfaces.Subject;

import java.util.*;

import coffeShop.Customer;

public class CurrentQueue implements Subject {
	
	Queue<Customer> queue;
	
	//need queue info here
	public CurrentQueue() {
		 queue = new LinkedList<>(); 
	}
	
	public void add(Customer cust) {
		queue.add(cust);
	}
	
	public String showQueue() {
		if(!queue.isEmpty()){
			String queueString = queue.toString();
			return queueString;
		}
		else {
			return "queue is empty /n/r";
		}
	}
	
	public String showCustomerBeingServed() {
		if(!queue.isEmpty()) {
			Customer cust = queue.remove();
			String custString = cust.toString();
			return custString;
		}
		else {
			return "queue is empty /n/r";
		}
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
