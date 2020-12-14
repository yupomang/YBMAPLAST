<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@ include file = "/include/init.jsp" %>
<%@page import="com.yondervision.mi.common.UserContext,com.yondervision.mi.dto.Mi620,java.util.HashMap"%>
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
<title>网点预约情况查询</title>
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
$(function() {
	$('#fullCalendar').attr("hidden",true);
	$('#dgdiv').attr("hidden",true);
	var Today = new Date();
	$('#yearmonth').val(Today.getFullYear()+"-"+(Today.getMonth()>8?"":"0")+(Today.getMonth() + 1));
})

//查询
function select62505(){
	if (!$("#form62505").form("validate")) return;
	var arr = $('#form62505').serializeArray();
	var dataJson = arrToJson(arr);
	var yearmonth=$('#yearmonth').val();
	var yearStr=yearmonth.split("-")[0];
	var monthStr=yearmonth.split("-")[1];
	$('#fullCalendar').removeAttr("hidden");
	$('#fullCalendar').attr("style","width:700px;height:400px");
	$('#fullCalendar').fullCalendar({
		//width : 600,
		//height : 380,
		fit : true,
		border : true,
		firstDay : 0,
		url : "<%=request.getContextPath()%>/webapi62505.json",
		para:dataJson,
		year:yearStr,
		month:monthStr,
		current:new Date(),
		onSelect : function (date, target) {
			var abbr=target.abbr.split(',');
			$('#appodate').val(abbr[0]+"-"+abbr[1]+"-"+(abbr[2].length>1?"":"0")+abbr[2]);
			select62506();
		},
		onChange : function (year, month) {
			document.getElementById("form62703").reset();
		}
	});
}

//数据转对象(无重复name值的数组)
function arrToJson(arr){
	var jsonObj = {};
	$.each(arr,function (index,ele){
		jsonObj[ele.name] = ele.value;
	});
	return jsonObj;
}

//查询明细
function select62506() {
	$('#dgdiv').removeAttr("hidden");
	var arr = $('#form62505').serializeArray();
	var dataJson = arrToJson(arr);
	//列表初始化
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		title:'预约明细信息',
		singleSelect: true,
		height:200,
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		method:'post',
		url: "<%=request.getContextPath()%>/webapi62506.json",
		queryParams:dataJson,
		columns:[[
			{title:'预约业务ID',field:'appoid',align:'center',width:60,editor:'text',hidden:'true'},
			{title:'预约业务凭证号',field:'apponum',align:'center',width:150,editor:'text'},
			{title:'预约业务ID',field:'appobusiid',align:'center',width:60,editor:'text',hidden:'true'},
			{title:'业务名称',field:'appobusiname',align:'center',width:100,editor:'text'},
			{title:'状态',field:'appostate',align:'center',width:60,editor:'text',formatter:function(v){
		        var json={"01":"已预约","04":"已办结","08":"已撤销"};
		        if(json[v]){
		           return json[v];
		        }else{
		           return v;
		        }
		    }},
		    {title:'统一用户名',field:'unifieduserid',align:'center',width:60,editor:'text',hidden:'true'},
			{title:'用户名',field:'channeluser',align:'center',width:100,editor:'text'},
			{title:'渠道平台',field:'channel',align:'center',width:60,editor:'text',hidden:'true'},
			{title:'联系方式',field:'phone',align:'center',width:100,editor:'text'},
			{title:'创建日期',field:'datecreated',align:'center',width:180,editor:'text'},
			{title:'办结日期',field:'completedate',align:'center',width:180,editor:'text'},
			{title:'撤销日期',field:'datecanceled',align:'center',width:180,editor:'text'}
		]],
		toolbar:'#toolbar',
		onLoadSuccess:function(data){           
           if (data.recode != SUCCESS_CODE) $.messager.alert('提示', '【查询出错】：'+data.msg, 'info' );
        }		
	});

}

function auto(flag){
	var yearStr=$('#yearmonth').val();
	if (!checkyear(yearStr+"-01")){
		$.messager.alert("提示","输入正确的月份[yyyy-mm]","info");
		return;
	}	
	if(yearStr!=""){
		var newYearMonth;
		if(flag=="add"){
			newYearMonth = getNewDateSubNum(yearStr+"-28",-10);
		}
		if(flag=="sub"){
			newYearMonth = getNewDateSubNum(yearStr+"-28",30);
			
		}
		newYearMonth=newYearMonth.substring(0,7);
		$('#yearmonth').val(newYearMonth);
	}
}

//校验日期格式 yyyy-mm-dd
function checkyear(year){
	var reg=/^([1-2]\d{3})[\/|\-](0?[1-9]|10|11|12)[\/|\-]([1-2]?[0-9]|0[1-9]|30|31)$/ig;
	return reg.test(year);
}
// 日期减去天数等于第二个日期
function getNewDateSubNum(date,dNum){
	var date = new Date(date)
	date = date.valueOf()
	date = date - dNum * 24 * 60 * 60 * 1000
	date = new Date(date);
	return date.getFullYear()+"-"+(date.getMonth()>8?"":"0")+(date.getMonth() + 1)+"-"+date.getDate();
}
</script>
</head>
  
<body >
<div class="easyui-panel" title="网点预约情况查询" style="height:130px;">
	<form id="form62505" class="dlg-form" novalidate="novalidate">
		<table class="container">
			<col width="10%" /><col width="23%" /><col width="10%" /><col width="23%" /><col width="10%" /><col width="24%" />
			<tr>	        
				<th><label for="appobranchid">预约网点:</label></th>
				<td>
					<select id="appobranchid" name="appobranchid" panelHeight="auto" required="true">	        	
							<%
								String appobranchidOptions = "";
								List<HashMap> list623=(List<HashMap>) request.getAttribute("mi623list");
								for(int i=0;i<list623.size();i++){
									HashMap map623=list623.get(i);
									String itemid=map623.get("website_code").toString();
									String itemval=map623.get("website_name").toString();
									appobranchidOptions = (new StringBuilder(String.valueOf(appobranchidOptions))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
								}
								out.println(appobranchidOptions);
							%>
					</select>
				</td>
				<th><label for="appobusiid" >预约业务：</label></th>
				<td><select id="appobusiid" name="appobusiid" panelHeight="auto" required="true">	        	
							<%
								String appobusiidOptions = "";
								List<Mi620> ary3=(List<Mi620>) request.getAttribute("mi620list");
								for(int i=0;i<ary3.size();i++){
									Mi620 mi620=ary3.get(i);
									String itemid=mi620.getAppobusiid();
									String itemval=mi620.getAppobusiname();
									appobusiidOptions = (new StringBuilder(String.valueOf(appobusiidOptions))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
								}
								out.println(appobusiidOptions);
							%>
							
						</select>
				</td>
				<th><label for="yearmonth">预约月份:</label></th>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="auto('sub')"></a>
					<input type="text" id="yearmonth" name="yearmonth" class="easyui-validatebox" required="true" validType="digits"/>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="auto('add')"></a>
				</td>
				
				
				<!-- 隐藏区域保存预约日期 -->
				<input type="text" id="appodate" name="appodate" type="hidden" hidden="true"/>
			</tr>
		</table> 
		<div class="buttons">
			<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="select62505()">查询</a>
		</div>
	</form>
</div>   
<div style="height:420px;border:0" ><div id="fullCalendar" style="border:0" ></div></div>

<div id-"dgdiv" class="easyui-panel"  style="height:200px;border:0">
	<!-- 列表数据 -->
	<table id="dg" ></table>
</div> 
</body>
</html>
