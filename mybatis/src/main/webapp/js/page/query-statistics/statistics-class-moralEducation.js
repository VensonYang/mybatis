$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'querysta/getStaDataByTimeAndClass',
        		columns: [{
                    field: 'classname',
                    title: '班级名称'
                }, {
                    field: 'studentid',
                    title: '学生编号'
                }, {
                    field: 'name',
                    title: '学生姓名'
                }, {
                    field: 'addNum',
                    title: '加分次数'
                }, {
                    field: 'addPoint',
                    title: '加分分数'
                }, {
                    field: 'subNum',
                    title: '扣分次数'
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
