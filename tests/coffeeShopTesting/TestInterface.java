package coffeeShopTesting;
import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;













import coffeShop.*;

public class TestInterface {
	private CoffeShopInterface shop;
	private Menu menu;
	AllOrders allorders;
	private Map<String, Integer> currentOrder;
	CustomerList customerList;

	@Before
	public void setUp() throws DuplicateIDException, IdNotContainedException { //this is to make sure that the test can add well! What to put in the setup method? one setup for all tests?
		shop = new CoffeShopInterface(new Menu());
		customerList = shop.getCustomerList();
		allorders = shop.getAllOrders();
		menu = shop.getMenu();
		currentOrder = new LinkedHashMap<String, Integer>();
		currentOrder.put("DES006", 2);

	}
	
	//Test that the right number of customers is created when adding the previous orders
	@Test
	public void testAddPreviousOrders() { 
	    int expectedSize = allorders.loadOrders().size(); //The number of expected customers
	    int actualSize = customerList.size(); //actual number of customers created
	    String message ="Customers added succesfully";
		assertEquals(message, expectedSize, actualSize);


	}
	
	//Test that one new customer is added and the expected number of orders in allorders is increased when placing an order
	@Test
	public void testPlaceOrder() throws DuplicateIDException, IdNotContainedException {  

		currentOrder.put("DES001", 1);
		shop.displayBill(currentOrder, false); //required in order to calculate the bills within the class CoffeShopInterface
		int initialOrders= allorders.getNumOrders(); //initial size of allOrders
		int inCustSize = customerList.size(); //initial customer size
		shop.placeOrder(currentOrder);
		int finCustSize = customerList.size(); //sizes of customerList and allorders after placing a new order
		int finalOrders = allorders.getNumOrders();
		int nOrdersExpected = 0;
		for(String key: currentOrder.keySet()){ 
			nOrdersExpected+=currentOrder.get(key);
		}
		int nOrdersActual = finalOrders-initialOrders;
		String message ="Orders added succesfully";
		assertEquals(message, 1, finCustSize-inCustSize ); //make sure that the number of customers has increased by one
		assertEquals(message, nOrdersActual, nOrdersExpected);


	}
	//Test that quantity of the item is updated correctly when an order is placed
	@Test
	public void testUpdateQuantity() throws IdNotContainedException  { 
		int initialQuantity = menu.findItemId("DES006").getQuantity();
	    int expectedQuantity = 2;
	    shop.updateItemQuantity(currentOrder);
	    int actualQuantity = menu.findItemId("DES006").getQuantity();
	    String message ="Value updated succesfully";
		assertEquals(message, actualQuantity, expectedQuantity ); 
        assertTrue(actualQuantity > initialQuantity); //make sure that when updating the value has increased

	}

	
	@After
	public void tearDown() throws DuplicateIDException, IdNotContainedException {
		shop = new CoffeShopInterface(new Menu());
	}
	
	
	

}
