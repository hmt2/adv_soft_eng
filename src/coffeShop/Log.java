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
	private String filepath;
	
	public Log(String path)  {
		filepath = path;
		createLogFile(path);
	}
	
	
	public void createLogFile(String filepath) {
		File file = new File(filepath);
		  
		//Create the file
		try {
			if (file.createNewFile())
			{
			    System.out.println("File is created!");
			} else {
			    System.out.println("File already exists.");
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
		String str = "World \n";
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true));
	    writer.append(' ');
	    writer.append(str);
	    writer.close();
	   
	}
	
}