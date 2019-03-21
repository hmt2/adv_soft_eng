package coffeeShopTesting;

import exceptions.IdNotContainedException;
import ordering.Item;
import ordering.Menu;
import preparing.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestMenuLoaded {
	private Menu menu;

	@Before
	public void setUp() throws Exception {
		menu = new Menu();
		menu.loadMenu();
	}	

//	@Test
//	public final void testMenu() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testLoadMenu() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public final void testGetEntrySet() {
//		fail("Not yet implemented"); // TODO
//	}


	//MENU LOADED
	
	@Test
	public final void testFindItemId_loaded() throws IdNotContainedException {
		Item item;
		String itemId;
	    String itemName;
		float itemPrice;
		int itemQuantity;
		String itemCategory;
		String itemDescription;
		
		item = menu.findItemId("HOT001");

		itemId = item.getId();
		itemName = item.getName();
		itemPrice = item.getPrice();
		itemQuantity = item.getQuantity();
		itemCategory = item.getCategory();
		itemDescription = item.getDescription();
		assertEquals("HOT001", itemId);
		assertEquals("Breakfast Tea", itemName);
		assertEquals(2, itemPrice, 0.005);
		assertEquals(0, itemQuantity);
		assertEquals("HotDrink", itemCategory);
		assertEquals("Pot of English breakfast tea", itemDescription);
	}
	
	@Test (expected = IdNotContainedException.class)
	public final void testFindItemId_loaded_fail() throws IdNotContainedException {
		Item item2;
		item2 = menu.findItemId("HOT016");
		assertEquals(null, item2);
	}

	@Test
	public final void testGetSize_loaded() {
		int size;
		size = menu.getSize();
		assertEquals(52, size);
	}

	@Test
	public final void testFindItemName_loaded() throws IdNotContainedException {
		Item item;
		String itemId;
	    String itemName;
		float itemPrice;
		int itemQuantity;
		String itemCategory;
		String itemDescription;
		
		item = menu.findItemName("Breakfast Tea");
		itemId = item.getId();
		itemName = item.getName();
		itemPrice = item.getPrice();
		itemQuantity = item.getQuantity();
		itemCategory = item.getCategory();
		itemDescription = item.getDescription();
		assertEquals("HOT001", itemId);
		assertEquals("Breakfast Tea", itemName);
		assertEquals(2, itemPrice, 0.005);
		assertEquals(0, itemQuantity);
		assertEquals("HotDrink", itemCategory);
		assertEquals("Pot of English breakfast tea", itemDescription);
	}
	
	@Test (expected = IdNotContainedException.class)
	public final void testFindItemName_loaded_fail() throws IdNotContainedException {
		Item item;
		item = menu.findItemName("Breakfat Te");
		assertEquals(null, item);
	}

//	@Test
//	public final void testListOutput() {
//		menu.listOutput(sortedMap);
//	}

	@Test
	public final void testListByItemId() {
		System.out.println("Test listByItemId");
		System.out.println(menu.listByItemId());
	}

	@Test
	public final void testListByItemName() {
		System.out.println("Test listByItemName");
		System.out.println(menu.listByItemName());
	}

	@Test
	public final void testListByItemCategory() {
		System.out.println("Test listByItemCategory");
		System.out.println(menu.listByItemCategory());
	}
}
