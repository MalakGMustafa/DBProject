package application;

public class Sponsor {

	private int sponsorID;
	private String name;
	private int phonenumber;
	private String occupation;
	private String address;
	private int SNN;
	private String workplace;
	private String email;
	
	
	public Sponsor(int sponsorID, String name, int phonenumber, String occupation, String address, int sNN,
			String workplace, String email) {
		super();
		this.sponsorID = sponsorID;
		this.name = name;
		this.phonenumber = phonenumber;
		this.occupation = occupation;
		this.address = address;
		SNN = sNN;
		this.workplace = workplace;
		this.email = email;
	}


	public int getSponsorID() {
		return sponsorID;
	}


	public void setSponsorID(int sponsorID) {
		this.sponsorID = sponsorID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getPhonenumber() {
		return phonenumber;
	}


	public void setPhonenumber(int phonenumber) {
		this.phonenumber = phonenumber;
	}


	public String getOccupation() {
		return occupation;
	}


	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public int getSNN() {
		return SNN;
	}


	public void setSNN(int sNN) {
		SNN = sNN;
	}


	public String getWorkplace() {
		return workplace;
	}


	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
}
