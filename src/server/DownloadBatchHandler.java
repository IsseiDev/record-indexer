package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.DatabaseException;
import server.facade.ServerFacade;
import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DownloadBatchHandler implements HttpHandler{
	Logger logger; 
	private XStream xmlStream = new XStream(new DomDriver());
	
	@Override
	public void handle(HttpExchange exchange) throws IOException{
		logger = Logger.getLogger("record-indexer");
		
		DownloadBatch_Params params = (DownloadBatch_Params)xmlStream.fromXML(exchange.getRequestBody());			

		
		DownloadBatch_Result b = null;
		
		try {
			b = ServerFacade.downloadBatch(params);
		}
		catch (ServerException | DatabaseException e)
		{
			//Server failure
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		if(b != null)
		{
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(b, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
		else
		{
			logger.log(Level.WARNING, "Could not download batch. The result returned null.");
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
		}
	}
}
