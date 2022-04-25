package com.model;

/**
 * Class is used to store customer information. customer_id is autogen by db
 * @author dannyzorn
 *
 */
public class Customer {
	//init class vars
	public int customer_id;
	public String customer_fname;
	public String customer_lname;
	public String customer_phonenum;
	public String customer_email;
	
	/**
	 * Customer object class
	 * @param customerFName
	 * @param customerID
	 */
	public Customer(String customer_fname, String customer_lname, String customer_phonenum, String customer_email) {
		this.setCustomer_fname(customer_fname);
		this.setCustomer_lname(customer_lname);
		this.setCustomer_PhoneNum(customer_phonenum);
		this.setCustomer_Email(customer_email);
	}
	
	public Customer() {
		
	}
	
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_fname(String customerID) {
		this.customer_fname = customerID;
	}

	public String getCustomer_fname() {
		return customer_fname;
	}
	
	public void setCustomer_lname(String customer_lname) {
		this.customer_lname = customer_lname;
	}
	
	public String getCustomer_lname() {
		return customer_lname;
	}
	
	public void setCustomer_PhoneNum(String phone_num) {
		customer_phonenum = phone_num;
	}
	
	public String getCustomer_PhoneNum() {
		return customer_phonenum;
	}
	
	public void setCustomer_Email(String email) {
		customer_email = email;
	}
	
	public String getCustomer_Email() {
		return customer_email;
	}
}
