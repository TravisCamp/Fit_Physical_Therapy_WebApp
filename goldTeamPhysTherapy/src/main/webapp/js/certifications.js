$(document).ready(function() {
	validateUser();
	
    var addDiv = $(".addCertification").detach();
    var removeDiv = $(".removeCertification").detach();
    var updateDiv = $(".updateCertification").detach(); 
    $("#addCertButton").click(function() {
        $(".updateCertification").remove();
        $(".removeCertification").remove();
        $(addDiv).appendTo(".updateCerts");
    });
    $("#updateCertButton").click(function() {
        $(".addCertification").remove();
        $(".removeCertification").remove();
        $(updateDiv).appendTo(".updateCerts");
    });
    
    $("#removeCertButton").click(function() {
        $(".addCertification").remove();
        $(".updateCertification").remove();
        $(removeDiv).appendTo(".updateCerts");
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