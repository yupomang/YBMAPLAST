package com.yondervision.mi.test.ningboshizizhuzhongduan;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

public class NingBoZiZhuZhongDuanShiYe {

	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "78ce07bdcdf4877f56305895659defc6";
	private static final String appId_V = "yondervisionselfservice53";

	public static String httpURLConnectionPOST5973(
			String certinum,
			String accname,
			String powerMatters,
			String subPowerMatters,
			String materialName,
			String aac003,
			String aac002) {
		try {
			URL url = new URL(POST_URL + "appapi00156.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,brcCode");
			AesTestZiZhu aes = null;

			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}


			String userId = aes.encrypt("330682199408031287".getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			String parm = "";
			
			
			// 用于数字签名
			parm = "centerId=00057400&userId="
					+ userId
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5973&devtoken=&channel=53&appid="
					+ appId
					+ "&appkey="
					+ appKey
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008";

			// 用于发送http报文
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5973&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=172.10.0.1&brcCode=05740008&certinum="
					+ certinum.replace("+", "%2B")
					+ "&certinum="+certinum
					+ "&accname="+accname
					+ "&powerMatters="+powerMatters
					+ "&subPowerMatters="+subPowerMatters
					+ "&materialName="+materialName
					+"&AAC003="+aac003
					+"&AAC002="+aac002;
			connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			connection.connect();
			DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
			dataout.writeBytes(parm1);
			dataout.flush();
			dataout.close();
			BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = bf.readLine()) != null) {
				sb.append(line).append(System.getProperty("line.separator"));
			} 
			bf.close();
			connection.disconnect(); 
//			String result=aes.decrypt(sb.toString());
//			System.out.println(result);
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * @param args
	 * @throws UnknownHostException
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {

		httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-005","出境定居提取住房公积金",URLEncoder.encode("沈磊","utf-8"),"330203197310010019");

	}

}
