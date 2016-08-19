$(function () {
    var data = [{
        text: '历史（示例题库）',
        children: [{
            text: '中国古代史',
            children: [{
                text: '第一章 夏、商、西周和春秋战国'
            }, {
                text: '第二章 秦汉'
            }, {
                text: '第三章 三国两晋南北朝',
                children: [{
                    text: '第四章 秦汉'
                }, {
                    text: '第四章 秦汉'
                }]
            }]
        }, {
            text: '中国古代史 上册'
        }, {
            text: '中国古代史 下册'
        }, {
            text: '中国近代史 上册'
        }, {
            text: '中国近代史 下册'
        }]
    }];
    $('#layout1').layout({
        fit: true
    });
    $('#layout1-left').layout({
        fit: true
    });
    $('#tree1').tree({
    	data:data,
    	lines:true
    })
    $('#layout1-center').layout({
    	fit:true
    });
    $('#layout1-right').layout({
    	fit:true
    });
});