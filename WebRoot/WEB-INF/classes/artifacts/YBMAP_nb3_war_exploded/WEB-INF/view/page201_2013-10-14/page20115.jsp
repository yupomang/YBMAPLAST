<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>公共条件增加/修改</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
body,html{height:97%;}

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
<form id="form20115" class="dlg-form" method="post" action="success.html">
	<table class="container">
		<tr>
			<th><label for="conditionId">公共条件ID：</label></th>
			<td><input id="conditionId" name="conditionId" readonly="readonly"  class="easyui-validatebox" type="text" data-options="required:true" /></td>
		</tr>
		<tr>
			<th><label for="consultItemId">咨询信息项目ID：</label></th>
			<td><input id="consultItemId" name="consultItemId" readonly="readonly"  class="easyui-validatebox" type="text" data-options="required:true" /></td>
		</tr>
		<tr>
			<th><label for="conditionGroupId">公共条件分组ID：</label></th>
			<td><input id="conditionGroupId" name="conditionGroupId" type="text" readonly="readonly"  class="easyui-validatebox" required="required" /></td>
		</tr>
		<tr>
			<th><label for="noOreder">公共条件序号：</label></th>
			<td><input id="noOreder" name="noOreder" type="text" class="easyui-validatebox" required="required" /></td>
		</tr>
		<tr>
			<th><label for="conditionDetail">条件内容：</label></th>
			<td><input id="conditionDetail" name="conditionDetail" type="text" class="easyui-validatebox" required="required" /></td>
		</tr>
	</table>
</form>
</body>
</html>
