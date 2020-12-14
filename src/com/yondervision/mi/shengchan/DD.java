package com.yondervision.mi.shengchan;


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

public class DD {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";

	private static final String userId_V = "330205198909033329";
	private static final String appKey_V = "f2f0ad42864d325739a38d76e2a2b8d7";
	private static final String appId_V = "yondervisiontelservice60";

	public static String encrypt(String value) {
		if (value == null || value.trim().length() == 0) {
			return null;
		}
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		byte[] byteArray = value.getBytes();
		byte[] md5Bytes = md5.digest(byteArray);
		return String.valueOf(Hex.encodeHex(md5Bytes));
	}

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
			connection
					.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,password,newpassword,confirmnewpassword,accnum");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt(userId_V.getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));

			certinum = aes.encrypt(certinum.getBytes("UTF-8"));
			accnum = aes.encrypt(accnum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));
			newpassword = aes.encrypt(newpassword.getBytes("UTF-8"));

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5448&devtoken=&channel=60&appid="
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
			return null;
		}
	}

	/**
	 * 单位公积金密码修改
	 * 
	 */
	public static String httpURLConnectionPOST5463(String unitaccnum,
			String pwd, String newpasswd) {
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
			String userId = aes.encrypt(userId_V.getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			unitaccnum = aes.encrypt(unitaccnum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));
			newpasswd = aes.encrypt(newpasswd.getBytes("UTF-8"));

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5463&devtoken=&channel=60&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ unitaccnum + "&password=" + pwd + "&newpasswd="
					+ newpasswd + "&confirmnewpassword=" + newpasswd;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5463&devtoken=&channel=60&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ unitaccnum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B") + "&newpasswd="
					+ newpasswd.replace("+", "%2B") + "&confirmnewpassword="
					+ newpasswd.replace("+", "%2B");

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
			return null;
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
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,password,accnum");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt(userId_V.getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			
			certinum = aes.encrypt(certinum.getBytes("UTF-8"));
			accnum = aes.encrypt(accnum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5001&devtoken=&channel=60&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ certinum + "&password=" + pwd + "&accnum=" + accnum;
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
			return null;
		}
	}

	/**
	 * 个人公积金登录验证
	 */
	public static String httpURLConnectionPOST5432(String logintype,
			String certinum, String accnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi50006.json");
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
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,password,accnum,logintype");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt(userId_V.getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			
			certinum = aes.encrypt(certinum.getBytes("UTF-8"));
			accnum = aes.encrypt(accnum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=60&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ certinum + "&password=" + pwd + "&accnum=" + accnum
					+ "&logintype=" + logintype;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=60&appid="
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
			return null;
		}
	}

	/**
	 * 贷款审批情况查询
	 * 
	 */
	public static String httpURLConnectionPOST5445(String certinum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi01101.json");
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
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,pwd");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt(userId_V.getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			
			certinum = aes.encrypt(certinum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5445&devtoken=&channel=60&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ certinum + "&pwd=" + pwd;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5445&devtoken=&channel=60&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ certinum.replace("+", "%2B") + "&pwd="
					+ pwd.replace("+", "%2B");

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
	public static String httpURLConnectionPOST5073(String certinum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi00702.json");
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
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,pwd");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt(userId_V.getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			
			certinum = aes.encrypt(certinum.getBytes("UTF-8"));
			pwd = aes.encrypt(pwd.getBytes("UTF-8"));

			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5073&devtoken=&channel=60&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ certinum + "&pwd=" + pwd;
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5073&devtoken=&channel=60&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ certinum.replace("+", "%2B") + "&pwd="
					+ pwd.replace("+", "%2B");

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
	 * 单位公积金登陆验证
	 * 
	 */
	public static String httpURLConnectionPOST5461(String unitaccnum, String pwd) {
		try {
			URL url = new URL(POST_URL);
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
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,unitaccnum,password");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt(userId_V.getBytes("UTF-8"));
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
	 * 单位账户查询
	 * 
	 * @return
	 */
	public static String httpURLConnectionPOST5801(String unitaccnum, String pwd) {
		try {
			URL url = new URL(POST_URL);
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
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,unitaccnum,pwd");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt(userId_V.getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			
			unitaccnum = aes.encrypt(unitaccnum.getBytes("UTF-8"));
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
	 * 
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException {
		// 渠道报文模拟发送
//		httpURLConnectionPOST5448("330205198909033329", "", "", "");
		httpURLConnectionPOST5001("330205198909033329", "", "111111");
		
		//用身份证号时   logintype赋值2 公积金账号   logintype赋值5
//		httpURLConnectionPOST5432("2", "330205198909033329", "", "");
		
//		httpURLConnectionPOST5445("330205198909033329", "");
		
//		httpURLConnectionPOST5073("330205198909033329", "");
		
//		httpURLConnectionPOST5463("", "", "");
//		httpURLConnectionPOST5461("", "");
//		httpURLConnectionPOST5801("", "");
		
		
	}

}

