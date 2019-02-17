package coffeShop;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map.Entry;

public class Menu {
	private LinkedHashMap<String, Item> menu;
	//private LinkedHashMap<String,Item> menu;
	
	public Menu()
    {
		menu = new LinkedHashMap<String, Item>() ;
		loadMenu();
    }
	
	public void loadMenu() {
		/**
		 * loads a text file of orders
		 */
	    	//Initialize empty list 
	        BufferedReader buff = null;
	    	String data [] = new String[5];
	    	try {
				buff = new BufferedReader(new FileReader("Menu.csv"));
				String inputLine = null;

				inputLine = buff.readLine();
				
				//need to skip the first line 
				inputLine = buff.readLine();
				//read first line
		    	while(inputLine != null){  
		    		//split line into parts
		    		data  = inputLine.split(",");
		    		//create Order object
		    		String itemId = data[0].trim();
		    		String itemName = data[1].trim();
		    		float itemPrice = Float.parseFloat(data[2].trim());
		    		String itemCategory = data[3].trim();
		    		String itemDescription = data[4].trim();
		    		// Composition of an Item : String String float float String String
		    		Item i = new Item(itemId, itemName, itemPrice, itemCategory, itemDescription);
		    		//add to LinkedHashMap
		    		menu.put(itemId, i);
		            //menu.put(itemId, i);
		            //read next line
		            inputLine = buff.readLine();
		    	}
	            
		    }
		    catch(FileNotFoundException e) {
		    	System.out.println(e.getMessage());
		        System.exit(1);
		    }
		    catch (IOException e) {
		    	e.printStackTrace();
		        System.exit(1);        	
		    }
		    finally  {
		    	try{
		    		buff.close();
		    	}
		    	catch (IOException ioe) {
		    		//don't do anything
		    	}
		    }
		
	}
	
	public Set<Map.Entry<String,Item>> getEntrySet()
	{
    	return menu.entrySet();
    }
	
	// method to find an item by its ID
	public Item findItemId(String itemId) {
		
		Item i ;
		i  = menu.get(itemId);
		return i;
		
	}
	
	public int getSize() {
		return menu.size();
	}
	

	//method to find an item by its name
	public Item findItemName(String itemName) {//need check
		
		for (Item i : menu.values())
    	{
    		if (i.getName().equals(itemName))
    		{
    			return i;
    		}
    	}
    	return null;
	}
	
	public Set<String> getKeySet(){
		return menu.keySet();
	}
	

	public String listOutput(Map<String, Item> sortedMap)
    {
		StringBuffer allEntries = new StringBuffer();
        for(Map.Entry<String, Item> details : sortedMap.entrySet()) {
            allEntries.append(details);
            allEntries.append('\n');
        }
        return allEntries.toString();
    }
	
	
	public String listByItemId() {
		List<Map.Entry<String, Item>> entries = new ArrayList<Map.Entry<String, Item>>(menu.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Item>>() {
			  public int compare(Map.Entry<String, Item> i1, Map.Entry<String, Item> i2){
			    return i1.getValue().compareToItemId(i2.getValue());
			  }
			});
			Map<String, Item> sortedMap = new LinkedHashMap<String, Item>();
			for (Entry<String, Item> entry : entries) {
			  sortedMap.put(entry.getKey(), entry.getValue());
			}
		
    	return listOutput(sortedMap);
	}
	
	//we sort by the name of the product
	public String listByItemName() {
		List<Map.Entry<String, Item>> entries = new ArrayList<Map.Entry<String, Item>>(menu.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Item>>() {
			  public int compare(Map.Entry<String, Item> i1, Map.Entry<String, Item> i2){
			    return i1.getValue().compareToItemName(i2.getValue());
			  }
			});
			Map<String, Item> sortedMap = new LinkedHashMap<String, Item>();
			for (Entry<String, Item> entry : entries) {
			  sortedMap.put(entry.getKey(), entry.getValue());
			}
		
    	return listOutput(sortedMap);
	}
	
	//we sort by the category's name 
	public String listByItemCategory() {
		List<Map.Entry<String, Item>> entries = new ArrayList<Map.Entry<String, Item>>(menu.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Item>>() {
			  public int compare(Map.Entry<String, Item> i1, Map.Entry<String, Item> i2){
			    return i1.getValue().compareToItemCategory(i2.getValue());
			  }
			});
			Map<String, Item> sortedMap = new LinkedHashMap<String, Item>();
			for (Entry<String, Item> entry : entries) {
			  sortedMap.put(entry.getKey(), entry.getValue());
			}
		
    	return listOutput(sortedMap);
	}
	
	
	
		
}
