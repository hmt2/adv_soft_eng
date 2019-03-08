/**
 * The MVC clock example from the MVC lecture.
 * This class is the Model, which holds the program's state.
 */

package model;

import interfaces.Observer;
import interfaces.Subject;

import java.util.*;

public class CurrentQueue implements Subject {
	
	Queue<Integer> q = new LinkedList<>(); 
	
	//need queue info here
	public CurrentQueue() {
		
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
