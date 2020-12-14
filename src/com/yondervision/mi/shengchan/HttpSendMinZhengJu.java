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

public class HttpSendMinZhengJu {
	private static final String clientIp_V="172.10.0.1";
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/appapi50006.json";
	public static void httpURLConnectionPOST () {  
		try {  
			URL url = new URL(POST_URL);  
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
			connection.addRequestProperty("headpara", "centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,password,accnum");			
			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String clientIp = aes.encrypt(clientIp_V.getBytes("UTF-8"));
			//userId必须加密处理
			String userId = aes.encrypt("330726198810170330".getBytes("UTF-8"));
			String appKey = aes.encrypt("c7e45eaa6e7940abd6069400316021dd".getBytes("UTF-8"));
			String appId = aes.encrypt("yondervisionapp41".getBytes("UTF-8"));
			String certinum = aes.encrypt("330726198810170330".getBytes("UTF-8"));			
			String accnum = aes.encrypt("".getBytes("UTF-8"));	
			String pwd = aes.encrypt("".getBytes("UTF-8"));	
			//用于数字签名			
			String parm ="centerId=00057400&userId="+userId+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=41&appid="+appId+"&appkey="+appKey+"&appToken=&clientIp="+clientIp+"&password="+pwd+"&accnum="+accnum;
			//用于发送http报文
			String parm1 ="centerId=00057400&userId="+userId.replace("+","%2B")+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=41&appid="+appId.replace("+", "%2B")+"&appkey="+appKey.replace("+", "%2B")+"&appToken=&clientIp="+clientIp+"&bodyCardNumber="+certinum.replace("+","%2B")+"&password="+pwd.replace("+","%2B")+"&accnum="+accnum.replace("+","%2B");
			System.out.println("parm"+parm);
			System.out.println("parm1"+parm1);
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");      
			connection.connect();  
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
			System.out.println("传递参数："+parm1);  
			dataout.writeBytes(parm1);  
			dataout.flush();   
			dataout.close(); 
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));   
			String line;  
			StringBuilder sb = new StringBuilder();
			while ((line = bf.readLine()) != null) {  
				sb.append(line).append(System.getProperty("line.separator"));  
			}			// 重要且易忽略步骤 (关闭流,切记!)   
			bf.close();    
			connection.disconnect(); // 销毁连接  
			System.out.println(sb.toString());  
		} catch (Exception e) {  
			e.printStackTrace();  
		}
	}
	
	public static void main(String[] args) throws UnknownHostException {  
		//渠道报文模拟发送
		httpURLConnectionPOST();  		
	}

}
