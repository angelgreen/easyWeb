$(document).ready(function() {
	$("#submit").bind("click",function() {
		ZTE.user =  {};
		ZTE.user['name'] = $('#name').val();
		ZTE.user['password'] = $('#password').val();
		
		console.log(ZTE.user);

		$.ajax({
			type:'POST',
			url: '/rest/test/login',
			data: JSON.stringify(ZTE.user),
			contentType: "application/json",
		}).done(function(data) {
			var code = data['code'] || '200';
			
			ZTE.debug(code);
			
			if(code == '200') {
				$(".container").load("/html/firstPage.html");	
			}else {
			}
		}).error(function() {

			ZTE.debug("error");
		});
	});

	//next page 
	//$(".container").load("/webapp/html/firstPage.html");
});
