var um = UM.getEditor('myEditor');
um.addListener('blur',function(){
	$("input[name='descr']").val(um.getContent());
});
$(function () {
	var userInfo=Comm.get("userInfo");
    $('.p100>.panel-body').css({ height: parent.window.$('.main').height() - 39 });
    $('#tab').tabs({
        fit: true,
        tabWidth: 80
    });
    $('select[name="flag"]').select2({
	    allowClear: false,
	    minimumResultsForSearch: Infinity
    });
    //设置dialog的默认值
    $.extend($.fn.dialog.defaults, {
        resizable: false,
        draggable: false,
        modal: true
    });
    //题目类型的table
    $('.questions-type').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'courseAttribute/showQueryCourseAttribute',
        		columns: [{
                    field: 'check',
                    title: '选择',
                    checkbox: true
                }, {
                    field: 'code',
                    title: '题型编号'
                }, {
                    field: 'name',
                    title: '题型名称'
                }, {
                    field: 'kemu',
                    title: '科目'
                }, {
                    field: 'descr',
                    title: '描述'
                }, {
                    field: 'remark',
                    title: '备注'
                }],
                queryParam:{
                	other1:0
                }
        	})	
       );
    $('.difficulty').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'courseAttribute/showQueryCourseAttribute',
        		columns: [{
        	            field: 'check',
        	            title: '选择',
        	            checkbox: true
        	        }, {
        	            field: 'code',
        	            title: '编号'
        	        }, {
        	            field: 'name',
        	            title: '难度'
	        	    }, {
	    	            field: 'kemu',
	    	            title: '科目'
	    	        }],
                queryParam:{
                	other1:1
                }
        	})
        );
    $('.claim').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'courseAttribute/showQueryCourseAttribute',
        		 columns: [{
        	            field: 'check',
        	            title: '选择',
        	            checkbox: true
        	        }, {
        	            field: 'name',
        	            title: '要求'
        	        }, {
        	            field: 'kemu',
        	            title: '科目'
        	        }],
                queryParam:{
                	other1:3
                }
        	})
        );
    $('.grade').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'courseAttribute/showQueryCourseAttribute',
        		columns: [{
                    field: 'check',
                    title: '选择',
                    checkbox: true
                }, {
                    field: 'name',
                    title: '等级'
                }, {
                    field: 'kemu',
                    title: '科目'
                }],
                queryParam:{
                	other1:4
                }
        	})
        );
    $('.paper-type').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'courseAttribute/showQueryCourseAttribute',
        		columns: [{
                    field: 'check',
                    title: '选择',
                    checkbox: true
                }, {
                    field: 'code',
                    title: '试卷类型编号'
                }, {
                    field: 'name',
                    title: '试卷类型名称'
                }, {
                    field: 'kemu',
                    title: '科目'
                }, {
                    field: 'remark',
                    title: '备注'
                }],
                queryParam:{
                	other1:5
                }
        	})
        );
    		
    
    $('.paper-style').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'courseAttribute/showQueryCourseAttribute',
        		columns: [{
                    field: 'check',
                    title: '选择',
                    checkbox: true
                }, {
                    field: 'code',
                    title: '试卷类型编号'
                }, {
                    field: 'name',
                    title: '试卷类型名称'
                }, {
                    field: 'kemu',
                    title: '科目'
                }],
                queryParam:{
                	other1:6
                }
        	})
        );
    $('.paper-header').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'courseAttribute/showQueryCourseAttribute',
        		columns: [{
        	            field: 'check',
        	            title: '选择',
        	            checkbox: true
        	        }, {
        	            field: 'code',
        	            title: '格式编号'
        	        }, {
        	            field: 'name',
        	            title: '格式名称'
        	        }, {
        	            field: 'kemu',
        	            title: '科目'
        	        }, {
        	            field: 'descr',
        	            title: '格式文档'
        	    }],
                queryParam:{
                	other1:7
                }
        	})
        );
    $('.select-more').click(function () {


    });
    $('.close-dialog').click(function(){
        $(this).parent().parent().dialog('close');
    })
    //增加
     $('.comm-add').click(function () {
    	var div=$(this).parent().parent().parent().parent();
    	var idName=div.attr("id");
    	if(idName=="paper-header"){
    		if($("."+idName).bootstrapTable('getData').length>=1){
    			Comm.alert("卷头说明数量不能大于1");
    			return;
    		}
    	}
    	var width=div.data('width');
    	var height=div.data('height');
    	var title=div.data('title');
    	$('#add-'+idName).dialog({
            title: title,
            width: width,
            height: height,
            zIndex: 1000,
            onClose: function () {
            	Comm.resetForm("add-"+idName);
            	um.setContent('');
            },onOpen:function(){
            	$("select[name='kemu']").val(userInfo.kemu).trigger('change');
            }

        });
    	$('#add-'+idName+' .btn-blue').off("click").on("click",function(){
        	if(!Comm.checkError("add-"+idName)){
        		var params=Comm.getParameters("add-"+idName);
        		Comm.saveAjax({url:"courseAttribute/addCourseAttribute",async:false,params:params});
        		$('#add-'+idName).dialog('close');
        		$("."+idName).bootstrapTable('refresh');
        	}
        })
    })
    //修改
    $('.comm-modify').click(function () {
    	var div=$(this).parent().parent().parent().parent();
    	var idName=div.attr("id");
    	var width=div.data('width');
    	var height=div.data('height');
    	var title=div.data('title');
    	var row=Comm.getSelectedRow($("."+idName));
    	if(!row){
    		Comm.alert("请先选择要修改的行！");
    		return;
    	}
    	Comm.setData("courseAttribute/getCourseAttribute", row.id, "add-"+idName);
    	if(row.descr){
    		um.setContent(row.descr);
    	}
    	$('#add-'+idName).dialog({
    		title: title,
            width: width,
            height: height,
            zIndex: 1000,
            onClose: function () {
            	Comm.resetForm("add-"+idName);
            	um.setContent('');
            }
        });
    	$('#add-'+idName+' .btn-blue').off("click").on("click",function(){
        	if(!Comm.checkError("add-"+idName)){
        		var params=Comm.getParameters("add-"+idName);
        		params.id=row.id;
        		Comm.saveAjax({url:"courseAttribute/modifyCourseAttribute",async:false,params:params});
        		$('#add-'+idName).dialog('close');
        		$("."+idName).bootstrapTable('refresh');
        	}
        })
    })
    //删除
    $('.comml-delete').click(function () {
    	var idName=$(this).parent().parent().parent().parent().attr("id");
        Comm.deleteObject("courseAttribute/deleteCourseAttribute", "."+idName);
    });
    
    //
    $('#paper-style .tool-add').click(function () {
    	$('input[name="fileName"]').data("require","true");
    	$('#add-paper-style').dialog({
            title: '设置',
            width: 481,
            height: 264,
            zIndex: 1000,
            onClose: function () {
            	Comm.resetForm("add-paper-style");
            	$("#fileName").text("");
            },onOpen:function(){
            	$("select[name='kemu']").val(userInfo.kemu).trigger('change');
            }
    	});
    	$('#add-paper-style .btn-blue').off("click").on("click",function(){
        	if(!Comm.checkError("add-paper-style")){
        		var options = {
            	        success: handle, // 提交后的回调函数
            	        url: baseUrl + 'courseAttribute/addPaperStyle', //默认是form的action，如果申明，则会覆盖
            	        type: "POST", // 默认值是form的method("GET" or "POST")，如果声明，则会覆盖
            	        dataType: "json", // html（默认）、xml、script、json接受服务器端返回的类型
            	        data:{priviledgesID:priviledgesID}
            	}
        		$("#uploadForm").ajaxSubmit(options);
        		function handle(data){
        			if(data.status==0){
        				$('#add-paper-style').dialog('close');
                		$(".paper-style").bootstrapTable('refresh');
        			}else{
        				Comm.alert(data.message, "error");
        			}
        		}
        		
        	}
        })
    });
    $('#paper-style .tool-modify').click(function () {
    	$('input[name="fileName"]').removeData("require");
    	var row=Comm.getSelectedRow($(".paper-style"));
    	if(!row){
    		Comm.alert("请先选择要修改的行！");
    		return;
    	}
    	Comm.setData("courseAttribute/getCourseAttribute", row.id, "add-paper-style");
    	$('#add-paper-style').dialog({
            title: '设置',
            width: 480,
            height: 360,
            zIndex: 1000,
            onClose: function () {
            	Comm.resetForm("add-paper-style");
            	$('input[name="fileName"]').val("");
            }
        });
    	$('#add-paper-style .btn-blue').off("click").on("click",function(){
        	if(!Comm.checkError("add-paper-style")){
        		var options = {
            	        success: handle, // 提交后的回调函数
            	        url: baseUrl + 'courseAttribute/modifyPaperStyle', //默认是form的action，如果申明，则会覆盖
            	        type: "POST", // 默认值是form的method("GET" or "POST")，如果声明，则会覆盖
            	        dataType: "json", // html（默认）、xml、script、json接受服务器端返回的类型
            	        data:{id:row.id,priviledgesID:priviledgesID}
            	}
        		$("#uploadForm").ajaxSubmit(options);
        		function handle(data){
        			if(data.status==0){
        				$('#add-paper-style').dialog('close');
                		$(".paper-style").bootstrapTable('refresh');
        			}else{
        				Comm.alert(data.message, "error");
        			}
        		}
        		
        	}
        })
    	
    	    
    });
    $('#paper-style .tool-delete').click(function () {
    	Comm.deleteObject("courseAttribute/deletePaperStyle", ".paper-style");
    });
    $('input[name="attachment"]').off("change").on("change",function(){
    	if (!$(this).fileTypeJudge("word"))
            return;
    		$('input[name="fileName"]').val($(this).val());
    })
    
})
