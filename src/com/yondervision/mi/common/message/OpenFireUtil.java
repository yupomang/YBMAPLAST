package com.yondervision.mi.common.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.form.AppApi40102Form;
import com.yondervision.mi.util.PropertiesReader;

/** 
* @ClassName: OpenFireUtil 
* @author Czy
* @date Jan 3, 2014 12:42:22 PM   
* 
*/ 
public class OpenFireUtil {
	public String toOpenFireServer(AppApi40102Form form) throws TransRuntimeErrorException{
		StringBuffer buffer = new StringBuffer();
		try {
			String urlString = PropertiesReader.getProperty(
					Constants.PROPERTIES_FILE_NAME, "openFireUrl");
			URL url = new URL(urlString.trim());
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();			
			System.out.println("用户注册或用户信息修改调用在线资询OPENFIRE服务通信接口:"+urlString);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);			
			String connectTimeout = PropertiesReader.getProperty(
					Constants.PROPERTIES_FILE_NAME, "openFireConnectTimeout").trim();
			String readTimeout = PropertiesReader.getProperty(
					Constants.PROPERTIES_FILE_NAME, "openFireopenFire").trim();
			httpUrlConn.setConnectTimeout(Integer.valueOf(connectTimeout));  
			httpUrlConn.setReadTimeout(Integer.valueOf(readTimeout));			 
			httpUrlConn.setRequestMethod("POST");
			String outputStr = "user_id="+form.getUserId()+"&user_name="+form.getFullName()+"&password="+form.getPassword()+"&provacct="+form.getSurplusAccount()+"&idnumber="+form.getIdcardNumber()+"&email="+form.getEmail()+"&tel="+form.getMobileNumber();
			System.out.println("调用在线资询OPENFIRE服务通信参数:"+outputStr);
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				 
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}			
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);			
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}			
			bufferedReader.close();
			inputStreamReader.close();			 
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();			
		}catch (MalformedURLException me){
			me.printStackTrace();
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"在线咨询服务地址异常");
		}catch (ProtocolException pe){
			pe.printStackTrace();
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"数据传输协议不匹配导致无法与在线咨询服务进行通信");
		}catch (UnsupportedEncodingException ue){
			ue.printStackTrace();
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"在线咨询服务字符编码异常");
		}catch (IOException ie) {
			ie.printStackTrace();
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"在线咨询服务通信异常");
		}
		if(!"success".equals(buffer.toString().trim())){
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"在线咨询服务端异常返回信息不正确");
		}
		return buffer.toString();
	}
	
	public static void main(String[] args){
		AppApi40102Form form = new AppApi40102Form();
		form.setUserId("wewiei");
		form.setFullName("大好人");
		form.setPassword("12121ssa21212");
		form.setSurplusAccount("1111111");
		form.setIdcardNumber("33333");
		form.setEmail("3213123@qq.com");
		form.setMobileNumber("121231232");
		OpenFireUtil open = new OpenFireUtil();
		System.out.println(open.toOpenFireServer(form));
	}
}
