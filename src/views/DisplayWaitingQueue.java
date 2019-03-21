package views;

import interfaces.Observer;

import java.awt.*;
import javax.swing.*;

import model.DisplayModel;


//using observer pattern
public class DisplayWaitingQueue extends JPanel implements Observer {
	private DisplayModel modeldata;
	private JTextArea queueText = new JTextArea("",10,15);

	// sets up general gui
	public DisplayWaitingQueue(DisplayModel model) {
		this.modeldata = model;
		model.registerObserver(this);
		queueText.setAlignmentX(BOTTOM_ALIGNMENT);
		this.add(queueText);
		queueText.setEditable(false);
		Font queueFont = new Font("SansSerif", Font.BOLD, 14);
		queueText.setFont(queueFont);
		update();
	}

	//Print the current waiting queue
	public void update() {
		String text = "Waiting Queue: \n\r" + modeldata.printWaitingQueue();
		queueText.setText(text);
	}
}