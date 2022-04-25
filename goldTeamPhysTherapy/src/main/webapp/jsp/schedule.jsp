<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.model.Employee" %>
<%@ page import="com.model.Appointment" %>
<%@ page import="com.model.Therapy" %>
<%@ page import="com.model.Customer" %>

<!-- verify user session before access -->
<div id=${session_type} name="userType"></div>
<%
	if(session.getAttribute("session_User")==null){
		response.sendRedirect("../index.jsp");
	}
%>

<%
	ArrayList <Employee> employees = (ArrayList <Employee>) session.getAttribute("session_employees");
	ArrayList <Customer> customers = (ArrayList <Customer>) session.getAttribute("session_customers");
	Employee selectedTherapist = (Employee) session.getAttribute("session_selectedTherapist");
	String selectedWeekStr = (String) session.getAttribute("session_selectedWeekStr");
	String selectedWeek = (String) session.getAttribute("session_selectedWeek");
	ArrayList<Therapy> therapies = (ArrayList<Therapy>) session.getAttribute("session_therapies");	
	ArrayList <Appointment> appointments = (ArrayList <Appointment>) session.getAttribute("session_selectedAppointments");
	String therapistStr = "";
	//set flag to refresh the page contents following a change
	Boolean refreshPage = (Boolean) session.getAttribute("session_refreshPage");
	Boolean scheduleChanged = (Boolean) session.getAttribute("session_scheduleChanged");
	
	if (selectedTherapist != null){
		therapistStr = selectedTherapist.getEmployee_fname();
	}
%>
<script>
	let therapist = '<%=therapistStr%>';
	let workWeek = '<%=selectedWeek%>';
	let scheduleWeek = '<%=selectedWeekStr%>';
	
	<%if(refreshPage!=null){%>
		<%= "let refreshPage = '" + refreshPage + "';" %> 
		<% if(refreshPage == true){%>
			<%	session.setAttribute("session_refreshPage",false);%>
		<%}%>
	<%}%>
	
	<%if(scheduleChanged!=null){%>
		<%= "let scheduleChanged = '" + scheduleChanged + "';" %> 
		<% if(scheduleChanged == true){%>
			<%	session.setAttribute("session_scheduleChanged",false);%>
		<%}%>
	<%}%>
	
	<% if (appointments != null) { %>
	let apps = [
		<% for (int i=0; i<appointments.size(); i++) { 
			Date appDate = appointments.get(i).getAppointment_startDateTime();
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(appDate);
		    int appDateInt = cal.get(Calendar.DAY_OF_WEEK);
			int therapyId = appointments.get(i).getFk_therapy_id();
			int appTime = cal.get(Calendar.HOUR_OF_DAY);
			int appId = appointments.get(i).getAppointment_id();
			int custId = appointments.get(i).getFk_customer_id();
			int therapistId = appointments.get(i).getFk_employee_id();
			String appNotes = appointments.get(i).getAppointment_desc();
			String appLastName = "";
			String appFirstName = "";
			String appEmail = "";
			String appPNumber = "";
			for (Customer c : customers) {
				if (c.getCustomer_id() == custId) {
					appLastName = c.getCustomer_lname();
					appFirstName = c.getCustomer_fname();
					appEmail = c.getCustomer_Email();
					appPNumber = c.getCustomer_PhoneNum();
				}
			}
			String appTherapist = "";
			for (Employee e : employees) {
				if (e.getEmployee_Id() == therapistId) {
					appTherapist = e.getEmployee_fname();
				}
			}
			String appTherapy = "";
			for (Therapy t : therapies) {
				if (t.getTherapy_id() == therapyId) {
					appTherapy = t.getTherapy_name();
				}
			} %>
			{
				'id' : '<%=appId%>',
				'date' : '<%=appDate%>',
				'day' : '<%=appDateInt%>',
				'time' : '<%=appTime%>',
				'therapy' : "<%=appTherapy%>",
				'therapist' : "<%=appTherapist%>",
				'fname' : "<%=appFirstName%>",
				'lname' : "<%=appLastName%>",
				'email' : "<%=appEmail%>",
				'pNumber': "<%=appPNumber%>",
				'notes' :"<%=appNotes%>"
			},	
		<% } %>
		];
	<% } %>	
