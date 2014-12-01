package client.searchGUI;

/*
 * TheButtonListener.java
 */
import java.awt.event.*;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;

public class SearchButtonListener  implements ActionListener
{ // TheButtonListener
    
    SearchFrame searchFrame;
    
    public SearchButtonListener(SearchFrame searchFrame)
    { // constructor
        this.searchFrame = searchFrame;
    } // constructor
    
    public void actionPerformed(ActionEvent e)
    { // actionPerformed
    	Vector<String> images = searchFrame.Search();
    	
		ImageListListener button3 = new ImageListListener(searchFrame);
        JList<String> imagesList = new JList<String>(images);
        imagesList.addListSelectionListener(button3);
    	
    	
        searchFrame.setImagesList(imagesList);
        JScrollPane imagesPane = new JScrollPane(imagesList);
        imagesPane.setBounds(280, 10, 380, 400);
        
        searchFrame.remove(searchFrame.getImagesPane());
        searchFrame.setImagesPane(imagesPane);
        searchFrame.add(searchFrame.getImagesPane());
        searchFrame.repaint();
        searchFrame.revalidate();
        
    } // actionPerformed

} // TheButtonListener
