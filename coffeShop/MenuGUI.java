package coffeShop;

//import all the GUI classes
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.*;




/**
* Simple GUI for StaffList application
*/
public class MenuGUI extends JFrame implements ActionListener
{
  private Menu menu;
  private Map<String, Integer> currentOrder = new LinkedHashMap<String, Integer>();
  CustomerList customerList;
  AllOrders allorders;
  private float totalBeforeDiscount = 0;
  private double totalAfterDiscount = 0;
  private int customerId;
  private int orderId;
  
  
  //GUI components
  Container contentPane;
  JPanel southPanel;
  JPanel menuPanel;
  JTextArea displayBill;
  JScrollPane menuList;
  JScrollPane billList;
  JButton checkout;
  JButton buy;
  JButton back;
  JButton clear;

  public MenuGUI() throws DuplicateIDException, CalculationError 
  {
      menu = new Menu();
      allorders = new AllOrders();
      customerList = new CustomerList();
      addPreviousOrders();
      
      setTitle("Menu GUI");
      //ensure program ends when window closes
	  setDefaultCloseOperation(EXIT_ON_CLOSE);
	  setPreferredSize(new Dimension(500, 300));
	  
	  contentPane = getContentPane();
	  
	  southPanel = new JPanel();
      southPanel.setLayout(new GridLayout(1,1));
      southPanel.setBackground(Color.DARK_GRAY);
      
      //set up checkout button
      checkout = new JButton("Checkout"); 
      //specify action when button is pressed
      checkout.addActionListener(this) ;
      southPanel.add(checkout);
     
      //add south panel to the content pane (GUI)
      contentPane.add(southPanel, BorderLayout.SOUTH);

      //set up item buttons
      menuPanel = new JPanel(new GridLayout(menu.getSize(), 1));
      //calls method which creates a button for each item in menu and adds it to menuPanel
      loadItemButtons();
      
      //want to be able to scroll through the menu
      menuList = new JScrollPane(menuPanel);
      menuList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      menuList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      
	  contentPane.add(menuList);
	  
	  //buttons for bill page 
	  buy = new JButton("buy"); 
      buy.addActionListener(this);
	  back = new JButton("back"); 
      back.addActionListener(this) ;
      clear = new JButton("clear");
      clear.addActionListener(this);
      
	  //set up bill display for bill page
	  displayBill = new JTextArea(15,20);
      displayBill.setFont(new Font (Font.MONOSPACED, Font.PLAIN,14));
      displayBill.setEditable(false);
      billList = new JScrollPane(displayBill);
      billList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      billList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
         
      //pack and set visible
      pack();
      setVisible(true);
  }
  
