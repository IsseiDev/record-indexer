package client.searchGUI;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class ImageListListener  implements ListSelectionListener
{ // TheButtonListener
    
    SearchFrame searchFrame;
    
    public ImageListListener(SearchFrame searchFrame)
    { // constructor
        this.searchFrame = searchFrame;
    } // constructor

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting())
		{
        	searchFrame.openImage();
        	searchFrame.repaint();	
		}
	}

}
