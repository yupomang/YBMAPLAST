package com.yondervision.mi.common.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.HostNameResolver;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.yondervision.mi.util.PropertiesReader;

public class HttpsUtils {	
	public static String oaUrl = PropertiesReader.getProperty("properties.properties", "dlOaUrl");
	private static DefaultHttpClient client;
	 /** 
     * 访问https的网站 
     * @param httpclient 
     */  
    private static void enableSSL(DefaultHttpClient httpclient){  
        //调用ssl  
         try {  
                SSLContext sslcontext = SSLContext.getInstance("TLS");  
                sslcontext.init(null, new TrustManager[] { truseAllManager }, null);  
                SSLSocketFactory sf = new SSLSocketFactory(sslcontext);  
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
                Scheme https = new Scheme("https", sf, 443);  
                httpclient.getConnectionManager().getSchemeRegistry().register(https);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
    } 
    /** 
     * 重写验证方法，取消检测ssl 
     */  
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
    /**
    * HTTP Client Object,used HttpClient Class before(version 3.x),but now the
    * HttpClient is an interface
     * @throws IOException 
    */


    public static String sendXMLDataByGet(String url,String xml) throws IOException{
       // 创建HttpClient实例     
//        if (client == null) {
        	// Create HttpClient Object
        	DefaultHttpClient client = new DefaultHttpClient();
        	enableSSL(client);
//        }
        StringBuilder urlString=new StringBuilder();
        urlString.append(url);
        urlString.append("?");
//        System.out.println("getUTF8XMLString(xml):"+getUTF8XMLString(xml));
	    try {
//        	urlString.append(URLEncoder.encode( getUTF8XMLString(xml) , "UTF-8" ));
	    	urlString.append(xml);
	    } catch (Exception e2) {
	    	// TODO Auto-generated catch block
	    	e2.printStackTrace();
	    }
        String urlReq=urlString.toString();
        // 创建Get方法实例     
        HttpGet httpsgets = new HttpGet(urlReq);

        String strRep="";
        HttpResponse response = null;
	    try {
		    response = client.execute(httpsgets);    
		    HttpEntity entity = response.getEntity(); 
		    client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);//2015-07-16添加
		    if (entity != null) { 
		    	strRep = EntityUtils.toString(response.getEntity());
		    	// Do not need the rest    
//		    	httpsgets.abort(); 
		    }		    
	    } catch (ClientProtocolException e) {
		    e.printStackTrace();
		    throw e;
	    } catch (IllegalStateException e) {
	    	e.printStackTrace();
	    	throw e;
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	throw e;
	    } finally{	    	
	    	 httpsgets.abort(); 
             if (response != null) { 
                  EntityUtils.consumeQuietly(response.getEntity()); 
             }
             client.close();
	    }
        return strRep;
    } 


    /**
    * Send a XML-Formed string to HTTP Server by post method
    * 
    * @param url
    *            the request URL string
    * @param xmlData
    *            XML-Formed string ,will not check whether this string is
    *            XML-Formed or not
    * @return the HTTP response status code ,like 200 represents OK,404 not
    *         found
     * @throws IOException 
    * @throws IOException
    * @throws ClientProtocolException
    */
    public static String sendXMLDataByPost(String url, String xmlData) throws IOException {
//	    if (client == null) {
		    // Create HttpClient Object
    	DefaultHttpClient client = new DefaultHttpClient();
		    enableSSL(client);
//	    }
	    client.getParams().setParameter("http.protocol.content-charset",
	    HTTP.UTF_8);
	    client.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
	    client.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);
	    client.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET,HTTP.UTF_8);
	
	    
	    
	    // System.out.println(HTTP.UTF_8);
	    // Send data by post method in HTTP protocol,use HttpPost instead of
	    // PostMethod which was occurred in former version
	    // System.out.println(url);
	    HttpPost post = new HttpPost(url);
	    post.getParams().setParameter("http.protocol.content-charset",HTTP.UTF_8);
	    post.getParams().setParameter(HTTP.CONTENT_ENCODING, HTTP.UTF_8);
	    post.getParams().setParameter(HTTP.CHARSET_PARAM, HTTP.UTF_8);
	    post.getParams().setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET, HTTP.UTF_8);	    
	
	    // Construct a string entity
//	    StringEntity entity = new StringEntity(getUTF8XMLString(xmlData), "UTF-8");
	    StringEntity entity;
	    String strrep="";
	    HttpResponse response=null;
		try {
			entity = new StringEntity(xmlData, "UTF-8");
			entity.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
		    entity.setContentEncoding("UTF-8");
		    // Set XML entity
		    post.setEntity(entity);
		    // Set content type of request header
		    post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		    // Execute request and get the response
		    
			System.out.println("===访问工单系统开始");
			response = client.execute(post);
			
		    HttpEntity entityRep = response.getEntity(); 
		    
	        if (entityRep != null) {     
	            strrep = EntityUtils.toString(response.getEntity());
	            // Do not need the rest    
//	            post.abort();
	            entityRep.getContent().close();
	        }  
	        System.out.println("===访问工单系统结束");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw e;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally{
			post.abort(); 
            if (response != null) { 
                 EntityUtils.consumeQuietly(response.getEntity());                 
            }
            client.close();
		}
	    
	    // Response Header - StatusLine - status code
	    // statusCode = response.getStatusLine().getStatusCode();
	    return strrep;
    }
    
    /**
    * Get XML String of utf-8
    * 
    * @return XML-Formed string
    */
    public static String getUTF8XMLString(String xml) {
	    // A StringBuffer Object
	    StringBuffer sb = new StringBuffer();
	    sb.append(xml);
	    String xmString = "";
	    try {
	    	xmString = new String(sb.toString().getBytes("UTF-8"));
	    } catch (UnsupportedEncodingException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
	    // return to String Formed
	    return xmString.toString();
    }
	
    public static void main(String[] args) throws ClientProtocolException, IOException {
    	HttpsUtils https = new HttpsUtils();
    	
    	https.sendXMLDataByPost("https://5.4.12.6/wsapi/login","username=cz&password=1&client=mobile&remote_host=10.11.12.13");
    }
}
