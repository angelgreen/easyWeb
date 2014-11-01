$(document).ready(function() {
	var ring_data = {};

	$.extend(ring_data,parent.ring_data);
	//ui

	alert(JSON.stringify(ring_data));

	$('.phoneNumber').val(ring_data['phoneNumber'] || "");

	$('.back').bind('click',function() {
		ring_data['phoneNumber'] = $('.phoneNumber').val();

		parent.ring_data = {};

		$.extend(parent.ring_data, ring_data);
		window.location.href='/html/main.html';
		return false;
	});

	$('.submit').bind('click',function() {

		ring_data['phoneNumber'] = $('.phoneNumber').val();
		parent.ring_data = {};

		$.extend(parent.ring_data, ring_data);
		
		$.ajax({
			type:'POST',
			data: JSON.stringify(ring_data),
			url: '/rest/ring/ring'
		}).success(function(txt) {
			var code = txt['code'] || '500';
			if(cpde == '200') {
				alert("ok");
			}else {
				alert("error");
			}
		}).error(function(xhr) {
			alert('error');
		});

		return false;
	});

});
