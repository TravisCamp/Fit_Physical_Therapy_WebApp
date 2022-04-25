package com.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import jakarta.servlet.http.HttpServletResponse;
import com.model.Appointment;
import com.model.Certification;
import com.model.Customer;
import com.model.Employee;
import com.model.Therapy;
import com.model.WorkWeek;

/**
 * Query class executes SQL statements on database established thru DB_Connector class
 * @author dannyzorn
 *
 */
public class DB_Query {
	//init class vars
	Connection l_dbconnector = new DB_Connector().connect();
	
	/**
	 * returns arraylist of all Appointments in db
	 * @return
	 */
	public ArrayList<Appointment> getAppArrayList()
	{
		String l_selectAppStr = "SELECT * FROM fitphys_db.Appointment;";
		ResultSet l_rSet = null;
		ArrayList<Appointment> appointmentList = new ArrayList<>();
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectAppStr);
			l_rSet =  l_ps.executeQuery();
	
			while (l_rSet.next()) {
				Appointment l_appointment = new Appointment();
				l_appointment.setAppointment_id(l_rSet.getInt("appointment_id"));
				l_appointment.setFk_customer_id(l_rSet.getInt("Customer_customer_id"));
				l_appointment.setFk_employee_id(l_rSet.getInt("Employee_employee_id"));
				l_appointment.setFk_therapy_id(l_rSet.getInt("Therapy_therapy_id"));
				try {
					String startDateTime = l_rSet.getString("appointment_startDateTime");
					String endDateTime = l_rSet.getString("appointment_endDateTime");
					java.util.Date startDate = sdfInput.parse(startDateTime);
					java.util.Date endDate = sdfInput.parse(endDateTime);
					l_appointment.setAppointment_startDateTime(startDate);
					l_appointment.setAppointment_endDateTime(endDate);
				} catch (ParseException ex) {
					
				}
				
				l_appointment.setAppointment_desc(l_rSet.getString("appointment_desc"));
				appointmentList.add(l_appointment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	
		return appointmentList;
	}
		
	/**
	 * returns arraylist of all Certifications in db
	 * @return
	 */
	public ArrayList<Certification> getCertArrayList() {
		//init local vars
		ResultSet l_rSet = null;
		String l_selectCertStr = "SELECT * FROM fitphys_db.Certification;";	
		ArrayList<Certification> certificationList = new ArrayList<Certification>();
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectCertStr);
			l_rSet = l_ps.executeQuery();
			
			while (l_rSet.next()) {
				Certification l_cert = new Certification();
				l_cert.setCertification_id(l_rSet.getInt("certification_id"));
				l_cert.setFk_therapy_id(l_rSet.getInt("Therapy_therapy_id"));
				l_cert.setCertification_name(l_rSet.getString("certification_name"));
				certificationList.add(l_cert);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return certificationList;
	}
	
	/**
	 * returns arraylist of all Customers in db
	 * @return
	 */
	public ArrayList<Customer> getCustomerArrayList() {
		//init local vars
		ResultSet l_rSet = null;
		String l_selectCustStr = "SELECT * FROM fitphys_db.Customer;";	
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectCustStr);
			l_rSet = l_ps.executeQuery();
			
			while (l_rSet.next()) {
				Customer l_cust = new Customer();
				l_cust.setCustomer_id(l_rSet.getInt("customer_id"));
				l_cust.setCustomer_fname(l_rSet.getString("customer_fname"));
				l_cust.setCustomer_lname(l_rSet.getString("customer_lname"));
				l_cust.setCustomer_PhoneNum(l_rSet.getString("customer_phone_num"));
				l_cust.setCustomer_Email(l_rSet.getString("customer_email"));
				customerList.add(l_cust);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customerList;
	}

	/**
	 * returns arraylist of all Employee in db
	 * @return
	 */
	public ArrayList<Employee> getEmpArrayList() {
		String l_selectEmpStr = "SELECT * FROM fitphys_db.Employee;";
		ResultSet l_rSet = null;
		ArrayList<Employee> employeeList = new ArrayList<>();
	
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectEmpStr);
			l_rSet =  l_ps.executeQuery();
	
			while (l_rSet.next()) {
				Employee l_emp = new Employee();
				l_emp.setEmployee_Id(l_rSet.getInt("employee_id"));
				l_emp.setEmployee_userTyp(l_rSet.getInt("employee_userTyp"));
				l_emp.setEmployee_fname(l_rSet.getString("employee_fname"));
				l_emp.setEmployee_lname(l_rSet.getString("employee_lname"));
				l_emp.setEmployee_uname(l_rSet.getString("employee_uname"));
				l_emp.setEmployee_password(l_rSet.getString("employee_password"));
				employeeList.add(l_emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return employeeList;
	}
	
	/**
	 * returns sorted arraylist of all Employee in db
	 * @return
	 */
	public ArrayList<Employee> getSortedEmpArrayList() {
		String l_selectEmpStr = "SELECT * FROM fitphys_db.Employee ORDER BY employee_fname, employee_lname;";
		ResultSet l_rSet = null;
		ArrayList<Employee> employeeList = new ArrayList<>();
	
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectEmpStr);
			l_rSet =  l_ps.executeQuery();
	
			while (l_rSet.next()) {
				Employee l_emp = new Employee();
				l_emp.setEmployee_Id(l_rSet.getInt("employee_id"));
				l_emp.setEmployee_userTyp(l_rSet.getInt("employee_userTyp"));
				l_emp.setEmployee_fname(l_rSet.getString("employee_fname"));
				l_emp.setEmployee_lname(l_rSet.getString("employee_lname"));
				l_emp.setEmployee_uname(l_rSet.getString("employee_uname"));
				l_emp.setEmployee_password(l_rSet.getString("employee_password"));
				employeeList.add(l_emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return employeeList;
	}
	
	/**
	 * returns arraylist of all type 2 employees in db
	 * @return
	 */
	public ArrayList<Employee> getTherapistArrayList() {
		String l_selectEmpStr = "SELECT * FROM fitphys_db.Employee WHERE employee_userTyp <= 2;";
		ResultSet l_rSet = null;
		ArrayList<Employee> therapistList = new ArrayList<>();
	
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectEmpStr);
			l_rSet =  l_ps.executeQuery();
	
			while (l_rSet.next()) {
				Employee l_emp = new Employee();
				l_emp.setEmployee_Id(l_rSet.getInt("employee_id"));
				l_emp.setEmployee_userTyp(l_rSet.getInt("employee_userTyp"));
				l_emp.setEmployee_fname(l_rSet.getString("employee_fname"));
				l_emp.setEmployee_lname(l_rSet.getString("employee_lname"));
				l_emp.setEmployee_uname(l_rSet.getString("employee_uname"));
				l_emp.setEmployee_password(l_rSet.getString("employee_password"));
				therapistList.add(l_emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return therapistList;
	}
	
	/**
	 * returns sorted arraylist of all type 2 employees in db
	 * @return
	 */
	public ArrayList<Employee> getSortedTherapistArrayList() {
		String l_selectEmpStr = "SELECT * FROM fitphys_db.Employee WHERE employee_userTyp <= 2 ORDER BY employee_fname, employee_lname;";
		ResultSet l_rSet = null;
		ArrayList<Employee> therapistList = new ArrayList<>();
	
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectEmpStr);
			l_rSet =  l_ps.executeQuery();
	
			while (l_rSet.next()) {
				Employee l_emp = new Employee();
				l_emp.setEmployee_Id(l_rSet.getInt("employee_id"));
				l_emp.setEmployee_userTyp(l_rSet.getInt("employee_userTyp"));
				l_emp.setEmployee_fname(l_rSet.getString("employee_fname"));
				l_emp.setEmployee_lname(l_rSet.getString("employee_lname"));
				l_emp.setEmployee_uname(l_rSet.getString("employee_uname"));
				l_emp.setEmployee_password(l_rSet.getString("employee_password"));
				therapistList.add(l_emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return therapistList;
	}

	/**
	 * returns arraylist of all Employees with (argument) certification id in db
	 * @return
	 */
	public ArrayList<Employee> getEmpByCertList(int certId) {
		//init local vars	
		ResultSet l_rSet = null;
		//This will only select employees with the correct certification
		String l_selectEmpCertStr = "SELECT Employee.employee_userTyp, Employee.employee_id, Employee.employee_fname, Employee.employee_lname, Employee.employee_uname, Employee.employee_password, employee_has_certification.Certification_certification_id, Certification.certification_name\r\n"
				+ "FROM employee_has_certification\r\n"
				+ "INNER JOIN Employee ON employee_has_certification.Employee_employee_id = Employee.employee_id\r\n"
				+ "INNER JOIN Certification ON employee_has_certification.Certification_certification_id = Certification.certification_id\r\n"
				+ "WHERE employee_has_certification.Certification_certification_id = " + certId;
		ArrayList<Employee> validEmpList = new ArrayList<>();
		
		//prepare and execute statement
		try {
			Statement statement = l_dbconnector.createStatement();
			l_rSet = statement.executeQuery(l_selectEmpCertStr);
			//I wasn't too sure on where I could write these to, so I just used response.getWriter, that'll be common for most of these
			while (l_rSet.next()) {
				Employee l_emp = new Employee();
				l_emp.setEmployee_Id(l_rSet.getInt("employee_id"));
				l_emp.setEmployee_userTyp(l_rSet.getInt("employee_userTyp"));
				l_emp.setEmployee_fname(l_rSet.getString("employee_fname"));
				l_emp.setEmployee_lname(l_rSet.getString("employee_lname"));
				l_emp.setEmployee_uname(l_rSet.getString("employee_uname"));
				l_emp.setEmployee_password(l_rSet.getString("employee_password"));
				validEmpList.add(l_emp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
 		return validEmpList;
	}
	
	//Return count of how many employees have a certification
	public String getEmpCertCount(int id) {
		//init local vars
		ResultSet l_rSet = null;
		String l_selectEmpCCountStr = "SELECT count(*) FROM fitphys_db.employee_has_certification WHERE employee_has_certification.Certification_certification_id = ?;";
		String l_cCountStr = "";
		
		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectEmpCCountStr);
			l_ps.setInt(1, id);
			
			l_rSet = l_ps.executeQuery();
			l_rSet.next();
			l_cCountStr = l_rSet.getString("count(*)");
		}	catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return l_cCountStr;
	}
	

	
	//Returns employee by id
	//For use by 
	public String getEmployee(int id) 
	{
		String l_selectStr = "SELECT `employee_fname` FROM fitphys_db.Employee WHERE employee_id = ?;";
		ResultSet l_rSet = null;
		String l_empStr = "";
		
		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectStr);
			l_ps.setInt(1,id);
			
			l_rSet = l_ps.executeQuery();
			l_rSet.next();
			l_empStr = l_rSet.getString("employee_fname");
		}	catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return l_empStr;
	}
	
	//Returns certification by id
	//For use by DeleteCertificationServlet
	public int getCertification(String code)
	{
		String l_selectStr = "SELECT `certification_id` FROM fitphys_db.Certification WHERE certification_name = ?;";
		ResultSet l_rSet = null;
		int l_certInt = 0;
		
		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectStr);
			l_ps.setString(1,code);
			l_rSet = l_ps.executeQuery();
			l_rSet.next();
			l_certInt = l_rSet.getInt("certification_id");
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return l_certInt;
		
	}
	
	//Returns therapy name by id
	public String getTherapy(int id)
	{
		String l_selectStr = "SELECT `therapy_name` FROM fitphys_db.Therapy WHERE therapy_id = ?;";
		ResultSet l_rSet = null;
		String l_therStr = "";
		
		try
		{
			PreparedStatement l_ps =  l_dbconnector.prepareStatement(l_selectStr);
			l_ps.setInt(1, id);
			l_rSet = l_ps.executeQuery();
			l_rSet.next();
			l_therStr = l_rSet.getString("therapy_name");
		}	catch (SQLException e) {
			e.printStackTrace();
		} 
		return l_therStr;
	}
	
	
	/**
	 * Returns the formatted string of all employee certification codes
	 * For use by bookAppointments.js and .jsp
	 * @param employee_Id
	 * @return
	 */
	public String getEmployeeCert(int employee_Id) {
		String l_selectStr = "SELECT * FROM fitphys_db.employee_has_certification WHERE Employee_employee_id = "+employee_Id+";";
		ResultSet l_rSet = null;
		String l_empCertStr = "";
		
		try{
			PreparedStatement  l_ps = l_dbconnector.prepareStatement(l_selectStr);
			l_rSet= l_ps.executeQuery();
	
			while (l_rSet.next()){
				l_empCertStr = l_empCertStr+"\""+String.valueOf(l_rSet.getInt("Certification_certification_id"))+"\",";
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
	
		return l_empCertStr;
	}
	
	/**
	 * Returns the formatted string of all employee certification codes
	 * For use by bookAppointments.js and .jsp
	 * @param employee_Id
	 * @return
	 */
	public String getEmployeeWorkDayShift(int employee_Id, String appDate) {
		
		String l_selectStr = "SELECT * FROM fitphys_db.work_week WHERE Employee_employee_id = "+employee_Id+" AND calendar_week = "+employee_Id+";";
		ResultSet l_rSet = null;
		String l_empShiftStr = "";
		
		try{
			PreparedStatement  l_ps = l_dbconnector.prepareStatement(l_selectStr);
			l_rSet= l_ps.executeQuery();
	
			while (l_rSet.next()){
				
				l_empShiftStr = l_empShiftStr+"\""+String.valueOf(l_rSet.getInt("Certification_certification_id"))+"\",";
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
	
		return l_empShiftStr;
	}
	
	public ArrayList<Certification> getEmployeeCertifications(int id)
	{
		String l_selectStr = "SELECT * FROM fitphys_db.employee_has_certification INNER JOIN fitphys_db.Certification ON employee_has_certification.Certification_certification_id = Certification.certification_id WHERE Employee_employee_id = ?;";
		ResultSet l_rSet = null;
		ArrayList<Certification> employeeCertifications = new ArrayList<>();
		
		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectStr);
			
			l_ps.setInt(1,id);
			
			l_rSet= l_ps.executeQuery();
			
			while(l_rSet.next()) {
				Certification l_cert = new Certification();
				l_cert.setCertification_id(l_rSet.getInt("certification_id"));
				l_cert.setFk_therapy_id(l_rSet.getInt("Therapy_therapy_id"));
				l_cert.setCertification_name(l_rSet.getString("certification_name"));
				employeeCertifications.add(l_cert);
			}
		}	catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return employeeCertifications;
	}
	
	//Return all employees from a certification
	public ArrayList<String> getCertificationEmployees(int id)
	{
		String l_selectStr = "SELECT * FROM fitphys_db.employee_has_certification WHERE Certification_certification_id = ?;";
		ResultSet l_rSet = null;
		ArrayList<String> certifciationEmps =  new ArrayList<>();
		
		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectStr);
			
			l_ps.setInt(1,id);
			l_rSet= l_ps.executeQuery();
			while(l_rSet.next()) {
				int empId = l_rSet.getInt("Employee_employee_id");
				int certId = l_rSet.getInt("Certification_certification_id");
				certifciationEmps.add(String.valueOf(empId));
				certifciationEmps.add(String.valueOf(certId));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return certifciationEmps;
	}
	
	
	
	/**
	 * gets all employee's weekly scheduled work hours
	 * @return
	 */
	public ArrayList<WorkWeek> getEmployeeWorkWeekArrayList()
	{
		String l_selectStr = "SELECT * FROM fitphys_db.work_week;";
		ResultSet l_rSet = null;
		ArrayList<WorkWeek> employeeSchedule = new ArrayList<>();
	
		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectStr);
			l_rSet = l_ps.executeQuery();
	
			while (l_rSet.next())
			{
				SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				WorkWeek schedule = new WorkWeek();
				schedule.setCalendar_week(l_rSet.getInt("calendar_week"));
				schedule.setFk_employee_id(l_rSet.getInt("employee_employee_id"));
				for (int i = 3; i < 15; i++) {
					String dateStr = l_rSet.getString(i);
					String dateStr1 = l_rSet.getString(i + 1);
					if (dateStr != null && dateStr1 != null) {
						try {
							java.util.Date startDate = sdfInput.parse(dateStr);
							java.util.Date endDate = sdfInput.parse(dateStr1);
							switch (i) {
							case (3):
								schedule.setMondayHours(startDate, endDate);
								break;
							case (5):
								schedule.setTuesdayHours(startDate, endDate);
								break;
							case (7):
								schedule.setWednesdayHours(startDate, endDate);
								break;
							case (9):
								schedule.setThursdayHours(startDate, endDate);
								break;
							case (11):
								schedule.setFridayHours(startDate, endDate);
								break;
							case (13):
								schedule.setSaturdayHours(startDate, endDate);
								break;
							}
							
						}
						catch (ParseException ex) {
							ex.getMessage();
						}
					}
					i++;	
				}
				schedule.setWorkWeek();
				employeeSchedule.add(schedule);
			}
		}	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return employeeSchedule;
	}
	/**
	 * gets all employee's weekly schedule templates
	 * @return
	 */
	public ArrayList<WorkWeek> getEmployeeWorkWeekTemplateArrayList(){
		String l_selectStr = "SELECT * FROM fitphys_db.work_week WHERE calendar_week = 0;";
		ResultSet l_rSet = null;
		ArrayList<WorkWeek> scheduleTemplates = new ArrayList<>();
	
		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectStr);
			l_rSet = l_ps.executeQuery();
	
			while (l_rSet.next())
			{
				SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				WorkWeek template = new WorkWeek();
				template.setCalendar_week(l_rSet.getInt("calendar_week"));
				template.setFk_employee_id(l_rSet.getInt("employee_employee_id"));
				for (int i = 3; i < 15; i++) {
					String dateStr = l_rSet.getString(i);
					String dateStr1 = l_rSet.getString(i + 1);
					if (dateStr != null && dateStr1 != null) {
						try {
							java.util.Date startDate = sdfInput.parse(dateStr);
							java.util.Date endDate = sdfInput.parse(dateStr1);
							switch (i) {
							case (3):
								template.setMondayHours(startDate, endDate);
								break;
							case (5):
								template.setTuesdayHours(startDate, endDate);
								break;
							case (7):
								template.setWednesdayHours(startDate, endDate);
								break;
							case (9):
								template.setThursdayHours(startDate, endDate);
								break;
							case (11):
								template.setFridayHours(startDate, endDate);
								break;
							case (13):
								template.setSaturdayHours(startDate, endDate);
								break;
							}
							
						}
						catch (ParseException ex) {
							ex.getMessage();
						}
					}
					i++;	
				}
				template.setWorkWeek();
				scheduleTemplates.add(template);
			}
		}	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return scheduleTemplates;
	}

	/**
	 * returns arraylist of all Therapt objects in db
	 * @return
	 */
	public ArrayList<Therapy> getTherapyArrayList() {
		//init local vars
		ResultSet l_rSet = null;
		String l_selectTherapyStr = "SELECT * FROM fitphys_db.Therapy;";	
		ArrayList<Therapy> therapyList = new ArrayList<Therapy>();
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectTherapyStr);
			l_rSet = l_ps.executeQuery();
			
			while (l_rSet.next()) {
				Therapy l_therapy = new Therapy();
				l_therapy.setTherapy_id(l_rSet.getInt("therapy_id"));
				l_therapy.setTherapy_name(l_rSet.getString("therapy_name"));
				l_therapy.setTherapy_desc(l_rSet.getString("therapy_desc"));
				therapyList.add(l_therapy);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return therapyList;
	}
	
	/**
	 * returns sorted arraylist of all Therapy objects in db
	 * @return
	 */
	public ArrayList<Therapy> getSortedTherapyArrayList() {
		//init local vars
		ResultSet l_rSet = null;
		String l_selectTherapyStr = "SELECT * FROM fitphys_db.Therapy ORDER BY therapy_name;";	
		ArrayList<Therapy> therapyList = new ArrayList<Therapy>();
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectTherapyStr);
			l_rSet = l_ps.executeQuery();
			
			while (l_rSet.next()) {
				Therapy l_therapy = new Therapy();
				l_therapy.setTherapy_id(l_rSet.getInt("therapy_id"));
				l_therapy.setTherapy_name(l_rSet.getString("therapy_name"));
				l_therapy.setTherapy_desc(l_rSet.getString("therapy_desc"));
				therapyList.add(l_therapy);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return therapyList;
	}

	/**
	 * get appointment by id
	 * @param appId
	 * @return
	 */
	public Appointment getAppointment(int appId) {
		//set local vars
		Appointment l_appointment = new Appointment();
		ResultSet l_rSet = null;
		SimpleDateFormat DBFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String l_selectAppointmentStr = "SELECT * FROM fitphys_db.Appointment WHERE appointment_id = ?;";
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectAppointmentStr);
			
			l_ps.setInt(1,appId);
			l_rSet =  l_ps.executeQuery();
			
			if (l_rSet.next()) {
				l_appointment.setAppointment_id(l_rSet.getInt("appointment_id"));
				l_appointment.setFk_customer_id(l_rSet.getInt("Customer_customer_id"));
				l_appointment.setFk_employee_id(l_rSet.getInt("Employee_employee_id"));
				l_appointment.setFk_therapy_id(l_rSet.getInt("Therapy_therapy_id"));
				String startDateTime = l_rSet.getString("appointment_startDateTime");
				String endDateTime = l_rSet.getString("appointment_endDateTime");
				java.util.Date startDate = DBFormat.parse(startDateTime);
				java.util.Date endDate = DBFormat.parse(endDateTime);
				l_appointment.setAppointment_startDateTime(startDate);
				l_appointment.setAppointment_endDateTime(endDate);
				
				l_appointment.setAppointment_desc(l_rSet.getString("appointment_desc"));
				return l_appointment;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return l_appointment;
	 }
	
	public ArrayList<Appointment> getEmpAppointmentsByWeek(Employee emp, int calendarWeek){ //Used for schedule change validation
		ArrayList<Appointment> weekAppointments = new ArrayList<Appointment>();
		ResultSet l_rSet = null;
		SimpleDateFormat DBFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String l_selectAppointmentStr = "SELECT * FROM fitphys_db.Appointment WHERE Employee_employee_id = ?;";
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectAppointmentStr);
			
			l_ps.setInt(1, emp.getEmployee_Id());
			l_rSet =  l_ps.executeQuery();
			
			while (l_rSet.next()) {
				String appStartTimeStr = l_rSet.getString("appointment_startDateTime");
				java.util.Date appStartDate = DBFormat.parse(appStartTimeStr);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(appStartDate);
				
				if (calendar.get(Calendar.WEEK_OF_YEAR) == calendarWeek) {
					Appointment l_appointment = new Appointment();
					l_appointment.setAppointment_id(l_rSet.getInt("appointment_id"));
					l_appointment.setFk_customer_id(l_rSet.getInt("Customer_customer_id"));
					l_appointment.setFk_employee_id(l_rSet.getInt("Employee_employee_id"));
					l_appointment.setFk_therapy_id(l_rSet.getInt("Therapy_therapy_id"));
					String startDateTime = l_rSet.getString("appointment_startDateTime");
					String endDateTime = l_rSet.getString("appointment_endDateTime");
					java.util.Date startDate = DBFormat.parse(startDateTime);
					java.util.Date endDate = DBFormat.parse(endDateTime);
					l_appointment.setAppointment_startDateTime(startDate);
					l_appointment.setAppointment_endDateTime(endDate);
					
					l_appointment.setAppointment_desc(l_rSet.getString("appointment_desc"));
					weekAppointments.add(l_appointment);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return weekAppointments;
	}
	 
	/**
	 * returns appointment object based on given parameters
	 * @param therapy
	 * @param therapist
	 * @param customer
	 * @param appointmentTime
	 * @return
	 */
	public Appointment getAppointment(Therapy therapy, Employee therapist, Customer customer, java.util.Date appointmentTime) {
		ResultSet l_rSet = null;
		String l_selectAppointmentStr = "SELECT * FROM fitphys_db.Appointment";	
		String whereStatement = " WHERE ";
		SimpleDateFormat DBFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Boolean parameterFound = false; //Determines if an AND statement is needed due to previous parameters existing
		if (therapy != null) {
			whereStatement.concat("Therapy_therapy_id = " + therapy.getTherapy_id());
			parameterFound = true;
		}
		if (therapist != null) {
			if (parameterFound) {
				whereStatement.concat(" AND ");
			}
			whereStatement.concat("Employee_employee_id = " + therapist.getEmployee_Id());
			parameterFound = true;
		}
		if (customer != null) {
			if (parameterFound) {
				whereStatement.concat(" AND ");
			}
			whereStatement.concat("Customer_customer_id = " + customer.getCustomer_id());
			parameterFound = true;
		}
		if (appointmentTime != null) {
			if (parameterFound) {
				whereStatement.concat(" AND ");
			}
			String date = DBFormat.format(appointmentTime); //Formats date param to String for where Statement
			whereStatement.concat("appointment_startDateTime = " + date);
			parameterFound = true;
		}
		if (parameterFound) { //If any of given parameters are found, where statement string is added to SQL statement. Should always occur
			l_selectAppointmentStr.concat(whereStatement);
		}
		l_selectAppointmentStr.concat(";");
		
		try {
			System.out.println(l_selectAppointmentStr);
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectAppointmentStr);
			l_rSet =  l_ps.executeQuery();
			
			if (l_rSet.next()) {
				Appointment l_appointment = new Appointment();
				l_appointment.setAppointment_id(l_rSet.getInt("appointment_id"));
				l_appointment.setFk_customer_id(l_rSet.getInt("Customer_customer_id"));
				l_appointment.setFk_employee_id(l_rSet.getInt("Employee_employee_id"));
				l_appointment.setFk_therapy_id(l_rSet.getInt("Therapy_therapy_id"));
				try {
					String startDateTime = l_rSet.getString("appointment_startDateTime");
					String endDateTime = l_rSet.getString("appointment_endDateTime");
					java.util.Date startDate = DBFormat.parse(startDateTime);
					java.util.Date endDate = DBFormat.parse(endDateTime);
					l_appointment.setAppointment_startDateTime(startDate);
					l_appointment.setAppointment_endDateTime(endDate);
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
				
				l_appointment.setAppointment_desc(l_rSet.getString("appointment_desc"));
				return l_appointment;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Appointment getAppointmentByParameters(Therapy therapy, Employee therapist, Customer customer, java.util.Date appointmentTime) {
		ResultSet l_rSet = null;
		String l_selectAppointmentStr = "SELECT * FROM fitphys_db.Appointment WHERE Customer_customer_id = ? AND Employee_employee_id = ? AND Therapy_therapy_id = ? AND appointment_endDateTime = ?;";	
		SimpleDateFormat DBFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Appointment l_appointment = new Appointment();

		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectAppointmentStr);

			l_ps.setInt(1,customer.getCustomer_id());
			l_ps.setInt(2,therapist.getEmployee_Id());
			l_ps.setInt(3,therapy.getTherapy_id());
			String date = DBFormat.format(appointmentTime);
			l_ps.setString(4,date);
			l_rSet =  l_ps.executeQuery();
			System.out.println(l_rSet);

			if (l_rSet.next()) {
				l_appointment.setAppointment_id(l_rSet.getInt("appointment_id"));
				l_appointment.setFk_customer_id(l_rSet.getInt("Customer_customer_id"));
				l_appointment.setFk_employee_id(l_rSet.getInt("Employee_employee_id"));
				l_appointment.setFk_therapy_id(l_rSet.getInt("Therapy_therapy_id"));
				String startDateTime = l_rSet.getString("appointment_startDateTime");
				String endDateTime = l_rSet.getString("appointment_endDateTime");
				java.util.Date startDate = DBFormat.parse(startDateTime);
				java.util.Date endDate = DBFormat.parse(endDateTime);
				l_appointment.setAppointment_startDateTime(startDate);
				l_appointment.setAppointment_endDateTime(endDate);

				l_appointment.setAppointment_desc(l_rSet.getString("appointment_desc"));
				return l_appointment;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return l_appointment;
	}

	/**
	 * Gets a specific employee's workWeek template
	 * @param emp
	 * @param calendarWeek
	 * @return
	 */
	public WorkWeek getWorkWeekTemplate(Employee emp) {
		WorkWeek workWeek = new WorkWeek();
		ResultSet l_rSet = null;

		String l_selectAppointmentStr = "SELECT * FROM fitphys_db.work_week WHERE calendar_week = 0 AND Employee_employee_id = ?;";
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectAppointmentStr);
			
			l_ps.setInt(1, emp.getEmployee_Id());
			
			l_rSet =  l_ps.executeQuery();
			if (l_rSet.next()) {
				SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				workWeek.setCalendar_week(l_rSet.getInt(1));
				workWeek.setFk_employee_id(l_rSet.getInt(2));
				
				for (int i = 3; i < 15; i++) {
					String dateStr = l_rSet.getString(i);
					String dateStr1 = l_rSet.getString(i + 1);
					if (dateStr != null && dateStr1 != null) {
						try {
							java.util.Date startDate = sdfInput.parse(dateStr);
							java.util.Date endDate = sdfInput.parse(dateStr1);
							switch (i) {
							case (3):
								workWeek.setMondayHours(startDate, endDate);
								break;
							case (5):
								workWeek.setTuesdayHours(startDate, endDate);
								break;
							case (7):
								workWeek.setWednesdayHours(startDate, endDate);
								break;
							case (9):
								workWeek.setThursdayHours(startDate, endDate);
								break;
							case (11):
								workWeek.setFridayHours(startDate, endDate);
								break;
							case (13):
								workWeek.setSaturdayHours(startDate, endDate);
								break;
							}
							
						}
						catch (ParseException ex) {
							ex.getMessage();
						}
					}
					i++;	
				}
				workWeek.setWorkWeek();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return workWeek;
	}
	
	public Employee getLastAddedEmployee() {
		ResultSet l_rSet = null;
 		//String query, inserts into appointment table (appointment_id, customer_customer_id, employee_employee_id, therapy_therapy_id, appointment_startDateTime, appointment_endDateTime
 		String l_selectAppStr = "SELECT * FROM fitphys_db.Employee ORDER BY employee_id desc limit 1;";
 		Employee emp = new Employee();
 		//prepare and execute statement
 		try {
 			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectAppStr);
			
 			l_rSet = l_ps.executeQuery();
 			
 			if (l_rSet.next()) {
 				emp.setEmployee_Id(l_rSet.getInt("employee_id"));
 				emp.setEmployee_userTyp(l_rSet.getInt("employee_userTyp"));
 				emp.setEmployee_fname(l_rSet.getString("employee_fname"));
 				emp.setEmployee_lname(l_rSet.getString("employee_lname"));
 			}
 			return emp;
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
 		return null;
	}
	
	/**
	 * Executes INSERT Employee prepared statement 
	 * @param emp
	 * @return returned value for error checking
	 */
	public int insert(Employee emp) {
		//init local vars 
		int retVal = -1;
		String l_insertEmpStr = "INSERT INTO `employee` (`employee_userTyp`, `employee_fname`, `employee_lname`, `employee_uname`, `employee_password`) VALUES (?, ?, ?, ?, ?);";
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_insertEmpStr);
			
			l_ps.setInt(1,emp.getEmployee_Id());
			l_ps.setInt(2,emp.getEmployee_userTyp());
			l_ps.setString(3,emp.getEmployee_fname());
			l_ps.setString(4,emp.getEmployee_lname());
			l_ps.setString(5,emp.getEmployee_uname());
			l_ps.setString(6,emp.getEmployee_password());
			
			//assign retVal
			retVal = l_ps.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public int insertEmp(HttpServletResponse response, String fName, String lName, int empCode, String uname, String password) {
		
		//init local Vars
		int retVal= -1;
		String l_insertEmpStr = "INSERT INTO fitphys_db.Employee (`employee_userTyp`, `employee_fname`, `employee_lname`, `employee_uname`, `employee_password`) VALUES (?, ?, ?, ?, ?);";
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_insertEmpStr);
			l_ps.setInt(1, empCode);;
			l_ps.setString(2,fName);
			l_ps.setString(3,lName);
			l_ps.setString(4,uname);
			l_ps.setString(5,password);
			//assign retVal
			retVal = l_ps.executeUpdate();
		}	catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
		
	}
	
	//INSERT EMPLOYEE SCHEDULE for auto-update
	public int insertSchedule(WorkWeek work, Calendar calendarReference) 
	{
			//init local vars
			int retVal = -1;
			String l_insertSchedule = "INSERT INTO fitphys_db.work_week (`calendar_week`, `employee_employee_id`,"
					+ "`employee_MonStartDateTime`, `employee_MonEndDateTime`,"
					+ "`employee_TueStartDateTime`, `employee_TueEndDateTime`,"
					+ "`employee_WedStartDateTime`, `employee_WedEndDateTime`,"
					+ "`employee_ThuStartDateTime`, `employee_ThuEndDateTime`,"
					+ "`employee_FriStartDateTime`, `employee_FriEndDateTime`,"
					+ "`employee_SatStartDateTime`, `employee_SatEndDateTime`) VALUES"
					+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			//prepare and execute statement
			try 
			{
				PreparedStatement l_ps = l_dbconnector.prepareStatement(l_insertSchedule);
				
				SimpleDateFormat dbInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				ArrayList<java.util.Date> hours = work.getWorkWeek();
				ArrayList<String> dates = new ArrayList<String>();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(calendarReference.getTime());
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				for (int i = 2; i < 13; i++) {
					if (hours.get(i) != null) {
						String dateTemp = dbInput.format(hours.get(i));
						int hourOfDay = Integer.parseInt(dateTemp.substring(11, 13));
						calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
						dates.add(dbInput.format(calendar.getTime()));
						
						dateTemp = dbInput.format(hours.get(i + 1));
						hourOfDay = Integer.parseInt(dateTemp.substring(11, 13));
						calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
						dates.add(dbInput.format(calendar.getTime()));
						
						
						if (calendar.get(Calendar.DAY_OF_WEEK) != 7) {
							calendar.add(Calendar.DAY_OF_WEEK, 1); //Adds a day to the week
						}
					}
					else {
						String date = null;
						dates.add(date);
						dates.add(date);
						if (calendar.get(Calendar.DAY_OF_WEEK) != 7) {
							calendar.add(Calendar.DAY_OF_WEEK, 1); //Adds a day to the week
						}
					}
					i++;
				}
				l_ps.setInt(1,calendarReference.get(Calendar.WEEK_OF_YEAR));
				l_ps.setInt(2,work.getFk_employee_id());
				l_ps.setString(3, dates.get(0));
				l_ps.setString(4, dates.get(1));
				l_ps.setString(5, dates.get(2));
				l_ps.setString(6, dates.get(3));
				l_ps.setString(7, dates.get(4));
				l_ps.setString(8, dates.get(5));
				l_ps.setString(9, dates.get(6));
				l_ps.setString(10, dates.get(7));
				l_ps.setString(11, dates.get(8));
				l_ps.setString(12, dates.get(9));
				l_ps.setString(13, dates.get(10));
				l_ps.setString(14, dates.get(11));
				retVal = l_ps.executeUpdate();;
			}	catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			return retVal;
	}
	
	

	/**
	 * Inserts a customer into the DB
	 * @param cust
	 * @return
	 */
	public int insertCustomer(Customer cust) {
		//init local vars
 		int retVal = -1;
 		//String query, inserts into appointment table (appointment_id, customer_customer_id, employee_employee_id, therapy_therapy_id, appointment_startDateTime, appointment_endDateTime
 		String l_selectAppStr = "INSERT INTO fitphys_db.Customer (`customer_id`, `customer_fname`, `customer_lname`, `customer_phone_num`, `customer_email`) VALUES (?, ?, ?, ?, ?);";
 		
 		//prepare and execute statement
 		try {
 			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectAppStr);
 			
 			l_ps.setInt(1, cust.getCustomer_id());
 			l_ps.setString(2, cust.getCustomer_fname());
 			l_ps.setString(3, cust.getCustomer_lname());
 			l_ps.setString(4, cust.getCustomer_PhoneNum());
 			l_ps.setString(5, cust.getCustomer_Email());
 			
			//assign retVal
 			retVal = l_ps.executeUpdate();
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
 		return retVal;
	}
	
	/**
	 * Sets the borrowed customer's ID to the latest ID added into the DB (required for appointment query)
	 * @param cust
	 * @return
	 */
	public Customer setLastCustomerID(Customer cust) {
		//init local vars

 		ResultSet l_rSet = null;
 		//String query, inserts into appointment table (appointment_id, customer_customer_id, employee_employee_id, therapy_therapy_id, appointment_startDateTime, appointment_endDateTime
 		String l_selectAppStr = "SELECT * FROM fitphys_db.Customer ORDER BY customer_id desc limit 1;";
 		
 		//prepare and execute statement
 		try {
 			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectAppStr);
			
 			l_rSet = l_ps.executeQuery();
 			
 			while (l_rSet.next()) {
 				cust.setCustomer_id(l_rSet.getInt("customer_id"));
 			}
 			return cust;
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
 		return null;
	}

	/**
	 * Insert new appointment into sql
	 * @param app
	 * @param cust 
	 * @param cust
	 * @param emp
	 * @param ther
	 * @return
	 */
 	public int insertAppointment(Appointment app, Customer cust) {
 		//init local vars
 		int retVal = -1;
 		//String query, inserts into appointment table (appointment_id, customer_customer_id, employee_employee_id, therapy_therapy_id, appointment_startDateTime, appointment_endDateTime
 		String l_selectAppStr = "INSERT INTO fitphys_db.Appointment (`appointment_id`, `Customer_customer_id`," 
 		+ "`Employee_employee_id`, `Therapy_therapy_id`,`appointment_startDateTime`,`appointment_endDateTime`,"
 		+ "`appointment_desc`) VALUES (?, ?, ?, ?, ?, ?, ?);";
 		
 		//prepare and execute statement ' `
 		try {
 			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectAppStr);
 			SimpleDateFormat dbInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 			String startTime = dbInput.format(app.getAppointment_startDateTime());
 			String endTime = dbInput.format(app.getAppointment_endDateTime());
 			
 			l_ps.setInt(1, app.getAppointment_id());
 			l_ps.setInt(2, cust.getCustomer_id());
 			l_ps.setInt(3, app.getFk_employee_id());
 			l_ps.setInt(4, app.getFk_therapy_id());
 			l_ps.setString(5, startTime);
 			l_ps.setString(6, endTime);
 			l_ps.setString(7, app.getAppointment_desc());
 			
			//assign retVal
 			retVal = l_ps.executeUpdate();
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
 		return retVal;
 	}

 	/**
 	 * Insert certification for employee into database
 	 * Used by AddEmpCertServlet.java
 	 * @param cert
 	 * @param emp
 	 * @return
 	 */
	public int insertCertification(HttpServletResponse response,int id, String fName) {
		//init local vars
		int retVal = -1;
		//String query, inserts into employee_has_certification table (employee_id, certification_id)
		String l_selectCertStr =  "INSERT INTO fitphys_db.employee_has_certification (`Employee_employee_id`, `Certification_certification_id`) VALUES ((SELECT `employee_id` FROM fitphys_db.Employee WHERE `employee_fName` = ?), ?);";
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectCertStr);
			
			l_ps.setString(1,fName);
			l_ps.setInt(2,id);
			
			//assign retVal
			retVal = l_ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}

	/**
	 * Insert a new certification
	 * @param cert
	 * @param ther
	 * @return
	 */
	//Insert a new certification
	public int insertNewCertification(Certification cert, Therapy ther)
	{

		//String query, inserts into certification table (cert_id, therapy_therapy_id, certification_name
		String l_insertStr = "INSERT INTO fitphys_db.Certification (`certification_id`, `therapy_therapy_id`, `certification_name`) VALUES (?, ?, ?);";
		int retVal = -1;

		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_insertStr);
			l_ps.setInt(1,cert.getCertification_id());
			l_ps.setInt(2,ther.getTherapy_id());
			l_ps.setString(3,cert.getCertification_name());

			//assign retVal
			retVal = l_ps.executeUpdate();
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;
	}
	
	//Insert a new certification based off of servlet response
	public int insertNewCertification(HttpServletResponse response, String certName, String therName)
	{

		//String query, inserts into certification table (cert_id, therapy_therapy_id, certification_name
		String l_insertStr = "INSERT INTO fitphys_db.Certification (`therapy_therapy_id`,`certification_name`) VALUES ((SELECT `therapy_id` FROM therapy WHERE `therapy_name` = ?),?);";
		int retVal = -1;

		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_insertStr);
			l_ps.setString(1,therName);
			l_ps.setString(2,certName);

			//assign retVal
			retVal = l_ps.executeUpdate();
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;
	}
	
