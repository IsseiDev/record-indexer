package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.DatabaseException;
import server.facade.ServerFacade;
import shared.communication.Search_Params;
import shared.communication.Search_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SearchHandler implements HttpHandler{
	Logger logger; 
	private XStream xmlStream = new XStream(new DomDriver());
	
	@Override
	public void handle(HttpExchange exchange) throws IOException{
		logger = Logger.getLogger("record-indexer");
		Search_Result result = null;
		Search_Params params = (Search_Params)xmlStream.fromXML(exchange.getRequestBody());	
		
		try {
			result = ServerFacade.search(params);
		}
		catch (ServerException | DatabaseException e)
		{
			//Server failure
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
		}
		if(result != null && !result.getSearchResults().isEmpty())
		{
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
		else
		{
			logger.log(Level.WARNING, "Could not get Projects");
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_ACCEPTABLE, -1);
		}
	}
}



