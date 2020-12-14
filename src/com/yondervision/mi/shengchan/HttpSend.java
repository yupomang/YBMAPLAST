package com.yondervision.mi.shengchan;


import java.io.BufferedReader;  
import java.io.DataOutputStream;  
import java.io.InputStreamReader;  
import java.net.HttpURLConnection;  
import java.net.InetAddress;  
import java.net.URL;  
import java.net.UnknownHostException;  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

import com.yondervision.mi.common.Constants;
import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.common.message.MessageSendMessageUtil;
import com.yondervision.mi.result.TitleInfoBean;
import com.yondervision.mi.result.TitleInfoNameFormatBean;
import com.yondervision.mi.util.JavaBeanUtil;
import com.yondervision.mi.util.JsonUtil;
import com.yondervision.mi.util.security.RSASignature;

public class HttpSend {
	public static final String GET_URL = "http://112.4.27.9/mall-back/if_user/store_list?storeId=32";  
//	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/appapi50009.json";
	public static final String POST_URL = "http://172.16.0.190:8080/YBMAPZH/appapi90419.json"; 
	//public static final String POST_URL = "http://172.16.0.190:8080/YBMAPZH/appapi90418.json"; 
	//public static final String POST_URL = "http://172.16.0.190:8080/YBMAPZH/appapi90417.json"; 
	//public static final String POST_URL = "http://172.16.0.190:8080/YBMAPZH/appapi90401.json"; 
//	public static final String POST_URL = "http://mp.wx.pangjiachen.com/weixin/sendall";
//	public static final String POST_URL = "http://10.19.143.173:88/pdjhwebservice/Service.asmx/getGroupWaitting2";

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
			//必要报文头connection.addRequestProperty("headpara", "centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp");
			//2、connection.addRequestProperty("headpara", "centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,accessToken,starttime,endtime,flag,tpl_type,tpl_name,currentPage,pageSize");
			//3、connection.addRequestProperty("headpara", "centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,accessToken,tpl_id");
			connection.addRequestProperty("headpara", "centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,accessToken,tpl_name,tpl_id,task_score,answer_id,answer_name,answer_ip");
			//1、connection.addRequestProperty("headpara", "centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,appSecret,platform,appKey");
			AesTest aes=null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
/*	1,		
 * dataMap.put("appKey", "2a12813db3135c7515805c31e29c2206d2a3800c");
	dataMap.put("appSecret", "rf6BRAByAiUctjXK69gW5yfu5VaXitIVhDKC3nT9");
	dataMap.put("platform", "web");*/

			//userId必须加密处理
			String userId = aes.encrypt("330203198212122417".getBytes("UTF-8"));
			String appKey = aes.encrypt("f53de13df5bf0d043c64467187a23759".getBytes("UTF-8"));
			String appId = aes.encrypt("yondervisionselfservice50".getBytes("UTF-8"));
			//String accnum = aes.encrypt("".getBytes("UTF-8"));
			//String bodycardnumber = aes.encrypt("330203198212122417".getBytes("UTF-8"));
			//String bodycardnumber = "330203198212122417";
			//一般测试时密码为空
			//String pwd = aes.encrypt("".getBytes("UTF-8"));
			//1、用于数字签名			
			//String parm ="centerId=00057400&userId="+userId+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5754&devtoken=&channel=50&appid="+appId+"&appkey="+appKey+"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&appSecret=4B6fxsM5Z6tu2nugtDssglretSjfc5jGkhIXIK0d&platform=web&appKey=a3ed9e06e7aaaa7eed769dff36215c4569092ab4";
			//1、用于发送http报文（综合服务平台将签名编译后与之比较）
			//String parm1 ="centerId=00057400&userId="+userId.replace("+", "%2B")+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5754&devtoken=&channel=50&appid="+appId.replace("+","%2B")+"&appkey="+appKey.replace("+", "%2B")+"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&appSecret=4B6fxsM5Z6tu2nugtDssglretSjfc5jGkhIXIK0d&platform=web&appKey=a3ed9e06e7aaaa7eed769dff36215c4569092ab4";
			//2、用于数字签名			
			//String parm ="centerId=00057400&userId="+userId+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5754&devtoken=&channel=50&appid="+appId+"&appkey="+appKey+"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&accessToken=TJho0BedNsxNU6T1KRw2YWM1NDUzOTQ1Mzc5NDAzOWE2ZjY5ZDk3YzJjMmQ1MDAqjaOlsv84lSVnMNzhTMNulJKmg6&starttime=2016-10-17 16:51:08&endtime=2016-10-18 16:51:08&flag=1&tpl_type=1&tpl_name=&currentPage=1&pageSize=1";
			//2、用于发送http报文
			//String parm1 ="centerId=00057400&userId="+userId.replace("+", "%2B")+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5754&devtoken=&channel=50&appid="+appId.replace("+", "%2B")+"&appkey="+appKey.replace("+", "%2B")+"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&accessToken=TJho0BedNsxNU6T1KRw2YWM1NDUzOTQ1Mzc5NDAzOWE2ZjY5ZDk3YzJjMmQ1MDAqjaOlsv84lSVnMNzhTMNulJKmg6&starttime=2016-10-17 16:51:08&endtime=2016-10-18 16:51:08&flag=1&tpl_type=1&tpl_name=&currentPage=1&pageSize=1";
			
