package application;

public class Member {

	private int memberID;
	private String name;
	private int phonenumber;
	private String maritalstatus;
	private String gender;
	private int SNN;
	private String branch;
	private String email;
	private int sponsorid;
	
	
	public Member(int memberID, String name, int phonenumber, String maritalstatus, String gender, int sNN,
			String branch, String email, int sponsorid) {
		super();
		this.memberID = memberID;
		this.name = name;
		this.phonenumber = phonenumber;
		this.maritalstatus = maritalstatus;
		this.gender = gender;
		SNN = sNN;
		this.branch = branch;
		this.email = email;
		this.sponsorid = sponsorid;
	}


	public int getMemberID() {
		return memberID;
	}


	public void setMemberID(int memberID) {
		this.memberID = memberID;
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


	public String getMaritalstatus() {
		return maritalstatus;
	}


	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public int getSNN() {
		return SNN;
	}


	public void setSNN(int sNN) {
		SNN = sNN;
	}


	public String getBranch() {
		return branch;
	}


	public void setBranch(String branch) {
		this.branch = branch;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getSponsorid() {
		return sponsorid;
	}


	public void setSponsorid(int sponsorid) {
		this.sponsorid = sponsorid;
	}
}
