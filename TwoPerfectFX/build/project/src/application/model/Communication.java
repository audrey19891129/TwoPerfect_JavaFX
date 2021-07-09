package application.model;

public class Communication {
	private int id, technicianId, clientId;
	private String dateTime, type;
	
	public Communication() {
		
	};
	
	public Communication(int id, int technicianId, int clientId, String dateTime, String type) {
		this.id = id;
		this.technicianId = technicianId;
		this.clientId = clientId;
		this.dateTime = dateTime;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(int technicianId) {
		this.technicianId = technicianId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Communication [id=" + id + ", technicianId=" + technicianId + ", clientId=" + clientId + ", dateTime="
				+ dateTime + ", type=" + type + "]";
	}

	
}
