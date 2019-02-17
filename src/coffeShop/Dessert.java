package coffeShop;


public class Dessert extends Item{

	public Dessert(String itemId, String itemName, float itemPrice, String itemCategory, String itemDescription) throws InvalidIdException {
		super(itemId, itemName, itemPrice, itemCategory, itemDescription);
	}

}
