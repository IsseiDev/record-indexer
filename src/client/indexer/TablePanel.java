package client.indexer;


import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TablePanel extends JPanel {
	ColorTable component;

	public TablePanel(){
		this.setBackground(Color.RED);
		
		component = new ColorTable();

		this.add(component, BorderLayout.CENTER);
	}
}