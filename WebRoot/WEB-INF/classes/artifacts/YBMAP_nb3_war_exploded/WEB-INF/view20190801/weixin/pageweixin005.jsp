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
<title>微信用户信息</title>
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
var page=1;
var rows=10;
$(function() {
	//初始化列表	
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'微信用户信息查询结果',
		height:369,
		singleSelect: true,
		url: "<%=request.getContextPath()%>/weixinapi00105.json",
		method:'post',
		queryParams:arrToJson($('#form40101').serializeArray()),
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		columns:[[
			//{field:'ck',checkbox:true},			
			{title:'是否订阅',field:'subscribe',align:'center',width:60,editor:'text'},
			//{title:'用户标识',field:'openid',align:'center',width:60,editor:'text'},
			{title:'昵称',field:'nickname',align:'center',width:60,editor:'text'},
			{title:'性别',field:'sex',align:'center',width:30,editor:'text'},
			{title:'国家',field:'country',align:'center',width:40,editor:'text'},
			{title:'省份',field:'province',width:50,align:'center'},
			{title:'用户关注时间',field:'subscribe_time',align:'center',width:150,editor:'text'},			
			{title:'是否绑定',field:'ifbind',align:'center',width:70,editor:'text'},
			{title:'绑定有效',field:'flag',align:'center',width:70,editor:'text'},
			{title:'公积金账号',field:'surplusaccount',align:'center',width:120,editor:'numberbox'},
			{title:'姓名',field:'fullname',align:'center',width:150,editor:'text'},			
			{title:'身份证号',field:'idcardnumber',align:'center',width:100,editor:'text'},
			{title:'邮箱',field:'email',align:'center',width:100,editor:'text'},
			{title:'电话',field:'tel',align:'center',width:100,editor:'text'}			
		]],
		toolbar: '#toolbar',
		onLoadSuccess:function(data){ 
			if(data.errcode != 0) $.messager.alert('提示', data.msg, 'info' );
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
		 $('#dlg').dialog('open').dialog('setTitle','微信用户设备信息');
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
		 $('#dlgs').dialog('open').dialog('setTitle','微信用户通讯信息');
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
<div class="easyui-panel" title="微信用户信息查询">
	<form id="form40101" class="dlg-form" novalidate="novalidate">
		<table class="container">	
			<col width="50%" /><col width="50%" />
			<tr>					
				<th>
					<label for="centid">中心名称:</label>
				</th>	        	
				<td>
					<%if(user.getCenterid().equals("00000000")){ %>
						<select id="centerId" name="centerId" style="width:220px;" editable="false" class="easyui-combobox" >
							<%
								String options4 = "";
								options4 = (new StringBuilder(String.valueOf(options4))).append("<option value=\"\">").append("请选择...").append("</option>\n").toString();
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
					<%}else{ %>
						<select id="centerId" name="centerId" style="width:220px;" editable="false" class="easyui-combobox" >
							<%
								String options5 = "";
								List<Mi001> ary5=(List<Mi001>) request.getAttribute("mi001list");
								for(int i=0;i<ary5.size();i++){
									Mi001 mi005=ary5.get(i);
									String itemid=mi005.getCenterid();
									String itemval=mi005.getCentername();
									if(user.getCenterid().equals(mi005.getCenterid())){
										options5 = (new StringBuilder(String.valueOf(options5))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
										break;				
									}									
								}
								out.println(options5);
								 %>	
						</select>
					<%} %>
					<!-- 
					<input name="page" value="1" type="hidden"/>
					<input name="rows" value="10" type="hidden"/>
					<input name="nextopenid" type="hidden"/> -->								
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

</body>
</html>
