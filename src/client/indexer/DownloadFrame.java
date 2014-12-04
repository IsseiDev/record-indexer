package client.indexer;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import server.database.DatabaseException;
import client.ClientException;
import client.UI.FrameController;
import client.listeners.DownloadFrameListener;

@SuppressWarnings("serial")
public class DownloadFrame extends JDialog{

	// Add subcomponents to the window
	JLabel projectsLabel;
    Vector<String> projects = new Vector<String>();
    JComboBox<String> projectsList;
	FrameController controller;
	
	public DownloadFrame(FrameController controller) {
		
		this.controller = controller;
		// Set the window's title
		this.setTitle("Download Batch");
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		// Specify what should happen when the user clicks the window's close icon
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		 
		 JScrollPane outputPane;
		
		// Project label
		projectsLabel = new JLabel("Project:");
		projectsLabel.setBounds(10, 10, 140, 20);
		this.add(projectsLabel);
		
		//The combo box will contain all the Projects
        projects = controller.getProjects();

      
        projectsList = new JComboBox<String>(projects);
        projectsList.setBounds(60, 10, 110, 25);
        this.add(projectsList);
        
        JButton viewSampleButton = new JButton("View Sample");
        viewSampleButton.addActionListener(new DownloadFrameListener(this, "view_sample"));
        viewSampleButton.setBounds(175, 10, 110, 25);
		this.add(viewSampleButton);
		
		JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new DownloadFrameListener(this, "cancel"));
        cancelButton.setBounds(60, 45, 80, 25);
		this.add(cancelButton);
		
		JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(new DownloadFrameListener(this, "download"));
        downloadButton.setBounds(145, 45, 100, 25);
		this.add(downloadButton);
		
		//Setting the output pane
		outputPane = new JScrollPane();
		outputPane.setBounds(10, 100, 210, 200);
		this.add(outputPane);

		this.setLocation(100, 100);

		// Size the window according to the preferred sizes and layout of its subcomponents
		this.setSize(315, 120);
		
	}
	
	public void getSampleImage()
	{		
		@SuppressWarnings("unused")
		SampleImagePanel i;
		try {
			i = new SampleImagePanel(controller.getSampleImage((String)projectsList.getSelectedItem()));
		} catch (ClientException | DatabaseException e) {
			System.out.println("Could not connect to the server.");
			e.printStackTrace();
		}
	}
	
	public void downloadBatch()
	{
		controller.downloadBatch((String)projectsList.getSelectedItem());
	}

}
