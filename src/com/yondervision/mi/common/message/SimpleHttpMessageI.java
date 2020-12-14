package com.yondervision.mi.common.message;

import java.util.HashMap;

public interface SimpleHttpMessageI {
	
	public String sendGet(String url, String charset)throws Exception;
	
	public String sendPost(String url, HashMap params, String charset)throws Exception;
	
	/**
     * 同步内容导出到第三方平台，发送post方法的请求,有签名认证
     * 
     * @param url
     *            发送请求的URL
     * @param params
     *            参数        
     * @param charset
     *            编码格式        
     * @return URL 所代表远程资源的响应结果
     */
	public String synchroExportContentPostBySign(String url, String param, String centerid)throws Exception;
}
