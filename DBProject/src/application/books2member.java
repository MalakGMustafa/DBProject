package application;

import java.util.Date;

public class books2member {

	private int bookid;
	private int memberid;
	private Date reservedate;
	private Date returndate;
	private Date duedate;
	
	
	public books2member(int bookid, int memberid, Date reservedate, Date returndate, Date duedate) {
		super();
		this.bookid = bookid;
		this.memberid = memberid;
		this.reservedate = reservedate;
		this.returndate = returndate;
		this.duedate = duedate;
	}


	public int getBookid() {
		return bookid;
	}


	public void setBookid(int bookid) {
		this.bookid = bookid;
	}


	public int getMemberid() {
		return memberid;
	}


	public void setMemberid(int memberid) {
		this.memberid = memberid;
	}


	public Date getReservedate() {
		return reservedate;
	}


	public void setReservedate(Date reservedate) {
		this.reservedate = reservedate;
	}


	public Date getReturndate() {
		return returndate;
	}


	public void setReturndate(Date returndate) {
		this.returndate = returndate;
	}


	public Date getDuedate() {
		return duedate;
	}


	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}
}
