package client.indexer;

import java.io.IOException;

import javax.swing.JEditorPane;

import client.UI.BatchState;
import client.listeners.BatchStateListener;

@SuppressWarnings("serial")
public class HelpPane extends JEditorPane {
	BatchState stateInfo;
	
	public HelpPane(final BatchState stateInfo, final String hostname, final String port) {
		this.stateInfo = stateInfo;
		setContentType("text/html");
		this.setEditable(false);
		
		try {
			if(stateInfo.getFieldCount() != 0)
			{
				HelpPane.this.setPage(stateInfo.getFields().get(stateInfo.getSelectedCell().getCol()).getHelphtml(hostname, port));
				
				stateInfo.addListener(new BatchStateListener() {
					@Override
					public void valueChanged(Cell cell, String newValue) {}
	
					@Override
					public void selectedCellChanged(Cell newSelectedCell) {
						try {
							HelpPane.this.setPage(stateInfo.getFields().get(newSelectedCell.getCol()).getHelphtml(hostname, port));
						} catch (IOException e) {
							HelpPane.this.setText("Incorrect URL.");
						}
	
						
					}
				});
			}
			
		} catch (IOException e) {
			HelpPane.this.setText("Incorrect URL.");
		}

	}
}
