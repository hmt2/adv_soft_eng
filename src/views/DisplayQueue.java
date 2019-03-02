/**
 * The MVC display example from the MVC lecture.
 * This class sets up the DigitalDisplay, one of the Views.
 */

package views;

import interfaces.Observer;

import java.awt.*;
import javax.swing.*;

import model.Display;

//using observer pattern
public class DisplayQueue extends JPanel implements Observer {
	private Display displaydata;
	private JTextField timeText = new JTextField(10);

	// sets up general gui
	public DisplayQueue(Display display) {
		this.displaydata = display;
		display.registerObserver(this);
		this.add(timeText);
		timeText.setEditable(false);
		timeText.setHorizontalAlignment(JTextField.CENTER);
		Font timeFont = new Font("SansSerif", Font.BOLD, 14);
		timeText.setFont(timeFont);
		update();
	}

	public void update() {

	}
}