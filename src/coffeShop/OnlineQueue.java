package coffeShop;


import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Queue;

public class OnlineQueue {
	private Queue<Customer> onlineQueue;
	CustomerList customerList;

	public OnlineQueue(CustomerList customerList) {
		onlineQueue = new LinkedList<>();
		this.customerList = customerList;
	}
	//for this function we don't need any exception because it is taking the data from allorders class. 
	//If this class is null it will be caught by its exception.
	public void loadOnlineQueue() {
		
		if (customerList!= null) 
		{
		Set<Entry<Integer, Customer>> entries = customerList.customers().entrySet();
		for (Map.Entry<Integer, Customer> entry : entries) {
			onlineQueue.offer(entry.getValue());
		}
		//System.out.println(onlineQueue.size());
		returnOnlineQueue();
		
		}
		else {
			throw new IllegalArgumentException("not valid customerList");
		}
	}
	
	public Queue<Customer> returnOnlineQueue(){
		return onlineQueue;
	}
	
	
	// Method to check what is inside the Online Queue
	public void displayOnlineQueue() {
		System.out.println(onlineQueue);
		
	}
	
	public void removeHead() {
		try {
			onlineQueue.remove();
		}
		catch (NoSuchElementException e) {
			System.out.println("Queue empty");
		}
	}
	
	
		
	
}
