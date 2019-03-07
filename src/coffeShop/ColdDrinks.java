package coffeShop;


public class ColdDrinks extends Item {

	public ColdDrinks(String itemId, String itemName, float itemPrice, String itemCategory, String itemDescription, float itemDuration) throws InvalidIdException {
		super(itemId, itemName, itemPrice, itemCategory, itemDescription, (long) itemDuration);
	}

}
