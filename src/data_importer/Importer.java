package data_importer;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.commons.io.*;

import server.ServerException;
import server.database.Database;
import server.database.DatabaseException;
import shared.model.*;

public class Importer {
	
	Database db;
	
	public Importer(){
		db = new Database();
	}
	
	//Add commandline prompt to accept the xmlfilename
	//See build.xml line 83
	
	public void importData(String xmlFilename) throws ParserConfigurationException, SAXException, IOException, DatabaseException
	{
		System.out.println("Clearing DB and moving records.");
		File xmlFile = new File(xmlFilename);
		File dest = new File("database" + File.separator + "Records");
		try
		{
			//	We make sure that the directory we are copying is not the the destination
			//	directory.  Otherwise, we delete the directories we are about to copy.
			if(!xmlFile.getParentFile().getCanonicalPath().equals(dest.getCanonicalPath()))
				FileUtils.deleteDirectory(dest);
				
			//	Copy the directories (recursively) from our source to our destination.
			FileUtils.copyDirectory(xmlFile.getParentFile(), dest);
			clearDB();
			System.out.println("Done.");
		}
		catch (IOException e)
		{
			throw new DatabaseException("Could not copy the directory: " + e);
		}
		boolean i = Database.init();
		if (i)
		{
			DocumentBuilder docbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = docbuilder.parse(xmlFile);
		
			NodeList usersList = doc.getElementsByTagName("user");
			System.out.println("Parsing Users.");
			parseUsers(usersList);
			System.out.println("Done.");
			System.out.println("Parsing Projects.");
			parseProjects(doc);
			System.out.println("Done.");

		}
		else
		{
			throw new DatabaseException("Could not initialize the database.");
		}
	}
	
	public void parseUsers(NodeList usersList) throws DatabaseException
	{
		for(int i = 0; i < usersList.getLength(); i++)
		{
			Element user = (Element) usersList.item(i);
			
			String username = ((Element) user.getElementsByTagName("username").item(0)).getTextContent();
			String password = ((Element) user.getElementsByTagName("password").item(0)).getTextContent();
			String firstname = ((Element) user.getElementsByTagName("firstname").item(0)).getTextContent();
			String lastname = ((Element) user.getElementsByTagName("lastname").item(0)).getTextContent();
			String email = ((Element) user.getElementsByTagName("email").item(0)).getTextContent();
			int indexedrecords = Integer.parseInt(((Element) user.getElementsByTagName("indexedrecords").item(0)).getTextContent());
			int current_batch = -1;
			
			User u = new User(firstname, lastname, username, password, email, indexedrecords, current_batch);
			importUser(u);
		}
		
	}
	
	public int importUser(User u) throws DatabaseException
	{
		db.startTransaction();
		int uID = db.getU().insert(u);
		db.endTransaction(uID != -1);
		
		return uID;
	}
	
	public void parseProjects(Document doc) throws DatabaseException
	{
		NodeList projectsList = doc.getElementsByTagName("project");
		
		for(int i = 0; i < projectsList.getLength(); i++)
		{
			Element project = (Element) projectsList.item(i);
			
			String title = ((Element) project.getElementsByTagName("title").item(0)).getTextContent();
			int recordsperimage = Integer.parseInt(((Element) project.getElementsByTagName("recordsperimage").item(0)).getTextContent());
			int firstycoord = Integer.parseInt(((Element) project.getElementsByTagName("firstycoord").item(0)).getTextContent());
			int recordheight = Integer.parseInt(((Element) project.getElementsByTagName("recordheight").item(0)).getTextContent());
			
			Project p = new Project(title, recordsperimage, firstycoord, recordheight);
			int projectID = importProject(p);
			
			if (projectID != -1)
			{
				parseFields(projectID, project.getElementsByTagName("field"));
				parseImages(projectID, project.getElementsByTagName("image"));
			}
		}
		
	}
	
	public void parseFields(int projectID, NodeList fieldsList) throws DatabaseException
	{		
		for(int i = 0; i < fieldsList.getLength(); i++)
		{
			Element field = (Element) fieldsList.item(i);
			
			String knowndata= null;
			String title = ((Element) field.getElementsByTagName("title").item(0)).getTextContent();
			String helphtml = ((Element) field.getElementsByTagName("helphtml").item(0)).getTextContent();
			NodeList know = field.getElementsByTagName("knowndata");
			if(know.getLength() != 0)
			{
				knowndata = ((Element) know.item(0)).getTextContent();
			}
			int xcoord = Integer.parseInt(((Element) field.getElementsByTagName("xcoord").item(0)).getTextContent());
			int width = Integer.parseInt(((Element) field.getElementsByTagName("width").item(0)).getTextContent());
			int position = i;
			
			Field f = new Field(title, helphtml, knowndata, xcoord, width, position, projectID);
			importField(f);
		}
		
	}
	
