<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.util.List"%>
<%@ include file="/include/init.jsp"%>
<%@ include file = "/include/styles.jsp" %>
<%@ include file="/include/scripts.jsp"%>
<%@page import="com.yondervision.mi.common.UserContext"%>
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
<meta name="contexPath" content="<%=_contexPath%>" />
<title></title>
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/foddermanager/css/fodder.css" />
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/foddermanager/css/webuploader.css" />
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/foddermanager/css/diyUpload.css" />
<script type="text/javascript" src="<%=_contexPath%>/scripts/foddermanager/js/diyUpload.js"></script>
<script type="text/javascript" src="<%=_contexPath%>/scripts/foddermanager/js/page13002.js"></script>
<script type="text/javascript" src="<%=_contexPath%>/scripts/foddermanager/js/webuploader.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
	<div class="mainbodyhide hide" id="mainbodyhide">
		<div class="thidebody">
			<div class="tthidehead">
				<div class="fontstyle">选择图片</div>
				<div class="bagpic" id="bagpic"></div>
			</div>
			
			<div class="ttleft" id="ttleft">
			</div>

			<div class="ttright">
				<div class="ttrighthead">
					<div class="newguize">大小: 不超过1M,&nbsp;&nbsp;&nbsp;&nbsp;格式: jpg,jpeg,bmp,png</div>
					<div id="test" ></div>
				</div>
				<div id="grouppicdiv">
				</div>
				<div class="currentpagehide hide" id="allcurrentpage">
					<div class="currentpagehideleft hide" id="currentpageleft">
						<img src="<%=_contexPath%>/scripts/foddermanager/image/left.png" alt="" />
					</div>
					<div class="currentpagenumhide"><span id="currentpagenum"></span><span>/</span><span id="allpagenum"></span></div>				
					<div class="currentpagehideleft" id="currentpageright">
						<img src="<%=_contexPath%>/scripts/foddermanager/image/right.png" alt="" />
					</div>
					<div class="systempagenum"><input type="text" id="systempagenum" /></div>
					<div class="gotonewpage" id="gotonewpage">跳转</div>
				</div>
			</div>

			<div class="tthideend">
				<div class="tthideendzt">
					<span>已选</span><span id="selectpicnum">0</span><span>个，可选</span><span>1</span><span>个</span>
				</div>
				<div class="tthideendchild">
					<span class="tthideendchildspan"><button class="tthideendchildspancommit" id="tthideendchildspancommit" disabled="disabled">确定</button></span>
					<span class="tthideendchildspan"><button class="tthideendchildspancancle" id="tthideendchildspancancle">取消</button></span>
				</div>
			</div>
		</div>
	</div>
	<div class="floatyes hide" id="floatyes">
		<img src="<%=_contexPath%>/scripts/foddermanager/image/yes.png" alt="" />
	</div>
	<input type="hidden"  id="dataTid"/>
</body>
</html>