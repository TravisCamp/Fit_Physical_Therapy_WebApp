/**
 * scripting for updating user profile
 */
let l_userType = document.getElementsByName("userType")[0].getAttribute('id');
let showPassBool = false;
const userType ={
	ADMIN : '1',
	THERAPIST : '2',
	USER : '3'
}

$(document).ready(function() {
	validateUser();
	$('.overlay').hide();
	
	switch (l_userType) {
		case userType.ADMIN:
			// code block for admin user
			break;
		case userType.THERAPIST:
			// code block for therapist user
			$('.administrator').remove();
			break;
		case userType.USER:
			// code block for registrar user
			$('.administrator').remove();
			break;
	}
});
	
function showPass(){
	showPassBool = !showPassBool;
	input = document.getElementById('password');
	btn = document.getElementById('showHideBtn');
	
	if(showPassBool){		
		input.type = 'text'; //show the password
		btn.innerText = 'hide';
	}else{
		input.type = 'password'; //show the password
		btn.innerText = 'show';
	}
	event.preventDefault(); //stop page from auto refreshing
}

function openConfirmationForm() {
	$('.overlay').show();
	document.getElementById("confirmMsg").innerHTML = "Are you sure you want to save these changes?";
	document.getElementById("confirmationForm").style.display = "block";
}

function closeForm() {	
	$('.overlay').hide();
	document.getElementById('confirmationForm').style.display = "none";
}

function callUpdateServlet() {
	let form = document.getElementById("createAcctForm");

	form.action = "../CreateAccountServlet";
	form.method="post";
	form.submit();
}

function validateUser(){	
	if(l_userType>userType.USER){
		// code block for invalid user
		alert("INVALID USER PRIVALEGES");
		location.href = "home.jsp";
	}
}