<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.dto.Mi007"%>
<%@page import="com.yondervision.mi.dto.Mi001"%>
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
<title>APP用户信息</title>
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
		title:'APP用户信息查询结果',
		height:369,
		singleSelect: true,
		url: "<%=request.getContextPath()%>/webapi40101.json",
		method:'post',
		queryParams:arrToJson($('#form40101').serializeArray()),
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		columns:[[
			{field:'ck',checkbox:true},
			{title:'中心名称',field:'centerid',align:'center',width:150,editor:'text',formatter:function (value,row,index){	
				return $('#centerid option[value='+value+']').text(); 	
			}},
			{title:'用户名',field:'userId',align:'center',width:60,editor:'text'},
			{title:'公积金账号',field:'accnum',width:120,align:'center'},
			{title:'姓名',field:'accname',align:'center',width:100,editor:'text'},
			{title:'证件号码',field:'certinum',align:'center',width:120,editor:'numberbox'},
			{title:'电子邮箱',field:'email',align:'center',width:150,editor:'text'},
			{title:'联名卡号',field:'cardno',align:'center',width:100,editor:'text'},
			{title:'创建时间',field:'datecreated',align:'center',width:100,editor:'text'}			
		]],
		toolbar: '#toolbar',
		onLoadSuccess:function(data){ 
			if(data.recode != SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
        }		
	});
	
	//查询
	$('#b_query').click(function (){
		//校验
		//if (!ydl.formValidate('form40101')) return;
		if (!$("#form40101").form("validate")) return;
		//组织查询条件
		var arr = $("#form40101").serializeArray();
		var dataJson = arrToJson(arr);
		//查询列表数据
		$('#dg').datagrid('options').queryParams = dataJson;//修改查询条件
		$('#dg').datagrid('reload');
	});	
	
	//重置
	$('#b_reset').click(function (){
		//document.getElementById("form40101").reset(); 
		$('#form40101').form('clear');
		//除去错误校验状态
		ydl.delErrorMessage('form40101');
	});
	
})

//设备信息
function select40102(){
	 var row = $('#dg').datagrid('getSelected');
     if (row){
		 $('#dlg').dialog('open').dialog('setTitle','APP用户设备信息');
		 ydl.setDlgPosition('dlg');//对话框高度超出页面高度时，设置top为0px
	    // var dataJson = convertToJson(row);		
		//datagrid列表
		$('#dlg40102').datagrid({   
			iconCls: 'icon-search',
			singleSelect: true,
			url: "<%=request.getContextPath()%>/webapi40102.json",
			method:'post',
			queryParams:row,
			onLoadSuccess:function(data){ 
	           if(data.recode!=SUCCESS_CODE)
	              $.messager.alert('提示', data.msg, 'info' );
	        	}					
		});	    
     }
}  
//通讯信息
function select40103(){
	 var row = $('#dg').datagrid('getSelected');
     if (row){
		 $('#dlgs').dialog('open').dialog('setTitle','APP用户通讯信息');
		 ydl.setDlgPosition('dlgs');//对话框高度超出页面高度时，设置top为0px
	     //var dataJson = convertToJson(row);		
		//datagrid列表
		$('#dlg40103').datagrid({   
			iconCls: 'icon-search',
			singleSelect: true,
			url: "<%=request.getContextPath()%>/webapi40103.json",
			method:'post',
			queryParams:row,
			onLoadSuccess:function(data){ 
	          if(data.recode!=SUCCESS_CODE)
	             $.messager.alert('提示', data.msg, 'info' );
	       		}		
		});	    
     }
}       

function rowformater(value,row,index){	
 	return $('#devType option[value='+value+']').text(); 	
}

