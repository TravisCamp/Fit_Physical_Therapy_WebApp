package com.model;

import java.util.Calendar;
import java.util.Date;

/**
 * Class stores therapy appointment details. appointment_id is autogen by db
 * @author dannyzorn
 *
 */
public class Appointment {
	//init class vars
	private int appointment_id;
	private int fk_customer_id;
	private int fk_employee_id;
	private int fk_therapy_id;
	private Date appointment_startDateTime;
	private Date appointment_endDateTime;
	private String appointment_desc;
	
	/**
	 * Appointment object class
	 * @param fk_customer_id
	 * @param fk_employee_id
	 * @param fk_therapy_id
	 * @param appointment_startDateTime
	 */
	public Appointment(int fk_customer_id, int fk_employee_id,int fk_therapy_id, Date appointment_startDateTime ) {
		this.setFk_customer_id(fk_customer_id);
		this.setFk_employee_id(fk_employee_id);
		this.setFk_therapy_id(fk_therapy_id);
		this.setAppointment_startDateTime(appointment_startDateTime);
	}
	
	public Appointment() {
		
	}
	
	public void setAppointmentEndDate() { //Going to be implemented soon. Going to be set one hour later
		Calendar temp = Calendar.getInstance();
		temp.setTime(appointment_startDateTime);
		temp.add(Calendar.HOUR_OF_DAY, 1);
		
		appointment_endDateTime = temp.getTime();
	}
	
	public void setAppointment_id(int appointmentID) {
		this.appointment_id = appointmentID;
	}
	
	public int getAppointment_id() {
		return appointment_id;
	}
	
	public void setFk_customer_id(int fk_customer_id) {
		this.fk_customer_id = fk_customer_id;
	}

	public int getFk_customer_id() {
		return fk_customer_id;
	}

	public void setFk_employee_id(int fk_employee_id) {
		this.fk_employee_id = fk_employee_id;
	}
	
	public int getFk_employee_id() {
		return fk_employee_id;
	}

	public void setFk_therapy_id(int fk_therapy_id) {
		this.fk_therapy_id = fk_therapy_id;
	}
	
	public int getFk_therapy_id() {
		return fk_therapy_id;
	}

	public void setAppointment_startDateTime(Date appointment_startDateTime) {
		this.appointment_startDateTime = appointment_startDateTime;
	}
	
	public Date getAppointment_startDateTime() {
		return appointment_startDateTime;
	}
	
	public void setAppointment_endDateTime(Date appointment_endDateTime) {
		this.appointment_endDateTime = appointment_endDateTime;
	}
	
	public Date getAppointment_endDateTime() { 
		return appointment_endDateTime;
	}

	public void setAppointment_desc(String appointment_desc) {
		this.appointment_desc = appointment_desc;
	}
	
	public String getAppointment_desc() {
		return appointment_desc;
	}
}
