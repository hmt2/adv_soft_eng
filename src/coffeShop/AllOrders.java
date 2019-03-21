package coffeShop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import exceptions.InvalidIdException;

import java.util.Set;
import java.util.TreeMap;

public class AllOrders {

	/** Storage for a certain amount of orders
	 *  LinkedHashMap to be able to sort the orders
	 * 
	 */
	private static LinkedHashMap<Integer, Order> allOrders;

	public AllOrders() {
		allOrders = new LinkedHashMap<Integer, Order>();
	}

	/** loadOrders : read the orders.txt file and add each line as a new order, with a new customer
	 *  as stated in the requirements, if a customer has ordered more than one item, it is divided in different orders
	 *  it returns a TreeMap of customers
	 *  @return TreeMap<Integer, ArrayList<String>>
	 */
	public TreeMap<Integer,ArrayList<String>> loadOrders() {
		// initialization : customers as a TreeMap and orders as a LinkedHashMap
		TreeMap<Integer,ArrayList<String>> customers = new TreeMap<Integer,ArrayList<String>>();
		LinkedHashMap<Integer, Order> entries = new LinkedHashMap<Integer, Order>();
		BufferedReader buff = null;
		String data [] = new String[4];
		try {
			// try to open the file orders.txt
			buff = new BufferedReader(new FileReader("src/coffeShop/orders.txt"));
	
			String inputLine = null;

			inputLine = buff.readLine();
			// read first line
			while(inputLine != null){  
				// split line into parts
				data  = inputLine.split(",");

				// get the orderId, customerId, itemId, timestamp for one order
				Integer orderId = Integer.parseInt(data[0].trim());
				Integer customerId = Integer.parseInt(data[1].trim());
				String itemId = data[2].trim();
				Timestamp timestamp = Timestamp.valueOf(data[3].trim());

				Order o;
				try {
					// create new Order with the orderId, customerId, itemId and timestamp we got
					o = new Order(orderId, customerId, itemId, timestamp);
					// if the customers has already been created (the customer has ordered more than one item)
					// we add the itemId to his ArrayList of items he has ordered in CustomerList
					if(customers.containsKey(customerId)) {
						ArrayList<String> currentItemIds = customers.get(customerId);
						currentItemIds.add(itemId);
						customers.put(customerId, currentItemIds);
					}
					else {
						// if he has not been created yet, we create his ArrayList of items he has ordered
						ArrayList<String> itemIds = new ArrayList<String>();
						itemIds.add(itemId);
						customers.put(customerId,itemIds);
					}
					// we add the order to the allOrders LinkedHashMap
					entries.put(orderId, o);
					// we read the next line
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
			}
		}
		allOrders = entries;
		return customers;
	}

	/** returnAllOrders : returns the LinkedHashMap allOrders
	 * 
	 * @return LinkedHashMap
	 */
	public LinkedHashMap<Integer, Order> returnAllOrders(){
		return allOrders;
	}

	/** checkOrderId : checks if the LinkedHashMap allOrders already contains a certain key
	 * 
	 * @param orderId
	 * @return boolean
	 */
	public static boolean checkOrderId(Integer orderId) {
		return allOrders.containsKey(orderId);
	}

	/** addOrder : adds a new Order in the orders.txt 
	 *  it also loads the allOrders LinkedHashMap again (it adds the new order to the LinkedHashMap)
	 *  as stated in the requirements, if a customer has ordered more than one item, it is divided in different orders
	 * 
	 * @param CustomerId
	 * @param currentOrder
	 */
	@SuppressWarnings("null")
	public void addOrder(Integer CustomerId, ArrayList<String> currentOrder) {
		int nb_orders = currentOrder.size();
		boolean check;
		int orderIds[] = new int[nb_orders];
		// we generate the orderIds, we check if the key is already in the LinkedHashMap
		int i = allOrders.size() + 1;
		check = checkOrderId(i);
		while(check) {
			i += 1;
			check = checkOrderId(i);
		}
		if (check == false) {
			orderIds[0] = allOrders.size() + 1;
		}
		for(int j=1; j < nb_orders; j++) {
			orderIds[j] = orderIds[0] + j;
		}
		// we get the timestamp
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		// we try to open the orders.txt file
		try {
			DataOutputStream stream = new DataOutputStream(new FileOutputStream("src/coffeShop/orders.txt", true));
			for (int l = 0; l < nb_orders; l++) {
				// Order object (orderId, customerId, itemId, timestamp)
				Integer orderId = orderIds[l];
				Integer customerId = CustomerId;
				String itemId = currentOrder.get(l).trim();
				// we add a line in the orders.txt
				String data = "\n" + orderId.toString() + ", " + customerId.toString() + ", " + itemId.toString() + ", " + timestamp.toString();
				stream.writeBytes(data);
			}
		} catch (IOException e) {
		}
		allOrders.clear();
		loadOrders();
	}

	/** findOrderId : finds an order when we are given an Id
	 * 
	 * @param orderId
	 * @return Order
	 */
	public static Order findOrderId(Integer orderId) {
		return allOrders.get(orderId);
	}

	/** findCustomerOrders : find the itemIds for one customer
	 * 
	 * @param customerId
	 * @return ArrayList<Integer>
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

	/** listByOrderId : list the allOrders LinkedHashMap by the OrderId
	 *  
	 */
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

	/** listByTimeStamp : list the allOrders LinkedHashMap by the TimeStamp
	 *  
	 */
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

	/** listByCustomerId : list the allOrders LinkedHashMap by the CustomerId
	 *  
	 */
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

	/** listByItemId : list the allOrders LinkedHashMap by the ItemId
	 *  
	 */
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

	/** printAllOrders : print the list of all orders in the LinkedHashMap allOrders
	 *  
	 */
	public static void printAllOrders() {
		Set<Entry<Integer, Order>> mapset = allOrders.entrySet();
		System.out.println(" PRINT ALL ORDERS ");
		for(Entry<Integer, Order> ent: mapset){
			System.out.println(ent.getKey() + " : " + ent.getValue().toString());
		}
	}

	/** getNumOrders : get the size of the LinkedHashMap allOrders
	 * created for testing purposes
	 * 
	 * @return int
	 */
	public  int getNumOrders(){
		return allOrders.size();
	}

}