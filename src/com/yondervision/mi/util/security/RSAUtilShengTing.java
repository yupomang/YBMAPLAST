package com.yondervision.mi.util.security;


import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Arrays;


public class RSAUtilShengTing {
/** 指定加密算法为DESede */
private static String ALGORITHM = "RSA/ECB/PKCS1Padding";
/** 指定key的大小 */
private static int KEYSIZE = 1024;
/** 指定公钥存放文件 */
private static String PUBLIC_KEY_FILE = "D:/workspace/gjjyecx/WebRoot/tmpPublicKey.dat";
/** 指定私钥存放文件 */
private static String PRIVATE_KEY_FILE = "D:/workspace/gjjyecx/WebRoot/tmpPrivateKey.dat";
/** *//** 
 * RSA最大加密明文大小 
 */  
private static final int MAX_ENCRYPT_BLOCK = 117;  
 
private static final String OS = System.getProperty("os.name"); 

private static final String LC = OS.toLowerCase().startsWith("win")?"\r\n":"\n";

/** *//** 
 * RSA最大解密密文大小 
 */ 
private static final int MAX_DECRYPT_BLOCK = 128;  

/**
* 生成密钥对
*/
private static void generateKeyPair() throws Exception{
  /** RSA算法要求有一个可信任的随机数源 */
   SecureRandom sr = new SecureRandom();
   /** 为RSA算法创建一个KeyPairGenerator对象 */
   KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
  /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
   kpg.initialize(KEYSIZE, sr);
   /** 生成密匙对 */
   KeyPair kp = kpg.generateKeyPair();
   System.out.println("公钥 :"+kp.getPublic().toString());
   /** 得到公钥 */
   Key publicKey = kp.getPublic();
   /** 得到私钥 */
   System.out.println("公钥 :"+kp.getPrivate().toString());
   Key privateKey = kp.getPrivate();
   /** 用对象流将生成的密钥写入文件 */
   ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));
   ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE));
   oos1.writeObject(publicKey);
   oos2.writeObject(privateKey);
   /** 清空缓存，关闭文件输出流 */
   oos1.close();
   oos2.close();
}

/**
* 加密方法
* source： 源数据
*/
public static String encrypt(String source, String keyfile) throws Exception{
   //generateKeyPair();
   /** 将文件中的公钥对象读出 */
   PUBLIC_KEY_FILE = keyfile;
   ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
   Key key = (Key) ois.readObject();
   ois.close();
   /** 得到Cipher对象来实现对源数据的RSA加密 */
   Cipher cipher = Cipher.getInstance(ALGORITHM);
   cipher.init(Cipher.ENCRYPT_MODE, key);
   byte[] b = source.getBytes("UTF-8");
   int inputLen = b.length;  
   ByteArrayOutputStream out = new ByteArrayOutputStream();  
   int offSet = 0;  
   byte[] cache;  
   int i = 0;  
   // 对数据分段加密  
   while (inputLen - offSet > 0) {  
       if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
           cache = cipher.doFinal(b, offSet, MAX_ENCRYPT_BLOCK);  
       } else {  
           cache = cipher.doFinal(b, offSet, inputLen - offSet);  
       }  
       out.write(cache, 0, cache.length);  
       i++;  
       offSet = i * MAX_ENCRYPT_BLOCK;  
   }  
   byte[] encryptedData = out.toByteArray();  
   out.close();  
   BASE64Encoder encoder = new BASE64Encoder();
//   String ens = encoder.encode(encryptedData);
   String ens = encoder.encode(encryptedData);
   return ens.replaceAll(LC, "");
}

/**
* 解密算法
* cryptograph:密文
*/
public static String decrypt(String cryptograph, String keyfile) throws Exception{
   /** 将文件中的私钥对象读出 */
   PRIVATE_KEY_FILE = keyfile;
   ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
   Key key = (Key) ois.readObject();
   System.out.println(key);
   /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
   Cipher cipher = Cipher.getInstance(ALGORITHM);
   cipher.init(Cipher.DECRYPT_MODE, key);
   BASE64Decoder decoder = new BASE64Decoder();
   byte[] b1 = decoder.decodeBuffer(cryptograph);
 
   int inputLen = b1.length;  
   ByteArrayOutputStream out = new ByteArrayOutputStream();  
   int offSet = 0;  
   byte[] cache;  
   int i = 0;  
   // 对数据分段解密  
   while (inputLen - offSet > 0) {  
       if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
           cache = cipher.doFinal(b1, offSet, MAX_DECRYPT_BLOCK);  
       } else {  
           cache = cipher.doFinal(b1, offSet, inputLen - offSet);  
       }  
       out.write(cache, 0, cache.length);  
       i++;  
       offSet = i * MAX_DECRYPT_BLOCK;  
   }  
   byte[] decryptedData = out.toByteArray();  
   out.close();  	   
//   System.out.println("~~~~~~~~~~"+Arrays.toString(decryptedData));
   
   return new String(decryptedData,"UTF-8").replaceAll(LC, ""); 
}
public static void main(String[] args) throws Exception {
   String  para="{\"SRFZJHM\":\"512201196911141311\",\"SZCS\":\"330200\",\"HTBH\":\"2018330205YS0032825\"}";
   String  enc_grhxmx = encrypt(para,"C:/nbPublicKey.dat");//生成的密文
   System.out.println("加密结果：\n"+enc_grhxmx);  
   
   String target = decrypt("M4g8/v3b489vjwQA04mdlSBZ5xHlX0fY09WLyMQx+4MlxxNGUZKH/MHrKcqHbnSwSCTq1jdMQzT/2RKHCvLORCVqVlaa2plPMMi0gJiq1TwUrPl6GrEWZPaWhbpZIHJvVXyrG3g9yT92JL0g754xZy766QyuTxDrrqPzAofqE8o="
		   ,"C:/nbPrivateKey.dat");//解密密文
   System.out.println("target"+target);
   
   String target1 = decrypt("X89hQjpwuSZmnc0sdOXrTOdPJHlL%2F6x1PgEcfFWpQiUZ0k04UfbrKnNuzSsLXZ4GNFIKbxbDkZNR%2BropMn0jSRe4MkjvL41tCBOxHD6RCBXAeoh5ocpFh0wssHVqvzkRSaWIe0hGzYcKf5b53AaOJ7Vy8hYEXJw%2FrlhT2Jg4sD4%3D"
		   ,"C:/nbPrivateKey.dat");//解密密文
   System.out.println("target1"+target1);
   

}
}