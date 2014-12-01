package shared.model;

import java.io.File;

public class Field{

int id = 0;
String title;
String helphtml;
String knowndata;
int xcoord;
int width;
int position;
int project_id;
	
	
	public Field() {
		setTitle(null);
		setHelphtml("");
		setKnowndata("");
		setXcoord(-1);
		setWidth(1);
		setPosition(0);
		setProject_id(0);
	}
	
	public Field(String title, String helphtml, String knowndata, 
			int xcoord, int width, int position, int project_id) {
		setTitle(title);
		setHelphtml(helphtml);
		setKnowndata(knowndata);
		setXcoord(xcoord);
		setWidth(width);
		setPosition(position);
		setProject_id(project_id);
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
	 * @return the helphtml
	 */
	public String getHelphtml() {
		return helphtml;
	}

	/**
	 * @param helphtml the helphtml to set
	 */
	public void setHelphtml(String helphtml) {
		this.helphtml = helphtml;
	}

	/**
	 * @return the knowndata
	 */
	public String getKnowndata() {
		return knowndata;
	}

	/**
	 * @param knowndata the knowndata to set
	 */
	public void setKnowndata(String knowndata) {
		this.knowndata = knowndata;
	}

	/**
	 * @return the xcoord
	 */
	public int getXcoord() {
		return xcoord;
	}

	/**
	 * @param xcoord the xcoord to set
	 */
	public void setXcoord(int xcoord) {
		this.xcoord = xcoord;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
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
	
	public String toURLString(String host, String port)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(id + "\n" + position + "\n" + title + "\n" + "http://" + host + ":" + port + File.separator + helphtml + "\n" + xcoord + "\n" + width + "\n");
		if (knowndata != null)
		{
			sb.append("http://" + host + ":" + port + File.separator + knowndata + "\n");
		}
		
		return  sb.toString();
	}

}

