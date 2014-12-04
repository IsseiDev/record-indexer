package client.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.indexer.DownloadFrame;

public class DownloadFrameListener implements ActionListener{
	
	DownloadFrame frame;
	String type;
	
	public DownloadFrameListener(DownloadFrame frame, String type)
	{
		this.frame = frame;
		this.type = type;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(type == "view_sample")
		{
			frame.getSampleImage();
		}
		else if(type == "download")
		{
			frame.downloadBatch();
		}
		else if(type == "cancel")
		{
			frame.dispose();
		}
		else
		{
			System.out.println("What are you even doing?");
		}
	}

}

