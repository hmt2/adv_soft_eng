package cmsc433.p2;

//maintains a map of Order objects as a TreeMap

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class AllOrders {
	
	// Storage for a certain amount of orders
	private static LinkedHashMap<Integer, Order> allOrders;
	
	// 
	public AllOrders() {
		allOrders = new LinkedHashMap<Integer, Order>();
	}

		
	/**
	 * loads a text file of orders
	 */
	public static TreeMap<Integer,ArrayList<String>> loadOrders() {
	 TreeMap<Integer,ArrayList<String>> customers = new TreeMap<Integer,ArrayList<String>>();
 	//initialise empty linkedhashmap of orders
     LinkedHashMap<Integer, Order> entries = new LinkedHashMap<Integer, Order>();
     BufferedReader buff = null;
 	String data [] = new String[4];
 	try {
 		// java.net.URL url = AllOrders.class.getResource("orders.txt");
			buff = new BufferedReader(new FileReader("orders.txt"));
			String inputLine = null;

			inputLine = buff.readLine();
			//read first line
	    	while(inputLine != null){  
	    		//split line into parts
	    		data  = inputLine.split(",");
	    		
	    		//create Order object (orderId, customerId, itemId, timestamp)
	    		Integer orderId = Integer.parseInt(data[0].trim());
	    		Integer customerId = Integer.parseInt(data[1].trim());
	    		String itemId = data[2].trim();
	    		Timestamp timestamp = Timestamp.valueOf(data[3].trim());

	    		Order o;
				try {
					o = new Order(orderId, customerId, itemId, timestamp);
					//Integer String Integer timestamp
		    		if(customers.containsKey(customerId)) {
		    			ArrayList<String> currentItemIds = customers.get(customerId);
		    			currentItemIds.add(itemId);
		    			customers.put(customerId, currentItemIds);
		    		}
		    		else {
		    			ArrayList<String> itemIds = new ArrayList<String>();
		    			itemIds.add(itemId);
		    			customers.put(customerId,itemIds);
		    		}
		    		//add to linkedhashmap
		            entries.put(orderId, o);
		            //read next line
		            inputLine = buff.readLine();
				} catch (InvalidIdException e) {
					e.printStackTrace();
				}
	    	}
         
	    }
	    catch(FileNotFoundException e) {
	    	System.out.println(e.getMessage());
	        System.exit(1);
	    }
	    catch (IOException e) {
	    	e.printStackTrace();
	        System.exit(1);        	
	    }
	    finally  {
	    	try{
	    		buff.close();
	    	}
	    	catch (IOException ioe) {
	    		//don't do anything
	    	}
	    }
 	allOrders = entries;
 	return customers;
	}
	
	public LinkedHashMap<Integer, Order> returnAllOrders(){
		return allOrders;
	}
	
	public static boolean checkOrderId(Integer orderId) {
		return allOrders.containsKey(orderId);
	}
	
	@SuppressWarnings("null")
	public void addOrder(Integer CustomerId, ArrayList<String> currentOrder) {
		int nb_orders = currentOrder.size();
		boolean check;
		int orderIds[] = new int[nb_orders];
		//generate order ids
		int i = allOrders.size() + 1;
		check = checkOrderId(i);
		while(check) { //true : already in the linkedhashmap
			i += 1;
		}
		if (check == false) {
			orderIds[0] = allOrders.size() + 1;
		}
		for(int j=1; j < nb_orders; j++) {
			orderIds[j] = orderIds[0] + j;
		}
		// get the timestamp
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		try(FileWriter fw = new FileWriter("orders.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
			for (int l = 0; l < nb_orders; l++) {
				//Order object (orderId, customerId, itemId, timestamp)
	    		Integer orderId = orderIds[l];
	    		Integer customerId = CustomerId;
	    		String itemId = currentOrder.get(l).trim();
	    		out.append("\n" + orderId.toString() + ", " + customerId.toString() + ", " + itemId.toString() + ", " + timestamp.toString());
			}
			} catch (IOException e) {
			    //exception handling
			}
		loadOrders();
	}
	
	/**
	 * adds an order (if more than one order, divides in a certain number of orders)
	 */
	public void addOrder(int nb_orders, String data[]) {
		int i = 1;
		
		while(i < nb_orders+1) {
			for (int j = 0; j < nb_orders*4; j+=4) {
				//Order object (orderId, customerId, itemId, timestamp)
	    		Integer orderId = Integer.parseInt(data[j].trim());
	    		Integer customerId = Integer.parseInt(data[j+1].trim());
	    		String itemId = data[j+2].trim();
	    		Timestamp timestamp = Timestamp.valueOf(data[j+3]);
	    		try(FileWriter fw = new FileWriter("orders.txt", true);
	    			    BufferedWriter bw = new BufferedWriter(fw);
	    			    PrintWriter out = new PrintWriter(bw))
	    			{
	    			    out.println(orderId.toString() + "," + customerId.toString() + "," + itemId.toString() + "," + timestamp.toString());
	    			} catch (IOException e) {
	    			    //exception handling
	    			}
	    		loadOrders();
			}
		}
		
	}
	
//	/**
//	 * checks if the customer is already created
//	 */
//	public void checkCustomer(Integer orderId) {
//		
//	}
	
	/**
	 * deletes an order 
	 * if the customer ordered only one item, then it deletes also the customer
	 * if the customer ordered several items, it will delete only this order
	 * 
	 * OR create a new text file without the item to delete?
	 */
	public void deleteOrder(Integer orderId) {
		Order o = findOrderId(orderId);
		ArrayList<Integer> list;
		list = findCustomerOrders(o.getCustomerId());
		System.out.println(list);
		if(list.size() == 1)
			//remove Customer
			System.out.println("We remove the customer: there is only one order");
		allOrders.remove(orderId);
		
	}
	
	/**
	 * finds an order when we are given an Id
	 * @return 
	 */
	public static Order findOrderId(Integer orderId) {
		return allOrders.get(orderId);
	}
	
	/**
	 * finds all the orders of one customer when we are given a customerId
	 */
	public static ArrayList<Integer> findCustomerOrders(Integer customerId) {
		ArrayList<Integer> orderIds = new ArrayList<Integer>();
		Set<Entry<Integer, Order>> mapset = allOrders.entrySet();
		for(Entry<Integer, Order> ent: mapset){
			if(ent.getValue().getCustomerId() == customerId) {
         	orderIds.add(ent.getKey());
			}
     }
		return orderIds;	   
	}
	
	public static void listByOrderId() {
		List<Map.Entry<Integer, Order>> entries = new ArrayList<Map.Entry<Integer, Order>>(allOrders.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<Integer, Order>>() {
			public int compare(Map.Entry<Integer, Order> o1, Map.Entry<Integer, Order> o2){
				return o1.getValue().compareToOrderId(o2.getValue());
			}
		});
		for (Map.Entry<Integer, Order> entry : entries) {
		  allOrders.put(entry.getKey(), entry.getValue());
		  // check the sorted hashmap
		  System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
	
	public static void listByTimestamp() {
		List<Map.Entry<Integer, Order>> entries = new ArrayList<Map.Entry<Integer, Order>>(allOrders.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<Integer, Order>>() {
			public int compare(Map.Entry<Integer, Order> o1, Map.Entry<Integer, Order> o2){
				return o1.getValue().compareToTimestamp(o2.getValue());
			}
		});
		allOrders.clear();
		for (Map.Entry<Integer, Order> entry : entries) {
		  allOrders.put(entry.getKey(), entry.getValue());
		}


	}
	
	public static void listByCustomerId() {
		List<Map.Entry<Integer, Order>> entries = new ArrayList<Map.Entry<Integer, Order>>(allOrders.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<Integer, Order>>() {
			public int compare(Map.Entry<Integer, Order> o1, Map.Entry<Integer, Order> o2){
				return o1.getValue().compareToCustomerId(o2.getValue());
			}
		});
		allOrders.clear();
		for (Map.Entry<Integer, Order> entry : entries) {
		  allOrders.put(entry.getKey(), entry.getValue());
		}
		
	}
	
	public static void listByItemId() {
		List<Map.Entry<Integer, Order>> entries = new ArrayList<Map.Entry<Integer, Order>>(allOrders.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<Integer, Order>>() {
			public int compare(Map.Entry<Integer, Order> o1, Map.Entry<Integer, Order> o2) {
				return o1.getValue().getItemId().compareTo(o2.getValue().getItemId());
			}
		});
		allOrders.clear();
		for (Map.Entry<Integer, Order> entry : entries) {
		  allOrders.put(entry.getKey(), entry.getValue());
		}
		
	}
	
	public static void printAllOrders() {
		Set<Entry<Integer, Order>> mapset = allOrders.entrySet();
		System.out.println(" PRINT ALL ORDERS ");
		for(Entry<Integer, Order> ent: mapset){
			System.out.println(ent.getKey() + " : " + ent.getValue().toString());
     }
	}
	
	
	//For testing purposes
	public  int getNumOrders(){
		return allOrders.size();
	}
	
}