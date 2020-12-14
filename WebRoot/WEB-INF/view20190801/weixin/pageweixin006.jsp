<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/PhotoGallery.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%=_contexPath%>" />
<title>微信关注信息配置</title>
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/foddermanager/css/weixinsubconfig.css" />
<script type="text/javascript" src="<%=_contexPath%>/scripts/foddermanager/js/jquery-form.js"></script>
<!--[if lte IE 9]>
<style type="text/css">
 /* 版本小于或等于IE9将用这行的样式 */
.subcount {
    position: relative;
    top: -32px;
    left: 570px;
    z-index: 1001;
    width: 50px;
    height: 30px;
    line-height: 30px;
    font-size: 14px;
    color: gray;
    border: 1px solid #E7E7EB;
    border-left: none;
    text-align: center;
    background-color: white;
}
.welcomesubcount {
	position: relative;
	top: -32px;
	left: 560px;
	width: 50px;
	height: 30px;
	line-height: 30px;
	font-size: 14px;
	color: gray;
	border: 1px solid #E7E7EB;
	border-left: none;
	text-align: center;
	background-color: white;
}
</style>
<![endif]-->
<script type="text/javascript">
var realurl;
var centerid;
var funcArray = {};
var funcresult;
var count = 1;
$(function(){
	centerid = '<%= user.getCenterid()%>';
	$("#savepicurl").data("savepicurl", "weixin/subscribe/" + centerid);
	$("#titleinput").keyup(function(){
		var length=this.value.length;
	    $("#count").text(length);
	    $("#leftbodytitle").text($(this).val());
	    if(length == 0){
	    	$("#leftbodytitle").text("标题");
		}
		var hei = $("#leftbodytitle").height();
		if(hei > 30){
			var obj = $("#leftbodytitle");
			var offset = obj.offset();   
			$("#leftbodytitle").css({position: "relative",'top':163,'left':11,'z-index':1}); 
		}
		if(hei == 30){
			var obj = $("#leftbodytitle");
			var offset = obj.offset();   
			$("#leftbodytitle").css({position: "relative",'top':193,'left':11,'z-index':1}); 
		}
	});

	$("#welcometitleinput").keyup(function(){
		var length=this.value.length;
	    $("#welcomecount").text(length);
	    $("#functionconfig").text($(this).val());
	    if(length == 0){
	    	$("#functionconfig").text("欢迎语");
		}
	});

	$("#selectpic").click(function(){
		$("#mainbodyhide").removeClass("hide");
		$("#mainbodyhide").css("min-height",$("#submainbody").height());
	});	

	$("#uploadfile").change(function(){
		var lim = 1024*1024;
		var f = document.getElementById("uploadfile").files;
		var pictype = f[0].type;
		if(pictype == "image/jpeg" || pictype == "image/png" || pictype == "image/jpg" || pictype == "image/bmp"){
			
		}else{
			alert("上传图片格式有误，图片格式为jpg,jpeg,bmp,png");
			return false;
		}
		if(f[0].size > lim){
			alert(f[0].name + "大小超过限制，大小不能超过1M");
			return false;
		}
	    $("#imageform").ajaxForm({  
	    	 dataType:'json',   
	         type:'POST',  
	         clearForm:true, 
	         resetForm:true, 
             success:function(data) {  
                var imgurl = data.imgUrl;
            	$("#fileChecker").attr("src",imgurl);
            	$("#showpic").removeClass("hide");
            	$("#deletdselectpic").removeClass("hide");
            	$("#subcover").empty();
            	$("#subcover").html("<img class='subcoverimg' src='" + imgurl + "'>");
                realurl = data.realUrl;
             }  
		}).submit();  
	});

	$("#deletdselectpic").click(function(){
		$.ajax({
			url:"weixinapi00402.json",
			data:{
				realurl:realurl
			},
			type:'POST',
			dataType:'json',
			success:function(info){
				var recode = info.recode
				if(recode == "999999"){
					window.location.href = window.location.href;
				}
				if(recode == "000000"){
					$("#showpic").addClass("hide");
	            	$("#deletdselectpic").addClass("hide");
					$("#subcover").empty();
					$("#subcover").text("封面图片");
					$("#fileChecker").attr("src","");
				}
			},
			error:function(){
			}
		});
	});
	$("#selectfunc").on('click',".radiolable",function(){
		var id = $(this).attr("id");
		var radioid = id.split("_")[1];
		if($("#functionlist_" + radioid).attr("checked")){
			var newfuncArray = {};
			count = 1;
			for(var prop in funcArray){
			    if(funcArray.hasOwnProperty(prop)){
				    if(funcArray[prop] != $("#radiolable_" + radioid).text()){
						newfuncArray[count] = funcArray[prop];
						count++;
					}
			    }
			}
			funcArray = newfuncArray;
			var content = "";
			for(var prop in funcArray){
			    if(funcArray.hasOwnProperty(prop)){
			    	content = content + '<span>' + prop + "." + funcArray[prop] + '</span><br />';
			    }
			}
			$("#functionlist_" + radioid).removeAttr("checked");
			$("#functions").html(content);
		}else{
			funcArray[count] = $("#radiolable_" + radioid).text();
			$("#functionlist_" + radioid).attr("checked","checked");
			var content = $("#functions").html();
			content = content + '<span>' + count + "." + $(this).text() + '</span><br />';
			$("#functions").html(content);
			count++;
		}
	});

	$("#selectfunc").on('click',".functionlist",function(){
		var id = $(this).attr("id");
		var radioid = id.split("_")[1];
		if($("#" + id).attr("checked")){
			funcArray[count] = $("#radiolable_" + radioid).text();
			var content = $("#functions").html();
			content = content + '<span>' + count + "." + $("#radiolable_" + radioid).text() + '</span><br />';
			$("#functions").html(content);
			count++;
		}else{
			var newfuncArray = {};
			count = 1;
			for(var prop in funcArray){
			    if(funcArray.hasOwnProperty(prop)){
				    if(funcArray[prop] != $("#radiolable_" + radioid).text()){
						newfuncArray[count] = funcArray[prop];
						count++;
					}
			    }
			}
			funcArray = newfuncArray;
			var content = "";
			for(var prop in funcArray){
			    if(funcArray.hasOwnProperty(prop)){
			    	content = content + '<span>' + prop + "." + funcArray[prop] + '</span><br />';
			    }
			}
			$("#functions").html(content);
		}
	});

	$("#commitfunc").click(function(){
		var title = $("#titleinput").val();
		if(title.trim() == ""){
			alert("请输入标题");
			return false;
		}
		var imgUrl = $("#fileChecker").attr("src");
		if(imgUrl == ""){
			alert("请选择图片");
			return false;
		}
		var welcome = $("#welcometitleinput").val();
		if(welcome.trim() == ""){
			alert("请输入欢迎语");
			return false;
		}
		if($("input[name='functionlist']:checked").length <= 0){
			alert("请选择功能");
			return false;
		}
		$.ajax({
			url:"weixinapi00407.json",
			data:{
				title:title,
				imgUrl:imgUrl,
				realurl:realurl,
				welcome:welcome,
				centerid:centerid,
				funcArray:funcArray
			},
			type:'POST',
			dataType:'json',
			success:function(info){
				var recode = info.errcode;
				var msg = info.errmsg;
				if(recode == "000000"){
					alert(msg);
				}else{
					alert(msg);
				}
			},
			error:function(){
			}
		});
	});
	
	getFuncConfig();
});

