package client.listeners;

import client.indexer.Cell;

public interface BatchStateListener {
	
	public void valueChanged(Cell cell, String newValue);
	
	public void selectedCellChanged(Cell newSelectedCell);
	

}
