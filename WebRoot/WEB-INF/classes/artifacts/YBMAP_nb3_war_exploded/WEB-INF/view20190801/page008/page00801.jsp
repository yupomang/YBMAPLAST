<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>test</title>
<%@ include file = "/include/styles.jsp" %>
<%@ include file = "/include/scripts.jsp" %>
<base target="main" />
<script type="text/javascript">
$(function() {
	$('#page00801').window('close');
	
	$('#consultType').combobox({
	    data:jsonConsultType,
	    valueField:'id',
	    textField:'text',
	    panelHeight:"auto"
	});
	
	$('#orderNo').numberbox({
		min:0
	});
})
function save00801() {
	var arr = $('#form00801').serializeArray();
	if (!$('#form00801').form( "validate" ,function(v){})) {
		//alert($('#form00801').form( "validate" ,function(v){} ));
		$.messager.alert('提示', "请输入完整数据!", 'info' );
		return;
	}
	$.ajax( {
		type : "POST",
		url : "<%=request.getContextPath()%>/webapi00801.json",
		dataType: "json",
		data:arr,
		success : function(data) {
			$.messager.alert('提示', data.msg, 'info');
		},
		error :function(){
			$.messager.alert('错误','网络连接出错！','error');
		}
	});
}

function cancel20101() {
	$('#page00801').window('close');
}

function loadform00801( row ) {
	$('#form00801').form('load',{
		consultType:row.id
	});
}
</script>
</head>

<body>
	<div class="easyui-panel" title="地图信息填加">
		<form id="form00801" novalidate="novalidate">
			<table class="container">
				<tr>
					<th><label for="houseCode">楼盘编码:</label></th>
			        <td><input id="houseCode" name="houseCode" required="true" class="easyui-validatebox"/></td>	
			        <th><label for="areaId">楼盘区域:</label></th>
		            <td><input id="areaId" name="areaId" required="true" class="easyui-validatebox" validType="length[0,20]"/></td>	
		        </tr>
		        <tr>	        
			        <th><label for="houseName">楼盘名称:</label></th>
			        <td><input id="houseName" name="houseName" required="true" class="easyui-validatebox"/></td>
		            <th><label for="developerName">开发商名称:</label></th>
		            <td><input id="developerName" name="developerName" required="true" class="easyui-validatebox" validType="length[0,100]"/></td>
		        </tr>
		        <tr>		           
		            <th><label for="address">楼盘地址:</label></th>
		            <td><input id="address" name="address" required="true" class="easyui-validatebox"/></td>
		            <th><label for="houseType">楼盘类型:</label></th>
		            <td><input id="houseType" name="houseType" required="true" class="easyui-validatebox"/></td>
		        </tr>
		        <tr>
		            <th><label for="contacterName">联系人姓名:</label></th>
		            <td><input id="contacterName" name="contacterName" required="true" class="easyui-validatebox" validType="length[0,100]"/></td>		        
		            <th><label for="tel">联系电话:</label></th>
		            <td><input id="tel" name="tel" required="true" class="easyui-validatebox" validType="length[0,20]"/></td>
		        </tr>
		        <tr>
		        	<th><label for="bankNames">合作银行:</label></th>
		            <td><input id="bankNames" name="bankNames" required="true" class="easyui-validatebox" validType="length[0,20]"/></td>
		            <th></th>
					<td>
					<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="save00801()">确认</a>
					<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="cancel20101()">取消</a></td>
		        </tr>
	        </table> 
		</form>
	</div>
<div id="page00801" class="easyui-window" modal="true" title="添加楼盘信息" iconCls="icon-save" style="width:500px;height:300px;padding:5px;">
	<div class="easyui-layout" fit="true">
		<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
		<div region="south" border="false" style="text-align:right;padding:5px 0;">
			<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="save00801()">确认</a>
			<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="cancel00801()">取消</a>
		</div>
		</div>
	</div>
</div>
</body>
</html>