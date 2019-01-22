import java.util.Date;
import java.sql.Timestamp;

public class Order implements Comparable<Order>
{
	private String orderId; 
	private String orderItemId;
	private String orderCustomerId;
	private Timestamp timestamp;
	
	public Order(String orderid, String orderItemId, Timestamp timestamp)
    {   
        this.orderId =orderId.trim();
        this.orderItemId = orderItemId.trim();
        this.timestamp = timestamp;
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getOrderCustomerId() {
		return orderCustomerId;
	}

	public void setOrderCustomerId(String orderCustomerId) {
		this.orderCustomerId = orderCustomerId;
	}
	/**
     * @return Add comments later
     */
	public int compareTo(Order otherDetails)
    {
        return orderId.compareTo(otherDetails.getOrderId());
    }    

    /**
     * @return A  string containing all details.
     */
    public String toString()
    {
        return String.format("%-5s", orderId ) + String.format("%-20s", orderItemId) +
        		String.format("%-20s", orderCustomerId) + String.format("%5d", timestamp );
    }
    
    
	
	
	
}
