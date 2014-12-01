package server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class DownloadHandler implements HttpHandler{
	Logger logger; 
	
	@Override
	public void handle(HttpExchange exchange) throws IOException{
		logger = Logger.getLogger("record-indexer");
		logger.log(Level.FINE, "database" + File.separator + "Records" + File.separator + exchange.getRequestURI().getPath());
		File file = new File("database" + File.separator + "Records" + File.separator + exchange.getRequestURI().getPath());
		exchange.sendResponseHeaders(200, 0);
		Files.copy(file.toPath(), exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}
