package coffeeShopTesting;

import coffeShop.*;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import coffeShop.Menu;
import coffeShop.SalesReport;

public class TestSalesReport {
	private Menu menu;
	
	@Before
	public void setUp() throws Exception {
		menu = new Menu();
	}	

//	To test SalesReport
//	String date;
//	public String getDate() {
//		return date;
//	}
//	
//	in createSalesreport()
//	date = timeStamp;
	
	@Test
	public final void testSalesReport() {
		float totalBillBeforeDiscount, totalBillAfterDiscount;
		totalBillBeforeDiscount = 42.55f;
		totalBillAfterDiscount = 35.78f;
		SalesReport sr = new SalesReport(menu, totalBillBeforeDiscount, totalBillAfterDiscount);
		sr.createSalesreport();
		String date;
		date = sr.getDate();
		File f = new File(date + ".csv");
		assertTrue(f.exists());
	}

	//To test setTotalBillBeforeDiscount() and setTotalBillAfterDiscount()
	//createSalesReport in the constructor of SalesReport commented
	@Test
	public final void testSetTotalBillBeforeDiscount() {
		float totalBillBeforeDiscount = 0, totalBillAfterDiscount = 0;
		SalesReport sr = new SalesReport(menu, totalBillBeforeDiscount, totalBillAfterDiscount);
		sr.setTotalBillBeforeDiscount(34.5f);
		assertEquals(34.5f, sr.getTotalBillBeforeDiscount(), 0.05);
	}


	@Test
	public final void testSetTotalBillAfterDiscount() {
		float totalBillBeforeDiscount = 0, totalBillAfterDiscount = 0;
		SalesReport sr = new SalesReport(menu, totalBillBeforeDiscount, totalBillAfterDiscount);
		sr.setTotalBillAfterDiscount(34.5f);
		assertEquals(34.5f, sr.getTotalBillAfterDiscount(), 0.05);
	}

	@Test
	public final void testCreateSalesreport() {
		float totalBillBeforeDiscount = 0, totalBillAfterDiscount = 0;
		totalBillBeforeDiscount = 42.55f;
		totalBillAfterDiscount = 35.78f;
		SalesReport sr = new SalesReport(menu, totalBillBeforeDiscount, totalBillAfterDiscount);
		sr.createSalesreport();
		String date;
		date = sr.getDate();
		File f = new File(date + ".csv");
		assertTrue(f.exists());
		
		totalBillBeforeDiscount = 4f;
		totalBillAfterDiscount = 3f;
		SalesReport sr2 = new SalesReport(menu, totalBillBeforeDiscount, totalBillAfterDiscount);
		sr2.createSalesreport();
		String date2;
		date2 = sr2.getDate();
		File f2 = new File(date2 + ".csv");
		assertTrue(f2.exists());
	}

}
