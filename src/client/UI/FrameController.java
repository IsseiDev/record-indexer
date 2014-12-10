package client.UI;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import server.database.DatabaseException;
import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;
import shared.communication.GetProjects_Result;
import shared.communication.GetProjects_Result.ProjectInfo;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.GetSampleImage_Params;
import shared.communication.GetSampleImage_Result;
import shared.communication.Search_Params;
import shared.communication.Search_Result;
import shared.communication.SubmitBatch_Params;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import client.ClientException;
import client.Client_Communicator;
import client.indexer.IndexerFrame;
import client.indexer.TableModel;
import client.login.InvalidFrame;
import client.login.LoginFrame;
import client.login.WelcomeFrame;

public class FrameController {

	Client_Communicator cc;
	LoginFrame loginFrame = null;
	WelcomeFrame welcome;
	InvalidFrame invalidFrame;
	IndexerFrame indexerFrame = null;
	String username = "test1";
	String password = "test1";
	String hostname;
	String port;
	GetProjects_Result proResult;
	BatchState stateInfo;
	XStream xStream;
	OutputStream outFile;
	
	public void runLogin(String hostname, String port)
	{
		this.hostname = hostname;
		this.port = port;
		cc = new Client_Communicator(hostname, port);
		System.out.println("running on " + hostname + " : " + port);
		//TO DO - This needs to be changed based on whether or not that person has information already
		stateInfo = new BatchState(5, 5);
		
		loginFrame = new LoginFrame(this);
		indexerFrame = new IndexerFrame(this, stateInfo);
		loginFrame.setVisible(true);
		indexerFrame.setVisible(false);
	}
	
	public void endLogin()
	{
		loginFrame.dispose();
	}
	
	public void runIndexer(boolean hasBatchState)
	{
		loginFrame.setVisible(false);
		indexerFrame.setUserDownloaded(hasBatchState);
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
		//TO DO - This needs to be changed based on whether or not that person has information already
		stateInfo = new BatchState(5, 5);
		stateInfo.setDummyFields();
		
		loginFrame = new LoginFrame(this);
		indexerFrame = new IndexerFrame(this, stateInfo);
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
						
				boolean hasBatchState = (result.getCurrent_batch() != -1);
				if(hasBatchState)
				{
					loadUser();
				}
				
				runIndexer(hasBatchState);
			}
			else
			{
				invalidFrame = new InvalidFrame(this, loginFrame);
				invalidFrame.setVisible(true);
			}
		} catch (ClientException e) {
			System.out.println("Exception: " + e);
			System.out.println("Could not connect with the server.\n");

		}
	}
	
	public void submitBatch()
	{		
		try {
			SubmitBatch_Params batchParams = new SubmitBatch_Params(username, password, stateInfo.getBatchID(), stateInfo.valuesToString());

			cc.Submit_Batch(batchParams);
			
			indexerFrame.setStateInfo(new BatchState(5, 5));
			indexerFrame.createTable();
			indexerFrame.updateImage();
			indexerFrame.createButtonPanel(false);
			indexerFrame.revalidate();
			indexerFrame.repaint();

		} catch (ClientException e) {
			System.out.println("Could not connect with the server.\n" + e);
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
		
		projectID = getIDfromProjectName(projectName);

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
	
	public DownloadBatch_Result downloadBatch(String projectName) {
		
		int projectID = 0;
		DownloadBatch_Result batchResult = null;
		
		projectID = getIDfromProjectName(projectName);
		
		System.out.println("The Name: " + projectName + " and ID: " + projectID);
				
		try {
			batchResult = cc.Download_Batch(new DownloadBatch_Params(username, password, projectID));
		} catch (ClientException e) {
			System.out.println("Could not connect with the server.");
		}
		
		if(batchResult != null)
		{
			stateInfo = new BatchState(batchResult.getNum_records(), batchResult.getNum_fields() + 1);
			stateInfo.setBatchID(batchResult.getBatch_id());
			stateInfo.loadImage(batchResult.getImage_url(hostname, port));
			stateInfo.setFirstycoord(batchResult.getFirst_y_coord());
			stateInfo.setRecordHeight(batchResult.getRecord_height());
			stateInfo.setFields(batchResult.getFields());
			stateInfo.setTableModel(new TableModel(stateInfo));
			indexerFrame.setStateInfo(stateInfo);
			indexerFrame.createListeners();
			indexerFrame.createTable();
			indexerFrame.updateImage();
			
		}
		//TODO Get All Fields and All Records for this batch
		
		//batchResult.get
		
		return batchResult;
	}
	
	public int getIDfromProjectName(String projectName)
	{
		int projectID = 0;
		
		for(ProjectInfo pi : proResult.getInfo())
		{
			if(pi.getProject_title().equals(projectName))
			{
				projectID = pi.getProject_id();
			}
		}
		
		return projectID;
	}
	
	public void saveUser()
	{
		System.out.println("Saving User.");
		xStream = new XStream(new DomDriver()); 

		try {
			System.out.println("Trying.");
			outFile = new BufferedOutputStream(new FileOutputStream( username + ".xml"));
			System.out.println("Putting to XML.");
			xStream.toXML(stateInfo, outFile); // This writes your batchstate to the outputFile;
			System.out.println("Saving OUTFILE.");
			outFile.close(); //close the writer
			System.out.println("Closing.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("The exception: " + e);

		} //Makes a xml file with the person's username

	}
	
	public void loadUser()
	{
		xStream = new XStream(new DomDriver()); 
		
		try {
			InputStream	inFile = new BufferedInputStream(new FileInputStream(username + ".xml")); //find the file with the given username
			stateInfo = (BatchState) xStream.fromXML(inFile); //Read that batchstate back in to the exact form it was before
			inFile.close();
			System.out.println("\n\n\nLoading the image scale: " + stateInfo.getScale());
			stateInfo.setTableModel(new TableModel(stateInfo));
			indexerFrame.setStateInfo(stateInfo);
			indexerFrame.createListeners();
			stateInfo.loadImage(stateInfo.imageLocation);
			indexerFrame.createTable();
			indexerFrame.updateImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

