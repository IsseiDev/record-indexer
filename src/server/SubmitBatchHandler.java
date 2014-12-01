package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.DatabaseException;
import server.facade.ServerFacade;
import shared.communication.SubmitBatch_Params;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SubmitBatchHandler implements HttpHandler{
	
	Logger logger = Logger.getLogger("record-indexer"); 
	private XStream xmlStream = new XStream(new DomDriver());
	
	@Override
	public void handle(HttpExchange exchange) throws IOException{

		SubmitBatch_Params params = (SubmitBatch_Params)xmlStream.fromXML(exchange.getRequestBody());			
		boolean submitted = false;
		
		try {
			submitted = ServerFacade.submitBatch(params);

		}
		catch (ServerException | DatabaseException e)
		{
			//Server failure
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		if(submitted)
		{		
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(true, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
		else
		{
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_ACCEPTABLE, -1);
			return;
		}
	}
}
