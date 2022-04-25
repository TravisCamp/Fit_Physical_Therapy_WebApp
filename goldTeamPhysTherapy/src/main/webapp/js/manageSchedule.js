let therapistId;
let schedule;
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
			callUpdateEmpScheduleServlet();
			
	event.preventDefault(); //stop page from auto refreshing
		}
	}
}

$(document).ready(function() {
	validateUser();
	schedule = $("#manageScheduleForm").detach();
	setDisableViewSchedBtn();
	$('.overlay').hide();
	
	if(typeof invalidUpdate != 'undefined' && invalidUpdate != "null")
	{
		showMessage();
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
				workWeekManage = $(this).datepicker('getDate');
				setDisableViewSchedBtn();
			}
		});
	});
	
	
	$('#therapistDropdown').change(function() {
		therapistId = $("#therapistDropdown :selected").val();
		therapist = $("#therapistDropdown :selected").text();
	});
	
	if (scheduleWeek != "null" && scheduleWeek != "" && workWeekManage != "") {
		showSchedule();
	}
	
	if (scheduleWeek == "" || workWeekManage == "") {
		if (scheduleWeek == ""){
			$('#scheduleHeader').html('<h2>This employee does not have a schedule for this week.</h2>');
		}
		else{
			$('#scheduleHeader').html('<h2>You must select a therapist and date to view schedule.</h2>');
		}
	}
});

