$(function() {
	$('#layout').layout({
		fit: true
	});
	$("select[name='kemu']").change(function(){
		var subject=$(this).val();
		if(subject){
			var data=Comm.getData("course/getAllCourseBySubject", {subject:subject}, true);
			var couIds=[];
			$.each(data.children,function(i,e){
				couIds.push(e.origin.id);
			})
			var option={};
			option.data=Comm.getData("queryStatistics/statisticsQuestionByCourse", {id:couIds.join(",")}, true);
			var html=[];
			$.each(option.data,function(i,e){
				html.push('<tr><td>'+e.name+'</td><td>'+e.data[0]+'</td></tr>');
			})
			$('.table tbody').html(html.join(''));
			$('#questions-amount-charts').highcharts(Comm.HighChartBar(option));
		}
	})
	$("select[name='kemu']").val(Comm.get("userInfo").kemu).trigger('change');
})