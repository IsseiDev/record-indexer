package client.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.indexer.IndexerFrame;

public class MenuListener implements ActionListener{
	
	IndexerFrame indexer;
	String type;
	
	public MenuListener(IndexerFrame indexer, String type)
	{
		this.indexer = indexer;
		this.type = type;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(type == "download")
		{
			indexer.downloadBatch();
		}
		else if(type == "logout")
		{
			indexer.logout();
		}
		else if(type == "exit")
		{
			indexer.exitProgram();
		}
		else
		{
			System.out.println("What are you even doing?");
		}
	}

}
