$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'student/showQueryStudent',
        		columns: [{
                    field: 'state',
                    checkbox: true,
                    title: '选择'
                }, {
                    field: 'id',
                    title: '学生编号'
                }, {
                    field: 'name',
                    title: '学生姓名'
                }, {
                    field: 'sex',
                    title: '性别'
                }, {
                    field: 'classname',
                    title: '班级'
                }, {
                    field: 'phone',
                    title: '联系电话'
                }, {
                    field: 'address',
                    title: '通讯地址'
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
