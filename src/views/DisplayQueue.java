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
	private CurrentQueue displaydata;
	private JTextField queueText = new JTextField(15);

	// sets up general gui
	public DisplayQueue(CurrentQueue display) {
		this.displaydata = display;
		display.registerObserver(this);
	
		this.add(queueText);
		queueText.setEditable(false);
		queueText.setSize(100, 300);
		queueText.setHorizontalAlignment(JTextField.CENTER);
		Font queueFont = new Font("SansSerif", Font.BOLD, 14);
		queueText.setFont(queueFont);
		update();
	}

	public void update() {
		//String text = display.showServer
		String text = "Queue: ";
		queueText.setText(text);
	}
}