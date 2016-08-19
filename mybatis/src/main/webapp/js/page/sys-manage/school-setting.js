$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'',
        		columns: [{
                    field: 'state',
                    checkbox: true,
                    title: '选择'
                }, {
                    field: 'schoolNumber',
                    title: '学校编号'
                }, {
                    field: 'schoolName',
                    title: '学校名称'
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
