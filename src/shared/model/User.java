package shared.model;

public class User {
	
	int id = 0;
	String firstname;
	String lastname;
	String username;
	String password;
	String email;
	int indexedrecords;
	int current_batch;
	
	public User(String username, String password) {
		setFirstname(null);
		setLastname(null);
		setUsername(username);
		setPassword(password);
		setEmail(null);
		setIndexedrecords(-1);
		setCurrent_batch(-1);
	}
	

	public User(String firstname, String lastname, String username, 
			String password, String email, int indexedrecords, int current_batch) {
		
		setFirstname(firstname);
		setLastname(lastname);
		setUsername(username);
		setPassword(password);
		setEmail(email);
		setIndexedrecords(indexedrecords);
		setCurrent_batch(current_batch);
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
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}


	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}


	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}


	/**
	 * @return the indexedrecords
	 */
	public int getIndexedrecords() {
		return indexedrecords;
	}


	/**
	 * @param indexedrecords the indexedrecords to set
	 */
	public void setIndexedrecords(int indexedrecords) {
		this.indexedrecords = indexedrecords;
	}


	/**
	 * @return the current_batch
	 */
	public int getCurrent_batch() {
		return current_batch;
	}


	/**
	 * @param current_batch the current_batch to set
	 */
	public void setCurrent_batch(int current_batch) {
		this.current_batch = current_batch;
	}

	
}

