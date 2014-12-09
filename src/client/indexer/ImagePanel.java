package client.indexer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.net.MalformedURLException;

import javax.swing.JPanel;

import client.UI.BatchState;
import client.listeners.BatchStateListener;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel{
	
	DrawingComponent component;

	public ImagePanel(BatchState stateInfo){
		this.setBackground(Color.GRAY);
		
		try {
			component = new DrawingComponent(stateInfo);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.add(component, BorderLayout.CENTER);
		
		//stateInfo.addListener(new BatchStateListener(){

		//	@Override
		//	public void valueChanged(Cell cell, String newValue) {
				// TODO Auto-generated method stub
				
		//	}

		//	@Override
		//	public void selectedCellChanged(Cell newSelectedCell) {
		//		// TODO Auto-generated method stub
		//		
		//	}
			
		//});
	}

	public DrawingComponent getDrawComponent() {
		return component;
	}

	public void setDrawComponent(DrawingComponent component) {
		this.component = component;
	}

}
