package com.yondervision.mi.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

import com.yondervision.mi.zwfwutil.RSAUtil;

public class TestHttp {

	public static void main(String[] args) throws Exception{
		//配置config.properties文件，指定那两个key的本地路径public_key，private_key
		JSONObject jsobj=new JSONObject();
		jsobj.put("sfzh", "330203198105042411");//根据需要替换
		jsobj.put("xm", "孙燕波");				//根据需要替换
		jsobj.put("ywlx", "GRYE");//这个参数是写死的
		jsobj.put("jgh", "12");//这个参数是写死的
		JSONObject req=new JSONObject();
		req.put("para", jsobj.toString());
		System.out.println(jsobj.toString());

		String reqstr="para="+RSAUtil.encrypt(jsobj.toString()).replace("+", "%2B");
		System.out.println(reqstr);
		
		String url="http://61.153.144.77:7006/YBMAPZH/webapi80005.json?centerId=00057400";//替换ip跟端口号，网站的ip端口

		String ret=send(url,"POST",reqstr,null);
		System.out.println(ret);
		System.out.println(RSAUtil.decrypt(ret));
	}
	
	public static String send(String urlString,String method,String param,Map<String,String> prop){
		if (method.equalsIgnoreCase("GET") && param != null) {
            urlString += "?" + param;
        }
		URL url=null;
		HttpURLConnection urlConnection=null;
		try {
			url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod(method);
	        urlConnection.setDoOutput(true);
	        urlConnection.setDoInput(true);
	        urlConnection.setUseCaches(false);
	        if( prop!=null ){
	        	for(String key : prop.keySet()){
	        		urlConnection.setRequestProperty(key, prop.get(key));
	        	}
	        }
	        if (method.equalsIgnoreCase("POST") && param != null) {
	            //urlConnection.getOutputStream().write(param.getBytes());
	            urlConnection.getOutputStream().write(param.getBytes("utf-8"));
	            urlConnection.getOutputStream().flush();
	            urlConnection.getOutputStream().close();
	        }
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	        StringBuffer temp = new StringBuffer();
	        String line = bufferedReader.readLine();
	        while (line != null) {  
	        	//System.out.println(line);
	            temp.append(line).append("\r\n");
	            line = bufferedReader.readLine();
	        }  
	        bufferedReader.close();
	        String ecod = urlConnection.getContentEncoding();
	        if(ecod==null){
	        	ecod="utf-8";
	        }
	        return new String(temp.toString().getBytes(),ecod);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(urlConnection!=null){
				urlConnection.disconnect();
			}
		}
		return null;
	}
}
