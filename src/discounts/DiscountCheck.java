package discounts;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import exceptions.IdNotContainedException;
import ordering.Menu;

public class DiscountCheck{

	int ord = 1;	
	double orgPrice, newPrice;
	boolean disc;	
	String discountDescription;
	Menu menu;

	public  static int[] discountId = new int[3];
	ArrayList<Discount> discounts;

	public DiscountCheck(ArrayList<Discount> discounts) {
		this.discounts = discounts;
		this.menu = new Menu();
	}

	public Discount getDiscount(ArrayList<String> itemIds) {
		AllDiscounts ad = new AllDiscounts();
		ArrayList<Discount> discounts = ad.loadDiscounts();
		DiscountCheck dc = new DiscountCheck(discounts);
		Discount custDiscount = dc.checkForDeals(itemIds);
		return custDiscount;
	}


	public ArrayList<String> toArrayList(Map<String, Integer> order){
		ArrayList<String> itemIds = new ArrayList<String>();

		Set<String> keys = order.keySet();
		for(String key: keys){
			for(int i =0; i < order.get(key); i++) {
				itemIds.add(key);
			}
		}
		return itemIds;
	}

	public  float calcBillBeforeDiscount(ArrayList<String> itemIds) throws IdNotContainedException {
		// while loop
		float billBeforeDiscount = 0;
		for(String itemid : itemIds) {
			billBeforeDiscount += menu.findItemId(itemid).getPrice();
		}


		return billBeforeDiscount;
	}	   

	public double calcAfterDiscount(Map<String, Integer> currentOrder,boolean isStudentDiscount) throws IdNotContainedException {
		return calcAfterDiscount(currentOrder, new ArrayList<String>(),isStudentDiscount);
	}

	public double calcAfterDiscount(Map<String, Integer> currentOrder, ArrayList<String> discountNames,boolean isStudentDiscount) throws IdNotContainedException {
		ArrayList<String> itemIds = toArrayList(currentOrder);
		return calcAfterDiscount(itemIds, discountNames,isStudentDiscount);
	}

	public double calcAfterDiscount(ArrayList<String> itemIds,boolean isStudentDiscount) throws IdNotContainedException {
		return calcAfterDiscount(itemIds, new ArrayList<String>(),isStudentDiscount);
	}

	public double calcAfterDiscount(ArrayList<String> itemIds, ArrayList<String> discountNames, boolean isStudentDiscount) throws IdNotContainedException {
		ArrayList<String> itemIdsBuffer =new ArrayList<>();  
		double totalAfterDiscount = 0;
		while(true) {
			Discount dis = getDiscount(itemIds);
			if(dis != null) {
				totalAfterDiscount += dis.getPrice();
				discountNames.add(dis.getName());
				for(String itemId : dis.getDiscountCodes()) {
					itemIds.remove(itemId);
					itemIdsBuffer.add(itemId);
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
		for(String itemId : itemIdsBuffer) {
			itemIds.add(itemId);
		}
		return totalAfterDiscount;
	}


	public Discount checkForDeals(ArrayList<String> itemIds) {
		for(Discount discount : discounts) {
			boolean found = true;
			for(String code : discount.getDiscountCodes()) {
				if(itemIds.contains(code)) {

				} else {
					found = false;
					break;
				}
			}
			if(found)
				return discount;
		}
		return null;
	}

}
