<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!-- verify user session before access -->
<div id=${session_type} name="userType"></div>
<%
	if(session.getAttribute("session_type")==null){
		response.sendRedirect("../index.jsp");
	}
%>

<%@ page import="java.util.ArrayList" %>
<%@ page import="com.model.Certification" %>
<%@ page import="com.model.Therapy" %>
<%@ page import="com.database.DB_Query" %>
<%
 	ArrayList<Certification> certifications = (ArrayList<Certification>) session.getAttribute("session_certifications");
	ArrayList<Therapy> therapies = (ArrayList<Therapy>) session.getAttribute("session_therapies");
	String invalidCertD = (String) session.getAttribute("session_certInvalid");
%>
<script>
var invalidDelete = <%=invalidCertD%>;
console.log(invalidDelete);
if(invalidDelete == false) {
	alert("ERROR, CERTIFICATION HAS EMPLOYEES!");
	<%session.removeAttribute("session_certInvalid");%>
}
</script>
<!DOCTYPE html>

<html>
 <head>
 <meta charset="ISO-8859-1">
     <title>Certifications</title>
     <link rel="stylesheet" href="../css/styles.css">
     <link href='https://fonts.googleapis.com/css?family=Open Sans' rel='stylesheet'>
     <script src="../js/jquery-3.6.0.js"></script>
     <script src="../js/certifications.js"></script>
 </head>
 <body>
 <div class="header">
 	<h1>Certifications</h1>
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
			<a class="active administrator" href="../jsp/certifications.jsp">Certifications<br></a>
			<a class="administrator" href="../jsp/therapists.jsp">Employees<br></a>
			<a href="../jsp/schedule.jsp">Schedule<br></a>
			<a href="../jsp/bookAppointment.jsp">Book Appointment<br></a>
			<a href="../jsp/appointmentSchedule.jsp">Appointments</a>
			<a class="administrator" href="../jsp/manageSchedule.jsp">Manage Hours</a>
			<a href="userProfile.jsp">User Profile</a>
		</div>
	</div>
	<div class="certMain">
	  <div class="updateCerts">
	 	<div class="certMenu">
			<a href="#"><button id="addCertButton" type="button">Add Certification</button></a>
	 		<a href="#"><button id="updateCertButton" type="button">Update Certification</button></a>
	 		<a href="#"><button id="removeCertButton" type="button">Remove Certification</button></a>
	 		<br><a href="#"><button id="printButton" type="button" onclick="window.print()">Print Page</button></a>
	 	</div>
			<div class="addCertification">
				<form action="../AddCertificationServlet" method="get">
					<label for="newCode">Code:&nbsp;&nbsp;</label>
					<input type="text" id="newCode" name="newCode" required><span style="color: red;"> *</span><br> 
					<label for="newCert">Certification:&nbsp;&nbsp;</label>
					<input type="text" id="newCert" name="newCert" required><span style="color: red;"> *</span><br> 
					<label for="newTherapy">Therapy:&nbsp;&nbsp;</label>
					<input type="text" id="newTherapy" name="newTherapy" required><span style="color: red;"> *</span><br> 
					<input id="addButton" type="submit" value="Add">
					<button id="cancelAddButton" type="button" onclick="cancel()">Cancel</button>
				</form>
			</div>
			
			<div class="removeCertification">
				<form action="../DeleteCertificationServlet" method="get">
					<label for="certCodesRemoveDropdown">Certification Code:&nbsp;&nbsp;</label> <select name="certCodesRemoveDropdown"
						id="certCodesRemoveDropdown" required>
						<%
						for (int idx = 0; idx < certifications.size(); idx++) {
						%>
						<%
						Certification cert = (Certification) certifications.get(idx);
						%>
						<%="<option value=" + cert.getCertification_name() + ">" + cert.getCertification_name() + "</option>"%>
						<%
						}
						%>
					</select><span style="color: red;"> *</span> <br> 
					<input class="btn" id="removeButton" type="submit" value="Remove">
					<button class="btn" id="cancelRemoveButton" type="button" onclick="cancel()">Cancel</button>
				</form>
			</div>
			
			<div class="updateCertification">
	         <form action="../UpdateCertificationServlet" method="get">
	            <!-- <label for="certCodesDropdown">Choose a Certification Code:</label>-->
	             <label for="certCodesDropdown">Code:&nbsp;&nbsp;</label>
	             <select name="certCodesDropdown" id="certCodesDropdown" required>
	                 <% for (int idx = 0; idx < certifications.size(); idx++ ){%>
						<% 		Certification cert = (Certification)certifications.get(idx);%>
						<%=  "<option value=" +cert.getCertification_name()+ ">" + cert.getCertification_name() + "</option>"%>
						<%} %>
	             </select><span style="color: red;"> *</span> <br>
	             <label for="certUpdate">Certification:&nbsp;&nbsp;</label><input type="text" id="certUpdate" name="certUpdate" required><span style="color: red;"> *</span><br>
	             <label for="therapyUpdate">Therapy:&nbsp;&nbsp;</label><input type="text" id="therapyUpdate" name="therapyUpdate" required><span style="color: red;"> *</span><br>
	             <input id="updateButton" type="submit" value="Update">
	             <button id="cancelUpdateButton" type="button" onclick="cancel()">Cancel</button>
	         </form>
	      </div> 
	    </div>
	 	<div class="certList">
	         <table>
	             <tr>
	                 <th>Code</th>
	                 <th>Certification</th>
	                 <th>Therapy</th>
	                 <th>Therapist Count </th>
	             </tr>	
	                <% for (int idx = 0; idx < certifications.size(); idx++ ){%>
						<% 		Certification cert = (Certification)certifications.get(idx);%>
						<% 		Therapy therapy = (Therapy)therapies.get(idx);%>
						<%		String count = new DB_Query().getEmpCertCount(cert.getCertification_id());%>
						<%= "<tr>" %>
						<%= 	"<td>"+ cert.getCertification_name()+"</td>" %>
						<%= 	"<td>"+ therapy.getTherapy_name()+"</td>" %>
						<%= 	"<td>"+ therapy.getTherapy_desc()+"</td>" %>
						<%=		"<td>"+ count + "</td>"%>
						<%= "</tr>" %>
					<%}%>	
	         </table>
	     </div>
	 </div>
</div>
 <script>
		function cancel() {
			$(".addCertification").remove();
			$(".updateCertification").remove();
			$(".removeCertification").remove();
		}
	</script>
 </body>
 </html> 