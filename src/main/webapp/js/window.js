function win(width,height) {
	this.win_width = $(window).width();
	this.win_height = $(window).height();
	this.width = this.width || this.win_width / 2;
	this.height = this.height || this.win_height / 2;
}

win.prototype.init = function() {
	var scope = this;
	$('.window_close').bind('click',function() {
		scope.hidePop();	
	});
};

win.prototype.showPop = function() {
	this.showOverlay();
	var win = $('#hide_window');
	if(win === undefined) return;
	win.css('top',(this.win_height - this.height) / 2)
		.css('left',(this.win_width - this.width) / 2)
		.css('height',this.height)
		.css('width',this.width);
	win.css('display','block');
};

win.prototype.hidePop = function() {
	this.hideOverlay();
	var win = $('#hide_window');
	if(win === undefined) return;
	win.css('display','none');
};

win.prototype.showOverlay = function() {
	var overlay = $('#overlay');
	if(overlay === undefined) return;
	overlay.css('width',$(window).width() + 10)
		.css('height',$(window).height() + 10)
		.css('display','block');
};

win.prototype.hideOverlay = function() {
	var overlay = $('#overlay');
	if(overlay === undefined) return;
	overlay.css('display','none');
};
