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
<title>节假日管理</title>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.fullcalendar.js"></script>
<script type="text/javascript">
var yearStr;
var monthStr;
$(function() {
	$('#form62703').attr("hidden",true);
	var Today = new Date();
	$('#year').val(Today.getFullYear());
	yearStr=$('#year').val();
	monthStr=((Today .getMonth()+1)<10?"0":"")+(Today .getMonth()+1);
})

//保存
function saveInfo(){            
	
	//校验
	if (!$("#form62703").form("validate")) return;	
	var arr = $('#form62703').serializeArray();	
	var dataJson = arrToJson(arr);
	$.ajax({
		type : "POST",
		url : "<%=request.getContextPath()%>/webapi62704.json",
		dataType: "json",
		data:arr,
		success : function(data) {					
			$.messager.alert('提示', data.msg, 'info' );
			document.getElementById("form62703").reset();
			showFullCalander(yearStr,monthStr);
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});						
}

//确定（增加一年的基本信息）
function insert62701() {
	yearStr=$('#year').val();
	//校验
	if (!checkyear(yearStr+"-01-01")){
		$.messager.alert("提示","输入正确的年度","info");
		return;
	}
	//组织提交数据
	var arr = $('#form62701').serializeArray();
	var dataJson = arrToJson(arr);
	$.ajax( {
		type : "POST",
		url : "<%=request.getContextPath()%>/webapi62701.json",
		dataType: "json",
		data:dataJson,
		beforeSend: function(){  
        	$.messager.progress({
		        title: '提示',
		        msg: '正在加载，请等待...',
		        text: 'LOADING.......'
		    });
    	},  
    	complete: function(){  
        	$.messager.progress('close');
    	}, 
		success : function(data) {
			var Today = new Date();
			if(yearStr==Today.getFullYear()){
				monthStr=((Today .getMonth()+1)<10?"0":"")+(Today .getMonth()+1)
			}else{
				monthStr="01";
			}
			showFullCalander(yearStr,monthStr);
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});
}

function showFullCalander(yearStr,monthStr){
	$('#fullCalendar').attr("style","width:700px;height:400px");
	$('#fullCalendar').fullCalendar({
		width : 600,
		height : 380,
		fit : false,
		border : true,
		firstDay : 0,
		url : "<%=request.getContextPath()%>/webapi62703.json",
		para:{"yearmonth":yearStr+"-"+(monthStr.length>1?"":"0")+monthStr},
		year:yearStr,
		month:monthStr,
		current:new Date(),
		onSelect : function (date, target) {
			var abbr=target.abbr.split(',');
			if(abbr[0]==yearStr){
				$('#festivaldate').val(abbr[0]+"-"+(abbr[1].length>1?"":"0")+abbr[1]+"-"+(abbr[2].length>1?"":"0")+abbr[2]);
				$('#festivalflag-'+abbr[3]).attr("checked",true);
				$('#freeuse2').val(abbr[4]=="undefined"?"":abbr[4]);
				yearStr=abbr[0];
				monthStr=(abbr[1].length>1?"":"0")+abbr[1];
			}else{
				document.getElementById("form62703").reset();
			}
			$('#year').val(yearStr);
		},
		onChange : function (year, month) {
			yearStr=year;
			monthStr=(month.length>1?"":"0")+month;
			document.getElementById("form62703").reset();
			$('#year').val(yearStr);
		}
	});
	$('#form62703').removeAttr("hidden");
}

//数据转对象(无重复name值的数组)
function arrToJson(arr){
	var jsonObj = {};
	$.each(arr,function (index,ele){
		jsonObj[ele.name] = ele.value;
	});
	return jsonObj;
}

function auto(flag){
	var yearStr=$('#year').val();
	if (!checkyear(yearStr+"-01-01")){
		$.messager.alert("提示","输入正确的年度","info");
		return;
	}	
	if(yearStr!=""){
		if(flag=="add"){
			$('#year').val(parseFloat(yearStr)+1);
		}
		if(flag=="sub"){
			$('#year').val(parseFloat(yearStr)-1);
		}
	}
}

//校验日期格式 yyyy-mm-dd
function checkyear(year){
	var reg=/^([1-2]\d{3})[\/|\-](0?[1-9]|10|11|12)[\/|\-]([1-2]?[0-9]|0[1-9]|30|31)$/ig;
	return reg.test(year);
}
</script>
</head>
  
<body  class="easyui-layout">

<div region="north" class="easyui-panel" title="节假日管理-年度维护" style="height:130px;">
	<form id="form62701" class="dlg-form" novalidate="novalidate">
		<table class="container">
			<col width="40%" /><col width="60%" />
			<tr>	        
				<th><label for="year">管理年度:</label></th>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="auto('sub')"></a>
					<input type="text" id="year" name="year" class="easyui-validatebox" required="true" validType="digits"/>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="auto('add')"></a>
				</td>
			</tr>
		</table> 
		<div class="buttons">
			<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="insert62701()">确定</a>
		</div>
	</form>
</div>   
<!-- 
<div  class="easyui-panel"  id="info">
	<p>年度维护中……</p>
</div>
 --> 
<div region="west" id="fullCalendar" style="width:700px;height:400px;border:0" ></div>
<div region="center" class="easyui-panel"  style="height:400px;border:0">
	<form id="form62703" class="dlg-form" novalidate="novalidate">
		<table id="fullCalendar" class="container" fit="true">
			<col width="40%" /><col width="60%" />
			<tr>	        
				<th><label for="festivaldate">日期:</label></th>
				<td><input type="text" id="festivaldate" name="festivaldate" class="easyui-validatebox" readOnly="true"/></td>
				
			</tr>
			<tr>
				<th><label for="festivalflag">是否是休息:</label></th>
				<td>
					<ul class="multivalue horizontal">
						<li><input id="festivalflag-0" name="festivalflag" type="radio" value="0"/><label for="festivalflag-0">否</label></li>
						<li><input id="festivalflag-1" name="festivalflag" type="radio" value="1"/><label for="festivalflag-1">是</label></li>
					</ul>
				</td>
			</tr>
			<tr>
				<th><label for="freeuse2">备注:</label></th>
				<td><textarea id="freeuse2" name="freeuse2" class="easyui-validatebox"  validType="length[1,40]"></textarea></td>
			</tr>
		</table> 
		<div class="buttons">
			<a class="easyui-linkbutton" iconCls="icon-save" href="javascript:void(0)" onclick="saveInfo()">保存</a>
		</div>
	</form>
</div> 
</body>
</html>
