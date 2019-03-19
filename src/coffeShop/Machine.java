package coffeShop;

import java.util.*;

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

	public void  prepareFood(Server serverId, int customerId, Item item) throws InterruptedException {
		getFoodsPreparing().add(item.getName()); //technically should be item, and then we need to get time. For now just add
		Thread curr = new Thread(new CookMachine(serverId, customerId, item));
		curr.start();
	}

	public Queue<String> getFoodsPreparing() {
		return foodsPreparing;
	}

	public void setFoodsPreparing(Queue<String> foodsPreparing) {
		this.foodsPreparing = foodsPreparing;
	}


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
				Thread.sleep(item.getItemDuration()*1000);
				System.out.println(Machine.this.name + " has finished " + item.getName() + " for customer " + customerId + " for server " + currentServer.getServerId());
				System.out.println("Server " + currentServer.getServerId() + " has finished " + item.getName() + " for customer " + customerId);

				synchronized(getFoodsPreparing()){
					getFoodsPreparing().remove();
					getFoodsPreparing().notifyAll();	
				}
				synchronized(currentServer.completedItem){
					currentServer.completedItem.add(item.getName());
					currentServer.completedItem.notifyAll();	
					
				}

			} catch(InterruptedException e) { }
		}
	}

}
