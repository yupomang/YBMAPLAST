package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
//商贷基本信息查询
public class HttpSend5013appapi00125 {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5013(String 	bankcode,String 	buyhousecerid,
			String buyhousename,String certitype) {

		try {
			URL url = new URL(POST_URL + "appapi00125.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330225197902061571";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5013&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq=&bankcode="+bankcode+"&buyhousecerid="+buyhousecerid
						+"&buyhousename="+buyhousename+"&certitype="+certitype;

			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
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
		for (int i = 1;i<=1;i++) {
			long starTime = System.currentTimeMillis();
			httpURLConnectionPOST5013("0012", "330211196808311025", "林洪波", "1");
			/*httpURLConnectionPOST5013("0001", "3310228110190", "吴菊", "1");
			httpURLConnectionPOST5013("0001", "330222197409030027", "蒋芙蓉", "1");
			httpURLConnectionPOST5013("0006", "330225197307012863", "张春敏", "1");
			httpURLConnectionPOST5013("0006", "332528197008144425", "蔡陈香", "1" );
			httpURLConnectionPOST5013("0003", "330203197911081816", "张大可", "1");
			httpURLConnectionPOST5013("0004", "330226198707280032", "王游", "1");
			httpURLConnectionPOST5013("0003", "330225197307012863", "张春敏", "1");
			httpURLConnectionPOST5013("0006", "330282198509184681", "徐旭威", "1");*/
/*		<bankcode>0006</>
		<buyhousecerid>330225197902061571</>
		<buyhousename>樊勇</>
		<certitype>1</>
		<drawreasoncode1>NBCB5001GG06007</>*/
			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求耗时" + Time + "毫秒");
		}
	}
}
