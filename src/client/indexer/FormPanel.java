package client.indexer;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shared.model.Field;
import client.UI.BatchState;

@SuppressWarnings("serial")
public class FormPanel extends JPanel{
	
	JPanel formPanel;
	JPanel horizLayout;
	BatchState stateInfo;
	
	public FormPanel(BatchState stateInfo){
		
		formPanel = this;
		this.stateInfo = stateInfo;
		
		this.setBackground(Color.GRAY);
		
		if(stateInfo.getFields() != null)
		{
			for(Field field : stateInfo.getFields())
			{
				horizLayout = new JPanel();
				
				horizLayout.setLayout(new BoxLayout(horizLayout, BoxLayout.X_AXIS));
				horizLayout.setSize(horizLayout.getWidth(), 30);
				
				JLabel label = new JLabel(field.getTitle());
				label.setSize(120, 20);
				
				horizLayout.add(label);
			}
			
		}


	}
}