			//3、用于数字签名			
			//String parm ="centerId=00057400&userId="+userId+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5754&devtoken=&channel=50&appid="+appId+"&appkey="+appKey+"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&accessToken=TJho0BedNsxNU6T1KRw2YWM1NDUzOTQ1Mzc5NDAzOWE2ZjY5ZDk3YzJjMmQ1MDAqjaOlsv84lSVnMNzhTMNulJKmg6&tpl_id=68";
			//3、用于发送http报文
			//String parm1 ="centerId=00057400&userId="+userId.replace("+","%2B")+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5754&devtoken=&channel=50&appid="+appId.replace("+", "%2B")+"&appkey="+appKey.replace("+", "%2B")+"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&accessToken=TJho0BedNsxNU6T1KRw2YWM1NDUzOTQ1Mzc5NDAzOWE2ZjY5ZDk3YzJjMmQ1MDAqjaOlsv84lSVnMNzhTMNulJKmg6&tpl_id=68";
			
			
			//4
			HashMap map = new HashMap();
			List<HashMap> list = new ArrayList();
			HashMap result = new HashMap();
			map.put("p_tpl_ids", "68|62|60");
			map.put("q_id", "122");
			map.put("a_score", "3");
			HashMap infomap = new HashMap();
			infomap.put("option_id", "1491548820696");
			infomap.put("option_score", "3");
			
			map.put("a_info", infomap);
			
			list.add(map);
			
			result.put("centerId", "00057400");
			result.put("userId", userId);
			result.put("usertype", "10");
			result.put("deviceType", "3");
			result.put("deviceToken", "");
			result.put("currenVersion", "1.0");
			result.put("buzType", "5754");
			result.put("devtoken", "");
			result.put("channel", "50");
			result.put("appid", appId);
			result.put("appkey",appKey);
			result.put("appToken", "");			
			result.put("clientIp", "DJv9mNfY9qPBA/x6cL6iFQ==");						
			result.put("accessToken", "TJho0BedNsxNU6T1KRw2YWM1NDUzOTQ1Mzc5NDAzOWE2ZjY5ZDk3YzJjMmQ1MDAqjaOlsv84lSVnMNzhTMNulJKmg6");
			result.put("tpl_name", "");
			result.put("tpl_id", "68");
			result.put("task_score", "");
			result.put("answer_id", "330203198212122417");
			result.put("answer_name", "");
			result.put("answer_ip", "172.16.0.191");
			result.put("array", list);
			
			String parm ="centerId=00057400&userId="+userId+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5754&devtoken=&channel=50&appid="+appId+"&appkey="+appKey+"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&accessToken=TJho0BedNsxNU6T1KRw2YWM1NDUzOTQ1Mzc5NDAzOWE2ZjY5ZDk3YzJjMmQ1MDAqjaOlsv84lSVnMNzhTMNulJKmg6&tpl_name=&tpl_id=68&task_score=&answer_id=330203198212122417&answer_name=&answer_ip=172.16.0.191";
			String parm0 =result.toString().substring(1);
			String parm2 = parm0.substring(0,parm0.length()-1).replace(", ","&");
			String parm1 =parm2.replace("+","%2B");
			System.out.println(parm1);
			
