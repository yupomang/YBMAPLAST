package com.yondervision.mi.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.LOG;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.security.Base64Encoder;
import com.yondervision.mi.util.security.KeySignature;
import com.yondervision.mi.util.security.RSASignature;
//个人账户查询（用于反显）
public class HttpSend5007appapi0010411111111111111 {
	public static final String POST_URL = "http://10.33.11.35:7001/YFMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	public static String httpURLConnectionPOST5007(String certinum,String accnum) {
		try {
			URL url = new URL(POST_URL + "appapi00104.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel,userId,usertype,deviceType,deviceToken,currenVersion,buzType,appid,appkey,appToken,clientMAC,brcCode,tranDate,channelSeq,certinum,accnum");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			 String userId=aes.encrypt("330204197508036114".getBytes("UTF-8"));
				String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
				String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
				certinum = aes.encrypt(certinum.getBytes("UTF-8"));

			 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5007&appid="
						+ appId+ "&appkey="+ appKey+ "&appToken=&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq=&certinum="+certinum+"&accnum="+accnum;
			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40&userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5007&appid="
						+ appId+ "&appkey="+ appKey+ "&appToken=&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq=&certinum="+certinum+"&accnum="+accnum;			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&centerId=00057400&channel=40&userId="+ userId.replace("+", "%2B")+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5007&appid="
					+ appId.replace("+", "%2B")+ "&appkey="+ appKey.replace("+", "%2B")+  "&appToken=&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq=&certinum="+certinum+"&accnum="+accnum;
			System.out.println("本地参数" + parm);
			System.out.println("传递参数：" + parm1);
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
			byte[] data = RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE).getBytes();		
			String keysig = Base64Encoder.encode(KeySignature.keySign(data));		
			connection.addRequestProperty("KeySignature", keysig.replaceAll("\n", ""));
			//参数设置
/*			NameValuePair[] postData = new NameValuePair[17];
			postData[1] = new NameValuePair("userId","330204197508036114");
			postData[2] = new NameValuePair("usertype","10");
			postData[3] = new NameValuePair("deviceType","3");
			postData[4] = new NameValuePair("deviceToken","");
			postData[5] = new NameValuePair("currenVersion","1.0");
			postData[6] = new NameValuePair("buzType","5007");
			postData[7] = new NameValuePair("appid","yondervisionwebservice40");
			postData[8] = new NameValuePair("appkey","b5b1c6938e5d0cef72457bd788ffdef0");
			postData[9] = new NameValuePair("appToken","");
			postData[10] = new NameValuePair("clientIp","172.10.0.1");
			postData[11] = new NameValuePair("clientMAC","E8:39:35:A7:AC:58");
			postData[12] = new NameValuePair("brcCode","05740008");
			postData[13] = new NameValuePair("tranDate","2017-09-19");
			postData[14] = new NameValuePair("channelSeq","");
			postData[15] = new NameValuePair("certinum","330204197508036114");
			postData[16] = new NameValuePair("accnum","");
			connection.addRequestProperty("postData", postData.toString());*/
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			dataout.writeBytes(parm1);
			dataout.flush();
			dataout.close();
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = bf.readLine()) != null) {
				sb.append(line).append(System.getProperty("line.separator"));
			} 
			bf.close();
			connection.disconnect(); 
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnknownHostException, TransRuntimeErrorException, UnsupportedEncodingException {
		httpURLConnectionPOST5007("330204197508036114","");	
	}
	
	private String send(HttpServletRequest request, HttpServletResponse response, String url)throws Exception{
		Logger log = LoggerUtil.getLogger();
		Map propsMap = request.getParameterMap();		
		System.out.println("requestUrl:"+url);
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
			
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			
			String appFilterServer = PropertiesReader.getProperty("properties.properties", "responseFilter");
			if (appFilterServer.indexOf(request.getServletPath())<0){
				String en = getEncoding(repMsg);
				response.getOutputStream().write(repMsg.getBytes(encoding));
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
