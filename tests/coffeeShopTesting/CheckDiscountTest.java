package coffeeShopTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import discounts.AllDiscounts;
import discounts.Discount;
import discounts.DiscountCheck;
import exceptions.DuplicateIDException;
import exceptions.IdNotContainedException;
import ordering.ProcessOrder;
import ordering.Item;
import ordering.Menu;
import preparing.*;

public class CheckDiscountTest {
	private AllDiscounts allDiscounts;
	private DiscountCheck discountCheck;
	private Menu menu;
	ProcessOrder coffeShopInterface;
	@Before
	public void setUp() throws DuplicateIDException, IdNotContainedException { //this is to make sure that the test can add well! What to put in the setup method? one setup for all tests?
		allDiscounts = new AllDiscounts();
	    discountCheck = new DiscountCheck(allDiscounts.loadDiscounts());
	    menu = new Menu();
	    coffeShopInterface = new ProcessOrder(menu); 
	}
	
	@Test
	public void testCalcBillBeforeDiscount() throws IdNotContainedException {
		 ArrayList<String> itemIds = new ArrayList<>();
	     itemIds.add("HOT011");  
		 itemIds.add("DES006");
		 itemIds.add("DES008");
		 
		 float expectedBill = 0;
		 for(int i =0; i < itemIds.size(); i++) {
			 Item currentItem = menu.findItemId(itemIds.get(i));
			 expectedBill += currentItem.getPrice();
		 }
		 
	     float calcBillBeforeDiscount = discountCheck.calcBillBeforeDiscount(itemIds);
	     assertEquals(expectedBill, calcBillBeforeDiscount, 0.0f);
	}
	
	@Test
	public void testNoDiscountPresent() throws IdNotContainedException {
		 ArrayList<String> itemIds = new ArrayList<>();
	     itemIds.add("DES006");  
		 itemIds.add("DES006");
		 
		 float expectedBill = 0;
		 for(int i =0; i < itemIds.size(); i++) {
			 Item currentItem = menu.findItemId(itemIds.get(i));
			 expectedBill += currentItem.getPrice();
		 }
		 float expectedDiscount = expectedBill;
		 
		 float billAfterDiscount = (float) discountCheck.calcAfterDiscount(itemIds, false);
	     
	     assertEquals(billAfterDiscount, expectedDiscount, 0.0f);
	}
	
	@Test
	public void testDiscountPlusOtherItem() throws IdNotContainedException {
		 ArrayList<String> itemIds = new ArrayList<>();
	     itemIds.add("HOT005");  
		 itemIds.add("DES006");
		 itemIds.add("DES006");
		 
		 float expectedDiscount = 4.5f + (menu.findItemId("DES006")).getPrice();
		 float billAfterDiscount = (float) discountCheck.calcAfterDiscount(itemIds, false);
	     
	     assertEquals(billAfterDiscount, expectedDiscount, 0.0f);
	}
	
	@Test
	public void testSingleRegCoffeAndCakeDiscountPresent() throws IdNotContainedException {
		 ArrayList<String> itemIds = new ArrayList<>();
	     itemIds.add("HOT005");  
		 itemIds.add("DES006");
		 
		 float expectedDiscount = 4.5f;
		 float billAfterDiscount = (float) discountCheck.calcAfterDiscount(itemIds, false);
	     
	     assertEquals(billAfterDiscount, expectedDiscount, 0.0f);
	}
	
	@Test
	public void testMultipleSameDiscountPresent() throws IdNotContainedException {
		 ArrayList<String> itemIds = new ArrayList<>();
	     itemIds.add("HOT005");  
		 itemIds.add("DES006");
		 itemIds.add("HOT005");  
		 itemIds.add("DES006");
		 itemIds.add("HOT005");  
		 itemIds.add("DES006");
		 
		 float expectedDiscount = 13.5f;
		 float billAfterDiscount = (float) discountCheck.calcAfterDiscount(itemIds, false);
	     
	     assertEquals(billAfterDiscount, expectedDiscount, 0.0f);
	}
	
	@Test
	public void testSingleSoupSandwichDealPresent() throws IdNotContainedException {
		 ArrayList<String> itemIds = new ArrayList<>();
	     itemIds.add("FOD006"); 
	     itemIds.add("FOD001");

		 float expectedDiscount = 6.5f;
		 float billAfterDiscount = (float) discountCheck.calcAfterDiscount(itemIds, false);
	     
	     assertEquals(billAfterDiscount, expectedDiscount, 0.0f);
	}
	
	@Test
	public void testSingleMealDealPresent() throws IdNotContainedException {
		 ArrayList<String> itemIds = new ArrayList<>();
	     itemIds.add("FOD005"); 
	     itemIds.add("CLD010");
		 itemIds.add("DES006");
		 
		 float expectedDiscount = 9f;
		 float billAfterDiscount = (float) discountCheck.calcAfterDiscount(itemIds, false);
	     
	     assertEquals(billAfterDiscount, expectedDiscount, 0.0f);
	}
	
