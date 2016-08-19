//pageJS
(function(){
	$(".attachment").change(function(i,e){
		Comm.uploadFile('word',excelResponse);
	})
	function excelResponse(data,statusText){
			if(data.status==0){
				Comm.post("parse/preview",htmlResponse,data.rows);
  		}else{
  			Comm.alert("导入出错:"+data.message, "error");
  		}
  	}
	function htmlResponse(data){
		if(data.status==0){
			var name=data.rows;
			var basePath=baseUrl+"upload/html/";
			var imgPath=basePath+name.substring(0,name.lastIndexOf("."));
			$("#previewArea").load(basePath+name);
				var paper=Comm.getData("parse/parseHtml",{fileName:name});
	   			var tree=$("#tree");
				if(paper){
					var treeData=[];
					var isImport=true;
					treeData.push(paper);
	   				tree.tree('loadData',treeData);
	    			$(".mark_tree").parent("span").parent("div").each(function(i,e){
	    		    	var that=$(this);
	    		    	var node = tree.tree('getData', that);
	    		    	var origin=node.origin;
	    		    	if(origin){
	    		    		//重新替换样式
	    					var icon=that.find(".tree-icon");
	    					icon.removeClass("tree-file");
	    					icon.html("<i></i>")
	    					//替换父节点样式
	    					var parent=tree.tree('getParent', that).target;
	    					var parentIcon=$(parent).find(".tree-icon");
	    					parentIcon.removeClass("tree-folder");
	    					parentIcon.removeClass("tree-folder-open");
	    					parentIcon.html("<i></i>")
	    					//如果错误，替换为错误样式
	    		    		if(origin.error){
	    		    			isImport=false;
	    		    			icon.addClass("check-red");
	    		    			parentIcon.addClass("check-red");
	    		    		}else{
	    		    			icon.addClass("check-green");
	    		    			parentIcon.addClass("check-green");
	    		    		}
	    				}
	    		    });
  	    		if(isImport){
  	    			 $('#layout .tool-import').off("click").on("click",function() {
 	  	    	        $('#layout').addClass('hidden');
 	  	    	        $('#layout1').removeClass('hidden');
			  	    	$('#layout1').layout({
			  	              fit: true
			  	        });
			  	          $('#layout1-left').layout({
			  	              fit: true
			  	        });
			  	  	    $('#layout1-center').layout({
			  	  	        fit: true
			  	  	    });
			  	  	    $('#layout1-right').layout({
			  	  	        fit: true
			  	  	    });
 	  	    	     	var tree1=$('#tree1');
 	  	    	     	tree1.tree('loadData',treeData);
  	    			 });
  	    		}else{
  	    			$('#layout .tool-import').off("click").on("click",function() {
  	    				Comm.alert("试卷校验未通过，请按照错误提示修改试卷！");
  	    			});
  	    		}
				}else{
					tree.tree('loadData',[]);
				}
  		}else{
  			Comm.alert("导入出错:"+data.message, "error");
  		}
	}
})()


