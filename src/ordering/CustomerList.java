package ordering;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import exceptions.DuplicateIDException;

public class CustomerList {
	private   HashMap <Integer, Customer> customerList; 
	private static AtomicInteger counter  = new AtomicInteger(99); 
	//Initialize it by 99 so that customer ids start from 100 

	public CustomerList()
	{
		customerList = new HashMap <Integer, Customer>() ;
	}

	//Add a new customer to the customerList hashmap. If there is a duplicate id an exception is thrown
	public int addCustomer(ArrayList<String> itemIds, float beforeDiscount, float afterDiscount) throws DuplicateIDException  { 
		int customerId = counter.incrementAndGet(); //increments the customer id by one everytime a new one needs to be added
		if(customerList.containsKey(customerId)){ 
			throw new DuplicateIDException(customerId);  
		}
		Customer newCustomer = new Customer(customerId,beforeDiscount,afterDiscount,itemIds,false); 
		customerList.put(customerId, newCustomer);
		return customerId;
	}
	
	/** addFirstCustomer : adds a customer which has made an order in priority so priority is set as true
	 * 
	 * @param itemIds
	 * @param beforeDiscount
	 * @param afterDiscount
	 * @return
	 * @throws DuplicateIDException
	 */
    public int addFirstCustomer(ArrayList<String> itemIds, float beforeDiscount, float afterDiscount) throws DuplicateIDException  { 
	    int customerId = counter.incrementAndGet(); //increments the customer id by one everytime a new one needs to be added
			if(customerList.containsKey(customerId)){ 
				throw new DuplicateIDException(customerId);  
			}
			Customer newCustomer = new Customer(customerId,beforeDiscount,afterDiscount,itemIds,true); 
			customerList.put(customerId, newCustomer);
			return customerId;
    }

	//Delete a customer with a specific customer id. If the customerList does not contain such a customer an exception is thrown
	public void deleteCustomer(int customerId) { 
		if(!customerList.containsKey(customerId)){
			throw new IllegalArgumentException("Customer with id" + customerId + "does not exist"); 
		}else{
			customerList.remove(customerId);
		}
	}


	//Find the customer object that has the specified customer id
	public Customer findCustomerId(int customerId) {
		if(!customerList.containsKey(customerId)){
			throw new IllegalArgumentException("Customer with id " + customerId + " does not exist");
		}else{
			Customer c = customerList.get(customerId);
			return c;
		}		
	}

	public int size() { 
		return customerList.size();
	}

	public Map listByCustomerId() { 
		Map<Integer, Customer>sortedCust = new TreeMap<>(customerList);
		return sortedCust;
	}	

	public void displayBill(Customer c) { 
		boolean discount = c.getDiscounts();
		if (discount == true){
			System.out.println("The bill for customer with id after discount " + c.getCustomerId() + " is: " + c.getBillAfterDiscount()  );

		}else{
			System.out.println("The bill for customer with id " + c.getCustomerId() + " is: " +c.getBillBeforeDiscount());
		}

	}

	//Print all the customers ordered by the customer id
	public void loadCustomers() { 
		Map<Integer, Customer>sortedCust = listByCustomerId(); 
		StringBuffer allEntries = new StringBuffer();
		for (Map.Entry<Integer, Customer> entry : sortedCust.entrySet()){
			allEntries.append(entry.getValue().toString());
			allEntries.append('\n');

		}
		System.out.println(allEntries);
	}

	//for testing
	public HashMap<Integer,Customer> customers(){
		return customerList;
	}

}
