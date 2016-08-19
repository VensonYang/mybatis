$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'moralRules/showQueryMoralRules',
        		columns: [ {
                    field: 'id',
                    title: '项目序号'
                }, {
                    field: 'rulename',
                    title: '加分项目'
                }, {
                    field: 'moraltypename',
                    title: '分类名称'
                }, {
                    field: 'point',
                    title: '规范对应分值'
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
