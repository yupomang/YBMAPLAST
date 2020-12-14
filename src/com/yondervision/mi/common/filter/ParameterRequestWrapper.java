package com.yondervision.mi.common.filter;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/** 
* @ClassName: ParameterRequestWrapper 
* @Description: 请求参数特殊处理
* @author Caozhongyan
* @date Nov 25, 2013 4:53:27 PM   
* 
*/ 
public class ParameterRequestWrapper extends HttpServletRequestWrapper {
	private Map params;

	public ParameterRequestWrapper(HttpServletRequest request,Map newParams)  {  //构造方法
		super(request);
		this.params=newParams;
	}
	public Map getParameterMap() {
		return params;
	}
	public Enumeration getParameterNames() {
		Vector l=new Vector(params.keySet());
		return l.elements();
	}
	public String[] getParameterValues(String name) {
		  Object v = params.get(name);
		  if(v==null){
		    return null;
		  }else if(v instanceof String[]){
		    return (String[]) v;
		  }else if(v instanceof String){
		    return new String[]{(String) v};
		  }else{
		    return new String[]{v.toString()};
		  }
	}
	public String getParameter(String name) {
		  Object v = params.get(name);
		  if(v==null){
		    return null;
		  }else if(v instanceof String[]){            
		    String []strArr=(String[]) v;
		    if(strArr.length>0){
		      return strArr[0];
		    }else{
		      return null;
		    }
		  }else if(v instanceof String){
		    return (String) v;
		  }else{
		    return v.toString();
		  }
	}   
}
