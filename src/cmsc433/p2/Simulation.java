package cmsc433.p2;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import cmsc433.p2.*;

/**
 * Simulation is the main class used to run the simulation.  You may
 * add any fields (static or instance) or any methods you wish.
 */
public class Simulation {
	// List to track simulation events during simulation
	public static List<SimulationEvent> events;  
	
	public static Queue<Customer> orderList = new LinkedList<Customer>();
	public static Queue<Customer> currCapacity = new LinkedList<Customer>();
	public static Map<Customer, Boolean> completedOrder = new HashMap<Customer,Boolean>();
	private Map<String, Integer> currentOrder = new LinkedHashMap<String, Integer>();
	public static Menu menu;
	static AllOrders allorders;
	private static float totalBeforeDiscount = 0;
	private static float totalAfterDiscount = 0;
	static CustomerList customerList;
	static DiscountCheck discountCheck;
	static AllDiscounts allDiscounts;
    static boolean isStudentDiscount;



	/**
	 * Used by other classes in the simulation to log events
	 * @param event
	 */
	public static void addPreviousOrders() throws DuplicateIDException, IdNotContainedException{
		  TreeMap<Integer,ArrayList<String>> cust = allorders.loadOrders();
	      Set<Integer> keys = cust.keySet();
			for(Integer key: keys){
				float totalBeforeDiscount = discountCheck.calcBillBeforeDiscount(cust.get(key));
				float totalAfterDiscount = totalBeforeDiscount;
				Discount ds = discountCheck.getDiscount(cust.get(key));
				if(ds != null)
					totalAfterDiscount = (float) discountCheck.calcAfterDiscount(cust.get(key),isStudentDiscount); //assume for the previous cases student is false
				try {
					customerList.addCustomer(cust.get(key), totalBeforeDiscount, totalAfterDiscount);
				} catch (DuplicateIDException | IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					continue; //continue to process the next customer

			  }
			}

	  }
	public static void logEvent(SimulationEvent event) {
		events.add(event);
		System.out.println(event);
	}
	

	/**
	 * 	Function responsible for performing the simulation. Returns a List of 
	 *  SimulationEvent objects, constructed any way you see fit. This List will
	 *  be validated by a call to Validate.validateSimulation. This method is
	 *  called from Simulation.main(). We should be able to test your code by 
	 *  only calling runSimulation.
	 *  
	 *  Parameters:
	 *	@param numCustomers the number of customers wanting to enter the coffee shop
	 *	@param numCooks the number of cooks in the simulation
	 *	@param numTables the number of tables in the coffe shop (i.e. coffee shop capacity)
	 *	@param machineCapacity the capacity of all machines in the coffee shop
	 *  @param randomOrders a flag say whether or not to give each customer a random order
	 * @throws IdNotContainedException 
	 *
	 */
	public static List<SimulationEvent> runSimulation(
			int numCustomers, int numCooks,
			int numTables, 
			int machineCapacity,
			
			boolean randomOrders
			) throws IdNotContainedException {

		//This method's signature MUST NOT CHANGE.  


		//We are providing this events list object for you.  
		//  It is the ONLY PLACE where a concurrent collection object is 
		//  allowed to be used.
		events = Collections.synchronizedList(new ArrayList<SimulationEvent>());




		// Start the simulation
		logEvent(SimulationEvent.startSimulation(numCustomers,
				numCooks,
				numTables,
				machineCapacity));



		// Set things up you might need

		// Start up machines


		// Let cooks in
		Thread[] cooks = new Thread[numCooks];
		for(int index = 0; index < numCooks; index++){
			cooks[index] = new Thread(
					new Cook(index));
		}
		for(int index = 0; index < numCooks; index++){
			cooks[index].start();
		}


		// Build the customers.
		Thread[] customers = new Thread[numCustomers];
		ArrayList<String> order = new ArrayList <String>();
		String[] names ={"Emily Young", "Maria Lacie", "Rachael McDonalds", "Kallen Stadtfeld", "Josh Barren", "Sara Cyrus", "Bradley Cooper"} ;
        
		for(int i = 0; i < customers.length; i++) {
          
				order.add("FOD002");
				order.add("DES001");
				order.add("CLD001");
				customers[i] = new Thread(
						new Customer (i+100, totalBeforeDiscount, totalAfterDiscount, order) //needs to be modified to suit
						);
			
			}
		
		

		// Now "let the customers know the shop is open" by
		//    starting them running in their own thread.
		for(int i = 0; i < customers.length; i++) {
			customers[i].start();
			//NOTE: Starting the customer does NOT mean they get to go
			//      right into the shop.  There has to be a table for
			//      them.  The Customer class' run method has many jobs
			//      to do - one of these is waiting for an available
			//      table...
		}
		System.out.println("hello");

		try {
			// Wait for customers to finish
			//   -- you need to add some code here...
			
			//waits for the customer threads to end
			for(int i = 0; i < customers.length; i++){
				customers[i].join();
			}
			
			
			

			// Then send cooks home...
			// The easiest way to do this might be the following, where
			// we interrupt their threads.  There are other approaches
			// though, so you can change this if you want to.
			for(int i = 0; i < cooks.length; i++)
				cooks[i].interrupt();
			for(int i = 0; i < cooks.length; i++)
				cooks[i].join();

		}
		catch(InterruptedException e) {
			System.out.println("Simulation thread interrupted.");
		}



		// Done with simulation		
		logEvent(SimulationEvent.endSimulation());

		return events;
	}

	/**
	 * Entry point for the simulation.
	 *
	 * @param args the command-line arguments for the simulation.  There
	 * should be exactly four arguments: the first is the number of customers,
	 * the second is the number of cooks, the third is the number of tables
	 * in the coffee shop, and the fourth is the number of items each cooking
	 * machine can make at the same time.  
	 * @throws IdNotContainedException 
	 * @throws DuplicateIDException 
	 */
	

	public static void main(String args[]) throws InterruptedException, IdNotContainedException, DuplicateIDException {
		// Parameters to the simulation
		/*
		if (args.length != 4) {
			System.err.println("usage: java Simulation <#customers> <#cooks> <#tables> <capacity> <randomorders");
			System.exit(1);
		}
		int numCustomers = new Integer(args[0]).intValue();
		int numCooks = new Integer(args[1]).intValue();
		int numTables = new Integer(args[2]).intValue();
		int machineCapacity = new Integer(args[3]).intValue();
		boolean randomOrders = new Boolean(args[4]);
		*/
		menu = new Menu();
		allorders = new AllOrders();
	    customerList = new CustomerList();
	    allDiscounts = new AllDiscounts();
	    discountCheck = new DiscountCheck(allDiscounts.loadDiscounts());
		
		int numCooks = 20;
		int numTables = 50;
		int machineCapacity = 4;
		boolean randomOrders = true;
		addPreviousOrders();
		int numCustomers = customerList.size();
		
		

		// Run the simulation and then 
		//   feed the result into the method to validate simulation.
		runSimulation(
								numCustomers, numCooks, 
								numTables, machineCapacity,
								randomOrders
								);
			
	}

}



