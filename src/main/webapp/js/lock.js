$(document).ready(function() {

	//ui

	var lock_data = {};
		
	$.extend(lock_data,parent.lock_data);

	//ui
	$('.phoneNumber').val(lock_data['phoneNumber'] || '');
	$('.msg').val(lock_data['msg'] || '');

	//event
	$('.phoneNumber').bind('click',function() {
		//validation ...
		lock_data.phoneNumber = $(this).val();
		return false;
	});
	//event
	$('.msg').bind('click',function() {
		//validation ...
		lock_data.msg = $(this).val();
		return false;
	});

	//event
	$('.back').bind('click',function() {
		lock_data.phoneNumber = $('.phoneNumber').val();
		lock_data.msg = $('.msg').val();

		parent.lock_data = {};
		$.extend(parent.lock_data, lock_data);
		//escope 
		window.location.href='/html/main.html';
		return false;
	});
	//event
	$('.submit').bind('click',function() {
		//validation ...
		lock_data.phoneNumber = $('.phoneNumber').val();
		lock_data.msg = $('.msg').val();
		
		parent.lock_data =  {};
		$.extend(parent.lock_data, lock_data);
		
		$.ajax({
			type:'POST',
			path: '/rest/lock/lock/lock',
			data: JSON.stringify(lock_data),
			contentType:'application/json'
		}).success(function(txt){
			var code = txt.code || '500';
			if( code == '200') {
				alert('ok');
				//need escape url
				window.location.href='/html/main.html';
			}
		}).error(function(){
			console.log("error");
		});
		return false;
	});
});
