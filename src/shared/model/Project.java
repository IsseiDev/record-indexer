package shared.model;

public class Project{
	
	int id = 0;
	String title;
	int recordsperimage;
	int firstycoord;
	int recordheight;
	
	public Project() {
		setTitle(null);
		setRecordsperimage(0);
		setFirstycoord(-1);
		setRecordheight(0);
	}
	
	public Project(String title, int recordsperimage, int firstycoord,
					int recordheight) {
		setTitle(title);
		setRecordsperimage(recordsperimage);
		setFirstycoord(firstycoord);
		setRecordheight(recordheight);
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the recordsperimage
	 */
	public int getRecordsperimage() {
		return recordsperimage;
	}

	/**
	 * @param recordsperimage the recordsperimage to set
	 */
	public void setRecordsperimage(int recordsperimage) {
		this.recordsperimage = recordsperimage;
	}

	/**
	 * @return the firstycoord
	 */
	public int getFirstycoord() {
		return firstycoord;
	}

	/**
	 * @param firstycoord the firstycoord to set
	 */
	public void setFirstycoord(int firstycoord) {
		this.firstycoord = firstycoord;
	}

	/**
	 * @return the recordheight
	 */
	public int getRecordheight() {
		return recordheight;
	}

	/**
	 * @param recordheight the recordheight to set
	 */
	public void setRecordheight(int recordheight) {
		this.recordheight = recordheight;
	}
	
}
