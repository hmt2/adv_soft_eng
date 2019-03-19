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
			Arrays.asList("FOOD001", "FOOD002","FOOD003", "FOOD004", "FOOD005")); 
	public List<String> completedItem = new LinkedList<String>();

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
	}

	@Override
	public void run() {
		WaitingQueue.getInstance().setServer(this.serverId-1, customer);
		System.out.println("Server " + this.serverId + " is processing customer " + customer.getCustomerId());
		try {
			Log.writeToLog("Server " + this.serverId + " is processing customer " + customer.getCustomerId());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String> itemsOrdered = customer.getItemIds();
		System.out.println(itemsOrdered);
		try {
			for(int index = 0; index < itemsOrdered.size(); index++){

				try {
					Item nextFood = Simulation.menu.findItemId(itemsOrdered.get(index));

					if (nextFood.getCategory().equals("HotDrink")){
						synchronized(Simulation.coffeeMachine.getFoodsPreparing()){
							while(!(Simulation.coffeeMachine.getFoodsPreparing().size() < Simulation.coffeeMachine.maxAmount)){
								Simulation.coffeeMachine.getFoodsPreparing().wait();
							}

							Simulation.coffeeMachine.prepareFood(this, customer.getCustomerId(), nextFood);
							Simulation.coffeeMachine.getFoodsPreparing().notifyAll();

						}
					}else if(nextFood.getCategory().equals("ColdDrink")){
						synchronized(Simulation.drinkDispenser.getFoodsPreparing()){
							while(!(Simulation.drinkDispenser.getFoodsPreparing().size() < Simulation.drinkDispenser.maxAmount)){
								Simulation.drinkDispenser.getFoodsPreparing().wait();
							}
							Simulation.drinkDispenser.prepareFood(this,customer.getCustomerId(), nextFood);
							Simulation.drinkDispenser.getFoodsPreparing().notifyAll();

						}

					}else if(nextFood.equals("FOOD006") |  nextFood.equals("FOOD0010")){
						synchronized(Simulation.hob.getFoodsPreparing()){
							while(!(Simulation.hob.getFoodsPreparing().size() < Simulation.hob.maxAmount)){
								Simulation.hob.getFoodsPreparing().wait();
							}
							Simulation.hob.prepareFood(this,customer.getCustomerId(), nextFood);
							Simulation.hob.getFoodsPreparing().notifyAll();

						}
					}else if (sandwiches.contains(nextFood)){
						synchronized(Simulation.toaster.getFoodsPreparing()){
							while(!(Simulation.toaster.getFoodsPreparing().size() < Simulation.hob.maxAmount)){
								Simulation.toaster.getFoodsPreparing().wait();
							}
							Simulation.toaster.prepareFood(this,customer.getCustomerId(), nextFood);
							Simulation.toaster.getFoodsPreparing().notifyAll();

						}

					}else{
						System.out.println("Server " + serverId + " has started preparing " + nextFood.getName() + " for customer " + customer.getCustomerId()); 
						Thread.sleep(20000);
						System.out.println("Server " + serverId + " has finished " + nextFood.getName()+ " for customer " + customer.getCustomerId());

						completedItem.add(nextFood.getName());
					}
				

				} catch (IdNotContainedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			synchronized(completedItem){
				while(!(completedItem.size() == customer.getItemIds().size())){
					completedItem.wait();
					completedItem.notifyAll();
	
			
				}
			}
			System.out.println("Server " + this.serverId + " has finished the order for customer " + customer.getCustomerId());

			completedItem = new LinkedList<String>();

			WaitingQueue.getInstance().clearServer(this.serverId-1);
			WaitingQueue.getInstance().addCollectionQueue(customer);
			if(WaitingQueue.getInstance().getSizeCollectionQueue() > 1) {
				WaitingQueue.getInstance().removeTopCollectionQueue();
			}
			try {
				Log.writeToLog("Server " + this.serverId + " has finished the order for customer " + customer.getCustomerId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}
