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
import com.model.Therapy;

//Creates URL to make directing to servlet easier
@WebServlet("/AddCertificationServlet")
/**
 * Servlet implementation class AddCertificationServlet
 */
public class AddCertificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//init local vars
		String l_code = request.getParameter("newCode");
		String l_desc = request.getParameter("newTherapy");
		String l_name = request.getParameter("newCert");
		HttpSession session = request.getSession();
		ArrayList<Therapy> therapyArrList = new ArrayList<>();
		ArrayList<Certification> certificationArrList = new ArrayList<>(); 

		//only are executed queries that are !NULL and !Empty 
		if(!(l_desc==null) && !(l_name==null) && !(l_code==null)) {
			if(!(l_desc.isBlank()) && !(l_name.isBlank()) && !(l_code.isBlank())) {
				DB_Query l_query = new DB_Query();			
				l_query.insertTherapy(response, l_desc,l_name);
				DB_Query s_query = new DB_Query();
				
				s_query.insertNewCertification(response, l_code, l_name);
				therapyArrList = l_query.getSortedTherapyArrayList();
				certificationArrList = l_query.getCertArrayList();
			}
			
			// refresh session var(s)
			session.setAttribute("session_therapies", therapyArrList);
			session.setAttribute("session_certifications", certificationArrList);	
			
			// redirect
			response.sendRedirect("jsp/certifications.jsp");
		}
	}
}
