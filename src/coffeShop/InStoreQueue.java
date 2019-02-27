package coffeShop;



import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

// this class stores the orders coming from the queue inside the store
public class InStoreQueue {
	
	private TreeMap<Integer, Order> instoreQueue; //key orderId , value : Order
	
	public InStoreQueue() {
		instoreQueue = new TreeMap<Integer, Order>();  
	}
	
	public void addInstoreQueue(int[] orderIds) {
		if (orderIds != null) 
		{
			for(int i=0;i<orderIds.length;i++)
				{
				instoreQueue.put(orderIds[i], AllOrders.findOrderId(orderIds[i]));
			
				}
		}
		else {
			throw new NullPointerException("OrderIds is empty");
		}
	}
		
	// Method to check what is inside the Instore Queue
	public void displayInstoreQueue() {
		Set<Entry<Integer, Order>> entries = instoreQueue.entrySet();
		for (Map.Entry<Integer, Order> entry : entries) {
			System.out.println(entry.getKey()+" "+entry.getValue());
		}
	}
	
	
	


}
