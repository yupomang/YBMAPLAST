<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.dto.Mi620"%>
<%@page import="com.yondervision.mi.dto.Mi202"%>
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
<title>预约信息查询</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
#allmap {width: 100%;height: 90%;overflow: hidden;margin:0;}
body{width: 100%; hidden;margin:0;}
.datagrid {padding-top:5px;}
.panel-header, .panel-body {width:auto !important;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=7db9536a2794ce477288908163157bc6"></script>
<script type="text/javascript">
var url;
$(function() {
	//屏蔽错误
	window.onerror=function(){
		return true;
	}
	
	//初始化列表
	$('#dg').datagrid({   
		iconCls: 'icon-edit',
		height:388,
		title:'预约信息',
		singleSelect: true,
		//pagination:true,
		rownumbers:true,
		//fitColumns:true,
		method:'post',
		url: "<%=request.getContextPath()%>/webapi62504.json",
		queryParams:arrToJson($('#form62504').serializeArray()),
		columns:[[
			{field:'ck',field:'checkbox',title:'更新状态',align:'center',width:60,formatter:function(v,row){
				return "<input type='checkbox' value='"+row["appoid"]+"' name='chk_appoid'>"
			}},
			{title:'预约凭证号',field:'apponum',align:'center',width:130,editor:'text'},
			{title:'预约网点',field:'appobranchname',width:170,align:'center'},
			{title:'预约日期',field:'appodate',align:'center',width:80,editor:'text'},
			{title:'预约时段',field:'timeinterval',width:80,align:'center'},
			{title:'预约业务',field:'appobusiname',align:'center',width:70},
			{title:'预约状态',field:'appostate',align:'center',width:60,editor:'text',formatter:function(value,row,index) {	
				return $('#appostate option[value='+value+']').text(); 
			}},
			{title:'用户名',field:'channeluserid',align:'center',width:70,editor:'text'},
			{title:'公积金帐号',field:'accnum',align:'center',width:80,editor:'text'},
			{title:'证件号',field:'certinum',align:'center',width:120,editor:'text'},
			{title:'姓名',field:'accname',align:'center',width:80,editor:'text'},
			{title:'联系方式',field:'phone',align:'center',width:100,editor:'text'},
			{title:'渠道平台',field:'channel',align:'center',width:60,editor:'text',formatter:function(value,row,index) {	
				return $('#channel option[value='+value+']').text(); 
			}},
			{title:'办结日期',field:'completedate',align:'center',width:80,editor:'text'},
			{title:'撤销日期',field:'datecanceled',align:'center',width:80,editor:'text'}
		]],
		toolbar:[{
		id:'btnupdate',
		text:'预约状态变更',
		iconCls:'icon-edit', 
		handler:function(){
		    selectRow();
		}
		}],
		onLoadSuccess:function(data){           
           if(data.recode!=SUCCESS_CODE) $.messager.alert('提示', data.msg, 'info' );
        }	
	});	
})
function selectRow(){
	var $checkboxs = $('input:checkbox:checked');
	if ($checkboxs.length == 0) {
    	$.messager.alert('提示', "请至少选择一条记录进行修改!", 'info' );
    }else{
		$('#dlgstatu').dialog('open').dialog('setTitle','预约状态变更');
    }
}
function updateStatu(){
    var $checkboxs = $('input:checkbox:checked');
	if ($checkboxs.length > 0) {
        var para = $checkboxs.map(function(index){
			return this.value;
		}).get().join(',');
		if($('#appostate1').combobox('getValue')==null||$('#appostate1').combobox('getValue')==""){
			$.messager.alert('提示', "请选择更新的状态！", 'info');
			return;
		}
        var map={appoid:para,appostate:$('#appostate1').combobox('getValue')};
        $.ajax({
			type : 'POST',
			url : '<%= _contexPath %>/webapi62503.json',
			dataType: 'json',
			data:map,
			success : function(data){
				$.messager.alert('提示', data.msg, 'info');		
				$('#dlgstatu').dialog('close')				
				$("#dg").datagrid("reload");
			},
			error :function(){
				$.messager.alert('错误','系统异常','error');							
			}
		}); 
    }
    else $.messager.alert('提示', "请至少选择一条记录进行编辑！", 'info');
}
//查询
function select62504() {
	if (!$('#form62504').form('validate')) return;
	var arr = $('#form62504').serializeArray();
	var dataJson = arrToJson(arr);
	//datagrid列表
	$('#dg').datagrid({   
		url: "<%=request.getContextPath()%>/webapi62504.json",
		queryParams:dataJson
	});	
}
function cancel62501() {	
	$('#form62504').form('clear');
	//设置combobox默认值
	$('#areaid').combobox('select','');
	ydl.delErrorMessage('form62504');
}
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
<script type="text/javascript" src="<%=_contexPath%>/scripts/jquery.form.js"></script><!-- jquery.form.js 为页面提交json请求应用 -->
<div class="easyui-panel" title="预约信息查询">
	<form id="form62504" class="dlg-form" novalidate="novalidate">
		<table class="container">		
			<col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" />
			<tr>	        
				<th><label for="apponum">预约凭证号:</label></th>
				<td><input type="text" id="apponum" name="apponum" class="easyui-numberbox" value="" ></td>
				<th><label for="accnum">公积金帐号:</label></th>
				<td><input type="text" id="accnum" name="accnum" value=""></td>
			</tr>
			<tr>	        
				<th><label for="certinum">证件号:</label></th>
				<td><input type="text" id="certinum" name="certinum" value="" ></td>
				<th><label for="accname">预约姓名:</label></th>
				<td><input type="text" id="accname" name="accname"  value=""></td>
			</tr>
			<tr>
				<th><label for="appobranchid">预约网点区域:</label></th>
				<td><select id="appobranchid" name="appobranchid" class="easyui-combobox" editable="false" style="width:200px;" panelHeight="auto">
					<option value="" selected="selected">请选择...</option>
						<%
							String options = "";
							List<Mi202> ary=(List<Mi202>) request.getAttribute("mi202list");
							for(int i=0;i<ary.size();i++){
								Mi202 mi202=ary.get(i);
								String itemid=mi202.getAreaId();
								String itemval=mi202.getAreaName();
								options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options);
						%>
					</select>
				</td>
				<th><label for="appobusiid">预约业务:</label></th>
				<td>
				<select id="appobusiid" name="appobusiid" class="easyui-combobox" editable="false" style="width:130px;" panelHeight="auto">
					<option value="" selected="selected">请选择...</option>
						<%
							String options1 = "";
							List<Mi620> ary1=(List<Mi620>) request.getAttribute("mi620list");
							for(int i=0;i<ary1.size();i++){
								Mi620 mi620=ary1.get(i);
								String itemid=mi620.getAppobusiid();
								String itemval=mi620.getAppobusiname();
								options1 = (new StringBuilder(String.valueOf(options1))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
							}
							out.println(options1);
						%>
				</select>
				</td>
			</tr>
			<tr>		           
				<th><label for="appostate">预约状态:</label></th>
				<td>
					<select id="appostate" name="appostate" class="easyui-combobox" editable="false" style="width:130px;" panelHeight="auto">
					<option value="" selected="selected">请选择...</option>
					<option value="01">已预约</option>
					<option value="04">已办结</option>
					<option value="08">已撤销</option>
				</select>
				</td>
				<th><label for="channel">渠道平台:</label></th>
				<td>
					<select id="channel" name="channel" class="easyui-combobox" editable="false" style="width:130px;" panelHeight="auto">
					<option value="" selected="selected">请选择...</option>
					<option value="10">APP</option>
					<option value="20">微信</option>
					<option value="30">WEB端</option>
					
				</select>
				</td>
			</tr>
			<tr>
				<th><label for="startdate">预约起始日期:</label></th>
				<td><input type="text" id="startdate" name="startdate" class="easyui-datebox" validType="date"></td>
				<th><label for="enddate">预约终止日期:</label></th>
				<td><input type="text" id="enddate" name="enddate" class="easyui-datebox" validType="date"></td>
			</tr>
		</table> 
		<div class="buttons">
			<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" onclick="select62504()">查询</a>
			<a class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" onclick="cancel62501()">重置</a>
		</div>
	</form>
</div>
<!-- 预约详细列表 -->
<table id="dg" ></table>
<div id="dlgstatu" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" modal="true"  buttons="#dlg-buttons">
        <div class="formtitle">状态变更</div>
        <form id="fmstatu" class="dlg-form" method="post" novalidate="novalidate">
			<table class="timecontainer">
			<th><label for="appostate1">预约状态:</label></th>
				<td>
					<select id="appostate1" name="appostate1" class="easyui-combobox" editable="false" style="width:130px;" panelHeight="auto">
					<option value="" selected="selected">请选择...</option>
					<!--<option value="01">已预约</option>-->
					<option value="04">办结</option>
					<option value="08">撤销</option>
				</select>
				</td>
			<th>
        </form>
		<div id="dlgtime-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="updateStatu()">保存</a>
		</div>
    </div>
</body>
</html>