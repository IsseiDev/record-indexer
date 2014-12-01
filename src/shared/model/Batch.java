package shared.model;

public class Batch{
	
	int id = 0;
	String filename;
	int project_id;
	boolean isRecorded;
	int currentIndexer;
	
	public Batch() {
		setFilename(null);
		setProject_id(0);
		setRecorded(false);
		setCurrentIndexer(-1);
	}
	

	public Batch(String filename, int project_id, int currIndexer, boolean isRecorded) {
		setFilename(filename);
		setProject_id(project_id);
		setRecorded(isRecorded);
		setCurrentIndexer(currIndexer);
	}


	/**
	 * @return the isRecorded
	 */
	public boolean isRecorded() {
		return isRecorded;
	}


	/**
	 * @param i the isRecorded to set
	 */
	public void setRecorded(boolean i) {
		this.isRecorded = i;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}


	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
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
	 * @return the currentIndexer
	 */
	public int getCurrentIndexer() {
		return currentIndexer;
	}


	/**
	 * @param currentIndexer the currentIndexer to set
	 */
	public void setCurrentIndexer(int currentIndexer) {
		this.currentIndexer = currentIndexer;
	}

}

