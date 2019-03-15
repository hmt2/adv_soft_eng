package coffeeShopTesting;

import coffeShop.*;
import exceptions.IdNotContainedException;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestMenuNotLoaded {
	private Menu menu;

	@Before
	public void setUp() throws Exception {
		menu = new Menu();
		menu.getLinkedHashMap().clear();
	}

	// MENU NOT LOADED
	
	@Test (expected = NullPointerException.class)
	public final void testFindItemId_notloaded() throws IdNotContainedException {
		
		Item item;
		item = menu.findItemId("HOT001");
		assertEquals(null, item);
		
		Item item2;
		item2 = menu.findItemId("HOT016");
		assertEquals(null, item2);
	}

	@Test (expected = NullPointerException.class)
	public final void testGetSize_notloaded() {
		int size;
		size = menu.getSize();
		assertEquals(0, size);
	}
	
	
	@Test (expected = NullPointerException.class)
	public final void testFindItemName_notloaded() throws IdNotContainedException {
		Item item;
		item = menu.findItemName("Breakfast Tea");
		assertEquals(null, item);
	}

	@Test (expected = NullPointerException.class)
	public final void testGetKeySet_notloaded() {
		menu.getKeySet();
	}

	@Test (expected = NullPointerException.class)
	public final void testListByItemId() {
		menu.listByItemId();
	}

	@Test (expected = NullPointerException.class)
	public final void testListByItemName() {
		menu.listByItemName(); 
	}

	@Test (expected = NullPointerException.class)
	public final void testListByItemCategory() {
		menu.listByItemCategory();
	}

}
