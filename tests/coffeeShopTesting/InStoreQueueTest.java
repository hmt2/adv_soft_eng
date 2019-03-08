package coffeeShopTesting;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import coffeShop.*;

public class InStoreQueueTest {

	private InStoreQueue instoreQueue; 
	CustomerList customerList;
	private AllOrders allorders;
	private ArrayList <String> testedOrder;
	
	
	@Before
	public void setUp() throws Exception {
		customerList = new CustomerList(); 
		instoreQueue = new InStoreQueue();
		allorders = new AllOrders();
		testedOrder = new ArrayList<String>();
	}

	//test if the addInstoreQueue works
	@Test
	public void addInstoreQueueTest() throws DuplicateIDException {
	
	testedOrder.add("HOT001"); //breakfast tea
	testedOrder.add("DES007"); //caramel shortbread
	int customerId;
	customerId= customerList.addCustomer(testedOrder,22.5f, 20.3f);
	instoreQueue.addInstoreQueue(customerList.findCustomerId(customerId));
	instoreQueue.displayInstoreQueue();
		
	}
	
	//test if the exception is working 
	@Test 
	public void addInstoreQueueExceptionTest() {
	
	 try {
		Customer cust= null;
		 instoreQueue.addInstoreQueue(cust);
		 instoreQueue.displayInstoreQueue();
		 //fail();
	 }catch (IllegalArgumentException e)
	 {
	 assertEquals("not valid customer",e.getMessage());
	 }
	}
	@Test
	public void removeheadInstoreQueueTest() throws DuplicateIDException {
	
	testedOrder.add("HOT001"); //breakfast tea
	testedOrder.add("DES007"); //caramel shortbread
	int customerId;
	int customerId2;
	customerId= customerList.addCustomer(testedOrder,22.5f, 20.3f);
	customerId2= customerList.addCustomer(testedOrder,22.5f, 20.3f);
	//System.out.println(customerList.findCustomerId(customerId2).toString());
	 instoreQueue.addInstoreQueue(customerList.findCustomerId(customerId));
	 instoreQueue.addInstoreQueue(customerList.findCustomerId(customerId2));
	 instoreQueue.displayInstoreQueue();
	 instoreQueue.removeHead();
	 assertEquals(1,instoreQueue.size());
	 instoreQueue.displayInstoreQueue();
		
	}

}
