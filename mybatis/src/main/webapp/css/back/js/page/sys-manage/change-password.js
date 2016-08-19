//pageJS
(function(){
	$("#modify").click(function(){
		var oldPas=$.trim($("#oldpassword").val());
		var newPas=$.trim($("#newpassword").val());
		var conPas=$.trim($("#confirmpassword").val());
		if(oldPas==""){
			$("#oldpassword").tips({msg :"原密码不能为空" });
			return;
		}else if(newPas==""){
			$("#newpassword").tips({msg :"新密码不能为空" });
			return;
		}else if(conPas==""){
			$("#confirmpassword").tips({msg :"确认密码不能为空" });
			return;
		}else if(oldPas.length<6||conPas.length<6||newPas.length<6){
			$(this).tips({msg :"密码长度不能小于6" });
		}else if(newPas!=conPas){
			$(this).tips({msg :"两次密码输入不一致" });
			return;
		}else{
			$.post(baseUrl+"user/modifyPassword",{'oldPassword':oldPas,'password' : newPas},function(data){
				if(data.status==0){
					alert("密码修改成功！");
				}else{
					$("#modify").tips({msg :data.message });
				}
			});
		}
	});
})()