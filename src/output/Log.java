package output;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class Log {
	private static String filepath;

	public Log()  {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
		filepath = "Log-" + timeStamp + ".csv"; 
		createLogFile(filepath);
	}

	public void createLogFile(String filepath) {
		try(
			FileWriter fw = new FileWriter(filepath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw))
		{
			out.append("Start of log: \n ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeToLog(String input) throws IOException {
		String timeStamp = new SimpleDateFormat("HH.mm.ss").format(new Date());
		String str = timeStamp + ":  " + input + "\n";
		try(FileWriter fw = new FileWriter(filepath, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw))
		{
			out.append(str);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}