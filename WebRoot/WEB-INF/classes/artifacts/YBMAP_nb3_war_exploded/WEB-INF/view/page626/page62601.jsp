<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@ include file = "/include/init.jsp" %>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%
  UserContext user=UserContext.getInstance();

  if(user==null)  {
     out.println("超时");
     return;
  }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>预约注意事项</title>
<%@ include file = "/include/styles.jsp" %>
<%@ include file = "/include/scripts.jsp" %>
<style type="text/css">
/*<![CDATA[*/
#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
body {width: 100%; hidden;margin:0;}
.datagrid {padding-top:5px;}
.panel-header, .panel-body {width:auto !important;}

.dlg_wrapper {padding:5px;}
#lr_buttons {margin:112px 0px 0px 205px;width:60px;}
#selnames {margin:-185px 0px 0px 265px;border: 1px solid #95B8E7;height:317px;}
#selnames div {background-color:#B2D6F5;padding: 2px 3px;}
#selnames ul {height:295px; overflow: auto;}
#selnames ul li{padding: 2px 3px;display:block;cursor:default;}

.selected {background-color:#FBEC88 !important;}
.hover {background-color:#EAF2FF;}

#b_left,#b_right{ margin-left: 3px;
    margin-right: 3px;
    padding-left: 1px;
    padding-right: 1px;
    width: 50px;}
	
.combobox-item {cursor:default;}	
/*]]>*/
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=7db9536a2794ce477288908163157bc6"></script>
<script type="text/javascript">
var url;
$(function() {

	//列表初始化
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'预约注意事项',
		singleSelect: true,
		height:369,
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		method:'post',
		url: "<%=request.getContextPath()%>/webapi62604.json",
		columns:[[
			{field:'ck',field:'checkbox',title:'删除',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" value="' + row.appoattenid + '"/>';
			}},
			{title:'编号',field:'appoattenid',align:'center',width:150,editor:'text'},
			{title:'注意事项',field:'templatename',align:'center',width:130,editor:'text'},
			{title:'有效标记',field:'validflag',align:'center',width:60,editor:'text',formatter:function(v){
		        var json={"0":"禁用","1":"启用"};
		        if(json[v]){
		           return json[v];
		        }else{
		           return v;
		        }
		    }},
			{title:'中心码',field:'centerid',align:'center',width:100,editor:'text'},
			{title:'操作员码',field:'loginid',align:'center',width:100,editor:'text'},
			{title:'创建日期',field:'datecreated',align:'center',width:180,editor:'text'},
			{title:'最近修改日期',field:'datemodified',align:'center',width:180,editor:'text'},
			{title:'备用字段1',field:'freeuse1',align:'center',width:60,editor:'text',hidden:'true'},
			{title:'备用字段2',field:'freeuse2',align:'center',width:60,editor:'text',hidden:'true'},
			{title:'备用日期',field:'freeuse3',align:'center',width:90,editor:'text',hidden:'true'},
			{title:'备用数值',field:'freeuse4',align:'center',width:90,editor:'text',hidden:'true'	}
		]],
		toolbar:'#toolbar',
		onLoadSuccess:function(data){           
           if (data.recode != SUCCESS_CODE) $.messager.alert('提示', '【查询出错】：'+data.msg, 'info' );
        }		
	});
})


