package com.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Class tracks employee work week
 * @author dannyzorn
 *
 */
public class WorkWeek {
	//init class vars
	private int calendar_week;
	private int fk_employee_id;
	private Date employee_MonStartDateTime;
	private Date employee_MonEndDateTime;
	private Date employee_TueStartDateTime;
	private Date employee_TueEndDateTime;
	private Date employee_WedStartDateTime;
	private Date employee_WedEndDateTime;
	private Date employee_ThuStartDateTime;
	private Date employee_ThuEndDateTime;
	private Date employee_FriStartDateTime;
	private Date employee_FriEndDateTime;
	private Date employee_SatStartDateTime;
	private Date employee_SatEndDateTime;

	/**
	 * Employee work week object class
	 * @param calendar_week
	 * @param fk_employee_id
	 * @param employee_MonStartDateTime
	 * @param employee_MonEndDateTime
	 * @param employee_TueStartDateTime
	 * @param employee_TueEndDateTime
	 * @param employee_WedStartDateTime
	 * @param employee_WedEndDateTime
	 * @param employee_ThuStartDateTime
	 * @param employee_ThuEndDateTime
	 * @param employee_FriStartDateTime
	 * @param employee_FriEndDateTime
	 * @param employee_SatStartDateTime
	 * @param employee_SatEndDateTime
	 */
public WorkWeek(int calendar_week, int fk_employee_id, Date employee_MonStartDateTime, Date employee_MonEndDateTime,
			Date employee_TueStartDateTime, Date employee_TueEndDateTime, Date employee_WedStartDateTime, Date employee_WedEndDateTime,
			Date employee_ThuStartDateTime, Date employee_ThuEndDateTime, Date employee_FriStartDateTime, Date employee_FriEndDateTime,
			Date employee_SatStartDateTime, Date employee_SatEndDateTime) {
		this.calendar_week = calendar_week;
		this.fk_employee_id = fk_employee_id;
		this.setMondayHours(employee_MonStartDateTime,employee_MonEndDateTime);
		this.setTuesdayHours(employee_TueStartDateTime, employee_TueEndDateTime);
		this.setWednesdayHours(employee_WedStartDateTime, employee_WedEndDateTime);
		this.setThursdayHours(employee_ThuStartDateTime, employee_ThuEndDateTime);
		this.setFridayHours(employee_FriStartDateTime, employee_FriEndDateTime);
		this.setSaturdayHours(employee_SatStartDateTime, employee_SatEndDateTime);
	}

	public WorkWeek() {
		
	}
	
	public void setCalendar_week(int calendar_week) {
		this.calendar_week = calendar_week;
	}
	
	public int getCalendar_week() {
		return calendar_week;
	}
	
	public void setFk_employee_id(int fk_employee_id) {
		this.fk_employee_id = fk_employee_id;
	}
	
	public int getFk_employee_id() {
		return fk_employee_id;
	}
	
	public void setMondayHours(Date employee_MonStartDateTime, Date employee_MonEndDateTime) {
		this.employee_MonStartDateTime = employee_MonStartDateTime;
		this.employee_MonEndDateTime = employee_MonEndDateTime;
	}
	public void setTuesdayHours(Date startHour, Date endHour) {
		employee_TueStartDateTime = startHour;
		this.employee_TueEndDateTime = endHour;
	}
	public void setWednesdayHours(Date startHour, Date endHour) {
		this.employee_WedStartDateTime = startHour;
		this.employee_WedEndDateTime = endHour;
	}
	public void setThursdayHours(Date startHour, Date endHour) {
		this.employee_ThuStartDateTime = startHour;
		this.employee_ThuEndDateTime = endHour;
	}
	public void setFridayHours(Date startHour, Date endHour) {
		this.employee_FriStartDateTime = startHour;
		this.employee_FriEndDateTime = endHour;
	}
	public void setSaturdayHours(Date startHour, Date endHour) {
		this.employee_SatStartDateTime = startHour;
		this.employee_SatEndDateTime = endHour;
	}

	public Date getEmployee_MonStartDateTime() {
		return employee_MonStartDateTime;
	}

