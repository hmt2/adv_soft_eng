package coffeShop;

import java.util.ArrayList;
public class Customer 
{
	private int customerId;
    private float billBeforeDiscount;
    private float billAfterDiscount;
    private boolean discount;
	private ArrayList<String> itemIds;  
	
	public Customer(int customerId, float billBeforeDiscount, float billAfterDiscount,ArrayList<String> itemIds) 
    {   
		this.billBeforeDiscount = billBeforeDiscount;
		this.billAfterDiscount = billAfterDiscount;
		if(this.billBeforeDiscount != this.billAfterDiscount)
			this.discount = true;
        if (customerId < 100){ //throws an exception if the customer id is below 100
        	throw new IllegalArgumentException ("Not a valid customer id");
        }
        this.customerId =customerId;
        this.itemIds = itemIds;
    }

	public Customer(int customerId) 
    {   
      
        if (customerId < 100){ 
        	throw new IllegalArgumentException ("Not a valid customer id");
        }

        this.customerId =customerId;

        this.itemIds = new ArrayList<>();

    }
	//Get and set methods
	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) { 
		this.customerId = customerId;
	}
	
	public void setDiscount(boolean isDiscount) {
		discount = isDiscount;
	}

	public boolean getDiscounts() {
		return discount;
	}
	public ArrayList<String> getItemIds() { 
		return itemIds;
	}


	
	public void setItemIds(ArrayList<String> itemIds) { 
	  this.itemIds = itemIds;
	}

	

	public float getBillBeforeDiscount() {
		
		return billBeforeDiscount;
	}
	
	
	public float getBillAfterDiscount() {
			
			return billAfterDiscount;
		}
	


    public String toString()
    {
    	String ordersString = "";

    	for (String s : itemIds)
    	{
    	    ordersString += s + "\t";
    	}
        return String.format("%-5s", customerId ) + String.format("%-20s", ordersString ) + String.format("%-20s", String.valueOf(discount) ) +
                 String.valueOf( billBeforeDiscount) ;
    }

}
