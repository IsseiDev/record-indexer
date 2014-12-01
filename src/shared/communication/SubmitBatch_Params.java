package shared.communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubmitBatch_Params {

	String user;
	String password;
	int batchID;
	Map<Integer, List<String>> field_values;
	
	/**
	 * @param user
	 * @param password
	 * @param batchID
	 * @param field_values
	 */
	public SubmitBatch_Params(String user, String password, int batchID,
			String field_values_unparsed) {
		this.user = user;
		this.password = password;
		this.batchID = batchID;
		field_values = new HashMap<Integer, List<String>>();
		parseFieldValues(field_values_unparsed);
	}
	
	public void parseFieldValues(String unparsed)
	{
		int recordNum = 0;
		char[] u = unparsed.toCharArray();
		StringBuilder b = new StringBuilder();
		field_values.put(recordNum, new ArrayList<String>());
		for(int i =0; i < u.length; i++)
		{
			if(u[i] == ';')
			{
				if(b.length() > 0)
				{
					field_values.get(recordNum).add(b.toString().toUpperCase());
					b.delete(0,b.length());
				}
				recordNum++;
				field_values.put(recordNum, new ArrayList<String>());
				if(i != u.length-1 && u[i+1] == ',')
				{
					if(b.length() > 0)
					{
						field_values.get(recordNum).add(b.toString().toUpperCase());
						b.delete(0,b.length());
					}
					field_values.get(recordNum).add("");
				}
			}
			else if(u[i] == ',')
			{
				if(i != u.length-1 && (u[i+1] == ',' || u[i+1] == ';'))
				{
					if(b.length() > 0)
					{
						field_values.get(recordNum).add(b.toString().toUpperCase());
						b.delete(0,b.length());
					}
					field_values.get(recordNum).add("");
				}
				else if (i == 0)
				{
					field_values.get(recordNum).add("");
				}
				{
					if(b.length() > 0)
					{
						field_values.get(recordNum).add(b.toString().toUpperCase());
						b.delete(0,b.length());
					}
				}
			}
			else
			{
				b.append(u[i]);
			}
		}
		if(b.length() > 0)
		{
			field_values.get(recordNum).add(b.toString().toUpperCase());
			b.delete(0,b.length());
		}
		else
		{
			field_values.get(recordNum).add("");
		}
		
	}

	/**
	 * @return the batchID
	 */
	public int getBatchID() {
		return batchID;
	}

	/**
	 * @param batchID the batchID to set
	 */
	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}

	/**
	 * @return the field_values
	 */
	public Map<Integer, List<String>> getField_values() {
		return field_values;
	}

	/**
	 * @param field_values the field_values to set
	 */
	public void setField_values(Map<Integer, List<String>> field_values) {
		this.field_values = field_values;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getNumValues()
	{
		int numValues = 0;
		for (Map.Entry<Integer, List<String>> entry : field_values.entrySet()) {
		    List<String> values = entry.getValue();
		    for(@SuppressWarnings("unused") String value : values)
		    {
		    	numValues++;
		    }
		}
		
		return numValues;
	}
	
	public String FieldValuesToString()
	{
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, List<String>> entry : field_values.entrySet()) {
		    sb.append(entry.getKey() + ":\n");
		    List<String> values = entry.getValue();
		    for(String value : values)
		    {
		    	sb.append("\t" + value + "\n");
		    }
		}
		sb.append("------------------------------------------------------------------------");
		return sb.toString();
	}
}
