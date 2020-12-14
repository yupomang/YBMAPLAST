package com.yondervision.mi.test;


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


public class RSAUtilWangWenJu {
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
   String grzhxx ="{\"ywid\":\"2ae4eb7569f611e895486c92bf2c16f5\",\"ywbh\":\"null581806071000001\",\"sqrlx\":\"1\",\"sfzh\":\"330211196907031029\",\"xm\":\"褚荣鑫\",\"sjhm\":\"\",\"jgh\":\"11\",\"ywbm\":\"20000-001\",\"qdmc\":\"2\",\"sqsj\":\"2018-06-07 09:57:43\",\"pageno\":\"1\",\"pagesize\":\"-1\"}";//要加密的字符串
   String enc_grzhxx = encrypt(grzhxx,"E:/nbPublicKey.dat");//生成的密文
//   System.out.println("个人账户信息查询\n"+enc_grzhxx);
   
   
   String grzhmx ="{\"ywid\": \"2ae4eb7569f611e895486c92bf2c16f5\",\"ywbh\": \"null581806071000001\",\"sqrlx\": \"1\",\"sfzh\": \"330211196907031029\",\"grzh\": \"0000004071\",\"xm\": \"褚荣鑫\",\"sjhm\": \"\",\"jgh\": \"11\",\"ywbm\": \"20000-001\",\"qdmc\": \"2\",\"sqsj\": \"2018-06-07 09:57:43\",\"pageno\": \"1\",\"pagesize\": \"-1\",\"qsny\": \"2016-01\",\"jzny\": \"2018-06\"}";//要加密的字符串
   String enc_grzhmx = encrypt(grzhmx,"E:/nbPublicKey.dat");//生成的密文
//   System.out.println("个人账户明细查询：\n"+enc_grzhmx);
   
   
   String grdcjd ="{\"ywid\": \"2ae4eb7569f611e895486c92bf2c16f5\",\"ywbh\": \"null581806071000001\",\"sqrlx\": \"1\",\"sfzh\": \"330222197401206008\",\"grzh\": \"0237132210\",\"xm\": \"胡央明\",\"sjhm\": \"\",\"jgh\": \"11\",\"ywbm\": \"20000-001\",\"qdmc\": \"2\",\"sqsj\": \"2018-06-07 09:57:43\",\"pageno\": \"1\",\"pagesize\": \"-1\"}";//要加密的字符串
   String enc_grdcjd = encrypt(grdcjd,"E:/nbPublicKey.dat");//生成的密文
//   System.out.println("贷款审批进度查询：\n"+enc_grdcjd);
   
   
   String grhxmx="{\"ywid\": \"173b5f2f8fe611e8a2a300163e0cb5c8\",\"ywbh\": \"null581807251000001\",\"sqrlx\": \"1\",\"sfzh\": \"330227197212184913\",\"xm\": \"张伟\",\"sjhm\": \"\",\"jgh\": \"11\",\"ywbm\": \"20000-014\",\"qdmc\": \"2\",\"sqsj\": \"2018-07-25 16:38:22\",\"pageno\": \"1\",\"pagesize\": \"-1\",\"tqyy1\": \"02491-008\",\"fjmx\": []}";
   String enc_grhxmx = encrypt(grhxmx,"E:/nbPublicKey.dat");//生成的密文
   System.out.println("个人还款明细查询：\n"+enc_grhxmx);  
   
