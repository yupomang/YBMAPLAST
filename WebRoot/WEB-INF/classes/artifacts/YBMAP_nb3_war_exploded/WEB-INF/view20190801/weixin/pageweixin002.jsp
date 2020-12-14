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
		<title>微信功能配置</title>
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
$(function() {
	//初始化列表
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'微信功能',
		height:530,
		singleSelect: true,		
		//pagination:true,
		rownumbers:true,
		fitColumns:true,
		method:'post',		
		url: "<%=request.getContextPath()%>/weixinapi00202.json",
		columns:[[
			//{field:'ck',title:'删除',align:'center',formatter:function (value,row,index){				
			//	return '<input type="checkbox" name="animate" value="' + row.animateid + '"/>';
			//}},					
			{title:'包名',field:'regionId',align:'center',width:100,editor:'text'},			
			{title:'类名',field:'funcName',align:'center',width:100,editor:'text'},
			{title:'路径',field:'className',align:'center',width:160,editor:'text'},
			{title:'KEY',field:'keyname',align:'center',width:100,editor:'text'},
			{title:'描述',field:'nickname',align:'center',width:100,editor:'text'}						
		]],
		toolbar: '#toolbar',
		onLoadSuccess:function(data){ 
			if(data.errcode != 0) $.messager.alert('提示', data.errmsg, 'info' );
        }
	});
	
	//查询
	$('#b_query').click(function (){		//校验
		if (!$("#form41101").form("validate")) return;
		//组织查询条件
		var arr = $("#form41101").serializeArray();
		var dataJson = arrToJson(arr);
		//查询列表数据
		$('#dg').datagrid('options').queryParams = dataJson;//修改查询条件
		$('#dg').datagrid('reload');
	});	
	
	//重置
	$('#b_reset').click(function (){
		//document.getElementById("form40101").reset(); 
		$('#form41101').form('clear');
		$('#devid').combobox('select','');
		$('#centerid').combobox('select','');
		//除去错误校验状态
		ydl.delErrorMessage('form41101');
	});
	
	//增加
	$('#b_add').click(function (){
		$('#dlg').dialog('open').dialog('setTitle','微信功能配置');		
		$('#formtitle').text('微信功能配置');
		$('#fm').form('clear');
		url = "<%=request.getContextPath()%>/weixinapi00201.json";
	});
	
	//修改
	$('#b_edit').click(function (){
		var row = $('#dg').datagrid('getSelections');
		if (row.length == 1){
			if (row){
				$('#dlg').dialog('open').dialog('setTitle','微信功能配置修改');
				$('#formtitle').text('微信功能配置修改');
				$('#fm').form('load',row[0]);
				url = "<%=request.getContextPath()%>/weixinapi00201.json";
			}
		}
		else $.messager.alert('提示', '请选中一条待修改微信功能信息！', 'info' );
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
				if (data.errcode == 0) {
					$('#dlg').dialog('close');        	// close the dialog
					$('#dg').datagrid('reload');    	// reload the user data
					$.messager.alert('提示','成功','info');
				}else{
					$.messager.alert('提示',data.errmsg,'info');
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
	
	
	
})




//数据转对象(无重复name值的数组)
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
		<table id="dg"></table>
		<div id="toolbar">
			<a id="b_add" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true">增加</a>
			<a id="b_edit" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true">修改</a>			
		</div>
		<div id="dlg" class="easyui-dialog"
			style="width: 400px; height: 370px; padding: 10px 20px" modal="true"
			closed="true" buttons="#dlg-buttons">
			<div class="formtitle" id="formtitle"></div>
			<form id="fm" class="dlg-form" method="post" novalidate="novalidate">
				<table class="dlgcontainer">
					<col width="30%" />
					<col width="70%" />
					<tr>
						<td>
							<label for="regionId">
								包名:
							</label>
						</td>
						<td>
							<input type="text" id="regionId" name="regionId"
								required="true" validType="length[0,200]"
								class="easyui-validatebox" style="width: 200px;"/>
						</td>
					</tr>
					
					<tr>
						<td>
							<label for="funcName">
								类名:
							</label>
						</td>
						<td>
							<input type="text" id="funcName" name="funcName"
								required="true" validType="length[0,200]"
								class="easyui-validatebox" style="width: 200px;"/>
						</td>
					</tr>
					<tr>
						<td>
							<label for="className">
								路径:
							</label>
						</td>
						<td>
							<input type="text" id="className" name="className" required="true"
								validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,200]"
								class="easyui-validatebox" style="width: 200px;"/>
						</td>
					</tr>
					<tr>
						<td>
							<label for="keyname">
								KEY:
							</label>
						</td>
						<td>
							<input type="text" id="keyname" name="keyname" required="true"
								validType="length[0,200]"
								class="easyui-validatebox" style="width: 200px;"/>
						</td>
					</tr>	
					<tr>
						<td>
							<label for="nickname">
								描述:
							</label>
						</td>
						<td>
							<input type="text" id="nickname" name="nickname" required="true"
								validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,200]"
								class="easyui-validatebox" style="width: 200px;"/>
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

	</body>
</html>
