/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.common.filter
 * 文件名：     MiHttpRequestWrapper.java
 * 创建日期：2013-10-15
 */
package com.yondervision.mi.common.filter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.yondervision.mi.util.CommonUtil;

/**
 * @author LinXiaolong
 *
 */
@SuppressWarnings("unchecked")
public class MiHttpRequestWrapper extends HttpServletRequestWrapper {

	
	private Map<String, Object> params = new HashMap(); 
	
	public MiHttpRequestWrapper(HttpServletRequest request, Map newParams) {
		super(request);
		params.putAll(request.getParameterMap()) ;
		for (Iterator iterator = newParams.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if (CommonUtil.isEmpty(params.get(key))) {
				params.put(key, newParams.get(key));
			}
		}
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
