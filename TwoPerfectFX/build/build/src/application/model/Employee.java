package application.model;

import java.util.ArrayList;
import java.util.Collection;

public class Employee {
	int id;
	public String lastname;
	public String firstname;
	public String phone;
	public String email;
	public String title;
	public String username;
	public String password;
	public String status;
	public String photo;
	public ArrayList<Availability> availability;
	public ArrayList<Holiday> holidays;
	public SQLite sqlite;
	
	public Employee() {
		this.availability = new ArrayList<Availability>();
		this.holidays = new ArrayList<Holiday>();
	}
	
	public Employee(int id, String lastname, String firstname, String phone, String email, String title,
			String username, String password, String status, String photo, SQLite sqlite) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.phone = phone;
		this.email = email;
		this.title = title;
		this.username = username;
		this.password = password;
		this.status = status;
		this.photo = photo;
		this.sqlite = sqlite;
	}

	public Employee(int id, String lastname, String firstname, String phone, String email, String title,
			String username, String password, String status, String photo) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.phone = phone;
		this.email = email;
		this.title = title;
		this.username = username;
		this.password = password;
		this.status = status;
		this.photo = photo;
	}


	public Employee(String lastname, String firstname, String phone, String email, String title, String username,
			String password, String status, String photo) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.phone = phone;
		this.email = email;
		this.title = title;
		this.username = username;
		this.password = password;
		this.status = status;
		this.photo = photo;
	}


	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Availability> getAvailability() {
		return availability;
	}

	public void setAvailability(ArrayList<Availability> availability) {
		this.availability = availability;
	}

	public ArrayList<Holiday> getHolidays() {
		return holidays;
	}

	public void setHolidays(ArrayList<Holiday> holidays) {
		this.holidays = holidays;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SQLite getSqlite() {
		return sqlite;
	}

	public void setSqlite(SQLite sqlite) {
		this.sqlite = sqlite;
	}

}
