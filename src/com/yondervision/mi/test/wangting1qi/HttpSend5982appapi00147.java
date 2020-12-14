package com.yondervision.mi.test.wangting1qi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;
//部门申报系统获取统一办件编码接口
public class HttpSend5982appapi00147 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5982(
			String certinum,
			String accname,
			String powerMatters,
			String subPowerMatters,
			String materialName,
			String	applyCardType,
			String	belongSystem,
			String	applyName,
			String	applyCardNumber,
			String	busType,
			String	deptId,
			String	applyFrom,
			String	areaCode,
			String	serviceCodeId) {
		try {
			URL url = new URL(POST_URL + "appapi00147.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5982&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&certinum="+certinum
						+ "&accname="+accname
						+ "&powerMatters="+powerMatters
						+ "&subPowerMatters="+subPowerMatters
						+ "&materialName="+materialName
						
						+"&applyCardType="+applyCardType
						+"&belongSystem="+belongSystem
						+"&applyName="+applyName
						+"&applyCardNumber="+applyCardNumber
						+"&busType="+busType
						+"&deptId="+deptId
						+"&applyFrom="+applyFrom
						+"&areaCode="+areaCode
						+"&serviceCodeId="+serviceCodeId;

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
			for (int i = 1; i < 2; i++) {
				long starTime=System.currentTimeMillis();
				httpURLConnectionPOST5982("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",
						"31","33020119005","张鹏程","330726198810170330","0","001008002016015","0","330201","10-02491-005");

/*				
				 &certinum=330726198810170330
				 &accname=张鹏程
				 &powerMatters=其他-02491-000
				 &subPowerMatters=其他-02491-005
				 &materialName="出境定居提取住房公积金
				 &applyCardType=31
				&belongSystem=33020119005
				&applyName=张鹏程
				&applyCardNumber=330726198810170330
				&busType=0				
				&deptId=001008002016015
				&applyFrom=0
				&areaCode=330201
				&serviceCodeId=10-02491-005*/
				
//				申请者证件类型	applyCardType
//				所属系统	belongSystem
//				申请者名称	applyName
//				申请者证件号码	applyCardNumber
//				业务类型	busType
//				收件部门编码	deptId
//				申报来源类型	applyFrom
//				收件部门所属行政区划编码	areaCode
//				权利事项基本码	serviceCodeId
				
				long endTime=System.currentTimeMillis();
				long Time=endTime-starTime;
				System.out.println("请求大数据平台耗时"+Time+"毫秒");
			}
	}



}
