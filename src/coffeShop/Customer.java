package coffeShop;

import java.util.ArrayList;
import java.util.List;

/**
 * Customers are simulation actors that have two fields: a name, and a list
 * of Food items that constitute the Customer's order.  When running, an
 * customer attempts to enter the coffee shop (only successful if the
 * coffee shop has a free table), place its order, and then leave the 
 * coffee shop when the order is complete.
 */
public class Customer{// implements Runnable {
	//JUST ONE SET OF IDEAS ON HOW TO SET THINGS UP...
	private int customerId;
	private  String name;

	private static int runningCounter = 0;

	    private float billBeforeDiscount;
	    private float billAfterDiscount;
	    private boolean discount;
		private ArrayList<String> itemIds;  
		
		public Customer(int customerId, float billBeforeDiscount, float billAfterDiscount,ArrayList<String> itemIds) 
	    {   
			this.billBeforeDiscount = billBeforeDiscount;
			this.billAfterDiscount = billAfterDiscount;
			if(this.billBeforeDiscount != this.billAfterDiscount)
				this.discount = true;
			else
				this.discount = false;
	        if (customerId < 100){ //throws an exception if the customer id is below 100
	        	throw new IllegalArgumentException ("Not a valid customer id");
	        }
	        this.customerId =customerId;
	        this.itemIds = itemIds;
	        this.name = name;
	    }

		public Customer(int customerId) 
	    {   
	      
	        if (customerId < 100){ 
	        	throw new IllegalArgumentException ("Not a valid customer id");
	        }

	        this.customerId =customerId;

	        this.itemIds = new ArrayList<>();

	    }
		//Get and set methods
		public int getCustomerId() {
			return customerId;
		}

		public String getName(){
			return name;
		}

		public void setCustomerId(int customerId) { 
			this.customerId = customerId;
		}
		
		public void setDiscount(boolean isDiscount) {
			discount = isDiscount;
		}

		public boolean getDiscounts() {
			return discount;
		}
		public ArrayList<String> getItemIds() { 
			return itemIds;
		}

		
		public void setItemIds(ArrayList<String> itemIds) { 
		  this.itemIds = itemIds;
		}

		

		public float getBillBeforeDiscount() {
			
			return billBeforeDiscount;
		}
		
		
		public float getBillAfterDiscount() {
				
				return billAfterDiscount;
			}
		


	    public String toString()
	    {
	    	String ordersString = "";

	    	for (String s : itemIds)
	    	{
	    	    ordersString += s + "\t";
	    	}
	        return String.format("%-5s", customerId ) + String.format("%-20s", ordersString ) + String.format("%-20s", String.valueOf(discount) ) +
	                 String.valueOf( billBeforeDiscount) ;
	    }

	
}

	/** 
	 * This method defines what an Customer does: The customer attempts to
	 * enter the coffee shop (only successful when the coffee shop has a
	 * free table), place its order, and then leave the coffee shop
	 * when the order is complete.
	 */
//	public void run() {
//		//YOUR CODE GOES HERE...
//		Simulation.logEvent(SimulationEvent.customerStarting(this));
//
//		synchronized(Simulation.currCapacity){ //the customers inside the store are compared to the number of tables
//			while(!(Simulation.currCapacity.size() < Simulation.events.get(0).simParams[2])){ //check the length of the inqueue. If it is less than the existing numberofcustomers, add
//				try {
//					Simulation.currCapacity.wait(); //if there is no space inside keep looping until there is place
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//			Simulation.currCapacity.add(this); //add customer to inqueue
//			Simulation.logEvent(SimulationEvent.customerEnteredCoffeeShop(this));
//			Simulation.currCapacity.notifyAll();
//		}
//		
//		List<String> orders = new ArrayList<String>();
//		for (String item : itemIds){
//			try {
//				orders.add(Simulation.menu.findItemId(item).getName());
//			} catch (IdNotContainedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
//		synchronized(Simulation.orderList){ //the outside queue of customers that want to served but whose order has not been processed
//			Simulation.orderList.add(this);
//			Simulation.logEvent(SimulationEvent.customerPlacedOrder(this, orders, this.itemIds.size()));
//			Simulation.orderList.notifyAll();
//		}
//		//initialize the persons order as not completed
//		synchronized(Simulation.completedOrder){ //once a customer's order has been completed mark it as completed
//			Simulation.completedOrder.put(this, false);
//		}
//		
//		synchronized(Simulation.completedOrder){
//			while(!(Simulation.completedOrder.get(this))){
//				try {
//					Simulation.completedOrder.wait();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//			Simulation.logEvent(SimulationEvent.customerReceivedOrder(this, orders, this.itemIds.size()));
//			Simulation.completedOrder.notifyAll();
//		}
//		synchronized(Simulation.currCapacity){
//			Simulation.currCapacity.remove(this);
//			Simulation.logEvent(SimulationEvent.customerLeavingCoffeeShop(this));
//			Simulation.currCapacity.notifyAll();
//		}
//	}
//}