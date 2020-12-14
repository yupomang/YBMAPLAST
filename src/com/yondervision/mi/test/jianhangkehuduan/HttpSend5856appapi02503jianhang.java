package com.yondervision.mi.test.jianhangkehuduan;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;
//根据U盾序列号查询U盾信息
public class HttpSend5856appapi02503jianhang {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "frb1w5988e5d0cef74457bd888ffdeff";
	private static final String appId_V = "yondervisionwebservice42";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5856(String ukeynum) {
		try {
			URL url = new URL(POST_URL + "appapi02503.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "111";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=20&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5856&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMac=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq=&ukeynum="+ukeynum;
		 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
//			String parm ="fullPara="+fullPara+"&centerId=00057400";		
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=42";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=42";
			System.out.println("本地参数" + parm);
			System.out.println("传递参数：" + parm1);
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
		httpURLConnectionPOST5856("146456722");
	}
}