</script>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
    <title>Therapist Schedule</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link rel="stylesheet" href="../css/jquery-ui.min.css">
    <link href='https://fonts.googleapis.com/css?family=Open Sans' rel='stylesheet'>
    <script src="../js/jquery-3.6.0.js"></script>
    <script src="../js/jquery-ui.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-csv/1.0.21/jquery.csv.min.js"></script>
    <script src="../js/schedule.js"></script>
</head>
<body>
<div id=${session_type} name="userType"></div>
<div class="overlay"></div>
<div class="header">
 	<h1>Therapist Schedule</h1>
 	<div>
 		<a href="../jsp/home.jsp"><img src="../img/home.png"></a>
		<a href="../LogoutServlet"><img src="../img/logout.png"></a>
 	</div>
</div>
<div class="main">
	<div class="sidebar">
		<img src="../img/logo-round.jpg">
		<div class="sideMenu">
			<a href="../jsp/home.jsp">Home<br></a>
			<a class="administrator" href="../jsp/certifications.jsp">Certifications<br></a>
			<a class="administrator" href="../jsp/therapists.jsp">Employees<br></a>
			<a class="active" href="../jsp/schedule.jsp">Schedule<br></a>
			<a href="../jsp/bookAppointment.jsp">Book Appointment<br></a>
			<a href="../jsp/appointmentSchedule.jsp">Appointments</a>
			<a class="administrator" href="../jsp/manageSchedule.jsp">Manage Hours</a>
			<a href="userProfile.jsp">User Profile</a>
		</div>
	</div>
	<div class="scheduleMain">
	    <div class="searchSchedule">
	        <h3>Please choose a therapist and week to view schedule: </h3>
	        <form id="searchScheduleForm">
	            <label for="therapistDropdown">Therapist:&nbsp;&nbsp;</label>
	            <select name="therapistDropdown" id="therapistDropdown" required>
	            	<!-- prepopulate if a therapist is already logged in -->
	            	<% if(session.getAttribute("session_User")!=null){%>
							<% Employee user = (Employee)session.getAttribute("session_User"); %>
							<%= "<option value='" + user.getEmployee_Id() + "'>" + user.getEmployee_fname() + "</option>" %>
						<%}%>
						
	            	<% if(selectedTherapist == null){%>
					  	<% for (int idx = 0; idx < employees.size(); idx++ ) {
							 	Employee therapist = (Employee) employees.get(idx);%>
							<%= "<option value='" + therapist.getEmployee_Id() + "'>" + therapist.getEmployee_fname() + "</option>" %>
						<% }}
	            	else{ %>
	            		<%= "<option value='" + selectedTherapist.getEmployee_Id() + "' selected>" + selectedTherapist.getEmployee_fname() + "</option>" %>
	            		<% for (int idx = 0; idx < employees.size(); idx++ ){
							 	Employee therapist = (Employee) employees.get(idx);
							if (therapist.getEmployee_Id() != selectedTherapist.getEmployee_Id()){ %>
								<%= "<option value='" + therapist.getEmployee_Id() + "'>" + therapist.getEmployee_fname() + "</option>" %>
							<% }}} %>
	            </select>
	            <label for="weekpicker">Week Beginning:&nbsp;&nbsp;</label>
	            <input type="text" id="weekpicker" name="weekpicker" readonly="readonly" value=${session_selectedWeek}>
	            <button type="button" class="btn" id="viewScheduleButton" onclick="callViewScheduleServlet()">View Schedule</button>
   		
	            <!-- <input id="viewScheduleButton" type="submit" value="View Schedule"> -->
	        </form>
	   	</div>
	   	<input id="printSchedule" type="submit" value="Print Page" name="download" onclick="window.print()" /> 
	   	<div id="scheduleHeader"></div>
		<div id="weeklySchedule">
	        <table id="scheduleTable" cellspacing="0">
	            <tr id="days">
	            </tr>
	            <tbody>
				<tr id="eight">
				</tr>
				<tr id="nine">
				</tr>
				<tr id="ten">
				</tr>
				<tr id="eleven">
				</tr>
				<tr id="twelve">
				</tr>
				<tr id="one">
				</tr>
				<tr id="two">
				</tr>
				<tr id="three">
				</tr>
				<tr id="four">
				</tr>	
				</tbody>
	        </table>
			<br><br>
	    </div>    
	</div>
