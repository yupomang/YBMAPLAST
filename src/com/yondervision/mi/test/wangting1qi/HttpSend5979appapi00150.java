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
//宁波市城镇职工个人参保证明基础信息
public class HttpSend5979appapi00150 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5979(

			String AAC003,
			String AAC002,
			String AAB301,
			String pageSize) {
		try {
			URL url = new URL(POST_URL + "appapi00150.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5979&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="

						+"&AAC003="+AAC003
						+"&AAC002="+AAC002
						+"&AAB301="+AAB301
						+"&pageSize="+pageSize;

			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
			//System.out.println(parm);
			//System.out.println(parm1);
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

    private static String change(String input) {  
    	String output = null;
        try {  
            output = new String(input.getBytes("iso-8859-1"),"utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }
		return output; 
    }  
	public static void main(String[] args) throws UnknownHostException {
			for (int i = 1; i < 10; i++) {
				long starTime=System.currentTimeMillis();
/*				httpURLConnectionPOST5979("330726198810170330","张鹏程","其他-02493-000","其他-02493-013","社保证明",
						"张鹏程","330726198810170330","330201","2");*/
				
				/*httpURLConnectionPOST5979("330283198801092326","黄锦晶","其他-02493-000","其他-02493-013","社保证明",
						"黄锦晶","330283198801092326","330200","2");
				
				httpURLConnectionPOST5979("330227196811226164","郑国英","其他-02493-000","其他-02493-013","社保证明",
						"郑国英","330227196811226164","330200","2");

				httpURLConnectionPOST5979("330204199711241016","王灏杰","其他-02493-000","其他-02493-013","社保证明",
						"王灏杰","330204199711241016","330200","2");*/

				httpURLConnectionPOST5979(
						"倪翠霞","421181198703085020","330200","2");

/*				httpURLConnectionPOST5979("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"程冠维","3623300198910020908","330201","2");*/
//				aac003:姓名
//				aac002:身份证号码
//				aab301:6位统筹区代码
//				pageSize:查询月数
				long endTime=System.currentTimeMillis();
				long Time=endTime-starTime;
				System.out.println("请求大数据平台耗时"+Time+"毫秒");
			}
	}



}
