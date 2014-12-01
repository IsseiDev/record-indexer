package shared.communication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Search_Result {

	List<SearchTuple> searchResults;

	public Search_Result() {
		searchResults = new ArrayList<SearchTuple>();
	}
	
	/**
	 * @return the searchResults
	 */
	public List<SearchTuple> getSearchResults() {
		return searchResults;
	}

	/**
	 * @param searchResults the searchResults to set
	 */
	public void setSearchResults(List<SearchTuple> searchResults) {
		this.searchResults = searchResults;
	}

	public class SearchTuple{
		int batch_id;
		String image_url;
		int record_num;
		int field_id;
		
		/**
		 * @param batch_id
		 * @param image_url
		 * @param record_num
		 * @param field_id
		 */
		public SearchTuple(int batch_id, String image_url, int record_num,
				int field_id) {
			this.batch_id = batch_id;
			this.image_url = image_url;
			this.record_num = record_num;
			this.field_id = field_id;
		}
		
		/**
		 * @return the batch_id
		 */
		public int getBatch_id() {
			return batch_id;
		}
		/**
		 * @param batch_id the batch_id to set
		 */
		public void setBatch_id(int batch_id) {
			this.batch_id = batch_id;
		}
		/**
		 * @return the image_url
		 */
		public String getImage_url() {
			return image_url;
		}
		/**
		 * @param image_url the image_url to set
		 */
		public void setImage_url(String image_url) {
			this.image_url = image_url;
		}
		/**
		 * @return the record_num
		 */
		public int getRecord_num() {
			return record_num;
		}
		/**
		 * @param record_num the record_num to set
		 */
		public void setRecord_num(int record_num) {
			this.record_num = record_num;
		}
		/**
		 * @return the field_id
		 */
		public int getField_id() {
			return field_id;
		}
		/**
		 * @param field_id the field_id to set
		 */
		public void setField_id(int field_id) {
			this.field_id = field_id;
		}
		
		public String toURLString(String host, String port)
		{
			return batch_id + "\n" + "http://" + host + ":" + port + File.separator + image_url + "\n" + record_num + "\n" + field_id + "\n";
		}
		
	}
	
	public String toURLString(String host, String port)
	{
		StringBuilder sb = new StringBuilder();
		for(SearchTuple s : searchResults)
		{
			sb.append(s.toURLString(host, port));
		}
		return sb.toString();
	}
	
	public Vector<String> getURLStringVector(String host, String port)
	{
		Vector<String> sb = new Vector<String>();
		for(SearchTuple s : searchResults)
		{
			sb.add("http://" + host + ":" + port + "/" + s.getImage_url());
		}
		return sb;
	}
	
}