  public void actionPerformed(ActionEvent e) 
  { 
	  //for buttons which represent the item there ActionCommand is set to their itemId
	  String command = e.getActionCommand();
	  Item isItem = menu.findItemId(command);

	  //if button pressed is an item button
	  if(isItem != null) {
		getQuantityGUI(command);
	  }
	  
	  //if checkout button is pressed then want to showing the Bill
	  if(e.getSource() == checkout)
		  switchBill();
	  //if back button is pressed then want to return to showing the menu
	  if(e.getSource() == back)
		  switchMenu();
	  
	  //where link to other classes!
	  //need to create customer
	  //need to create orders for each item with the newly created customer id
	  if(e.getSource() == buy) {
		 try {
			placeOrder();
		} catch (DuplicateIDException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CalculationError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	  }
	  
	  if(e.getSource() == clear) 
		  clearOrder();

  } 
 
  private void updateGUI() {
	  revalidate();
	  repaint();
  }
  
 
  //need to add in bill after discount
  private String displayBill() {
	  totalBeforeDiscount = 0;
	  String bill = "CHECKOUT \n";
	  
	  float billCurrentItem = 0;
	  int currentItemQuantity = 0;
	  
	  Set<String> keys = currentOrder.keySet();
		for(String key: keys){
			if(currentOrder.get(key) > 1) {
				currentItemQuantity = currentOrder.get(key);
				billCurrentItem = currentItemQuantity*menu.findItemId(key).getPrice();			
			}
			else {
				currentItemQuantity = 1;
				billCurrentItem = menu.findItemId(key).getPrice();
			}
			totalBeforeDiscount += billCurrentItem;
					
			bill += String.format("%-2s %s",currentItemQuantity,"x  ");
			bill += String.format("%-20s %s",menu.findItemId(key).getName(),"£",billCurrentItem);
			bill += String.format("%-10s",billCurrentItem);
			bill += "\n";
		}
		Discount dis = getDiscount(toArrayList(currentOrder));
		if(dis != null)
			totalAfterDiscount = dis.getPrice();
		else
			totalAfterDiscount = totalBeforeDiscount;
		
		bill += String.format("%-2s %s","Total bill before discount: £",totalBeforeDiscount);
		bill += "\n";
		bill += String.format("%-2s %s","Total bill after discount: £",totalAfterDiscount);
				
	  return bill;
  }
  
  private void loadItemButtons() {
	  Set<String> keys = menu.getKeySet();
		for(String key: keys){
			JButton button = new JButton(String.format("%30s%16s",menu.findItemId(key).getName(),"£" + menu.findItemId(key).getPrice()));
			button.addActionListener(this);
		    menuPanel.add(button);
			button.setActionCommand(key);
		}
  }
  
  private void switchBill() {
	  southPanel.removeAll();
      southPanel.add(buy);
      southPanel.add(back);
      southPanel.add(clear);
	  contentPane.remove(menuList);
	  contentPane.add(billList,BorderLayout.CENTER);
	  displayBill.setText(displayBill());
      
	  updateGUI();   
  }
  
  private void addPreviousOrders() throws DuplicateIDException, CalculationError {
	  TreeMap<Integer,ArrayList<String>> cust = allorders.loadOrders();
      Set<Integer> keys = cust.keySet();
		for(Integer key: keys){
			float totalBeforeDiscount = calcBillBeforeDiscount(cust.get(key));
			float totalAfterDiscount = totalBeforeDiscount;
			Discount ds = getDiscount(cust.get(key));
			if(ds != null)
				totalAfterDiscount = (float) ds.getPrice(); //does it replace it or do we have to remove the discount from actual bill?
			try {
				customerList.addCustomer(key, cust.get(key), totalBeforeDiscount, totalAfterDiscount);
			}catch (DuplicateIDException e){
				continue; //if a duplicate is found when creating it will be omitted
			}
		}
	    this.customerId = cust.lastKey()+1;
	    this.orderId = 200; //for sample
	    //some way of getting the last order Id
	    //try{
	    //	orderId = ent.getKey()+1; //exception in case it is not integer? but should be handled in allOrders
	    //  if (orderId < 1){
	    //  	throw new IllegalArgumentException("Order id is not of the right format"); //is this necessary? 
	    //  }
	    //}catch(NoSuchElementException | IllegalArgumentException e) {
	    // 	orderId = 1; //if there are no other orders start from 1
	    //  }
	    
	    
	   

		
  }
  
  public void clearOrder() {
	  currentOrder.clear();
	  displayBill.setText(displayBill());
	  updateGUI();
  }
  
  private void switchMenu() {
	  southPanel.removeAll();
	  contentPane.add(menuList);
	  contentPane.remove(billList);	    
      southPanel.add(checkout);   
      
	  updateGUI();   
  }
  
  ArrayList<String> toArrayList(Map<String, Integer> order){
	  ArrayList<String> itemIds = new ArrayList<String>();
		
		Set<String> keys = order.keySet();
		for(String key: keys){
			for(int i =0; i < order.get(key); i++) {
				itemIds.add(key);
			}
		}
		return itemIds;
  }
   
  
  public Discount getDiscount(ArrayList<String> itemIds) {
	    AllDiscounts ad = new AllDiscounts();
		ArrayList<Discount> discounts = ad.loadDiscounts();
		DiscountCheck dc = new DiscountCheck(discounts);
		Discount custDiscount = dc.checkForDeals(itemIds);
		return custDiscount;
}

  
   private float calcBillBeforeDiscount(ArrayList<String> itemIds) {
  // while loop
	  float billBeforeDiscount = 0;
	  try {
		  for(String itemid : itemIds) {
		  	billBeforeDiscount += menu.findItemId(itemid).getPrice();
		  }
	      if (billBeforeDiscount < 0 || billBeforeDiscount>  300){
	      		throw new IllegalArgumentException ("Bill not calculated properly"); //or do custom designed exception
	      }
		} catch (IllegalArgumentException e){ //exception should be thrown as well if the customer thinks the bill was not correctly done
	    	System.out.println("Input the items selected again"); 
	    	switchMenu();
	    }
	  return billBeforeDiscount;
   }

  private void getQuantityGUI(String command) {
	  String val = JOptionPane.showInputDialog(this,"Number of " + menu.findItemId(command).getName());
		 
	  //if val is null then cancel has been selected
	  if(val != null){
		  try
		  {
			  int quantity = Integer.parseInt(val);
			  if (quantity < 0) {
		            throw new IllegalArgumentException();
		        }
			    if(currentOrder.containsKey(command)) {
					  int updateGUIdQuantity = quantity + currentOrder.get(command);
					  currentOrder.put(command, updateGUIdQuantity);
				  }
				  //item is not yet in currentOrder
				  else
					  currentOrder.put(command, quantity);
		  }
		  catch(NumberFormatException exp){
			  JOptionPane.showMessageDialog(this, "Quantity has to be an integer");
		  }
		  catch(IllegalArgumentException exp){
			  JOptionPane.showMessageDialog(this, "Quantity has to be positive");
		  }
	  }
  }
  
  //need to update quanities
  private void placeOrder() throws DuplicateIDException, CalculationError {
	  if(!currentOrder.isEmpty()) {
		  updateItemQuantity(currentOrder);
		  try{
			  customerList.addCustomer(customerId, toArrayList(currentOrder),totalBeforeDiscount,(float)totalAfterDiscount);
		  //still need to add all orders
			  customerId++;
			  Date date= new Date();
			  long time = date.getTime();
			  Timestamp timestamp = new Timestamp(time);
			  for (String item : toArrayList(currentOrder)){
					allorders.addOrder(customerId, orderId, item,  timestamp );
					orderId ++;
			  }
			  JOptionPane.showMessageDialog(this, "Order placed");
			  currentOrder.clear();
			  switchMenu(); 
			}catch (DuplicateIDException e){
				customerId++;
				JOptionPane.showMessageDialog(this, "System error, please retry");

			}
	  }
	  else {
		  JOptionPane.showMessageDialog(this, "Unable to place order as no items selected");
	  }
  }
  

  private void updateItemQuantity(Map<String, Integer> order) {
	  Set<String> keys = order.keySet();
		for(String key: keys){
			int quantityToAdd = order.get(key);
			int currentQuantity = menu.findItemId(key).getQuantity();
			int quantity = currentQuantity + quantityToAdd;
			menu.findItemId(key).setQuantity(quantity);
		}
  }

  
}
