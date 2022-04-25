package com.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import com.database.DB_Query;
import com.model.Employee;

/**
 * CreateAccountServlet class allows user's who exist in the defined database Employee table
 * to create a user name and password.
 * @author dannyzorn
 *
 */
public class CreateAccountServlet extends HttpServlet {
	//init class vars
	private static final long serialVersionUID = 1L;

	/**
	 * load createAccount page
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//init local vars
		String l_fname = request.getParameter("fname");
		String l_lname = request.getParameter("lname");
		String l_uname = request.getParameter("uname");
		String l_password = request.getParameter("password");
		
		//only executed queries that are !NULL and !Empty 
		if(!(l_fname==null)) {
			if(!(l_fname.isBlank())) {
				DB_Query l_query = new DB_Query();
				Employee emp = new Employee();
				
				//find the employee by their user name
				emp = l_query.getEmployee(l_uname);
				
				//modify the employee to the new input fields
				emp.setEmployee_fname(l_fname);
				emp.setEmployee_lname(l_lname);
				emp.setEmployee_password(l_password);
				
				//update the database with changes
				l_query.updateEmp(emp);
				
				//update the session user to refresh the page data
				HttpSession session = request.getSession();
				session.setAttribute("session_User", emp);
				
				//redirect to user profile page
				response.sendRedirect("jsp/userProfile.jsp");
			}	
		}
	}
}
