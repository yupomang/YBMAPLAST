package com.yondervision.mi.common.util;

import com.yondervision.mi.test.myutils.BSPDesTest;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class SecurityUtil {

	public static String HMAC(String secret,String message){
		String hash = null;
		try {
	        Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
	        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA512");
	        sha512_HMAC.init(secret_key);
	        hash = Base64.encodeBase64String(sha512_HMAC.doFinal(message.getBytes()));
//	        String hash = sun.misc.BASE64Encoder().encode(sha256_HMAC.doFinal(message.getBytes()));
	     }catch (Exception e){
	    	 e.printStackTrace();
	     }
	    return replace(hash);
	}
	public static String SHA(String text){
		String sha = null;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(text.getBytes("UTF-8"));
            sha = Base64.encodeBase64String(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
	    return replace(sha);
	}
	public static String genUUID(){
		String uuid = null;
        try {
        	uuid = UUID.randomUUID().toString().replaceAll("-", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return uuid;
	}
	public static String replace(String val){
        	return val.replaceAll("\\r\\n|\\n|\\r", "");
	}

	 public static void main(String[] args) throws UnsupportedEncodingException{
		 //System.out.println(genUUID());
		 //加密后88字符串 用固定盐12345678 密码明文为003030
		 System.out.println(HMAC("12345678","003030"));
		 System.out.println(SHA("003030"));
		 //为方便加密升级 数据库在原有的密码基础上加盐加密
		 // 秘钥
		 String key = "12345678";
		 // 密码
		 String s = "111111";
		 byte[] m = BSPDesTest.encryption(s, key);
		 byte[] n = BSPDesTest.bcd_to_asc(m);
		 String pwd = new String(n);
		 System.out.println(s + "--------" +pwd);
		 System.out.println("jiayanjiami====="+HMAC("12345678",pwd));
		 System.out.println("jiami======"+SHA(pwd));

	 }
}
