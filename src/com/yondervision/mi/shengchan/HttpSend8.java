package com.yondervision.mi.shengchan;


import java.io.BufferedReader;  
import java.io.DataOutputStream;  
import java.io.InputStreamReader;  
import java.net.HttpURLConnection;  
import java.net.URL;  
import java.net.UnknownHostException;  

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

public class HttpSend8 {

	public static final String POST_URL = "http://172.16.0.165:7001/YBMAPZH/appapi50006.json";
	
	/** 
	* 接口调用  POST 
	*/
	public static void httpURLConnectionPOST () {  
		try {  
			URL url = new URL(POST_URL);  
			// 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)  
			// 此时cnnection只是为一个连接对象,待连接中  
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
			// application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据  
			// ;charset=utf-8 必须要，不然妙兜那边会出现乱码【★★★★★】  
			//addRequestProperty添加相同的key不会覆盖，如果相同，内容会以{name1,name2}  
			//--connection.addRequestProperty("from", "sfzh");  //来源哪个系统  
			connection.addRequestProperty("headpara", "centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,bodyCardNumber,password,accnum");
			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = aes.encrypt("330205198909033329".getBytes("UTF-8"));
			String appId = aes.encrypt("yondervisionwebservice40".getBytes("UTF-8"));
			String appKeyZH = aes.encrypt("b5b1c6938e5d0cef72457bd788ffdef0".getBytes("UTF-8"));
			String clientIp = aes.encrypt("172.16.0.165".getBytes("UTF-8"));
			
			
			String bodyCardNumber = aes.encrypt("330205198909033329".getBytes("UTF-8")) ;		
			String password = aes.encrypt("111111".getBytes("UTF-8"));
			String accnum = aes.encrypt("0237550054".getBytes("UTF-8"));
			//用于数字签名
			String parm = "centerId=00057400&userId="+userId+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=40&appid="
					+appId+"&appkey="+appKeyZH+"&appToken=&clientIp="+clientIp+"&bodyCardNumber="+bodyCardNumber+"&password="+password+"&accnum="+accnum;
			//用于发送http报文
			String parm1 = "centerId=00057400&userId="+userId+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&devtoken=&channel=40&appid="
					+appId+"&appkey="+appKeyZH+"&appToken=&clientIp="+clientIp+"&bodyCardNumber="+bodyCardNumber+"&password="+password+"&accnum="+accnum;
			parm1 = parm1.replace("+", "%2B");
			System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()), RSASignature.RSA_PRIVATE));
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
			dataout.close(); // 重要且易忽略步骤 (关闭流,切记!)   
			//System.out.println(connection.getResponseCode());  
			// 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)  
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));   
			String line;  
			StringBuilder sb = new StringBuilder(); // 用来存储响应数据  

			// 循环读取流,若不到结尾处  
			while ((line = bf.readLine()) != null) {  
				//sb.append(bf.readLine());  
				sb.append(line).append(System.getProperty("line.separator"));  
			}
			bf.close();    // 重要且易忽略步骤 (关闭流,切记!)   
			connection.disconnect(); // 销毁连接  
			System.out.println(sb.toString());  
		} catch (Exception e) {  
			e.printStackTrace();  
		}
	}
	
	public static void main(String[] args) throws UnknownHostException {  
		httpURLConnectionPOST();  
	}

}
