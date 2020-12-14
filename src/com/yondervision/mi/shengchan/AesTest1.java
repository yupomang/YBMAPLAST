package com.yondervision.mi.shengchan;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.util.security.Base64Decoder;
import com.yondervision.mi.util.security.Base64Encoder;

public class AesTest1 {
	
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
	
	
	public AesTest1() throws Exception {
		try {
			//注意更改秘钥
			//char[] key = "042bH21pqad2kG8x".toCharArray(); 
			//民政局渠道
			char[] key = "03EkE87WkX2fuT2s".toCharArray(); 		
			//char[] key = "05Xd318IsU1xoI1j".toCharArray(); 
			//char[] key = "01pcaijsmkefgG3d".toCharArray(); 			
			System.out.println(key);
			myKeyspec = new PBEKeySpec(key, salt, HASH_ITERATIONS, KEY_LENGTH);
			keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
			sk = keyfactory.generateSecret(myKeyspec);

		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		} catch (InvalidKeySpecException ikse) {
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
			nsae.printStackTrace();
			throw nsae;
		} catch (NoSuchPaddingException nspe) {
			nspe.printStackTrace();
			throw nspe;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw e;
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			throw e;
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw e;
		} catch (BadPaddingException e) {
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
			nsae.printStackTrace();
			throw nsae;
		} catch (NoSuchPaddingException nspe) {
			nspe.printStackTrace();
			throw nspe;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw e;
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			throw e;
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw e;
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public static void main(String[] args) throws TransRuntimeErrorException, UnsupportedEncodingException {
		AesTest1 aes=null;
		try {
			aes = new AesTest1();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		String aaa = "/m79s%2BgY0tSUL25cWH0n31KCznWszCcBJl2%2BnstZG54=";
//		String aaa = "350B66124C72AC16";
		
//		String bbb = "111111";
//		char[] a1 = aaa.toCharArray();
		
		
//		System.out.println("str === " + aes.encrypt(aaa.getBytes("UTF-8")));
//		System.out.println("d str === " + aes.decrypt("zZid9DgTNlRDE/Cl7gWodxlOGFVQST0ymGUnYIGkwgY="));
		
		System.out.println("d str === " + aes.decrypt("350B66124C72AC16"));
		
		
		
	}
}
