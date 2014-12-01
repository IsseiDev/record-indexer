package client;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;

public class Client_Communicator {

	private String server_host;
	private String server_port;
	private String url_prefix;
	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";
	private XStream xmlStream;
	
	/**
	 * @param server_host
	 * @param server_port
	 */
	
	public Client_Communicator()
	{
		xmlStream = new XStream(new DomDriver());
		this.server_host = "localhost";
		this.server_port = "8080";
		this.url_prefix = "http://" + getServer_host() + ":" + getServer_port();
	}

	public Client_Communicator(String server_host, String server_port) {
		xmlStream = new XStream(new DomDriver());
		this.server_host = server_host;
		this.server_port = server_port;
		this.url_prefix = "http://" + getServer_host() + ":" + getServer_port();
	}

	/**
	 * @return the server_host
	 */
	public String getServer_host() {
		return server_host;
	}

	/**
	 * @param server_host the server_host to set
	 */
	public void setServer_host(String server_host) {
		this.server_host = server_host;
	}

	/**
	 * @return the server_port
	 */
	public String getServer_port() {
		return server_port;
	}

	/**
	 * @param server_port the server_port to set
	 */
	public void setServer_port(String server_port) {
		this.server_port = server_port;
	}

	/**
	 * @return the xmlStream
	 */
	public XStream getXmlStream() {
		return xmlStream;
	}

	/**
	 * @param xmlStream the xmlStream to set
	 */
	public void setXmlStream(XStream xmlStream) {
		this.xmlStream = xmlStream;
	}
	
	public ValidateUser_Result Validate_User(ValidateUser_Params params) throws ClientException
	{
		return (ValidateUser_Result) doPost("/ValidateUser", params);
	}
	
	public GetProjects_Result Get_Projects() throws ClientException
	{
		return (GetProjects_Result) doGet("/GetProjects");
	}
	
	public GetSampleImage_Result Sample_Image(GetSampleImage_Params params) throws ClientException
	{
		return (GetSampleImage_Result) doPost("/GetSampleImage", params);
	}
	
	public DownloadBatch_Result Download_Batch(DownloadBatch_Params params) throws ClientException
	{
		return (DownloadBatch_Result) doPost("/DownloadBatch", params);
	}
	
	public void Submit_Batch(SubmitBatch_Params params) throws ClientException
	{
		doPost("/SubmitBatch", params);
	}
	
	public GetFields_Result Get_Fields(GetFields_Params params) throws ClientException
	{
		return (GetFields_Result) doPost("/GetFields", params);
	}
	
	public Search_Result Search(Search_Params params) throws ClientException
	{
		return (Search_Result) doPost("/Search", params);
	}
	
	public void Download_File()
	{
		
	}
	
	private Object doGet(String urlPath) throws ClientException {
		try {
			URL url = new URL(url_prefix + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_GET);
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				Object result = xmlStream.fromXML(connection.getInputStream());
				return result;
			}
			else {
				throw new ClientException(String.format("doGet failed: %s (http code %d)",
											urlPath, connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new ClientException();
		}
	}
	
	private Object doPost(String urlPath, Object postData) throws ClientException {
		try {
			URL url = new URL(url_prefix + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.connect();

			xmlStream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				Object result = xmlStream.fromXML(connection.getInputStream());
				return result;
			}
			else if (connection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND)
			{
				return null;
			}
			else
			{
				throw new ClientException(String.format("doPost failed: %s (http code %d)",
						urlPath, connection.getResponseCode()));
			}

		}
		catch (IOException e) {
			throw new ClientException(String.format("doPost failed: %s", e.getMessage()), e);
		}
	}

	/**
	 * @return the url_prefix
	 */
	public String getUrl_prefix() {
		return url_prefix;
	}

	/**
	 * @param url_prefix the url_prefix to set
	 */
	public void setUrl_prefix(String url_prefix) {
		this.url_prefix = url_prefix;
	}
}
