package client.indexer;


import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class TablePanel extends JScrollPane {

	public TablePanel(JComponent table){
		super(table);
		this.setBackground(Color.GRAY);
		
	}
}