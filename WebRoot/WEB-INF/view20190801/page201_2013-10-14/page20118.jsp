<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>业务咨询向导步骤添加/修改</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<base target="main" />
<script type="text/javascript">//<![CDATA[
$(function () {

});
//]]></script>
</head>

<body>
<form id="form20118" class="dlg-form" method="post" action="success.html">
	<table class="container">
		<tr>
			<th><label for="stepId">向导步骤ID：</label></th>
			<td><input id="stepId" name="stepId" class="easyui-validatebox" type="text" data-options="required:true" readonly="readonly" /></td>
		</tr>
		<tr>
			<th><label for="consultSubItemId">咨询信息子项ID：</label></th>
			<td><input id="consultSubItemId" name="consultSubItemId" class="easyui-validatebox" type="text" data-options="required:true" readonly="readonly" /></td>
		</tr>
		<tr>
			<th><label for="step">向导步骤号：</label></th>
			<td><input id="step" name="step" type="text" class="easyui-validatebox" required="required" /></td>
		</tr>
		<tr>
			<th><label for="stepName">向导步骤名称：</label></th>
			<td><input id="stepName" name="stepName" type="text" class="easyui-validatebox" required="required" /></td>
		</tr>
	</table>
</form>
</body>
</html>
