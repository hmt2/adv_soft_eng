package coffeeShopTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import coffeShop.*;
import controllers.DisplayController;
import model.CurrentQueue;
import views.DisplayGUI; 

class TestDisplayController {
	
	
	private CurrentQueue model = new CurrentQueue(); 
	private DisplayGUI view = new DisplayGUI(model); 
	private DisplayController displayController;

	@BeforeEach
	void setUp() throws Exception {
		displayController = new DisplayController(view, model);
	}

	@Test
	final void testGetTopOfWaitingQueue() {
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
	final void testSetServerCustomer() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");
		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayController.setServerCustomer(2, customer1);
		assertEquals(displayController.getServerCustomer(2), customer1);
	}

	@Test
	final void testRemoveTopWaitingQueue() {
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
	final void testClearServer() {
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
	final void testIsEmpty() {
		assertTrue(displayController.isWaitingQueueEmpty());
	}

	@Test
	final void testAddWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayController.addWaitingQueue(customer1);
		int sizeWaitingQueue = displayController.getSizeWaitingQueue();
		assertEquals(sizeWaitingQueue, 1);
	}

	@Test
	final void testAddFirstWaitingQueue() {
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
	final void testUpdateView() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testAddCollectionQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayController.addCollectionQueue(customer1);
		int sizeCollectionQueue = displayController.getSizeCollectionQueue();
		assertEquals(sizeCollectionQueue, 1);
	}

	@Test
	final void testRemoveTopCollectionQueue() {
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
	final void testEmptyCollectionQueue() {
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
