package coffeShop;

//import all the GUI classes
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
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
  boolean isStudentDiscount;

  private float totalAllItemsBeforeDiscount = 0;
  private float totalAllItemsAfterDiscount = 0;
  
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
  JButton studentDiscount;


  public MenuGUI() throws DuplicateIDException 
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
	  
	  
	  addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	 SalesReport salrep = new SalesReport(menu,totalAllItemsAfterDiscount,totalAllItemsBeforeDiscount);
		    }
		});
	  
	  southPanel = new JPanel();
      southPanel.setLayout(new GridLayout(1,1));
      southPanel.setBackground(Color.DARK_GRAY);
      
    
      //student discount button
      studentDiscount = new JButton("student discount (10%)");
      studentDiscount.addActionListener(this);
      isStudentDiscount = false;
      southPanel.add(studentDiscount);

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
	  if(e.getSource() == checkout) {
		  studentDiscount.setEnabled(true);
		  switchBill();
	  }

	  //if back button is pressed then want to return to showing the menu
	  if(e.getSource() == back) {
		  isStudentDiscount = false;
		  switchMenu();
	  }

	  //where link to other classes!
	  //need to create customer
	  //need to create orders for each item with the newly created customer id
	  if(e.getSource() == buy) {
		 isStudentDiscount = false;
		 try {
			placeOrder();
		} catch (DuplicateIDException | IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "System error, please retry");
			switchMenu(); //maybe not necessary

	  }
	  
	  if(e.getSource() == clear)
		  isStudentDiscount = false;
		  clearOrder();
	  }
	  
	  if(e.getSource() == studentDiscount) {
		  isStudentDiscount = true;
		  studentDiscount.setEnabled(false);
	  }

  } 
 
  private void updateGUI() {
	  revalidate();
	  repaint();
  }
  
  private double calcAfterDiscount(Map<String, Integer> currentOrder) {
	  return calcAfterDiscount(currentOrder, new ArrayList<String>());
  }
  
  private double calcAfterDiscount(Map<String, Integer> currentOrder, ArrayList<String> discountNames) {
	  ArrayList<String> itemIds = toArrayList(currentOrder);
	  return calcAfterDiscount(itemIds, discountNames);
  }
  
  private double calcAfterDiscount(ArrayList<String> itemIds) {
	  return calcAfterDiscount(itemIds, new ArrayList<String>());
  }
  
  private double calcAfterDiscount(ArrayList<String> itemIds, ArrayList<String> discountNames) {
	  double totalAfterDiscount = 0;
	  while(true) {
		  Discount dis = getDiscount(itemIds);
		  if(dis != null) {
			  totalAfterDiscount += dis.getPrice();
			  discountNames.add(dis.getName());
			  for(String itemId : dis.getDiscountCodes()) {
				  itemIds.remove(itemId);
			  }
		  } else {
			  double price = calcBillBeforeDiscount(itemIds);
			  totalAfterDiscount += isStudentDiscount ? price * 0.9 : price;
			  break;
		  }
	  }
	  return totalAfterDiscount;
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
		
		ArrayList<String> discountNames = new ArrayList<>();
		totalAfterDiscount = calcAfterDiscount(currentOrder, discountNames);

		bill += String.format("%-2s %s","Total bill before discount: £",totalBeforeDiscount);
		bill += "\n";
		if(!discountNames.isEmpty() || isStudentDiscount)
			bill += "Applied discounts:\n";
		if(isStudentDiscount)
			bill += "\tStudent Discount: 10%\n";
		for(String discountName : discountNames)
			bill += "\t" + discountName + "\n"; 
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
  
  private void addPreviousOrders() throws DuplicateIDException{
	  TreeMap<Integer,ArrayList<String>> cust = allorders.loadOrders();
      Set<Integer> keys = cust.keySet();
		for(Integer key: keys){
			float totalBeforeDiscount = calcBillBeforeDiscount(cust.get(key));
			float totalAfterDiscount = totalBeforeDiscount;
			Discount ds = getDiscount(cust.get(key));
			if(ds != null)
				totalAfterDiscount = (float) calcAfterDiscount(cust.get(key));
			try {
				customerList.addCustomer(cust.get(key), totalBeforeDiscount, totalAfterDiscount);
			} catch (DuplicateIDException | IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				continue; //continue to process the next customer

		  }
		}
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
	  southPanel.add(studentDiscount);
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
	  for(String itemid : itemIds) {
		  	billBeforeDiscount += menu.findItemId(itemid).getPrice();
		  }
		  
	   if (billBeforeDiscount < 0 || billBeforeDiscount > 100){
			JOptionPane.showMessageDialog(this, "Bill not calculated properly, input items again");
            switchMenu();
			//throw new IllegalArgumentException("Bill not calculated properly");
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
  private void placeOrder() throws DuplicateIDException {
	  if(!currentOrder.isEmpty()) {
		  updateItemQuantity(currentOrder);
		  int custId = customerList.addCustomer(toArrayList(currentOrder),totalBeforeDiscount,(float)totalAfterDiscount);
		  totalAllItemsBeforeDiscount += totalBeforeDiscount;
		  totalAllItemsAfterDiscount += (float)totalAfterDiscount;
		  ArrayList<String> ord = toArrayList(currentOrder);
		  allorders.addOrder(custId, toArrayList(currentOrder));

		  JOptionPane.showMessageDialog(this, "Order placed");
		  currentOrder.clear();
		  switchMenu(); 
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
