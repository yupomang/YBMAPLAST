<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<%
  UserContext user=UserContext.getInstance();

  if(user==null)  {
     out.println("超时");
     return;
  }
%>
<title>密码修改</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
#list-container {width: 28%; padding:5px;}
#form-container {width: 70%; padding-top:5px; padding-left:0px; padding-right:5px; padding-bottom:5px;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<base target="main" />
<script type="text/javascript">

$(function() {

	//校验输入登录名是否同实际登录的登录名一致
	$.extend($.fn.validatebox.defaults.rules, {  
    	/*输入登录名是否同实际登录的登录名必须一致*/
    	loginidEqualTo: {
        	validator:function(value,param){
            	return param[0] == value;
        	},
        	message:'输入登录名与实际登录不符'
    	}
	});
	
	//校验两次输入新密码是否一致
	$.extend($.fn.validatebox.defaults.rules, {  
    	/*两次输入新密码必须一致*/
    	equalTo: {
        	validator:function(value,param){
            	return $(param[0]).val() == value;
        	},
        	message:'两次输入密码不匹配'
    	}
	});
	
	//点击保存
	$("#btnsave").click(function(){  
		var mi002arry = $('#operForm').serializeArray();    
   
     	var pwdUpd=$("#operForm").form("validate");
     	if(pwdUpd){          
			$.ajax({	 
				type : "POST",
				url : '<%= _contexPath %>/ptl40011Upd.json',
				dataType: "json",
				data:mi002arry,
				success : function(data) {	
					if(SUCCESS_CODE == data.recode) {
						$.messager.alert('提示', data.msg, 'info');
						operForm.reset();
					}else{
						$.messager.alert('错误',data.msg,'error');		
					}
				},
				error :function(){
					$.messager.alert('错误','系统异常','error');							
				}
		 	});
     	} 
	}); 
	
		//点击保存
	$("#reset").click(function(){  

		$('#loginid').val('');
		$('#password').val('');
		$('#newpassword').val('');
		$('#renewpassword').val('');
	}); 
	
	//设置页面结构高度宽度
	$('#layout').height($(document).height()-30);
	$('#list-container,#form-container').height($(document).height()-40);
	
}); 
</script>
</head> 
<body> 
<div id="layout" class="easyui-panel easyui-layout" title="密码修改"> 
	<form id="operForm" class="dlg-form" novalidate="novalidate">
		<input type='hidden' id="centerid" name="centerid" value=""/>
		<table class="container">
			<col width="30%" /><col width="70%" />
			<tr>
				<td><label for="loginid">登录名：</label></td>
				<td><input type='text' id="loginid" name="loginid" required="true" class="easyui-validatebox"  maxlength=8 validType="loginidEqualTo['<%=user.getLoginid()%>']"/></td>
			</tr>
			<tr>
				<td><label for="password">旧密码：</label></td>
				<td><input type="password" id="password" name="password" required="true" class="easyui-validatebox" maxlength=16 /> </td>
			</tr>
			<tr>
				<td><label for="newpassword">新密码：</label></td>
				<td><input type="password" id="newpassword" name="newpassword" required="true" class="easyui-validatebox" maxlength=16 validType="length[6,16]" />
				<span>(注：由数字、字母、特殊符号组成，区分大小写)</span></td>
			</tr>
			<tr>
				<td><label for="renewpassword">确认新密码：</label></td>
				<td><input type="password" id="renewpassword" name="renewpassword" required="true" class="easyui-validatebox" maxlength=16 validType="equalTo['#newpassword']" /></td>
			</tr>
		</table>
		<div class="buttons">
			<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" id="btnsave">保存</a>
			<a class="easyui-linkbutton" iconCls="icon-clear" href="javascript:void(0)" id="reset">重置</a>
		</div>
	</form> 
</div> 
</body>
</html>