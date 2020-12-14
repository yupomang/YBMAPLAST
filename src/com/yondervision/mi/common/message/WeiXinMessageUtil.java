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
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.ui.ModelMap;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.PropertiesReader;

/** 
* @ClassName: OpenFireUtil 
* @author Czy
* @date Jan 3, 2014 12:42:22 PM   
* 
*/ 
public class WeiXinMessageUtil {
	
	public void toWeiXinServer(String urlString,String value,HttpServletRequest request, HttpServletResponse response) throws TransRuntimeErrorException{
		StringBuffer buffer = new StringBuffer();
		try {			
			URL url = new URL(urlString.trim());
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();			
			System.out.println("调用微信前置通信服务接口:"+urlString);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);			
			String connectTimeout = PropertiesReader.getProperty(
					Constants.PROPERTIES_FILE_NAME, "weixinTimeout").trim();
			String readTimeout = PropertiesReader.getProperty(
					Constants.PROPERTIES_FILE_NAME, "weixinTimeout").trim();
			httpUrlConn.setConnectTimeout(Integer.valueOf(connectTimeout));  
			httpUrlConn.setReadTimeout(Integer.valueOf(readTimeout));			 
			httpUrlConn.setRequestMethod("POST");
			
			System.out.println("调用在线资询OPENFIRE服务通信参数:"+value);
			if (null != value) {
				OutputStream outputStream = httpUrlConn.getOutputStream();				 
				outputStream.write(value.getBytes("UTF-8"));
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
			if(buffer.toString().indexOf("\"errcode\":0")<0){
				throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"微信服务端异常返回信息不正确");
			}
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(buffer.toString().getBytes(encoding));
		}catch (MalformedURLException me){
			me.printStackTrace();
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"微信服务地址异常");
		}catch (ProtocolException pe){
			pe.printStackTrace();
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"数据传输协议不匹配导致无法与微信服务进行通信");
		}catch (UnsupportedEncodingException ue){
			ue.printStackTrace();
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"微信服务字符编码异常");
		}catch (IOException ie) {
			ie.printStackTrace();
			throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"微信服务通信异常");
		}		
	}
	
	public String post(String url, String data ,ModelMap modelMap,HttpServletRequest request, HttpServletResponse response){
		HttpPost post = new HttpPost(url);		
		StringEntity entity;
		String msg="";
		try {
			entity = new StringEntity(data, "utf-8");
			post.setEntity(entity);
			HttpResponse response1;
			response1 = getClient().execute(post);
			if (response1.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.println();
			}
			String encoding = "UTF-8";			
//			if (CommonUtil.isEmpty(request)) {
//				encoding = "UTF-8"; 
//			}else {
//				encoding = request.getCharacterEncoding();
//			}
			msg = EntityUtils.toString(response1.getEntity(),encoding);
			System.out.println("********  微信前置通信返回信息："+msg);
			if(request!=null){
				if(request.getServletPath().startsWith("/weixinapi")){
					System.out.println("********  response.getOutputStream().write(msg.getBytes(encoding)) ："+msg);
					response.getOutputStream().write(msg.getBytes(encoding));
				}else{
					System.out.println("********  NO Print 'response.getOutputStream().write(msg.getBytes(encoding))' :"+msg);
				}
			}
			
			 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			modelMap.put("errorcode", "101");
			modelMap.put("errormsg", "微信服务字符编码异常");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			modelMap.put("errorcode", "103");
			modelMap.put("errormsg", "与微信前置IO异常");
		}catch (Exception e){
			e.printStackTrace();
			modelMap.put("errcode", "104");
			modelMap.put("errmsg", "与微信前置通讯异常");
		}finally{
			post.releaseConnection();
			return msg;
		}
	}
	
	private static TrustManager truseAllManager = new X509TrustManager(){
        public void checkClientTrusted(  
                java.security.cert.X509Certificate[] arg0, String arg1)  
                throws CertificateException {
            // TODO Auto-generated method stub  
              
        }  
        public void checkServerTrusted(  
                java.security.cert.X509Certificate[] arg0, String arg1)  
                throws CertificateException {  
            // TODO Auto-generated method stub  
              
        }  
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
            // TODO Auto-generated method stub  
            return null;  
        }  
    }; 
    public String post(String url, String data,HttpServletRequest request, HttpServletResponse response){
		HttpPost post = new HttpPost(url);		
		StringEntity entity;
		String msg="";
		try {
			entity = new StringEntity(data, "utf-8");
			post.setEntity(entity);
			HttpResponse response1;
			response1 = new WeiXinMessageUtil().getClient().execute(post);
			if (response1.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.println();
			}
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			msg = EntityUtils.toString(response1.getEntity(),encoding);
			if(request.getServletPath().startsWith("/weixinapi")){
				System.out.println("********  response.getOutputStream().write(msg.getBytes(encoding)) ："+msg);
				//response.getOutputStream().write(msg.getBytes(encoding));
			}else{
				System.out.println("********  NO Print 'response.getOutputStream().write(msg.getBytes(encoding))' :"+msg);
			}
			return msg;
		} catch (Exception e){
			e.printStackTrace();
		}finally{
			post.releaseConnection();
			return msg;
		}
	}
	
	private HttpClient getClient() throws NoSuchAlgorithmException, KeyManagementException{
		HttpClient client = new DefaultHttpClient();		
		SSLContext sslcontext = SSLContext.getInstance("TLS");
		sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
		SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
		sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme https = new Scheme("https", sf, 443);
		client.getConnectionManager().getSchemeRegistry().register(https);		
		return client;
		
	}
	
	public static void main(String[] args){
		System.out.println("errorcode:0alsdfjasldfjlaskdfjlsakdfj".indexOf("errorcode:0"));	
	}
}
