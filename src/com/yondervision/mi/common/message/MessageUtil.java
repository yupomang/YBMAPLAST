package com.yondervision.mi.common.message;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi011DAO;
import com.yondervision.mi.dto.Mi011;
import com.yondervision.mi.dto.Mi011Example;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.security.Base64Encoder;
import com.yondervision.mi.util.security.KeySignature;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 移动互联应用服务平台同公积金中心通信实现类
 * @author gongqi
 *
 */
public class MessageUtil implements APPMessageI{
	
	/**
	 * 移动互联应用服务平台同公积金中心通信
	 */
	@SuppressWarnings("unchecked")
	public String send(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Logger log = LoggerUtil.getLogger();
		String req = "";
		try {
			// 1. 根据中心id，获取目标静态URL
			String centerid = request.getAttribute("centerId").toString();

			Mi011Example example = new Mi011Example();
			Mi011DAO mi011Dao = (Mi011DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi011Dao");
			
			example.createCriteria().andCenteridEqualTo(centerid);
			List<Mi011> resultList = mi011Dao.selectByExample(example);
		
			if (CommonUtil.isEmpty(resultList)) {
				throw new TransRuntimeErrorException(ERROR.CONNECT_ERROR.getValue(),"获取通讯URL失败，通讯表中无记录");
			}else{
				if (CommonUtil.isEmpty(resultList.get(0).getUrl())) {
					throw new TransRuntimeErrorException(ERROR.CONNECT_ERROR.getValue(),"获取通讯URL失败，通讯表中url为空");
				}
			}
	
			String httpURL = resultList.get(0).getUrl();
			if (httpURL.endsWith("/")) {
				httpURL = httpURL.substring(0, httpURL.length()-1);
			}
			httpURL = httpURL + request.getServletPath();
			
			long starTime=System.currentTimeMillis();
			if(request.getServletPath().startsWith("/webapi500")||request.getServletPath().startsWith("/webapi80001")
					||request.getServletPath().startsWith("/webapi80002")||request.getServletPath().startsWith("/webapi80003")
					||request.getServletPath().startsWith("/webapi80004")||request.getServletPath().startsWith("/webapi80005")
					||request.getServletPath().startsWith("/webapi80006")||request.getServletPath().startsWith("/webapi80007")
					||request.getServletPath().startsWith("/webapi80008")||request.getServletPath().startsWith("/appapi00228")
					||request.getServletPath().startsWith("/appapi00235")||request.getServletPath().startsWith("/appapi00240")
					||request.getServletPath().startsWith("/appapi00241")||request.getServletPath().startsWith("/appapi00242")
					||request.getServletPath().startsWith("/appapi00243")||request.getServletPath().startsWith("/appapi00244")
					||request.getServletPath().startsWith("/appapi00245")||request.getServletPath().startsWith("/webapi80009")
					||request.getServletPath().startsWith("/webapi80010")){
				req = sendTY(request, response, httpURL);
			}else if(request.getServletPath().startsWith("/appapi04101") ){
				req = sendYiBu(request, response, httpURL);
			}else{
				req = send(request, response, httpURL);
			}
			long endTime=System.currentTimeMillis();
			long Time=endTime-starTime;
			System.out.println("YF耗时"+Time+"毫秒");
			System.out.println("YF MessageUtil  cost "+Time+"milliseconds");
		}catch (TransRuntimeErrorException e) {
			throw e;
		}catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					ERROR.SYS.getValue(), "未知错误，请查看日志或者联系管理员。");
			log.error(e.getMessage(), e);
			throw tre;
		}
		return req;
	}
	
	/**
	 * 通信转发
	 */
	@SuppressWarnings("unchecked")
	private String send(HttpServletRequest request, HttpServletResponse response, String url)throws Exception{
		Logger log = LoggerUtil.getLogger();
		Map propsMap = request.getParameterMap();
		
		System.out.println("****************requestUrl********start**************");
		System.out.println("requestUrl:"+url);
		System.out.println("****************requestUrl********end**************");
		PostMethod mypost = new PostMethod(url);
		mypost.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		//头部请求信息
		mypost.setRequestHeader("headpara", request.getHeader("headpara"));
		mypost.setRequestHeader("headparaMD5", request.getHeader("headparaMD5"));
		byte[] data = request.getHeader("headparaMD5").getBytes();		
		String keysig = Base64Encoder.encode(KeySignature.keySign(data));
		
		mypost.setRequestHeader("KeySignature", keysig.replaceAll("\n", ""));
		//参数设置
		Set<String> keySet = propsMap.keySet();
		NameValuePair[] postData = new NameValuePair[keySet.size()];
		int index=0;
		for(String key:keySet){
			postData[index++] = new NameValuePair(key,request.getParameter(key));
		}
		mypost.addParameters(postData);
	
		if(request.getAttribute("DL_LOG_SEQ_NO")!=null){
			NameValuePair[] postData1= new NameValuePair[1];
			postData1[0] = new NameValuePair("logid",String.valueOf(request.getAttribute("DL_LOG_SEQ_NO")));
			mypost.addParameters(postData1);
		}
		

		HttpClient httpClient = new HttpClient();		
		String connectTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_connection_time").trim();
		String readTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_read_time").trim();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(Integer.valueOf(connectTimeout));
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(Integer.valueOf(readTimeout));
		String req = "";

		try {
			int re_code = httpClient.executeMethod(mypost);
			String repMsg = null;
			if (re_code == 200) {
				repMsg = mypost.getResponseBodyAsString();
				log.info(LOG.REV_INFO.getLogText(repMsg));
//				System.out.println("&&&&&&&&&&&&&&&&&");
//				System.out.println("response-encoding:"+mypost.getResponseCharSet());
//				System.out.println("repMsg:"+repMsg);
//				System.out.println("repMsg-byte"+mypost.getResponseBody());
//				System.out.println("repMsg---byte"+repMsg.getBytes(request.getCharacterEncoding()));
//				System.out.println("repMsg---string"+new String(repMsg.getBytes(request.getCharacterEncoding()),request.getCharacterEncoding()));
//				System.out.println("&&&&&&&&&&&&&&&&&");
				
			} else {
				throw new TransRuntimeErrorException(ERROR.CONNECT_SEND_ERROR.getValue(),"http错误码" + re_code);
			}
			
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			
			String appFilterServer = PropertiesReader.getProperty("properties.properties", "responseFilter");
			
			System.out.println("request.getServletPath()========="+request.getServletPath());
			
			if (appFilterServer.indexOf(request.getServletPath())<0){
				String en = getEncoding(repMsg);
				response.setContentType("application/json; charset=utf-8");
				response.getOutputStream().write(repMsg.getBytes(encoding));
			}
			
//			response.getOutputStream().write(repMsg.getBytes(encoding));
			req = repMsg;
		} catch (TransRuntimeErrorException e) {
			throw e;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					ERROR.SYS.getValue(), "请检查通信相关参数是否正确或连接通道是否畅通。");
			log.error(e.getMessage(), e);
			throw tre;
		}finally{
			mypost.releaseConnection();
		}
		return req;
	}
	
	
	
	/**
	 * 通信转发,异步交易，适用基数调整
	 */
	@SuppressWarnings("unchecked")
	private String sendYiBu(HttpServletRequest request, HttpServletResponse response, String url)throws Exception{
		Logger log = LoggerUtil.getLogger();
		Map propsMap = request.getParameterMap();
		
		System.out.println("****************requestUrl********start**************");
		System.out.println("requestUrl:"+url);
		System.out.println("****************requestUrl********end**************");
		PostMethod mypost = new PostMethod(url);
		mypost.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		//头部请求信息
		mypost.setRequestHeader("headpara", request.getHeader("headpara"));
		mypost.setRequestHeader("headparaMD5", request.getHeader("headparaMD5"));
		byte[] data = request.getHeader("headparaMD5").getBytes();		
		String keysig = Base64Encoder.encode(KeySignature.keySign(data));
		
		mypost.setRequestHeader("KeySignature", keysig.replaceAll("\n", ""));
		//参数设置
		Set<String> keySet = propsMap.keySet();
		NameValuePair[] postData = new NameValuePair[keySet.size()];
		int index=0;
		for(String key:keySet){
			postData[index++] = new NameValuePair(key,request.getParameter(key));
		}
		mypost.addParameters(postData);
	
		if(request.getAttribute("DL_LOG_SEQ_NO")!=null){
			NameValuePair[] postData1= new NameValuePair[1];
			postData1[0] = new NameValuePair("logid",String.valueOf(request.getAttribute("DL_LOG_SEQ_NO")));
			mypost.addParameters(postData1);
		}
		

		HttpClient httpClient = new HttpClient();		
		String connectTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_connection_time").trim();
		String readTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_read_time").trim();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(Integer.valueOf(connectTimeout));
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(Integer.valueOf(readTimeout));
		String req = "";

		try {
			int re_code = httpClient.executeMethod(mypost);
			String repMsg = null;
			if (re_code == 200) {
				repMsg = mypost.getResponseBodyAsString();
				log.info(LOG.REV_INFO.getLogText(repMsg));		
			} else {
				throw new TransRuntimeErrorException(ERROR.CONNECT_SEND_ERROR.getValue(),"http错误码" + re_code);
			}
		} catch (TransRuntimeErrorException e) {
			throw e;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					ERROR.SYS.getValue(), "请检查通信相关参数是否正确或连接通道是否畅通。");
			log.error(e.getMessage(), e);
			throw tre;
		}finally{
			mypost.releaseConnection();
		}
		return req;
	}
	
	/**
	 * 通信转发
	 */
	@SuppressWarnings("unchecked")
	private String sendTY(HttpServletRequest request, HttpServletResponse response, String url)throws Exception{
		Logger log = LoggerUtil.getLogger();
		Map propsMap = request.getParameterMap();
		System.out.println("****************requestUrl********start**************");
		System.out.println("propsMap.toString()"+propsMap.toString());
		System.out.println("requestUrl:"+url);
		System.out.println("****************requestUrl********end**************");
		PostMethod mypost = new PostMethod(url);
		mypost.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		Set<String> keySet = propsMap.keySet();
		NameValuePair[] postData = new NameValuePair[keySet.size()];
		int index=0;
		for(String key:keySet){
			postData[index++] = new NameValuePair(key,request.getParameter(key));
		}
		mypost.addParameters(postData);
		if(request.getAttribute("DL_LOG_SEQ_NO")!=null){
			NameValuePair[] postData1= new NameValuePair[1];
			postData1[0] = new NameValuePair("logid",String.valueOf(request.getAttribute("DL_LOG_SEQ_NO")));
			mypost.addParameters(postData1);
		}
		HttpClient httpClient = new HttpClient();		
		String connectTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_connection_time").trim();
		String readTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_read_time").trim();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(Integer.valueOf(connectTimeout));
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(Integer.valueOf(readTimeout));
		String req = "";
		try {
			int re_code = httpClient.executeMethod(mypost);
			String repMsg = null;
			if (re_code == 200) {
				repMsg = mypost.getResponseBodyAsString();
				log.info(LOG.REV_INFO.getLogText(repMsg));
			} else {
				throw new TransRuntimeErrorException(ERROR.CONNECT_SEND_ERROR.getValue(),"http错误码" + re_code);
			}
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			req = repMsg;
		} catch (TransRuntimeErrorException e) {
			throw e;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					ERROR.SYS.getValue(), "请检查通信相关参数是否正确或连接通道是否畅通。");
			log.error(e.getMessage(), e);
			throw tre;
		}finally{
			mypost.releaseConnection();
		}
		return req;
	}
	
	
	
	public static String getEncoding(String str) {      
      String encode = "GB2312";      
      try {      
          if (str.equals(new String(str.getBytes(encode), encode))) {      
               String s = encode;      
              return s;      
           }
       } catch (Exception exception) {      
       }      
       encode = "ISO-8859-1";      
      try {      
          if (str.equals(new String(str.getBytes(encode), encode))) {      
               String s1 = encode;      
              return s1;      
           }      
       } catch (Exception exception1) {      
       }      
       encode = "UTF-8";      
      try {      
          if (str.equals(new String(str.getBytes(encode), encode))) {      
               String s2 = encode;      
              return s2;      
           }      
       } catch (Exception exception2) {      
       }      
       encode = "GBK";      
      try {      
          if (str.equals(new String(str.getBytes(encode), encode))) {      
               String s3 = encode;      
              return s3;      
           }      
       } catch (Exception exception3) {      
       }      
      return "";      
   } 

	
}
