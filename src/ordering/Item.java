package ordering;

import exceptions.InvalidIdException;

public class Item {
	private String itemId;
	private String itemName;
	private float itemPrice;
	private int itemQuantity;
	private String itemCategory;
	private String itemDescription;
	private long itemDuration;

	public Item(String itemId, String itemName, float itemPrice, String itemCategory, String itemDescription, long itemDuration2) throws InvalidIdException{


		if( itemId.length() ==0 || itemName.length()== 0 || itemCategory.length()== 0)    
		{
			throw new IllegalStateException("Cannot have a blank id, name or category");
		}
		if( itemPrice < 0) {
			throw new IllegalStateException(
					"Cannot have a negative price");
		}
		String header = itemId.substring(0,3);
		if (itemId.length()!= 6) {

			throw new InvalidIdException ("ItemId must be 6 characters long");

		}

		else if(!(header.equals("DES") || header.equals("CLD") || header.equals("ADD") || header.equals("FOD") || header.equals("HOT")) ){

			throw new InvalidIdException ("ItemId must contain a category header (DES, HOT, CLD, ADD, FOD) ");

		}

		try
		{
			Integer.parseInt(itemId.substring(3,6));

		}
		catch(NumberFormatException | StringIndexOutOfBoundsException e)
		{
			throw new InvalidIdException ("ItemId must contain a 3 digit number");
		}


		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemQuantity = 0;
		this.itemCategory = itemCategory;
		this.itemDescription = itemDescription;
		this.itemDuration = itemDuration2;
	}

	//getter methods
	public String getId() {
		return itemId;
	}

	public String getName() {
		return itemName;
	}

	public float getPrice() {
		return itemPrice;
	}

	public String getCategory() {
		return itemCategory;
	}

	public String getDescription() {
		return itemDescription;
	}

	public int getQuantity() {
		return itemQuantity;
	}

	public long getItemDuration() {
		return itemDuration;
	}

	public void setQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	//checks if 2 objects are the same by comparing ids
	public boolean equals(Object other)
	{
		if(other instanceof Item) {
			Item otherItem = (Item) other;
			return itemId.equals(otherItem.getId());
		}
		else {
			return false;
		}
	}

	//checks if 2 Item objects are the same by comparing ids
	public int compareToItemId(Item otherDetails)
	{
		return itemId.compareTo(otherDetails.getId());
	}     

	//return a string containing the details of the Item object
	public String toString()
	{
		return String.format("%-15s", itemId ) + String.format("%-15s", itemName) +
				itemPrice +String.format("%-10s", "") + String.format("%-15s", itemCategory)
				+ String.format("%-15s", itemDescription);
	}

	public int compareToItemCategory(Item otherDetails) {

		return itemCategory.compareTo(otherDetails.getCategory());
	}

	public int compareToItemName(Item otherDetails) {

		return itemName.compareTo(otherDetails.getName());
	}
	public int compareTo(Item arg0) {
		return 0;
	}


}