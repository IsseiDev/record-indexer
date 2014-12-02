package lectures;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class FavPanel extends JPanel {

    public interface Context {
		
		public String getCurrentUrl();
		
		public void onFavSelected(String url);
	}

	
	private Context context;
    private JButton addButton;
    private JButton removeButton;
    private JList<String> favList;
    private JPopupMenu favPopupMenu;
    private JMenuItem favAddMenuItem;
    private JMenuItem favRemoveMenuItem;

	public FavPanel(Context c) {
		
		super();
		
		context = c;
		
        addButton = new JButton("Add");
        addButton.addActionListener(actionListener);
        
        removeButton = new JButton("Remove");
        removeButton.addActionListener(actionListener);
        
        JToolBar favToolBar = new JToolBar();
        favToolBar.add(addButton);
        favToolBar.add(removeButton);
        
        favList = new JList<String>(new String[]{ "FAVORITES WILL GO HERE" });
        favList.addMouseListener(mouseAdapter);
       
        JScrollPane favScrollPane = new JScrollPane(favList);
        favScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        favScrollPane.setPreferredSize(new Dimension(250, 600));
        
        favAddMenuItem = new JMenuItem("Add");
        favAddMenuItem.addActionListener(actionListener);
        
        favRemoveMenuItem = new JMenuItem("Remove");
        favRemoveMenuItem.addActionListener(actionListener);
        
        favPopupMenu = new JPopupMenu();
        favPopupMenu.add(favAddMenuItem);
        favPopupMenu.add(favRemoveMenuItem);
       
        this.setLayout(new BorderLayout());
        this.add(favToolBar, BorderLayout.NORTH);
        this.add(favScrollPane, BorderLayout.CENTER);
	}

    private ActionListener actionListener = new ActionListener() {
    	
	    public void actionPerformed(ActionEvent e) {
	    	
	    	if (e.getSource() == addButton ||
	    			e.getSource() == favAddMenuItem) {
	    		addFav();
	    	}
	    	else if (e.getSource() == removeButton ||
	    				e.getSource() == favRemoveMenuItem) {
	    		removeFav();
	    	}
	    }
    };
    
    private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {
				if (e.getSource() == favList) {
					favPopupMenu.show(e.getComponent(),
		                       			e.getX(), e.getY());
				}
			}
		} 	
    };
	
	public void addFav() {
		
    	System.out.println("add favorite");
    	
		// TODO
	}
	
	public void removeFav() {
		
    	System.out.println("remove favorite");
    	
		// TODO
	}

}

