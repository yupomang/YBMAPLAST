package com.yondervision.mi.test;


import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;  
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;  
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;  

public class Weixindenglu2222 {
	public static final String POST_URL  = "http://61.153.144.77:7001/YBMAPZH/"; //测试环境
	//public static final String POST_URL  = "http://61.153.144.77:7001/YBMAPZH/";//生产环境
	private static final String appKey_V = "2c9d3f99763faf9de0dbb499b8844899";
	private static final String appId_V = "yondervisionweixin20";
	private static final String CHANNEL = "20";
	//登录OK，用这个
	public static String httpURLConnectionPOST5432(String logintype, String certinum, String accnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi50006.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			if (null != certinum && !"".equals(certinum)) {
				connection.addRequestProperty("headpara",
						"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,password,accnum,logintype");
			} else {
				connection.addRequestProperty("headpara",
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
			if (null != certinum && !"".equals(certinum)) {
				userId = aes.encrypt(certinum.getBytes("UTF-8"));
			} else {
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
				parm = "centerId=00057400&userId=" + userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=20&appid="
						+ appId + "&appkey=" + appKey + "&appToken=&clientIp=183.136.157.20&bodyCardNumber=" + certinum
						+ "&password=" + pwd + "&accnum=" + accnum + "&logintype=" + logintype;
			} else {
				parm = "centerId=00057400&userId=" + userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=20&appid="
						+ appId + "&appkey=" + appKey + "&appToken=&clientIp=183.136.157.20&password=" + pwd + "&accnum="
						+ accnum + "&logintype=" + logintype;
			}
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId=" + userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=20&appid="
					+ appId.replace("+", "%2B") + "&appkey=" + appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=183.136.157.20&bodyCardNumber=" + certinum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B") + "&accnum=" + accnum.replace("+", "%2B") + "&logintype="
					+ logintype.replace("+", "%2B");

			connection.addRequestProperty("headparaMD5",
					RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			System.out.println("httpURLConnectionPOST5432()-传递参数："+parm);
			System.out.println("httpURLConnectionPOST5432()-传递参数1："+parm1);
			System.out.println("httpURLConnectionPOST5432()-headparaMD5："+RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
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
	 * 个人业务——预约确定
	 * 网点ID：appobranchid
	 * 网点名称：appobranchname
	 * 日期：appodate
	 * 时段id：appotpldetailid
	 * 时间段：timeinterval
	 * 业务ID：appobusiid
	 * 业务类型：appobusiname
	 * 电话：phone
	 */
	public static String getMyYYQD(String appobranchid,String appobranchname,String appodate,String appotpldetailid,String timeInterval,String appobusiid,
								   String appobusiname,String phone) {
		String result = "";
		try {
			URL url = new URL(POST_URL + "appapi30304.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			//centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp
			connection.addRequestProperty("headpara",
					"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp," +
							"appobranchid,appobranchname,appodate,appotpldetailid,timeInterval,appobusiid,appobusiname,phone");

			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			//userId必须加密处理
			String userId = aes.encrypt("362422199510198710".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));


			//用于数字签名
			String parm ="centerId=00057400&userId="+userId
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5341&"
					+ "devtoken=&channel=20&appid="+appId+"&appkey="+appKey+
					"&appToken=&clientIp=10.33.11.35"
					+ "&appobranchid="+appobranchid + "&appobranchname="+appobranchname + "&appodate="+appodate
					+ "&appotpldetailid="+appotpldetailid + "&timeInterval="+timeInterval + "&appobusiid="+appobusiid
					+ "&appobusiname="+appobusiname + "&phone="+phone
					;

			//用于发送http报文
			String parm1 ="centerId=00057400&userId="+userId.replace("+", "%2B")
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&"
					+ "buzType=5341&devtoken=&channel=20&appid="+appId.replace("+","%2B")+
					"&appkey="+appKey.replace("+", "%2B")+"&appToken=&"
					+ "clientIp=10.33.11.35"
					+ "&appobranchid="+appobranchid.replace("+", "%2B") + "&appobranchname="+appobranchname.replace("+", "%2B") + "&appodate="+appodate.replace("+", "%2B")
					+ "&appotpldetailid="+appotpldetailid.replace("+", "%2B") + "&timeInterval="+timeInterval.replace("+", "%2B") + "&appobusiid="+appobusiid.replace("+", "%2B")
					+ "&appobusiname="+appobusiname.replace("+", "%2B") + "&phone="+phone.replace("+", "%2B")
					;

			System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			//connection.addRequestProperty("headparaMD5", "8B74946C709BDA49D881674BE0669A14");

			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			System.out.println("getMyYYYqd-传递参数："+parm);
			System.out.println("getMyYYqd-传递参数1："+parm1);
			System.out.println("getMyYYqd-headparaMD5："+RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
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
			System.out.println("getMyYYqd="+sb.toString());
			result = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//此文件使用channel=20 的微信接口相关配置
	//这个文件缺logintype，为啥也能运行成功， 调用能成功，不要用这个...
	public static void gjjLogin () {  
		try {  
			URL url = new URL(POST_URL + "appapi50006.json");
			// 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)  
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
			// application/x-javascript text/xml->xml数据 application/x-javascript->json对象
			//addRequestProperty添加相同的key不会覆盖，如果相同，内容会以{name1,name2}  
			//--connection.addRequestProperty("from", "sfzh");  //来源哪个系统  
			//公共部分：centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp
			connection.addRequestProperty("headpara", "centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,password,accnum");			
			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			//userId必须加密处理
			String userId = aes.encrypt("330225198312301613".getBytes("UTF-8"));
			String appKey = aes.encrypt("2c9d3f99763faf9de0dbb499b8844899".getBytes("UTF-8"));
			String appId = aes.encrypt("yondervisionweixin20".getBytes("UTF-8"));
			String certinum = aes.encrypt("330225198312301613".getBytes("UTF-8"));
			String accnum = aes.encrypt("".getBytes("UTF-8"));
			String pwd = aes.encrypt("112233".getBytes("UTF-8"));
			//String pwd = "111111";

			//用于数字签名			
			String parm ="centerId=00057400&userId="+userId+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=20&appid="+appId+"&appkey="+appKey+"&appToken=&clientIp=10.33.11.35&bodyCardNumber="+certinum+"&password="+pwd+"&accnum="+accnum;
			//用于发送http报文
			String parm1 ="centerId=00057400&userId="+userId.replace("+","%2B")+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=20&appid="+appId.replace("+", "%2B")+"&appkey="+appKey.replace("+", "%2B")+"&appToken=&clientIp=10.33.11.35&bodyCardNumber="+certinum.replace("+","%2B")+"&password="+pwd.replace("+","%2B")+"&accnum="+accnum.replace("+","%2B");
					
			System.out.println("parm"+parm);
			System.out.println("parm1"+parm1);
								
			//System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			//application/x-www-form-urlencoded->表单数据 ;charset=utf-8 必须要，不然妙兜那边会出现乱码【★★★★★】  
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");      
			// 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)  
			connection.connect();  
			// 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)  
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
			System.out.println("传递参数："+parm1);  
			// 将参数输出到连接  
			dataout.writeBytes(parm1);  
			// 输出完成后刷新并关闭流  
			dataout.flush();   
			// 重要且易忽略步骤 (关闭流,切记!)   
			dataout.close(); 
			//System.out.println(connection.getResponseCode());  
			// 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)  
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));   
			String line;  
			 // 用来存储响应数据  
			StringBuilder sb = new StringBuilder();
			// 循环读取流,若不到结尾处
			while ((line = bf.readLine()) != null) {  
				//sb.append(bf.readLine());  
				sb.append(line).append(System.getProperty("line.separator"));  
			}			// 重要且易忽略步骤 (关闭流,切记!)   
			bf.close();    
			connection.disconnect(); // 销毁连接  
			System.out.println(sb.toString());  
		} catch (Exception e) {  
			e.printStackTrace();  
		}
	}

	
	/**  
	 * 个人业务——预约网点日期人数查询 / 应该是OK了 30303.json
	 * 
     * @param appobranchid 网点ID
     * @param appodate 日期
     * @param appobusiid 业务类型
     * 
	 * @return array 返回排号信息
	*/
	public static String getSingleSiteTimeNumWx(int appobranchid,String appodate, int appobusiid) {
		String result = "";//查询网点排号人数列表返回数据
		try {
			URL url = new URL(POST_URL + "appapi30303.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
			connection.setDoOutput(true);  
			connection.setDoInput(true);  
			connection.setRequestMethod("POST");   
			connection.setUseCaches(false);  
			connection.setInstanceFollowRedirects(true);  
			connection.addRequestProperty("headpara", 
					"centerId,userId,usertype,deviceType,deviceToken,"
					+ "currenVersion,buzType,devtoken,channel,appid,appkey,"
					+ "appToken,clientIp,appSecret,platform,appKey,websitecode,appobranchid,appodate,appobusiid");
			
			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String userId = aes.encrypt("web".getBytes("UTF-8"));//各渠道客户标识
			//String userId = aes.encrypt(idcardNumber.getBytes("UTF-8"));//各渠道客户标识
			//String userId = aes.encrypt("330225198312301613".getBytes("UTF-8"));
			
			String appkey = aes.encrypt("2c9d3f99763faf9de0dbb499b8844899".getBytes("UTF-8"));
			String appId = aes.encrypt("yondervisionweixin20".getBytes("UTF-8"));

			//用于数字签名
			String parm ="centerId=00057400&userId="+userId
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5341&"
					+ "devtoken=&channel=20&appid="+appId+"&appkey="+appkey+
					"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&"
					+ "appSecret=4B6fxsM5Z6tu2nugtDssglretSjfc5jGkhIXIK0d&"
					+ "platform=web&appKey=a3ed9e06e7aaaa7eed769dff36215c4569092ab4&"
					+ "appobranchid=" + appobranchid + "&appodate=" + appodate + "&appobusiid=" + appobusiid;
			//用于发送http报文
			String parm1 ="centerId=00057400&userId="+userId.replace("+", "%2B")
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&"
					+ "buzType=5341&devtoken=&channel=20&appid="+appId.replace("+","%2B")+
					"&appkey="+appkey.replace("+", "%2B")+"&appToken=&"
							+ "clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&"
							+ "appSecret=4B6fxsM5Z6tu2nugtDssglretSjfc5jGkhIXIK0d"
							+ "&platform=web&appKey=a3ed9e06e7aaaa7eed769dff36215c4569092ab4&"
							+ "appobranchid=" + appobranchid + "&appodate=" + appodate + "&appobusiid=" + appobusiid;
		
			System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			//connection.addRequestProperty("headparaMD5", "8B74946C709BDA49D881674BE0669A14");

			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");      
			connection.connect();  
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
			System.out.println("getSingleSiteTimeNum()-传递参数："+parm);
			System.out.println("getSingleSiteTimeNum()-传递参数1："+parm1);
			System.out.println("getSingleSiteTimeNum()-headparaMD5："+RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
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
			System.out.println("getSingleSiteTimeNum="+sb.toString());
			result = sb.toString();
		} catch (Exception e) {  
			e.printStackTrace();  
		}
		return result;
	}	
	
	
	/**
	 * 可贷楼盘查询
	 */
	public static String getHouseList(String projectname_wb,String cocustname_wb,
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
			
			
			String userId = aes.encrypt("web".getBytes("UTF-8"));//各渠道客户标识			
			String appkey = aes.encrypt("2c9d3f99763faf9de0dbb499b8844899".getBytes("UTF-8"));
			String appId = aes.encrypt("yondervisionweixin20".getBytes("UTF-8"));


			System.out.println("userId==" + userId);
			System.out.println("appkey==" + appkey);
			System.out.println("appId==" + appId);
			
			// 用于数字签名
			String parm = "centerId=00057400&userId="+userId+"&usertype=10&deviceType=3&deviceToken=&" +
					"currenVersion=1.0&buzType=5940&devtoken=&channel=20&appid="+ appId + "&appkey=" +
					appkey + "&appToken=&clientIp=183.136.157.20";
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="+userId.replace("+","%2B")+"&usertype=10&deviceType=3&deviceToken=&" +
					"currenVersion=1.0&buzType=5940&devtoken=&channel=20&appid=" + appId.replace("+","%2B") + "&" +
					"appkey=" +appkey.replace("+","%2B") + "&appToken=&clientIp=183.136.157.20" +
					"&cocustname_wb=" + cocustname_wb +
					"&projectname_wb=" + projectname_wb +
					"&num_web1=" + num_web1 +
					"&num_web2=" + num_web2 ;
			System.out.println("parm =" + parm);
			System.out.println("parm1=" + parm1);
 
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
			System.out.println("headparaMD5=" +  RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
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
	 * 在线排队——我的排号 （需登录认证） 10108.json 目前提示请求验证失败
	 * 
	 * @param idcardNumber 身份证号码
	 * @return array 返回排号信息
	*/
	public static String getMyPDNUMWx(String idcardNumber) {
		String result = "";//查询网点排号人数列表返回数据
		try {
			URL url = new URL(POST_URL + "appapi10108.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
			connection.setDoOutput(true);  
			connection.setDoInput(true);  
			connection.setRequestMethod("POST");   
			connection.setUseCaches(false);  
			connection.setInstanceFollowRedirects(true);
			//centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp
			connection.addRequestProperty("headpara", 
					"centerId,userId,usertype,deviceType,deviceToken,"
					+ "currenVersion,buzType,devtoken,channel,appid,appkey,"
					+ "appToken,clientIp,idcardNumber");
			
			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//userId必须加密处理
			String userId = aes.encrypt("330225198312301613".getBytes("UTF-8"));
			//String userId = aes.encrypt("web".getBytes("UTF-8"));
			String appkey = aes.encrypt("2c9d3f99763faf9de0dbb499b8844899".getBytes("UTF-8"));
			String appId = aes.encrypt("yondervisionweixin20".getBytes("UTF-8"));
			//String certinum = aes.encrypt("330225198312301613".getBytes("UTF-8"));
			//String accnum = aes.encrypt("".getBytes("UTF-8"));
			//String pwd = aes.encrypt("112233".getBytes("UTF-8"));

			//用于数字签名
			String parm ="centerId=00057400&userId="+userId
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5516&"
					+ "devtoken=&channel=20&appid="+appId+"&appkey="+appkey+
					"&appToken=&clientIp=10.33.11.35&"
					+ "idcardNumber="+idcardNumber;
			//用于发送http报文
			String parm1 ="centerId=00057400&userId="+userId.replace("+", "%2B")
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&"
					+ "buzType=5516&devtoken=&channel=20&appid="+appId.replace("+","%2B")+
					"&appkey="+appkey.replace("+", "%2B")+"&appToken=&"
							+ "clientIp=10.33.11.35&"
							+ "idcardNumber="+idcardNumber;
		
			System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			//connection.addRequestProperty("headparaMD5", "8B74946C709BDA49D881674BE0669A14");

			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");      
			connection.connect();  
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
			System.out.println("getMyPDNUM()-传递参数："+parm);
			System.out.println("getMyPDNUM()-传递参数1："+parm1);
			System.out.println("getMyPDNUM()-headparaMD5："+RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
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
			System.out.println("mypd-num="+sb.toString());
			result = sb.toString();
		} catch (Exception e) {  
			e.printStackTrace();  
		}
		return result;
	}




	/**  
	 * 在线排队——我的排号 （需登录认证） 30305.json OK
	 * @param idcardNumber 身份证号码
	 * @return array 返回排号信息
	*/
	public static String getMyYYWx(String idcardNumber) {
		String result = "";//查询网点排号人数列表返回数据
		try {
			URL url = new URL(POST_URL + "appapi30305.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
			connection.setDoOutput(true);  
			connection.setDoInput(true);  
			connection.setRequestMethod("POST");   
			connection.setUseCaches(false);  
			connection.setInstanceFollowRedirects(true);
			//centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp
			connection.addRequestProperty("headpara", 
					"centerId,userId,usertype,deviceType,deviceToken,"
					+ "currenVersion,buzType,devtoken,channel,appid,appkey,"
					+ "appToken,clientIp");
			
			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//userId必须加密处理
			String userId = aes.encrypt(idcardNumber.getBytes("UTF-8"));
			String appkey = aes.encrypt("2c9d3f99763faf9de0dbb499b8844899".getBytes("UTF-8"));
			String appId = aes.encrypt("yondervisionweixin20".getBytes("UTF-8"));


			//用于数字签名
			String parm ="centerId=00057400&userId="+userId
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5343&"
					+ "devtoken=&channel=20&appid="+appId+"&appkey="+appkey+
					"&appToken=&clientIp=183.136.157.20"
					;
			//用于发送http报文
			String parm1 ="centerId=00057400&userId="+userId.replace("+", "%2B")
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&"
					+ "buzType=5343&devtoken=&channel=20&appid="+appId.replace("+","%2B")+
					"&appkey="+appkey.replace("+", "%2B")+"&appToken=&"
							+ "clientIp=183.136.157.20"
							;
		
			System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			//connection.addRequestProperty("headparaMD5", "8B74946C709BDA49D881674BE0669A14");

			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");      
			connection.connect();  
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
			System.out.println("getMyYYWx()-传递参数："+parm);
			System.out.println("getMyYYWx()-传递参数1："+parm1);
			System.out.println("getMyYYWx()-headparaMD5："+RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
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
			System.out.println("getMyYYWx="+sb.toString());
			result = sb.toString();

		} catch (Exception e) {  
			e.printStackTrace();  
		}
		return result;
	}
	
	/**  
	 * 绑定用户 50010.json 
	* 
	* @param idcardNumber 身份证号码
	* @return array 返回排号信息
	*/
	public static String binduser(String logintype, String certinum, String accnum, String pwd) {
		try {
			URL url = new URL(POST_URL + "appapi50010.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			if (null != certinum && !"".equals(certinum)) {
				connection.addRequestProperty("headpara",
						"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,password,accnum,logintype");
			} else {
				connection.addRequestProperty("headpara",
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
			if (null != certinum && !"".equals(certinum)) {
				userId = aes.encrypt(certinum.getBytes("UTF-8"));
			} else {
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
				parm = "centerId=00057400&userId=" + userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5431&devtoken=&channel=20&appid="
						+ appId + "&appkey=" + appKey + "&appToken=&clientIp=10.33.11.35&bodyCardNumber=" + certinum
						+ "&password=" + pwd + "&accnum=" + accnum + "&logintype=" + logintype;
			} else {
				parm = "centerId=00057400&userId=" + userId
						+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5431&devtoken=&channel=20&appid="
						+ appId + "&appkey=" + appKey + "&appToken=&clientIp=10.33.11.35&password=" + pwd + "&accnum="
						+ accnum + "&logintype=" + logintype;
			}
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId=" + userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5431&devtoken=&channel=20&appid="
					+ appId.replace("+", "%2B") + "&appkey=" + appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=10.33.11.35&bodyCardNumber=" + certinum.replace("+", "%2B") + "&password="
					+ pwd.replace("+", "%2B") + "&accnum=" + accnum.replace("+", "%2B") + "&logintype="
					+ logintype.replace("+", "%2B");

			connection.addRequestProperty("headparaMD5",
					RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
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
	 * 解除绑定用户 50002.json 
	* 
	* @param certinum 身份证号码
	* @return array 返回解绑结果
	*/
	public static String unbinduser(String certinum) {
		try {
			URL url = new URL(POST_URL + "appapi50002.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);

			connection.addRequestProperty("headpara",
					"centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp");

			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = "";
			// userId必须加密处理
			userId = aes.encrypt(certinum.getBytes("UTF-8"));

			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));

			String parm = "";
			// 用于数字签名
			parm = "centerId=00057400&userId=" + userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5444&devtoken=&channel=20&appid="
					+ appId + "&appkey=" + appKey + "&appToken=&clientIp=10.33.11.35";
					

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId=" + userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5444&devtoken=&channel=20&appid="
					+ appId.replace("+", "%2B") + "&appkey=" + appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=10.33.11.35";
					
					
			connection.addRequestProperty("headparaMD5",
					RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
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
	
	
	/**获取网点排号人数列表*/
	public static String getPdrs(String positionX,String positionY) {
		String pdrs = "";//查询网点排号人数列表返回数据
		try {
			URL url = new URL(POST_URL + "appapi10110.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
			connection.setDoOutput(true);  
			connection.setDoInput(true);  
			connection.setRequestMethod("POST");   
			connection.setUseCaches(false);  
			connection.setInstanceFollowRedirects(true);  
			connection.addRequestProperty("headpara", 
					"centerId,userId,usertype,deviceType,deviceToken,"
					+ "currenVersion,buzType,devtoken,channel,appid,appkey,"
					+ "appToken,clientIp,positionX,positionY");
			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String userId = aes.encrypt("web".getBytes("UTF-8"));//各渠道客户标识
			String appkey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			
			//用于数字签名
			String parm ="centerId=00057400&userId="+userId
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5516&"
					+ "devtoken=&channel="+CHANNEL+"&appid="+appId+"&appkey="+appkey+
					"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&"
					+ "positionX="+positionX+"&positionY="+positionY;
			//用于发送http报文
			String parm1 ="centerId=00057400&userId="+userId.replace("+", "%2B")
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&"
					+ "buzType=5516&devtoken=&channel="+CHANNEL+"&appid="+appId.replace("+","%2B")+
					"&appkey="+appkey.replace("+", "%2B")+"&appToken=&"
							+ "clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&"
							+ "positionX="+positionX+"&positionY="+positionY;
		
			System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			//connection.addRequestProperty("headparaMD5", "8B74946C709BDA49D881674BE0669A14");

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
	
	
	/**排队取号*/
	public static String setOnePD(String websitecode, String jobid, String jobname, String idcardNumber, String ordertype, String getMethod) {
		String pdrs = "";//查询网点排号人数列表返回数据
		try {
			URL url = new URL(POST_URL + "appapi10107.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara",
					"centerId,userId,usertype,deviceType,deviceToken,"
					+ "currenVersion,buzType,devtoken,channel,appid,appkey,"
					+ "appToken,clientIp,websitecode,jobid,jobname,idcardNumber,ordertype,getMethod");
			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String userId = aes.encrypt(idcardNumber.getBytes("UTF-8"));//各渠道客户标识
			String appkey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));

			idcardNumber = aes.encrypt(idcardNumber.getBytes("UTF-8"));//各渠道客户标识
			//websitecode = aes.encrypt(websitecode.getBytes("UTF-8"));
			//jobid = aes.encrypt(jobid.getBytes("UTF-8"));
			//jobname = aes.encrypt(jobname.getBytes("UTF-8"));
			//ordertype = aes.encrypt(ordertype.getBytes("UTF-8"));
			//getMethod	= aes.encrypt(getMethod.getBytes("UTF-8"));

			/*
			websitecode,
			jobid
			jobname,
			idcardNumber,
			ordertype,
			getMethod
			*/

			//用于数字签名
			String parm ="centerId=00057400&userId="+userId
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5516&"
					+ "devtoken=&channel="+CHANNEL+"&appid="+appId+"&appkey="+appkey+
					"&appToken=&clientIp=183.136.157.20"
					+ "&websitecode="+websitecode+"&jobid="+jobid
					+ "&jobname="+jobname+"&idcardNumber="+idcardNumber
					+ "&ordertype="+ordertype+"&getMethod="+getMethod;

			//用于发送http报文
			String parm1 ="centerId=00057400&userId="+userId.replace("+", "%2B")
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&"
					+ "buzType=5516&devtoken=&channel="+CHANNEL+"&appid="+appId.replace("+","%2B")+
					"&appkey="+appkey.replace("+", "%2B")+"&appToken=&"
							+ "clientIp=183.136.157.20"
							+ "&websitecode="+websitecode.replace("+", "%2B")+"&jobid="+jobid.replace("+", "%2B")
							+ "&jobname="+jobname.replace("+", "%2B")+"&idcardNumber="+idcardNumber.replace("+", "%2B")
							+ "&ordertype="+ordertype.replace("+", "%2B")+"&getMethod="+getMethod.replace("+", "%2B");

			System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			//connection.addRequestProperty("headparaMD5", "8B74946C709BDA49D881674BE0669A14");

			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			System.out.println("setOnePD()-传递参数："+parm);
			System.out.println("setOnePD()-传递参数1："+parm1);
			System.out.println("setOnePD()-headparaMD5："+RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
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
			System.out.println("setOnePD="+sb.toString());
			pdrs = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();  
		}
		return pdrs;
	}
/*身份证号：BodyCardNumber
公积金密码：password
公积金新密码：newpassword
确认公积金新密码：confirmnewpassword
个人账号：accnum*/
	/**密码修改*/
	public static String setMMXG(String BodyCardNumber, String password, String newpassword, String confirmnewpassword, String accnum) {
		String pdrs = "";//查询网点排号人数列表返回数据
		try {
			URL url = new URL(POST_URL + "appapi50009.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara",
					"centerId,userId,usertype,deviceType,deviceToken,"
							+ "currenVersion,buzType,devtoken,channel,appid,appkey,"
							+ "appToken,clientIp,BodyCardNumber,password,newpassword,confirmnewpassword,accnum");
			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			String userId = aes.encrypt(BodyCardNumber.getBytes("UTF-8"));//各渠道客户标识
			String appkey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));

			//idcardNumber = aes.encrypt(idcardNumber.getBytes("UTF-8"));//各渠道客户标识
			//websitecode = aes.encrypt(websitecode.getBytes("UTF-8"));
			//jobid = aes.encrypt(jobid.getBytes("UTF-8"));
			//jobname = aes.encrypt(jobname.getBytes("UTF-8"));
			//ordertype = aes.encrypt(ordertype.getBytes("UTF-8"));
			//getMethod	= aes.encrypt(getMethod.getBytes("UTF-8"));

			/*
			websitecode,
			jobid
			jobname,
			idcardNumber,
			ordertype,
			getMethod
			*/

			//用于数字签名
			String parm ="centerId=00057400&userId="+userId
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5448&"
					+ "devtoken=&channel="+CHANNEL+"&appid="+appId+"&appkey="+appkey+
					"&appToken=&clientIp=183.136.157.20"
					+ "&BodyCardNumber="+BodyCardNumber+"&password="+password
					+ "&newpassword="+newpassword+"&confirmnewpassword="+confirmnewpassword
					+ "&accnum="+accnum;

			//用于发送http报文
			String parm1 ="centerId=00057400&userId="+userId.replace("+", "%2B")
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&"
					+ "buzType=5448&devtoken=&channel="+CHANNEL+"&appid="+appId.replace("+","%2B")+
					"&appkey="+appkey.replace("+", "%2B")+"&appToken=&"
					+ "clientIp=183.136.157.20"
					+ "&BodyCardNumber="+BodyCardNumber.replace("+", "%2B")+"&password="+password.replace("+", "%2B")
					+ "&newpassword="+newpassword.replace("+", "%2B")+"&confirmnewpassword="+confirmnewpassword.replace("+", "%2B")
					+ "&accnum="+accnum.replace("+", "%2B") + "&AESFlag=1";

			System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			//connection.addRequestProperty("headparaMD5", "8B74946C709BDA49D881674BE0669A14");

			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			System.out.println("setOnePD()-传递参数："+parm);
			System.out.println("setOnePD()-传递参数1："+parm1);
			System.out.println("setOnePD()-headparaMD5："+RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
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
			System.out.println("setOnePD="+sb.toString());
			pdrs = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdrs;
	}

	/**预约查询*/
	public static String setYYCX(String bodyCardNumber, String centerid, String websiteCode) {
		String pdrs = "";//查询网点排号人数列表返回数据
		try {
			URL url = new URL(POST_URL + "appapi30314.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);

			//用于数字签名
			String parm ="bodyCardNumber="+bodyCardNumber+"&centerid="+centerid
					+ "&websiteCode="+websiteCode;

			//用于发送http报文
			String parm1 ="&bodyCardNumber="+bodyCardNumber.replace("+", "%2B")+"&centerid="+centerid.replace("+", "%2B")
					+ "&websiteCode="+websiteCode.replace("+", "%2B");

			System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			//connection.addRequestProperty("headparaMD5", "8B74946C709BDA49D881674BE0669A14");

			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			System.out.println("setOnePD()-传递参数："+parm);
			System.out.println("setOnePD()-传递参数1："+parm1);
			System.out.println("setOnePD()-headparaMD5："+RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
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
			System.out.println("setOnePD="+sb.toString());
			pdrs = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdrs;
	}
	public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {
		//渠道报文模拟发送
		//gjjLogin(); //微信版本 登录OK，不推荐这个函数
		//binduser("2", "330225198312301613", "", "112233");//WX Ok
		//httpURLConnectionPOST5432("2", "362422199510198710", "", "951019");//微信版本 登录OK,推荐用这个
		
		//binduser("2", "33102319831019441X", "", "123456");//WX Ok
		//unbinduser("33102319831019441X");//WX OK 返回值 {"msg":"成功","recode":"000000"}

		
		//getSingleSiteTimeNumWx(158, "2020-01-10", 202);//OK

		//getMyPDNUMWx("330903199308240613"); // NOT OK, {"msg":"渠道用户信息不存在！","recode":"999999"}
		//getMyPDNUMWx("330225198312301613"); // OK 了, {"msg":"渠道用户信息不存在！","recode":"999999"} 999999 表示要先绑定
		
		//getMyYYWx("362422199510198710");//{"recode":"100005","msg":" 预约失败：尚未注册","rows":[],"total":0}  现在OK了
		//getMyYYQD("158","市中心办事大厅","2020-03-20","60","09:00-11:00","202","提取缴存","17681808295");
		//getMyYYQD("158","市中心办事大厅","2020-03-26","60","09:00-11:00","202","提取缴存","17681808295");

		//getHouseList("", "", "1","10");//OK
		
		//getPdrs("29.86", "121.555");//OK
		


		//setOnePD("0574000801", "3", "大市外购房", "330225198312301613", "0", "微信");//排队取号
//		setOnePD("0574000801", "3", "", "330225198312301613", "0", URLEncoder.encode("微信","utf-8"));//排队取号
		//setOnePD("0574000801", "3", "", "330225198312301613", "0", "1");//排队取号
		//setMMXG("320706198111221015","123456","111111","111111","");
		//158;202;0;340223198608244696
		setYYCX("33102319831019441X","00057400","0574000801");
		
	}

}
