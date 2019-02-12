package coffeShop;

//import all the GUI classes
import java.awt.*;
import java.awt.event.*;
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
 
  public MenuGUI()
  {
      menu = new Menu();
      AllOrders allOrders = new AllOrders();
      CustomerList customerList = new CustomerList();
      
      //set up window title
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
		setQuantity(command);
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
		 placeOrder();
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
	  String bill = "CHECKOUT \n";
	  
	  float billCurrentItem = 0;
	  int currentItemQuantity = 0;
	  float totalBeforeDiscount = 0;
	  
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
		bill += String.format("%-2s %s","Total bill: £",totalBeforeDiscount);
		
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
  
  private void setQuantity(String command) {
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
  private void placeOrder() {
	  if(!currentOrder.isEmpty()) {
		  updateItemQuantity();
		  JOptionPane.showMessageDialog(this, "Order placed");
		  currentOrder.clear();
		  switchMenu(); 
	  }
	  else {
		  JOptionPane.showMessageDialog(this, "Unable to place order as no items selected");
	  }
  }
  
  //
  private void updateItemQuantity() {
	  Set<String> keys = currentOrder.keySet();
		for(String key: keys){
			int quantityToAdd = currentOrder.get(key);
			int currentQuantity = menu.findItemId(key).getQuantity();
			int quantity = currentQuantity + quantityToAdd;
			menu.findItemId(key).setQuantity(quantity);
		}
  }

  
}
