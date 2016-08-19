$(function () {
    $('.view-papers-table').bootstrapTable(
        Comm.bootstrapTableParams({
            url: 'exampaperM/showQueryExampaperM',
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
                field: 'createby',
                title: '创建人'
            }, {
                field: 'createdate',
                title: '创建时间'
            }, {
                field: 'details',
                title: '详细参数'
            }, {
                field: 'preview',
                title: '预览'
            }]
        })
    );
    $('.view-papers-table').on('click', '.view-papers-link', function () {
    	var index=$(this).parent("td").parent("tr").data("index");
    	var row=$('.view-papers-table').bootstrapTable('getData')[index];
        var data = Comm.getData("exampaperM/showPaperDetail", { id: row.id }, true);
        $('.questions-structure').bootstrapTable('load', data.question);
        $('.difficulty-structure').bootstrapTable('load', data.difficulty)
        $('.questions-list').bootstrapTable('load', data.itemInfo)
        if (!$('#add').data('dialog')) {
            $('#add').dialog({
                title: '试卷详细参数',
                width: 510,
                height: 400,
                draggable: false,
                resizable: false,
                zIndex: 1000
            });
        } else {
            $('#add').dialog('open');
        }
        if (!$('#tab').data('tabs')) {
            $('#tab').tabs({
                height: 247
            });
        }
        $('#tree').tree({
            lines: true,
            data: [data.sectionTree]
        });
    })
    $('.view-papers-table').on('click', '.view-html-link', function () {
	    	var index=$(this).parent("td").parent("tr").data("index");
	    	var row=$('.view-papers-table').bootstrapTable('getData')[index];
	    	var html = Comm.getData("exampaperM/previewPaper", { id: row.id }, false);
	    	$("#paperContent").html(html);
	    	$('#preview-papers').dialog({
	            title: '预览试卷',
	            maximized:true,
	            zIndex: 1000
	        });
	    	
    })
    $('.questions-structure').bootstrapTable({
        pagination: true,
        pageNumeber: 1,
        pageSize: 20,
        pageList: [20, 30, 50, 100],
        checkboxHeader: false,
        cache: false,
        paginationDetailHAlign: 'right',
        columns: [{
            field: 'question',
            title: '题型',
        }, {
            field: 'amount',
            title: '题量合计'
        }],
        data: []
    });
    $('.difficulty-structure').bootstrapTable({
        pagination: true,
        pageNumeber: 1,
        pageSize: 20,
        pageList: [20, 30, 50, 100],
        checkboxHeader: false,
        cache: false,
        paginationDetailHAlign: 'right',
        columns: [{
            field: 'difficulty',
            title: '难度',
        }, {
            field: 'amount',
            title: '题量合计'
        }],
        data: []
    });
    $('.questions-list').bootstrapTable({
        pagination: true,
        pageNumeber: 1,
        pageSize: 20,
        pageList: [20, 30, 50, 100],
        checkboxHeader: false,
        cache: false,
        paginationDetailHAlign: 'right',
        columns: [{
            field: 'question',
            title: '题型',
        }, {
            field: 'difficulty',
            title: '难度'
        }, {
            field: 'knowledge',
            title: '知识点'
        }, {
            field: 'rank',
            title: '等级'
        }, {
            field: 'source',
            title: '来源'
        }],
        data: []
    })
    $(window).resize(function () {
        $('table').bootstrapTable('resetWidth');
    });
    $('#export').on('nextStep.dialog', function () {
        var $btn = $('#export .next-step');
        if ($btn.length > 0) {
            $('.step1').addClass('hidden');
            $('.step2').removeClass('hidden');
            $('#export').dialog({
                width: 510,
                height: 400
            });

            $btn.text('上一步').removeClass('next-step').addClass('prev-step');
            $btn.parent().append('<button class="btn btn-blue complete-js ml10">生成文档</button>');
        }

    })
    $('#export .text-center').on('click', '.next-step', function () {
        $('#export').trigger('nextStep');
    });
    $('#export').on('prevStep.dialog', function () {
        var $btn = $('#export .prev-step');
        if ($btn.length > 0) {
            $('.step2').addClass('hidden');
            $('.step1').removeClass('hidden');
            $btn.text('下一步').removeClass('prev-step').addClass('next-step');
        }
        if ($('.complete-js').length > 0) {
            $('.complete-js').remove();
        }
    })
    $('#export .text-center').on('click', '.prev-step', function () {
        $('#export').trigger('prevStep');
        $('#export').dialog({
            width: 835,
            height: 566
        });
    })
    $('.start-time,.end-time').datepicker({
        changeYear: true,
        changeMonth: true,
        showButtonPanel: true
    });

    $("#searche").bind("click", function () {
        Comm.queryData();
    })
    $(".tool-delete").click(function () {
        Comm.deleteObject("exampaperM/deleteExampaperM", ".view-papers-table");
    })
    $(".tool-up").click(function () {
        if ($("#questions .tree-node-selected").length > 0) {
            var that = $("#questions .tree-node-selected");
            var prev = that.prev();
            if (prev.length > 0) {
                prev.insertAfter(that)
            }
        } else {
            Comm.alert("请选择要调整的题型");
        }

    })
    $(".tool-down").click(function () {
        if ($("#questions .tree-node-selected").length > 0) {
            var that = $("#questions .tree-node-selected");
            var next = that.next();
            if (next.length > 0) {
                next.insertBefore(that);
            }
        } else {
            Comm.alert("请选择要调整的题型");
        }
    })
    $('#questions').on('click', '.question', function () {
        $("#questions p").removeClass("tree-node-selected");
        $(this).addClass("tree-node-selected");
    })
    $('.tool-export').click(function () {
        var obj = Comm.getSelectedRow($('.view-papers-table'));
        var paperHead=Comm.getData("courseAttribute/getPaperHead",{},true);
        UM.getEditor('myEditor').setContent(paperHead);
        if (!obj) {
            Comm.alert("请点击要导出的试卷！");
            return;
        }
        window.paperTitle = obj.name;
        window.itemIds = Comm.getData("exampaperM/getItemIdsByPaperId", { id: obj.id }, true);
        var questions = Comm.getData("queryStatistics/showItemsQuestion", { id: itemIds }, true);
        var html = [];
        $.each(questions, function (i, e) {
            html.push('<p class="question" style="cursor:default">' + e.question + '</p>');
        })
        $("#questions").html(html.join(''));
        if (!$('#layout,#layout-center,#layout-bottom').data('layout')) {
            $('#layout').layout({});
            $('#layout-center,#layout-bottom').layout({
                fit: true
            })
        }
        $('#export').dialog({
            title: '导出试卷',
            width: 835,
            height: 566,
            draggable: false,
            resizable: false,
            modal: true,
            zIndex: 1000,
            onClose: function () {
            	$("#export textarea[name='head']").val("");
                $('#export').trigger('prevStep');
            }
        });
    });
    $("#export input[name='generate-papers-check']").change(function () {
        if ($(this).val() == "answer") {
            $("select[name='after']").attr("disabled", true);
        } else if ($(this).val() == "content") {
            $("select[name='after']").attr("disabled", true);
        } else {
            $("select[name='after']").attr("disabled", false);
            $("select[name='after']").val("false").trigger('change');
        }
    })
    $('#export').on('click', '.complete-js', function () {
        var params={ ids: window.itemIds, title: window.paperTitle, content: false, answer: false };
        var sort = [];
        $("#questions p").each(function (i, e) {
            sort.push($(this).text());
        });
        var head = UM.getEditor('myEditor').getContent();
        var startOne = $("#export input[name='questions-index']:checked").val();
        var val = $("#export input[name='generate-papers-check']:checked").val();
        params.sort = sort.join(',');
        params.head = head;
        params.startOne = startOne;
        if (val == "answer") {
            params.answer = true;
            Comm.postByForm("itemcontent/createPaperByIds", params);
        } else if (val == "content") {
            params.content = true;
            Comm.postByForm("itemcontent/createPaperByIds", params);
        } else {
            var after = $("select[name='after']").val();
            if (!after) {
                $("select[name='after']").tips({ msg: "请选择答案生成位置" })
            } else {
            	params.after=after;
            	params.content=true;
            	params.answer=true;
                Comm.postByForm("itemcontent/createPaperByIds", params);
            }
        }
    })
    $('.close-dialog').click(function () {
        $(this).parent().parent().dialog('close');
    })
})
