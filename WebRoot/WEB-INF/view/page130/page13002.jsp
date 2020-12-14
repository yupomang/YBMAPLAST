<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/PhotoGallery.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>素材管理调用实例</title>
</head>
<style>
.selectpic{
	width:100px;
	height: 30px;
	border: 1px solid #E7E7EB;
	background-color: white;
}
.selectpic:hover {
	background-color: #E6E7EC;
}
</style>
<body>
	<button class="selectpic" id="selectpic">从图片库选择</button>
	<%--选择图片的保存路劲	--%>
	<input type="hidden" id="savepicurl" />
	<%--选择完成图片返回图片绝对路劲--%>
	<input type="hidden" id="repicurl" />
	<%--返回服务器上的真实路劲，供删除使用	--%>
	<input type="hidden" id="serverimg" />
</body>
<script type="text/javascript">
$(function(){
	//设置保存图片的图片
	$("#savepicurl").data("savepicurl", "weixin/serverimage/ceshi/00075500");
	$("#selectpic").click(function(){
		$("#mainbodyhide").removeClass("hide");
	});
});
//选择图片后,图片保存到指定路径后执行此方法,获得图片的路劲
function commenfunc(){
	var repicurl = $("#repicurl").data("repicurl");//获得图片绝对路劲
	var serverimg =$("#serverimg").data("serverimg");//获得图片服务器路劲
}
</script>
</html>
