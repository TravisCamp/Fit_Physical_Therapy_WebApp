$(document).ready(function() {
	validateUser();
    var addDiv = $(".addEmployee").detach();
    var removeDiv = $(".removeEmployee").detach();
    var updateDiv = $(".updateEmployee").detach(); 
    var manageDiv = $(".manageEmpCert").detach();
    
	$('td:nth-child(5),th:nth-child(5)').hide();
	$('td:nth-child(6),th:nth-child(6)').hide();
   		
    $("#addEmpButton").click(function() {
        $(".updateEmployee").remove();
        $(".removeEmployee").remove();
        $(".manageEmpCert").remove();
        $(addDiv).appendTo(".updateEmployeeList");
    });
    $("#updateEmpButton").click(function() {
        $(".addEmployee").remove();
        $(".removeEmployee").remove();
        $(".manageEmpCert").remove();
        $(updateDiv).appendTo(".updateEmployeeList");
    });
    
    $("#removeEmpButton").click(function() {
        $(".addEmployee").remove();
        $(".updateEmployee").remove();
        $(".manageEmpCert").remove();
        $(removeDiv).appendTo(".updateEmployeeList");
    });
    
    $("#manageEmpButton").click(function() {
		$(".addEmployee").remove();
        $(".updateEmployee").remove();
		$(".removeEmployee").remove();
		$(manageDiv).appendTo(".updateEmployeeList");
	});
	
	$("#hide").change(function() {
		if ($(this).is(":checked")) {
		$('td:nth-child(5),th:nth-child(5)').hide();
		$('td:nth-child(6),th:nth-child(6)').hide();
   		 } else {
		$('td:nth-child(5),th:nth-child(5)').show();
		$('td:nth-child(6),th:nth-child(6)').show();
   		 }	
	});
	

	 
});


function validateUser(){
	let l_userType = document.getElementsByName("userType")[0].getAttribute('id');
	const userType ={
		ADMIN : '1',
		THERAPIST : '2',
		USER : '3'
	}
	
	if(l_userType>userType.ADMIN){
		// code block for invalid user
		alert("INVALID USER PRIVALEGES");
		location.href = "home.jsp";
	}
}


function validateForm() {
	var id = document.getElementById("empDropdown").value;
	var userName = document.getElementById("updateUserName").value;
	var userValidate = emps.find(employee => employee.uName === userName);
	if (userValidate.uName == userName && id != userValidate.eId && userName != "")
	{
		alert("Employees cannot have the same user name!");
		return false;
	}
}

//generate and validate username and password
function addValidateForm() {
	var userName = document.getElementById("newFName").value.toLowerCase().concat('_',generateId(5));
	var userValidate = emps.find(employee => employee.uName === userName);
	var uname = document.getElementById('newUserName');
	var upass = document.getElementById('newPassword');
	
	//set input form
	uname.value=userName;
	upass.value='P@ssword';
	
	if (typeof userValidate !== 'undefined')
	{
		alert("Employees cannot have the same user name!");
		return false;
	}
}

// function creates random n number of chars
function generateId(length) {
    var result           = '';
    var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!?';
    
    for ( var i = 0; i < length; i++ ) {
      result += characters.charAt(Math.floor(Math.random() * characters.length));
   }
   return result;
}