			//4、用于数字签名			
			//String parm ="centerId=00057400&userId="+userId+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5754&devtoken=&channel=50&appid="+appId+"&appkey="+appKey+"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&accessToken=TJho0BedNsxNU6T1KRw2YWM1NDUzOTQ1Mzc5NDAzOWE2ZjY5ZDk3YzJjMmQ1MDAqjaOlsv84lSVnMNzhTMNulJKmg6&tpl_name=&tpl_id=68&task_score=&answer_id=330203198212122417&answer_name=&answer_ip=172.16.0.191";
			//4、用于发送http报文
			//String parm1 ="centerId=00057400&userId="+userId.replace("+","%2B")+"&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5754&devtoken=&channel=50&appid="+appId.replace("+","%2B")+"&appkey="+appKey.replace("+","%2B")+"&appToken=&clientIp=DJv9mNfY9qPBA/x6cL6iFQ==&accessToken=TJho0BedNsxNU6T1KRw2YWM1NDUzOTQ1Mzc5NDAzOWE2ZjY5ZDk3YzJjMmQ1MDAqjaOlsv84lSVnMNzhTMNulJKmg6&tpl_name=&tpl_id=68&task_score=&answer_id=330203198212122417&answer_name=&answer_ip=172.16.0.191&array=[{\"p_tpl_ids\":\"68|62|60\",\"q_id\":\"122\",\"a_score\":\"3\",\"a_info\":{\"option_id\":\"1491549033809\",\"option_score\":\"3\"}}]]";
		
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
		//httpURLConectionGET();  
		httpURLConnectionPOST();  
		
		
		//文字信息
		/*HashMap mapreq = new HashMap();
		mapreq.put("content", "1234567890qwertyuiop");
		mapreq.put("centerid", "00057400");
		mapreq.put("articles", "");
		mapreq.put("pushcontent", "text");
		mapreq.put("media_id", "");
		mapreq.put("sendType", "text");
		mapreq.put("imgurl", "");
		mapreq.put("file_suffix", "");*/
		
		//图文信息
		/*HashMap mapreq = new HashMap();
		mapreq.put("centerid", "00057400");
		ArrayList temp = new ArrayList();
		StringBuffer articles = new StringBuffer();
		articles.append("[{\"thumb_media_id\":\"http://61.153.144.77:7001/YBMAPZH/downloadimg.file?filePathParam=push_msg_img&fileName=00057400/20170331094722945.jpg&isFullUrl=true\",\"author\":\"adminNB\",\"title\":\"pic_text\",\"content_source_url\":\"\",\"content\":\"<p>&nbsp;文字信息</p>\",\"digest\":\"\",\"show_cover_pic\":\"1\"}]");
		mapreq.put("articles", articles.toString());
		//{"centerid":"00057400","articles":"[{\"thumb_media_id\":\"http://61.153.144.77:7001/YBMAPZH/downloadimg.file?filePathParam\u003dpush_msg_img\u0026fileName\u003d00057400/20170331094722945.jpg\u0026isFullUrl\u003dtrue\",\"author\":\"adminNB\",\"title\":\"pic_text\",\"content_source_url\":\"\",\"content\":\"\u003cp\u003e\u0026nbsp;文字信息\u003c/p\u003e\",\"digest\":\"\",\"show_cover_pic\":\"1\"}]","pushcontent":"","media_id":"","sendType":"mpnews","imgurl":"","file_suffix":""}
		mapreq.put("pushcontent", "");
		mapreq.put("media_id", "");
		mapreq.put("sendType", "mpnews");
		mapreq.put("imgurl", "");
		mapreq.put("file_suffix", "");
		System.out.println("articles="+JsonUtil.getGson().toJson(mapreq).toString());*/
		
