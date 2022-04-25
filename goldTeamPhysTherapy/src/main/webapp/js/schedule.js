let therapistId;
let schedule;
let selectedAppId;
let l_userType = document.getElementsByName("userType")[0].getAttribute('id');
const userType ={
	ADMIN : '1',
	THERAPIST : '2',
	USER : '3'
}

// refresh page data if flag is set
window.onload=function(){
	if(typeof refreshPage != typeof undefined){
		if(refreshPage=="true"){
			callViewScheduleServlet();
		}
	}
	if(typeof scheduleChanged != typeof undefined){
		if(scheduleChanged=="true"){
			callViewScheduleServlet();
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
			document.getElementById("therapistDropdown").options.length = 1;
			break;
		case userType.USER:
			// code block for registrar user
			$('.administrator').remove();
			break;
	}
	
	$(function() {
		$("#weekpicker").datepicker({
			showOtherMonths: true,
			selectOtherMonths: true,
			showOn: "button",
			buttonImage: "../img/calendar.gif",
			buttonImageOnly: true,
			buttonText: "Select date",
			minDate: "-6D",
			maxDate: "+1M +10D",
			beforeShowDay: function(date) {
				var day = date.getDay();
				return [(day == 1), ''];
			},
			onSelect: function() {
				workWeek = $(this).datepicker('getDate');
				setDisableViewSchedBtn();
			}
		});
	});
	$('#therapistDropdown').change(function() {
		therapistId = $("#therapistDropdown :selected").val();
		therapist = $("#therapistDropdown :selected").text();
	});
	if (scheduleWeek != "null" && scheduleWeek != "" && workWeek != "") {
		showSchedule();
	}
	if (scheduleWeek == "" || workWeek == "") {
		if (scheduleWeek == ""){
			$('#scheduleHeader').html('<h2>This employee does not have a schedule for this week.</h2>');
		}
		else{
			$('#scheduleHeader').html('<h2>You must select a therapist and date to view schedule.</h2>');
		}
	}
});

function showSchedule() {
	$('#printSchedule').show();
	$('.scheduleMain').append(schedule);
	$('#scheduleHeader').html('<h2>Schedule for ' + therapist + ' for the week of ' + workWeek + '</h2>');
	$('#days').html(
		'<th></th> \
		<th>Monday</th> \
		<th>Tuesday</th> \
		<th>Wednesday</th> \
		<th>Thursday</th> \
		<th>Friday</th> \
		<th>Saturday</th>');
	$('#eight').html(
		'<th>8:00</th> \
		<td id="Mon-8" class="off"></td> \
		<td id="Tue-8" class="off"></td> \
		<td id="Wed-8" class="off"></td> \
		<td id="Thu-8" class="off"></td> \
		<td id="Fri-8" class="off"></td> \
		<td id="Sat-8" class="off"></td>');
	$('#nine').html(
		'<th>9:00</th> \
		<td id="Mon-9" class="off"></td> \
		<td id="Tue-9" class="off"></td> \
		<td id="Wed-9" class="off"></td> \
		<td id="Thu-9" class="off"></td> \
		<td id="Fri-9" class="off"></td> \
		<td id="Sat-9" class="off"></td>');
	$('#ten').html(
		'<th>10:00</th> \
		<td id="Mon-10" class="off"></td> \
		<td id="Tue-10" class="off"></td> \
		<td id="Wed-10" class="off"></td> \
		<td id="Thu-10" class="off"></td> \
		<td id="Fri-10" class="off"></td> \
		<td id="Sat-10" class="off"></td>');
	$('#eleven').html(
		'<th>11:00</th> \
		<td id="Mon-11" class="off"></td> \
		<td id="Tue-11" class="off"></td> \
		<td id="Wed-11" class="off"></td> \
		<td id="Thu-11" class="off"></td> \
		<td id="Fri-11" class="off"></td> \
		<td id="Sat-11" class="off"></td>');
	$('#twelve').html(
		'<th>12:00</th> \
		<td id="Mon-12" class="off"></td> \
		<td id="Tue-12" class="off"></td> \
		<td id="Wed-12" class="off"></td> \
		<td id="Thu-12" class="off"></td> \
		<td id="Fri-12" class="off"></td> \
		<td id="Sat-12" class="off"></td>');
	$('#one').html(
		'<th>1:00</th> \
		<td id="Mon-13" class="off"></td> \
		<td id="Tue-13" class="off"></td> \
		<td id="Wed-13" class="off"></td> \
		<td id="Thu-13" class="off"></td> \
		<td id="Fri-13" class="off"></td> \
		<td id="Sat-13" class="off"></td>');
	$('#two').html(
		'<th>2:00</th> \
		<td id="Mon-14" class="off"></td> \
		<td id="Tue-14" class="off"></td> \
		<td id="Wed-14" class="off"></td> \
		<td id="Thu-14" class="off"></td> \
		<td id="Fri-14" class="off"></td> \
		<td id="Sat-14" class="off"></td>');
	$('#three').html(
		'<th>3:00</th> \
		<td id="Mon-15" class="off"></td> \
		<td id="Tue-15" class="off"></td> \
		<td id="Wed-15" class="off"></td> \
		<td id="Thu-15" class="off"></td> \
		<td id="Fri-15" class="off"></td> \
		<td id="Sat-15" class="off"></td>');
	$('#four').html(
		'<th>4:00</th> \
		<td id="Mon-16" class="off"></td> \
		<td id="Tue-16" class="off"></td> \
		<td id="Wed-16" class="off"></td> \
		<td id="Thu-16" class="off"></td> \
		<td id="Fri-16" class="off"></td> \
		<td id="Sat-16" class="off"></td>');

	let weekArr = scheduleWeek.split(' , ');

	//Monday schedule
	let monStart = parseInt(weekArr[2].split(':')[0]);
	let monEnd = parseInt(weekArr[3].split(':')[0]);
	for (i = monStart; i < monEnd; i++) {
		let id = '#Mon-' + i;
		$(id).removeClass('off');
		$(id).addClass('available');
		$(id).html('Available');
	}

	//Tuesday schedule
	let tueStart = parseInt(weekArr[4].split(':')[0]);
	let tueEnd = parseInt(weekArr[5].split(':')[0]);
	for (i = tueStart; i < tueEnd; i++) {
		let id = '#Tue-' + i;
		$(id).removeClass('off');
		$(id).addClass('available');
		$(id).html('Available');
	}

	//Wednesday schedule
	let wedStart = parseInt(weekArr[6].split(':')[0]);
	let wedEnd = parseInt(weekArr[7].split(':')[0]);
	for (i = wedStart; i < wedEnd; i++) {
		let id = '#Wed-' + i;
		$(id).removeClass('off');
		$(id).addClass('available');
		$(id).html('Available');
	}

	//Thursday schedule
	let thuStart = parseInt(weekArr[8].split(':')[0]);
	let thuEnd = parseInt(weekArr[9].split(':')[0]);
	for (i = thuStart; i < thuEnd; i++) {
		let id = '#Thu-' + i;
		$(id).removeClass('off');
		$(id).addClass('available');
		$(id).html('Available');
	}

	//Friday schedule
	let friStart = parseInt(weekArr[10].split(':')[0]);
	let friEnd = parseInt(weekArr[11].split(':')[0]);
	for (i = friStart; i < friEnd; i++) {
		let id = '#Fri-' + i;
		$(id).removeClass('off');
		$(id).addClass('available');
		$(id).html('Available');
	}

	//Saturday schedule
	let satStart = parseInt(weekArr[12].split(':')[0]);
	let satEnd = parseInt(weekArr[13].split(':')[0]);
	for (i = satStart; i < satEnd; i++) {
		let id = '#Sat-' + i;
		$(id).removeClass('off');
		$(id).addClass('available');
		$(id).html('Available');
	}

	//Add appointments
	apps.forEach(a => {
		console.log(a);
		let id = "";
		switch (a.day) {
			case '2':
				id = "#Mon-"
				break;
			case '3':
				id = "#Tue-"
				break;
			case '4':
				id = "#Wed-"
				break;
			case '5':
				id = "#Thu-"
				break;
			case '6':
				id = "#Fri-"
				break;
			case '7':
				id = "#Sat-"
				break;
		}
		id += a.time;
		$(id).removeClass('available');
		$(id).addClass('booked');
		$(id).html(a.therapy);
		$(id).click(function() {
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
			
			document.getElementById("appNotes").readOnly = true;
			document.getElementById("editAppButton").style.display="block";
			document.getElementById("saveChangesButton").style.display="none";
			//set appId for use in callServlet()
			selectedAppId=a.id;
		});
	});
}

