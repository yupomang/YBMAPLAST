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
//网厅年度验审
public class HttpSend5849appapi03805 {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5849(String	unitaccnum,
			String	unitaccname,
			String	accname,
			String	handset,
			String	mannum,
			String	qdapprnum,
			String	UKseq) {
		try {
			URL url = new URL(POST_URL + "appapi03805.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "010100003106";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5849&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+"&unitaccnum="+unitaccnum
						+"&unitaccname="+unitaccname
						+"&accname="+accname
						+"&handset="+handset
						+"&mannum="+mannum
						+"&qdapprnum="+qdapprnum 
						+"&UKseq="+UKseq;

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
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnknownHostException {
		httpURLConnectionPOST5849("010100003106","宁波市住房公积金管理ss中","陈阿甫 ","12222222222","3","WT14224418","ukey001");
		/*userId=010100003106&usertype=4&deviceType=3&deviceToken=&currenVersion=1.0&appid=yondervisionwebservice40&appkey=b5b1c6938e5d0cef72457bd788ffdef0&appToken=&clientIp=172.16.0.186 

				&clientMAC=5C-F3-FC-B7-B8-20&brcCode=05740008&channelSeq=&tranDate=2017-12-01&unitaccname=null&handset=13777175047&accname=gg&mannum=0&unitaccnum=010100003106&qdapprnum=wt17120110003243240523&buzType=5849*/
		/*	
			单位账号：unitaccnum         010100003106
			单位名称：unitaccname      宁波市住房公积金管理ss中
			年审经办人姓名：accname   陈阿甫
			手机号码：handset               12222222222
			单位外来务工人数：mannum 3
			渠道唯一标识：qdapprnum 
			UK序列号：UKseq					ukey001
		*/
	}
}
