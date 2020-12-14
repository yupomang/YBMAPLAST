package com.yondervision.mi.test.zhengwujiqiren.shengchan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

//个人公积金登录验证
public class HttpSend5432appapi50006 {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "1f93b24ff9ee856f153b8c5605ea8eb4";
	private static final String appId_V = "yondervisionwebservice51";
	private static final String clientIp_V="172.10.0.1";
	/**
	 * 个人公积金登录验证
	 */
	public static String httpURLConnectionPOST5432(String logintype,String bodyCardNumber, String accnum, String pwd,String tel, String checkcode, String checkflag) {
		try {
			URL url = new URL(POST_URL + "appapi50006.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "intelligentRobot";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008"
								+ "&tranDate=2017-09-19&channelSeq="
								+ "&bodyCardNumber="+ bodyCardNumber + "&password=" + pwd + "&accnum=" + accnum+ "&logintype=" + logintype+ 
								"&tel=" +tel+"&checkcode=" +checkcode+"&checkflag=" +checkflag;
			String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			// 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=51";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=51";
            parm1 = parm1.replaceAll("\r|\n", "");
//			System.out.println("本地参数" + parm);
//			System.out.println("headparaMD5：" + RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
//			System.out.println("传递参数：" + parm1);

//			File file=new File("E:/book.txt");
//	        try{
//	            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("E:/book.txt"),true));
////	            writer.write("\n"+RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
//	            writer.write("\n"+parm1);
//	            writer.close();
//	        }catch(Exception e){
//	            e.printStackTrace();
//	        }
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
//			String result=aes.decrypt(sb.toString());
			String result=sb.toString();
			System.out.println(result);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	
	public static void main(String[] args) throws UnknownHostException {
//		httpURLConnectionPOST5432("2", "330726198810170330", "", "123456","","","");//身份证密码登录
//		httpURLConnectionPOST5432("5", "", "0237425912", "111111","","","");//公积金账号密码登录		
//		httpURLConnectionPOST5432("2", "330205198909033329", "0237550054", "123456","","","");//身份证、公积金账号密码登录				
//		httpURLConnectionPOST5432("2", "330205198909033329", "","","15161178395","","0");//身份证，手机号发送验证码
//		httpURLConnectionPOST5432("2", " 330726198810170330", "","","15867529638","","0");//身份证，手机号发送验证码
//		httpURLConnectionPOST5432("2", "142228196711102730", "","","13034611159","497340","1");//身份证，手机号，验证码登录
//		httpURLConnectionPOST5432("2", "0237425912", "", "111111","","","");//身份证密码登录
		 httpURLConnectionPOST5432("2","330726198810170330 ", "", "123456","","","");
	}
}
