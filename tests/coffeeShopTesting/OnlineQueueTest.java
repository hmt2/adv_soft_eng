package coffeeShopTesting;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import coffeShop.*;


public class OnlineQueueTest {

	private OnlineQueue onlineQueue; 
	AllOrders allOrders;
	CustomerList customerList;
	Menu menu;
	CoffeShopInterface coffeShopInterface;

	
	
	
	@Before
	public void setUp() throws Exception {
		this.menu = menu;
		coffeShopInterface = new CoffeShopInterface(menu);
		customerList= coffeShopInterface.getCustomerList();	
		onlineQueue = new OnlineQueue(customerList);
		
	}

	@Test
	public void loadOnlineQueueTest() throws DuplicateIDException {
		//customerList.loadCustomers();
		onlineQueue.loadOnlineQueue();
		onlineQueue.displayOnlineQueue();

		assertEquals(customerList.customers().size(),onlineQueue.returnOnlineQueue().size());
	}
	
	@Test
	public void removeHeadTest() throws DuplicateIDException {
		onlineQueue.loadOnlineQueue();
		int beforeRemoveSize = onlineQueue.returnOnlineQueue().size();
		onlineQueue.removeHead();
		assertEquals(beforeRemoveSize-1,onlineQueue.returnOnlineQueue().size());
	}
	
	

}
