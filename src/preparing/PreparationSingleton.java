package preparing;


import java.util.*;
import controllers.DisplayController;
import discounts.*;
import exceptions.*;
import model.DisplayModel;
import ordering.*;
import output.Log;
import views.DisplayGUI;

public class PreparationSingleton extends CustomerList{

	private static DisplayModel model = new DisplayModel();

	private static DisplayGUI view = new DisplayGUI(model);

	private DisplayController controller = new DisplayController(view, model);

	//the Log is placed inside the WaitingQueue which is a Singleton so that there is only ever 1 instance
	private Log log = new Log();
	private Deque<Integer> mainQueue = new LinkedList<>();
	private static Map<Customer, Boolean> completedOrder = new HashMap<Customer,Boolean>();

	private static Object lock;
    private AllOrders allorders  = new AllOrders();
	//create lock to apply in methods to make them thread safe
	private PreparationSingleton(){
		super();
		lock = new Object();

	}
	
	public void clearServer(int i) {
		controller.clearServer(i);
	}

	//create instance of class with singleton design pattern
	private static class SingletonHelper{
		private static final PreparationSingleton INSTANCE = new PreparationSingleton();
	}

	//instance of class to be called, to make sure only one instance can exist at one time
	public static PreparationSingleton getInstance(){
		return SingletonHelper.INSTANCE;
	}

	//method to gradually add previous orders from orders.txt to the main queue
	public void addPreviousOrders(DiscountCheck discountCheck, boolean isStudentDiscount) throws DuplicateIDException, IdNotContainedException{
		synchronized (lock) { // to make it thread safe
			TreeMap<Integer,ArrayList<String>> cust = allorders.loadOrders();
			Set<Integer> keys = cust.keySet();
			for(Integer key: keys){
				float totalBeforeDiscount = discountCheck.calcBillBeforeDiscount(cust.get(key));
				float totalAfterDiscount = totalBeforeDiscount;
				Discount ds = discountCheck.getDiscount(cust.get(key));
				if(ds != null)
					totalAfterDiscount = (float) discountCheck.calcAfterDiscount(cust.get(key),isStudentDiscount); //assume for the previous cases student is false
				try {
					Customer currentCust = new Customer(key, totalBeforeDiscount, totalAfterDiscount,cust.get(key), false);
					int id = super.addCustomer(cust.get(key), totalBeforeDiscount, totalAfterDiscount);
					controller.addWaitingQueue(currentCust);
				} catch (DuplicateIDException | IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					continue; //continue to process the next customer

				} 
			}
		}
	}

	//add new customer to the queue
	public void addCustomer(ArrayList<String> itemIds, DiscountCheck discountCheck, boolean isStudentDiscount) throws DuplicateIDException, IdNotContainedException{
		synchronized (lock) { // to make it thread safe
			float totalBeforeDiscount = discountCheck.calcBillBeforeDiscount(itemIds);
			float totalAfterDiscount = totalBeforeDiscount;
			Discount ds = discountCheck.getDiscount(itemIds);
			if(ds != null)
				totalAfterDiscount = (float) discountCheck.calcAfterDiscount(itemIds,isStudentDiscount); //assume for the previous cases student is false
			try {
				int id = super.addCustomer(itemIds, totalBeforeDiscount, totalAfterDiscount);
				Customer currentCust = new Customer(id, totalBeforeDiscount, totalAfterDiscount,itemIds,false);
				controller.addWaitingQueue(currentCust);
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
			Customer currentCust = new Customer(id, beforeDiscount, afterDiscount,itemIds,false);
			controller.addWaitingQueue(currentCust);
			return id;
		}
	}
	
	/** addFirstCustomer : adds a customer, which has made an order in priority
	 * 	it also adds the customer to the waitingQueue at the head
	 * 
	 * @param itemIds
	 * @param discountCheck
	 * @param isStudentDiscount
	 * @throws DuplicateIDException
	 * @throws IdNotContainedException
	 */
	public void addFirstCustomer(ArrayList<String> itemIds, DiscountCheck discountCheck, boolean isStudentDiscount) throws DuplicateIDException, IdNotContainedException{
		synchronized (lock) { // to make it thread safe
			float totalBeforeDiscount = discountCheck.calcBillBeforeDiscount(itemIds);
			float totalAfterDiscount = totalBeforeDiscount;
			Discount ds = discountCheck.getDiscount(itemIds);
			if(ds != null)
				totalAfterDiscount = (float) discountCheck.calcAfterDiscount(itemIds,isStudentDiscount); //assume for the previous cases student is false
			try {
				int id = super.addFirstCustomer(itemIds, totalBeforeDiscount, totalAfterDiscount);
				Customer currentCust = new Customer(id, totalBeforeDiscount, totalAfterDiscount,itemIds,true);
				controller.addFirstWaitingQueue(currentCust);
			} catch (DuplicateIDException | IllegalArgumentException e1) {
				System.out.println(e1);
			}
		}
	}
	/** addFirstCustomer : adds a customer, which has made an order in priority
	 *  it also adds the customer to the waitingQueue at the head
	 * 
	 * @param itemIds
	 * @param beforeDiscount
	 * @param afterDiscount
	 * @throws DuplicateIDException
	 */
	@Override
	public int addFirstCustomer(ArrayList<String> itemIds, float beforeDiscount, float afterDiscount)
			throws DuplicateIDException {
		// TODO Auto-generated method stub
		synchronized (lock) { // to make it thread safe
			int id = super.addFirstCustomer(itemIds, beforeDiscount, afterDiscount);
			Customer currentCust = new Customer(id, beforeDiscount, afterDiscount,itemIds,true);
			controller.addFirstWaitingQueue(currentCust);
			return id;
		}
	}


	@Override
	public void deleteCustomer(int customerId) {
		// TODO Auto-generated method stub
		synchronized (lock) { // to make it thread safe
			super.deleteCustomer(customerId);
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
			controller.removeTopWaitingQueue();
			Customer cust = controller.getTopOfWaitingQueue();
			return cust;
		}
	}
	
	public void setServer(int i, Customer cust) {
		controller.setServerCustomer(i, cust);
	}
	
	public void setOnOff(boolean off) {
		controller.setOnOff(off);
	}
	
	public int getSizeCollectionQueue() {
		return controller.getSizeCollectionQueue();
	}
	
	public void addCollectionQueue(Customer cust) {
		controller.addCollectionQueue(cust);
	}
	
	public void removeTopCollectionQueue() {
		controller.removeTopCollectionQueue();
	}

	public boolean isEmpty() {
		return controller.isEmpty();
	}

	public void displayQueue() {
		synchronized (lock) { // to make it thread safe
			System.out.print(mainQueue.peek());
		}
	}

	public void emptyCollectionQueue() {
		controller.emptyCollectionQueue();
		
	}
}

