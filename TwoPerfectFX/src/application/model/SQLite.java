package application.model;
import java.util.ArrayList;
import java.util.List;

public class SQLite {

	public ArrayList<Intervention> interventions;
	public ArrayList<Ticket> tickets;
	public ArrayList<Client> clients;
	public ArrayList<Address> addresses;
	public ArrayList<Description> descriptions;
	
	
	public SQLite() {
		this.interventions = new ArrayList<Intervention>();
		this.tickets = new ArrayList<Ticket>();
		this.clients = new ArrayList<Client>();
		this.addresses =  new ArrayList<Address>();
		this.descriptions = new ArrayList<Description>();
	}
	
	public ArrayList<Intervention> getInterventions() {
		return interventions;
	}
	public void setInterventions(ArrayList<Intervention> interventions) {
		this.interventions = interventions;
	}
	public ArrayList<Ticket> getTickets() {
		return tickets;
	}
	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}
	public ArrayList<Client> getClients() {
		return clients;
	}
	public void setClients(ArrayList<Client> clients) {
		this.clients = clients;
	}
	public ArrayList<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(ArrayList<Address> addresses) {
		this.addresses = addresses;
	}
	public ArrayList<Description> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(ArrayList<Description> descriptions) {
		this.descriptions = descriptions;
	}
	
	
}

