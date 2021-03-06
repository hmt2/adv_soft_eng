package discounts;


import java.util.ArrayList;

public class Discount {
	private String name;
	private int id;
	private ArrayList<String> discountCodes;
	private double price;

	public Discount(String name, int id, ArrayList<String> discountCodes, double price) {
		this.name = name;
		this.id = id;
		this.discountCodes = discountCodes;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<String> getDiscountCodes() {
		return discountCodes;
	}

	public void setDiscountCodes(ArrayList<String> discountCodes) {
		this.discountCodes = discountCodes;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return name + '\n' + Integer.toString(id) + '\n' + Double.toString(price);
	}

}
