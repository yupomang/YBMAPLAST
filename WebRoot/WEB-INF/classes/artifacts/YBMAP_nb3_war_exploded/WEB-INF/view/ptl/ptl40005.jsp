<%@ page language="java" contentType="text/html; charset=utf-8" %>
<% 
  /*
     文件名：ptl40005.jsp
     作者： 韩占远
     日期：2013-10-07
     作用：功能(菜单)管理
  */
%>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>功能(菜单)管理</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
#tree-container{width: 300px; height:100%; margin:5px;padding:5px;overflow-y: scroll;}
#form-container {width: 70%;padding:5px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %> 
<script type="text/javascript">//<![CDATA[
function reloadTree(){ 
	var node = $('#MyTree').tree('getSelected'); 
	if(node) $('#MyTree').tree('reload', node.target); 
	else $('#MyTree').tree('reload'); 
	$("#menuTab").datagrid("reload");	
} 
function createTree(){
	$('#MyTree').tree({
		//width:300,  
		height:$(document).height()-50,
		onBeforeExpand:function(node,param){  
			$('#MyTree').tree('options').url = "ptl40005Qry.html?pid=" + node.id;    
		},
		onClick:function(node){  
			$('#MyTree').tree("expand",node.target); 
		},
		onExpand:function(node){ 
			$('#MyTree').tree("select",node.target); 
		},
		onSelect:function(node){ 
			var id=node.id; 	 
			var $grid=$("#nemuTab");
			$grid.datagrid('options').queryParams={pid:id};  
			$grid.datagrid("reload"); 
			treeForm.parentfuncid.value=id; 
			treeForm.reset();
		}               
	}).tree("loadData",[{"id":"00000000","text":"根","state":"closed"}]);	
	//$("#p").height($(document).height()-30); 
	var node=$('#MyTree').tree("find","00000000");
	$('#MyTree').tree("expand",node.target); 
	$('#MyTree').tree("select",node.target); 
	return  $('#MyTree');
}

function createOperTable(){
	$("#nemuTab").datagrid({
		width:$('#formInfo').width(),
		height:$(document).height()-196,
		fitColumns: true,
		idField:'funcid',
		singleSelect:true,
		url:'ptl40005Qry.json',
		onClickRow:function(rowIndex, rowData){
			$('#formInfo').panel('setTitle','修改功能(菜单)信息');
			$('#treeForm').form('clear').form('load',rowData);
			/*
		 for(var key in rowData ){
			 if(treeForm[key]){
				if(rowData[key])
					$(treeForm[key]).val(rowData[key]);
				else
					treeForm[key].value="";
			 }   
		  }  
		  */
			treeForm.oldfuncid.value = rowData.funcid;
		},
		columns:[[
			{field:'op',title:'删除标记',width:100,formatter:function(v,row){
				return "<input type='checkbox' value='"+row["funcid"]+"' name='chk_funcid'>"
			}}, 
			{field:'orderid',title:'序号',width:100},
			{field:'funcid',title:'功能(菜单)代码',width:100},   
			{field:'funcname',title:'功能(菜单)名称',width:200},
			{field:'url',title:'url',width:200} 
		]],
		toolbar:[{	
			id:'btnadd',
			text:'新建',
			iconCls:'icon-add', 
			handler:function(){	
				$('#formInfo').panel('setTitle','添加功能(菜单)信息');
				$('#treeForm').form('clear');
				//treeForm.reset();		       
				var node = $('#MyTree').tree('getSelected'); 
				if(node) treeForm.parentfuncid.value = node.id;
				else treeForm.parentfuncid.value = "00000000";
			}
		},{	
			id:'btndel',
			text:'删除',
			iconCls:'icon-remove', 
			handler:function(){
				var funcs=[];  
				$("[name='chk_funcid']:checked").each(function(i){
					funcs.push($.trim($(this).val()));
				});
				if(funcs.length == 0){
					$.messager.alert('提示', "请至少选中一条记录进行维护！", 'info');
				}else{
					$.messager.confirm('提示','请确认是否删除?',function(r){
						if (r){
							var map={funcid:funcs.join(",")}
							$.ajax({
								type : "POST",
								url : "ptl40005Del.json",
								dataType: "json",
								data:map,
								success :function(data) {
									$.messager.alert('提示', data.msg, 'info');
									if(SUCCESS_CODE == data.recode){
										reloadTree();
										treeForm.reset();
										$("[name='funcid']").prop("readOnly",false);
									}
								},
								error :function(){
									$.messager.alert('错误','系统异常','error');							
								}
							});
						}
					})
				}
			}
		}]
	});
	return  $('#nemuTab');
}
    
$(function(){ 
	var $grid=createOperTable();
	createTree();
	$grid.datagrid('options').queryParams={pid:'00000000'};  
	$grid.datagrid("reload"); 
	treeForm.parentfuncid.value="00000000";  
	//保存
	$("#btnsave").click(function(){
		var mi002arry = $('#treeForm').serializeArray();        
		var url=null;     
		if(treeForm.oldfuncid.value!="") url="ptl40005Upd.json";
		else url="ptl40005Add.json";
		//校验
		var tgbz=$("#treeForm").form("validate");      
		if(tgbz){          
			$.ajax({	 
				type : "POST",
				url : url,
				dataType: "json",
				data:mi002arry,
				success :function(data) {
					$.messager.alert('提示', data.msg, 'info');
				    if (data.recode == SUCCESS_CODE) reloadTree();
					//else $.messager.alert('提示', data.msg, 'info' );
				},
				error :function(){
					$.messager.alert('错误','系统异常','error');
				}
			});
		} 
	}); 
	
	//设置页面结构高度宽度
	$('#layout').height($(document).height()-30);
	$('#tree-container').height($(document).height()-52).width($('#tree-container').width()-5);
	$('#form-container').height($(document).height()-45);
	//功能(菜单)信息表单标题
	$('#formInfo').panel({
		title:'添加功能(菜单)信息', 
		height:150,
		width:$('#formInfo').width()
	}).closest('.panel').css('padding-top','5px');
	
});
//]]></script>
</head> 
<body> 
<div id="layout" class="easyui-panel easyui-layout" title="功能(菜单)管理"> 
	<div id="tree-container" data-options="region:'west',split:false,border:true">
		<div id="MyTree"></div>
	</div>
	<div id="form-container" data-options="region:'center',border:false">
		<table id="nemuTab"></table>
		<div id="formInfo" class="easyui-panel"> 
			<form method="post" action="" id="treeForm" class="dlg-form" novalidate="novalidate">
			<input type='hidden' id="oldfuncid" name="oldfuncid" />
			<input type='hidden' id="parentfuncid" name="parentfuncid" />
			<table class="container">
				<col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" />
				<tr>
					<td><label for="orderid">序号：</label></td>
					<td><input type='text' id="orderid" name="orderid" required='true' class="easyui-validatebox" maxlength="2" size="20" /></td>
					<td><label for="funcname">功能(菜单)名称：</label></td>
					<td><input type='text' id="funcname" name="funcname" required='true' class="easyui-validatebox" size="20" /></td>
				</tr>
				<tr>
					<td><label for="funcid">功能(菜单)代码：</label></td>
					<td><input type='text' id="funcid" name="funcid" required='true' class="easyui-validatebox" maxlength="8" size="20" /></td>
					<td><label for="url">url：</label></td>
					<td><input type='text' id="url" name="url" required='true' class="easyui-validatebox" size="20" /></td>
				</tr>
			</table>
			<div class="buttons"><a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" id="btnsave">保存</a></div>
			</form>
		</div>
	 </div> 
</div> 
</body>
</html>