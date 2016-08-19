//pageJS
(function() {
        var userInfo = Comm.get("userInfo");
        $("#filterForm input[name='kemu']").val(userInfo.kemu);
        $("#filterForm input[name='filterflag']").click(function() {
            if (this.checked) {
                $("#filterForm select[name='filterdate']").attr("disabled", false);
            } else {
                $("#filterForm select[name='filterdate']").attr("disabled", true);
            }
        })
        $("#filterForm input[name='countflag']").click(function() {
        	if (this.checked) {
        		window.countflag="1";
        	} else {
        		window.countflag="0";
        	}
        })
        $("#filterForm input[name='byYear']").click(function() {
            if (this.checked) {
                $("#filterForm input[name='beginyear']").attr("disabled", false);
                $("#filterForm input[name='endyear']").attr("disabled", false);
            } else {
                $("#filterForm input[name='beginyear']").attr("disabled", true);
                $("#filterForm input[name='endyear']").attr("disabled", true);
            }
        })
        $("#saveId").click(function() {
            var saveParams = Comm.getParameters('filterForm');
            var schemeParams = Comm.getParameters("schemeForm");
            schemeParams.sections = sectionids;
            schemeParams.difficultymode=window.difficultymode;
            schemeParams.countflag=window.countflag;
            //schemeParams.courseid=courseid;
            $.extend(saveParams, schemeParams);
            Comm.post("schemeM/addSchemeM", response, saveParams)
            
            function response(data) {
                if (data.status == 0) {
                    var schemeMId = data.rows;
                    $.each(detailParam, function(i, e) {
                        e.schemeId = schemeMId;
                        e.priviledgesID = priviledgesID;
                        e.sections = sectionids;
                        $.post(baseUrl + "schemeD/addSchemeD", e);
                    })
                    $('#complete').dialog('close');
                    $('.papers-information').bootstrapTable('refresh');
                }
            }
        })
        $(".tool-delete").click(function() {
            Comm.deleteObject("schemeM/deleteSchemeM", ".papers-information");
        })
        $("#searche").bind("click", function() {
            Comm.queryData();
        })
        var userInfo = Comm.get("userInfo");
        window.queryItem = {};
        window.queryItem.priviledgesID = priviledgesID;
        $("input[name='createby']").val(userInfo.userName);
       $("#filterForm :input").each(function(i, e) {
            $(this).off("change").on("change",function() {
            	if($(this).is(":visible")){
		            var query = Comm.getParameters('filterForm');
		            if (!query.byYear) {
		                query.beginyear = "";
		                query.endyear = "";
		            }
		            if (!query.filterflag) {
		                query.filterdate = "";
		            }
		            if (query.countflag) {
		                query.useLess = true;
		            }
		            if (window.sectionids) {
		                query.section = window.sectionids;
		                query.course = window.courseids;
		            }
		            if (window.questionids) {
		                query.question = window.questionids;
		            }
		            window.queryItem = query;
		            Comm.setText("itemcontent/getItemNum", "#itemNum", window.queryItem);
            	}
            })
        })
})()



