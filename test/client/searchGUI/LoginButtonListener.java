package client.searchGUI;

/*
 * TheButtonListener.java
 */
import java.awt.event.*;

public class LoginButtonListener  implements ActionListener
{ // TheButtonListener
    
    LoginFrame loginFrame;
    
    public LoginButtonListener(LoginFrame loginFrame)
    { // constructor
        this.loginFrame = loginFrame;
    } // constructor
    
    public void actionPerformed(ActionEvent e)
    { // actionPerformed
        loginFrame.validateUser();
        
        loginFrame.repaint();
    } // actionPerformed

} // TheButtonListener
