package com.yondervision.mi.test.zhengwujiqiren;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
//排队取号
public class HttpSend5516appapi10107 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "1f93b24ff9ee856f153b8c5605ea8eb4";
	private static final String appId_V = "yondervisionwebservice51";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5516(String websitecode,String jobid,String jobname,String idcardNumber 
			,String ordertype,String getMethod) {
		try {
			URL url = new URL(POST_URL + "appapi10107.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "intelligentRobot";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5516&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+"&websitecode="+websitecode
						+"&jobid="+jobid
						+"&jobname="+jobname
						+"&idcardNumber="+idcardNumber
						+"&ordertype="+ordertype
						+"&getMethod="+getMethod;
			System.out.println("Para" + Para);
			String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			// 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=51";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=51";
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
//			String result=aes.decrypt(sb.toString());
			String result=sb.toString();
			System.out.println(result);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnknownHostException {

		/*httpURLConnectionPOST5516("0574000801","7","缴存","","0","00");
		httpURLConnectionPOST5516("0574000801","1","公积金综合业务","330204197508036114","0","00");*/
		//httpURLConnectionPOST5516("0574000801","1","公积金综合业务","330281199106034636","0","00");
		//httpURLConnectionPOST5516("0574000801","7","公积金综合业务","330204197508036114","0","00");
/*		httpURLConnectionPOST5516("0574000801","7","公积金综合业务","330204197508036115","0","00");*/
		/*httpURLConnectionPOST5516("0574000801","7","公积金综合业务","330204197508036116","0","00");
		httpURLConnectionPOST5516("0574000801","7","公积金综合业务","330204197508036117","0","00");
		httpURLConnectionPOST5516("0574000801","7","公积金综合业务","330204197508036118","0","00");
		httpURLConnectionPOST5516("0574000801","7","公积金综合业务","330204197508036119","0","00");
		httpURLConnectionPOST5516("0574000801","7","公积金综合业务","330204197508036120","0","00");*/
		httpURLConnectionPOST5516("0574000801","9","司法协助","330204197508036120","0","00");
/*		网点ID：websitecode
		业务ID : jobid（各网点支持业务不同，业务列表根据网点排队情况查询接口获取）
		业务类型名称 : jobname
		身份证号(综合服务补充) : idcardNumber
		是否预约排号（1 预约，0 非预约）：ordertype（默认填写0）
		取号方式（现场、APP、微信）：getMethod*/
	}
}
