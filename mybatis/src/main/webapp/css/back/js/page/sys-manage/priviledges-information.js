//pageJS
(function(){
	//查询
	   $("#searche").bind("click",function(){
	   	 Comm.queryData();
	   })
	   //删除
	   $("#delete").bind("click",function(){
	   	Comm.deleteObject('priviledges/deletePriviledges','.table');
	   })
	   //重置数据
	    $('#priviledges').on('hidden.bs.modal', function (e) {
	    	$('#saveForm')[0].reset();
	        if ($('#saveForm select').length > 0) {
	            $('#saveForm select').select2('val', '');
	        }
	    })
	    //修改
	   $("#modify").bind("click",function(){
		var obj=Comm.getSelectedRow($('.table'));
	 	if(obj){
	 		 Comm.setData("priviledges/getPriviledges",obj.id);
	 		 $('#priviledges').modal({backdrop: false,show:true})
	    		 $("#saveId").off("click").on("click",function(){
	    			 Comm.fastModifyAjax("priviledges/modifyPriviledges", obj.id, "refresh");
	    		 })
	    	 }
	    })
	   	//增加
	    $("#add").bind("click",function(){
	   		 $('#priviledges').modal({backdrop: false,show:true})
	   		 $("#saveId").off("click").on("click",function(){
	   			Comm.fastSaveAjax("priviledges/addPriviledges", "refresh");
	   		 })
	    })
	
})()

$(function(){
	$('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'priviledges/showQueryPriviledges',
        		columns: [{
                    field: 'state',
                    checkbox: true,
                    title: '选择'
                }, {
                    field: 'id',
                    title: 'ID'
                }, {
                    field: 'name',
                    title: '权限名称'
                }, {
                    field: 'url',
                    title: '权限管理地址'
                }, {
                    field: 'pid',
                    title: '父节点'
                }, {
                    field: 'icon',
                    title: '图标'
                }, {
                    field: 'status',
                    title: '是否启用'
                }]
        	})	
    	);
	$(window).resize(function () {
	    $('table').bootstrapTable('resetWidth');
	});
})