<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/init.jsp"%>
<%@page import="java.util.*"%>
<%@page import="com.yondervision.mi.dto.Mi001"%>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%
	UserContext user = UserContext.getInstance();
	if (user == null) {
		out.println("超时");
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="contexPath" content="<%=_contexPath%>" />
<title>微信关注信息功能配置</title>
<%@ include file="/include/styles.jsp"%>
<%@ include file="/include/scripts.jsp"%>
<%
	ArrayList<Mi001> list = (ArrayList<Mi001>)request.getAttribute("mi001list");
%>
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/foddermanager/css/weixinsubconfig.css" />
<script type="text/javascript">
var result;
var data;
$(function(){

	//编辑子项功能
	$("#b_updateson").click(function(){
		var childdata = data.children;
		var content = "";
		content = content + '<li><div style="background-color: none;border: none">序号</div><div>功能名称</div><div>功能键值</div></li>';
		for(var i = 0; i < childdata.length; i++){
			content = content + '<li><div>' + (i + 1) + '</div><div>' + childdata[i].name + '</div><div>' + childdata[i].key + '</div></li>';
		}
		$("#childedit").html(content);
		$("#maskLayer,#sontablechildedit").show();
	});
	//添加子项功能
	$("#b_addson").click(function(){
		$("#addorupdatechild").text("添加子项功能");
		$("#funcname").val("");
		$("#isflag").val("0");
		$("#functype").val("0");
		$("#funckey").val("");
		$("#maskLayer,#sontablechild").show();
	});
	//添加功能
	$('#b_add').click(function (){
		$("#addorupdate").text("添加功能");
		$("#funcname").val("");
		$("#isflag").val("0");
		$("#functype").val("0");
		$("#funckey").val("");
		$("#maskLayer,#sontable").show();
	});
	//编辑功能
	$('#b_update').click(function (){
		$("#addorupdate").text("修改功能");
		$("#funcname").val(data.name);
		$("#isflag").val(data.isflag);
		$("#functype").val(data.type);
		$("#funckey").val(data.key);
		$("#maskLayer,#sontable").show();
	});
	//删除功能
	$('#b_delete').click(function (){
		var name = data.name;
		var regionId = $("#regionId").val();
		$.ajax({
			url:"weixinapi00405.json",
			data:{
				regionId:regionId,
				funcname:name
			},
			type:'POST',
			dataType:'json',
			success:function(info){
				window.location.href = window.location.href;
			},
			error:function(){
			}
		});		
	});

	//关闭，取消
	$('#closechildedit,#b_cancelchildedit').click(function (){
		$("#maskLayer,#sontablechildedit").hide();
	});
	
	//关闭，取消
	$('#close,#b_cancel').click(function (){
		$("#maskLayer,#sontable").hide();
	});

	//关闭，取消
	$('#closechild,#b_cancelchild').click(function (){
		$("#maskLayer,#sontablechild").hide();
	});
	//点击变颜色
	$("#datalist").on('click','li',function(){
		var num = $(this).nextAll().length;
		var max=$("#datalist li").length;
		if(num == (max-1))
			return;
		$("#table li:not(':first')").css("background-color","#fff");
		$(this).css("background-color","#faa51a");
		var id = $(this).attr("id");
		data = result[id.split("_")[1]];
		var flag = data.isflag;
		if(flag == "0"){
			$("#b_addson").css('display','inline-block');
			$("#b_updateson").css('display','inline-block');
		}else{
			$("#b_addson").css('display','none'); 
			$("#b_updateson").css('display','none');
		}
		$("#b_update").css('display','inline-block'); 
		$("#b_delete").css('display','inline-block'); 
	});
	
	$("#regionId").change(function(){
		$("#datalist").empty();
		$("#funcarea").addClass("hide");
		getInfo();
	});
	
	$("#commitfunc").click(function(){
		var regionId = $("#regionId").val();
		if(regionId == "0"){
			alert("请选择中心");
			return false;
		}
		var funcname = $("#funcname").val();
		if(funcname.trim() == ""){
			alert("请输入功能名称");
			return false;
		}
		var isflag = $("#isflag").val();
		var functype = $("#functype").val();
		var funckey = $("#funckey").val();
		if(isflag == "1"){
			if(funckey.trim() == ""){
				alert("请输入功能键值");
				return false;
			}
		}
		$.ajax({
			url:"weixinapi00403.json",
			data:{
				regionId:regionId,
				funcname:funcname,
				isflag:isflag,
				functype:functype,
				funckey:funckey
			},
			type:'POST',
			dataType:'json',
			success:function(info){
				var recode = info.errcode;
				var msg = info.errmsg;
				if(recode != 0){
					alert(msg);
				}else{
					window.location.href = window.location.href;
				}
			},
			error:function(){
			}
		});		
	});

	$("#commitfuncchild").click(function(){
		var regionId = $("#regionId").val();
		var funcname = $("#funcnamechild").val();
		if(funcname.trim() == ""){
			alert("请输入功能名称");
			return false;
		}
		var functype = $("#functypechild").val();
		var funckey = $("#funckeychild").val();
		if(funckey.trim() == ""){
			alert("请输入功能键值");
			return false;
		}
		$.ajax({
			url:"weixinapi00406.json",
			data:{
				fathername:data.name,
				regionId:regionId,
				funcname:funcname,
				functype:functype,
				funckey:funckey
			},
			type:'POST',
			dataType:'json',
			success:function(info){
				var recode = info.errcode;
				var msg = info.errmsg;
				if(recode != 0){
					alert(msg);
				}else{
					window.location.href = window.location.href;
				}
			},
			error:function(){
			}
		});		
	});
	
	getInfo();
});
//查询所有中心关注信息功能配置   --------------------------------->最后做
function getInfo(){
	var regionId = $("#regionId").val();
	$.ajax({
		url:"weixinapi00404.json",
		data:{
			regionId:regionId
		},
		type:'POST',
		dataType:'json',
		success:function(info){
			var recode = info.errcode;
			var msg = info.errmsg;
			if(recode != 0){
				alert(msg);
				$("#funcarea").removeClass("hide");
			}else{
				result = eval('(' + info.rows + ')').result; 
				var content = "";
				content = content + '<li class="title"><div></div><div>功能名称</div><div>功能键值</div></li>';
				for(var i = 0; i < result.length; i++){
					if(result[i].isflag == "1"){
						content = content +  '<li id="li_' + i + '"><div>' + (i + 1) + '</div><div>' + result[i].name + '</div><div>' + result[i].key + '</div></li>';
					}else{
						content = content +  '<li id="li_' + i + '"><div>' + (i + 1) + '</div><div>' + result[i].name + '</div><div></div></li>';
					}
				}
				$("#datalist").html(content);
				$("#datalist").removeClass("hide");
				$("#funcarea").removeClass("hide");
			}
		},
		error:function(){
		}
	});
}
</script>
</head>
<body>
	<section id="table" class="table">
		<section id="selectarea">
			城市中心:
			<select id="regionId"  editable="false" panelHeight="auto">							
					<%for(int i = 0; i < list.size(); i++){ 
						if("00075500".equals(list.get(i).getCenterid())){%>
							<option value="<%=list.get(i).getCenterid() %>" selected="selected"> <%=list.get(i).getCentername() %></option>
						<%}else{%>
							<option value="<%=list.get(i).getCenterid() %>"> <%=list.get(i).getCentername() %></option>
						<%}%>
					<% } %>
			</select>
		</section>
		<ul class="hide" id="datalist">

		</ul>
	</section>
	<section class="funcarea hide" id="funcarea">
		<a id="b_add" class="button white" >添加</a>
		<a id="b_update" class="button white" style="display: none;">编辑</a>
		<a id="b_delete" class="button white" style="display: none;">删除</a>
		<a id="b_addson" class="button orange" style="display: none;">添加子项功能</a>
		<a id="b_updateson" class="button orange" style="display: none;">查看子项功能</a>
	</section>
	<section id="sontable" class="table sontabel">
		<div id="close" class="close">x</div>
		<div id="addorupdate"></div>
		<ul>
		<li><div>1</div><div>功能名称</div><div><input type="text" id="funcname"/></div></li>
		<li><div>2</div><div>是否有子项</div><div><select id="isflag"><option value="0">有子项</option><option value="1">无子项</option></select></div></li>
		<li><div>3</div><div>类型</div><div><select id="functype"><option value="view">链接事件</option><option value="click">点击事件</option><option value="auto">人工服务</option></select></div></li>
		<li><div>4</div><div>功能键值</div><div><input type="text" id="funckey"/></div></li>
		</ul>
		<a class="button white" id="commitfunc">保存</a>
		<a id="b_cancel" class="button white" >取消</a>
	</section>
	
	<section id="sontablechild" class="table sontabel">
		<div id="closechild" class="close">x</div>
		<div id="addorupdatechild"></div>
		<ul>
		<li><div>1</div><div>功能名称</div><div><input type="text" id="funcnamechild"/></div></li>
		<li><div>2</div><div>类型</div><div><select id="functypechild"><option value="view">链接事件</option><option value="click">点击事件</option><option value="auto">人工服务</option></select></div></li>
		<li><div>3</div><div>功能键值</div><div><input type="text" id="funckeychild"/></div></li>
		</ul>
		<a class="button white" id="commitfuncchild">保存</a>
		<a id="b_cancelchild" class="button white" >取消</a>
	</section>
	
	<section id="sontablechildedit" class="table sontabel">
		<div id="closechildedit" class="close">x</div>
		<div id="addorupdatechildedit">编辑子项功能</div>
		<ul id="childedit">
			
		</ul>
<%--		<a class="button white" id="commitfuncchildedit">保存</a>--%>
		<a id="b_cancelchildedit" class="button white" >取消</a>
	</section>
	
	<div onClick="closePop()" id="maskLayer" class="mask-layer"></div>
</body>
</html>
