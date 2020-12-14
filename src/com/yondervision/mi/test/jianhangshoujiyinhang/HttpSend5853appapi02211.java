package com.yondervision.mi.test.jianhangshoujiyinhang;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;
//根据材料号查询贷款提取信息
public class HttpSend5853appapi02211 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "ugptea6iv8r67r2luuejzk16yk6g2f36";
	private static final String appId_V = "yondervisionwebservice92";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5853(String	accnum,
			String	certinum,
			String	drawreason,
			String	drawreasoncode1,
			String	accloantype,
			String	certitype,
			String	relation	) {
		try {
			URL url = new URL(POST_URL + "appapi02211.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330225197902061571";
			AesTestJiianHangShouJi aes = null;
			try {
				aes = new AesTestJiianHangShouJi();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5853&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+"&accnum="+accnum
						+"&certinum="+certinum
						+"&drawreason="+drawreason
						+"&drawreasoncode1="+drawreasoncode1
						+"&accloantype="+accloantype
						+"&certitype="+certitype
						+"&relation="+relation;
		 
			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=92";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=92";
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

	/*	1. 偿还贷款本息提取
	主贷人： 0129073251 周成泽 320107198210182633 证件类型 1
	材料号： HT057400082016010626
	手机： 15258370006
	卡号： 3344556677889900
	提取原因： 001
	提取贷款类型accloantype： 11 - 系统内公积金贷款
	关系： 本人
	收款银行： 0101-市本级工行*/
	public static void main(String[] args) throws UnknownHostException {
//		httpURLConnectionPOST5853("0129073251","320107198210182633","001","HT057400082016010626","11","1","01");
		httpURLConnectionPOST5853("0937630966","330225197902061571","001","NBCB5001GG06007","13","1","01");

		
		//注意不能重复。
		/*个人账号：accnum                               0937630966
			证件号码：certinum                            330225197902061571
			提取原因：drawreason                         001
			提取材料号：drawreasoncode1            NBCB5001GG06007
			贷款类型：accloantype                        13
			证件类型：certitype                              1
			关系：relation                                      01*/
	}
}
