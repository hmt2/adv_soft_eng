package views;

import interfaces.Observer;

import java.awt.*;
import javax.swing.*;

import model.DisplayModel;


//using observer pattern
public class DisplayCollectionQueue extends JPanel implements Observer {
	private DisplayModel modeldata;
	private JTextArea queueText = new JTextArea("",7,15);

	// sets up general gui
	public DisplayCollectionQueue(DisplayModel model) {
		this.modeldata = model;
		model.registerObserver(this);
		queueText.setAlignmentX(BOTTOM_ALIGNMENT);
		this.add(queueText);
		queueText.setEditable(false);
		Font queueFont = new Font("SansSerif", Font.BOLD, 14);
		queueText.setFont(queueFont);
		update();
	}

	//Print the current collection queue
	public void update() {
		String text = "Collection Queue: \n\r" + modeldata.printCollectionQueue();
		queueText.setText(text);
	}
}