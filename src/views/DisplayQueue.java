/**
 * The MVC display example from the MVC lecture.
 * This class sets up the DigitalDisplay, one of the Views.
 */

package views;

import interfaces.Observer;

import java.awt.*;
import javax.swing.*;

import model.CurrentQueue;


//using observer pattern
public class DisplayQueue extends JPanel implements Observer {
	private CurrentQueue modeldata;
	private JTextArea queueText = new JTextArea("",10,10);

	// sets up general gui
	public DisplayQueue(CurrentQueue model) {
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
		String text = "Queue: \n\r" + modeldata.showQueue();
		queueText.setText(text);
	}
}