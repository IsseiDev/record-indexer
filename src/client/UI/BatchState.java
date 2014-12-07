package client.UI;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import shared.model.Field;
import shared.model.Record;
import client.indexer.Cell;
import client.listeners.BatchStateListener;

public class BatchState {
		
		List<Field> fields;
		List<Record> records;
		
		int recordCount = 6;
		int fieldCount = 6;
		int batchID;
		int firstycoord;
		int recordHeight;
		private String[][] values;
		private Cell selectedCell;
		private List<BatchStateListener> listeners;
		private static BufferedImage NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		private BufferedImage image = NULL_IMAGE;
		
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
		
		public void loadImage(String imageFile) {
			try {
				image = ImageIO.read(new URL(imageFile));
			}
			catch (IOException e) {
				image = NULL_IMAGE;
			}
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
		
		public String[][] getValues() {
			return values;
		}

		public void setValues(String[][] values) {
			this.values = values;
		}

		public List<BatchStateListener> getListeners() {
			return listeners;
		}

		public void setListeners(List<BatchStateListener> listeners) {
			this.listeners = listeners;
		}

		public static BufferedImage getNULL_IMAGE() {
			return NULL_IMAGE;
		}

		public static void setNULL_IMAGE(BufferedImage nULL_IMAGE) {
			NULL_IMAGE = nULL_IMAGE;
		}

		public BufferedImage getImage() {
			return image;
		}

		public void setImage(BufferedImage image) {
			this.image = image;
		}

		public int getBatchID() {
			return batchID;
		}

		public void setBatchID(int batchID) {
			this.batchID = batchID;
		}

		public int getFirstycoord() {
			return firstycoord;
		}

		public void setFirstycoord(int firstycoord) {
			this.firstycoord = firstycoord;
		}

		public int getRecordHeight() {
			return recordHeight;
		}

		public void setRecordHeight(int recordHeight) {
			this.recordHeight = recordHeight;
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
