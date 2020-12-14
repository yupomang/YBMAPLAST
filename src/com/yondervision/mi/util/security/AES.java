package com.yondervision.mi.util.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yondervision.mi.common.ERRCODE.ERROR;
import com.yondervision.mi.common.ERRCODE.WEB_ALERT;
import com.yondervision.mi.common.exp.NoRollRuntimeErrorException;
import com.yondervision.mi.common.exp.TransRuntimeErrorException;
import com.yondervision.mi.common.log.LoggerUtil;
import com.yondervision.mi.dao.Mi040DAO;
import com.yondervision.mi.dto.Mi040;
import com.yondervision.mi.dto.Mi040Example;
import com.yondervision.mi.util.CommonUtil;

public class AES {
	Logger log = LoggerUtil.getLogger();
	
	private final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";

	private final int HASH_ITERATIONS = 10000;
	private final int KEY_LENGTH = 128;

	// 定义密钥
	
	private char[] KEY = { '0', '1', 'p', 'c', 'a', '3', '5', 'D', 'p', 'W', '8', 'f', 'g', 'G', '3', 'd' };
	
	
	
	
	private byte[] salt = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF }; // must save this for next
																							// time we want the key

	private PBEKeySpec myKeyspec = new PBEKeySpec(KEY, salt, HASH_ITERATIONS, KEY_LENGTH);
//	private final String CIPHERMODEPADDING = "AES/CBC/PKCS7Padding";
	private final String CIPHERMODEPADDING = "AES/CBC/PKCS5Padding";

	private SecretKeyFactory keyfactory = null;
	private SecretKey sk = null;
	private SecretKeySpec skforAES = null;
	private byte[] iv = { 0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC, 0xD, 91 };

	private IvParameterSpec IV;

	public AES() {

		try {
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
	
	
	public AES(String centerId, String channel, String appid, String appkey) throws Exception {
		
		Mi040DAO mi040DAO = (Mi040DAO)com.yondervision.mi.util.SpringContextUtil.getBean("mi040DAO");
		Mi040Example example = new Mi040Example();		
		example.createCriteria()
		.andCenteridEqualTo(centerId)
		.andChannelEqualTo(channel)
		.andValidflagEqualTo("1");
		List<Mi040> mi040List = null;
		try{
			mi040List = mi040DAO.selectByExample(example);
		}catch(Exception e){
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"应用数据安全密钥获取失败");
		}		
		if (CommonUtil.isEmpty(mi040List)) {
			log.error(ERROR.NO_DATA.getLogText("MI040：","centerid="+centerId+",channel="+channel+",appid="+appid+",appkey="+appkey));
			throw new NoRollRuntimeErrorException(WEB_ALERT.SYS.getValue(),"应用数据安全密钥获取失败");
		}
		try {
			char[] key = mi040List.get(0).getSecretkey().trim().toCharArray(); 
			System.out.println(key);
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
		System.out.println("#####    待解密数据： "+ciphertext_base64);
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

	// Use this method if you want to add the padding manually
	// AES deals with messages in blocks of 16 bytes.
	// This method looks at the length of the message, and adds bytes at the end
	// so that the entire message is a multiple of 16 bytes.
	// the padding is a series of bytes, each set to the total bytes added (a
	// number in range 1..16).
	private byte[] addPadding(byte[] plain) {
		byte plainpad[] = null;
		int shortage = 16 - (plain.length % 16);
		// if already an exact multiple of 16, need to add another block of 16
		// bytes
		if (shortage == 0)
			shortage = 16;

		// reallocate array bigger to be exact multiple, adding shortage bits.
		plainpad = new byte[plain.length + shortage];
		for (int i = 0; i < plain.length; i++) {
			plainpad[i] = plain[i];
		}
		for (int i = plain.length; i < plain.length + shortage; i++) {
			plainpad[i] = (byte) shortage;
		}
		return plainpad;
	}

	// Use this method if you want to remove the padding manually
	// This method removes the padding bytes
	private byte[] dropPadding(byte[] plainpad) {
		byte plain[] = null;
		int drop = plainpad[plainpad.length - 1]; // last byte gives number of
													// bytes to drop

		// reallocate array smaller, dropping the pad bytes.
		plain = new byte[plainpad.length - drop];
		for (int i = 0; i < plain.length; i++) {
			plain[i] = plainpad[i];
			plainpad[i] = 0; // don't keep a copy of the decrypt
		}
		return plain;
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
		AES aes=null;
		try {
			aes = new AES("00087100","40","1","1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String aaa = "123456";
//		char[] a1 = aaa.toCharArray();
		
		
		System.out.println("str === " + aes.encrypt(aaa.getBytes("UTF-8")));
		
		System.out.println("d str === " + aes.decrypt(aes.encrypt(aaa.getBytes("UTF-8"))));
		
		System.out.println(aes.decrypt("vYtUAj6UU7vHgbLGlaBB4scQw1it4PVt3lm1Qcoubny/ZwEoOg /LmMAOXufGwvB"));
		
	}
}
