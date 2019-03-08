package coffeShop;


import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

// this class stores the orders coming from the queue inside the store
public class InStoreQueue {
	
	private Queue<Customer> instoreQueue; //queue of Customers 
	
	public InStoreQueue() {
		instoreQueue = new LinkedList<>();
	}
	
	public void addInstoreQueue(Customer customer) {
		if (customer!= null) 
		{
		
			instoreQueue.add(customer);
		}
		else {
			throw new IllegalArgumentException("not valid customer");
		}
			
								
	}
		
	// Method to check what is inside the In store Queue
	public void displayInstoreQueue() {
		System.out.println(instoreQueue);
	}
	
	public void removeHead() {
		try {
			instoreQueue.remove();
		}
		catch (NoSuchElementException e) {
			System.out.println("Queue empty");
		}
	}
	
	public int size() {
		return instoreQueue.size();
	}
	
	

}
