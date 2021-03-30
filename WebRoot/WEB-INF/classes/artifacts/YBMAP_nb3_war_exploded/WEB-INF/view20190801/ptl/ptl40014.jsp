<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>菜单子功能管理</title>
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
		height:$(document).height()-50,
		onBeforeExpand:function(node,param){  
			$('#MyTree').tree('options').url = "ptl40014Qry.html?pid=" + node.id;    
		},
		onClick:function(node){  
			$('#MyTree').tree("expand",node.target);
		},
		onExpand:function(node){
			$('#MyTree').tree("select",node.target); 
		},
		onSelect:function(node){ 
			var id=node.id; 	 
			var $grid=$("#menuTab");
			$grid.datagrid('options').queryParams={pid:id};
			$grid.datagrid("reload"); 
			//treeForm.parentfuncid.value=id; 
			treeForm.reset();
		}               
	}).tree("loadData",[{"id":"00000000","text":"根","state":"closed"}]);
	var node=$('#MyTree').tree("find","00000000");
	$('#MyTree').tree("expand",node.target); 
	$('#MyTree').tree("select",node.target); 
	return  $('#MyTree');
}

function createOperTable(){
	$("#menuTab").datagrid({
		width:$('#formInfo').width(),
		height:$(document).height()-196,
		fitColumns: true,
		singleSelect:false,
		url:'ptl40014Qry.json',
		onCheck:function(rowIndex, rowData){
			$('#formInfo').panel('setTitle','修改菜单子功能信息');
			$('#subname').prop('disabled',false);
			$('#subdesc').prop('disabled',false);
			$("#btnsave").linkbutton("enable");
			$('#treeForm').form('clear').form('load',rowData);
			//treeForm.oldfuncid.value = rowData.funcid;
			treeForm.oldSubname.value = rowData.subname;
			treeForm.operType.value = "mod";
		},
		columns:[[
			{field:'id',checkbox : true},
			{field:'subno',title:'序号',width:100},
			{field:'funcid',title:'所属功能编码',width:150},
			{field:'subname',title:'菜单子功能名称(程序中使用)',width:200},
			{field:'subdesc',title:'菜单子功能描述',width:200}
		]],
		toolbar:[{	
			id:'btnadd',
			text:'新建',
			iconCls:'icon-add', 
			handler:function(){
				// 是否可以新建的校验
				var grid = $('#menuTab');  
				var length = grid.datagrid('getRows').length;
				if(length >= 8){
					$.messager.alert('提示', "子功能数已满，不能再添加！", 'info');
				}else{
					// 允许新建
					$('#subname').prop('disabled',false);
					$('#subdesc').prop('disabled',false);
					$("#btnsave").linkbutton("enable");
					$('#formInfo').panel('setTitle','添加菜单子功能信息');
					$('#treeForm').form('clear');
					var node = $('#MyTree').tree('getSelected'); 
					if(node) treeForm.funcid.value = node.id;
					else treeForm.funcid.value = "00000000";
					treeForm.subno.value = length+1;
				}


			}
		},{	
			id:'btndel',
			text:'删除',
			iconCls:'icon-remove', 
			handler:function(){
			 	var rows = $('#menuTab').datagrid('getSelections');
				if(rows.length == 0){
					$.messager.alert('提示', "至少选中一条记录进行维护！", 'info');
				}else{
					$.messager.confirm('提示','确认是否删除?',function(r){
						if(r){
							var para1 = rows[0].funcid;
							var para2 = rows[0].subname;
             				for(var i=1;i<rows.length;i++){
             					para1 = para1 + "," + rows[i].funcid;
             					para2 = para2 + "," + rows[i].subname;
             				}

            				var map={funcid:para1,subname:para2} 
							$.ajax({	 
								type : "POST",
								url : "ptl40014Del.json",
								dataType: "json",
								data:map,
								success :function(data) {
									$.messager.alert('提示', data.msg, 'info');
									if(SUCCESS_CODE == data.recode){
										reloadTree();
										treeForm.reset();
										$('#subname').prop('disabled',true);
										$('#subdesc').prop('disabled',true);
										$("#btnsave").linkbutton("disable");
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
	return  $('#menuTab');
}
    
$(function(){ 
	var $grid=createOperTable();
	createTree();
	$grid.datagrid('options').queryParams={pid:'00000000'};  
	$grid.datagrid("reload"); 
	//treeForm.parentfuncid.value="00000000";  
	// 设置FORM的可输入控制
	$('#subname').prop('disabled',true);
	$('#subdesc').prop('disabled',true);
	$("#btnsave").linkbutton("disable");
	
	//保存
	$("#btnsave").click(function(){
		var mi014arry = $('#treeForm').serializeArray();        
		var url=null;     
		//if(treeForm.oldfuncid.value!="") url="ptl40014Upd.json";
		//else url="ptl40014Add.json";
		if(treeForm.operType.value=="mod") url="ptl40014Upd.json";
		else url="ptl40014Add.json";
		//校验
		var tgbz=$("#treeForm").form("validate");      
		if(tgbz){          
			$.ajax({	 
				type : "POST",
				url : url,
				dataType: "json",
				data:mi014arry,
				success :function(data) {
					$.messager.alert('提示', data.msg, 'info');
				    if(data.recode==SUCCESS_CODE)					    
					   	reloadTree();
						$('#subname').prop('disabled',true);
						$('#subdesc').prop('disabled',true);
						$("#btnsave").linkbutton("disable");
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
	//菜单子功能信息表单标题
	$('#formInfo').panel({
		title:'添加菜单子功能信息', 
		height:150,
		width:$('#formInfo').width()
	}).closest('.panel').css('padding-top','5px');
	
});
//]]></script>
</head> 
<body> 
<div id="layout" class="easyui-panel easyui-layout" title="菜单子功能管理"> 
	<div id="tree-container" data-options="region:'west',split:false,border:true">
		<div id="MyTree"></div>
	</div>
	<div id="form-container" data-options="region:'center',border:false">
		<table id="menuTab"></table>
		<div id="formInfo" class="easyui-panel"> 
			<form method="post" action="" id="treeForm" class="dlg-form" novalidate="novalidate">
			<input type='hidden' id="oldSubname" name="oldSubname" />
			<input type='hidden' id="operType" name="operType" />
			<table class="container">
				<col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" />
				<tr>
					<td><label for="subname">菜单子功能名称：</label></td>
					<td>
						<input type='hidden' id="subno" name="subno" />
						<input type='hidden' id="funcid" name="funcid"/>
						<input type='text' id="subname" name="subname" required='true' class="easyui-validatebox" size="20" /></td>
					<td>
						<label for="subdesc">菜单子功能描述：</label>
					</td>
					<td>
						<input type='text' id="subdesc" name="subdesc" required='true' class="easyui-validatebox" size="20" />
					</td>
				</tr>
			</table>
			<div class="buttons"><a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" id="btnsave">保存</a></div>
			</form>
		</div>
	 </div> 
</div> 
</body>
</html>