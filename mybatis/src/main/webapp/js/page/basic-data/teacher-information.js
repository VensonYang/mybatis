$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'teacher/showQueryTeacher',
        		 columns: [{
        	            field: 'state',
        	            checkbox: true,
        	            title: '选择'
        	        }, {
        	            field: 'id',
        	            title: '教师编号'
        	        }, {
        	            field: 'name',
        	            title: '教师姓名'
        	        }, {
        	            field: 'sex',
        	            title: '性别'
        	        }, {
        	            field: 'level',
        	            title: '职务类别'
        	        }, {
        	            field: 'classname',
        	            title: '负责班级'
        	        }, {
        	            field: 'course',
        	            title: '科目'
        	        }, {
        	            field: 'status',
        	            title: '状态'
        	        }, {
        	            field: 'lastlogin',
        	            title: '最后登录时间'
        	        }]
        	})
    	);
});
$(window).load(function () {
    //窗口宽度变化时 改变bootstrap table宽度
    $(window).resize(function () {
        $('.table').bootstrapTable('resetWidth');
    });
});
