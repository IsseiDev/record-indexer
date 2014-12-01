package server.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import shared.model.*;


public class ProjectDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Load database driver	
		Database.init();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
		
	private Database db;
	private ProjectDAO project;

	@Before
	public void setUp() throws Exception {

		// Delete all contacts from the database	
		db = new Database();		
		db.startTransaction();
				
		db.getP().deleteAll();
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		project = db.getP();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		project = null;
	}

	@Test
	public void testInsert() throws DatabaseException {
		
		Project lol = new Project("2014 League Championships", 8, 12, 120);
		Project dota = new Project("2014 Dota Championships", 10, 10, 130);

		
		int id1 = project.insert(lol);
		int id2 = project.insert(dota);
		
		Project lolTest = project.get(id1);
		Project dotaTest = project.get(id2);
		
		boolean foundDan = false;
		boolean foundTrav = false;
		
		foundDan = areEqual(lol, lolTest, false);
		foundTrav = areEqual(dota, dotaTest, false);

		assertTrue(foundDan && foundTrav);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Project lol = new Project("2014 League Championships", 8, 12, 120);
		Project dota = new Project("2014 Dota Championships", 10, 10, 130);

		
		int id1 = project.insert(lol);
		int id2 = project.insert(dota);
		
		Project lolObj = project.get(id1);
		Project dotaObj = project.get(id2);
		
		lolObj.setTitle("Champss 4 LoL");
		lolObj.setRecordsperimage(15);
		lolObj.setFirstycoord(4);
		lolObj.setRecordheight(100);
		
		dotaObj.setTitle("Champss 4 LoL");
		dotaObj.setRecordsperimage(15);
		dotaObj.setFirstycoord(4);
		dotaObj.setRecordheight(100);
		
		project.update(lolObj);
		project.update(dotaObj);
		
		boolean foundLol = false;
		boolean foundDota = false;
		
		Project lolTest = project.get(id1);
		Project dotaTest = project.get(id2);
		
		foundLol = areEqual(lolObj, lolTest, false);
		foundDota = areEqual(dotaObj, dotaTest, false);
		
		assertTrue(foundLol && foundDota);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		Project lol = new Project("2014 League Championships", 8, 12, 120);
		Project dota = new Project("2014 Dota Championships", 10, 10, 130);

		
		int id1 = project.insert(lol);
		int id2 = project.insert(dota);
		
		project.delete(lol);
		project.delete(dota);
		
		assertEquals(null, project.get(id1));
		assertEquals(null, project.get(id2));
	}
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		List<Project> projects = new ArrayList<Project>();
		
		projects = db.getP().getAll();
		
		assertEquals(0, projects.size());
		
		Project lol = new Project("2014 League Championships", 8, 12, 120);
		Project dota = new Project("2014 Dota Championships", 10, 10, 130);

		
		project.insert(lol);
		project.insert(dota);
		
		projects = db.getP().getAll();

		assertEquals(2, projects.size());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Project invalidObject = new Project(null, -1, -1, -1);
		project.insert(invalidObject);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Project invalidObject = new Project(null, -1, -1, -1);
		
		project.update(invalidObject);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Project invalidObject = new Project(null, -1, -1, -1);

		project.delete(invalidObject);
	}
	
	private boolean areEqual(Project a, Project b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getTitle(), b.getTitle()) &&
				safeEquals(a.getRecordsperimage(), b.getRecordsperimage()) &&
				safeEquals(a.getFirstycoord(), b.getFirstycoord()) &&
				safeEquals(a.getRecordheight(), b.getRecordheight()));
		}
	
	private boolean safeEquals(Object a, Object b) {
		if (a == null || b == null) {
			return (a == null && b == null);
		}
		else {
			return a.equals(b);
		}
	}

}
