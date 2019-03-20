package coffeShop;


import java.io.IOException;
import java.util.*;

import output.Log;
import exceptions.IdNotContainedException;
import main.Simulation;

public class Machine {
	public final String name;
	int maxAmount;
	private Queue<String> foodsPreparing;

	public Machine(String name, int maxAmount) {
		this.name = name;
		this.maxAmount = maxAmount;
		this.setFoodsPreparing(new LinkedList<String>()); 

	}

	/**Method  that begins a thread for each item given by the server for a particular customer and a machine

    */
	public void  prepareFood(Server serverId, int customerId, Item item) throws InterruptedException {
		getFoodsPreparing().add(item.getName());  //add the new item to the list of items that is being prepared by the machine
		Thread machineThread = new Thread(new CookMachine(serverId, customerId, item));
		machineThread.start();
	}

	public Queue<String> getFoodsPreparing() {
		return foodsPreparing;
	}

	public void setFoodsPreparing(Queue<String> foodsPreparing) {
		this.foodsPreparing = foodsPreparing;
	}

	/**Runnnable class that prepares an item for a server that is assigned to a customer. The steps are,
	-Machine starts preparing food (log)
	-Wait a given time depending on item preparation duration (log)
	-Machine finished preparing food (log)
	-Server finished preparing food (log)
	-Remove item from the list of items are waiting to be prepared by the machine
	-Add the item to the completed list of items of the server  */
	private class CookMachine implements Runnable {
		Server currentServer;
		int customerId;
		Item item;
		public CookMachine(Server currentServer, int customerId, Item item){
			this.currentServer = currentServer;
			this.customerId = customerId;
			this.item = item;
		}


		public void run() {
			try {

				System.out.println(Machine.this.name + " has started preparing " + item.getName() + " for customer " + customerId + " for server " + currentServer.getServerId()); 
				Log.writeToLog(Machine.this.name + " has started preparing " + item.getName() + " for customer " + customerId + " for server " + currentServer.getServerId());

				/**Sleep the thread taking into account 
				the duration of the item (multiplied by a scalar to slow down the simulation)
				*/
				Thread.sleep(item.getItemDuration()*500); 
				System.out.println(Machine.this.name + " has finished " + item.getName() + " for customer " + customerId + " for server " + currentServer.getServerId());
				Log.writeToLog(Machine.this.name + " has finished " + item.getName() + " for customer " + customerId + " for server " + currentServer.getServerId());

				
				System.out.println("Server " + currentServer.getServerId() + " has finished " + item.getName() + " for customer " + customerId);
				Log.writeToLog("Server " + currentServer.getServerId() + " has finished " + item.getName() + " for customer " + customerId);

				/**Synchronised with foodsPreparing object, which contains the items that are waiting to be processed by this machine. Once the item is processed,
					//the first in the queue is removed and the threads waiting on the object are notified to indicate the next item can be processed
					 * */
					 
				synchronized(getFoodsPreparing()){ 
					getFoodsPreparing().remove();   
					getFoodsPreparing().notifyAll();	
				}
				
				/**Add the item to the list of completed items for a server*/
				synchronized(currentServer.completedItem){      
					currentServer.completedItem.add(item.getName());
					currentServer.completedItem.notifyAll();	
					
				}

			} catch(InterruptedException e) { } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
