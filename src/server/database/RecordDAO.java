package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import shared.model.Record;

public class RecordDAO {
	
	Database db;

	public RecordDAO(Database database) {
		db = database;
	}
	
public List<Record> getAllFromBatch(int batchID) throws DatabaseException {
		List<Record> result = new ArrayList<Record>();
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "select * from Record where batch_id = ? ORDER BY record_number";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, batchID);
			keyRS = stmt.executeQuery();
			while (keyRS.next()) {
				int id = keyRS.getInt("id");
				int batch_id = keyRS.getInt("batch_id");
				int record_number = keyRS.getInt("record_number");
				Record record = new Record(batch_id, record_number);
				record.setId(id);
				result.add(record);
			}
		} catch (SQLException e) {
			db.endTransaction(false);
			throw new DatabaseException("SQL Exception from Get All From Batch: " + e);					
		}		
		finally {
			db.safeClose(keyRS);
			db.safeClose(stmt);
		}
		return result;	
}

	/**
	 * @param r the Record being inserted
	 * @throws DatabaseException 
	 */
	public int insert(Record r) throws DatabaseException
	{
		int id = -1;
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Statement keyStmt = null;
		try {
			String query = "insert into Record (batch_id, record_number) values (?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, r.getBatch_id());
			stmt.setInt(2, r.getRecord_number());
			int update = stmt.executeUpdate();
			if(update == 1) {
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				id = keyRS.getInt(1);
				r.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert Record.");
			}

		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert Record: " + e);
		} catch (Exception e) {
			throw new DatabaseException("Could not insert Record: " + e);
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
			db.safeClose(keyStmt);
		}
		
		return id;
	}
	
	/**
	 * @param r the Record being updated
	 * @throws DatabaseException 
	 */
	public void update(Record r) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "update Record set batch_id = ?, record_number = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, r.getBatch_id());
			stmt.setInt(2, r.getRecord_number());
			stmt.setInt(3, r.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete Record.");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update Record: " + e);
		}
		finally {
			db.safeClose(stmt);
		}
	}
	
	/**
	 * @param id the id of the Record being searched by
	 * @return the Record model object with the found results
	 * @throws DatabaseException 
	 */
	public Record get(int id) throws DatabaseException
	{
		Record record = null;
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "Select * from Record where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
			
			keyRS = stmt.executeQuery();
			boolean hasNext = keyRS.next();
			//If we found a Record
			if (hasNext)
			{
				int batch_id= keyRS.getInt("batch_id");
				int record_number = keyRS.getInt("record_number");
				record = new Record(batch_id, record_number);
				record.setId(id);
			}
		} catch (SQLException e)
		{
			System.out.println("Could not Get the Batch. " + e); 
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
		}
		
		return record;
	}
	
	/**
	 * @param r the Record being deleted
	 * @throws DatabaseException 
	 */
	public void delete(Record r) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "delete from Record where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, r.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete Record.");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete Record: " + e);
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
			String query = "delete from Record";
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
