package client.indexer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.listeners.IndexerButtonListener;

@SuppressWarnings("serial")
public class SampleImagePanel extends JDialog{
	
	SampleImagePanel(String fileLocation)
	{
		this.setTitle("Download Batch");
		this.setModal(true);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		// Specify what should happen when the user clicks the window's close icon
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		BufferedImage myPicture;
		try {
			System.out.println(fileLocation);
			myPicture = ImageIO.read(new URL(fileLocation));
			
			myPicture = resizeExact(myPicture, new Dimension(600, 500), new Dimension());
			
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			
			this.setSize(myPicture.getWidth(), myPicture.getHeight() + 70 );
			
			JPanel buttonPanel = new JPanel(new FlowLayout(BoxLayout.Y_AXIS));
			JButton cancel = new JButton("Cancel");
			cancel.addActionListener(new IndexerButtonListener(this, "cancel"));
			buttonPanel.add(picLabel);
			buttonPanel.add(cancel);
			this.add(buttonPanel);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.setLocation(100, 100);
		this.setVisible(true);

	}
	
	public BufferedImage resizeExact(final BufferedImage original, final Dimension scaled, final Dimension offset) {
	    final Image newImage = original.getScaledInstance(scaled.width, scaled.height, Image.SCALE_SMOOTH);
	    final BufferedImage bufferedImage = new BufferedImage(newImage.getWidth(null),
	                                                          newImage.getHeight(null),
	                                                          BufferedImage.TYPE_INT_BGR);
	    bufferedImage.createGraphics().drawImage(newImage, offset.width, offset.height, null);
	    return bufferedImage;
	}
}
