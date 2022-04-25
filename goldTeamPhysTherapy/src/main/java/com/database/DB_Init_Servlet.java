package com.database;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import com.model.Appointment;
import com.model.Certification;
import com.model.Customer;
import com.model.Employee;
import com.model.Therapy;
import com.model.WorkWeek;

/**
 * Servlet implementation class DB_Init_Servlet
 */
public class DB_Init_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		//init local vars
		int NUMWEEKS = 16;
		HttpSession session = request.getSession();
		DB_Query l_query = new DB_Query();
		ArrayList<Appointment> appointmentArrList = new ArrayList<>();
		ArrayList<Certification> certificationArrList = new ArrayList<>(); 		// Create an ArrayList object
		ArrayList<Customer> customerArrList = new ArrayList<>();				// Create an ArrayList object
		ArrayList<Employee> employeeArrList = new ArrayList<>();				// Create an ArrayList object
		ArrayList<Therapy> therapyArrList = new ArrayList<>();					// Create an ArrayList object
		ArrayList<WorkWeek> workWeekArrList = new ArrayList<>();				// Create an ArrayList object
		
		/*
		 * Process of auto deleting and adding workWeeks to the DB whenever launched
		 */
		l_query.removeOldWorkWeeks();
		l_query.SelectOldAppointments();
		ArrayList<WorkWeek> workWeekTemplateArrList = new ArrayList<>();
		workWeekTemplateArrList = l_query.getEmployeeWorkWeekTemplateArrayList();
		Calendar calendar = Calendar.getInstance();
		
		int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayNum != Calendar.MONDAY) {
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		}
		
		int numWeeks = NUMWEEKS;
		while (numWeeks > 0) { 
			for (WorkWeek week : workWeekTemplateArrList) {
				l_query.insertSchedule(week, calendar);
			}
			//TODO replace the 1 with a meaningful var
			calendar.add(Calendar.WEEK_OF_YEAR, 1); //Adds a week to the calendar objects 
			numWeeks--;
		}
		
		//init session vars
		appointmentArrList = l_query.getAppArrayList();
		certificationArrList = l_query.getCertArrayList();
		customerArrList = l_query.getCustomerArrayList();
		employeeArrList = l_query.getSortedEmpArrayList();
		therapyArrList = l_query.getTherapyArrayList();
		workWeekArrList = l_query.getEmployeeWorkWeekArrayList();
		session.setAttribute("session_workWeeksLength", NUMWEEKS);
		session.setAttribute("session_appointments", appointmentArrList);
		session.setAttribute("session_certifications", certificationArrList);
		session.setAttribute("session_customers", customerArrList);
		session.setAttribute("session_employees", employeeArrList);
		session.setAttribute("session_therapies", therapyArrList);
		session.setAttribute("session_workWeeks", workWeekArrList);
		session.setAttribute("session_invalidUpdate", "null");
		
		//inits the user name and password of all employees
		initUnassignedUnamePw(employeeArrList,l_query);
		
		//go to home page
		response.sendRedirect("jsp/home.jsp");
	}
	
	private void initUnassignedUnamePw(ArrayList<Employee> employeeArrList, DB_Query l_query) {
		// function creates random n number of chars
		int length = 5;
	    char [] characters      = {'A','B','C','D','E','F','G','H','I','J','K',
                'L','M','N','O','P','Q','R','S','T','U','V',
                'W','X','Y','Z','a','b','c','d','e','f','g',
                'h','i','j','k','l','m','n','o','p','q','r',
                's','t','u','v','w','x','y','z','0','1','2',
                '3','4','5','6','7','8','9','!','?'};
	    String result          = "";
	    
	    for(Employee emp : employeeArrList) {
	    	if(emp.getEmployee_uname()==null) {
		    	result = emp.getEmployee_fname().toLowerCase() + '_';
			    for ( var i = 0; i < length; i++ ) {
			      result += String.valueOf( characters[(int) Math.floor(Math.random() * characters.length)]);
			   }
			    emp.setEmployee_uname(result);
			    l_query.updateEmp(emp);
			   System.out.println(result);
	    	}
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
