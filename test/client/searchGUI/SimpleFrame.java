package client.searchGUI;

import java.awt.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class SimpleFrame extends JFrame {

	public SimpleFrame() {
		
		// Set the window's title
		this.setTitle("Simple");
		
		// Specify what should happen when the user clicks the window's close icon
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add subcomponents to the window
		this.add(new JLabel("Hi There"), BorderLayout.WEST);
		this.add(new JTextArea(5, 40), BorderLayout.CENTER);
		this.add(new JButton("Poke Me"), BorderLayout.EAST);
		
		// Set the location of the window on the desktop
		this.setLocation(100, 100);

		// Size the window according to the preferred sizes and layout of its subcomponents
		this.pack();
	}
}