	public void setEmployee_MonStartDateTime(Date employee_MonStartDateTime) {
		this.employee_MonStartDateTime = employee_MonStartDateTime;
	}

	public Date getEmployee_MonEndDateTime() {
		return employee_MonEndDateTime;
	}

	public void setEmployee_MonEndDateTime(Date employee_MonEndDateTime) {
		this.employee_MonEndDateTime = employee_MonEndDateTime;
	}

	public Date getEmployee_TueStartDateTime() {
		return employee_TueStartDateTime;
	}

	public void setEmployee_TueStartDateTime(Date employee_TueStartDateTime) {
		this.employee_TueStartDateTime = employee_TueStartDateTime;
	}

	public Date getEmployee_TueEndDateTime() {
		return employee_TueEndDateTime;
	}

	public void setEmployee_TueEndDateTime(Date employee_TueEndDateTime) {
		this.employee_TueEndDateTime = employee_TueEndDateTime;
	}

	public Date getEmployee_WedStartDateTime() {
		return employee_WedStartDateTime;
	}

	public void setEmployee_WedStartDateTime(Date employee_WedStartDateTime) {
		this.employee_WedStartDateTime = employee_WedStartDateTime;
	}

	public Date getEmployee_WedEndDateTime() {
		return employee_WedEndDateTime;
	}

	public void setEmployee_WedEndDateTime(Date employee_WedEndDateTime) {
		this.employee_WedEndDateTime = employee_WedEndDateTime;
	}

	public Date getEmployee_ThuStartDateTime() {
		return employee_ThuStartDateTime;
	}

	public void setEmployee_ThuStartDateTime(Date employee_ThuStartDateTime) {
		this.employee_ThuStartDateTime = employee_ThuStartDateTime;
	}

	public Date getEmployee_ThuEndDateTime() {
		return employee_ThuEndDateTime;
	}

	public void setEmployee_ThuEndDateTime(Date employee_ThuEndDateTime) {
		this.employee_ThuEndDateTime = employee_ThuEndDateTime;
	}

	public Date getEmployee_FriStartDateTime() {
		return employee_FriStartDateTime;
	}

	public void setEmployee_FriStartDateTime(Date employee_FriStartDateTime) {
		this.employee_FriStartDateTime = employee_FriStartDateTime;
	}

	public Date getEmployee_FriEndDateTime() {
		return employee_FriEndDateTime;
	}

	public void setEmployee_FriEndDateTime(Date employee_FriEndDateTime) {
		this.employee_FriEndDateTime = employee_FriEndDateTime;
	}

	public Date getEmployee_SatStartDateTime() {
		return employee_SatStartDateTime;
	}

	public void setEmployee_SatStartDateTime(Date employee_SatStartDateTime) {
		this.employee_SatStartDateTime = employee_SatStartDateTime;
	}

	public Date getEmployee_SatEndDateTime() {
		return employee_SatEndDateTime;
	}

	public void setEmployee_SatEndDateTime(Date employee_SatEndDateTime) {
		this.employee_SatEndDateTime = employee_SatEndDateTime;
	}

	private ArrayList<Date> weekHours = new ArrayList<Date>(14);
	private ArrayList<Date> appointments = new ArrayList<Date>();
	public void addAppointment(Date appointment) {
		appointments.add(appointment);
	}
	
	public ArrayList<Date> getAppointments(){
		return appointments;
	}
	
	public void setWorkWeek() {
		weekHours.add(null);
		weekHours.add(null);
		Date[] dates = new Date[]{employee_MonStartDateTime,  employee_MonEndDateTime,  employee_TueStartDateTime,  employee_TueEndDateTime,  employee_WedStartDateTime, employee_WedEndDateTime,  employee_ThuStartDateTime, employee_ThuEndDateTime, employee_FriStartDateTime, employee_FriEndDateTime, employee_SatStartDateTime, employee_SatEndDateTime};
		for (Date d : dates) {
			weekHours.add(d);
		}
	}
	
	public ArrayList<Date> getWorkWeek() {
		return weekHours;
	}
	
	
}
