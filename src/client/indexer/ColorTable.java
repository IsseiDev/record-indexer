package client.indexer;


import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;


@SuppressWarnings("serial")
public class ColorTable extends JPanel {

	private ArrayList<ColorScheme> colorSchemes;
	private TableModel tableModel;
	private JTable table;
	
	public ColorTable() throws HeadlessException {
		
		colorSchemes = generateSchemes();
		
		tableModel = new TableModel(colorSchemes);
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		
		TableColumnModel columnModel = table.getColumnModel();
		for (int i = 0; i < tableModel.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(150);
		}	
		
	}
	
	private ArrayList<ColorScheme> generateSchemes() {
		
		final int NUM_SCHEMES = 20;
		
		ArrayList<ColorScheme> result = new ArrayList<ColorScheme>();
		Random rand = new Random();
		
		for (int i = 1; i <= NUM_SCHEMES; ++i) {
			
			ColorScheme scheme = new ColorScheme("Scheme " + i,
													generateColor(rand),
													generateColor(rand),
													generateColor(rand),
													generateColor(rand));
			result.add(scheme);
		}
		
		return result;
	}
	
	private Color generateColor(Random rand) {
		
		int r = rand.nextInt(256);
		int g = rand.nextInt(256);
		int b = rand.nextInt(256);
		
		return new Color(r, g, b);
	}

}
