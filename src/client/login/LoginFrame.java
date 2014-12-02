package client.login;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import client.UI.FrameController;
import client.listeners.LoginButtonListener;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	// Add subcomponents to the window
	JLabel usernameLabel;
	public JTextField usernameField;
	JLabel passwordLabel;
	public JTextField passwordField;
	LoginButtonListener button;
	LoginButtonListener button2;
	
	FrameController controller;
	
	public LoginFrame(FrameController controller) {
		
		this.controller = controller;
		// Set the window's title
		this.setTitle("Login");
		
		// Specify what should happen when the user clicks the window's close icon
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 JScrollPane outputPane;
		
		// Username label and field
		usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(10, 10, 140, 20);
		this.add(usernameLabel);
		usernameField = new JTextField();
		usernameField.setBounds(100, 10, 200, 20);
		this.add(usernameField);

		// Password label and field
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 140, 20);
		this.add(passwordLabel);
		passwordField = new JPasswordField();
		passwordField.setBounds(100, 40, 200, 20);
		this.add(passwordField);
		
		//Setting the output pane
		outputPane = new JScrollPane();
		outputPane.setBounds(10, 100, 210, 200);
		this.add(outputPane);
		
		
		//Setting the button and the listener to the button
		JPanel buttonPanel = new JPanel(new GridLayout(0,2,0,2));
		button = new LoginButtonListener(this, "validate");
		JButton theButton = new JButton("Login");
		JButton theExit = new JButton("Exit");
		buttonPanel.add(theButton);
		theButton.addActionListener(button);
		button2 = new LoginButtonListener(this, "exit");
		buttonPanel.add(theExit);
		theExit.addActionListener(button2);
		
		this.add(buttonPanel, BorderLayout.PAGE_END);
		// Set the location of the window on the desktop
		this.setLocation(100, 100);

		// Size the window according to the preferred sizes and layout of its subcomponents
		this.setSize(375, 100);
	}
	
	public void validateUser()
    { // displayFullName
        controller.validateUser(usernameField.getText(), passwordField.getText());
    } // displayFullName
	
	public void exitProgram()
	{
		controller.exitProgram();
	}
}
