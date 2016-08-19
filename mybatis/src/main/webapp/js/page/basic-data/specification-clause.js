$(function () {
    $('.table').bootstrapTable(
		Comm.bootstrapTableParams({
    		url:'moralRules/showQueryMoralRules',
    		columns: [{
                field: 'state',
                checkbox: true,
                title: '选择'
            }, {
                field: 'id',
                title: '德育规范编号'
            }, {
                field: 'rulename',
                title: '德育规范名称'
            }, {
                field: 'moraltypename',
                title: '德育规范条款'
            }, {
                field: 'point',
                title: '规范对应分值'
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
