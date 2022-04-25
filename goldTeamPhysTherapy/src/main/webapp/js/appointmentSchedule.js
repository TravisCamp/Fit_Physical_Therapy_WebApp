let schedule;
let l_userType = document.getElementsByName("userType")[0].getAttribute('id');
const userType ={
	ADMIN : '1',
	THERAPIST : '2',
	USER : '3'
}	

window.onload=function(){
	if(typeof refreshPage != typeof undefined){
		if(refreshPage=="true"){
			callAppScheduleServlet();
		}
	}
}

$(document).ready(function() {
	validateUser();
	schedule = $("#weeklySchedule").detach();
	$('#printSchedule').hide();
	setDisableViewSchedBtn();
	$('.overlay').hide();
	
	switch (l_userType) {
		case userType.ADMIN:
			// code block for admin user
			break;
		case userType.THERAPIST:
			// code block for therapist user
			$('.administrator').remove();
			//$('#editAppButton').remove();
			$('#cancelAppButton').remove();
			break;
		case userType.USER:
			// code block for registrar user
			$('.administrator').remove();
			break;
	}
	
	$(function() {
		$("#daypicker").datepicker({
			showOtherMonths: true,
			selectOtherMonths: true,
			showOn: "button",
			buttonImage: "../img/calendar.gif",
			buttonImageOnly: true,
			buttonText: "Select date",
			minDate: 0,
			maxDate: "+1M +10D",
			beforeShowDay: function(date) {
        		var day = date.getDay();
        		return [(day != 0), ''];
    		},
			onSelect: function() {
				workDay = $(this).datepicker('getDate');
				setDisableViewSchedBtn();
			}
		});
	});	
	if (workDay != "" && workDay != "null"){
		showSchedule();
	}
	if (workDay == "" || workDay == "null"){
		$('#scheduleHeader').html('<h2>You must select a date to view schedule.</h2>');
	}
});

function showSchedule(){
	$('#printSchedule').show();
	$('.scheduleMain').append(schedule);
	$('#scheduleHeader').html('<h2>Appointments for ' + workDay + '</h2>');
	apps.forEach(a => {
		console.log(a);
		let id = "";
		switch (a.time) {
			case '8':
			id = "#eight";
			break;
			case '9':
			id = "#nine";
			break;
			case '10':
			id = "#ten";
			break;
			case '11':
			id = "#eleven";
			break;
			case '12':
			id = "#twelve";
			break;
			case '13':
			id= "#one";
			break;
			case '14':
			id= "#two";
			break;
			case '15':
			id= "#three";
			break;
			case '16':
			id= "#four";
		}
		if(a.time>= 13)
		{
			var newTime = 0;
			newTime = a.time -12;
		}
		else {
			var newTime = 0;
			newTime = a.time;
		}
		/*$(id).html(
			
			'<th>'+ newTime + ':00' +'</th>');*/
		$(id).append(
			/*'<th>'+ newTime + ':00' +'</th>' / */
			'<td id="'+ a.id +'"class="booked">'+ a.therapist + ": " + a.therapy +'</td>'
		);
		var testId = "#" + a.id;
		$(testId).click(function() {
			$('.overlay').show();
			document.getElementById("myForm").style.display = "block";
			//document.getElementById("appId").setAttribute('value', appId);
			//getElementById("appId").innerHTML = a.id;
			$('#appId').html(a.id);
			$('#appFirstName').html(a.fname);
			$('#appLastName').html(a.lname);
			$('#appPNumber').html(a.pNumber);
			$('#appEmail').html(a.email);
			$('#appTherapy').html(a.therapy);
			$('#appTherapist').html(a.therapist);
			dateArr = a.date.split(' ');
			$('#appDate').html(dateArr[0] + ', ' + dateArr[1] + ' ' + dateArr[2] + ', ' + dateArr[5]);
			time = 0;
			if (a.time > 12) {
				time = a.time - 12;
			}
			else {
				time = a.time;
			}
			$('#appTime').html(time + ":00");
			$('#appNotes').text(a.notes);
			$('#editAppId').val(a.id);
	//		switch (l_userType) {
	//	case userType.ADMIN:
	//		// code block for admin user
	//		break;
	//	case userType.THERAPIST:
	//		// code block for therapist user
	//		$('#editAppButton').remove();
	//		$('#cancelAppButton').remove();
	//		$('#saveChangesButton').remove();
	//		break;
	//	case userType.USER:
	//		// code block for registrar user
	//		break;
	//		}
			document.getElementById("appNotes").readOnly = true;
			document.getElementById("editAppButton").style.display="block";
			document.getElementById("saveChangesButton").style.display="none";
			//set appId for use in callServlet()
			selectedAppId=a.id;
			});
	});
}

//open confirmation window
function openConfirmationForm(actionTxt) {
	document.getElementById('myForm').style.display = "none";
	document.getElementById("confirmMsg").innerHTML = "Are you sure you want to " + actionTxt + " this appointment?";
	document.getElementById("confirmationForm").style.display = "block";
}

//close all open forms
function closeForm() {	
	$('.overlay').hide();
	document.getElementById('myForm').style.display = "none";
	document.getElementById('confirmationForm').style.display = "none";
}

//edit notes
function callEditServlet() {
	let form = document.getElementById("editNotesForm");
	form.action = "../EditNotesServlet";
	form.method="get";
	form.submit();
}

//cancel appointment
function callCancelServlet() {
	let form = document.getElementById("cancelConfirmationForm");
	document.getElementById("appIdIn1").setAttribute('value', selectedAppId);
	form.action = "../CancelAppointmentServlet";
	form.method="post";
	form.submit();
}

//allows notes text field to be edited
function editTextArea(){
	document.getElementById("appNotes").removeAttribute('readonly');
	document.getElementById("editAppButton").style.display="none";
	document.getElementById("saveChangesButton").style.display="block";
}

//set the button to be disabled
function setDisableViewSchedBtn(){
	let searchAppBtn = document.getElementById("viewScheduleButton");

	$('#viewScheduleButton').prop('disabled',fieldValsNull());

	if(!$('#viewScheduleButton').prop('disabled')){
		searchAppBtn.style.backgroundColor = "#4b7db3";
	}else{
		searchAppBtn.style.backgroundColor = "lightgrey";
	}
}

//check if required fields are null
function fieldValsNull(){
	if(workDay == "" || workDay == "null"){
		return true;
	}else{
		return false;
	}
}

//validate user type
function validateUser(){
	if(l_userType>userType.USER){
		// code block for invalid user
		alert("INVALID USER PRIVILEGES");
		location.href = "home.jsp";
	}
}

//shows appointments
function callAppScheduleServlet(){
	let form = document.getElementById("searchScheduleForm");
	form.action = "../AppScheduleServlet";
	form.method="get";
	form.submit();
}