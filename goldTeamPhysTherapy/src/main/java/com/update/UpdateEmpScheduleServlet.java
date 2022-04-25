package com.update;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.database.DB_Query;
import com.model.Appointment;
import com.model.Employee;
import com.model.MainServlet;
import com.model.WorkWeek;


public class UpdateEmpScheduleServlet extends HttpServlet {
	
	private static int calendarWeek;
	private static Employee employee;
	private static final long serialVersionUID = 1L;
	private static SimpleDateFormat dbInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
	private static ArrayList<WorkWeek> workWeeks = new ArrayList<WorkWeek>();
	private static ArrayList<Employee> employees = new ArrayList<Employee>();
	private static int therapist_id;
	private static Calendar calendar;
	private static Employee therapist;
	private static String invalidUpdate; 

	/**
	 * Updates an employee's schedule
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//init local vars
		HttpSession session = request.getSession();
		employee =  (Employee) session.getAttribute("session_selectedTherapistManage");
		therapist_id = employee.getEmployee_Id();
		invalidUpdate = "null";
		String weekDate = (String) session.getAttribute("session_selectedWeekManage");
		String monOff = request.getParameter("mondayOff");
		String tueOff = request.getParameter("tuesdayOff");
		String wedOff = request.getParameter("wednesdayOff");
		String thuOff = request.getParameter("thursdayOff");
		String friOff = request.getParameter("fridayOff");
		String satOff = request.getParameter("saturdayOff");
		String updateAllWeeks = request.getParameter("updateWorkWeeks");
		String mondayHours, tuesdayHours, wednesdayHours, thursdayHours, fridayHours, saturdayHours;
		mondayHours = tuesdayHours = wednesdayHours = thursdayHours = fridayHours = saturdayHours = null;
		if (monOff == null) {
			mondayHours = request.getParameter("mondayStart") + "-" + request.getParameter("mondayEnd");
		}
		if (tueOff == null) {
			tuesdayHours = request.getParameter("tuesdayStart") + "-" + request.getParameter("tuesdayEnd");
		}
		if (wedOff == null) {
			wednesdayHours = request.getParameter("wednesdayStart") + "-" + request.getParameter("wednesdayEnd");
		}
		if (thuOff == null) {
			thursdayHours = request.getParameter("thursdayStart") + "-" + request.getParameter("thursdayEnd");
		}
		if (friOff == null) {
			fridayHours = request.getParameter("fridayStart") + "-" + request.getParameter("fridayEnd");
		}
		if (satOff == null) {
			saturdayHours = request.getParameter("saturdayStart") + "-" + request.getParameter("saturdayEnd");
		}
		setCalendarWeek(weekDate);
		ArrayList<String> hoursArrList = setHoursArrList(mondayHours, tuesdayHours, wednesdayHours, thursdayHours, fridayHours, saturdayHours);
		
		if (updateAllWeeks != null && updateAllWeeks.equalsIgnoreCase("true")) { //If flag is true for changing all weeks after inputted week this occurs
			setWorkWeekTemplate(response, employee, hoursArrList);
			//WorkWeek workWeek_template = findWorkWeekTemplate();
			int numWeeks = (int) session.getAttribute("session_workWeeksLength");
			validateAllScheduleChange(employee, calendarWeek, hoursArrList, numWeeks);
			//query.updateWorkWeeksFrom(response, employee, calendarWeek, workWeek_template); //Updates all workWeeks starting from calendarWeek
		}
		else { //If flag is not true for changing all weeks after inputted week this occurs
			validateScheduleChange(employee, calendarWeek, hoursArrList);
			//query.updateWorkWeek(response, employee, calendarWeek, hoursArrList); //Updates specific workWeek
		}
		
		// set flag to refresh page data
		session.setAttribute("session_refreshPage", true);
		session.setAttribute("session_scheduleChanged", session.getAttribute("session_refreshPage"));

		// set flag to alert for error updating
		session.setAttribute("session_invalidUpdate", invalidUpdate);
		
		// redirect
		response.sendRedirect("jsp/manageSchedule.jsp");	
	}


	/**
	 * Logic for loading times for an Employee's schedule for front end use.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		try {
			loadTherapistWorkWeek(session);
			loadEmployees(session);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		therapist_id = Integer.parseInt(request.getParameter("therapistDropdown"));
		therapist = getTherapist(therapist_id);
		
		String l_week_str = request.getParameter("weekpicker");	
		Date date = new Date(); 
		
		try { 
			date = sdf.parse(l_week_str); 
		} catch(ParseException e) {  
			e.printStackTrace();
		} 
		
		calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY); 
		
		int dayNum = calendar.get(Calendar.DAY_OF_WEEK); 
		if (dayNum != 1) {
			calendar.set(Calendar.DAY_OF_WEEK, 2); 
		} 
		
		int numWeeks = 16; 
		while (numWeeks > 0) { 
			calendar.add(Calendar.WEEK_OF_YEAR, 1); //Adds a week to the calendar
			numWeeks--; 
		}
		
		calendar.setTime(date); 
		if(calendar.get(Calendar.HOUR_OF_DAY) < 8) { 
			calendar.set(Calendar.HOUR_OF_DAY, 12 + Calendar.HOUR_OF_DAY); 
		}
		
		ArrayList<WorkWeek> emp_weeks = getWorkWeeks(therapist_id);
		WorkWeek week = MainServlet.getWorkWeek(emp_weeks, calendar);
		ArrayList<Date> weekSchedule = week.getWorkWeek();
		String schedule = "";
		for (Date d : weekSchedule) {
			if (d != null) {
				schedule += sdfHour.format(d) + " , ";
			}
			else {
				schedule += "null , ";
			}
		}
		
		// refresh session var(s)
		session.setAttribute("session_selectedWeekStrManage", schedule);
		session.setAttribute("session_selectedWeekManage", l_week_str);
		session.setAttribute("session_selectedTherapistManage", therapist);
		// redirect
		response.sendRedirect("jsp/manageSchedule.jsp");
	}

	private void setWorkWeekTemplate(HttpServletResponse response, Employee employee, ArrayList<String> hoursArrList) {
		DB_Query query = new DB_Query();
		query.updateWorkTemplate(response, employee, hoursArrList);
	}
	
	private ArrayList<String> setHoursArrList(String mondayHours, String tuesdayHours, String wednesdayHours,
			String thursdayHours, String fridayHours, String saturdayHours) {
		ArrayList<String> hourList = new ArrayList<String>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.WEEK_OF_YEAR, calendarWeek);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		addHours(hourList, mondayHours, calendar);
		calendar.add(Calendar.DAY_OF_WEEK, 1);
		addHours(hourList, tuesdayHours, calendar);
		calendar.add(Calendar.DAY_OF_WEEK, 1);
		addHours(hourList, wednesdayHours, calendar);
		calendar.add(Calendar.DAY_OF_WEEK, 1);
		addHours(hourList, thursdayHours, calendar);
		calendar.add(Calendar.DAY_OF_WEEK, 1);
		addHours(hourList, fridayHours, calendar);
		calendar.add(Calendar.DAY_OF_WEEK, 1);
		addHours(hourList, saturdayHours, calendar);
		calendar.add(Calendar.DAY_OF_WEEK, 1);
		
		return hourList;
	}
	
	private void addHours(ArrayList<String> hourList, String shift, Calendar calendar) {
		if (shift == null) {
			hourList.add(null);
			hourList.add(null);
		}
		else {
			String[] hours = shift.split("-");
			for (String hour : hours) {
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
				String formatStr = dbInput.format(calendar.getTime());
				hourList.add(formatStr);
			}
		}
		
	}

	private void setCalendarWeek(String weekDate) {
		try {
			java.util.Date week = new Date();
			week = sdf.parse(weekDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(week);
			calendarWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void validateScheduleChange(Employee employee, int calWeek, ArrayList<String> hoursArrList) {
		Boolean validHours = true;
		DB_Query query = new DB_Query();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.WEEK_OF_YEAR, calWeek);
		ArrayList<Appointment> employeeWeekAppointments = query.getEmpAppointmentsByWeek(employee, calWeek);

		for (Appointment appt : employeeWeekAppointments) {
			validHours = compareApptToHours(appt, hoursArrList);
		}
		if (validHours) {
			query.updateWorkWeek(employee, calendarWeek, hoursArrList); //Updates specific workWeek
		}
		else {
			System.out.println("This week could not be modified as an appointment would be in conflict with the new shift hours.");
			invalidUpdate = "true";
		}		
	}
	

	private Boolean compareApptToHours(Appointment appt, ArrayList<String> hoursArrList) {
		Calendar apptStart = Calendar.getInstance();
		apptStart.setTime(appt.getAppointment_startDateTime());
		int dayOfWeek = apptStart.get(Calendar.DAY_OF_WEEK);
		String startShiftHour = "";
		String endShiftHour = "";
		switch (dayOfWeek) { //Fetches the right shift Hours based on appt's day of the week
			case(Calendar.MONDAY):
				startShiftHour = hoursArrList.get(0);
				endShiftHour = hoursArrList.get(1);
				break;
			case(Calendar.TUESDAY):
				startShiftHour = hoursArrList.get(2);
				endShiftHour = hoursArrList.get(3);
				break;
			case(Calendar.WEDNESDAY):
				startShiftHour = hoursArrList.get(4);
				endShiftHour = hoursArrList.get(5);
				break;
			case(Calendar.THURSDAY):
				startShiftHour = hoursArrList.get(6);
				endShiftHour = hoursArrList.get(7);
				break;
			case(Calendar.FRIDAY):
				startShiftHour = hoursArrList.get(8);
				endShiftHour = hoursArrList.get(9);
				break;
			case(Calendar.SATURDAY):
				startShiftHour = hoursArrList.get(10);
				endShiftHour = hoursArrList.get(11);
				break;
		}
		
		try {
			if (startShiftHour != null && endShiftHour != null) {
				Date startShiftDate = dbInput.parse(startShiftHour);
				Date endShiftDate = dbInput.parse(endShiftHour);
				Calendar startShift = Calendar.getInstance();
				startShift.setTime(startShiftDate);
				Calendar endShift = Calendar.getInstance();
				endShift.setTime(endShiftDate);
				if (startShift.compareTo(apptStart) <= 0 && endShift.compareTo(apptStart) > 0) {
					return true;
				}
			}
		}
		catch (ParseException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	


	private void validateAllScheduleChange(Employee employee, int calWeek, ArrayList<String> hoursArrList, int numWeeks) {
		DB_Query query = new DB_Query();
		Calendar calendar = Calendar.getInstance();
		int weekCount = numWeeks;
		calendar.set(Calendar.WEEK_OF_YEAR, calWeek);
		Boolean validHours = true;
		for (int i = 0; i < weekCount; i++) { //Loops through every week until the end of loaded workWeeks
		
			ArrayList<Appointment> employeeWeekAppointments = query.getEmpAppointmentsByWeek(employee, calendar.get(Calendar.WEEK_OF_YEAR));
			for (Appointment appt : employeeWeekAppointments) {
				validHours = compareApptToHours(appt, hoursArrList);
			}
			calendar.add(Calendar.WEEK_OF_YEAR, 1); //Sets calendar to the next week
		}
		if (validHours) {
			query.updateWorkWeeksFrom(employee, calWeek, hoursArrList); //Updates all workWeeks starting from calendarWeek
		}
		else {
			System.out.println("These changes could not be committed due to an appointment being in conflict. Please manually review each week's appointments.");
			invalidUpdate = "true";
		}
	}
	
	/**
	 * function pulls latest schedule from db and stores into session var
	 * @param session
	 * @throws ParseException
	 */
	public static void loadTherapistWorkWeek(HttpSession session) throws ParseException {
		DB_Query l_query = new DB_Query();
		workWeeks = l_query.getEmployeeWorkWeekArrayList();
		
		// refresh session var(s)
		session.setAttribute("session_workWeeks", workWeeks);
	}
	
	public static void loadEmployees(HttpSession session) throws ParseException {	
		employees = (ArrayList<Employee>) session.getAttribute("session_employees");
	}
	
	private Employee getTherapist(int therapist_id) {
		for (Employee e : employees) {
			if (e.getEmployee_Id() == therapist_id) {
				return e;
			}
		}
		return null;
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
}
