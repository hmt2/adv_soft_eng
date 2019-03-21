package coffeeShopTesting;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import controllers.DisplayController;
import model.DisplayModel;
import ordering.Customer;
import preparing.*;
import views.DisplayGUI; 

public class TestDisplayController {
	
	private DisplayModel model = new DisplayModel(); 
	private DisplayGUI view = new DisplayGUI(model); 
	private DisplayController displayController;

	@Before
	public void setUp() throws Exception {
		displayController = new DisplayController(view, model);
	}

	@Test
	public void testGetTopOfWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		displayController.addWaitingQueue(customer1);
		displayController.addWaitingQueue(customer2);
		assertEquals(displayController.getTopOfWaitingQueue(), displayController.peekWaitingQueue());
	}
	
	@Test
	public void testSetServerCustomer() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");
		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayController.setServerCustomer(2, customer1);
		assertEquals(displayController.getServerCustomer(2), customer1);
	}

	@Test
	public void testRemoveTopWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		displayController.addWaitingQueue(customer1);
		displayController.addWaitingQueue(customer2);
		displayController.removeTopWaitingQueue();
		assertEquals(displayController.peekWaitingQueue(), customer2);
	}
	
	@Test
	public void testClearServer() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");
		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayController.setServerCustomer(2, customer1);
		assertEquals(displayController.getServerCustomer(2), customer1);
		displayController.clearServer(2);
		assertEquals(displayController.getServerCustomer(2), null);
	}

	@Test
	public void testIsEmpty() {
		assertTrue(displayController.isWaitingQueueEmpty());
	}

	@Test
	public void testAddWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayController.addWaitingQueue(customer1);
		int sizeWaitingQueue = displayController.getSizeWaitingQueue();
		assertEquals(sizeWaitingQueue, 1);
	}

	@Test
	public void testAddFirstWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayController.addWaitingQueue(customer1);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		displayController.addFirstWaitingQueue(customer2);
		assertEquals(displayController.peekWaitingQueue(), customer2);
	}

	@Test
	public void testUpdateView() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddCollectionQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayController.addCollectionQueue(customer1);
		int sizeCollectionQueue = displayController.getSizeCollectionQueue();
		assertEquals(sizeCollectionQueue, 1);
	}

	@Test
	public void testRemoveTopCollectionQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		displayController.addCollectionQueue(customer1);
		displayController.addCollectionQueue(customer2);
		displayController.removeTopCollectionQueue();
		assertEquals(displayController.peekCollectionQueue(), customer2);
	}

	@Test
	public void testEmptyCollectionQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		displayController.addCollectionQueue(customer1);
		displayController.addCollectionQueue(customer2);
		int sizeCollectionQueue = displayController.getSizeCollectionQueue();
		assertEquals(sizeCollectionQueue, 2);
		displayController.emptyCollectionQueue();
		sizeCollectionQueue = displayController.getSizeCollectionQueue();
		assertEquals(sizeCollectionQueue, 0);
	}

}
