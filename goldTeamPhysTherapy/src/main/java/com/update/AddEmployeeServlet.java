package com.update;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import com.database.DB_Query;
import com.model.Employee;
import com.model.WorkWeek;

//Creates URL to make directing to servlet easier
@WebServlet("/AddEmployeeServlet")
/**
 * Servlet implementation class AddCertificationServlet
 */
public class AddEmployeeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//init local vars
		String l_fName = request.getParameter("newFName");
		String l_lName = request.getParameter("newLName");
		String empType = request.getParameter("empTypeDropdown");
		String empUserName = request.getParameter("newUserName");
		String empPassword = request.getParameter("newPassword");
		HttpSession session = request.getSession();
		ArrayList<Employee> employeeArrList = new ArrayList<>();

		//only are executed queries that are !NULL and !Empty 
		if(!(l_fName==null)) {
			if(!(l_fName.isBlank())) {
				DB_Query l_query = new DB_Query();
				
				l_query.insertEmp(response, l_fName,l_lName, Integer.parseInt(empType), empUserName, empPassword);
				employeeArrList = l_query.getSortedEmpArrayList();
				Employee emp = l_query.getLastAddedEmployee();
				l_query.insertBlankTemplate(emp); //Creates workWeek template for newly created employee
				createEmpWorkWeeks(emp, l_query, session); //Function that creates workWeeks based off of template for newly made employee
				ArrayList<WorkWeek> workWeekArrList = l_query.getEmployeeWorkWeekArrayList(); //Reinitializes workWeekArrList for session
				
				// refresh session var(s)
				session.setAttribute("session_employees", employeeArrList);
				session.setAttribute("session_workWeeks", workWeekArrList);
			}
			// redirect
			response.sendRedirect("jsp/therapists.jsp");
		}
	}
	/**
	 * creates workWeeks for newly created employee.
	 * @param emp
	 * @param l_query
	 * @param session
	 */
	public void createEmpWorkWeeks(Employee emp, DB_Query l_query, HttpSession session) {
		int numWeeks = (int) session.getAttribute("session_workWeeksLength");
		WorkWeek template = l_query.getWorkWeekTemplate(emp);
		Calendar calendar = Calendar.getInstance();
		while (numWeeks > 0) { 
			l_query.insertSchedule(template, calendar); //Adds workWeek for employee based off of calendar and workWeek template

			//TODO replace the 1 with a meaningful var
			calendar.add(Calendar.WEEK_OF_YEAR, 1); //Adds a week to the calendar objects 
			numWeeks--;
		}
	}
}