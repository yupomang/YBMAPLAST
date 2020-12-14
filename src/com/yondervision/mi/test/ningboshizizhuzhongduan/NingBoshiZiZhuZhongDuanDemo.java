package com.yondervision.mi.test.ningboshizizhuzhongduan;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class NingBoshiZiZhuZhongDuanDemo {

	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "78ce07bdcdf4877f56305895659defc6";
	private static final String appId_V = "yondervisionselfservice53";

	/**
	 * 个人公积金登录验证
	 */
	public static String httpURLConnectionPOST5432(String logintype,
			String certinum, String accnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi50006.json");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
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
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = "";
			// userId必须加密处理
			if(null != certinum && !"".equals(certinum)){
				userId = aes.encrypt(certinum.getBytes("UTF-8"));
			}else{
				userId = aes.encrypt(accnum.getBytes("UTF-8"));
			}
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));

			certinum = aes.encrypt(certinum.getBytes("UTF-8"));
			accnum = aes.encrypt(accnum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));
			String parm = "";
			if (null != certinum && !"".equals(certinum)) {
				// 用于数字签名
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=53&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
						+ certinum + "&password=" + pwd + "&accnum=" + accnum
						+ "&logintype=" + logintype;
			} else {
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=53&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&password="
						+ pwd + "&accnum=" + accnum + "&logintype=" + logintype;
			}
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ certinum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B") + "&accnum="
					+ accnum.replace("+", "%2B") + "&logintype="
					+ logintype.replace("+", "%2B");

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
	 * 个人公积金密码修改
	 * @return
	 */
	public static String httpURLConnectionPOST5448(String certinum,
			String accnum, String pwd, String newpassword) {
		try {
			URL url = new URL(POST_URL + "appapi50009.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection
					.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,password,newpassword,confirmnewpassword,accnum");
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String userId = "";
			// userId必须加密处理
			if(null != certinum && !"".equals(certinum)){
				userId = aes.encrypt(certinum.getBytes("UTF-8"));
			}else{
				userId = aes.encrypt(accnum.getBytes("UTF-8"));
			}

			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));
			newpassword = aes.encrypt(newpassword.getBytes("UTF-8"));

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5448&devtoken=&channel=53&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ certinum + "&password=" + pwd + "&newpassword="
					+ newpassword + "&confirmnewpassword=" + newpassword
					+ "&accnum=" + accnum;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5448&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ certinum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B") + "&newpassword="
					+ newpassword.replace("+", "%2B") + "&confirmnewpassword="
					+ newpassword.replace("+", "%2B") + "&accnum="
					+ accnum.replace("+", "%2B");

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
	 * 个人明细查询新
	 * 
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
			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp");
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = aes.encrypt("330726198810170330".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String parm = "centerId=00057400&userId="+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5001&devtoken=&channel=53&appid="
						+ appId+ "&appkey="+ appKey
						+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==" ;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5001&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==";
			System.out.println("parm" + parm);
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
	 * 贷款审批情况查询
	 */
	public static String httpURLConnectionPOST5445(String certinum,
			String accnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi01101.json");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			if (null != certinum && !"".equals(certinum)) {
				connection
						.addRequestProperty(
								"headpara",
								"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,pwd");
			} else {
				connection
						.addRequestProperty(
								"headpara",
								"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,accnum,pwd");
			}
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = "";
			userId = aes.encrypt("330726198810170330".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));

			// certinum = aes.encrypt(certinum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));
			String parm = "";
			if (null != certinum && !"".equals(certinum)) {
				// 用于数字签名
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5445&devtoken=&channel=53&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
						+ certinum + "&pwd=" + pwd;
			} else {
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5445&devtoken=&channel=53&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&accnum="
						+ accnum + "&pwd=" + pwd;
			}
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5445&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ certinum.replace("+", "%2B") + "&pwd="
					+ pwd.replace("+", "%2B")+"&accnum="+accnum.replace("+","%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);

			// System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),
					RSASignature.RSA_PRIVATE));
			// application/x-www-form-urlencoded->表单数据 ;charset=utf-8
			// 必须要，不然妙兜那边会出现乱码【★★★★★】
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			// 建立连接
			// (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
			connection.connect();
			// 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
			DataOutputStream dataout = new DataOutputStream(
					connection.getOutputStream());
			System.out.println("传递参数：" + parm1);
			// 将参数输出到连接
			dataout.writeBytes(parm1);
			// 输出完成后刷新并关闭流
			dataout.flush();
			// 重要且易忽略步骤 (关闭流,切记!)
			dataout.close();
			// System.out.println(connection.getResponseCode());
			// 连接发起请求,处理服务器响应 (从连接获取到输入流并包装为bufferedReader)
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;
			// 用来存储响应数据
			StringBuilder sb = new StringBuilder();
			// 循环读取流,若不到结尾处
			while ((line = bf.readLine()) != null) {
				// sb.append(bf.readLine());
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
	 * 贷款余额信息查询
	 */
	public static String httpURLConnectionPOST5073(String certinum,
			String accnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi00702.json");
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
								"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,pwd");
			} else {
				connection
						.addRequestProperty(
								"headpara",
								"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,accnum,pwd");
			}
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = "";
			userId = aes.encrypt("330726198810170330".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));

			// certinum = aes.encrypt(certinum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));
			String parm = "";
			if (null != certinum && !"".equals(certinum)) {
				// 用于数字签名
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5073&devtoken=&channel=53&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=CZ9S5oqIpufybtUFcEIBaw==&bodyCardNumber="
						+ certinum + "&pwd=" + pwd;
			} else {
				// 用于数字签名
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5073&devtoken=&channel=53&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=CZ9S5oqIpufybtUFcEIBaw==&accnum="
						+ accnum + "&pwd=" + pwd;
			}
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5073&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=CZ9S5oqIpufybtUFcEIBaw==&bodyCardNumber="
					+ certinum.replace("+", "%2B") + "&pwd="
					+ pwd.replace("+", "%2B")+"&accnum="+accnum.replace("+","%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);
			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),
					RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(
					connection.getOutputStream());
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
	 * 贷款余额信息查询
	 */
	public static String httpURLConnectionPOST5914(String accnum,String qdapprnum, String projectname) {

		try {
			URL url = new URL(POST_URL + "appapi03824.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,accnum,qdapprnum,projectname");
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = "";
			userId = aes.encrypt("330726198810170330".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String parm = "";
			// 用于数字签名
			parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5914&devtoken=&channel=53&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&accnum="
					+ accnum + "&qdapprnum=" + qdapprnum+ "&projectname=" + projectname;

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5914&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&accnum="
					+ accnum.replace("+", "%2B") + "&qdapprnum="
					+ qdapprnum.replace("+", "%2B")+"&projectname="+projectname.replace("+","%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);
			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
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
	
	public static String httpURLConnectionPOST5021(String accnum,String indiacctype, String startdate,String enddate,String ispaging,String pageflag,String pagenum) {

		try {
			URL url = new URL(POST_URL + "appapi00131.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,brcCode,accnum,indiacctype");
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}

//			String userId = aes.encrypt("330726198810170330".getBytes("UTF-8"));
			String userId = aes.encrypt("330682199408031287".getBytes("UTF-8"));

			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String parm = "";
			// 用于数字签名
			parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5021&devtoken=&channel=53&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008&accnum="
					+ accnum 
					+ "&indiacctype=" + indiacctype;

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5021&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008&accnum="
					+ accnum.replace("+", "%2B") 
					+"&indiacctype="+indiacctype.replace("+", "%2B")
					+"&startdate="+startdate.replace("+","%2B")
					+"&enddate="+enddate.replace("+","%2B")
					+"&ispaging="+ispaging.replace("+","%2B")
					+"&pageflag="+pageflag.replace("+","%2B")
					+"&pagenum="+pagenum.replace("+","%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);
			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
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
	
/*	通知公告*/
	public static String httpURLConnectionPOST5521(String classification,String pagenum,String pagerows) {
		try {
			URL url = new URL(POST_URL + "appapi70001.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,brcCode");
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}

					String userId = aes.encrypt("330682199408031287".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String parm = "";
			// 用于数字签名
			parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5521&devtoken=&channel=53&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008";

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5521&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008&classification="
					+ classification.replace("+", "%2B") 
					+"&pagenum="+pagenum.replace("+","%2B")
					+"&pagerows="+pagerows.replace("+", "%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);
			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
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

	/*	内容管理-详细信息/正文内容获取*/
	public static String httpURLConnectionPOST5521(String titleId) {
		try {
			URL url = new URL(POST_URL + "appapi70002.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,brcCode");
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = aes.encrypt("330682199408031287".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String parm = "";
			// 用于数字签名
			parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5521&devtoken=&channel=53&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008";

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5521&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008&titleId="
					+ titleId.replace("+", "%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);
			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
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
	/*	中心简介*/
	public static String httpURLConnectionPOST5525(String classification,String pagenum, String pagerows) {
		try {
			URL url = new URL(POST_URL + "appapi70001.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,brcCode");
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = aes.encrypt("330682199408031287".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String parm = "";
			// 用于数字签名
			parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5525&devtoken=&channel=53&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008";

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5525&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008&classification="
					+ classification.replace("+", "%2B") 
					+"&pagerows="+pagerows.replace("+", "%2B")
					+"&pagenum="+pagenum.replace("+","%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);
			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
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
	
	
	/*	业务指南  或 常见问题*/
	public static String httpURLConnectionPOST5752(String strucid) {
		try {
			URL url = new URL(POST_URL + "appapi90501.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,brcCode");
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = aes.encrypt("330682199408031287".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String parm = "";
			// 用于数字签名
			parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5752&devtoken=&channel=53&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008";

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5752&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008&strucid="
					+ strucid.replace("+", "%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);
			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
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

	public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {
		
		//个人公积金登录验证 用身份证号时 logintype赋值2 公积金账号 logintype赋值5
//		System.out.println(" 个人公积金登录验证 用身份证号时 logintype赋值2 公积金账号 logintype赋值5");
//		httpURLConnectionPOST5432("2", "330726198810170330", "", "123456");
//		httpURLConnectionPOST5432("5", "", "0076458492", "123456");
		
		//个人公积金密码修改
//		System.out.println(" 个人公积金密码修改");
//		httpURLConnectionPOST5448("330726198810170330", "", "123456", "123456");
//		httpURLConnectionPOST5448("", "0076458492", "123456", "123456");
		
		
		//个人明细查询新
//		System.out.println("个人明细查询新");
//		httpURLConnectionPOST5001("330726198810170330", "", "123456");
//		httpURLConnectionPOST5001("", "0076458492", "123456");

		//贷款审批情况查询
//		System.out.println("贷款审批情况查询");
//		httpURLConnectionPOST5445("330726198810170330", "", "123456");
//		httpURLConnectionPOST5445("", "0076458492", "123456");

		//贷款余额信息查询
//		System.out.println("贷款余额信息查询");
//		httpURLConnectionPOST5073("330726198810170330", "", "123456");
		
/*		//通知公告及正文内容获取
		//通知公告
		System.out.println("通知公告");
		httpURLConnectionPOST5521("21","0","100");
		System.out.println("通知公告正文内容获取");
		httpURLConnectionPOST5521("6195");
		
		
		System.out.println("中心简介");
		httpURLConnectionPOST5525("19","0","1");
		System.out.println("中心简介正文内容获取");
		httpURLConnectionPOST5521("6130");
		
		System.out.println("常用信息");
		httpURLConnectionPOST5525("20","0","1");
		System.out.println("常用信息正文内容获取");
		httpURLConnectionPOST5521("6131");*/

		System.out.println("业务指南");
		httpURLConnectionPOST5752("25");

//		System.out.println("常见问题");
//		httpURLConnectionPOST5752("30");
	}

}
