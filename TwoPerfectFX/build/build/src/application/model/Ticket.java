package application.model;
import java.util.ArrayList;

public class Ticket {
	
	public int id;
	public int addressId;
	public Address address;
	public int clientId;
	public String status;
	public int descriptionId;
	public Description description;
	public String creationDate;
	public String closingDate;
	public ArrayList<Intervention> interventions;
	
	public Ticket() {
		this.interventions = new ArrayList<Intervention>();
	};

	

	public Ticket(int id, int addressId, Address address, int clientId, String status, int descriptionId,
			Description description, String creationDate, String closingDate, ArrayList<Intervention> interventions) {
		super();
		this.id = id;
		this.addressId = addressId;
		this.address = address;
		this.clientId = clientId;
		this.status = status;
		this.descriptionId = descriptionId;
		this.description = description;
		this.creationDate = creationDate;
		this.closingDate = closingDate;
		this.interventions = interventions;
	}



	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public int getDescriptionId() {
		return descriptionId;
	}

	public void setDescriptionId(int descriptionId) {
		this.descriptionId = descriptionId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}

	public ArrayList<Intervention> getInterventions() {
		return interventions;
	}

	public void setInterventions(ArrayList<Intervention> interventions) {
		this.interventions = interventions;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", addressId=" + addressId + ", address=" + address + ", clientId=" + clientId
				+ ", status=" + status + ", descriptionId=" + descriptionId + ", description=" + description
				+ ", creationDate=" + creationDate + ", closingDate=" + closingDate + ", interventions=" + interventions
				+ "]";
	}
	
	public Ticket(int id, int addressId, int clientId, String status, int descriptionId, String creationDate,
			String closingDate) {
		super();
		this.id = id;
		this.addressId = addressId;
		this.clientId = clientId;
		this.status = status;
		this.descriptionId = descriptionId;
		this.creationDate = creationDate;
		this.closingDate = closingDate;
	}
	
	public String Accordion() {
		String str= "Status : " + status.toUpperCase() + "\n" + 
				"Description : " + description + "\n" +
				"Address : " + address + "\n" +
				"Creation date : " + creationDate + "\n";
		if(closingDate.contentEquals("0000-00-00"))
			str += "Closing date : OPEN \n";
		else
			str += "Closing date : " + closingDate + "\n";
		return str;
	}
}
