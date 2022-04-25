<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.Employee" %>

<!-- verify user session before access -->
<div id=${session_type} name="userType"></div>
<%
	if(session.getAttribute("session_User")==null){
		response.sendRedirect("../index.jsp");
	}
	session.removeAttribute("session_selectedWeekStr");
	session.removeAttribute("session_selectedWeek");
	session.removeAttribute("session_selectedTherapist");
	session.removeAttribute("session_selectedTherapy");
	session.removeAttribute("session_selectedAppDateStr");
	session.removeAttribute("session_selectedDayStr");
	session.removeAttribute("session_selectedDay");
	session.removeAttribute("session_confirmValue");
	session.removeAttribute("session_certInvalid");
	session.removeAttribute("session_invalid");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Fit Physical Therapy</title>
<link rel="stylesheet" href="../css/styles.css">
<script src="../js/jquery-3.6.0.js"></script>
<script src="../js/homePage.js"></script>
<link href='https://fonts.googleapis.com/css?family=Open Sans' rel='stylesheet'>
</head>
<body>
<div class="overlay"></div>
<div class="header">
	<% Employee emp = (Employee) session.getAttribute("session_User");  %>
	<!-- check user's password is not default -->
	<%="<script> displayWarningMsg="+(emp.getEmployee_password().equals("P@ssword"))+"; </script>" %>
	
	<%="<h1 id='whoAmI'>Welcome, "+emp.getEmployee_fname()+"</h1>"%>
	<div>
		<a href="../jsp/home.jsp"><img src="../img/home.png"></a>
		<a href="../LogoutServlet"><img src="../img/logout.png"></a>
	</div>
</div>
<div class="main">
	<div class="sidebar">
		<img src="../img/logo-round.jpg">
		<div class="sideMenu">
			<a class="active" href="../jsp/home.jsp">Home<br></a>
			<a class="administrator" href="../jsp/certifications.jsp">Certifications<br></a>
			<a class="administrator" href="../jsp/therapists.jsp">Employees<br></a>
			<a href="../jsp/schedule.jsp">Schedule<br></a>
			<a href="../jsp/bookAppointment.jsp">Book Appointment<br></a>
			<a href="appointmentSchedule.jsp">Appointments</a>
			<a class="administrator" href="../jsp/manageSchedule.jsp">Manage Hours</a>
			<a href="userProfile.jsp">User Profile</a>
		</div>
	</div>
	<div class="menu">
		<div class="cards">
			<a class="card administrator" href="certifications.jsp">
				<div class="card">
					<img class="cardIcon" src="../img/certificate.png">
					<p><strong>Certifications</strong></p>
				</div>
			</a>
			<a class="card administrator" href="therapists.jsp">
				<div class="card">
					<img class="cardIcon" src="../img/teamwork.png">
					<p><strong>Employees</strong></p>
				</div>
			</a>				
			<a class="card" href="schedule.jsp">
				<div class="card">
					<img class="cardIcon" src="../img/schedule.png">
					<p><strong>Schedule</strong></p>
				</div>
			</a>				
			<a class="card" href="bookAppointment.jsp">
				<div class="card">
					<img class="cardIcon" src="../img/notebook.png">
					<p><strong>Book<br>Appointment</strong></p>
				</div>
			</a>				
			<a class="card" href="appointmentSchedule.jsp">
				<div class="card">
					<img class="cardIcon" src="../img/stopwatch.png">
					<p><strong>Appointments</strong></p>
				</div>
			</a>
			<a class="card administrator" href="manageSchedule.jsp">
				<div class="card">
					<img class="cardIcon" src="../img/clocks.png">
					<p><strong>Manage<br>Hours</strong></p>
				</div>
			</a>
			<a class="card" href="userProfile.jsp">
				<div class="card">
					<img class="cardIcon" src="../img/manager.png">
					<p><strong>User<br>Profile</strong></p>
				</div>
			</a>			
		</div>
		<div class="therapist">

		</div>
		<div class="scheduler">

		</div>
	</div>
</div>

<!-- confirmation window -->
<div class="form-popup" id="confirmationForm">
  <div class="form-container">
	<div id="btnContainer">
		<h3 class="appDetailsTitle" id="confirmMsg"></h3>
    	<!-- <button type="button" class="btn" onclick="closeForm()">Ok</button> -->
    	<button type="button" class="btn cancel" onclick="closeForm()">Ok</button>
  	</div>
  </div>
</div> 
<!-- end confirmation window -->

</body>
</html>