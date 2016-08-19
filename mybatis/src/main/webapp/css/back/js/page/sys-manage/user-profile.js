$(function(){
	var userInfo = Comm.get("userInfo");
	$("table td").each(function(i,e){
		var value=userInfo[e.lang];
		if(value){
			$(this).text(value);
		}
	})
	var role=userInfo.userRole[0].name;
	var course=[];
	for(var i in userInfo.userCourse){
		course.push(userInfo.userCourse[i].name);
	}
	$("td[lang='role']").text(role);
	$("#course").text(course.join(','));
	$('.btn-green-s').click(function () {
	    $('#select-course').dialog({
	        title: '选择课程',
	        width: 480,
	        height: 400,
	        resizable: false,
	        draggable: false,
	        modal: true,
	        zIndex: 1010
	    });
	});
	$('#course-tree').tree({
		lines: true,
		data: [],
		checkbox: true
	})
	Comm.refreshTree('#course-tree', "course/getAllCourseBySubject", {subject:userInfo.kemu});
	$('#select-course .btn-blue').click(function () {
		//获取勾选数据
		var cids=Tree.getCheckData('#course-tree', 'id');
		var course=Tree.getCheckData('#course-tree', 'text');
		for(var i=0;i<cids.length;i++){
			Comm.saveAjax("userManage/setUserCourse", {couId:cids[i],userId:userInfo.userId})
		}
		if(course.length>0){
			$("#course").text(course.join(','));
		}
		Tree.reset('#course-tree');
    	$('#select-course').dialog('close');
    })
	
	$('.close-dialog').click(function () {
	    $(this).parent().parent().dialog('close');
	})
})