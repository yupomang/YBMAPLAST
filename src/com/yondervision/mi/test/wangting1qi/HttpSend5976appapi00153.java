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
import java.net.UnknownHostException;
//医保费用个人负担证明
public class HttpSend5976appapi00153 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5976(
			String certinum,
			String accname,
			String powerMatters,
			String subPowerMatters,
			String materialName,
			String FYQSSJ,
			String JYLX,
			String FYJSSJ,
			String SFZH,
			String JKLX) {
	
		try {
			URL url = new URL(POST_URL + "appapi00153.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330726198810170330";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5976&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&certinum="+certinum
						+ "&accname="+accname
						+ "&powerMatters="+powerMatters
						+ "&subPowerMatters="+subPowerMatters
						+ "&materialName="+materialName
						+"&FYQSSJ="+FYQSSJ
						+"&JYLX="+JYLX
						+"&FYJSSJ="+FYJSSJ
						+"&SFZH="+SFZH
						+"&JKLX="+JKLX;

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

    private static String change(String input) {  
    	String output = null;
        try {  
            output = new String(input.getBytes("iso-8859-1"),"utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }
		return output; 
    }  
	public static void main(String[] args) throws UnknownHostException {
			for (int i = 1; i < 20; i++) {
				long starTime=System.currentTimeMillis();
				httpURLConnectionPOST5976("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"20151113","","20181113","330206195712254921","Nbyb-Yspt1002");		
				/*httpURLConnectionPOST5976("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"20151113","","20181113","330203201610280626","Nbyb-Yspt1002");		
				httpURLConnectionPOST5976("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"20151113","","20181113","330227195507036812","Nbyb-Yspt1002");		
				httpURLConnectionPOST5976("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"20151113","","20181113","330212201805281035","Nbyb-Yspt1002");		
				httpURLConnectionPOST5976("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"20151113","","20181113","330226198308035283","Nbyb-Yspt1002");
				httpURLConnectionPOST5976("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"20151113","","20181113","330205195312193916","Nbyb-Yspt1002");*/

//				楼泽萱330203201610280626（婴幼儿）
//				张国华330227195507036812（成年居民A）
//				楼星泽330212201805281035（统筹人员）
//				曹灵丽43100219840624054X（职工险种）个人医疗负担证明测试案例
				
//				FYQSSJ:费用所属起始时间(格式：YYYYMMDD)
//				JYLX:就医类型(可为空，为空时查询所有， 具体如字典说明)
//				FYJSSJ:费用所属截止时间(格式：YYYYMMDD)
//				SFZH:身份证号(身份证号)
//				JKLX:接口类型(Nbyb-Yspt1002)
				long endTime=System.currentTimeMillis();
				long Time=endTime-starTime;
				System.out.println("请求大数据平台耗时"+Time+"毫秒");
			}
	}



}
