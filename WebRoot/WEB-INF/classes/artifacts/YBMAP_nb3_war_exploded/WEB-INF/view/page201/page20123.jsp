<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>公共条件与内容配置</title>
<%@ include file = "/include/styles.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= _contexPath %>/ui/consult/condetail.css" />
<style type="text/css">
/*<![CDATA[*/

/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<base target="main" />
<script type="text/javascript">//<![CDATA[
var editIndex = undefined;
var stepId;
var consultItemId;
var consultSubItemId;
$(function () {
	var field = decodeURIComponent(location.search);
	var stepName = field.replace(/.*&stepName=(.*)/g, "$1");	//步骤名称
	stepId = '<%=request.getParameter("stepId") %>';			//步骤id
	consultItemId = '<%=request.getParameter("consultItemId") %>';		
	consultSubItemId = '<%=request.getParameter("consultSubItemId") %>';		
	
	//设置title
	$('#title h1').text(stepName+' → 公共条件与内容配置');	
	
	//业务咨询向导查询列表
	$('#conDetail').treegrid({
		title:'业务咨询向导内容',
		iconCls:'icon-tip',
		height:430,
		width:510,
		url: '<%=request.getContextPath()%>/webapi20121.json',
		queryParams: {stepId:stepId},
		singleSelect: true,
		rownumbers: true,
		method: 'post',
		idField:'consultId',
		treeField:'detail',
		columns:[[
			{title:'必选条件项目序号',field:'usedConditionItemOrderNo',align:'center',hidden:true},
			{title:'选择',field:'consultId',width:40,align:'center',formatter : function(value){
				return '<input type="checkbox" id="'+value+'" class="del"/>';
			}},
			{title:'业务咨询向导信息',field:'detail',align:'left'}
		]],
		loadFilter:function(data){
			if (data.recode != SUCCESS_CODE) {
				if (data.recode != BLANK_CODE) $.messager.alert('提示',data.msg,'info');
				data.rows = [];
			}
			return setParent(data.rows);
			//return data;
		},
		onClickRow:function(row){

		},
		onLoadSuccess: function (){

		}
	});
	ydl.displayRunning(true);
	$.ajax({
		type : "POST",
		url : "<%=request.getContextPath()%>/webapi20111.json",
		dataType: "json",
		data:{consultItemId:consultItemId},
		success : function(data) {
			ydl.displayRunning(false);
			//刷新
			$('#groupItemContent ul').children().remove();
			if (data.recode == SUCCESS_CODE || data.recode == BLANK_CODE) {
				//如果有数据
				if (data.rows){
					var lastItemId = null;
					var conNumber = 1;
					$.each(data.rows,function (index,ele){
						//添加分类信息
						if (ele.conditionItemId != lastItemId) {
							var $itemTitle = $('<li class="itemTitle"><h1><span>'+('①②③④⑤⑥⑦⑧⑨⑩'.charAt(conNumber - 1))+'</span> '+ele.conditionItemName+'</h1><span class="icon icon_clear" title="重置选项"></span></li>');
							$itemTitle.data('data',ele);
							$('#groupContent').append($itemTitle);
							lastItemId = ele.conditionItemId;
							conNumber++;
						}
						//添加分组与内容信息
						var $li = $('<li id="group_'+ele.conditionGroupId+'"><div><h2>'+ele.groupName.replace(/\n/g, '<br>')+'</h2>'+
						'</div>'+
						'<ul class="detail multivalue"></ul></li>');
						$.each(ele.listMi305,function (i,obj){
							$detailLi = $('<li><input id="' + obj.conditionGroupId + '-' + i + '" name="' + ele.conditionItemId + '" type="radio" value="'+obj.conditionId+'"/>'+
							'<label for="'+obj.conditionGroupId + '-' + i +'">'+obj.conditionDetail.replace(/\n/g, '<br>').replace(/\t/g, '　　　　').replace(/ /g, '&nbsp;')+'</label></li>');
							$detailLi.data('data',obj);
							$li.find('ul').append($detailLi);
						});
						$li.data('data',ele);
						$('#groupContent').append($li);
					});
				}
			}
			else $.messager.alert('提示',data.msg,'info');
		},
		error :function(){
			ydl.displayRunning(false);
			$.messager.alert('错误','网络连接出错！','error');
		}
	});
	
	//设置按钮状态
	$('#b_con_save').prop('disabled',true);
	$('#b_con_clear').prop('disabled',true);
	$('#b_common_save').linkbutton('disable');
	$('#b_common_clear').linkbutton('disable');
	
	//重置选项
	$('#groupContent').on('click','span.icon_clear',function(){
		var name = $(this).closest('li').data('data').conditionItemId;
		$('input[name='+name+']').prop('checked',false);
		$('#b_con_save').prop('disabled',true);
		$('#b_con_clear').prop('disabled',true);
	});
	
	//设置保存按钮状态
	$('#groupContent').on('change','input',function(){
		$('#b_con_save').prop('disabled',true);
		$('#b_con_clear').prop('disabled',true);
	});
	
	//查询公共条件内容 
	$('#b_common_query').click(function (){
		//parent.parent.$('#running-overlay').show(); //显示等待标识
		ydl.displayRunning(true);
		//设置查询按钮状态
		$('#b_common_save').linkbutton('enable');
		$('#b_common_clear').linkbutton('enable');
		$('#b_con_save').prop('disabled',true);
		$('#b_con_clear').prop('disabled',true);
		//查询
		$.ajax({
			type : "POST",
			url : "<%=request.getContextPath()%>/webapi20126.json",
			dataType: "json",
			data:{stepId:stepId,method:'query'},
			success : function(data) {
				//parent.parent.$('#running-overlay').hide(); //隐藏等待标识
				ydl.displayRunning(false);
				if (data.recode == SUCCESS_CODE || data.recode == BLANK_CODE) {
					//清空选中项
					$('#detailContent input').prop('checked',false).prop('disabled',false);
					if (data.result.length > 0) {
						for (var i = 0; i< data.result.length; i++) {
							$('#'+data.result[i]).prop('checked',true);
						}
					}
					//隐藏列
					$('#conDetail').treegrid('hideColumn','usedConditionItemOrderNo');
				}
				else $.messager.alert('提示',data.msg,'info');
			},
			error :function(){
				//parent.parent.$('#running-overlay').hide(); //隐藏等待标识
				ydl.displayRunning(false);
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});	
	//保存公共内容设置 
	$('#b_common_save').click(function (){
		commonSave(false);
	});	
	
	//清空公共内容 
	$('#b_common_clear').click(function (){
		$.messager.confirm('提示','是否确认清空【公共内容】？', function(isOK){
			if (isOK) commonSave(true);
		});
	});
	
	//根据条件查询内容 
	$('#b_con_query').click(function (){
		if ($('#groupContent li:not(.itemTitle) input:checked').length > 0) {
			//设置查询按钮状态
			$('#b_common_save').linkbutton('disable');
			$('#b_common_clear').linkbutton('disable');
			$('#b_con_save').prop('disabled',false);
			$('#b_con_clear').prop('disabled',false);
			//显示等待标识
			//parent.parent.$('#running-overlay').show(); 
			ydl.displayRunning(true);
			//上传参数
			var paras = [
				{name:'method',value:'query'},
				{name:'consultItemId',value:consultItemId},
				{name:'stepId',value:stepId}
			];
			$('#groupContent li:not(.itemTitle) input:checked').each(function (index,ele){
				paras[index+3] = {name:'listConditionId',value:ele.value};
			});
			//查询
			$.ajax({
				type : "POST",
				url : "<%=request.getContextPath()%>/webapi20125.json",
				dataType: "json",
				data:paras,
				success : function(data) {
					//parent.parent.$('#running-overlay').hide(); //隐藏等待标识
					ydl.displayRunning(false);
					if (data.recode == SUCCESS_CODE || data.recode == BLANK_CODE) {
						//清空选中项
						$('#detailContent input').prop('checked',false).prop('disabled',false);
						//清空序号列值
						$('#detailContent input').closest('td').prev().children('div').text('');
						if (data.result.length > 0) {
							for (var i = 0; i< data.result.length; i++) {
								//设置选中与编辑状态
								$('#'+data.result[i].consultId).prop('checked',true).prop('disabled',data.result[i].readOnly);
								//设置序号列值
								$('#'+data.result[i].consultId).closest('td').prev().children('div').text(data.result[i].usedConditionItemOrderNo);
							}
						}
						//显示列
						$('#conDetail').treegrid('showColumn','usedConditionItemOrderNo');
						//自适应列宽
						$('#conDetail').treegrid('autoSizeColumn','usedConditionItemOrderNo');
						
					}
					else $.messager.alert('提示',data.msg,'info');
				},
				error :function(){
					//parent.parent.$('#running-overlay').hide(); //隐藏等待标识
					ydl.displayRunning(false);
					$.messager.alert('错误','网络连接出错！','error');
				}
			});
		}
		else $.messager.alert('提示','请先选择公共咨询条件内容！','info');
	});	
	//保存条件内容设置 
	$('#b_con_save').click(function (){
		conSave(false);
	});
	
	//清空条件内容 
	$('#b_con_clear').click(function (){
		$.messager.confirm('提示','是否确认清空当前选择条件下的【咨询内容】？', function(isOK){
			if (isOK) conSave(true);
		});
	});
	
	//内容选中项关联 
	$('#detailContent').on('change',' input.del',function (){
		$('#conDetail').treegrid('select',this.id);
		var row = $('#conDetail').treegrid('getSelected');
		var children = $('#conDetail').treegrid('getChildren',row.consultId);//子节点 
		for (var i = 0; i < children.length; i++){
			$('#'+children[i].consultId).prop('checked',$(this).prop('checked'));
		}
		if ($(this).prop('checked')) checkParent($('#conDetail').treegrid('getParent',row.consultId));//父节点
	});
});

//添加父节点key值
function setParent(children){
	for (var i = 0; i < children.length; i++){
		children[i]._parentId = children[i].parentId;
		var cData = children[i].children;
		if (cData.length > 0) {
			setParent(cData);
		}
	}
	return children;
}
//选择父节点递归
function checkParent(parentNode){
	if (parentNode) {
		$('#'+parentNode.consultId).prop('checked',true);
		checkParent($('#conDetail').treegrid('getParent',parentNode.consultId));//父节点
	}
}

//保存（含清空）公共内容
function commonSave(isclear) {
	//设置查询按钮状态
	$('#b_con_save').prop('disabled',true);
	$('#b_con_clear').prop('disabled',true);
	//上传参数
	var paras = [
		{name:'method',value:'save'},
		{name:'consultItemId',value:consultItemId},
		{name:'consultSubItemId',value:consultSubItemId},
		{name:'stepId',value:stepId}
	];
	if (!isclear){
		var $checkedInputs = $('#detailContent input:checked');
		if ($checkedInputs.length == 0) {
			$.messager.alert('提示','请选择需要设置成【公共内容】的记录！','info');
			return ;
		}
		$checkedInputs.each(function (index,ele){
			paras[index+4] = {name:'listConsultId',value:ele.id};
		});
		
	}
	//parent.parent.$('#running-overlay').show(); //显示等待标识
	ydl.displayRunning(true);
	//保存
	$.ajax({
		type : "POST",
		url : "<%=request.getContextPath()%>/webapi20126.json",
		dataType: "json",
		data:paras,
		success : function(data) {
			//parent.parent.$('#running-overlay').hide(); //隐藏等待标识
			ydl.displayRunning(false);
			var info = isclear ? '【清空公共内容】出错：' : '【保存公共内容设置】出错：';
			if (data.recode == SUCCESS_CODE) {
				if (isclear) $('#detailContent input:checked').prop('checked',false);
				$.messager.alert('提示',data.msg,'info');
			}
			else $.messager.alert('提示',info + data.msg,'info');
		},
		error :function(){
			//parent.parent.$('#running-overlay').hide(); //隐藏等待标识
			ydl.displayRunning(false);
			$.messager.alert('错误','网络连接出错！','error');
		}
	});
}

//保存（含清空）条件内容
function conSave(isclear) {
	//设置查询按钮状态
	$('#b_common_save').linkbutton('disable');
	$('#b_common_clear').linkbutton('disable');
	//上传参数
	var paras = [
		{name:'method',value:'save'},
		{name:'consultItemId',value:consultItemId},
		{name:'consultSubItemId',value:consultSubItemId},
		{name:'stepId',value:stepId}
	];
	//条件内容
	var listConditionIds = [];
	$('#groupContent li:not(.itemTitle) input:checked').each(function (index,ele){
		listConditionIds[index] = {name:'listConditionId',value:ele.value};
	});
	$.merge(paras,listConditionIds);//合并【条件内容】上传参数 
	if (!isclear) {
		var $checkedInputs = $('#detailContent input:checked:not(:disabled)');
		if ($checkedInputs.length == 0) {
			$.messager.alert('提示','请选择需要设置成选择条件下【咨询内容】的记录！','info');
			return ;
		}
		//向导内容
		var listConsultIds = [];
		$checkedInputs.each(function (index,ele){
			if (!$(ele).prop('disabled')) listConsultIds[index] = {name:'listConsultId',value:ele.id};
		});
		$.merge(paras,listConsultIds);//合并【向导内容】上传参数 
	}
	//parent.parent.$('#running-overlay').show(); //显示等待标识
	ydl.displayRunning(true);
	$.ajax({
		type : "POST",
		url : "<%=request.getContextPath()%>/webapi20125.json",
		dataType: "json",
		data:paras,
		success : function(data) {
			//parent.parent.$('#running-overlay').hide(); //隐藏等待标识
			ydl.displayRunning(false);
			var info = isclear ? '【清空条件内容设置】出错：' : '【保存条件内容设置】出错：';
			if (data.recode == SUCCESS_CODE) {
				$.messager.alert('提示',data.msg,'info',function (){
					$('#b_con_query').click();//查询
				});
			}
			else $.messager.alert('提示',info + data.msg,'info');
		},
		error :function(){
			//parent.parent.$('#running-overlay').hide(); //隐藏等待标识
			ydl.displayRunning(false);
			$.messager.alert('错误','网络连接出错！','error');
		}
	});
}

//]]></script>
</head>
<body class="easyui-layout">
<div id="title" data-options="region:'north',split:false,border:false"><h1>步骤名称 → 公共条件与内容配置</h1></div>
<div id="conditionContent" data-options="region:'west',split:false,border:false">
	<div class="phonebg">
		<div class="screenbg">
			<h1 id="groupTitle" class="item-title">公共咨询条件</h1>
			<div id="groupItemContent" class="item-content">
				<ul id="groupContent">
					
				</ul>
			</div>
			<div id="con-buttons">
				<button id="b_con_query"><span class="icon icon_query">查询内容</span></button>
				<button id="b_con_save"><span class="icon icon_save">保存内容设置</span></button>
				<button id="b_con_clear"><span class="icon icon_empty">清空条件内容</span></button>
			</div>
		</div>
	</div>
</div>
<div id="detailContent" data-options="region:'center',border:false">
	<table id="conDetail"></table>
	<div id="detail-buttons" class="buttons">
		<a id="b_common_query"  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询公共内容</a>
		<a id="b_common_save" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save">保存公共内容设置</a>
		<a id="b_common_clear" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-empty">清空公共内容</a>
	</div>
</div>
</body>
</html>
