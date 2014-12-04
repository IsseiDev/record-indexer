package client.indexer;


import java.awt.*;
import javax.swing.table.*;

@SuppressWarnings("serial")
public class TableModel extends AbstractTableModel {

	public TableModel() {
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public String getColumnName(int column) {

		String result = null;

		if (column >= 0 && column < getColumnCount()) {

			switch (column) {
			case 0:
				result = "Scheme Name";
				break;
			case 1:
				result = "Foreground";
				break;
			case 2:
				result = "Background";
				break;
			case 3:
				result = "Highlight";
				break;
			case 4:
				result = "Shadow";
				break;
			default:
				assert false;
				break;
			}
		} else {
			throw new IndexOutOfBoundsException();
		}

		return result;
	}

	@Override
	public int getRowCount() {
		return 5;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if(column == 0)
		{
			return false;
		}
		
		return true;
	}

	@Override
	public Object getValueAt(int row, int column) {

		String result = "hello";

		return result;
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		
		if (row >= 0 && row < getRowCount() && column >= 0
				&& column < getColumnCount()) {

			
			if (column > 0) {
				
			}
			
			this.fireTableCellUpdated(row, column);
			
		} else {
			throw new IndexOutOfBoundsException();
		}		
	}

}
