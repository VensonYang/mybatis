$(function(){
	var userInfo=Comm.get("userInfo");
	$('#layout').layout({
		fit: true
	});
	var courses=Comm.getData("course/getAllCourseBySubject", {subject:userInfo.kemu}, true);
	var cou=[];
	$.each(courses.children,function(i,e){
		cou.push({"id":e.origin.id,"text":e.text});
	})
	$("select[name='course']").select2({data:cou});
	$("select[name='course']").change(function(){
		var course=$(this).val();
		if(course){
			var option={};
			option.data=Comm.getData("queryStatistics/statisticsQuestionByCourseGroupByCreater", {id:course}, true);
			var data=[];
			$.each(option.data,function(i,e){
				data.push({name:e.name,amount:e.data[0]});
			})
			$('.table').bootstrapTable('load', data);
			$('#questions-amount-charts').highcharts(Comm.HighChartBar(option));
		}
	})
	$('.table').bootstrapTable({
		cache: false,
		columns: [{
			field: 'name',
			title: '录题人'
		}, {
			field: 'amount',
			title: '出题量'
		}],
		data: []
	});
})