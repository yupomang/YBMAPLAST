package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//生成工单
public class HttpSend6015appapi00217 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	
	public static String httpURLConnectionPOST6015(
		/*
		WorkSheetLogVo
{
    "appCode":"1DF4Wae2R4Tdxm8f",
    "feedbackMan":"张三",
    "feedbackPhone":"185*******1",
    "generallyOneName":"李四",
    "generallyOnePhone":"185*******1",
    "interfaceName":"人口信息",
    "outputColumns":[
        {
            "codeName":"年龄",
            "desc":"年龄为空",
            "type":"V"
        },
        {
            "codeName":"住址",
            "desc":"住址错误",
            "type":"E"
        }
    ],
    "requestId":"543572119909401a9f09d7530f4367fe",
    "source":"creditSource"
}


	部门反馈人姓名  fullName
部门反馈人手机号码 mobileNumber
办事者姓名 accname
办事者手机号码 tel
接口名称 businName
问题字段名称 csmc
问题字段类型（”E”表示错误，”V”表示空缺cslbmc
问题字段描述 ms
		 */

		    String fullName,
			String mobileNumber,
			String accname,
			String tel,
			String businName,
			String csmc,
			String cslbmc,
			String ms
	) {
		try {
			URL url = new URL(POST_URL + "appapi00217.json");
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

			String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6015&appid="
					+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
					+ "&fullName="+fullName + "&mobileNumber="+mobileNumber + "&accname="+accname + "&tel="+tel + "&businName="+businName + "&csmc="+csmc + "&cslbmc="+cslbmc
					+ "&ms="+ms;

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
		for (int i = 0; i < 100; i++) {
			long starTime = System.currentTimeMillis();

			httpURLConnectionPOST6015("公积金测试", "17681808295", "公积金测试", "17681808295", "人口信息", "年龄", "V", "年龄为空");

			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时" + Time + "毫秒");

		}
	}
}