function openConfirmationForm(actionTxt) {
	document.getElementById('myForm').style.display = "none";
	document.getElementById("confirmMsg").innerHTML = "Are you sure you want to " + actionTxt + " this appointment?";
	document.getElementById("confirmationForm").style.display = "block";
}

function closeForm() {	
	$('.overlay').hide();
	document.getElementById('myForm').style.display = "none";
	document.getElementById('confirmationForm').style.display = "none";
}

function callViewScheduleServlet(){
	let form = document.getElementById("searchScheduleForm");
	form.action="../ViewScheduleServlet";
	form.method="get";
	form.submit();
}

function callEditServlet() {
	let form = document.getElementById("editNotesForm");
	//document.getElementById("editAppId").setAttribute('value', selectedAppId);
	form.action = "../EditNotesServlet";
	form.method="get";
	form.submit();
}

function callCancelServlet() {
	let form = document.getElementById("cancelConfirmationForm");
	document.getElementById("appIdIn1").setAttribute('value', selectedAppId);
	form.action = "../CancelAppointmentServlet";
	form.method="post";
	form.submit();
}

function editTextArea(){
	document.getElementById("appNotes").removeAttribute('readonly');
	document.getElementById("editAppButton").style.display="none";
	document.getElementById("saveChangesButton").style.display="block";
}

function setDisableViewSchedBtn(){
	let searchAppBtn = document.getElementById("viewScheduleButton");

	$('#viewScheduleButton').prop('disabled', fieldValsNull());

	if(!$('#viewScheduleButton').prop('disabled')){
		searchAppBtn.style.backgroundColor = "#4b7db3";
	}else{
		searchAppBtn.style.backgroundColor = "lightgrey";
	}
}

function fieldValsNull(){
	if(therapist == null || workWeek == null || workWeek == "" || workWeek == "null"){
		return true;
	}else{
		return false;
	}
}

function validateUser(){	
	if(l_userType>userType.USER){
		// code block for invalid user
		alert("INVALID USER PRIVILEGES");
		location.href = "home.jsp";
	}
}