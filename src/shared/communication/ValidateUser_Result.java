package shared.communication;

public class ValidateUser_Result {

	String user_first_name;
	String user_last_name;
	int num_records;
	int current_batch;
	/**
	 * @param user_first_name
	 * @param user_last_name
	 * @param num_records
	 */
	public ValidateUser_Result(String user_first_name,
			String user_last_name, int num_records, int current_batch) {
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.num_records = num_records;
		this.current_batch = current_batch;
	}
	/**
	 * @return the user_first_name
	 */
	public String getUser_first_name() {
		return user_first_name;
	}
	/**
	 * @param user_first_name the user_first_name to set
	 */
	public void setUser_first_name(String user_first_name) {
		this.user_first_name = user_first_name;
	}
	/**
	 * @return the user_last_name
	 */
	public String getUser_last_name() {
		return user_last_name;
	}
	/**
	 * @param user_last_name the user_last_name to set
	 */
	public void setUser_last_name(String user_last_name) {
		this.user_last_name = user_last_name;
	}
	/**
	 * @return the num_records
	 */
	public int getNum_records() {
		return num_records;
	}
	/**
	 * @param num_records the num_records to set
	 */
	public void setNum_records(int num_records) {
		this.num_records = num_records;
	}
	
	public int getCurrent_batch() {
		return current_batch;
	}
	public void setCurrent_batch(int current_batch) {
		this.current_batch = current_batch;
	}
	@Override
	public String toString()
	{
		return user_first_name + "\n" + user_last_name + "\n" + num_records + "\n";		
	}
}
