package coffeShop;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class OnlineQueue {
	private TreeMap<Integer, Order> onlineQueue; //key orderId , value : Order
	AllOrders allOrders;

	public OnlineQueue(AllOrders allOrders) {
		onlineQueue = new TreeMap<Integer, Order>();
		this.allOrders = allOrders;
	}
	//for this function we don't need any exception because it is taking the data from allorders class. 
	//If this class is null it will be caught by its exception.
	public void loadOnlineQueue() {
		
		Set<Entry<Integer, Order>> entries = allOrders.returnAllOrders().entrySet();
		for (Map.Entry<Integer, Order> entry : entries) {
			onlineQueue.put(entry.getKey(), entry.getValue());
		}
	}
	
	public TreeMap<Integer, Order> returnOnlineQueue(){
		return onlineQueue;
	}
	
	
	// Method to check what is inside the Online Queue
		public void displayOnlineQueue() {
			Set<Entry<Integer, Order>> entries = onlineQueue.entrySet();
			for (Map.Entry<Integer, Order> entry : entries) {
				System.out.println(entry.getKey()+" "+entry.getValue());
			}
		}
		
	
}
