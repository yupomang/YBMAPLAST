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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/echarts/echarts.js"></script>
<script type="text/javascript">
var chartType="1";
require.config({
    paths: {
        echarts: '/YBMAPZH/scripts/echarts'
    }
});
var myChart;
require(
	['echarts','echarts/chart/line','echarts/chart/bar','echarts/chart/pie'],
    function (ec) {
        myChart = ec.init(document.getElementById('linechart'));
   	 	var curr_time = new Date();   
   	 	$("#enddate").datebox("setValue",myformatter(curr_time));
   		$("#startdate").datebox("setValue", myformatter(new Date(new Date()-6*24*60*60*1000)));
   		var arr = $('#form10304').serializeArray();
   		var map = arrToJson(arr);
   		myChart.showLoading({
   	    	text: '正在努力的读取数据中...',    //loading话术
   	    	effect:'whirling'//loading话术
   		});
        $.ajax({
	       		type : 'POST',
	       		url : '<%= _contexPath %>/webapi10304New.json',
	       		dataType: 'json',
	       		data:map,
	       		success : function(data){
	       			setOption(data.data[0]);
	       			$("#chartnew")[0].style.background = "#2b2";
	       			$("#chartsum")[0].style.background = "#fff";
	       			$('#dg').datagrid({   
	       				iconCls: 'icon-edit',
	       				height:388,
	       				title:'用户统计信息',
	       				//pagination:true,
	       				rownumbers:true,
	       				//fitColumns:true,
	       				method:'post',
	       				url: "<%=request.getContextPath()%>/webapi10304.json",
	       				queryParams:map,
	       				columns:[[
	       					{title:'时间',field:'wxcreatedate',align:'center',width:110,editor:'text'},
	       					{title:'App新增注册人数',field:'appcountnum',align:'center',width:110,align:'center'},
	       					{title:'App累计注册人数',field:'appsumcount',align:'center',width:110,editor:'text'},
	       					{title:'微信新增绑定人数',field:'wxcountnum',align:'center',width:110,align:'center'},
	       					{title:'微信累计绑定人数',field:'wxsumcount',align:'center',width:110,editor:'text'},
	       					{title:'微信新增关注人数',field:'wxattcountnum',align:'center',width:110,align:'center'},
	       					{title:'微信累计关注人数',field:'wxattsumcount',align:'center',width:110,editor:'text'}
	       				]],
	       				onLoadSuccess:function(data){   
	       					myChart.hideLoading();        
	       		           if(data.recode!=SUCCESS_CODE){ 
	       		        	   $.messager.alert('提示', data.msg, 'info' );
	       		        	}
	       		        }	
	       			});	
	       		},
	       		error :function(){
	       			$.messager.alert('错误','系统异常','error');							
	       		}
	       	}); 
	
})
function setOption(data){
    var option = {
		tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:data.legend,
	        orient:'horizontal',
	        y:'bottom'
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            magicType : {show: true, type:['line', 'bar', 'tiled', 'stack']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : data.xaxis
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            axisLabel : {
    				formatter: '{value}'
				 	}
	        }
	    ],
	    series : data.data
	};
myChart.clear();
myChart.setOption(option); 
}
function select10304() {
	if (!$('#form10304').form('validate')) return;
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
	var arr = $('#form10304').serializeArray();
	var dataJson = arrToJson(arr);
	myChart.showLoading({
    	text: '正在努力的读取数据中...', 
    	effect:'whirling'//loading话术
	});
	$.ajax({
		type : 'POST',
		url : '<%= _contexPath %>/webapi10304New.json',
		dataType: 'json',
		data:dataJson,
		success : function(data){
			setOption(data.data[0]);
			$("#chartnew")[0].style.background = "#2b2";
			$("#chartsum")[0].style.background = "#fff";
			//datagrid列表
			$('#dg').datagrid({   
				url: "<%=request.getContextPath()%>/webapi10304.json",
				queryParams:dataJson
			});	
			myChart.hideLoading();
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
function chartsum(){
	if (!$('#form10304').form('validate')) return;
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
	$("#chartsum")[0].style.background = "#2b2";
	$("#chartnew")[0].style.background = "#fff";
	var arr = $('#form10304').serializeArray();
	var map = arrToJson(arr);
	myChart.showLoading({
    	text: '正在努力的读取数据中...',   
    	effect:'whirling'//loading话术
	});
	$.ajax({
		type : 'POST',
		url : '<%= _contexPath %>/webapi10304Sum.json',
		dataType: 'json',
		data:map,
		success : function(data){
			setOption(data.data[0]);
			myChart.hideLoading();
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');							
		}
	}); 
}
function chartnew(){
	if (!$('#form10304').form('validate')) return;
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
	$("#chartnew")[0].style.background = "#2b2";
	$("#chartsum")[0].style.background = "#fff";
	var arr = $('#form10304').serializeArray();
	var map = arrToJson(arr);
	myChart.showLoading({
    	text: '正在努力的读取数据中...',
    	effect:'whirling'//loading话术
	});
	$.ajax({
		type : 'POST',
		url : '<%= _contexPath %>/webapi10304New.json',
		dataType: 'json',
		data:map,
		success : function(data){
			setOption(data.data[0]);
			myChart.hideLoading();
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
<div class="easyui-panel" title="用户信息查询">
	<form id="form10304" class="dlg-form" novalidate="novalidate">
		<table class="container">		
			<col width="20%" /><col width="80%" />
			<tr>
				<th><label for="startdate">日期从:</label></th>
				<td><input type="text" id="startdate" name="startdate" class="easyui-datebox" validType="date" required="true"> <b>至:</b><input type="text" id="enddate" name="enddate" class="easyui-datebox" validType="date" required="true"></td>
			</tr>
		</table> 
		<div class="buttons">
			<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="select10304()">查询</a>
		</div>
	</form>
</div>
<div id="charttype">
<b>展示用户数据类型：</b><a id='chartnew' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="chartnew()">新增人数</a>
<a id='chartsum' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="chartsum()">累计人数</a>
</div>
<div id="linechart" class="easyui-panel" title="用户统计趋势图" style="height:300px;"></div>
<table id="dg" ></table>
</body>
</html>
