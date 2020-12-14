package com.yondervision.mi.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

public class HttpSendWangZhan2 {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "ba7f3a3dd146c4613102d0a16be5107b";
	private static final String appId_V = "yondervisionwebsite30";




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

			//certinum = aes.encrypt(certinum.getBytes("UTF-8"));
			//accnum = aes.encrypt(accnum.getBytes("UTF-8"));
			//pwd = aes.encrypt(pwd.getBytes("UTF-8"));
			String parm = "";
			if (null != certinum && !"".equals(certinum)) {
				// 用于数字签名
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=30&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=10.33.11.35&bodyCardNumber="
						+ certinum + "&password=" + pwd + "&accnum=" + accnum
						+ "&logintype=" + logintype;
			} else {
				parm = "centerId=00057400&userId="
						+ userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=30&appid="
						+ appId
						+ "&appkey="
						+ appKey
						+ "&appToken=&clientIp=10.33.11.35&password="
						+ pwd + "&accnum=" + accnum + "&logintype=" + logintype;
			}
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=30&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=10.33.11.35&bodyCardNumber="
					+ certinum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B") + "&accnum="
					+ accnum.replace("+", "%2B") + "&logintype="
					+ logintype.replace("+", "%2B");

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
	 * 可贷楼盘查询
	 */
	public static String httpURLConnectionPOST5940(String projectname_wb,String cocustname_wb,
			String num_web1,  String num_web2) {
		try {
			URL url = new URL(POST_URL + "appapi03829.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty(
							"headpara",
							"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp");
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = aes.encrypt("web".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));

			System.out.println("userId==" + aes.encrypt("web".getBytes("UTF-8")));
			System.out.println("appKey==" + aes.encrypt(appKey_V.getBytes("UTF-8")));
			System.out.println("appId==" + aes.encrypt(appId_V.getBytes("UTF-8")));
			// 用于数字签名
			String parm = "centerId=00057400&userId=cxZToZ1plgevE7Se8Fl4GA==&usertype=10&deviceType=3&deviceToken=&" +
					"currenVersion=1.0&buzType=5940&devtoken=&channel=30&appid=B40ODwGGaHv8QtqQK4z8K2iwgUXFcW+7o7AUJdk1FAQ=&appkey=" +
					"cpyywZULhBLziy+yFFQlIepXtHzlH3KUIwFxD2fQxIg+pmlAetojpgxGtQMHFCwr&appToken=&clientIp=183.136.157.20";
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId=cxZToZ1plgevE7Se8Fl4GA==&usertype=10&deviceType=3&deviceToken=&" +
					"currenVersion=1.0&buzType=5940&devtoken=&channel=30&appid=B40ODwGGaHv8QtqQK4z8K2iwgUXFcW%2B7o7AUJdk1FAQ=&" +
					"appkey=cpyywZULhBLziy%2ByFFQlIepXtHzlH3KUIwFxD2fQxIg%2BpmlAetojpgxGtQMHFCwr&appToken=&clientIp=183.136.157.20" +
					"&cocustname_wb=" + cocustname_wb +
					"&projectname_wb=" + projectname_wb +
					"&num_web1=" + num_web1 +
					"&num_web2=" + num_web2 ;
			System.out.println("parm" + parm);
			System.out.println("parm1" + parm1);
 
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
			System.out.println("headparaMD5==" +  RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
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

	
	/*排队请求URL*/
	public static final String POST_URL4 = "http://61.153.144.77:7006/YBMAPZH/appapi10106.json";
	/*排队人数距离请求URL*/
	public static final String POST_URL5 = "http://61.153.144.77:7006/YBMAPZH/appapi10110.json";


	/**获取排队信息*/
	public static String getPdxx(String id) {
		String pdxx = "";//查询网点排队信息返回数据
		try {  
			URL url = new URL("http://61.153.144.77:7001/YBMAPZH/appapi10106.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
			connection.setDoOutput(true);  
			connection.setDoInput(true);  
			connection.setRequestMethod("POST");   
			connection.setUseCaches(false);  
			connection.setInstanceFollowRedirects(true);  
			connection.addRequestProperty("headpara", 
					"centerId,userId,usertype,deviceType,deviceToken,"
					+ "currenVersion,buzType,devtoken,channel,appid,appkey,"
					+ "appToken,clientIp,appSecret,platform,appKey,websitecode");
			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String userId = aes.encrypt("web".getBytes("UTF-8"));//各渠道客户标识
			String appkey = aes.encrypt("ba7f3a3dd146c4613102d0a16be5107b".getBytes("UTF-8"));
			String appId = aes.encrypt("yondervisionwebsite30".getBytes("UTF-8"));

			//1、用于数字签名
			String parm ="centerId=00057400&userId="+userId
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5516&"
					+ "devtoken=&channel=30&appid="+appId+"&appkey="+appkey+
					"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&"
					+ "appSecret=4B6fxsM5Z6tu2nugtDssglretSjfc5jGkhIXIK0d&"
					+ "platform=web&appKey=a3ed9e06e7aaaa7eed769dff36215c4569092ab4&"
					+ "websitecode="+id;
		    //##05		//1、用于发送http报文
			String parm1 ="centerId=00057400&userId="+userId.replace("+", "%2B")
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&"
					+ "buzType=5516&devtoken=&channel=30&appid="+appId.replace("+","%2B")+
					"&appkey="+appkey.replace("+", "%2B")+"&appToken=&"
							+ "clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&"
							+ "appSecret=4B6fxsM5Z6tu2nugtDssglretSjfc5jGkhIXIK0d"
							+ "&platform=web&appKey=a3ed9e06e7aaaa7eed769dff36215c4569092ab4&"
							+ "websitecode="+id;

		
			System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
			System.out.println(RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");      
			connection.connect();  
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
			System.out.println("getPdxx()-传递参数："+parm);
			System.out.println("getPdxx()-传递参数1："+parm1);
			dataout.writeBytes(parm1);
			dataout.flush();   
			dataout.close();

			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));   
			String line;  
			StringBuilder sb = new StringBuilder(); // 用来存储响应数据  
			while ((line = bf.readLine()) != null) {  
				sb.append(line).append(System.getProperty("line.separator"));  
			}
			bf.close();
			connection.disconnect();  
			System.out.println("wwj-pdxx="+sb.toString());
			pdxx = sb.toString();
		} catch (Exception e) {  
			e.printStackTrace();  
		}
		return pdxx;
	}
	
	/**获取网点排号人数列表*/
	public static String getPdrs(String positionX,String positionY) {
		String pdrs = "";//查询网点排号人数列表返回数据
		try {
			URL url = new URL("http://61.153.144.77:7006/YBMAPZH/appapi10110.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
			connection.setDoOutput(true);  
			connection.setDoInput(true);  
			connection.setRequestMethod("POST");   
			connection.setUseCaches(false);  
			connection.setInstanceFollowRedirects(true);  
			connection.addRequestProperty("headpara", 
					"centerId,userId,usertype,deviceType,deviceToken,"
					+ "currenVersion,buzType,devtoken,channel,appid,appkey,"
					+ "appToken,clientIp,appSecret,platform,appKey,websitecode,positionX,positionY");
			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String userId = aes.encrypt("web".getBytes("UTF-8"));//各渠道客户标识
			String appkey = aes.encrypt("ba7f3a3dd146c4613102d0a16be5107b".getBytes("UTF-8"));
			String appId = aes.encrypt("yondervisionwebsite30".getBytes("UTF-8"));

			//用于数字签名
			String parm ="centerId=00057400&userId="+userId
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5516&"
					+ "devtoken=&channel=30&appid="+appId+"&appkey="+appkey+
					"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&"
					+ "appSecret=4B6fxsM5Z6tu2nugtDssglretSjfc5jGkhIXIK0d&"
					+ "platform=web&appKey=a3ed9e06e7aaaa7eed769dff36215c4569092ab4&"
					+ "positionX="+positionX+"&positionY="+positionY;
			//用于发送http报文
			String parm1 ="centerId=00057400&userId="+userId.replace("+", "%2B")
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&"
					+ "buzType=5516&devtoken=&channel=30&appid="+appId.replace("+","%2B")+
					"&appkey="+appkey.replace("+", "%2B")+"&appToken=&"
							+ "clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&"
							+ "appSecret=4B6fxsM5Z6tu2nugtDssglretSjfc5jGkhIXIK0d"
							+ "&platform=web&appKey=a3ed9e06e7aaaa7eed769dff36215c4569092ab4&"
							+ "positionX="+positionX+"&positionY="+positionY ;
		
			System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
//			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			connection.addRequestProperty("headparaMD5", "8B74946C709BDA49D881674BE0669A14");

			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");      
			connection.connect();  
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
			System.out.println("getPdrs()-传递参数："+parm);
			System.out.println("getPdrs()-传递参数1："+parm1);
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
			System.out.println("wwj-pdrs="+sb.toString());
			pdrs = sb.toString();
		} catch (Exception e) {  
			e.printStackTrace();  
		}
		return pdrs;
	}

	public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {
		//httpURLConnectionPOST5432("2", "330726198810170330", "", "");
		getPdxx("0574000801");
		//lat=29.88525897 lng=121.57900597
		//getPdrs("121.555398","29.874727");
		//可贷楼盘查询
		/*System.out.println("可贷楼盘查询");
		httpURLConnectionPOST5940("", "", "1","10");*/
		//httpURLConnectionPOST5940(URLEncoder.encode("澜悦花苑","utf-8"),"" , "1","10");

	}

}
