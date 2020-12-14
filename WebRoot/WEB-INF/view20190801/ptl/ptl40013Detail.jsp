<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file = "/include/styles.jsp" %>
<%@ include file = "/include/scripts.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>APP业务日志信息</title>
</head>
<body>
<%
String url = null;
String stat = null;
String describe = null;
if (request.getAttribute("url") == null || request.getAttribute("url") == "" ) {
	url = "-";
}else{
	url = request.getAttribute("url").toString();
}
if (request.getAttribute("describe") == null || request.getAttribute("describe") == "" ) {
	describe = "没有对请求进行应答，系统异常，请查看日志文件";
}else{
	describe = request.getAttribute("describe").toString();
}
%>
<div id="detailInfo">
	<ul>
		<li>
			<label>请求URL：</label>
			<span><%out.println(url); %></span>
		</li>
		<li>
			<label>处理描述：</label>
			<span><%out.println(describe); %></span>
		</li>
	</ul>
</div>
</body>
</html>