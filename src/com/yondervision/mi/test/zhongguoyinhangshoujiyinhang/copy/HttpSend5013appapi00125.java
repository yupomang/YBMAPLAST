package com.yondervision.mi.test.zhongguoyinhangshoujiyinhang.copy;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
//商贷基本信息查询
public class HttpSend5013appapi00125 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "6afbfb1d0f1d443f75135e7ad2c095df";
	private static final String appId_V = "yondervisionwebservice93";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5013(String bankcode,String buyhousecerid,
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
			String userId =buyhousecerid;
			AesTestZhongGuoYinHang aes = null;
			try {
				aes = new AesTestZhongGuoYinHang();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5013&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq=&bankcode="+bankcode+"&buyhousecerid="+buyhousecerid
						+"&buyhousename="+buyhousename+"&certitype="+certitype;

			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=93";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=93";
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
//		httpURLConnectionPOST5013("0006","330226196608105627","叶夏娟","1");
//		httpURLConnectionPOST5013("0006","330225197902061571","樊勇","1");
		
		long starTime=System.currentTimeMillis();
		httpURLConnectionPOST5013("0003","330225197307012863","张春敏","1");
		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("5013商贷基本信息查询耗时"+Time+"毫秒");

		
//		httpURLConnectionPOST5013("0002","330204198506300000","徐某某","1");
//		httpURLConnectionPOST5013("0002","330211199310250046",	"高宁烨","1");
//		httpURLConnectionPOST5013("0002","330224197809206020","吴娟","1");
//		httpURLConnectionPOST5013("0002","130126199011291819","吕冬冬","1");
//		httpURLConnectionPOST5013("0006","南字第0520044","卢丙组","9");
//		httpURLConnectionPOST5013("0006","330227197812310776","张光仁","1");
//		httpURLConnectionPOST5013("0006","330203197812110642","倪益君","1");
		
		
		

		
		
		
//		户名：海警校字第0011     合同号：NBCB5001GG06019    身份证号：海警校字第0011-1
/*		<bankcode>0006</>
		<buyhousecerid>330225197902061571</>
		<buyhousename>樊勇</>
		<certitype>1</>
		<drawreasoncode1>NBCB5001GG06007</>*/
	}
}
