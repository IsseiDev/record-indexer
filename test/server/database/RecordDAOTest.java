package server.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import shared.model.*;


public class RecordDAOTest {

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
	private RecordDAO record;

	@Before
	public void setUp() throws Exception {

		// Delete all contacts from the database	
		db = new Database();		
		db.startTransaction();
				
		db.getR().deleteAll();
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		record = db.getR();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		record = null;
	}

	@Test
	public void testInsert() throws DatabaseException {
		
		Record record1 = new Record(5, 1);
		Record record2 = new Record(10, 2);
		db.endTransaction(false);
		db.startTransaction();
		int id1 = record.insert(record1);
		int id2 = record.insert(record2);
		Record test1 = record.get(id1);
		Record test2 = record.get(id2);
		db.endTransaction(false);
		boolean foundTest1 = false;
		boolean foundTest2 = false;
		
		foundTest1 = areEqual(record1, test1, false);
		foundTest2 = areEqual(record2, test2, false);
		
		assertTrue(foundTest1 && foundTest2);
	}
	
	@Test
	public void testMultipleInserts() {
		try{
		List<Record> recordList = new ArrayList<Record>();
		List<Integer> recordIDs = new ArrayList<Integer>();		
		db.endTransaction(false);
		db.startTransaction();
		recordList = db.getR().getAllFromBatch(5);
		db.endTransaction(false);
		if(recordList.isEmpty())
		{
			for(int i=0; i < 3; i++)
			{
				int newRecID = db.getR().insert(new Record(5, i));
				recordIDs.add(newRecID);
			}
		}
		Record record1 = new Record(5, 1);
		Record record2 = new Record(10, 2);
		int id1 = record.insert(record1);
		int id2 = record.insert(record2);
		db.startTransaction();
		Record test1 = record.get(id1);
		Record test2 = record.get(id2);
		
		boolean foundTest1 = false;
		boolean foundTest2 = false;
		
		foundTest1 = areEqual(record1, test1, false);
		foundTest2 = areEqual(record2, test2, false);
		
		assertTrue(foundTest1 && foundTest2);
		}
		catch (Exception e)
		{

		}
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Record record1 = new Record(5, 1);
		Record record2 = new Record(10, 2);
		
		int id1 = record.insert(record1);
		int id2 = record.insert(record2);
		
		Record rec1Obj = record.get(id1);
		Record rec2Obj = record.get(id2);
		rec1Obj.setBatch_id(100);
		rec1Obj.setRecord_number(3);
		
		rec2Obj.setBatch_id(120);
		rec2Obj.setRecord_number(7);
		
		record.update(rec1Obj);
		record.update(rec2Obj);
		
		boolean foundDan = false;
		boolean foundTrav = false;
		
		Record rec1Test = record.get(id1);
		Record rec2Test = record.get(id2);
		db.endTransaction(false);

		foundDan = areEqual(rec1Obj, rec1Test, false);
		foundTrav = areEqual(rec2Obj, rec2Test, false);
		
		assertTrue(foundDan && foundTrav);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		Record record1 = new Record(5, 1);
		Record record2 = new Record(10, 2);
		int id1 = record.insert(record1);
		int id2 = record.insert(record2);
		record.delete(record1);
		record.delete(record2);
		
		assertEquals(null, record.get(id1));
		assertEquals(null, record.get(id2));
		db.endTransaction(false);
	}
	
	@Test
	public void testGetAllFromBatch() throws DatabaseException {
		db.endTransaction(false);
		db.startTransaction();
		Record record1 = new Record(5, 1);
		Record record2 = new Record(5, 3);
		Record record3 = new Record(10, 2);
		record.insert(record1);
		record.insert(record2);
		record.insert(record3);
		List<Record> result = record.getAllFromBatch(5);
		
		assertEquals(2, result.size());
		int i=0;
		boolean found1 = false;
		boolean found2 = false;
		for(Record r : result)
		{
			if(i == 0)
			{
				found1 = areEqual(record1, r, false);
			} else {
				found2 = areEqual(record2, r, false);
			}
			i++;
		}
		db.endTransaction(false);
		assertTrue(found1 && found2);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Record invalidObject = null;
		record.insert(invalidObject);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Record invalidObject = new Record(~29, 4*0);
		
		record.update(invalidObject);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Record invalidObject = new Record(~29, 4*0);

		record.delete(invalidObject);
	}
	
	private boolean areEqual(Record a, Record b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getBatch_id(), b.getBatch_id()) &&
				safeEquals(a.getRecord_number(), b.getRecord_number()));
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