function commenfunc(){
	var repicurl = $("#repicurl").data("repicurl");//获得图片绝对路劲
	alert(repicurl);
	var serverimg =$("#serverimg").data("serverimg");//获得图片服务器路劲
	alert(serverimg);
	$("#fileChecker").attr("src",repicurl);
	$("#showpic").removeClass("hide");
	$("#deletdselectpic").removeClass("hide");
	$("#subcover").empty();
	$("#subcover").html("<img class='subcoverimg' src='" + repicurl + "'>");
    realurl = serverimg;
}

function getFuncConfig(){
	$.ajax({
		url:"weixinapi00404.json",
		data:{
			regionId:centerid
		},
		type:'POST',
		dataType:'json',
		success:function(info){
			var recode = info.errcode;
			if(recode == 0){
				var content = "";
				funcresult = eval('(' + info.rows + ')').result; 
				for(var i = 0; i < funcresult.length; i++){
					content = content + '<div class="selectfuncdiv"><input type="checkbox" class="functionlist" name="functionlist" id="functionlist_' + i + '"/><label class="radiolable" id="radiolable_' + i + '">' + funcresult[i].name + '</lable></div>';
				}
				$("#selectfunc").html(content);
			}
			getQueryInfo();
		},
		error:function(){
		}
	});
}
function getQueryInfo(){
	$.ajax({
		url:"weixinapi00408.json",
		data:{
			regionId:centerid
		},
		type:'POST',
		dataType:'json',
		success:function(info){
			var recode = info.errcode;
			if(recode == 0){
				var data = info.rows;
				$("#titleinput").val(data.title);
				$("#leftbodytitle").text(data.title);
				$("#count").text(data.title.length);
				$("#fileChecker").attr("src",data.imgUrl);
				$("#showpic").removeClass("hide");
				$("#deletdselectpic").removeClass("hide");
				$("#subcover").html("<img class='subcoverimg' src='" + data.imgUrl + "'>");
				realurl = data.realurl;
				$("#welcometitleinput").val(data.welcome);
				$("#welcomecount").text(data.welcome.length);
				$("#functionconfig").text(data.welcome);
				funcArray = data.funcArray;
				var content = "";
				for(var prop in funcArray){
				    if(funcArray.hasOwnProperty(prop)){
					    count++;
				    	content = content + '<span>' + prop + "." + funcArray[prop] + '</span><br />';
				    	for(var i = 0; i < funcresult.length; i++){
							if(funcArray[prop] == funcresult[i].name){
								$("#functionlist_" + i).attr("checked","checked");
							}
					    }
				    }
				}
				$("#functions").html(content);
			}
		},
		error:function(){
		}
	});
}
</script>
</head>
<body>
	<div class="submainbody" id="submainbody">
		<div class="submainheadtitle">微信关注信息配置</div>
		<div class="subleftbody">
			<div class="subcover" id="subcover">
				封面图片
			</div>
			<div class="subleftbodytitle" id="leftbodytitle">标题</div>
			<div class="functionconfig">
				<p id="functionconfig">欢迎语</p>
				<p id="functions"></p>
			</div>
		</div>
		<div class="subrightbody">
			<div class="subrightbodytitle">
				<ul>
					<li class="subtitleli">标题 </li>
					<li class="subtitlelit"><input type="text" class="subtitleinput" id="titleinput" maxlength="28"/></li>
					<li class="subcount"><span id="count">0</span><span>/28</span></li>
					<li class="subtitlelicover">封面<span>（建议尺寸：536像素 * 300像素）</span></li>
					<li class="conmonli"><div class="uploadpic">上传图片</div></li>
					<li class="conmonli"><div id="selectpic" class="selectpic">从图片库选择</div></li>
					<form id="imageform" method="post" enctype="multipart/form-data" action="weixinapi00401.json">  
						<li><input type="file"  class="inputfile" id="uploadfile" name="uploadfile"/></li>
					</form>
				</ul>
			</div>
			<div class="showpic hide" id="showpic">
				<img id="fileChecker"  src=""/>
			</div>
			<div class="deletdselectpic hide" id="deletdselectpic">删除</div>
			<div class="welcometitle">
				<ul>
					<li class="welcometitleli">欢迎语</li>
					<li class="welcometitlelit"><input type="text" class="welcometitleinput" id="welcometitleinput" maxlength="64"/></li>
					<li class="welcomesubcount"><span id="welcomecount">0</span><span>/64</span></li>
					<li class="welcomefunc">选择功能</li>
					<li class="selectfunc" id="selectfunc">
