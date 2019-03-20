package coffeShop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import exceptions.IdNotContainedException;
import main.Simulation;
import output.Log;

public class Server implements Runnable {
	private Customer customer;
	private int serverId;
	private ArrayList<String> sandwiches = new ArrayList<String>( 
			Arrays.asList("FOD001", "FOD002","FOD003", "FOD004", "FOD005")); 
	public List<String> completedItem = new LinkedList<String>(); //list of all the items that are ready for a particular customer

	public Server(int serverId) {
		this.serverId = serverId;
	}

	public Server(Customer customer) { 
		this.customer = customer;
	}

	public int getServerId() {
		return serverId;
	}

	// Returns the current customer that the server is serving
	public void serveCustomer(Customer customer) throws IdNotContainedException, IOException {
		this.customer = customer;
	}
	
	/**
	 * Runs the server thread, initiates the corresponding machine threads, and prints the output to log. 
	 * Depending on the items ordered by the customer, the processing will be different
	 * -Sandwiches: initiate "Toaster" machine thread
	 * -Cold drinks: initiate "Drink Dispenser" machine thread
	 * -Hot drinks: initiate "Coffee Machine" machine thread
	 * -Soup or fried food: initiate "Hob" machine thread
	 * -Others (salads, cakes): the server will prepare them without the need of a machine or another thread
	 * Each machine has a maximum capacity, if it is busy, the server will need to wait to process the item. 
	 */
   
	@Override
	public void run() {
		WaitingQueue.getInstance().setServer(this.serverId-1, customer); //Displays the customer that the server is serving at the moment, and the items that are processing
		System.out.println("Server " + this.serverId + " is processing customer " + customer.getCustomerId());
	    try {
			Log.writeToLog("Server " + this.serverId + " is processing customer " + customer.getCustomerId());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ArrayList<String> itemsOrdered = customer.getItemIds(); //Retrieve the ids of the items ordered by the customer
		System.out.println(itemsOrdered);
		try {
			for(int index = 0; index < itemsOrdered.size(); index++){  //process each item ordered by the customer 

				try {
					Item nextFood = Simulation.menu.findItemId(itemsOrdered.get(index));
                    //Depending on the type of item, it will be sent to a different machine
					//If it is a hot drink, it will be processed by the coffee machine. Cold drinks by drink dispenser,  sandwiches by toaster, fried food and soup by hob, and the remaining by the server himself
					if (nextFood.getCategory().equals("HotDrink")){
						synchronized(Simulation.coffeeMachine.getFoodsPreparing()){ //each coffee machine can process coffees from different servers (can handle multiple threads) which need to be synchronised
							//If the machine is already preparing more than the maximum amount of items it can handle, it will tell the calling thread to go to sleep 
							while(!(Simulation.coffeeMachine.getFoodsPreparing().size() < Simulation.coffeeMachine.maxAmount)){
								Simulation.coffeeMachine.getFoodsPreparing().wait();
							}
                   
							Simulation.coffeeMachine.prepareFood(this, customer.getCustomerId(), nextFood);  //start a new thread and making a new coffee
							Simulation.coffeeMachine.getFoodsPreparing().notifyAll(); //awake the threads waiting on getFoodsPreparing() object

						}
					}else if(nextFood.getCategory().equals("ColdDrink")){
						synchronized(Simulation.drinkDispenser.getFoodsPreparing()){
							while(!(Simulation.drinkDispenser.getFoodsPreparing().size() < Simulation.drinkDispenser.maxAmount)){
								Simulation.drinkDispenser.getFoodsPreparing().wait();
							}
							Simulation.drinkDispenser.prepareFood(this,customer.getCustomerId(), nextFood);
							Simulation.drinkDispenser.getFoodsPreparing().notifyAll();

						}

					}else if(nextFood.getId().equals("FOD006") |  nextFood.getId().equals("FOD010")){
						synchronized(Simulation.hob.getFoodsPreparing()){
							while(!(Simulation.hob.getFoodsPreparing().size() < Simulation.hob.maxAmount)){
								Simulation.hob.getFoodsPreparing().wait();
							}
							Simulation.hob.prepareFood(this,customer.getCustomerId(), nextFood);
							Simulation.hob.getFoodsPreparing().notifyAll();

						}
					}else if (sandwiches.contains(nextFood.getId())){
						synchronized(Simulation.toaster.getFoodsPreparing()){
							while(!(Simulation.toaster.getFoodsPreparing().size() < Simulation.hob.maxAmount)){
								Simulation.toaster.getFoodsPreparing().wait();
							}
							Simulation.toaster.prepareFood(this,customer.getCustomerId(), nextFood);
							Simulation.toaster.getFoodsPreparing().notifyAll();

						}
             
					}else{
						System.out.println("Server " + serverId + " has started preparing " + nextFood.getName() + " for customer " + customer.getCustomerId()); 
						Log.writeToLog("Server " + serverId + " has started preparing " + nextFood.getName() + " for customer " + customer.getCustomerId()); 

						Thread.sleep(nextFood.getItemDuration()*500);
						System.out.println("Server " + serverId + " has finished " + nextFood.getName()+ " for customer " + customer.getCustomerId());
						Log.writeToLog("Server " + serverId + " has finished " + nextFood.getName()+ " for customer " + customer.getCustomerId());

						completedItem.add(nextFood.getName()); //add the processed item as completed
					}
				

				} catch (IdNotContainedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
			//Wait until all the items for a particular customer has been completed
			synchronized(completedItem){ 
				while(!(completedItem.size() == customer.getItemIds().size())){
					completedItem.wait();
					completedItem.notifyAll();
			
				}
				
			}
			System.out.println("Server " + this.serverId + " has finished the order for customer " + customer.getCustomerId());
			Log.writeToLog("Server " + this.serverId + " has finished the order for customer " + customer.getCustomerId());

			completedItem = new LinkedList<String>();

			WaitingQueue.getInstance().clearServer(this.serverId-1); //clear the customer for a particular server from the GUI once all items are ready
			WaitingQueue.getInstance().addCollectionQueue(customer); //add the customer to the collection queue
			if(WaitingQueue.getInstance().getSizeCollectionQueue() > 4) { //Whenever there are more than 4 customers, the first is dequeued as sign that the customer has picked up the order
				
				WaitingQueue.getInstance().removeTopCollectionQueue();
			}

		}

		catch(InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
}
