package client.indexer;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import client.UI.FrameController;
import client.listeners.MenuListener;

@SuppressWarnings("serial")
public class IndexerFrame extends JFrame {
	// Add subcomponents to the window
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem downloadBatch;
	
	//All of the buttons
	JButton zoomInButton;
	JButton zoomOutButton;
	JButton invertButton;
	JButton highlightsButton;
	JButton saveButton;
	JButton submitButton;
	
	FrameController controller;
	
	public IndexerFrame(FrameController controller) {
		
		this.controller = controller;
		// Set the window's title
		this.setTitle("Indexer");
		
		// Specify what should happen when the user clicks the window's close icon
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		//JScrollPane outputPane;
		
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		
		//Adding items to the menu
		downloadBatch = new JMenuItem("Download Batch");
		downloadBatch.addActionListener(new MenuListener(this, "download"));
		menu.add(downloadBatch);
		
		menuBar.add(menu);
		
		this.setJMenuBar(menuBar);
		
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setBounds(10, 10, 900, 40);
		zoomInButton = new JButton("Zoom In");
		panel.add(zoomInButton);
		zoomOutButton = new JButton("Zoom Out");
		panel.add(zoomOutButton);
		invertButton = new JButton("Invert Image");
		panel.add(invertButton);
		highlightsButton = new JButton("Toggle Highlights");
		panel.add(highlightsButton);
		saveButton = new JButton("Save");
		panel.add(saveButton);
		submitButton = new JButton("Submit");
		panel.add(submitButton);
		
		this.add(panel);
		
		//Setting the output pane
		//outputPane = new JScrollPane();
		//outputPane.setBounds(10, 100, 210, 200);
		//this.add(outputPane);
		

		// Set the location of the window on the desktop
		this.setLocation(100, 100);

		// Size the window according to the preferred sizes and layout of its subcomponents
		this.setSize(900, 700);
	}

	public void exitProgram()
	{
		controller.exitProgram();
	}
	
	public void logout()
	{
		controller.logout();
	}
}
