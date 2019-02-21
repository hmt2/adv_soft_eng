package coffeeShopTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.jupiter.api.*;

import coffeShop.InvalidIdException;
import coffeShop.Order;

class OrdersTest {
	
	private static final Integer orderId = new Integer(7);
	private static final Integer customerId = new Integer(110);
	private static final String itemId = "FOD001";
	//private static final Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
	private static final Timestamp timestamp = Timestamp.valueOf("2018-11-12 01:02:02.123456789");
	private Order order;
	
	
	@BeforeEach
	void setup() {
		try {
			order = new Order(OrdersTest.orderId, OrdersTest.customerId, OrdersTest.itemId, OrdersTest.timestamp);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
	}

	@Test
	void testGetOrderId() {
		assertTrue(OrdersTest.orderId.equals(order.getOrderId()));
	}
	
	@Test
	void testSetOrderId() {
		Integer i = new Integer(6);
		order.setOrderId(i);
		assertTrue(i.equals(order.getOrderId()));
	}
	
	@Test
	void testGetCustomerId() {
		assertTrue(OrdersTest.customerId.equals(order.getCustomerId()));
	}
	
	@Test
	void testSetCustomerId() {
		Integer i = new Integer(101);
		order.setCustomerId(i);
		assertTrue(i.equals(order.getCustomerId()));
	}
	
	@Test
	void testGetItemId() {
		assertTrue(OrdersTest.itemId.equals(order.getItemId()));
	}
	
	@Test
	void testSetItemId() {
		String i = "FOD016";
		order.setItemId(i);
		assertTrue(i.equals(order.getItemId()));
	}
	
	@Test
	void testGetTimestamp() {
		assertTrue(OrdersTest.timestamp.equals(order.getTimestamp()));
	}
	
	@Test
	void testSetTimestamp() {
		Timestamp t = new Timestamp(Calendar.getInstance().getTimeInMillis());
		order.setTimestamp(t);
		assertTrue(t.equals(order.getTimestamp()));
		
	}
	
	@Test
	void testCompareToCustomerId() {
		Order order = null;
		try {
			order = new Order(OrdersTest.orderId, new Integer(103), OrdersTest.itemId, OrdersTest.timestamp);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
		
		assertEquals(-1, this.order.compareToCustomerId(order));
		
		try {
			order = new Order(OrdersTest.orderId, new Integer(120), OrdersTest.itemId, OrdersTest.timestamp);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
		
		assertEquals(1, this.order.compareToCustomerId(order));
		
		try {
			order = new Order(OrdersTest.orderId, new Integer(110), OrdersTest.itemId, OrdersTest.timestamp);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
		
		assertEquals(0, this.order.compareToCustomerId(order));
	}
	
	
	@Test
	void testCompareToTimestamp(){
		Order order = null;
		try {
			order = new Order(OrdersTest.orderId, OrdersTest.customerId, OrdersTest.itemId, Timestamp.valueOf("2018-11-13 01:03:02.123456789"));
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
		
		assertEquals(-1, this.order.compareToTimestamp(order));
		
		try {
			order = new Order(OrdersTest.orderId, OrdersTest.customerId, OrdersTest.itemId, Timestamp.valueOf("2018-11-11 01:04:02.123456789"));
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
		
		assertEquals(1, this.order.compareToTimestamp(order));
		
		try {
			order = new Order(OrdersTest.orderId, OrdersTest.customerId, OrdersTest.itemId, OrdersTest.timestamp);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
		
		assertEquals(0, this.order.compareToTimestamp(order));
		
		
	}
	
	
	@Test
	void testCompareToOrderId() {
		Order order = null;
		try {
			order = new Order(new Integer(5), OrdersTest.customerId, OrdersTest.itemId, OrdersTest.timestamp);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
		
		assertTrue(this.order.compareToOrderId(order) > 0);
		
		try {
			order = new Order(new Integer(8), OrdersTest.customerId, OrdersTest.itemId, OrdersTest.timestamp);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
		
		assertTrue(this.order.compareToOrderId(order) < 0);
		
		try {
			order = new Order(OrdersTest.orderId, OrdersTest.customerId, OrdersTest.itemId, OrdersTest.timestamp);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
		
		assertTrue(this.order.compareToOrderId(order) == 0);
	}

}
