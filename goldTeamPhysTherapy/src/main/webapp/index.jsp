<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Log In</title>
	<link rel="stylesheet" href="css/logInPage.css">
	<link href='https://fonts.googleapis.com/css?family=Open Sans'rel='stylesheet'>
</head>
<body>
	<div id="header">
		<h1>Fit Physical Therapy</h1>
	</div>
	<div id="selection">
		<div id="image">
			<img src="img/logo.jpg">
		</div>
		<div id="userSelect">
			<form action="LoginServlet" method="post">
				<h3>Login</h3>			
				<label for="uname">User Name:&nbsp;</label>
				<input type="text" name="uname" maxlength="45"><br>
				<label for="password">Password:&nbsp;</label>
				<input type="password" name="password" maxlength="45"><br> 
				<input id="loginButton" type="submit" value="Login">
			</form>
		</div>
	</div>
</body>
<!-- Sample footer -->
<footer> Copyright © 2022 Fit Physical Therapy | All rights reserved </footer>
</html>