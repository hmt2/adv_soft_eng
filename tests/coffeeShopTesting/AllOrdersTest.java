package coffeeShopTesting;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import coffeShop.*;


public class AllOrdersTest {
	private static final Integer orderId = new Integer(7);
	private static final Integer customerId = new Integer(110);
	private static final String itemId = "FOD001";
	private static final Timestamp timestamp = Timestamp.valueOf("2018-11-12 01:02:02.123456789");
	private ArrayList<Order> orders;
	private AllOrders allOrders;
	
	
	@Before
	public void setup() {
		allOrders = new AllOrders();
		AllOrders.loadOrders();
	}
	
	@Test
	public void testAddOrders() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckOrderId() {
//		assertTrue(allOrders.containsKey(orderId));
	}
	
	@Test
	public void testReturnAllOrder() {
		fail("Not yet implemented");
	}
	
	
	@Test
	public void testFindOrderId() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testListByOrderId() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testListByTimestamp() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testListByCustomerId() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testListByItemId() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testPrintAllOrders() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetNumOrders() {
		fail("Not yet implemented");
	}
	

}
