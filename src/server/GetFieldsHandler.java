package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.DatabaseException;
import server.facade.ServerFacade;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.GetFields_Result.Project_id;
import shared.model.Field;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetFieldsHandler implements HttpHandler{
	Logger logger; 
	private XStream xmlStream = new XStream(new DomDriver());
	
	@Override
	public void handle(HttpExchange exchange) throws IOException{
		logger = Logger.getLogger("record-indexer");
		List<Field> fields = null;
		
		GetFields_Params params = (GetFields_Params)xmlStream.fromXML(exchange.getRequestBody());	
		
		try {
			fields = ServerFacade.getFields(params);
		}
		catch (ServerException | DatabaseException e)
		{
			//Server failure
			logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		if(fields != null)
		{
			List<Project_id> infoList = new ArrayList<Project_id>();
			GetFields_Result result = new GetFields_Result();
			for(Field f : fields)
			{
				infoList.add(result.new Project_id(f.getProject_id(), f.getId(), f.getTitle()));
			}
			result.setProjects(infoList);
		
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
		else
		{
			logger.log(Level.WARNING, "Could not get Projects");
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
		}
	}
}


