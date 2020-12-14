package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//组织机构代码信息
public class HttpSend6019appapi00221 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	//public static final String POST_URL = "http://172.16.10.96:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST6019(
			//组织机构代码 organCode 组织机构代码
			String organCode
	) {
		try {
			URL url = new URL(POST_URL + "appapi00221.json");
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

			String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6019&appid="
					+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
					+ "&organCode="+organCode;

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
			//System.out.println("sb.toString()"+sb.toString());
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
/*{
    "code":"00",
    "msg":"成功",
    "datas":[
        {
            "zipCode":"324200",
            "abandonedDate":"2014-07-13 00:00:00",
            "country":"",
            "codeDate":null,
            "maintenanceDate":"2012-08-07 09:16:17",
            "supervisorDepartment":"",
            "employeeNumber":"10",
            "uniscId":null,
            "corpCert":"常民民证字第050005号",
            "organCode":"55862105X",
            "legalPerson":"徐金妹",
            "annualInspectionDate":"2012-08-07 09:16:17",
            "currency":"人民币元 ",
            "busiScope":"体育技能培训、训练、表演、竞赛等。",
            "distributeCertificateDate":"2010-07-13 00:00:00",
            "annualInspectionTerm":"2013-07-13 00:00:00",
            "organAddr":"浙江省衢州市常山县天马镇紫港社区３５号",
            "approvalNumber":null,
            "administrativeDivisions":"浙江省衢州市常山县",
            "telNo":"05705033605",
            "organName":"常山县天马镇紫港社区体育健身俱乐部",
            "distributeCertificateUnit":"常山县质量技术监督局",
            "cardId":"330822197304090624",
            "economicType":"其它经济",
            "economicIndustry":"娱乐服务业",
            "organType":"民办非企业单位",
            "tong_time":"2017-01-17 16:58:45.0"
        }
    ],
    "dataCount":1
}*/
			httpURLConnectionPOST6019("55862105X");


			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时"+Time+"毫秒");
		}
	}
}
