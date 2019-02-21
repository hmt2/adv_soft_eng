package coffeShop;

import java.util.TreeMap;




public class CoffeShop
{
	private Menu menu;
    private CoffeShopInterface interaction;
	private MenuGUI gui;


  public CoffeShop() 
  {
  	//initialise empty list of staff
	  menu = new Menu();
	  
  }
  public void showInterface() throws DuplicateIDException, IdNotContainedException {
	  interaction = new CoffeShopInterface(menu);
  }
  public void showGUI() throws DuplicateIDException, IdNotContainedException {
      gui = new MenuGUI(menu, interaction);
      gui.setVisible(true);
  }     
  
  public static void main (String arg[]) throws DuplicateIDException, IdNotContainedException  {
     	//creates demo object, with a populated staff list
	  
  	CoffeShop run = new CoffeShop();   
  	
  	//allow user to interact using a GUI
  	run.showInterface();
  	run.showGUI();

  	//allow user to interact with this list
  	//using text interface
  	
  }

}
