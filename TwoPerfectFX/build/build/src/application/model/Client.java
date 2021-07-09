package application.model;

import java.util.ArrayList;
import java.util.Collection;

public class Client {
//	public enum Status { ACTIVE, INACTIVE }
	private int id;
	private String lastname, firstname, phone, email;
//	private Status status;
	public Collection<Address> addresses;
	public Collection<Package> packages;
	public Collection<Ticket> tickets;
	
	
	public Client() {
		this.addresses = new ArrayList<Address>();
		this.packages = new ArrayList<Package>();
		this.tickets = new ArrayList<Ticket>();
	}
	
	public Client(int id, String lastname, String firstname, String phone, String email) {
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.phone = phone;
		this.email = email;
		this.addresses = new ArrayList<Address>();
		this.packages = new ArrayList<Package>();
		this.tickets = new ArrayList<Ticket>();
	}
	
	// ====>  Constructor avec ENUM Status ( en attente d'appropation .......) <====
	
	/*public Client(int id, String lastname, String firstname, String phone, String email, Status status) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.phone = phone;
		this.email = email;
		this.status = status;
		this.addresses = new ArrayList<Address>();
		this.packages = new ArrayList<Package>();
		this.tickets = new ArrayList<Ticket>();
	}*/
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/*public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}*/

	public Collection<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(ArrayList<Address> addresses) {
		this.addresses = addresses;
	}
	

	public Collection<Package> getPackages() {
		return packages;
	}

	public void setPackages(ArrayList<Package> packages) {
		this.packages = packages;
	}

	public Collection<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + ", phone=" + phone
				+ ", email=" + email + ", addresses=" + addresses + ", packages=" + packages + ", tickets=" + tickets
				+ "]";
	}
}