  // cryptograph = "ETjKOrSnfYK+j0p3wlGKUzrgDENY5G8bJppXk+/2IctK7xUZIBt2ievQluYoGXdDOxlj5V3ljO3fEPLjBkYWImR4Paqsg6KD3KaBiC5GUrRd+bo0FKtrMv5CcPDYihU1JiVy69K9/bXvAoP1UIBk+6eSQ1loGDZdNWKE6QbH0KQRDtftOob0Sc3P/xTjF6z/FpKcsR7YLCQ2634PlMhvxuNpyLrX8s9eN/ktNV6uthzYnGiX/uiqsZZSk7UurDnKXi0c71srub0x9m8dltg5fIj8wWiU4Q+D9K4+DErjZiOyDxL4dVHI2QVNSqSZl1PHHNu8qhIpwL68r/SDHQaHGHKkgz1w55aGClQj/EaN7wn3SfO4aBSIPAlKTS09BBRfiXpxxg2p+i7PVgCzBDs8Sbk9jFkFIfVT/opK6Us7xmRMYNOUd6ccQVzUtFnKbHAAjNTuVKVpQTDKr4cSHRHN+kpFv3PToB2H7nfHwp2zxE2EVysQa+13Sb1BFYHINBl8";
   //enc_grdcjd = "Q6M8HIvAhG0mJTldewtUyk0X9jivnyJbwWjIAy8noSZ+KZezekEkU/frhyCekJrQyWnWBayDfD0BixQ1LoRyAz9TtU8TRfC8uePk7lECImQ64TZ1fBxhGHUNx+oDs2rcHY7Hv4orUBchA7jVvvpVeXy8aJubO3HiYPWVQqMiVWR5b8Yrgtkm3TGoUabbQzzyHY204YOa6nA+6/+wSf99+EpfUOlKp+6pu3jmNqFgFDeU0Nj2jhCNOlK2048RZSwZxlmqSfHEzbTFKCx74Ec2xt7BDzNtSBn4J+ggjeMbSRc0l+f4eOTab6pStMNvH+oRKlm2TX4TKes4dBNQFd+YtlTU7OuPJrGBlNMK7c6/jCZ68acNdOBtVZh++f5Ere75wQ8mdHwXjGVfzJsL+FEcvwykg8eCRNffeOk1csSQN7dPsyv3ZR6ycfrUSB1MqLM05utHo1ALwb7frGyqFWjvClhdpRQO+Ak8L9KM8NCpD1QNxHq0ge6dS9meWDEEXbns";
   String target = decrypt("VzezTOkhS+2qCmnRqYz2yVz1+6Sh2XPCiPpJ75XpL+uX80U3SBb3gVMD0ZXyyNjcAsFMFQu77qzl2Uw+uxdIrudUE4CsLvVcPxwBpqCauBju2FB1hcyL9CudY7RIsRrjl65hP8fJZjruk2lLClguGw0uYkQf7zbSpRGYUhkpmOABE4+BzMuW4XjKS6LVqClydcypcZOjxmMQcOwpRedZJu1+EIDc9HRusXI12y3H1IkjBK4dE5oOb6XFshb0umAeESz0ly5uP/z1mfq0mxEsOIjhkYLgkqe3uHDEE6LwJbVOE0bIjPaY0xDg2+XACy0CLn2SuJ7TIp1MO+fOMME1qEQlkcloqb51UmuABegjUZSKp8B676V7cCsTOnxxFKMRu6nVEhJAvX8SuFWts9X+ic7L5JRclw3gbWkYkUqepnDIRyb05dcR+sMyaa4DUzDRfLKUz448RlpmJ0iqUPVpcgE+e19KJm0T0B3Vx39+FUjWSK6rhgfX89usorlovRW5Ji3c6HWo6LhOZ8hs6/OHRPHyakQD8CS8OaK8vYTYHsJ2AyuxXofoClnLvz8E1jmp3skxzW7sDwmluut3iAsGcgblpdM2JTWXctLC9BXdumo2Uphn0VQoHNgC1xklXBdS6Y6xVMn/AmMC75zLnQTcYEWZHFeAPR1cKE8utyrsF11PZJ8KmEwvgLwxR8GgILFYGuQNzevR8XBMa1G20+b7Wac9SQVg7HfkVDK8THscmvBDcwgtUMWJPDyjXlpvpf+ylq/L3nNn5ADcqqvwgU90GXR1qZceos6O5M1MH9mOtnvM5mi0inLR/SLRhdnCcoTuaO17MO+wCuVrYZJVgI43Cg00I6lxknLy1TgAltmQOlSBdG/6SSelAp+4oo42IwF/g3nmlnoF3H3J6pGVaWHO/npM1Y4NkqiT39xCttJcNPpm1MilrIHcd9nIqIL8WrhSpMrl82VtXcn7k5a7iYo/s+zJJZ59c5reVnyfP0ktl6h2Cpz7cvr8mxvjT/ZUBlVzmKj2NkZ53TDe+6IBa40LxOKmv0IH/kzQWm8QjZcXIsu9fYOBY1rjCYi5D5NJhTmBdhEyBd3Sno7RwzeFUwi8zpeMGbRQ/ZnUobPSGyumBK8RH5oZqIJUJA2ZCF1Gkj7EWepd87HU3l54kbhfkJ7LcjgSYqmAWHc/DL+3GN++3jM="
		   ,"E:/nbPrivateKey.dat");//解密密文
   System.out.println("target"+target);
   
/*    
   String grkded="{	\"ywid\": \"2ae4eb7569f611e895486c92bf2c16f5\",\"ywbh\": \"null581806071000001\",\"sqrlx\": \"1\",\"sfzh\": \"330203197609061216\",\"grzh\": \"0000207158\",\"xm\": \"李宁\",\"sjhm\": \"\",\"jgh\": \"11\",\"ywbm\": \"20000-001\",\"qdmc\": \"2\",\"sqsj\": \"2018-06-07 09:57:43\",\"pageno\": \"1\",\"pagesize\": \"-1\"}";
   String enc_grkded = encrypt(grkded,"C:\\Users\\houzl\\Desktop\\sptkey/nbPublicKey.dat");//生成的密文
   System.out.println("个人可贷额度：\n"+enc_grkded);  
*/    
   String grjbxx="{\"ywid\": \"2ae4eb7569f611e895486c92bf2c16f5\",\"ywbh\": \"null581806071000001\",\"sqrlx\": \"1\",\"jgh\": \"11\",\"ywbm\": \"20000-002\",\"qdmc\": \"2\",\"sqsj\": \"2018-06-07 09:57:43\",\"grzh\":\"0000088658\",\"sfzh\":\"330227197212184913\",\"qsny\":\"2018-01\",\"jzny\":\"2018-07\",\"pageno\": \"1\",\"pagesize\": \"-1\"}";
   String enc_grjbxx = encrypt(grjbxx,"E:/nbPublicKey.dat");//生成的密文
//   System.out.println("个人基本信息：\n"+enc_grjbxx);
   

}
}