$(function () {

    $('#layout').layout({
        fit: true
    });
    $('#layout-left').layout({
        fit: true
    });
    var tree = $('#tree');
    var tree1 = $('#tree1');
    tree.tree({
        data: [],
        lines: true
    });
    tree.tree({
        onClick: function (node) {
            showItem(node);
        },
        formatter: function (node) {
            var s = ' <span class="mark_tree"></span>';
            s += node.text;
            return s;
        },
        onExpand: function (node) {
            var target = $(node.target).find(".tree-icon");
            if (target.hasClass('check-red')) {
                target.removeClass("tree-folder-open");
            } else if (target.hasClass('check-green')) {
                target.removeClass("tree-folder-open");
            }
        }
    });
    $('#tab').tabs({
        height: 269
    });
    $('.tool-set').click(function () {
        $('#win').dialog({
            title: '识别导入设置',
            width: 512,
            height: 402,
            resizable: false,
            draggable: false,
            zIndex: 1000,
            modal: true
        })
    });
    var sectionData = [Comm.getData('courseSection/getAllSectionBySubject', {}, true)];
    $('.select-charpter').click(function () {
            $('#assign-chapter').dialog({
                title: '选择章节',
                width: 480,
                height: 400,
                resizable: false,
                draggable: false,
                modal: true,
                zIndex: 1010
            });
            $('#chapter-tree').tree({
                lines: true,
                data: [],
                checkbox: true
            })
        })
        //layout1
    tree1.tree({
        data: [],
        lines: true
    })
    tree1.tree({
        onClick: function (node) {
            showItem(node);
        }
    });

    function showItem(node) {
        if (node.origin) {
            var origin = node.origin;
            if (origin.items.length > 0) {
                var content = [origin.title];
                var answer = [];
                $.each(origin.items, function (i, e) {
                    content.push(e.title);
                    answer.push(e.answer);
                })
                $(".verifyArea").find("td[lang='question']").text(origin.question);
                $(".verifyArea").find("td[lang='explain']").text('复合题型');
                $(".verifyArea").find("td[lang='answer']").text(answer.join(''));
                $(".verifyArea").find("td[lang='difficulty']").text('复合题型');
                $("#itemContent").html(content.join('').replace(/\n/g, '<br>'));
                $("#itemAnswer").html(answer.join('').replace(/\n/g, '<br>'));
            } else {
                $("#itemContent").html(origin.title.replace(/\n/g, '<br>'));
                $("#itemAnswer").html(origin.answer);
                var obj = $(".verifyArea").find("td[lang]");
                $.each(obj, function (i, e) {
                    var k = this.lang;
                    if (origin[k]) {
                        $(this).text(origin[k]);
                    } else {
                        $(this).text(origin.error);
                    }
                })
            }
        }
    }

    function removeNode(tree, node) {
        if (node) {
            if (node.origin) {
                var parent = tree.tree('getParent', node.target);
                tree.tree('remove', node.target);
                if (parent.children.length < 1) {
                    tree.tree('remove', parent.target);
                }
            }
        }

    }
    //移动滚动条
    function scroll(node, parentSelector) {
        var height = $(node).innerHeight(),
            offsetTop = $(node).offset().top - $(parentSelector).offset().top,
            view = $(parentSelector).parent().innerHeight(),
            position = offsetTop + height - view;
        if (position < 0) {
            position = 0;
        } else if (position > $(parentSelector).innerHeight() - view) {
            position = $(parentSelector).innerHeight() - view;
        };
        $(parentSelector).parent().scrollTop(position);
    }
    //定位首题
    function lockRootNode(tree) {
        var root = tree.tree('getRoot');
        if (root) {
            var parent = tree.tree('getChildren', root.target)[0];
            var children = tree.tree('getChildren', parent.target)[0];
            tree.tree('select', children.target);
            scroll(children.target, tree)
        }
    }
    //执行导入
    function importItem(data) {
        if (data) {
            Comm.post("parse/importPaper", importResponse, {json:JSON.stringify(data)} )
        }

    }
    //导入回调
    function importResponse(data) {
        if (data.status == 0) {
            Comm.alert('导入成功！');
        } else {
            Comm.alert(data.message, 'error');
        }
    }
    //删除
    $("#layout1 .tool-delete").click(function () {
            var node = tree1.tree('getSelected');
            removeNode(tree1, node);
        })
        //导入
    $("#layout1 .tool-import").click(function () {
            var node = tree1.tree('getSelected');
            //1.导入
            importItem(node);
            //2.删除
            removeNode(tree1, node);
        })
        //全部导入
    $("#layout1 .tool-import-all").click(function () {
            var nodes = tree1.tree('getRoots');
            //1.导入
            importItem(nodes[0]);
            //2.删除
            tree1.tree('loadData', []);
        })
        //首题
    $("#layout1 .tool-first").click(function () {
            lockRootNode(tree1)

        })
        //上一题
    $("#layout1 .tool-prev").click(function () {
            var node = tree1.tree('getSelected');
            if (node) {
                var prev = $(node.target).parent().prev().children();
                if (prev.length == 1) {
                    tree1.tree('select', prev);
                    scroll(prev, tree1)
                }
            } else
                lockRootNode(tree1)
        })
        //下一题
    $("#layout1 .tool-next").click(function () {
            var node = tree1.tree('getSelected');
            if (node) {
                var next = $(node.target).parent().next().children();
                if (next.length == 1) {
                    tree1.tree('select', next);
                    scroll(next, tree1)
                }

            } else
                lockRootNode(tree1)

        })
        //尾题
    $("#layout1 .tool-last").click(function () {
        var target = tree1.find('li:last').children('div');
        if (target.length > 0) {
            tree1.tree('select', target);
            scroll(target, tree1)
        }
    })

    $('#layout .tool-import').off("click").on("click", function () {
        Comm.alert("请先打开合法试卷！");
    });
    $('.close-dialog').click(function () {
        $(this).parent().parent().dialog('close')
    })

});
