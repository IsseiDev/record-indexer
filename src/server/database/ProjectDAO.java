package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import shared.model.Project;

public class ProjectDAO {
	
private static Logger logger;
	
	static {
		logger = Logger.getLogger("record-indexer");
	}
	
	Database db;

	public ProjectDAO(Database database) {
		db = database;
	}
	
	public List<Project> getAll() throws DatabaseException {
		
		logger.entering("server.database.Project", "getAll");

		ArrayList<Project> result = new ArrayList<Project>();
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "select * from Project";
			stmt = db.getConnection().prepareStatement(query);

			keyRS = stmt.executeQuery();
			while (keyRS.next()) {
				int id = keyRS.getInt("id");
				String title = keyRS.getString("title");
				int recordsperimage = keyRS.getInt("recordsperimage");
				int firstycoord = keyRS.getInt("firstycoord");
				int recordheight = keyRS.getInt("recordheight");
				Project project = new Project(title, recordsperimage, firstycoord, recordheight);
				project.setId(id);
				result.add(project);
			}
		} catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Project", "getAll", serverEx);
			
			throw serverEx;
		}		
		finally {
			db.safeClose(keyRS);
			db.safeClose(stmt);
		}
		logger.exiting("server.database.Project", "getAll");

		return result;	
	}

	/**
	 * @param p the Project being inserted
	 * @throws DatabaseException 
	 */
	public int insert(Project p) throws DatabaseException
	{
		int id = -1;
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Statement keyStmt = null;
		try {
			String query = "insert into Project (title, recordsperimage, firstycoord, recordheight) values (?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, p.getTitle());
			stmt.setInt(2, p.getRecordsperimage());
			stmt.setInt(3, p.getFirstycoord());
			stmt.setInt(4, p.getRecordheight());

			
			if(stmt.executeUpdate() == 1) {
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				id = keyRS.getInt(1);
				p.setId(id);
			}
			else {
				throw new DatabaseException("Could not insert Project.");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert Project: " + e);
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
			db.safeClose(keyStmt);
		}
		return id;
	}
	
	/**
	 * @param p the Project being updated
	 * @throws DatabaseException 
	 */
	public void update(Project p) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "update Project set title = ?, recordsperimage = ?, firstycoord = ?, recordheight= ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, p.getTitle());
			stmt.setInt(2, p.getRecordsperimage());
			stmt.setInt(3, p.getFirstycoord());
			stmt.setInt(4, p.getRecordheight());
			stmt.setInt(5, p.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update Project.");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update Project: " + e);
		}
		finally {
			db.safeClose(stmt);
		}
	}
	
	/**
	 * @param id the id of the Project being searched by
	 * @return the Project model object with the found results
	 * @throws DatabaseException 
	 */
	public Project get(int id) throws DatabaseException
	{
		Project project = null;
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "Select * from Project where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
			
			keyRS = stmt.executeQuery();
			
			boolean hasNext = keyRS.next();
			//If we found a Project
			if (hasNext)
			{
				String title = keyRS.getString("title");
				int recordsperimage = keyRS.getInt("recordsperimage");
				int firstycoord = keyRS.getInt("firstycoord");
				int recordheight = keyRS.getInt("recordheight");
				project = new Project(title, recordsperimage, firstycoord, recordheight);
				project.setId(id);
			}
		} catch (SQLException e)
		{
			System.out.println("Could not Get the Project. " + e); 
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
		}
		
		return project;
	}
	
	/**
	 * @param p the Project being deleted
	 */
	public void delete(Project p) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "delete from Project where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, p.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete Project.");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete Project: " + e);
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
			String query = "delete from Project";
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
