$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'querysta/getStaDataByStudent',
        		 columns: [{
        	            field: 'eventdate',
        	            title: '发生时间'
        	        }, {
        	            field: 'grade',
        	            title: '年级'
        	        }, {
        	            field: 'classname',
        	            title: '班级名称'
        	        }, {
        	            field: 'studentname',
        	            title: '学生姓名'
        	        }, {
        	            field: 'eventtype',
        	            title: '事件分类'
        	        }, {
        	            field: 'point',
        	            title: '分数'
        	        }, {
        	            field: 'status',
        	            title: '教育情况'
        	        }, {
        	            field: 'medsc',
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
