<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@ include file = "/include/init.jsp" %>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="com.yondervision.mi.dto.Mi001"%>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/echarts/config.js"></script>
<script type="text/javascript">
require.config({
    paths: {
        echarts: '/YBMAPZH/scripts/echarts'
    }
});
var myChart;
var alldata;
var centerid;
var flag = 1;
var type='Total';
var startdate;
var enddate;
var channel;
var centername="";
var subtext
var clickcentername;
require(
	['echarts','echarts/chart/pie','echarts/chart/funnel'],
    function (ec) {
		var ecConfig = require('echarts/config');
        myChart = ec.init(document.getElementById('linechart'));
   	 	var curr_time = new Date();   
   	 	$("#enddate").datebox("setValue",myformatter(curr_time));
   		$("#startdate").datebox("setValue", myformatter(new Date(new Date()-6*24*60*60*1000)));
   		startdata=$("#startdate").datebox('getValue');
   		enddate=$("#enddate").datebox('getValue');
   		myChart.on(ecConfig.EVENT.CLICK, function (param){
   			if(flag==1){
   				centerid = param['data']['centerid'];
   				clickcentername=subtext=param['data']['name'];
   				setOption(alldata['perCenterObj'][centerid]['all'+type]);
   				flag=2;
   				$("#returnpre").linkbutton("enable");
   			}else if(flag==2){
	   			channel=param['data']['channel'];
   				if(channel=="20"){
   	   				flag=3;
   	   				subtext="微信关注";
   					setOption(alldata['perCenterObj'][centerid]['bind'+type]);
   	   				$("#returnpre").linkbutton("enable");
   				}
   			}else if(flag==3){
   				
   			}
		}); 
		$('input:checkbox').click(function() {   
         	if($(this).attr('checked') == true&&$(this).attr('id').indexOf("center")>=0) {  
				centername = $(this).next('label').html(); 
				return;
         	}
         	if($(this).attr('id').indexOf("channel")>=0){
         		if(alldata!=null){
	         		flag==3;
					if($('#channel-0').is(':checked')&&$('#channel-1').is(':checked')) {
						setOption(alldata['all'+type]);
					}else if($('#channel-0').is(':checked')){
						setOption(alldata['allApp'+type]);
					}else if($('#channel-1').is(':checked')){
						setOption(alldata['allWx'+type]);
					}else if(!$('#channel-0').is(':checked')&&!$('#channel-1').is(':checked')){
						alert("请至少选择一项");
						$(this).attr('checked',"checked");
					}
         		}
			}
     	}) ;
	}
);
var channelChecked;
var checkCenter=0;
var centerlist="";
function select10301() {
	if (!$('#form10301').form('validate')) return;
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
	var arr = $('#form10301').serializeArray();
	$("input[name='centerlist']:checkbox").each(function() {
        if ($(this).attr("checked")) {
        	checkCenter += 1;
        }
    }) 
	<%if(!user.getCenterid().equals("00000000")){%>
		checkCenter=1;
		centerlist='<%= user.getCenterid()%>';
		centerid='<%= user.getCenterid()%>';
		centername = '<%= user.getCenterName()%>';
	<%}%>
    if(checkCenter==0){
    	alert("请至少选择一个中心");
    	return;
    }
	flag="1";
	//var dataJson = arrToJson(arr);
	myChart.showLoading({
    	text: '正在努力的读取数据中...',
    	effect:'whirling'//loading话术
	});
	$.ajax({
		type : 'POST',
		url : '<%= _contexPath %>/webapi10301.json',
		dataType: 'json',
		data:arr,
		success : function(data){
			alldata = data;
			$("input[name='centerlist']:checkbox").each(function() {
		        if ($(this).attr("checked")) {
		        	if(centerlist!="")centerlist=centerlist+",";
		        	centerlist = centerlist+$(this).val();
		        }
		    });
		     $("#chartsum")[0].style.background = "#2b2";
		     $('channel-0').attr('checked',"checked");
		     $('channel-1').attr('checked',"checked");
			$("#returnpre").linkbutton("disable");
			if(centerlist.split(",").length>1){
				flag=1;
				subtext='各中心数据';
				setOption(data['all'+type]);
			}else{
				flag=2;
				centerid=centerlist;
				subtext=centername ;
				setOption(data['perCenterObj'][centerid]['all'+type]);
			}
			myChart.hideLoading();
			showGrid();
		},
		error :function(){
			$.messager.alert('错误','系统异常','error');							
		}
	}); 
}
function returnPre(){
	if(flag==3){
		subtext=clickcentername ;
		setOption(alldata['perCenterObj'][centerid]['all'+type]);
		flag=flag-1;
		if(centerlist.split(",").length==1){
			$("#returnpre").linkbutton("disable");
		}
	}else if(flag==2){
		flag=flag-1;
		subtext='各中心数据';
		setOption(alldata['all'+type]);
		$("#returnpre").linkbutton("disable");
	}else if(flag==1){
		alert("已经是最上层");
	}
}
function setOption(data){
	myChart.clear();
    var option = {
    	    title : {
    	        text: '用户数据',
    	        subtext: subtext,
    	        x:'center'
    	    },
    	    tooltip : {
    	        trigger: 'item',
    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
    	    },
    	    legend: {
    	        orient : 'vertical',
    	        x : 'left',
    	        data:data.legend
    	    },
    	    toolbox: {
    	        show : true,
    	        feature : {
    	            dataView : {show: true, readOnly: false},
    	            restore : {show: true},
    	            saveAsImage : {show: true}
    	        }
    	    },
    	    calculable : true,
    	    series : [
    	        {
    	            name:'占比数据',
    	            type:'pie',
    	            radius : '55%',
    	            center: ['50%', '60%'],
    	            itemStyle:{
    	            	normal : {
    	            		label : {
    	            			formatter : function(a,b,c,d){
    	            				return b+"\n占比："+d+'%';    	            				
    	            			}
    	            		}
    	        		}
    	            },
    	            data:data.data
    	        }
    	    ]
    	};
	myChart.setOption(option);
		                    
}
function chartsum(){
	if (!$('#form10301').form('validate')) return;
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
	type="Total";
	if(startdata!=$("#startdate").datebox('getValue')||enddate!=$("#enddate").datebox('getValue')){
		select10301();
	}else{
		$('channel-0').attr('checked',"checked");
	     $('channel-1').attr('checked',"checked");
		if(flag==1){
			setOption(alldata['all'+type]);
		}else if(flag==2){
			subtext=centername;
			setOption(alldata['perCenterObj'][centerid]['all'+type]);
		}else if(flag==3){
			if(channel=="20"){
				subtext="微信关注";
				setOption(alldata['perCenterObj'][centerid]['bind'+type]);
			}
		}
	}
}
function chartnew(){
	if (!$('#form10301').form('validate')) return;
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
	type="New";
	if(startdata!=$("#startdate").datebox('getValue')||enddate!=$("#enddate").datebox('getValue')){
		select10301();
	}else{
		$('channel-0').attr('checked',"checked");
	     $('channel-1').attr('checked',"checked");
		if(flag==1){
			setOption(alldata['all'+type]);
		}else if(flag==2){
			subtext=centername;
			setOption(alldata['perCenterObj'][centerid]['all'+type]);
		}else if(flag==3){
			if(channel=="20"){
				subtext="微信关注";
				setOption(alldata['perCenterObj'][centerid]['bind'+type]);
			}
		}
	}
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
function showGrid(){
	var gridData= new Array();
	for(var i=0;i<alldata['allTotal'].data.length;i++){
		var result={};
		var obj = alldata['allTotal'].data[i];
		result.centerid=obj.name;
		result.allcount=alldata['allNew'].data[i].value;
		result.allsum=obj.value;
		result.appcountnum = alldata['perCenterObj'][obj.centerid]['allNew'].data[0].value;
		result.appsumcount = alldata['perCenterObj'][obj.centerid]['allTotal'].data[0].value;
		result.wxcountnum = alldata['perCenterObj'][obj.centerid]['bindNew'].data[0].value;
		result.wxsumcount = alldata['perCenterObj'][obj.centerid]['bindTotal'].data[0].value;
		result.wxattcountnum = alldata['perCenterObj'][obj.centerid]['allNew'].data[1].value;
		result.wxattsumcount = alldata['perCenterObj'][obj.centerid]['allTotal'].data[1].value;
		gridData[i]=result;
	}
	$('#dg').datagrid({   
			iconCls: 'icon-edit',
			height:388,
			title:'用户统计信息',
			//pagination:true,
			rownumbers:true,
			//fitColumns:true,
			//method:'post',
			//url: "<%=request.getContextPath()%>/webapi10304.json",
			toolbar:'#toolbar',
			//queryParams:map,
			columns:[[
				{title:'中心',field:'centerid',align:'center',width:150,editor:'text'},
				{title:'新增人数',field:'allcount',align:'center',width:90,align:'center'},
				{title:'App新增注册人数',field:'appcountnum',align:'center',width:100,align:'center'},
				{title:'微信新增关注人数',field:'wxattcountnum',align:'center',width:100,align:'center'},
				{title:'微信新增绑定人数',field:'wxcountnum',align:'center',width:100,align:'center'},
				{title:'累计人数',field:'allsum',align:'center',width:90,editor:'text'},
				{title:'App累计注册人数',field:'appsumcount',align:'center',width:100,editor:'text'},
				{title:'微信累计关注人数',field:'wxattsumcount',align:'center',width:100,editor:'text'},
				{title:'微信累计绑定人数',field:'wxsumcount',align:'center',width:100,editor:'text'}
			]],
			data:gridData	
		});	
}
</script>
</head> 
<body>
<script type="text/javascript" src="<%=_contexPath%>/scripts/jquery.form.js"></script><!-- jquery.form.js 为页面提交json请求应用 -->
<div class="easyui-panel" title="用户信息查询">
	<form id="form10301" class="dlg-form" novalidate="novalidate">
		<table class="container">		
			<col width="20%" /><col width="80%" />
			  <tr>
				<th><label for="startdate">日期从:</label></th>
				<td><input type="text" id="startdate" name="startdate" class="easyui-datebox" validType="date" required="true"> <b>至:</b><input type="text" id="enddate" name="enddate" class="easyui-datebox" validType="date" required="true"></td>
			</tr>
				<%
				if(user.getCenterid().equals("00000000")){
				%>
				<tr>
					<td><label >中心：</label></td>
					<td>  	
						<ul class="multivalue horizontal" id="centerlist">
						<%
								String options4 = "";
								List<Mi001> ary4=(List<Mi001>) request.getAttribute("mi001list");
								for(int i=0;i<ary4.size();i++){
									Mi001 mi003=ary4.get(i);
									String itemid=mi003.getCenterid();
									String itemval=mi003.getCentername();
									options4 = (new StringBuilder(String.valueOf(options4))).append("<li><input id=\"center-"+i+"\" name=\"centerlist\" type=\"checkbox\" value=\"").append(itemid).append("\"><label for=\"center-"+i+"\">").append(itemval).append("</label><li>\n").toString();
								}
								System.out.println(options4);
								out.println(options4);
							%>
						</ul>
					</td>          
				</tr>
				 <tr>
					<td><label >渠道：</label></td>
					<td>  	
						<ul class="multivalue horizontal">
							<li><input id="channel-0" name="channel" type="checkbox" value="10"/ checked><label for="channel-0">手机注册</label></li>
							<li><input id="channel-1" name="channel" type="checkbox" value="20"/ checked><label for="channel-1">微信关注</label></li>
						</ul>
					</td>          
				</tr>
				<%} %>
				<!--<tr>
					<td><label >微信是否绑定：</label></td>
					<td>  	
						<ul class="multivalue horizontal">
							<li><input id="bind-0" name="stat" type="checkbox" value="10"/><label for="bind-0">未绑定</label></li>
							<li><input id="bind-1" name="stat" type="checkbox" value="20"/><label for="bind-1">绑定</label></li>
						</ul>
					</td>          
				</tr> -->
		</table> 
		<div class="buttons">
			<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="select10301()">查询</a>
		</div>
	</form>
</div>
<div id="charttype">
<b>展示用户数据类型：</b>
<a id='chartsum' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="chartsum()">累计人数</a>
<a id='chartnew' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="chartnew()">新增人数</a>
<a id='returnpre' href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="returnPre()">返回上层</a>
</div>
<div id="linechart" class="easyui-panel" title="用户统计占比图" style="height:300px;"></div>
<table id="dg" ></table>
</body>
</html>
