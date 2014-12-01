package server;

import org.junit.* ;

import server.database.Database;
import server.database.DatabaseException;
import data_importer.Importer;
import static org.junit.Assert.* ;

public class ServerUnitTests {
	
	Importer dataImporter = new Importer(); 
	
	@Before
	public void setup() throws DatabaseException {
		Database.init();
	}
	
	@After
	public void teardown() throws DatabaseException {
	}
	
	@Test
	public void test_1(){
		//This is just to make sure the JUnit test works. The real tests are in the seperate server.database package
		assertEquals(0, 0);
	}

	public static void main(String[] args) {
		
		String[] testClasses = new String[] {
				"server.ServerUnitTests",
				"server.database.BatchDAOTest",
				"server.database.FieldDAOTest",
				"server.database.ProjectDAOTest",
				"server.database.RecordDAOTest",
				"server.database.UserDAOTest",
				"server.database.ValueDAOTest"

		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

