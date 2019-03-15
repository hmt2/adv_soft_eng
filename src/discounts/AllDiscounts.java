package discounts;

import java.util.*;

public class AllDiscounts {
	private ArrayList<Discount> discounts;

	public AllDiscounts() {
		discounts = new ArrayList<>();
	}

	public ArrayList<Discount> loadDiscounts() {
		DiscountFactory df = new DiscountFactory();
		//three item deals

		//meal deal
		ArrayList<String> cld = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			cld.add((i < 10 ? "CLD00" : "CLD0")+i);
		}
		ArrayList<String> des = new ArrayList<>();
		for (int i = 2; i <= 9; i++) {
			des.add((i < 10 ? "DES00" : "DES0")+i);
		}
		for (int i = 1; i <= 8; i++) {
			discounts.addAll(
					df.createThreeItemDiscounts(
							"Meal Deal", 
							1, 
							(i < 10 ? "FOD00" : "FOD0")+i, 
							cld,
							des,
							9.0)
					);
		}

		//two item deals

		//Soup and Sandwich deal
		ArrayList<String> secondaryItems = new ArrayList<>();
		secondaryItems.add("FOD001"); secondaryItems.add("FOD002");
		discounts.addAll(
				df.createTwoItemDiscounts(
						"Soup and Sandwich Deal", 
						2, 
						"FOD006", 
						secondaryItems, 
						6.5)
				);

		//Coffee and Cake deal
		secondaryItems = new ArrayList<>();
		secondaryItems.add("DES002");
		secondaryItems.add("DES003");
		secondaryItems.add("DES004");
		secondaryItems.add("DES005");
		secondaryItems.add("DES006");
		secondaryItems.add("DES007");
		secondaryItems.add("DES008");
		secondaryItems.add("DES009");

		ArrayList<String> hot = new ArrayList<>();
		hot.add("HOT001");
		hot.add("HOT002");
		hot.add("HOT003");
		hot.add("HOT005");
		hot.add("HOT006");
		hot.add("HOT008");
		hot.add("HOT010");

		for(String h : hot) {
			discounts.addAll(
					df.createTwoItemDiscounts(
							"Coffee and Cake Deal", 
							3, 
							h, 
							secondaryItems, 
							4.5)
					);
		}
		return discounts;
	}
}