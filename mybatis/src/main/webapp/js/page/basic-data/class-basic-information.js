$(function () {
    $('.table').bootstrapTable(
		Comm.bootstrapTableParams({
    		url:'tClass/showQueryTClass',
    		columns: [{
    	            field: 'state',
    	            checkbox: true,
    	            title: '选择'
    	        }, {
    	            field: 'id',
    	            title: '班级编号'
    	        }, {
    	            field: 'grade',
    	            title: '年级'
    	        }, {
    	            field: 'classname',
    	            title: '班级名称'
    	        }, {
    	            field: 'status',
    	            title: '状态'
    	        }, {
    	            field: 'classteacher',
    	            title: '班主任'
    	        }, {
    	            field: 'classnum',
    	            title: '班级人数'
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
