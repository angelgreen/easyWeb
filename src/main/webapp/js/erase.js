
$(document).ready(function() {

	var erase_data = {};
	$.extend(erase_data, parent.erase_data);
	//ui
	$('.phoneNumber').val(erase_data['phoneNumber'] || '');

	//event
	$('.back').bind('click',function() {
		erase_data['phoneNumber'] = $('.phoneNumber').val();
		$.extend(parent.erase_data, erase_data);
		window.location.href="/html/main.html";
		return false;
	});

	$('.submit').bind('click',function() {

		parent.erase_data = {};
		erase_data.phoneNumber = $('.phoneNumber').val();

		$.extend(parent.erase_data, erase_data);
		$.ajax({
			type:'POST',
			data: JSON.stringify(erase_data),
			url: '/rest/erase/erase'
		}).success(function(txt) {
			var code = txt.code || '500';
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
