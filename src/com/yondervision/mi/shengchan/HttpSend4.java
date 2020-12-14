package com.yondervision.mi.shengchan;

import java.io.BufferedReader;  
import java.io.DataOutputStream;  
import java.io.InputStreamReader;  
import java.net.HttpURLConnection;  
import java.net.InetAddress;  
import java.net.URL;  
import java.net.UnknownHostException;  
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

public class HttpSend4 {
	public static final String GET_URL = "http://112.4.27.9/mall-back/if_user/store_list?storeId=32";  

	public static final String POST_URL = "http://10.11.20.70:9080/YBMAPZH/appapi90419.json"; 

	/** 
	* 接口调用 GET 
	*/
	public static void httpURLConectionGET() {  
		try {  
			URL url = new URL(GET_URL);    //把字符串转换为URL请求地址  
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接  
			//addRequestProperty添加相同的key不会覆盖，如果相同，内容会以{name1,name2}  
			connection.addRequestProperty("from", "sfzh");  //来源哪个系统  
			//setRequestProperty添加相同的key会覆盖value信息  
			//setRequestProperty方法，如果key存在，则覆盖；不存在，直接添加。  
			//addRequestProperty方法，不管key存在不存在，直接添加。  
			connection.setRequestProperty("user", "user");  //访问申请用户  
			InetAddress address = InetAddress.getLocalHost();   
			String ip=address.getHostAddress();//获得本机IP  
			connection.setRequestProperty("ip",ip);  //请求来源IP  
			connection.setRequestProperty("encry", "00000");  
			connection.connect();// 连接会话  
			// 获取输入流  
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));  
			String line;  
			StringBuilder sb = new StringBuilder();  
			while ((line = br.readLine()) != null) {// 循环读取流  
				sb.append(line);  
			}
			br.close();// 关闭流  
			connection.disconnect();// 断开连接  
			System.out.println(sb.toString());  
		} catch (Exception e) {  
			e.printStackTrace();  
			System.out.println("失败!");  
		}
	}

	/** 
	* 接口调用  POST 
	*/
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
			// application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据  
			// ;charset=utf-8 必须要，不然妙兜那边会出现乱码【★★★★★】  
			//addRequestProperty添加相同的key不会覆盖，如果相同，内容会以{name1,name2}  
			//--connection.addRequestProperty("from", "sfzh");  //来源哪个系统  
			connection.addRequestProperty("headpara", "centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,accessToken,tpl_name,tpl_id,task_score,answer_id,answer_name,answer_ip");			
			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			//userId必须加密处理
			String userId = aes.encrypt("330203198212122417".getBytes("UTF-8"));
			String appKey = aes.encrypt("8084005a0eaa977400faa441f70d1a3b".getBytes("UTF-8"));
			String appId = aes.encrypt("yondervisionwebsite30".getBytes("UTF-8"));
								
			HashMap map = new HashMap();
			List<HashMap> list = new ArrayList();
			HashMap result = new HashMap();
			map.put("\"p_tpl_ids\"", "\"68|62|61\"");
			map.put("\"q_id\"", "\"122\"");			
			map.put("\"a_score\"", "\"5\"");
			HashMap infomap = new HashMap();
			infomap.put("\"option_id\"", "\"1491548820696\"");
			infomap.put("\"option_score\"", "\"5\"");
			
			map.put("a_info", infomap);
			
			list.add(map);
			
			result.put("centerId", "00087100");
			result.put("userId", userId);
			result.put("usertype", "10");
			result.put("deviceType", "3");
			result.put("deviceToken", "");
			result.put("currenVersion", "1.0");
			result.put("buzType", "5754");
			result.put("devtoken", "");
			result.put("channel", "30");
			result.put("appid", appId);
			result.put("appkey",appKey);
			result.put("appToken", "");			
			result.put("clientIp", "DJv9mNfY9qPBA/x6cL6iFQ==");						
			result.put("accessToken", "AelkYidOHZ2aSe0YQbTDZjNjYTQ2NjU5ZTg2NGRmNTg1ZTYyNzUyNjFkMWQ0NzUjeHsqjMYvWEBElBheBTga2gWgVQ");
			String abc= java.net.URLEncoder.encode("最佳银行","UTF-8");
			result.put("tpl_name", abc.replace("+","%2B"));
			result.put("tpl_id", "68");
			result.put("task_score", "");
			result.put("answer_id", "330203198212122417");
			result.put("answer_name", "nihao");
			result.put("answer_ip", "172.16.0.191");
									
			String parm ="centerId=00087100&userId="+userId+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5754&devtoken=&channel=30&appid="+appId+"&appkey="+appKey+"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&accessToken=AelkYidOHZ2aSe0YQbTDZjNjYTQ2NjU5ZTg2NGRmNTg1ZTYyNzUyNjFkMWQ0NzUjeHsqjMYvWEBElBheBTga2gWgVQ&tpl_name=最佳银行&tpl_id=68&task_score=&answer_id=330203198212122417&answer_name=nihao&answer_ip=172.16.0.191";
			
			String parm0 =result.toString();			
			String parm1 = parm0.substring(1,parm0.length()-1).replace("+","%2B").replace(", ","&")+"&array="+list.toString().replace("=",":");
		
			
			System.out.println("=========="+parm);
			System.out.println(parm1);
								
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

	public static void httpURLConnectionPOST1 () {  
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
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");      
			// 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)  
			connection.connect();  
			// 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)  
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
			String parm1 = "ctrllerid=1";
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
		//渠道报文模拟发送
		httpURLConnectionPOST();  
		
	}

}
