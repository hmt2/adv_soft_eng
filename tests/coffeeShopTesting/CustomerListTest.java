package coffeeShopTesting;
import static org.junit.Assert.*;

import org.junit.Test;


import java.util.ArrayList;


import coffeShop.*;

public class CustomerListTest {




	//Check the double ID exception
	@Test
	public void checkDoubleIdExceptions() throws coffeShop.DuplicateIDException {

		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds) ;
		Customer customer2 = new Customer(120, 25.6f, 22.4f,itemIds) ;
		CustomerList customerList1 = new CustomerList() ;
		customerList1.customers().put(120, customer1);
		customerList1.customers().put(120, customer2);


	    }

	//check if it display Bill Before Discount
	@Test
	public void displayBillBeforeDiscount() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("COL012");
		coffeShop.Customer customer1 = new Customer(120, 25.6f, 25.6f,itemIds) ;
		CustomerList customerList = new CustomerList() ;
		customer1.setDiscount(false);
		customerList.displayBill(customer1);

	    }
//	test display Bill After Discount	
	@Test
	public void displayBillAfterDiscount() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");
		coffeShop.Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds) ;
		CustomerList customerList = new CustomerList() ;
		customerList.displayBill(customer1);

	    }


	@Test
	public void ableToDelete() throws DuplicateIDException {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		CustomerList customerList = new CustomerList() ;

		int customerId= customerList.addCustomer(itemIds, 22.5f,23.4f);

		customerList.deleteCustomer(customerId);


	    }

	@Test (expected = IllegalArgumentException.class)
	public void throwExceptionDelete() throws DuplicateIDException {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		CustomerList customerList = new CustomerList() ;
		customerList.addCustomer(itemIds, 22.5f,23.4f);
		customerList.deleteCustomer(104);


	    }


	@Test
	public void findCustomerId() throws DuplicateIDException {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");

		CustomerList customerList = new CustomerList() ;
		Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds) ;
		customerList.customers().put(120, customer1);
		assertEquals(customer1,customerList.findCustomerId(120));


	    }

	@Test (expected = IllegalArgumentException.class)
	public void notFindCustomereId() {
		ArrayList<String> itemIds = new ArrayList<String>();
		itemIds.add("HOT011");
		itemIds.add("DES006");
		coffeShop.Customer customer1 = new Customer(120, 25.6f, 22.4f,itemIds) ;
		CustomerList customerList = new CustomerList() ;
		customerList.findCustomerId(121);

	    }

	@Test
	public void testListByCustomerId() throws DuplicateIDException {
		ArrayList<String> itemIds1 = new ArrayList<String>();
		itemIds1.add("HOT011");
		itemIds1.add("DES007");

		CustomerList customerList = new CustomerList() ;
		customerList.addCustomer(itemIds1, 25.6f, 22.4f);
		customerList.addCustomer(itemIds1, 25.6f, 22.4f);
		customerList.loadCustomers();
		assertEquals(2,customerList.size());
	    }





}
