package client.searchGUI;


import java.util.Vector;

import shared.communication.GetProjects_Result;
import shared.communication.GetProjects_Result.ProjectInfo;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.Search_Params;
import shared.communication.Search_Result;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import client.ClientException;
import client.Client_Communicator;

public class FrameController {

	Client_Communicator cc = new Client_Communicator("localhost", "8080");
	LoginFrame loginFrame = new LoginFrame(this);
	SearchFrame searchFrame = new SearchFrame(this);
	String username = "test1";
	String password = "test1";
	String hostname = "localhost";
	String port = "8080";

	
	public void runLogin()
	{	
		loginFrame.setVisible(true);
		searchFrame.setVisible(false);
	}
	
	public void endLogin()
	{
		loginFrame.setVisible(false);
	}
	
	public void runSearch()
	{
		loginFrame.setVisible(false);
		searchFrame.setVisible(true);
	}
	
	public void validateUser(String hostname, String port, String username, String password)
	{
		cc.setServer_host(hostname);
		cc.setServer_port(port);
		cc.setUrl_prefix("http://" + hostname + ":" + port);
		ValidateUser_Params params = new ValidateUser_Params(username , password);
		ValidateUser_Result result;
		try {
			result = cc.Validate_User(params);
			if (result != null)
			{
				loginFrame.setVisible(false);
				searchFrame.setVisible(true);
				setUsername(username);
				setPassword(password);
				setHostname(hostname);
				setPort(port);
			}
			else
			{
				System.out.println("Could not validate the user.\n");
			}
		} catch (ClientException e) {
			System.out.println("Could not connect with the server.\n");

		}
	}
	
	public Vector<String> getProjects()
	{		
		GetProjects_Result proResult;
		Vector<String> result = new Vector<String>();
		
		try {
			proResult = cc.Get_Projects();		
			
			for(ProjectInfo pi : proResult.getInfo())
			{
				result.add(pi.getProject_title());
			}
		

		
		} catch (ClientException e) {
			System.out.println("Could not connect with the server.\n");
		}
		
		return result;
	}
	
	public GetFields_Result getFields(int projectID) {

		GetFields_Result fieldsResult = new GetFields_Result();

		try {
			fieldsResult= cc.Get_Fields(new GetFields_Params(username, password, projectID));
		
		} catch (ClientException e) {
			System.out.println("Could not connect with the server.\n");
		}
		
		return fieldsResult;
	}	
	
	public Vector<String> Search(String fieldsString, String valuesString) {

		Search_Result searchResult = new Search_Result();

		try {
			searchResult = cc.Search(new Search_Params(username, password, fieldsString, valuesString));
		
		} catch (ClientException e) {
			System.out.println("Could not connect with the server or no results were found.\n");
		}
		
		return searchResult.getURLStringVector(hostname, port);
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
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * @param hostname the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}
}
