$(function () {
    $.extend($.fn.dialog.defaults, {
        resizable: false,
        draggable: false,
        modal: true
    });
    $('#course-information').layout({
        fit: true
    });
    $('#layout-right').layout({
        fit: true
    });
    $('.subject-menu').menu();

    function openMenu(id,e) {
        $('.subject-menu').menu('show', {
            left: e.pageX,
            top: e.pageY,
            duration: 500,
            onClick: function (item) {
                if ($(item.target).hasClass('tree-add')) {
                	switchItem(id,false);
                }else if($(item.target).hasClass('tree-modify')){
                	switchItem(id,true);
                }else if($(item.target).hasClass('tree-delete')){
                	switch (id) {
					case 1:
						var node=$("#subject").tree("getSelected");
						if(!node){
							Comm.alert("请先选择科目");return;
						}
						Comm.confirm('是否要删除选择的对象', function () {
							Comm.deleteAjax("enumbank/deleteSubject", false, node.origin.id);
							$("#subject").tree('remove',node.target);
				        })
						
						//删除
						break;
					case 2:
						var node=$("#course").tree("getSelected");
						if(!node){
							Comm.alert("请先选择课程");return;
						}
						Comm.confirm('是否要删除选择的对象', function () {
							Comm.deleteAjax("course/deleteCourse", false, node.origin.id);
							$("#course").tree('remove',node.target);
				        })
						break;
					case 3:
						var node=$("#unit").tree("getSelected");
						if(!node){
							Comm.alert("请先选择单元");
							return;
						}
						Comm.confirm('是否要删除选择的对象', function () {
							Comm.deleteAjax("courseSection/deleteCourseSection", false, node.origin.id);
							$("#unit").tree('remove',node.target);
				        })
						break;
					default:
						var node=$("#section").tree("getSelected");
						if(!node){
							Comm.alert("请先选择小节");
							return;
						}
						Comm.confirm('是否要删除选择的对象', function () {
							Comm.deleteAjax("courseSection/deleteCourseSection", false, node.origin.id);
							$("#section").tree('remove',node.target);
				        })
						break;
					}
                }
            }
        });
        function switchItem(id,flag){
        	switch (id) {
				case 1:
					if(flag){
						var node=$("#subject").tree("getSelected");
						if(!node){
							Comm.alert("请先选择科目");return;
						}
						var objId=node.origin.id;
						Comm.setData("enumbank/getEnumbank", objId, "add-subject");
						$('#add-subject .btn-blue').off("click").on("click",function(e){
							if(!Comm.checkError("add-subject")){
								var data=Comm.getParameters("add-subject");
								data.id=objId;
								Comm.saveAjax({url:"enumbank/modifyEnumbank",async:false,params:data});
								$('#add-subject').dialog('close');
								Comm.refreshTree('#subject', "enumbank/getAllSubject");
							}
				    	})
					}else{
						$('#add-subject .btn-blue').off("click").on("click",function(e){
							if(!Comm.checkError("add-subject")){
								var data=Comm.getParameters("add-subject");
								Comm.saveAjax({url:"enumbank/addEnumbank",async:false,params:data});
								$('#add-subject').dialog('close');
								Comm.refreshTree('#subject', "enumbank/getAllSubject");
							}
				    	})
					}
					$('#add-subject').dialog({
                    title: '科目',
                    width: 344,
                    height: 200,
                    zIndex: 1000,
                    onClose: function () {
                    	Comm.resetForm("add-subject");
                    }
                })
					break;
				case 2:
					if(flag){
						var node=$("#course").tree("getSelected");
						if(!node){
							Comm.alert("请先选择课程");return;
						}
						var objId=node.origin.id;
						Comm.setData("course/getCourse", objId, "add-course");
						$('#add-course .btn-blue').off("click").on("click",function(e){
							if(!Comm.checkError("add-course")){
								var data=Comm.getParameters("add-course");
								data.id=objId;
								Comm.saveAjax({url:"course/modifyCourse",async:false,params:data});
								$('#add-course').dialog('close');
								var subject=$("#subject").tree("getSelected");
								Comm.refreshTree('#course', "course/getAllCourseBySubject", {subject:subject.text});
							}
				    	})
					}else{
						var node=$("#course").tree("getRoot");
						if(!node){
							Comm.alert("请先选择科目");
							return;
						}
						$('#add-course .btn-blue').off("click").on("click",function(e){
							if(!Comm.checkError("add-course")){
								var data=Comm.getParameters("add-course");
								Comm.saveAjax({url:"course/addCourse",async:false,params:data});
								$('#add-course').dialog('close');
								var subject=$("#subject").tree("getSelected");
								Comm.refreshTree('#course', "course/getAllCourseBySubject", {subject:subject.text});
							}
				    	})
					}
					$('#add-course').dialog({
                    title: '课程',
                    width: 344,
                    height: 200,
                    zIndex: 1000,
                    onClose: function () {
                    	Comm.resetForm("add-course");
                    }
                })
					break;
				case 3:
					if(flag){
						var node=$("#unit").tree("getSelected");
						if(!node){
							Comm.alert("请先选择课程");
							return;
						}
						var objId=node.origin.id;
						Comm.setData("courseSection/getCourseSection", objId, "add-unit");
						$('#add-unit .btn-blue').off("click").on("click",function(e){
							if(!Comm.checkError("add-unit")){
								var data=Comm.getParameters("add-unit");
								data.id=objId;
								
								Comm.saveAjax({url:"courseSection/modifyCourseSection",async:false,params:data});
								$('#add-unit').dialog('close');
								var course=$("#course").tree("getSelected");
								Comm.refreshTree('#unit', "courseSection/getAllSectionByCourse", {id:course.origin.id});
							}
				    	})
					}else{
						var node=$("#unit").tree("getRoot");
						if(!node){
							Comm.alert("请先选择课程");
							return;
						}
						$('#add-unit .btn-blue').off("click").on("click",function(e){
							if(!Comm.checkError("add-unit")){
								var data=Comm.getParameters("add-unit");
								
								Comm.saveAjax({url:"courseSection/addCourseSection",async:false,params:data});
								$('#add-unit').dialog('close');
								var course=$("#course").tree("getSelected");
								Comm.refreshTree('#unit', "courseSection/getAllSectionByCourse", {id:course.origin.id});
								
							}
				    	})
					}
					$('#add-unit').dialog({
                    title: '单元',
                    width: 344,
                    height: 150,
                    zIndex: 1000,
                    onClose: function () {
                    	Comm.resetForm("add-unit");
                    }
                })
					break;
				default:
					if(flag){
						var node=$("#section").tree("getSelected");
						if(!node){
							Comm.alert("请先选择小节");
							return;
						}
						var objId=node.origin.id;
						Comm.setData("courseSection/getCourseSection", objId, "add-section");
						$('#add-section .btn-blue').off("click").on("click",function(e){
							if(!Comm.checkError("add-section")){
								var data=Comm.getParameters("add-section");
								data.id=objId;
								
								Comm.saveAjax({url:"courseSection/modifyCourseSection",async:false,params:data});
								$('#add-section').dialog('close');
								var section=$("#unit").tree("getSelected");
								Comm.refreshTree('#section', "courseSection/getAllNodeByCourse", {id:section.origin.id});
								
							}
				    	})
					}else{
						var node=$("#section").tree("getRoot");
						if(!node){
							Comm.alert("请先选择单元");
							return;
						}
						$('#add-section .btn-blue').off("click").on("click",function(e){
							if(!Comm.checkError("add-section")){
								var data=Comm.getParameters("add-section");
								
								Comm.saveAjax({url:"courseSection/addCourseSection",async:false,params:data});
								$('#add-section').dialog('close');
								var section=$("#unit").tree("getSelected");
								Comm.refreshTree('#section', "courseSection/getAllNodeByCourse", {id:section.origin.id});
								
							}
				    	})
					}
					$('#add-section').dialog({
                    title: '小节',
                    width: 344,
                    height: 150,
                    zIndex: 1000,
                    onClose: function () {
                    	Comm.resetForm("add-section");
                    }
                });
					break;
				}
        }
    }
    var subject=[Comm.getData("enumbank/getAllSubject")];
    $('#subject').tree({
        lines: true,
        data: subject,
        onContextMenu: function (e, node) {
            e.preventDefault();
            openMenu(1,e);
        },
        onClick: function(node){
            $("input[name='kemu']").val(node.text);
            Comm.refreshTree('#course', "course/getAllCourseBySubject", {subject:node.text});
            $('#unit').tree('loadData',[]);
            $('#section').tree('loadData',[]);
        }
    });
    $('.subject-js').bind('contextmenu', function (e) {
        e.preventDefault();
        openMenu(1,e);
    });
    $('#add-subject .btn-blue').click(function () {
        var info = {};
        var form = $('#add-subject .form-horizontal')[0];
        info.subjectId = form.elements['subjectId'].value;
        info.subjectName = form.elements['subjectName'].value;
        info.subjectIndex = form.elements['subjectIndex'].value;
        var node = $('#subject').tree('getRoot');
        $('#subject').tree('append', {
            parent: node.target,
            data: [{
                text: info.subjectName
            }]
        })
        form.reset();
    });
    $('#course').tree({
        lines: true,
        data: [],
        onContextMenu: function (e, node) {
            e.preventDefault();
            $('#course').tree('select', node.target);
            openMenu(2,e);
        }
    });
    $('#course').tree({
    	onClick: function(node){
    		if(node.origin){
    			$("input[name='courseid']").val(node.origin.id);
    			Comm.refreshTree('#unit', "courseSection/getAllSectionByCourse", {id:node.origin.id});
    			$('#section').tree('loadData',[]);
    		}
    	}
    })
    $('.course-js').bind('contextmenu', function (e) {
        e.preventDefault();
        openMenu(2,e);
    });
    $('#unit').tree({
        lines: true,
        data: [],
        onContextMenu: function (e, node) {
            e.preventDefault();
            $('#unit').tree('select', node.target);
            openMenu(3,e);
        }
    });
    $('#unit').tree({
    	onClick: function(node){
    		if(node.origin){
    			$("input[name='parentid']").val(node.origin.id);
    			Comm.refreshTree('#section', "courseSection/getAllNodeByCourse", {id:node.origin.id});
    		}
    	}
    })
    $('.unit-js').bind('contextmenu', function (e) {
        e.preventDefault();
        openMenu(3,e);
    });
    $('#section').tree({
        lines: true,
        data: [],
        onContextMenu: function (e, node) {
            e.preventDefault();
            $('#section').tree('select', node.target);
            openMenu(4,e);
        }
    });
    $('.section-js').bind('contextmenu', function (e) {
        e.preventDefault();
        openMenu(4,e);
    });
    $('.close-dialog').click(function () {
        $(this).parent().parent().dialog('close');
    });
});
