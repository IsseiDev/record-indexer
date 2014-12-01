package client.listeners;

/*
 * TheButtonListener.java
 */
import java.awt.event.*;

import client.login.InvalidFrame;
import client.login.LoginFrame;
import client.login.WelcomeFrame;

public class LoginButtonListener  implements ActionListener
{ // TheButtonListener
    
    LoginFrame loginFrame;
    InvalidFrame invalidFrame;
    WelcomeFrame welcomeFrame;
    String type;
    
    public LoginButtonListener(LoginFrame loginFrame,String type)
    { // constructor
        this.loginFrame = loginFrame;
        this.type = type;
    } // constructor
    
    public LoginButtonListener(InvalidFrame invalidFrame,String type)
    { // constructor
        this.invalidFrame = invalidFrame;
        this.type = type;
    } // constructor
    
    public LoginButtonListener(WelcomeFrame welcomeFrame,String type)
    { // constructor
        this.welcomeFrame = welcomeFrame;
        this.type = type;
    } // constructor
    
    public void actionPerformed(ActionEvent e)
    { // actionPerformed
    	if(type == "validate")
    	{
    		loginFrame.validateUser();
            loginFrame.repaint();
    	}
    	else if (type == "exit")
    	{
    		loginFrame.exitProgram();
    	}
    	else if (type == "invalid")
    	{
    		invalidFrame.dispose();
    	}
    	else if (type == "welcome")
    	{
    		welcomeFrame.dispose();
    	}
    	else
    	{
    		System.out.println("I have no idea what you're trying to do.");
    	}
    } // actionPerformed

} // TheButtonListener
