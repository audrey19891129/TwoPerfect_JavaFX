package application.model;

public class ClientSearch extends Client{

	public static Client client;
	
	public static Client getClient() {
		return client;
	}

	public static void setClient(Client client) {
		ClientSearch.client = client;
	}
	
	
}
