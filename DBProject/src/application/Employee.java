package application;

public class Employee {

	private int employeeID;
	private String name;
	private double salary;
	private int phonenumber;
	private int libraryid;
	
	
	public Employee(int employeeID, String name, double salary, int phonenumber, int libraryid) {
		super();
		this.employeeID = employeeID;
		this.name = name;
		this.salary = salary;
		this.phonenumber = phonenumber;
		this.libraryid = libraryid;
	}


	public int getEmployeeID() {
		return employeeID;
	}


	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getSalary() {
		return salary;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}


	public int getPhonenumber() {
		return phonenumber;
	}


	public void setPhonenumber(int phonenumber) {
		this.phonenumber = phonenumber;
	}


	public int getLibraryid() {
		return libraryid;
	}


	public void setLibraryid(int libraryid) {
		this.libraryid = libraryid;
	}
}
