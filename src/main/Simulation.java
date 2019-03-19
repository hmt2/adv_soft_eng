package main;

import coffeShop.*;
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

	public static void runSimulation(int numServer) throws IdNotContainedException, IOException, InterruptedException {
		Queue<Server> servers = new LinkedList<>();
		Map<Thread, Server> busy = new HashMap<>();
		for(int i = 0; i < numServer; i++) {
			servers.add(new Server(i+1));
		}
	
		toaster = new Machine("Toaster", 5);
		hob = new Machine("Hob", 4);
		coffeeMachine = new Machine("Coffee Machine", 5);
		drinkDispenser = new Machine("Drink dispenser", 4);
		while(true) {
		    
			if(!servers.isEmpty() && !WaitingQueue.getInstance().isEmpty()) {
				Server s = servers.poll();
				servers.add(s);
				Customer c = WaitingQueue.getInstance().dequeue();
				if(c != null && s != null) {
					s.serveCustomer(c);
					Thread t = new Thread(s);
					t.start();
					busy.put(t, s);
					Thread.sleep(1000);
				}

			} else {
				Iterator<Map.Entry<Thread, Server>> it = busy.entrySet().iterator();
				while(it.hasNext()) {
					Map.Entry<Thread, Server> e = it.next();
					if(!e.getKey().isAlive()) {
						servers.add(e.getValue());
						it.remove();
					}
				}
			}
			pause(5);
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		menu = new Menu();
		AllOrders allorders = new AllOrders();
		CustomerList customerList = new CustomerList();
		AllDiscounts allDiscounts = new AllDiscounts();
		final DiscountCheck discountCheck = new DiscountCheck(allDiscounts.loadDiscounts());

		try {
			WaitingQueue.getInstance().addPreviousOrders(discountCheck, true);
		} catch (DuplicateIDException | IdNotContainedException e) {
			e.printStackTrace();
		}
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
