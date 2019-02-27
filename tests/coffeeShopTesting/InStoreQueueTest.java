package coffeeShopTesting;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import coffeShop.*;

public class InStoreQueueTest {

	private InStoreQueue instoreQueue; 
	private AllOrders allorders;
	private ArrayList <String> testedOrder;
	
	@Before
	public void setUp() throws Exception {
		instoreQueue = new InStoreQueue();
		allorders = new AllOrders();
		testedOrder = new ArrayList<String>();
	}

	//test if the addInstoreQueue works
	@Test
	public void addInstoreQueueTest() {
	 testedOrder.add("HOT001"); //breakfast tea
	 testedOrder.add("DES007"); //caramel shortbread
	 int[] orderIds = allorders.addOrder(174, testedOrder);
	 instoreQueue.addInstoreQueue(orderIds);
	 instoreQueue.displayInstoreQueue();
		
	}
	
	//test if the exception is working 
	@Test 
	public void addInstoreQueueExceptionTest() {
	
	 try {
		 int[] orderIds = null; 
		 instoreQueue.addInstoreQueue(orderIds);
		 fail();
	 }catch (NullPointerException e)
	 {
	 assertEquals("OrderIds is empty",e.getMessage());
	 }
	}

}
