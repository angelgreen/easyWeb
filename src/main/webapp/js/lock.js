$(document).ready(function() {

	//ui

	var data = {};
		
	$.extend(data,parent.data);

	//ui
	$('.phoneNumber').val(data['phoneNumber'] || '');
	$('.msg').val(data['msg'] || '');

	//event
	$('.phoneNumber').bind('click',function() {
		//validation ...
		data.phoneNumber = $(this).val();
		return;
	});
	//event
	$('.msg').bind('click',function() {
		//validation ...
		data.msg = $(this).val();
		return;
	});

	//event
	$('.back').bind('click',function() {
		data.phoneNumber = $('.phoneNumber').val();
		data.msg = $('.msg').val();

		parent.data = {};
		$.extend(parent.data, data);
		//escope 
		window.location.href='/html/main.html';
		return;
	});
	//event
	$('.submit').bind('click',function() {
		//validation ...
		$.ajax( {
			type:'POST',
			path: '/rest/lock',
			data: JSON.stringify(data),
			contentType
		}).success(function(txt){
			var code = txt.code || '500';
			if( code == '200') {
				alert('ok');
				$.extend(parent.data,data);
				//need escape url
				window.location.href='/html/main.html';
			}
		}).error(function(){
			alert('error');
		});
		return;
	});
});
