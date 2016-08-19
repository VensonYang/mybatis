$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'querysta/getStaClassTotalPoint',
        		columns: [{
                    field: 'eventdate',
                    title: '时间'
                }, {
                    field: 'id',
                    title: '班级编号'
                }, {
                    field: 'classname',
                    title: '班级名称'
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
