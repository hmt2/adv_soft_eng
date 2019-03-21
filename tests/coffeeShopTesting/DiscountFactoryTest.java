package coffeeShopTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import discounts.Discount;
import discounts.DiscountFactory;
import preparing.*;

public class DiscountFactoryTest {
	String seconItem1 = "ION01";
	String seconItem2 = "ION02";
	String seconItem3 = "ION03";
	String thirdItem1 = "DES01";
	String mainItem = "FOD001";
	
	String name = "meal deal";
	int id = 1;
	double price = 9;
	
	DiscountFactory disFact;
	
	ArrayList<String> secondaryItems;
	ArrayList<String> thirdItems;
	
	ArrayList<Discount> wanted2dDis;
	ArrayList<Discount> actual2dDis;
	ArrayList<Discount> wanted3dDis;
	ArrayList<Discount> actual3dDis;
	
	@Before
	public void setUp2dDiscount()  { //this is to make sure that the test can add well! What to put in the setup method? one setup for all tests?
		disFact = new DiscountFactory();
		actual2dDis = new ArrayList<Discount>();
		secondaryItems = new ArrayList<String>();
		secondaryItems.add(seconItem1);
		secondaryItems.add(seconItem2);
		secondaryItems.add(seconItem3);
		actual2dDis = disFact.createTwoItemDiscounts(name, id, mainItem, secondaryItems, price);
		
		wanted2dDis = new ArrayList<Discount>();
		
		ArrayList<String> discountCode1 = new ArrayList<String>();
		discountCode1.add(mainItem);
		discountCode1.add(seconItem1);
		
		ArrayList<String> discountCode2 = new ArrayList<String>();
		discountCode2.add(mainItem);
		discountCode2.add(seconItem2);
		
		ArrayList<String> discountCode3 = new ArrayList<String>();
		discountCode3.add(mainItem);
		discountCode3.add(seconItem3);
		
		Discount d1 = new Discount(name, id,discountCode1,price);
		Discount d2 = new Discount(name, id,discountCode2,price);
		Discount d3 = new Discount(name, id,discountCode3,price);
		wanted2dDis.add(d1);
		wanted2dDis.add(d2);
		wanted2dDis.add(d3);
	
	}
	
	@Before
	public void setUp3dDiscount()  { //this is to make sure that the test can add well! What to put in the setup method? one setup for all tests?
		thirdItems = new ArrayList<String>();
		thirdItems.add(thirdItem1);
		actual3dDis = disFact.createThreeItemDiscounts(name, id, mainItem, secondaryItems,thirdItems, price);
		
		wanted3dDis = new ArrayList<Discount>();
		
		ArrayList<String> discountCode1 = new ArrayList<String>();
		discountCode1.add(mainItem);
		discountCode1.add(seconItem1);
		discountCode1.add(thirdItem1);
		
		ArrayList<String> discountCode2 = new ArrayList<String>();
		discountCode2.add(mainItem);
		discountCode2.add(seconItem2);
		discountCode2.add(thirdItem1);
		
		ArrayList<String> discountCode3 = new ArrayList<String>();
		discountCode3.add(mainItem);
		discountCode3.add(seconItem3);
		discountCode3.add(thirdItem1);
		
		Discount d1 = new Discount(name, id,discountCode1,price);
		Discount d2 = new Discount(name, id,discountCode2,price);
		Discount d3 = new Discount(name, id,discountCode3,price);
		wanted3dDis.add(d1);
		wanted3dDis.add(d2);
		wanted3dDis.add(d3);
	}
	
	@Test
	public void testcreateTwoItemDiscountsPriceStart() {		
		assertEquals(wanted2dDis.get(0).getPrice(), actual2dDis.get(0).getPrice(),1.0);
	}
	
	@Test
	public void testcreateTwoItemDiscountsPriceEnd() {		
		assertEquals(wanted2dDis.get(wanted2dDis.size()-1).getPrice(), actual2dDis.get(actual2dDis.size()-1).getPrice(),1.0);
	}
	
	@Test
	public void testcreateTwoItemDiscountsCheckDiscountCodesStart() {
		assertEquals(wanted2dDis.get(0).getDiscountCodes(), actual2dDis.get(0).getDiscountCodes());
	}
	
	@Test
	public void testcreateTwoItemDiscountsCheckDiscountCodesEnd() {
		assertEquals(wanted2dDis.get(wanted2dDis.size()-1).getDiscountCodes(), actual2dDis.get(actual2dDis.size()-1).getDiscountCodes());
	}
	
	@Test
	public void testcreateTwoItemDiscountsCheckSize() {
		assertEquals(wanted2dDis.size(), actual2dDis.size());
	}
	
	@Test
	public void testcreateTwoItemDiscountsCheckNameStart() {
		assertEquals(wanted2dDis.get(0).getName(), actual2dDis.get(0).getName());
	}
	
	@Test
	public void testcreateTwoItemDiscountsCheckNameEnd() {
		assertEquals(wanted2dDis.get(wanted2dDis.size()-1).getName(), actual2dDis.get(actual2dDis.size()-1).getName());
	}
	
	@Test
	public void testcreateTwoItemDiscountsCheckIdStart() {
		assertEquals(wanted2dDis.get(0).getId(), actual2dDis.get(0).getId());
	}
	
	@Test
	public void testcreateTwoItemDiscountsCheckIdEnd() {
		assertEquals(wanted2dDis.get(wanted2dDis.size()-1).getId(), actual2dDis.get(actual2dDis.size()-1).getId());
	}
	
	@Test
	public void testcreateThreeItemDiscountsPriceStart() {		
		assertEquals(wanted3dDis.get(0).getPrice(), actual3dDis.get(0).getPrice(),1.0);
	}
	
	@Test
	public void testcreateThreeItemDiscountsPriceEnd() {		
		assertEquals(wanted3dDis.get(wanted3dDis.size()-1).getPrice(), actual3dDis.get(actual3dDis.size()-1).getPrice(),1.0);
	}
	
	@Test
	public void testcreateThreeItemDiscountsCheckDiscountCodesStart() {
		assertEquals(wanted3dDis.get(0).getDiscountCodes(), actual3dDis.get(0).getDiscountCodes());
	}
	
	@Test
	public void testcreateThreeItemDiscountsCheckDiscountCodesEnd() {
		assertEquals(wanted3dDis.get(wanted3dDis.size()-1).getDiscountCodes(), actual3dDis.get(actual2dDis.size()-1).getDiscountCodes());
	}
	
	@Test
	public void testcreateThreeItemDiscountsCheckSize() {
		assertEquals(wanted3dDis.size(), actual3dDis.size());
	}
	
	@Test
	public void testcreateThreeItemDiscountsCheckNameStart() {
		assertEquals(wanted3dDis.get(0).getName(), actual3dDis.get(0).getName());
	}
	
	@Test
	public void testcreateThreeItemDiscountsCheckNameEnd() {
		assertEquals(wanted3dDis.get(wanted3dDis.size()-1).getName(), actual3dDis.get(actual3dDis.size()-1).getName());
	}
	
	@Test
	public void testcreateThreeItemDiscountsCheckIdStart() {
		assertEquals(wanted3dDis.get(0).getId(), actual3dDis.get(0).getId());
	}
	
	@Test
	public void testcreateThreeItemDiscountsCheckIdEnd() {
		assertEquals(wanted3dDis.get(wanted3dDis.size()-1).getId(), actual3dDis.get(actual3dDis.size()-1).getId());
	}
	
	
}
