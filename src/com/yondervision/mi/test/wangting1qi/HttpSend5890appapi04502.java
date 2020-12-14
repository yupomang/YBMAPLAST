package com.yondervision.mi.test.wangting1qi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

//补缴登记撤销
public class HttpSend5890appapi04502 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V = "172.10.0.1";

	public static String httpURLConnectionPOST5890(String unitaccnum,
			String regnum, String qdapprnum, String UKseq) {
		try {
			URL url = new URL(POST_URL + "appapi04502.json");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara",
					"fullPara,centerId,channel");
			String userId = "010100003106";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String Para = "userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5890&appid="
					+ appId_V
					+ "&appkey="
					+ appKey_V
					+ "&appToken=&clientIp="
					+ clientIp_V
					+ "&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
					+ "&unitaccnum=" + unitaccnum + "&regnum=" + regnum
					+ "&qdapprnum=" + qdapprnum + "&UKseq=" + UKseq;

			String fullPara = aes.encrypt(Para.getBytes("UTF-8"));
			// 用于数字签名
			String parm = "fullPara=" + fullPara
					+ "&centerId=00057400&channel=40";
			// 用于发送http报文
			String parm1 = "fullPara=" + fullPara.replace("+", "%2B")
					+ "&AESFlag=1&centerId=00057400&channel=40";
			System.out.println("本地参数" + parm);
			System.out.println("传递参数：" + parm1);
			connection.addRequestProperty("headparaMD5", RSASignature.sign(
					EncryptionByMD5.getMD5(parm.getBytes()),
					RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(
					connection.getOutputStream());
			dataout.writeBytes(parm1);
			dataout.flush();
			dataout.close();
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = bf.readLine()) != null) {
				sb.append(line).append(System.getProperty("line.separator"));
			}
			bf.close();
			connection.disconnect();
			String result = aes.decrypt(sb.toString());
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnknownHostException {
		httpURLConnectionPOST5890("010100003106", "2017011056410","WT1226322344118", "ukey001");
		/*
		 * 单位公积金账号：unitaccnum "011500001167" 登记号：regnum 20170902107142
		 * 渠道唯一标识：qdapprnum UK序列号：UKseq "ukey001"
		 */
	}
}
