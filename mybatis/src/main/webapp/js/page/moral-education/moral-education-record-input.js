$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'moralStudent/showQueryMoralStudent',
        		columns: [{
                    field: 'state',
                    checkbox: true,
                    title: '选择'
                }, {
                    field: 'eventdate',
                    title: '发生时间'
                }, {
                    field: 'grade',
                    title: '年级'
                }, {
                    field: 'classname',
                    title: '班级名称'
                }, {
                    field: 'studentname',
                    title: '学生姓名'
                }, {
                    field: 'moraltype',
                    title: '德育规范类别'
                }, {
                    field: 'moralrule',
                    title: '德育规范条款'
                }, {
                    field: 'point',
                    title: '分数'
                }, {
                    field: 'status',
                    title: '教育情况'
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
