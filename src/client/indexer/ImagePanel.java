package client.indexer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.net.MalformedURLException;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel{
	
	DrawingComponent component;

	public ImagePanel(String imageLocation){
		this.setBackground(Color.GRAY);
		
		try {
			component = new DrawingComponent(imageLocation);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.add(component, BorderLayout.CENTER);
	}

	public DrawingComponent getDrawComponent() {
		return component;
	}

	public void setDrawComponent(DrawingComponent component) {
		this.component = component;
	}
}
