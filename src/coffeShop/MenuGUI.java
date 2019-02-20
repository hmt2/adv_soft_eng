package coffeShop;


//import all the GUI classes
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.*;

/**
* Simple GUI for StaffList application
*/
public class MenuGUI extends JFrame implements ActionListener
{
private Menu menu;
private CoffeShopInterface interaction;
private Map<String, Integer> currentOrder = new LinkedHashMap<String, Integer>();


boolean isStudentDiscount;



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


public MenuGUI( final Menu menu, final CoffeShopInterface interaction) throws DuplicateIDException, IdNotContainedException 
{
    this.menu = menu;
    this.interaction = interaction;


  
    
    setTitle("Menu GUI");
    //ensure program ends when window closes
	  setDefaultCloseOperation(EXIT_ON_CLOSE);
	  
	  setPreferredSize(new Dimension(500, 300));
	  
	  contentPane = getContentPane();
	  
	  
	  addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	 interaction.generateReport();
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
	  Item isItem = null;
	  try {
		  isItem = menu.findItemId(command);
	  } catch (IdNotContainedException e2) {
		  e2.printStackTrace();
	  }

	  //if button pressed is an item button
	  if(isItem != null) {
		getQuantityGUI(command);
	  }

	  //if checkout button is pressed then want to showing the Bill
	  if(e.getSource() == checkout) {
		  studentDiscount.setEnabled(true);
		  try {
			switchBill();
		} catch (IdNotContainedException e1) {
			e1.printStackTrace();
		}
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
		} catch (DuplicateIDException | IllegalArgumentException | IdNotContainedException e1) {
			JOptionPane.showMessageDialog(this, "System error, please retry");
			switchMenu(); //maybe not necessary
	 	 }
	  }
	  
	  if(e.getSource() == clear){
		  isStudentDiscount = false;
		  try {
			clearOrder();
		  } catch (IdNotContainedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		  }
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



private void loadItemButtons() throws IdNotContainedException {
	  Set<String> keys = menu.getKeySet();
		for(String key: keys){
			JButton button = new JButton(String.format("%30s%16s",menu.findItemId(key).getName(),"£" + menu.findItemId(key).getPrice()));
			button.addActionListener(this);
		    menuPanel.add(button);
			button.setActionCommand(key);
		}
}

private void switchBill() throws IdNotContainedException {
	  southPanel.removeAll();
    southPanel.add(buy);
    southPanel.add(back);
    southPanel.add(clear);
	  contentPane.remove(menuList);
	  contentPane.add(billList,BorderLayout.CENTER);
	  try {
		  displayBill.setText(interaction.displayBill(currentOrder, isStudentDiscount));
	  } catch (IllegalArgumentException e){
			JOptionPane.showMessageDialog(this, "Bill not calculated properly, input items again");
            switchMenu();
	  }
    
	  updateGUI();   
}



public void clearOrder() throws IdNotContainedException {
	  currentOrder.clear();

	  try {
		  displayBill.setText(interaction.displayBill(currentOrder, isStudentDiscount));
	  } catch (IllegalArgumentException e){
			JOptionPane.showMessageDialog(this, "Bill not calculated properly, input items again");
            switchMenu();
	  }
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




private void getQuantityGUI(String command) {
	  String val = null;
	  try {
		  val = JOptionPane.showInputDialog(this,"Number of " + menu.findItemId(command).getName());
	  } catch (HeadlessException e) {
		  e.printStackTrace();
	  } catch (IdNotContainedException e) {
		  e.printStackTrace();
	  }
		 
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

//need to update quantities
private void placeOrder() throws DuplicateIDException, IdNotContainedException {
	  if(!currentOrder.isEmpty()) {
        interaction.placeOrder(currentOrder);
		  JOptionPane.showMessageDialog(this, "Order placed");
		  currentOrder.clear();
		  switchMenu(); 
	  }
	  else {
		  JOptionPane.showMessageDialog(this, "Unable to place order as no items selected");
	  }
}





}
