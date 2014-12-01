package server.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import server.database.*;
import shared.communication.*;
import shared.communication.Search_Result.SearchTuple;
import shared.model.*;
import server.*;

public class ServerFacade {

	public static void initialize() throws ServerException {		
		try {
			Database.init();		
		}
		catch (DatabaseException e) {
			throw new ServerException(e.getMessage(), e);
		}		
	}
	
	public static User validateUser(ValidateUser_Params params) throws ServerException, DatabaseException {

		Database db = new Database();
		User u = null;
		try {
			db.startTransaction();
			u = db.getU().validateUser(params.getUsername(), params.getPassword());
			db.endTransaction(true);
			return u;
		}
		catch (Exception e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}	
	}
	
	public static List<Project> getProjects() throws ServerException, DatabaseException {

		Database db = new Database();
		List<Project> p = null;
		try {
			db.startTransaction();
			p = db.getP().getAll();
			db.endTransaction(true);
			return p;
		}
		catch (Exception e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}	
	}
	
	public static Batch getSampleImage(GetSampleImage_Params params) throws ServerException, DatabaseException {

		Database db = new Database();
		Batch b = null;
		try {
			db.startTransaction();
			b = db.getB().getSampleImage(params.getProject_id());
			db.endTransaction(true);
			return b;
		}
		catch (Exception e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}	
	}
	
	public static DownloadBatch_Result downloadBatch(DownloadBatch_Params params) throws ServerException, DatabaseException {

		Database db = new Database();
		DownloadBatch_Result b = null;
		try {
			db.startTransaction();
			User u = db.getU().validateUser(params.getUsername(), params.getPassword());
			if (u.getCurrent_batch() == -1)
			{
				b = db.getB().downloadBatch(params.getProjectID());
				Batch batch = db.getB().get(b.getBatch_id());
				u.setCurrent_batch(b.getBatch_id());
				batch.setCurrentIndexer(u.getId());
				db.getU().update(u);
				db.getB().update(batch);
				if (b != null)
				{
					b.setFields(db.getF().getFromProjectID(params.getProjectID()));
					b.setNum_fields();
				}
			}
			db.endTransaction(true);
			return b;
		}
		catch (Exception e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}	
	}
	
	public static List<Field> getFields(GetFields_Params params) throws ServerException, DatabaseException {

		Database db = new Database();
		List<Field> fields = null;
		try {
			db.startTransaction();
			if(params.getProjectID() != -1)
			{
				fields = db.getF().getFromProjectID(params.getProjectID());
			}
			else
			{
				fields = db.getF().getAll();
			}
			db.endTransaction(true);
			return fields;
		}
		catch (Exception e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}	
	}
	
	public static boolean submitBatch(SubmitBatch_Params params) throws ServerException, DatabaseException {

		Database db = new Database();
		List<Record> recordList = new ArrayList<Record>();
		List<Integer> recordIDs = new ArrayList<Integer>();
		boolean submitted = false;
		Project p = null;
		int requiredNumOfValues = 0;
		try {
			db.startTransactionAuto();
			User u = db.getU().validateUser(params.getUser(), params.getPassword());
			Batch b = db.getB().get(params.getBatchID());
			
			int fieldNum = 0;
			if(b != null)
			{
				p = db.getP().get(b.getProject_id());
				fieldNum = db.getF().getFromProjectID(b.getProject_id()).size();
				requiredNumOfValues = p.getRecordsperimage() * fieldNum;
			}

			
			boolean rightVals = true;
			if(!params.getField_values().isEmpty())
			{
				for (Map.Entry<Integer, List<String>> entry : params.getField_values().entrySet()) {
					if(entry.getValue().size() != fieldNum)
					{
						rightVals = false;
					}

				}
			}
			//If the batch numbers don't match, the user doesn't own the batch trying to be submitted, or the number of values don't match the required number
			if((u.getCurrent_batch() == params.getBatchID()) && b != null && p != null && requiredNumOfValues == params.getNumValues() && params.getField_values().size() == p.getRecordsperimage() && rightVals)
			{
				recordList = db.getR().getAllFromBatch(params.getBatchID());
				
				if(recordList.isEmpty())
				{
					for(int i=0; i < p.getRecordsperimage(); i++)
					{
						Record rec = new Record(params.getBatchID(), i+1);
						int newRecID = db.getR().insert(rec);
						recordIDs.add(newRecID);
					}
				}
	
				int i=0;
				for(List<String> valueList : params.getField_values().values())
				{
					//If for some reason there's more values than records
					if(recordList.isEmpty())
					{
						parseSubmit(db, recordIDs.get(i), valueList, b);
					}
					else
					{
						parseSubmit(db, recordList.get(i).getId(), valueList, b);
					}
					i++;
				}
				b.setRecorded(true);
				b.setCurrentIndexer(-1);
				db.getB().update(b);
				u.setIndexedrecords(u.getIndexedrecords() + p.getRecordsperimage());
				u.setCurrent_batch(-1);
				db.getU().update(u);
				submitted = true;
				db.endTransaction();
				return submitted;
			}
			else
			{
				db.endTransaction();
				return submitted;
			}
		}
		catch (Exception e) {
			db.endTransaction();
			throw new ServerException(e.getMessage(), e);
		}	
	}
	
	public static void parseSubmit(Database db, int recordID, List<String> values, Batch b) throws DatabaseException
	{
		List<Field> fields = db.getF().getFromProjectID(b.getProject_id());
		
		int i=0;
		int fieldID = -1;
		for (String value : values)
		{
			for(Field f : fields)
			{
				if(f.getPosition() == i)
				{
					fieldID = f.getId(); 
				}
			}
			if (b.isRecorded())
			{
				db.getV().update(new Value(value, recordID, fieldID));
			}
			else
			{
				db.getV().insert(new Value(value, recordID, fieldID));
			}
			i++;
		}

		
	}
	
	public static Search_Result search(Search_Params params) throws ServerException, DatabaseException {

		Database db = new Database();
		List<Value> values = null;
		Search_Result result = new Search_Result();
		List<SearchTuple> tuple = new ArrayList<SearchTuple>();
		try {
			db.startTransaction();
			params.parseFields();
			params.parseSearchValues();
			//Get all the values that are in the list of fields and search values
			
			for(Integer i : params.getFields())
			{

				for(String s : params.getSearch_values())
				{
					values = db.getV().getFromSearch(i, s);
					for(Value v : values)
					{
						Record r = db.getR().get(v.getRecord_id());
						if (r != null)
						{
							Batch b = db.getB().get(r.getBatch_id());
							if (b != null && b.isRecorded())
							{
								tuple.add(result.new SearchTuple(r.getBatch_id(), b.getFilename(), r.getRecord_number(), v.getField_id()));
							}
							else
							{
								throw new DatabaseException("Could not find a batch in Search.");
							}
						}
						else
						{
							throw new DatabaseException("Could not find a record in Search.");
						}
					}
				}	
			}
				
				
			result.setSearchResults(tuple);
			db.endTransaction(true);
			return result;
		}
		catch (Exception e) {
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		} 
	}

}

