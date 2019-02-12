package coffeShop;

import java.util.ArrayList;
public class Customer 
{
	private int customerId;
    private float bill;
    private boolean discount;
	private ArrayList<Integer> orderIds;  
	public Customer(int customerId) 
    {   
       //Adjust the way customerId is created
        if (customerId < 100){ //we assume all customerId start from 100. However is better to check which is the last customerId from orders.txt or refresh the orders.txt every day
        	throw new IllegalArgumentException ("Not a valid customer id");
        }

        this.customerId =customerId;

        this.orderIds = new ArrayList<>();

    }

	/**
     * @return The customer Id.
     */
	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) { 
		this.customerId = customerId;
	}
	
	/**
     * @return The name of the customer.
     */
	public boolean getDiscounts() {
		return discount;
	}
	public ArrayList<Integer> getOrderIds() { 
		return orderIds;
	}

    //Not sure if this attribute is needed as we can findCustomerOrders in AllOrders class
	public void setOrderIds(int orderId) { 
		orderIds.add(orderId);
	}

	
	/**
     * @return The total cost of the orders / bill of the customer.
     */
	public float getBill() {
		
		return bill;
	}

	
	/**
     * @return Add comments later
     */

    /**
     * @return A  string containing all details.
     */

    public String toString()
    {
    	String ordersString = "";

    	for (Integer s : orderIds)
    	{
    	    ordersString += s + "\t";
    	}
        return String.format("%-5s", customerId ) + String.format("%-20s", ordersString ) + String.format("%-20s", String.valueOf(discount) ) +
                 String.valueOf( bill) ;
    }

}
