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
	<meta charset="utf-8"/>
	<title>客户端下载</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link href="<%=request.getContextPath()%>/ui/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="<%=request.getContextPath()%>/ui/bootstrap-theme.min.css" rel="stylesheet">
	<style type="text/css">
		.hide{display:none;}
		html{height:100%}
		body{margin:0px;padding:0px}
	</style>
</head>
<body style="background:#419ade;" ontouchstart="">
		<div style="background:url(ui/downimg/iOSdownZS.jpg) top no-repeat; background-size:100%;" id="ios" class="hide">
			<div class="container">
				<div class="row">
					<div class="col-xs-12" id="iosUrl">
			 			<a href="itms-services://?action=download-manifest&url=http://www.12329app.com/YBMAP/download/app/zsgjj/zsgjj.plist"><button type="button" class="btn btn-warning" style="width:100%;height:3em;margin-top:25em; margin-bottom:0.8em;"><span style="font-size:1.5em;">立即下载</span></button></a>
					</div>
				</div>
				<br>
				<br>
				<br>
				<div class="row">
					<div class="col-xs-12" style="color: #ffffff; text-align:center;">
						<span >华信永道（北京）科技有限公司出品</span>
					</div>
				</div>
				<br>
			</div>
		</div>


		<div style="background:url(ui/downimg/androidDownZS.jpg) top no-repeat; background-size:100%;" id="android" class="hide">
			<div class="container">
				<div class="row">
					<div class="col-xs-12" id="andriodUrl">
						<a href="http://www.12329app.com/YBMAP/downloadimg.file?filePathParam=push_app&fileName=00076000/zsgjj.apk&isFullUrl=true"><button type="button" class="btn btn-warning" style="width:100%;height:3em;margin-top:25em; margin-bottom:0.8em;"><span style="font-size:1.5em;">立即下载</span></button></a>
					</div>
				</div>
				<br>
				<br>
				<br>
				<div class="row">
					<div class="col-xs-12" style="color: #ffffff; text-align:center;">
						<span >华信永道（北京）科技有限公司出品</span>
					</div>
				</div>
				<br>
			</div>
		</div>
		<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/bootstrap.min.js"></script>
		<script type="text/javascript">
		jQuery(document).ready(function(){
			var sUserAgent = navigator.userAgent.toLowerCase();
			var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
			var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
			var bIsAndroid = sUserAgent.match(/android/i) == "android";
			if(bIsIphoneOs){
				$("#ios").removeClass("hide");
			}
			if(bIsAndroid){
				$("#android").removeClass("hide");
			}
		});
		</script>
	</body>
</html>