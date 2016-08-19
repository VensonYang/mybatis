//pageJS
(function(){
    	var baseUrl=window.location.protocol+"//"+window.location.host+"/tkglDI/";
        document.getElementById("verify").src = baseUrl+"get/createVerifyCode";
        $("#verify").click(function(){
        	document.getElementById("verify").src = baseUrl+"get/createVerifyCode?timestamp="
			+ new Date().getTime();
        })
      	//取消保存密码
        function savePassword() {
        		var obj=$("#saveid").get(0);
        		if (!obj.checked) {
        			$("#userAccount").val('');
        			$("#password").val('');
        		}
        	}
         //查看cookie中是否存放用户信息
           $.ajax({
          		type:"post",
          		url:baseUrl+"get/getUserAccount",
          		async:false,
          		success:function(response){
          			if(response.status==0){
          				var loginname = response.rows
          	  		   var password = "sdfdsffsd";
          	  		   if (loginname) {
          		  		   	$("#userAccount").val(loginname);
          		  		   	$("#password").val(password);
          		  		   	$("#saveid").attr("checked", true);
          	  		   }
          			}else{
          		  		   	$("#userAccount").val("");
          		  		   	$("#password").val("");
          		  		   	$("#saveid").attr("checked", false);
          			}
          		}
        })
        $("#saveid").bind("click", function() {
        	savePassword();
        });
        $("#login").bind("click", function() {
        	if(check()){
        		var vuserAccount = $("#userAccount").val();
        		var vpassword = $("#password").val();
        		var visSavePassword=$("#saveid").get(0).checked;
        		var vverifyCode=$("#verifyCode").val();
        					$.ajax({
        						type: "POST",
        						url: baseUrl+'user/login',
        				    	data: {userAccount:vuserAccount,password:vpassword,savePassword:visSavePassword,verifyCode:vverifyCode},
        						cache: false,
        						success: function(data){
        							if(0 == data.status){
        									window.location.href="index.html";
        							}else {
        								var msg  = data.message;
        								$("#login").tips({
        									side : 1,
        									msg : msg,
        									bg : '#FF5080',
        								});
        							}
        						},error:function(){
        							$("#login").tips({
        								side : 1,
        								msg : "网络出错，请检查！",
        								bg : '#FF5080',
        							});
        						}
        					});
        	}
        });

        function check() {
        	if ($("#userAccount").val() == "") {
        		$("#userAccount").tips({
        			side : 2,
        			msg : '用户名不得为空！',
        			bg : '#AE81FF',
        			time : 3
        		});

        		$("#userAccount").focus();
        		return false;
        	} else {
        		$("#userAccount").val(jQuery.trim($('#userAccount').val()));
        	}

        	if ($("#password").val() == "") {
        		$("#password").tips({
        			side : 2,
        			msg : '密码不得为空！',
        			bg : '#AE81FF',
        			time : 3
        		});
        		$("#password").focus();
        		return false;
        	}
        	if ($("#verifyCode").val() == "") {

        		$("#verifyCode").tips({
        			side : 2,
        			msg : '验证码不得为空！',
        			bg : '#AE81FF',
        			time : 3
        		});

        		$("#verifyCode").focus();
        		return false;
        	}
        		return true;
        }
        $(document).keyup(function(event) {
        	if (event.keyCode == 13) {
        		$("#login").trigger("click");
        	}
        });
})() 	



$(function() {
	$('.form-horizontal input').each(function () {
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