package application;

public class Requests {

	private int requestsID;
	private int price;
	private String typeofrequest;
	private int employeeid;
	
	
	public Requests(int requestsID, int price, String typeofrequest, int employeeid) {
		super();
		this.requestsID = requestsID;
		this.price = price;
		this.typeofrequest = typeofrequest;
		this.employeeid = employeeid;
	}


	public int getRequestsID() {
		return requestsID;
	}


	public void setRequestsID(int requestsID) {
		this.requestsID = requestsID;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public String getTypeofrequests() {
		return typeofrequest;
	}


	public void setTypeofrequests(String typeofrequest) {
		this.typeofrequest = typeofrequest;
	}


	public int getEmployeeid() {
		return employeeid;
	}


	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}
}
