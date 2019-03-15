package views;

import interfaces.Observer;

import java.awt.*;
import javax.swing.*;

import model.CurrentQueue;


//using observer pattern
public class DisplayWaitingQueue extends JPanel implements Observer {
	private CurrentQueue modeldata;
	private JTextArea queueText = new JTextArea("",13,50);

	// sets up general gui
	public DisplayWaitingQueue(CurrentQueue model) {
		this.modeldata = model;
		model.registerObserver(this);
		queueText.setAlignmentX(BOTTOM_ALIGNMENT);
		this.add(queueText);
		queueText.setEditable(false);
		Font queueFont = new Font("SansSerif", Font.BOLD, 14);
		queueText.setFont(queueFont);
		update();
	}

	public void update() {
		String text = "Waiting Queue: \n\r" + modeldata.printWaitingQueue();
		queueText.setText(text);
	}
}