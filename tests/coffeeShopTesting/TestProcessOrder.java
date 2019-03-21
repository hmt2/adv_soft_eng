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

import exceptions.DuplicateIDException;
import exceptions.IdNotContainedException;
import ordering.AllOrders;
import ordering.ProcessOrder;
import ordering.CustomerList;
import ordering.Menu;
import preparing.*;

public class TestProcessOrder {
	private ProcessOrder processOrder;
	private Menu menu;
	AllOrders allorders;
	private Map<String, Integer> currentOrder;
	CustomerList customerList;

	@Before
	public void setUp() throws DuplicateIDException, IdNotContainedException { //this is to make sure that the test can add well! What to put in the setup method? one setup for all tests?
		processOrder = new ProcessOrder(new Menu());
		customerList = processOrder.getCustomerList();
		allorders = processOrder.getAllOrders();
		menu = processOrder.getMenu();
		currentOrder = new LinkedHashMap<String, Integer>();
		currentOrder.put("DES006", 2);
	}
	
	
	
	//Test that quantity of the item is updated correctly when an order is placed
	@Test
	public void testUpdateQuantity() throws IdNotContainedException  { 
		int initialQuantity = menu.findItemId("DES006").getQuantity();
	    int expectedQuantity = 2;
	    processOrder.updateItemQuantity(currentOrder);
	    int actualQuantity = menu.findItemId("DES006").getQuantity();
	    String message ="Value updated succesfully";
		assertEquals(message, actualQuantity, expectedQuantity ); 
        assertTrue(actualQuantity > initialQuantity); //make sure that when updating the value has increased

	}

	
	@After
	public void tearDown() throws DuplicateIDException, IdNotContainedException {
		processOrder = new ProcessOrder(new Menu());
	}
	
	
	

}