		//图片信息
		/*HashMap mapreq = new HashMap();
		mapreq.put("centerid", "00057400");
		mapreq.put("articles", "");
		mapreq.put("pushcontent", "232323");
		mapreq.put("media_id", "");
		mapreq.put("sendType", "image");
		mapreq.put("imgurl", "http://61.153.144.77:7001/YBMAPZH/downloadimg.file?filePathParam=push_msg_img&fileName=00057400/20170331162220252.jpg&isFullUrl=true");
		mapreq.put("file_suffix", "jpg");
		System.out.println(JsonUtil.getGson().toJson(mapreq).toString());*/
		
		//多条图文
		/*HashMap mapreq = new HashMap();
		mapreq.put("centerid", "00057400");
		StringBuffer articles = new StringBuffer();
		articles.append("[{\"thumb_media_id\":\"http://61.153.144.77:7001/YBMAPZH/downloadimg.file?filePathParam=dpush_msg_img&fileName=00057400/20170331155944789.jpg&isFullUrl=true\",\"author\":\"adminNB\",\"title\":\"1111122\",\"content_source_url\":\"\",\"content\":\"<p>&nbsp;123ewq123ewq</p>\",\"digest\":\"\",\"show_cover_pic\":\"1\"},"
			+ "{\"thumb_media_id\":\"http://61.153.144.77:7001/YBMAPZH/downloadimg.file?filePathParam=dpush_msg_img&fileName=00057400/20170331164610535.jpg&isFullUrl=true\",\"author\":\"adminNB\",\"title\":\"34567\",\"content_source_url\":\"\",\"content\":\"<p>&nbsp;文字</p>\",\"digest\":\"\",\"show_cover_pic\":\"1\"}]");
		
		mapreq.put("articles", articles.toString());
		mapreq.put("pushcontent", "");
		mapreq.put("media_id", "");
		mapreq.put("sendType", "mpnews");
		mapreq.put("imgurl", "");
		mapreq.put("file_suffix", "");
		System.out.println("articles="+JsonUtil.getGson().toJson(mapreq).toString());*/
		
		/*MessageSendMessageUtil sendMessage = new MessageSendMessageUtil();
		String msg = sendMessage.post(POST_URL, JsonUtil.getGson().toJson(mapreq).toString(), null, null, null);
		System.out.println("msg="+msg);*/
		
		//排队机查询接口模拟
		//httpURLConnectionPOST1();
		
		//数字签名验证
		/*String values = "centerId=00087100&userId=uc3ax5dYlC/Jhk1431vLqkOClzqF0+4LeqWS6dmy8dM=&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&channel=40&appid=60IHldvLlLoxb8nBdu0lxWZU6QC75Edhgscd3xdFya4=&appkey=HYzpktAxY6fgH8mZfWrFjx/54uo2yzsy3jI0KqBVvCnxauT7mjRABGyJ7bhQruO8&appToken=&tellCode=8802&brcCode=00087102&channelSeq=-11300&tranDate=2016-09-28&clientIp=0:0:0:0:0:0:0:1&buzType=5001&accnum=113099582753";
		String b = RSASignature.sign(EncryptionByMD5.getMD5(values.getBytes()), RSASignature.RSA_PRIVATE);
		System.out.println(RSASignature.doCheck(EncryptionByMD5.getMD5(values.getBytes()), b, RSASignature.RSA_ALIPAY_PUBLIC));
		*/
		
		/*List<List<TitleInfoNameFormatBean>> resultList = new ArrayList<List<TitleInfoNameFormatBean>>();
		for(int i=0;i<2;i++){
			List<TitleInfoNameFormatBean> TitleInfoBeanList = new ArrayList<TitleInfoNameFormatBean>();
			HashMap map = new HashMap();
			map.put("websitecode", i);
			map.put("websitename", i);
			map.put("distance", i);
			map.put("businesstype", i);
			map.put("tel", "1");
			map.put("servicetime", i);
			map.put("windows", i);
			map.put("address", i);
			map.put("waitcount", i);
			TitleInfoBeanList = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi10101.list", map);
			resultList.add(TitleInfoBeanList);
		}
		HashMap modelMap = new HashMap();
		modelMap.put("recode", Constants.WEB_SUCCESS_CODE);
		modelMap.put("msg", Constants.WEB_SUCCESS_MSG);
		modelMap.put("result", resultList);
		System.out.println(JsonUtil.getGson().toJson(modelMap));*/
	}

}
