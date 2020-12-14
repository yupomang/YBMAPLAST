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
//省公安厅居民身份证旧
public class HttpSend5991appapi00138 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5991(String certinum,String accname,String powerMatters,String subPowerMatters,String materialName) {
		try {
			URL url = new URL(POST_URL + "appapi00138.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5991&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
								+ "&certinum="+certinum
								+ "&accname="+accname
								+ "&powerMatters="+powerMatters
								+ "&subPowerMatters="+subPowerMatters
								+ "&materialName="+materialName;

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
			for (int i = 1; i < 2; i++) {
				long starTime=System.currentTimeMillis();
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-012","经办人身份证明");
//
//				
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-012","经办人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02492-000","其他-02492-005","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02492-000","其他-02492-002","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-008","经办人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02492-000","其他-02492-002","售房人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-007","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-003","委托代理人办理提取的，提供代理人身份证明及委托书");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-001","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-007","经办人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-009","委托代理人办理提取的，提供代理人身份证明及委托书");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-003","经办人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-002","经办人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-003","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-001","经办人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02492-000","其他-02492-003","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02492-000","其他-02492-001","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-010","经办人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-009","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-004","经办人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-010","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-010","委托代理人办理提取的，提供代理人身份证明及委托书");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-009","经办人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-006","经办人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-002","委托代理人办理提取的，提供代理人身份证明及委托书");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-002","法定代表人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-001","法定代表人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","委托代理人办理提取的，提供代理人身份证明及委托书");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-006","职工身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-001","委托代理人办理提取的，提供代理人身份证明及委托书");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-002","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-008","继承人或受遗赠人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","委托代理人办理提取的，提供代理人身份证明及委托书");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02492-000","其他-02492-004","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-008","委托代理人办理提取的，提供代理人身份证明及委托书");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-013","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-005","职工身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-007","委托代理人办理提取的，提供代理人身份证明及委托书");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-006","身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02491-000","其他-02491-006","委托代理人办理提取的，提供代理人身份证明及委托书");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-004","职工身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-011","经办人身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-007","职工身份证明");
//				httpURLConnectionPOST5991("330726198810170330","张鹏程","其他-02493-000","其他-02493-005","经办人身份证明");

				httpURLConnectionPOST5991("330206197211091422","邵建宁","其他-02492-000","其他-02492-001","户籍证明");
//				
//				httpURLConnectionPOST5991("330203197811030616","肖忠炳","其他-02492-000","其他-02492-001","户籍证明");
//
//				httpURLConnectionPOST5991("330281198811137917","方涛","其他-02492-000","其他-02492-001","户籍证明");
//
//				httpURLConnectionPOST5991("330283198911031482","张幼蓉","其他-02492-000","其他-02492-001","户籍证明");
//
//				httpURLConnectionPOST5991("330183198511300013","赵程遥","其他-02492-000","其他-02492-001","户籍证明");
				httpURLConnectionPOST5991("362422199510198710","","其他-02492-000","其他-02492-001","户籍证明");

				httpURLConnectionPOST5991("330204196906123024","","其他-02492-000","其他-02492-001","户籍证明");

				httpURLConnectionPOST5991("330682199408031287","","其他-02492-000","其他-02492-001","户籍证明");
				httpURLConnectionPOST5991("330203196105211212","","其他-02492-000","其他-02492-001","户籍证明");
				httpURLConnectionPOST5991("330203196810020614","","其他-02492-000","其他-02492-001","户籍证明");
				httpURLConnectionPOST5991("33020319970406182X","","其他-02492-000","其他-02492-001","户籍证明");

	
	
				long endTime=System.currentTimeMillis();
				long Time=endTime-starTime;
				System.out.println("请求大数据平台耗时"+Time+"毫秒");
			}
	}


}
