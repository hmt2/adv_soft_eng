package output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import coffeShop.Customer;

public class Log {
	private static String filepath;

	public Log()  {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
		filepath = "log:" + timeStamp + ".csv";
		createLogFile(filepath);
	}

	public void createLogFile(String filepath) {
		try {
			FileWriter fw = new FileWriter(filepath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);

			out.println("Start of log: \n ");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeToLog(String input) throws IOException {
		String timeStamp = new SimpleDateFormat("HH.mm.ss").format(new Date());
		String str = timeStamp + ":  " + input + "\n";
		BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true));
		writer.append(str);
		writer.close();   
	}

}