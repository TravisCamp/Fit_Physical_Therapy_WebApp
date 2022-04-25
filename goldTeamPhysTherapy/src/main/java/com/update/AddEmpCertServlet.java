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
import com.model.Certification;
import com.model.Employee;

//Creates URL to make directing to servlet easier
@WebServlet("/AddEmpCertServlet")
/**
 * Servlet implementation class AddCertificationServlet
 */
public class AddEmpCertServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//init local vars
		String l_id = request.getParameter("addCertDropDown");
		String l_fName = request.getParameter("newFName");
		String l_empId = request.getParameter("empManage");
		HttpSession session = request.getSession();
		ArrayList<Employee> employeeArrList = new ArrayList<>();
		ArrayList<Certification> certificationArrList = new ArrayList<>();
		DB_Query l_query = new DB_Query();

		l_query.insertCertification(response, Integer.parseInt(l_id), l_fName);
		certificationArrList = l_query.getEmployeeCertifications(Integer.parseInt(l_empId));
		employeeArrList = l_query.getSortedEmpArrayList();
		
		// refresh session var(s)
		session.setAttribute("session_employees", employeeArrList);
		session.setAttribute("session_empCertifications", certificationArrList);
		
		// redirect
		response.sendRedirect("jsp/therapistsCert.jsp");
	}

}