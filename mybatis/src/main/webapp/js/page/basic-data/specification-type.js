$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'moralType/showQueryMoralType',
        		columns: [{
                    field: 'state',
                    checkbox: true,
                    title: '选择'
                }, {
                    field: 'id',
                    title: '规范类别编号'
                }, {
                    field: 'moraltypename',
                    title: '德育规范条款'
                }, {
                    field: 'createuser',
                    title: '录入用户'
                }, {
                    field: 'createdate',
                    title: '录入日期'
                }, {
                    field: 'modiuser',
                    title: '修改用户'
                }, {
                    field: 'modidate',
                    title: '修改日期'
                }, {
                    field: 'status',
                    title: '状态'
                }, {
                    field: 'remark',
                    title: '备注'
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
