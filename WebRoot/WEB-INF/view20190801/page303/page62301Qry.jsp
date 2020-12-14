<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.yondervision.mi.common.log.LoggerUtil"%>
<%
Logger log = LoggerUtil.getLogger();
log.debug("getArea==="+request.getAttribute("ary"));
   out.println(request.getAttribute("ary"));
%>
 