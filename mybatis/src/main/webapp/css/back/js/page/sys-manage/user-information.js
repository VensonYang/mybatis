//pageJS
(function(){
	$("#searche").bind("click", function() {
        Comm.queryData();
    })
    $("#delete").bind("click", function() {
        Comm.deleteObject('userManage/deleteUser', '.table');
    })
    $('#user').on('hidden.bs.modal', function(e) {
        $('#saveForm')[0].reset();
        if ($('#saveForm select').length > 0) {
            $('#saveForm select').select2('val', '');
        }
    })
    $("#modify").bind("click", function() {
        var obj = Comm.getSelectedRow($('.table'));
        if (obj) {
            Comm.setData("userManage/getUser", obj.id);
            $('#add-user').dialog({
	            title: '用户信息',
	            width: 480,
	            height: 360,
	            resizable: false,
	            draggable: false,
	            modal: true,
	            zIndex: 1000,
	            onClose: function () {
	            	Comm.resetForm("add-user");
	            }
        	})
            $("#saveId").off("click").on("click", function() {
            	if(!Comm.checkError()){
            		var params=Comm.getParameters();
            		params.id=obj.id;
            		if(window.courseid){
	            		params.courseIds=window.courseid;
            		}
            		Comm.saveAjax("userManage/modifyUser", params, "refresh");
            		$("#add-user").dialog('close');
            	}
            })
        }
    })
    $("#add").bind("click", function() {
        $("input[name='password']").val('123456')
        $('#add-user').dialog({
	            title: '用户信息',
	            width: 480,
	            height: 360,
	            resizable: false,
	            draggable: false,
	            modal: true,
	            zIndex: 1000,
	            onClose: function () {
	            	$("#courseName").text('');
	            	Comm.resetForm("add-user");
	            }
        	})
        $("#saveId").off("click").on("click", function() {
        	if(!Comm.checkError()){
        		if(!window.courseid){
        			Comm.alert("请选择用户课程");
        		}
        		var params=Comm.getParameters();
        		params.courseIds=window.courseid;
        		Comm.saveAjax("userManage/addUser", params, "refresh");
        		$("#add-user").dialog('close');
        	}
        })
    })
	
})()

$(function () {
    $('.table').bootstrapTable(
        Comm.bootstrapTableParams({
            url: 'userManage/showQueryUser',
            columns: [{
                field: 'state',
                checkbox: true,
                title: '选择'
            }, {
                field: 'userName',
                title: '姓名'
            }, {
                field: 'userAccount',
                title: '帐号'
            }, {
                field: 'creator',
                title: '创建人'
            }, {
                field: 'modifyTime',
                title: '修改时间'
            }, {
                field: 'createTime',
                title: '创建时间'
            }, {
                field: 'sex',
                title: '性别'
            }, {
                field: 'modifier',
                title: '修改人'
            }, {
                field: 'status',
                title: '状态'
            }, {
                field: 'memo',
                title: '备注'
            }]
        })
    );
    $('.select-more').click(function () {
    	var kemu=$("select[name='kemu']").val()
    	if(!kemu){
    		Comm.alert("请先选择科目！");
    		return;
    	}
        $('#assign-chapter').dialog({
            title: '选择课程',
            width: 480,
            height: 400,
            resizable: false,
            draggable: false,
            modal: true,
            zIndex: 1010
        });
        $('#chapter-tree').tree({
            lines: true,
            data: [],
            checkbox: true
        })
        Comm.refreshTree('#chapter-tree', "course/getAllCourseBySubject", {subject:kemu});
    });
    $('#assign-chapter .btn-blue').click(function(){
//    	var checkData=$('#chapter-tree').tree('getChecked');
//    	var course=[];
//    	$.each(checkData,function(i,e){
//    		if(e.children.length>0){
//    			return;
//        	}else{
//        		course.push(e.origin.id)
//        		$('#chapter-tree').tree('uncheck',e.target);
//        	}
//        })
//        window.courseid=course.join(',');
    	var course=Tree.getCheckData('#chapter-tree', 'id');
    	var courseName=Tree.getCheckData('#chapter-tree', 'text');
    	$("#courseName").text(courseName);
    	window.courseid=course.join(',');
    	$("#assign-chapter").dialog('close');
    })
    $(window).resize(function () {
        $('table').bootstrapTable('resetWidth');
    });
    $('.close-dialog').click(function () {
        $(this).parent().parent().dialog('close');
    })
});
