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
import java.net.URLEncoder;
import java.net.UnknownHostException;

//公安厅居民身份证（新）-市平台
public class HttpSend6010appapi00212 {

	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	
	public static String httpURLConnectionPOST6010(
		/*
			"常住人口姓名 czrkxm 常住人口姓名（精确匹配）
			公民身份证号码 czrkgmsfhm 公民身份证号码（必传）"
		 */
		    String czrkxm,
			String czrkgmsfhm
	) {
		try {
			URL url = new URL(POST_URL + "appapi00212.json");
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

		 	String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6010&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&czrkxm="+czrkxm + "&czrkgmsfhm="+czrkgmsfhm;

			System.out.println("Para----------"+Para);

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
			System.out.println(sb);
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

	public static String httpURLConnectionPOST8009(String spt_tyxyydm, String spt_dwmc) {

		try {
			URL url = new URL(POST_URL + "webapi80009.json");
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
			System.out.println("开始请求接口webapi\nspt_tyxyydm="+spt_tyxyydm+" spt_dwmc="+spt_dwmc);

			String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6010&appid="
					+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
					+ "&spt_tyxyydm="+spt_tyxyydm + "&spt_dwmc="+ spt_dwmc;

			System.out.println("请求参数Para："+Para);

			String fullPara =aes.encrypt(Para.getBytes("UTF-8"));

			// 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			System.out.println("接口webapi.json连接成功.");

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
			System.out.println("返回结果加密："+sb);
			bf.close();
			connection.disconnect();
			String result = aes.decrypt(sb.toString());
			System.out.println("返回结果解密后："+result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {

		long starTime=System.currentTimeMillis();
		httpURLConnectionPOST8009("9133020414409785X9", URLEncoder.encode("宁波市园林工程有限公司","utf-8"));
//		httpURLConnectionPOST8009("91330200599483386B", "轨道交通");

		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("请求大数据平台耗时"+Time+"毫秒");

//		for(int i=1;i<10;i++) {
//		long starTime=System.currentTimeMillis();
//		/*
//		httpURLConnectionPOST6010("","362422199510198710");
//		httpURLConnectionPOST6010("","330204196906123024");
//		httpURLConnectionPOST6010("","330682199408031287");
//		httpURLConnectionPOST6010("","330203196105211212");
//		httpURLConnectionPOST6010("","330203196810020614");
//		httpURLConnectionPOST6010("","33020319970406182X");
//		*/
//
//		//httpURLConnectionPOST6010("陈珩","330203196411299910");
//
//		/*
//		httpURLConnectionPOST6010("王琰慧", "330682199408031287");
//		httpURLConnectionPOST6010("刘哲语", "330903199308240613");
//		httpURLConnectionPOST6010("余倩宁", "330682198411032868");
//		httpURLConnectionPOST6010("周飞翔", "330227198306037310");
//		*/
//
//		httpURLConnectionPOST6010("王琰慧", "330682199408031287");
//		httpURLConnectionPOST6010("陈育人", "330211197512020514");
//		httpURLConnectionPOST6010("裘雅妮", "330203197809230029");
//
//		long endTime=System.currentTimeMillis();
//		long Time=endTime-starTime;
//		System.out.println("请求大数据平台耗时"+Time+"毫秒");
//
//		}
	}
}
