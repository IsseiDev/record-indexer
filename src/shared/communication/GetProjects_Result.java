package shared.communication;

import java.util.List;

public class GetProjects_Result {

	List<ProjectInfo> info; 
	
	/**
	 * @params info
	 */
	public GetProjects_Result(List<ProjectInfo> info)
	{
		this.info = info;
	}
	/**
	 * @return info
	 */
	public List<ProjectInfo> getInfo()
	{
		return info;
	}
	
	/**
	 * @params setInfo the info to be set
	 */
	public void setInfo(List<ProjectInfo> setInfo)
	{
		this.info = setInfo;
	}
	
	public class ProjectInfo{
		int project_id;
		String project_title;
		/**
		 * @param project_id
		 * @param project_title
		 */
		public ProjectInfo(int project_id, String project_title) {
			this.project_id = project_id;
			this.project_title = project_title;
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
		 * @return the project_title
		 */
		public String getProject_title() {
			return project_title;
		}
		/**
		 * @param project_title the project_title to set
		 */
		public void setProject_title(String project_title) {
			this.project_title = project_title;
		}
		
		@Override
		public String toString()
		{
			return project_id + "\n" + project_title + "\n";
		}
		
		
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(ProjectInfo pr : info)
		{
			sb.append(pr.toString());
		}
		return sb.toString();
	}
}
