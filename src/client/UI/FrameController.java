package client.UI;


import java.util.Vector;

import server.database.DatabaseException;
import shared.communication.GetProjects_Result;
import shared.communication.GetProjects_Result.ProjectInfo;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.GetSampleImage_Params;
import shared.communication.GetSampleImage_Result;
import shared.communication.Search_Params;
import shared.communication.Search_Result;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import client.ClientException;
import client.Client_Communicator;
import client.indexer.IndexerFrame;
import client.login.InvalidFrame;
import client.login.LoginFrame;
import client.login.WelcomeFrame;

public class FrameController {

	Client_Communicator cc = new Client_Communicator("localhost", "8080");
	LoginFrame loginFrame = null;
	WelcomeFrame welcome;
	InvalidFrame invalidFrame;
	IndexerFrame indexerFrame = null;
	String username = "test1";
	String password = "test1";
	String hostname = "localhost";
	String port = "8080";
	GetProjects_Result proResult;

	
	public void runLogin(String hostname, String port)
	{
		if(hostname != "")
		{
			this.hostname = hostname;
		}
		this.port = port;
		
		loginFrame = new LoginFrame(this);
		indexerFrame = new IndexerFrame(this);
		loginFrame.setVisible(true);
		indexerFrame.setVisible(false);
	}
	
	public void endLogin()
	{
		loginFrame.dispose();
	}
	
	public void runIndexer()
	{
		loginFrame.setVisible(false);
		indexerFrame.setVisible(true);
	}
	
	public void logout()
	{
		indexerFrame.setVisible(false);
		loginFrame.setVisible(true);
		loginFrame.usernameField.setText("");
		loginFrame.passwordField.setText("");
	}
	
	public void testIndexer()
	{
		loginFrame = new LoginFrame(this);
		indexerFrame = new IndexerFrame(this);
		indexerFrame.setVisible(true);
		loginFrame.setVisible(false);
	}
	
	public void exitProgram()
	{
		if (loginFrame != null)
		{
			loginFrame.dispose();
		}
		if (indexerFrame != null)
		{
			indexerFrame.dispose();
		}
	}
	
	public void validateUser(String username, String password)
	{
		ValidateUser_Params params = new ValidateUser_Params(username , password);
		ValidateUser_Result result;
		try {
			result = cc.Validate_User(params);
			if (result != null)
			{
				setUsername(username);
				setPassword(password);
				String welcomeMessage = "Welcome, " + result.getUser_first_name() + " " + result.getUser_last_name() + ".";
				String indexedMessage = "You have indexed " + result.getNum_records() + " records.";
				welcome = new WelcomeFrame(this, welcomeMessage, indexedMessage);
				welcome.setVisible(true);
				
				runIndexer();
			}
			else
			{
				invalidFrame = new InvalidFrame(this, loginFrame);
				invalidFrame.setVisible(true);
			}
		} catch (ClientException e) {
			System.out.println("Could not connect with the server.\n");

		}
	}
	
	public Vector<String> getProjects()
	{		
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
	
	public String getSampleImage(String projectName) throws ClientException, DatabaseException {
		
		int projectID = 0;
		GetSampleImage_Result samResult = null;
		
		for(ProjectInfo pi : proResult.getInfo())
		{
			if(pi.getProject_title().equals(projectName))
			{
				projectID = pi.getProject_id();
			}
		}

		try {
			samResult= cc.Sample_Image(new GetSampleImage_Params(projectID));
		} catch (ClientException e) {
			System.out.println("Could not connect with the server.");
		}
		
		return samResult.toURLString(hostname, port);
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

