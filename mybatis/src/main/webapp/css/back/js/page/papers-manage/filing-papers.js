$(function () {
	var userInfo = Comm.get("userInfo");
	$("input[name='createby']").val(userInfo.userName);
	$("input[name='kemu']").val(userInfo.kemu);
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'pigeonhole/showQueryPigeonhole',
        		columns: [{
                    field: 'check',
                    title: '选择',
                    checkbox: true
                }, {
                    field: 'name',
                    title: '试卷名称'
                }, {
                    field: 'exampapertype',
                    title: '试卷类型'
                }, {
                    field: 'kemu',
                    title: '科目'
                }, {
                    field: 'course',
                    title: '课程'
                }, {
                    field: 'createby',
                    title: '创建人'
                }, {
                    field: 'createdate',
                    title: '创建时间'
                }, {
                    field: 'remark',
                    title: '备注'
                }, {
                    field: 'operating',
                    title: '操作'
                }]
        	})
    			
    
    );
    $('.table').on('click', '.filing-papers-link', function () {
//    	var id = $(this).data('id');
    	var index=$(this).parent("td").parent("tr").data("index");
    	var row=$('.table').bootstrapTable('getData')[index];
    	var url=row.url;
    	var ext=url.substring(url.lastIndexOf("."),url.length);
    	var downName=row.name+ext;
    	Comm.postByForm("attachment/attachmentDownload", {fileName:row.url,downName:downName});
    })
    $('.tool-add').click(function () {
        $('#add').dialog({
            width: 510,
            height: 400,
            title: '试卷详细参数',
            zIndex: 1000,
            onClose: function () {
                Comm.resetForm("add");
            }
        })
        if (!$('#add select').eq(0).hasClass('select2-hidden-accessible')) {
            $('#add select').select2();
        }
    });
    $('.close-dialog').click(function () {
        $(this).parent().parent().dialog('close');
    });
    $('.start-time,.end-time,.create-time').datepicker({
        changeYear:true,
        changeMonth:true,
        showButtonPanel:true
    });
    $(".create-time").datepicker("setDate", new Date());
    $(".attachment").change(function(i,e){
		Comm.uploadFile('word',excelResponse);
		function excelResponse(data,statusText){
			if(data.status==0){
				var rows=data.rows;
				var url=Comm.getUrlParam('url', rows);
				var fileNames=Comm.getUrlParam('fileNames', rows);
				$('input[name="fileurl"]').val(url);
				$('input[name="filename"]').val(fileNames);
	  		}else{
	  			Comm.alert("导入出错:"+data.message, "error");
	  		}
		}
	})
	$("#add .btn-blue").click(function(){
		if(!Comm.checkError('add')){
			var params=Comm.getParameters('add');
			Comm.saveAjax("pigeonhole/addPigeonhole", params,"refresh");
			$("#add").dialog('close');
		}
	})
	$(".tool-delete").click(function() {
            Comm.deleteObject("pigeonhole/deletePigeonhole", ".table");
        })
    $("#searche").bind("click", function() {
        Comm.queryData();
    })
    
})
