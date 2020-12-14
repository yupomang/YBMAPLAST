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

public class HttpSendWangZhan {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "ba7f3a3dd146c4613102d0a16be5107b";
	private static final String appId_V = "yondervisionwebsite30";
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
/*// 用于数字签名
			String parm = "centerId=00057400&userId=cxZToZ1plgevE7Se8Fl4GA==&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5940&
			devtoken=&channel=30&appid=B40ODwGGaHv8QtqQK4z8K2iwgUXFcW+7o7AUJdk1FAQ=&appkey=cpyywZULhBLziy+yFFQlIepXtHzlH3KUIwFxD2fQxIg+pmlAetojpgxGtQMHFCwr&
			appToken=&clientIp=183.136.157.20";
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId=cxZToZ1plgevE7Se8Fl4GA==&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5940&
			devtoken=&channel=30&appid=B40ODwGGaHv8QtqQK4z8K2iwgUXFcW%2B7o7AUJdk1FAQ=&appkey=cpyywZULhBLziy%2ByFFQlIepXtHzlH3KUIwFxD2fQxIg%2BpmlAetojpgxGtQMHFCwr&
			appToken=&clientIp=183.136.157.20&cocustname_wb=&projectname_wb=&num_web1=&num_web2=";
			*/
			// 用于数字签名
			String parm = "centerId=00057400&userId="
					+ userId
					//+"cxZToZ1plgevE7Se8Fl4GA=="
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5940&devtoken=&channel=30&appid="
					+ appId
					//+"B40ODwGGaHv8QtqQK4z8K2iwgUXFcW+7o7AUJdk1FAQ="
					+ "&appkey="
					+ appKey
					//+"cpyywZULhBLziy+yFFQlIepXtHzlH3KUIwFxD2fQxIg+pmlAetojpgxGtQMHFCwr"
					+ "&appToken=&clientIp=183.136.157.20";
			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					//+"cxZToZ1plgevE7Se8Fl4GA=="
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5940&devtoken=&channel=30"
					+ "&appid="+ appId.replace("+", "%2B")
					//+"B40ODwGGaHv8QtqQK4z8K2iwgUXFcW%2B7o7AUJdk1FAQ="
					+ "&appkey="+ appKey.replace("+", "%2B")
					//+"cpyywZULhBLziy+yFFQlIepXtHzlH3KUIwFxD2fQxIg+pmlAetojpgxGtQMHFCwr"
					+ "&appToken=&clientIp=183.136.157.20"
					+ "&cocustname_wb="+ cocustname_wb.replace("+", "%2B") 
					+ "&projectname_wb="+ projectname_wb.replace("+", "%2B") 
					+ "&num_web1="+ num_web1.replace("+", "%2B")
					+ "&num_web2="+ num_web2.replace("+", "%2B");
			System.out.println("parm0" + parm);
			System.out.println("parm1" + parm1);
			System.out.println("parm3" + EncryptionByMD5.getMD5(parm.getBytes()));
			System.out.println("parm2" + RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
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

	
	/*排队请求URL*/
	public static final String POST_URL4 = "http://61.153.144.77:7006/YBMAPZH/appapi10106.json";
	/*排队人数距离请求URL*/
	public static final String POST_URL5 = "http://61.153.144.77:7006/YBMAPZH/appapi10110.json";


	/**获取排队信息*/
	public   String getPdxx(String id) {  
		String pdxx = "";//查询网点排队信息返回数据
		try {  
			URL url = new URL(POST_URL4);  
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
	public  String getPdrs(String positionX,String positionY) {  
		String pdrs = "";//查询网点排号人数列表返回数据
		try {
			URL url = new URL(POST_URL5);  
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
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
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

		//可贷楼盘查询
//		System.out.println("可贷楼盘查询");
		//String projectname_wb,String cocustname_wb,String num_web1,  String num_web2
		httpURLConnectionPOST5940(URLEncoder.encode("澜悦花苑","utf-8"),"" , "1","10");
		//httpURLConnectionPOST5940("","" , "1","10");
		httpURLConnectionPOST5940("","","1","10");

//		System.out.println(URLEncoder.encode("佳源","utf-8"));
	}

}
