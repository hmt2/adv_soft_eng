package coffeShop;


import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import controllers.DisplayController;
import model.CurrentQueue;
import views.DisplayGUI;

//import WaitingQueue.SingletonHelper;.*

public class WaitingQueue extends CustomerList{

	private CurrentQueue model = new CurrentQueue();

	private DisplayGUI view = new DisplayGUI(model);

	private DisplayController controller = new DisplayController(view, model);
	
	
	//	private LinkedList<Integer, Order> MainQueue; //key orderId , value : Order
	private Queue<Integer> mainQueue = new LinkedList<>();
	//private static List<SimulationEvent> events;  
	private static Map<Customer, Boolean> completedOrder = new HashMap<Customer,Boolean>();

	private static Object lock;

	private WaitingQueue(){
		super();
		lock = new Object();

	}


	private static class SingletonHelper{
		private static final WaitingQueue INSTANCE = new WaitingQueue();
	}

	public static WaitingQueue getInstance(){
		return SingletonHelper.INSTANCE;
	}


	public void addPreviousOrders(DiscountCheck discountCheck, boolean isStudentDiscount) throws DuplicateIDException, IdNotContainedException{
		synchronized (lock) { // to make it thread safe
			TreeMap<Integer,ArrayList<String>> cust = AllOrders.loadOrders();
			Set<Integer> keys = cust.keySet();
			for(Integer key: keys){
				float totalBeforeDiscount = discountCheck.calcBillBeforeDiscount(cust.get(key));
				float totalAfterDiscount = totalBeforeDiscount;
				Discount ds = discountCheck.getDiscount(cust.get(key));
				if(ds != null)
					totalAfterDiscount = (float) discountCheck.calcAfterDiscount(cust.get(key),isStudentDiscount); //assume for the previous cases student is false
				try {
					Customer currentCust = new Customer(key, totalBeforeDiscount, totalAfterDiscount,cust.get(key));
					int id = super.addCustomer(cust.get(key), totalBeforeDiscount, totalAfterDiscount);
					controller.add(currentCust);
				} catch (DuplicateIDException | IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					continue; //continue to process the next customer

				}
			}
		}

	}
	
	public void updateDisplay() {
		controller.updateView();
	}

	public void addCustomer(ArrayList<String> itemIds, DiscountCheck discountCheck, boolean isStudentDiscount) throws DuplicateIDException, IdNotContainedException{
		synchronized (lock) { // to make it thread safe
			float totalBeforeDiscount = discountCheck.calcBillBeforeDiscount(itemIds);
			float totalAfterDiscount = totalBeforeDiscount;
			Discount ds = discountCheck.getDiscount(itemIds);
			if(ds != null)
				totalAfterDiscount = (float) discountCheck.calcAfterDiscount(itemIds,isStudentDiscount); //assume for the previous cases student is false
			try {
				int id = super.addCustomer(itemIds, totalBeforeDiscount, totalAfterDiscount);
				Customer currentCust = new Customer(id, totalBeforeDiscount, totalAfterDiscount,itemIds);
				controller.add(currentCust);
			} catch (DuplicateIDException | IllegalArgumentException e1) {
				System.out.println(e1);
			}
		}
	}


	@Override
	public int addCustomer(ArrayList<String> itemIds, float beforeDiscount, float afterDiscount)
			throws DuplicateIDException {
		// TODO Auto-generated method stub
		synchronized (lock) { // to make it thread safe
			int id = super.addCustomer(itemIds, beforeDiscount, afterDiscount);
			Customer currentCust = new Customer(id, beforeDiscount, afterDiscount,itemIds);
			controller.add(currentCust);
			return id;
		}
	}


	@Override
	public void deleteCustomer(int customerId) {
		// TODO Auto-generated method stub
		synchronized (lock) { // to make it thread safe
			super.deleteCustomer(customerId);
			controller.updateView();
		}
	}


	@Override
	public Customer findCustomerId(int customerId) {
		// TODO Auto-generated method stub
		synchronized (lock) { // to make it thread safe
			return super.findCustomerId(customerId);
		}
	}


	@Override
	public int size() {
		// TODO Auto-generated method stub
		synchronized (lock) { // to make it thread safe
			return super.size();
		}
	}


	@Override
	public Map listByCustomerId() {
		// TODO Auto-generated method stub
		synchronized (lock) { // to make it thread safe
			return super.listByCustomerId();
		}
	}


	@Override
	public void displayBill(Customer c) {
		// TODO Auto-generated method stub
		synchronized (lock) { // to make it thread safe
			super.displayBill(c);
		}
	}


	@Override
	public void loadCustomers() {
		// TODO Auto-generated method stub		
		synchronized (lock) { // to make it thread safe
			super.loadCustomers();
		}
	}


	@Override
	public HashMap<Integer, Customer> customers() {
		// TODO Auto-generated method stub
		synchronized (lock) { // to make it thread safe
			return super.customers();
		}
	}

	public Customer dequeue() {
		synchronized (lock) {
			controller.removeTop();
			return controller.getTopOfQueue();
		}
	}
	
	public boolean isEmpty() {
		return mainQueue.isEmpty();
	}
}

