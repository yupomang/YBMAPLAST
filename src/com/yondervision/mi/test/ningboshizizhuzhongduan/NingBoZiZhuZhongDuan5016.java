package com.yondervision.mi.test.ningboshizizhuzhongduan;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class NingBoZiZhuZhongDuan5016 {

	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "78ce07bdcdf4877f56305895659defc6";
	private static final String appId_V = "yondervisionselfservice53";
	//private static final String clientIp_V="172.10.0.1";
	public static String httpURLConnectionPOST5900(String certinum,String relation) {
		try {
			URL url = new URL(POST_URL + "appapi00128.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,brcCode");

			String buyhousecerid = "330726198810170330";
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = aes.encrypt(buyhousecerid.getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			
			String parm ="centerId=00057400&userId="
			+ userId
			+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5016&devtoken=&channel=53&appid="
			+ appId
			+ "&appkey="
			+ appKey
			+ "&appToken=&clientIp=&brcCode=05740008";

			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5016&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=&brcCode=05740008"
					+ "&certinum="+certinum
					+ "&relation="+relation;



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
			String result=sb.toString();
			System.out.println(result);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {
		
		long starTime = System.currentTimeMillis();
		//URLEncoder.encode("直系亲属证明","utf-8")
		/*httpURLConnectionPOST5900("330282199004121516",URLEncoder.encode("","utf-8"),URLEncoder.encode("其他-02492-000","utf-8"),URLEncoder.encode("其他-02492-001","utf-8"),URLEncoder.encode("直系亲属证明","utf-8"));
		httpURLConnectionPOST5900("33020519710509093X",URLEncoder.encode("","utf-8"),URLEncoder.encode("其他-02492-000","utf-8"),URLEncoder.encode("其他-02492-001","utf-8"),URLEncoder.encode("直系亲属证明","utf-8"));
		httpURLConnectionPOST5900("430821198105197738","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330282199503047787","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330203199405010918","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330921198512302023","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330282198401041144","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330226199303016088","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("512225196411255591","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330283199501300036","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("622621199511020023","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330683199310180838","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("33022619931004559X","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("33022719900505471X","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("410322199406289855","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330225198610235810","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330225198602170324","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("420821197202046249","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330203198402240924","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("33028219870310282X","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330225198604153173","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330781199107300526","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("362331198912150011","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330283199512126045","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330227199504095428","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("33028319930513371X","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330282199602149172","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330225199010182289","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("332525199311115336","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330222197911145532","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("340828197609080558","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330205197410021818","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("331082198311091404","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("33020519700617031X","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("331023199708271432","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330327199301130057","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("331024198205186246","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330224198105030519","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330205199001253334","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330283198610244710","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330225198301140818","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("522422198511024822","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330281198402153328","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("410403199601225589","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330205198112123351","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330283198103275811","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("33022719881023249X","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330227197803241377","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330282198811082213","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("342222199107246028","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330281198706065714","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330281199205111318","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330282199105190051","","其他-02492-000","其他-02492-001","直系亲属证明");
		httpURLConnectionPOST5900("330203196810181215","","其他-02492-000","其他-02492-001","直系亲属证明");
		*/
		httpURLConnectionPOST5900("330211197706240013","02");
		httpURLConnectionPOST5900("330211198210061024","01");

		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("请求大数据平台耗时"+Time+"毫秒");
	}
}
