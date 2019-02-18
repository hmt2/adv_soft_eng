package coffeShop;

import java.util.ArrayList;

public class DiscountFactory {
	
	public ArrayList<Discount> createTwoItemDiscounts(String name, int id, String mainItem, ArrayList<String> secondaryItems, float price) {
		ArrayList<Discount> result = new ArrayList<>();
		for(String si : secondaryItems) {
			ArrayList<String> codes = new ArrayList<>();
			codes.add(mainItem); codes.add(si);
			result.add(new Discount(name, id, codes, price));
		}
		return result;
	}
	
	public ArrayList<Discount> createThreeItemDiscounts(String name, int id, String mainItem, ArrayList<String> secondaryItems, ArrayList<String> thirdsItems, float price) {
		ArrayList<Discount> result = new ArrayList<>();
		for(String si : secondaryItems) {
			for(String ti : thirdsItems) {
				ArrayList<String> codes = new ArrayList<>();
				codes.add(mainItem); codes.add(si); codes.add(ti);
				result.add(new Discount(name, id, codes, price));
			}
		}
		return result;
	}

}
