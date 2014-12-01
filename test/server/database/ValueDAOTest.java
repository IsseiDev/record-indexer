package server.database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;

import shared.model.*;


public class ValueDAOTest {

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
	private ValueDAO value;

	@Before
	public void setUp() throws Exception {

		// Delete all contacts from the database	
		db = new Database();		
		db.startTransaction();
				
		db.getV().deleteAll();
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		value = db.getV();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		value = null;
	}

	@Test
	public void testInsert() throws DatabaseException {
		
		Value numberOne = new Value("NORTH", 27, 32);
		Value numberTwo = new Value("27", 27, 33);

		int id1 = value.insert(numberOne);
		int id2 = value.insert(numberTwo);
		
		Value oneTest = value.get(id1);
		Value twoTest = value.get(id2);
		
		boolean foundOne = false;
		boolean foundTwo = false;
		
		foundOne = areEqual(numberOne, oneTest, false);
		foundTwo = areEqual(numberTwo, twoTest, false);
		
		assertTrue(foundOne && foundTwo);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Value numberOne = new Value("NORTH", 27, 32);
		Value numberTwo = new Value("27", 27, 33);

		int id1 = value.insert(numberOne);
		int id2 = value.insert(numberTwo);
		
		Value oneObj = value.get(id1);
		Value twoObj = value.get(id2);
		
		oneObj.setValue("RIVER");
		oneObj.setRecord_id(30);
		oneObj.setField_id(31);

		twoObj.setValue("HOUSE");
		twoObj.setRecord_id(31);
		twoObj.setField_id(32);
		
		value.update(oneObj);
		value.update(twoObj);
		
		boolean foundOne = false;
		boolean foundTwo = false;
		
		Value oneTest = value.get(id1);
		Value twoTest = value.get(id2);
		
		foundOne = areEqual(oneObj, oneTest, false);
		foundTwo = areEqual(twoObj, twoTest, false);
		
		assertTrue(foundOne && foundTwo);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		Value numberOne = new Value("NORTH", 27, 32);
		Value numberTwo = new Value("27", 27, 33);

		int id1 = value.insert(numberOne);
		int id2 = value.insert(numberTwo);
		
		value.delete(numberOne);
		value.delete(numberTwo);
		
		assertEquals(null, value.get(id1));
		assertEquals(null, value.get(id2));
	}
	
	@Test
	public void testGetFromSearch() throws DatabaseException {
		
		Value numberOne = new Value("NORTH", 27, 32);
		Value numberTwo = new Value("27", 27, 33);

		value.insert(numberOne);
		value.insert(numberTwo);
		
		Value testOne = value.getFromSearch(32, "NORTH").iterator().next();
		List<Value> testTwo = value.getFromSearch(33, "NORTH");
		boolean foundOne = areEqual(numberOne, testOne, false);
		assertTrue(foundOne);
		assertEquals(0, testTwo.size());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Value numberOne = null;
		value.insert(numberOne);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Value numberOne = new Value(null, -1, -1);
		
		value.update(numberOne);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Value numberOne = new Value(null, -1, -1);

		value.delete(numberOne);
	}
	
	private boolean areEqual(Value a, Value b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getField_id(), b.getField_id()) &&
				safeEquals(a.getRecord_id(), b.getRecord_id()) &&
				safeEquals(a.getValue(), b.getValue()));
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
