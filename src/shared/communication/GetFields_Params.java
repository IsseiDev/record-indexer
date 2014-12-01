package shared.communication;

public class GetFields_Params {

	String user;
	String password;
	int projectID;
	
	/**
	 * @param username
	 * @param password
	 * @param project
	 */
	public GetFields_Params(String username, String password, int project) {
		this.user = username;
		this.password = password;
		this.projectID = project;
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
	
	/**
	 * @return the project
	 */
	public int getProjectID() {
		return projectID;
	}

	/**
	 * @param project the project id to set
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
}
