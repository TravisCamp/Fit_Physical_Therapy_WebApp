<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!-- verify user session before access -->
<div id=${session_type} name="userType"></div>
<%
	if(session.getAttribute("session_User")==null){
		response.sendRedirect("../index.jsp");
	}else{
		Employee user = (Employee)session.getAttribute("session_User");
	}
%>

<%@ page import="java.util.ArrayList" %>
<%@ page import="com.model.Certification" %>
<%@ page import="com.model.Therapy" %>
<%@ page import="com.model.Employee" %>
<%
 	ArrayList<Certification> certifications = (ArrayList<Certification>) session.getAttribute("session_certifications");
	ArrayList<Therapy> therapies = (ArrayList<Therapy>) session.getAttribute("session_therapies");
	ArrayList<Certification> EmpCertifications = (ArrayList<Certification>) session.getAttribute("session_empCertifications");
	String empName =(String) session.getAttribute("session_employee");
	String empManage = (String) session.getAttribute("session_manage");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Manage Therapist Certifications</title>
     <link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
     <link href='https://fonts.googleapis.com/css?family=Open Sans' rel='stylesheet'>
     <script src="<%=request.getContextPath()%>/js/jquery-3.6.0.js"></script>
          <script src="<%=request.getContextPath()%>/js/therapistsCert.js"></script>
</head>
<body>
<div class="header">
 	<%= "<h1>" + "Manage Certifications for " + empName + "</h1>" %>
 	<div>
 		<a href="<%=request.getContextPath()%>/jsp/home.jsp"><img src="<%=request.getContextPath()%>/img/home.png"></a>
		<a href="<%=request.getContextPath()%>/LogoutServlet"><img src="<%=request.getContextPath()%>/img/logout.png"></a>
 	</div>
 </div>
 <div class="main">
 	<div class="sidebar">
		<img src="<%=request.getContextPath()%>/img/logo-round.jpg">
		<div class="sideMenu">
			<a href="<%=request.getContextPath()%>/jsp/home.jsp">Home<br></a>
			<a class="administrator" href="<%=request.getContextPath()%>/jsp/certifications.jsp">Certifications<br></a>
			<a class="administrator active" href="<%=request.getContextPath()%>/jsp/therapists.jsp">Employees<br></a>
			<a href="<%=request.getContextPath()%>/jsp/schedule.jsp">Schedule<br></a>
			<a href="<%=request.getContextPath()%>/jsp/bookAppointment.jsp">Book Appointment<br></a>
			<a href="<%=request.getContextPath()%>/jsp/appointmentSchedule.jsp">Appointments</a>
			<a class="administrator" href="<%=request.getContextPath()%>/jsp/manageSchedule.jsp">Manage Hours</a>
			<a href="<%=request.getContextPath()%>/jsp/userProfile.jsp">User Profile</a>
		</div>
	</div>
	 <div class="empMain">
	 	<div class="empList">
	 	<%= "<h2>" + empName + "'s Certifictions" + "</h2>"%>
	         <table>
	             <tr>
	                 <th>Code</th>
	                 <th>Certification</th>
	             </tr>	
	                <% for (int idx = 0; idx < EmpCertifications.size(); idx++ ){%>
						<% 		Certification cert = (Certification)EmpCertifications.get(idx);%>
						<% 		Therapy therapy = (Therapy)therapies.get(idx);%>
						<%= "<tr>" %>
						<%= 	"<td>"+ cert.getCertification_id()+"</td>" %>
						<%= 	"<td>"+ cert.getCertification_name()+"</td>" %>
						<%= "</tr>" %>
					<%}%>	
	         </table>
	     </div>
	     
	     <div class="empMenu">
	 		<a href="#"><button id="addEmpButton" type="button">Add Certification</button></a>
	 		<a href="#"><button id="removeEmpButton" type="button">Remove Certification</button></a>
	 		<br><a href="#"><button id="printButton" type="button" onclick="window.print()">Print Page</button></a>
	 	</div>
	 	
	 	<div class="addEmployee">
				<form action="<%=request.getContextPath()%>/AddEmpCertServlet" method="get">
					<label for="addCertDropDown">Certification:&nbsp;&nbsp;</label> 
					<select name="addCertDropDown" id="addCertDropDown">
						<%
						for (int idx = 0; idx < certifications.size(); idx++) {
						%>
						<%
						Certification cert = (Certification) certifications.get(idx);
						%>
						<%="<option value=" + cert.getCertification_id() +">" + cert.getCertification_name() + "</option>"%>
						<%
						}
						%>
					</select> <br> 
					<div class="notSeen">
					<label for="newFName">Employee Name: </label><input type="text" id="newFName" value="<%= empName %>" name="newFName" readonly><br>
					
					<input type="text" id="empManage" value="<%= empManage %>" name="empManage" readonly>
					</div>
					<input id="addButton" type="submit" value="Add">
					<button id="cancelAddButton" type="button" onclick="cancel()">Cancel</button>
				</form>
			</div>
	 	
	 	
	 	
	 	<div class="removeEmployee">
				<form action="<%=request.getContextPath()%>/DeleteEmpCertServlet" method="get">
					<label for="empRemoveDropdown">Certification:&nbsp;&nbsp;</label> 
					<select name="empRemoveDropdown" id="empRemoveDropdown">
						<%
						for (int idx = 0; idx < EmpCertifications.size(); idx++) {
						%>
						<%
						Certification cert = (Certification) EmpCertifications.get(idx);
						%>
						<%="<option value=" + cert.getCertification_id() +">" + cert.getCertification_name() + "</option>"%>
						<%
						}
						%>
					</select> <br> 
					<div class="notSeen">
					<label for="newFName">Employee Name: </label><input type="text" id="newFName" value="<%= empName %>" name="newFName" readonly><br>
					
					<input type="text" id="empManage" value="<%= empManage %>" name="empManage" readonly>
					</div>
					<input id="removeButton" type="submit" value="Remove">
					<button id="cancelRemoveButton" type="button" onclick="cancel()">Cancel</button>
				</form>
			</div>
	 	
	    </div>
	   </div>
      <script>
		function cancel() {
			$(".addEmployee").remove();
			$(".removeEmployee").remove();
		}
	</script>
</body>
</html>