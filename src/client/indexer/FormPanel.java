package client.indexer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import shared.model.Field;
import client.UI.BatchState;

@SuppressWarnings("serial")
public class FormPanel extends JPanel{
	
	JList<String> list = new JList<String>();
	JPanel horizLayout;
	BatchState stateInfo;
	JPanel formPanel;
	
	public FormPanel(BatchState stateInfo){
		
		setLayout(new BorderLayout(0, 0));
		
		add(list, BorderLayout.WEST);
		this.stateInfo = stateInfo;
		this.setBackground(Color.GRAY);
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for (int i = 0; i < stateInfo.getRecordCount(); i++) {
			listModel.addElement(Integer.toString(i+1) + "               ");
		}
		
		list.setModel(listModel);
		list.setSelectedIndex(stateInfo.getSelectedCell().getRow());
		
		formPanel = new JPanel();
		add(formPanel, BorderLayout.CENTER);
		
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		JTextField[] textFields = new JTextField[stateInfo.getFieldCount()];
		int col = 0;
		if(stateInfo.getFields() != null)
		{
				for(Field field : stateInfo.getFields())
				{
					if(col != 0)
					{
						System.out.println("Adding new panel.");
						horizLayout = new JPanel();
						
						horizLayout.setLayout(new FlowLayout(FlowLayout.LEFT));
						horizLayout.setSize(horizLayout.getWidth(), 20);
						
						JLabel label = new JLabel(field.getTitle());
						label.setSize(120, 20);
						
						horizLayout.add(label);
						
						JTextField tf = textFields[col] = new JTextField();
						
						tf.setColumns(20);
						
						horizLayout.add(textFields[col]);
						formPanel.add(horizLayout);
					}
					col++;
				}
		}
		
	}
}
