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
public class DisplayServer extends JPanel implements Observer {
	private CurrentQueue displaydata;
	private JTextField serverText = new JTextField(15);

	// sets up general gui
	public DisplayServer(CurrentQueue display) {
		this.displaydata = display;
		display.registerObserver(this);
		this.add(serverText);
		serverText.setEditable(false);
		serverText.setHorizontalAlignment(JTextField.CENTER);
		Font serverFont = new Font("SansSerif", Font.BOLD, 14);
		serverText.setFont(serverFont);
		update();
	}

	public void update() {
		//String text = display.showQueue
		String text = "Server ";
		serverText.setText(text);
	}
}