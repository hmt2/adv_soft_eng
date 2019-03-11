package coffeShop;

import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

class Server implements Runnable {
	private Customer customer;

	public Server() {}

	public Server(Customer customer) {
		this.customer = customer;
	}

	public void serveCustomer(Customer customer) {
		this.customer = customer;
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
	private static Menu menu;

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

	public static void runSimulation(int numServer) throws IdNotContainedException {
		Queue<Server> servers = new LinkedList<>();
		Map<Thread, Server> busy = new HashMap<>();
		for(int i = 0; i < numServer; i++) {
			servers.add(new Server());
		}
		while(true) {
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

	public static void main(String[] args) {
		menu = new Menu();
		AllOrders allorders = new AllOrders();
		CustomerList customerList = new CustomerList();
		AllDiscounts allDiscounts = new AllDiscounts();
		DiscountCheck discountCheck = new DiscountCheck(allDiscounts.loadDiscounts());

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
	                	WaitingQueue.getInstance().updateDisplay();
	                	
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
			runSimulation(numServer);
		} catch (IdNotContainedException e) {
			e.printStackTrace();
		}

	}


}
