package coffeShop;


import java.sql.Timestamp;

public class Order implements Comparable<Order>
{
	private Integer orderId; 
	private Integer customerId;
	private String itemId;
	private Timestamp timestamp;
	
	public Order(Integer orderId, Integer customerId,  String itemId, Timestamp timestamp) throws InvalidIdException
    {   
		if(orderId.toString().length() == 0 || customerId.toString().length() == 0 || itemId.length() == 0 || timestamp.toString().length() == 0) {
			throw new IllegalStateException("Cannot have a blank orderId, customerId, itemId or timestamp");
		}
		if (itemId.length()!= 6) {
			throw new InvalidIdException ("ItemId must contain a category header (DES, HOT, CLD, ADD, FOD) and a 3 digit number");
		}
        this.orderId = orderId;
        this.customerId = customerId;
        this.itemId = itemId.trim();
        this.timestamp = timestamp;
    }

	public Integer getOrderId() {
		return orderId;
	}


	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	

	public Integer getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}


	public String getItemId() {
		return itemId;
	}


	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


	/**
     * @return Add comments later
     */
	public int compareToOrderId(Order otherDetails)
    {
        return orderId.compareTo(otherDetails.getOrderId());
    }
	
	/**
     * @return Add comments later
     */
	public int compareToCustomerId(Order otherDetails)
    {
		int compare = customerId.compareTo(otherDetails.getCustomerId());
        return -compare;
    }  
	
	public int compareToTimestamp(Order otherDetails)
    {
		if (timestamp.getTime() > otherDetails.getTimestamp().getTime()) { //if after otherDetails
			return 1;
		}
		else if (timestamp.getTime() < otherDetails.getTimestamp().getTime()) { //if before otherDetails
			return -1;
		}
		return 0;
    } 

    /**
     * @return A  string containing all details.
     */
    public String toString()
    {
        return String.format("%d", orderId ) + " " + String.format("%d ", customerId) + " " + String.format("%s", itemId) + " " + String.format("%1$TD %1$TT", timestamp);
    }

	@Override
	public int compareTo(Order arg0) {
		return 0;
	}
	
}
