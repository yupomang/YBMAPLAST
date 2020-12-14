package com.yondervision.mi.test;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
//个人账户查询（用于反显）
public class HttpSend5007appapi00104 {
	//public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	public static final String POST_URL = "http://172.16.10.96:7006/YBMAPZH/";
	private static final String appKey_V = "6afbfb1d0f1d443f75135e7ad2c095df";
	private static final String appId_V = "yondervisionwebservice93";
	private static final String clientIp_V="172.10.0.1";
	/**
	 * 个人账户查询（用于反显）
	 */
	public static String httpURLConnectionPOST5007(String certinum,String accnum ) {
		try {
			URL url = new URL(POST_URL + "appapi00104.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = certinum;
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5007&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq=&certinum="+certinum+"&accnum="+accnum;
			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=93";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=93";
			System.out.println("本地参数" + parm);
			System.out.println("传递参数:" + parm1);
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
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnknownHostException {

		httpURLConnectionPOST5007("330226196608105627","");
//		httpURLConnectionPOST5007("330222197409030027","");
//		03901000042015一手贷0000780	吴菊玲 

//		String str="219,23,9,10,32";
//		String[]  a=str.split(",");
//		Arrays.sort(a);
////		str=a.toString();
//
//		str = Arrays.toString(a);
//		System.out.println(str);
		
	}
}
