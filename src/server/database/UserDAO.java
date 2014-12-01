package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import shared.model.User;

public class UserDAO {
	
	Database db;

	public UserDAO(Database database) {
		db = database;
	}
	
	/**
	 * @param id the id of the User being searched by
	 * @return the User model object with the found results
	 * @throws DatabaseException 
	 */
	public User get(int id) throws DatabaseException
	{
		User use = null;
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "Select * from User where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
			
			keyRS = stmt.executeQuery();
			
			boolean hasNext = keyRS.next();
			
			if (hasNext)
			{
				String first = keyRS.getString("firstname");
				String last = keyRS.getString("lastname");
				String user = keyRS.getString("username");
				String pass = keyRS.getString("password");
				String email = keyRS.getString("email");
				int indexed = keyRS.getInt("indexedrecords");
				int current_batch = keyRS.getInt("current_batch");
			
				use = new User(first, last, user, pass, email, indexed, current_batch);
				use.setId(id);
			}
		} catch (SQLException e)
		{
			throw new DatabaseException("Could not get the user.");
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
		}
		
		return use;
	}
	
	public User validateUser(String username, String password) throws DatabaseException
	{
		User use = null;
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "Select * from User where username = ? and password = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
			keyRS = stmt.executeQuery();
			
			boolean hasResult = keyRS.next();

			if (hasResult) //ResultSet will never return null.
			{
				String first = keyRS.getString("firstname");
				String last = keyRS.getString("lastname");
				String user = keyRS.getString("username");
				String pass = keyRS.getString("password");
				String email = keyRS.getString("email");
				int indexed = keyRS.getInt("indexedrecords");
				int id = keyRS.getInt("id");
				int current_batch = keyRS.getInt("current_batch");
			
				use = new User(first, last, user, pass, email, indexed, current_batch);
				use.setId(id);
			}
		} catch (SQLException e)
		{
			throw new DatabaseException("Could not validate the User. ", e);
		}
		finally {
			db.safeClose(stmt);
		}
		
		return use;
	}

	/**
	 * @param u the User being inserted
	 * @throws DatabaseException 
	 */
	public int insert(User u) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Statement keyStmt = null;
		int id = -1;
		try {
			String query = "insert into User (firstname, lastname, username, password, email, indexedrecords, current_batch) values (?, ?, ?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, u.getFirstname());
			stmt.setString(2, u.getLastname());
			stmt.setString(3, u.getUsername());
			stmt.setString(4, u.getPassword());
			stmt.setString(5, u.getEmail());
			stmt.setInt(6, u.getIndexedrecords());
			stmt.setInt(7, u.getCurrent_batch());
			
			if(stmt.executeUpdate() == 1) {
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				id = keyRS.getInt(1);
				u.setId(id);
			}
			else {
				throw new DatabaseException("Could not update User.");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert User: " + e);
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
			db.safeClose(keyStmt);
		}
		return id;
	}
	
	/**
	 * @param u the User being updated
	 * @throws DatabaseException 
	 */
	public void update(User u) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "update User set firstname = ?, lastname = ?, username = ?, password = ?, email = ?, indexedrecords = ?, current_batch = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, u.getFirstname());
			stmt.setString(2, u.getLastname());
			stmt.setString(3, u.getUsername());
			stmt.setString(4, u.getPassword());
			stmt.setString(5, u.getEmail());
			stmt.setInt(6, u.getIndexedrecords());
			stmt.setInt(7, u.getCurrent_batch());
			stmt.setInt(8, u.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete User.");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update User: " + e);
		}
		finally {
			db.safeClose(stmt);
		}
	}
	
	/**
	 * @param query the String being searched
	 * @return the ResultSet found from the query
	 */

	
	/**
	 * @param u the User being deleted
	 * @throws DatabaseException 
	 */
	public void delete(User u) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "delete from User where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, u.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete User.");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete User: " + e);
		}
		finally {
			db.safeClose(stmt);
		}
	}
	
	/**
	 * @param u the User being deleted
	 * @throws DatabaseException 
	 */
	public void deleteAll() throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "delete from User";
			stmt = db.getConnection().prepareStatement(query);
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete all: " + e);
		}
		finally {
			db.safeClose(stmt);
		}
	}
	
}
