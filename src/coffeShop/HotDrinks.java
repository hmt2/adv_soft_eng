package coffeShop;

public class HotDrinks extends Item {
	public HotDrinks(String itemId, String itemName, float itemPrice, String itemCategory, String itemDescription, Boolean sugar) throws InvalidIdException {
		super(itemId, itemName, itemPrice, itemCategory, itemDescription);
	}

}
