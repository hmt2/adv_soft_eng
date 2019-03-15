package views;

import interfaces.Observer;

import java.awt.*;
import javax.swing.*;

import model.CurrentQueue;

//using observer pattern
public class DisplayServer extends JPanel implements Observer {
	private CurrentQueue modeldata;
	private JTextArea serverText = new JTextArea("",10,20);

	// sets up general gui
	public DisplayServer(CurrentQueue model) {
		this.modeldata = model;
		model.registerObserver(this);
		this.add(serverText);
		serverText.setAlignmentX(TOP_ALIGNMENT);
		serverText.setEditable(false);
		Font serverFont = new Font("SansSerif", Font.BOLD, 14);
		serverText.setFont(serverFont);
		update();
	}

	public void update() {
		String text = "Server: \n\r" + modeldata.showCustomerBeingServed();
		serverText.setText(text);
	}
}