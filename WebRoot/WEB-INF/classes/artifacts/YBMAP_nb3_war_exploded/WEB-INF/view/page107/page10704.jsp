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
<title>用户统计</title>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/highcharts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/HighChart.js"></script>
<script type="text/javascript">
var chartType="1";
$(function() {
	var curr_time = new Date();   
	$("#enddate").datebox("setValue",myformatter(curr_time));
	$("#startdate").datebox("setValue", myformatter(new Date(new Date()-6*24*60*60*1000)));
	
	var arr = $('#form10704').serializeArray();
	var map = arrToJson(arr);
	$.ajax({
		type : 'POST',
		url : '<%= _contexPath %>/webapi10704.json',
		dataType: 'json',
		data:map,
		success : function(data){
			var opt = HighChart.ChartOptionTemplates.Bars(data.data, '','','功能次数(次)', "");
            var container = $("#container");
            HighChart.RenderChart(opt, container);
            $("#chartall")[0].style.background = "#2b2";
			$("#chartapp")[0].style.background = "#fff";
			$("#chartwx")[0].style.background = "#fff";
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');							
		}
	}); 
	
})
function select10704() {
	if (!$('#form10704').form('validate')) return;
	if(new Date($("#startdate").datebox('getValue'))>new Date()){
		alert('开始日期大于当前日期');
		return;
	}else if(new Date($("#enddate").datebox('getValue'))>new Date()){
		alert('结束日期大于当前日期');
		return;
	}	
	if(new Date($("#startdate").datebox('getValue'))>new Date($("#enddate").datebox('getValue'))){
		alert('开始日期大于结束日期，请重新选择日期');
		return;
	}
	var arr = $('#form10704').serializeArray();
	var dataJson = arrToJson(arr);
	$.ajax({
		type : 'POST',
		url : '<%= _contexPath %>/webapi10704.json',
		dataType: 'json',
		data:dataJson,
		success : function(data){
			var opt = HighChart.ChartOptionTemplates.Bars(data.data, '','','功能次数(次)', "");
            var container = $("#container");
            HighChart.RenderChart(opt, container);
            $("#chartall")[0].style.background = "#2b2";
			$("#chartapp")[0].style.background = "#fff";
			$("#chartwx")[0].style.background = "#fff";
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');							
		}
	}); 
	
}
function arrToJson(arr){
	var jsonObj = {};
	$.each(arr,function (index,ele){
		jsonObj[ele.name] = ele.value;
	});
	return jsonObj;
}
function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function chartall(){
	if (!$('#form10704').form('validate')) return;
    $("#chartall")[0].style.background = "#2b2";
	$("#chartapp")[0].style.background = "#fff";
	$("#chartwx")[0].style.background = "#fff";
	var arr = $('#form10704').serializeArray();
	var map = arrToJson(arr);
	$.ajax({
		type : 'POST',
		url : '<%= _contexPath %>/webapi10704.json',
		dataType: 'json',
		data:map,
		success : function(data){
			var opt = HighChart.ChartOptionTemplates.Bars(data.data, '','','功能次数(次)', "");
            var container = $("#container");
			HighChart.RenderChart(opt, container);
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');							
		}
	}); 
}
function chartsun(type){
	if (!$('#form10704').form('validate')) return;
	if(type=='10'){
	    $("#chartapp")[0].style.background = "#2b2";
		$("#chartwx")[0].style.background = "#fff";
	}else{
	    $("#chartwx")[0].style.background = "#2b2";
		$("#chartapp")[0].style.background = "#fff";
	}
	$("#chartall")[0].style.background = "#fff";
	var arr = $('#form10704').serializeArray();
	var map = arrToJson(arr);
	map['channeltype']=type;
	$.ajax({
		type : 'POST',
		url : '<%= _contexPath %>/webapi10704Sun.json',
		dataType: 'json',
		data:map,
		success : function(data){
			var opt = HighChart.ChartOptionTemplates.Bars(data.data, '','','功能次数(次)', "");
            var container = $("#container");
			HighChart.RenderChart(opt, container);
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');							
		}
	}); 
}
</script>
</head>
  
<body>
<script type="text/javascript" src="<%=_contexPath%>/scripts/jquery.form.js"></script><!-- jquery.form.js 为页面提交json请求应用 -->
<div class="easyui-panel" title="功能使用信息查询">
	<form id="form10704" class="dlg-form" novalidate="novalidate">
		<table class="container">		
			<col width="20%" /><col width="80%" />
			<tr>
				<th><label for="startdate">日期从:</label></th>
				<td><input type="text" id="startdate" name="startdate" class="easyui-datebox" validType="date" required="true"> <b>至:</b><input type="text" id="enddate" name="enddate" class="easyui-datebox" validType="date" required="true"></td>
			</tr>
		</table> 
		<div class="buttons">
			<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="select10704()">查询</a>
		</div>
	</form>
</div>
<div id="charttype">
<b>展示用户数据类型：</b><a id='chartall' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="chartall()">功能统计汇总</a>
<a id='chartapp' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="chartsun('10')">手机功能统计</a>
<a id='chartwx' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="chartsun('20')">微信功能统计</a>
</div>
<div id="container" class="easyui-panel" title="功能统计柱状图" style="height:400px;"></div>
</body>
</html>
