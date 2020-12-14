<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="contexPath" content="<%=_contexPath%>" />
<title>已推送短信息查询</title>
<%@ include file="/include/styles.jsp"%>
<link rel="stylesheet" type="text/css" href="<%= _contexPath %>/ui/message/comMessage.css" />
<%@ include file="/include/scripts.jsp"%>
<script type="text/javascript"src="<%=_contexPath%>/scripts/datagrid-detailview.js"></script>
<script type="text/javascript">
var pusMessageType= <%=request.getAttribute("pusMessageType")%>;
$(function() {
	ydl.displayRunning(false);
	$('#dg').datagrid({
		title : '已推送短信息查询',
		height : $(parent).height() - 235,
		url : 'webapi30204.json',
		method : 'post',
		queryParams : {
			'method' : 'query'
		},
		pagination: true,
		fitColumns: true,
		singleSelect: true,
		columns : [[
			{title : '业务类型',field : 'transname',width : 300},
			{title : '消息类型',hidden:true,field : 'transKeyword1',width : 180},
			{title : '消息总条数',field : 'transCount',width : 180},
			{title : '业务日期',field : 'transdate',width : 280},
			{title : '业务渠道来源',field : 'channeltype',width : 180 ,formatter : function(value) {
					if(value=='30'){
						return "移动互联应用服务平台";
					}else if(value=='40'){
						return "核心应用服务平台";
					}else{
						return "--";
					}
					//return value == '30' ? "移动互联应用服务平台": "--";
				}},
			{title : '业务类型码',hidden:true,field : 'transtype',width : 10},
			{title : 'KEY',hidden:true,field : 'seqno',width : 110}
		]],
		view : detailview,
		detailFormatter : function(index, row) {
			return '<div id="ddv-' + index + '" style="margin:5px 0"></div>';
		},
		onExpandRow : function(index, row) {			
			$('#ddv-' + index).panel({
				height : 350,
				width : 1050,
				border : false,
				cache : false,
				href : 'page3020401.html?seqid='+ row.transSeqid+'&transtype='+row.transtype,
				onLoad : function() {
					$('#dg').datagrid('fixDetailRowHeight',index);
				}
			});
			$('#dg').datagrid('fixDetailRowHeight', index);
		}
	});
	
	//查询
	$('#b_query').click(function (){
		//校验
		//if (!ydl.formValidate('form40101')) return;
		if (!$("#form30204").form("validate")) return;
		//组织查询条件
		var arr = $("#form30204").serializeArray();
		var dataJson = arrToJson(arr);
		dataJson.method = 'query';
		//查询列表数据
		$('#dg').datagrid('options').queryParams = dataJson;//修改查询条件
		$('#dg').datagrid('reload');
	});	
	
	//重置
	$('#b_reset').click(function (){
		//document.getElementById("form40101").reset(); 
		$('#form30204').form('clear');
		//除去错误校验状态
		ydl.delErrorMessage('form30204');
	});
	
});

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
<div class="easyui-panel" title="已推送短信息查询">
	<form id="form30204" class="dlg-form" novalidate="novalidate">
		<table class="container">	
			<col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" />
			<tr>		           
				<th><label for="startdate">开始日期:</label></th>
				<td><input type="text" id="startdate" name="startdate" class="easyui-datebox" editable="false" validType="date&&maxdate['enddate','【结束日期】']" maxlength="10"/></td>
				<th><label for="enddate">结束日期:</label></th>
				<td><input type="text" id="enddate" name="enddate" class="easyui-datebox" editable="false" validType="date&&mindate['startdate','【开始日期】']" maxlength="10"/></td>
			</tr>
			<tr>		           
				<th><label for="checktitle">标题信息:</label></th>
				<td><input type="text" id="checktitle" name="checktitle" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]" maxlength="100"/></td>
				<th><label for="checktext">内容信息:</label></th>
				<td><input type="text" id="checktext" name="checktext" class="easyui-validatebox" validType="validchar[['!','%','^','&','*','$','~','#','@']]&&length[0,100]" maxlength="100"/></td>
			</tr>
		</table>  
		<div class="buttons">
			<a id="b_query" class="easyui-linkbutton" iconCls="icon-search" href="javascript:void(0)" >查询</a>
			<a id="b_reset" class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" >重置</a>
		</div>
	</form>
</div>
	<table id="dg"></table>
</body>
</html>