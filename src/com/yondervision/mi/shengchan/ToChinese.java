package com.yondervision.mi.shengchan;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;

public class ToChinese {  
    public static void main(String[] args) throws UnsupportedEncodingException {  
//        String[] input = {  
//                "\u00E6\u00B5\u0099\u00E6\u00B1\u009F\u00E7\u009C\u0081\u00E5\u00AE\u0081\u00E6\u00B3\u00A2\u00E5\u00B8\u0082\u00E9\u0084\u009E\u00E5\u00B7\u009E\u00E5\u008C\u00BA\u00E4\u00B8\u00AD\u00E6\u00B2\u00B3\u00E8\u00A1\u0097\u00E9\u0081\u0093\u00E5\u00BD\u00A9\u00E8\u0099\u00B9\u00E6\u0096\u00B0\u00E6\u009D\u0091\u00EF\u00BC\u0092\u00EF\u00BC\u0095\u00E5\u00B9\u00A2\u00EF\u00BC\u0094\u00EF\u00BC\u0090\u00EF\u00BC\u0092\u00E5\u00AE\u00A4"  
//        };  
//        for(int i=0; i<input.length;i++){  
//            change(input[i]);  
//        }  
        System.out.println("转换前得结果："+getRandomString(16));  
        System.out.println("转换前得结果："+getRandomString(32));  
    	for (int i = 0; i <10; i++) {
			//System.out.println(URLEncoder.encode("邵建宁", "utf-8"));
//			System.out.println(URLDecoder.decode("%E9%96%AD%E9%9D%9B%E7%BC%93%E7%80%B9%EF%BF%BD", "utf-8"));
//			System.out.println(URLDecoder.decode("%E9%82%B5%E5%BB%BA%E5%AE%81", "utf-8"));
		}
    }  
    private static void change(String input) {  

//        System.out.println("转换前得结果："+input);  
        try {  
            String output = new String(input.getBytes("iso-8859-1"),"utf-8");  
            System.out.println("转换后得结果："+output);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
    }  
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
        Random random = new Random();   
        StringBuffer sb = new StringBuffer();   
        for (int i = 0; i < length; i++) {   
            int number = random.nextInt(base.length());   
            sb.append(base.charAt(number));   
        }   
        return sb.toString();   
     }  
     
}  

 