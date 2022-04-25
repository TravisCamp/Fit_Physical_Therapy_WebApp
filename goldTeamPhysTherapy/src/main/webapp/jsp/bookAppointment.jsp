<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.model.Therapy" %>
<%@ page import="com.model.Employee" %>
<%@ page import="com.model.Appointment" %>
<%@ page import="com.database.DB_Query" %>

<!-- verify user session before access -->
<div id=${session_type} name="userType"></div>
<%
	if(session.getAttribute("session_type")==null){
		response.sendRedirect("../index.jsp");
	}
%>

<%
	ArrayList<Employee> validTherapists = (ArrayList<Employee>) session.getAttribute("session_validTherapists"); 
	ArrayList<Therapy> therapies = (ArrayList<Therapy>) session.getAttribute("session_therapies");
	Therapy selectedTherapy = (Therapy) session.getAttribute("session_selectedTherapy");
	Appointment selectedAppointment = (Appointment) session.getAttribute("session_selectedAAppointment");
	String confirmValue = (String) session.getAttribute("session_confirmValue");
	String selectedAppDate = (String) session.getAttribute("session_selectedAppDateStr");
	String empCertStr = "";
	String empShiftStr = "";
	String empApptStr = "";
	String empfNameStr = "";
	String appTherapy = "";
	String appTime = "";
	String appTherStr = "";
	String appTimeStr = "";
	int appId = 0;
