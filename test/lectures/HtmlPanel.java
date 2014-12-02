package lectures;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;


@SuppressWarnings("serial")
public class HtmlPanel extends JPanel {

	public interface Context {

		public void onUrlSelected(String url);
	}

	
	private Context context;
	private JButton backButton;
	private JButton forwardButton;
	private JEditorPane htmlViewer;
    private JPopupMenu htmlPopupMenu;
    private JMenuItem htmlBackMenuItem;
    private JMenuItem htmlForwardMenuItem;

	private ArrayList<String> history;
	private int historyPosition;

	public HtmlPanel(Context c) {

		super();

		context = c;

		history = new ArrayList<String>();
		historyPosition = -1;

		backButton = new JButton("Back");
		backButton.addActionListener(actionListener);

		forwardButton = new JButton("Forward");
		forwardButton.addActionListener(actionListener);

		JToolBar htmlToolBar = new JToolBar();
		htmlToolBar.add(backButton);
		htmlToolBar.add(forwardButton);

		htmlViewer = new JEditorPane();
		htmlViewer.setOpaque(true);
		htmlViewer.setBackground(Color.white);
		htmlViewer.setPreferredSize(new Dimension(500, 600));
		htmlViewer.setEditable(false);
		htmlViewer.addHyperlinkListener(hyperlinkListener);
        htmlViewer.addMouseListener(mouseAdapter);

		JScrollPane htmlScrollPane = new JScrollPane(htmlViewer);
		htmlScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		htmlScrollPane.setPreferredSize(new Dimension(500, 600));
        
        htmlBackMenuItem = new JMenuItem("Back");
        htmlBackMenuItem.addActionListener(actionListener);
        
        htmlForwardMenuItem = new JMenuItem("Forward");
        htmlForwardMenuItem.addActionListener(actionListener);
        
        htmlPopupMenu = new JPopupMenu();
        htmlPopupMenu.add(htmlBackMenuItem);
        htmlPopupMenu.add(htmlForwardMenuItem);

		this.setLayout(new BorderLayout());
		this.add(htmlToolBar, BorderLayout.NORTH);
		this.add(htmlScrollPane, BorderLayout.CENTER);
	}

	private ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == backButton ||
					e.getSource() == htmlBackMenuItem) {
				back();
			} 
			else if (e.getSource() == forwardButton ||
						e.getSource() == htmlForwardMenuItem) {
				forward();
			}
		}
	};

	private HyperlinkListener hyperlinkListener = new HyperlinkListener() {

		public void hyperlinkUpdate(HyperlinkEvent e) {

			System.out.println("hyperlink");

			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				String url = e.getURL().toString();
				historyLoadPage(url);
			}
		}
	};
    
    private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {
				if (e.getSource() == htmlViewer) {
						htmlPopupMenu.show(e.getComponent(),
	                   						e.getX(), e.getY());
				}
			}
		} 	
    };
	
	public void setUrl(String url) {
		historyLoadPage(url);
	}
	
	public void back() {
    	
        System.out.println("go back");
        
        if (historyPosition > 0) {
        	--historyPosition;
        	String url = history.get(historyPosition);
            loadPage(url);
       }
	}
	
	public void forward() {
    	
        System.out.println("go forward");
        
        if (historyPosition < (history.size() - 1)) {
        	++historyPosition;
        	String url = history.get(historyPosition);
            loadPage(url);
        }
	}

    private void loadPage(String url) {
    	
        System.out.println("loadPage: " + url);
        
        if (context != null) {
        	context.onUrlSelected(url);
        }

        try {
            htmlViewer.setPage(url);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void historyLoadPage(String url) {
    	
    	// Remove URLs after the current one in the history
    	while (history.size() > (historyPosition + 1))
    		history.remove(historyPosition + 1);
 
    	// Append the new URL to the history
    	history.add(url);
    	
    	// Update the history position
    	++historyPosition;
    	assert (historyPosition == history.size() - 1);
    	
    	// Load the new URL
        loadPage(url);
    }
    
}

