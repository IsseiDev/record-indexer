package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import shared.communication.DownloadBatch_Result;
import shared.model.Batch;

public class BatchDAO {
	
	Database db;

	public BatchDAO(Database database) {
		db = database;
	}

	/**
	 * @param b the Batch being inserted
	 * @throws DatabaseException 
	 */
	public int insert(Batch b) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Statement keyStmt = null;
		int id = -1;
		try {
			String query = "insert into Batch (filename, project_id, currentIndexer, isRecorded) values (?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, b.getFilename());
			stmt.setInt(2, b.getProject_id());
			stmt.setInt(3, b.getCurrentIndexer());
			stmt.setBoolean(4, b.isRecorded());
			
			if(stmt.executeUpdate() == 1) {
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				id = keyRS.getInt(1);
				b.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert batch.");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException(e);
		} catch (Exception e) {
			throw new DatabaseException(e);
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
			db.safeClose(keyStmt);
		}
		
		return id;
	}
	
	/**
	 * @param b the Batch being updated
	 * @throws DatabaseException 
	 */
	public void update(Batch b) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "update Batch set filename = ?, isRecorded = ?, currentIndexer = ?, project_id = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, b.getFilename());
			stmt.setBoolean(2, b.isRecorded());
			stmt.setInt(3, b.getCurrentIndexer());
			stmt.setInt(4, b.getProject_id());
			stmt.setInt(5, b.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException();
			}
		}
		catch (SQLException e) {
			throw new DatabaseException(e);
		}
		finally {
			db.safeClose(stmt);
		}
	}
	
	/**
	 * @param id the id of the batch being searched by
	 * @return the Batch model object with the found results
	 * @throws DatabaseException 
	 */
	public Batch get(int id) throws DatabaseException
	{
		Batch batch = null;
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "Select * from Batch where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
			
			keyRS = stmt.executeQuery();
			boolean hasNext = keyRS.next();
			//If we found a Batch
			if (hasNext)
			{
				String filename = keyRS.getString("filename");
				int project_id = keyRS.getInt("project_id");
				int currentIndexer = keyRS.getInt("currentIndexer");
				boolean isRecorded = keyRS.getBoolean("isRecorded");
				batch = new Batch(filename, project_id, currentIndexer, isRecorded);
				batch.setId(id);
			}
		} catch (SQLException e)
		{
			System.out.println("Could not Get the Batch. " + e); 
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
		}
		
		return batch;
	}
	
	public Batch getSampleImage(int projectID) throws DatabaseException
	{
		Batch batch = null;
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "Select * from Batch where project_id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, projectID);
			
			keyRS = stmt.executeQuery();

			boolean hasResult = keyRS.next();

			if (hasResult)
			{
				int id = keyRS.getInt("id");
				String filename = keyRS.getString("filename");
				int project_id = keyRS.getInt("project_id");
				int currentIndexer = keyRS.getInt("currentIndexer");
				boolean isRecorded = keyRS.getBoolean("isRecorded");
				batch = new Batch(filename, project_id, currentIndexer, isRecorded);
				batch.setId(id);
			}
		} catch (SQLException e)
		{
			throw new DatabaseException("Could not Get the Batch. " + e);
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
		}
		
		return batch;	
	}
	
	public DownloadBatch_Result downloadBatch(int projectID) throws DatabaseException
	{
		DownloadBatch_Result batchResult = null;
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "SELECT *, Batch.id as batch_id FROM Batch JOIN Project ON Batch.project_id = Project.id WHERE Batch.project_id = ? and currentIndexer = -1 and Batch.isRecorded = 0 LIMIT 1";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, projectID);
			
			keyRS = stmt.executeQuery();

			boolean hasResult = keyRS.next();

			if (hasResult)
			{
				int batch_id = keyRS.getInt("batch_id");
				int project_id = keyRS.getInt("project_id");
				String image_url = keyRS.getString("filename");
				int first_y_coord = keyRS.getInt("firstycoord");
				int record_height = keyRS.getInt("recordheight");
				int num_records = keyRS.getInt("recordsperimage");
				
				batchResult = new DownloadBatch_Result(batch_id, project_id, image_url, first_y_coord, record_height, num_records);
			}
		} catch (SQLException e)
		{
			throw new DatabaseException("Could not Get the Batch. " + e);
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
		}
		
		return batchResult;	
	}
	
	/**
	 * @param b the Batch being deleted
	 * @throws DatabaseException 
	 */
	public void delete(Batch b) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "delete from Batch where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, b.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete batch.");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException(e);
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
			String query = "delete from Batch";
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
