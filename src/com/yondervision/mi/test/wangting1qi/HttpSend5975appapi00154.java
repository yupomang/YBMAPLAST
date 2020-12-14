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
//宁波市医疗保险参保人员信息（新）
public class HttpSend5975appapi00154 {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	//public static final String POST_URL = "http://172.16.10.96:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5975(
			String certinum,
			String accname,
			String powerMatters,
			String subPowerMatters,
			String materialName,
			String name,
			String cardId) {
		try {
			URL url = new URL(POST_URL + "appapi00154.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5975&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&certinum="+certinum
						+ "&accname="+accname
						+ "&powerMatters="+powerMatters
						+ "&subPowerMatters="+subPowerMatters
						+ "&materialName="+materialName
						+"&name="+name
						+"&cardId="+cardId;
		//System.out.println(Para);
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
			for (int i = 1; i < 20; i++) {
				long starTime=System.currentTimeMillis();
//				httpURLConnectionPOST5975("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
//						"邵建宁","330204197508036114");
//				httpURLConnectionPOST5975("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
//						"朱晓玲","330227196810215260");
//				httpURLConnectionPOST5975("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
//						"曹洪根","330225195808231571");

				/*	李其康	330211195810274011  	0068690726  	正常参保	退休
					陈祖强	330205195612192712  	0124317512  	正常参保	退休
					这两个如果都查不到信息，说明人社局服务炸了
				*/
				httpURLConnectionPOST5975("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"李其康","330211195810274011");
				/*httpURLConnectionPOST5975("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"陈祖强","330205195612192712");*/
			/*
				httpURLConnectionPOST5975("330226198308035283","王蕾","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"王蕾","330226198308035283");

				httpURLConnectionPOST5975("330205195312193916","王良金","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"王良金","330205195312193916");


				httpURLConnectionPOST5975("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"魏玉素","330219196905286029");

				httpURLConnectionPOST5975("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"张鹏程","330726198810170330");*/

				/*httpURLConnectionPOST5975("510902196403288862","黄晋蓉","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"黄晋蓉","510902196403288862");*/
				//510902196403288862 黄晋蓉

				/*httpURLConnectionPOST5975("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"魏玉素","330219196905286029");*/
				long endTime=System.currentTimeMillis();
				long Time=endTime-starTime;
				System.out.println("请求大数据平台耗时"+Time+"毫秒");
			}
	}
}
