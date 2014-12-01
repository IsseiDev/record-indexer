package servertester.controllers;

import java.util.*;

import client.ClientException;
import client.Client_Communicator;
import servertester.views.*;
import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.GetProjects_Result;
import shared.communication.GetSampleImage_Params;
import shared.communication.GetSampleImage_Result;
import shared.communication.Search_Params;
import shared.communication.Search_Result;
import shared.communication.SubmitBatch_Params;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("8080");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		
		Client_Communicator cc = new Client_Communicator(getView().getHost() , getView().getPort());
		String[] p = getView().getParameterValues();
		ValidateUser_Params params = new ValidateUser_Params(p[0] , p[1]);
		ValidateUser_Result result;
		try {
			result = cc.Validate_User(params);
			if (result != null)
			{
				getView().setResponse("TRUE\n" + result.toString());
			}
			else
			{
				getView().setResponse("FALSE\n");
			}
		} catch (ClientException e) {
			getView().setResponse("FAILED\n");

		}
		
		
		//Get a client communicator instance, give it the 
		// getView().getHost(), getView.getPort()
		//Start the server
		//Call validateUser, give it the parameters
		// getView().getParamaters()
		//Print the response
	}
	
	private void getProjects() {
		
		Client_Communicator cc = new Client_Communicator(getView().getHost() , getView().getPort());
		String[] p = getView().getParameterValues();
		ValidateUser_Params params = new ValidateUser_Params(p[0] , p[1]);
		ValidateUser_Result result;
		GetProjects_Result proResult;
		try {
			result = cc.Validate_User(params);
			System.out.println(params.getPassword());
			if (result != null)
			{
				proResult = cc.Get_Projects();
				getView().setResponse(proResult.toString());
			}
			else
			{
				//Could not validate the user
				getView().setResponse("FAILED\n");
			}
		} catch (ClientException e) {
			getView().setResponse("FAILED\n");

		}
		
	}
	
	private void getSampleImage() {
		Client_Communicator cc = new Client_Communicator(getView().getHost() , getView().getPort());
		String[] p = getView().getParameterValues();
		ValidateUser_Params params = new ValidateUser_Params(p[0] , p[1]);
		ValidateUser_Result result;
		GetSampleImage_Result samResult;
		
		try {
			GetSampleImage_Params samParams = new GetSampleImage_Params(Integer.parseInt(p[2]));
			result = cc.Validate_User(params);
			if (result != null)
			{
				samResult = cc.Sample_Image(samParams);
				
				if (samResult != null)
				{
					getView().setResponse(samResult.toURLString(getView().getHost(), getView().getPort()));
				}
				else
				{
					//There was no sample image to find
					getView().setResponse("FAILED\n");
				}
			}
			else
			{
				//Could not validate the user
				getView().setResponse("FAILED\n");
			}
		} catch (ClientException e) {
			//Server error
			getView().setResponse("FAILED\n");

		} catch (NumberFormatException e) {
			//Third input value was not an integer
			getView().setResponse("FAILED\n");
		}
	}
	
	private void downloadBatch() {
		Client_Communicator cc = new Client_Communicator(getView().getHost() , getView().getPort());
		String[] p = getView().getParameterValues();
		ValidateUser_Params params = new ValidateUser_Params(p[0] , p[1]);
		ValidateUser_Result result;
		DownloadBatch_Result batchResult;
		
		try {
			DownloadBatch_Params batchParams = new DownloadBatch_Params(p[0], p[1], Integer.parseInt(p[2]));
			result = cc.Validate_User(params);
			if (result != null)
			{
				batchResult = cc.Download_Batch(batchParams);
				
				if (batchResult != null)
				{
					getView().setResponse(batchResult.toURLString(getView().getHost(), getView().getPort()));
				}
				else
				{
					//There was no sample image to find
					getView().setResponse("FAILED\n");
				}
			}
			else
			{
				//Could not validate the user
				getView().setResponse("FAILED\n");
			}
		} catch (ClientException e) {
			//Server error
			getView().setResponse("FAILED\n");

		} catch (NumberFormatException e) {
			//Third input value was not an integer
			getView().setResponse("FAILED\n");
		}
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	private void getFields() {
		
		Client_Communicator cc = new Client_Communicator(getView().getHost() , getView().getPort());
		String[] p = getView().getParameterValues();
		ValidateUser_Params params = new ValidateUser_Params(p[0] , p[1]);
		ValidateUser_Result result;
		int project_id = -1;
		if(isInteger(p[2]))
		{
			project_id = Integer.parseInt(p[2]);
		}
		GetFields_Params fieldParams = new GetFields_Params(p[0], p[1], project_id);
		GetFields_Result fieldResult;
		try {
			result = cc.Validate_User(params);
			if (result != null)
			{
				fieldResult = cc.Get_Fields(fieldParams);
				if(fieldResult != null && fieldResult.getProjects().size() != 0)
				{
					getView().setResponse(fieldResult.toString());
				} 
				else
				{
					getView().setResponse("FAILED\n");
				}
			}
			else
			{
				//Could not validate the user
				getView().setResponse("FAILED\n");
			}
		} catch (ClientException e) {
			getView().setResponse("FAILED\n");

		}
		
	}
	
	private void submitBatch() {
		Client_Communicator cc = new Client_Communicator(getView().getHost() , getView().getPort());
		String[] p = getView().getParameterValues();
		ValidateUser_Params params = new ValidateUser_Params(p[0] , p[1]);
		ValidateUser_Result result;
		
		try {
			SubmitBatch_Params batchParams = new SubmitBatch_Params(p[0], p[1], Integer.parseInt(p[2]), p[3]);
			result = cc.Validate_User(params);
			if (result != null)
			{
				cc.Submit_Batch(batchParams);
				getView().setResponse("TRUE\n");
			}
			else
			{
				//Could not validate the user
				getView().setResponse("FAILED\n");
			}
		} catch (ClientException e) {
			//Server error
			getView().setResponse("FAILED\n");

		} catch (NumberFormatException e) {
			//Third input value was not an integer
			getView().setResponse("FAILED\n");
		}
	}
	
	private void search() {
		Client_Communicator cc = new Client_Communicator(getView().getHost() , getView().getPort());
		String[] p = getView().getParameterValues();
		ValidateUser_Params params = new ValidateUser_Params(p[0] , p[1]);
		ValidateUser_Result result;

		Search_Params searchParams = new Search_Params(p[0], p[1], p[2], p[3]);
		Search_Result searchResult;
		try {
			result = cc.Validate_User(params);
			if (result != null)
			{
				System.out.println("User was validated.");
				searchResult = cc.Search(searchParams);
				getView().setResponse(searchResult.toURLString(getView().getHost(), getView().getPort()));
			}
			else
			{
				//Could not validate the user
				getView().setResponse("FAILED\n");
			}
		} catch (ClientException e) {
			getView().setResponse("FAILED\n");

		} catch (Exception e) {
			getView().setResponse("FAILED\n");
		}
	}

}

