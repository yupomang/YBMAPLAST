<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="com.yondervision.mi.util.Datelet"%> 
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@ include file = "/include/init.jsp" %>
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
<title>移动互联应用服务平台</title>
<%@ include file = "/include/styles.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= _contexPath %>/ui/frames.css" />
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript" src="<%= _contexPath %>/scripts/frames.js"></script>
<script type="text/javascript">//<![CDATA[
var menujson=<%=user.getFuncJson().toString()%>
$(function () {
	$('#main')[0].src = 'welcome.html';
});
//]]></script>
<base target="main" />
</head>
<body class="easyui-layout">
    <div id="frame_header" data-options="region:'north',split:false,border:true">
		<img id="header_logo" src="<%= _contexPath %>/ui/logo.png" alt="移动互联应用服务平台" />
		<ul id="header_links">
			<li id="homepage"><a href="welcome.html" target="main">首页</a></li>
			<li id="logoff"><a href="logout.do" target="_top">退出登录</a></li>
		</ul>
		<ul id="header_info">
			<li><label>机　构：</label><span><%=user.getCenterName()%></span></li>
			<li><label>操作员：</label><span><%=user.getOpername() %></span></li>
			<li><label>日　期：</label><span><%=Datelet.getCurrentDateString()	%></span></li>
		</ul>
	</div>
	

    <div id="frame_menu" data-options="region:'west',title:'功能菜单',split:true">
		<dl id="menu"></dl>
	</div> 
    <div id="frame_main" data-options="region:'center'">
		<iframe id="main" name="main" src="" frameborder="0"></iframe>
		<div id="page-running-overlay"></div>
	</div>

</body>
</html>