	public void parseImages(int projectID, NodeList imagesList) throws DatabaseException
	{		
		for(int i = 0; i < imagesList.getLength(); i++)
		{
			Element image = (Element) imagesList.item(i);
			
			String filename = ((Element) image.getElementsByTagName("file").item(0)).getTextContent();
			boolean isRecorded = false;
			int currentIndexer = -1;
			Batch b = new Batch(filename, projectID, currentIndexer, isRecorded);
			int batchID = importImage(b);
			
			if(batchID != -1)
			{
				NodeList records = image.getElementsByTagName("record");
				if(records.getLength() != 0)
				{
					//If there are records then there are values and this was already recorded
					db.startTransaction();
					b.setRecorded(true);
					db.getB().update(b);
					db.endTransaction(true);
					parseRecords(projectID, batchID, records);
				}
			}
			
		}
		
	}
	
	public void parseRecords(int projectID, int batchID, NodeList recordsList) throws DatabaseException
	{		
		for(int i = 0; i < recordsList.getLength(); i++)
		{
			Element record = (Element) recordsList.item(i);
			
			int record_number = i+1;
			Record r = new Record(batchID, record_number);
			int recordID = importRecord(r);

			if (recordID != -1)
			{
				parseValues(projectID, recordID, record.getElementsByTagName("value"));
			}
		}
	}
	
	public void parseValues(int projectID, int recordID, NodeList valuesList) throws DatabaseException
	{		
		db.startTransaction();
		List<Field> fields = db.getF().getFromProjectID(projectID);
		db.endTransaction(true);
		for(int i = 0; i < valuesList.getLength(); i++)
		{
			Element value = (Element) valuesList.item(i);
			
			String valueName =  value.getTextContent();
			int fieldID = -2;
			for(Field field : fields)
			{
				if(field.getPosition() == i)
				{
					fieldID = field.getId();
				}
			}
			Value v = new Value(valueName.toUpperCase(), recordID, fieldID);
			importValue(v);
		}
	}
	
	public int importProject(Project p) throws DatabaseException
	{
		db.startTransaction();
		int pID = db.getP().insert(p);
		db.endTransaction(pID != -1);
		
		return pID;
	}
	
	public int importImage(Batch b) throws DatabaseException
	{
		db.startTransaction();
		int bID = db.getB().insert(b);
		db.endTransaction(bID != -1);
		
		return bID;
	}
	
	public int importField(Field f) throws DatabaseException
	{
		db.startTransaction();
		int fID = db.getF().insert(f);
		db.endTransaction(fID != -1);
		
		return fID;
	}
	
	public int importRecord(Record r) throws DatabaseException
	{
		db.startTransaction();
		int rID = db.getR().insert(r);
		db.endTransaction(rID != -1);
		
		return rID;
	}
	
	public int importValue(Value v) throws DatabaseException
	{
		db.startTransaction();
		int vID = db.getV().insert(v);
		db.endTransaction(vID != -1);
		
		return vID;
	}

	/**
	 * @return the db
	 */
	public Database getDb() {
		return db;
	}

	/**
	 * @param db the db to set
	 */
	public void setDb(Database db) {
		this.db = db;
	}
	
	public static void main(String[] args) throws ServerException, DatabaseException, ParserConfigurationException, SAXException, IOException {
		try{
			if(args.length > 0)
			{
				String xmlFileName = args[0];
				Importer importation = new Importer();
				importation.importData(xmlFileName);
			}
			else
			{
				throw new ServerException("No XML filename found");
			}
		}
		catch (NumberFormatException e)
		{
			throw new ServerException("Not a valid port number.");
		}
	}
	
	public void clearDB() throws DatabaseException, IOException
	{
		File emptydb = new File("database" + File.separator + "empty" + File.separator + "empty.sqlite");
		File currentdb = new File("database" + File.separator + "record-indexer.sqlite");
		//	Overwrite my existing *.sqlite database with an empty one.  Now, my
		//	database is completely empty and ready to load with data.
		FileUtils.copyFile(emptydb, currentdb);
	}
}
