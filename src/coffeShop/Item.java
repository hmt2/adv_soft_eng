package coffeShop;

public class Item {
	private String itemId;
    private String itemName;
	private float itemPrice;
	private int itemQuantity;
	private String itemCategory;
	private String itemDescription;

	public Item(String itemId, String itemName, float itemPrice, String itemCategory, String itemDescription){
		if( itemId.trim().length() ==0|| itemName.trim().length()== 0 || itemCategory.trim().length()== 0)    
        {
          throw new IllegalStateException(
             "Cannot have a blank id, name or category");
        }
		if( itemPrice < 0) {
			throw new IllegalStateException(
		      "Cannot have a negative price");
		}
		
		this.itemId = itemId.trim();
		this.itemName = itemName.trim();
		this.itemPrice = itemPrice;
		this.itemQuantity = 0;
		this.itemCategory = itemCategory.trim();
		this.itemDescription = itemDescription;
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
    public int compareTo(Item otherDetails)
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

	public int compareToItemCategory(Item value) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int compareToItemName(Item value) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int compareToItemId(Item value) {
		// TODO Auto-generated method stub
		return 0;
	}
    
	
	
}