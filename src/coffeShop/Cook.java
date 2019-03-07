package coffeShop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



/**
 * Cooks are simulation actors that have at least one field, a name.
 * When running, a cook attempts to retrieve outstanding orders placed
 * by Eaters and process them.
 */
public class Cook implements Runnable {
	private final int cookId;
	private Customer currCustomer;
	public List<Item> finishedFood = new LinkedList<Item>();

	
	/**
	 * You can feel free modify this constructor.  It must
	 * take at least the name, but may take other parameters
	 * if you would find adding them useful. 
	 *
	 * @param: the name of the cook
	 */
	public Cook(int cookId) {
		this.cookId = cookId;
	}
	
	public int getId(){
		return cookId;
	}


	/**
	 * This method executes as follows.  The cook tries to retrieve
	 * orders placed by Customers.  For each order, a List<Food>, the
	 * cook submits each Food item in the List to an appropriate
	 * Machine, by calling makeFood().  Once all machines have
	 * produced the desired Food, the order is complete, and the Customer
	 * is notified.  The cook can then go to process the next order.
	 * If during its execution the cook is interrupted (i.e., some
	 * other thread calls the interrupt() method on it, which could
	 * raise InterruptedException if the cook is blocking), then it
	 * terminates.
	 */
	public void run() {

		Simulation.logEvent(SimulationEvent.cookStarting(this)); //just keep this in simulationevent
		try {
			while(true) {
				//YOUR CODE GOES HERE...

				//get the customer currently up next
				synchronized(Simulation.orderList){ //the Customers that are waiting to be served

					while(Simulation.orderList.isEmpty()){ //if there are no customers, wait
						Simulation.orderList.wait();
					}
					currCustomer = Simulation.orderList.remove(); //get the next customer in the queue
					
					ArrayList<String> itemsOrdered = currCustomer.getItemIds(); //read the customer orders
					List<String> itemsName = new ArrayList<String>(); //show the item names in log for each customer. It could be ids as well
					for (String item : itemsOrdered){
						itemsName.add(Simulation.menu.findItemId(item).getName());
								
					}
					
					//List<Item> orderItem = new ArrayList<Item>();
					//	for (String item : itemsOrdered){
				//		orders.add(Simulation.menu.findItemId(item));
						
				//	}
					
				
					Simulation.logEvent(SimulationEvent.cookReceivedOrder(this, itemsName, currCustomer.getCustomerId()));
					Simulation.orderList.notifyAll();
				}
				//sends food to specific machine
				ArrayList<String> itemsOrdered = currCustomer.getItemIds();
				for(int index = 0; index < itemsOrdered.size(); index++){
					Item currFood = Simulation.menu.findItemId(itemsOrdered.get(index));

					Simulation.logEvent(SimulationEvent.cookStartedFood(this, currFood , currCustomer.getCustomerId()));
				//	Thread.sleep(currFood.getItemDuration()); //to wait depending on item cooking time
					Thread.sleep(5); //remove this later
					Simulation.logEvent(SimulationEvent.cookFinishedFood(this, currFood,currCustomer.getCustomerId())); //must specify different threads for different staff, for now just one

					synchronized(finishedFood){
						this.finishedFood.add(currFood);
						this.finishedFood.notifyAll();	
					}
					
					
						}
						
					
				
				synchronized(finishedFood){
					while(!(finishedFood.size() == itemsOrdered.size())){
						finishedFood.wait();
						finishedFood.notifyAll();
					}
				}
				Simulation.logEvent(SimulationEvent.cookCompletedOrder(this, currCustomer.getCustomerId()));
				
				synchronized(Simulation.completedOrder){
					Simulation.completedOrder.put(currCustomer, true);
					Simulation.completedOrder.notifyAll();
				}
				finishedFood = new LinkedList<Item>();
			
			}
		}
		catch(InterruptedException e) {
			// This code assumes the provided code in the Simulation class
			// that interrupts each cook thread when all customers are done.
			// You might need to change this if you change how things are
			// done in the Simulation class.
			Simulation.logEvent(SimulationEvent.cookEnding(this));
		} catch (IdNotContainedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}