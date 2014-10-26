
$(document).ready(function() {

	//init UI
	var user = {};
	$.extend(user, ZTE.user);

	//init UI
	$('#name').val(ZTE.user['name'] || '');
	$('#password').val(ZTE.user['password'] || '');

	//bind event
	$('#prev').bind('click',function() {
		alert('leave');
		validate();
		$.extend(ZTE.user, user);
		$('.container').load('/html/firstPage.html');
	});

	$('#next').bind('click',function() {
		//validate
		validate();
		$.extend(ZTE.user, user);
		//next page
		$('.container').load("/html/thirdPage.html");
	});


	//validate function ... TODO more abstract ...
	function validate() {
	
		ZTE.validate_name($('#name'),
						function(obj) { ZTE.user['name'] = obj.val(); },
						function(obj) {
							alert("name error"); return;
						});
		ZTE.validate_phone($('#phone'),
						function(obj) {ZTE.user['phone'] = obj.val(); },
						function(obj) { alert("phone error"); return;});
		ZTE.validate_password($('password'), 
						function(obj) {ZTE.user['password'] = obj.val();},
						function() {alert("password error"); return;});

	}
});
