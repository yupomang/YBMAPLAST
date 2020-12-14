package com.yondervision.mi.test.wangting1qi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.CommonUtil;
import com.yondervision.mi.util.security.Base64Decoder;
import com.yondervision.mi.util.security.RSASignature;

public class HttpSendWangTing {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	
	//public static final String POST_URL = "http://172.16.10.96:7001/YBMAPZH/";
	private static final String userId_V = "330205198909033329";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	private static final String brcCode="05740008";

	/**
	 * 个人账户查询（用于反显）
	 * @return
	 */
    //所有入口参数c
	public static String httpURLConnectionPOST5007(String certinum, String accnum) {
		try {			
			URL url = new URL(POST_URL + "appapi00104.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,brcCode,accnum");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String userId = aes.encrypt("330205198909033329".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String clientIp = aes.encrypt(clientIp_V.getBytes("UTF-8"));
			
			if(!CommonUtil.isEmpty(certinum)){
				certinum = aes.encrypt(certinum.getBytes("UTF-8"));
			}
			System.out.println("certinum="+certinum);
			if(!CommonUtil.isEmpty(accnum)){
				accnum = aes.encrypt(accnum.getBytes("UTF-8"));
			}
			System.out.println("accnum="+accnum);
			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5007&devtoken=&channel=40&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp="+clientIp+"&brcCode="
							+ brcCode+"&accnum="+accnum;			

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5007&devtoken=&channel=40&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp="+clientIp.replace("+", "%2B")+  "&brcCode="
					+ brcCode.replace("+", "%2B")+"&certinum="
					+ certinum.replace("+", "%2B")+"&accnum="
					+ accnum.replace("+", "%2B");
			
			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);
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
	 * 个人公积金密码修改
	 * @return
	 */
	public static String httpURLConnectionPOST5448(String bodyCardNumber,
			String accnum, String pwd, String newpassword, String confirmnewpassword, String brcCode) {
		try {
			URL url = new URL(POST_URL + "appapi50009.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,"
							+ "bodyCardNumber,password,newpassword,confirmnewpassword,accnum");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String userId = "";
			if(null != bodyCardNumber && !"".equals(bodyCardNumber)){
				userId = aes.encrypt(bodyCardNumber.getBytes("UTF-8"));
			}else{
				userId = aes.encrypt(accnum.getBytes("UTF-8"));
			}
			
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String clientIp = aes.encrypt(clientIp_V.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));
			newpassword = aes.encrypt(newpassword.getBytes("UTF-8"));
			confirmnewpassword= aes.encrypt(confirmnewpassword.getBytes("UTF-8"));

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5448&devtoken=&channel=40&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp="+clientIp+"&bodyCardNumber="
					+ bodyCardNumber + "&password=" + pwd + "&newpassword="
					+ newpassword + "&confirmnewpassword=" + confirmnewpassword
					+ "&accnum=" + accnum;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5448&devtoken=&channel=40&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp="+clientIp+"&bodyCardNumber="
					+ bodyCardNumber.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B") + "&newpassword="
					+ newpassword.replace("+", "%2B") + "&confirmnewpassword="
					+ confirmnewpassword.replace("+", "%2B") + "&accnum="
					+ accnum.replace("+", "%2B")+ "&brcCode="
							+ brcCode.replace("+", "%2B");

			System.out.println("parm" + parm);
			System.out.println("传递参数：" + parm1);

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
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 单位公积金密码修改
	 */
	public static String httpURLConnectionPOST5463(String unitaccnum,String pwd, String newpasswd,String confirmnewpassword) {
		try {
			URL url = new URL(POST_URL + "appapi50015.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,unitaccnum,password,newpasswd,confirmnewpassword");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = aes.encrypt("330211136954".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String clientIp = aes.encrypt(clientIp_V.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));
			newpasswd = aes.encrypt(newpasswd.getBytes("UTF-8"));
			confirmnewpassword=aes.encrypt(confirmnewpassword.getBytes("UTF-8"));
			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5463&devtoken=&channel=40&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp="+clientIp+"&unitaccnum="
					+ unitaccnum + "&password=" + pwd + "&newpasswd="
					+ newpasswd + "&confirmnewpassword=" + confirmnewpassword;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5463&devtoken=&channel=40&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp="+clientIp+"&unitaccnum="
					+ unitaccnum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B") + "&newpasswd="
					+ newpasswd.replace("+", "%2B") + "&confirmnewpassword="
					+ confirmnewpassword.replace("+", "%2B");

			System.out.println("parm" + parm);
			System.out.println("传递参数：" + parm1);

			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),
					RSASignature.RSA_PRIVATE));
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
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 个人账户查询
	 */
	public static String httpURLConnectionPOST5001() {
		try {
			URL url = new URL(POST_URL + "appapi00101.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5001&devtoken=&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V;
			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
			System.out.println("本地参数" + parm);
			System.out.println("传递参数：" + parm1);
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
			String userId = "";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&bodyCardNumber="+ bodyCardNumber + "&password=" + pwd + "&accnum=" + accnum+ "&logintype=" + logintype;
			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
			System.out.println("本地参数" + parm);
			System.out.println("传递参数：" + parm1);
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
	public static String httpURLConnectionPOST5461(String unitaccnum, String pwd,String dlfs) {
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
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,unitaccnum,password,dlfs");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt(unitaccnum.getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String clientIp = aes.encrypt(clientIp_V.getBytes("UTF-8"));
			unitaccnum = aes.encrypt(unitaccnum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5461&devtoken=&channel=40&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp="+clientIp+"&unitaccnum="
					+ unitaccnum + "&password=" + pwd+ "&dlfs=" + dlfs;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5461&devtoken=&channel=40&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp="+clientIp+"&unitaccnum="
					+ unitaccnum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B")+ "&dlfs=" + dlfs.replace("+", "%2B");

			System.out.println("parm" + parm);
			System.out.println("传递参数：" + parm1);

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
	 * 单位账户查询
	 * @return
	 */
	public static String httpURLConnectionPOST5801(String unitaccnum, String brcCode) {
		try {
			URL url = new URL(POST_URL + "appapi02001.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,unitaccnum");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt("330211136954".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String clientIp = aes.encrypt(clientIp_V.getBytes("UTF-8"));

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5801&devtoken=&channel=40&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp="+clientIp+"&unitaccnum="
					+ unitaccnum;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5801&devtoken=&channel=40&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp="+clientIp+"&unitaccnum="
					+ unitaccnum.replace("+", "%2B") +  "&brcCode="
							+ brcCode.replace("+", "%2B");

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
	 * 个人基数调整
	 * @return
	 */
	public static String httpURLConnectionPOST5809(String unitaccnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi03701.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,unitaccnum,pwd");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt("330211136954".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String clientIp = aes.encrypt(clientIp_V.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5801&devtoken=&channel=40&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp="+clientIp+"&unitaccnum="
					+ unitaccnum + "&pwd=" + pwd;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5801&devtoken=&channel=40&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp="+clientIp+"&unitaccnum="
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
			DataOutputStream dataout = new DataOutputStream(
					connection.getOutputStream());
			System.out.println("传递参数：" + parm1);
			dataout.writeBytes(parm1);
			dataout.flush();
			dataout.close();
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
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
	 * 个人密码重置获取验证码
	 * @return
	 */
	public static String httpURLConnectionPOST5088(String bodyCardNumber, String fullName,String tel) {
		try {
			URL url = new URL(POST_URL + "appapi50032.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,fullName,tel");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt("330205198909033329".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String clientIp = aes.encrypt(clientIp_V.getBytes("UTF-8"));
			bodyCardNumber = aes.encrypt(bodyCardNumber.getBytes("UTF-8"));
			fullName = aes.encrypt(fullName.getBytes("UTF-8"));
			tel = aes.encrypt(tel.getBytes("UTF-8"));
			
			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5088&devtoken=&channel=40&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp="+clientIp+"&fullName="
					+ fullName + "&tel=" + tel;
			

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5088&devtoken=&channel=40&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp="+clientIp+"&fullName="
					+ fullName.replace("+", "%2B") + "&tel="
					+ tel.replace("+", "%2B")+ "&bodyCardNumber="+ bodyCardNumber.replace("+", "%2B");

			System.out.println("parm" + parm);
			System.out.println("传递参数：" + parm1);

			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),
					RSASignature.RSA_PRIVATE));
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
	 * 个人密码重置
	 * 
	 * @return
	 */
	public static String httpURLConnectionPOST5437(String bodyCardNumber, String fullName,String tel, String checkcode) {
		try {
			URL url = new URL(POST_URL + "appapi50007.json");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);

			connection.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,fullName,tel,checkcode");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt("330205198909033329".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String clientIp = aes.encrypt(clientIp_V.getBytes("UTF-8"));
			bodyCardNumber = aes.encrypt(bodyCardNumber.getBytes("UTF-8"));
			fullName = aes.encrypt(fullName.getBytes("UTF-8"));
			tel = aes.encrypt(tel.getBytes("UTF-8"));
			checkcode = aes.encrypt(checkcode.getBytes("UTF-8"));
			

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5437&devtoken=&channel=40&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp="+clientIp+"&fullName="
					+ fullName + "&tel=" + tel + "&checkcode=" + checkcode;
			

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5437&devtoken=&channel=40&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp="+clientIp+"&fullName="
					+ fullName.replace("+", "%2B") + "&tel="
					+ tel.replace("+", "%2B")+ "&checkcode="
							+ checkcode.replace("+", "%2B")+ "&bodyCardNumber="
									+ bodyCardNumber.replace("+", "%2B");

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
	 * 单位资料变更
	 */
	public static String httpURLConnectionPOST5809( String unitaccnum,
			String   mngdept,
			String   tradetype,
			String   unitkind,
			String   unitlinkphone,
			String   unitlinkman,
			String   apprnum,
			String   linkmancertinum,
			String   unitaccname,
			String   agentdept,
			String   unitlinkphone2,
			String   zip,
			String   linkmancertitype,
			String   linkmanemail,
			String   unitaddr,
			String   unitareacode,
			String   leglaccname,
			String   unitcustid,
			String   unitsoicode,
			String   supsubrelation) {
		try {
			URL url = new URL(POST_URL + "appapi03701.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty(
								"headpara",
								"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,"
			+ "mngdept,tradetype,unitkind,unitlinkphone");

			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String clientIp = aes.encrypt(clientIp_V.getBytes("UTF-8"));
			String userId = aes.encrypt(unitaccnum.getBytes("UTF-8"));
			unitlinkman=URLEncoder.encode(unitlinkman, "utf-8");
			unitaccname=URLEncoder.encode(unitaccname, "utf-8");
			unitaddr=URLEncoder.encode(unitaddr, "utf-8");
			leglaccname=URLEncoder.encode(leglaccname, "utf-8");
			String parm = "";
				// 用于数字签名
				parm = "centerId=00057400&userId="+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5809&devtoken=&channel=40&appid="
						+ appId+ "&appkey="+ appKey
						+ "&appToken=&clientIp="+ clientIp  
						+ "&mngdept="+ mngdept  
						+"&tradetype=" + tradetype 
						+ "&unitkind=" + unitkind
						+ "&unitlinkphone=" + unitlinkphone;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5809&devtoken=&channel=40&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp="+ clientIp.replace("+","%2B")  
					+"&mngdept="+mngdept.replace("+","%2B")
					+"&tradetype="+tradetype.replace("+","%2B")
					+"&unitkind="+unitkind.replace("+","%2B")
					+"&unitlinkphone="+unitlinkphone.replace("+","%2B")
					+"&unitlinkman="+unitlinkman.replace("+","%2B")
					+"&apprnum="+apprnum.replace("+","%2B")
					+"&linkmancertinum="+linkmancertinum.replace("+","%2B")
					+"&unitaccname="+unitaccname.replace("+","%2B")
					+"&agentdept="+agentdept.replace("+","%2B")
					+"&unitlinkphone2="+unitlinkphone2.replace("+","%2B")
					+"&zip="+zip.replace("+","%2B")
					+"&linkmancertitype="+linkmancertitype.replace("+","%2B")
					+"&linkmanemail="+linkmanemail.replace("+","%2B")
					+"&unitaddr="+unitaddr.replace("+","%2B")
					+"&unitareacode="+unitareacode.replace("+","%2B")
					+"&leglaccname="+leglaccname.replace("+","%2B")
					+"&unitcustid="+unitcustid.replace("+","%2B")
					+"&unitsoicode="+unitsoicode.replace("+","%2B")
					+"&supsubrelation="+supsubrelation.replace("+","%2B");					
					
			System.out.println("parm" + parm);
			System.out.println("传递参数：" + parm1);

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
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	
	/**
	 * 单位明细账查询
	 * @return
	 */
    //所有入口参数c
	public static String httpURLConnectionPOST5802(String brcCode,String unitaccnum, String querytype,String chgtype,String begdate,String enddate,String ispaging,String pagerows,String pagenum) {
		try {			
			//需要修改appapi
			URL url = new URL(POST_URL + "appapi02102.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			//需要修改入口参数，写两三个
			//"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,不要修改
			connection.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,unitaccnum,querytype");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt("330211136954".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String clientIp = aes.encrypt(clientIp_V.getBytes("UTF-8"));

			//同上buzType
			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5802&devtoken=&channel=40&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp="+clientIp+"&unitaccnum="
					+ unitaccnum+"&querytype="+querytype;
			
			// 填上所有入口参数
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5802&devtoken=&channel=40&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp="+clientIp.replace("+", "%2B")+  "&brcCode="
					+ brcCode.replace("+", "%2B")+"&unitaccnum="
					+ unitaccnum.replace("+", "%2B")+"&querytype="
					+ querytype.replace("+", "%2B")+"&chgtype="
					+ chgtype.replace("+", "%2B")+"&begdate="
					+ begdate.replace("+", "%2B")+"&enddate="
					+ enddate.replace("+", "%2B")+"&ispaging="
					+ ispaging.replace("+", "%2B")+"&pagerows="
					+ pagerows.replace("+", "%2B")+"&pagenum="
					+ pagenum.replace("+", "%2B");
			
			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);

			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),
					RSASignature.RSA_PRIVATE));
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
	 * 多级字典及摘要接口
	 */
	public static String httpURLConnectionPOST5850() {
		try {
			URL url = new URL(POST_URL + "appapi50033.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330211136954";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5850&devtoken=&tranDate=2017-09-19&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V;
/*			 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5850&devtoken=&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V;*/
			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
			System.out.println("本地参数" + parm);
			System.out.println("传递参数：" + parm1);
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
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException {
		
		//个人账户查询(已OK)
//		System.out.println("个人账户查询");
//		httpURLConnectionPOST5001();
		
		//个人账户查询（用于反显）(已OK)
//		System.out.println("个人账户查询（用于反显）");
//		long starTime=System.currentTimeMillis();
//		httpURLConnectionPOST5007("330205198909033329","");
//		long endTime=System.currentTimeMillis();
//		long Time=endTime-starTime;
//		System.out.println(Time);
//		httpURLConnectionPOST5007("", "0237550054");

		
		//个人公积金登录验证 用身份证号时 logintype赋值2 公积金账号 logintype赋值5(已OK)
//		System.out.println(" 个人公积金登录验证 用身份证号时 logintype赋值2 公积金账号 logintype赋值5");
//		httpURLConnectionPOST5432("2", "330205198909033329", "", "111111","","","");//身份证密码登录
		httpURLConnectionPOST5432("5", "", "0237550054", "111111","","","");//公积金账号密码登录		
//		httpURLConnectionPOST5432("5", "330205198909033329", "0237550054", "123456","","","");//身份证密码登录		
		
//		httpURLConnectionPOST5432("2", "330205198909033329", "","","15990240206","","0");//身份证，手机号发送验证码
//		httpURLConnectionPOST5432("2", "330203198212122417", "","","15161178395","","0");//身份证，手机号发送验证码
		
//		httpURLConnectionPOST5432("2", "330205198909033329", "","","15990240206","783094","1");//身份证，手机号，验证码登录
		
//		httpURLConnectionPOST5432("5", "", "0237550054","","15990240206","","0");//公积金账号，手机号发送验证码
//		httpURLConnectionPOST5432("5", "", "0060490636","","15161178395","","0");//公积金账号，手机号发送验证码
		
//		httpURLConnectionPOST5432("5", "", "0237550054","","15990240206","783094","1");//公积金账号，手机号，验证码登录
		//signer：面签标志；1-是；0-否
		
//		//单位公积金登陆验证(已OK)
//		System.out.println("单位公积金登陆验证");
//		httpURLConnectionPOST5461("011500001167", "891801","0");//一般登录
//		httpURLConnectionPOST5461("011500001167", "891801","1");//U盾登录
		
//		//个人公积金密码修改(已OK)
//		System.out.println(" 个人公积金密码修改");
//		httpURLConnectionPOST5448("330205198909033329", "", "123456", "123456", "123456","05742222");
//		httpURLConnectionPOST5448("", "0237550054", "123456", "123456", "123456");
//
//		//单位公积金密码修改(已OK)
//		System.out.println("单位公积金密码修改");
//		httpURLConnectionPOST5463("330211136954", "111111", "111111","111111");
		
//		//获取短信验证码(已OK)写入数据库pb309
//		 System.out.println("获取短信验证码");
// 		 httpURLConnectionPOST5088("330205198909033329", "胡炯", "15990240206") ;
		
//		//个人账号密码重置(已OK)测试时验证码从数据库里获取最新值
//		 System.out.println("个人账号密码重置");
//		 httpURLConnectionPOST5437("330205198909033329", "胡炯", "15990240206", "319287") ;

//		//单位账户查询(已OK)
//		System.out.println("单位账户查询");
//		httpURLConnectionPOST5801("330211136954", "0570008");
		

		//单位资料变更
/*		System.out.println("单位资料变更");
		httpURLConnectionPOST5809( 
				"011500001167",//uniaccnum
				"01",//mngdept
				"S",//tradetype
				"6",//unitkind
				"15161178395",//unitlinkphone
				"宋豪",//unitlinkman
				"",//apprnum
				"341226199208125799",//linkmancertinum
				"宁波市住房公积金管理中心",//unitaccname
				"",//agentdept
				"87978803",//unitlinkphone2
				"315000",//zip
				"1",//linkmancertitype
				"",//linkmanemail
				"宁波市解放南路11号",//unitaddr
				"04",//unitareacode
				"陈志明1",//leglaccname
				"AU00012202",//unitcustid
				"",//unitsoicode
				"1"//supsubrelation
				 ) ;*/
		httpURLConnectionPOST5850();
		//单位业务——单位账户发生额明细批量查询下载
		//httpURLConnectionPOST5802("05740008","330211136954", "1","1219","20170101","20170901","1","10","2");
		
/*		String w ="qo3HltpJlLCtDHk1wAOGptiMVTmac1BiEnsSXfNNh3m1Doc9Jvb5x23FyRAP4RxpFfvo4Qr8hg0RvCbwSVjiQva086fKdtKK%2BMqfXDAgq3bNxnTNXXIPqWfxmtiqK2g0b9nhnHz7abo7iWZww48nenh0EGIuOw%2B/6qBwDB3c%2B2dPNPrHWnpBo%2BIXJDFZ6afc4ILkVqruhL2Ql2EjDPg0ImXsM2n62fB1uF1zm/vo5TgH%2BTKPsLzYFy/nGlLYTVYty01DWx980vn3xOGTrdXLb7dDFFgFAxxgff4XuT28FDtRhmxGMM1kTU9z9nJFCC%2BPZtiK25wEkSh11Vf/kh5tiS8%2BeK0QRT7CYN1EIFM9TRM=".replace("%2B", "+");
		String m="XDsMa4jFp6d1ivoWqxfLHdZcJMhHy9x0P8pLuZC409I4I6GqY5QyJhuQP1h6TqhW6hW5Nl6F4ePytFBpyHFjc9Hx3/IuWymwr0mlFyBEI8BxorMaKageBZhOjHZWlRjOPmilP9Twqb2FRCL9UVgyUOrKTkOcCM6Hqy0GcPBho0aie625v9Bx2200sqTstJRZyrJVSflLkjIsKtma1oYuFVAUCG8aJyn0TQXFSS/mObnj6Npgc0wqcMHrkHs/21K6QTdxTk00BrqJNJMbVojqgQ==";
		AesTest aes = null;
		try {
			aes = new AesTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 String fullPara =aes.decrypt(m);
		 System.out.println(fullPara);*/
	}
}
