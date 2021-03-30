<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ page import = "java.util.*" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>错误提示</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
html,body {height:100%;overflow:hidden;}
.light-border {border: 1px solid #c5dbec;}
#result-page {margin: 5px 10px;}
#panel {background: #fff url(<%= _contexPath %>/ui/resultpage/bg_top.jpg) repeat-x;}
.result-error {background: #fff url(<%= _contexPath %>/ui/resultpage/ico_error.jpg) no-repeat; width: 500px; margin: 100px auto 50px; padding: 0 0 0 120px;}
#info h1 {font-size: 24px; line-height: 50px; font-weight: normal; color: #ff7b00; margin: 5px 0 10px;}
#info div {font-size: 14px; word-break: break-all;}
div.buttons {height: 102px; margin:0; background-image: url(<%= _contexPath %>/ui/resultpage/bg_bottom.jpg); background-repeat: no-repeat; background-position: bottom;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript">
//<![CDATA[
$(function() {
	ydl.displayRunning(false);
	$('#b-back').click(function() {
		history.back();
	}).focus();
});
//]]>
</script>
</head>
<body id="result-page">
<div id="panel" class="light-border">
	<div id="info" class="result-error">
		<h1>抱歉，出现了一些问题</h1>
		<div><%out.println(request.getAttribute("message"));%></div>
	</div>
	<div class="buttons">
		<a id="b-back" href="javascript:void(0)" class="easyui-linkbutton">返回</a>
	</div>
</div>
</body>
</html>