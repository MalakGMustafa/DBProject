package application;

public class Books {

	private int bookID;
	private String title;
	private String auther;
	private String bookstatus;
	private int libraryid;
	private int publisherid;
	
	
	public Books(int bookID, String title, String auther, String bookstatus, int libraryid, int publisherid) {
		super();
		this.bookID = bookID;
		this.title = title;
		this.auther = auther;
		this.bookstatus = bookstatus;
		this.libraryid = libraryid;
		this.publisherid = publisherid;
	}


	public int getBookID() {
		return bookID;
	}


	public void setBookID(int bookID) {
		this.bookID = bookID;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAuther() {
		return auther;
	}


	public void setAuther(String auther) {
		this.auther = auther;
	}


	public String getBookstatus() {
		return bookstatus;
	}


	public void setBookstatus(String bookstatus) {
		this.bookstatus = bookstatus;
	}


	public int getLibraryid() {
		return libraryid;
	}


	public void setLibraryid(int libraryid) {
		this.libraryid = libraryid;
	}


	public int getPublisherid() {
		return publisherid;
	}


	public void setPublisherid(int publisherid) {
		this.publisherid = publisherid;
	}
}
