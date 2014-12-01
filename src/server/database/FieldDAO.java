package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import shared.model.Field;

public class FieldDAO {

	Database db;
	
	public FieldDAO(Database database) {
		db = database;
	}

	/**
	 * @param f the Field being inserted
	 * @throws DatabaseException 
	 */
	public int insert(Field f) throws DatabaseException
	{
		int id = -1;
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Statement keyStmt = null;
		try {
			String query = "insert into Field (title, helphtml, knowndata, xcoord, width, position, project_id) values (?, ?, ?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, f.getTitle());
			stmt.setString(2, f.getHelphtml());
			stmt.setString(3, f.getKnowndata());
			stmt.setInt(4, f.getXcoord());
			stmt.setInt(5, f.getWidth());
			stmt.setInt(6, f.getPosition());
			stmt.setInt(7, f.getProject_id());
			
			if(stmt.executeUpdate() == 1) {
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				id = keyRS.getInt(1);
				f.setId(id);
			}
			else {
				throw new DatabaseException("Could not Insert Field.");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not Insert Field: " + e);
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
			db.safeClose(keyStmt);
		}
		
		return id;
	}
	
	/**
	 * @param f the Field being updated
	 * @throws DatabaseException 
	 */
	public void update(Field f) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "update Field set title = ?, helphtml = ?, knowndata = ?, xcoord = ?, width = ?, position = ?, project_id = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, f.getTitle());
			stmt.setString(2, f.getHelphtml());
			stmt.setString(3, f.getKnowndata());
			stmt.setInt(4, f.getXcoord());
			stmt.setInt(5, f.getWidth());
			stmt.setInt(6, f.getPosition());
			stmt.setInt(7, f.getProject_id());
			stmt.setInt(8, f.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update Field.");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update Field: " + e);
		}
		finally {
			db.safeClose(stmt);
		}
	}
	
	/**
	 * @param id the id of the Field being searched by
	 * @return the Field model object with the found results
	 * @throws DatabaseException 
	 */
	public Field get(int id) throws DatabaseException
	{
		Field field = null;
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "Select * from Field where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
			
			keyRS = stmt.executeQuery();
			boolean hasNext = keyRS.next();
			//If we found a Batch
			if (hasNext)
			{
				String title = keyRS.getString("title");
				String helphtml = keyRS.getString("helphtml");
				String knowndata = keyRS.getString("knowndata");
				int xcoord = keyRS.getInt("xcoord");
				int width = keyRS.getInt("width");
				int position = keyRS.getInt("position");
				int project_id = keyRS.getInt("project_id");
			
				field = new Field(title, helphtml, knowndata, xcoord, width, position, project_id);
				field.setId(id);
			}
		} catch (SQLException e)
		{
			System.out.println("Could not Get the Field. " + e); 
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
		}
		
		return field;
	}
	
	/**
	 * @param project_id the id of the Project being searched by
	 * @return the List of Field model objects with the found results
	 * @throws DatabaseException 
	 */
	public List<Field> getFromProjectID(int project_id) throws DatabaseException
	{
		List<Field> fields = new ArrayList<Field>();
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "Select * from Field where project_id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, project_id);
			
			keyRS = stmt.executeQuery();
			//While there are rows 
			while (keyRS.next())
			{
				int id = keyRS.getInt("id");
				String title = keyRS.getString("title");
				String helphtml = keyRS.getString("helphtml");
				String knowndata = keyRS.getString("knowndata");
				int xcoord = keyRS.getInt("xcoord");
				int width = keyRS.getInt("width");
				int position = keyRS.getInt("position");
			
				Field field = new Field(title, helphtml, knowndata, xcoord, width, position, project_id);
				field.setId(id);
				
				fields.add(field);
			}
		} catch (SQLException e)
		{
			System.out.println("Could not Get the Field. " + e); 
		}
		finally {
			db.safeClose(stmt);
			db.safeClose(keyRS);
		}
		
		return fields;
	}
	
public List<Field> getAll() throws DatabaseException {
		
		List<Field> result = new ArrayList<Field>();
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "select * from Field";
			stmt = db.getConnection().prepareStatement(query);

			keyRS = stmt.executeQuery();
			while (keyRS.next()) {
				int id = keyRS.getInt("id");
				String title = keyRS.getString("title");
				String helphtml = keyRS.getString("helphtml");
				String knowndata = keyRS.getString("knowndata");
				int xcoord = keyRS.getInt("xcoord");
				int width = keyRS.getInt("width");
				int position = keyRS.getInt("position");
				int project_id = keyRS.getInt("project_id");

				Field field = new Field(title, helphtml, knowndata, xcoord, width, position, project_id);
				field.setId(id);
				result.add(field);
			}
		} catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
						
			throw serverEx;
		}		
		finally {
			db.safeClose(keyRS);
			db.safeClose(stmt);
		}

		return result;	
	}
	
	/**
	 * @param f the Field being deleted
	 * @throws DatabaseException 
	 */
	public void delete(Field f) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "delete from Field where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, f.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not Field.");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete Field: " + e);
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
			String query = "delete from Field";
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
