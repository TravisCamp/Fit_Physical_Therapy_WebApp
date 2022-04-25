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
import com.model.Appointment;
import com.model.Employee;

//Creates URL to make directing to servlet easier
@WebServlet("/DeleteEmpServlet")
/**
 * Servlet implementation class AddCertificationServlet
 */
public class DeleteEmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 	{
		//init local vars
		String l_id = request.getParameter("empRemoveDropdown");
		HttpSession session = request.getSession();
		ArrayList<Employee> employeeArrList = new ArrayList<>();
		DB_Query l_query = new DB_Query();
		employeeArrList = l_query.getSortedEmpArrayList();
		Employee emp = getEmployee(employeeArrList,Integer.parseInt(l_id));
		ArrayList<Appointment> appointmentArrList = (ArrayList<Appointment>) session.getAttribute("session_appointments");
		Boolean hasAppointments = empHasAppointments(emp, appointmentArrList);
		
		if (hasAppointments == false) {
			l_query.removeEmployeesWorkWeeks(response, emp);
			l_query.removeAllCertFromEmployee(response, emp.getEmployee_Id());
			l_query.removeEmp(response, Integer.parseInt(l_id));
			employeeArrList = l_query.getSortedEmpArrayList();
			
		}
		
		else
		{
			employeeArrList = l_query.getEmpArrayList();
			
		}
		
		// refresh session var(s)
		session.setAttribute("session_employees", employeeArrList);
		session.setAttribute("session_invalid", hasAppointments.toString());
		// redirect
		response.sendRedirect("jsp/therapists.jsp");
	}
	
	private Boolean empHasAppointments(Employee emp, ArrayList<Appointment> appointmentArrList) {
		Calendar today = Calendar.getInstance();
		for (Appointment appt : appointmentArrList) {
			if (appt.getFk_employee_id() == emp.getEmployee_Id()) {
				Calendar apptTime = Calendar.getInstance();
				apptTime.setTime(appt.getAppointment_startDateTime());
				if (today.compareTo(apptTime) <= 0) {
					return true;
				}
			}
		}
		return false;
	}

	private Employee getEmployee(ArrayList<Employee> employeeArrList, int id) {
		Employee emp = null;
		for (Employee employee : employeeArrList) {
			if (employee.getEmployee_Id() == (id)) {
				emp = employee;
				break;
			}
		}
		
		return emp;
	}
}