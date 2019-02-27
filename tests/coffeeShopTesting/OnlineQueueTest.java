package coffeeShopTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import coffeShop.*;


public class OnlineQueueTest {

	private OnlineQueue onlineQueue; 
	private AllOrders allorders;
	
	@Before
	public void setUp() throws Exception {
		
		allorders = new AllOrders();
		
	}

	@Test
	public void loadOnlineQueueTest() {
		allorders.loadOrders();
		onlineQueue = new OnlineQueue(allorders);
		onlineQueue.loadOnlineQueue();
		onlineQueue.displayOnlineQueue();
		assertEquals(allorders.returnAllOrders().size(),onlineQueue.returnOnlineQueue().size());
	}
	

}
