package main;

import coffeShop.*;
import model.CurrentQueue;
import discounts.AllDiscounts;
import discounts.DiscountCheck;
import exceptions.DuplicateIDException;
import exceptions.IdNotContainedException;
import model.MenuGUI;

import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;


public class Simulation {
	private static CoffeShopInterface interaction;
	private static MenuGUI gui;
	public static Menu menu;
	public static Machine hob;
	public static Machine drinkDispenser;
	public static Machine toaster;
	public static Machine coffeeMachine;
	private CurrentQueue modeldata;

	public static void showInterface() throws DuplicateIDException, IdNotContainedException {
		interaction = new CoffeShopInterface(menu);
	}
	public static void showGUI() throws DuplicateIDException, IdNotContainedException {
		gui = new MenuGUI(menu, interaction);
		gui.setVisible(true);
	}

	private static void pause(long millis) {
		long start = Calendar.getInstance().getTimeInMillis();
		while(Calendar.getInstance().getTimeInMillis() - start < millis);
	}

	public static void runSimulation(int numServer) throws IdNotContainedException, IOException {
		Queue<Server> servers = new LinkedList<>();
		Map<Thread, Server> busy = new HashMap<>();
		for(int i = 0; i < numServer; i++) {
			servers.add(new Server(i+1));
		}
	
		//Begin all the machines, indicating their name and the maximum capacity
		toaster = new Machine("Toaster", 5);
		hob = new Machine("Hob", 4);
		coffeeMachine = new Machine("Coffee Machine", 5);
		drinkDispenser = new Machine("Drink dispenser", 4);
		
		//As long as the waiting queue is not empty, continue processing new orders.  If there are servers
		//that are free, assign them a new customer and initiate a thread
		while(true) {

			if(!servers.isEmpty() && !WaitingQueue.getInstance().isEmpty()) {
				Server s = servers.poll();
				Customer c = WaitingQueue.getInstance().dequeue(); //get the first customer in the queue
				if(c != null && s != null) {
					s.serveCustomer(c);
					Thread t = new Thread(s);
					t.start();
					busy.put(t, s); //place the server as "busy"
				}

			} else {
				
				//Loops over all the servers in the "busy" hashmap and if any of the threads is dead, it will be added to the queue of free servers
				//so they can process another customer
				Iterator<Map.Entry<Thread, Server>> it = busy.entrySet().iterator();
				while(it.hasNext()) {
					Map.Entry<Thread, Server> e = it.next();
					if(!e.getKey().isAlive()) {
						servers.add(e.getValue());

						it.remove();
					}
				}
			}
			pause(500); 
			if(WaitingQueue.getInstance().isEmpty() && busy.isEmpty()){
                break;
			
			}
		}
		
		  
		WaitingQueue.getInstance().emptyCollectionQueue();
		System.out.println("Cafeteria is closed");
		
	
	}

	public static void main(String[] args) throws IOException {
		menu = new Menu();
		AllOrders allorders = new AllOrders();
		CustomerList customerList = new CustomerList();
		AllDiscounts allDiscounts = new AllDiscounts();
		final DiscountCheck discountCheck = new DiscountCheck(allDiscounts.loadDiscounts());

		int numServer = 4;
		try {
			showInterface();
		} catch (DuplicateIDException | IdNotContainedException e) {
			e.printStackTrace();
		}
		try {
			showGUI();
		} catch (DuplicateIDException | IdNotContainedException e) {
			e.printStackTrace();
		}

		try {
			runSimulation(numServer);
		} catch (IdNotContainedException e) {
			e.printStackTrace();
		}
	}
}
