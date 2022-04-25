<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- verify user session before access -->
<div id=${session_type} name="userType"></div>
<%
	if(session.getAttribute("session_User")==null){
		response.sendRedirect("../index.jsp");
	}
%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="com.model.Certification"%>
<%@ page import="com.model.Therapy"%>
<%@ page import="com.model.Employee"%>
<%
ArrayList<Certification> certifications = (ArrayList<Certification>) session.getAttribute("session_certifications");
ArrayList<Therapy> therapies = (ArrayList<Therapy>) session.getAttribute("session_therapies");
ArrayList<Employee> employees = (ArrayList<Employee>) session.getAttribute("session_employees");
String invalidDel = (String) session.getAttribute("session_invalid");
%>

<script>
var invalidDelete = <%=invalidDel%>;
if(invalidDelete == true) {
	alert("ERROR, EMPLOYEE HAS APPOINTMENTS!");
	<%session.removeAttribute("session_invalid");%>
}
let emps = [
	<% for(int i=0; i<employees.size(); i++) {
		String empfName  = " ";
		String emplName = " ";
		String empuName = " ";
		String empPassword = " ";
		int empType = 0;
		int arrayId = 0;
		int empId = 0;
		empId = employees.get(i).getEmployee_Id();
		arrayId = i + 1;
		empType = employees.get(i).getEmployee_userTyp();
		empfName = employees.get(i).getEmployee_fname();
		emplName = employees.get(i).getEmployee_lname();
		empuName = employees.get(i).getEmployee_uname();
		empPassword = employees.get(i).getEmployee_password();
	%>
	{
		eId: <%=empId%>,
		id: <%=arrayId%>,
		eType: <%=empType%>,
		fName: '<%=empfName%>',
		lName: '<%=emplName%>',
		uName: '<%=empuName%>',
		password: '<%=empPassword%>'
	},
	<%}%>
];
</script>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employees</title>
<link rel="stylesheet" href="../css/styles.css">
<link href='https://fonts.googleapis.com/css?family=Open Sans'
	rel='stylesheet'>
