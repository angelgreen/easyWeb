$(document).ready(function(){

	var user = {};
	$.extend(user, ZTE.user);
		
	ZTE.debug(user,"new user");

	//UI binding ...
	$('#name').val(ZTE.user['name'] || "");
	$('#password').val(ZTE.user['password'] || "");

	$("#prev").bind("click",function() {
		
		alert("are you leave");
		//TODO .....
		window.location = "/index.html";	
		return;
	});

	$("#next").bind("click",function() {
		var v = ZTE.validate_phone($('#name').val());
		v =  v && ZTE.validate_password($('#password').val());
		
		ZTE.validate_name($('#name').val(),
						function(obj) {
						},
						function(obj) {
							alert("error");
							return;
						});

		ZTE.validate_password($('#password').val(),
						function(obj) {
							alert('right');
						},
						function(obj) {
							alert("error"); return;
						});

		$.extend(ZTE.user, user);

		$('.container').load('/html/secondPage.html');
		return ;	
	});
});
