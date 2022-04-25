package com.model;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import com.database.DB_Query;


/**
 * servlet handles cancelling an appointment and redirecting to passed url
 * @author dannyzorn
 *
 */
public class CancelAppointmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Cancels appointment passed in form and sets the page refresh boolean to ensure page data is up to date
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// set session vars
		HttpSession session = request.getSession();
		DB_Query query = new DB_Query();
		int l_appId = Integer.parseInt(request.getParameter("appIdIn1"));
		Appointment appointment = query.getAppointment(l_appId);
		
		//preferred url to redirect
		String url = request.getParameter("url");
		
		if (appointment != null){
			query.removeAppointment(appointment);
			
			// set flag to refresh page data
			session.setAttribute("session_refreshPage", true);
			
			//redirect to arg url
			response.sendRedirect(url);
		}
	}
}
