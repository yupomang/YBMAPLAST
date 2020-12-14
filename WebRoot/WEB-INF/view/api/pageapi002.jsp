<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="com.yondervision.mi.dto.Mi001"%>
<%@page import="com.yondervision.mi.dto.Mi007"%>
<%@page import="java.util.List"%>
<%@ include file="/include/init.jsp"%>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%
	UserContext user = UserContext.getInstance();

	if (user == null) {
		out.println("超时");
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="contexPath" content="<%=_contexPath%>" />
		<title>API平台信息配置</title>
		<%@ include file="/include/styles.jsp"%>
		<style type="text/css">
/*<![CDATA[*/
.datagrid {
	padding-top: 5px;
}

.panel-header,.panel-body {
	width: auto !important;
}
/*]]>*/
</style>
		<%@ include file="/include/scripts.jsp"%>
		<script type="text/javascript">
var url;
var ctid;
var centerid;
$(function() {
	//初始化列表
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'平台信息',
		height:500,
		singleSelect: true,		
		//pagination:true,
		rownumbers:true,
		fitColumns:true,		
		method:'post',		
		url: "<%=request.getContextPath()%>/apiCtrl00201.json",
		columns:[[
			{field:'ck',title:'  ',align:'center',formatter:function (value,row,index){				
				return '<input type="checkbox" name="animate" value="' + row.name + '"/>';
			}},			
			//{title:'城市中心',field:'regionId',align:'center',width:110,editor:'text',formatter:function (value,row,index){	
			//	return $('#regionId option[value='+value+']').text(); 	
			//}},			
			{title:'平台名',field:'name',align:'center',width:80,editor:'text'},			
			{title:'回调URL',field:'callback',align:'center',width:100,editor:'text'}									
		]],
		toolbar: '#toolbar',
		onLoadSuccess:function(data){
			if(data.code != '0') $.messager.alert('提示', data.msg, 'info' );
        }
	});
	
	//查询
	$('#b_query').click(function (){		
		if (!$("#formweixin001").form("validate")) return;		
		var arr = $("#formweixin001").serializeArray();
		var dataJson = arrToJson(arr);
		$('#dg').datagrid({
			url: "<%=request.getContextPath()%>/weixinapi00102.json",
			queryParams:dataJson	
		});	
	});	
	
	//重置
	$('#b_reset').click(function (){
		//document.getElementById("form40101").reset(); 
		$('#formweixin001').form('clear');
		$('#devid').combobox('select','');
		$('#centerid').combobox('select','');
		//除去错误校验状态
		ydl.delErrorMessage('formweixin001');
	});
	
	//增加
	$('#b_add').click(function (){
		$('#dlg').dialog('open').dialog('setTitle','平台基础信息配置');		
		$('#formtitle').text('平台基础信息配置');
		$('#fm').form('clear');		
		url="<%=request.getContextPath()%>/apiCtrl00202.json"		
	});
	
	//修改
	$('#b_edit').click(function (){
		var row = $('#dg').datagrid('getSelections');
		if (row.length == 1){
			if (row){							
				$('#dlg').dialog('open').dialog('setTitle','平台基础信息配置修改');
				$('#formtitle').text('平台基础信息配置修改');
				$('#fm').form('load',row[0]);
				url = "<%=request.getContextPath()%>/apiCtrl00203.json";
			}
		}
		else $.messager.alert('提示', '请选中一条待修改平台基础信息！', 'info' );
	});
	
	//保存
	$('#b_save').click(function (){		
		if (!$("#fm").form("validate")) return;
		var arr = $('#fm').serializeArray();
			
		$.ajax({
			type : "POST",
			url : url,
			dataType: "json",
			data:arr,			
			success : function(data) {
				if (data.code == 0) {					
					$('#dg').datagrid("reload");    	// reload the user data
					$('#dlg').dialog('close');        	// close the dialog						
					$.messager.alert('提示','成功','info');					
				}else{
					$.messager.alert('提示',data.msg,'info');
				}					
			},
			error :function() {
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
		
	});
	//删除
	$('#b_del').click(function (){
		//var row = $('#dg').datagrid('getSelections');        
		//if (row.length > 0) {
			//var code=row[0].versionid;            	
			//for(var i=1;i<row.length;i++){
			//	code = code + ","+row[i].versionid;
			//} 
		var $checkboxs = $('input[name=animate]:checkbox:checked');
		var code;
		if ($checkboxs.length > 0) {
			code = $checkboxs.map(function(index){				
				return this.value;
			}).get().join(',');			
			var arr = [{name : "animateid",value : code}];
			$.messager.confirm('提示','确认是否删除?',function(r){
				if(r){
					$.ajax({
						type : "POST",
						url : "<%=request.getContextPath()%>/webapi41102.json",
						dataType: "json",
						data:arr,
						success : function(data) {
							if (data.recode == SUCCESS_CODE) {
								$('#dg').datagrid('reload');    // reload the user data
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
		else $.messager.alert('提示', '请选中待删除信息！', 'info' );
	});
	
	//取消
	$('#b_cancel').click(function (){
		$('#dlg').dialog('close');
	});
	
	
	
	//addon
	$('#b_get_addon').click(function (){		
		var row = $('#dg').datagrid('getSelections');
		if (row.length == 1){
			if (row){							
				$('#dlg_addon').dialog('open').dialog('setTitle','平台参数信息配置修改');
				$('#formtitle_addon').text('平台参数信息配置修改');
				$('#fm').form('load',row[0]);
				if (!$("#fm").form("validate")) return;
				var arr = $('#fm').serializeArray();
				url = "<%=request.getContextPath()%>/apiCtrl00204.json?name="+row[0].name;
				centerid = row[0].name;
				$('#dg_addon').datagrid({   
					iconCls: 'icon-edit',
					title:'参数信息',
					height:380,
					singleSelect: true,		
					//pagination:true,
					rownumbers:true,
					fitColumns:true,		
					method:'post',		
					url: url,					
					columns:[[
						{field:'ck',title:'  ',align:'center',formatter:function (value,row,index){				
							return '<input type="checkbox" name="paraname" value="' + row.name + '"/>';
						}},			
						{title:'参数名称',field:'name',align:'center',width:80,editor:'text'},			
						{title:'参数值',field:'value',align:'center',width:100,editor:'text'}									
					]],
					toolbar: '#toolbar_addon',
					onLoadSuccess:function(data){
						if(data.code != '0') $.messager.alert('提示', data.msg, 'info' );
			        }
				});				
			}
		}
		else $.messager.alert('提示', '请选中一条待修改平台参数信息！', 'info' );
	});
	
	$('#b_add_addon').click(function (){
		$('#dlg_addon_pare').dialog('open').dialog('setTitle','平台参数信息配置添加');		
		$('#formtitle_addon_pare').text('平台参数信息配置添加');
		$('#fm_addon_pare').form('clear');		
		url="<%=request.getContextPath()%>/apiCtrl00205.json?centerid="+centerid;		
	});
	
	$('#b_edit_addon').click(function (){
		var row = $('#dg_addon').datagrid('getSelections');
		if (row.length == 1){		
			if (row){							
				$('#dlg_addon_pare').dialog('open').dialog('setTitle','平台参数信息配置修改');
				$('#formtitle_addon_pare').text('平台参数信息配置修改');
				$('#fm_addon_pare').form('load',row[0]);
				url = "<%=request.getContextPath()%>/apiCtrl00205.json?centerid="+centerid;
			}
		}
		else $.messager.alert('提示', '请选中一条待修改平台参数信息！', 'info' );
	});
	
	$('#b_save_addon_pare').click(function (){		
		if (!$("#fm_addon_pare").form("validate")) return;
		var arr = $('#fm_addon_pare').serializeArray();
		$.ajax({
			type : "POST",
			url : url,
			dataType: "json",
			data:arr,			
			success : function(data) {
				if (data.code == 0) {					
					$('#dg_addon').datagrid("reload");    	// reload the user data
					$('#dlg_addon_pare').dialog('close');        	// close the dialog						
					$.messager.alert('提示','成功','info');
				}else{
					$.messager.alert('提示',data.msg,'info');
				}					
			},
			error :function() {
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
		
	});
	
	$('#b_cancel_addon_pare').click(function (){
		$('#dlg_addon_pare').dialog('close');
	});
	
	
	$('#b_del_addon').click(function (){
		var $checkboxs = $('input[name=paraname]:checkbox:checked');
		var code;
		if ($checkboxs.length > 0) {
			code = $checkboxs.map(function(index){				
				return this.value;
			}).get().join(',');
			var arr = [{name : "animateid",value : code}];
			$.messager.confirm('提示','确认是否删除?',function(r){
				if(r){
					$.ajax({
						type : "POST",
						url : "<%=request.getContextPath()%>/apiCtrl00206.json?centerid="+centerid+"&name="+code,
						dataType: "json",
						//data:arr,
						success : function(data) {
							if (data.code == SUCCESS_CODE) {
								$('#dg_addon').datagrid('reload');    // reload the user data
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
		else $.messager.alert('提示', '请选中待删除信息！', 'info' );
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
})




//数据转对象(无重复name值的数平台)
function arrToJson(arr){
	var jsonObj = {};
	$.each(arr,function (index,ele){
		jsonObj[ele.name] = ele.value;
	});
	return jsonObj;
}




</script>
	</head>
	<body>
		<script type="text/javascript"
			src="<%=_contexPath%>/scripts/jquery.form.js"></script>
		<!-- jquery.form.js 为页面提交json请求应用 
		<div class="easyui-panel" title="中心微信基础信息查询">
			<form id="formweixin001" class="dlg-form" novalidate="novalidate">
				<table class="container">				
					<col width="50%" />
					<col width="50%" />	
					<tr>
						<th>
							<label for="regionId">
								城市中心:
							</label>
						</th>
						<td>
							<select id="regionId" name="regionId" class="easyui-combobox" editable="false" style="width:250px;" panelHeight="auto">
							
							<option value="">请选择...</option>
							
					    </select>					
						</td>						
					</tr>				
				</table>
				<div class="buttons">
					<a id="b_query" class="easyui-linkbutton" iconCls="icon-search"
						href="javascript:void(0)">查询</a>
					<a id="b_reset" class="easyui-linkbutton" iconCls="icon-clear"
						href="javascript:void(0)">重置</a>
				</div>
			</form>
		</div>-->
		<table id="dg"></table>
		<div id="toolbar">
			<a id="b_add" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">增加</a>
			<a id="b_edit" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">修改</a>
			<a id="b_get_addon" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">参数</a>			
		</div>
		<div id="dlg" class="easyui-dialog"
			style="width: 550px; height: 370px; padding: 10px 20px" modal="true"
			closed="true" buttons="#dlg-buttons">
			<div class="formtitle" id="formtitle"></div>
			<form id="fm" class="dlg-form" method="post" novalidate="novalidate">
				<table class="dlgcontainer">
					<col width="30%" />
					<col width="70%" />					
					<tr>
						<td>
							<label for="name">
								平台名称:
							</label>
						</td>
						<td>
							<input type="text" id="name" name="name"
								weixinId"" required="true" validType="length[0,200]"
								class="easyui-validatebox" style="width: 248px;"/>
						</td>
					</tr>
					<tr>
						<td>
							<label for="callback">
								所属平台:
							</label>
						</td>
						<td>
							<input type="text" id="callback" name="callback" validType="length[0,400]"
								class="easyui-validatebox" style="width: 248px;"/>
						</td>
					</tr>					
				</table>
			</form>
		</div>
		<div id="dlg-buttons">
			<a id="b_save" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-ok">保存</a>
			<a id="b_cancel" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel">关闭</a>
		</div>
		<div id="dlg_addon" class="easyui-dialog"
			style="width: 750px; height: 500px; padding: 10px 20px" modal="true"
			closed="true">
			<div id="toolbar_addon">
				<a id="b_add_addon" href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-add" plain="true">增加</a>
				<a id="b_edit_addon" href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true">修改</a>
				<a id="b_del_addon" href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true">删除</a>			
			</div>
			<div class="formtitle" id="formtitle_addon"></div>
			<table id="dg_addon"></table>				
			<div id="dlg_addon_pare" class="easyui-dialog"
				style="width: 550px; height: 370px; padding: 10px 20px" modal="true"
				closed="true" buttons="#dlg-buttons1">
				<div class="formtitle" id="formtitle_addon_pare"></div>
				<form id="fm_addon_pare" class="dlg-form" method="post" novalidate="novalidate">
					<table class="dlgcontainer">
						<col width="30%" />
						<col width="70%" />					
						<tr>
							<td>
								<label for="name">
									平台名称:
								</label>
							</td>
							<td>
								<input type="text" id="name" name="name"
									weixinId"" required="true" validType="length[0,200]"
									class="easyui-validatebox" style="width: 248px;"/>
							</td>
						</tr>
						<tr>
							<td>
								<label for="value">
									所属平台:
								</label>
							</td>
							<td>
								<input type="text" id="value" name="value" validType="length[0,200]"
									class="easyui-validatebox" style="width: 248px;"/>
							</td>
						</tr>					
					</table>
				</form>					
			</div>					
			<div id="dlg-buttons1">
				<a id="b_save_addon_pare" href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-ok">保存</a>
				<a id="b_cancel_addon_pare" href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-cancel">关闭</a>
			</div>	
		</div>
	</body>
</html>
