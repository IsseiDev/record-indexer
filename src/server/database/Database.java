package server.database;

import java.io.File;
import java.sql.*;

public class Database {
	UserDAO u;
	BatchDAO b;
	FieldDAO f;
	ProjectDAO p;
	RecordDAO r;
	ValueDAO v;
	Connection connection;

	public Database(){
		u = new UserDAO(this);
		b = new BatchDAO(this);
		f = new FieldDAO(this);
		p = new ProjectDAO(this);
		r = new RecordDAO(this);
		v = new ValueDAO(this);
		connection = null;
	}
	
	public static boolean init() throws DatabaseException{
		//load driver for SQL
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
			//Database driver loaded, returning true
			return true;
		}
		catch(ClassNotFoundException e){
			throw new DatabaseException(e.getMessage(), e);//Could not load database driver, returning false
		}
	}
	
	//Starts a transaction. Returns TRUE if the transaction started, FALSE if there was an error.
	public boolean startTransaction(){
		String dbName = "database" + File.separator + "record-indexer.sqlite";
		String connectionURL = "jdbc:sqlite:" + dbName;
		
		connection = null;
		
		try{
			//Open a database connection
			connection = DriverManager.getConnection(connectionURL);
			
			// Start a transaction   (Grouping all the statements so it's all or nothing)
			connection.setAutoCommit(false);
			return true;
		}
		catch (SQLException e){
			System.out.println("Could not start Transaction."  + e);
			return false;
		}
	}
	
	public boolean endTransaction(boolean commit) throws DatabaseException {
		if (connection != null) {		
			try {
				if (commit) {
					connection.commit();
				}
				else {
					connection.rollback();
				}
			}
			catch (SQLException e) {
				System.out.println("Could not end transaction: " + e);
				e.printStackTrace();
				return false;
			}
			finally {
				safeClose(connection);
				connection = null;
			}
			return true;
		}
		return false;
	}
	
	//Starts a transaction. Returns TRUE if the transaction started, FALSE if there was an error.
		public boolean startTransactionAuto(){
			String dbName = "database" + File.separator + "record-indexer.sqlite";
			String connectionURL = "jdbc:sqlite:" + dbName;
			
			connection = null;
			
			try{
				//Open a database connection
				connection = DriverManager.getConnection(connectionURL);
				
				// Start a transaction   (Grouping all the statements so it's all or nothing)
				connection.setAutoCommit(true);
				return true;
			}
			catch (SQLException e){
				System.out.println("Could not start Transaction."  + e);
				return false;
			}
		}
		
		public boolean endTransaction() throws DatabaseException {
			if (connection != null) {		

				safeClose(connection);
				connection = null;
				return true;
			}
			return false;
		}
	
	public void safeClose(Connection conn) throws DatabaseException {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				throw new DatabaseException("Connection Close not cool: " + e);
			}
		}
	}
	
	public void safeClose(Statement stmt) throws DatabaseException {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				throw new DatabaseException("Statement Close not cool: " + e);
			}
		}
	}
	
	public void safeClose(PreparedStatement stmt) throws DatabaseException {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				throw new DatabaseException("Prepared Statement Close not cool: " + e);
			}
		}
	}
	
	public void safeClose(ResultSet rs) throws DatabaseException {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				throw new DatabaseException("Result set Close not cool: " + e);
			}
		}
	}
	
	
	/**
	 * @return the u
	 */
	public UserDAO getU() {
		return u;
	}

	/**
	 * @param u the u to set
	 */
	public void setU(UserDAO u) {
		this.u = u;
	}

	/**
	 * @return the b
	 */
	public BatchDAO getB() {
		return b;
	}

	/**
	 * @param b the b to set
	 */
	public void setB(BatchDAO b) {
		this.b = b;
	}

	/**
	 * @return the f
	 */
	public FieldDAO getF() {
		return f;
	}

	/**
	 * @param f the f to set
	 */
	public void setF(FieldDAO f) {
		this.f = f;
	}

	/**
	 * @return the p
	 */
	public ProjectDAO getP() {
		return p;
	}

	/**
	 * @param p the p to set
	 */
	public void setP(ProjectDAO p) {
		this.p = p;
	}

	/**
	 * @return the r
	 */
	public RecordDAO getR() {
		return r;
	}

	/**
	 * @param r the r to set
	 */
	public void setR(RecordDAO r) {
		this.r = r;
	}

	/**
	 * @return the v
	 */
	public ValueDAO getV() {
		return v;
	}

	/**
	 * @param v the v to set
	 */
	public void setV(ValueDAO v) {
		this.v = v;
	}

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
