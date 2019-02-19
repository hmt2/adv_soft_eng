package coffeShop;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;


public class CoffeShopInterface {
	  private Menu menu;
	  private Map<String, Integer> currentOrder = new LinkedHashMap<String, Integer>();
	  CustomerList customerList;
	  AllOrders allorders;
	  private float totalBeforeDiscount = 0;
	  private double totalAfterDiscount = 0;
	  boolean isStudentDiscount;

	  private float totalAllItemsBeforeDiscount = 0;
	  private float totalAllItemsAfterDiscount = 0;
	
	public CoffeShopInterface(Menu menu) throws DuplicateIDException{
		
		this.menu = menu;
	    allorders = new AllOrders();
	    customerList = new CustomerList();
	    addPreviousOrders();
	}
	
	public void generateReport(){
   	 	SalesReport salrep = new SalesReport(menu,totalAllItemsAfterDiscount,totalAllItemsBeforeDiscount);

	}
	public void addPreviousOrders() throws DuplicateIDException{
		  TreeMap<Integer,ArrayList<String>> cust = allorders.loadOrders();
	      Set<Integer> keys = cust.keySet();
			for(Integer key: keys){
				float totalBeforeDiscount = calcBillBeforeDiscount(cust.get(key));
				float totalAfterDiscount = totalBeforeDiscount;
				Discount ds = getDiscount(cust.get(key));
				if(ds != null)
					totalAfterDiscount = (float) calcAfterDiscount(cust.get(key)); //assume for the previous cases student is false
				try {
					customerList.addCustomer(cust.get(key), totalBeforeDiscount, totalAfterDiscount);
				} catch (DuplicateIDException | IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					continue; //continue to process the next customer

			  }
			}
	  }
	
	public Discount getDiscount(ArrayList<String> itemIds) {
	    AllDiscounts ad = new AllDiscounts();
		ArrayList<Discount> discounts = ad.loadDiscounts();
		DiscountCheck dc = new DiscountCheck(discounts);
		Discount custDiscount = dc.checkForDeals(itemIds);
		return custDiscount;
}

  
	public float calcBillBeforeDiscount(ArrayList<String> itemIds) {
  // while loop
	  float billBeforeDiscount = 0;
	  for(String itemid : itemIds) {
		  	billBeforeDiscount += menu.findItemId(itemid).getPrice();
		  }
		  

	  return billBeforeDiscount;
   }
	
	
		   
   
   public double calcAfterDiscount(Map<String, Integer> currentOrder) {
		  return calcAfterDiscount(currentOrder, new ArrayList<String>());
	  }
	  
   public double calcAfterDiscount(Map<String, Integer> currentOrder, ArrayList<String> discountNames) {
		  ArrayList<String> itemIds = toArrayList(currentOrder);
		  return calcAfterDiscount(itemIds, discountNames);
	  }
	  
   public double calcAfterDiscount(ArrayList<String> itemIds) {
		  return calcAfterDiscount(itemIds, new ArrayList<String>());
	  }
	  
   public double calcAfterDiscount(ArrayList<String> itemIds, ArrayList<String> discountNames) {
		  double totalAfterDiscount = 0;
		  while(true) {
			  Discount dis = getDiscount(itemIds);
			  if(dis != null) {
				  totalAfterDiscount += dis.getPrice();
				  discountNames.add(dis.getName());
				  for(String itemId : dis.getDiscountCodes()) {
					  itemIds.remove(itemId);
				  }
			  } else {
				  double price = calcBillBeforeDiscount(itemIds);
				   if (price < 0 || price > 100){
						throw new IllegalArgumentException( "Bill not calculated properly, input items again");
			        
					}
				  totalAfterDiscount += isStudentDiscount ? price * 0.9 : price;
				  break;
			  }
		  }
		  return totalAfterDiscount;
	  }
	  
	  ArrayList<String> toArrayList(Map<String, Integer> order){
		  ArrayList<String> itemIds = new ArrayList<String>();
			
			Set<String> keys = order.keySet();
			for(String key: keys){
				for(int i =0; i < order.get(key); i++) {
					itemIds.add(key);
				}
			}
			return itemIds;
	  }
	  
	//need to add in bill after discount
	  public String displayBill(Map<String, Integer> currentOrder, boolean isStudentDiscount) {
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
	  		totalAfterDiscount = calcAfterDiscount(currentOrder, discountNames);
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
	  
	  //need to update quanities
	  public void placeOrder(Map<String, Integer> currentOrder) throws DuplicateIDException {
			  updateItemQuantity(currentOrder);
			  int custId = customerList.addCustomer(toArrayList(currentOrder),totalBeforeDiscount,(float)totalAfterDiscount);
			  totalAllItemsBeforeDiscount += totalBeforeDiscount;
			  totalAllItemsAfterDiscount += (float)totalAfterDiscount;
			  ArrayList<String> ord = toArrayList(currentOrder);
			  allorders.addOrder(custId, toArrayList(currentOrder));


	  }
	  
	  public void updateItemQuantity(Map<String, Integer> order) {
		  Set<String> keys = order.keySet();
			for(String key: keys){
				int quantityToAdd = order.get(key);
				int currentQuantity = menu.findItemId(key).getQuantity();
				int quantity = currentQuantity + quantityToAdd;
				menu.findItemId(key).setQuantity(quantity);
			}
	  }
	  

}
