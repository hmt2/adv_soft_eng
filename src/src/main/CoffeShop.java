package main;

import java.util.TreeMap;

import coffeShop.CoffeShopInterface;
import coffeShop.Menu;
import exceptions.DuplicateIDException;
import exceptions.IdNotContainedException;
import model.MenuGUI;


public class CoffeShop
{
	private Menu menu;
	private CoffeShopInterface interaction;
	private MenuGUI gui;


	public CoffeShop(){
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

		CoffeShop run = new CoffeShop();   
		run.showInterface();
		run.showGUI();
	}

}
