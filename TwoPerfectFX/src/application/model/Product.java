package application.model;

public class Product {
	private int id;
	private String type, subtype, description;
	
	public Product() {
		
	};
	
	public Product(int id, String type, String subtype, String description) {
		this.id = id;
		this.type = type;
		this.subtype = subtype;
		this.description = description;
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

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", type=" + type + ", subtype=" + subtype + ", description=" + description + "]";
	}
	
}
