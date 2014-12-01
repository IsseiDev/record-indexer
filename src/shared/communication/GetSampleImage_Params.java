package shared.communication;

public class GetSampleImage_Params {
	
	int project_id;

	public GetSampleImage_Params(int project_id) {
		this.project_id = project_id;
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
	
	

}
