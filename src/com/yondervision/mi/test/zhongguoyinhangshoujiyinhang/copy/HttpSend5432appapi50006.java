package com.yondervision.mi.test.zhongguoyinhangshoujiyinhang.copy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;
//个人公积金登录验证
public class HttpSend5432appapi50006 {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "6afbfb1d0f1d443f75135e7ad2c095df";
	private static final String appId_V = "yondervisionwebservice93";
	private static final String clientIp_V="172.10.0.1";
		
	/**
	 * 个人公积金登录验证
	 */
	public static String httpURLConnectionPOST5432(String bodyCardNumber) {
		try {
			URL url = new URL(POST_URL + "appapi50006.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = bodyCardNumber;
			AesTestZhongGuoYinHang aes = null;
			try {
				aes = new AesTestZhongGuoYinHang();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008"
								+ "&tranDate=2017-09-19&channelSeq="
								+ "&bodyCardNumber="+ bodyCardNumber;

			String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			// 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=93";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=93";
//			System.out.println("本地参数" + parm);
//			System.out.println("传递参数：" + parm1);
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
		
		for (int i = 0; i < 1; i++) {
			long starTime = System.currentTimeMillis();
			httpURLConnectionPOST5432("330203197301111838");
			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("5432个人公积金登录验证耗时" + Time + "毫秒");
		}
		
//		httpURLConnectionPOST5432( "411321198107030018");
//		httpURLConnectionPOST5432("330203195908132417");		
//		httpURLConnectionPOST5432("33020419831112502X");		
		
	}
}
