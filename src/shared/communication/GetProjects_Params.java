package shared.communication;

public class GetProjects_Params {
	String user;
	String password;
	
	/**
	 * @param username
	 * @param password
	 */
	public GetProjects_Params(String username, String password) {
		this.user = username;
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return user;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.user = username;
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
	

}
