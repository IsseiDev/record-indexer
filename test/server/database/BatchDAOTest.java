package server.database;

import static org.junit.Assert.*;

import org.junit.*;

import shared.communication.DownloadBatch_Result;
import shared.model.*;


public class BatchDAOTest {

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
	private BatchDAO batch;

	@Before
	public void setUp() throws Exception {

		// Delete all contacts from the database	
		db = new Database();		
		db.startTransaction();
				
		db.getB().deleteAll();
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		batch = db.getB();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		batch = null;
	}

	@Test
	public void testInsert() throws DatabaseException {
		
		Batch numberOne = new Batch("text.txt", 15, 10, true);
		Batch numberTwo = new Batch("image.txt", 27, 8, false);

		
		int id1 = batch.insert(numberOne);
		int id2 = batch.insert(numberTwo);
		
		Batch oneTest = batch.get(id1);
		Batch twoTest = batch.get(id2);
		
		boolean foundOne = false;
		boolean foundTwo = false;
		
		foundOne = areEqual(numberOne, oneTest, false);
		foundTwo = areEqual(numberTwo, twoTest, false);
		
		assertTrue(foundOne && foundTwo);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		Batch numberOne = new Batch("text.txt", 15, 10, true);
		Batch numberTwo = new Batch("image.txt", 27, 8, false);

		
		int id1 = batch.insert(numberOne);
		int id2 = batch.insert(numberTwo);
		
		Batch oneObj = batch.get(id1);
		Batch twoObj = batch.get(id2);
		
		oneObj.setFilename("TheBigOne.txt");
		oneObj.setRecorded(false);
		oneObj.setProject_id(100);
		oneObj.setCurrentIndexer(-1);

		twoObj.setFilename("bigBoyBooty.png");
		twoObj.setRecorded(true);
		twoObj.setProject_id(59);
		twoObj.setCurrentIndexer(12);
		
		batch.update(oneObj);
		batch.update(twoObj);
		
		boolean foundOne = false;
		boolean foundTwo = false;
		
		Batch oneTest = batch.get(id1);
		Batch twoTest = batch.get(id2);
		
		foundOne = areEqual(oneObj, oneTest, false);
		foundTwo = areEqual(twoObj, twoTest, false);
		
		assertTrue(foundOne && foundTwo);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		Batch numberOne = new Batch("text.txt", 15, 10, true);
		Batch numberTwo = new Batch("image.txt", 27, 8, false);

		int id1 = batch.insert(numberOne);
		int id2 = batch.insert(numberTwo);
		
		batch.delete(numberOne);
		batch.delete(numberTwo);
		
		assertEquals(null, batch.get(id1));
		assertEquals(null, batch.get(id2));
	}
	
	@Test
	public void testGetSampleImage() throws DatabaseException {
		
		Batch numberOne = new Batch("text.txt", 15, 10, true);
		@SuppressWarnings("unused")
		Batch numberTwo = new Batch("image.txt", 27, 8, false);

		batch.insert(numberOne);
		
		Batch testOne = batch.getSampleImage(15);
		Batch testTwo = batch.getSampleImage(25);
		boolean foundOne = areEqual(numberOne, testOne, false);
		assertTrue(foundOne);
		assertEquals(null, testTwo);
	}
	
	@Test
	public void testDownloadBatch() throws DatabaseException {
		
		Project lol = new Project("2014 League Championships", 8, 12, 120);
		@SuppressWarnings("unused")
		Batch numberTwo = new Batch("image.txt", 27, -1, true);

		int pID = db.getP().insert(lol);
		Batch numberOne = new Batch("text.txt", pID, -1, false);
		int bID = batch.insert(numberOne);
		
		DownloadBatch_Result testing = new DownloadBatch_Result(bID, pID, "text.txt", 12, 120, 8);
		DownloadBatch_Result testOne = batch.downloadBatch(pID);
		DownloadBatch_Result testTwo = batch.downloadBatch(25);
		assertEquals(testing.getBatch_id(), testOne.getBatch_id());
		assertEquals(testing.getFirst_y_coord(), testOne.getFirst_y_coord());
		assertEquals(testing.getImage_url(), testOne.getImage_url());
		assertEquals(testing.getProject_id(), testOne.getProject_id());
		assertEquals(null, testTwo);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		Batch numberOne = null;
		batch.insert(numberOne);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		Batch numberOne = new Batch(null, -1, -1, false);
		
		batch.update(numberOne);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		Batch numberOne = new Batch(null, -1, -1, false);

		batch.delete(numberOne);
	}
	
	private boolean areEqual(Batch a, Batch b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getFilename(), b.getFilename()) &&
				safeEquals(a.getProject_id(), b.getProject_id()) &&
				safeEquals(a.getCurrentIndexer(), b.getCurrentIndexer()) &&
				safeEquals(a.isRecorded(), b.isRecorded()));
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
