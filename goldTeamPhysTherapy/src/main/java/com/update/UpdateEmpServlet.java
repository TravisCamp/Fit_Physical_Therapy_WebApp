package com.update;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import com.database.DB_Query;
import com.model.Employee;

//Creates URL to make directing to servlet easier
@WebServlet("/UpdateEmployeeServlet")
/**
 * Servlet implementation class AddCertificationServlet
 */
public class UpdateEmpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//init local vars
		String l_empId = request.getParameter("empDropdown");
		String l_lName = request.getParameter("lNameUpdate");
		String l_fName = request.getParameter("fNameUpdate");
		String userType = request.getParameter("empTypeDropdownU");
		String userName = request.getParameter("updateUserName");
		String password = request.getParameter("updatePassword");
		HttpSession session = request.getSession();
		ArrayList<Employee> employeeArrList = new ArrayList<>();

		if(!(l_fName==null)) {
			if(!(l_fName.isBlank())) {
				DB_Query l_query = new DB_Query();
				
				l_query.updateEmp(response, Integer.parseInt(l_empId),l_lName, l_fName, Integer.parseInt(userType), userName, password);
				employeeArrList = l_query.getSortedEmpArrayList();
			}
			
			//refresh session var(s)
			session.setAttribute("session_employees", employeeArrList);
			
			// redirect
			response.sendRedirect("jsp/therapists.jsp");
		}
	}
}