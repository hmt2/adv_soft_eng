package coffeShop;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import discounts.AllDiscounts;
import discounts.Discount;
import discounts.DiscountCheck;
import exceptions.DuplicateIDException;
import exceptions.IdNotContainedException;
import output.SalesReport;


public class CoffeShopInterface {
	private Menu menu;
	private Map<String, Integer> currentOrder = new LinkedHashMap<String, Integer>();
	CustomerList customerList;
	AllOrders allorders;
	DiscountCheck discountCheck;
	AllDiscounts allDiscounts;
	private float totalBeforeDiscount = 0;
	private double totalAfterDiscount = 0;
	boolean isStudentDiscount;
	WaitingQueue waitingQueue;

	private float totalAllItemsBeforeDiscount = 0;
	private float totalAllItemsAfterDiscount = 0;

	public CoffeShopInterface(Menu menu) throws DuplicateIDException, IdNotContainedException{

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
		TreeMap<Integer,ArrayList<String>> cust = allorders.loadOrders();
		Set<Integer> keys = cust.keySet();
		for(Integer key: keys){
			float totalBeforeDiscount = discountCheck.calcBillBeforeDiscount(cust.get(key));
			float totalAfterDiscount = totalBeforeDiscount;
			Discount ds = discountCheck.getDiscount(cust.get(key));
			if(ds != null)
				totalAfterDiscount = (float) discountCheck.calcAfterDiscount(cust.get(key),isStudentDiscount); //assume for the previous cases student is false
				try {
					customerList.addCustomer(cust.get(key), totalBeforeDiscount, totalAfterDiscount);
				} catch (DuplicateIDException | IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					continue; //continue to process the next customer

				}
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

	//need to update quantities
	public void placeOrder(Map<String, Integer> currentOrder, boolean priority) throws DuplicateIDException, IdNotContainedException {
		updateItemQuantity(currentOrder);
		int custId = customerList.addCustomer(discountCheck.toArrayList(currentOrder),totalBeforeDiscount,(float)totalAfterDiscount);
		totalAllItemsBeforeDiscount += totalBeforeDiscount;
		totalAllItemsAfterDiscount += (float)totalAfterDiscount;
		allorders.addOrder(custId, discountCheck.toArrayList(currentOrder));
        if(priority == false) {
      	  WaitingQueue.getInstance().addCustomer(discountCheck.toArrayList(currentOrder), (float)totalBeforeDiscount, (float)totalAfterDiscount);
        }
        else {
      	  WaitingQueue.getInstance().addFirstCustomer(discountCheck.toArrayList(currentOrder), (float)totalBeforeDiscount, (float)totalAfterDiscount);
        }
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
