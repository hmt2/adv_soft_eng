package coffeeShopTesting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import coffeShop.*;
import discounts.Discount;

public class TestDiscounts {
	Discount ds;
	String name = "Meal deal";
	int id = 1;
	ArrayList<String> cld;
	double price = 9;
	
	@Before
	public void setUp()  { //this is to make sure that the test can add well! What to put in the setup method? one setup for all tests?
		cld = new ArrayList<>();
		cld.add("CLD00");
		cld.add("CLD01");
		cld.add("CLD02");
		ds = new Discount(name,id,cld,9);
	}
	
	@Test
	public void testGetName() {
		String actualName = ds.getName();
		assertEquals(name, actualName);
	}
	
	@Test
	public void testSetName() {
		String name = "Dessert";
		ds.setName(name);
		assertEquals(name, ds.getName());
	}
	
	@Test
	public void testGetId() {
		assertEquals(id, ds.getId());
	}
	
	
	@Test
	public void testSetId() {
		int id = 2;
		ds.setId(id);
		assertEquals(id, ds.getId());
	}
	
	@Test
	public void testgetDiscountCodes() {
		assertEquals(cld, ds.getDiscountCodes());
	}
	
	@Test
	public void setDiscountCodes() {
		ArrayList<String> codes = new ArrayList<String>();
		codes.add("CLD00");
		codes.add("CLD02");
		ds.setDiscountCodes(codes);
		assertEquals(codes, ds.getDiscountCodes());
	}
	
	@Test
	public void testgetPrice() {
		assertEquals(price, ds.getPrice(),1.0);
	}
	
	@Test
	public void testSetPrice() {
		double newPrice = 10;
		ds.setPrice(newPrice);
		assertEquals(newPrice, ds.getPrice(),1.0);
	}
	
}