function showSchedule() {
	$('.scheduleMain').append(schedule);
	$('#scheduleHeader').html('<h2>Schedule for ' + therapist + ' for the week of ' + workWeekManage + '</h2>');
	$("label[for*='updateWorkWeeks']").html("Change schedule for all weeks starting " + workWeekManage);	
	let weekArr = scheduleWeek.split(' , ');
	
	$('.startTimes').change(function() {
		$(this).siblings('.endTimes').find('option').each(function(){
			$(this).show();
		});

		var start = parseInt($(this).val());
		$(this).siblings('.endTimes').find('option').each(function(){
			if(parseInt($(this).val()) <= start){
				$(this).hide();
			}
		});
	});
	
	$('.endTimes').change(function() {
		$(this).siblings('.startTimes').find('option').each(function(){
			$(this).show();
		});

		var end = parseInt($(this).val());
		$(this).siblings('.startTimes').find('option').each(function(){
			if(parseInt($(this).val()) >= end){
				$(this).hide();
			}
		});
	});
	
	// Monday Schedule
	let monStart = parseInt(weekArr[2].split(':')[0]);
	let monEnd = parseInt(weekArr[3].split(':')[0]);
	let monStartStandardTime = monStart;
	let monEndStandardTime = monEnd;
	
	$('#mondayOff').click(function() {
		if(this.checked){
			$('#mondayTimes').hide();
		}
		else{
			$('#mondayTimes').show();
		}
	});
	
	if(isNaN(monStart)){
		$('#currentShiftMonday').html("<strong>Current Shift: Off</strong>");
		$('#mondayOff').click();
	}
	else{
		if(monStartStandardTime > 12)
		{
			monStartStandardTime -=12;
		}
		if(monEndStandardTime > 12)
		{
			monEndStandardTime -=12;
		}
		$('#currentShiftMonday').html("<strong>Current Shift: " + monStartStandardTime + ":00 - " + monEndStandardTime + ":00</strong>");
		$('#mondayStart').val(monStart);
		$('#mondayEnd').val(monEnd);
		
		$('#mondayStart').find('option').each(function(){
			if(parseInt($(this).val()) >= monEnd){
				$(this).hide();
			}
		});
		
		$('#mondayEnd').find('option').each(function(){
			if(parseInt($(this).val()) <= monStart){
				$(this).hide();
			}
		});
	}
	
	// Tuesday Schedule
	let tueStart = parseInt(weekArr[4].split(':')[0]);
	let tueEnd = parseInt(weekArr[5].split(':')[0]);
	let tueStartStandardTime = tueStart;
	let tueEndStandardTime = tueEnd;
	$('#tuesdayOff').click(function() {
		if(this.checked){
			$('#tuesdayTimes').hide();
		}
		else{
			$('#tuesdayTimes').show();
		}
	});
	
	if(isNaN(tueStart)){
		$('#currentShiftTuesday').html("<strong>Current Shift: Off</strong>");
		$('#tuesdayOff').click();
	}
	else{
		if(tueStartStandardTime > 12)
		{
			tueStartStandardTime -=12;
		}
		if(tueEndStandardTime > 12)
		{
			tueEndStandardTime -=12;
		}
		$('#currentShiftTuesday').html("<strong>Current Shift: " + tueStartStandardTime + ":00 - " + tueEndStandardTime + ":00</strong>");
		$('#tuesdayStart').val(tueStart);
		$('#tuesdayEnd').val(tueEnd);
		
		$('#tuesdayStart').find('option').each(function(){
			if(parseInt($(this).val()) >= tueEnd){
				$(this).hide();
			}
		});
		
		$('#tuesdayEnd').find('option').each(function(){
			if(parseInt($(this).val()) <= tueStart){
				$(this).hide();
			}
		});
	}
	
	// Wednesday Schedule
	let wedStart = parseInt(weekArr[6].split(':')[0]);
	let wedEnd = parseInt(weekArr[7].split(':')[0]);
	let wedStartStandardTime = wedStart;
	let wedEndStandardTime = wedEnd;
	$('#wednesdayOff').click(function() {
		if(this.checked){
			$('#wednesdayTimes').hide();
		}
		else{
			$('#wednesdayTimes').show();
		}
	});
	
	if(isNaN(wedStart)){
		$('#currentShiftWednesday').html("<strong>Current Shift: Off</strong>");
		$('#wednesdayOff').click();
	}
	else{
		if(wedStartStandardTime > 12)
		{
			wedStartStandardTime -=12;
		}
		if(wedEndStandardTime > 12)
		{
			wedEndStandardTime -=12;
		}
		$('#currentShiftWednesday').html("<strong>Current Shift: " + wedStartStandardTime + ":00 - " + wedEndStandardTime + ":00</strong>");
		$('#wednesdayStart').val(wedStart);
		$('#wednesdayEnd').val(wedEnd);
		
		$('#wednesdayStart').find('option').each(function(){
			if(parseInt($(this).val()) >= wedEnd){
				$(this).hide();
			}
		});
		
		$('#wednesdayEnd').find('option').each(function(){
			if(parseInt($(this).val()) <= wedStart){
				$(this).hide();
			}
		});
	}
	
	// Thursday Schedule
	let thuStart = parseInt(weekArr[8].split(':')[0]);
	let thuEnd = parseInt(weekArr[9].split(':')[0]);
	let thuStartStandardTime = thuStart;
	let thuEndStandardTime = thuEnd;
	$('#thursdayOff').click(function() {
		if(this.checked){
			$('#thursdayTimes').hide();
		}
		else{
			$('#thursdayTimes').show();
		}
	});
	
	if(isNaN(thuStart)){
		$('#currentShiftThursday').html("<strong>Current Shift: Off</strong>");
		$('#thursdayOff').click();
	}
	else{
		if(thuStartStandardTime > 12)
		{
			thuStartStandardTime -=12;
		}
		if(thuEndStandardTime > 12)
		{
			thuEndStandardTime -=12;
		}
		$('#currentShiftThursday').html("<strong>Current Shift: " + thuStartStandardTime + ":00 - " + thuEndStandardTime + ":00</strong>");
		$('#thursdayStart').val(thuStart);
		$('#thursdayEnd').val(thuEnd);
		
		$('#thursdayStart').find('option').each(function(){
			if(parseInt($(this).val()) >= thuEnd){
				$(this).hide();
			}
		});
		
		$('#thursdayEnd').find('option').each(function(){
			if(parseInt($(this).val()) <= thuStart){
				$(this).hide();
			}
		});
	}
	
	// Friday Schedule
	let friStart = parseInt(weekArr[10].split(':')[0]);
	let friEnd = parseInt(weekArr[11].split(':')[0]);
	let friStartStandardTime = friStart;
	let friEndStandardTime = friEnd;
	$('#fridayOff').click(function() {
		if(this.checked){
			$('#fridayTimes').hide();
		}
		else{
			$('#fridayTimes').show();
		}
	});
	
	if(isNaN(friStart)){
		$('#currentShiftFriday').html("<strong>Current Shift: Off</strong>");
		$('#fridayOff').click();
	}
	else{
		if(friStartStandardTime > 12)
		{
			friStartStandardTime -=12;
		}
		if(friEndStandardTime > 12)
		{
			friEndStandardTime -=12;
		}
		$('#currentShiftFriday').html("<strong>Current Shift: " + friStartStandardTime + ":00 - " + friEndStandardTime + ":00</strong>");
		$('#fridayStart').val(friStart);
		$('#fridayEnd').val(friEnd);
		
		$('#fridayStart').find('option').each(function(){
			if(parseInt($(this).val()) >= friEnd){
				$(this).hide();
			}
		});
		
		$('#fridayEnd').find('option').each(function(){
			if(parseInt($(this).val()) <= friStart){
				$(this).hide();
			}
		});
	}
	
	
	// Saturday Schedule
	let satStart = parseInt(weekArr[12].split(':')[0]);
	let satEnd = parseInt(weekArr[13].split(':')[0]);
	let satStartStandardTime = satStart;
	let satEndStandardTime = satEnd;
	$('#saturdayOff').click(function() {
		if(this.checked){
			$('#saturdayTimes').hide();
		}
		else{
			$('#saturdayTimes').show();
		}
	});
	
	if(isNaN(satStart)){
		$('#currentShiftSaturday').html("<strong>Current Shift: Off</strong>");
		$('#saturdayOff').click();
	}
	else{
		if(satStartStandardTime > 12)
		{
			satStartStandardTime -=12;
		}
		if(satEndStandardTime > 12)
		{
			satEndStandardTime -=12;
		}
		$('#currentShiftSaturday').html("<strong>Current Shift: " + satStartStandardTime + ":00 - " + satEndStandardTime + ":00</strong>");
		$('#saturdayStart').val(satStart);
		$('#saturdayEnd').val(satEnd);
		
		$('#saturdayStart').find('option').each(function(){
			if(parseInt($(this).val()) >= satEnd){
				$(this).hide();
			}
		});
		
		$('#saturdayEnd').find('option').each(function(){
			if(parseInt($(this).val()) <= satStart){
				$(this).hide();
			}
		});
	}
}
function setDisableViewSchedBtn(){
	let searchAppBtn = document.getElementById("manageScheduleButton");

	$('#manageScheduleButton').prop('disabled',fieldValsNull());

	if(!$('#manageScheduleButton').prop('disabled')){
		searchAppBtn.style.backgroundColor = "#4b7db3";
	}else{
		searchAppBtn.style.backgroundColor = "lightgrey";
	}
}

