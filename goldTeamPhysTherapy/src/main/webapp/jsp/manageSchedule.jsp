<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.model.Employee" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>

<!-- verify user session before access -->
<div id=${session_type} name="userType"></div>
<%
	if(session.getAttribute("session_User")==null){
		response.sendRedirect("../index.jsp");
	}
%>

<%
	ArrayList <Employee> employees = (ArrayList <Employee>) session.getAttribute("session_employees");
	Employee selectedTherapist = (Employee) session.getAttribute("session_selectedTherapistManage");
	String selectedWeekStrManage = (String) session.getAttribute("session_selectedWeekStrManage");
	String selectedWeekManage = (String) session.getAttribute("session_selectedWeekManage");
	String invalidUpdate = (String) session.getAttribute("session_invalidUpdate");
	String therapistStr = "";
	//set flag to refresh the page contents following a change
	Boolean refreshPage = (Boolean) session.getAttribute("session_refreshPage");
	
	if (selectedTherapist != null){
		therapistStr = selectedTherapist.getEmployee_fname();
	}	
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Schedules</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link rel="stylesheet" href="../css/jquery-ui.min.css">
    <link href='https://fonts.googleapis.com/css?family=Open Sans' rel='stylesheet'>
    <script src="../js/jquery-3.6.0.js"></script>
    <script src="../js/jquery-ui.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-csv/1.0.21/jquery.csv.min.js"></script>
    <script src="../js/manageSchedule.js"></script>
    <script>
		let therapist = '<%=therapistStr%>';
		let workWeekManage = '<%=selectedWeekManage%>';
		let scheduleWeek = '<%=selectedWeekStrManage%>';
		let invalidUpdate;

		<% if(invalidUpdate == "true"){ %>
			invalidUpdate = '<%=invalidUpdate%>';
		<%}%>
		
		<%if(refreshPage!=null){%>
			<%= "let refreshPage = '" + refreshPage + "';" %>
			<% if(refreshPage == true){%>
				<%	session.setAttribute("session_refreshPage",false);%>
			<%}%>
		<%}%>
	</script>
