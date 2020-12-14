<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/init.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%=_contexPath%>" />
<%@ include file="/include/styles.jsp"%>
<%@ include file="/include/scripts.jsp"%>
<title></title>
</head>
<body>
<script type="text/javascript" src="<%=_contexPath%>/scripts/jquery.form.js"></script>
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/kindeditor/themes/default/default.css" />
<script type="text/javascript" charset="utf-8" src="<%=_contexPath%>/scripts/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=_contexPath%>/scripts/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript">

$(function() {
	//判断ie8设置透明
	if ($.browser.msie && parseInt($.browser.version) < 9) {
		$('#file').css('filter', 'alpha(opacity=0)');
	}
});
</script>
<div id="dlg-panel" class="easyui-panel" >
	<form id="form30402" class="dlg-form" method="post" action="success.html" novalidate="novalidate">
		<table class="container">
			<col width="15%" /><col width="85%" />
			<tr>
				<th><label for="title">模版要素序号：</label></th>
				<td><input id="serialNumber" name="serialNumber" class="easyui-numberbox" min="1" validType="length[1,50]" type="text" required="required" /></td>
			</tr>
			<tr>
				<th><label for="theme">模板要素占位符：</label></th>
				<td><input id="templateDetailName" name="templateDetailName" class="easyui-validatebox" required="required" readonly="readonly"/></td>
			</tr>
			<tr>
				<th><label for="detail">模板要素备注：</label></th>
				<td><input id="templateDetailRemark" name="templateDetailRemark" class="easyui-validatebox" required="required" ></textarea></td>
			</tr>
		</table>		
		<input type="hidden" id="templateDetailId" name="templateDetailId" />
	</form>
</div>
</body>
</html>