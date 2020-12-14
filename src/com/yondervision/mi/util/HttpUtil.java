package com.yondervision.mi.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName: HttpUtil
 * @Description: HTTP工具类
 * @author jinbo.tan
 * @date 2016-7-20
 */
public class HttpUtil {
	protected static Log LOG = LogFactory.getLog(HttpUtil.class);
	/**
	 * @Description: 发送http请求
	 * @param url  地址
	 * @param method post或get
	 * @param paramArray 传递的参数
	 * @return Object 返回值
	 * @throws
	 */
	public static String sendRequest(String url, String method, Map<String, String> paramMap, String charset) throws Exception{
		String param = "";
		param = map2HttpParam(paramMap, charset);
		System.out.println("HttpUtil------param==="+param);
		return getResponse(url, charset, param, method);
	}
	
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @Description: map转http参数
	 * @param paramMap
	 * @return String
	 * @throws
	 */
	public static String map2HttpParam(Map<String, String> paramMap, String charset) throws UnsupportedEncodingException {
		if(paramMap == null || paramMap.size() == 0) {
			return "";
		}
		StringBuffer param = new StringBuffer();
		Iterator<String> it = paramMap.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			String value = paramMap.get(key);
			param.append("&").append(key).append("=");
			if(value != null && !"".equals(value)) {
				param.append(URLEncoder.encode(value, charset));
			}
		}
		return param.toString();
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String getResponse(String url, String charset, String param, String method) throws Exception{
		String respTxt = "";
		try {
			if (charset == null || charset.trim().equals("")) {
				charset = "UTF-8";
			}
			URL urlObj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
			conn.setDoOutput(true);
			conn.setRequestMethod(method);
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			PrintWriter out = new PrintWriter(new OutputStreamWriter(
					conn.getOutputStream(), charset));
			out.println(param);
			out.close();
			
			java.io.BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			String lineTxt;
			while ((lineTxt = in.readLine()) != null) {
				respTxt += lineTxt;
			}
			in.close();
		}
		catch (Exception e) {
			LOG.error("http url[" + url + "]发送失败:" + e);
			throw e;
		}
		return respTxt;
	}
	
}
