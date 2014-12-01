package client;

import java.io.IOException;

import org.junit.*;

import data_importer.Importer;
import server.Server;
import server.database.Database;
import server.database.DatabaseException;
import shared.communication.DownloadBatch_Params;
import shared.communication.DownloadBatch_Result;
import shared.communication.GetFields_Params;
import shared.communication.GetFields_Result;
import shared.communication.GetProjects_Result;
import shared.communication.GetSampleImage_Params;
import shared.communication.GetSampleImage_Result;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import shared.model.User;
import static org.junit.Assert.*;

public class ClientUnitTests {
	
	@BeforeClass
	public static void oneTimeSetup(){
		new Server().run();
	}
	
	@Before
	public void setup() throws DatabaseException, IOException {
		Importer i = new Importer();
		//Clear the database
		i.clearDB();
	}
	
	
	@After
	public void teardown() {
	}
	
	@Test
	public void testValidateUser() throws ClientException, DatabaseException {
		Database db = new Database();
		Database.init();
		Client_Communicator cc = new Client_Communicator("localhost" , "8080");	
		
		String[] array = {"test1", "test1"};
		String validateUserString = validateUser(cc, array);
		assertEquals("FALSE\n", validateUserString);
		db.startTransaction();
		db.getU().insert(new User("Danny", "North", "test1", 
				"test1", "dnorth2@hotmail.com", 0, -1));
		db.endTransaction(true);
		validateUserString = validateUser(cc, array);
		assertEquals("Danny\nNorth\n0\n", validateUserString);
	}
	
	@Test
	public void testGetProjects() throws ClientException, DatabaseException {
		Database db = new Database();
		Database.init();
		Client_Communicator cc = new Client_Communicator("localhost" , "8080");	
		
		String[] array = {"test1", "test1"};
		String validateUserString = validateUser(cc, array);
		assertEquals("FALSE\n", validateUserString);
		db.startTransaction();
		db.getU().insert(new User("Danny", "North", "test1", 
				"test1", "dnorth2@hotmail.com", 0, -1));
		db.endTransaction(true);
		validateUserString = validateUser(cc, array);
		assertEquals("Danny\nNorth\n0\n", validateUserString);
		db.startTransaction();
		int id1 = db.getP().insert(new Project("League of Legends", 8, 10, 100));
		int id2 = db.getP().insert(new Project("Dota 2", 5, 12, 10));
		db.endTransaction(true);
		GetProjects_Result proResult = cc.Get_Projects();
		assertEquals(id1 + "\nLeague of Legends\n" + id2  + "\nDota 2\n", proResult.toString());
	}
	
	@Test
	public void testGetSampleImage() throws ClientException, DatabaseException {
		Database db = new Database();
		Database.init();
		Client_Communicator cc = new Client_Communicator("localhost" , "8080");	
		
		String[] array = {"test1", "test1"};
		String validateUserString = validateUser(cc, array);
		assertEquals("FALSE\n", validateUserString);
		db.startTransaction();
		db.getU().insert(new User("Danny", "North", "test1", 
				"test1", "dnorth2@hotmail.com", 0, -1));
		db.endTransaction(true);
		validateUserString = validateUser(cc, array);
		assertEquals("Danny\nNorth\n0\n", validateUserString);
		db.startTransaction();
		int id1 = db.getP().insert(new Project("League of Legends", 8, 10, 100));
		db.getP().insert(new Project("Dota 2", 5, 12, 10));
		db.getB().insert(new Batch("images/lol.jpg", id1, -1, false));
		db.endTransaction(true);
		GetSampleImage_Result samResult= cc.Sample_Image(new GetSampleImage_Params(id1));
		assertEquals("http://localhost:8080/images/lol.jpg\n", samResult.toURLString("localhost", "8080"));
		samResult= cc.Sample_Image(new GetSampleImage_Params(2933));
		assertEquals(null, samResult);
	}
	
	@Test
	public void testDownloadBatch() throws ClientException, DatabaseException {
		Database db = new Database();
		Database.init();
		Client_Communicator cc = new Client_Communicator("localhost" , "8080");	
		
		String[] array = {"test1", "test1"};
		String validateUserString = validateUser(cc, array);
		assertEquals("FALSE\n", validateUserString);
		db.startTransaction();
		db.getU().insert(new User("Danny", "North", "test1", 
				"test1", "dnorth2@hotmail.com", 0, -1));
		db.endTransaction(true);
		validateUserString = validateUser(cc, array);
		
		assertEquals("Danny\nNorth\n0\n", validateUserString);
		
		db.startTransaction();
		int id1 = db.getP().insert(new Project("League of Legends", 8, 10, 100));
		int id2 = db.getB().insert(new Batch("images/lol.jpg", id1, -1, false));
		int id3 = db.getF().insert(new Field("Champ", "help.html", "known.txt", 2, 5, 1, id1));
		int id4 = db.getF().insert(new Field("Damage", "helpDam.html", "knownDam.txt", 3, 6, 2, id1));
		db.endTransaction(true);
		DownloadBatch_Result batchResult= cc.Download_Batch(new DownloadBatch_Params("test1", "test1", id1));
		assertEquals(id2 + "\n" + id1 + "\nhttp://localhost:8080/images/lol.jpg\n10\n100\n8\n2\n" + id3 +
				  "\n1\nChamp\nhttp://localhost:8080/help.html\n2\n5\nhttp://localhost:8080/known.txt\n" + id4 + "\n2\nDamage\nhttp://localhost:8080/helpDam.html\n3\n6\nhttp://localhost:8080/knownDam.txt\n", batchResult.toURLString("localhost", "8080"));
	}
	
	@Test
	public void testGetFields() throws ClientException, DatabaseException {
		Database db = new Database();
		Database.init();
		Client_Communicator cc = new Client_Communicator("localhost" , "8080");	
		
		String[] array = {"test1", "test1"};
		String validateUserString = validateUser(cc, array);
		assertEquals("FALSE\n", validateUserString);
		db.startTransaction();
		db.getU().insert(new User("Danny", "North", "test1", 
				"test1", "dnorth2@hotmail.com", 0, -1));
		db.endTransaction(true);
		validateUserString = validateUser(cc, array);
		
		assertEquals("Danny\nNorth\n0\n", validateUserString);
		
		db.startTransaction();
		int id1 = db.getP().insert(new Project("League of Legends", 8, 10, 100));
		db.getB().insert(new Batch("images/lol.jpg", id1, -1, false));
		int id3 = db.getF().insert(new Field("Champ", "help.html", "known.txt", 2, 5, 1, id1));
		int id4 = db.getF().insert(new Field("Damage", "helpDam.html", "knownDam.txt", 3, 6, 2, id1));
		db.endTransaction(true);
		GetFields_Result fieldsResult= cc.Get_Fields(new GetFields_Params("test1", "test1", id1));
		assertEquals(id1 + "\n" + id3 + "\nChamp\n" + id1 + "\n" + id4 + "\nDamage\n" , fieldsResult.toString());
	}			
	
public String validateUser(Client_Communicator cc, String[] p) {
		
		ValidateUser_Params params = new ValidateUser_Params(p[0] , p[1]);
		ValidateUser_Result result;
		try {
			result = cc.Validate_User(params);
			if (result != null)
			{
				return (result.toString());
			}
			else
			{
				return ("FALSE\n");
			}
		} catch (ClientException e) {
			return ("FAILED\n");

		}
	}


	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"client.ClientUnitTests"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}

