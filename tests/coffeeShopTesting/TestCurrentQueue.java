package coffeeShopTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import coffeShop.*;
import interfaces.Observer;
import model.CurrentQueue;
import views.DisplayWaitingQueue; 

public class TestCurrentQueue {
	
	private CurrentQueue currentQueue;
	private Customer[] serverCust = new Customer[4];
	
	private List<Observer> registeredObservers;

	@Before
	public void setUp() throws Exception {
		currentQueue = new CurrentQueue();
		registeredObservers = new LinkedList<Observer>();
	}

	@Test
	public void testCurrentQueue() {
		int sizeWaitingQueue, sizeCollectionQueue = 0;
		sizeWaitingQueue = currentQueue.getSizeWaitingQueue();
		sizeCollectionQueue = currentQueue.getSizeCollectionQueue();
		assertEquals(sizeWaitingQueue, 0);
		assertEquals(sizeCollectionQueue, 0);
	}

	@Test
	public void testAddWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		currentQueue.addWaitingQueue(customer1);
		int sizeWaitingQueue = currentQueue.getSizeWaitingQueue();
		assertEquals(sizeWaitingQueue, 1);
	}

	@Test
	public void testAddFirstWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		currentQueue.addWaitingQueue(customer1);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		currentQueue.addFirstWaitingQueue(customer2);
		assertEquals(currentQueue.peekWaitingQueue(), customer2);
	}

	@Test
	public void testAddCollectionQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		currentQueue.addCollectionQueue(customer1);
		int sizeCollectionQueue = currentQueue.getSizeCollectionQueue();
		assertEquals(sizeCollectionQueue, 1);
	}

	@Test
	public void testRemoveTopCollectionQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		currentQueue.addCollectionQueue(customer1);
		currentQueue.addCollectionQueue(customer2);
		currentQueue.removeTopCollectionQueue();
		assertEquals(currentQueue.peekCollectionQueue(), customer2);
	}

	@Test
	public void testSetAndGetServerCustomer() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");
		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		currentQueue.setServerCustomer(2, customer1);
		assertEquals(currentQueue.getServerCustomer(2), customer1);
	}
	@Test
	public void testEmptyCollectionQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		currentQueue.addCollectionQueue(customer1);
		currentQueue.addCollectionQueue(customer2);
		int sizeCollectionQueue = currentQueue.getSizeCollectionQueue();
		assertEquals(sizeCollectionQueue, 2);
		currentQueue.emptyCollectionQueue();
		sizeCollectionQueue = currentQueue.getSizeCollectionQueue();
		assertEquals(sizeCollectionQueue, 0);
	} 

	@Test
	public void testRemoveTopWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		currentQueue.addWaitingQueue(customer1);
		currentQueue.addWaitingQueue(customer2);
		currentQueue.removeTopWaitingQueue();
		assertEquals(currentQueue.peekWaitingQueue(), customer2);
	}

	@Test
	public void testGetTopOfWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		currentQueue.addWaitingQueue(customer1);
		currentQueue.addWaitingQueue(customer2);
		assertEquals(currentQueue.getTopOfWaitingQueue(), currentQueue.peekWaitingQueue());
	}

	@Test
	public void testIsWaitingQueueEmpty() {
		assertTrue(currentQueue.isWaitingQueueEmpty());
	}

	@Test
	public void testPrintWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		currentQueue.addWaitingQueue(customer1);
		String expected = "Customer id: 120\n";
		assertEquals(expected, currentQueue.printWaitingQueue());
	}

	@Test
	public void testPrintCollectionQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		currentQueue.addCollectionQueue(customer1);
		String expected = "Customer id: 120\n";
		assertEquals(expected, currentQueue.printCollectionQueue());
	}

	@Test
	public void testClearServer() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");
		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		currentQueue.setServerCustomer(2, customer1);
		assertEquals(currentQueue.getServerCustomer(2), customer1);
		currentQueue.clearServer(2);
		assertEquals(currentQueue.getServerCustomer(2), null);
	}

	@Test
	public void testPrintQueue_Waiting() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f, itemIds, false);
		Customer customer2 = new Customer(121, 25.6f, 22.4f, itemIds, false);
		Customer customer3 = new Customer(122, 25.6f, 22.4f, itemIds, false);
		Customer customer4 = new Customer(123, 25.6f, 22.4f, itemIds, false);
		Customer customer5 = new Customer(124, 25.6f, 22.4f, itemIds, false);
		Customer customer6 = new Customer(125, 25.6f, 22.4f, itemIds, false);
		Customer customer7 = new Customer(126, 25.6f, 22.4f, itemIds, false);
		Customer customer8 = new Customer(127, 25.6f, 22.4f, itemIds, false);
		Customer customer9 = new Customer(128, 25.6f, 22.4f, itemIds, false);
		Customer customer10 = new Customer(129, 25.6f, 22.4f, itemIds, false);
		currentQueue.addWaitingQueue(customer1);
		currentQueue.addWaitingQueue(customer2);
		currentQueue.addWaitingQueue(customer3);
		currentQueue.addWaitingQueue(customer4);
		currentQueue.addWaitingQueue(customer5);
		currentQueue.addWaitingQueue(customer6);
		currentQueue.addWaitingQueue(customer7);
		currentQueue.addWaitingQueue(customer8);
		currentQueue.addWaitingQueue(customer9);
		currentQueue.addWaitingQueue(customer10);
		String expected = "Customer id: 120\nCustomer id: 121\nCustomer id: 122\nCustomer id: 123\nCustomer id: 124\nCustomer id: 125\nCustomer id: 126\nCustomer id: 127\n";
		assertEquals(expected, currentQueue.printQueue(currentQueue.getWaitingQueue()));
	}

	@Test
	public void testPrintQueue_Collection() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f, itemIds, false);
		Customer customer2 = new Customer(121, 25.6f, 22.4f, itemIds, false);
		Customer customer3 = new Customer(122, 25.6f, 22.4f, itemIds, false);
		Customer customer4 = new Customer(123, 25.6f, 22.4f, itemIds, false);
		Customer customer5 = new Customer(124, 25.6f, 22.4f, itemIds, false);
		Customer customer6 = new Customer(125, 25.6f, 22.4f, itemIds, false);
		Customer customer7 = new Customer(126, 25.6f, 22.4f, itemIds, false);
		Customer customer8 = new Customer(127, 25.6f, 22.4f, itemIds, false);
		Customer customer9 = new Customer(128, 25.6f, 22.4f, itemIds, false);
		Customer customer10 = new Customer(129, 25.6f, 22.4f, itemIds, false);
		currentQueue.addCollectionQueue(customer1);
		currentQueue.addCollectionQueue(customer2);
		currentQueue.addCollectionQueue(customer3);
		currentQueue.addCollectionQueue(customer4);
		currentQueue.addCollectionQueue(customer5);
		currentQueue.addCollectionQueue(customer6);
		currentQueue.addCollectionQueue(customer7);
		currentQueue.addCollectionQueue(customer8);
		currentQueue.addCollectionQueue(customer9);
		currentQueue.addCollectionQueue(customer10);
		String expected = "Customer id: 120\nCustomer id: 121\nCustomer id: 122\nCustomer id: 123\nCustomer id: 124\nCustomer id: 125\nCustomer id: 126\nCustomer id: 127\n";
		assertEquals(expected, currentQueue.printQueue(currentQueue.getCollectionQueue()));
	}
	
	@Test
	public void testShowCustomer() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f, itemIds, false);
		currentQueue.addWaitingQueue(customer1);
		String expected = "id: 120\nitems: Lrg Mocha\nBlueberry Muffin" + "\n\n\n" + " total before discount: £25.6\n total after discount: £22.4\n";
		System.out.println(currentQueue.showCustomer(customer1));
		assertEquals(expected, currentQueue.showCustomer(customer1));
	}

	@Test
	public void testRegisterObserver() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testRemoveObserver() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testNotifyObservers() {
		fail("Not yet implemented"); // TODO
	}

}
