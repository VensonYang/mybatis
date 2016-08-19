//pageJS
(function(){
//		$(window).bind('beforeunload',function(){
//	 		alert("------------");
//		 	Comm.post("user/loginOut", function(data){alert("用户已退出");})
//		});
    	Comm.post("get/userInfo", response);
    	function response(data){
    		if(data.status==0){
    			Comm.set("userInfo", data.rows);
    			//获取菜单
    			$("#userName").text(data.rows.userName);
		    	var tree=Comm.getData("user/getMenuByUserId", {"id":data.rows.userId}, true);
		    	//初始化菜单
		    	initMenu(tree.childNode);
		    	//初始化菜单点击事件
		    	$('.sub-menu-header').click(function () {
		            var $parent = $(this).parent();
		            if ($parent.hasClass('off')) {
		                $('.menu-list .open').removeClass('open').addClass('off')
		                $parent.removeClass('off').addClass('open');
		            } else if ($parent.hasClass('open')) {
		                $parent.removeClass('open').addClass('off');
		            }

		        });
		        $('.sub-menu a').click(function () {
		            $('.sub-menu .select').removeClass('select');
		            $(this).addClass('select');
		        });
		        
		        $('.open-window-js').on('click', 'a', function () {
		            var href = $(this).data('href'),
		                title = $(this).text(),
		                $win = $('iframe[name="window"]'),
		                $winc = $win.clone(),
		                $parent = $win.parent();
		            $win.attr('src', href);
		            $win.window({
		                title: title,
		                width: 1008,
		                height: 628,
		                minWidth: 1008,
		                minHeight: 628,
		                modal: true,
		                collapsible: false,
		                minimizable: false,
		                maximizable: false,
		                draggable:false,
		                resizable:false,
		                onClose: function () {
		                    $parent.append($winc);
		                    $win.window('destroy');
		                },
		                zIndex:1000
		            });
		            $win.window('open');
		            $win.window('maximize');
		        })
		    	// 初始化菜单
		    	function initMenu(data){
		    		$.each(data,function(i,menu){
		    			var menu_li = getMenu(menu);
		    			$("#menu").append(menu_li);
		    		});
		    	}
		    	function getMenu(menu){
		    		var menu_li = '';
		    		if(menu.childNode.length>0){
		    			menu_li+='<li id="'+menu.id+'" class="off">';
		    			menu_li +='<div class="sub-menu-header"><span class="'+menu.icon+'"></span><a href="'+menu.url+'">'+menu.name+'</a><i></i></div>';
		    			menu_li +='<ul class="sub-menu'+(menu.memo==1?" open-window-js":"")+'">';
		    			$.each(menu.childNode,function(j,cmenu){
		    				menu_li += getMenu(cmenu);
		    			});
		    			menu_li +=' </ul> ';
		    		}else{
		    			var str='data-href="'+menu.url+'?id='+menu.id+'" href="javascript:void(0)"';
		    			var str1="href='"+menu.url+'?id='+menu.id+"'target='content'";
		    			menu_li+='<li id='+menu.id+'><a '+(menu.memo==1?str:str1)+'>'+menu.name+'</a>';
		    		}
		    		menu_li +='</li>';
		    		return menu_li;
		    	}
    		}else{
    			Comm.confirm("您还未登录，请先登录!", function(){
    				window.location.href="login.html";
        		})
    		}
    	}
    	//注销
    	//用户退出
    	$("#loginOut").click(function(){
    		Comm.confirm("你确定要退出系统吗？", function(){
    			Comm.post("user/loginOut", function(data){
    				window.location.href="login.html";
				})
    		})
    	})
    })()
    
    $(function () {
    /*页面高度*/
    function maxHeight() {
        var arr = [];
        arr[0] = $(document).innerHeight() - (60 + 23);
        /*      arr[1] = $('.tab-content>.active').innerHeight() + 32;*/
        arr[1] = $('.menu-body').innerHeight() + 30;
        arr.sort(function (a, b) {
            return a - b;
        });
        $('.main').height(arr[1]);
        $('.menu-body').height(arr[1] - 40);
        /*      $('.tab-content').height(arr[2] - 32);*/
        /*      $('.index.active').height(arr[2] - 32);*/
    }
    $(document).on('resizeHeight', function () {
        maxHeight();
    });
    $(document).trigger('resizeHeight');
    $(window).resize(function () {
        $(document).trigger('resizeHeight');
    });
})
