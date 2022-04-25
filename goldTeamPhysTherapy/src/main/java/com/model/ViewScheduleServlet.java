package com.model;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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

@WebServlet("/ViewScheduleServlet")

/**
 * Servlet implementation class ViewSchedule
 */
public class ViewScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
	private static ArrayList<WorkWeek> workWeeks = new ArrayList<WorkWeek>();
	private static ArrayList<Employee> employees = new ArrayList<Employee>();
	private static ArrayList<Appointment> appointments = new ArrayList<Appointment>();
	private static int therapist_id;
	private static Calendar calendar;
	private static Employee therapist;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewScheduleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		try {
			loadTherapistWorkWeek(session);
			loadEmployees(session);
			loadAppointments(session);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		therapist_id = Integer.parseInt(request.getParameter("therapistDropdown"));
		therapist = getTherapist(request.getParameter("therapistDropdown"));
		String l_week_str = request.getParameter("weekpicker");	
		Date date = new Date(); 
		try { 
			date = sdf.parse(l_week_str); 
		} catch(ParseException e) { // TODO Auto-generated catch block 
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
		
		ArrayList<Appointment> emp_apps = getAppointments(therapist_id);
		ArrayList<Appointment> emp_apps_weekly = getAppointmentsByWeek(emp_apps, calendar);
		
		session.setAttribute("session_selectedWeekStr", schedule);
		session.setAttribute("session_selectedWeek", l_week_str);
		session.setAttribute("session_selectedTherapist", therapist);
		session.setAttribute("session_selectedAppointments", emp_apps_weekly);
		response.sendRedirect("jsp/schedule.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public static void loadTherapistWorkWeek(HttpSession session) throws ParseException {	
		workWeeks = (ArrayList<WorkWeek>) session.getAttribute("session_workWeeks");
	}
	
	public static void loadEmployees(HttpSession session) throws ParseException {	
		employees = (ArrayList<Employee>) session.getAttribute("session_employees");
	}
	
	public static void loadAppointments(HttpSession session) throws ParseException {	
		DB_Query l_query = new DB_Query();
		ArrayList<Appointment> appointmentArrList = l_query.getAppArrayList();
		session.setAttribute("session_appointments", appointmentArrList);
		appointments = (ArrayList<Appointment>) session.getAttribute("session_appointments");
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
	
	private Employee getTherapist(String therapist_id) {
		for (Employee e : employees) {
			if (e.getEmployee_Id() == Integer.parseInt(therapist_id)) {
				return e;
			}
		}
		return null;
	}
	
	public static ArrayList<Appointment> getAppointments(int empID){ //Gets all appointments associated to an employee
		ArrayList<Appointment> apps = new ArrayList<Appointment>();
		
		for (Appointment appt : appointments) {
			if (appt.getFk_employee_id() == empID) {
				apps.add(appt);
			}
		}
		
		return apps;
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
}
