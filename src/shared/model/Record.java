package shared.model;

public class Record{
	
	int id = 0;
	int batch_id;
	int record_number;
	
	public Record() {
		setBatch_id(-1);
		setRecord_number(-1);
	}
	
	public Record(int batch_id, int record_number) {
		setBatch_id(batch_id);
		setRecord_number(record_number);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the batch_id
	 */
	public int getBatch_id() {
		return batch_id;
	}

	/**
	 * @param batch_id the batch_id to set
	 */
	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}

	/**
	 * @return the record_number
	 */
	public int getRecord_number() {
		return record_number;
	}

	/**
	 * @param record_number the record_number to set
	 */
	public void setRecord_number(int record_number) {
		this.record_number = record_number;
	}

	@Override
	public String toString()
	{
		return "id: " + id + "\nBatch ID: " + batch_id + "\nRecord Number: " + record_number + "\n";  
	}
	
}

