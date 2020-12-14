package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
//购房发票查询
public class HttpSend5988appapi00140 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	
	public static String httpURLConnectionPOST5988(
			String certinum,
			String accname,
			String powerMatters,
			String subPowerMatters,
			String materialName,
			String nsrsbh,
			String nsrmc,
			String kprqz,
			String kprqq,
			String fpdm,
			String fphm) {
		try {
			URL url = new URL(POST_URL + "appapi00140.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5988&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
								+ "&certinum="+certinum
							+ "&accname="+accname
							+ "&powerMatters="+powerMatters
							+ "&subPowerMatters="+subPowerMatters
							+ "&materialName="+materialName
							+"&nsrmc="+nsrmc
							+"&kprqz="+kprqz
							+"&nsrsbh="+nsrsbh
							+"&kprqq="+kprqq
							+"&fpdm="+fpdm
							+"&fphm="+fphm;
		 
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
	for(int i=0;i<2;i++){


			long starTime=System.currentTimeMillis();
			//王伟杰 李静 33028319920120005X
			/*httpURLConnectionPOST5988("330219197011011194","胡建飞","其他-02490-000","其他-02490-000","法定代表人",
					"330219197011011194","胡建飞","","","","");*/
		httpURLConnectionPOST5988("33028319920120005X","王伟杰","其他-02490-000","其他-02490-000","法定代表人",
				"33028319920120005X","王伟杰","","","","");
		/*httpURLConnectionPOST5988("33020319851111242X","赵晓蓓","其他-02490-000","其他-02490-000","法定代表人",
					"33020319851111242X","赵晓蓓","","","",""); 
		httpURLConnectionPOST5988("420303198310081740","刘姣","其他-02490-000","其他-02490-000","法定代表人",
				"420303198310081740","刘姣","","","","");
			httpURLConnectionPOST5988("330283198609100023","徐娜","其他-02490-000","其他-02490-000","法定代表人",
					"330283198609100023","徐娜","","","","");*/
		/*httpURLConnectionPOST5988("131124198502241449","李立华","其他-02490-000","其他-02490-000","法定代表人",
				"131124198502241449","李立华","","","","");*/
/*		httpURLConnectionPOST5988("330205198711153317","姚维达","其他-02490-000","其他-02490-000","法定代表人",
				"330205198711153317","姚维达","","","",""); 
		httpURLConnectionPOST5988("320302198610182041","庄昕","其他-02490-000","其他-02490-000","法定代表人",
				"320302198610182041","庄昕","","","",""); */
/*					httpURLConnectionPOST5988("330283198609100023","徐娜","其他-02490-000","其他-02490-000","法定代表人",
					"330211199202142013","郑科斌","","","",""); */
/*			httpURLConnectionPOST5988("33020319851111242X","赵晓蓓","其他-02490-000","其他-02490-000","法定代表人",
					"330802197411224012","徐荣贵","","","",""); */
		/*httpURLConnectionPOST5988("411024198002027747","韩琦","其他-02490-000","其他-02490-000","法定代表人",
				"411024198002027747","韩琦","","","","");
			httpURLConnectionPOST5988("33020319851111242X","赵晓蓓","其他-02490-000","其他-02490-000","法定代表人",
				"330227197211027916","周武杰","","","","");

		httpURLConnectionPOST5988("330281198412104167","吴珊珊","其他-02490-000","其他-02490-000","法定代表人",
				"330281198412104167","吴珊珊","","","","");

		httpURLConnectionPOST5988("33028119850529713X","张建勋","其他-02490-000","其他-02490-000","法定代表人",
				"33028119850529713X","张建勋","","","","");

		httpURLConnectionPOST5988("330227197404025116","翁其斌","其他-02490-000","其他-02490-000","法定代表人",
				"330227197404025116","翁其斌","","","","");*/



		/*httpURLConnectionPOST5988("330282198411021518","张裕杰","其他-02490-000","其他-02490-000","法定代表人",
				"330282198411021518","张裕杰","","","","");

		httpURLConnectionPOST5988("33028199102236011","王磊","其他-02490-000","其他-02490-000","法定代表人",
				"33028199102236011","王磊","","","","");

		httpURLConnectionPOST5988("331081198810049116","毛庆石","其他-02490-000","其他-02490-000","法定代表人",
				"331081198810049116","毛庆石","","","","");
		httpURLConnectionPOST5988("330282199108236924","陈洁","其他-02490-000","其他-02490-000","法定代表人",
				"330282199108236924","陈洁","","","","");*/

/*			httpURLConnectionPOST5988("330205199611063327","3302172350","00026653");  
			httpURLConnectionPOST5988("330224197502257528","3302171350","02194330");  
			httpURLConnectionPOST5988("330204198001261034","3302171350","01303450");  
			httpURLConnectionPOST5988("370722198011083814","3302172350","00688470");  */
			
			long endTime=System.currentTimeMillis();
			long Time=endTime-starTime;
			System.out.println("请求大数据平台耗时"+Time+"毫秒");

//			String certinum,String fpdm,String fphm
//			fphm :00155111;fpdm:3302153350;nsrsbh:330281198610140441
	}

	}
}
