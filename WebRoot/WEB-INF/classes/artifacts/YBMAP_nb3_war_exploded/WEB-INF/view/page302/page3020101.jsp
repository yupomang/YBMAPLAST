<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="com.yondervision.mi.dto.Mi404"%>
<%@ page import="java.util.List" %>
<%@ include file = "/include/init.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file = "/include/styles.jsp" %>
<%@ include file = "/include/scripts.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="contexPath" content="<%= _contexPath %>" />
<title>公共短消息推送</title>
</head>
<body>
<script type="text/javascript">
$(function(){
	$('#d_dlgImg').dialog({   
		title: '图片预览',   
		width: 800,   
		height: 500,   
		closed: true,   
		cache: false,   
		modal: true  
	}); 
})
function showImg(url){
	$('#img').attr("src", url);	
	$('#d_dlgImg').dialog('open');
	ydl.setDlgPosition('d_dlgImg');//对话框高度超出页面高度时，设置top为0px
}
</script>
<%if("01".equals(request.getAttribute("tsmsgtype"))){ %>
<div class="easyui-layout" data-options="fit:true" style="width:900px;height:180px;">	
	<div id="detailContent" data-options="region:'west',split:true" title="标题信息">
		<ul>			
			<li>
				<label>消息标题：</label>
				<%out.println(request.getAttribute("title").toString()); %>
			</li>
			<li>
				<label>客服电话：</label>
				<%=request.getAttribute("param1") %>
			</li>
			<li>
				<label>消息描述：</label>
				<%=request.getAttribute("detail") %>
			</li>
			<%
				if(!"".equals(request.getAttribute("theme"))){
				%>
					<li>
						<label>主题类型：</label>
						<%=request.getAttribute("theme") %>
					</li>
					
			<%
				}
			 %>
			
				<%
				List<String> listImg = (List<String>)request.getAttribute("listImg");
				if(listImg!=null){
				%>
				<li>
				<label>消息标题图片：</label>
				<%
					for(int i=0; i<listImg.size(); i++) {
						String img = listImg.get(i);
				 %>				
					<a href="javascript:void(0)" onclick='showImg("<%=img.replaceAll("\\\\","\\\\\\\\") %>")'>
						<img height="100" width="100" src='<%=img %>'/>
					</a>				
				<%
					}
				%>
				</li>
				<%
				} 
				%>
			
		</ul>
	</div>	
	<div id="tsmsgContent" data-options="region:'center',split:true" title="消息内容">			
		
		<%=request.getAttribute("tsmsg")%>
				
	</div>
</div>
<%}else if("02".equals(request.getAttribute("tsmsgtype"))) {%>
<div class="easyui-layout" data-options="fit:true" style="width:900px;height:180px;">	
	<div id="detailContent" data-options="region:'west',split:true" title="标题信息">
		<ul>			
			<li>
				<label>消息标题：</label>
				<%out.println(request.getAttribute("title").toString()); %>
			</li>
			<li>
				<label>客服电话：</label>
				<%=request.getAttribute("param1") %>
			</li>
			<%
				if(!"".equals(request.getAttribute("theme"))){
				%>
					<li>
						<label>主题类型：</label>
						<%=request.getAttribute("theme") %>
					</li>
			<%
				}
			 %>					
		</ul>
	</div>	
	<div id="tsmsgContent" data-options="region:'center',split:true" title="消息图片">
		<%
		List<String> listImg1 = (List<String>)request.getAttribute("listImg");
		if(listImg1!=null){
			for(int i=0; i<listImg1.size(); i++) {
				String img = listImg1.get(i);
		 %>				
			<a href="javascript:void(0)" onclick='showImg("<%=img.replaceAll("\\\\","\\\\\\\\") %>")'>
				<img  src='<%=img %>' style="width:300px;height:300px;"/><!-- height="100" width="100" -->
			</a>				
		<%
			}
		} 
		else {
		%>
		无
		<%} %>				
	</div>
</div>
<%}else if("03".equals(request.getAttribute("tsmsgtype"))){ 
	List<Mi404> list = (List)request.getAttribute("list");
	%>
	<div id="mslb" class="easyui-accordion" style="width:1100px;height:340px;">
	<%
	if(list.size()>0){
		for(int i=0;i<list.size();i++){
			Mi404 mi404 = (Mi404)list.get(i);
%>
			<div title="<%=mi404.getMstitle() %>" data-options="iconCls:'icon-search'" style="overflow:auto;padding:10px;">
				<div class="easyui-layout" data-options="fit:true" style="width:900px;height:180px;">	
					<div id="detailContent" data-options="region:'west',split:true" title="标题信息">
						<ul>			
							<li>
								<label>消息标题：</label>
								<%=mi404.getMstitle() %>
							</li>
							<li>
								<label>客服电话：</label>
								<%=mi404.getMsparam1() %>
							</li>
							
							<%
								if(!"".equals(mi404.getFreeuse1())){
								%>
									<li>
										<label>主题类型：</label>
										<%=mi404.getFreeuse1() %>
									</li>
									
							<%
								}
							 %>
							
														
							<li>
								<label>消息标题图片：</label>
								<%								
								if(!mi404.getMsparam2().equals("")){
									String[] listImg = mi404.getMsparam2().split(",");
									if(listImg.length>0){
										for(int j=0; j<listImg.length; j++) {
											String img = listImg[j];
											%>				
											<a href="javascript:void(0)" onclick='showImg("<%=img.replaceAll("\\\\","\\\\\\\\") %>")'>
												<img height="100" width="100" src='<%=img %>'/>
											</a>				
										<%
										}
									}
								} 
								else {
								%>
								无
								<%} %>
							</li>					
						</ul>
					</div>	
					<div id="tsmsgContent" data-options="region:'center',split:true" title="消息内容">
						<%=mi404.getMstsmess() %>
					</div>
				</div>
			</div>	
<%	
		}
	}
%>
	</div>
<%} %>
<div id="d_dlgImg">
	<img id="img" />
</div>
</body>
</html>