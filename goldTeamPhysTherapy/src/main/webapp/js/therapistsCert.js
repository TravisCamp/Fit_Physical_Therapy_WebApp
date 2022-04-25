$(document).ready(function() {
	$(".notSeen").hide();
    var addDiv = $(".addEmployee").detach();
    var removeDiv = $(".removeEmployee").detach();
    
    $("#addEmpButton").click(function() {
        $(".removeEmployee").remove();
        $(addDiv).appendTo(".empMain");
    });
    
    $("#removeEmpButton").click(function() {
        $(".addEmployee").remove();
        $(removeDiv).appendTo(".empMain");
    });
    
});