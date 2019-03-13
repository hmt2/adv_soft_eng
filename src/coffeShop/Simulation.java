package coffeShop;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;





class Server implements Runnable {
	private Customer customer;
	private int serverId;
	private ArrayList<String> sandwiches = new ArrayList<String>( 
            Arrays.asList("FOOD001", "FOOD002","FOOD003", "FOOD004", "FOOD005")); 
	public List<String> finishedFood = new LinkedList<String>();


	public Server(int serverId) {
         this.serverId = serverId;
		
	}

	public Server(Customer customer) { //do we need this?
		this.customer = customer;
		
	}

	public int getServerId() {
 		return serverId;
 	}
	
	public void serveCustomer(Customer customer) throws IdNotContainedException, IOException {
		this.customer = customer;
		System.out.println("Server " + this.serverId + " is processing customer " + customer.getCustomerId());
	    Log.writeToLog("Server " + this.serverId + " is processing customer " + customer.getCustomerId());
		ArrayList<String> itemsOrdered = customer.getItemIds();
		System.out.println(itemsOrdered);
		try {
			for(int index = 0; index < itemsOrdered.size(); index++){
				Item nextFood = Simulation.menu.findItemId(itemsOrdered.get(index));
				if (nextFood.getCategory().equals("HotDrink")){
					synchronized(Simulation.coffeeMachine.foodsPreparing){
						while(!(Simulation.coffeeMachine.foodsPreparing.size() < Simulation.coffeeMachine.maxAmount)){
								Simulation.coffeeMachine.foodsPreparing.wait();
						}
	
							Simulation.coffeeMachine.makeFood(this, customer.getCustomerId());
						    Simulation.coffeeMachine.foodsPreparing.notifyAll();
	
					}
				}else if(nextFood.getCategory().equals("ColdDrink")){
					synchronized(Simulation.drinkDispenser.foodsPreparing){
						while(!(Simulation.drinkDispenser.foodsPreparing.size() < Simulation.drinkDispenser.maxAmount)){
							Simulation.drinkDispenser.foodsPreparing.wait();
						}
						Simulation.drinkDispenser.makeFood(this,customer.getCustomerId());
						Simulation.drinkDispenser.foodsPreparing.notifyAll();
						
					}
					
				}else if(nextFood.equals("FOOD006") |  nextFood.equals("FOOD0010")){
					synchronized(Simulation.hob.foodsPreparing){
						while(!(Simulation.hob.foodsPreparing.size() < Simulation.hob.maxAmount)){
							Simulation.hob.foodsPreparing.wait();
						}
						Simulation.hob.makeFood(this,customer.getCustomerId());
						Simulation.hob.foodsPreparing.notifyAll();
						
				}
				}else if (sandwiches.contains(nextFood)){
					synchronized(Simulation.toaster.foodsPreparing){
						while(!(Simulation.toaster.foodsPreparing.size() < Simulation.hob.maxAmount)){
							Simulation.toaster.foodsPreparing.wait();
						}
						Simulation.toaster.makeFood(this,customer.getCustomerId());
						Simulation.toaster.foodsPreparing.notifyAll();
					
				}
				
			}else{
				Thread.sleep(nextFood.getItemDuration()*1000);
				System.out.println("Server " + this.getServerId() + " has finished " + nextFood.getName() + " for customer " + customer.getCustomerId());
			}
				
			}

			System.out.println("Server " + this.serverId + " has finished the order for customer " + customer.getCustomerId());
			 Log.writeToLog("Server " + this.serverId + " has finished the order for customer " + customer.getCustomerId());
			}
			
		catch(InterruptedException e) {
					// This code assumes the provided code in the Simulation class
					// that interrupts each cook thread when all customers are done.
					// You might need to change this if you change how things are
					// done in the Simulation class.
					e.printStackTrace();
				}
		}
		

	

	@Override
	public void run() {
		try {
			Thread.sleep(new Random().nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(customer);

	}

}



public class Simulation {
	private static CoffeShopInterface interaction;
	private static MenuGUI gui;
	public static Menu menu;
	public static Machine hob;
	public static Machine drinkDispenser;
	public static Machine toaster;
	public static Machine coffeeMachine;
	private static int serverId = 1;
	

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

	public static void runSimulation(int numServer, int machinemaxAmount) throws IdNotContainedException, IOException {
		Queue<Server> servers = new LinkedList<>();
		Map<Thread, Server> busy = new HashMap<>();
		for(int i = 0; i < numServer; i++) {
			servers.add(new Server(i+serverId));
			serverId ++;
		}
		// Start up machines
		toaster = new Machine("Toaster", "Toast, panini", machinemaxAmount);
		hob = new Machine("Hob", "Hot food", machinemaxAmount);
		coffeeMachine = new Machine("Coffee Machine", "Hot Drink", machinemaxAmount);
		drinkDispenser = new Machine("Drink dispenser", "Cold Drink", machinemaxAmount);
		while(true) {
			
			while (WaitingQueue.getInstance().size() > servers.size()*2){ //if the customer queue is double the staff size add another member. Can be an if too instead of while
                servers.add(new Server(serverId));			
			}
			while (WaitingQueue.getInstance().size() < servers.size()/2){ //if the number of customers is less than half of the existing staff, remove
				servers.poll();
			}
			if(!servers.isEmpty() && !WaitingQueue.getInstance().isEmpty()) {
				Server s = servers.poll();
				Customer c = WaitingQueue.getInstance().dequeue();
				if(c != null && s != null) {
					s.serveCustomer(c);
					Thread t = new Thread(s);
					t.start();
					busy.put(t, s);
					

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
			pause(500);
		}
	}

	public static void main(String[] args) throws IOException {
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
		int machinemaxAmount = 4;
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
		
		TimerTask task = new TimerTask() {
	        private final int MAX_SECONDS = 2;
	        private int seconds = 0;
	        @Override
	        public void run() { 
	            if (seconds > MAX_SECONDS) {
	                seconds = 0;
	                ArrayList<String> items = new ArrayList<>();
	                items.add("FOD010");
	                try {
	                	System.out.println("Adding: new customer");
	              
						WaitingQueue.getInstance().addCustomer(items, discountCheck, new Random().nextBoolean());
					} catch (DuplicateIDException | IdNotContainedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            } else {
	            	seconds++;
	            }
	        }
	    };
	    new Timer().schedule(task, 0, 1000);

		try {
			runSimulation(numServer, machinemaxAmount);
		} catch (IdNotContainedException e) {
			e.printStackTrace();
		}

	}


}
