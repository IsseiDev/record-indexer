package shared.communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search_Params {

	String user;
	String password;
	String fieldsString;
	String valuesString;
	/**
	 * @return the searchMap
	 */
	public Map<Integer, String> getSearchMap() {
		return searchMap;
	}

	List<Integer> fields;
	List<String> search_values;
	Map<Integer, String> searchMap;
	
	/**
	 * @param user
	 * @param password
	 * @param fields
	 * @param search_values
	 */
	public Search_Params(String user, String password, String fieldsString, String valuesString) {
		this.user = user;
		this.password = password;
		this.fieldsString = fieldsString;
		this.valuesString = valuesString;
		this.fields = new ArrayList<Integer>();
		this.search_values = new ArrayList<String>();
		this.searchMap = new HashMap<Integer, String>();
	}
	/**
	 * @return the fieldsString
	 */
	public String getFieldsString() {
		return fieldsString;
	}
	/**
	 * @param fieldsString the fieldsString to set
	 */
	public void setFieldsString(String fieldsString) {
		this.fieldsString = fieldsString;
	}
	/**
	 * @return the valuesString
	 */
	public String getValuesString() {
		return valuesString;
	}
	/**
	 * @param valuesString the valuesString to set
	 */
	public void setValuesString(String valuesString) {
		this.valuesString = valuesString;
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
	/**
	 * @return the fields
	 */
	public List<Integer> getFields() {
		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<Integer> fields) {
		this.fields = fields;
	}
	/**
	 * @return the search_values
	 */
	public List<String> getSearch_values() {
		return search_values;
	}
	/**
	 * @param search_values the search_values to set
	 */
	public void setSearch_values(List<String> search_values) {
		this.search_values = search_values;
	}

	public void parseFields() throws NumberFormatException
	{
		
		char[] chars = fieldsString.toCharArray();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < chars.length; i++)
		{
			if(chars[i] == ',')
			{
				fields.add(Integer.parseInt(sb.toString()));
				sb.delete(0, sb.length());
			}
			else if (i == chars.length-1)
			{
				//Get the last int
				sb.append(chars[i]);
				fields.add(Integer.parseInt(sb.toString()));
				sb.delete(0, sb.length());
			}
			else if (Character.isWhitespace(chars[i]) )
			{
				//pass
			}
			else
			{
				sb.append(chars[i]);
			}
		}
	}
	
	/**
	 * @param searchMap the searchMap to set
	 */
	public void setSearchMap(Map<Integer, String> searchMap) {
		this.searchMap = searchMap;
	}
	public void parseSearchValues()
	{
		char[] chars = valuesString.toCharArray();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < chars.length; i++)
		{
			if(chars[i] == ',')
			{
				search_values.add(sb.toString().toUpperCase());
				sb.delete(0, sb.length());
			}
			else if (i == chars.length-1)
			{
				//Get the last string
				if (chars[i] != '\"' && chars[i] != '\''){
					sb.append(chars[i]);
				}
				search_values.add(sb.toString().toUpperCase());
				sb.delete(0, sb.length());
			}
			else
			{
				if (chars[i] != '\"' && chars[i] != '\''){
					sb.append(chars[i]);
				}
			}
		}
	}
	
	public String parseFieldsToQueryString(List<Integer> list)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		int i = 0;
		for (Integer o : list)
		{
			if(i == list.size()-1)
			{
				sb.append("\'" + o.toString() + "\')");
			}
			else
			{
				sb.append("\'" + o.toString() + "\',");
			}
			i++;
		}
		return sb.toString();
	}
	
	public String parseValuesToQueryString(List<String> list)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		int i = 0;
		for (String o : list)
		{
			if(i == list.size()-1)
			{
				sb.append("\'" + o + "\')");
			}
			else
			{
				sb.append("\'" + o + "\',");
			}
			i++;
		}
		return sb.toString();
	}
	
	public void populateSearchMap()
	{
		for(Integer i : fields)
		{

			for(String s : search_values)
			{
				searchMap.put(i, s);
			}
		}
	}
	
	public String searchMapToString()
	{
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, String> entry : getSearchMap().entrySet()) {
			sb.append("\nKey: " + entry.getKey() + " Value: " + entry.getValue());
		}
		return sb.toString();
	}
	
	public String listValuesToString()
	{
		StringBuilder sb = new StringBuilder();
		for(String s : search_values)
		{
			sb.append("\n" + s);
		}
		return sb.toString();
	}
	
	public String fieldsToString()
	{
		StringBuilder sb = new StringBuilder();
		for(Integer s : fields)
		{
			sb.append("\n" + s);
		}
		return sb.toString();
	}
	
}
