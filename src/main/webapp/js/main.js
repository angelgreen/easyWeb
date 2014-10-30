$(document).ready(function(){
	//ui
	
	//event
	$('#list .lock').bind('click',function() {
	//go to 
	window.location.href='/html/lock.html';
	return;
});

	$('#list .erase').bind('click',function() {
		//go to 
		window.location.href='/html/erase.html';
		return;
	});

	$('#list .ring').bind('click',function() {
		window.location.href='/html/ring.html';
		return;
	});
	$.bind('beforeunload',function() {
		return "are you leave";
	});
});
