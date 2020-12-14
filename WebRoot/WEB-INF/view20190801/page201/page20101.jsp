<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@page import="com.yondervision.mi.util.Datelet"%> 
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="com.yondervision.mi.common.PermissionContext"%>
<%
  UserContext user=UserContext.getInstance();

  if(user==null)  {
     out.println("超时");
     return;
  }
  PermissionContext pc = PermissionContext.getInstance();
%>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>业务咨询配置</title>
<%@ include file = "/include/styles.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= _contexPath %>/ui/consult/consult.css" />
<style type="text/css">
/*<![CDATA[*/
html,body {width:100%;height:100%;}
#consultType {height:30px;padding:0px 0px 0px 0px; border-top: 0px solid #95B8E7;border-left: 0px solid #95B8E7;border-right: 0px solid #95B8E7;}
#tabs {position: relative; top: 0px; left: 0; list-style: none; padding: 0; font: normal 14px/22px Arial, 微软雅黑, 宋体; color: #0E2D5F; z-index: 10;}
#tabs li {border-top: 1px solid #95B8E7;border-left: 1px solid #95B8E7;border-right: 1px solid #95B8E7;width: 100px; height: 29px; text-align: center; background-color: #E6F0FF; float: left; cursor: pointer; opacity: 0.5;}
#tabs li.selected { background-color: #fff; opacity: 1;}
#consultContent {border: 1px solid #95B8E7;}

