$(function() {
	$('#layout,#layout-center,#layout-right').layout({
		fit: true
	});
	var treeData = [Comm.getData('courseSection/getAllSectionBySubject', {}, true)];
	$('.questions-type-data').bootstrapTable({
		cache: false,
		columns: [{
			field: 'question',
			title: '题型'
		}, {
			field: 'amount',
			title: '题量合计'
		}],
		data: []
	})
	$('.difficulty-data').bootstrapTable({
		cache: false,
		columns: [{
			field: 'difficulty',
			title: '难度'
		}, {
			field: 'amount',
			title: '题量合计'
		}],
		data: []
	});
	$('#tree').tree({
		lines:true,
		data:treeData
	})
	var ids=[];
	$('#tree').tree({
        onClick: function (node) {
        	ids=[];
        	if(node.children.length>0){
        		getChild(node)
        	}else{
        		ids.push(node.origin.id);
        	}
        	ids=Comm.uniqueArr(ids).join(',');
        	var data=Comm.getData("queryStatistics/statisticsPaperStruct", {id:ids}, true);
        	$('.difficulty-data').bootstrapTable('load', data.difficulty);
        	$('.questions-type-data').bootstrapTable('load', data.question);
        	var optionD = { data: data.chartD };
            var optionQ = { data: data.chartQ };
        	$('#difficulty-charts').highcharts(Comm.HighChartPie(optionD));
            $('#questions-type-charts').highcharts(Comm.HighChartPie(optionQ));
        }
    });
	function getChild(node){
		$.each(node.children,function(i,e){
			if(e.children.length>0){
				getChild(e)
			}else{
				ids.push(e.origin.id);
			}
		})
	}
})