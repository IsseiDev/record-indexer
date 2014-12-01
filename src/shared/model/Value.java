package shared.model;

public class Value{
	
	int id = 0;
	String value;
	int record_id;
	int field_id;

	
	public Value() {
		setValue(null);
		setRecord_id(-1);
		setField_id(-1);
	}
	
	public Value(String value, int record_id, int field_id) {
		setValue(value);
		setRecord_id(record_id);
		setField_id(field_id);
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the record_id
	 */
	public int getRecord_id() {
		return record_id;
	}

	/**
	 * @param record_id the record_id to set
	 */
	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}

	/**
	 * @return the field_id
	 */
	public int getField_id() {
		return field_id;
	}

	/**
	 * @param field_id the field_id to set
	 */
	public void setField_id(int field_id) {
		this.field_id = field_id;
	}
	
}

