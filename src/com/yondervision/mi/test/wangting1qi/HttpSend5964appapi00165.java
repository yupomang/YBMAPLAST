package com.yondervision.mi.test.wangting1qi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;
//火化信息
public class HttpSend5964appapi00165 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5964(
			String certinum,
			String accname,
			String powerMatters,
			String subPowerMatters,
			String materialName,
			String cardId) {
		try {
			URL url = new URL(POST_URL + "appapi00165.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5964&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&certinum="+certinum
						+ "&accname="+accname
						+ "&powerMatters="+powerMatters
						+ "&subPowerMatters="+subPowerMatters
						+ "&materialName="+materialName
						+"&cardId="+cardId;

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
			for (int i = 1; i < 10; i++) {
				long starTime=System.currentTimeMillis();		
//				httpURLConnectionPOST5964("330205197003213310","冯海波","其他-02491-008","其他-02491-008","火化证明","330205197003213310"); 
				httpURLConnectionPOST5964("34242319680609678X","刘良莹","其他-02491-008","其他-02491-008","火化证明","34242319680609678X"); 
/*
				httpURLConnectionPOST5964("330224197301257513","杨雷吉","其他-02491-008","其他-02491-008","火化证明","330224197301257513");
				httpURLConnectionPOST5964("330225196103139139","井松青","其他-02491-008","其他-02491-008","火化证明","330225196103139139"); 
				httpURLConnectionPOST5964("330225198104251578","周元熊","其他-02491-008","其他-02491-008","火化证明","330225198104251578"); 
				httpURLConnectionPOST5964("330225194812185411","俞根绪","其他-02491-008","其他-02491-008","火化证明","330225194812185411"); 
				httpURLConnectionPOST5964("330224194606131870","马协力","其他-02491-008","其他-02491-008","火化证明","330224194606131870"); 
				httpURLConnectionPOST5964("332525198312295717","吴树","其他-02491-008","其他-02491-008","火化证明","332525198312295717"); 
				httpURLConnectionPOST5964("33022719580409789X","戴明宏","其他-02491-008","其他-02491-008","火化证明","33022719580409789X"); 
*/

				
				
				long endTime=System.currentTimeMillis();	
				long Time=endTime-starTime;
				System.out.println("请求大数据平台耗时"+Time+"毫秒");
			}
	}
}
