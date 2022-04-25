package com.model;

/**
 * Class is for a therapy certification. Includes therapy id that it certifies. 
 * Certification_id is autogen by db
 * @author dannyzorn
 *
 */
public class Certification {
	//init class vars
	private int certification_id;
	private int fk_therapy_id;
	private String certification_name;
	
	/**
	 * Therapy Certification object class
	 * @param certification_id
	 * @param certification_name
	 * @param fk_therapy_id
	 */
	public Certification( int certification_id, int therapy_id, String certification_name ) {
		this.setCertification_id(certification_id);
		this.setCertification_name(certification_name);
		this.setFk_therapy_id(therapy_id);
	}
	
	public Certification() {
		
	}
	
	public void setCertification_id(int certification_id) {
		this.certification_id = certification_id;
	}

	public int getCertification_id() {
		return certification_id;
	}

	public void setFk_therapy_id(int fk_therapy_id) {
		this.fk_therapy_id = fk_therapy_id;
	}

	public int getFk_therapy_id() {
		return fk_therapy_id;
	}

	public void setCertification_name(String certification_name) {
		this.certification_name = certification_name;
	}

	public String getCertification_name() {
		return certification_name;
	}
}
