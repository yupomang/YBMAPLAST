package com.yondervision.mi.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class HttpSend {
	public static List<Element> httpURLConnectionPOST (String postUrl, String parm1) {  
		List<Element> result = null; 
		try {  
			URL url = new URL(postUrl);  
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
			
			Document document = null;
			SAXReader reader = new SAXReader();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(sb.toString().getBytes());
			InputStreamReader ir = new InputStreamReader(inputStream);
			//document已经获取到xml文件
			document = reader.read(ir);
			Element rootElement = document.getRootElement();
			Element newDataSet = rootElement.element("NewDataSet");
			result = newDataSet.elements();
		} catch (Exception e) {  
			e.printStackTrace();  
		}
		return result;
	}
	
	public static void main(String[] args) throws UnknownHostException {  
		//排队机查询接口模拟
		httpURLConnectionPOST("http://10.19.143.173:88/pdjhwebservice/Service.asmx/getGroupWaitting2", "ctrllerid=1");
	}
}
