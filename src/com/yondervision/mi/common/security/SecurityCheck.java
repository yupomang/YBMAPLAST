/**
 * 文件名：SecurityCheck.java
 * 功能：安全检查过滤器
 * */
package com.yondervision.mi.common.security;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class SecurityCheck {
	
	
	public HashMap<String,String> securityService(HttpServletRequest req ,HttpServletResponse rep ,FilterConfig filterConfig) throws NoRollRuntimeErrorException{
		HashMap<String,String> hm = new HashMap<String, String>();
		//动态加载校验规则配置文件
		String isAutoLoadSecurityFilter = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "isAutoLoadSecurityFilter");
		/*
		* isAutoLoadSecurityFilter=1 时，每次动态加载，生产环境下不应使用动态加载，测试可酌情考虑是否动态加载处理。
		* */
		if("1".equals(isAutoLoadSecurityFilter)){
			SecurityCheckManager.reload();
		}

		if (SecurityCheckManager.IS_CHECK) {
			req.setAttribute("YDCHECKURL", req.getServletPath());
			if (SecurityCheckManager.IS_CHECK_HEADER) {
				java.util.Enumeration<String> en = req.getHeaderNames();
				StringBuffer sb = new StringBuffer();
				while (en.hasMoreElements()) {
					String key = en.nextElement();
					if (!SecurityCheckManager.HEADERWHITE.contains(key))
						sb.append(req.getHeader(key) + "|@|");
				}
				if (SecurityCheckManager.matches(sb.toString())) {
					SecurityCheckLog
							.writeLog(getIP(req) + "  " + sb.toString());
					hm.put("recode", "999996");
					hm.put("msg", "链接HEADER不符合规则！");
					return hm;
				}
			}
			
			if (SecurityCheckManager.IS_CHECK_PARAMETER) {
				java.util.Enumeration<String> en = req.getParameterNames();
				StringBuffer sb = new StringBuffer();
				while (en.hasMoreElements()) {
					String key = en.nextElement();
					if (!SecurityCheckManager.PARAMETERWHITE.contains(key)) {
						//内容管理富文本
						if (req.getServletPath().indexOf("webapi70001.json") != -1 &&
								("content".equals(key) || "image".equals(key))) {
							continue;
						//消息群发富文本
						}else if(req.getServletPath().indexOf("webapi30201.json") != -1 &&
								"tsmsg".equals(key)){
							continue;
						//内容管理修改
						}else if(req.getServletPath().indexOf("webapi70003.json") != -1 &&
								("content".equals(key) || "image".equals(key))){
							continue;
						}
						sb.append(req.getParameter(key) + "|@|");
					}
				}
				if (SecurityCheckManager.matches(sb.toString())) {
					SecurityCheckLog.writeLog(getIP(req) + "  " + sb.toString());
					SecurityCheckLog.writeLog(getIP(req) + " 不符合规则的字符串是：" + SecurityCheckManager.getMatchStr(sb.toString()));
					hm.put("recode", "999997");
					hm.put("msg", "链接上传参数不符合规则！");
					return hm;
				}
			}
			
			// 针对host篡改，增加判断方法
			if (SecurityCheckManager.IS_CHECK_HOST) {
			String hostValue = req.getHeader("host");
			if (!SecurityCheckManager.HOSTWHITE.contains(hostValue)) {
				SecurityCheckLog.writeLog(getIP(req) + "  " + hostValue);
				hm.put("recode", "999998");
				hm.put("msg", "链接HOST校验不符合规则！");
				return hm;
			}
			}
			
			// 针对referer的跨站点请求伪造，增加判断方法  2015-10-23 by LXH  begin
			if (SecurityCheckManager.IS_CHECK_REFERER) {
				String refererValue = req.getHeader("referer");
				System.out.println("refererValue==========================================" + refererValue);
				if(CommonUtil.isEmpty(refererValue))
				{
					System.out.println("refererValue不存在,IE录入url发请求，不校验==========================================" + refererValue);
				}
				else
				{
					if(refererValue.substring(0,5).equals("https"))
					{
						refererValue = refererValue.substring(8);
						refererValue = refererValue.substring(0,refererValue.indexOf('/'));
					}
					else if(refererValue.substring(0,5).equals("http:"))
					{
						refererValue = refererValue.substring(7);
						refererValue = refererValue.substring(0,refererValue.indexOf('/'));
					}
					System.out.println("refererValue.substring=====================" + refererValue);
					if (!SecurityCheckManager.REFERERWHITE.contains(refererValue)) {
						SecurityCheckLog.writeLog(getIP(req) + "  " + refererValue);
						hm.put("recode", "999999");
						hm.put("msg", "链接referer校验不符合规则！");
						return hm;
					}
				}
			}
		}
		hm.put("recode", "000000");
		hm.put("msg", "成功！");
		return hm;
	}
	
	private String getIP(HttpServletRequest req) {

		String ip = req.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}
}
