$(document).ready(function() {
	//load ...
	$('#window').load('/html/window.html',function() {
	var user = {};
	var win_ui = new win();
	win_ui.init();
	//UI
	//event
	$('.btn_show').bind('click',function() {
		win_ui.showPop();
		return; //no popo
	});
	$(".btn").bind('click',function() {
		var name = $('#name').val();
		var password = $('#password').val();
	
		user.name = name;
		user.password = password;
		if(!/\w+/.test(name)) { alert("name error"); return;}
		if(!/\w+/.test(password)) { alert("password error"); return;};

		$.ajax ({
			type: 'POST',
			url: '/rest/user/login',
			data: JSON.stringify(user),
			contentType:'application/json',
			beforeSend: function() {
			},
			complete: function() {
			},
		}).success(function(data) {
			var code = data['code'] || '500';
			if(code == '200') {
				win_ui.showOverlay();
				window.location.href='/html/retrieve.html';
			}
		}).error(function(XMLHttpRequest) {
			 var code = XMLHttpRequest.status;

			if (code == '404') {
				$('#hide_window .container').load('/html/404.html');	
				win_ui.showPop('#hide_window');
			}
		});
		return;
	});
	});
});
