<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="net.sf.json.JSONObject" %>

<% 
	Enumeration enumNames = request.getAttributeNames();
	JSONObject jo = new JSONObject();
	while (enumNames.hasMoreElements()) {
		String key = (String) enumNames.nextElement();
		if( request.getAttribute(key) instanceof String ) {
			String value = (String) request.getAttribute(key);
			jo.put(key, value);
		}
	}
	out.print(jo.toString());
%>

