package application.model;

public class Coordinates {

	public int techId;
	public String latitude;
	public String longitude;
	
	public Coordinates() {}
	
	public Coordinates(int techId, String latitude, String longitude) {
		super();
		this.techId = techId;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getTechId() {
		return techId;
	}

	public void setTechId(int techId) {
		this.techId = techId;
	}

	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Coordinates [techid=" + techId + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
	
}

