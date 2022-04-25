package com.login;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import com.database.DB_Query;
import com.model.Employee;

/**
 * LoginServlet class verifies username and pasword
 * @author dannyzorn
 *
 */
public class LoginServlet extends HttpServlet {
	//init class vars
	private static final long serialVersionUID = 1L;
	private static Employee user;
	
	public static Employee getUser() {
		return user;
	}

	public static void setUser(Employee user) {
		LoginServlet.user = user;
	}

	/**
	 * logs in user based on their l_uname. will change to userType in future
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String l_uname = request.getParameter("uname");
		String l_password = request.getParameter("password");
		
		if(validateUser(l_uname,l_password)) {
			session.setAttribute("session_User", getUser());
			
			//TODO remove and replace with session_User
			session.setAttribute("session_uFName", getUser().getEmployee_fname());
			session.setAttribute("session_type", getUser().getEmployee_userTyp());
			
			System.out.println(session.getAttribute("session_uFName"));
			
			//forward request, init session vars and redirect to home page
			RequestDispatcher l_rd = request.getRequestDispatcher("DB_Init_Servlet");
			l_rd.forward(request, response);
		} else
			response.sendRedirect("index.jsp");
	}

	/**
	 * check if uname and password are valid
	 * @param l_uname
	 * @param l_password
	 * @param allEmps
	 * @return 
	 */
	private boolean validateUser(String l_uname, String l_password) {
		DB_Query l_query = new DB_Query();
		ArrayList<Employee> allEmps = l_query.getEmpArrayList();
		
		//validate uname and password against all other emps
		for(Employee emp : allEmps) {
			if( (emp.getEmployee_uname()!=null)&& (emp.getEmployee_password()!=null) ){
				if( (emp.getEmployee_uname().equals(l_uname))&& (emp.getEmployee_password().equals(l_password))) {
					setUser(emp);
					return true;
				}
			}
		}
		return false;
	}
}
