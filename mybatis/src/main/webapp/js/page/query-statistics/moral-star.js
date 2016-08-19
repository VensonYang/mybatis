$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'querysta/getQueryMoralStraPerson',
        		columns: [{
                    field: 'name',
                }, {
                    field: 'total',
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
