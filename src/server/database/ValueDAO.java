package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import shared.model.Value;

public class ValueDAO {
	
	Database db;
	
	public ValueDAO(Database database) {
		db = database;
	}
	/**
	 * @param v the Value being inserted
	 * @throws DatabaseException 
	 */
	public int insert(Value v) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Statement keyStmt = null;
		int id = -1;
		try {
			String query = "insert into Value (value, record_id, field_id) values (?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, v.getValue());
			stmt.setInt(2, v.getRecord_id());
			stmt.setInt(3, v.getField_id());
			
			if(stmt.executeUpdate() == 1) {
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				id = keyRS.getInt(1);
				v.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert Value");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("SQL Exception: " + e);
		} catch (Exception e) {
			throw new DatabaseException("Could not insert Value: " + e);
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
			db.safeClose(keyStmt);
		}
		
		return id;
	}
	
	/**
	 * @param v the Value being updated
	 * @throws DatabaseException 
	 */
	public void update(Value v) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "update Value set value = ?, record_id = ?, field_id = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, v.getValue());
			stmt.setInt(2, v.getRecord_id());
			stmt.setInt(3, v.getField_id());
			stmt.setInt(4, v.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update Value");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("SQL Exception: " + e);
		}
		finally {
			db.safeClose(stmt);
		}
	}
	
	/**
	 * @param id the id of the Value being searched by
	 * @return the Value model object with the found results
	 * @throws DatabaseException 
	 */
	public Value get(int id) throws DatabaseException
	{
		Value val = null;
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "Select * from Value where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
			
			keyRS = stmt.executeQuery();
			boolean hasNext = keyRS.next();
			if (hasNext)
			{
				String value = keyRS.getString("value");
				int record_id = keyRS.getInt("record_id");
				int field_id = keyRS.getInt("field_id");
			
				val = new Value(value, record_id, field_id);
				val.setId(id);
			}
		} catch (SQLException e)
		{
			throw new DatabaseException("SQL Exception: " + e);
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
		}
		
		return val;
	}
	
	/**
	 * @param v the Value being deleted
	 * @throws DatabaseException 
	 */
	public void delete(Value v) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "delete from Value where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, v.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete the Value.");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("SQL Exception: " + e);
		}
		finally {
			db.safeClose(stmt);
		}
	}
	
	public List<Value> getFromSearch(int fieldID, String value) throws DatabaseException
	{
		List<Value> values = new ArrayList<Value>();
		Value val = null;
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "Select * from Value where field_id = ? and value = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, fieldID);
			stmt.setString(2, value);
			keyRS = stmt.executeQuery();
			
			while (keyRS.next()) {
				int id = keyRS.getInt("record_id");
				int record_id = keyRS.getInt("record_id");			
				val = new Value(value, record_id, fieldID);
				val.setId(id);
				values.add(val);
			}
		} catch (SQLException e)
		{
			throw new DatabaseException("SQL Exception: " + e);
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
		}
		
		return values;
	}
	
	public void deleteAll() throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "delete from Value";
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