<%--						<div class="selectfuncdiv"><input type="checkbox" name="functionlist"/>在线咨询一个十个字吧</div>--%>
<%--						<div class="selectfuncdiv"><input type="checkbox" name="functionlist"/>在线咨询一个十个字</div>--%>
<%--						<div class="selectfuncdiv"><input type="checkbox" name="functionlist"/>在线咨询一个十个</div>--%>
<%--						<div class="selectfuncdiv"><input type="checkbox" name="functionlist"/>在线咨询一个十</div>--%>
<%--						<div class="selectfuncdiv"><input type="checkbox" name="functionlist"/>在线咨询一个</div>--%>
<%--						<div class="selectfuncdiv"><input type="checkbox" name="functionlist"/>在线咨询一</div>--%>
<%--						<div class="selectfuncdiv"><input type="checkbox" name="functionlist"/>在线咨询</div>--%>
<%--						<div class="selectfuncdiv"><input type="checkbox" name="functionlist"/>在线咨</div>--%>
					</li>
				</ul>
			</div>
		</div>
		<div class="commitfunc" id="commitfunc">完成</div>
	</div>
<%--选择图片的保存路劲	--%>
<input type="hidden" id="savepicurl" />
<%--选择完成图片返回图片绝对路劲--%>
<input type="hidden" id="repicurl" />
<%--返回服务器上的真实路劲，供删除使用	--%>
<input type="hidden" id="serverimg" />
</body>
</html>
