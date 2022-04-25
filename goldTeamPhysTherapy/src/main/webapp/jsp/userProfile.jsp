<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<%@ page import="com.model.Employee" %>

<!-- verify user session before access -->
<div id=${session_type} name="userType"></div>
<%
	if(session.getAttribute("session_User")==null){
		response.sendRedirect("../index.jsp");
	}
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>User Profile</title>
	<link rel="stylesheet" href="../css/styles.css">   
	<link href='https://fonts.googleapis.com/css?family=Open Sans' rel='stylesheet'>
	<script src="../js/jquery-3.6.0.js"></script>
	<script src="../js/userProfile.js"></script>
</head>
<body>
	<div class="overlay"></div>
	<div class="header">
		<h1>User Profile</h1>
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
				<a href="../jsp/schedule.jsp">Schedule<br></a>
				<a href="../jsp/bookAppointment.jsp">Book Appointment<br></a>
				<a href="appointmentSchedule.jsp">Appointments</a>
				<a class="administrator" href="../jsp/manageSchedule.jsp">Manage Hours</a>
				<a class="active" href="userProfile.jsp">User Profile</a>
			</div>
		</div>
		<div id="selection">
			<div id="image">
				<img src="../img/logo.jpg">
			</div>
			<div>
				<form id="createAcctForm" >
					<% Employee emp = (Employee) session.getAttribute("session_User");  %>
					<label for="fname">First Name:&nbsp;&nbsp;</label>
					<%="<input id='fname' type='text' name='fname' maxlength='45' value="+emp.getEmployee_fname()+">"%>
					<br> 
					
					<label for="lname">Last Name:&nbsp;&nbsp;</label>
					<%if(emp.getEmployee_lname() != null) {%>
						<%="<input id='lname' type='text' name='lname' maxlength='45' value="+emp.getEmployee_lname()+">"%>
					<% }else {%>
						<%="<input id='lname' type='text' name='lname' maxlength='45'>"%>	
					<%} %>
					<br> 
					
					<label for="uname">User Name:&nbsp;&nbsp;</label>
					<%="<input id='uname'readonly type='text' name='uname' maxlength='45' value="+emp.getEmployee_uname()+">"%>
					<br>
					
					<label for="password">Password:&nbsp;&nbsp;</label>
					<%="<input id='password' type='password' name='password' maxlength='45' value="+emp.getEmployee_password()+">"%>
					
					<button id="showHideBtn" id="updateButton" onclick="showPass()">show</button>
					<br> 
					
					<button type="button" class="btn" id="updateButton" onclick="openConfirmationForm()">Update Info</button>
				</form>
			</div>
		</div>
	</div>
	
	<!-- confirmation window -->
	<div class="form-popup" id="confirmationForm">
	  <div class="form-container">
		<div id="btnContainer">
		  	<form id=updateConfirmationForm class="appForm">
		  		<input type="hidden" name="appIdIn1" id="appIdIn1" required>
				<h3 class="confTitle" id="confirmMsg"></h3>
		    	<button type="button" class="btn" onclick="callUpdateServlet()">Ok</button>
		    	<button type="button" class="btn cancel" onclick="closeForm()">Cancel</button>
		    </form>
	  	</div>
	  </div>
	</div> 
<!-- end confirmation window -->

</body>
</html>