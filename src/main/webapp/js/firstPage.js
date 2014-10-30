$(document).ready(function() {
	$('#window').load('/html/window.html',function() {
		var win_ui = new win();
		win_ui.init();
		$('.btn_next').click(function() {
			win_ui.showPop();
		});
	});
});
