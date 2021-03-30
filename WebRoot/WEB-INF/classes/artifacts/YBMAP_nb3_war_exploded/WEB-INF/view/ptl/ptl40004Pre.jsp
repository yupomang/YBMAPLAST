<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ include file = "/include/init.jsp" %>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%@page import="net.sf.json.JSONArray"%> 
<%@page import="net.sf.json.JSONObject"%>
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
<title>角色权限分配</title>
<%@ include file = "/include/styles.jsp" %>
<style type="text/css">
/*<![CDATA[*/
body, html {width: 100%;height: 100%;overflow: hidden;margin:0;}
/*]]>*/
</style>
<%@ include file = "/include/scripts.jsp" %>
<script type="text/javascript">//<![CDATA[
$(function () {
	$("#confirm_btn").click(function(){
			
		var mi006arry = $('#form40004').serializeArray();
		//校验
		var tgbz = $("#form40004").form("validate");  
		if (tgbz){          
			$.ajax({	 
				type : "POST",
				url : '<%= _contexPath %>/ptl40004.do',
				dataType: "json",
				data:mi006arry,
				success :function(data) {					    
					
				}
			});
		} 
	});
});
function confirm() {
	//校验
	//if (!ydl.formValidate('form40004')) return;
	if (!$("#form40004").form("validate")) return;
	$('#form40004').submit();
}
//]]></script>
</head>
<body>
	<div class="easyui-panel" title="所属中心的选择">
		<form id="form40004" class="dlg-form" method="post" action="ptl40004.do" novalidate="novalidate">
			<table class="container">
				<col width="30%" /><col width="70%" />
				<tr>
					<th><label for="centeridLabel">中心编码：</label></th>
					<td>
						<select id="centerid" name="centerid" class="easyui-combobox" style="width:220px;" editable="false"><!-- panelHeight="auto" -->
							<option value=""></option>
							<%
								String options = "";
           						JSONArray ary=(JSONArray) request.getAttribute("centerList");
           						for(int i=0;i<ary.size();i++){
              						JSONObject obj=ary.getJSONObject(i);
              						String itemid=obj.getString("centerid");
              						String itemval=obj.getString("centername");
              						options = (new StringBuilder(String.valueOf(options))).append("<option value=\"").append(itemid).append("\">").append(itemval).append("</option>\n").toString();
           						}
           						out.println(options);
          					%>
						</select>
					</td>
				</tr>
			</table>
			<div class="buttons">
				<a href="javascript:void(0)" iconCls="icon-search" class="easyui-linkbutton" id="confirm_btn" onclick="confirm()">确定</a>
			</div>
		</form>
	</div>
</body>
</html>
