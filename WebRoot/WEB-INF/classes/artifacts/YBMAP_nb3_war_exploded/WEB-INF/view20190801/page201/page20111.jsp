<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>公共条件配置</title>
<%@ include file = "/include/styles.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= _contexPath %>/ui/consult/condition.css" />
<style type="text/css">
/*<![CDATA[*/

/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript">//<![CDATA[
var consultItemId;
var enter = 'false';	//是否允许输入回车
$(function () {
	
	//初始化
	var conditionItems = <%=request.getAttribute("rows") %>;	//返回结果
	//parent.$('#running-overlay').hide(); //隐藏
	ydl.displayRunning(false);
	//初始化 取值赋值
	var field = decodeURI(location.search);
	var consultType = field.replace(/.*&curConsultType=(.*?)&.*/g, "$1");	//业务类型名称
	//consultItemId = field.replace(/^\?consultItemId=(.*?)&.*/g, "$1");	//业务咨询项id
	consultItemId = '<%=request.getParameter("consultItemId") %>';		//业务咨询项ID
	var itemName = field.replace(/.*&itemName=(.*?)/g, "$1");				//业务咨询项名称
	$('#title').text(consultType+' → '+itemName);							//设置title
	var conditionTitle = field.replace(/.*&conditionTitle=(.*?)&.*/g, "$1");//业务咨询项中的 【公共条件标题】
	if (conditionTitle == 'null') conditionTitle = '';
	$('#conditionTitle').val(conditionTitle);								//设置默认公共条件标题,可修改
	
	//修改公共条件标题
	$('#conditionTitle').change(function (){
		var value = this.value;
		//校验长度
		if (value.length2() > 50) {
			$.messager.alert('提示','【咨询条件标题】的长度应在0~50个字符之间，您输入了'+value.length2()+'个字符，请重新输入！','info');
			return;
		}
		//上传参数
		var paras = {method:'edit',consultItemId:consultItemId,conditionTitle:value};
		$.ajax({
			type : 'POST',
			url : '<%=request.getContextPath()%>/webapi20103.json',
			dataType: 'json',
			data:paras,
			success : function(data) {
				if (data.recode == SUCCESS_CODE ){
					parent.$('#itemContent li.selected-item').data('data').conditionTitle = value;
					$.messager.alert('提示',data.msg,'info');
				}
				else $.messager.alert('提示','【修改公共条件标题】出错：'+data.msg,'info');
			},
			error :function(){
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});
	
	
	//点击公共条件项目,查询咨询条件
	$('#conItemContent').on('click',' li button:nth-child(2)',function(){
		//parent.$('#running-overlay').show();
		ydl.displayRunning(true);
		$('#conItemContent li').removeClass('selected-item');
		var $curli = $(this).closest('li');
		$curli.addClass('selected-item');
		var curli = $curli[0];
		var info = {};
		var isAll = false;
		var paras = {consultItemId:consultItemId};
		/*
		if (this.id != 'allitem') {
			info = $(this).data('data');
			paras.conditionItemId = info.conditionItemId;
			$('#addgroupItem').data('conditionItemId',info.conditionItemId ||'');
		}
		else {
			$('#addgroupItem').data('conditionItemId','all');
			isAll = true;
		}
		*/
		info = $curli.data('data');
		paras.conditionItemId = info.conditionItemId;
		
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/webapi20111.json",
			dataType: "json",
			data:paras,
			success : function(data) {
				//parent.$('#running-overlay').hide();
				ydl.displayRunning(false);
				//刷新
				$('#groupItemContent ul').children().remove();
				if (data.recode == SUCCESS_CODE || data.recode == BLANK_CODE) {
					//如果有数据
					if (data.rows){
						var lastItemId = null;
						$.each(data.rows,function (index,ele){
							/*
							//如果查询全部，添加分类信息
							if (isAll && (ele.conditionItemId != lastItemId)) {
								$('#groupContent').append('<li class="itemTitle"><h1>'+$('#con_'+ele.conditionItemId +' h2').text()+'</h1></li>');
								lastItemId = ele.conditionItemId;
							}
							*/
							//添加分组与内容信息
							var $li = $('<li id="group_'+ele.conditionGroupId+'"><div><h2>'+ele.groupName.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;')+'</h2>'+
							'<span title="删除" class="icon icon_del" ></span><img src="<%=_contexPath%>/ui/consult/blank.gif" class="up" title="上移"/>'+
							'<img src="<%=_contexPath%>/ui/consult/blank.gif" class="down" title="下移"/></div>'+
							'<ul class="detail"></ul></li>');
							$.each(ele.listMi305,function (i,obj){
								$detailLi = $('<li class="drag-item"><div class="drag" title="上下拖动调整顺序"></div><h3>'+
								obj.conditionDetail.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;')+'</h3><span  title="删除" style="visibility: hidden;" class="icon icon_del"></span></li>');
								$detailLi.data('data',obj);
								$li.find('ul').append($detailLi);
								//drag();
							});
							
							$li.find('ul').append('<li class="addDetail"><span class="icon icon_add"></span><h3>点击此处添加公共条件内容</h3></li>');
							$li.data('data',ele);
							$('#groupContent').append($li);

							//drag();
						});
						drag();
					}
					
				}
				else $.messager.alert('提示',data.msg,'info');
				$('#groupContent').append('<li><button id="addgroupItem"><span class="icon icon_add">添加</span></button></li>');//添加按钮
			},
			error :function(){
				//parent.$('#running-overlay').hide();
				ydl.displayRunning(false);
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
		$('#addgroupItem').data('conditionItemId',info.conditionItemId ||'');
	});
	
	
	//查询全部
	$('#conItemContent').on('click','#allitem',function (){
		//parent.$('#running-overlay').show();
		ydl.displayRunning(true);
		$('#conItemContent li').removeClass('selected-item');
		var $curli = $(this).closest('li');
		$curli.addClass('selected-item');
		var paras = {consultItemId:consultItemId};
		$('#addgroupItem').data('conditionItemId','all');
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/webapi20111.json",
			dataType: "json",
			data:paras,
			success : function(data) {
				//parent.$('#running-overlay').hide();
				ydl.displayRunning(false);
				//刷新
				$('#groupItemContent ul').children().remove();
				if (data.recode == SUCCESS_CODE || data.recode == BLANK_CODE) {
					//如果有数据
					if (data.rows){
						var lastItemId = null;
						$.each(data.rows,function (index,ele){
							//添加分类信息
							if (ele.conditionItemId != lastItemId) {
								$('#groupContent').append('<li class="itemTitle"><h1>'+$('#con_'+ele.conditionItemId +' h2').text()+'</h1></li>');
								lastItemId = ele.conditionItemId;
							}
							//添加分组与内容信息
							var $li = $('<li class="view" id="group_'+ele.conditionGroupId+'"><div><h2>'+ele.groupName.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;')+'</h2>'+
							'</div>'+
							'<ul class="detail"></ul></li>');
							$.each(ele.listMi305,function (i,obj){
								$detailLi = $('<li class="view"><span class="icon icon_info"></span><h3>'+
								obj.conditionDetail.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;')+'</h3></li>');
								$detailLi.data('data',obj);
								$li.find('ul').append($detailLi);
							});
							$('#groupContent').append($li);
						});
					}
				}
				else $.messager.alert('提示',data.msg,'info');
			},
			error :function(){
				//parent.$('#running-overlay').hide();
				ydl.displayRunning(false);
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});
	
	//初始化【公共条件项目】
	$.each(conditionItems,function (index,ele){
		var $li = $('<li class="drag-item" id="con_'+ele.conditionItemId+'"><div class="drag" title="上下拖动调整顺序"></div><h2 title="单击进行编辑">'+
		ele.conditionItemName.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;')+'</h2>'+
		'<div><button class="del"><span class="icon icon_del">删除</span></button>'+
		'<button><span class="icon icon_sub">查询咨询条件</span></button></div></li>');
		$li.data('data',ele);
		$('#conItemContent ul').append($li);
		//drag();
	});
	$('#conItemContent ul').append('<li><button id="addconItem"><span class="icon icon_add">添加</span></button></li>');//添加按钮
	drag();
	$('#conItemContent #allitem').click();//查询全部公共条件项目的分组和内容
	
	//浮动到公共条件分组和内容
	$('#groupItemContent').on('mouseover','li ul li.drag-item',function (){
		$(this).find('span').css('visibility', 'visible');
	}).on('mouseout','li ul li.drag-item',function (){
		$(this).find('span').css('visibility', 'hidden');
	}); 	
	
	//单击编辑项目名称
	$('ul').on('click',' li:not(.view) h2,li:not(.view) h3',function(event){
		//设置高度与是否允许输入回车
		if (this.tagName == 'H3') {
			enter = 'true';
			if ($(this).height() < 48) $(this).css('height','48px');
		}
		else {
			enter = 'false';
			if ($(this).height() < 38) $(this).css('height','38px');
		}
		
		//设置公共组件的字体
		if ($(this).closest('#conItemContent').length > 0) $('#common_input').css('font','14px 微软雅黑,宋体');
		else $('#common_input').css('font','12px 微软雅黑,宋体');
		
		//var value = $(this).html().replace(/<br>/g, '\n');
		var value = $(this).html().replace(/<br>/g, '\n').replace(/&nbsp;/g,' ').replace(/　　　　/g,'\t');
		var isAdd = $(this).parent().hasClass('addDetail');
		if (isAdd) value = '';
		var offset = $(this).offset();
		var top = offset.top-1;
		var left = offset.left-1;
		$('#common_input').data('item',$(this))
		.css('left', left).css('top', top).height($(this).height()).width($(this).width()-4)
		.val(value)
		.show().focus();
		
		event.stopPropagation();
	});	 
	
	//输入公共组件失去焦点,修改
	$('#common_input').on('blur',function(){
		$(this).hide();
		var $item = $(this).data('item');
		
		if ($item.parent()[0].tagName == 'DIV' || $item[0].tagName == 'H3') $item.css('height','auto');
		
		var value = this.value;
		var valuehtml = value.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;');
		var isChange = $item.html() != valuehtml; //数据是否更改
		//var isChange = $item.html().replace(/<br>/g, '\n') != value; //数据是否更改
		if ($item.parent().hasClass('addDetail')) isChange = value != '';
		
		if (isChange){
			var paras = {};
			//添加公共条件内容
			if ($item[0].tagName == 'H3' && $item.parent().hasClass('addDetail')){
				paras = {method:'add',consultItemId:consultItemId,conditionDetail:value};
				paras.conditionGroupId = $item.closest('ul').parent().data('data').conditionGroupId;
				paras.noOreder = $item.closest('ul').children().length > 1 ? $item.parent().prev().data('data').noOreder + 1 : 1;
				//添加
				$.ajax({
					type : 'POST',
					url : '<%=request.getContextPath()%>/webapi20114.json',
					dataType: 'json',
					data:paras,
					success : function(data) {
						if (data.recode == SUCCESS_CODE) {
							value = value.replace(/\n/g, '<br>');
							//$item.html(value);
							var info = $.extend({conditionId:data.conditionId},paras);
							var $newLi = $('<li class="drag-item"><div class="drag" title="上下拖动调整顺序"></div><h3>'+
								value+'</h3><span  title="删除" style="visibility: hidden;" class="icon icon_del"></span></li>');
							$newLi.data('data',info);
							$item.parent().before($newLi);;
							drag(true);
						}
						else $.messager.alert('提示',data.msg,'info');
						
					},
					error :function(){
						$.messager.alert('错误','网络连接出错！','error');
					}
				});
			}
			//修改名称
			else {
				paras = {method:'edit'};//上传参数
				var url = ''
				var info = $item.closest('li').data('data');
				var itemName = '';
				var maxLength = 100;
				//修改公共条件分组
				if ($item.parent()[0].tagName == 'DIV') {
					paras.conditionGroupId = info.conditionGroupId;
					paras.groupName = value;
					url = '<%=request.getContextPath()%>/webapi20113.json';
					itemName = '公共条件分组名称';
				}
				//修改公共条件内容
				else if ($item[0].tagName == 'H3'){
					paras.conditionId = info.conditionId;
					paras.conditionDetail = value;
					url = '<%=request.getContextPath()%>/webapi20114.json';
					itemName = '公共条件内容';
					maxLength = 500;
				}
				//修改公共条件项目
				else {
					paras.conditionItemId = info.conditionItemId;
					paras.conditionItemName = value;
					url = '<%=request.getContextPath()%>/webapi20112.json';
					itemName = '公共条件项目名称';
				}
				
				//校验长度
				if (value.length2() > maxLength || value.length2() < 1) {
					$.messager.alert('提示','【'+itemName+'】的长度应在1~'+maxLength+'个字符之间，您输入了'+value.length2()+'个字符，请重新输入！','info',function (){
						$('#common_input').show().focus();
					});
					return;
				}
				
				//修改
				$.ajax({
					type : 'POST',
					url : url,
					dataType: 'json',
					data:paras,
					success : function(data) {
						
						if (data.recode == SUCCESS_CODE) {
							//value = value.replace(/\n/g, '<br>');
							//$item.html(value);
							$item.html(valuehtml);
						}
						else $.messager.alert('提示',data.msg,'info');
					},
					error :function(){
						$.messager.alert('错误','网络连接出错！','error');
					}
				});
			}
		}
	});
	
	//添加公共条件项目（分类）
	//$('#addconItem').click(function(){
	$('#conItemContent').on('click','#addconItem',function(){
		var $curLi = $(this).parent();
		var $lastLi = $(this).parent().prev();
		//上传参数
		var paras = {method:'add', consultItemId:consultItemId};
		paras.itemNo =  $('#conItemContent ul li').length > 2 ? ($lastLi.data('data').itemNo + 1 ): 1;
		paras.conditionItemName = '请输入公共条件项目名称';
		$.ajax({
			type : 'POST',
			url : '<%=request.getContextPath()%>/webapi20112.json',
			dataType: 'json',
			data:paras,
			success : function(data) {
				if (data.recode == SUCCESS_CODE) {
					var info = {
						conditionItemId:data.conditionItemId,
						itemNo:paras.itemNo,
						conditionItemName:paras.conditionItemName,
						consultItemId:consultItemId
					}
					
					var $li = $('<li class="drag-item" id="con_'+info.conditionItemId+'"><div class="drag" title="上下拖动调整顺序"></div><h2 title="单击进行编辑">'+
						info.conditionItemName+'</h2>'+
						'<div><button class="del"><span class="icon icon_del">删除</span></button>'+
						'<button><span class="icon icon_sub">查询咨询条件</span></button></div></li>');
					
					$li.data('data',info);
					//$('#conItemContent ul').append($li);
					$curLi.before($li);
					drag(true);
					//考虑滚动条
					//$('#conItemContent ul').scrollTop($('#conItemContent ul').scrollTop()+$('#conItemContent ul li:last').height()+10);
					$('#conItemContent').scrollTop($('#conItemContent').scrollTop()+$lastLi.height()+10);
					$('#conItemContent ul li h2:last').click();	
					$('#common_input').val('');
				}
				else $.messager.alert('提示',data.msg,'info');
				
			},
			error :function(){
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});
	
	//删除公共条件项目（分类）
	//$('#conItemContent').on('click','li span',function (){
	$('#conItemContent').on('click','li.drag-item button:first-child',function (){
		if (!$(this).closest('li').hasClass('selected-item')) {
			$.messager.alert('提示','请先查询咨询条件！','info');
			return;
		}
		//校验无分组时可删除
		if ($('#groupItemContent ul').children().length > 1) {
			$.messager.alert('提示','请先删除选中公共条件的所有分组！','info');
			return;
		}
		//开始删除
		var $del_span = $(this);
		$.messager.confirm('提示','是否要删除选中公共条件分类？', function(isDel){
			if (isDel) {
				$.ajax( {
					type : "POST",
					url : "<%=request.getContextPath()%>/webapi20112.json",
					dataType: "json",
					data:{method:'delete',conditionItemId:$del_span.closest('li').data('data').conditionItemId},
					success : function(data) {
						if (data.recode == SUCCESS_CODE) $del_span.closest('li').remove();
						else $.messager.alert('提示',data.msg,'info');
						//$('#groupItemContent ul').children().remove();//清空所有分组，有分组不允许删除
					},
					error :function(){
						$.messager.alert('错误','网络连接出错！','error');
					}
				});
			}
		});
	})
	
	//添加公共条件分组 
	//$('#addgroupItem').click(function(){
	$('#groupContent').on('click','#addgroupItem',function(){
		//$('.selected-item').data('data') drag-item
		if ($('.selected-item').length == 0) {
			$.messager.alert('提示','请先选择公共条件咨询分类！','info');
			return;
		}
		var conditionItemId = $('.selected-item').data('data').conditionItemId;
		var $curLi = $(this).parent();
		var $lastLi = $curLi.prev();
		var paras = {method:'add',consultItemId:consultItemId};//上传参数
		paras.noOreder =  $('#groupContent li').length > 1 ? ($lastLi.data('data').noOreder + 1) : 1;
		paras.conditionItemId = conditionItemId;
		paras.groupName = '请输入公共条件分组名称';
		//添加
		$.ajax({
			type : 'POST',
			url : '<%=request.getContextPath()%>/webapi20113.json',
			dataType: 'json',
			data:paras,
			success : function(data) {
				if (data.recode == SUCCESS_CODE) {
					var info = {
						conditionGroupId: data.conditionGroupId ,
						consultItemId: consultItemId,
						conditionItemId:paras.conditionItemId,
						noOreder:paras.noOreder,
						groupName:paras.groupName
					}
					//TODO:
					var $li = $('<li id="group_'+info.conditionGroupId+'"><div><h2>'+info.groupName+'</h2>'+
					'<span title="删除" class="icon icon_del" ></span><img src="<%=_contexPath%>/ui/consult/blank.gif" class="up" title="上移"/>'+
					'<img src="<%=_contexPath%>/ui/consult/blank.gif" class="down" title="下移"/></div>'+
						'<ul class="detail"><li class="addDetail"><span class="icon icon_add"></span><h3>点击此处添加公共条件内容</h3></li></ul></li>');
					$li.data('data',info);
					$curLi.before($li);
					//考虑滚动条
					$('#groupItemContent').scrollTop($('#groupItemContent').scrollTop()+$lastLi.height()+10);
					$('#groupItemContent ul li h2:last').click();	
					$('#common_input').val('');
					
				}
				else $.messager.alert('提示',data.msg,'info');
				
			},
			error :function(){
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});
	
	//删除公共条件分组
	$('#groupContent').on('click','li div span',function (){
		if($('#addgroupItem').data('conditionItemId') == '') $.messager.alert('提示','请先选择公共咨询条件分类~','info');
		else {
			//判断有没有公共内容
			if ($(this).parent().next().children().length > 1) $.messager.alert('提示','请先删除此公共条件分组下的所有公共内容~','info');
			else {
				var info = $(this).closest('li').data('data');
				var $del_span = $(this);
				$.messager.confirm('提示','是否确认删除此公共条件分组？', function(isDel){
					if (isDel) {
						$.ajax({
							type : "POST",
							url : "<%=request.getContextPath()%>/webapi20113.json",
							dataType: "json",
							data:{method:'delete',conditionGroupId:info.conditionGroupId},
							success : function(data) {
								if (data.recode == SUCCESS_CODE) $del_span.closest('li').remove();
								else $.messager.alert('提示',data.msg,'info');
							},
							error :function(){
								$.messager.alert('错误','网络连接出错！','error');
							}
						});
					}
				});
			}
		}
	});

	//删除公共条件内容
	$('#groupContent').on('click','.detail li.drag-item span',function (){
		if($('#addgroupItem').data('conditionItemId') == '') $.messager.alert('提示','请先选择公共咨询条件分类','info');
		else {
			var info = $(this).closest('li').data('data');
			var $del_span = $(this);
			$.messager.confirm('提示','是否确认删除此公共条件内容？', function(isDel){
				if (isDel) {
					$.ajax({
						type : "POST",
						url : "<%=request.getContextPath()%>/webapi20114.json",
						dataType: "json",
						data:{method:'delete',conditionId:info.conditionId},
						success : function(data) {
							if (data.recode == SUCCESS_CODE) $del_span.closest('li').remove();
							else $.messager.alert('提示',data.msg,'info');
						},
						error :function(){
							$.messager.alert('错误','网络连接出错！','error');
						}
					});
				}
			});
		}
	});
	
	//公共分组排序 -【上移】
	$('#groupContent').on('click','img.up',function (){
		var $groupMoveUp = $(this).closest('li');
		if($groupMoveUp.prev().length > 0){
			//上传参数
			var paras = {
				method: 'sort',
				conditionItemId: $groupMoveUp.data('data').conditionItemId, 
				sourceOrderNo: $groupMoveUp.data('data').noOreder,
				targetOrderNo:$groupMoveUp.prev().data('data').noOreder
			}
			$.ajax({
				type : "POST",
				url : "<%=request.getContextPath()%>/webapi20113.json",
				dataType: "json",
				data : paras,
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$groupMoveUp.data('data').noOreder = paras.targetOrderNo;
						$groupMoveUp.prev().data('data').noOreder = paras.sourceOrderNo;
						$groupMoveUp.prev().before($groupMoveUp);
					}
					else $.messager.alert('提示',data.msg,'info');
				},
				error :function(){
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
			
		}
		else alert("已经是最顶端，无法上移！");
	});
	//公共分组排序 -【下移】
	$('#groupContent').on('click','img.down',function (){
		var $groupMoveDown = $(this).closest('li');
		if($groupMoveDown.next().length > 0){
			//上传参数
			var paras = {
				method: 'sort',
				conditionItemId: $groupMoveDown.data('data').conditionItemId, 
				sourceOrderNo: $groupMoveDown.data('data').noOreder,
				targetOrderNo:$groupMoveDown.next().data('data').noOreder
			}
			$.ajax({
				type : "POST",
				url : "<%=request.getContextPath()%>/webapi20113.json",
				dataType: "json",
				data : paras,
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$groupMoveDown.data('data').noOreder = paras.targetOrderNo;
						$groupMoveDown.next().data('data').noOreder = paras.sourceOrderNo;
						$groupMoveDown.next().after($groupMoveDown);
					}
					else $.messager.alert('提示',data.msg,'info');
				},
				error :function(){
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
		}
		else alert("已经是最下端，无法下移！");
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
			//被拖拽元素信息
			var sourceInfo = $(source).data('data');
			var targetInfo = $(this).data('data');
			//目标元素
			var targetLi = this; 
			var $target = $(this);
			//上传参数
			var paras = {method:'sort'};
			var url = '';
			var $lis;
			var orderNo;
			//公共条件项目排序 
			if ($(source).closest('#conItemContent').length >0) {
				paras.consultItemId = consultItemId;
				paras.sourceOrderNo = sourceInfo.itemNo;
				paras.targetOrderNo = targetInfo.itemNo;
				url = '<%=request.getContextPath()%>/webapi20112.json';
				$lis = $('#conItemContent ul li');
				orderNo = 'itemNo';
			}
			//公共条件内容排序
			else {
				paras.conditionGroupId = sourceInfo.conditionGroupId;
				paras.sourceOrderNo = sourceInfo.noOreder;
				paras.targetOrderNo = targetInfo.noOreder;
				url = '<%=request.getContextPath()%>/webapi20114.json';
				$lis = $(source).parent().children();
				orderNo = 'noOreder';
			}
			//有点诡异的问题，
			if (paras.sourceOrderNo > paras.targetOrderNo) paras.targetOrderNo = paras.targetOrderNo +1;
			//排序
			$.ajax({
				type : 'POST',
				url : url,
				dataType: 'json',
				data:paras,
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						var sourceIndex = $lis.index($(source)); 
						var targetIndex = $lis.index($target); 
						//向上移-重新排序赋值
						if (sourceIndex > targetIndex) {
							$lis.each(function (index){
								if (index > targetIndex && index < sourceIndex +1 ) $(this).data('data')[orderNo] = $(this).data('data')[orderNo] +1;
							});
						}
						//向下移-重新排序赋值
						else {
							$lis.each(function (index){
								if (index > sourceIndex && index < targetIndex +1 ) $(this).data('data')[orderNo] = $(this).data('data')[orderNo] -1;
							});
						}
						$(source).data('data')[orderNo] = paras.targetOrderNo;
						
						//$lis.each(function (index){
						//	if ($(this).data('data')) alert($(this).data('data')[orderNo])
						//});
						$(source).insertAfter(targetLi);
						//$indicator.hide();
						
					}
					else $.messager.alert('提示',data.msg,'info');
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
<div class="title"><h1 id="title">业务类型->业务咨询项</h1></div>
<div id="condTitle">
	<label for="conditionTitle">业务咨询项公共条件标题：</label><input id="conditionTitle" type="text" value="" maxlength="50"/>
</div>
<div id="conditionContent" class="easyui-layout">
	<div id="conditionItemId" data-options="region:'west',split:false,border:false">
		<div class="phonebg">
			<div class="screenbg">
				<h1 id="conTitle" class="item-title">公共咨询条件分类</h1>
				<div id="conItemContent" class="item-content">
					<ul>
						<li><button id="allitem"><span class="icon icon_search">全部分类</span></button></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div id="groupItemId" data-options="region:'center',border:false">
		<div class="phonebg">
			<div class="screenbg">
				<h1 id="groupTitle" class="item-title">公共咨询条件</h1>
				<div id="groupItemContent" class="item-content">
					<ul id="groupContent">
						<li><button id="addgroupItem"><span class="icon icon_add">添加</span></button></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<textarea type="text" id="common_input"></textarea> 
</body>
</html>
