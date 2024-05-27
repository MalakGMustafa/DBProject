package application;

public class Publisher {

	private int publisherID;
	private String place;
	private String country;
	private int yearofpublication;
	
	
	public Publisher(int publisherID, String place, String country, int yearofpublication) {
		super();
		this.publisherID = publisherID;
		this.place = place;
		this.country = country;
		this.yearofpublication = yearofpublication;
	}


	public int getPublisherID() {
		return publisherID;
	}


	public void setPublisherID(int publisherID) {
		this.publisherID = publisherID;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public int getYearofpublication() {
		return yearofpublication;
	}


	public void setYearofpublication(int yearofpublication) {
		this.yearofpublication = yearofpublication;
	}
}
