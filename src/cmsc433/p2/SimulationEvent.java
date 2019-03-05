package cmsc433.p2;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents each of the salient events that occur during the
 * simulation.  These events are created by the public factory methods
 * provided by the class.  DO NOT CHANGE THIS CLASS.
 */
public class SimulationEvent {
    public enum EventType {
    	/* General events */
    	SimulationStarting,
    	SimulationEnded,
    	/* Customer events */
	CustomerStarting,
    	CustomerEnteredCoffeeShop,
    	CustomerPlacedOrder,
    	CustomerReceivedOrder,
    	CustomerLeavingCoffeeShop,
    	/* Cook Events */
    	CookStarting,
    	CookReceivedOrder,
    	CookStartedFood,
	CookFinishedFood,
    	CookCompletedOrder,
    	CookEnding,
 			
    };
    public final EventType event;
    /* Not all of these fields are relevant for every event; 
       see factory methods below */
    public final Cook cook;
    public final Customer customer;
    public final Item food;
    public final List<Item> orderFood;
    public final int customerId;
    public final int[] simParams;

    private SimulationEvent(EventType event, 
			    Cook cook,
			    Customer customer,
			    Item currFood,
			    List<Item> order,
			    int customerId,
			    int[] simParams) {
	this.event = event;
	this.cook = cook;
	this.customer = customer;

	this.food = currFood;
	this.orderFood = order;
	this.customerId = customerId;
	this.simParams = simParams;
    }

    /* Factory methods */

    /* Simulation events */
    public static SimulationEvent startSimulation(int numCustomers,
					   int numCooks,
					   int numTables,
					   int capacity) {
	int[] params = new int[4];
	params[0] = numCustomers;
	params[1] = numCooks;
	params[2] = numTables;
	params[3] = capacity;
	return new SimulationEvent(EventType.SimulationStarting,
				   null,null,null,null,0,
				   params);
    }

    public static SimulationEvent endSimulation() {
	return new SimulationEvent(EventType.SimulationEnded,
				   null,null,null,null,0,null);
    }

    /* Customer events */
    public static SimulationEvent customerStarting(Customer customer) {
	return new SimulationEvent(EventType.CustomerStarting,
				   null,
				   customer,
				   null,null,0,null);
    }

    public static SimulationEvent customerEnteredCoffeeShop(Customer customer) {
	return new SimulationEvent(EventType.CustomerEnteredCoffeeShop,
				   null,
				   customer,
				   null,null,0,null);
    }

    public static SimulationEvent customerPlacedOrder(Customer customer,
					    List<Item> order,
					    int customerId) {
	return new SimulationEvent(EventType.CustomerPlacedOrder,
				   null,
				   customer,
				  null,
				   order, customerId,
				   null);
    }

    public static SimulationEvent customerReceivedOrder(Customer customer,
					    List<Item> order,
					    int customerId) {
	return new SimulationEvent(EventType.CustomerReceivedOrder,
				   null,
				   customer,
				    null,
				   order, customerId,
				   null);
    }

    public static SimulationEvent customerLeavingCoffeeShop(Customer customer) {
	return new SimulationEvent(EventType.CustomerLeavingCoffeeShop,
				   null,
				   customer,
				   null, null, 0, null);
    }

    /* Cook events */
    public static SimulationEvent cookStarting(Cook cook) {
	return new SimulationEvent(EventType.CookStarting,
				   cook,
				   null,null,null,0,null);
    }

    public static SimulationEvent cookReceivedOrder(Cook cook,
					     List<Item> order,
					     int customerId) {
	return new SimulationEvent(EventType.CookReceivedOrder,
				   cook,
				   null, null,
				   order, customerId,
				   null);
    }

    public static SimulationEvent cookStartedFood(Cook cook, Item currFood,
					   int customerId) {
	return new SimulationEvent(EventType.CookStartedFood,
				   cook,
				   null,
				   currFood,
				   null,
				   customerId,
				   null);
    }

    public static SimulationEvent cookFinishedFood(Cook cook, Item food,
					   int customerId) {
	return new SimulationEvent(EventType.CookFinishedFood,
				   cook,
				   null,
				   food,
				   null,
				   customerId,
				   null);
    }

    public static SimulationEvent cookCompletedOrder(
    						Cook cook, int customerId) {
	return new SimulationEvent(EventType.CookCompletedOrder,
				   cook,
				   null,null,null,
				   customerId,
				   null);
    }

    public static SimulationEvent cookEnding(Cook cook) {
	return new SimulationEvent(EventType.CookEnding, cook,
				   null,null,null,0,null);
    }



    public String toString() {
	switch (event) {
	/* Simulation events */
    	case SimulationStarting:
	    int numCustomers = simParams[0];
	    int numCooks = simParams[1];
	    int numTables = simParams[2];
	    int capacity = simParams[3];
	    return "Starting simulation: "+numCustomers+" customers; "+
		numCooks+" cooks; "+numTables+" tables; "+
		"machine capacity "+capacity+".";
	    
	case SimulationEnded:
	    return "Simulation ended.";

    	/* Customer events */
	case CustomerStarting:
	    return customer + " going to coffee shop.";

	case CustomerEnteredCoffeeShop:
	    return customer + " entered coffee shop.";

	case CustomerPlacedOrder:
	    return customer + " with customer id " +  customerId +" placing order " + orderFood; 
	    
	case CustomerReceivedOrder:
	    return customer  + " with customer id " + customerId + " received order "+ orderFood;

	case CustomerLeavingCoffeeShop:
	    return customer + " leaving coffee shop.";

    	/* Cook Events */
	case CookStarting:
	    return "Server  " + cook.getId()+ " reporting for work.";

	case CookReceivedOrder:

	    return "Server " + cook.getId()+ " starting order for customer"+ customerId + " " + orderFood;

	case CookStartedFood:
	    return "Server " +  cook.getId() +" cooking " + food.getName() + " for customer " + customerId;

	case CookFinishedFood:
	    return "Server " + cook.getId()+ " finished " + food.getName() + " for customer " + customerId;

	case CookCompletedOrder:
	    return "Server " + cook.getId()+ " completed order of customer "+customerId;

	case CookEnding:
	    return "Server " + cook.getId()+ " going home for the night.";


	default:
	    throw new Error("Illegal event; can't be stringified");
	}
    }
}
