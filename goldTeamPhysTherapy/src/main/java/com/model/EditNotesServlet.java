package com.model;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import com.database.DB_Query;

@WebServlet("/EditNotesServlet")

/**
 * Servlet implementation class EditNotes
 */
public class EditNotesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DB_Query query = new DB_Query();
		int app_id = Integer.parseInt(request.getParameter("editAppId"));
		String app_notes = request.getParameter("appNotes");
		Appointment appointment = query.getAppointment(app_id);
		HttpSession session = request.getSession();	
		
		//preferred url to redirect
		String url = request.getParameter("url");
		
		query.editAppointmentNotes(appointment, app_notes);
		
		// set flag to refresh the contents of the schedule page
		session.setAttribute("session_refreshPage", true);
		
		// redirect
		response.sendRedirect(url);
	}
}
