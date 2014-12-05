package client.UI;

import java.util.ArrayList;
import java.util.List;

import shared.model.Field;
import shared.model.Record;
import client.indexer.Cell;
import client.listeners.BatchStateListener;

public class BatchState {
		
		List<Field> fields;
		List<Record> records;
		
		int recordCount = 6;
		int fieldCount = 6;
		private String[][] values;
		private Cell selectedCell;
		private List<BatchStateListener> listeners;
		private String imageLocation = "http://localhost:8080/images/draft_image3.png";
		
		public BatchState(int recordCount, int fieldCount) {
			
			this.recordCount = recordCount;
			this.fieldCount = fieldCount;
			values = new String[recordCount][fieldCount];
			selectedCell = null;
			listeners = new ArrayList<BatchStateListener>();
			
		}
		
		public void addListener(BatchStateListener l) {
			listeners.add(l);
		}
		
		public void setValue(Cell cell, String value) {
			
			values[cell.getRow()][cell.getCol()] = value;
			
			for (BatchStateListener l : listeners) {
				l.valueChanged(cell, value);
			}
		}
		
		public String getValue(Cell cell) {
			return values[cell.getRow()][cell.getCol()];
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

		public String getImageLocation() {
			return imageLocation;
		}

		public void setImageLocation(String imageLocation) {
			this.imageLocation = imageLocation;
		}

		public int getRecordCount() {
			return recordCount;
		}

		public void setRecordCount(int recordCount) {
			this.recordCount = recordCount;
		}

		public int getFieldCount() {
			return fieldCount;
		}

		public void setFieldCount(int fieldCount) {
			this.fieldCount = fieldCount;
		}

		public List<Field> getFields() {
			return fields;
		}

		public void setFields(List<Field> fields) {
			this.fields = fields;
		}

		public List<Record> getRecords() {
			return records;
		}

		public void setRecords(List<Record> records) {
			this.records = records;
		}
		
		public String getFieldName(int fieldIndex)
		{
			return fields.get(fieldIndex).getTitle();
		}
		
		public void setDummyFields(){
			fields = new ArrayList<Field>();
			Field f = new Field();
			f.setTitle("Hi");
			fields.add(f);
			Field fi = new Field();
			fi.setTitle("My");
			fields.add(fi);
			Field fie = new Field();
			fie.setTitle("Name");
			fields.add(fie);
			Field fiel = new Field();
			fiel.setTitle("Is");
			fields.add(fiel);
			Field field = new Field();
			field.setTitle("Moe");
			fields.add(field);
		}

	}
