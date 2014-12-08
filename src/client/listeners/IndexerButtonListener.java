package client.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.indexer.DrawingComponent;
import client.indexer.IndexerFrame;

public class IndexerButtonListener implements ActionListener{

	IndexerFrame frame;
	DrawingComponent drawComponent;
	String type;
	
	public IndexerButtonListener(IndexerFrame frame, String type)
	{
		this.frame = frame;
		this.drawComponent  = frame.getImagePanel().getDrawComponent();
		this.type = type;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(type == "zoomIn")
		{
			drawComponent.setScale(drawComponent.getScale() + 0.20);
		}
		else if(type == "zoomOut")
		{
			drawComponent.setScale(drawComponent.getScale() - 0.20);
		}
		else if(type == "invert")
		{
			System.out.println("Trying to invert");
			drawComponent.invertImage();
		}
		else if(type == "toggle")
		{
			
		}
		else if(type == "save")
		{
			frame.saveState();
		}
		else if(type == "submit")
		{
			frame.submitBatch();
		}
		else
		{
			System.out.println("What are you even doing?");
		}
	}
	
}
