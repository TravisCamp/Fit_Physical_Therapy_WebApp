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

@WebServlet("/DeleteCertificationServlet")
public class DeleteCertificationServlet extends HttpServlet {

	//init class vars
	private static final long serialVersionUID = 2L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//init local vars
		String l_code = request.getParameter("certCodesRemoveDropdown");
		HttpSession session = request.getSession();
		ArrayList<Therapy> therapyArrList = new ArrayList<>();
		ArrayList<Certification> certificationArrList = new ArrayList<>(); 
		
		//only are executed queries that are !NULL and !Empty 
		DB_Query l_query = new DB_Query();
		certificationArrList = l_query.getCertArrayList();
		Certification cert = getCertification(certificationArrList,l_code);
		
		Boolean certEmp = certHasEmp(cert);
		if (certEmp == true) {
		l_query.removeCertFromEmployees(response, l_code);
		l_query.removeCertification(response,l_code);
		therapyArrList = l_query.getTherapyArrayList();
		certificationArrList = l_query.getCertArrayList();
		}
		
		else
		{
			therapyArrList = l_query.getTherapyArrayList();
			certificationArrList = l_query.getCertArrayList();
		}
		// refresh session var(s)
		session.setAttribute("session_therapies", therapyArrList);
		session.setAttribute("session_certifications", certificationArrList);
		session.setAttribute("session_certInvalid", certEmp.toString());
		
		// redirect
		response.sendRedirect("jsp/certifications.jsp");
	}
	
	private Boolean certHasEmp(Certification cert) {
		ArrayList<String> certEmpExists = new ArrayList<>(); 
		certEmpExists = new DB_Query().getCertificationEmployees(cert.getCertification_id());
		System.out.println(certEmpExists);
			if (certEmpExists.isEmpty()) {
					return true;
				}
		return false;
	}
	
	private Certification getCertification(ArrayList<Certification> certificationArrList, String code) {
		Certification cert = null;
		int certId = new DB_Query().getCertification(code);
		for (Certification certification : certificationArrList) {
			if (certId == (certification.getCertification_id())) {
				cert = certification;
				break;
			}
		}
		
		return cert;
	}
	
}
