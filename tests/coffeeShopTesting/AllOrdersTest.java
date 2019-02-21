package coffeeShopTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.jupiter.api.*;

import coffeShop.AllOrders;
import coffeShop.Order;

class AllOrdersTest {

	private static final Integer orderId = new Integer(7);
	private static final Integer customerId = new Integer(110);
	private static final String itemId = "FOD001";
	private static final Timestamp timestamp = Timestamp.valueOf("2018-11-12 01:02:02.123456789");
	private ArrayList<Order> orders;
	private AllOrders allOrders;
	
	
	
	@BeforeEach
	void setup() {
		allOrders = new AllOrders();
		AllOrders.loadOrders();
	}
	
	@Test
	void testAddOrders() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckOrderId() {
//		assertTrue(allOrders.containsKey(orderId));
	}
	
	@Test
	void testReturnAllOrder() {
		fail("Not yet implemented");
	}
	
	
	@Test
	void testFindOrderId() {
		fail("Not yet implemented");
	}
	
	@Test
	void testFindCustomerOrders() {
		ArrayList<String> items = new ArrayList<>();
		items.add("FOD666");
		Integer orderId = new Integer(666);
		allOrders.addOrder(orderId, items);
		ArrayList<Integer> order = AllOrders.findCustomerOrders(orderId);
		assertTrue(order.get(0).equals(orderId));
	}
	
	@Test
	void testListByOrderId() {
		fail("Not yet implemented");
	}
	
	@Test
	void testListByTimestamp() {
		fail("Not yet implemented");
	}
	
	@Test
	void testListByCustomerId() {
		fail("Not yet implemented");
	}
	
	@Test
	void testListByItemId() {
		fail("Not yet implemented");
	}
	
	@Test
	void testPrintAllOrders() {
		fail("Not yet implemented");
	}
	
	@Test
	void testGetNumOrders() {
		fail("Not yet implemented");
	}
	

}
