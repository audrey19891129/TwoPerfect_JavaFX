package application.model;

public class Holiday {
	private int id, employeeId;
	private String startDate, endDate;
	
	public Holiday() {
		
	}

	public Holiday(int id, int employeeId, String startDate, String endDate) {
		this.id = id;
		this.employeeId = employeeId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Holiday [id=" + id + ", employeeId=" + employeeId + ", startDate=" + startDate + ", endDate=" + endDate
				+ "]";
	};
	
}
