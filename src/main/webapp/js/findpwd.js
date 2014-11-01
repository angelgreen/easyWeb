

$(document).ready(function() {
	//ui
	var findpwd_data = {};

	$.extend(findpwd_data, parent.findpwd_data);

	$('.phoneNumber').val(findpwd_data.phoneNumber || "");

	//event
	$('.back').bind('click',function() {
		findpwd_data.phoneNumber = $('.phoneNumber').val();
		parent.findpwd_data = {};
		$.extend(parent.findpwd_data, findpwd_data);
		window.location.href='/html/main.html';
		return false;
	});	

	$('.submit').bind('click',function() {
		findpwd_data.phoneNumber = $('.phoneNumber').val();
		parent.findpwd_data = {};
		$.extend(parent.findpwd_data, findpwd_data);
	
		$.ajax({
			type:'POST',
			path: '/rest/retrieve/findPwd',	
			data: JSON.stringify(findpwd_data)	
		}).success(function(data) {
			var code = data.code || "500";
			if(code == '200') {
				alert('ok');
			}else {
				alert('error');
			}
		}).error(function(xhr) {
			alert('error');
		});

		return false;
	});
});
