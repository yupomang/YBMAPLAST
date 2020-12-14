package com.yondervision.mi.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

public class HttpSendQuZhouPaiDui {
	
	/*排队请求URL*/
	public static final String POST_URL4 = "http://221.12.139.170:7002/YBMAPZH/appapi10106.json";
	/**获取排队信息*/
	public static   String getPdxx(String id) {  
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
			String appkey = aes.encrypt("5ff3f4873e8b2b1408e4e88bbee5c5e2".getBytes("UTF-8"));
			String appId = aes.encrypt("yondervisionselfservice50".getBytes("UTF-8"));

			//1、用于数字签名
			String parm ="centerId=00057000&userId="+userId
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5516&"
					+ "devtoken=&channel=50&appid="+appId+"&appkey="+appkey+
					"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&"
					+ "appSecret=4B6fxsM5Z6tu2nugtDssglretSjfc5jGkhIXIK0d&"
					+ "platform=web&appKey=a3ed9e06e7aaaa7eed769dff36215c4569092ab4&"
					+ "websitecode="+id;
		    //##05		//1、用于发送http报文
			String parm1 ="centerId=00057000&userId="+userId.replace("+", "%2B")
					+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&"
					+ "buzType=5516&devtoken=&channel=50&appid="+appId.replace("+","%2B")+
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
	
	
	public static void main(String[] args) throws UnknownHostException {
		getPdxx("05709001");
	}

}
