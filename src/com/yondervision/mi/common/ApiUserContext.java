package com.yondervision.mi.common;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.filter.PermissionEncodingFilter;
import com.yondervision.mi.form.AppApi40102Form;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.security.AES;
import com.yondervision.mi.util.security.RSASignature;

/**
 * @ClassName: ApiUserContext
 * @Description: aip用户对象
 * @author 韩占远
 * @date 2013-10-29
 * 
 */
public class ApiUserContext implements  java.io.Serializable{
	public final static String APIUSERCONTEXT = "_APIUSERCONTEXT_";
	// 中心ID
	private String centerId = "";

	// 用户ID
	private String userId = "";

	// 公积金账户
	private String surplusAccount = "";

	// 设备区分
	private String deviceType = "";

	// 设备识别码
	private String deviceToken = "";

	public String getCenterId() {
		return centerId;
	}

	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSurplusAccount() {
		return surplusAccount;
	}

	public void setSurplusAccount(String surplusAccount) {
		this.surplusAccount = surplusAccount;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public static ApiUserContext saveToSession() {
		ApiUserContext user = new ApiUserContext();
		HttpServletRequest request = (HttpServletRequest) PermissionEncodingFilter.threadRequestLocal
				.get();		
		request.getSession().setAttribute(APIUSERCONTEXT, user);
		user.setCenterId(request.getParameter("centerId"));
		user.setUserId(request.getParameter("userId"));
		user.setSurplusAccount(request.getParameter("surplusAccount"));
		user.setDeviceType(request.getParameter("deviceType"));
		user.setDeviceToken(request.getParameter("deviceToken"));
		System.out.println("APP用户会话状态:"+user);
		return user;
	}
	
	public static ApiUserContext saveToSession(String userId,String surplusAccount) {
		ApiUserContext user = new ApiUserContext();
		HttpServletRequest request = (HttpServletRequest) PermissionEncodingFilter.threadRequestLocal
				.get();		
		request.getSession().setAttribute(APIUSERCONTEXT, user);
		user.setCenterId(request.getParameter("centerId"));
		user.setUserId(request.getParameter("userId"));
		user.setSurplusAccount(surplusAccount);
		user.setDeviceType(request.getParameter("deviceType"));
		user.setDeviceToken(request.getParameter("deviceToken"));
		System.out.println("APP用户会话状态:"+user);
		return user;
	}

	public static ApiUserContext getInstance() {
		HttpServletRequest request = (HttpServletRequest) PermissionEncodingFilter.threadRequestLocal
				.get();
//		ApiUserContext user=(ApiUserContext) request.getSession().getAttribute(
//				APIUSERCONTEXT);
		
		StringBuffer values = new StringBuffer();
		String paras = request.getHeader("headpara");
		if(paras==null)
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_HEAD_CHECK.getValue());
		String[] par = paras.split(",");
		System.out.println("检查变量:"+paras);
		for(int i=0;i<par.length;i++){			
			if(i!=0){
				values.append("&");
			}						
			values.append(par[i]+"="+request.getParameter(par[i]));
		}
		String headMD5 = request.getHeader("headparaMD5");
		System.out.println("请求head中参数MD5:"+headMD5);
		System.out.println("参数接收封装MD5前字符串 ："+values.toString());
		if(headMD5==null)
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_HEAD_CHECK.getValue());
		String paraMD5;
		try {
			paraMD5 = EncryptionByMD5.getMD5(values.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_HEAD_CHECK.getValue());
		}
//		if(request.getParameter("channel")==null){
//			if(user==null){
//				System.out.println("检查请求参channel为null");
//				throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_TIMEOUT.getValue());
//			}
//		}else{
//			if(!"20".equals(request.getParameter("channel"))){
//				if(user==null){
//					throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_TIMEOUT.getValue());
//				}else{
//					if(!user.getUserId().equals((String)request.getParameter("userId"))){
//						throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_MD5_CHECK.getValue());
//					}
//					String bindkeytype = PropertiesReader.getProperty("properties.properties", "bindkeytype"+((String)request.getParameter("centerId")));
//					String bindkey = "";
//					if("00".equals(bindkeytype)){
//						String appFilterServer = PropertiesReader.getProperty("properties.properties", "appFilterServer");
//						bindkey = (String)request.getParameter("surplusAccount");						
//						if(appFilterServer.indexOf(request.getServletPath())>=0){
//							if(!"QTdy6OkoL7berGC0MQocxg==".equals(bindkey)){
//								if(!user.getSurplusAccount().trim().equals(bindkey)){
//									throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_MD5_CHECK.getValue());
//								}
//							}
//						}					
//					}
//				}
//			}
//		}
		
		if(RSASignature.doCheck(paraMD5, headMD5, RSASignature.RSA_ALIPAY_PUBLIC)){            
            System.out.println("APP服务==============================APP签名验证成功");
        }else{
        	System.out.println("APP服务==============================APP签名验证失败");
        	throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_MD5_CHECK.getValue());
        }
//		return user;
		return null;
	}
	
	public static void removeInstance() {
		HttpServletRequest request = (HttpServletRequest) PermissionEncodingFilter.threadRequestLocal
				.get();
		 request.getSession().removeAttribute(APIUSERCONTEXT);
		 request.getSession().invalidate();
	}
	
//	public static void main(String[] args) throws Exception
//	{  
//		String values="dddddddddddddddddd";
//		
//		String paraMD5 = EncryptionByMD5.getMD5(values.getBytes("UTF-8"));
//		String headMD5=RSASignature.sign(paraMD5, RSASignature.RSA_PRIVATE);
//		System.out.println(headMD5);
//		System.out.println(RSASignature.doCheck(paraMD5, headMD5, RSASignature.RSA_ALIPAY_PUBLIC));
//		 
//	} 

}
