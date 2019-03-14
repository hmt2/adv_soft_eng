package coffeShop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class Log {
	private static String filepath;
	
	public Log()  {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
		filepath = "log:" + timeStamp;
		createLogFile(filepath);
	}
	
	public void createLogFile(String filepath) {
		File file = new File(filepath);
		  
		//Create the file
		try {
			if (file.createNewFile())
			{
			    System.out.println("Log file is created!");
			} else {
			    System.out.println("Log file already exists.");
			}
			
			String str = "Start of file: \n";
			BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
		    writer.write(str);
		    writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addCustomer(Customer cust) throws IOException {
		String timeStamp = new SimpleDateFormat("HH.mm.ss").format(new Date());
		String str = timeStamp + "   Adding Customer ID to queue:  " +  cust.getCustomerId() + "\n";
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true));
		writer.append(str);
		writer.close();   
	}
	
	public void processingCustomer(Customer cust) throws IOException {
		String timeStamp = new SimpleDateFormat("HH.mm.ss").format(new Date());
		String str = timeStamp + "   Processing Customer ID: " + cust.getCustomerId() + "\n";
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true));
	    writer.append(str);
	    writer.close();   
	}
	
	public static void writeToLog(String input) throws IOException {
		String timeStamp = new SimpleDateFormat("HH.mm.ss").format(new Date());
		String str = input + "\n";
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true));
	    writer.append(str);
	    writer.close();   
	}
	
}
