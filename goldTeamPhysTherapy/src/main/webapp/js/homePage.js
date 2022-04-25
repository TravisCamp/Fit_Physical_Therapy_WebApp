let displayWarningMsg;

$(document).ready(function() {
	validateUser();
	$('.overlay').hide();
	
	if(displayWarningMsg){
		openConfirmationForm()
	}else{
		console.log("displayWarningMsg="+displayWarningMsg);
	}
});

function validateUser(){
	let l_userType = document.getElementsByName("userType")[0].getAttribute('id');
	const userType ={
		ADMIN : '1',
		THERAPIST : '2',
		USER : '3'
	}
	
	switch (l_userType) {
		case userType.ADMIN:
			// code block for admin user
/*			removeDivs = document.querySelectorAll('.therapist, .scheduler');	
			removeDivs.forEach(div => {
				div.remove();
			});*/
			break;
		case userType.THERAPIST:
			// code block for therapist user
			$('.administrator').remove();
			break;
		case userType.USER:
			// code block for registrar user
			$('.administrator').remove();
			break;
		default:
			// code block for invalid user
			alert("INVALID USER");
			location.href = "../index.jsp";
	}
}

function openConfirmationForm() {
	$('.overlay').show();
	document.getElementById("confirmMsg").innerHTML = "Change your password";
	document.getElementById("confirmationForm").style.display = "block";
}


function closeForm() {	
	$('.overlay').hide();
	document.getElementById('confirmationForm').style.display = "none";
}