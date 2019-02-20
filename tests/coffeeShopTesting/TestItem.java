package coffeeShopTesting;

import static org.junit.Assert.*;

import org.junit.Test;

import coffeShop.*;

import org.junit.Test;

public class TestItem {

	//Test that a new item is created and contains a valid id
	@Test
	public void testGetId() throws InvalidIdException { //makes sure that the new item is created 
		Item item1 = new Item("HOT012","White chocolate",2.5f,"HotDrink","Regular hot chocolate");
	    String expectedId = "HOT012";
	    String actualId = item1.getId();
	    String message=  "Failed for id = HOT012";
	    assertEquals(message, expectedId, actualId);
		}
	//Checkes whether the two items are equals or not
	@Test
	public void testEquals() throws InvalidIdException {
		 Item item1 = new Item("HOT012","White chocolate",2.5f,"HotDrink","Regular hot chocolate");
		 Item item2 =  new Item("HOT012","White chocolate",2.5f,"HotDrink","Regular hot chocolate");
		 Item item3 =  new Item("HOT008","Reg Latte",2.5f,"HotDrink","Regular hot chocolate");
		  
		 assertTrue(item1.equals(item2));
		 assertFalse(item1.equals(item3));
		 
	
		 
	}
	
	

    //Test to make sure the item's id is of the right format
    @Test
    public void testItemId() throws InvalidIdException
    {
		try {
			Item item = new Item("CAT001","Candy",2.5f,"HotDrink","Regular white chocolate"); //the id does not begin with the right header
			fail("Invalid id supplied - should throw exception"); 

		}
		catch (InvalidIdException e) {
			assertTrue(e.getMessage().contains("ItemId must contain a category header (DES, HOT, CLD, ADD, FOD) and a 3 digit number"));
		}

    }
    
    //Test that the name of the item is not empty
    @Test
    public void testItemName() throws InvalidIdException
    {
		try {
			Item item = new Item("FOD001","",2.5f,"HotDrink","Regular white chocolate"); 
			fail("Invalid id supplied - should throw exception");  
		}

		catch (IllegalStateException e) {
			assertTrue(e.getMessage().contains("Cannot have a blank id, name or category")); 
		}
    }
    
    
    //Test for invalid prize
	@Test(expected = IllegalStateException.class) 
	public  void invalidPrize() throws InvalidIdException {
		Item item = new Item("FOD001","Tuna sandwich",-3f,"Food","Tuna and salad on white bread");
	}
	




}