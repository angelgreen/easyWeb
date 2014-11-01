$(document).ready(function(){
	//ui

		
	//event
	$('#list .lock').bind('click',function() {
	//go to 
	window.location.href='/html/lock.html';
	return false;
	});

	$('#list .erase').bind('click',function() {
		//go to 
		window.location.href='/html/erase.html';
		return false;
	});

	$('#list .ring').bind('click',function() {
		window.location.href='/html/ring.html';
		return false;
	});

	$('#list .findpwd').bind('click',function() {
		window.location.href='/html/findpwd.html';
		return false;
	});
});
