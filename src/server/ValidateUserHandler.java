package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.DatabaseException;
import server.facade.ServerFacade;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import shared.model.User;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ValidateUserHandler implements HttpHandler{
	
	Logger logger = Logger.getLogger("record-indexer"); 
	private XStream xmlStream = new XStream(new DomDriver());
	
	@Override
	public void handle(HttpExchange exchange) throws IOException{

		ValidateUser_Params params = (ValidateUser_Params)xmlStream.fromXML(exchange.getRequestBody());			
		User u = null;
		
		try {
			u = ServerFacade.validateUser(params);
		}
		catch (ServerException | DatabaseException e)
		{
			//Server failure
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		if(u != null)
		{
			ValidateUser_Result result = new ValidateUser_Result(u.getFirstname(), u.getLastname(), u.getIndexedrecords());
		
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
		else
		{
			//Credentials are invalid
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
		}
	}
}
