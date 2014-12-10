package client.indexer;


import javax.swing.table.*;

import client.UI.BatchState;
import client.listeners.BatchStateListener;

@SuppressWarnings("serial")
public class TableModel extends AbstractTableModel {
	
	BatchState stateInfo;
	
	public TableModel(BatchState stateInfo) {
		this.stateInfo = stateInfo;
		
	}

	@Override
	public int getColumnCount() {
		return stateInfo.getFieldCount();
	}

	@Override
	public String getColumnName(int column) {
		return stateInfo.getFieldName(column);
	}

	@Override
	public int getRowCount() {
		return stateInfo.getRecordCount();
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
		return stateInfo.getValue(new Cell(row, column));	
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		stateInfo.setValue(new Cell(row, column), (String)value);	
	}

}
