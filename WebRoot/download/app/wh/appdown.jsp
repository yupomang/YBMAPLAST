<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>

	<body>
		<br>
		<br>
		<br>
		<br>
		<br>
		<a
			href="itms-services://?action=download-manifest&url=http://www.12329app.com/YBMAP/download/app/wh/gjj.plist">
			<h1>
				☛☛安装公积金iOS版---威海公积金中心☚☚
			</h1> </a>
		<br>
		<br>
		<br>
		<br>
		<br>
		<!-- 
		<a
			href="itms-services://?action=download-manifest&url=http://gjjtest.sinaapp.com/gjjtest.plist">
			<h1>
				☛☛安装公积金iOS版---测试环境☚☚
			</h1> </a>
		<br>
		<br>
		<br>
		<br>
		<br>
		<a
			href="itms-services://?action=download-manifest&url=https://gjjtest.sinaapp.com/gjjhh.plist">
			<h1>
				☛☛安装公积金iOS版---标准☚☚
			</h1> </a>
		<br>
		<br>
		<br>
		<br>
		<br>
		 -->
		<!-- http://gjjtest.sinaapp.com/gjj.apk -->
		<a href="<%=request.getContextPath()%>/downloadimg.file?filePathParam=push_app&fileName=00063100/gjj.apk&isFullUrl=true">
			<h1>
				☛☛安装公积金Android版---威海公积金中心☚☚
			</h1> </a>
		<br>
		<br>
		<br>
		<br>
		<br>
	</body>
</html>