	//Insert a new therapy based on input from certifications.jsp
	public void insertTherapy(HttpServletResponse response,String therDesc, String therName) 
	{
		//init local vars
		//String query, inserts new certification name into certification, and a new description and therapy name into therapies
		String l_insertTher = "INSERT INTO fitphys_db.Therapy (`therapy_name`, `therapy_desc`) VALUES (?, ?);";

		//prepare and execute statement
		try 
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_insertTher);

			l_ps.setString(1,therName);
			l_ps.setString(2,therDesc);

			l_ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int insertBlankTemplate(Employee emp) {
		String l_insertStr = "INSERT INTO fitphys_db.work_week (calendar_week, Employee_employee_id) VALUES (0,?);";
		int retVal = -1;

		//calendar_week, Employee_employee_id, employee_MonStartDateTime, employee_MonEndDateTime, employee_TueStartDateTime, employee_TueEndDateTime, employee_WedStartDateTime, employee_WedEndDateTime, employee_ThuStartDateTime, employee_ThuEndDateTime, employee_FriStartDateTime, employee_FriEndDateTime, employee_SatStartDateTime, employee_SatEndDateTime
		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_insertStr);
			l_ps.setInt(1,emp.getEmployee_Id());

			//assign retVal
			retVal = l_ps.executeUpdate();
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;
		
	}
	
	public int updateCert(HttpServletResponse response, String therDesc, String therName, String certName) {
		//init local vars
		int retVal = -1;
		String l_insertStr = "UPDATE fitphys_db.Certification JOIN fitphys_db.Therapy ON certification.therapy_therapy_id = therapy.therapy_id SET `therapy_name` = ?, `therapy_desc` = ? WHERE (`certification_name` = ?);";
		System.out.println(therName);
		System.out.println(therDesc);
		System.out.println(certName);
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_insertStr);

			l_ps.setString(1,therName);
			l_ps.setString(2,therDesc);
			l_ps.setString(3,certName);

			//assign retVal
			retVal = l_ps.executeUpdate();
		}	catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;

	}
	
	public int updateEmp(HttpServletResponse response, int id, String lName, String fName, int userType, String uname, String password) {
		//init local vars
		int retVal = -1;
		String l_insertStr = "UPDATE fitphys_db.Employee SET `employee_fname`= ?, `employee_lname` = ?, `employee_userTyp` = ?, `employee_uname` = ?, `employee_password` = ? WHERE (`employee_id` = ?);";
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_insertStr);
			
			l_ps.setString(1,fName);
			l_ps.setString(2,lName);
			l_ps.setInt(3,userType);
			l_ps.setString(4,uname);
			l_ps.setString(5,password);
			l_ps.setInt(6,id);
			
			
			//assign retVal
			retVal = l_ps.executeUpdate();
		}	catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public int updateEmp(Employee emp) {
		//init local vars
		int retVal = -1;
		String l_insertStr = "UPDATE fitphys_db.Employee SET `employee_fname`= ?, `employee_lname` = ?, `employee_userTyp` = ?, `employee_uname` = ?, `employee_password` = ? WHERE (`employee_id` = ?);";
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_insertStr);
			
			l_ps.setString(1,emp.getEmployee_fname());
			l_ps.setString(2,emp.getEmployee_lname());
			l_ps.setInt(3,emp.getEmployee_userTyp());
			l_ps.setString(4,emp.getEmployee_uname());
			l_ps.setString(5,emp.getEmployee_password());
			l_ps.setInt(6,emp.getEmployee_Id());
			
			
			//assign retVal
			retVal = l_ps.executeUpdate();
		}	catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	/**
	 * updates an employee's workWeek to edit the hours for a day (typically to call off for the day)
	 * @param response
	 * @param emp
	 * @param week
	 * @param appts
	 * @return
	 */
	public int updateWorkDay(HttpServletResponse response, Employee emp, WorkWeek week, Calendar date, String startHour, String endHour) {
		int retVal = -1;
		
		String l_updateStr = "UPDATE fitphys_db.work_week SET ";
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case(2):
			l_updateStr.concat("employee_MonStartDateTime = ?, employee_SunEndDateTime = ?");
			break;
		case(3):
			l_updateStr.concat("employee_TueStartDateTime = ?, employee_TueEndDateTime = ?");
			break;
		case(4):
			l_updateStr.concat("employee_WedStartDateTime = ?, employee_WedEndDateTime = ?");
			break;
		case(5):
			l_updateStr.concat("employee_ThuStartDateTime = ?, employee_ThuEndDateTime = ?");
			break;
		case(6):
			l_updateStr.concat("employee_FriStartDateTime = ?, employee_FriEndDateTime = ?");
			break;
		case(7):
			l_updateStr.concat("employee_SatStartDateTime = ?, employee_SatEndDateTime = ?");
			break;
		}
		
		l_updateStr.concat("WHERE calendar_week = ? AND Employee_employee_id = ?;");
		
		
		//`employee_fname`= ?, `employee_lname` = ?, `employee_userTyp` = ? WHERE (`employee_id` = ?);"
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_updateStr);
			
			l_ps.setString(1, startHour);
			l_ps.setString(2, endHour);
			l_ps.setInt(3, week.getCalendar_week());
			l_ps.setInt(4, emp.getEmployee_Id());
			
			//assign retVal
			retVal = l_ps.executeUpdate();
		}	catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retVal;
	}
	/**
	 * Updates an employee's workWeek template in the DB
	 * @param response
	 * @param emp
	 * @param hoursArrList
	 * @return
	 */
	public int updateWorkTemplate(HttpServletResponse response, Employee emp, ArrayList<String> hoursArrList) {
		int retVal = -1;
		String l_updateStr = "UPDATE `fitphys_db`.`work_week` SET `employee_MonStartDateTime` = ?, `employee_MonEndDateTime` = ?,`employee_TueStartDateTime` = ?, `employee_TueEndDateTime` = ?,`employee_WedStartDateTime` = ?, `employee_WedEndDateTime` = ?,`employee_ThuStartDateTime` = ?, `employee_ThuEndDateTime` = ?,`employee_FriStartDateTime` = ?, `employee_FriEndDateTime` = ?,`employee_SatStartDateTime` = ?, `employee_SatEndDateTime` = ? WHERE (`calendar_week` = '0') and (`Employee_employee_id` = ?);";
		
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_updateStr);
			
			l_ps.setString(1, hoursArrList.get(0));
			l_ps.setString(2, hoursArrList.get(1));
			l_ps.setString(3, hoursArrList.get(2));
			l_ps.setString(4, hoursArrList.get(3));
			l_ps.setString(5, hoursArrList.get(4));
			l_ps.setString(6, hoursArrList.get(5));
			l_ps.setString(7, hoursArrList.get(6));
			l_ps.setString(8, hoursArrList.get(7));
			l_ps.setString(9, hoursArrList.get(8));
			l_ps.setString(10, hoursArrList.get(9));
			l_ps.setString(11, hoursArrList.get(10));
			l_ps.setString(12, hoursArrList.get(11));
			l_ps.setInt(13, emp.getEmployee_Id());
			
			//assign retVal
			retVal = l_ps.executeUpdate();
		}	catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retVal;
	}
	/**
	 * updates an employee's specific workWeek to have custom hours
	 * @param response
	 * @param emp
	 * @param week
	 * @param appts
	 * @return
	 */
	public int updateWorkWeek(Employee emp, int calendarWeek, ArrayList<String> hoursArrList) {
		int retVal = -1;
		String l_updateStr = "UPDATE `fitphys_db`.`work_week` SET `employee_MonStartDateTime` = ?, `employee_MonEndDateTime` = ?,`employee_TueStartDateTime` = ?, `employee_TueEndDateTime` = ?,`employee_WedStartDateTime` = ?, `employee_WedEndDateTime` = ?,`employee_ThuStartDateTime` = ?, `employee_ThuEndDateTime` = ?,`employee_FriStartDateTime` = ?, `employee_FriEndDateTime` = ?,`employee_SatStartDateTime` = ?, `employee_SatEndDateTime` = ? WHERE (`calendar_week` = ?) and (`Employee_employee_id` = ?);";
		
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_updateStr);

			
			l_ps.setString(1, hoursArrList.get(0));
			l_ps.setString(2, hoursArrList.get(1));
			l_ps.setString(3, hoursArrList.get(2));
			l_ps.setString(4, hoursArrList.get(3));
			l_ps.setString(5, hoursArrList.get(4));
			l_ps.setString(6, hoursArrList.get(5));
			l_ps.setString(7, hoursArrList.get(6));
			l_ps.setString(8, hoursArrList.get(7));
			l_ps.setString(9, hoursArrList.get(8));
			l_ps.setString(10, hoursArrList.get(9));
			l_ps.setString(11, hoursArrList.get(10));
			l_ps.setString(12, hoursArrList.get(11));
			l_ps.setInt(13, calendarWeek);
			l_ps.setInt(14, emp.getEmployee_Id());
			
			//assign retVal
			retVal = l_ps.executeUpdate();
		}	catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retVal;
	}
	/**
	 * Updates all workWeeks from given week onward, to match employee's template.
	 * @param response
	 * @param emp
	 * @param calendarWeek
	 * @return
	 */
	public int updateWorkWeeksFrom(Employee emp, int calendarWeek, ArrayList<String> hoursArrList) {
		
		int retVal = -1;
		String l_updateStr = "UPDATE `fitphys_db`.`work_week` SET `employee_MonStartDateTime` = ?, `employee_MonEndDateTime` = ?,`employee_TueStartDateTime` = ?, `employee_TueEndDateTime` = ?,`employee_WedStartDateTime` = ?, `employee_WedEndDateTime` = ?,`employee_ThuStartDateTime` = ?, `employee_ThuEndDateTime` = ?,`employee_FriStartDateTime` = ?, `employee_FriEndDateTime` = ?,`employee_SatStartDateTime` = ?, `employee_SatEndDateTime` = ? WHERE (`calendar_week` >= ?) and (`Employee_employee_id` = ?);";
		
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_updateStr);
						
			l_ps.setString(1, hoursArrList.get(0));
			l_ps.setString(2, hoursArrList.get(1));
			l_ps.setString(3, hoursArrList.get(2));
			l_ps.setString(4, hoursArrList.get(3));
			l_ps.setString(5, hoursArrList.get(4));
			l_ps.setString(6, hoursArrList.get(5));
			l_ps.setString(7, hoursArrList.get(6));
			l_ps.setString(8, hoursArrList.get(7));
			l_ps.setString(9, hoursArrList.get(8));
			l_ps.setString(10, hoursArrList.get(9));
			l_ps.setString(11, hoursArrList.get(10));
			l_ps.setString(12, hoursArrList.get(11));
			l_ps.setInt(13, calendarWeek);
			l_ps.setInt(14, emp.getEmployee_Id());
			
			//assign retVal
			retVal = l_ps.executeUpdate();
		}	catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retVal;
	}
	
	//Delete certification/Therapy based on user input
	public void removeAppointment(Appointment appointment){
		//init local vars 
		String l_removeAppStr = "DELETE FROM `fitphys_db`.`Appointment` WHERE (`appointment_id` = ?) and (`Customer_customer_id` = ?) and (`Employee_employee_id` = ?) and (`Therapy_therapy_id` = ?);";

		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_removeAppStr);
			String appId  = String.valueOf(appointment.getAppointment_id());
			String custId = String.valueOf(appointment.getFk_customer_id());
			String empId  = String.valueOf(appointment.getFk_employee_id());
			String therId = String.valueOf(appointment.getFk_therapy_id());
			
			l_ps.setString(1,appId);
			l_ps.setString(2,custId);
			l_ps.setString(3,empId);
			l_ps.setString(4,therId);

			l_ps.executeUpdate();
		}	catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Edit appointment notes based on user input
	public void editAppointmentNotes(Appointment appointment, String notes){
		//init local vars 
		String l_updateNotesStr = "UPDATE `Appointment` SET `appointment_desc` = ? WHERE (`appointment_id` = ?);";

		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_updateNotesStr);
			String appId  = String.valueOf(appointment.getAppointment_id());
			
			l_ps.setString(1,notes);
			l_ps.setString(2,appId);

			l_ps.executeUpdate();
		}	catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	//Delete certification/Therapy based on user input
	public int removeCertification(HttpServletResponse response, String certName)
	{
		//init local vars 
		int retVal = -1;
		String l_insertStr = "DELETE fitphys_db.Certification, fitphys_db.Therapy FROM fitphys_db.Certification INNER JOIN fitphys_db.Therapy ON certification.Therapy_therapy_id = therapy.therapy_id WHERE (SELECT `therapy_id` WHERE `certification_name` = ?);";

		try 
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_insertStr);

			l_ps.setString(1,certName);

			//Assign retval
			retVal = l_ps.executeUpdate();
		}	catch (SQLException e) {
			
			e.printStackTrace();
		}
		return retVal;
	}
	
	//Delete certification from employee based on user input from above function
	public int removeCertFromEmployees(HttpServletResponse response, String certName)
	{
		//init local vars
		int retVal = -1;
		String l_insertStr = "DELETE FROM fitphys_db.employee_has_certification WHERE `Certification_certification_id` IN (SELECT Certification.certification_id FROM fitphys_db.Certification WHERE `certification_name` = ?);";
			
		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_insertStr);
			
			l_ps.setString(1, certName);
			//assign retval
			retVal = l_ps.executeUpdate();
		}	catch (SQLException e) {
			
			e.printStackTrace();
		}
		return retVal;
	}
	
	//Delete certification from employee based on user input from above function
	public int removeAllCertFromEmployee(HttpServletResponse response, int empId)
	{
		//init local vars
		int retVal = -1;
		String l_insertStr = "DELETE FROM fitphys_db.employee_has_certification WHERE `Employee_employee_id` = ?;";

		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_insertStr);

			l_ps.setInt(1, empId);
			//assign retval
			retVal = l_ps.executeUpdate();
		}	catch (SQLException e) {

			e.printStackTrace();
		}
		return retVal;
	}
	
	//Remove employee certification based on user input
	//Used by DeleteEmpServlet.java
	public int removeEmpCertification(HttpServletResponse response, int certId, int empId)
	{
		//init local vars
		int retVal = -1;
		String l_removeStr = "DELETE FROM fitphys_db.employee_has_certification WHERE `Employee_employee_id` = ? AND Certification_certification_id = ?;";
		
		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_removeStr);
			
			l_ps.setInt(1,empId);
			l_ps.setInt(2,certId);
			
			//assign retVal
			retVal = l_ps.executeUpdate();		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
		
	}
	
	//Delete employee based on user input
	public int removeEmp(HttpServletResponse response, int id)
	{
		//Init local vars
		int retVal = -1;
		String l_delStr = "DELETE FROM fitphys_db.Employee WHERE `employee_id` = ?;";
		
		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_delStr);
			
			l_ps.setInt(1,id);
			//Assign retVal
			retVal = l_ps.executeUpdate();
		}	catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retVal;
	}
	/**
	 * removes all old workWeeks that are more than 6 weeks ago
	 * @param response
	 * @return
	 */
	public int removeOldWorkWeeks() {
		int retVal = -1;
		Calendar calendar = Calendar.getInstance();
		int weekNum = calendar.get(Calendar.WEEK_OF_YEAR);
		weekNum = weekNum - 2;
		if (weekNum <= 0) { //If weekNum is 0 or less, sets weekNum to max weekNum - 6.
			weekNum = 53 - calendar.get(Calendar.WEEK_OF_YEAR);
		}
		
		String l_delStr = "DELETE FROM fitphys_db.work_week WHERE calendar_week != 0 AND calendar_week < ?;";
		
		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_delStr);
			l_ps.setInt(1, weekNum);
			//Assign retVal
			retVal = l_ps.executeUpdate();
		}	catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retVal;
	}
	
	public ArrayList<Integer> SelectOldAppointments() {
		ResultSet l_rSet = null;
		ArrayList<Integer> oldAppointmentIds = new ArrayList<Integer>();
		ArrayList<Integer> oldCustomerIds = new ArrayList<Integer>();
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String l_selStr = "SELECT * FROM fitphys_db.Appointment";

		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selStr);
			//Assign retVal
			Calendar calendarRef = Calendar.getInstance();
			calendarRef.add(Calendar.WEEK_OF_YEAR, -1); //Creates cutoff for old appointments to being a week before
			Calendar appointmentCal = Calendar.getInstance();
			l_rSet = l_ps.executeQuery();
			while (l_rSet.next()) {
				String apptDateStr = l_rSet.getString("appointment_startDateTime");
				try {
					java.util.Date apptDate = sdfInput.parse(apptDateStr);
					appointmentCal.setTime(apptDate);
					if (calendarRef.compareTo(appointmentCal) > 0) {
						int appt_id = l_rSet.getInt("appointment_id");
						int cust_id = l_rSet.getInt("Customer_customer_id");
						oldAppointmentIds.add(appt_id);
						oldCustomerIds.add(cust_id);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			removeOldAppointments(oldAppointmentIds);
			removeOldCustomers(oldCustomerIds);
		}	catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int removeOldAppointments(ArrayList<Integer> appt_ids) {
		int retVal = -1;
		
		String l_delStr = "DELETE FROM fitphys_db.Appointment WHERE appointment_id = ?;";
		
		for (int id : appt_ids) {
			try {
				PreparedStatement l_ps = l_dbconnector.prepareStatement(l_delStr);
				l_ps.setInt(1, id);
				
				retVal = l_ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return retVal;
	}
	
	public int removeOldCustomers(ArrayList<Integer> cust_ids) {
		int retVal = -1;
		
		String l_delStr = "DELETE FROM fitphys_db.Customer WHERE customer_id = ?;";
		
		for (int id : cust_ids) {
			try {
				PreparedStatement l_ps = l_dbconnector.prepareStatement(l_delStr);
				l_ps.setInt(1, id);
				
				retVal = l_ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return retVal;
	}
	
	public int removeEmployeesWorkWeeks(HttpServletResponse response, Employee emp) {
		int retVal = -1;
		String l_delStr = "DELETE FROM fitphys_db.work_week WHERE Employee_employee_id = ?;";
		
		try
		{
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_delStr);
			
			l_ps.setInt(1,emp.getEmployee_Id());
			//Assign retVal
			retVal = l_ps.executeUpdate();
		}	catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retVal;
	}
	
	
 	/**
	 * Select Employee by fname and lname or if lname null, just fname
	 * @param response
	 * @param fname
	 * @param lname
	 * @param uname
	 * @param pass
	 * @return returned value for error checking
	 */
	public int getEmployee(HttpServletResponse response, String fname, String lname, String uname, String pass) {
		//init local vars	
		int retVal = -1;
		ResultSet l_rSet = null;
		String l_selectEmpStr = "SELECT * FROM fitphys_db.Employee WHERE ( ( employee_fname=? AND employee_lname=?) OR ( employee_fname=? ) );";	
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectEmpStr);
			
			l_ps.setString(1,fname);
			l_ps.setString(2,lname);
			l_ps.setString(3,fname);
	
			//result set created if employee exists in db
			l_rSet = l_ps.executeQuery();
			
			if (l_rSet.next()) {
				//initialize new employee instance
				Employee l_emp = new Employee(l_rSet.getInt("employee_id"),l_rSet.getInt("employee_userTyp"),l_rSet.getString("employee_fname"),lname,uname,pass);
				
				//update employee record
				updateEmp(l_emp);
				response.sendRedirect("jsp/userProfile.jsp");
			}
			
			//assign retVal
			retVal = 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
 	/**
	 * Select Employee by fname and lname or if lname null, just fname
	 * @param fname
	 * @param lname
	 * @param uname
	 * @param pass
	 * @return employee
	 */
	public Employee getEmployee(String uname) {
		//init local vars	
		ResultSet l_rSet = null;
		String l_selectEmpStr = "SELECT * FROM fitphys_db.Employee WHERE ( employee_uname=? );";
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectEmpStr);
			
			l_ps.setString(1,uname);
	
			//result set created if employee exists in db
			l_rSet = l_ps.executeQuery();
			
			if (l_rSet.next()) {
				//initialize new employee instance
				Employee l_emp = new Employee(l_rSet.getInt("employee_id"),l_rSet.getInt("employee_userTyp"),l_rSet.getString("employee_fname"),l_rSet.getString("employee_lname"),l_rSet.getString("employee_uname"),l_rSet.getString("employee_password"));
				return l_emp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Employee getEmployeeById(int id) {
		//init local vars	
		ResultSet l_rSet = null;
		String l_selectEmpStr = "SELECT * FROM fitphys_db.Employee WHERE `employee_id`=?;";	
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_selectEmpStr);
			
			l_ps.setInt(1,id);
	
			//result set created if employee exists in db
			l_rSet = l_ps.executeQuery();
			
			if (l_rSet.next()) {
				//initialize new employee instance
				Employee l_emp = new Employee(l_rSet.getInt("employee_id"),l_rSet.getInt("employee_userTyp"),l_rSet.getString("employee_fname"),l_rSet.getString("employee_lname"),l_rSet.getString("employee_uname"),l_rSet.getString("employee_password"));
				return l_emp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	//List all therapists availability
	public int therapistTime(int employee_id) {
		//init local vars	
		int retVal = -1;
		ResultSet l_rSet = null;
		String selectTime = "SELECT * FROM `work_week` WHERE employee_employee_id= " + employee_id;
		
		try {
			Statement statement = l_dbconnector.createStatement();
			l_rSet = statement.executeQuery(selectTime);
			while (l_rSet.next()) {
				l_rSet.getInt("calendar_week");
				l_rSet.getInt("employee_employee_id");
				l_rSet.getDate("employee_MonStartDateTime");
				l_rSet.getDate("employee_MonEndDateTime");
				l_rSet.getDate("employee_TueStartDateTime");
				l_rSet.getDate("employee_TueEndDateTime");
				l_rSet.getDate("employee_WedStartDateTime");
				l_rSet.getDate("employee_WedEndDateTime");
				l_rSet.getDate("employee_ThuStartDateTime");
				l_rSet.getDate("employee_ThuEndDateTime");
				l_rSet.getDate("employee_FriStartDateTime");
				l_rSet.getDate("employee_FriEndDateTime");
				l_rSet.getDate("employee_SatStartDateTime");
				l_rSet.getDate("employee_SatEndDateTime");
	
				//assign retVal
				retVal = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	/**
	 * Executes UPDATE Employee by employee_id
	 * allows lastname, username and password for an employee to be changed
	 * @param emp
	 */
	public void updateEmployee(Employee emp) {
		//init local vars 
		String l_insertStr = "UPDATE `Employee` SET `employee_lname` = ?, `employee_uname` = ?, `employee_password` = ? WHERE (`employee_id` = ?);";
		
		//prepare and execute statement
		try {
			PreparedStatement l_ps = l_dbconnector.prepareStatement(l_insertStr);
			
			l_ps.setString(1,emp.getEmployee_lname());
			l_ps.setString(2,emp.getEmployee_uname());
			l_ps.setString(3,emp.getEmployee_password());
			l_ps.setInt(4,emp.getEmployee_Id());
			
			l_ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}	