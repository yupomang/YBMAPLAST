package com.yondervision.mi.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

public class HttpSendqqqqqqqqqqqqq {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "ba7f3a3dd146c4613102d0a16be5107b";
	private static final String appId_V = "yondervisionwebsite30";

	/**
	 * 个人账户查询
	 */
	public static String httpURLConnectionPOST5001(String certinum,String accnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi00101.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			if (null != certinum && !"".equals(certinum)) {
				connection
						.addRequestProperty(
								"headpara",
								"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,password,accnum");
			} else {
				connection
						.addRequestProperty(
								"headpara",
								"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,password,accnum");
			}
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = "0076458492";
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			
			String parm = "";
			if (null != certinum && !"".equals(certinum)) {
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5001&devtoken=&channel=30&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=10.33.12.19&bodyCardNumber="
						+ certinum + "&password=" + pwd + "&accnum=" + accnum;
			} else {
				// 用于数字签名
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5001&devtoken=&channel=30&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=10.33.12.19&password="
						+ pwd + "&accnum=" + accnum;
			}
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5001&devtoken=&channel=30&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=10.33.12.19&bodyCardNumber="
					+ certinum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B") + "&accnum="
					+ accnum.replace("+", "%2B");
			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);
			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),
					RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			System.out.println("传递参数：" + parm1);
			dataout.writeBytes(parm1);
			dataout.flush();
			dataout.close();
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = bf.readLine()) != null) {
				sb.append(line).append(System.getProperty("line.separator"));
			} // 重要且易忽略步骤 (关闭流,切记!)
			bf.close();
			connection.disconnect(); // 销毁连接
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 个人公积金登录验证
	 */
	public static String httpURLConnectionPOST5432(String logintype,
			String certinum, String accnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi50006.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			if (null != certinum && !"".equals(certinum)) {
				connection
						.addRequestProperty(
								"headpara",
								"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,password,accnum,logintype");
			} else {
				connection
						.addRequestProperty(
								"headpara",
								"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,password,accnum,logintype");
			}
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = "";
			// userId必须加密处理
			if(null != certinum && !"".equals(certinum)){
				userId = certinum;
			}else{
				userId = accnum;
			}
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			
			
			String parm = "";
			if (null != certinum && !"".equals(certinum)) {
				// 用于数字签名
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=30&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=10.33.12.19&bodyCardNumber="
						+ certinum + "&password=" + pwd + "&accnum=" + accnum
						+ "&logintype=" + logintype;
			} else {
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=30&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=10.33.12.19&password="
						+ pwd + "&accnum=" + accnum + "&logintype=" + logintype;
			}
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=30&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=10.33.12.19&bodyCardNumber="
					+ certinum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B") + "&accnum="
					+ accnum.replace("+", "%2B") + "&logintype="
					+ logintype.replace("+", "%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);

			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),
					RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			System.out.println("传递参数：" + parm1);
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
			connection.disconnect(); // 销毁连接
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 单位公积金登陆验证
	 */
	public static String httpURLConnectionPOST5461(String unitaccnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi50013.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,unitaccnum,password");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = "330211136954";
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));	

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5461&devtoken=&channel=30&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=10.33.12.19&unitaccnum="
					+ unitaccnum + "&password=" + pwd;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5461&devtoken=&channel=30&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=10.33.12.19&unitaccnum="
					+ unitaccnum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);

			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			System.out.println("传递参数：" + parm1);
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
			connection.disconnect(); // 销毁连接
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 单位账户查询
	 */
	public static String httpURLConnectionPOST5801(String unitaccnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi02001.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection
					.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,unitaccnum,pwd");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String userId = "330211136954";
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5801&devtoken=&channel=30&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=10.33.12.19&unitaccnum="
					+ unitaccnum + "&pwd=" + pwd;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5801&devtoken=&channel=30&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=10.33.12.19&unitaccnum="
					+ unitaccnum.replace("+", "%2B") + "&pwd="
					+ pwd.replace("+", "%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);
			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),
					RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			System.out.println("传递参数：" + parm1);
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
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnknownHostException {
		
    	//个人公积金登录验证 用身份证号时 logintype赋值2 公积金账号 logintype赋值5
//		System.out.println(" 个人公积金登录验证 用身份证号时 logintype赋值2 公积金账号 logintype赋值5");
//		httpURLConnectionPOST5432("2", "330726198810170330", "", "123456");
//		httpURLConnectionPOST5432("5", "", "0076458492", "123456");


	//个人账户查询
//		System.out.println("个人账户查询");
//		httpURLConnectionPOST5001("330726198810170330", "", "123456");
//		httpURLConnectionPOST5001("", "0076458492", "123456");

		//单位公积金登陆验证
//		System.out.println("单位公积金登陆验证");
//		httpURLConnectionPOST5461("330211136954", "111111");

		//单位账户查询
//		System.out.println("单位账户查询");
		httpURLConnectionPOST5801("330211136954", "111111");
	}

}
