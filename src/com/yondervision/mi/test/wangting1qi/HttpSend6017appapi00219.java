package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//宁波市房管中心商品房合同查询
public class HttpSend6017appapi00219 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	//public static final String POST_URL = "http://172.16.10.96:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST6017(
			//合同编号 ContractNo 合同编号
			String ContractNo
	) {
		try {
			URL url = new URL(POST_URL + "appapi00219.json");
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

			String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6017&appid="
					+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
					+ "&ContractNo="+ContractNo;

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

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			long starTime = System.currentTimeMillis();

			httpURLConnectionPOST6017("2019330282YS0359772");

			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时"+Time+"毫秒");
		}
	}
}
