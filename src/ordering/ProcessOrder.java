package ordering;

import java.util.*;

import discounts.*;
import discounts.DiscountCheck;
import exceptions.*;
import output.SalesReport;
import preparing.PreparationSingleton;


public class ProcessOrder {
	private Menu menu;
	private Map<String, Integer> currentOrder = new LinkedHashMap<String, Integer>();
	CustomerList customerList;
	AllOrders allorders;
	DiscountCheck discountCheck;
	AllDiscounts allDiscounts;
	private float totalBeforeDiscount = 0;
	private double totalAfterDiscount = 0;
	boolean isStudentDiscount;
	PreparationSingleton waitingQueue;

	private float totalAllItemsBeforeDiscount = 0;
	private float totalAllItemsAfterDiscount = 0;

	public ProcessOrder(Menu menu) throws DuplicateIDException, IdNotContainedException{

		this.menu = menu;
		allorders = new AllOrders();
		customerList = new CustomerList();
		allDiscounts = new AllDiscounts();
		discountCheck = new DiscountCheck(allDiscounts.loadDiscounts());
		addPreviousOrders();
	}

	public void generateReport(){
		SalesReport salrep = new SalesReport(menu,totalAllItemsBeforeDiscount,totalAllItemsAfterDiscount);

	}
	public void addPreviousOrders() throws DuplicateIDException, IdNotContainedException{
		try {
			PreparationSingleton.getInstance().addPreviousOrders(discountCheck, true);
		} catch (DuplicateIDException | IdNotContainedException e) {
			e.printStackTrace();
		}
	}

	//need to add in bill after discount
	public String displayBill(Map<String, Integer> currentOrder, boolean isStudentDiscount) throws IdNotContainedException {
		this.isStudentDiscount = isStudentDiscount;
		totalBeforeDiscount = 0;
		String bill = "CHECKOUT \n";

		float billCurrentItem = 0;
		int currentItemQuantity = 0;

		Set<String> keys = currentOrder.keySet();
		for(String key: keys){
			if(currentOrder.get(key) > 1) {
				currentItemQuantity = currentOrder.get(key);
				billCurrentItem = currentItemQuantity*menu.findItemId(key).getPrice();			
			}
			else {
				currentItemQuantity = 1;
				billCurrentItem = menu.findItemId(key).getPrice();
			}
			totalBeforeDiscount += billCurrentItem;

			bill += String.format("%-2s %s",currentItemQuantity,"x  ");
			bill += String.format("%-20s %s",menu.findItemId(key).getName(),"£",billCurrentItem);
			bill += String.format("%-10s",billCurrentItem);
			bill += "\n";
		}

		ArrayList<String> discountNames = new ArrayList<>();
		totalAfterDiscount = discountCheck.calcAfterDiscount(currentOrder, discountNames,isStudentDiscount);
		if (totalAfterDiscount < 0 || totalAfterDiscount > 100){
			throw new IllegalArgumentException( "Bill not calculated properly, input items again");
			//throw new IllegalArgumentException("Bill not calculated properly");
		}
		bill += String.format("%-2s %s","Total bill before discount: £",totalBeforeDiscount);
		bill += "\n";
		if(!discountNames.isEmpty() || isStudentDiscount)
			bill += "Applied discounts:\n";
		if(isStudentDiscount)
			bill += "\tStudent Discount: 10%\n";
		for(String discountName : discountNames)
			bill += "\t" + discountName + "\n"; 
		bill += String.format("%-2s %s","Total bill after discount: £",totalAfterDiscount);

		return bill;
	}

	/** placeOrder : adds a customer and adds the order(s) associated to that customer
	 *  it also adds the customer to the waitingQueue (at the head if priority is true and at the tail if false)
	 * 
	 * @param currentOrder
	 * @param priority
	 * @throws DuplicateIDException
	 * @throws IdNotContainedException
	 */
	public void placeOrder(Map<String, Integer> currentOrder, boolean priority) throws DuplicateIDException, IdNotContainedException {
		updateItemQuantity(currentOrder);
		int custId;
		totalAllItemsBeforeDiscount += totalBeforeDiscount;
		totalAllItemsAfterDiscount += (float)totalAfterDiscount;
        if(priority == false) {
        	custId = PreparationSingleton.getInstance().addCustomer(discountCheck.toArrayList(currentOrder), (float)totalBeforeDiscount, (float)totalAfterDiscount);
        }
        else {
        	custId = PreparationSingleton.getInstance().addFirstCustomer(discountCheck.toArrayList(currentOrder), (float)totalBeforeDiscount, (float)totalAfterDiscount);
        }
        allorders.addOrder(custId, discountCheck.toArrayList(currentOrder));
	}

	public void updateItemQuantity(Map<String, Integer> order) throws IdNotContainedException {
		Set<String> keys = order.keySet();
		for(String key: keys){
			int quantityToAdd = order.get(key);
			int currentQuantity = menu.findItemId(key).getQuantity();
			int quantity = currentQuantity + quantityToAdd;
			menu.findItemId(key).setQuantity(quantity);
		}
	}
	//Helps with the testing
	public CustomerList getCustomerList(){
		return customerList;
	}
	public Menu getMenu(){
		return menu;
	}

	public AllOrders getAllOrders(){
		return allorders;
	}
}
