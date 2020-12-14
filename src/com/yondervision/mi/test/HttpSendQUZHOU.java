package com.yondervision.mi.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

public class HttpSendQUZHOU {
	
	public static final String POST_URL = "http://221.12.139.170:7002/YBMAPZH/";
	private static final String appKey_V = "32c07afeab6268c4996abc6f8eee9fb5";
	private static final String appId_V = "yondervisionalipay90";

	/**
	 * 支付宝自助开户
	 * @return
	 */
	public static String httpURLConnectionPOST6009(String 	zjhm	,
			String 	xingming	,
			String 	zgzhlx	,
			String 	grjcjs	,
			String 	indiprop	,
			String 	indipaysum	,
			String 	instcode	,
			String 	hyzk	,
			String 	jtzz	,
			String 	sjhm	,
			String 	yhdm	,
			String 	bankaccnum	) {
		try {
			URL url = new URL(POST_URL + "appapi00105.json");
			// 将url 以 open方法返回的urlConnection 连接强转为HttpURLConnection连接
			// (标识一个url所引用的远程对象连接)
			// 此时connection只是为一个连接对象,待连接中
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,zjhm,xingming,zgzhlx,grjcjs,indiprop,indipaysum,instcode,hyzk,jtzz,sjhm,yhdm,bankaccnum");
//			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp");

			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// userId必须加密处理
			String userId = aes.encrypt("220822199110132818".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));

			// 用于数字签名
			String parm = "centerId=00057000&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6009&devtoken=&channel=90&appid="
					+ appId
					+ "&appkey="+ appKey
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ=="
					+"&zjhm="+zjhm
					+"&xingming="+xingming
					+"&zgzhlx="+zgzhlx
					+"&grjcjs="+grjcjs
					+"&indiprop="+indiprop
					+"&indipaysum="+indipaysum
					+"&instcode="+instcode
					+"&hyzk="+hyzk
					+"&jtzz="+jtzz
					+"&sjhm="+sjhm
					+"&yhdm="+yhdm
					+"&bankaccnum="+bankaccnum;
			// 用于发送http报文
			String parm1 = "centerId=00057000&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6009&devtoken=&channel=90&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ=="
					+"&zjhm="+zjhm.replace("+","%2B")
					+"&xingming="+xingming.replace("+","%2B")
					+"&zgzhlx="+zgzhlx.replace("+","%2B")
					+"&grjcjs="+grjcjs.replace("+","%2B")
					+"&indiprop="+indiprop.replace("+","%2B")
					+"&indipaysum="+indipaysum.replace("+","%2B")
					+"&instcode="+instcode.replace("+","%2B")
					+"&hyzk="+hyzk.replace("+","%2B")
					+"&jtzz="+jtzz.replace("+","%2B")
					+"&sjhm="+sjhm.replace("+","%2B")
					+"&yhdm="+yhdm.replace("+","%2B")
					+"&bankaccnum="+bankaccnum.replace("+","%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);

			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
			// application/x-www-form-urlencoded->表单数据 ;charset=utf-8
			// 必须要，不然妙兜那边会出现乱码【★★★★★】
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			// 建立连接
			// (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
			connection.connect();
			// 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
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
	 */
	public static String httpURLConnectionPOST5432(String logintype,
			String certinum, String fullName, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi50006.json");
			// 将url 以 open方法返回的urlConnection 连接强转为HttpURLConnection连接
			// (标识一个url所引用的远程对象连接)
			// 此时connection只是为一个连接对象,待连接中
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);

			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = aes.encrypt(certinum.getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));

			String parm = "";
				// 用于数字签名
				parm = "centerId=00057000&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=90&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==";

			// 用于发送http报文
			String parm1 = "centerId=00057000&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=90&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&bodyCardNumber="
					+ certinum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B") + "&fullName="
					+ fullName.replace("+", "%2B") + "&logintype="
					+ logintype.replace("+", "%2B");

			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);

			// System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),
					RSASignature.RSA_PRIVATE));
			// application/x-www-form-urlencoded->表单数据 ;charset=utf-8
			// 必须要，不然会出现乱码【★★★★★】
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
	
	


	public static void main(String[] args) throws UnknownHostException {
		
		//支付宝自助开户
//		System.out.println("支付宝自助开户");
/*		httpURLConnectionPOST6009("220822199110132818",
				"吕明河",
				"1",
				"4164.00",
				"0.12",
				"500",
				"05700001",
				"10",
				"某地方",
				"13029138261",
				"102",
				 "123123123123");*/
		
		httpURLConnectionPOST5432("2", "220822199110132818", "吕明河", "lmh123");
/*        zjhm = "220822199110132818";
        xingming = "吕明河";
        zgzhlx = "1";
        grjcjs = "4164.00";
        indiprop = "0.12";
        indipaysum = "500";
        instcode = "05700001";
        hyzk = "10";
        jtzz = "某地方";
        sjhm = "13029138261";
        yhdm = "102";
        bankaccnum = "123123123123";
        userId = "220822199110132818";*/

}
}
