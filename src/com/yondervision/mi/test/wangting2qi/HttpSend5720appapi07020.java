package com.yondervision.mi.test.wangting2qi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;
//开发商注册
public class HttpSend5720appapi07020 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5720(String	instcode	,
			String	unitaccname	,
			String	orgcode	,
			String	buslicnum	,
			String	tysocialxydm	,
			String	qualevel	,
			String	projectname	,
			String	saleprojectname	,
			String	projarea	,
			String	linkman	,
			String	linkphone	,
			String	checkcode	,
			String	crtflag	,
			String	qdapprnum) {
		try {
			URL url = new URL(POST_URL + "appapi07020.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330203195908132417";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5720&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+"&instcode="+instcode
						+"&unitaccname="+unitaccname
						+"&orgcode="+orgcode
						+"&buslicnum="+buslicnum
						+"&tysocialxydm="+tysocialxydm
						+"&qualevel="+qualevel
						+"&projectname="+projectname
						+"&saleprojectname="+saleprojectname
						+"&projarea="+projarea
						+"&linkman="+linkman
						+"&linkphone="+linkphone
						+"&checkcode="+checkcode
						+"&crtflag="+crtflag
						+"&qdapprnum="+qdapprnum;
		 
			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
			System.out.println("本地参数" + parm);
			System.out.println("传递参数：" + parm1);
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
			String result=aes.decrypt(sb.toString());
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnknownHostException {
		//发送验证码
//		httpURLConnectionPOST5720("05740008",
//				"abv",
//				"",
//				"12222",
//				"",
//				"1",
//				"EEEEE",
//				"EEEEE",
//				"1",
//				"abc",
//				"13323233232",
//				"",
//				"0",
//				"3414132422325125125");
		httpURLConnectionPOST5720("05740008",
				"abv",
				"",
				"12222",
				"",
				"1",
				"EEEEE",
				"EEEEE",
				"1",
				"abc",
				"13323233232",
				"302973",
				"1",
				"2135235669764745");
                                                  		/*<buslicnum>12222</><instcode>05740008</><linkman>abc</><linkphone>13323233232</><projectname>dddd</><qdapprnum>qazxddcv</><qualevel>1</><saleprojectname>dddd</><unitaccname>abv</><flag>1</><projarea>1</><vericode>345960</>*/
	}
}
