package com.yondervision.mi.test;

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

public class HttpSendDaShuJu {
	public static final String POST_URL = "http://59.202.58.68/gateway/api/popInfo.htm?";
	public static String encrypt(String value) {
		if(value==null||value.trim().length()==0){
			return null;
		}
		MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        byte[] byteArray = value.getBytes();
        byte[] md5Bytes = md5.digest(byteArray);        
        return String.valueOf(Hex.encodeHex(md5Bytes)); 
	}

	public static void httpURLConnectionPOST () {  
		try {  
			URL url = new URL(POST_URL);  
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
			connection.setDoOutput(true);  
			connection.setDoInput(true);  
			connection.setRequestMethod("POST");  
			connection.setUseCaches(false);  
			connection.setInstanceFollowRedirects(true);  
			connection.addRequestProperty("headpara", "appKey,appSecret,requestTime");			
			
			String appKey = "9ea84ff99a604620b96b9d87b2fb6cd2".toLowerCase();
			String appSecret = "cc9c4b7532714f658b24c0009a18ad7f".toLowerCase();
			String requestTime = "1489729457800".toLowerCase();
			String sign = encrypt(appKey+appSecret+requestTime);
			System.out.println("sign="+sign);
			String cardId = "330921196609224026";

			//用于数字签名			
			String parm ="appkey="+appKey+"&sign="+sign+"&requestTime="+requestTime+"&cardId="+cardId;
			//用于发送http报文
			String parm1 ="appkey="+appKey+"&sign="+sign+"&requestTime="+requestTime+"&cardId="+cardId;

			System.out.println("parm:"+parm);
			System.out.println("parm1:"+parm1);
								
			System.out.println(EncryptionByMD5.getMD5(parm.getBytes()));
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
			}
			bf.close();    
			connection.disconnect(); 
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
