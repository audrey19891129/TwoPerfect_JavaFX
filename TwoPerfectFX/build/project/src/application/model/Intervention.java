package application.model;

import java.util.ArrayList;
import java.util.Collection;


import java.util.ArrayList;
import java.util.Collection;

public class Intervention {

	public int id;
	public int ticketId;
	public int technicianId;
	public String date;
	public String clientAvailStart;
	public String clientAvailEnd;
	public String punchIn;
	public String punchOut;
	public String note;
	public String status;
	public String comment;
	public String communicationType;
	//public ArrayList<Communication> communications;
	
	public Intervention() {
		//this.communications = new ArrayList<Communication>();
	}


	public Intervention(int id, int ticketId, int technicianId, String date, String clientAvailStart,
			String clientAvailEnd, String punchIn, String punchOut, String note, String status, String comment,
			String communicationType) {
		super();
		this.id = id;
		this.ticketId = ticketId;
		this.technicianId = technicianId;
		this.date = date;
		this.clientAvailStart = clientAvailStart;
		this.clientAvailEnd = clientAvailEnd;
		this.punchIn = punchIn;
		this.punchOut = punchOut;
		this.note = note;
		this.status = status;
		this.comment = comment;
		this.communicationType = communicationType;
	}

	public String getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(String communicationType) {
		this.communicationType = communicationType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getClientAvailStart() {
		return clientAvailStart;
	}

	public void setClientAvailStart(String clientAvailStart) {
		this.clientAvailStart = clientAvailStart;
	}

	public String getClientAvailEnd() {
		return clientAvailEnd;
	}

	public void setClientAvailEnd(String clientAvailEnd) {
		this.clientAvailEnd = clientAvailEnd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public int getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(int technicianId) {
		this.technicianId = technicianId;
	}

	public String getPunchIn() {
		return punchIn;
	}

	public void setPunchIn(String punchIn) {
		this.punchIn = punchIn;
	}

	public String getPunchOut() {
		return punchOut;
	}

	public void setPunchOut(String punchOut) {
		this.punchOut = punchOut;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
/*
	public ArrayList<Communication> getCommunications() {
		return communications;
	}

	public void setCommunications(ArrayList<Communication> communications) {
		this.communications = communications;
	}
*/

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	@Override
	public String toString() {
		return "Intervention [id=" + id + ", ticketId=" + ticketId + ", technicianId=" + technicianId + ", date=" + date
				+ ", clientAvailStart=" + clientAvailStart + ", clientAvailEnd=" + clientAvailEnd + ", punchIn="
				+ punchIn + ", punchOut=" + punchOut + ", note=" + note + ", status=" + status + ", comment=" + comment
				+ ", communicationType=" + communicationType + "]";
	}
}
