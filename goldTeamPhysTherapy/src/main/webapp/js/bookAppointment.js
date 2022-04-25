let appDate;
let appTherapy;
let therapyId;
let time;
let l_userType = document.getElementsByName("userType")[0].getAttribute('id');
const userType ={
	ADMIN : '1',
	THERAPIST : '2',
	USER : '3'
}

//window.onload = (function() {
$(document).ready(function() {
	validateUser();
	let appList = $(".appointmentList").detach();	
	setDisableSearchAppBtn();
	
	$('.overlay').hide();
	if(typeof confirmationV != 'undefined')
	{
		$('.overlay').show();
		openNewConfirmationForm();
	}
	
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
	
	$(function() {
		$("#datepicker").datepicker({
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
				appDate = $(this).datepicker('getDate');
				setDisableSearchAppBtn();
				$("#availableAppointments").empty();
			}
		});
	});

	$('#therapyDropdown').change(function() {
		therapyId = $("#therapyDropdown :selected").val();
		appTherapy = $("#therapyDropdown :selected").text();
		$("#availableAppointments").empty();
	});

	showEmpSchedule(appList);
});

function fieldValsNull(){
	if($("#therapyDropdown :selected").text()=='Select Therapy'){
		return true;
	}else{
		return false;
	}
}

function setDisableSearchAppBtn(){
	let searchAppBtn = document.getElementById("searchAppointmentButton");

	$('#searchAppointmentButton').prop('disabled',fieldValsNull());

	if(!$('#searchAppointmentButton').prop('disabled')){
		searchAppBtn.style.backgroundColor = "#4b7db3";
	}else{
		searchAppBtn.style.backgroundColor = "lightgrey";
	}
}
function showEmpSchedule(appList) {
	$(".appointmentMain").append(appList);
	$("#availableAppointments").empty();
	let timeFound = false;

	if (typeof therapists != typeof undefined) {
		$('#availableAppointments').append('<tr><th>Therapist</th><th>Available Times</th></tr>');
		therapists.forEach(t => {
			//let shiftArr = t.shift.split(':00-');
			let shiftArr = t.shift.split('-');
			let appTimesArr = t.scheduledAppTimes.split(',');
			let shiftStart = parseInt(shiftArr[0]);
			let shiftEnd = parseInt(shiftArr[1]);
			let availableAppTime;
			if (shiftStart != 0) {
				timeFound = true;
				$("#availableAppointments").append("<tr>")
					.append("<td>" + t.fname + "</td>");
				for (l_AvailableHr = shiftStart; l_AvailableHr < shiftEnd; l_AvailableHr++) {
					let appTimeIsAvailable = true;
					
					//check if hour is really available or if already booked
					for(idx = 0; idx < appTimesArr.length; idx++){
						let appHr = parseInt(appTimesArr[idx]);
						if (appHr != NaN){
								if(appHr == l_AvailableHr){
								appTimeIsAvailable = false;
							}
						}
					}
					if(appTimeIsAvailable){
						availableAppTime = l_AvailableHr;
						standardTime = availableAppTime;
						if (standardTime > 12){
							standardTime -= 12;
						}
						$("#availableAppointments").append("<input class='searchButton' type='submit' onclick='openForm(this.id)' value=" + standardTime + ":00 id=" + availableAppTime + "-" + t.fname + "-" + appTherapy + ">");
					}
				}
				$("#availableAppointments").append("</tr>")
			}
			if (!timeFound) {
				$("#availableAppointments").append("<h3>There are no appointments available on this date.</h3>");
			}
		});
	} else {

	}
}
function openForm(id) {
	$('.overlay').show();
	document.getElementById("myForm").style.display = "block";
	let detailsArray = id.split('-');
	let time = detailsArray[0];
	let therapistName = detailsArray[1];
	therapyId = $("#therapyDropdown :selected").val();
	appTherapy = $("#therapyDropdown :selected").text();
	appDate = $("#datepicker").datepicker('getDate');
	
	document.getElementById("appTherapyIdIn").setAttribute('value', therapyId);
	document.getElementById("appTherapistIn").setAttribute('value',therapistName);
	document.getElementById("appTimeIn").setAttribute('value', time);
	
	$('#appTherapy').html(appTherapy);
	$('#appTherapist').html(therapistName);
	$('#appDate').html(appDate.toDateString());
	if (time > 12){
		time -= 12;
	}
	$('#appTime').html(time + ":00");
}

function openConfirmationForm(actionTxt) {
	document.getElementById('myForm').style.display = "none";
	document.getElementById("confirmMsg").innerHTML = "Are you sure you want to " + actionTxt + " this appointment?";
	document.getElementById("confirmationForm").style.display = "block";
}

function closeForm() {
	$('.overlay').hide();
	document.getElementById("myForm").style.display = "none";
	document.getElementById('confirmationForm').style.display = "none";
}

function callMainServlet() {
	
	/*let confirmation = false
	while (confirmation == false) {
	confirmation = openConfirmAppointmentForm();
	}*/
	let form = document.getElementById("bookingForm");
	
	//document.getElementById("appIdIn1").setAttribute('value', selectedAppId);
	form.action = "../MainServlet";
	form.method="post";
	form.submit();
}

/*function openConfirmAppointmentForm() {
	$('#confirmationForm').hide();
	var therapist = document.getElementById("appTherapistIn").value;
	var therapy = $("#therapyDropdown :selected").text();
	var time = document.getElementById("appTimeIn").value;
	
	if (time > 12)
	{
		time -= 12;
	}
	document.getElementById('myForm').style.display = "none";
	document.getElementById("appId").innerHMTL = 
	document.getElementById("confirmationMsg").innerHTML = "Appointment for: <br>" + therapy + "<br>with "+ therapist + "<br>at "+ time + ":00<br>Approved";
	document.getElementById("newConfirmationForm").style.display = "block";
}*/

function openNewConfirmationForm() {
	$('#confirmationForm').hide();
	document.getElementById('myForm').style.display = "none";
	document.getElementById("newAppId").innerHTML = "Confirmation ID: " + selAppt.selAppId;
	var appTimeArr = selAppt.appTime.split(' ');
	var appTimeConfStr = appTimeArr[0] + ', ' + appTimeArr[1] + ' ' + appTimeArr[2] + ', ' + appTimeArr[5] + ' at ' + appTimeArr[3].split(':')[0] + ':00';
	document.getElementById("newConfirmationMsg").innerHTML = "Appointment: <br>" + selAppt.appTher + " with " + selAppt.empFName + "<br>" + appTimeConfStr +"<br>Approved";
	document.getElementById("diffConfirmationForm").style.display = "block";
}

function validateUser(){
	if(l_userType>userType.USER){
		// code block for invalid user
		alert("INVALID USER PRIVALEGES");
		location.href = "home.jsp";
	}
}