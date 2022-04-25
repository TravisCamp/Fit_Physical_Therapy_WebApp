package com.model;

/**
 * Class stores employee details and login credentials. employee_Id is autogen by db
 * @author dannyzorn
 *
 */
public class Employee {
	//init class vars
	private int employee_Id;
	private int employee_userTyp;
	private String employee_fname;
	private String employee_lname;
	private String employee_uname;
	private String employee_password;
//	//TODO remove
//	private ArrayList<Certification> certifications = new ArrayList<Certification>();
	
	/**
	 * Employee object class
	 * @param employee_Id
	 * @param employee_userTyp
	 * @param employee_fname
	 * @param employee_lname
	 * @param employee_uname
	 * @param employee_password
	 */
	public Employee(int employee_Id, int employee_userTyp, String employee_fname, String employee_lname,
			String employee_uname, String employee_password) {
		this.setEmployee_Id(employee_Id);
		this.setEmployee_userTyp(employee_userTyp);
		this.setEmployee_fname(employee_fname);
		this.setEmployee_lname(employee_lname);
		this.setEmployee_uname(employee_uname);
		this.setEmployee_password(employee_password);
	}
	
	public Employee() {
		
	}

	public void setEmployee_Id(int employee_Id) {
		this.employee_Id = employee_Id;
	}

	public int getEmployee_Id() {
		return employee_Id;
	}

	public void setEmployee_userTyp(int employee_userTyp) {
		this.employee_userTyp = employee_userTyp;
	}

	public int getEmployee_userTyp() {
		return employee_userTyp;
	}

	public void setEmployee_fname(String employee_fname) {
		this.employee_fname = employee_fname;
	}

	public String getEmployee_fname() {
		return employee_fname;
	}

	public void setEmployee_lname(String employee_lname) {
		this.employee_lname = employee_lname;
	}

	public String getEmployee_lname() {
		return employee_lname;
	}

	public void setEmployee_uname(String employee_uname) {
		this.employee_uname = employee_uname;
	}

	public String getEmployee_uname() {
		return employee_uname;
	}

	public void setEmployee_password(String employee_password) {
		this.employee_password = employee_password;
	}

	public String getEmployee_password() {
		return employee_password;
	}
}
