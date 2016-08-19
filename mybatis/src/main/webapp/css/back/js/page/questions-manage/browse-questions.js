//pageJS
 (function(){
	 $("#searche").bind("click",function(){
	   	 Comm.queryData();
	 })
	 $(".searche").bind("click",function(){
		 Comm.queryData("searchForm1");
	 })
	 $("#saveId").bind("click",function(){
		 if(!Comm.checkError("modify-multiple")){
			 var data=Comm.getParameters("modify-multiple");
			 //查看是否有选择章节
			 if(window.sectionid){
		   			data.knowledge=window.sectionid;
					data.sectionid=window.sectionid;
		   	}
			 for(var i in ids){
				 data.id=ids[i];
				 Comm.saveAjax("itemcontent/modifyItemcontent", data)
			 }
	   		$('#modify-multiple').dialog('close');
		 }
	 })
	  $(".tool-delete").click(function(){
		  Comm.deleteObject('itemcontent/deleteItemcontent','.table');
	  })
	  $(".tool-save").click(function(){
			if(!Comm.checkError("modify")){
		   		var data=Comm.getParameters("modify");
		   		//查看是否有选择章节
		   		if(window.sectionid){
		   			data.knowledge=window.sectionid;
					data.sectionid=window.sectionid;
		   		}
		   		var param=$.extend(window.rowdata,data);
		   		Comm.saveAjax("itemcontent/modifyItemcontent", param,"refresh");
		   		$('#modify').dialog('close');
		   	}
	  })
	  $(".tool-export").click(function(){
		    if (ids.length <= 0)
		        Comm.alert("请勾选要导出的题目！");
		    else {
		    	Comm.postByForm("itemcontent/createPaperByIds",{ids:Comm.uniqueArr(ids).join(',')});
		    	ids=[];
		    	$('.table').bootstrapTable('uncheckAll');
		    }
	  })
	  window.ids=[];
	  $('.table').on('check.bs.table', function (element, row) {
		  ids.push(row.id);
	  });
	  $('.table').on('uncheck.bs.table', function (element, row) {
		  ids=Comm.removeArr(ids,row.id);
	  });
 })()

