package views;

import interfaces.Observer;

import java.awt.*;
import javax.swing.*;

import model.CurrentQueue;

//using observer pattern
public class DisplayServer extends JPanel implements Observer {
	private CurrentQueue modeldata;
	private JTextArea server1Text = new JTextArea("",15,20);
	private JTextArea server2Text = new JTextArea("",15,20);
	private JTextArea server3Text = new JTextArea("",15,20);
	private JTextArea server4Text = new JTextArea("",15,20);
	
	// sets up general gui
	public DisplayServer(CurrentQueue model) {
		this.modeldata = model;
		model.registerObserver(this);
		Font serverFont = new Font("SansSerif", Font.BOLD, 14);
		
		this.add(server1Text);
		server1Text.setAlignmentX(CENTER_ALIGNMENT);
		server1Text.setEditable(false);
		server1Text.setFont(serverFont);
		
		this.add(server2Text);
		server2Text.setAlignmentX(CENTER_ALIGNMENT);
		server2Text.setEditable(false);
		server2Text.setFont(serverFont);
		
		this.add(server3Text);
		server3Text.setAlignmentX(CENTER_ALIGNMENT);
		server3Text.setEditable(false);
		server3Text.setFont(serverFont);
		
		this.add(server4Text);
		server4Text.setAlignmentX(CENTER_ALIGNMENT);
		server4Text.setEditable(false);
		server4Text.setFont(serverFont);
		
		update();
	}

	public void update() {
		String server1text = "Server 1: \n\r" + modeldata.showCustomer(modeldata.getServerCustomer(0));
		server1Text.setText(server1text);
		System.out.print("GUI:  " + server1text);
		
		String server2text = "Server 2: \n\r" + modeldata.showCustomer(modeldata.getServerCustomer(1));
		server2Text.setText(server2text);
		System.out.print("GUI:  " + server2text);
		
		String server3text = "Server 3: \n\r" + modeldata.showCustomer(modeldata.getServerCustomer(2));
		server3Text.setText(server3text);
		System.out.print("GUI:  " + server3text);
		
		String server4text = "Server 4: \n\r" + modeldata.showCustomer(modeldata.getServerCustomer(3));
		server4Text.setText(server4text);
		System.out.print("GUI:  " + server4text);

	}
}