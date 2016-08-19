var baseUrl=window.location.protocol+"//"+window.location.host+"/dyxtDI/";
//前台跳转前缀
var baseUIUrl=window.location.protocol+"//"+window.location.host+"/dyxtUI/";
 

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}
var priviledgesID=getUrlParam("id");
function goTo(url){
	console.log(url+","+priviledgesID);
	location.href=url+"?id="+priviledgesID;
}
/*获取用户页面操作权限
$('#table').on('load-success.bs.table', function () {
	$.ajax({
		   type: "get",
		   url: baseUrl+"user/getUOP",
		   data:{"priviledgesID":priviledgesID},
		   cache: false,
		   success: function(data){
			   if(data.status==0){
				   if(data.rows.iscreate==false){
					   $(".add").hide();
				   }
				   if(data.rows.isdelete==false){
					   $(".delete").hide();
				   }
				   if(data.rows.ismodify==false){
					   $(".modify").each(function(i){
						   $(this).hide();
						});
				   }
			   }else{
				   console.log(data.message);
			   }
		   }
	});
});
*/
function setRole(idTag){
	$.ajax({
		   type: "POST",
		   url: baseUrl+"role/showRole",
		   data:{"priviledgesID":priviledgesID},
		   cache: false,
		   success: function(da){
			   idTag.select2({
				   placeholder: '',
			       allowClear: true,
			       minimumResultsForSearch: Infinity,
				   data: da.rows,
				 });
		   }
			
	});
}
function deleteObject(url,table){
	var $table=$(table);
	var row=$table.bootstrapTable('getSelections');
	console.log(row);
	if(row.length<=0){
		alert("请选择要删除的对象！");
	}else{
		for(var i=0;i<row.length;i++){
			deleteAjax(url,$table,row[i].id);
		}	
	}
}

function modifyObject(url,table){
	var $table=$(table);
	var row=getSelectedRow($table);
	return row;
	
}

function getSelectedRow($table) {
    var index = $table.find('tr.selecttr').data('index');
    return $table.bootstrapTable('getData')[index];
}

function deleteAjax(url,table,ids){
		$.ajax({
		   type: "POST",
		   url: baseUrl+url,
		   data:{id:ids,"priviledgesID":priviledgesID},
		   //async:false,
		   success: function(response){
			  if(response.status==0){
				  table.bootstrapTable('remove', {field: 'id',values: [ids]});
			  }else{
				  alert("错误提示："+response.message);
			  }
		   },error:function(response){
			   	 alert("连接服务器出错，请检查！");
		   }
		});
	}

function saveAjax(url,params,tip){
	params.priviledgesID=priviledgesID;
	console.log(params);
	$.ajax({
		   type: "POST",		
		   url: baseUrl+url,
		   data:params,
		   success: function(response){
			  if(response.status==0){
				  location.href=document.referrer;
			  }else{
				  if(response.message!=null){
					  tip.tips({msg : response.message});
				  }else{
					  tip.tips({msg : "保存失败，请联系管理员！"});
				  }
			  }
		   },error:function(){
			   tip.tips({msg : "网络出错，请检查网络！"});
		   }
		});
}