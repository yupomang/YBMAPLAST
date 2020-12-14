package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//信用中心核查奖惩数据返回接口
public class HttpSend6006appapi00208 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	//private static final String message="{\"servicecode\":\"许可-00393-002\",\"servicename\":\"测试1\",\"projid\":\"123\",\"areacode\":\"123456\",\"dept\":\"01\",\"deptname\":\"测试部门\",\"name\":\"任巍巍330227198708146819\",\"creditkey\":\"G2TN5J7M86K099EI\"}";
	//private static final String message="{\"servicecode\":\"许可-00393-002\",\"servicename\":\"测试1\",\"projid\":\"123\",\"areacode\":\"330401\",\"dept\":\"01\",\"deptname\":\"测试部门\",\"name\":\"张仙云332601196309114140\",\"creditkey\":\"G2TN5J7M86K099EI\"}";
	//private static final String message="{\"servicecode\":\"许可-00393-002\",\"servicename\":\"测试\",\"projid\":\"123\",\"areacode\":\"330401\",\"dept\":\"01\",\"deptname\":\"测试部门\",\"name\":\"宁波鄞州兰盾贸易有限公司\",\"creditkey\":\"G2TN5J7M86K099EI\"}";

	public static void main(String[] args) {
		for (int i = 1;i<=10;i++) {
			long starTime = System.currentTimeMillis();
			httpURLConnectionPOST6006("朱海燕330227198112120781");
			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时" + Time + "毫秒");
		}
		/*httpURLConnectionPOST6006("王琰慧330682199408031287");
		httpURLConnectionPOST6006("罗霖362422199510198710");
		httpURLConnectionPOST6006("任巍巍330227198708146819");
		httpURLConnectionPOST6006("张仙云332601196309114140");
		httpURLConnectionPOST6006("宁波鄞州兰盾贸易有限公司");*/
	}
	//生成JSON文本格式
	/*private static String creatJson() {

		JSONObject obj = new JSONObject();
		obj.put("servicecode", "许可-00393-002");
		obj.put("servicename", "测试");
		obj.put("projid", "123");
		obj.put("areacode", "330401");
		obj.put("dept", "01");
		obj.put("deptname", "测试部门");
		obj.put("name", "宁波鄞州兰盾贸易有限公司");
		obj.put("creditkey", "G2TN5J7M86K099EI");
		JSONArray array = new JSONArray();
		array.add(obj);
		JSONObject clazz = new JSONObject();
		clazz.put("message", array);
		System.out.println(clazz.toString());
		return clazz.toString();
	}*/

	public static String httpURLConnectionPOST6006(
		/*	传入参数 message,json中的name字段,其他字段写死
		 */

			String message
			//message={"servicecode":"许可-00393-002","servicename":"测试","projid":"123","areacode":"330401","dept":"01","deptname":"测试部门",
			// "name":"宁波鄞州兰盾贸易有限公司","creditkey":"对应系统的creditkey"}
			//creditkey":"对应系统的creditkey，就是授权码
			//宁波市新一代住房公积金管理系统  G2TN5J7M86K099EI


	) {
		try {
			URL url = new URL(POST_URL + "appapi00208.json");
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

		 	String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6006&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&message="+message
				 ;
			//System.out.println("message_V-------------"+message);
			//System.out.println("Para-------------"+Para);
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
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection. getInputStream(), "UTF-8"));
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


}
