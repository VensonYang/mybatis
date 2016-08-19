//pageJS
(function(){
	$(".tool-save").click(function() {
        if (!Comm.checkError("cc")) {
            var param = Comm.getParameters("cc");
            var node = $("#tree").tree("getSelected");
            if (!node || !node.origin.parentid) {
                Comm.alert("请选择章节");
                return;
            } else {
                param.sectionid = node.origin.id;
            }
            //查看是否有选择章节
            if (window.sectionid) {
                param.knowledge = window.sectionid;
            }
            Comm.saveAjax("itemcontent/addItemcontent", param);
            Comm.resetForm("cc");
            Tree.reset('#chapter-tree');
        }
    })
    $(".tool-add").click(function() {
        Comm.resetForm("cc");
        Tree.reset('#chapter-tree');
    })
})()

$(function(){
	var data=Comm.getData('courseSection/getAllSectionBySubject',{},true);
	var treeData=[data];
	$('#tree').tree({
		data:treeData,
		lines:true
	})
	 $('#chapter-tree').tree({
	        lines: true,
	        data: treeData,
	        checkbox:true
	 })
	$('.select-more').click(function () {
	    $('#assign-chapter').dialog({
	        title: '选择知识点',
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
    })
	$('.close-dialog').click(function(){
	    $(this).parent().parent().dialog('close')
	})

})