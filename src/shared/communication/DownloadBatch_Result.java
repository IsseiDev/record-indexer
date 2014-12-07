package shared.communication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import shared.model.Field;

public class DownloadBatch_Result {

	int batch_id;
	int project_id;
	String image_url;
	int first_y_coord;
	int record_height;
	int num_records;
	int num_fields;
	
	List<Field> fields;
	
	
	/**
	 * @param batch_id
	 * @param project_id
	 * @param field_id
	 * @param image_url
	 * @param first_y_coord
	 * @param record_height
	 * @param num_records
	 * @param num_fields
	 */
	public DownloadBatch_Result(int batch_id, int project_id,
			String image_url, int first_y_coord, int record_height,
			int num_records) {
		this.batch_id = batch_id;
		this.project_id = project_id;
		this.image_url = image_url;
		this.first_y_coord = first_y_coord;
		this.record_height = record_height;
		this.num_records = num_records;
		fields = new ArrayList<Field>();
	}



	/**
	 * @return the fields
	 */
	public List<Field> getFields() {
		return fields;
	}



	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}



	/**
	 * @return the batch_id
	 */
	public int getBatch_id() {
		return batch_id;
	}


	public void setNum_fields(){
		this.num_fields = fields.size();
	}
	
	/**
	 * @param batch_id the batch_id to set
	 */
	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}



	/**
	 * @return the project_id
	 */
	public int getProject_id() {
		return project_id;
	}



	/**
	 * @param project_id the project_id to set
	 */
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	/**
	 * @return the image_url
	 */
	public String getImage_url() {
		return image_url;
	}

	public String getImage_url(String hostname, String port)
	{
		return "http://" + hostname + ":" + port + "/" + image_url ;
	}
	
	/**
	 * @param image_url the image_url to set
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}



	/**
	 * @return the first_y_coord
	 */
	public int getFirst_y_coord() {
		return first_y_coord;
	}



	/**
	 * @param first_y_coord the first_y_coord to set
	 */
	public void setFirst_y_coord(int first_y_coord) {
		this.first_y_coord = first_y_coord;
	}



	/**
	 * @return the record_height
	 */
	public int getRecord_height() {
		return record_height;
	}



	/**
	 * @param record_height the record_height to set
	 */
	public void setRecord_height(int record_height) {
		this.record_height = record_height;
	}



	/**
	 * @return the num_records
	 */
	public int getNum_records() {
		return num_records;
	}



	/**
	 * @param num_records the num_records to set
	 */
	public void setNum_records(int num_records) {
		this.num_records = num_records;
	}



	/**
	 * @return the num_fields
	 */
	public int getNum_fields() {
		return num_fields;
	}



	/**
	 * @param num_fields the num_fields to set
	 */
	public void setNum_fields(int num_fields) {
		this.num_fields = num_fields;
	}
	
	public String toURLString(String host, String port){
		StringBuilder sb = new StringBuilder();
		sb.append(batch_id + "\n" + project_id + "\n" +
		"http://" + host + ":" + port + File.separator + image_url + "\n" 
		+ first_y_coord + "\n" + record_height + "\n" + num_records + "\n" + num_fields + "\n");
		for (Field f : fields)
		{
			sb.append(f.toURLString(host, port));
		}
		return sb.toString();
	}

}
