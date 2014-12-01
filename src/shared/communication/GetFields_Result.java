package shared.communication;

import java.util.ArrayList;
import java.util.List;

public class GetFields_Result {

	List<Project_id> projects;
	
	/**
	 * @param projects
	 */
	public GetFields_Result() {
		this.projects = new ArrayList<Project_id>();
	}


	/**
	 * @return the projects
	 */
	public List<Project_id> getProjects() {
		return projects;
	}


	/**
	 * @param projects the projects to set
	 */
	public void setProjects(List<Project_id> projects) {
		this.projects = projects;
	}



	public class Project_id{
		int project_id;
		int field_id;
		String field_title;
		/**
		 * @param project_id
		 * @param field_id
		 * @param field_title
		 */
		public Project_id(int project_id, int field_id, String field_title) {
			this.project_id = project_id;
			this.field_id = field_id;
			this.field_title = field_title;
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
		/**
		 * @return the field_title
		 */
		public String getField_title() {
			return field_title;
		}
		/**
		 * @param field_title the field_title to set
		 */
		public void setField_title(String field_title) {
			this.field_title = field_title;
		}
		
		@Override 
		public String toString()
		{
			return project_id + "\n" + field_id + "\n" + field_title + "\n";
		}
		
		
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (Project_id p : projects)
		{
			sb.append(p.toString());
		}
		return sb.toString();
	}
}
