package com.yondervision.mi.test.zhujianwei;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//住建委--中台--受理服务
public class HttpSendapiaccept {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "ba7f3a3dd146c4613102d0a16be5107b";
	private static final String appId_V = "yondervisionwebsite30";
	private static final String clientIp_V="172.10.0.1";
	public static String httpURLConnectionPOST6020(
		/*	银行卡的所属银行:payeebankcode
卡号:payeebankaccnum
姓名:accname
证件类型:certitype
证件号码:certinum
冻结状态:frzflag
提取原因:drawreason
渠道流水号：qdapprnum
单位账号:unitaccnum
手机号码:handset
可提取金额:inputamt
		 */
		    String payeebankcode,
			String payeebankaccnum,
			String accname,
			String certitype,
			String certinum,
			String frzflag,
			String drawreason,
			String qdapprnum,
			String unitaccnum,
			String handset,
			String inputamt

	) {
		try {
			URL url = new URL(POST_URL + "accept.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId ="330726198810170330";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}


		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=6020&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&payeebankcode="+payeebankcode + "&payeebankaccnum="+payeebankaccnum + "&accname="+accname
				 		+ "&certitype="+certitype + "&certinum="+certinum + "&frzflag="+frzflag
				 + "&drawreason="+drawreason + "&qdapprnum="+qdapprnum + "&unitaccnum="+unitaccnum
				 + "&handset="+handset + "&inputamt="+inputamt
				 ;


			System.out.println("para:"+Para);
			String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=30";
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=30";
			System.out.println("fullPara:"+Para);
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

	public static void main(String[] args){
		for(int i=0;i<1;i++) {
			long starTime = System.currentTimeMillis();
			/*	银行卡的所属银行:payeebankcode
				卡号:payeebankaccnum
				姓名:accname
				证件类型:certitype
				证件号码:certinum
				冻结状态:frzflag
				提取原因:drawreason
				渠道流水号：qdapprnum
				单位账号:unitaccnum
				手机号码:handset
				可提取金额:inputamt
			 */
			httpURLConnectionPOST6020("", "", "", "", "", "","","","","","");

			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时" + Time + "毫秒");
		}
	}
}