%>
<!-- Load in therapist from database for schedule retrieval -->
<script type="text/javascript">
	<% if(selectedAppointment != null && confirmValue != null && confirmValue == "true"){ %>
		let confirmationV = <%=confirmValue%>;
		let selAppt = {
			<% appId = selectedAppointment.getAppointment_id(); %>
			<% empfNameStr = new DB_Query().getEmployee(selectedAppointment.getFk_employee_id());  %>
			<% appTherStr = new DB_Query().getTherapy(selectedAppointment.getFk_therapy_id()); %>
			<% appTimeStr = selectedAppointment.getAppointment_startDateTime().toString(); 
			
			%>
				'selAppId' : '<%=appId%>',
				'empFName' : "<%=empfNameStr%>",
 				'appTher' : "<%=appTherStr%>",
 				'appTime' : "<%=appTimeStr%>"
			}
	<%}%>
	<% if( (validTherapists!=null) && (selectedAppDate!=null) ){%>
	<%="appDate = "+selectedAppDate+";" %>
	<%="therapyId = "+selectedTherapy.getTherapy_id()+";" %>
	<%="appTherapy = "+"\""+selectedTherapy.getTherapy_name()+"\""+";" %>
		const therapists = [
		<% for (int idx = 0; idx < validTherapists.size(); idx++ ){%>
		<% 		Employee emp = (Employee)validTherapists.get(idx);%>
		<%		empCertStr = new DB_Query().getEmployeeCert(emp.getEmployee_Id()); %>
		<%		empShiftStr = (String) session.getAttribute("session_empWorkDayShiftStr"+emp.getEmployee_Id()); %>
		<%		empApptStr = (String) session.getAttribute("session_empWorkDayApptsStr"+emp.getEmployee_Id()); %>
		<%= 	" { id: \""+ emp.getEmployee_Id()+"\", fname: \""+ emp.getEmployee_fname()+"\", lname: \""+ emp.getEmployee_lname()+"\", shift:"+empShiftStr+", scheduledAppTimes:"+empApptStr+",}," %>
		<%}%>
		<%="];"%>
	<%}%>
</script>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
    <title>Book Appointment</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link rel="stylesheet" href="../css/jquery-ui.min.css">
    <link href='https://fonts.googleapis.com/css?family=Open Sans' rel='stylesheet'>
    <script src="../js/jquery-3.6.0.js"></script>
    <script src="../js/jquery-ui.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-csv/1.0.21/jquery.csv.min.js"></script>
    <script src="../js/bookAppointment.js"></script>
</head>
<body>
<div id=${session_type} name="userType"></div>
<div class="header">
	<h1>Book Appointment</h1>
	<div>
		<a href="../jsp/home.jsp"><img src="../img/home.png"></a>
		<a href="../index.jsp"><img src="../img/logout.png"></a>
	</div>
</div>
<div class="main">
	<div class="sidebar">
		<img src="../img/logo-round.jpg">
		<div class="sideMenu">
			<a href="../jsp/home.jsp">Home<br></a>
			<a class="administrator" href="../jsp/certifications.jsp">Certifications<br></a>
			<a class="administrator" href="../jsp/therapists.jsp">Employees<br></a>
			<a href="../jsp/schedule.jsp">Schedule<br></a>
			<a class="active" href="../jsp/bookAppointment.jsp">Book Appointment<br></a>
			<a href="../jsp/appointmentSchedule.jsp">Appointments</a>
			<a class="administrator" href="../jsp/manageSchedule.jsp">Manage Hours</a>
			<a href="userProfile.jsp">User Profile</a>
		</div>
	</div>
	<div class="appointmentMain">
		<div class="overlay"></div>
	
	    <div class="searchAppointment">
	        <h3>Please choose a type of therapy and date of appointment: </h3>
	        <form id="searchAppointmentForm" action="../MainServlet" method="get">
	            <label for="therapyDropdown">Therapy:&nbsp;&nbsp;</label>
	            <select name="therapyDropdown" id="therapyDropdown" required>
	            	<% if(selectedTherapy == null){%>
						<%= "<option>Select Therapy</option>"%>
						<% for (int idx = 0; idx < therapies.size(); idx++ ){%>
							<% 	Therapy therapy = (Therapy)therapies.get(idx);%>
							<%= "<option value='" + therapy.getTherapy_id() + "'>" + therapy.getTherapy_name() + "</option>" %>
						<%}%>	
	            	<% }else{ %>
	            		<%= "<option value='" + selectedTherapy.getTherapy_id() + "'>" + selectedTherapy.getTherapy_name() + "</option>" %>
	            		<% for (int idx = 0; idx < therapies.size(); idx++ ){%>
							<% 	Therapy therapy = (Therapy)therapies.get(idx);%>
							<% if (therapy.getTherapy_id() != selectedTherapy.getTherapy_id()){ %>
							<%= "<option value='" + therapy.getTherapy_id() + "'>" + therapy.getTherapy_name() + "</option>" %>
						<%}}%>
	            	<% } %>	
	            </select>
	            <label for="dateDropdown">Date:&nbsp;&nbsp;</label>
	            <input type="text" id="datepicker" name="appDate" readonly="readonly" value=${session_selectedAppDateStr}>
	           
	            <input id="searchAppointmentButton" type="submit" value="Search Appointments" disabled>
	        </form>
	    </div>
	    <div class="appointmentList">
	        <table>
	            <tbody id="availableAppointments">
	            
	            </tbody>
	        </table>
	    </div>
	</div>
</div>
 <div class="form-popup" id="myForm">
  <form class="form-container" id="bookingForm" action="../MainServlet" method="post">
    <h3>Enter patient details for appointment: </h3>
	<div class="appDetailsTitle">Therapy:&nbsp;&nbsp;</div>
	<div class="appDetails" id="appTherapy" ></div><br>
	<div class="appDetailsTitle">Therapist:&nbsp;&nbsp;</div>
	<div class="appDetails" id="appTherapist" ></div><br>
	<div class="appDetailsTitle">Date:&nbsp;&nbsp;</div>
	<div class="appDetails" id="appDate" ></div><br>
	<div class="appDetailsTitle">Time:&nbsp;&nbsp;</div>
	<div class="appDetails" id="appTime" ></div><br><br>

	<input type="hidden" name="appTherapyIdIn" id="appTherapyIdIn" required>
 	<input type="hidden" name="appTherapistIn" id="appTherapistIn" required> 
    <input type="hidden" name="appTimeIn" id="appTimeIn" required>
 	
    <label for="fname">First Name:&nbsp;&nbsp;</label>
    <input type="text" name="fname" id="fname" maxlength="45" required><span style="color: red; display: inline-block; width: 13px;"> *</span><br>

    <label for="lname">Last Name:&nbsp;&nbsp;</label>
    <input type="text" name="lname" id="lname" maxlength="45"><span style="display: inline-block; width: 13px;">&nbsp;</span><br>
    
    <label for="pNumber">Phone #:&nbsp;&nbsp;</label>
    <input type="text" name="pNumber" id="pNumber" maxlength="25"><span style="display: inline-block; width: 13px;">&nbsp;</span><br>
    
    <label for="email">Email:&nbsp;&nbsp;</label>
    <input type="text" name="email" id="email" maxlength="255"><span style="display: inline-block; width: 13px;">&nbsp;</span><br>
    
    <label for="notes">Notes:&nbsp;&nbsp;</label>
    <textarea name="notes" id="notes" maxlength="255"></textarea><span style="display: inline-block; width: 13px;">&nbsp;</span><br>

    <button type="button" class="btn" id="bookAppButton" onclick="openConfirmationForm('book')">Book</button>
    <button type="button" class="btn cancel" onclick="closeForm()">Cancel</button>
  </form>
</div>

<!-- confirmation window -->
<div class="form-popup" id="confirmationForm">
  <div class="form-container">
	<div id="btnContainer">
	  	<form class="appForm">
	  		<!-- <input type="hidden" name="appTherapyIdIn" id="appTherapyIdIn" required>
		 	<input type="hidden" name="appTherapistIn" id="appTherapistIn" required> 
		    <input type="hidden" name="appTimeIn" id="appTimeIn" required>h3 class="appDetailsTitle" id="confirmMsg"></h3>
	    	<input type="text" name="fname" id="fname" maxlength="45" required><span style="color: red;"> *</span><br>
	    	<input type="text" name="lname" id="lname" maxlength="45"><span>&nbsp&nbsp&nbsp</span><br>
			<textarea name="notes" id="notes" maxlength="255"></textarea><span>&nbsp&nbsp&nbsp</span><br> -->
    		
			<h3 class="confTitle" id="confirmMsg"></h3>
    		<button type="button" class="btn" onclick="callMainServlet()">OK</button>
	    	<button type="button" class="btn cancel" onclick="closeForm()">Cancel</button>
	    </form>
  	</div>
  </div>
</div> 

<!-- <div class="form-popup" id="newConfirmationForm"> -->
<!--   <div class="form-container"> -->
<!-- 	<div id="appbtnContainer"> -->
<!-- 	  	<form class="confAppForm"> -->
<!-- 	  		<h1 class="confTitle" id="appId"></h1> -->
<!-- 			<h3 class="confTitle" id="confirmationMsg"></h3> -->
<!--     		<button type="button" class="btn" id="confirmBtn" onclick="callMainServlet()">OK</button> -->
<!-- 	    </form> -->
<!--   	</div> -->
<!--   </div> -->
<!-- </div>  -->

<div class="form-popup" id="diffConfirmationForm">
  <div class="form-container">
	<div id="appbtnContainer">
	  	<form class="DiffconfAppForm" action="../DB_Init_Servlet">
	  		<h1 class="confTitle" id="newAppId"></h1>
			<h3 class="confTitle" id="newConfirmationMsg"></h3>
			<input type="submit" class="btn" value="OK" >
<!-- 			<button type="button" class="btn" id="confirmBtn" onclick="goHome()">OK</button> -->
	    </form>
  	</div>
  </div>
</div> 
 <!-- end confirmation window -->
</body>
</html>