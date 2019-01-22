
public class Customer implements Comparable<Customer>
{
	private String customerId;
	private String name;
    private float bill;
	
	public Customer(String customerId, String name)
    {   
        this.customerId =customerId.trim();
        this.name = name.trim();
    }

	/**
     * @return The customer Id.
     */
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	/**
     * @return The name of the customer.
     */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	/**
     * @return The total cost of the orders / bill of the customer.
     */
	public float getBill() {
		return bill;
	}

	public void setBill(float bill) {
		this.bill = bill;
	}
	
	/**
     * @return Add comments later
     */
	public int compareTo(Customer otherDetails)
    {
        return customerId.compareTo(otherDetails.getCustomerId());
    }    

    /**
     * @return A  string containing all details.
     */
    public String toString()
    {
        return String.format("%-5s", customerId ) + String.format("%-20s", name) +
                 String.format("%5d", bill );
    }
	
    public void calculateBill()// returns the bill of the customer
    {
    
    }

}
