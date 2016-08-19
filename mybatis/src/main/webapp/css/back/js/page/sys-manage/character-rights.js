//pageJS
(function(){
	//查询
	$("#searche").bind("click",function(){
   	 Comm.queryData();
   })
   //删除
   $("#delete").bind("click",function(){
   	Comm.deleteObject('role/deleteRole','.table');
   })
   	//重置数据
    $('#role').on('hidden.bs.modal', function (e) {
    	$('#saveForm')[0].reset();
        if ($('#saveForm select').length > 0) {
            $('#saveForm select').select2('val', '');
        }
    	addRolePrivliedgesParam=null;
    	clearCheckBox();
    })
	Comm.setPriviledgeMenu();
    //修改
   $("#modify").bind("click",function(){
	   	var obj=Comm.getSelectedRow($('.table'));
   	 	if(obj){
   	 		 Comm.setData("role/getRole",obj.id);
   	 		 $('#role').modal({backdrop: false,show:true})
   	 		 	Comm.setCheckbox("role/getRolePriviledges",obj.id,"#content input[name='priviledgesID']")
	    		 $("#saveId").off("click").on("click",function(){
	    			 if(!Comm.checkError()){
	    	        		var params=Comm.getParameters();
	    	        		params.id=obj.id;
	    	        		var option={"url":"role/modifyRole","params":params,"async":false}
	    	        		Comm.saveAjax(option);
	    	       			if(addRolePrivliedgesParam){
	    	       				addRolePrivliedgesParam+="&id="+obj.id;
	    	       				Comm.saveAjax("role/addRolePriviledges",addRolePrivliedgesParam,"refresh");
	    	       			}else{
	    	       				$('.modal').modal('hide');
	    	       				$('.table').bootstrapTable('refresh');
	    	       			}
	    	        		
	    	    		}
	    		 })
    	 }
    })
    //增加
    $("#add").bind("click",function(){
   		 $('#role').modal({backdrop: false,show:true})
   		 $("#saveId").off("click").on("click",function(){
   			if(!Comm.checkError()){
        		var params=Comm.getParameters();
       			var option={"url":"role/addRole","params":params,"handle":"returnValue","async":false}
       			Comm.saveAjax(option);
        		if(addRolePrivliedgesParam){
        			if(window.primaryKey){
        				addRolePrivliedgesParam+="&id="+window.primaryKey;
        				Comm.saveAjax("role/addRolePriviledges",addRolePrivliedgesParam,"refresh");
        			}
        		}else{
        			$('.modal').modal('hide');
        			$('.table').bootstrapTable('refresh');
        		}
        		
    		}
   		 })
    })
	var addRolePrivliedgesParam;
	$("#save").click(function(){
		addRolePrivliedgesParam=Comm.getCheckedData("input[name='priviledgesID']:checked", "priviledgesIds");
		$('#assign-rights').modal('hide');
	})
	function clearCheckBox(){
		$("#content :checkbox").each(function(i,e){
			this.checked=false;
		})
	}
	
})()

$(function(){
	$('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'role/showQueryRole',
        		columns: [{
                    field: 'state',
                    checkbox: true,
                    title: '选择'
                }, {
                    field: 'name',
                    title: '角色名称'
                }, {
                    field: 'status',
                    title: '是否启用'
                }, {
                    field: 'memo',
                    title: '描述'
                }]
        	})	
    	);
	$(window).resize(function () {
	    $('table').bootstrapTable('resetWidth');
	});
})