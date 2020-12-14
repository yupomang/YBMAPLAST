package com.yondervision.mi.test.gongshangyinhangshoujiyinhang;

import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.util.security.Base64Decoder;
import com.yondervision.mi.util.security.Base64Encoder;
import org.apache.log4j.Logger;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class AesTestGongShangYinHang {
	Logger log = LoggerUtil.getLogger();
	
	private final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";

	private final int HASH_ITERATIONS = 10000;
	private final int KEY_LENGTH = 128;

	// 定义密钥
	
	private char[] KEY = { '0', '1', 'p', 'c', 'a', '3', '5', 'D', 'p', 'W', '8', 'f', 'g', 'G', '3', 'd' };
	private byte[] salt = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF }; // must save this for next

	private PBEKeySpec myKeyspec = new PBEKeySpec(KEY, salt, HASH_ITERATIONS, KEY_LENGTH);
//	private final String CIPHERMODEPADDING = "AES/CBC/PKCS7Padding";
	private final String CIPHERMODEPADDING = "AES/CBC/PKCS5Padding";

	private SecretKeyFactory keyfactory = null;
	private SecretKey sk = null;
	private SecretKeySpec skforAES = null;
	private byte[] iv = { 0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC, 0xD, 91 };

	private IvParameterSpec IV;
	
	
	public AesTestGongShangYinHang() throws Exception {
		try {
			//注意更改秘钥

			char[] key = "h4hxyujsdns1vaea".toCharArray(); 	     //测试
			//char[] key = "es1cmcl7s144ovbf".toCharArray();         //生产
			
			myKeyspec = new PBEKeySpec(key, salt, HASH_ITERATIONS, KEY_LENGTH);
			keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
			sk = keyfactory.generateSecret(myKeyspec);

		} catch (NoSuchAlgorithmException nsae) {
			log.error("no key factory support for PBEWITHSHAANDTWOFISH-CBC");
			nsae.printStackTrace();
		} catch (InvalidKeySpecException ikse) {
			log.error("invalid key spec for PBEWITHSHAANDTWOFISH-CBC");
			ikse.printStackTrace();
		}
		byte[] skAsByteArray = sk.getEncoded();
		skforAES = new SecretKeySpec(skAsByteArray, "AES");
		IV = new IvParameterSpec(iv);
	}
	

	public String encrypt(byte[] plaintext) throws TransRuntimeErrorException{
		try{
			byte[] ciphertext = encrypt(CIPHERMODEPADDING, skforAES, IV, plaintext);
			String base64_ciphertext = Base64Encoder.encode(ciphertext);
			return base64_ciphertext;
		}catch (Exception e){
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"加密处理异常");
		}		
	}

	public String decrypt(String ciphertext_base64) throws TransRuntimeErrorException{
		try{
			System.out.println("#####    待解密数据： "+ciphertext_base64);
			byte[] s = Base64Decoder.decodeToBytes(ciphertext_base64);
			String decrypted = new String(decrypt(CIPHERMODEPADDING, skforAES, IV, s), "UTF-8");
			return decrypted;
		}catch (Exception e){
			e.printStackTrace();
			throw new TransRuntimeErrorException(WEB_ALERT.LOGIN_ERROR_PARA.getValue(),"解密处理异常");
		}
	}

	private byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] msg) throws Exception{
		try {
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.ENCRYPT_MODE, sk, IV);
			return c.doFinal(msg);
		} catch (NoSuchAlgorithmException nsae) {
			log.error("no cipher getinstance support for " + cmp);
			nsae.printStackTrace();
			throw nsae;
		} catch (NoSuchPaddingException nspe) {
			log.error("no cipher getinstance support for padding " + cmp);
			nspe.printStackTrace();
			throw nspe;
		} catch (InvalidKeyException e) {
			log.error("invalid key exception");
			e.printStackTrace();
			throw e;
		} catch (InvalidAlgorithmParameterException e) {
			log.error("invalid algorithm parameter exception");
			e.printStackTrace();
			throw e;
		} catch (IllegalBlockSizeException e) {
			log.error("illegal block size exception");
			e.printStackTrace();
			throw e;
		} catch (BadPaddingException e) {
			log.error("bad padding exception");
			e.printStackTrace();
			throw e;
		}
	}

	private byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] ciphertext) throws Exception{
		try {
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.DECRYPT_MODE, sk, IV);
			return c.doFinal(ciphertext);
		} catch (NoSuchAlgorithmException nsae) {
			log.error("no cipher getinstance support for " + cmp);
			nsae.printStackTrace();
			throw nsae;
		} catch (NoSuchPaddingException nspe) {
			log.error("no cipher getinstance support for padding " + cmp);
			nspe.printStackTrace();
			throw nspe;
		} catch (InvalidKeyException e) {
			log.error("invalid key exception");
			e.printStackTrace();
			throw e;
		} catch (InvalidAlgorithmParameterException e) {
			log.error("invalid algorithm parameter exception");
			e.printStackTrace();
			throw e;
		} catch (IllegalBlockSizeException e) {
			log.error("illegal block size exception");
			e.printStackTrace();
			throw e;
		} catch (BadPaddingException e) {
			log.error("bad padding exception");
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public static void main(String[] args) throws TransRuntimeErrorException, UnsupportedEncodingException {
		AesTestGongShangYinHang aes=null;
		try {
			aes = new AesTestGongShangYinHang();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String x="userId=010100000682&usertype=20&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5432&appid=yondervisionwebservice91&appkey=w6r8msk88243epqs3bezqiuek1z1il4t&appToken=&clientIp=172.10.0.1&clientMac=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2018-06-07&channelSeq=&bodyCardNumber=330205198303230619";
		System.out.println("str === " + aes.encrypt(x.getBytes()));
		//String w ="pwBDHfH/Leg4Nanw569DBd+viJca+bJGLKgO1/BIeqbhZQ9ULW0Gj5NjkR8cLgp/LNaTZmVUn+vcIuckRfLxCGRfVyjAs+8vBjN5mbAACKcIo/TUqBgq3+NpNveXdGYhVqhQqJ4tc0IzZGtEzd8CetRDb2U7ENoWSNM62L2wx/pWc969VW1uc/94YLAKOu///otZghzfjfMEWabF0e+y/eJHaNkX9Pzi0AaOKPObrDOpSvkBTrVstFLnlAjenvYKVTwIIJ6oOkpmOw30KmY2P7OXq4cyNGuwv1hfZMRXHFZeznAFtzvwoFNGebft1hXJ4wc/h1GF/ZZxGqDknYU9SQ==";
		//String w ="F8/USOZOl3Of/6SvlHp85BIjzsISLalmfPNKvODA0IgPAGCMUif1rh5YjnMHanQNStCbEjO/TryyrN4OI73OmZ5tUbEj+AFC/w6LGBfTV5exUXYu0feXIfK/aGGQviYAoF6k43bQaWmxOoinp1A1li4wigNJzXRI9VjLBq2tBsLcrwA4agXhRPHyaE+Lbd3zNyjGGHLeMDNsy9xQpXz7fBW56yL39wRGaO4TqEpcYimbQaSaW6yePstiOoErA5/JIqQ8HE0FcvYpEFEOTRzUP4tnpnHcfebQVEon+eAim9CFEB5MU3BE7dIdo5TpsKpgvNNLjCS3uwWrsZ+TQKOz5IuQ4W91EGtO81OhT8p/GU4xUdipFW4p6bUvT1VfBWougPOoVY929v6dPEKaR2ETsm31nTpG1kKvPCULh2ZL7oa7kXoqEfXhBwShd2Xb4vRqGu8Aq2I10/Dgpk+UMOrHHRDOtTr0EFRu+cXlvP1q80pmib+MCvOSkZGGtJKuVicBfb+P66erox1J1Gs+MYxMzJNq1RcUsLQ6CHulYSPYOt9wrBODM5EyzPEJVyDxhxtYQpqgOeFqjgr8m62pkUhBEFgqStzxD/Yvv6SjodEkjZLQ6/nlBEOLog5svclBHH2I1NFCXZUZbds65MjfIuIPrit1Q4Oyijm59gEbeQX2sHZdLeN8a+DqUU9PtvLDJRi4QAkLT/tEsozr2PvGi0RxHw==";
		//String w ="lE9RG6AASxqTzEqdeort5G2Q8MxV2bnI6IG9kfnOYS0=";
		
//		String w="MKtIl5O62W4Hhk5k2kBEZGFtz%2BRpO2oC1AwnfMBDvoscgj9wysq0uTmkG8/GoGDmVoQs8ihx9bexNMveShVTkjFdVrMOs1%2B1UCfVTXJwCiFtLaWQe4scQ7N7aP%2B0VyS/FCokaJrAqGA6krK/E4SwvOQ89VSGXfVzmPsrw2mI4XnxmsAZTaVVa97QKB2tAPzC8/56TseSWTAIDCBf1Aq5taSthp7cxYQrQ/IgKF4q4DFWdbtifFnFL6op2fiK1n/ObCIUbyJnnN9Jx0bXUy/MxeyaxW89vYr69mG1f/iY3egRnpI0ZwFw9zgEAB0YnR1Tc0bJ2gtaWcMy3zP0Bc4w6Wtt1nYb8uaRysEdDGRzL7cPxJW%2BhbQVijYPHb3iruz%2BYs5OfoyivlhaSgsqC%2Bi6KA==";
//		System.out.println("str === " + aes.decrypt(w));
	
	}
}
