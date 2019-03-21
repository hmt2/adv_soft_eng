package ordering;

import java.util.ArrayList;
import java.util.List;


public class Customer{

	private int customerId;
	private  String name;

	private static int runningCounter = 0;

	private float billBeforeDiscount;
	private float billAfterDiscount;
	private boolean discount;
	private ArrayList<String> itemIds;  
	private boolean priority; // if the customer has made an order in priority

	public Customer(int customerId, float billBeforeDiscount, float billAfterDiscount,ArrayList<String> itemIds, boolean priority) 
	{   
		this.billBeforeDiscount = billBeforeDiscount;
		this.billAfterDiscount = billAfterDiscount;
		if(this.billBeforeDiscount != this.billAfterDiscount)
			this.discount = true;
		else
			this.discount = false;
		if (customerId < 100){
			//throws an exception if the customer id is below 100
			throw new IllegalArgumentException ("Not a valid customer id");
		}
		this.customerId =customerId;
		this.itemIds = itemIds;
		this.name = name;
		this.priority = priority;
	}

	public Customer(int customerId) 
	{   
		if (customerId < 100){ 
			throw new IllegalArgumentException ("Not a valid customer id");
		}
		this.customerId =customerId;
		this.itemIds = new ArrayList<>();
		this.priority = false; // used for adding the previous orders so the customers have not ordered in priority

	}
	//Get and set methods
	public int getCustomerId() {
		return customerId;
	}

	public String getName(){
		return name;
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