</div>
<div class="form-popup" id="myForm">
  <div class="form-container">
    <h3>Appointment Details: </h3>
    <div class="appDetailsTitle">Confirmation #:&nbsp;&nbsp;</div>
	<div class="appDetails" id="appId" ></div><br>
    <div class="appDetailsTitle">First Name:&nbsp;&nbsp;</div>
	<div class="appDetails" id="appFirstName" ></div><br>
	<div class="appDetailsTitle">Last Name:&nbsp;&nbsp;</div>
	<div class="appDetails" id="appLastName" ></div><br>
	<div class="appDetailsTitle">Phone Number:&nbsp;&nbsp;</div>
	<div class="appDetails" id="appPNumber" ></div><br>
	<div class="appDetailsTitle">Email:&nbsp;&nbsp;</div>
	<div class="appDetails" id="appEmail" ></div><br>
	<div class="appDetailsTitle">Therapy:&nbsp;&nbsp;</div>
	<div class="appDetails" id="appTherapy" ></div><br>
	<div class="appDetailsTitle">Therapist:&nbsp;&nbsp;</div>
	<div class="appDetails" id="appTherapist" ></div><br>
	<div class="appDetailsTitle">Date:&nbsp;&nbsp;</div>
	<div class="appDetails" id="appDate" ></div><br>
	<div class="appDetailsTitle">Time:&nbsp;&nbsp;</div>
	<div class="appDetails" id="appTime" ></div><br>
	
	<!-- input form -->
	<form id="editNotesForm" class="appForm">	
	 	<input type="hidden" name="editAppId" id="editAppId" required>
    	<label for="appNotes"><strong>Notes:&nbsp;&nbsp;</strong></label>
   	 	<textarea name="appNotes" id="appNotes" maxlength="255" readonly ></textarea>
	  	<input type="hidden" name="url" value="jsp/schedule.jsp" required>
	  	<div id="confBtnContainer">
   			<button type="button" class="btn" id="editAppButton" onclick="editTextArea()">Edit</button>
   			<button type="button" class="btn" id="saveChangesButton" onclick="callEditServlet()">Save Changes</button>
   		</div>
   	</form>
	<!-- end input form -->
	
	<div id="btnContainer">
    	<button type="button" class="btn" onclick="closeForm()">Ok</button>
       	<button type="button" class="btn cancel" id="cancelAppButton" onclick="openConfirmationForm('cancel')">Cancel Appointment</button>
  	</div>
  </div>
</div> 

<!-- confirmation window -->
<div class="form-popup" id="confirmationForm">
  <div class="form-container">
	<div id="btnContainer">
	  	<form id=cancelConfirmationForm class="appForm">
	  		<input type="hidden" name="appIdIn1" id="appIdIn1" required>
	  		<input type="hidden" name="url" value="jsp/schedule.jsp" required>
			<h3 class="confTitle" id="confirmMsg"></h3>
	    	<button type="button" class="btn" onclick="callCancelServlet()">Ok</button>
	    	<button type="button" class="btn cancel" onclick="closeForm()">Cancel</button>
	    </form>
  	</div>
  </div>
</div> 
<!-- end confirmation window -->
 
</body>
</html> 