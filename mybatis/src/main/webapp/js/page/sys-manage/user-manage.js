$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'user/query',
        		columns: [ {
                	field: 'state',
                    checkbox: true,
                    title: '选择'
                }, {
                    field: 'id',
                    title: 'ID'
                }, {
                    field: 'account',
                    title: '账号'
                }, {
                    field: 'userName',
                    title: '姓名'
                }, {
                    field: 'creator',
                    title: '创建人'
                }, {
                    field: 'createTime',
                    title: '创建时间'
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
