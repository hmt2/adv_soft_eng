
public class Item {
	private String itemId;
	private String itemName;
	private float itemPrice;
	private String itemCategory;
	
	private String itemDescription;
	
	public Item(String itemId, String itemName, float itemPrice, String itemCategory, String itemDescription)
    {   
        this.itemId =itemId.trim();
        this.itemName = itemName.trim();
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory.trim();
        
        this.itemDescription = itemDescription.trim();
    }

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public float getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	
	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}


}
