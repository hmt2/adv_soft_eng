package coffeShop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Map;
import java.util.Set;



public class SalesReport {
	
	Menu menu;
	private float totalBillBeforeDiscount;
	private float totalBillAfterDiscount;
	CustomerList customerList;
	
//Constructor that get the menu updated inside the GUI (with the new quantities)	
	public SalesReport(Menu menu, float totalBillBeforeDiscount, float totalBillAfterDiscount) {
		this.menu= menu; 
		this.totalBillBeforeDiscount =totalBillBeforeDiscount;
		this.totalBillAfterDiscount =  totalBillAfterDiscount;
		createSalesreport();
	}
	
	
	public float getTotalBillBeforeDiscount() {
		return totalBillBeforeDiscount;
	}

	public void setTotalBillBeforeDiscount(float totalBillBeforeDiscount) {
		this.totalBillBeforeDiscount = totalBillBeforeDiscount;
	}

	public float getTotalBillAfterDiscount() {
		return totalBillAfterDiscount;
	}

	public void setTotalBillAfterDiscount(float totalBillAfterDiscount) {
		this.totalBillAfterDiscount = totalBillAfterDiscount;
	}

	
	/**The end of the day report prints for each item of the menu the Id, name and its quantity 
	Then it prints the total of all the bills of the day
	**/
	public void createSalesreport() {
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
		
		try(FileWriter fw = new FileWriter(timeStamp + ".csv", true);
				
			    BufferedWriter bw = new BufferedWriter(fw);
				
			    PrintWriter out = new PrintWriter(bw))
		
			{// header
			//System.out.println("File created"); for testing
			out.println(  "Id,Name, Quantity ");
			//Print Menu
			Set<Map.Entry<String,Item>> mapset = menu.getEntrySet();
			for (Map.Entry<String,Item> entry : mapset)
	    	{
				out.println(entry.getKey().toString() + ", " + entry.getValue().getName().toString() + ", " + String.valueOf(entry.getValue().getQuantity()));
	    	}
						    
			//Bill before Discount
			    out.println("Total bill before discount :"+String.valueOf(totalBillBeforeDiscount));
			//Bill after Discount
			    out.println("Total bill after discount :"+String.valueOf(totalBillAfterDiscount));
			} 
		catch (IOException e) 
		{
			    //exception handling
		}
	
	}
	
/** For testing
	public static void main (String arg[]) {
		Menu newMenu = new Menu(); 
		SalesReport newSalesreport = new SalesReport(newMenu,38.8f, 25.9f);
		System.out.println("gate 1");
		newSalesreport.createSalesreport();
	}**/
}