function rowformater1(value,row,index){	
 	return $('#contactType option[value='+value+']').text(); 	
}

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
<div class="easyui-panel" title="APP用户信息查询">
	<form id="form40101" class="dlg-form" novalidate="novalidate">
		<table class="container">	
			<col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" />
			<tr>	        
				<th><label for="accnum">个人公积金账号:</label></th>
				<td><input type="text" id="accnum" name="accnum" style="width:220px;" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,30]"/></td>
				<th><label for="accname">姓名:</label></th>
				<td><input type="text" id="accname" name="accname" style="width:220px;" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,40]"/></td>
			</tr>
			<tr>		           
				<th><label for="startdate">开始日期:</label></th>
				<td><input type="text" id="startdate" name="startdate" style="width:220px;" class="easyui-datebox" editable="false" validType="date&&maxdate['enddate','【结束日期】']" maxlength="10"/></td>
				<th><label for="enddate">结束日期:</label></th>
				<td><input type="text" id="enddate" name="enddate" style="width:220px;" class="easyui-datebox" editable="false" validType="date&&mindate['startdate','【开始日期】']" maxlength="10"/></td>
			</tr>
			<tr>		           
				<th><label for="certinum">证件号码:</label></th>
				<td><input type="text" id="certinum" name="certinum" style="width:220px;" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,30]"/></td>
				<th><label for="email">电子邮箱:</label></th>
				<td><input type="text" id="email" name="email" style="width:220px;" class="easyui-validatebox" maxlength="50" validType="validchar[['!','%','^','&','*','$','~','#']]&&email"/></td>
			</tr>			       
			<tr>
				<th><label for="cardno">联名卡号:</label></th>
				<td><input type="text" id="cardno" name="cardno" style="width:220px;" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,30]"/></td>	
				<th>				
					<%						
					if(user.getCenterid().equals("00000000")){ %>
						<label for="centid">中心名称:</label>
					<%}%>					
				</th>	        	
				<td>
					<%if(user.getCenterid().equals("00000000")){ %>
						<select id="centid" name="centid" editable="false" style="width:220px;" class="easyui-combobox" >				        	
							<option value="">请选择...</option>
							<%
								String options4 = "";
								List<Mi001> ary4=(List<Mi001>) request.getAttribute("mi001list");
								for(int i=0;i<ary4.size();i++){
									Mi001 mi003=ary4.get(i);
									String itemid=mi003.getCenterid();
									String itemval=mi003.getCentername();
									options4 = (new StringBuilder(String.valueOf(options4))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
								}
								out.println(options4);
							%>
						</select>
					<%} %>					
				</td>
			</tr>
		</table>  
		<div class="buttons">
			<a id="b_query" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
			<a id="b_reset" class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" >重置</a>
		</div>
	</form>
</div>

<table id="dg" ></table>
<div id="toolbar">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="select40102()">设备信息</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="select40103()">通讯信息</a>	        	        
</div>

<div id="dlg" class="easyui-dialog" style="width:880px;height:520px;padding:10px 20px" closed="true" buttons="#dlg-buttons">        
	<table id="dlg40102" class="easyui-datagrid" title="APP用户设备详细信息" pagination="true" rownumbers="true" fitColumns="true" singleSelect="true">		
	<thead>
		<tr>
			<!--  <th data-options="field:'ck',checkbox:true"></th>  -->
			<th data-options="field:'userId',align:'center',width:80">用户名</th>
			<th data-options="field:'devtype',align:'center',width:120,editor:'text',formatter:rowformater">设备区分</th>
			<th data-options="field:'devtoken',align:'center',width:120,editor:'text'">设备TOKEN</th>
			<th data-options="field:'logintimes',width:100,align:'center',editor:'text'">登录次数</th>
			<th data-options="field:'lastlogindate',align:'center',width:100,editor:'text'">上次登录日期</th>	
			<!-- <th data-options="field:'datemodified',align:'center',width:100,editor:'text'">联名卡号</th> -->											
			<th data-options="field:'devId',hidden:true,width:1,align:'center',editor:'text'">通讯ID</th>				
		</tr>
	</thead>
</table>
</div>
<div id="dlg-buttons">
	<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>   -->
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
</div>

<div id="dlgs" class="easyui-dialog" style="width:880px;height:520px;padding:10px 20px" closed="true" buttons="#dlgs-buttons">        
	<table id="dlg40103" class="easyui-datagrid" title="APP用户通讯详细信息" pagination="true" rownumbers="true" fitColumns="true" singleSelect="true">		
		<thead>
			<tr>
				<!--  <th data-options="field:'ck',checkbox:true"></th>  -->
				<th data-options="field:'userId',align:'center',width:100">用户名</th>
				<th data-options="field:'usercomtype',align:'center',width:160,editor:'text',formatter:rowformater1">通讯类型</th>				
				<th data-options="field:'usercomaccnum',width:100,align:'center',editor:'text'">用户通讯帐号</th>
				<th data-options="field:'usercomaccname',align:'center',width:180,editor:'text'">用户通讯帐户名称</th>
				<th data-options="field:'comunacationid',hidden:true,width:1,align:'center',editor:'text'">通讯ID</th>				
			</tr>
		</thead>
	</table>
</div>
<div id="dlgs-buttons">
	<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>   -->
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgs').dialog('close')">关闭</a>
</div>

<div style="display:none">
	<select id="devType" name="devType" editable="false" class="easyui-combobox">				        	
	<%
		String options1 = "";
		List<Mi007> ary1=(List<Mi007>) request.getAttribute("devicetypeList");
		for(int i=0;i<ary1.size();i++){
			Mi007 mi007=ary1.get(i);
			String itemid=mi007.getItemid();
			String itemval=mi007.getItemval();
			options1 = (new StringBuilder(String.valueOf(options1))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
		}
		out.println(options1);
	%>
	</select>	
	 <select id="contactType" editable="false" name="contactType" class="easyui-combobox">				        	
	<%
		String options2 = "";
		List<Mi007> ary2=(List<Mi007>) request.getAttribute("contacttypeList");
		for(int i=0;i<ary2.size();i++){
			Mi007 mi007=ary2.get(i);
			String itemid=mi007.getItemid();
			String itemval=mi007.getItemval();				
			options2 = (new StringBuilder(String.valueOf(options2))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
		}
		out.println(options2);
	%>
	</select>
	
	<select id="centerid" name="centerid" editable="false" class="easyui-combobox" >				        	
		<%
			String options3 = "";
			List<Mi001> ary3=(List<Mi001>) request.getAttribute("mi001list");
			for(int i=0;i<ary3.size();i++){
				Mi001 mi003=ary3.get(i);
				String itemid=mi003.getCenterid();
				String itemval=mi003.getCentername();
				options3 = (new StringBuilder(String.valueOf(options3))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
			}
			out.println(options3);
		%>
	</select>
	
</div>
</body>
</html>
