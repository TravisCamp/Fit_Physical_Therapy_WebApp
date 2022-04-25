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
import com.model.Certification;

//Creates URL to make directing to servlet easier
@WebServlet("/LoadEmpCertificationsServlet")
/**
 * Servlet implementation class AddCertificationServlet
 */
public class LoadEmpCertificationsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//init local vars
		String l_id = request.getParameter("empManage");
		HttpSession session = request.getSession();
		ArrayList<Certification> certificationArrList = new ArrayList<>();
		ArrayList<Employee> employeeArrList = new ArrayList<>();
		String empName = "";

		DB_Query l_query = new DB_Query();
		
		certificationArrList = l_query.getEmployeeCertifications(Integer.parseInt(l_id));
		empName = l_query.getEmployee(Integer.parseInt(l_id));
		System.out.println(empName);
		
		employeeArrList = l_query.getSortedEmpArrayList();
		
		// refresh session var(s)
		session.setAttribute("session_employees", employeeArrList);
		session.setAttribute("session_empCertifications", certificationArrList);
		session.setAttribute("session_employee", empName);
		session.setAttribute("session_manage", l_id);
		
		// redirect
		request.getRequestDispatcher("jsp/therapistsCert.jsp").forward(request, response);
	}
}