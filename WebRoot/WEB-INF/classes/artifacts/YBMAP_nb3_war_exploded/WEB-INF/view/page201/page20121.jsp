<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>业务咨询向导配置（步骤）</title>
<%@ include file = "/include/styles.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= _contexPath %>/ui/consult/step.css" />
<style type="text/css">
/*<![CDATA[*/

/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript">//<![CDATA[
var consultSubItemId = '';
var enter = 'false';	//是否允许输入回车
$(function () {
	//初始化
	var stepInfos = <%=request.getAttribute("rows") %>;	//返回结果
	//parent.$('#running-overlay').hide(); //隐藏
	ydl.displayRunning(false);
	//初始化 取值赋值
	var field = decodeURI(location.search);
	//consultSubItemId = field.replace(/^\?consultSubItemId=(.*?)&.*/g, "$1");		//业务咨询子项id
	//var consultItemId = field.replace(/.*&consultItemId=(.*?)&.*/g, "$1");			//业务咨询项ID
	var consultType = field.replace(/.*&curConsultType=(.*?)&.*/g, "$1");			//业务类型名称
	var itemName = field.replace(/.*&itemName=(.*?)&.*/g, "$1");					//业务咨询项名称
	var subitemName = field.replace(/.*&subitemName=(.*?)/g, "$1");					//业务咨询子项名称
	
	consultSubItemId = '<%=request.getParameter("consultSubItemId") %>';	//业务咨询子项id
	var consultItemId = '<%=request.getParameter("consultItemId") %>';		//业务咨询项ID
	/*
	var consultType = '<%=new String(request.getParameter("curConsultType").toString().getBytes("ISO8859-1"), "UTF-8" ) %>';	
	var itemName = '<%=new String(request.getParameter("itemName").toString().getBytes("ISO8859-1"), "UTF-8" ) %>';
	var subitemName = '<%=new String(request.getParameter("subitemName").toString().getBytes("ISO8859-1"), "UTF-8" ) %>'.replace(/\n/g,'');
	*/
	
	//设置title
	$('#title h1').text(consultType+' → '+itemName+' → '+subitemName);				
	//设置按钮状态
	$('#b-set').linkbutton('disable');
	$('#subContent').hide();
	
	//点击向导步骤
	$('#stepInfo').on('click',' li',function(event){
		if($(this).hasClass('addStep')) return;
		
		$('#stepInfo li').removeClass('selected-item');
		$(this).addClass('selected-item');
		var $curli = $(this);
		//TODO:查询
		var info = $curli.data('data');
		//$('#conDetail').treegrid('load',{stepId:info.stepId});
		//url:'<%=request.getContextPath()%>/webapi20121.json',
		//设置按钮状态
		$('#b-set').linkbutton('disable');
		$('#conDetail').treegrid('options').url = '<%=request.getContextPath()%>/webapi20121.json';
		$('#conDetail').treegrid('options').queryParams.stepId = info.stepId;//修改查询条件
		$('#conDetail').treegrid('reload');
		$('#b-add').data('stepId',info.stepId);
		//清空业务内容子级显示
		//$('#subContent').children().remove();
		$('#subContent').hide();
		event.stopPropagation();
		
	});
	

	//业务咨询向导查询列表
	$('#conDetail').treegrid({
		title:'业务咨询向导内容',
		iconCls:'icon-tip',
		height:360,
		singleSelect: true,
		rownumbers: true,
		method: 'post',
		idField:'consultId',
		treeField:'detail',
		columns:[[
			{title:'选择',field:'consultId',width:40,align:'center',formatter : function(value){
				return '<input type="checkbox" id="'+value+'" class="del"/>';
			}},
			{title:'业务咨询向导信息',field:'detail',width:510,align:'left'}
		]],
		toolbar:[{
			id:'edit',
			text:'修改',
			iconCls:'icon-edit', 
			handler:function(){ 
				var row = $('#conDetail').treegrid('getSelected');
				if(row){
					//parent.$('#running-overlay').show(); //等待显示
					//公共条件组合ID	conditionItemGroupId
					var groupidStr = row.conditionItemGroupId ?  '&conditionItemGroupId='+row.conditionItemGroupId : '';
					//&enter=false
					var level = $('#conDetail').treegrid('getLevel',row.consultId);
					var enter = level == 1 ? '&enter=false' : '&enter=true';
					$('#detail_content')[0].src='page20122.html?consultItemId='+consultItemId+ groupidStr +
					'&consultId='+row.consultId + enter +
					'&detail='+encodeURIComponent(row.detail.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;')) +'&task=edit';
					$('#detailInfo').show();
				}
				else $.messager.alert('提示','请先选择需要修改的业务咨询内容记录','info');
			  }
			},
			{
			id:'add',
			text:'添加子级',
			iconCls:'icon-add', 
			handler:function(){ 
				var row = $('#conDetail').treegrid('getSelected');
				if(row){
					//parent.$('#running-overlay').show(); //等待显示
					ydl.displayRunning(true);
					var children = $('#conDetail').treegrid('getChildren',row.consultId);
					var orderNo = children.length > 0 ? children[children.length-1].orderNo + 1 : 1;//顺序号
					var stepId = $('#b-add').data('stepId');
					//公共条件组合ID	conditionItemGroupId
					var groupidStr = row.conditionItemGroupId ?  '&conditionItemGroupId='+row.conditionItemGroupId : '';
					$('#detail_content')[0].src='page20122.html?consultItemId='+consultItemId + groupidStr+
					'&stepId='+stepId+'&parentId='+row.consultId+'&orderNo='+orderNo+'&task=add&enter=true';
					$('#detailInfo').show();
				}
				else $.messager.alert('提示','请先选择业务咨询内容记录~','info');
			  }
			}
		],
		loadFilter:function(data){
			if (data.recode != SUCCESS_CODE) {
				if (data.recode != BLANK_CODE) $.messager.alert('提示',data.msg,'info');
				data.rows = [];
			}
			/*
			else {
				
				for (var i =0; i< data.rows.length; i++ ){
					//data.rows[i]._parentId = data.rows[i].parentId;
				}
			}
			*/
			return data
		},
		onClickRow:function(row){
			//alert('点击行 ');
			
			var children = $('#conDetail').treegrid('getChildren',row.consultId);//子节点 
			//var parentNode = $('#conDetail').treegrid('getParent',row.consultId);//父节点
			//显示并清空数据
			$('#subContent').show().data('consultId',row.consultId);
			$('#subContent ul').children().remove();
			if (children.length > 0){
				$('#parentInfo').show();
				var clevel =  $('#conDetail').treegrid('getLevel',row.consultId)+1
				$('#parentInfo h2').html(row.detail.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;'));
				$.each(children,function (index,ele){
					var curlevel = $('#conDetail').treegrid('getLevel',ele.consultId);
					if(clevel == curlevel) 
						$('#subContent ul').append('<li><span class="icon icon_info"></span><h3>'+ele.detail.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;')+'</h3></li>');
				});
			}
			/* //关于父节点
			else if(parentNode.length > 0) {
				$('#subTitle').text(parentNode.detail);
			}
			*/
			else {
				//$('#subTitle').html(row.detail.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;'));
				//$('#subTitle').html('业务咨询向导内容详情');
				$('#parentInfo').hide();
				$('#subContent ul').append('<li><span class="icon icon_info"></span><h3>'+row.detail.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;')+'</h3></li>');
			}
			
			//设置内容最大高度
			//var totalHeight = $('.screenbg').height();
			//var titleHeight = $('#subTitle').parent().height();
			//$('#subContent').css('max-height',totalHeight - titleHeight -10);
		},
		onLoadSuccess: function (){
			subContentDrag();//注册拖拽
			$('.datagrid-cell-c1-detail').attr('title','上下拖动图标调整同级顺序');
			//设置按钮状态
			$('#b-set').linkbutton('enable');
		}
	});

	//级联选择
	$('#conInfo').on('change',' input.del',function (){
		$('#conDetail').treegrid('select',this.id);
		var row = $('#conDetail').treegrid('getSelected');
		var children = $('#conDetail').treegrid('getChildren',row.consultId);//子节点 
		for (var i = 0; i < children.length; i++){
			$('#'+children[i].consultId).prop('checked',$(this).prop('checked'));
			if($(this).prop('checked')) $('#'+children[i].consultId).prop('disabled',true);
			else $('#'+children[i].consultId).prop('disabled',false);
		}
		//var parentNode = $('#conDetail').treegrid('getParent',row.consultId);//父节点
	});
	
	
	//删除业务咨询内容
	$('#b-del').click(function (){
		var $checkedInputs = $('#conInfo input:checked');
		//alert($checkedInputs.length)
		if ($checkedInputs.length > 0){
			$.messager.confirm('提示','是否确认删除选中的【'+$checkedInputs.length+'】条向导内容记录？', function(isDel){
				if (isDel) {
					//上传参数
					var paras = [{name:'method',value:'delete'}]
					var consultIds = [];
					$.each($checkedInputs,function (index){
						consultIds[index++] = this.id;
						paras[index+1] = {name:'listConsultId',value:this.id};
					});
					//删除
					$.ajax({
						type : "POST",
						url : "<%=request.getContextPath()%>/webapi20124.json",
						dataType: "json",
						data:paras,
						success : function(data) {
							if (data.recode == SUCCESS_CODE) {
								$('#conDetail').treegrid('reload');
								//清空预览 
								var consultId = $('#subContent').data('consultId');
								var index = $.inArray(consultId, consultIds);  
								if (index >= 0 ) $('#subContent ul').children().remove();
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
		else $.messager.alert('提示','请先选择要删除的记录~','info');
	});
	
	//初始化排序
	$.each(stepInfos,function(index,ele){
		var $li = $('<li class="drag-item"><div><div class="drag" title="左右拖动调整顺序"></div><span title="删除"></span></div><h2>'+ele.stepName.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;')+'</h2></li>');
		$li.data('data',ele);
		$('#stepInfo ul').append($li);
	});
	$('#stepInfo ul').append('<li class="addStep"><button id="addStep"><span class="icon icon_add">添加步骤</span></button></li>');
	stepDrag();// 注册拖拽事件
	//有步骤时，设置步骤1状态
	if(!$('#stepInfo li:first').hasClass('addStep')) $('#stepInfo li:first').click();
	
	
	//单击编辑名称
	$('#stepInfo ul').on('click',' li h2',function(){
		var value = $(this).html().replace(/<br>/g, '\n').replace(/&nbsp;/g,' ').replace(/　　　　/g,'\t');
		var offset = $(this).offset();
		var top = offset.top-1;
		var left = offset.left-1;
		$('#common_input').data('item',$(this))
		.css('left', left).css('top', top).height($(this).height()).width($(this).width()-4)
		.val(value)
		.show().focus();
	});	 
	
	//输入公共组件失去焦点,修改
	$('#common_input').on('blur',function(){
		$(this).hide();
		var $item = $(this).data('item');
		var value = this.value;
		var valuehtml = value.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;');
		var isChange = $item.html() != valuehtml; //数据是否更改
		
		if (isChange){
			var paras = {};
			//校验长度
			if (value.length2() > 100 || value.length < 1) {
				$.messager.alert('提示','【向导步骤名称】的长度应在1~100个字符之间，您输入了'+value.length2()+'个字符，请重新输入！','info',
				function (){
					$('#common_input').show().focus();
				});
				return;
			}
			var url = '';
			var info  = $item.closest('li').data('data');
			//修改向导步骤信息
			//上传参数
			//paras = {method:'edit',consultSubItemId,consultSubItemId}	//业务咨询子项ID
			paras.method  = 'edit';		
			paras.consultSubItemId  = consultSubItemId;
			paras.stepId  = info.stepId;			//向导步骤ID
			paras.stepName = value;					//向导步骤名称

			url = '<%=request.getContextPath()%>/webapi20122.json';
			$.ajax({
				type : 'POST',
				url : url,
				dataType: 'json',
				data:paras,
				success : function(data) {
					if (data.recode == SUCCESS_CODE) {
						$item.html(valuehtml);
						//更新步骤名称
						$item.closest('li').data('data').stepName = value;
					}
					else $.messager.alert('提示','修改【步骤名称】出错：'+data.msg,'info');
				},
				error :function(){
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
		}
	});
	
	//添加向导步骤
	$('#stepInfo').on('click','#addStep',function (){
		var $curLi = $(this).parent();
		var $lastLi = $curLi.prev();
		var paras = {method:'add',stepName:'请输入向导步骤名称'};
		paras.consultSubItemId = consultSubItemId;
		paras.step = $('#stepInfo ul').children().length > 1 ? $lastLi.data('data').step + 1 : 1;
		//添加
		$.ajax({
			type : 'POST',
			url : '<%=request.getContextPath()%>/webapi20122.json',
			dataType: 'json',
			data:paras,
			success : function(data) {
				if (data.recode == SUCCESS_CODE) {
					var info = $.extend({stepId:data.stepId},paras);
					var $li = $('<li class="drag-item"><div><div class="drag" title="左右拖动调整顺序"></div><span title="删除"></span></div><h2>'+info.stepName+'</h2></li>');
					$li.data('data',info);
					$curLi.before($li);
					//考虑滚动条
					$('#stepInfo').scrollTop($('#stepInfo').scrollTop());
					$('#stepInfo ul li h2:last').click();	
					$('#common_input').val('');
					stepDrag(true);
				}
				else $.messager.alert('提示',data.msg,'info');
			},
			error :function(){
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});
	
	
	//删除向导步骤
	$('#stepInfo').on('click','li span',function (event){
		event.stopPropagation();
		//判断是否有业务咨询内容
		var $curLi = $(this).closest('li');
		if (!$curLi.hasClass('selected-item')) {
			$.messager.alert('提示','请先查询此步骤下向导内容！','info');
			return;
		}
		var roots = $('#conDetail').treegrid('getRoots');
		if (roots.length > 0) {
			$.messager.alert('提示','请先删除此步骤下所有向导内容！','info');
			return;
		}
		var info = $curLi.data('data');
		var $del_span = $(this);
		$.messager.confirm('提示','是否确认删除此向导步骤？', function(isDel){
			if (isDel) {
				$.ajax({
					type : "POST",
					url : "<%=request.getContextPath()%>/webapi20122.json",
					dataType: "json",
					data:{method:'delete',stepId:info.stepId},
					success : function(data) {
						if (data.recode == SUCCESS_CODE) {
							$del_span.closest('li').remove();
							$('#b-add').removeData('stepId');
						}
						else $.messager.alert('提示',data.msg,'info');
					},
					error :function(){
						$.messager.alert('错误','网络连接出错！','error');
					}
				});
			}
		});
		event.stopPropagation();
	});
	
	
	//添加业务咨询内容
	$('#b-add').click(function (){
		var stepId = $(this).data('stepId');
		if (stepId != undefined) {
			var roots = $('#conDetail').treegrid('getRoots');
			var orderNo = roots.length > 0 ? roots[roots.length-1].orderNo + 1 : 1;//顺序号
			$('#detail_content')[0].src='page20122.html?consultItemId='+consultItemId+
			'&stepId='+stepId+'&parentId=src&orderNo='+orderNo+'&task=add&enter=false';
			$('#detailInfo').show();
		}
		else $.messager.alert('提示','请先添加或选择向导步骤','info');
	});
	
	
	//【添加/修改向导内容】关闭按钮
	$('#detail_close').click(function () {
		$('#detailInfo').hide();
		$('#detail_content')[0].src='about:blank';
	});
	
	
	//不允许输入回车
	$('#common_input').keydown(function (e){
		if (enter == 'false' && e.keyCode == '13') return false;
	});
	
	//设置条件与内容
	$('#b-set').click(function (){
	
		var stepId = $('#b-add').data('stepId');
		if (stepId == undefined) {
			$.messager.alert('提示','请先添加或选择向导步骤','info');
			return;
		}
		//此步骤下向导内容一级标题--TODO:判断列表是否加载查询完成
		var roots = $('#conDetail').treegrid('getRoots');
		if (roots.length == 0) {
			$.messager.alert('提示','请先在此步骤下添加【业务咨询内容】！','info');
			return;
		}
		//parent.$('#running-overlay').show(); //等待显示
		ydl.displayRunning(true);
		//查询此业务咨询项是否配置了公共条件
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/webapi20111.json",
			dataType: "json",
			data:{consultItemId:consultItemId},
			success : function(data) {
				//parent.$('#running-overlay').hide();
				ydl.displayRunning(false);
				if (data.recode == SUCCESS_CODE || data.recode == BLANK_CODE) {
					//如果有数据
					if (data.rows) {
						//selected-item
						var info = $('li.selected-item').data('data');
						var stepName = info.stepName;
						$('#condInfo').show();
						$('#cond_content')[0].src='page20123.html?stepId='+info.stepId+'&consultItemId='+consultItemId+
						'&consultSubItemId='+consultSubItemId+'&stepName='+encodeURIComponent(stepName.trim());
					}
					else $.messager.alert('提示','请先为【'+itemName+'】业务咨询项配置公共条件','info');
				}
				else $.messager.alert('提示','【查询公共条件】出错：'+data.msg,'info');
			},
			error :function(){
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});
	
	//关闭设置对话框
	$('#cond_close').click(function () {
		$('#condInfo').hide();
		$('#cond_content')[0].src='about:blank';
	});
	
	
});

//注册拖拽排序事件
function stepDrag(isAdd){
	//不重复创建指示器
	var $indicator = $('#icon_arrow_lr');
	if ($indicator.length == 0) $indicator = $('<img id="icon_arrow_lr" class="indicator" src="<%=_contexPath%>/ui/consult/icon_arrow_lr.png" />').appendTo('body');
	var selector = isAdd ? '#stepInfo .drag-item:last' : '#stepInfo .drag-item';
	$(selector).draggable({
		revert: true,
		proxy:function(source){
			var $liClone = $('<li class="drag-item" style="background-color: #FFFFAA;width:'+$(source).width()+'px;opacity:0.8;"></li>');
			$liClone.html($(source).html()).appendTo($(source).closest('ul'));
			return $liClone;
		},
		deltaX: 0,
		deltaY: 0,
		axis: 'h',
		handle: ' .drag',
		onBeforeDrag: function (e) {
			if (e.target.tagName != 'DIV') return false;
			$('#common_input').blur();
		},
		onStartDrag: function (e) {
			//$(e.target).closest('.drag-item').addClass('drag-current');
		},
		onStopDrag: function (e) {
			//$(e.target).removeAttr('style');
			//$(e.target).closest('.drag-item').removeClass('drag-current');
			
		}
	}).droppable({
		accept:'.drag-item',
		onDragOver: function(e,source){
			$indicator.css({
				display: 'block',
				left: $(this).offset().left - 10 + $(this).width(),
				top: $(this).offset().top + $(this).outerHeight() - 7
			});
		},
		onDragLeave:function(e,source){
			$indicator.hide();
		},
		onDrop:function(e,source){
			//被拖拽元素信息
			var info = $(source).data('data');
			var targetInfo = $(this).data('data');
			var targetLi = this;
			var $target = $(this);
			//排序上传参数
			var paras = {
				method:'sort',
				sourceOrderNo:info.step,
				targetOrderNo: targetInfo.step,
				consultSubItemId:consultSubItemId
			};
			//有点诡异的问题，
			if (paras.sourceOrderNo > paras.targetOrderNo) paras.targetOrderNo = paras.targetOrderNo +1;

			//排序
			$.ajax({
				type : 'POST',
				url : '<%=request.getContextPath()%>/webapi20122.json',
				dataType: 'json',
				data:paras,
				success : function(data) {
					if (data.recode == SUCCESS_CODE) { 
						var $lis = $('#stepInfo ul li');
						var sourceIndex = $lis.index($(source)); 
						var targetIndex = $lis.index($target); 
						//向上移-重新排序赋值
						if (sourceIndex > targetIndex) {
							$lis.each(function (index){
								if (index > targetIndex && index < sourceIndex +1 ) $(this).data('data').step = $(this).data('data').step +1;
							});
						}
						//向下移-重新排序赋值
						else {
							$lis.each(function (index){
								if (index > sourceIndex && index < targetIndex +1 ) $(this).data('data').step = $(this).data('data').step -1;
							});
						}
						$(source).data('data').step = paras.targetOrderNo;
						//测试
						//$('#stepInfo ul li').each(function(index){
						//	if (!$(this).hasClass('addStep')) alert($(this).data('data').step);
						//});
						//移动元素
						$(source).insertAfter(targetLi);
						$indicator.hide();
						
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

//注册拖拽排序事件
function subContentDrag(){
	//不重复创建指示器
	var $indicator = $('#icon_arrow');
	if ($indicator.length == 0) $indicator = $('<img id="icon_arrow" class="indicator" src="<%=_contexPath%>/ui/consult/icon_arrow.png" />').appendTo('body');
	$('.datagrid-cell-c1-detail').draggable({
		revert: true,
		proxy:function(source){
			var $clone = $('<div class="datagrid-cell datagrid-cell-c1-detail" style="background-color: #FFFFAA;width:'+$(source).width()+'px;height:25px;opacity:0.8;"></div>');
			$clone.html($(source).html()).appendTo($(source).parent());
			return $clone;
		},
		deltaX: 0,
		deltaY: 0,
		axis: 'v',
		handle: ' .tree-icon',
		onBeforeDrag: function (e) {
			//if (e.target.tagName != 'DIV') return false;
			if ($(e.target).closest('.datagrid-cell-c1-detail').length == 0) return false;
		},
		onStartDrag: function (e) {
			//$(e.target).closest('.datagrid-cell-c1-detail').addClass('drag-current');
		},
		onStopDrag: function (e) {
			//$(e.target).closest('.datagrid-cell-c1-detail').removeClass('drag-current');
		}
	}).droppable({
		accept:'.datagrid-cell-c1-detail',
		onDragOver: function(e,source){
			$indicator.css({
				display: 'block',
				left: $(this).offset().left - 10 ,
				top: $(this).offset().top + $(this).outerHeight() - 7
			});
		},
		onDragLeave:function(e,source){
			$indicator.hide();
		},
		onDrop:function(e,source){
			var $targetNode = $(this);
			//var $sourceParent = $(source).parent();
			//$targetNode.parent().append($(source));
			//$sourceParent.append($targetNode);
			
			$indicator.hide();
			//
			var sourceid = $(source).closest('tr').attr('node-id');
			var targetid = $targetNode.closest('tr').attr('node-id');
			
			var sourceNode = $('#conDetail').treegrid('find',sourceid);
			var targetNode = $('#conDetail').treegrid('find',targetid);

			if (sourceNode.parentId == targetNode.parentId) {
				//排序上传参数
				var paras = {
					method:'sort',
					parentId:sourceNode.parentId,
					sourceOrderNo:sourceNode.orderNo,
					targetOrderNo: targetNode.orderNo,
					stepId:$('#b-add').data('stepId')
				};
				//有点诡异的问题，
				if (sourceNode.orderNo > paras.targetOrderNo) paras.targetOrderNo = paras.targetOrderNo +1;
				
				//排序
				$.ajax({
					type : 'POST',
					url : '<%=request.getContextPath()%>/webapi20124.json',
					dataType: 'json',
					data:paras,
					success : function(data) {
						if (data.recode == SUCCESS_CODE) { 
							//$.messager.alert('提示',data.msg,'info');
							$('#conDetail').treegrid('reload');
						}
						else $.messager.alert('提示',data.msg,'info');
					},
					error :function(){
						$.messager.alert('错误','网络连接出错！','error');
					}
				});
			}
			else {
				$.messager.alert('提示','请进行同级排序~','info');
				return false;
			}
		}
	});
}

//]]></script>
</head>
<body >
<div id="title" ><h1>业务类型->业务咨询项->业务咨询子项</h1></div>
<div id="center">
	<div id="conInfo" >
		<table id="conDetail"></table>
		<div class="buttons">
			
			<a id="b-set" href="javascript:void(0)" class="easyui-linkbutton">条件与内容配置</a>
			
			<a id="b-add" href="javascript:void(0)" class="easyui-linkbutton">添加业务咨询内容</a>
			<a id="b-del" href="javascript:void(0)" class="easyui-linkbutton">删除</a>
		</div>
	</div>
	<div id="subInfo" >
		<div class="phonebg">
			<div class="screenbg">
				<div><h1 id="subTitle">业务咨询向导内容详情</h1></div>
				<div id="subContent" >
					<div id="parentInfo"><h2>父级</h2></div>
					<ul>
						<!--
						<li><span class="icon icon_info"></span><h3>业务咨询向导内容详情业务咨询向导内容详情</h3></li>
						-->
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="stepInfo" >
	<ul></ul>
</div>
<textarea type="text" id="common_input"></textarea> 
<!-- 添加修改公共内容  -->
<div id="detailInfo">
	<img id="detail_close" alt="关闭" title="关闭" src="<%=_contexPath%>/ui/consult/close.png" />
	<iframe id="detail_content" src="about:blank"></iframe>
</div>
<!-- 条件与内容配置  -->
<div id="condInfo">
	<img id="cond_close" alt="关闭" title="关闭" src="<%=_contexPath%>/ui/consult/close.png" />
	<iframe id="cond_content" src="about:blank"></iframe>
</div>
</body>
</html>
