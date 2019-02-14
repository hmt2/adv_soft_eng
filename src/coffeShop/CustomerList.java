package coffeShop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerList {
	private   HashMap <Integer, Customer> customerList; 
	private static AtomicInteger counter  = new AtomicInteger(99); //assume customerId starts from 100 and increments. Better check last customerId from orders.txt
	
    public CustomerList()
    {
       customerList = new HashMap <Integer, Customer>() ;
    }
    
    public int addCustomer(ArrayList<String> itemIds, float beforeDiscount, float afterDiscount) throws DuplicateIDException, CalculationError  { 
	    int customerId = counter.incrementAndGet();
			if(customerList.containsKey(customerId)){ //as long as there is a copy change. 
				throw new DuplicateIDException(customerId);  //or else repeat counter.incrementAndGet();
			}
			Customer newCustomer = new Customer(customerId,beforeDiscount,afterDiscount,itemIds); //it can be an Order object to
			customerList.put(customerId, newCustomer);
			return customerId;
    }
    
	public void deleteCustomer(int customerId) { 
        //If the user inputs the wrong id no change will be made to the file, therefore it is necessary to notify the user. Custom designed exception better
		if(!customerList.containsKey(customerId)){
			throw new IllegalArgumentException("Customer with id" + customerId + "does not exist"); 
		}else{
			customerList.remove(customerId);
		}
	}
	
	public Customer findCustomerId(int customerId) {
		if(!customerList.containsKey(customerId)){
			throw new IllegalArgumentException("Customer with id " + customerId + " does not exist");
		}else{
			Customer c = customerList.get(customerId);
			return c;
		}		
	}
		
	public Map listByCustomerId() { 
		Map<Integer, Customer>sortedCust = new TreeMap<>(customerList);
    	return sortedCust;
	}	
	
	public void displayBill(Customer c) { //in this method if there is a discount it won't show the previous bill. If we want that better to put this method inside Customer class
		boolean discount = c.getDiscounts();
		if (discount = true){
			System.out.println("The bill for customer with id after discount " + c.getCustomerId() + " is: " + c.getBillAfterDiscount()  );

		}else{
	        System.out.println("The bill for customer with id " + c.getCustomerId() + " is: " +c.getBillBeforeDiscount());
		}
		
	}
	public void loadCustomers() { 
        Map<Integer, Customer>sortedCust = listByCustomerId(); 
        

        StringBuffer allEntries = new StringBuffer();
        for (Map.Entry<Integer, Customer> entry : sortedCust.entrySet()){
        	allEntries.append(entry.getValue().toString());
        	allEntries.append('\n');
        	
        }
        System.out.println(allEntries);
	}

}
