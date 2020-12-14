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
//省建设厅商品房买卖合同接口
public class HttpSend5985appapi00144 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5985(String jgh,
																				String jkbm,
																				String certinum,
																				String SZCS,
																				String HTBH) {
		try {
			URL url = new URL(POST_URL + "appapi00144.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5985&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
								+ "&jgh="+jgh
								+ "&jkbm="+jkbm
								+ "&certinum="+certinum
								+ "&SZCS="+SZCS
								+ "&HTBH="+HTBH;

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
//		private String  jgh;//机构号 非加密；11
//		11	宁波市住房公积金管理中心
//		16	宁波市住房公积金管理中心鄞州分中心
//		12	宁波市住房公积金管理中心北仑分中心
//		19	宁波市住房公积金管理中心奉化分中心
//		18	宁波市住房公积金管理中心慈溪分中心
//		14	宁波市住房公积金管理中心象山分中心
//		15	宁波市住房公积金管理中心宁海分中心
//		13	宁波市住房公积金管理中心镇海分中心
//		17	宁波市住房公积金管理中心余姚分中心

//		private String  jkbm;//接口编码 非加密 spfht
//		private String  certinum;//	certinum	房屋买受人证件号码
//		private String  SZCS;//	SZCS	所在城市行政编码
//		private String  HTBH;//	HTBH	合同编号
		httpURLConnectionPOST5985("11","spfht","512201196911141311","330200","2018330205YS0032825");  

	}
}
