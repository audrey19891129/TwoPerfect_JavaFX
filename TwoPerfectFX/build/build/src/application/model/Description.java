package application.model;

public class Description {
	private int id;
	private String type, service, productDesc;
	
	public Description() {
		
	};
	
	public Description(int id, String type, String service, String productDesc) {
		this.id = id;
		this.type = type;
		this.service = service;
		this.productDesc = productDesc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	@Override
	public String toString() {
		return type + " " + service + " (" + productDesc + ")";
	}
}
