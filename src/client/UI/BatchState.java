package client.UI;

import java.util.ArrayList;
import java.util.List;

import client.listeners.BatchStateListener;

public class BatchState {
		
		private String[][] values;
		private Cell selectedCell;
		private List<BatchStateListener> listeners;
		
		public BatchState(int records, int fields) {
			values = new String[records][fields];
			selectedCell = null;
			listeners = new ArrayList<BatchStateListener>();
		}
		
		public void addListener(BatchStateListener l) {
			listeners.add(l);
		}
		
		public void setValue(Cell cell, String value) {
			
			values[cell.row][cell.col] = value;
			
			for (BatchStateListener l : listeners) {
				l.valueChanged(cell, value);
			}
		}
		
		public String getValue(Cell cell) {
			return values[cell.row][cell.col];
		}
		
		public void setSelectedCell(Cell selCell) {
			
			selectedCell = selCell;
			
			for (BatchStateListener l : listeners) {
				l.selectedCellChanged(selCell);
			}
		}
		
		public Cell getSelectedCell() {
			return selectedCell;
		}

		public class Cell {
			int row;
			int col;
		}
	}
