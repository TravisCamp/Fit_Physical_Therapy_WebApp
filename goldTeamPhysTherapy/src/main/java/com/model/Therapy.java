package com.model;

/**
 * Class creates a therapy object. therapy_id is autogen
 * @author dannyzorn
 *
 */
public class Therapy {
	//init class vars
	public int therapy_id;
	public String therapy_name;
	public String therapy_desc;
	
	/**
	 * Therapy object class
	 * @param therapy_name
	 * @param therapy_desc
	 */
	public Therapy(String therapy_name, String therapy_desc) {
		this.setTherapy_name(therapy_name);
		this.setTherapy_desc(therapy_desc);
	}
	
	public Therapy() {
		
	}
	
	public void setTherapy_desc(String therapy_desc) {
		this.therapy_desc = therapy_desc;
	}
	
	public String getTherapy_desc() {
		return therapy_desc;
	}
	
	public void setTherapy_id(int therapy_id) {
		this.therapy_id = therapy_id;
	}

	public int getTherapy_id() {
		return therapy_id;
	}

	public void setTherapy_name(String therapy_name) {
		this.therapy_name = therapy_name;
	}
	
	public String getTherapy_name() {
		return therapy_name;
	}
}
