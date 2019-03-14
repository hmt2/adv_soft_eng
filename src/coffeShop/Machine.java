package coffeShop;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A Machine is used to make a particular Food.  Each Machine makes
 * just one kind of Food.  Each machine has a maxAmount: it can make
 * that many food items in parallel; if the machine is asked to
 * produce a food item beyond its maxAmount, the requester blocks.
 * Each food item takes at least item.cookTimeMS milliseconds to
 * produce.
 */
public class Machine {
	public final String name;
	int maxAmount;
	Queue<String> foodsPreparing;
	//YOUR CODE GOES HERE...


	/**
	 * The constructor takes at least the name of the machine,
	 * the Food item it makes, and its maxAmount.  You may extend
	 * it with other arguments, if you wish.  Notice that the
	 * constructor currently does nothing with the maxAmount; you
	 * must add code to make use of this field (and do whatever
	 * initialization etc. you need).
	 */
	public Machine(String name, int maxAmount) {
		this.name = name;
		this.maxAmount = maxAmount;
		this.foodsPreparing = new LinkedList<String>(); 

	}
	

	public void  prepareFood(Server serverId, int customerId, String foodName) throws InterruptedException {
		//YOUR CODE GOES HERE...
      
		foodsPreparing.add(foodName); //technically should be item, and then we need to get time. For now just add
		Thread curr = new Thread(new CookMachine(serverId, customerId, foodName));
		curr.start();
	}

	
	private class CookMachine implements Runnable {
		Server currentServer;
		int customerId;
		String foodName;
		public CookMachine(Server currentServer, int customerId, String foodName){
			this.currentServer = currentServer;
			this.customerId = customerId;
			this.foodName = foodName;
		}
		
	
		public void run() {
			try {
				
				    System.out.println(Machine.this.name + " has started preparing " + foodName + " for customer " + customerId + " for server " + currentServer.getServerId()); 
				    Thread.sleep(2);
				    System.out.println(Machine.this.name + " has finished " + foodName + " for customer " + customerId + " for server " + currentServer.getServerId());
				    System.out.println("Server " + currentServer.getServerId() + " has finished " + foodName + " for customer " + customerId);
				     
                    
					synchronized(foodsPreparing){
						foodsPreparing.remove();
						foodsPreparing.notifyAll();	
					}

				
				
			} catch(InterruptedException e) { }
		}
	}
 

}