//增加
function newUser(){
	$('#dlg').dialog('open').dialog('setTitle','预约注意事项增加');
	$('#fm').form('clear');
	$('#validflag-0').attr("checked",true);
	$('#templatename')[0].focus();
	url = "<%=request.getContextPath()%>/webapi62601.json";
}
//删除
function destroyUser(){
	var $checkboxs = $('input:checkbox:checked');
	if ($checkboxs.length > 0) {
		var code = $checkboxs.map(function(index){
			return this.value;
		}).get().join(',');
		var arr = [{name : "appoattenid",value : code}];
		$.messager.confirm('提示','确认是否删除?',function(r){
			if(r){
				$.ajax({
					type : "POST",
					url : "<%=request.getContextPath()%>/webapi62602.json",
					dataType: "json",
					data:arr,
					success : function(data) {
						if (data.recode != SUCCESS_CODE) $.messager.alert('提示', '【删除出错】：'+data.msg, 'info' );
						else {
							$.messager.alert('提示', data.msg, 'info' );						
							$('#dg').datagrid('reload');    // reload the user data
						}
					},
					error :function(){
						$.messager.alert('错误','网络连接出错！','error');							
					}
				});
			}					
		});
	}
	else $.messager.alert('提示', "请选中待删除信息！", 'info' );
}
//修改
function editUser(){     
	var row = $('#dg').datagrid('getSelections');
	if(row.length==1){
		if (row){
			$('#dlg').dialog('open').dialog('setTitle','预约注意事项修改');
			$('#fm').form('load',row[0]);
			url = "<%=request.getContextPath()%>/webapi62603.json";
		}
	}
	else $.messager.alert('提示', "请选中一条待修改楼盘信息！", 'info' );
}
//保存
function saveUser(){            
	var arr = $('#fm').serializeArray();
	//校验
	if (!$("#fm").form("validate")) return;		
	$.ajax({
		type : "POST",
		url : url,
		dataType: "json",
		data:arr,
		success : function(data) {					
			if (data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
			else {
				$.messager.alert('提示', data.msg, 'info' );
				$('#dlg').dialog('close');        // close the dialog
				$('#dg').datagrid('reload');    // reload the user data
			}
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});						
}
</script>
</head>
  
<body>
    

<!-- 列表数据 -->
<table id="dg" ></table>
<div id="toolbar">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">增加</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
</div>
<!-- 添加/修改对话框 -->
<div id="dlg" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px" closed="true" modal="true" buttons="#dlg-buttons">  
	<div class="formtitle">预约注意事项</div>
	<form id="fm" class="dlg-form" method="post" novalidate="novalidate">
		<table class="dlgcontainer">
			<tr hidden="true">	        
				<td><label for="appoattenid">编号:</label></td>
				<td><input type="text" id="appoattenid" name="appoattenid" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
			<tr>	        
				<td><label for="validflag">有效标记:</label></td>
				<!-- <td><input type="text" id="validflag" name="validflag" required="true" class="easyui-validatebox"/></td> 
				-->
				<td>  	
					<ul class="multivalue horizontal">
						<li><input id="validflag-0" name="validflag" type="radio" value="0"/><label for="validflag-0">禁用</label></li>
						<li><input id="validflag-1" name="validflag" type="radio" value="1"/><label for="validflag-1">启用</label></li>
					</ul>
				</td>
			</tr>
			<tr>
				<td><label for="templatename">注意事项:</label></td>
				<td><textarea id="templatename" name="templatename" validType="length[1,300]" required="true" class="easyui-validatebox" style="width:200px;height:150px" ></textarea></td>		        	
			</tr>
			<tr hidden="true">	        
				<td><label for="centerid">中心码:</label></td>
				<td><input type="text" id="centerid" name="centerid" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
			<tr hidden="true">	        
				<td><label for="loginid">操作人员:</label></td>
				<td><input type="text" id="loginid" name="loginid" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
			<tr hidden="true">	        
				<td><label for="datecreated">创建日期:</label></td>
				<td><input type="text" id="datecreated" name="datecreated" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
			<tr hidden="true">	        
				<td><label for="datemodified">最近修改日期:</label></td>
				<td><input type="text" id="datemodified" name="datemodified" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
			<tr hidden="true">	        
				<td><label for="freeuse1">备用字段1:</label></td>
				<td><input type="text" id="freeuse1" name="freeuse1" class="easyui-validatebox" readOnly="true" /></td>
			</tr>
			<tr hidden="true">	        
				<td><label for="freeuse2">备用字段2:</label></td>
				<td><input type="text" id="freeuse2" name="freeuse2" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
			<tr hidden="true">	        
				<td><label for="freeuse3">备用日期:</label></td>
				<td><input type="text" id="freeuse3" name="freeuse3" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
			<tr hidden="true">	        
				<td><label for="freeuse4">备用数字:</label></td>
				<td><input type="text" id="freeuse4" name="freeuse4" class="easyui-validatebox" readOnly="true"/></td>
			</tr>
		  </table> 
	</form>
</div>
<div id="dlg-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
</div>
  
</body>
</html>