$(function () {
    $('.table').bootstrapTable(
    		Comm.bootstrapTableParams({
        		url:'itemcontent/showQueryItemcontent',
        		 columns: [{
        		        field: 'check',
        		        title: '选择',
        		        checkbox: true
        		    }, {
        		        field: 'id',
        		        title: '题目编号'
        		    }, {
        		        field: 'question',
        		        title: '题型'
        		    }, {
        		        field: 'content',
        		        title: '内容'
        		    }, {
        		        field: 'difficulty',
        		        title: '难度'
        		    }, {
        		        field: 'createby',
        		        title: '录题人'
        		    }]
        	})	
        );
    $('.table').on('click-row.bs.table', function (element, row) {
       window.rowdata=Comm.getData("itemcontent/getItemcontent",{id:row.id},false);
       var content=rowdata.content.replace(/\n/g,'<br>');
       if(rowdata.answer){
    	   var answer=rowdata.answer.replace(/\n/g,'<br>');
       }
       $("#itemContent").html(content+"<br>答案:"+answer);
    });
    $(window).resize(function () {
        $('.table').bootstrapTable('resetWidth');
    });
    //布局左侧手风琴效果
   $('#accordion').accordion({
       fit: true,
       onSelect: function (title, index) { //选择不同项，控制右侧查询表单的显隐
           if (index === 1 || index === 2) {
               if (!$('#searchForm').hasClass('hidden')) {
                   $('#searchForm').addClass('hidden');
               }
           } else {
               if ($('#searchForm').hasClass('hidden')) {
                   $('#searchForm').removeClass('hidden');
               }
           }
       }
   });
    //整体布局layout
    $('#layout').layout();
    //右侧上下布局layout
    $('#tab0').layout();
    var treeData=[Comm.getData('courseSection/getAllSectionBySubject',{},true)];
    var tree= $('#tree');
    var tree2= $('#tree2');
    tree.tree({
        lines: true,
        data:treeData
    });
    tree2.tree({
    	lines: true,
    	data:treeData
    });
    $('#chapter-tree').tree({
        lines: true,
        data: treeData,
        checkbox:true
    })
    tree.tree({
    	onClick: function(node){
    		var origin=node.origin;
    		//科目
    		if(origin){
    			//课程
    			if(origin.id){
    				//子节点
    				var other1;
    				if(origin.parentid){
    					other1=node.origin.id;
    				}else{
    					var children=tree.tree('getChildren',node.target);
    					var ids=[];
    					$.each(children,function(i,e){
    						ids.push(e.origin.id);
    					})
    					other1=ids.join(',');
    				}
    				$(".table").bootstrapTable('refresh', {
        		        query: {other1:other1}
        		    });
    			}
    		}
    	}
    });
    
    $('.tool-set').click(function(){
    	$('#set').dialog({
    		title:'设置',
    		width:480,
    		height:297,
    		resizable:false,
    		modal:true,
    		zIndex:1000
    	})
    });
    var tree1Data=[Comm.getData('itemcontent/getRecentUpdate',{},false)];
    //手风琴效果中最近更新项中的树
    $('#tree1').tree({
        lines: true,
        data: tree1Data
    });
    $('#tree1').tree({
    	onClick: function(node){
    		$(".table").bootstrapTable('refresh', {
		        query: {id:node.origin.id}
		    });
    	}
    });
    //点击批量修改弹出批量修改dialog
    $('.tool-modify-multiple').click(function () {
	 if (ids.length <= 0)
	        Comm.alert("请勾选要修改的题目！");
	    else {
	    	 $('#modify-multiple').dialog({
	             title: '批量修改试题',
	             width: 480,
	             height: 297,
	             resizable: false,
	             draggable: false,
	             modal: true,
	             zIndex: 1000,
	             onClose: function () {
	            	window.ids=[];
	     			window.sectionid="";
	     	   		$('.table').bootstrapTable('refresh');
	     	   		Comm.resetForm("modify-multiple");
	     	   	    Tree.reset('#chapter-tree');
	             }
	         })
	    }
    });
    $('.tool-modify').click(function(){
    	if(window.rowdata){
    		$('#modify-layout').layout({
                fit:true
            });
            $('#modify-layout-left').layout({
                fit:true
            });
            $('#modify-layout-right').layout({
                fit:true
            })
    		Comm.setFormData(rowdata,"modify");
        	$('#modify').dialog({
        		title:'修改试题',
        		width:1002,
        		height:636,
        		resizable:false,
        		modal:true,
        		zIndex:1000,
                onClose: function () {
    		   		//清空章节
    		   		window.sectionid="";
    		   		Tree.reset('#chapter-tree');
                }
        	})
    	}else{
    		Comm.alert("请点击要修改的行");
    	}
    })
        //点击工具栏的设置 弹出dialog
    $('.tool-set').click(function () {
        $('#set').dialog({
            title: '设置',
            width: 480,
            height: 297,
            resizable: false,
            draggable: false,
            modal: true,
            zIndex: 1000
        })
    })
    $('.close-dialog').click(function () {
            $(this).parent().parent().dialog('close')
        })
        //点击章节后更多弹出选择章节dialog
    $('.select-more').click(function () {
        $('#assign-chapter').dialog({
            title: '选择章节',
            width: 480,
            height: 400,
            resizable: false,
            draggable: false,
            modal: true,
            zIndex: 1010
        });
    })
    $('#assign-chapter .btn-blue').click(function () {
    	window.sectionid=Tree.getCheckData('#chapter-tree', 'id').join(',');
    	$('#assign-chapter').dialog('close');
    });
    $('.start-time,.end-time').datepicker({
        changeYear:true,
        changeMonth:true,
        showButtonPanel:true
    });
    
})
