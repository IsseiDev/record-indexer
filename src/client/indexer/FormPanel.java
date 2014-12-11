package client.indexer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import shared.model.Field;
import client.UI.BatchState;
import client.listeners.BatchStateListener;

@SuppressWarnings("serial")
public class FormPanel extends JPanel{
	
	JList<String> list = new JList<String>();
	JPanel horizLayout;
	BatchState stateInfo;
	JPanel formPanel;
	JTextField[] textFields;
	
	public FormPanel(final BatchState stateInfo){
		
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
		list.addListSelectionListener(new ListSelectionListener()
		{

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) return;
				System.out.println("Selected List Index: " + list.getSelectedIndex() + " Selected Cell Col: " + stateInfo.getSelectedCell().getCol());
				stateInfo.setSelectedCell(new Cell(list.getSelectedIndex(), stateInfo.getSelectedCell().getCol()));
				
			}
			
		});
		formPanel = new JPanel();
		add(formPanel, BorderLayout.CENTER);
		
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		textFields = new JTextField[stateInfo.getFieldCount()];
		int col = 0;
		if(stateInfo.getFields() != null)
		{
				for(Field field : stateInfo.getFields())
				{
					if(col != 0)
					{
						horizLayout = new JPanel();
						
						horizLayout.setLayout(new FlowLayout(FlowLayout.LEFT));
						horizLayout.setSize(horizLayout.getWidth(), 20);
						
						JLabel label = new JLabel(field.getTitle());
						label.setSize(120, 20);
						
						horizLayout.add(label);
						
						JTextField tf = textFields[col] = new JTextField();
						
						tf.setColumns(20);
						
						final int finalcol = col;
						tf.addMouseListener(new MouseListener(){

							@Override
							public void mouseClicked(MouseEvent arg0) {
								textFields[finalcol].requestFocusInWindow();
								stateInfo.setSelectedCell(new Cell(stateInfo.getSelectedCell().getRow(), finalcol));
							}

							@Override
							public void mouseEntered(MouseEvent arg0) {
								// TODO Auto-generated method stu
								
							}

							@Override
							public void mouseExited(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void mousePressed(MouseEvent arg0) {
								textFields[finalcol].requestFocusInWindow();
								stateInfo.setSelectedCell(new Cell(stateInfo.getSelectedCell().getRow(), finalcol));
							}

							@Override
							public void mouseReleased(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}
							
						});
						
						tf.addKeyListener(new KeyListener()
						{

							@Override
							public void keyPressed(KeyEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void keyReleased(KeyEvent arg0) {
								
							}

							@Override
							public void keyTyped(KeyEvent arg0) {
								if(arg0.getKeyCode() == KeyEvent.VK_TAB)
								{
									textFields[finalcol].requestFocusInWindow();
									stateInfo.setSelectedCell(new Cell(stateInfo.getSelectedCell().getRow(), finalcol));	
								}
								else
								{
									System.out.println("Arg 0 is: " + arg0.getKeyCode());
								}
								
							}
							
						});
						
						if(stateInfo.getSelectedCell().getCol() == col)
						{
							textFields[col].requestFocusInWindow();
						}
						
						horizLayout.add(textFields[col]);
						formPanel.add(horizLayout);
					}
					col++;
				}
		}	
		
		
		stateInfo.addListener(new BatchStateListener(){

			@Override
			public void valueChanged(Cell cell, String newValue) {
				//getValues();
			}

			@Override
			public void selectedCellChanged(Cell newSelectedCell) {
				System.out.println("Trying to get from this Row: " + newSelectedCell.getRow() + " and this Col: " + newSelectedCell.getCol());
				list.setSelectedIndex(newSelectedCell.getRow());
				getValues();
				if(newSelectedCell.getCol() != 0)
				{
					textFields[newSelectedCell.getCol()].requestFocusInWindow();
				}
			}
			
		});
		
	}
	
	
	public void getValues(){
		for(int col = 1; col < textFields.length; col++)
		{
			textFields[col].setText(stateInfo.getValue(new Cell(stateInfo.getSelectedCell().getRow(), col)));
		}
	}
}
