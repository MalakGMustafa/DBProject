package application;

public class Library {

	private int libraryID;
	private String name;
	private String address;
	
	
	public Library(int libraryID, String name, String address) {
		super();
		this.libraryID = libraryID;
		this.name = name;
		this.address = address;
	}


	public int getLibraryID() {
		return libraryID;
	}


	public void setLibraryID(int libraryId) {
		this.libraryID = libraryId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
