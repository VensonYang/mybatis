$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'querysta/getQueryMoralStraDept',
        		columns: [{
                    field: 'sort',
                    title: '名次'
                }, {
                    field: 'grade',
                    title: '年级'
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
