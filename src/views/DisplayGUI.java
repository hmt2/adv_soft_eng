package views;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

import interfaces.Observer;
import model.CurrentQueue;

public class DisplayGUI extends JFrame implements Observer{
	private CurrentQueue modeldata;
	
	public DisplayGUI(CurrentQueue model) {
		this.modeldata = model;
		model.registerObserver(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// The display classes below do need to know about the model data.
		//The waiting queue is displayed at the top
		add(new DisplayWaitingQueue(model), BorderLayout.NORTH);
		//The collection queue is displayed at the bottom
		add(new DisplayCollectionQueue(model), BorderLayout.SOUTH);
		//The servers are displayed in the centre
		add(BorderLayout.CENTER, new DisplayServer(model));

		setSize(1200, 600);
		setVisible(true);
		update();
	}

	// add listener to update button
	public void update() {
		if(modeldata.getOff())
			System.exit(0);
	}
}
