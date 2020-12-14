package com.yondervision.mi.shengchan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.yondervision.mi.util.security.Base64Encoder;

public class AesEnc {
    public static byte[] aesEncrypt(byte[] source, byte rawKeyData[])
            throws GeneralSecurityException {
        // 处理密钥
        SecretKeySpec key = new SecretKeySpec(rawKeyData, "AES");
        // 加密
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(source);
    }
    public static void main(String[] args) throws Exception {
        // AES算法要求密鈅128位(16字节)
        byte rawKeyData[] = "72WaP81AwB9hdS0p".getBytes("UTF-8");
        // 读取文件内容(为了简单忽略错误处理）
        byte[] source = "123456".getBytes("UTF-8");
        // 加密
        byte[] enc = aesEncrypt(source, rawKeyData);
        System.out.println("aesEncrypt:" + source.length + "->" + enc.length);
        // 输出到文件
        String result = Base64Encoder.encode(enc);
        System.out.println(result);
    }
}
