package coffeShop;


public class Food extends Item{

	public Food(String itemId, String itemName, float itemPrice, String itemCategory, String itemDescription) throws InvalidIdException {
		super(itemId, itemName, itemPrice, itemCategory, itemDescription);
	}

}
