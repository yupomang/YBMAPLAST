<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>欢迎</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
body {background: #fff url(<%= _contexPath %>/ui/welcome_bg1.gif) repeat-x; margin:0;}
div {background: url(<%= _contexPath %>/ui/welcome_bg2.jpg) no-repeat; width: 1050px; height: 580px;}
a:link, a:visited {color: #1B73E1; font-family: "微软雅黑"; font-size: 14px; display: block; width: 126px; height: 143px; position: absolute;}
.a_hover {color: #E4161F;}
.disbled {
	color:#888 !important;
	cursor: text;
}
a span {position: absolute; bottom: 1px; width: 100%; text-align: center;}
#link1 {left: 44px; top: 272px;}
#link2 {left: 166px; top: 162px;}
#link3 {left: 325px; top: 92px;}
#link4 {left: 494px; top: 58px;}
#link5 {left: 670px; top: 68px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<%
  UserContext user = UserContext.getInstance();
  if(user==null)  {
     out.println("超时");
     return;
  }
%>
<script type="text/javascript">
//<![CDATA[
$(function() {
	$('a').on('mouseover',function (){
		if (!$(this).hasClass('disbled')) $(this).find('span').addClass('a_hover');
	}).on('mouseout',function (){
		$(this).find('span').removeClass('a_hover');
	}); 

    // 页面按钮权限控制 -业务咨询
	var activeflg1 = <%=user.checkFuncUrlExist("page20101.html")%>;
	if(activeflg1){
		$('#link1').attr('href','<%=user.getShortcutsUrl("page20101.html")%>').removeClass('disbled');
	}else{
		$('#link1').attr('href','#').addClass('disbled');
	}
    // 页面按钮权限控制 -楼盘信息维护
	var activeflg2 = <%=user.checkFuncUrlExist("page00804.html")%>;
	if(activeflg2){
		$('#link2').attr('href','<%=user.getShortcutsUrl("page00804.html")%>').removeClass('disbled');
	}else{
		$('#link2').attr('href','#').addClass('disbled');
	}
	// 页面按钮权限控制 -网点信息维护
	var activeflg3 = <%=user.checkFuncUrlExist("page10104.html")%>;
	if(activeflg3){
		$('#link3').attr('href','<%=user.getShortcutsUrl("page10104.html")%>').removeClass('disbled');
	}else{
		$('#link3').attr('href','#').addClass('disbled');
		
	}
	// 页面按钮权限控制 -公共短消息推送
	var activeflg4 = <%=user.checkFuncUrlExist("page30201.html")%>;
	if(activeflg4){
		$('#link4').attr('href','<%=user.getShortcutsUrl("page30201.html")%>').removeClass('disbled');
	}else{
		$('#link4').attr('href','#').addClass('disbled');
	}
	// 页面按钮权限控制 -短消息报盘推送
	var activeflg5 = <%=user.checkFuncUrlExist("page30202.html")%>;
	if(activeflg5){
		$('#link5').attr('href','<%=user.getShortcutsUrl("page30202.html")%>').removeClass('disbled');
	}else{
		$('#link5').attr('href','#').addClass('disbled');
	}
});
//]]>
</script>
</head>
<body>
<div>
<a id="link1" href=""><span>业务咨询</span></a>
<a id="link2" href=""><span>楼盘信息维护</span></a>
<a id="link3" href=""><span>网点信息维护</span></a>
<a id="link4" href=""><span>公共短消息推送</span></a>
<a id="link5" href=""><span>短消息报盘推送</span></a>
</div>
</body>
</html>