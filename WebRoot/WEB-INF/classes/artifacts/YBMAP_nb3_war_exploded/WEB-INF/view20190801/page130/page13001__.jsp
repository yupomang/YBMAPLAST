<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.yondervision.mi.result.WebApi13001Result"%>
<%
	ArrayList<WebApi13001Result> list = (ArrayList<WebApi13001Result>)request.getAttribute("groupList");
%>
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
<title>素材管理</title>
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/foddermanager/css/fodder.css" />
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/foddermanager/css/webuploader.css" />
<link rel="stylesheet" type="text/css" href="<%=_contexPath%>/scripts/foddermanager/css/diyUpload.css" />
<script type="text/javascript" src="<%=_contexPath%>/scripts/foddermanager/js/diyUpload.js"></script>
<script type="text/javascript" src="<%=_contexPath%>/scripts/foddermanager/js/page13001.js"></script>
<script type="text/javascript" src="<%=_contexPath%>/scripts/foddermanager/js/webuploader.js"></script>
<script type="text/javascript">
initid = <%=list.get(0).getGroupid()%>;
</script>
</head>
<body>
	<div id="mainbody">
		<div id="piccontentbody">
			<div id="changegroupname"><%=list.get(0).getGroupname() %></div>
			<div class="changegroupname hide" id="rename">重命名</div>
			<div class="hide" id="renamediv">
				<label class="styleli">编辑名称</label><br/>
				<input type="text" value="" id="renameinput"/><br/>
				<button class="buttonstyle1" id="updategroupname">确定</button>
				<button class="buttonstyle2" id="cancleupdate">取消</button>
			</div>
			<div class="changegroupname hide" id="deletegroup">删除分组</div>
			<div class="hide" id="deletediv">
				<div class="deteledivdetail" id="deteledivdetail">仅删除分组，不删除图片，组内图片将自动归入未分组</div><br/>
				<button class="buttonstyle1" id="completedeletegroup">确定</button>
				<button class="buttonstyle2" id="cancledelete">取消</button>
			</div>
			<div class="guize" id="guize">大小: 不超过1M,&nbsp;&nbsp;&nbsp;&nbsp;格式: jpg,jpeg,bmp,png</div>
			<div id="test" ></div>
			<div class="lanmu">
				<ul style="position: relative;">
					<li class="lanmuli"><input type="checkbox" id="checkall" name="chexklistall"/>全选</li>
					<li class="lanmuli"><button id="movepicgroup" class="lanmulidiv" disabled="disabled">移动分组</button></li>
					<li class="lanmuli"><button id="deletepic" class="lanmulidiv" disabled="disabled">删除</button></li>
					<li>	
						<div class="hide" id="movepichide">
						</div>
					</li>
					<li>	
						<div class="hide" id="deletepichide">
							<div class="deletepichide" id="deletepichideid">确定删除选中的素材吗</div><br/>
							<button class="buttonstyle1" id="deletepicall">确定</button>
							<button class="buttonstyle2" id="cancledeletepicall">取消</button>
						</div>
					</li>
				</ul>
			</div>
			<div class="picdiv" id="picdiv">
			</div>
			<div class="currentpage hide" id="allcurrentpage">
				<div class="currentpageleft hide" id="currentpageleft">
					<img src="<%=_contexPath%>/scripts/foddermanager/image/left.png" alt="" />
				</div>
				<div class="currentpagenum"><span id="currentpagenum"></span><span>/</span><span id="allpagenum"></span></div>				
				<div class="currentpageleft" id="currentpageright">
					<img src="<%=_contexPath%>/scripts/foddermanager/image/right.png" alt="" />
				</div>
				<div class="systempagenum"><input type="text" id="systempagenum" /></div>
				<div class="gotonewpage" id="gotonewpage">跳转</div>
			</div>
		</div>
		<div id="groupbody">
			<%for(int i = 0; i < list.size(); i ++){ %>
				<%if( i == 0){%>
					<p class="stylep stylepchange" id="<%=list.get(i).getGroupid() %>" onclick="selectPicCountByGroup(<%=list.get(i).getGroupid() %>)"><%=list.get(i).getGroupname()%>(<%=list.get(i).getJl() %>)</p>
				<%}else{ %>
					<p class="stylep" id="<%=list.get(i).getGroupid() %>" onclick="selectPicCountByGroup(<%=list.get(i).getGroupid() %>)"><%=list.get(i).getGroupname()%>(<%=list.get(i).getJl() %>)</p>
				<%}%>	
			<%}%>
			<p id="newgroup" class="stylep" style="position: absolute;">新建分组</p>
			<div class="hide" id="newgroupdiv">
				<label class="styleli">创建分组</label><br/>
				<input type="text" value="" id="groupname"/><br/>
				<button class="buttonstyle1" id="creategroup">确定</button>
				<button class="buttonstyle2" id="cancleCG">取消</button>
			</div>
		</div>
	</div>
	<div id="editpicnamediv" class="hide">
		<label class="styleli">编辑名称</label><br/>
		<input type="text" value="" id="renamepicname"/><br/>
		<button class="buttonstyle1" id="updatepicname">确定</button>
		<button class="buttonstyle2" id="cancleupdatepicname">取消</button>
	</div>
	<div class="hide" id="movepicbyone">
	</div>
	<div class="hide" id="deletepicbyone">
		<div class="deletepichide" id="">确定删除此素材吗</div><br/>
		<button class="buttonstyle1" id="commitdeletepicbyone">确定</button>
		<button class="buttonstyle2" id="concledeletepicbyone">取消</button>
	</div>
	<div class="hide" id="altpositon"></div>
	<input type="hidden"  id="dataTid"/>
</body>
</html>