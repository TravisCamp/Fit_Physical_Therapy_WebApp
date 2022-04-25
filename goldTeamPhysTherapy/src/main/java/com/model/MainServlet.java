package com.model;


import java.util.Date;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.database.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Date sample = new Date();
	public static SimpleDateFormat sdf1 = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm aa");
	public static SimpleDateFormat sdfDay_Hour = new SimpleDateFormat("EEE HH:mm");
	public static SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
	public static SimpleDateFormat sdfTest = new SimpleDateFormat("MM/dd/yyyy");
	public static SimpleDateFormat sdfAppointment = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	public static SimpleDateFormat sdfFile_Read = new SimpleDateFormat("E hh:mm");
	public static SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static ArrayList<Therapy> therapyList = new ArrayList<Therapy>();
	public static ArrayList<Employee> employeeList = new ArrayList<Employee>();
	public static ArrayList<Certification> certificationList = new ArrayList<Certification>();
	public static ArrayList<WorkWeek> workWeeks = new ArrayList<WorkWeek>();
	public static ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
	public static final int NUMWEEKS = 16;
	

	/**
	 * logs in user based on theeir l_uname. will change to userType in future
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		/* Create a customer with given names. Post customer to DB for the ID to be created & incremented
		 * Then fetch the custId to be used in creating the appointment
		 * 
		 */
		
		String appDateStr = (String) session.getAttribute("session_selectedAppDateStr");
		String appTherapyIdStr = request.getParameter("appTherapyIdIn");
		String apptherapist = request.getParameter("appTherapistIn");
		String appTimeStr = request.getParameter("appTimeIn");
		String appfname = request.getParameter("fname");
		String applname = request.getParameter("lname");
		String appPNumber = request.getParameter("pNumber");
		String appEmail = request.getParameter("email");
		String appDesc = request.getParameter("notes");
		
		Therapy therapy = (Therapy) session.getAttribute("session_selectedTherapy");
		Employee emp = getTherapist(employeeList, apptherapist);
		Customer cust = createCustomer(appfname, applname, appPNumber, appEmail);
		
		String apptDateFullStr = appDateStr + " " + appTimeStr + ":00:00";
		Calendar apptCalendar = Calendar.getInstance();
		Date apptDate = new Date();
		
		if(appDateStr!=null) {
			try {
				apptDate = sdfAppointment.parse(apptDateFullStr);
				apptCalendar.setTime(apptDate);
				apptCalendar.set(Calendar.MINUTE, 0);
				apptCalendar.set(Calendar.SECOND, 0);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			createAppointment(emp, therapy, cust, apptCalendar, appDesc);
		}
		Appointment selectedAppt = new Appointment();
		
		System.out.println(appDateStr+appTherapyIdStr+apptherapist+appTimeStr+appfname+applname+appDesc);
		selectedAppt = selectAppointment(emp, therapy, cust, apptCalendar);
		String confirmValue = "true";
		
		// refresh session var(s)
		session.setAttribute("session_selectedAAppointment", selectedAppt);
		session.setAttribute("session_confirmValue", confirmValue);
		
		// redirect
		response.sendRedirect("jsp/bookAppointment.jsp");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String appDateStr = request.getParameter("appDate");
		String appTherapyId = request.getParameter("therapyDropdown");
		int numWeeks = NUMWEEKS;
		HttpSession session = request.getSession();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);

		//Load data from session
		loadTherapys(session);
		loadCertifications(session);
		loadTherapist(session);
		loadAppointments(session);

		//initialize calendar to monday
		int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayNum != 1) {
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}
		
		//initialize work schedules
		while (numWeeks > 0) { 
			//createWorkWeeks(fileName, calendar); //Run only if workweek db table needs populating
			loadTherapistWorkWeek(session, calendar);
			calendar.add(Calendar.WEEK_OF_YEAR, 1); //Adds a week to the calendar objects
			numWeeks--;
		}
		//not sure
		resetCalendar(calendar);
		Therapy therapy = getTherapy(appTherapyId);
	
		System.out.println("Selected : \n\t" + therapy.getTherapy_name());//debugging
		
		//System.out.println("What time would your appointment best suit you? Please format as \"mm/dd/yyyy hr:min AM\\PM\"");
		Date therapyDate = new Date();
		
		
		
		//parse date string to date object
		if(appDateStr!=null) {
			try {
				therapyDate = sdfTest.parse(appDateStr);
				System.out.println("\nDate Selected : \n\t" +therapyDate);//debugging
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//set calendar to therapy date and convert to 12hr time
			calendar.setTime(therapyDate);
			if (calendar.get(Calendar.HOUR_OF_DAY) < 8) {
				calendar.set(Calendar.HOUR_OF_DAY, 12 + Calendar.HOUR_OF_DAY);
			}
			
			//get employees with correct certification qualifications
			ArrayList<Employee> qualifiedTherapists = FindQualifiedTherapists(therapy);
			ArrayList<Employee> validTherapists = FindValidTherapists(qualifiedTherapists, calendar);
			
			//set session var(s)
			session.setAttribute("session_selectedTherapy", therapy);
			session.setAttribute("session_selectedAppDateStr", appDateStr);
			session.setAttribute("session_validTherapists", validTherapists);

			for (Employee emp : validTherapists) {
				//Appointment appt = createAppointment(t, therapy, therapyDate); instead create an appointment obj
				//String date = sdfInput.format(therapyDate);
				sdfInput.format(therapyDate);
				//System.out.println("An appointment has been made with " + emp.getEmployee_fname() + " at " + date);
				calendar.setTime(therapyDate);

				WorkWeek schedule = getWorkWeek(getWorkWeeks(emp.getEmployee_Id()), calendar);
				getWorkDayShift(session,emp,schedule,calendar);
				ArrayList<Appointment> empAppts = getAppointmentsByDate(getAppointments(emp.getEmployee_Id()),calendar);
				
				getWorkDayAppts(session,emp,empAppts,calendar);
			}
		}
	
		//redirect to book appointments to rebuild with user selected data
		response.sendRedirect("jsp/bookAppointment.jsp");
		 
		resetCalendar(calendar);
	}

	private Appointment createAppointment(Employee emp, Therapy therapy, Customer cust, Calendar apptCalendar,
			String appDesc) {
		DB_Query query = new DB_Query();
		Appointment appointment = new Appointment();
		Date startDate = apptCalendar.getTime();
		apptCalendar.add(Calendar.HOUR, 1);
		Date endDate = apptCalendar.getTime();
		
		appointment.setFk_employee_id(emp.getEmployee_Id());
		appointment.setFk_therapy_id(therapy.getTherapy_id());
		appointment.setAppointment_startDateTime(startDate);
		appointment.setAppointment_endDateTime(endDate);
		appointment.setFk_customer_id(cust.getCustomer_id());
		
		
		if (appDesc != null ) {
			appointment.setAppointment_desc(appDesc);
		}
		
		query.insertAppointment(appointment, cust);
		return appointment;
	}
	
	private Appointment selectAppointment(Employee emp, Therapy therapy, Customer cust, Calendar apptCalendar)
	{
		DB_Query query = new DB_Query();
		Appointment appointment = new Appointment();
		Date startDate = apptCalendar.getTime();
		apptCalendar.add(Calendar.HOUR, 1);
		
		appointment = query.getAppointmentByParameters(therapy, emp, cust, startDate);
		return appointment;
	}

	private Customer createCustomer(String appfname, String applname, String appPNumber, String appEmail) {
		
		DB_Query query = new DB_Query();
		Customer customer = new Customer();
		customer.setCustomer_fname(appfname);
		if (applname != null) {
			customer.setCustomer_lname(applname);
		}
		
		if (appPNumber != null) {
			customer.setCustomer_PhoneNum(appPNumber);
		}
		
		if (appEmail != null) {
			customer.setCustomer_Email(appEmail);
		}
		
		query.insertCustomer(customer);
		query.setLastCustomerID(customer);
		
		return customer;
	}
	/**
	 * returns the formatted start and end of shift for appointment date
	 * @param session 
	 * @param emp 
	 * @param schedule
	 * @param calendar
	 */
	private void getWorkDayShift(HttpSession session, Employee emp, WorkWeek schedule, Calendar appDate) {
		// TODO Auto-generated method stub
		String empShiftStr = "\"";
		Calendar l_calendarWeekDay = Calendar.getInstance();
	    boolean first = false;
		for (Date l_weekDay : schedule.getWorkWeek()) {
			if(l_weekDay!=null) {
				l_calendarWeekDay.setTime(l_weekDay);
				if (l_calendarWeekDay.get(Calendar.DAY_OF_WEEK) == appDate.get(Calendar.DAY_OF_WEEK)) {
					//extraxt just the start/end time of the date string
					String tmpEmpShiftStr = l_calendarWeekDay.getTime().toString();
				    
					Pattern pattern = Pattern.compile("[1-9]{1,2}:[0-9]{1,2}");
				    Matcher matcher = pattern.matcher(tmpEmpShiftStr);
				    boolean matchFound = matcher.find();
				    if(matchFound && !first) {
				      tmpEmpShiftStr = matcher.group(0);
				      empShiftStr = empShiftStr+tmpEmpShiftStr+"-";
				      first = true;
				    }else {
					    tmpEmpShiftStr = matcher.group(0);
				    	empShiftStr = empShiftStr+tmpEmpShiftStr;
					    first = false;
				    }
				}
			}
		}
		
		// refresh session var(s)
		session.setAttribute("session_empWorkDayShiftStr"+emp.getEmployee_Id(), empShiftStr+"\"");
	}
	
	/**
	 * returns formatted string of times for appointments to prevent double booking
	 * @param session
	 * @param emp
	 * @param empAppts
	 * @param calendar
	 */
	private void getWorkDayAppts(HttpSession session, Employee emp, ArrayList<Appointment> empAppts,
			Calendar appDate) {
		String empApptStr = "\"";
		Calendar l_calendarWeekDay = Calendar.getInstance();
		for (Appointment appt : empAppts) {
			if(appt!=null) {
				l_calendarWeekDay.setTime(appt.getAppointment_startDateTime());
				if (l_calendarWeekDay.get(Calendar.DAY_OF_WEEK) == appDate.get(Calendar.DAY_OF_WEEK)) {
					//extraxt just the start/end time of the date string
					String tmpEmpShiftStr = l_calendarWeekDay.getTime().toString();
				    
					Pattern pattern = Pattern.compile("[0-9]{1,2}:[0-9]{1,2}");
				    Matcher matcher = pattern.matcher(tmpEmpShiftStr);
				    boolean matchFound = matcher.find();
				    if(matchFound) {
				      tmpEmpShiftStr = matcher.group(0);
				      empApptStr = empApptStr+tmpEmpShiftStr+",";
				    }
				}
			}
		}
		
		// refresh session var(s)
		session.setAttribute("session_empWorkDayApptsStr"+emp.getEmployee_Id(), empApptStr+"\"");
	}

	private Therapy getTherapy(String appTherapyId) {
		for (Therapy t : therapyList) {
			if (t.getTherapy_id() == Integer.parseInt(appTherapyId)) {
				return t;
			}
		}
		return null;
	}

	private static void resetCalendar(Calendar calendar) {
		calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
	}

	private static Boolean compareDates(Date startHour, Date endHour, Calendar appointment) {
		if (startHour != null && endHour != null) {
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			start.setTime(startHour);
			end.setTime(endHour);
			
			if (appointment.get(Calendar.DAY_OF_WEEK) == start.get(Calendar.DAY_OF_WEEK)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a employee object that is a therapist by name.
	 * @param therapists
	 * @param therapistName
	 * @return
	 */
	public static Employee getTherapist(ArrayList<Employee> therapists, String therapistName) {
		
		for (Employee t : therapists) {
			if (t.getEmployee_fname().equalsIgnoreCase(therapistName) && t.getEmployee_userTyp() <= 2) {
				return t;
			}
		}
		
		return null;
	}

	public static void displayAvailableTherapist(ArrayList<Employee> validTherapist) { //Displays given list of therapist
		System.out.println("Available Therapists: ");
		for (Employee t : validTherapist) {
			System.out.println(t.getEmployee_fname());
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	//TODO
	public static WorkWeek getWorkWeek(ArrayList<WorkWeek> weeks, Calendar calendar) {
		WorkWeek workWeek = new WorkWeek();
		
		for (WorkWeek week : weeks) {
			//System.out.println(week.getCalendar_week() + "==" + calendar.get(Calendar.WEEK_OF_YEAR));
			if (week.getCalendar_week() == calendar.get(Calendar.WEEK_OF_YEAR)) {
				workWeek = week;
			}
		}
		
		return workWeek;
	}
	
	public static ArrayList<WorkWeek> getWorkWeeks(int id) { //Gets all the workWeeks associated with a therapist
		
		ArrayList<WorkWeek> weeks = new ArrayList<WorkWeek>();
		
		for (WorkWeek week : workWeeks) {
			if (week.getFk_employee_id() == id) {
				weeks.add(week);
			}
		}
		
		return weeks;
	}

	public static ArrayList<Employee> FindQualifiedTherapists(Therapy therapy) {
		return new DB_Query().getEmpByCertList(therapy.therapy_id);		
	}
	
	public static ArrayList<Employee> FindValidTherapists(ArrayList<Employee> therapists, Calendar appointment) {
		ArrayList<Employee> validTherapist = new ArrayList<Employee>();
		
		for (Employee t : therapists) {
			Boolean valid = false;
			WorkWeek week = getWorkWeek(getWorkWeeks(t.getEmployee_Id()), appointment);
			int dayOfWeek = appointment.get(Calendar.DAY_OF_WEEK);
			ArrayList<Date> hours = week.getWorkWeek();
			switch(dayOfWeek) { //Determines which day of the week it is from appointment
			case Calendar.MONDAY:
				valid = compareDates(hours.get(2), hours.get(3), appointment);
				break;
			case Calendar.TUESDAY:
				valid = compareDates(hours.get(4), hours.get(5), appointment);
				break;
			case Calendar.WEDNESDAY:
				valid = compareDates(hours.get(6), hours.get(7), appointment);
				break;
			case Calendar.THURSDAY:
				valid = compareDates(hours.get(8), hours.get(9), appointment);
				break;
			case Calendar.FRIDAY:
				valid = compareDates(hours.get(10), hours.get(11), appointment);
				break;
			case Calendar.SATURDAY:
				valid = compareDates(hours.get(12), hours.get(13), appointment);
				break;
			}
			if (valid == true) {
				validTherapist.add(t);
			}
		}
		return validTherapist;		
	}
	
	public static Certification findCertificationByTherapy(Therapy therapy) {
		
		for (Certification c : certificationList) {
			if (c.getFk_therapy_id() == therapy.getTherapy_id()) {
				return c;
			}
		}
		
		return null;
	}
	
	
	public static Certification findCertificationByID(int id) {
		
		for (Certification c : certificationList) {
			if (c.getFk_therapy_id() == id) {
				return c;
			}
		}
		
		return null;
	}
	
//	public static void printWeekSchedule() {
//		
//		for (Employee t : employeeList) {
//			for (WorkWeek week : workWeeks) {
//				if (t.getEmployee_Id() == week.getFk_employee_id()) {
//					Boolean start = true;
//					ArrayList<Date> schedule = week.getWorkWeek();
//					System.out.print(t.getEmployee_fname() + " Working: ");
//					for (Date date : schedule) {
//						if (date != null) {
//							if (start == true) {
//								System.out.print(sdfDay_Hour.format(date) + " - ");
//								start = false;
//							}
//							else {
//								System.out.print(sdfHour.format(date) + " , ");
//								start = true;
//							}
//						}
//					}
//					break;
//				}
//			}
//			System.out.println();
//		}
//	}

	public static void printEmployeeSchedule(Employee emp, Calendar calendar) {
		for (WorkWeek week : workWeeks) {
			if (emp.getEmployee_Id() == week.getFk_employee_id() && week.getCalendar_week() == calendar.get(Calendar.WEEK_OF_YEAR)) {
				Boolean start = true;
				ArrayList<Date> weekSchedule = week.getWorkWeek();
				String schedule = (emp.getEmployee_fname() + " Working: ");
				System.out.print(emp.getEmployee_fname() + " Working: ");
				for (Date date : weekSchedule) {
					if (date != null) {
						if (start == true) {
							schedule.concat(sdfDay_Hour.format(date) + " - ");
							System.out.print(sdfDay_Hour.format(date) + " - ");
							start = false;
						}
						else {
							schedule.concat(sdfHour.format(date) + " , ");
							System.out.print(sdfHour.format(date) + " , ");
							start = true;
						}
					}
				}
				break;
			}
		}
		System.out.println();
	}
	
	public static ArrayList<Appointment> getAppointments(int empID){ //Gets all appointments associated to an employee
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		
		for (Appointment appt : appointmentList) {
			if (appt.getFk_employee_id() == empID) {
				appointments.add(appt);
			}
		}
		
		return appointments;
	}
	
	public static ArrayList<Appointment> getAppointmentsByWeek(ArrayList<Appointment> appts, Calendar calendar){ //Gets all appointments in a specific week
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		Calendar apptDay = Calendar.getInstance();
		
		for (Appointment appt : appts) {
			apptDay.setTime(appt.getAppointment_startDateTime());
			if (apptDay.get(Calendar.WEEK_OF_YEAR) == calendar.get(Calendar.WEEK_OF_YEAR)) {
				appointments.add(appt);
			}
		}
		
		return appointments;
	}
	
	public static ArrayList<Appointment> getAppointmentsByDate(ArrayList<Appointment> appts, Calendar calendar) {
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		Calendar apptDay = Calendar.getInstance();

		
		for (Appointment appt : appts) {
			apptDay.setTime(appt.getAppointment_startDateTime());
			if (apptDay.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)) {
				appointments.add(appt);
			}
		}
		
		return appointments;
	}
	
	public static Appointment getAppointmentByID(ArrayList<Appointment> appts, int id) {
		Appointment appointment = null;
		
		for (Appointment appt : appts) {
			if (appt.getAppointment_id() == id) {
				appointment = appt;
			}
		}
		
		return appointment;
	}

	public static void printCertifications() {
		System.out.printf("%-6s%-72s\n", "ID","Certification Name");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for (Certification c : certificationList) {
			System.out.printf("%-6d%-72s\n",c.getCertification_id(),c.getCertification_name());
		}
	}

	public static void loadTherapys(HttpSession session) {
		therapyList = (ArrayList<Therapy>) session.getAttribute("session_therapies");
	}
	
	
	public static void loadCertifications(HttpSession session) {
		certificationList =  (ArrayList<Certification>) session.getAttribute("session_certifications");
	}
	
	public static void loadTherapist(HttpSession session) {
		employeeList = (ArrayList<Employee>) session.getAttribute("session_employees");
	}
	
	public static void loadTherapistWorkWeek(HttpSession session, Calendar calendar){	
		//Calendar origin = calendar;
		workWeeks = (ArrayList<WorkWeek>) session.getAttribute("session_workWeeks");
	}
	
	public static void loadAppointments(HttpSession session) {
		appointmentList = (ArrayList<Appointment>) session.getAttribute("session_appointments");
	}
}
