<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>公共条件查询</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
#list-container{float: left; width: 80%; padding:0px;overflow: hidden}
#tree-container {float: left; width: 19%;overflow-y: scroll;margin-right:5px;background-color:#E6EEF8;}
#form20109 {margin-left:5px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<base target="main" />
<script type="text/javascript">//<![CDATA[
$(function () {
	//公共条件查询列表
	$('#tab20111').treegrid({
		title:'公共条件查询',
		iconCls:'icon-tip',
		toolbar:'#toolbar',
		//height:$(document).height()-50,
		//height:document.documentElement.clientHeight - 60,
		height:$('#tree-container').height()+2,
		singleSelect: true,
		checkOnSelect: false,
		rownumbers: true,
		animate:true,
		collapsible:true,
		//fitColumns:true,
		url:'webapi20111.json',
		method: 'post',
		idField:'id',
		treeField:'name',
		queryParams: {"centerId":"00001"}, //TODO 查询条件中心编码
		//与page20104一样了
		frozenColumns : [ [ {
			title : '删除',
			field : 'checkbox',//
			width : 30,
			formatter : function(value) {
				var values = value.split('-');
				if (values.length && values.length==3) {
					var cbId = values[2]=="1" ? "listConditionItemId" : values[2]=="2" ? "listConditionGroupId" : "listConditionId";
					var cbValue = values[0];
					var cbPValue = values[1];
					
					return '<input id="'+cbId+cbValue+'" name="'+cbId+'" type="checkbox" onChange="checkClick(\''+cbId+cbValue+'\');" level="'+values[2]+'" value="'+cbValue+'" pvalue="'+cbPValue+'"></input>';
				}
				return value;
			}
		} ] ],
		columns:[[
			//{title:'删除',field:'ck',checkbox:true,width:60},
			{title:'公共条件项目名称',field:'name',width:400,align:'left'},
			{title:'类别',field:'level',width:100, formatter : function(value){
				return value=='1' ? '公共条件项目' : value=='2' ? '公共条件分组' : value=='3' ? '公共条件内容' : value;
			}},
			{title:'公共条件项目ID',field:'id',width:80},
			{title:'公共条件项目序号',field:'orderNo',width:120},
			{title:'咨询信息项目ID',field:'consultItemId',width:120}
		]],
		onClickRow:function(row){
			//alert(row.id)
		}
	});

	//左侧树
	$.ajax( {
		type : "POST",
		url : "<%=request.getContextPath()%>/webapi20100.json",
		dataType: "json",
		data:putPubData([{"name":"level", "value":"2"}]),
		success : function(data) {
			var treeData = data.result;
			$('#ptree').tree({   
				data:treeData, 
				//height:$(document).height()-50,  
				onClick: function(node){
					if(node.attributes.level==2){
						$('#tab20111').treegrid('options').queryParams.consultItemId = node.attributes.id;//修改查询条件
						$('#tab20111').treegrid('reload'); //刷新列表
					}
				}
			}); 
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});
	
	
	//隐藏
	$('#dlg-buttons').hide();
	
	
});
//添加公共条件项目
function add(){
	var row = $('#ptree').tree('getSelected');
	if(row && row.attributes.level==2){
		//alert(row.attributes.id)
		var href = 'page20108.html';
		var title = '添加公共条件项目';
		var load = {'id':'conditionItemId', 'consultItemId':row.attributes.id};
		$('#d_dlg').dialog({   
		    title: title,   
		    width: 400,   
		    height: 300,   
		    closed: false,   
		    cache: false,   
		    href: href,
		    onLoad:function(){ 
		    	$('#'+load.id).closest('tr').hide();
		    	$('#h_action').attr('value','webapi20108.json');
		    	$('#h_formName').attr('value','form20108');
		        $('form').data('task','add').form('load',load);
		    },
		    buttons:'#dlg-buttons',   
		    modal: true  
		});  
	}
	else $.messager.alert('提示','请选择业务咨询项目进行添加!','info');
}
//增加下级信息
function addChildren(){
	var row = $('#tab20111').treegrid('getSelected');
	if(row){
		if (row.level == 3){
			$.messager.alert('提示','请选择上业务类型或业务咨询项进行添加!','info');
			return;
		}
		else {
			var href = '';
			var title = '';
			var load = $.extend({},row);
			if (row.level == 1){
				href = 'page20112.html';
				title = '添加公共条件分组';
				load.id = 'conditionGroupId';
				load.action = 'webapi20112.json';
				load.formName = 'form20112';
			}
			else if(row.level == 2){
				href = 'page20115.html';
				title = '添加公共条件';
				load.id = 'conditionId';
				load.action = 'webapi20115.json';
				load.formName = 'form20115';
			}
			$('#d_dlg').dialog({   
			    title: title,   
			    width: 400,   
			    height: 300,   
			    closed: false,   
			    cache: false,   
			    href: href,
			    onLoad:function(){    
			    	$('#'+load.id).closest('tr').hide();
			    	$('#h_action').attr('value',load.action);
			    	$('#h_formName').attr('value',load.formName);
			        $('form').form('load',load);
			    },
			    onClose:function(){
			    	//$('#tab20111').treegrid('reload');
			    },
			    buttons:'#dlg-buttons',   
			    modal: true  
			});  
		}
	}
	else $.messager.alert('提示','请选择公共条件项目或公共条件分组进行添加！','info');
}
//删除记录
function del(){
	$.messager.confirm('提示','是否要删除选中项极其子项？', function(isDel){
		if (isDel) {	// IF1 确认删除
			var arr = $('#form20109').serializeArray();
			if ($('input[name=listConditionId]:checked').length==0) {	// IF2 未选中公共条件内容
				if ($('input[name=listConditionGroupId]:checked').length==0) {	// IF3 未选中公共条件分组
					if ($('input[name=listConditionItemId]:checked').length!=0) {	// IF4 选中公共条件项
						$.ajax( {	//删除公共条件项目
							type : "POST",
							url : "<%=request.getContextPath()%>/webapi20109.json",
							dataType: "json",
							data:arr,
							success : function(data) {
								$.messager.alert('提示', data.msg, 'info');
								if (data.recode==SUCCESS_CODE) {
									$('#tab20111').treegrid('reload'); //刷新列表
								}
							},
							error :function(){
								$.messager.alert('错误','网络连接出错！','error');
							}
						});
					}	// END IF4 选中公共条件项
					else {	//未选择删除复选框
						$.messager.alert('提示', '请选择要删除的公共条件项、公共条件分组或公共条件内容！', 'info');
					}
				}	// END IF3 未选中公共条件分组
				else {	// 未选中公共条件内容，选中公共条件分组，可能选中公共条件项
					$.ajax( {	// 删除公共条件分组
						type : "POST",
						url : "<%=request.getContextPath()%>/webapi20113.json",
						dataType: "json",
						data:arr,
						success : function(data) {
							if (data.recode==SUCCESS_CODE) {
								if ($('input[name=listConditionItemId]:checked').length!=0) {	// IF5 选中公共条件项
									$.ajax( {	// 删除公共条件项目
										type : "POST",
										url : "<%=request.getContextPath()%>/webapi20109.json",
										dataType: "json",
										data:arr,
										success : function(data) {
											$.messager.alert('提示', data.msg, 'info');
											if (data.recode==SUCCESS_CODE) {
												$('#tab20111').treegrid('reload'); //刷新列表
											}
										},
										error :function(){
											$.messager.alert('错误','网络连接出错！','error');
										}
									});
								}	// END IF5 选中公共条件项
								else {	// 未选中公共条件项
									$('#tab20111').treegrid('reload'); //刷新列表
								}
							} else {
								$.messager.alert('提示', data.msg, 'info');
							}
						} ,
						error :function(){
							$.messager.alert('错误','网络连接出错！','error');
						}
					});
				}
			}	// IF2 未选中公共条件内容
			else {	// 选中公共条件内容，可能选中公共条件分组和公共条件项
				$.ajax( {	//删除公共条件内容
					type : "POST",
					url : "<%=request.getContextPath()%>/webapi20116.json",
					dataType: "json",
					data:arr,
					success : function(data) {
						if (data.recode==SUCCESS_CODE) {
							if ($('input[name=listConditionGroupId]:checked').length!=0) {	// IF6 选中公共条件分组
								$.ajax( {	// 删除公共条件分组
									type : "POST",
									url : "<%=request.getContextPath()%>/webapi20113.json",
									dataType: "json",
									data:arr,
									success : function(data) {
										if (data.recode==SUCCESS_CODE) {
											if ($('input[name=listConditionItemId]:checked').length!=0) {	// IF7 选中公共条件项
												$.ajax( {	// 删除公共条件项目
													type : "POST",
													url : "<%=request.getContextPath()%>/webapi20109.json",
													dataType: "json",
													data:arr,
													success : function(data) {
														$.messager.alert('提示', data.msg, 'info');
														if (data.recode==SUCCESS_CODE) {
															$('#tab20111').treegrid('reload'); //刷新列表
														}
													},
													error :function(){
														$.messager.alert('错误','网络连接出错！','error');
													}
												});
											}	// END IF7 选中公共条件项
											else {	// 未选中公共条件项
												$('#tab20111').treegrid('reload'); //刷新列表
											}
										} else {
											$.messager.alert('提示', data.msg, 'info');
										}
									},
									error :function(){
										$.messager.alert('错误','网络连接出错！','error');
									}
								});
							}	// END IF6 选中公共条件分组
							else {	// 未选中公共条分组
								$('#tab20111').treegrid('reload'); //刷新列表
							}
						} else {
							$.messager.alert('提示', data.msg, 'info');
						}
					},
					error :function(){
						$.messager.alert('错误','网络连接出错！','error');
					}
				});
			}
		}
	});
	
}
//保存记录，添加修改分别调不同的路径
function save(){
alert($('#h_action').val());
	var paras = $('#'+$('#h_formName').val()).serializeArray();
alert(paras);
	var url = $('#h_action').val();
	if (!$('from').form( "validate" ,function(v){})) {
		alert($('from').form( "validate" ,function(v){} ));
		$.messager.alert('提示', "请输入完整数据!", 'info' );
		return;
	}
	$.ajax( {
		type : "POST",
		url : url,
		dataType: "json",
		data: paras,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			$('#tab20111').treegrid('reload');
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});
	//$('form').submit();
	$('#d_dlg').dialog('close');
}
//修改记录
function edit(){
	var row = $('#tab20111').treegrid('getSelected');
	if(row){
		var href = '';
		var title = '';
		var load = $.extend({},row);
		if (row.level == 1){
			href = 'page20108.html';
			title = '修改公共条件项目';
			load.id = 'conditionItemId';
			load.action = 'webapi20110.json';
			load.formName = 'form20108';
			
			load.itemNo = row.orderNo;
			load.conditionItemName = row.name;
		}
		else if(row.level == 2){
			href = 'page20112.html';
			title = '修改公共条件分组';
			load.id = 'conditionGroupId';
			load.action = 'webapi20114.json';
			load.formName = 'form20112';
			
			load.noOreder = row.orderNo;
			load.groupName = row.name;
		}
		else if(row.level == 3){
			href = 'page20115.html';
			title = '修改公共条件';
			load.id = 'conditionId';
			load.action = 'webapi20117.json';
			load.formName = 'form20115';
			
			load.noOreder = row.orderNo;
			load.conditionDetail = row.name;
		}
		$('#d_dlg').dialog({   
		    title: title,   
		    width: 400,   
		    height: 300,   
		    closed: false,   
		    cache: false,   
		    href: href,
		    onLoad:function(){   
		    	$('#'+load.id).closest('tr').show();
		    	$('#h_action').attr('value',load.action);
		    	$('#h_formName').attr('value',load.formName);
		        $('form').data('task','edit').form('load',load);
		    },
		    buttons:'#dlg-buttons',   
		    modal: true  
		});  
	}
	else $.messager.alert('提示','请选择要修改的记录!','info');
}
//删除复选框onChange事件
function checkClick(id){
	var level = $('#'+id).attr('level');
	if (level == 1) {
		var $checkbox2 = $('input[name=listConditionGroupId][pvalue='+$('#'+id).val()+']')
		$checkbox2.prop('checked',$('#'+id).prop('checked')).change();
	}
	else if (level == 2) {
		$('input[name=listConditionId][pvalue='+$('#'+id).val()+']').prop('checked',$('#'+id).prop('checked'));
	}
	
	// TODO 下级取消选中时上级也要取消选中；浏览器兼容性
}

//]]></script>
</head>

<body class="easyui-layout">
<div class="page-header" data-options="region:'north',split:false,border:false">业务咨询->公共条件配置</div>
<div id="tree-container" data-options="region:'west',split:false,border:true">
	<ul id="ptree" ></ul>
</div>
<div id="list-container" data-options="region:'center',border:false">
	<form id='form20109'>
		<table id="tab20111" class="container"></table>
	</form>
	<div id="toolbar" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="add()">公共条件项目</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="del()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addChildren()">下级</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="edit()">修改</a>
	</div>
	<div id="d_dlg"></div>
	<div id="dlg-buttons">
	     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">保存</a>
	     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#d_dlg').dialog('close')">取消</a>
	 </div>
	 <input type="hidden" id='h_action' />
	 <input type="hidden" id='h_formName' />
</div>
</body>
</html>
