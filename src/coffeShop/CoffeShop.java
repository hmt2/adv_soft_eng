package coffeShop;

import java.util.TreeMap;

public class CoffeShop {
	
	 public static void main (String arg[]) {
	       	Item item1 = new Item("HOT001","Breakfast Tea",2,"HotDrink","");	
	    	
	       	Item item2 = new Item("HOT002","Herbal Tea",2,"HotDrink","");
	       	
	       	Item item3 = new Item("HOT003","Expresso",2,"HotDrink","");
	       	
	       	Item item4 = new Item("HOT004","Double Expresso",3,"HotDrink","");
	       	
	       	Item item5 = new Item("HOT005","Flat white",2.5f,"HotDrink","");
	       	
	       	Item item6 = new Item("HOT006","Mocha",2.5f,"HotDrink","");
	       	
	       	Item item7 = new Item("HOT007","Hot choc",2.5f,"HotDrink","");
	       	
            Item item8 = new Item("HOT008","Mocha",2.5f,"HotDrink","");
	       	
	       	Item item9 = new Item("HOT009","Hot choc",2.5f,"HotDrink","");
	       	
	       	Item item10 = new Item("HOT010","Mocha",2.5f,"HotDrink","");
	       	
	       	Item item11 = new Item("HOT011","Hot choc",2.5f,"HotDrink","");
	       	
	       	TreeMap <String,Item> menu = new TreeMap<>(); 
	       	
	       	menu.put(item1.getId(), item1);
	       	menu.put(item2.getId(), item2);
	       	menu.put(item3.getId(), item3);
	       	menu.put(item4.getId(), item4);
	       	menu.put(item5.getId(), item5);
	       	menu.put(item6.getId(), item6);
	       	menu.put(item7.getId(), item7);
	       	menu.put(item8.getId(), item8);
	       	menu.put(item9.getId(), item9);
	       	menu.put(item10.getId(), item10);
	       	menu.put(item11.getId(), item11);
	       	
	       	MenuGUI menuGui = new MenuGUI();
	       	
	       	
	    }
}
