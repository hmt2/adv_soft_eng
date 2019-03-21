package coffeeShopTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import interfaces.Observer;
import model.DisplayModel;
import ordering.Customer;
import preparing.*;
import views.DisplayWaitingQueue; 

public class TestDisplayModel {
	
	private DisplayModel displayModel;
	private Customer[] serverCust = new Customer[4];
	
	private List<Observer> registeredObservers;

	@Before
	public void setUp() throws Exception {
		displayModel = new DisplayModel();
		registeredObservers = new LinkedList<Observer>();
	}

	@Test
	public void testCurrentQueue() {
		int sizeWaitingQueue, sizeCollectionQueue = 0;
		sizeWaitingQueue = displayModel.getSizeWaitingQueue();
		sizeCollectionQueue = displayModel.getSizeCollectionQueue();
		assertEquals(sizeWaitingQueue, 0);
		assertEquals(sizeCollectionQueue, 0);
	}

	@Test
	public void testAddWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayModel.addWaitingQueue(customer1);
		int sizeWaitingQueue = displayModel.getSizeWaitingQueue();
		assertEquals(sizeWaitingQueue, 1);
	}

	@Test
	public void testAddFirstWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayModel.addWaitingQueue(customer1);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		displayModel.addFirstWaitingQueue(customer2);
		assertEquals(displayModel.peekWaitingQueue(), customer2);
	}

	@Test
	public void testAddCollectionQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayModel.addCollectionQueue(customer1);
		int sizeCollectionQueue = displayModel.getSizeCollectionQueue();
		assertEquals(sizeCollectionQueue, 1);
	}

	@Test
	public void testRemoveTopCollectionQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		displayModel.addCollectionQueue(customer1);
		displayModel.addCollectionQueue(customer2);
		displayModel.removeTopCollectionQueue();
		assertEquals(displayModel.peekCollectionQueue(), customer2);
	}

	@Test
	public void testSetAndGetServerCustomer() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");
		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayModel.setServerCustomer(2, customer1);
		assertEquals(displayModel.getServerCustomer(2), customer1);
	}
	@Test
	public void testEmptyCollectionQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		displayModel.addCollectionQueue(customer1);
		displayModel.addCollectionQueue(customer2);
		int sizeCollectionQueue = displayModel.getSizeCollectionQueue();
		assertEquals(sizeCollectionQueue, 2);
		displayModel.emptyCollectionQueue();
		sizeCollectionQueue = displayModel.getSizeCollectionQueue();
		assertEquals(sizeCollectionQueue, 0);
	} 

	@Test
	public void testRemoveTopWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		displayModel.addWaitingQueue(customer1);
		displayModel.addWaitingQueue(customer2);
		displayModel.removeTopWaitingQueue();
		assertEquals(displayModel.peekWaitingQueue(), customer2);
	}

	@Test
	public void testGetTopOfWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		Customer customer2 = new Customer(121, 30.2f, 28.4f,itemIds, true);
		displayModel.addWaitingQueue(customer1);
		displayModel.addWaitingQueue(customer2);
		assertEquals(displayModel.getTopOfWaitingQueue(), displayModel.peekWaitingQueue());
	}

	@Test
	public void testIsWaitingQueueEmpty() {
		assertTrue(displayModel.isWaitingQueueEmpty());
	}

	@Test
	public void testPrintWaitingQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayModel.addWaitingQueue(customer1);
		String expected = "Customer id: 120\n";
		assertEquals(expected, displayModel.printWaitingQueue());
	}

	@Test
	public void testPrintCollectionQueue() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayModel.addCollectionQueue(customer1);
		String expected = "Customer id: 120\n";
		assertEquals(expected, displayModel.printCollectionQueue());
	}

	@Test
	public void testClearServer() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");
		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds, false);
		displayModel.setServerCustomer(2, customer1);
		assertEquals(displayModel.getServerCustomer(2), customer1);
		displayModel.clearServer(2);
		assertEquals(displayModel.getServerCustomer(2), null);
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
		displayModel.addWaitingQueue(customer1);
		displayModel.addWaitingQueue(customer2);
		displayModel.addWaitingQueue(customer3);
		displayModel.addWaitingQueue(customer4);
		displayModel.addWaitingQueue(customer5);
		displayModel.addWaitingQueue(customer6);
		displayModel.addWaitingQueue(customer7);
		displayModel.addWaitingQueue(customer8);
		displayModel.addWaitingQueue(customer9);
		displayModel.addWaitingQueue(customer10);
		String expected = "Customer id: 120\nCustomer id: 121\nCustomer id: 122\nCustomer id: 123\nCustomer id: 124\nCustomer id: 125\nCustomer id: 126\nCustomer id: 127\n";
		assertEquals(expected, displayModel.printQueue(displayModel.getWaitingQueue()));
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
		displayModel.addCollectionQueue(customer1);
		displayModel.addCollectionQueue(customer2);
		displayModel.addCollectionQueue(customer3);
		displayModel.addCollectionQueue(customer4);
		displayModel.addCollectionQueue(customer5);
		displayModel.addCollectionQueue(customer6);
		displayModel.addCollectionQueue(customer7);
		displayModel.addCollectionQueue(customer8);
		displayModel.addCollectionQueue(customer9);
		displayModel.addCollectionQueue(customer10);
		String expected = "Customer id: 120\nCustomer id: 121\nCustomer id: 122\nCustomer id: 123\nCustomer id: 124\nCustomer id: 125\nCustomer id: 126\nCustomer id: 127\n";
		assertEquals(expected, displayModel.printQueue(displayModel.getCollectionQueue()));
	}
	
	@Test
	public void testShowCustomer() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f, itemIds, false);
		displayModel.addWaitingQueue(customer1);
		String expected = "id: 120\nitems: Lrg Mocha\nBlueberry Muffin" + "\n\n\n" + " total before discount: £25.6\n total after discount: £22.4\n";
		System.out.println(displayModel.showCustomer(customer1));
		assertEquals(expected, displayModel.showCustomer(customer1));
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