<script src="../js/jquery-3.6.0.js"></script>
<script src="../js/therapists.js"></script>
</head>
<body>
	<div class="header">
		<h1>Employees</h1>
		<div>
			<a href="../jsp/home.jsp"><img src="../img/home.png"></a> <a
				href="../LogoutServlet"><img src="../img/logout.png"></a>
		</div>
	</div>
	<div class="main">
		<div class="sidebar">
			<img src="../img/logo-round.jpg">
			<div class="sideMenu">
				<a href="../jsp/home.jsp">Home<br></a> 
				<a class="administrator" href="../jsp/certifications.jsp">Certifications<br></a> 
				<a class="active administrator" href="../jsp/therapists.jsp">Employees<br></a> 
				<a href="../jsp/schedule.jsp">Schedule<br></a> 
				<a href="../jsp/bookAppointment.jsp">Book Appointment<br></a> 
				<a href="../jsp/appointmentSchedule.jsp">Appointments</a>
				<a class="administrator" href="../jsp/manageSchedule.jsp">Manage Hours</a>
				<a href="userProfile.jsp">User Profile</a>
			</div>
		</div>
		<div class="empMain">
			<div class="updateEmployeeList">
				<div class="empMenu">
					<a href="#"><button id="addEmpButton" type="button">Add
							Employee</button></a> <a href="#"><button id="updateEmpButton"
							type="button">Update Employee</button></a> <a href="#"><button
							id="removeEmpButton" type="button">Remove Employee</button></a> <a
						href="#"><button id="manageEmpButton" type="button">Manage
							Employee Certifications</button></a>
							<br><a href="#"><button id="printButton" type="button" onclick="window.print()">Print Page</button></a>
							<br><label for="hide">Hide Passwords</label>
							<input id="hide" type="checkbox" checked/>
				</div>

				<div class="addEmployee">
					<form action="../AddEmployeeServlet" onsubmit="return addValidateForm()" method="get">
						<label for="newFName">First Name:&nbsp;&nbsp;</label> 
						<input type="text" id="newFName" name="newFName" required>
						<span style="color: red; display: inline-block; width: 13px;"> *</span><br> 
						
						<label for="newLName">Last Name:&nbsp;&nbsp;</label>
						<input type="text" id="newLName" name="newLName">
						<span style="display: inline-block; width: 13px;">&nbsp;</span><br>
						
						<label for="empTypeDropDown">Type:&nbsp;&nbsp;</label>
						<select	name="empTypeDropdown" id="empTypeDropdown" required>
							<option value=1>Admin</option>
							<option value=2>Therapist</option>
							<option value=3>Registrar</option>
						</select>
						<span style="color: red; display: inline-block; width: 13px;"> *</span><br>
						 
						<!-- <label for="newUserName">User Name:&nbsp;&nbsp;</label> -->
						<input type="hidden" id="newUserName" name="newUserName">
						<span style="display: inline-block; width: 13px;">&nbsp;</span><br>
						
						<!-- <label for="newPassword">Password:&nbsp;&nbsp;</label> -->
						<input type="hidden" id="newPassword" name="newPassword">
						<span style="display: inline-block; width: 13px;">&nbsp;</span><br>
						
						<input id="addButton" type="submit" value="Add">
						<button id="cancelAddButton" type="button" onclick="cancel()">Cancel</button>
					</form>
				</div>

				<div class="removeEmployee">
					<form action="../DeleteEmpServlet"  method="get">
						<label for="empRemoveDropdown">Employee:&nbsp;&nbsp;</label> <select
							name="empRemoveDropdown" id="empRemoveDropdown">
							<%
							for (int idx = 0; idx < employees.size(); idx++) {
							%>
							<%
							Employee emp = (Employee) employees.get(idx);
							%>
							<% String lName = " ";%>
							<% if(emp.getEmployee_lname() != null) {
								lName = emp.getEmployee_lname();
							}
							%>
							<%="<option value=" + emp.getEmployee_Id() + ">" + emp.getEmployee_fname() + " " + lName
		+ "</option>"%>
							<%
							}
							%>
						</select> <br> <input id="removeButton" type="submit" value="Remove">
						<button id="cancelRemoveButton" type="button" onclick="cancel()">Cancel</button>
					</form>

				</div>

				<div class="updateEmployee">
					<form action="../UpdateEmployeeServlet" onsubmit="return validateForm()" method="get">
						<label for="empDropdown">Employee:&nbsp;&nbsp;</label> <select
							onchange="changeEmp();" name="empDropdown" id="empDropdown">
							<option value=""></option>
							<%
							for (int idx = 0; idx < employees.size(); idx++) {
							%>
							<%
							Employee emp = (Employee) employees.get(idx);
							%>
							<% String lName = " ";%>
							<% if(emp.getEmployee_lname() != null) {
								lName = emp.getEmployee_lname();
							}
							%>
							<%="<option value=" + emp.getEmployee_Id() + ">" + emp.getEmployee_fname() + " " + lName
		+ "</option>"%>
							<%
							}
							%>
						</select>
						<span style="color: red; display: inline-block; width: 13px;"> *</span> <br> 
						
						<label for="fNameUpdate">First Name:&nbsp;&nbsp;</label>
						<input type="text" id="fNameUpdate" name="fNameUpdate" required>
						<span style="color: red; display: inline-block; width: 13px;"> *</span><br> 
						
						<label for="lNameUpdate">Last Name:&nbsp;&nbsp;</label>
						<input type="text" id="lNameUpdate" name="lNameUpdate">
						<span style="color: red; display: inline-block; width: 13px;">&nbsp;&nbsp;</span><br>
						
						<label for="empTypeDropdownU">Type:&nbsp;&nbsp;</label> 
						<select	name="empTypeDropdownU" id="empTypeDropdownU" required>
							<option value=1>Admin</option>
							<option value=2>Therapist</option>
							<option value=3>Registrar</option>
						</select>
						<span style="color: red; display: inline-block; width: 13px;"> *</span> <br>
						
						<label for="updateUserName">UserName:&nbsp;&nbsp;</label> 
						<input type="text" id="updateUserName" readonly name="updateUserName">
						<span style="color: red; display: inline-block; width: 13px;">&nbsp;&nbsp;</span><br>
						
						<label for="updatePassword">Password:&nbsp;&nbsp;</label> 
						<input type="text" id="updatePassword" name="updatePassword">
						<span style="color: red; display: inline-block; width: 13px;">&nbsp;&nbsp;</span><br>
						
						<input
							id="updateButton" type="submit" value="Update">
						<button id="cancelUpdateButton" type="button" onclick="cancel()">Cancel</button>
					</form>
				</div>

				<div class="manageEmpCert">
					<form action="../LoadEmpCertificationsServlet" method="get">
						<label for="empManage">Employee:&nbsp;&nbsp;</label> <select
							name="empManage" id="empManage">
							<%
							for (int idx = 0; idx < employees.size(); idx++) {
							%>
							<%
							Employee emp = (Employee) employees.get(idx);
							%>
							<% String lName = " ";%>
							<% if(emp.getEmployee_lname() != null) {
								lName = emp.getEmployee_lname();
							}
							%>
							<% if (emp.getEmployee_userTyp() <= 2) {%>
							<%="<option value=" + emp.getEmployee_Id() + ">" + emp.getEmployee_fname() + " " + lName
		+ "</option>"%>
							<%} %>
							<%
							}
							%>
						</select> <br> 
						<input id="manageButton" type="submit" value="Manage">
						<button id="cancelManageButton" type="button" onclick="cancel()">Cancel</button>
					</form>
				</div>
			</div>
			<div class="empList">
				<table id="empTable">
					<tr>
						<th>Employee ID</th>
						<th>Employee First Name</th>
						<th>Employee Last Name</th>
						<th>Employee Type</th>
						<th>User Name</th>
						<th>Password</th>
					</tr>
					<%
					for (int idx = 0; idx < employees.size(); idx++) {
					%>
					<%
					Employee emp = (Employee) employees.get(idx);
					%>
					<%
					int userType = emp.getEmployee_userTyp();
					%>
					<%
					String output = " ";
					String lName = " ";
					%>
					<%="<tr>"%>
					<%="<td>" + emp.getEmployee_Id() + "</td>"%>
					<%="<td>" + emp.getEmployee_fname() + "</td>"%>
					<% if(emp.getEmployee_lname() != null) {
						lName = emp.getEmployee_lname();
					}
					%>
					<%="<td>" + lName + "</td>"%>
					<%
					switch (userType) {
						case (1) :
							output = "Admin";
							break;
						case (2) :
							output = "Therapist";
							break;
						case (3) :
							output = "Registrar";
							break;
						default :
							output = "Error";
							break;

					}
					%>
					<%="<td>" + output + "</td>"%>
					<%
					String uName = " ";
					String password = " ";
					%> 
					<% if(emp.getEmployee_uname() != null) {
					  uName = emp.getEmployee_uname();
					}
					if(emp.getEmployee_password() != null) {
						password = emp.getEmployee_password();
					}
					%>
					<%="<td>" + uName + "</td>" %>
					<%="<td>" + password + "</td>" %>
					<%="</tr>"%>
					<%
					}
					%>
				</table>
			</div>
		</div>
	</div>
	<script>
		function cancel() {
			$(".addEmployee").remove();
			$(".updateEmployee").remove();
			$(".removeEmployee").remove();
		}
		
		function changeEmp() {
			var empl = document.getElementById("empDropdown");
			var lName = " ";
			var uName = " ";
			var Tpassword = " ";
			var employee = emps.find(employee => employee.id === empl.selectedIndex)
			document.getElementById("fNameUpdate").value = employee.fName;
			if (employee.lName != null && employee.lName != 'null')
				{
				lName = employee.lName;
				document.getElementById("lNameUpdate").value = lName;
				}
			else
				{
				document.getElementById("lNameUpdate").value = "";
				}
			
			if (employee.uName != null && employee.uName != 'null')
				{
				uName = employee.uName;
				document.getElementById("updateUserName").value = uName;
				}
			else
				{
				document.getElementById("updateUserName").value = "";
				}
			
			if (employee.password != null && employee.password !='null')
				{
				password = employee.password;
				document.getElementById("updatePassword").value = password;
				}
			else
				{
				document.getElementById("updatePassword").value = "";
				}
			document.getElementById('empTypeDropdownU').value=employee.eType;
		}
	</script>
</body>


</html>