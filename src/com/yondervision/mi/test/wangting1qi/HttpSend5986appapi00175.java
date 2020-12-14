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

//契税完税信息接口
public class HttpSend5986appapi00175 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5986(
			String powerMatters,
			String subPowerMatters,
			String materialName,
			String certinum,
			String accname,
			//String nsrqz,
			//String nsrqq,
			String nsrsbh,
			String nsrmc,
			String dzsphm) {
		try {
			URL url = new URL(POST_URL + "appapi00175.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5986&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
								+ "&powerMatters="+powerMatters
								+ "&subPowerMatters="+subPowerMatters
								+ "&materialName="+materialName
								+ "&certinum="+certinum
								+ "&accname="+accname
								//+ "&nsrqz="+nsrqz
								//+ "&nsrqq="+nsrqq
								+ "&nsrsbh="+nsrsbh
								+ "&nsrmc="+nsrmc
								+ "&dzsphm="+dzsphm;

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
		for(int i=1;i<10;i++) {
			long starTime = System.currentTimeMillis();
//			httpURLConnectionPOST5986("152624198908205714","刘涛","浙江省宁波市江北区","84.73");  
//			httpURLConnectionPOST5986("330205199611063327","蒋若韵","欢乐海岸小区","178.32");  
		   /* httpURLConnectionPOST5986("其他-02491-000","其他-02491-006","契税完税凭证","330802197411224012","徐荣贵","","","330802197411224012","徐荣贵","");
			httpURLConnectionPOST5986("其他-02491-000","其他-02491-006","契税完税凭证","330203198808212413","柳铭创","","","330203198808212413","柳铭创","");
			httpURLConnectionPOST5986("其他-02491-000","其他-02491-006","契税完税凭证","420606198311223515","郑凌飞","","","420606198311223515","郑凌飞","");
			httpURLConnectionPOST5986("其他-02491-000","其他-02491-006","契税完税凭证","330204199211225044","张倩","","","330204199211225044","张倩","");
			//httpURLConnectionPOST5986("其他-02491-000","其他-02491-006","契税完税凭证","320322198606158669","王聪","","","320322198606158669","王聪","");
			httpURLConnectionPOST5986("其他-02491-000","其他-02491-006","契税完税凭证","330206197902091421","曹开艳","","","330206197902091421","曹开艳","");
			httpURLConnectionPOST5986("其他-02491-000","其他-02491-006","契税完税凭证","331082199209093037","朱佳峰","","","331082199209093037","朱佳峰","");
			httpURLConnectionPOST5986("其他-02491-000"," 其他-02491-006","契税完税凭证","33020519841021274X","洪锦尔","","","33020519841021274X","洪锦尔","");
	*/
			//httpURLConnectionPOST5986("其他-02491-000", " 其他-02491-006", "契税完税凭证", "330802198506143228", "傅群", "", "", "330802198506143228", "傅群", "");
			httpURLConnectionPOST5986("其他-02491-000", " 其他-02491-006", "契税完税凭证", "33028319881027771X", "卓锋杰",  "33028319881027771X", "卓锋杰", "");
			//httpURLConnectionPOST5986("其他-02491-000", " 其他-02491-006", "契税完税凭证", "310105197812273234", "周生利", "", "", "310105197812273234", "周生利", "");
			//httpURLConnectionPOST5986("其他-02491-000", " 其他-02491-006", "契税完税凭证", "330227198712304437", "徐宁川", "", "", "330227198712304437", "徐宁川", "");
			//httpURLConnectionPOST5986("其他-02491-000", " 其他-02491-006", "契税完税凭证", "330283199411040058", "肖牵辉", "", "", "330283199411040058", "肖牵辉", "");

			/*
		    httpURLConnectionPOST5986("其他-02491-000"," 其他-02491-006","契税完税凭证","33022719890823272X","徐倩蓉","","","33022719890823272X","徐倩蓉","");
			httpURLConnectionPOST5986("其他-02491-000"," 其他-02491-006","契税完税凭证","330227198310080565","俞可露","","","330227198310080565","俞可露","");
*/

			/*httpURLConnectionPOST5986("其他-02491-000","其他-02491-006","契税完税凭证","330204197903075017","徐海民","","","330204197903075017","徐海民","");
			httpURLConnectionPOST5986("其他-02491-000","其他-02491-006","契税完税凭证","420606198311223515","郑凌飞","","","420606198311223515","郑凌飞","");
			httpURLConnectionPOST5986("其他-02491-000","其他-02491-006","契税完税凭证","33020419810525201X","陈鲁","","","33020419810525201X","陈鲁","");*/
			//httpURLConnectionPOST5986("其他-02491-000","其他-02491-006","契税完税凭证","330211198307314024","王春慧","","","330211198307314024","王春慧","");
			//httpURLConnectionPOST5986("其他-02491-000","其他-02491-006","契税完税凭证","330182198801270039","胡欣立","","","330182198801270039","胡欣立","");

			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时" + Time + "毫秒");

//			httpURLConnectionPOST5986("其他-02492-000","其他-02492-002","契税完税凭证","33022719900104002X","周姗姗","樟树街168开19号610","57.53");  
//			httpURLConnectionPOST5986("其他-02491-000","其他-02491-001","契税完税凭证","330226199104066736","方帅","汇嘉新园7幢16号307","68.46");  
//			httpURLConnectionPOST5986("341221198104085490","马云","慈城镇慈湖人家81幢212号302","102.76");  

		}
	}
}
