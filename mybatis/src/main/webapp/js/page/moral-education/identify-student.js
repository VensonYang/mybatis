$(function () {
    $('.table').bootstrapTable(
    	Comm.bootstrapTableParams({
    		url:'studentAppraisal/showQueryStudentAppraisal',
    		columns: [{
                field: 'state',
                checkbox: true,
                title: '选择'
            }, {
                field: 'schoolterm',
                title: '学期名称'
            }, {
                field: 'grade',
                title: '年级'
            }, {
                field: 'classname',
                title: '班级名称'
            }, {
                field: 'studentid',
                title: '学生编号'
            }, {
                field: 'studentname',
                title: '学生姓名'
            }, {
                field: 'appraisal',
                title: '鉴定评语'
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
