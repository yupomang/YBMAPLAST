package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
//省公安厅居民身份证新（测试环境接口申请未成功）
public class HttpSend5996appapi00119 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5996(String certinum,String accname) {
		try {
			URL url = new URL(POST_URL + "appapi00119.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330726198810170330";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5996&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
								+ "&certinum="+certinum
		 						+ "&accname="+accname;

			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
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
//			String result=change(aes.decrypt(sb.toString()));
//			String result=sb.toString();
//			String result=change(sb.toString());
			String result=aes.decrypt(sb.toString());
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

    private static String change(String input) {  
    	String output = null;
        try {  
            output = new String(input.getBytes("iso-8859-1"),"utf-8");  
//            output = new String(input.getBytes("gb18030"),"utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }
		return output; 
    }  
	public static void main(String[] args) throws UnknownHostException {
		
			//httpURLConnectionPOST5996("330206197211091422","乐申峰");
		httpURLConnectionPOST5996("330903199308240613","");
		httpURLConnectionPOST5996("33020319970406182X","");
		httpURLConnectionPOST5996("330203196105211212","");
	}
}
