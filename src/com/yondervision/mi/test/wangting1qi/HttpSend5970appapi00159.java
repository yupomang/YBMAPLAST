package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
//可信身份认证接口
public class HttpSend5970appapi00159 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	public static String httpURLConnectionPOST5970(String filepath,
												   String filename,
												   String certinum,
												   String accname) {
		try {
			URL url = new URL(POST_URL + "appapi00159.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId ="330726198810170330";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5970&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
								+ "&filepath="+filepath
								+ "&filename="+filename
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
			String result=aes.decrypt(sb.toString());
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
    
	public static void main(String[] args) throws UnknownHostException {
		for(int i=0;i<1;i++) {
/*//正确案例
		httpURLConnectionPOST5970("/wls/","IMG_20181106_163606.jpg","330903199308240613","刘哲语");  
		httpURLConnectionPOST5970("/wls/","IMG_20180811_200054.jpg","320924199307037197","徐志文");  
//错误案例
		httpURLConnectionPOST5970("/wls/","IMG_20181106_163606.jpg","320924199307037197","徐志文");  
		httpURLConnectionPOST5970("/wls/","IMG_20180811_200054.jpg","330903199308240613","刘哲语");  */

			long starTime = System.currentTimeMillis();
			httpURLConnectionPOST5970("/wls/compress/", "20181113025542209.jpg", "330726198810170330", "张鹏程");
/*		httpURLConnectionPOST5970("/wls/compress/","20181113025350122.png","330726198810170330","张鹏程");
		httpURLConnectionPOST5970("/wls/compress/","20181113025708855.jpg","330726198810170330","张鹏程");  */

//		httpURLConnectionPOST5970("/wls/compress/","20181113025542209.jpg","330726198810170330","张鹏程");  
			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时" + Time + "毫秒");
		}
	}
}
