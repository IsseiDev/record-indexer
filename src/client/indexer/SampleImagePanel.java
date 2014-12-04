package client.indexer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
class SampleImagePanel extends JDialog{
	
	SampleImagePanel(String fileLocation)
	{
		this.setTitle("Download Batch");
		this.setModal(true);
		this.setAlwaysOnTop(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		
		// Specify what should happen when the user clicks the window's close icon
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		BufferedImage myPicture;
		try {
			System.out.println(fileLocation);
			myPicture = ImageIO.read(new URL(fileLocation));
						
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			this.setSize(myPicture.getWidth(), myPicture.getHeight());
			this.add(picLabel);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.setVisible(true);

	}
}
