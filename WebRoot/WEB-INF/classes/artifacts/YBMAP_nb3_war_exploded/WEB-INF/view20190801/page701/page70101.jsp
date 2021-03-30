<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.dto.Mi702"%>
<%@page import="com.yondervision.mi.dto.Mi007"%>
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
<title>报刊期次管理</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
.datagrid {padding-top:5px;}
.panel-header, .panel-body {width:auto !important;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript">
var url;
$(function() {
	//初始化列表
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'报刊期次详细信息',
		height:369,
		singleSelect: true,
		url: "<%=request.getContextPath()%>/webapi70104.json",
		method:'post',
		queryParams:{qry_itemid:''},
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		columns:[[
			{field:'ck',title:'删除',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" value="' + row.seqno + '"/>';
			}},
			//{title:'期次编号',field:'itemid',align:'center',width:120},
			{title:'期次名称',field:'itemval',align:'center',width:200,editor:'text'},
			{title:'创建日期',field:'createdate',align:'center',width:80,editor:'text'},	
			{title:'创建人',field:'createuser',align:'center',width:100,editor:'text'},
			{title:'修改日期',field:'modifieddate',align:'center',width:80,editor:'text'},
			{title:'修改人',field:'modifieduser',align:'center',width:100,editor:'text'},
			{title:'发布日期',field:'publishdate',align:'center',width:80,editor:'text'},
			{title:'发布人',field:'publishuser',align:'center',width:100,editor:'text'},		
			{title:'是否发布',field:'publishflag',align:'center',width:70,formatter:function(value,row,index) {	
				return $('#publishflagList option[value='+value+']').text(); 	
			}},
			{title:'期次ID',field:'seqno',hidden:true,align:'center',width:120}
		]],
		toolbar: '#toolbar',
		onLoadSuccess:function(data){ 
			if (data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
        }		
	});	
	
	//查询
	$('#b_query').click(function (){
		//校验
		if (!$("#form70104").form("validate")) return;
		
		var arr = $('#form70104').serializeArray();
		var dataJson = arrToJson(arr);
		//datagrid列表
		$('#dg').datagrid('options').queryParams = dataJson;//修改查询条件
		$('#dg').datagrid('reload');
	});
	
	//增加
	$('#b_add').click(function (){
		$('#dlg').dialog('open').dialog('setTitle','报刊期次增加');
		$('#formtitle').text('报刊期次增加');
		$('#fm').form('clear');
		url = "<%=request.getContextPath()%>/webapi70101.json";
	});
	
	//修改
	$('#b_edit').click(function (){
		var row = $('#dg').datagrid('getSelections');
		if (row.length == 1){
			if (row){
				if(row[0].publishflag==0){										
					$('#dlg').dialog('open').dialog('setTitle','报刊期次修改');
					$('#formtitle').text('报刊期次修改');
					$('#fm').form('load',row[0]);
					url = "<%=request.getContextPath()%>/webapi70103.json";
				}else{
					$.messager.alert('提示', '该期次己发布不能修改！', 'info' );
				}
			}
		}
		else $.messager.alert('提示', '请选中一条记录进行修改！', 'info' );
	});
	
	//保存
	$('#b_save').click(function (){
		//校验
		if (!$("#fm").form("validate")) return;
		var arr = $('#fm').serializeArray();
		$.ajax({
			type : "POST",
			url : url,
			dataType: "json",
			data:arr,
			success : function(data) {
				if (data.recode == SUCCESS_CODE) {
					$('#dlg').dialog('close');        	// close the dialog
					$('#dg').datagrid('reload');    	// reload the user data 
				}
				$.messager.alert('提示',data.msg,'info');	
			},
			error :function() {
				$.messager.alert('错误','网络连接出错！','error');
			}
		});
	});

	//删除
	$('#b_del').click(function (){
		var $checkboxs = $('input:checkbox:checked');
		if ($checkboxs.length > 0) {
			var code = $checkboxs.map(function(index){
				return this.value;
			}).get().join(',');
			var arr = [{name : "seqnoList",value : code}];
			$.messager.confirm('提示','请注意：与该期次相关的所有内容都将被删除，仍要删除该期次吗？',function(r){
				if(r){
					$.ajax({
						type : "POST",
						url : "<%=request.getContextPath()%>/webapi70102.json",
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
		else $.messager.alert('提示', '请至少选择一条记录进行删除！', 'info' );
	});
	
	//发布
	$('#b_publish').click(function (){		
		var row = $('#dg').datagrid('getSelections');
		if (row.length == 1){
			if (row){
				if(row[0].publishflag==0){					
					$('#fm').form('load',row[0]);
					url = "<%=request.getContextPath()%>/webapi70105.json";
					$.messager.confirm('提示','确定要发布此期刊吗？',function(r){
						if (r){
							$.ajax({
								type : "POST",
								url : url,
								dataType: "json",
								data:row[0],
								success : function(data) {
									if (data.recode == SUCCESS_CODE) {
										$('#dlg').dialog('close');        	// close the dialog
										$('#dg').datagrid('reload');    	// reload the user data
									}
									$.messager.alert('提示',data.msg,'info');	
								},
								error :function() {
									$.messager.alert('错误','网络连接出错！','error');
								}
							});
						}
					});
				}else{
					$.messager.alert('提示', '该期次己经发布！', 'info' );
				}
			}
		}
		else $.messager.alert('提示', '请至少选择一条记录进行发布！', 'info' );
	});
	
	
	//取消
	$('#b_cancel').click(function (){
		$('#dlg').dialog('close');
	});
	
		//重置
	$('#b_reset').click(function (){
		$('#form70104').form('clear');
		$('#qry_itemid').val('');
		ydl.delErrorMessage('form70001');
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
<div class="easyui-panel" title="报刊期次信息查询">
	<form id="form70104" class="dlg-form" novalidate="novalidate">
		<table class="container">	
			<col width="40%" /><col width="60%" />
			<tr>	        
				<th><label for="qry_itemid">报刊期次:</label></th>
				<td>
				<input type="text" id="qry_itemid" name="qry_itemid" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,50]" style="width:200px;"/>
				</td>
			</tr>
		</table> 
		<div class="buttons">
			<a id="b_reset" class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" >重置</a>
			<a id="b_query" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
		</div>
	</form>
</div>

<table id="dg"></table>

<div id="toolbar">
	<a id="b_add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" >增加</a>
	<a id="b_edit" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >修改</a>
	<a id="b_del" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >删除</a>
	<a id="b_publish" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" >发布</a>	        
</div>

<div id="dlg" class="easyui-dialog" style="width:400px;height:300px;padding:10px 20px" modal="true" closed="true" buttons="#dlg-buttons">
	<div class="formtitle" id="formtitle"></div>
	<form id="fm" class="dlg-form" method="post" novalidate="novalidate">
		<table class="dlgcontainer">
			<col width="30%" /><col width="70%" />
			<!-- <tr>
				<td><label for="itemid">期次编号:</label></td>
				<td><input type="text" id="itemid" name="itemid" required="true" class="easyui-numberbox" validType="length[0,32]"/></td>
			</tr> -->
			<tr>		           
				<td><label for="itemval">报刊期次:</label></td>
				<td>
					<input type="text" id="itemval" name="itemval" required="true" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,50]" style="width:200px;"/>
					<input id="seqno" name="seqno" type='hidden' class="easyui-validatebox"/>
				</td>
			</tr>	        
		  </table>
	</form>
</div>
<div id="dlg-buttons">
	<a id="b_save" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a id="b_cancel" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

<div style="display:none">
	<select id="publishflagList" name="publishflagList" editable="false" class="easyui-combobox">				        	
	<%
		String options4 = "";
		List<Mi007> ary4=(List<Mi007>) request.getAttribute("publishflagList");
		for(int i=0;i<ary4.size();i++){
			Mi007 mi007=ary4.get(i);
			String itemidTmp=mi007.getItemid();
			String itemvalTmp=mi007.getItemval();
			options4 = (new StringBuilder(String.valueOf(options4))).append("<option value=\"").append(itemidTmp).append("\">").append(itemvalTmp).append("</option>\n").toString();
		}
		out.println(options4);
	%>
	</select>
</div>
</body>
</html>