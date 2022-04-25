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

@WebServlet("/AppScheduleServlet")


public class AppScheduleServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static SimpleDateFormat sdfApp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private static ArrayList<Appointment> appointments = new ArrayList<Appointment>();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session = request.getSession();
    	try 
    	{
    		loadAppointments(session);
    	}	catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String l_day_str = request.getParameter("daypicker");
    	Date date = new Date();
    	Calendar dayCalendar = Calendar.getInstance();
    	
    	try { 
			date = sdf.parse(l_day_str);
			dayCalendar.setTime(date);
			dayCalendar.set(Calendar.MINUTE, 0);
			dayCalendar.set(Calendar.SECOND, 0);
			
		} catch(ParseException e) { // TODO Auto-generated catch block 
			e.printStackTrace();
		}
    	String dayString = sdfApp.format(dayCalendar.getTime());
    	System.out.println(date);
    	System.out.println(l_day_str);
    	System.out.println(dayString);
    	ArrayList<Appointment> emp_apps = getAppointments();
		ArrayList<Appointment> emp_apps_daily = getAppointmentsByDay(emp_apps, dayCalendar);
		
		// refresh session var(s)
		session.setAttribute("session_selectedDayStr", dayString);
		session.setAttribute("session_selectedDay", l_day_str);
		session.setAttribute("session_selectedAppointments", emp_apps_daily);
		
		// redirect  
		response.sendRedirect("jsp/appointmentSchedule.jsp");
    }
    
    public static void loadAppointments(HttpSession session) throws ParseException {	
		DB_Query l_query = new DB_Query();
		ArrayList<Appointment> appointmentArrList = l_query.getAppArrayList();
		session.setAttribute("session_appointments", appointmentArrList);
		appointments = (ArrayList<Appointment>) session.getAttribute("session_appointments");
	}
    
    public static ArrayList<Appointment> getAppointments(){ //Gets all appointments associated to an employee
		ArrayList<Appointment> apps = new ArrayList<Appointment>();
		
		for (Appointment appt : appointments) {
			{
				apps.add(appt);
			}
		}
		
		return apps;
	}
    
    public static ArrayList<Appointment> getAppointmentsByDay(ArrayList<Appointment> appts, Calendar calendar){ //Gets all appointments in a specific week
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		Calendar apptDay = Calendar.getInstance();
		
		for (Appointment appt : appts) {
			apptDay.setTime(appt.getAppointment_startDateTime());
			if (apptDay.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)) 
			{
				appointments.add(appt);
			}
		}
		
		return appointments;
	}
}


