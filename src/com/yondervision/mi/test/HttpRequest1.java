package com.yondervision.mi.test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.yondervision.mi.zwfwutil.RSAUtil;

/**
 * author: cjianquan
 * date: 2016/9/29
 */

public class HttpRequest1 {

    // 表示服务器端的url

    private HttpRequest1() {
        // TODO Auto-generated constructor stub
    }

    /*
     * params 填写的URL的参数 encode 字节编码
     */
    public static String sendPostMessage(String strUrl,Map<String, String> params,
                                         String encode) {

        URL url = null;
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        StringBuffer stringBuffer = new StringBuffer();

        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    stringBuffer
                            .append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), encode))
                            .append("&");

                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // 删掉最后一个 & 字符
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            System.out.println("-->>" + stringBuffer.toString());

            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) url
                        .openConnection();
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setDoInput(true);// 从服务器获取数据
                httpURLConnection.setDoOutput(true);// 向服务器写入数据

                // 获得上传信息的字节大小及长度
                byte[] mydata = stringBuffer.toString().getBytes();
                // 设置请求体的类型
                httpURLConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                httpURLConnection.setRequestProperty("Content-Lenth",
                        String.valueOf(mydata.length));

                // 获得输出流，向服务器输出数据
                OutputStream outputStream = (OutputStream) httpURLConnection
                        .getOutputStream();
                outputStream.write(mydata);

                // 获得服务器响应的结果和状态码
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {

                    // 获得输入流，从服务器端获得数据
                    InputStream inputStream = (InputStream) httpURLConnection
                            .getInputStream();
                    return (changeInputStream(inputStream, encode));

                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return "";
    }

    /*
     * // 把从输入流InputStream按指定编码格式encode变成字符串String
     */
    public static String changeInputStream(InputStream inputStream,
                                           String encode) {

        // ByteArrayOutputStream 一般叫做内存流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {

            try {
                while ((len = inputStream.read(data)) != -1) {
                    byteArrayOutputStream.write(data, 0, len);

                }
                result = new String(byteArrayOutputStream.toByteArray(), encode);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return result;
    }

    public static void main(String[] args) throws Exception {  

          HashMap map =new HashMap();
          
          
		JSONObject jsobj=new JSONObject();
		jsobj.put("sfzh", "330726198810170330");
		jsobj.put("xm", "张鹏程");
		jsobj.put("ywlx", "GRYE");
		jsobj.put("jgh", "11");
		String para=RSAUtil.encrypt(jsobj.toString());
		String para2=RSAUtil.decrypt(RSAUtil.encrypt(jsobj.toString()));
		System.out.println("para2"+para2);
		map.put("para",para);//根据需要替换        
          String sr=HttpRequest1.sendPostMessage("http://61.153.144.77:7001/YBMAPZH/webapi80005.json?centerId=00057400", map,"UTF-8");  
      	  System.out.println("sr==="+sr);
        	String rs=RSAUtil.decrypt(sr);
        	System.out.println(rs);

/*      	String sr1="222";
      	String rs=RSAUtil.encrypt(sr1);
    	System.out.println(rs);
    	
    	System.out.println(RSAUtil.decrypt(rs));
      	String rs2=RSAUtil.encrypt(sr1);
    	System.out.println(rs2);
    	System.out.println(RSAUtil.decrypt(rs2));*/

    }
}  