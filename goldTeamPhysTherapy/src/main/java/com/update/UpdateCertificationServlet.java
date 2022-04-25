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

@WebServlet("/UpdateCertificationServlet")
public class UpdateCertificationServlet extends HttpServlet {
	
	//init class vars
	private static final long serialVersionUID = 2L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//init local vars
		String l_code = request.getParameter("certCodesDropdown");
		String l_desc = request.getParameter("therapyUpdate");
		String l_name = request.getParameter("certUpdate");
		HttpSession session = request.getSession();
		ArrayList<Therapy> therapyArrList = new ArrayList<>();
		ArrayList<Certification> certificationArrList = new ArrayList<>(); 
		//only are executed queries that are !NULL and !Empty 
		if(!(l_desc==null) && !(l_name==null)) {
			if(!(l_desc.isBlank()) && !(l_name.isBlank())) {
				DB_Query l_query = new DB_Query();
				l_query.updateCert(response, l_desc,l_name,l_code);
				therapyArrList = l_query.getTherapyArrayList();
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
