package com.yondervision.mi.common.filter;

import java.io.IOException; 
import javax.servlet.Filter; 
import javax.servlet.FilterChain; 
import javax.servlet.FilterConfig; 
import javax.servlet.ServletException; 
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

public class IPFilter implements Filter {
	 
	/**
	 * 校验访问Ip是否有访问权限
	 * @param ipAddr
	 * @param uri
	 * @return
	 */
    public boolean isIPDenied(String ipAddr, String uri){
        
    	String filterIP = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "filter_ip");
    	if (CommonUtil.isEmpty(filterIP)) {
    		return true;
    	}
    	
    	String[] filterIPs = filterIP.split(",");
    	
    	for(int i = 0; i <= filterIPs.length - 1; i++) {
    		if(filterIPs[i].equals(ipAddr)){
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * 销毁
     */
    public void destroy() {
    	
    }
    
    /**
     * 过滤器方法
     */
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) req;
        
        String clientIP = request.getRemoteHost();
        String clientURI = request.getRequestURI();
        
        // TODO 打印日志
        System.out.println("访问服务的IP：" + clientIP);
        System.out.println("访问服务的url：" + clientURI);
        System.out.println("开始过滤...");
        
        if(isIPDenied(clientIP, clientURI)){
            // TODO 打印日志
            System.out.println("你没有权限访问此服务！");
            //response.sendRedirect("error.jsp");
            throw new ServletException("你没有权限访问此服务！");
        }else{
            chain.doFilter(req, res);
        }
    }

    /**
     * 初始化
     */
    public void init(FilterConfig arg0) throws ServletException {

    }

}
