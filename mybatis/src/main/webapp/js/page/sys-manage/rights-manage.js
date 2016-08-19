$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'role/showQueryRole',
        		columns: [{
                    field: 'state',
                    checkbox: true,
                    title: '选择'
                }, {
                    field: 'name',
                    title: '角色名称'
                }, {
                    field: 'status',
                    title: '是否启用'
                }, {
                    field: 'memo',
                    title: '描述'
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