	@Test
	public void checkStudentDiscount() throws IdNotContainedException {
		 ArrayList<String> itemIds = new ArrayList<>();
	     itemIds.add("FOD005"); 
		 itemIds.add("FOD005"); 

		 float expectedBill = 0;
		 for(int i =0; i < itemIds.size(); i++) {
			 Item currentItem = menu.findItemId(itemIds.get(i));
			 expectedBill += currentItem.getPrice();
		 }
		 
		 float expectedDiscount = (float) (0.9*expectedBill);
		 float billAfterDiscount = (float) discountCheck.calcAfterDiscount(itemIds, true);
	     
	     assertEquals(billAfterDiscount, expectedDiscount, 0.0f);
	}

	@Test
	public void testTakesMaxDiscountWhenConflictingDiscounts() throws IdNotContainedException {
		 ArrayList<String> itemIds = new ArrayList<>(); 
		 itemIds.add("HOT005"); 
		 itemIds.add("DES006");
		 itemIds.add("FOD005");
		 itemIds.add("CLD010");
		 
		 float expectedDiscount = 9f + (menu.findItemId("HOT005")).getPrice();
		 float billAfterDiscount = (float) discountCheck.calcAfterDiscount(itemIds, false);
	     
	     assertEquals(billAfterDiscount, expectedDiscount, 0.0f);
	}
	
	@Test
	public void testAllowedDifferentDiscountsWithinOneOrder() throws IdNotContainedException {
		 ArrayList<String> itemIds = new ArrayList<>(); 
		 itemIds.add("HOT005"); 
		 itemIds.add("DES006");
		 itemIds.add("DES006");
		 itemIds.add("FOD005");
		 itemIds.add("CLD010");
		 itemIds.add("DES006");
		 itemIds.add("FOD005");
		 itemIds.add("CLD010");
		 
		 float expectedDiscount = (18f + 4.5f);
		 float billAfterDiscount = (float) discountCheck.calcAfterDiscount(itemIds, false);
	     
	     assertEquals(billAfterDiscount, expectedDiscount, 0.0f);
	}
	

	@Test
	public void checkStudentDiscountOnlyAppliedToNonDiscountDeals() throws IdNotContainedException {
		 ArrayList<String> itemIds = new ArrayList<>();
	     itemIds.add("HOT005");  
		 itemIds.add("DES006");
		 itemIds.add("DES006");
		 
		 float expectedDiscount = (float) (4.5f + 0.9*(menu.findItemId("DES006")).getPrice());
		 float billAfterDiscount = (float) discountCheck.calcAfterDiscount(itemIds, true);
	     
	     assertEquals(billAfterDiscount, expectedDiscount, 0.0f);
	}
	
	@Test
	public void testDisplayBill() throws IdNotContainedException {
		 Map<String, Integer> currentOrder = new LinkedHashMap<String, Integer>();
		 
		 currentOrder.put("DES006", 2);
		 String bill = "CHECKOUT \n";
		 bill += String.format("%-2s %s",2,"x  ");
		 bill += String.format("%-20s %s","Blueberry Muffin" ,"£", 6.0);
	     bill += String.format("%-10s",6.0);
	     bill += "\n";
		 bill += String.format("%-2s %s","Total bill before discount: £",6.0);
		 bill += "\n";
		 bill += String.format("%-2s %s","Total bill after discount: £",6.0);
		 String actualDisplayBill = coffeShopInterface.displayBill(currentOrder, false); 
         String message = "Not same";
         System.out.println(actualDisplayBill);
         System.out.println(bill);
		 assertEquals(message, bill, actualDisplayBill);
	}
	
	@Test
	public void testGetDiscount() {
		 ArrayList<String> itemIds = new ArrayList<>();
	     itemIds.add("HOT005");  
		 itemIds.add("DES006");
		 
		 Discount actual = discountCheck.getDiscount(itemIds);
		
		 assertNotNull(actual); 
		 itemIds.clear();
		 actual = discountCheck.getDiscount(itemIds);
	     itemIds.add("HOT005");  
	     assertNull(actual);
	}
	
	@Test
	public void testToArrayList() {
		Map<String, Integer> currentOrder = new LinkedHashMap<String, Integer>();
		currentOrder.put("DES006", 2);
		currentOrder.put("DES002", 1);
		currentOrder.put("HOT003", 2);
		ArrayList<String> actualItemIds = discountCheck.toArrayList(currentOrder);
		ArrayList<String> expectedItemIds = new ArrayList<String>(Arrays.asList("DES006", "DES006", "DES002", "HOT003", "HOT003"));

		assertTrue(actualItemIds.equals(expectedItemIds));
	}  
	
}
