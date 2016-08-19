//pageJs
(function(){
	//查询
	   $("#searche").bind("click",function(){
	   	 Comm.queryData();
	   })
	   //删除
	   $("#delete").bind("click",function(){
	   	Comm.deleteObject('department/deleteDepartment','.table');
	   })
	   //重置数据
	    $('#department').on('hidden.bs.modal', function (e) {
	    	$('#saveForm')[0].reset();
	        if ($('#saveForm select').length > 0) {
	            $('#saveForm select').select2('val', '');
	        }
	    })
	    //修改
	   $("#modify").bind("click",function(){
 	var obj=Comm.getSelectedRow($('.table'));
 	 	if(obj){
 	 		 Comm.setData("department/getDepartment",obj.id);
 	 		 $('#department').modal({backdrop: false,show:true})
	    		 $("#saveId").off("click").on("click",function(){
	    			 Comm.fastModifyAjax("department/modifyDepartment", obj.id, "refresh");
	    		 })
	    	 }
	    })
	   	//增加
	    $("#add").bind("click",function(){
	   		 $('#department').modal({backdrop: false,show:true})
	   		 $("#saveId").off("click").on("click",function(){
	   			Comm.fastSaveAjax("department/addDepartment", "refresh");
	   		 })
	    })
	
})()

$(function () {
	 $('.table').bootstrapTable(
	    		Comm.bootstrapTableParams({
	        		url:'department/showQueryDepartment',
	        		columns: [{
	                    field: 'state',
	                    checkbox: true,
	                    title: '选择'
	                }, {
	                    field: 'deptNo',
	                    title: '编号'
	                }, {
	                    field: 'name',
	                    title: '名称'
	                }, {
	                    field: 'createTime',
	                    title: '创建时间'
	                }, {
	                    field: 'status',
	                    title: '是否启用'
	                }]
	        	})	
	    	);
    $(window).resize(function () {
        $('table').bootstrapTable('resetWidth');
    });
});
