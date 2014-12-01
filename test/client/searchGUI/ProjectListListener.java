package client.searchGUI;

import java.awt.event.*;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;

public class ProjectListListener  implements ActionListener
{ // TheButtonListener
    
    SearchFrame searchFrame;
    
    public ProjectListListener(SearchFrame searchFrame)
    { // constructor
        this.searchFrame = searchFrame;
    } // constructor
    
    public void actionPerformed(ActionEvent e)
    { // actionPerformed
    	
        Vector<String> fields = searchFrame.getFields();
        
        JList<String> fieldsList = new JList<String>(fields);
        searchFrame.setFieldsList(fieldsList);
        JScrollPane fieldsPane = new JScrollPane(fieldsList);
        fieldsPane.setBounds(10, 70, 240, 300);
        
        JScrollPane imagesPane = new JScrollPane(new JList<String>());
        imagesPane.setBounds(280, 10, 380, 400);
        
        searchFrame.remove(searchFrame.getImagesPane());
        searchFrame.setImagesPane(imagesPane);
        searchFrame.add(searchFrame.getImagesPane());
        
        searchFrame.remove(searchFrame.getFieldsPane());
        searchFrame.setFieldsPane(fieldsPane);
        searchFrame.add(searchFrame.getFieldsPane());
        searchFrame.repaint();
        searchFrame.revalidate();
    } // actionPerformed

} // TheButtonListener