$(function () {
    $.extend($.fn.dialog.defaults, {
        resizable: false,
        draggable: false,
        modal: true
    });
    //初始化表格
    $('.papers-information').bootstrapTable(
        Comm.bootstrapTableParams({
            url: 'schemeM/showQuerySchemeM',
            columns: [{
                field: 'check',
                title: '选择',
                checkbox: true
            }, {
                field: 'schemeName',
                title: '方案名称'
            }, {
                field: 'schemeType',
                title: '方案类型'
            }, {
                field: 'createby',
                title: '创建人'
            }, {
                field: 'createdate',
                title: '创建时间'
            }, {
                field: 'view',
                title: '查看'
            }, {
                field: 'operate',
                title: '操作'
            }]
        })
    );
    $('.view-paper-table').bootstrapTable({
        cache: false,
        height: 221,
        columns: [{
            field: 'question',
            title: '题型',
        },{
            field: 'difficulty',
            title: '难度',
        }, {
            field: 'amount',
            title: '数量'
        }],
		data: []
    });
    $('.view-paper-tree').tree({
        lines: true,
        data: []
    });
    $('.papers-information').on('click', '.generate-papers-view', function () {
    	var index=$(this).parent("td").parent("tr").data("index");
    	var row=$('.papers-information').bootstrapTable('getData')[index];
    	$('#view-paper').dialog({
            title: '查看方案',
            width: 935,
            height: 516,
            zIndex: 1000
        });
    	var data=Comm.getData("schemeM/viewPaper", {id:row.id}, true);
    	$('.view-paper-table').bootstrapTable('load', data.data);
    	$('.view-paper-tree').tree('loadData',[data.tree]);
    	var questions=[];
    	$.each(data.data,function(i,e){
    		questions.push(e.question);
    	})
    	Comm.setFormData(data.form, "view-paper");
    	$("#view-paper input[name='question']").val(Comm.uniqueArr(questions).join(','));
    	if(data.form.difficultymode==0){
    		$("#view-paper input[name='difficulty']").val(data.data[0].difficulty);
    	}
    	if($("#view-paper select[name='filterdate']").val()){
    		$("#view-paper input[name='filterflag']").get(0).checked=true;
    	}else{
    		$("#view-paper input[name='filterflag']").get(0).checked=false;
    	}
    	if($("#view-paper input[name='beginyear']").val()){
    		$("#view-paper input[name='byYear']").get(0).checked=true;
    	}else{
    		$("#view-paper input[name='byYear']").get(0).checked=false;
    	}
    	if(data.form.countflag==1){
    		$("#view-paper input[name='countflag']").get(0).checked=true;
    	}else{
    		$("#view-paper input[name='countflag']").get(0).checked=false;
    	}
    });
    $('#tree3').tree({
        lines: true,
        data: []
    });
    $('.papers-information').on('click', '.generate-papers-link', function () {
    	var index=$(this).parent("td").parent("tr").data("index");
    	var row=$('.papers-information').bootstrapTable('getData')[index];
        window.paperTitle = row.schemeName;
        //点击生成试卷 弹出生成试卷dialog
        $('#generate-papers').dialog({
            title: '试卷生成方案--整卷调整',
            width: 1002,
            height: 626,
            zIndex: 1000
        });
        Comm.post("schemeD/createItemIds", function(data){
        	if(data.status==0){
        		if(data.rows){
        			window.itemIds=data.rows;
        			$('.tool-save').show();
                	$('.tool-analysis').show();
                	$('.tool-export').show();
                	var data = Comm.getData("itemcontent/createHtmlByIds", { ids: window.itemIds, title: window.paperTitle }, true);
                    $("#paperContent").html(data.html);
                    $('#tree3').tree("loadData",[data.tree]);
        		}else{
        			$("#paperContent").html("");
                	$('#tree3').tree("loadData",[]);
                	$('.tool-save').hide();
                	$('.tool-analysis').hide();
                	$('.tool-export').hide();
                	Comm.alert("未找到试题");
        		}
        	}
        }, { id: row.id })
       
        //生成试卷dialog整体,右边布局
        $('#layout1,#layout').layout({
            fit: true
        });
    });

    $(window).resize(function () {
        $('table').bootstrapTable('resetWidth');
    });
    //表格上方的增加按钮 弹出增加dialog
    $('.tool-add').click(function () {
        $('#add').dialog({
            title: '试卷生成方案',
            width: 835,
            height: 516,
            zIndex: 1000,
            onClose: function () {
                Comm.resetForm("add");
                $('#add').trigger('prevStep');
            }
        });
        //增加dialog中的树
        var treeData = [Comm.getData('courseSection/getAllSectionBySubject', {}, true)];
        $('#tree').tree({
            lines: true,
            data: treeData,
            checkbox: true
        });
        var data = Comm.getData("courseAttribute/getAttrByType", {
            id: 0
        }, true);
        var questionTree = Tree.dataToTree(data, "题型");
        $('#chapter-tree').tree({
            lines: true,
            data: questionTree,
            checkbox: true
        })
        $('#tree').tree({
            onCheck: function (node, checked) {
                var checkData = $('#tree').tree('getChecked');
                var section = [];
                var course = [];
                getCourseSection(checkData);

                function getCourseSection(children) {
                    $.each(children, function (i, e) {
                        if (e.children.length > 0) {
                            getCourseSection(e.children);
                        } else {
                            var parent = $('#tree').tree('getParent', e.target);
                            course.push(parent.origin.courseid);
                            section.push(e.origin.id);
                        }
                    })
                }
                window.sectionids = Comm.uniqueArr(section).join(',');
                window.courseids = Comm.uniqueArr(course).join(',');
                window.queryItem.section = window.sectionids;
                window.queryItem.course = window.courseids;
                Comm.setText("itemcontent/getItemNum", "#itemNum", window.queryItem);
            }
        });

        $('.select-more').click(function () {
            $('#assign-chapter').dialog({
                title: '选择考试题型',
                width: 480,
                height: 400,
                resizable: false,
                draggable: false,
                modal: true,
                zIndex: 1010
            });
        });
    });

    $('#assign-chapter .btn-blue').click(function () {
        var checkData1 = Tree.getCheckData('#chapter-tree', 'id');
        if (checkData1.length == 0) {
            Comm.alert("请选择题型！");
            return;
        }
        window.questionids = checkData1.join(',');
        window.queryItem.question = window.questionids;
        Comm.setText("itemcontent/getItemNum", "#itemNum", window.queryItem);
        $('#assign-chapter').dialog('close');
    })
    $('#add .text-center').on('click', '.prev-step', function () {
        $('#add').trigger('prevStep');
    });
    $('#add').on('prevStep.diaog', function () {
        var $btn = $('#add .prev-step')
        if ($btn.length > 0) {
            $('.step1').removeClass('hidden');
            $('.step2').addClass('hidden');
            $btn.removeClass('prev-step').addClass('next-step').text('下一步');
        }
        if ($('.complete-js').length > 0) {
            $('.complete-js').remove();
        }
    })
    $('#add').on('nextStep.dialog', function () {
            if ($('#tree').tree('getChecked').length == 0) {
                Comm.alert("请勾选考核范围！");
                return;
            }
            if ($('#chapter-tree').tree('getChecked').length == 0) {
                Comm.alert("请选择题型！");
                return;
            }
            var $btn = $('#add .next-step');
            //隐藏第一步
            $('.step1').addClass('hidden');
            //显示第二步
            $('.step2').removeClass('hidden');
            //将下一步按钮变为上一步按钮，并改变选择器
            $btn.removeClass('next-step').addClass('prev-step').text('上一步');
            //增加完成按钮
            $btn.parent().append('<button class="btn btn-blue complete-js">完成</button>');
            //初始化树
            function getChecked(data) {
                var arr = [];

                function isArray(data) {
                    return data instanceof Array;
                }

                function customForEach(data, fn) {
                    if (isArray(data)) {
                        for (var i = 0; i < data.length; i++) {
                            if (data[i].children.length > 0 && data[i].checkState && data[i].checkState !== 'checked') {
                                customForEach(data[i].children, fn);
                            } else {
                                fn(data[i]);
                            }
                        }
                    }
                }
                customForEach(data, function (n) {
                    if (n.checkState && n.checkState === 'checked') {
                        arr.push(n);
                    }
                });
                return arr;
            }
            $('#tree-no-checkbox').tree({
                lines: true,
                data: getChecked($('#tree').data('tree').data)
            });

            $('.total-questions-difficulty').bootstrapTable({
                height: 241,
                method: 'POST',
                contentType: 'application/x-www-form-urlencoded',
                dataField: 'rows',
                cache: false,
                sidePagination: 'server',
                columns: [{
                    field: 'question',
                    title: '题型',
                }, {
                    field: 'count',
                    title: '题目数量'
                }, {
                    field: 'setQuestion',
                    title: '设置题数',
                    editable: true
                }],
                url: baseUrl + 'itemcontent/showQuestionItemByDifficulty',
                queryParams: function (params) {
                    $.each(window.queryItem, function (i, e) {
                        params[i] = e;
                    })
                    params.difficulty = '中';
                    return params;
                }
            });
            //初始化第二步中的定义题目难度表格
            $('.every-questions-difficulty').bootstrapTable({
                height: 241,
                method: 'POST',
                contentType: 'application/x-www-form-urlencoded',
                dataField: 'rows',
                cache: false,
                sidePagination: 'server',
                columns: [{
                    field: 'question',
                    title: '题型'
                }, {
                    field: 'difficulty',
                    title: '难度'
                }, {
                    field: 'count',
                    title: '题目数量'
                }, {
                    field: 'setQuestion',
                    title: '设置题数',
                    editable: true //单元格可编辑
                }],
                url: baseUrl + 'itemcontent/showQuestionItem',
                queryParams: function (params) {
                    $.each(window.queryItem, function (i, e) {
                        params[i] = e;
                    })
                    return params;
                }
            });
        })
        //点击增加dialog中的下一步按钮
    $('#add .text-center').on('click', '.next-step', function () {
        $('#add').trigger('nextStep');
    });
    $("#selectDif").on("change", function () {
            var d = $(this).val();
            //初始化第二步中的定义题目难度表格
            var params = window.queryItem;
            params.difficulty = d;
            $('.total-questions-difficulty').bootstrapTable('refresh', {
                query: params
            });
        })
        //点击第二步中的完成按钮
    $('#add .text-center').on('click', '.complete-js', function () {
    		var tableData=$('.every-questions-difficulty').bootstrapTable('getData');
    		if(!tableData||tableData.length==0){
    			Comm.alert("当前考核范围和题型未找到试题！");
                return;
    		}
            window.detailParam = [];
            if(!$("#add :radio[name='difficultymode']").is(":checked")){
            	Comm.alert("请选择试卷模式！");
                return;
            }
            var flag = $('.detail-set').get(0).checked;
            if (flag) {
                $(".every-questions-difficulty tbody tr").each(function (i, e) {
                	window.difficultymode="1"
                    var itemNum = $(this).find("td:last").text();
                    if (!isNaN(itemNum) && itemNum / 1 > 0) {
                        var param = {};
                        param.txid = $($(this).children("td").get(0)).text();
                        param.difficultyid = $($(this).children("td").get(1)).text();
                        param.itermcount = $($(this).children("td").get(3)).text();
                        detailParam.push(param);
                    }
                })
            } else {
                $(".total-questions-difficulty tbody tr").each(function (i, e) {
                	window.difficultymode="0"
                    var itemNum = $(this).find("td:last").text();
                    if (!isNaN(itemNum) && itemNum / 1 > 0) {
                        var param = {};
                        param.txid = $($(this).children("td").get(0)).text();
                        param.difficultyid = $("#selectDif").val();
                        param.itermcount = $($(this).children("td").get(2)).text();
                        detailParam.push(param);
                    }
                })
            }
            if (detailParam.length == 0) {
                Comm.alert("请设置题数");
                return;
            }
            //关闭增加dialog
            $('#add').dialog('close');
            //打开保存dialog
            $('#complete').dialog({
                title: '保存试卷生成方案',
                width: 480,
                height: 228,
                zIndex: 1000,
                onClose: function () {
                	window.countflag="0";
                	Comm.resetForm("complete");
                }
            });
//            $('.step1').removeClass('hidden');
//            $('.step2').addClass('hidden');
//            $(this).prev().prev().removeClass('prev-step').addClass('next-step').text('下一步');
//            if ($('.complete-js').length > 0) {
//                $('.complete-js').remove();
//            }
        })
        //定义editable 全局样式
    $.extend($.fn.editable.defaults, {
        mode: 'inline', //input的显示为行内形式
        emptytext: '&nbsp;&nbsp;', //当值为空时 显示为两个空格
        showbuttons: false, //不显示按钮
        clear: false, //不显示clear按钮
        onblur: 'submit', //当焦点从input上离开时，提交input的值
        validate: function (value) { //提交前的验证
            if (isNaN(+value)) { //验证输入的是否是纯数字
                return '请输入数字';
            }
            if (+value > +($(this).parent().prev().text())) { //验证输入的数字是否大于总题量
                return '题目数量不能大于总题量';
            }
        }
    });

    $('.whole-set').click(function () {
        if (this.checked) {
            $('.questions-js .ov-h').eq(0).removeClass('hidden');
            $('.questions-js .ov-h').eq(1).addClass('hidden');
            $("#selectDif").attr('disabled', false);
        }
    });
    $('.detail-set').click(function () {
        if (this.checked) {
            $('.questions-js .ov-h').eq(0).addClass('hidden');
            $('.questions-js .ov-h').eq(1).removeClass('hidden');
            $("#selectDif").attr('disabled', true);
        }
    });
    //生成试卷dialog中的分析按钮
    $('.tool-analysis').click(function () {
        //打开试卷分析dialog
        $('#analysis-papers').dialog({
            title: '试卷分析',
            width: 1000,
            height: 700,
            zIndex: 2000 //zIndex应当大于生成试卷
        });
        //分析试卷dialog整体，左边，右边的布局
        $('.analysis-layout-left,.analysis-layout,.analysis-layout-right').layout({
            fit: true
        });
        //分析试卷的布局
        var ids = window.itemIds;
        var data = Comm.getData("exampaperM/showItemsChartData", { id: ids }, true);
        //题型类型
        var itemType = data.itemType;
        var nonstandard, standard;
        if (itemType[0].itemType == 1) {
            nonstandard = itemType[0].amount;
            standard = itemType[1].amount;
        } else {
            nonstandard = itemType[1].amount;
            standard = itemType[0].amount;
        }
        //计算难度系数    难度系数=(难题+中题/2)/总题数
        var diff=data.difficulty.data;
        var midd=0;
        var difficu=0;
        for(var i=0;i<diff.length;i++){
        	if(diff[i][0]=="难"){
        		difficu+=diff[i][1];
        	}else if(diff[i][0]=="中"){
        		midd+=diff[i][1];
        	}
        }
        var DOD=(midd/2+difficu)/(standard+nonstandard);
        $("#itemStandardNum").text(standard);
        $("#itemNonstandardNum").text(nonstandard);
        $("#DOD").text(Math.round(parseFloat(DOD)*100)/100);
        var optionS = { data: data.section };
        var optionD = { data: data.difficulty };
        var optionQ = { data: data.question };
        $('#range-charts').highcharts(Comm.HighChartPie(optionS));
        $('#difficulty-charts').highcharts(Comm.HighChartPie(optionD));
        $('#questions-type-charts').highcharts(Comm.HighChartPie(optionQ));
    });
    //生成试卷dialog中的导出按钮
    $('.tool-export').click(function () {
        $('#export').dialog({
            title: '生成试卷文档',
            width: 510,
            height: 400,
            zIndex: 1010
        })
    });
    //生成试卷dialog中的保存按钮
    $('.tool-save').click(function () {
        $('#save').dialog({
            title: '保存试卷',
            width: 480,
            height: 228,
            zIndex: 1010,
            onClose: function () {
                Comm.resetForm("save");
            }
        });
    })
    $('#save .btn-blue').click(function () {
        if (!Comm.checkError("save")) {
            var params = Comm.getParameters("save");
            Comm.post("exampaperM/addExampaperM", response, params)

            function response(data) {
                if (data.status == 0) {
                    var param = {};
                    param.priviledgesID = priviledgesID;
                    param.exampaperid = data.rows;
                    param.itemIds = window.itemIds;
                    Comm.saveAjax("exampaperD/addBatchExampaperD", param);
                    $('#save').dialog('close');
                }
            }
        }
    })
    $("#export input[name='generate-papers-check']").change(function () {
        if ($(this).val() == "answer") {
            $("select[name='after']").attr("disabled", true);
        } else if ($(this).val() == "content") {
            $("select[name='after']").attr("disabled", true);
        } else {
            $("select[name='after']").attr("disabled", false);
        }
    })
    $('#export .btn-blue').click(function () {
        var val = $("#export input[name='generate-papers-check']:checked").val();
        var params={ ids: window.itemIds, title: window.paperTitle, content: false, answer: false };
        if (val == "answer") {
        	params.answer=true;
            Comm.postByForm("itemcontent/createPaperByIds", params);
        } else if (val == "content") {
        	params.content=true;
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
    });
    $('.start-time,.end-time,.create-time').datepicker({
        changeYear: true,
        changeMonth: true,
        showButtonPanel: true
    });

    $('.close-dialog').click(function () {
        $(this).parent().parent().dialog('close');
    });
    $('.start-time,.end-time,.create-time').datepicker({
        changeYear: true,
        changeMonth: true,
        showButtonPanel: true
    });
    $(".create-time").datepicker("setDate", new Date());
})
