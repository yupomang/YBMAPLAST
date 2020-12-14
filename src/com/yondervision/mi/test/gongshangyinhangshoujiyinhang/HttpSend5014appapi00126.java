package com.yondervision.mi.test.gongshangyinhangshoujiyinhang;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;
//商贷提取金额计算
public class HttpSend5014appapi00126 {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "w6r8msk88243epqs3bezqiuek1z1il4t";
	private static final String appId_V = "yondervisionwebservice91";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5014(String	accnum,
			String	bankcode,
			String	buyhousecerid,
			String	certitype,
			String	buyhousename,
			String	drawreasoncode1) {
		try {
			URL url = new URL(POST_URL + "appapi00126.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "332528197008144425";
			AesTestGongShangYinHang aes = null;
			try {
				aes = new AesTestGongShangYinHang();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5014&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq=&accnum="+accnum
						+"&bankcode="+bankcode
						+"&buyhousecerid="+buyhousecerid
						+"&certitype="+certitype
						+"&buyhousename="+buyhousename
						+"&drawreasoncode1="+drawreasoncode1;

			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=91";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=91";
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
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnknownHostException {
//		httpURLConnectionPOST5014("0937631040","0006","332528197008144425","1","蔡陈香","02205GG20120064");
		httpURLConnectionPOST5014("0937630966","0006","330225197902061571","1","樊勇","NBCB5001GG06007");

/*		<accnum>0118783856</><bankcode>0006</><buyhousecerid>330225197902061571</><buyhousename>樊勇</><certitype>1</><drawreasoncode1>NBCB5001GG06007</><*/
	}
}
