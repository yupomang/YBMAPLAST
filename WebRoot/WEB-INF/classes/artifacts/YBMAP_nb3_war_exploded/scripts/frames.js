/**
 * 主页面框架脚本
 */

//参数：是否允许同时展开多个菜单
var ALLOW_EXPAND_MULTI_MENU = false;

$(function () {
	//获取菜单数据
	//TODO: 改为用ajax从后台获取，要求按一级菜单二级菜单排序
	var menuData = [];
	
	if(window.menujson){
	   menuData=window.menujson;
	}
	//创建菜单
	var menuHtml = '';
	for (var i = 0; i < menuData.length; i++) {
		if (menuData[i].level == 1) {
			menuHtml += '<dt>' + menuData[i].name + '</dt>';
		}
		else {
			menuHtml += '<dd><a href="' + menuData[i].url + '" target="main">' + menuData[i].name + '</a></dd>';
		}
	}
	$('#menu').html(menuHtml);
	
	//初始化菜单
	$('#menu').on('click', 'dt', function () {
		var $dt = $(this);
		if ($dt.hasClass('fold')) {
			if (!ALLOW_EXPAND_MULTI_MENU) {
				$('dd').hide();
				$('dt').addClass('fold');
			}
			$dt.nextUntil('dt').show();
			$dt.removeClass('fold');
		}
		else {
			$dt.nextUntil('dt').hide();
			$dt.addClass('fold');
		}
	});
	
	//设置当前菜单选择样式
	$('#menu').on('click', 'dd', function () {
		$('.current').removeClass('current');
		$(this).addClass('current');
		ydl.displayRunning(false);
	});

	if (!ALLOW_EXPAND_MULTI_MENU) {
		//不允许同时展开多个菜单时，初始只展开第一个菜单
		$('dt:gt(0)').nextAll('dd').hide();
		$('dt:gt(0)').addClass('fold');
	}
	
	//点击首页
	$('#homepage').click(function (){
		ydl.displayRunning(false);
	});
	
});