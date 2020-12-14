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
//省工商局营业执照
public class HttpSend5969appapi00161 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5969(
			String certinum,
			String accname,
			String powerMatters,
			String subPowerMatters,
			String materialName,
			String regno,
			String entname,
			String uniscid) {
		try {
			URL url = new URL(POST_URL + "appapi00161.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5969&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&certinum="+certinum
						+ "&accname="+accname
						+ "&powerMatters="+powerMatters
						+ "&subPowerMatters="+subPowerMatters
						+ "&materialName="+materialName
						+"&regno="+regno
						+"&entname="+entname
						+"&uniscid="+uniscid;
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
			for (int i = 1; i < 10; i++) {
				long starTime = System.currentTimeMillis();
				httpURLConnectionPOST5969("330205199002120314","赵马甬","其他-02490-000","其他-02490-000","法定代表人","330212000812170","浙江诚旭供应链管理有限公司","91330212MA2CK56F5H"); 
				/*httpURLConnectionPOST5969("330205199002120314","赵马甬","其他-02493-000","其他-02493-002","单位负责人姓名","330212000812170","浙江诚旭供应链管理有限公司","91330212MA2CK56F5H");
				httpURLConnectionPOST5969("330205199002120314","赵马甬","其他-02493-000","其他-02493-002","证件号码","330212000812170","浙江诚旭供应链管理有限公司","91330212MA2CK56F5H"); 
				httpURLConnectionPOST5969("330205199002120314","赵马甬","其他-02493-000","其他-02493-002","证件种类","330212000812170","浙江诚旭供应链管理有限公司","91330212MA2CK56F5H");
*/				long endTime = System.currentTimeMillis();
				long Time = endTime - starTime;
				System.out.println("请求大数据平台耗时" + Time + "毫秒");
			}
	}



}
