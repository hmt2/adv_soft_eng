package views;

import interfaces.Observer;

import java.awt.*;
import javax.swing.*;

import model.CurrentQueue;


//using observer pattern
public class DisplayCollectionQueue extends JPanel implements Observer {
	private CurrentQueue modeldata;
	private JTextArea queueText = new JTextArea("",13,50);

	// sets up general gui
	public DisplayCollectionQueue(CurrentQueue model) {
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
		String text = "Collection Queue: \n\r" + modeldata.printCollectionQueue();
		queueText.setText(text);
	}
}