$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'schoolTerm/showQuerySchoolTerm',
        		 columns: [{
        	            field: 'state',
        	            checkbox: true,
        	            title: '选择'
        	        }, {
        	            field: 'id',
        	            title: '学期编号'
        	        }, {
        	            field: 'termname',
        	            title: '学期名称'
        	        }, {
        	            field: 'termyear',
        	            title: '年份'
        	        }, {
        	            field: 'termno',
        	            title: '上/下学期'
        	        }, {
        	            field: 'startdate',
        	            title: '开始日期'
        	        }, {
        	            field: 'enddate',
        	            title: '截止日期'
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
