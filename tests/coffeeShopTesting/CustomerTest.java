package coffeeShopTesting;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ordering.Customer;
import preparing.*; 
public class CustomerTest {
    private ArrayList<String> itemIds;
	@Before
	public void setUp() {
		itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");
	}

	// We are checking if the customer is created with a valid customerId which needs to be over 100

	@Test
	public void testGetId() {
		ordering.Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false) ;
	    int expectedId = 120;
	    int actualId = customer1.getCustomerId();
	    String message=  "Failed for id = 120";
	    assertEquals(message, expectedId, actualId);
		}
	//we are trying the illegal exception
	@Test
	public void test_fail_exception() {

		try {
			ordering.Customer customer = new Customer(93, 25.6f, 22.4f,itemIds,false);
		    fail("should throw exception");
		  } catch(IllegalArgumentException e) {
		    // expected
		  }	
	}

}