$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'user/findAll',
        		columns: [ {
                	field: 'state',
                    checkbox: true,
                    title: '选择'
                }, {
                    field: 'userName',
                    title: '姓名'
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
