//global object ...
window.ZTE = {};
window.debug = true;

/**
*  ZTE debug ...
**/
ZTE.debug = function(obj,msg) {
	if(window.debug) {
		if( console === undefined) {
			alert("msg:"+(msg || " ") +"=>"+ JSON.stringify(obj));
		}else {
			console.log("msg:"+(msg || "") +"=>" + JSON.stringify(obj));
		}
	}
}

ZTE.validate_phone = function(obj) {
	obj = obj || "";
	
	var regrex = /\d+/; //more simple ...

	return typeof(obj) === 'string' &&
			regrex.test(obj);
}

ZTE.validate_name = function(obj,success, fail) {
	var regrex = /\w+/;

	var r = obj !== undefined && 
			typeof(obj) === 'string' && 
			regrex.test(obj);
	
	if(r === true){
		if(success !== undefined && typeof(success) === 'function'){
			success(obj);
		}
	}else {
		if(fail !== undefined && typeof(fail) === 'function'){
			fail(obj);
		}
	}

	return r;
}

ZTE.validate_password = function(obj,success, fail) {
	var regrex = /\w+/;
	
	var r = obj !== undefined &&
			typeof(obj) === 'string' &&
			regrex.test(obj);
	if(r) {
		if(success !== undefined && typeof(success) === 'function') {
			success(obj);
		}
	}else {
		if(fail !== undefined && typeof(fail) === 'function') {
			fail(obj);
		}
	}
	return r;
}