/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript">
//<![CDATA[
var curConsultType = {};  //当前业务类型
var enter = 'false';	//是否允许输入回车
$(function () {
	//初始化
	var result = <%=request.getAttribute("result") %>;	//返回结果
	var consultType = result.consultType; //业务类型 //[{itemid:'1',itemval:'缴存'},{itemid:'2',itemval:'提取'},{itemid:'2',itemval:'贷款'}]; 
	var subitemIcon = result.subitemIcon;	//业务咨询子项图标下拉列表数据
	

	//设置宽高
	//$('#consultContent').height($('body').height()-32).width($('body').width()-2);
	//$('#consultContent').width($('body').width()-2);
	
	//初始化图标层
	var icons = subitemIcon;
	$.each(icons,function(index,ele){
		var id = ele.iconId.replace("\.", "-");
		var $img = $('<img src="'+ele.url+'" id="'+id+'" />'); 
		$img.data('data',ele);
		$('#common_icon').append($img);
	});
	
	//点击业务咨询项  查询业务咨询子项
	$('#itemContent').on('click',' li button:nth-child(3)',function(){
		//$('#running-overlay').show();
		ydl.displayRunning(true);
		$('#itemContent li').removeClass('selected-item');
		var $curli = $(this).closest('li');
		$curli.addClass('selected-item');

		//TODO:查询子项信息
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/webapi20102.json",
			dataType: "json",
			data:{consultItemId:$curli[0].id},
			async: false,
			success : function(data) {
				//$('#running-overlay').hide();
				ydl.displayRunning(false);
				//刷新项目咨询子项
				$('#subItemContent ul').children().remove();
				if (data.recode == SUCCESS_CODE || data.recode == BLANK_CODE) {
					//如果有数据
					if (data.rows){
						$.each(data.rows,function (index,ele){
							var id = ele.iconId.replace("\.", "-");
							var srcStr = $('#'+id).length > 0 ? $('#'+id)[0].src : '';
							
							
							var $li = $('<li class="drag-item" id="consub_'+ele.consultSubItemId+'">'+
							'<div class="drag" title="上下拖动调整顺序"></div>'+
							'<h2>'+ele.subitemName.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;')+'</h2>'+
							'<div><img id="'+ele.iconId + '-' + ele.consultSubItemId+'" src="'+srcStr+'" title="选择图标"  />'+
							'<button><span class="icon_symbol"><b>▼</b>选择图标</span></button>'+
							'<button><span class="icon icon_del">删除</span></button>'+
							'<button><span class="icon icon_guide">向导配置...</span></button></div></li>');
							
							
							
							$li.data('data',ele);
							$('#subItemContent ul').append($li);
						});
						drag();
					}
				}
				else $.messager.alert('提示','【查询子项】出错：'+data.msg,'info');
				$('#subItemContent ul').append('<li><button id="addsubItem"><span class="icon icon_add">添加</span></button></li>');
			},
			error :function(){
				//$('#running-overlay').hide();
				ydl.displayRunning(false);
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});
	
	//点击业务类型
	$('#tabs').on('click','li',function (){
		$('#tabs li').removeClass('selected');
		$(this).addClass('selected');
		curConsultType = $(this).data('data');
		//$('#running-overlay').show();
		ydl.displayRunning(true);
		//初始化项目咨询项
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/webapi20101.json",
			dataType: "json",
			data:{centerId:'<%=user.getCenterid()%>',consultType: curConsultType.itemid},
			success : function(data) {
				$('#itemContent ul').children().remove();
				//$('#running-overlay').hide();
				ydl.displayRunning(false);
				if (data.recode == SUCCESS_CODE || data.recode == BLANK_CODE) {
					if (data.rows) {
						//初始化项目咨询项
						$.each(data.rows,function (index,ele){
							var $li = $('<li class="drag-item" id="'+ele.consultItemId+'"><div class="drag" title="上下拖动调整顺序"></div><h2 title="单击进行编辑">'+ele.itemName.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;')+'</h2><div><button><span class="icon icon_del">删除</span></button>'+
									'<button><span class="icon icon_cond">公共条件配置...</span></button>'+
									'<button><span class="icon icon_sub">查询子项</span></button></div></li>');
							$li.data('data',ele);
							$('#itemContent ul').append($li);
						});
						drag();
						$('#itemContent ul').append('<li><button id="addItem"><span class="icon icon_add">添加</span></button></li>');
						$('#itemContent li:first button:nth-child(3)').click();//查询第一个项目咨询项的子项内容
					}
				}
				else $.messager.alert('提示','【查询项目咨询项】出错：'+data.msg,'info');
			},
			error :function(){
				//$('#running-overlay').hide();
				ydl.displayRunning(false);
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});
	
	//初始化业务类型
	$.each(consultType,function (index,ele){
			var $li = $('<li id="'+ele.itemid+'">'+ele.itemval+'</li>');
			$li.data('data',ele);
			$('#tabs').append($li);
	});
	
	//选中第一个业务类型
	$('#tabs li:first').click(); 
	
	
	//单击编辑项目名称
	$('ul').on('click',' li h2',function(event){
		var value = $(this).html().replace(/<br>/g, '\n').replace(/&nbsp;/g,' ').replace(/　　　　/g,'\t');
		var offset = $(this).offset();
		var top = offset.top -32;
		var left = offset.left - 2;
		$('#common_input').data('item',$(this))
		.css('left', left).css('top', top).height($(this).height()).width($(this).width()-2)
		.val(value)
		.show().focus();
		event.stopPropagation();
	});	  
	
	//输入公共组件失去焦点,修改
	$('#common_input').on('blur',function(){
		$(this).hide();
		var $item = $(this).data('item');
		var value = this.value;
		var valuehtml = value.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;');
		//var isChange = $item.html().replace(/<br>/g, '\n') != value; //数据是否更改
		var isChange = $item.html() != valuehtml; //数据是否更改
		if (isChange){
			//修改项目名称
			var url = '';
			var paras = {method:'edit'};//上传参数
			var info  = $item.closest('li').data('data');
			//是否为业务咨询子项
			var issub = info.iconId;
			//校验长度
			if (value.length2() > 100 || value.length2() < 1) {
				$.messager.alert('提示','【'+(issub ? '业务咨询子项名称' : '业务咨询项名称')+'】的长度应在1~100个字符之间，您输入了'+value.length2()+'个字符，请重新输入！','info',function (){
					$('#common_input').show().focus();
				});
				return;
			}
			var name = '';
			//如果是业务咨询子项修改
			if (issub) {
				url = '<%=request.getContextPath()%>/webapi20104.json';
				paras.consultSubItemId = info.consultSubItemId;
				paras.subitemName = value;
				name = 'subitemName';
			}
			//如果是业务咨询项修改
			else {
				url = '<%=request.getContextPath()%>/webapi20103.json';
				paras.consultItemId = info.consultItemId;
				paras.itemName = value;
				name = 'itemName';
				
			}
			$.ajax({
				type : 'POST',
				url : url,
				dataType: 'json',
				data:paras,
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						//value = value.replace(/\r\n|\r|\n/g, '<br/>');
						//value = value.replace(/\r/g, '<br/>');
						//value = value.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '　');
						$item.html(valuehtml);
						$item.closest('li').data('data')[name] = value;
					}
					else $.messager.alert('提示','【修改名称】出错：'+data.msg,'info');
				},
				error :function(){
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
		}
	});
	
	//添加业务咨询项
	//$('#addItem').click(function(){
	$('#itemContent').on('click','#addItem',function(){
		var $curLi = $(this).parent();
		var $lastLi = $(this).parent().prev();
		var paras = {method:'add', centerId:'<%=user.getCenterid()%>', consultType:curConsultType.itemid};//上传参数
		paras.orderNo =  $('#itemContent ul li').length > 1 ? ($lastLi.data('data').orderNo + 1 ): 1;
		paras.itemName = '请输入项目咨询项名称';
		paras.conditionTitle = '';
		$.ajax( {
			type : 'POST',
			url : '<%=request.getContextPath()%>/webapi20103.json',
			dataType: 'json',
			data:paras,
			success : function(data) {
				//$.messager.alert('提示',data.msg,'info');
				if (data.recode == SUCCESS_CODE) {
					var info = {
						consultItemId:data.consultItemId,
						orderNo:paras.orderNo,
						itemName:'请输入业务咨询项名称',
						conditionTitle:''
					}
					
					var $li = $('<li class="drag-item" id="'+info.consultItemId+'"><div class="drag" title="上下拖动调整顺序"></div><h2 title="单击进行编辑">'+info.itemName+'</h2><div><button><span class="icon icon_del">删除</span></button>'+
								'<button><span class="icon icon_cond">公共条件配置...</span></button>'+
								'<button><span class="icon icon_sub">查询子项</span></button></div></li>');
					
					$li.data('data',info);
					$curLi.before($li);
					//$('#itemContent ul').append($li);
					drag(true);
					//考虑滚动条
					$('#itemContent').scrollTop($('#itemContent').scrollTop()+$lastLi.height()+10);

					$('#itemContent ul li h2:last').click();	
					$('#common_input').val('');
				}
				else $.messager.alert('提示','【添加项目咨询项】出错：'+data.msg,'info');
				
			},
			error :function(){
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});
	
	//添加业务咨询子项
	//$('#addsubItem').click(function(){
	$('#subItemContent').on('click','#addsubItem',function(){
		if ($('.selected-item').length == 0) {
			$.messager.alert('提示','请先选择业务咨询项','info');
			return;
		}
		var $lastLi = $(this).parent().prev();
		var $curLi = $(this).parent();
		var paras = {method:'add',iconId:icons[0].iconId};//上传参数
		paras.orderNo =  $('#subItemContent ul li').length > 1 ? ($lastLi.data('data').orderNo + 1) : 1;
		paras.consultItemId = $('.selected-item').data('data').consultItemId;
		//paras.consultItemId = $('#subItemContent ul li:last').data('data').consultItemId;
		paras.subitemName = '请输入业务咨询子项名称';
		
		$.ajax({
			type : 'POST',
			url : '<%=request.getContextPath()%>/webapi20104.json',
			dataType: 'json',
			data:paras,
			success : function(data) {
				//$.messager.alert('提示',data.msg,'info');
				if (data.recode == SUCCESS_CODE) {
					var info = {
						consultSubItemId:data.consultSubItemId,
						consultItemId:paras.consultItemId,
						orderNo:paras.orderNo,
						subitemName:'请输入业务咨询子项名称',
						iconId:paras.iconId
					}
					//TODO:添加时默认图标问题
					var $li = $('<li class="drag-item" id="consub_'+info.consultSubItemId+'">'+
							'<div class="drag" title="上下拖动调整顺序"></div>'+
							'<h2>'+info.subitemName+'</h2>'+
							'<div><img id="'+info.iconId + '-' + info.consultSubItemId+'" src="'+icons[0].url+'" title="点击选择图标"  />'+
							'<button><span class="icon_symbol"><b>▼</b>选择图标</span></button>'+
							'<button><span class="icon icon_del">删除</span></button>'+
							'<button><span class="icon icon_guide">向导配置...</span></button></div></li>');
					$li.data('data',info);
					//$('#subItemContent ul').append($li);
					$curLi.before($li);
					drag(true);
					//考虑滚动条
					$('#subItemContent').scrollTop($('#subItemContent').scrollTop()+$lastLi.height()+10);
					//$('#subItemContent ul').scrollTop($('#subItemContent ul').scrollTop()+$('#subItemContent ul li:last').height()+10);
					$('#subItemContent ul li h2:last').click();
					$('#common_input').val('');
				}
				else $.messager.alert('提示','【添加项目咨询子项】出错：'+data.msg,'info');
			},
			error :function(){
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});
	
	//点击删除业务咨询项
	$('#itemContent').on('click','li.drag-item button:first-child',function (){
		if (!$(this).closest('li').hasClass('selected-item')) {
			$.messager.alert('提示','请先查询子项！','info');
			return;
		}
		//校验无子项时可删除-TODO
		if ($('#subItemContent ul').children().length > 1) {
			$.messager.alert('提示','请先删除选中业务咨询项的所有子项！','info');
			return;
		}
		//开始删除
		var $del_button = $(this);
		$.messager.confirm('提示','是否要删除选中业务咨询项？', function(isDel){
			if (isDel) {
				$.ajax( {
					type : "POST",
					url : "<%=request.getContextPath()%>/webapi20103.json",
					dataType: "json",
					data:{method:'delete',consultItemId:$del_button.closest('li').data('data').consultItemId},
					success : function(data) {
						if (data.recode == SUCCESS_CODE) $del_button.closest('li').remove();
						else $.messager.alert('提示','【删除业务咨询项】出错：'+data.msg,'info');
						//$('#subItemContent ul').children().remove();//清空业务咨询子项
					},
					error :function(){
						$.messager.alert('错误','网络连接出错！','error');
					}
				});
				//$del_button.closest('li').remove();//测试
				//$('#subItemContent ul').children().remove();//清空业务咨询子项,有子项不允许删除
			}
		});
	})
	
	//点击删除业务咨询子项
	$('#subItemContent').on('click','li button:nth-child(3)',function (){
		var $del = $(this);
		$.messager.confirm('提示','是否确认删除此业务咨询子项？', function(isDel){
			if (isDel) {
				$.ajax({
					type : "POST",
					url : "<%=request.getContextPath()%>/webapi20104.json",
					dataType: "json",
					data:{method:'delete',consultSubItemId:$del.closest('li').data('data').consultSubItemId},
					success : function(data) {
						//$(this).closest('li').remove();
						if (data.recode == SUCCESS_CODE) $del.closest('li').remove();
						else $.messager.alert('提示','【删除业务咨询子项】出错：'+data.msg,'info');
					},
					error :function(){
						$.messager.alert('错误','网络连接出错！','error');
					}
				});
			}
		});
	})
	
	//点击图标,打开图标层
	$('#subItemContent').on('click','li.drag-item button:nth-child(2)',function(event){
		//TODO:考虑滚动条
		var offset = $(this).offset();
		var top = offset.top -32;
		var left = offset.left -2;
		$('#common_icon').data('img',$(this).prev())
		.css('left', left).css('top', top+20);
		if ($('#common_icon').is(':hidden')) $('#common_icon').show();
		else $('#common_icon').hide();
		event.stopPropagation();
	});
	
	//点击图标层里的图标进行选择
	$('#common_icon').on('click','img',function(event){
		$('#common_icon').hide();
		var $icon = $('#common_icon').data('img');
		var curIcon = this;
		var isChange = $icon[0].src != this.src; //数据是否更改
		var iconId = $(this).data('data').iconId;
		//判断是否更改图标,修改图标
		if (isChange) {
			var paras = {method:'edit',iconId:iconId,consultSubItemId:$icon.closest('li').data('data').consultSubItemId}
			$.ajax( {
				type : 'POST',
				url : '<%=request.getContextPath()%>/webapi20104.json',
				dataType: 'json',
				data:paras,
				success : function(data) {
					if (data.recode == SUCCESS_CODE) $icon[0].src = curIcon.src;
					else $.messager.alert('提示','【修改业务咨询子项图标】出错：'+data.msg,'info');
				},
				error :function(){
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
		}
		event.stopPropagation();
	});
	
	//取消选择图标
	$('body').on('click',function(){
		$('#common_icon').hide();
	});
	
	
	
	//公共条件配置
	$('#itemContent').on('click','li button:nth-child(2)',function (event){
		//$('#running-overlay').show();
		ydl.displayRunning(true);
		var data = $(this).closest('li').data('data');
		//添加选中状态
		$('#itemContent li').removeClass('selected-item');
		var $curli = $(this).closest('li');
		$curli.addClass('selected-item');
		
		//alert(data.itemName)
		$('#condition_content')[0].src='page20111.html?consultItemId='+data.consultItemId+ //后台需要，业务咨询项id
			'&curConsultType='+encodeURI(curConsultType.itemval)+ 	//当前业务类型名称
			'&conditionTitle='+encodeURI(data.conditionTitle)+		//公共条件标题
			'&itemName='+encodeURI(data.itemName);					//当前业务咨询项名称

		$('#conditioninfo').show();
		event.stopPropagation();
	});
	
	//公共条件配置关闭按钮
	$('#condition_close').click(function () {
		$('#conditioninfo').hide();
		$('#condition_content')[0].src='about:blank';
	});
	
	//业务咨询向导配置（步骤）
	$('#subItemContent').on('click','li button:nth-child(4)',function (){
		//$('#running-overlay').show();
		ydl.displayRunning(true);
		var data = $(this).closest('li').data('data');
		$('#step_content')[0].src='page20121.html?consultSubItemId='+data.consultSubItemId+	//后台用：业务咨询子项id
			'&curConsultType='+encodeURI(curConsultType.itemval)+ 							//当前业务类型名称
			'&itemName='+encodeURI($('li.selected-item h2').text())+						//当前业务咨询项名称
			'&consultItemId='+$('li.selected-item').data('data').consultItemId +			//当前业务咨询项ID
			'&subitemName='+encodeURI(data.subitemName);									//当前业务咨询子项名称
		$('#stepinfo').show();
	});
	
	//业务咨询向导配置（步骤）关闭按钮
	$('#step_close').click(function () {
		$('#stepinfo').hide();
		$('#step_content')[0].src='about:blank';
	});
	
	//滚动条事件 
	//$('.item-content').on('scroll',function(){
	//	if ($('#common_input').is(':visible')) $('#common_input').blur();
	//});
	
	//不允许输入回车
	$('#common_input').keydown(function (e){
		if (enter == 'false' && e.keyCode == '13') return false;
	});
});

//注册拖拽排序事件
function drag(isAdd){
	//不重复创建指示器
	var $indicator = $('.indicator');
	if ($indicator.length == 0) $indicator = $('<img class="indicator" src="<%=_contexPath%>/ui/consult/icon_arrow.png" />').appendTo('body');
	//var selector = isAdd ? '.drag-item:last' : '.drag-item';
	var selector = '.drag-item';
	$(selector).draggable({
		revert: true,
		proxy:function(source){
			var $liClone = $('<li class="drag-item" style="background-color: #FFFFAA;width:'+$(source).width()+'px;opacity:0.8;"></li>');
			$liClone.html($(source).html()).appendTo($(source).closest('ul'));
			return $liClone;
		},
		cursor: 'n-resize',
		deltaX: 0,
		deltaY: 0,
		axis: 'v',
		handle: ' div',
		onBeforeDrag: function (e) {
			if (e.target.tagName != 'DIV') return false;
		},
		onStartDrag: function (e) {
			//$(e.target).closest('li').addClass('ondrag');
		},
		onStopDrag: function (e) {
			//$(e.target).closest('li').removeClass('ondrag');
		}
	}).droppable({
		//accept:'.drag-item',
		onDragOver: function(e,source){
			$indicator.css({
				display: 'block',
				left: $(this).offset().left - 10,
				top: $(this).offset().top + $(this).outerHeight() - 7
			});
		},
		onDragLeave:function(e,source){
			$indicator.hide();
		},
		onDrop:function(e,source){
			$indicator.hide();
			if ($(source).closest('div')[0].id != $(this).closest('div')[0].id) return false;
			//alert ($(this).closest('div')[0].id)
			//被拖拽元素信息
			var info = $(source).data('data');
			var targetInfo = $(this).data('data');
			var $target = $(this);
			//排序上传参数
			var paras = {
				method:'sort',
				sourceOrderNo:info.orderNo,
				targetOrderNo: targetInfo.orderNo
			};
			
			//有点诡异的问题，
			if (paras.sourceOrderNo > paras.targetOrderNo) paras.targetOrderNo = paras.targetOrderNo +1;
			
			var url = '';
			var itemid = '';
			//是否为业务咨询子项
			var issub = info.iconId;
			//如果是业务咨询子项修改
			if (issub) {
				paras.consultItemId = info.consultItemId;
				url = '<%=request.getContextPath()%>/webapi20104.json';
				itemid = 'subItemContent';
			}
			//如果是业务咨询项修改
			else {
				url = '<%=request.getContextPath()%>/webapi20103.json';
				paras.centerId = '<%=user.getCenterid()%>';
				paras.consultType = curConsultType.itemid;
				itemid = 'itemContent';
			}	
			var targetLi = this;
			$.ajax({
				type : 'POST',
				url : url,
				dataType: 'json',
				data:paras,
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						var sourceIndex = $('#'+itemid + ' ul li').index($(source)); 
						var targetIndex = $('#'+itemid + ' ul li').index($target); 
						//向上移-重新排序赋值
						if (sourceIndex > targetIndex) {
							$('#'+itemid + ' ul li').each(function (index){
								if (index > targetIndex && index < sourceIndex +1 ) $(this).data('data').orderNo = $(this).data('data').orderNo +1;
							});
						}
						//向下移-重新排序赋值
						else {
							$('#'+itemid + ' ul li').each(function (index){
								if (index > sourceIndex && index < targetIndex +1 ) $(this).data('data').orderNo = $(this).data('data').orderNo -1;
							});
						}
						$(source).data('data').orderNo = paras.targetOrderNo;
						//元素移动
						$(source).insertAfter(targetLi);
						//$indicator.hide();
						
					}
					else $.messager.alert('提示','【排序】出错：'+data.msg,'info');
				},
				error :function(){
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
		}
	});
}

//]]></script>
</head>
<body>
<!-- 业务咨询配置  -->
<div id="consultType"><ul id="tabs"></ul></div>
<div id="consultContent" class="easyui-layout">
	<div id="consultItemId" data-options="region:'west',split:false,border:false">
		<div class="phonebg">
			<div class="screenbg">
				<h1 id="itemTitle" class="item-title">业务咨询项</h1>
				<div id="itemContent" class="item-content">
					<ul>
						<li><button id="addItem"><span class="icon icon_add">添加</span></button></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div id="consultSubItemId" data-options="region:'center',border:false">
		<div class="phonebg">
			<div class="screenbg">
				<h1 id="subItemTitle" class="item-title">业务咨询子项</h1>
				<div id="subItemContent" class="item-content">
					<ul>
						<li><button id="addsubItem"><span class="icon icon_add">添加</span></button></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<textarea type="text" id="common_input"></textarea>
	<div id="common_icon"></div>
</div>
<!-- 公共条件配置  -->
<div id="conditioninfo">
	<img id="condition_close" alt="关闭" title="关闭" src="<%=_contexPath%>/ui/consult/close.png" />
	<iframe id="condition_content" frameborder="0" allowtransparency="true" src="about:blank"></iframe>
</div>
<!-- 业务咨询向导配置（步骤） -->
<div id="stepinfo">
	<img id="step_close" alt="关闭" title="关闭" src="<%=_contexPath%>/ui/consult/close.png" />
	<iframe id="step_content" frameborder="0" allowtransparency="true" src="about:blank"></iframe>
</div>
</body>
</html>
