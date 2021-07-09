package application.model;

import java.util.ArrayList;
import java.util.Collection;

public class Package {
	private int id, clientId, addressId;
	private String start, end;
	public Collection<Entry> entries;
	
	public Package() {
		this.entries = new ArrayList<Entry>();
	};
	
	public Package(int id, int clientId, int addressId, String start, String end, ArrayList<Entry> entries) {
		this.id = id;
		this.clientId = clientId;
		this.addressId = addressId;
		this.start = start;
		this.end = end;
		this.entries = entries;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Collection<Entry> getEntries() {
		return entries;
	}

	public void setEntries(ArrayList<Entry> entries) {
		this.entries = entries;
	}

	@Override
	public String toString() {
		return "Package [id=" + id + ", clientId=" + clientId + ", addressId=" + addressId + ", start=" + start
				+ ", end=" + end + ", entries=" + entries + "]";
	}

}
