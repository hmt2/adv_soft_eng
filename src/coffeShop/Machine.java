package coffeShop;

import java.util.*;

public class Machine {
	public final String name;
	int maxAmount;
	private Queue<String> foodsPreparing;

	public Machine(String name, int maxAmount) {
		this.name = name;
		this.maxAmount = maxAmount;
		this.setFoodsPreparing(new LinkedList<String>()); 

	}

	public void  prepareFood(Server serverId, int customerId, String foodName) throws InterruptedException {
		getFoodsPreparing().add(foodName); //technically should be item, and then we need to get time. For now just add
		Thread curr = new Thread(new CookMachine(serverId, customerId, foodName));
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

				synchronized(getFoodsPreparing()){
					getFoodsPreparing().remove();
					getFoodsPreparing().notifyAll();	
				}

			} catch(InterruptedException e) { }
		}
	}

}
