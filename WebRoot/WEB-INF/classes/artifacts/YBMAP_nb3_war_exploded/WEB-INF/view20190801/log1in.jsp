<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
String _contexPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>登录</title>
<link rel="stylesheet" type="text/css" href="<%= _contexPath %>/css/master.css"/>
<script src="<%= _contexPath %>/js/lib/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
<script src="<%= _contexPath %>/js/lib/vue.js" type="text/javascript" charset="utf-8"></script>
<script src="<%= _contexPath %>/js/login.js" type="text/javascript" charset="utf-8"></script>

</head>

<body>
	<div class="loginTopBox">
		<a href="index.html"><img class="db fl" src="images/toplogo.gif"/></a>
	</div>
	<div class="loginBoxWrapper">
		<div class="loginBox" id="login">
			<p class="loginTitleText">登　录</p>
			<input type="text" class="loginInputType loignIconUser" placeholder="请输入用户名" v-model="postData.username" />
			<input type="password" class="loginInputType loignIconPwd" placeholder="请输入密码" v-model="postData.password" />
			<div class="loginInputBox">
				<input type="password" id="loginPinInput" class="loginInputType loignIconVer" placeholder="请输入验证码" v-model="postData.rancode" />
				<div class="verCbox">
					<img src="vericode.html" title="点击刷新" name="valimg" id="valimg" onmouseover="this.style.cursor='pointer'"/>
					<span class="rVerCodeBtn"></span>
					<script type="text/javascript">
						$('#valimg, .rVerCodeBtn').click(function() {
							$('#valimg')[0].src ='vericode.html?' + Math.random();
						});

					</script>
				</div>
			</div>
			<input type="button" class="loginBtn" @click="loginAjaxPost" value="登　录" />
		</div>
	</div>
	<p class="copy">版权所有 &copy; 2016 华信永道（北京）科技股份有限公司</p>
</body>
</html>