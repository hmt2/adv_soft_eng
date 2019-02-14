package coffeShop;

import java.util.ArrayList;

public class DiscountCheck{ //extends Customer{

	int ord = 1;	
	double orgPrice, newPrice;
	boolean disc;	
	String discountDescription;

	public  static int[] discountId = new int[3];
	ArrayList<Discount> discounts;
	
	public DiscountCheck(ArrayList<Discount> discounts) {
		this.discounts = discounts;
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