<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file = "/include/init.jsp" %>
<%@page import="com.yondervision.mi.dto.Mi001"%>
<%@page import="java.util.List"%>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<%
  UserContext user=UserContext.getInstance();
	
  if(user==null)  {
     out.println("超时");
     return;
  }
%>
<title>预约业务类型</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
.datagrid {padding-top:5px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript">//<![CDATA[
var url;
$(function () {
	// 隐藏城市的下拉列表，只用于datagrid中名字的显示
	$("#centeridQry").hide();
	
	//初始化列表
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'预约业务类型',
		height:369,
		url: "<%=request.getContextPath()%>/webapi62004.json",
		method:'post',
		queryParams:{'centerid':"<%=user.getCenterid()%>"},
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		singleSelect:true,
		columns:[[
			{field:'id',title:'删除',align:'center',formatter:function (value,row,index){
				return '<input type="checkbox" value="' + row.appobusiid + '"/>';
			}},
			{title:'预约业务类型编号',field:'appobusiid',width:20,align:'center',editor:'text'},
			{title:'预约业务类型名称',field:'appobusiname',align:'center',width:50,editor:'text'},
			{title:'中心ID/名称',field:'centerid',align:'center',width:60,editor:'text',formatter:centerformater}
		]],
		toolbar: '#toolbar',
		onLoadSuccess:function(data){ 
			if(data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
        }		
	});
});

function addInfo(){
    $('#dlg').dialog('open').dialog('setTitle','预约业务类型-增加');
    $('#fm').form('clear');
	$("#trid").hide();   
    url = '<%= _contexPath %>/webapi62001.json';
}

function editInfo(){
    var rows = $('#dg').datagrid('getSelections');
    if (rows.length != 1){
    	$.messager.alert('提示', "请选择一条记录进行修改!", 'info' );
    }else{
		$('#dlg').dialog('open').dialog('setTitle','预约业务类型-修改');
        $('#fm').form('load',rows[0]);	
		$("#trid").show();
		
        url = '<%= _contexPath %>/webapi62003.json';
    }
}

function saveInfo(){
	//校验
	if (!$("#fm").form("validate")) return;
	var arr = $('#fm').serializeArray();
	$.ajax({
		type : 'POST',
		url : url,
		dataType: 'json',
		data:arr,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
			$('#dlg').dialog('close');        // close the dialog
            $('#dg').datagrid('reload');    // reload the user data
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');
		}
	});
}

function delInfo(){
    // var rows = $('#dg').datagrid('getSelections');
    // if (rows.length > 0){
	$("#trid").show();
    var $checkboxs = $('input:checkbox:checked');
	if ($checkboxs.length > 0) {
         $.messager.confirm('提示','确认是否删除?',function(r){
             if (r) {
				var para = $checkboxs.map(function(index){
					return this.value;
				}).get().join(',');
            	var map={appobusiid:para}
            	          
                $.ajax( {
					type : 'POST',
					url : '<%= _contexPath %>/webapi62002.json',
					dataType: 'json',
					data:map,
					success : function(data) {
						$.messager.alert('提示', data.msg, 'info');							
	                	$('#dg').datagrid('reload');    // reload the user data
					},
					error :function(){
						$.messager.alert('错误','系统异常','error');							
					}
				});
            }
    	});
    }
    else $.messager.alert('提示', "请至少选择一条记录进行删除！", 'info');
}
function centerformater(value,row,index){
 	return $('#centeridQry option[value='+value+']').text();
}
function flagformater(value,row,index){
 	if ("0" == row.validflag) return "无效数据";
 	else return "有效数据";
}

//数据转对象(无重复name值的数组)
function arrToJson(arr){
	var jsonObj = {};
	$.each(arr,function (index,ele){
		jsonObj[ele.name] = ele.value;
	});
	return jsonObj;
}
//]]></script>
</head>
<body>	
	<table id="dg" >
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addInfo()">增加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editInfo()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delInfo()">删除</a>
		<select id="centeridQry" name="centeridQry" panelHeight="auto">	        	
							<%
								String optionsCenterid = "";
								List<Mi001> ary3=(List<Mi001>) request.getAttribute("mi001list");
								for(int i=0;i<ary3.size();i++){
									Mi001 mi003=ary3.get(i);
									String itemid=mi003.getCenterid();
									String itemval=mi003.getCentername();
									optionsCenterid = (new StringBuilder(String.valueOf(optionsCenterid))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
								}
								out.println(optionsCenterid);
							%>
						</select>
    </div>
    
   <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" modal="true"  buttons="#dlg-buttons">
        <div class="formtitle">预约业务类型</div>
        <form id="fm" class="dlg-form" method="post" novalidate="novalidate">
			<table class="dlgcontainer">
			<tr id='trid'>
				<td><label for="appobusiid" >预约业务编号：</label></td>
				<td><input type="text" id="appobusiid" name="appobusiid" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,20]" class="easyui-validatebox" readonly="true"></td>
			</tr>
			<tr>
				<td><label for="appobusiname" >预约业务名称：</label></td>
                <td><input type="text" id="appobusiname" name="appobusiname" required="true" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,50]" class="easyui-validatebox"></td>
			</tr>
			</table>
			
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveInfo()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
    </div>
</body>
</html>
