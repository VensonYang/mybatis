$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'querysta/getStaTeacherEnroll',
        		columns: [{
                    field: 'id',
                    title: '教师编号'
                }, {
                    field: 'name',
                    title: '教师姓名'
                }, {
                    field: 'addNum',
                    title: '加分人次'
                }, {
                    field: 'subNum',
                    title: '扣分人次'
                }, {
                    field: 'addPoint',
                    title: '加分分数'
                }, {
                    field: 'subPoint',
                    title: '扣分分数'
                }, {
                    field: 'couPoint',
                    title: '总分'
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
