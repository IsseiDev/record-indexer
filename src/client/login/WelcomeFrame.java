package client.login;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import client.UI.FrameController;
import client.listeners.LoginButtonListener;

@SuppressWarnings("serial")
public class WelcomeFrame extends JDialog {
	// Add subcomponents to the window
	JLabel invalidLabel;
	LoginButtonListener button;
	
	FrameController controller;
	
	public WelcomeFrame(FrameController controller, String welcomeMessage, String indexedMessage) {
		
		this.controller = controller;
		// Set the window's title
		this.setTitle("Welcome");
		this.setModal(true);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		// Specify what should happen when the user clicks the window's close icon
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 
		 JScrollPane outputPane;
		
		// Username label and field
		invalidLabel = new JLabel(welcomeMessage);
		invalidLabel.setBounds(20, 20, 350, 20);
		this.add(invalidLabel);
		
		invalidLabel = new JLabel(indexedMessage);
		invalidLabel.setBounds(20, 40, 350, 20);
		this.add(invalidLabel);
		
		//Setting the output pane
		outputPane = new JScrollPane();
		outputPane.setBounds(10, 100, 210, 200);
		this.add(outputPane);
		
		
		//Setting the button and the listener to the button
		JPanel buttonPanel = new JPanel(new GridLayout(0,1,0,1));
		button = new LoginButtonListener(this, "welcome");
		JButton theButton = new JButton("OK");
		buttonPanel.add(theButton);
		theButton.addActionListener(button);
		
		this.add(buttonPanel, BorderLayout.PAGE_END);
		// Set the location of the window on the desktop
		this.setLocation(500, 400);

		// Size the window according to the preferred sizes and layout of its subcomponents
		this.setSize(270, 160);
	}
}