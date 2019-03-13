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
	public final String food;
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
	public Machine(String name, String food, int maxAmount) {
		this.name = name;
		this.food = food;
		this.maxAmount = maxAmount;
		this.foodsPreparing = new LinkedList<String>(); //should be type Food
		//this.foodsPreparing = new LinkedList<Food>();
		//YOUR CODE GOES HERE...

	}
	

	

	/**
	 * This method is called by a Cook in order to make the Machine's
	 * food item.  You can extend this method however you like, e.g.,
	 * you can have it take extra parameters or return something other
	 * than Object.  It should block if the machine is currently at full
	 * maxAmount.  If not, the method should return, so the Cook making
	 * the call can proceed.  You will need to implement some means to
	 * notify the calling Cook when the food item is finished.
	 */
	public void  makeFood(Server serverId, int customerId) throws InterruptedException {
		//YOUR CODE GOES HERE...
      
		foodsPreparing.add(food); //technically should be item, and then we need to get time. For now just add
		Thread curr = new Thread(new CookAnItem(serverId, customerId));
		curr.start();
	}

	//THIS MIGHT BE A USEFUL METHOD TO HAVE AND USE BUT IS JUST ONE serverIdEA
	private class CookAnItem implements Runnable {
		Server currentServer;
		int customerId;
		public CookAnItem(Server currentServer, int customerId){
			this.currentServer = currentServer;
			this.customerId = customerId;
		}
	
		public void run() {
			try {
				//YOUR CODE GOES HERE...	
				//Simulation.logEvent(SimulationEvent.machineCookingFood(Machine.this, food));
				    System.out.println(Machine.this + " has started preparing " + food); 
				    Thread.sleep(2);
				    System.out.println(Machine.this + " has finished " + food);
				    System.out.println("Server " + currentServer.getServerId() + " has finished " + food + " for customer " + customerId);
				     
				//Thread.sleep(food.cookTimeMS);
					//Simulation.logEvent(SimulationEvent.machineDoneFood(Machine.this, food));
					//Simulation.logEvent(SimulationEvent.cookFinishedFood(currentServer, food,customerId));
					synchronized(foodsPreparing){
						foodsPreparing.remove();
						foodsPreparing.notifyAll();	
					}
					synchronized(currentServer.finishedFood){
						currentServer.finishedFood.add(food);
						currentServer.finishedFood.notifyAll();	
					}
				
				
			} catch(InterruptedException e) { }
		}
	}
 

	public String toString() {
		return name;
	}
}