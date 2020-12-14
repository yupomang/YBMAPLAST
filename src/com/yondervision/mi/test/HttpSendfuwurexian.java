package com.yondervision.mi.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

public class HttpSendfuwurexian {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String userId_V = "330222198007226320";
	private static final String appKey_V = "f2f0ad42864d325739a38d76e2a2b8d7";
	private static final String appId_V = "yondervisiontelservice60";

	/**
	 * 个人公积金密码修改
	 * 
	 * @return
	 */
	public static String httpURLConnectionPOST5448(String certinum,
			String accnum, String pwd, String newpassword) {
		try {
			URL url = new URL(POST_URL + "appapi50009.json");
			// 将url 以 open方法返回的urlConnection 连接强转为HttpURLConnection连接
			// (标识一个url所引用的远程对象连接)
			// 此时connection只是为一个连接对象,待连接中
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			// 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
			connection.setDoOutput(true);
			// 设置连接输入流为true
			connection.setDoInput(true);
			// 设置请求方式为post
			connection.setRequestMethod("POST");
			// post请求缓存设为false
			connection.setUseCaches(false);
			// 设置该HttpURLConnection实例是否自动执行重定向
			connection.setInstanceFollowRedirects(true);
			// 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
			// application/x-javascript text/xml->xml数据
			// application/x-javascript->json对象
			// addRequestProperty添加相同的key不会覆盖，如果相同，内容会以{name1,name2}
			// --connection.addRequestProperty("from", "sfzh"); //来源哪个系统
			// 公共部分：centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp
//			connection
//					.addRequestProperty(
//							"headpara",
//							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,password,newpassword,confirmnewpassword,accnum");
			connection
			.addRequestProperty(
					"headpara",
					"centerId,userId");
			AesTest aes = null;
			try {
				aes = new AesTest();
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

			// certinum = aes.encrypt(certinum.getBytes("UTF-8"));
			// accnum = aes.encrypt(accnum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));
			newpassword = aes.encrypt(newpassword.getBytes("UTF-8"));

			// 用于数字签名
//			String parm = "centerId=00057400&userId="
//					+ userId
//					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5448&devtoken=&channel=60&appid="
//					+ appId
//					+ "&appkey="
//					+ appKey
//					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
//					+ certinum + "&password=" + pwd + "&newpassword="
//					+ newpassword + "&confirmnewpassword=" + newpassword
//					+ "&accnum=" + accnum;
			String parm = "centerId=00057400&userId="
			+ userId;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5448&devtoken=&channel=60&appid="
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
	 * 单位公积金密码修改
	 * 
	 */
	public static String httpURLConnectionPOST5463(String unitaccnum,
			String pwd, String newpasswd,String confirmnewpassword) {
		try {
			URL url = new URL(POST_URL + "appapi50015.json");
			// 将url 以 open方法返回的urlConnection 连接强转为HttpURLConnection连接
			// (标识一个url所引用的远程对象连接)
			// 此时connection只是为一个连接对象,待连接中
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			// 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
			connection.setDoOutput(true);
			// 设置连接输入流为true
			connection.setDoInput(true);
			// 设置请求方式为post
			connection.setRequestMethod("POST");
			// post请求缓存设为false
			connection.setUseCaches(false);
			// 设置该HttpURLConnection实例是否自动执行重定向
			connection.setInstanceFollowRedirects(true);
			// 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
			// application/x-javascript text/xml->xml数据
			// application/x-javascript->json对象
			// addRequestProperty添加相同的key不会覆盖，如果相同，内容会以{name1,name2}
			// --connection.addRequestProperty("from", "sfzh"); //来源哪个系统
			// 公共部分：centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp
			connection
					.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,unitaccnum,password,newpasswd,confirmnewpassword");
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
//			unitaccnum = aes.encrypt(unitaccnum.getBytes("UTF-8"));
//			unitaccnum = aes.encrypt(unitaccnum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));
			newpasswd = aes.encrypt(newpasswd.getBytes("UTF-8"));
			confirmnewpassword=aes.encrypt(confirmnewpassword.getBytes("UTF-8"));
			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5463&devtoken=&channel=60&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&unitaccnum="
					+ unitaccnum + "&password=" + pwd + "&newpasswd="
					+ newpasswd + "&confirmnewpassword=" + confirmnewpassword;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5463&devtoken=&channel=60&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&unitaccnum="
					+ unitaccnum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B") + "&newpasswd="
					+ newpasswd.replace("+", "%2B") + "&confirmnewpassword="
					+ confirmnewpassword.replace("+", "%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);

 
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
	 * 个人账户查询
	 * 
	 */
	public static String httpURLConnectionPOST5001(String certinum,
			String accnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi00101.json");
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

			String userId = "";
			// userId必须加密处理
	//			if(null != certinum && !"".equals(certinum)){
	//			userId = aes.encrypt(certinum.getBytes("UTF-8"));
	//		}else{
	//			userId = aes.encrypt(accnum.getBytes("UTF-8"));
	//		}
			userId = aes.encrypt("330726198810170330".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));

			// certinum = aes.encrypt(certinum.getBytes("UTF-8"));
			// accnum = aes.encrypt(accnum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));
			String parm = "";
			if (null != certinum && !"".equals(certinum)) {
				// 用于数字签名
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5001&devtoken=&channel=60&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
						+ certinum + "&password=" + pwd + "&accnum=" + accnum;

			} else {
				// 用于数字签名
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5001&devtoken=&channel=60&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&password="
						+ pwd + "&accnum=" + accnum;
			}
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5001&devtoken=&channel=60&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ certinum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B") + "&accnum="
					+ accnum.replace("+", "%2B");
			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);
 
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
	 * 个人公积金登录验证
	 * 
	 * {"phone":"13034611159","recode":"000000","accname":"胡炯","userid":
	 * "330222198007226320","cardno":"",
	 * "bodyCardNumber":"330222198007226320","certinum"
	 * :"330222198007226320","msg":"成功","accnum":"0076458492"}
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
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=60&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=&bodyCardNumber="
						+ certinum + "&password=" + pwd + "&accnum=" + accnum
						+ "&logintype=" + logintype;
			} else {
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=60&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=&password="
						+ pwd + "&accnum=" + accnum + "&logintype=" + logintype;
			}
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=60&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=&bodyCardNumber="
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
			// 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
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
	 * 贷款审批情况查询
	 * 
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
			// 设置该HttpURLConnection实例是否自动执行重定向
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
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = aes.encrypt("330726198810170330".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));

			// certinum = aes.encrypt(certinum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));
			String parm = "";
			if (null != certinum && !"".equals(certinum)) {
				// 用于数字签名
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5445&devtoken=&channel=60&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
						+ certinum + "&pwd=" + pwd;
			} else {
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5445&devtoken=&channel=60&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&accnum="
						+ accnum + "&pwd=" + pwd;
			}
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5445&devtoken=&channel=60&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ certinum.replace("+", "%2B") + "&pwd="
					+ pwd.replace("+", "%2B")+"&accnum="+accnum.replace("+","%2B");

			System.out.println("headparaMD5：" + RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
			System.out.println("传递参数：" + parm1);

 
			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),
					RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(
					connection.getOutputStream());
			dataout.writeBytes(parm1);
			dataout.flush();
			dataout.close();
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;
			// 用来存储响应数据
			StringBuilder sb = new StringBuilder();
			// 循环读取流,若不到结尾处
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
	public static String httpURLConnectionPOST5073(String certinum,
			String accnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi00702.json");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			// 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
			connection.setDoOutput(true);
			// 设置连接输入流为true
			connection.setDoInput(true);
			// 设置请求方式为post
			connection.setRequestMethod("POST");
			// post请求缓存设为false
			connection.setUseCaches(false);
			// 设置该HttpURLConnection实例是否自动执行重定向
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
			AesTest aes = null;
			try {
				aes = new AesTest();
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
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5073&devtoken=&channel=60&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
						+ certinum + "&pwd=" + pwd;
			} else {
				// 用于数字签名
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5073&devtoken=&channel=60&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&accnum="
						+ accnum + "&pwd=" + pwd;
			}
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5073&devtoken=&channel=60&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ certinum.replace("+", "%2B") + "&pwd="
					+ pwd.replace("+", "%2B")+"&accnum="+accnum.replace("+","%2B");

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
	 * 单位公积金登陆验证
	 * 
	 */
	public static String httpURLConnectionPOST5461(String unitaccnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi50013.json");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			// 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
			connection.setDoOutput(true);
			// 设置连接输入流为true
			connection.setDoInput(true);
			// 设置请求方式为post
			connection.setRequestMethod("POST");
			// post请求缓存设为false
			connection.setUseCaches(false);
			// 设置该HttpURLConnection实例是否自动执行重定向
			connection.setInstanceFollowRedirects(true);
			connection
					.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,unitaccnum,password");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt("330222198007226320".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));

			unitaccnum = aes.encrypt(unitaccnum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5461&devtoken=&channel=60&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&unitaccnum="
					+ unitaccnum + "&password=" + pwd;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5461&devtoken=&channel=60&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&unitaccnum="
					+ unitaccnum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);

 
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
	 * 单位账户查询
	 * 
	 * @return
	 */
	public static String httpURLConnectionPOST5801(String unitaccnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi02001.json");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			// 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
			connection.setDoOutput(true);
			// 设置连接输入流为true
			connection.setDoInput(true);
			// 设置请求方式为post
			connection.setRequestMethod("POST");
			// post请求缓存设为false
			connection.setUseCaches(false);
			// 设置该HttpURLConnection实例是否自动执行重定向
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

			// userId必须加密处理
			String userId = aes.encrypt("330211136954".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));

			// unitaccnum = aes.encrypt(unitaccnum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5801&devtoken=&channel=60&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&unitaccnum="
					+ unitaccnum + "&pwd=" + pwd;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5801&devtoken=&channel=60&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&unitaccnum="
					+ unitaccnum.replace("+", "%2B") + "&pwd="
					+ pwd.replace("+", "%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);

			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),
					RSASignature.RSA_PRIVATE));
 
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
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
	 * 
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException {
		
//		//个人公积金登录验证 用身份证号时 logintype赋值2 公积金账号 logintype赋值5
//		System.out.println(" 个人公积金登录验证 用身份证号时 logintype赋值2 公积金账号 logintype赋值5");
//		httpURLConnectionPOST5432("2", "330726198810170330", "", "654321");
//		httpURLConnectionPOST5432("5", "", "0237550054", "111111");
		
//		//个人公积金密码修改
//		System.out.println(" 个人公积金密码修改");
//		httpURLConnectionPOST5448("330726198810170330", "", "654321", "654321");
//		httpURLConnectionPOST5448("", "0076458492", "123456", "123456");
//
		//个人账户查询
//		System.out.println("个人账户查询");
		httpURLConnectionPOST5001("330726198810170330", "", "654321");
//		httpURLConnectionPOST5001("", "0076458492", "123456");

		//贷款审批情况查询
//		System.out.println("贷款审批情况查询");
//		httpURLConnectionPOST5445("330726198810170330", "", "654321");
//		httpURLConnectionPOST5445("", "0236239239", "111111");
//
		//贷款余额信息查询
//		System.out.println("贷款余额信息查询");
//		httpURLConnectionPOST5073("330726198810170330", "", "123456");
//		httpURLConnectionPOST5073("", "0076458492", "111111");
//		
//		//单位公积金登陆验证
//		System.out.println("单位公积金登陆验证");
//		httpURLConnectionPOST5461("330211136954", "891801");
//		
//		//单位公积金密码修改
//		System.out.println("单位公积金密码修改");
//		httpURLConnectionPOST5463("330211136954", "111111", "111111","111111");
//
//		//单位账户查询
//		System.out.println("单位账户查询");
//		httpURLConnectionPOST5801("330211136954", "111111");
	}

}
