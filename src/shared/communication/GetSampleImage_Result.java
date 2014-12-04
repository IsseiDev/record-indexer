package shared.communication;

public class GetSampleImage_Result {

	String image_url;

	/**
	 * @param image_url
	 */
	public GetSampleImage_Result(String image_url) {
		this.image_url = image_url;
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
	
	public String toURLString(String host, String port)
	{
		return "http://" + host + ":" + port + "/" + image_url + "\n";
	}
	
	
}
