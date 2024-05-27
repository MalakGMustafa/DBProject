package application;

import java.util.Date;

public class requests2member {

	private int requestsid;
	private int memberid;
	private Date dateofregitration;
	private Date registrationexpirydate;
	private String note;
	
	
	public requests2member(int requestsid, int memberid, Date dateofregitration, Date registrationexpirydate,
			String note) {
		super();
		this.requestsid = requestsid;
		this.memberid = memberid;
		this.dateofregitration = dateofregitration;
		this.registrationexpirydate = registrationexpirydate;
		this.note = note;
	}


	public int getRequestid() {
		return requestsid;
	}


	public void setRequestid(int requestsid) {
		this.requestsid = requestsid;
	}


	public int getMemberid() {
		return memberid;
	}


	public void setMemberid(int memberid) {
		this.memberid = memberid;
	}


	public Date getDateofregitration() {
		return dateofregitration;
	}


	public void setDateofregitration(Date dateofregitration) {
		this.dateofregitration = dateofregitration;
	}


	public Date getRegistrationexpirydate() {
		return registrationexpirydate;
	}


	public void setRegistrationexpirydate(Date registrationexpirydate) {
		this.registrationexpirydate = registrationexpirydate;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}
}
