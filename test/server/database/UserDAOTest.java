package server.database;

import static org.junit.Assert.*;

import org.junit.*;

import shared.model.*;


public class UserDAOTest {

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
	private UserDAO user;

	@Before
	public void setUp() throws Exception {

		// Delete all contacts from the database	
		db = new Database();		
		db.startTransaction();
				
		db.getU().deleteAll();
		
		db.endTransaction(true);

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		user = db.getU();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		
		db = null;
		user = null;
	}

	@Test
	public void testInsert() throws DatabaseException {
		
		User danny = new User("Danny", "North", "dnorth2", 
				"traviscool", "dnorth2@hotmail.com", 0, -1);
		User travis = new User("Travis", "Grossen", "travismg", 
				"daniscool", "travis.grossen@parentlink.net", 0, -1);
		
		int id1 = user.insert(danny);
		int id2 = user.insert(travis);
		
		User danTest = user.get(id1);
		User travTest = user.get(id2);
		
		boolean foundDan = false;
		boolean foundTrav = false;
		
		foundDan = areEqual(danny, danTest, false);
		foundTrav = areEqual(travis, travTest, false);
		
		assertTrue(foundDan && foundTrav);
	}

	@Test
	public void testUpdate() throws DatabaseException {
		
		User danny = new User("Danny", "North", "dnorth2", 
				"traviscool", "dnorth2@hotmail.com", 0, -1);
		User travis = new User("Travis", "Grossen", "travismg", 
				"daniscool", "travis.grossen@parentlink.net", 0, -1);
		
		int id1 = user.insert(danny);
		int id2 = user.insert(travis);
		
		User danObj = user.get(id1);
		User travObj = user.get(id2);
		
		danObj.setFirstname("Daniel");
		danObj.setLastname("Northicus");
		danObj.setCurrent_batch(24);
		danObj.setEmail("danneh@hotmail.com");
		danObj.setIndexedrecords(27);
		danObj.setPassword("thisisasecret");
		danObj.setUsername("someoneelse");	
		
		travObj.setFirstname("Treevus");
		travObj.setLastname("Halloween");
		travObj.setCurrent_batch(21);
		travObj.setEmail("danneh2@hotmail.com");
		travObj.setIndexedrecords(27);
		travObj.setPassword("thisisnortasecret");
		travObj.setUsername("someonedifferent");
		
		user.update(danObj);
		user.update(travObj);
		
		boolean foundDan = false;
		boolean foundTrav = false;
		
		User danTest = user.get(id1);
		User travTest = user.get(id2);
		
		foundDan = areEqual(danObj, danTest, false);
		foundTrav = areEqual(travObj, travTest, false);
		
		assertTrue(foundDan && foundTrav);
	}

	@Test
	public void testDelete() throws DatabaseException {
		
		User danny = new User("Danny", "North", "dnorth2", 
				"traviscool", "dnorth2@hotmail.com", 0, -1);
		User travis = new User("Travis", "Grossen", "travismg", 
				"daniscool", "travis.grossen@parentlink.net", 0, -1);
		
		int id1 = user.insert(danny);
		int id2 = user.insert(travis);
		
		user.delete(danny);
		user.delete(travis);
		
		assertEquals(null, user.get(id1));
		assertEquals(null, user.get(id2));
	}
	
	@Test
	public void testValidateUser() throws DatabaseException {
		
		User danny = new User("Danny", "North", "dnorth2", 
				"traviscool", "dnorth2@hotmail.com", 0, -1);
		
		user.insert(danny);
		
		User danReturn = user.validateUser("dnorth2", "traviscool");
		User travReturn = user.validateUser("travisg", "yolo");
		boolean foundDan = areEqual(danny, danReturn, false);
		assertTrue(foundDan);
		assertEquals(null, travReturn);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		User dannyInvalid = new User(null, null, null, 
				null, null, 0, -1);
		user.insert(dannyInvalid);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		User dannyInvalid = new User(null, null, null, 
				null, null, 0, -1);		
		user.update(dannyInvalid);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		User dannyInvalid = new User(null, null, null, 
				null, null, 0, -1);
		user.delete(dannyInvalid);
	}
	
	private boolean areEqual(User a, User b, boolean compareIDs) {
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (safeEquals(a.getFirstname(), b.getFirstname()) &&
				safeEquals(a.getLastname(), b.getLastname()) &&
				safeEquals(a.getEmail(), b.getEmail()) &&
				safeEquals(a.getIndexedrecords(), b.getIndexedrecords()) &&
				safeEquals(a.getUsername(), b.getUsername()) &&
				safeEquals(a.getPassword(), b.getPassword()));
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
