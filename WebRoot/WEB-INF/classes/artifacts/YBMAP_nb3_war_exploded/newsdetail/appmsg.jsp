<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String messageid =  request.getParameter("messageid")==null ? "" : request.getParameter("messageid");
	String msgid =  request.getParameter("msgid")==null ? "" : request.getParameter("msgid");
	String centerId = request.getParameter("centerId")==null ? "" :request.getParameter("centerId");
	String userId = request.getParameter("userId")==null ? "" :request.getParameter("userId");
	String surplusAccount = request.getParameter("surplusAccount")==null ? "" :request.getParameter("surplusAccount");
	String deviceType = request.getParameter("deviceType")==null ? "" :request.getParameter("deviceType");
	String deviceToken = request.getParameter("deviceToken")==null ? "" :request.getParameter("deviceToken");
	String currenVersion = request.getParameter("currenVersion")==null ? "" :request.getParameter("currenVersion");
	String buzType = request.getParameter("buzType")==null ? "" :request.getParameter("buzType");
	//String channel = request.getParameter("channel")==null ? "" :request.getParameter("channel");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>详细内容</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/newsdetail/css/detail.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript">
var sum = 0;
if(sum==0){
	$(function(){
		var messageid = '<%=messageid%>';
		var msgid = '<%=msgid%>';
		var centerId = '<%=centerId%>';
		var userId = '<%=userId%>';
		var surplusAccount = '<%=surplusAccount%>';
		var deviceType = '<%=deviceType%>';
		var deviceToken = '<%=deviceToken%>';
		var currenVersion = '<%=currenVersion%>';
		var buzType = '<%=buzType%>';
		var sUserAgent = navigator.userAgent.toLowerCase();
		var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
		$.ajax({
			type : 'POST',
			url: "<%=request.getContextPath()%>/appapi30203.json",
			dataType: 'json',
			data:{messageid:messageid,
				  msgid:msgid,
				  centerId:centerId,
				  userId:userId,
				  surplusAccount:surplusAccount,
				  deviceType:deviceType,
				  deviceToken:deviceToken,
				  currenVersion:currenVersion,
				  buzType:buzType,
				  channel:'10'},
			success : function(data) {
				$.each(data.result, function(i, item) {	
					if(sum==0){								
						$("#detail").append(
							"<h1>"+data.result.messagetitle+"</h1><div style='margin-bottom:5px;'></p><p class='info'>"+data.result.messagetime+"</p></div>"+
		        			"<div id='inserContent' class='inserContent'>"+data.result.tsmsg+"</div>"
		                );
		                sum+=1;
					}else{
						$('#notfound').show();
					}
					
	        	});
	        	var _img = "";
	        	$("article img").each(function(i){
					_img = _img+$(this).attr('src')+"#";				
				});
				$("#toApple").text(_img);
				
				toAndroid(_img);
	        	
	        	if(bIsIpad){
					changeFontSize(20);
				}
	        	if(Android.getscreenInches()>6){
					changeFontSize(20);
				}
			},
			error :function(){
				$.messager.alert('错误','系统异常','error');
			}
		});
	});
}
function toAndroid(_img){	
		$("article img").click(function(){
			
			Android.openImage(_img,this.src);
		});
}
function toApple(){	
	return $("#toApple").html();
}
function changeFontSize(_size){
	$("#inserContent").css("font-size",_size+"px");
	$("#inserContent span").css("font-size",_size+"px");
	$("#inserContent table").css("font-size",_size+"px");
}
</script>
</head>

<body>
	<article class="scroll mainContent" id="detail">
			
     </article>
	<div id="notfound" style="display: none;">
		<img src="img/notfound.png" /><br /><br /><br /><br />
		<span>很抱歉，您要查看的内容不存在！</span>
	</div>
     <div id="toApple" style="display: none;"></div>
</body>
</html>
