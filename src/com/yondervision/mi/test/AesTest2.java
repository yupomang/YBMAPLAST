package com.yondervision.mi.test;

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

public class AesTest2 {
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


	public AesTest2() throws Exception {
		try {
			//注意更改秘钥

			char[] key = "03EkE87ijfe36T2s".toCharArray(); //	网站生产

			
//			System.out.println(key);
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
			//System.out.println("#####    待解密数据： "+ciphertext_base64);
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
		AesTest2 aes=null;
		try {
			aes = new AesTest2();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//String w ="pwBDHfH/Leg4Nanw569DBd+viJca+bJGLKgO1/BIeqbhZQ9ULW0Gj5NjkR8cLgp/LNaTZmVUn+vcIuckRfLxCGRfVyjAs+8vBjN5mbAACKcIo/TUqBgq3+NpNveXdGYhVqhQqJ4tc0IzZGtEzd8CetRDb2U7ENoWSNM62L2wx/pWc969VW1uc/94YLAKOu///otZghzfjfMEWabF0e+y/eJHaNkX9Pzi0AaOKPObrDOpSvkBTrVstFLnlAjenvYKVTwIIJ6oOkpmOw30KmY2P7OXq4cyNGuwv1hfZMRXHFZeznAFtzvwoFNGebft1hXJ4wc/h1GF/ZZxGqDknYU9SQ==";
		//String w ="F8/USOZOl3Of/6SvlHp85BIjzsISLalmfPNKvODA0IgPAGCMUif1rh5YjnMHanQNStCbEjO/TryyrN4OI73OmZ5tUbEj+AFC/w6LGBfTV5exUXYu0feXIfK/aGGQviYAoF6k43bQaWmxOoinp1A1li4wigNJzXRI9VjLBq2tBsLcrwA4agXhRPHyaE+Lbd3zNyjGGHLeMDNsy9xQpXz7fBW56yL39wRGaO4TqEpcYimbQaSaW6yePstiOoErA5/JIqQ8HE0FcvYpEFEOTRzUP4tnpnHcfebQVEon+eAim9CFEB5MU3BE7dIdo5TpsKpgvNNLjCS3uwWrsZ+TQKOz5IuQ4W91EGtO81OhT8p/GU4xUdipFW4p6bUvT1VfBWougPOoVY929v6dPEKaR2ETsm31nTpG1kKvPCULh2ZL7oa7kXoqEfXhBwShd2Xb4vRqGu8Aq2I10/Dgpk+UMOrHHRDOtTr0EFRu+cXlvP1q80pmib+MCvOSkZGGtJKuVicBfb+P66erox1J1Gs+MYxMzJNq1RcUsLQ6CHulYSPYOt9wrBODM5EyzPEJVyDxhxtYQpqgOeFqjgr8m62pkUhBEFgqStzxD/Yvv6SjodEkjZLQ6/nlBEOLog5svclBHH2I1NFCXZUZbds65MjfIuIPrit1Q4Oyijm59gEbeQX2sHZdLeN8a+DqUU9PtvLDJRi4QAkLT/tEsozr2PvGi0RxHw==";
//		String w ="l2WIW/LNqyDKctF3fEN3kxWBCOB+Ig60CQzv5vs0xTNrcGdaR/Okt6mhP4U9FOLoo8rTNQPHfRg7VhKB98Ry+P+53PPq0VptHl/g5kG5gQUuLwK/IXISPCsox8GGEj23LnNQryFIXG7hi639ijLJX6lrmj8ngdJIIHHtbhnNyjUWw3DoR35xvG0beFu2VrylZVn71ThMZhxDOD7IICdwxxpQ934b42MyahGgXAc9ymvR9ONGGkKDeTsUeVMFjnKX8qij+qD15e7CXqHXe5bHfDbJ+ljps0UiHE5+akxsdAxUXzXu8nkOYpC6EPnejTQTe8NFpd4+F2I+SZQV/IhZiCcLbnsvxjFWSTJzuWvjh0ZHZ58XkvbC0hurZFFitq09AHJcPqy4ABjelU1SC9qbJ0RC7RzYODoNXa0MxBd4e4zibiewPOoOaeygd3MSWIRkdaA2wQmcasWEkkRNsLSJ6XqlZ3O7M8L/HmvO2dGr/V9eW7VIPA7kOVNtj0cmK0qLB8TYM1SWkrz0Lh9XxHiATbn8WcqaHK/a/k8yqjGero5MKE1IQwxFUv6TiHXXJhnrWS/xehqwZN6aErToM0bZBC+FzFf/b5z4sBC5lNI6lGkaWyHw8Tu4zJ76Q5CnC2Z3SCb0wqqVvn8e70zTg52iJA1P2EbR1JHEpxBv3udFKFdAo5Xr+lVQ2VZbQeNlZ0nToK+tpsmfbhcF/scx0Hq/8zTcwl9EYb2epBBSAaxYq7I=";
//		String w ="GdYAFXuUCi2l7pbmGcgKYTrxxly0fNhF4CLtcpWKZdmImngKfZYg2nwdYqJlFiEWTTxX0oOl9XbBt9E3Cyk8/uSv3WhmNhXu/Mz9pFG82YbKrg1wwgbDb0XuY9uWv7RHqOpvE5n7bGeSSs5QGWYyPnb032zc2Cm/J4dreQwHM/3RaxWmuqqYdcqnnYuvKPw7RbRf6+yx8cWz/S4NK4EyGVZfOMA4sOU19ZHWFUZDktW8LDCjNtq8hXK9xEtaX2ppSPOwrrO9wxI17ZnMdbBCqkVnemEA8oOICnh4KrZPU0+IxnPpaFVt/WYd1YJyG5EYKTRr0ILQd0butILD9Zp5F+00E9T+jycTziJ0g2Ih36LVhYOpVYon+oDuZvaFSJvTxDX2mxZEUPxDcr6VIhmL4y8dXeAzmmqxda9N1QG4uHWFiapM9o3PNZ8/D0xnoRfSDYpCpVe71mRqOyn4mZi2Aw==";
//		String w ="efmeTB67/8KBJkEyXeYl/ogtqZV4W1/vgZFKHk+5N4Vsb0z2/kY/O8hjUnvTOq3iohXQ9Tb4vVejWVjvPi0Y6w==";
//		String w ="FWDZ+BpSA9Ll7HnccDxvjIdwXR6b9BodED33WuN3d6VQxs4jeNGcyJl4DZ+5zKOl6RJRk8+2Wot1G7ahAy67IbeLx4MjIlAoTv3JZSHT102PA+WddCcwzS8SeQ+3IHoetSelYBclyveizHwoEa+zglFpigW5hHfn4wp4AEulpdxCJlCGy6wIrclvGMW2XbKTc2+pW31QyuMh1b1oij85KBosVHVkmL4ULSQgNTz6ZTu+f6F4Konei+Uvjs/pJ2jht7Kyd7Bm/ZamFBl9PLBOoIa/UdOTevMUqEzpc/I/zcJoVdsM/ahywT7oZS5LLzAy2rm6guQIwjB2n8xhZxyBOokc8clbTJBHPe/ZgW4peRpJ4Ll3bybg7gCu3H1PBpCGh/T7/7c1YSB0LsA5/1B/3FAzAM5WxEgYDhlrSzDpUyfZYoGHvK6fEtFDRQtm5i+j3SSWYlpbI8IzRJZZsVRZE+gw8LhPu2Qf59V6OOzlNyk=";
		String w ="FWDZ+BpSA9Ll7HnccDxvjIdwXR6b9BodED33WuN3d6VQxs4jeNGcyJl4DZ+5zKOl6RJRk8+2Wot1G7ahAy67IbeLx4MjIlAoTv3JZSHT102PA+WddCcwzS8SeQ+3IHoetSelYBclyveizHwoEa+zglFpigW5hHfn4wp4AEulpdxCJlCGy6wIrclvGMW2XbKTc2+pW31QyuMh1b1oij85KBosVHVkmL4ULSQgNTz6ZTu+f6F4Konei+Uvjs/pJ2jht7Kyd7Bm/ZamFBl9PLBOoIa/UdOTevMUqEzpc/I/zcJoVdsM/ahywT7oZS5LLzAy2rm6guQIwjB2n8xhZxyBOokc8clbTJBHPe/ZgW4peRpJ4Ll3bybg7gCu3H1PBpCGh/T7/7c1YSB0LsA5/1B/3FAzAM5WxEgYDhlrSzDpUyfZYoGHvK6fEtFDRQtm5i+j3SSWYlpbI8IzRJZZsVRZE+gw8LhPu2Qf59V6OOzlNyk=";
		String s = "{\"ZJHM\":\"362422199510198710\"}";
		System.out.println("解密 === " + aes.decrypt(w));
		System.out.println("加密 === " + aes.encrypt(s.getBytes()));
	}
	}
