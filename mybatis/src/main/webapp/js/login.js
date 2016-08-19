$(function() {
	$('.input-container input').each(function () {
		$(this).focus(function(){
			if ($(this).prev().hasClass('user')) {
				$(this).prev().removeClass('user').addClass('user-a');
			}else if($(this).prev().hasClass('password')){
				$(this).prev().removeClass('password').addClass('password-a');
			}else if($(this).prev().hasClass('key')){
				$(this).prev().removeClass('key').addClass('key-a');
			}
		});
		$(this).blur(function() {
			if ($(this).prev().hasClass('user-a')) {
				$(this).prev().removeClass('user-a').addClass('user');
			}else if($(this).prev().hasClass('password-a')){
				$(this).prev().removeClass('password-a').addClass('password');
			}else if($(this).prev().hasClass('key-a')){
				$(this).prev().removeClass('key-a').addClass('key');
			}
		})
	})
})