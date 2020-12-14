package com.yondervision.mi.common.message;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.util.PropertiesReader;
import com.yondervision.mi.util.security.Base64;
import com.yondervision.mi.util.security.Base64Encoder;
import com.yondervision.mi.util.security.KeySignature;
import com.yondervision.mi.util.security.RSAEncrypt;

/**
 * 移动互联应用服务平台同第三方http通信实现类
 *
 */
public class SimpleHttpMessageUtil implements SimpleHttpMessageI{
	
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param charset
     *            编码格式        
     * @return URL 所代表远程资源的响应结果
     */
	public String sendGet(String url, String charset)throws Exception{
		Logger log = LoggerUtil.getLogger();
		String content = null;
		String connectTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_connection_time").trim();
		String readTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_read_time").trim();
		
		//创建默认的httpClient实例
        CloseableHttpClient httpClient = getHttpClient();
        try {
            //用get方法发送http请求
            HttpGet get = new HttpGet(url);
            System.out.println("执行get请求:...."+get.getURI());
            CloseableHttpResponse httpResponse = null;
            //发送get请求
            httpResponse = httpClient.execute(get);
            try{
                //response实体
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity){
                    System.out.println("响应状态码:"+ httpResponse.getStatusLine());
                    System.out.println("-------------------------------------------------");
                    content = EntityUtils.toString(entity);
                    System.out.println("响应内容:" + content);
                    System.out.println("-------------------------------------------------");                    
                }
            }
            finally{
                httpResponse.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try{
                closeHttpClient(httpClient);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
		return content;
	}
	
	private CloseableHttpClient getHttpClient(){
        return HttpClients.createDefault();
    }

	private void closeHttpClient(CloseableHttpClient client) throws IOException{
        if (client != null){
            client.close();
        }
    }

    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param params
     *            参数        
     * @param charset
     *            编码格式        
     * @return URL 所代表远程资源的响应结果
     */
	@SuppressWarnings("unchecked")
	public String sendPost(String url, HashMap propsMap, String charset)throws Exception{
		System.out.println("【调用远程服务地址】:"+url);		
		PostMethod mypost = new PostMethod(url);
		mypost.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		//头部请求信息
		//mypost.setRequestHeader("headpara", request.getHeader("headpara"));
		//mypost.setRequestHeader("headparaMD5", request.getHeader("headparaMD5"));
		//byte[] data = request.getHeader("headparaMD5").getBytes();		
		//String keysig = Base64Encoder.encode(KeySignature.keySign(data));
		
		//mypost.setRequestHeader("KeySignature", keysig.replaceAll("\n", ""));
		//参数设置
		Set<String> keySet = propsMap.keySet();
		NameValuePair[] postData = new NameValuePair[keySet.size()];
		int index=0;
		for(String key:keySet){
			postData[index++] = new NameValuePair(key,(String)propsMap.get(key));
		}
		mypost.addParameters(postData);
	
//		if(request.getAttribute("DL_LOG_SEQ_NO")!=null){
//			NameValuePair[] postData1= new NameValuePair[1];
//			postData1[0] = new NameValuePair("logid",String.valueOf(request.getAttribute("DL_LOG_SEQ_NO")));
//			mypost.addParameters(postData1);
//		}
		

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
			} else {
				throw new TransRuntimeErrorException(ERROR.CONNECT_SEND_ERROR.getValue(),"http错误码" + re_code);
			}
			
//			response.getOutputStream().write(repMsg.getBytes(encoding));
			req = repMsg;
		} catch (TransRuntimeErrorException e) {
			throw e;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					ERROR.SYS.getValue(), "请检查通信相关参数是否正确或连接通道是否畅通。");
			throw tre;
		}finally{
			mypost.releaseConnection();
		}
		return req;
	}

	/**
     * 同步内容导出到第三方平台，发送post方法的请求,有签名认证
     * 
     * @param url
     *            发送请求的URL
     * @param params
     *            参数        
     * @param charset
     *            编码格式        
     * @return URL 所代表远程资源的响应结果
     */
	public String synchroExportContentPostBySign(String url, String param, String centerid)throws Exception{
		RSAEncrypt rsaEncrypt = new RSAEncrypt();
		PostMethod mypost = new PostMethod(url);
		mypost.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		//头部请求信息
		mypost.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		mypost.setRequestHeader("headpara", "paramdata");
		String headparaMD5 = "";
		try {
			headparaMD5 = Base64.encode(
					rsaEncrypt.encryptPrivateSignature(
							rsaEncrypt.loadPrivateKeyReturn(RSAEncrypt.DEFAULT_PRIVATE_KEY),
							EncryptionByMD5.getMD5(("paramdata="+ param).getBytes("UTF-8")).getBytes())).replaceAll("\n", "");
		} catch (UnsupportedEncodingException e1) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					ERROR.SYS.getValue(), "对发送参数进行签名失败。");
			throw tre;
		} catch (Exception e1) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					ERROR.SYS.getValue(), "对发送参数进行签名失败。");
			throw tre;
		}
		
		mypost.setRequestHeader("headparaMD5", headparaMD5);
		byte[] data = headparaMD5.getBytes();
		String keysig = Base64Encoder.encode(KeySignature.keySign(data));
		mypost.setRequestHeader("KeySignature", keysig.replaceAll("\n", ""));
		
		
		//参数设置
		mypost.addParameter("paramdata", param);

		HttpClient httpClient = new HttpClient();		
		String connectTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_connection_time_"+centerid).trim();
		String readTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_read_time_"+centerid).trim();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(Integer.valueOf(connectTimeout));
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(Integer.valueOf(readTimeout));
		String req = "";
		try {
			int re_code = httpClient.executeMethod(mypost);
			String repMsg = null;
			if (re_code == 200) {
				repMsg = mypost.getResponseBodyAsString();
			} else {
				throw new TransRuntimeErrorException(ERROR.CONNECT_SEND_ERROR.getValue(),"http错误码" + re_code);
			}
			
			req = repMsg;
		} catch (TransRuntimeErrorException e) {
			throw e;
		} catch (Exception e) {
			TransRuntimeErrorException tre = new TransRuntimeErrorException(
					ERROR.SYS.getValue(), "请检查通信相关参数是否正确或连接通道是否畅通。");
			throw tre;
		}finally{
			mypost.releaseConnection();
		}
		return req;
	}
}
