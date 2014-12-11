package client.indexer;


import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class ScrollPane extends JScrollPane {

	public ScrollPane(JComponent component){
		super(component);
		this.setBackground(Color.GRAY);
		
	}
}