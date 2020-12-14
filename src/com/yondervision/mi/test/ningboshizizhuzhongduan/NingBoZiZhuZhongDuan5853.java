package com.yondervision.mi.test.ningboshizizhuzhongduan;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class NingBoZiZhuZhongDuan5853 {

	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "78ce07bdcdf4877f56305895659defc6";
	private static final String appId_V = "yondervisionselfservice53";
	//private static final String clientIp_V="172.10.0.1";
	public static String httpURLConnectionPOST5853(String	accnum,
												   String	certinum,
												   String	drawreason,
												   String	drawreasoncode1,
												   String	accloantype,
												   String	certitype,
												   String	relation) {
		try {
			URL url = new URL(POST_URL + "appapi02211.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,brcCode");

			String buyhousecerid = "330726198810170330";
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}

			//String userId = aes.encrypt(buyhousecerid.getBytes("UTF-8"));
			/*String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5900&appid="
					+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
					+ "&bodyCardNumber="+bodyCardNumber
					+ "&accname="+accname
					+ "&powerMatters="+powerMatters
					+ "&subPowerMatters="+subPowerMatters
					+ "&materialName="+materialName;*/
			String userId = aes.encrypt(buyhousecerid.getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			
			String parm ="centerId=00057400&userId="
			+ userId
			+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5853&devtoken=&channel=53&appid="
			+ appId
			+ "&appkey="
			+ appKey
			+ "&appToken=&clientIp=&brcCode=05740008";

			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5853&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=&brcCode=05740008"
					+"&accnum="+accnum
					+"&certinum="+certinum
					+"&drawreason="+drawreason
					+"&drawreasoncode1="+drawreasoncode1
					+"&accloantype="+accloantype
					+"&certitype="+certitype
					+"&relation="+relation;

			/*String parm1 = "userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5900&appid="
					+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
					+ "&bodyCardNumber="+bodyCardNumber
					+ "&accname="+accname
					+ "&powerMatters="+powerMatters
					+ "&subPowerMatters="+subPowerMatters
					+ "&materialName="+materialName;*/

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
			String result=sb.toString();
			System.out.println(result);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {
		
		long starTime = System.currentTimeMillis();
		httpURLConnectionPOST5853("0129073251","320107198210182633","001","HT057400082016010626","11","1","01");

		httpURLConnectionPOST5853("0000144307","330203196111141812","001","0000000493","11","1","01");
		httpURLConnectionPOST5853("0059289366","330283198408064716","001","2013308640002100008","11","1","01");


		//注意不能重复。
		/*个人账号：accnum                               0129073251
			证件号码：certinum                            320107198210182633
			提取原因：drawreason                         001
			提取材料号：drawreasoncode1            HT057400082016010626
			贷款类型：accloantype                        11
			证件类型：certitype                              1
			关系：relation                                      01*/

		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("请求大数据平台耗时"+Time+"毫秒");
	}
}
