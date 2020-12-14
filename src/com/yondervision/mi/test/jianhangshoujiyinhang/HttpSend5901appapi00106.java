package com.yondervision.mi.test.jianhangshoujiyinhang;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;
//婚姻登记信息查询
public class HttpSend5901appapi00106 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "ugptea6iv8r67r2luuejzk16yk6g2f36";
	private static final String appId_V = "yondervisionwebservice92";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5901(String bodyCardNumber,String sex,String birthday,String fullName) {
		try {
			URL url = new URL(POST_URL + "appapi00106.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330204197508036114";
			AesTestJiianHangShouJi aes = null;
			try {
				aes = new AesTestJiianHangShouJi();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5901&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
								+ "&bodyCardNumber="+bodyCardNumber
								+ "&sex="+sex
								+ "&birthday="+birthday
								+ "&fullName="+fullName;

			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=92";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=92";
			//System.out.println("本地参数" + parm);
			//System.out.println("传递参数：" + parm1);
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
//			String result=change(aes.decrypt(sb.toString()));
			String result=aes.decrypt(sb.toString());
//			String result=change(sb.toString());
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
		httpURLConnectionPOST5901("330902196810059537", "M", "1968-10-05","林维忠");
//		httpURLConnectionPOST5901("330204197606293026", "F", "1976-06-29","吴盛");
//			httpURLConnectionPOST5901("330107660919091", "M", "1966-09-19","李明");
			//httpURLConnectionPOST5901("330726198810170330", "M", "1988-10-17","张鹏程");
//			httpURLConnectionPOST5901("330205197109290322", "F", "1971-09-29","张淑波");
//			httpURLConnectionPOST5901("330203197709181813", "M", "1977-09-18","褚晖");
//			httpURLConnectionPOST5901("33102319831019441X", "M", "1983-10-19","陈胜");
//			httpURLConnectionPOST5901("330204197508036114", "M", "1975-08-03","邵建宁");
//			httpURLConnectionPOST5901("33022519870923034X", "F", "1987-09-23","林丹姝");			
//			for (int i = 0; i < 100; i++) {
//				httpURLConnectionPOST5901("330204197508036114", "M", "1975-08-03","邵建宁");
//			}
			

			
			/*		
			 * 身份证号：bodyCardNumber
			 性别：sex
			 生日：birthday（YYYY-MM-DD）
			 姓名：fullName*/

	}
}
