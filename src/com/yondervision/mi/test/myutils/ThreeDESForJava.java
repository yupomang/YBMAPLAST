package com.yondervision.mi.test.myutils;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * ThreeDESForJava {3DES加密解密的工具类 }
 * @author Carter King
 * @date 2018-06-06
 */
public class ThreeDESForJava {
	// 定义加密算法，有DES、DESede(即3DES)、Blowfish
	private static final String Algorithm = "DESede";
//	private static final String PASSWORD_CRYPT_KEY = "2012PinganVitality075522628888ForShenZhenBelter075561869839";
	//测试秘钥
	private static final String PASSWORD_CRYPT_KEY = "http://www.nbcredit.gov.cn/1011";

	//生产秘钥

	/**
	 * 加密方法
	 * @param src
	 * 源数据的字节数组
	 * @return
	 */
	public static byte[] encryptMode(byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(
					build3DesKey(PASSWORD_CRYPT_KEY), Algorithm); // 生成密钥
			Cipher c1 = Cipher.getInstance(Algorithm); // 实例化负责加密/解密的Cipher工具类
			c1.init(Cipher.ENCRYPT_MODE, deskey); // 初始化为加密模式
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密函数
	 * @param src
	 * 密文的字节数组
	 * @return
	 */
	public static byte[] decryptMode(byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(
					build3DesKey(PASSWORD_CRYPT_KEY), Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey); // 初始化为解密模式
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	/*
	 * 根据字符串生成密钥字节数组
	 * @param keyStr 密钥字符串
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] build3DesKey(String keyStr)
			throws UnsupportedEncodingException {
		byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
		byte[] temp = keyStr.getBytes("UTF-8"); // 将字符串转成字节数组

		/*
		 * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}
	
	 /** 
     * 转换成十六进制字符串 
     * @param Carter King
     * @return 
     * @date 2018-06-06
     */  
    public static byte[] hex(String key){    
        String f = DigestUtils.md5Hex(key);    
        byte[] bkeys = new String(f).getBytes();    
        byte[] enk = new byte[24];    
        for (int i=0;i<24;i++){    
            enk[i] = bkeys[i];    
        }    
        return enk;    
    }  

	public static void main(String[] args) {
		String msg = "3DES加密解密案例";
		System.out.println("【加密前】：" + msg);

		// 加密
		byte[] secretArr = encryptMode(msg.getBytes());
		for (byte k : secretArr) {
			System.out.printf("%h,", k);
		}
		System.out.println("【加密后】：" + new String(secretArr));

		// 解密
		byte[] myMsgArr = decryptMode(secretArr);
		System.out.println("【解密后】：" + new String(myMsgArr));

	}

}
