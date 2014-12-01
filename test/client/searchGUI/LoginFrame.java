package client.searchGUI;

import java.awt.*;

import javax.swing.*;


@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	// Add subcomponents to the window
	JLabel hostLabel;
	JTextField hostField;
	JLabel portLabel;
	JTextField portField;
	JLabel usernameLabel;
	JTextField usernameField;
	JLabel passwordLabel;
	JTextField passwordField;
	LoginButtonListener button;
	
	FrameController controller;
	
	public LoginFrame(FrameController controller) {
		
		this.controller = controller;
		// Set the window's title
		this.setTitle("Login");
		
		// Specify what should happen when the user clicks the window's close icon
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 JScrollPane outputPane;
		 
		// Hostname label and field
		hostLabel = new JLabel("Host");
		hostLabel.setBounds(10, 10, 50, 20);
		this.add(hostLabel);
		hostField = new JTextField("localhost");
		hostField.setBounds(45, 10, 100, 20);
		this.add(hostField);
		
		// Port label and field
		portLabel = new JLabel("Port");
		portLabel.setBounds(150, 10, 50, 20);
		this.add(portLabel);
		portField = new JTextField("8080");
		portField.setBounds(180, 10, 50, 20);
		this.add(portField);
		
		// Username label and field
		usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(10, 60, 100, 20);
		this.add(usernameLabel);
		usernameField = new JTextField();
		usernameField.setBounds(80, 60, 100, 20);
		this.add(usernameField);

		// Password label and field
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 90, 100, 20);
		this.add(passwordLabel);
		passwordField = new JTextField();
		passwordField.setBounds(80, 90, 100, 20);
		this.add(passwordField);
		
		//Setting the output pane
		outputPane = new JScrollPane();
		outputPane.setBounds(10, 100, 210, 200);
		this.add(outputPane);
		
		
		//Setting the button and the listener to the button
		button = new LoginButtonListener(this);
		JButton theButton = new JButton("Login");
		this.add(theButton, BorderLayout.PAGE_END);
		theButton.addActionListener(button);
		
		// Set the location of the window on the desktop
		this.setLocation(100, 100);

		// Size the window according to the preferred sizes and layout of its subcomponents
		this.setSize(250, 200);
	}
	
	public void validateUser()
    { // displayFullName
        controller.validateUser(hostField.getText(), portField.getText(), usernameField.getText(), passwordField.getText());
    } // displayFullName
}