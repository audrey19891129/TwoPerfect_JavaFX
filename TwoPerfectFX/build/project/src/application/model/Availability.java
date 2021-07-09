package application.model;

public class Availability {

	private int id, employeeId;
	private String day, startTime, endTime, startDate, endDate;
	
	public Availability() {
		
	};
	
	public Availability(int id, int employeeId, String day, String startTime, String endTime, String startDate,
			String endDate) {
		this.id = id;
		this.employeeId = employeeId;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
		return "Availability [id=" + id + ", employeeId=" + employeeId + ", day=" + day + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
}
