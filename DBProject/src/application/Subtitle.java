package application;

public class Subtitle {

	private int bookid;
	private String booksubtitle;
	private int publisherid;
	
	
	public Subtitle(int bookid, String booksubtitle, int publisherid) {
		super();
		this.bookid = bookid;
		this.booksubtitle = booksubtitle;
		this.publisherid = publisherid;
	}


	public int getBookid() {
		return bookid;
	}


	public void setBookid(int bookid) {
		this.bookid = bookid;
	}


	public String getBooksubtitle() {
		return booksubtitle;
	}


	public void setBooksubtitle(String booksubtitle) {
		this.booksubtitle = booksubtitle;
	}


	public int getPublisherid() {
		return publisherid;
	}


	public void setPublisherid(int publisherid) {
		this.publisherid = publisherid;
	}
}