function fieldValsNull(){
	if(therapist == null || workWeekManage == null || workWeekManage == "" || workWeekManage == "null"){
		return true;
	}else{
		return false;
	}
}

function validateUser(){
	if(l_userType>userType.ADMIN){
		// code block for invalid user
		alert("INVALID USER PRIVILEGES");
		location.href = "home.jsp";
	}
}

function callUpdateEmpScheduleServlet() {
	let form = document.getElementById("searchScheduleForm");
	form.action = "../UpdateEmpScheduleServlet";
	form.method="post";
	form.submit();
}

function callUpdateEmpScheduleServletGet() {
	let form = document.getElementById("manageScheduleForm");
	form.action = "../UpdateEmpScheduleServlet";
	form.method="get";
	form.submit();
}

function openConfirmationForm() {
	$('.overlay').show();
	document.getElementById("confirmMsg").innerHTML = "Are you sure you want to make this schedule change?";
	document.getElementById("confirmationForm").style.display = "block";
}

function closeForm() {	
	$('.overlay').hide();
	document.getElementById('confirmationForm').style.display = "none";
}

function showMessage() {	
	$('.overlay').show();
	$('#errorMessage').css("display", "block");

	if (invalidUpdate == "true")
	{
		$("#errorMsg").html("<h3>These changes could not be made due to a conflicting appointment. Please review each week's appointments.</h3>");
	}
}
function closeMessage() {	
	$('.overlay').hide();
	$('#errorMessage').css("display", "none");
}

