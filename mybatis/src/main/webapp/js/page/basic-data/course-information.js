$(function () {
    $('.table').bootstrapTable(
		Comm.bootstrapTableParams({
    		url:'xtEnumbank/showQueryXtEnumbank',
    		columns: [{
                field: 'state',
                checkbox: true,
                title: '选择'
            }, {
                field: 'enumid',
                title: '科目编号'
            }, {
                field: 'enumvalue',
                title: '科目名称'
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
