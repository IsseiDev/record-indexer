package server.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import shared.model.*;


public class FieldDAOTest {

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
	private FieldDAO field;

	@Before
	public void setUp() throws Exception {

		// Delete all contacts from the database	
		db = new Database();		
		db.startTransaction();
				
		db.getF().deleteAll();
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		field = db.getF();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		field = null;
	}

	@Test
	public void testInsert() throws DatabaseException {
		
		Field numberOne = new Field("Gender", "help.html", "known.html", 25, 100, 1, 27);
		Field numberTwo = new Field("Age", "help.html", "known.html", 10, 50, 2, 27);

		
		int id1 = field.insert(numberOne);
		int id2 = field.insert(numberTwo);
		
		Field oneTest = field.get(id1);
		Field twoTest = field.get(id2);
		
		boolean foundOne = false;
		boolean foundTwo = false;
		
		foundOne = areEqual(numberOne, oneTest, false);
		foundTwo = areEqual(numberTwo, twoTest, false);
		
		assertTrue(foundOne && foundTwo);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Field numberOne = new Field("Gender", "help.html", "known.html", 25, 100, 1, 27);
		Field numberTwo = new Field("Age", "help.html", "known.html", 10, 50, 2, 27);

		
		int id1 = field.insert(numberOne);
		int id2 = field.insert(numberTwo);
		
		Field oneObj = field.get(id1);
		Field twoObj = field.get(id2);
		
		oneObj.setTitle("Lastname");
		oneObj.setHelphtml("HALP.html");
		oneObj.setKnowndata("uSRite.txt");
		oneObj.setXcoord(100);
		oneObj.setPosition(3);
		oneObj.setProject_id(30);

		twoObj.setTitle("Firstname");
		twoObj.setHelphtml("HEEELP.html");
		twoObj.setKnowndata("dontdoit.txt");
		twoObj.setXcoord(40);
		twoObj.setPosition(30);
		twoObj.setProject_id(300);
		
		field.update(oneObj);
		field.update(twoObj);
		
		boolean foundOne = false;
		boolean foundTwo = false;
		
		Field oneTest = field.get(id1);
		Field twoTest = field.get(id2);
		
		foundOne = areEqual(oneObj, oneTest, false);
		foundTwo = areEqual(twoObj, twoTest, false);
		
		assertTrue(foundOne && foundTwo);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		Field numberOne = new Field("Gender", "help.html", "known.html", 25, 100, 1, 27);
		Field numberTwo = new Field("Age", "help.html", "known.html", 10, 50, 2, 27);

		int id1 = field.insert(numberOne);
		int id2 = field.insert(numberTwo);
		
		field.delete(numberOne);
		field.delete(numberTwo);
		
		assertEquals(null, field.get(id1));
		assertEquals(null, field.get(id2));
	}
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		List<Field> fields = new ArrayList<Field>();
		
		fields = field.getAll();
		
		assertEquals(0, fields.size());
		
		Field numberOne = new Field("Gender", "help.html", "known.html", 25, 100, 1, 27);
		Field numberTwo = new Field("Age", "help.html", "known.html", 10, 50, 2, 27);

		field.insert(numberOne);
		field.insert(numberTwo);
		
		fields = field.getAll();

		assertEquals(2, fields.size());
	}
	
	@Test
	public void testGetFromProjectID() throws DatabaseException {
		
		Field numberOne = new Field("Gender", "help.html", "known.html", 25, 100, 1, 27);
		Field numberTwo = new Field("Age", "help.html", "known.html", 10, 50, 2, 27);

		field.insert(numberOne);
		field.insert(numberTwo);
		
		List<Field> result = field.getFromProjectID(27);
		
		assertEquals(2, result.size());
		int i=0;
		boolean found1 = false;
		boolean found2 = false;
		for(Field r : result)
		{
			if(i == 0)
			{
				found1 = areEqual(numberOne, r, false);
			} else {
				found2 = areEqual(numberTwo, r, false);
			}
			i++;
		}
		assertTrue(found1 && found2);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Field numberOne = new Field(null, null, null, -1, -1, -1, -1);
		field.insert(numberOne);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Field numberOne = new Field(null, null, null, -1, -1, -1, -1);
		
		field.update(numberOne);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Field numberOne = new Field(null, null, null, -1, -1, -1, -1);

		field.delete(numberOne);
	}
	
	private boolean areEqual(Field a, Field b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getHelphtml(), b.getHelphtml()) &&
				safeEquals(a.getKnowndata(), b.getKnowndata()) &&
				safeEquals(a.getPosition(), b.getPosition()) &&
				safeEquals(a.getTitle(), b.getTitle()) &&
				safeEquals(a.getWidth(), b.getWidth()) &&
				safeEquals(a.getXcoord(), b.getXcoord()) &&
				safeEquals(a.getProject_id(), b.getProject_id()));
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