</head>
<body>
<div id=${session_type} name="userType"></div>
<div class="overlay"></div>
<div class="header">
 	<h1>Schedule</h1>
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
			<a href="../jsp/appointmentSchedule.jsp">Appointments</a>
			<a class="administrator active" href="../jsp/manageSchedule.jsp">Manage Hours</a>
			<a href="userProfile.jsp">User Profile</a>
		</div>
	</div>
	<div class="scheduleMain">
		<div class="searchSchedule">
	        <h3>Please choose a therapist and week to manage schedule: </h3>
	        <form id="searchScheduleForm"><!-- 
	        <form id="searchScheduleForm" action="../UpdateEmpScheduleServlet" method="post"> -->
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
	            <input type="text" id="weekpicker" name="weekpicker" readonly="readonly" value=${session_selectedWeekManage}>
	           
	            <input id="manageScheduleButton" onclick="callUpdateEmpScheduleServlet()" value="Manage Schedule">
	            <!-- <input id="manageScheduleButton" type="submit" value="Manage Schedule"> -->
	        </form>
	   	</div>
	   	<div id="scheduleHeader"></div>
	   	<form id="manageScheduleForm">
			<div class="manageScheduleDays">
				<div id="mondaySchedule" class="scheduleBox">
					<h3>Monday</h3>
					<p id="currentShiftMonday"></p>
					<div id="mondayTimes">
						<label for="mondayStart" class="time">Start Time:</label>
						<select class="startTimes" id="mondayStart" name="mondayStart">
							<option value="8">8:00</option>
							<option value="9">9:00</option>
							<option value="10">10:00</option>
							<option value="11">11:00</option>
							<option value="12">12:00</option>
							<option value="13">1:00</option>
							<option value="14">2:00</option>
							<option value="15">3:00</option>
							<option value="16">4:00</option>
						</select>
						<br>
						<label for="mondayEnd" class="time">End Time:</label>
						<select class="endTimes" id="mondayEnd" name="mondayEnd">
							<option value="9">9:00</option>
							<option value="10">10:00</option>
							<option value="11">11:00</option>
							<option value="12">12:00</option>
							<option value="13">1:00</option>
							<option value="14">2:00</option>
							<option value="15">3:00</option>
							<option value="16">4:00</option>
							<option value="17">5:00</option>
						</select>
					</div>
					<input type="checkbox" name="mondayOff" id="mondayOff" value="true">
					<label for="mondayOff"> Off</label>				
				</div>
				<div id="tuesdaySchedule" class="scheduleBox">
					<h3>Tuesday</h3>
					<p id="currentShiftTuesday"></p>
					<div id="tuesdayTimes">
						<label for="tuesdayStart" class="time">Start Time:</label>
						<select class="startTimes" id="tuesdayStart" name="tuesdayStart">
							<option value="8">8:00</option>
							<option value="9">9:00</option>
							<option value="10">10:00</option>
							<option value="11">11:00</option>
							<option value="12">12:00</option>
							<option value="13">1:00</option>
							<option value="14">2:00</option>
							<option value="15">3:00</option>
							<option value="16">4:00</option>
						</select>
						<br>
						<label for="tuesdayEnd" class="time">End Time:</label>
						<select class="endTimes" id="tuesdayEnd" name="tuesdayEnd">
							<option value="9">9:00</option>
							<option value="10">10:00</option>
							<option value="11">11:00</option>
							<option value="12">12:00</option>
							<option value="13">1:00</option>
							<option value="14">2:00</option>
							<option value="15">3:00</option>
							<option value="16">4:00</option>
							<option value="17">5:00</option>
						</select>
					</div>
					<input type="checkbox" name="tuesdayOff" id="tuesdayOff" value="true">
					<label for="tuesdayOff"> Off</label>	
				</div>
				<div id="wednesdaySchedule" class="scheduleBox">
					<h3>Wednesday</h3>
					<p id="currentShiftWednesday"></p>
					<div id="wednesdayTimes">
						<label for="wednesdayStart" class="time">Start Time:</label>
						<select class="startTimes" id="wednesdayStart" name="wednesdayStart">
							<option value="8">8:00</option>
							<option value="9">9:00</option>
							<option value="10">10:00</option>
							<option value="11">11:00</option>
							<option value="12">12:00</option>
							<option value="13">1:00</option>
							<option value="14">2:00</option>
							<option value="15">3:00</option>
							<option value="16">4:00</option>
						</select>
						<br>
						<label for="wednesdayEnd" class="time">End Time:</label>
						<select class="endTimes" id="wednesdayEnd" name="wednesdayEnd">
							<option value="9">9:00</option>
							<option value="10">10:00</option>
							<option value="11">11:00</option>
							<option value="12">12:00</option>
							<option value="13">1:00</option>
							<option value="14">2:00</option>
							<option value="15">3:00</option>
							<option value="16">4:00</option>
							<option value="17">5:00</option>
						</select>
					</div>
					<input type="checkbox" name="wednesdayOff" id="wednesdayOff" value="true">
					<label for="wednesdayOff"> Off</label>	
				</div>
			</div>
			<br>
			<div class="manageScheduleDays">
				<div id="thursdaySchedule" class="scheduleBox">
					<h3>Thursday</h3>
					<p id="currentShiftThursday"></p>
					<div id="thursdayTimes">
						<label for="thursdayStart" class="time">Start Time:</label>
						<select class="startTimes" id="thursdayStart" name="thursdayStart">
							<option value="8">8:00</option>
							<option value="9">9:00</option>
							<option value="10">10:00</option>
							<option value="11">11:00</option>
							<option value="12">12:00</option>
							<option value="13">1:00</option>
							<option value="14">2:00</option>
							<option value="15">3:00</option>
							<option value="16">4:00</option>
						</select>
						<br>
						<label for="thursdayEnd" class="time">End Time:</label>
						<select class="endTimes" id="thursdayEnd" name="thursdayEnd">
							<option value="9">9:00</option>
							<option value="10">10:00</option>
							<option value="11">11:00</option>
							<option value="12">12:00</option>
							<option value="13">1:00</option>
							<option value="14">2:00</option>
							<option value="15">3:00</option>
							<option value="16">4:00</option>
							<option value="17">5:00</option>
						</select>
					</div>
					<input type="checkbox" name="thursdayOff" id="thursdayOff" value="true">
					<label for="thursdayOff"> Off</label>	
				</div>
				<div id="fridaySchedule" class="scheduleBox">
					<h3>Friday</h3>
					<p id="currentShiftFriday"></p>
					<div id="fridayTimes">
						<label for="fridayStart" class="time">Start Time:</label>
						<select class="startTimes" id="fridayStart" name="fridayStart">
							<option value="8">8:00</option>
							<option value="9">9:00</option>
							<option value="10">10:00</option>
							<option value="11">11:00</option>
							<option value="12">12:00</option>
							<option value="13">1:00</option>
							<option value="14">2:00</option>
							<option value="15">3:00</option>
							<option value="16">4:00</option>
						</select>
						<br>
						<label for="fridayEnd" class="time">End Time:</label>
						<select class="endTimes" id="fridayEnd" name="fridayEnd">
							<option value="9">9:00</option>
							<option value="10">10:00</option>
							<option value="11">11:00</option>
							<option value="12">12:00</option>
							<option value="13">1:00</option>
							<option value="14">2:00</option>
							<option value="15">3:00</option>
							<option value="16">4:00</option>
							<option value="17">5:00</option>
						</select>
					</div>
					<input type="checkbox" name="fridayOff" id="fridayOff" value="true">
					<label for="fridayOff"> Off</label>	
				</div>
				<div id="saturdaySchedule" class="scheduleBox">
					<h3>Saturday</h3>
					<p id="currentShiftSaturday"></p>
					<div id="saturdayTimes">
						<label for="saturdayStart" class="time">Start Time:</label>
						<select class="startTimes" id="saturdayStart" name="saturdayStart">
							<option value="8">8:00</option>
							<option value="9">9:00</option>
							<option value="10">10:00</option>
							<option value="11">11:00</option>
							<option value="12">12:00</option>
							<option value="13">1:00</option>
							<option value="14">2:00</option>
							<option value="15">3:00</option>
							<option value="16">4:00</option>
						</select>
						<br>
						<label for="saturdayEnd" class="time">End Time:</label>
						<select class="endTimes" id="saturdayEnd" name="saturdayEnd">
							<option value="9">9:00</option>
							<option value="10">10:00</option>
							<option value="11">11:00</option>
							<option value="12">12:00</option>
							<option value="13">1:00</option>
							<option value="14">2:00</option>
							<option value="15">3:00</option>
							<option value="16">4:00</option>
							<option value="17">5:00</option>
						</select>
					</div>
					<input type="checkbox" name="saturdayOff" id="saturdayOff" value="true">
					<label for="saturdayOff"> Off</label>	
				</div>
			</div>
			<br>
			<input type="checkbox" name="updateWorkWeeks" id="updateWorkWeeks" value="true">
			<label for="updateWorkWeeks"></label>
			<br>
			<div id="btn-container">
				<input id="submitChangesBtn" type="button" onclick="openConfirmationForm()" value="Submit Changes">
				<input id="cancelChangesBtn" type="button" value="Cancel" onClick="window.location.reload();">
			</div>
			
			<!-- confirmation window -->
			<div class="form-popup" id="confirmationForm">
			  <div class="form-container">
				<div id="btnContainer">
					<h3 class="confTitle" id="confirmMsg"></h3>
				    <button type="button" class="btn" onclick="callUpdateEmpScheduleServletGet()">Ok</button>
				    <button type="button" class="btn cancel" onclick="closeForm()">Cancel</button>
			  	</div>
			  </div>
			</div> 
			<!-- end confirmation window -->
		</form>
	</div>
</div>
<!-- error alert window -->
<div class="form-popup" id="errorMessage">
  <div class="form-container">
  	<form action="../DB_Init_Servlet">
		<h3 class="confTitle" id="errorMsg"></h3>
		<button type="submit" class="btn">Ok</button>
	</form>
  </div>
</div> 
<!-- end error alert window -->
</body>
</html>