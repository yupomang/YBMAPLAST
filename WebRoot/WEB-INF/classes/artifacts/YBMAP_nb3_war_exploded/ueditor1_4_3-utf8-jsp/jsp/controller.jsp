<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page import="com.yondervision.mi.common.UserContext"%>
<%
  UserContext user=UserContext.getInstance();

  if(user==null)  {
     out.println("超时");
     return;
  }
%>

<%		
    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	request.setAttribute("ueditorYcmapType",session.getAttribute("beiyong"));
	System.out.println("ueditorYcmapType："+request.getAttribute("ueditorYcmapType"));
	request.setAttribute("centerId",user.getCenterid());
	String rootPath = application.getRealPath( "/" );	
	out.write( new ActionEnter( request, rootPath ).exec() );
	//out.write( new ActionEnter( request, "C:\\logs" ).exec() );
%>