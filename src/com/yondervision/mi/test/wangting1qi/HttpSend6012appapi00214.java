package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//省公安厅居民户口簿电子证照接口
public class HttpSend6012appapi00214 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	
	public static String httpURLConnectionPOST6012(
		/*	调用机构代码 Organization_Id 调用机构代码
			公民身份证号码 gmsfhm 公民身份证号码
			请求人证件号码 User_ID 请求人证件号码
			姓名 xm 姓名
			请求人姓名 User_Name 请求人姓名
			调用机构名称 Organization 调用机构名称
		 */
		    String Organization_Id,
			String gmsfhm,
			String User_ID,
			String xm,
			String User_Name,
			String Organization
	) {
		try {
			URL url = new URL(POST_URL + "appapi00214.json");
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

		 	String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6012&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&Organization_Id="+Organization_Id + "&gmsfhm="+gmsfhm + "&User_ID="+User_ID
				 		+ "&xm="+xm + "&User_Name="+User_Name + "&Organization="+Organization;

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

	public static void main(String[] args){
		for(int i=0;i<1;i++) {
			long starTime = System.currentTimeMillis();
			/*调用机构代码 Organization_Id 调用机构代码
			公民身份证号码 gmsfhm 公民身份证号码
			请求人证件号码 User_ID 请求人证件号码
			姓名 xm 姓名
			请求人姓名 User_Name 请求人姓名
			调用机构名称 Organization 调用机构名称*/
			httpURLConnectionPOST6012("05740000", "330682199408031287", "330682199408031287", "王琰慧", "王琰慧", "宁波市住房公积金管理中心");
			httpURLConnectionPOST6012("05740000", "330903199308240613", "330903199308240613", "刘哲语", "刘哲语", "宁波市住房公积金管理中心");
			httpURLConnectionPOST6012("05740000", "330203198111060675", "330203198111060675", "程建勇", "程建勇", "宁波市住房公积金管理中心");
			httpURLConnectionPOST6012("05740000", "330622196611271913", "330622196611271913", "王永苗", "王永苗", "宁波市住房公积金管理中心");
			httpURLConnectionPOST6012("05740000", "330227198306037310", "330227198306037310", "周飞翔", "周飞翔", "宁波市住房公积金管理中心");
			httpURLConnectionPOST6012("05740000", "330203199208212713", "330203199208212713", "孙健", "孙健", "宁波市住房公积金管理中心");
			httpURLConnectionPOST6012("05740000", "330682198411032868", "330682198411032868", "余倩宁", "余倩宁", "宁波市住房公积金管理中心");
			httpURLConnectionPOST6012("05740000", "330522201006250813", "330522201006250813", "王萍萍", "王萍萍", "宁波市住房公积金管理中心");
			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时" + Time + "毫秒");
		}
	}
}
