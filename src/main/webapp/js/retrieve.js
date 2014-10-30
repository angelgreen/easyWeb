$(document).ready(function() {

	$.bind('beforeunload',function() {
		return 'are leave';
	});

	$('#main_window').load(function() {
		var height = $(document).height();
		$(this).css({'height':height});
	});
})

