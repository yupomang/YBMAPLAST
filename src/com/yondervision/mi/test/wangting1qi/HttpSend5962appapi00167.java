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
//注销企业信息
public class HttpSend5962appapi00167 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5962(
			String certinum,
			String accname,
			String powerMatters,
			String subPowerMatters,
			String materialName,
			String tyshxydm) {
		try {
			URL url = new URL(POST_URL + "appapi00167.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330726198810170330";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5962&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&certinum="+certinum
						+ "&accname="+accname
						+ "&powerMatters="+powerMatters
						+ "&subPowerMatters="+subPowerMatters
						+ "&materialName="+materialName
						+"&tyshxydm="+tyshxydm;

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
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] args) throws UnknownHostException {
			for (int i = 1; i < 10; i++) {
				long starTime=System.currentTimeMillis();		
//				httpURLConnectionPOST5962("330205197003213310","冯海波","其他-02491-000","其他-02491-004","证明","330205197003213310"); 
//				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-003","表格（住房公积金缴存单位注销登记表）","91330203316874804L"); 
//				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-003","其他","92330206MA291QWJ4U"); 
//				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-003","证明","92330206MA291QWJ4U"); 
//				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-003","证明","92330206MA291QWJ4U"); 
				
				//httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302007645281201");
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330212772306827D"); 
				/*httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330206768516856P");
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302001440599257"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330201778231766G"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330204144060483B"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330212790076703F"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330283742185024W"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330206662081145G"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330200662096144J"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330205753278912J"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203671220389C"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330204144083175L"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302037930343734"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203677666138J"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330204677663359U"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","9133021275325956XG"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330205681052112K"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330204674701794A"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203677661417A"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302127960346270"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203753289072P"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302046655580091"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302047843218805"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302006620673275"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","9133021275629562XN"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203772305111R"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302036982012929"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203671225999K"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302126982159181"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","9133020455450143XU"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","9133020566208221X6"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330201561255724E"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302006950998788"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302056776835017"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203736950586R"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","9133020078432642XC"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330212567013646H"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330205MA2AE0BC4K"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330212573679807E"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302046810860489"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302015839717482"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302045839763037"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330205704869740Y"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330201583963107F"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302036913791273"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203704871701Y"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330201587485958Y"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330201580544644H"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203053826930C"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330201053838931Q"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330201551133627F"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302016880289088"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302045994768647"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","9133021106291537XA"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203058281618U"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330201074917997P"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330205684290888D"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203066621547P"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","9133020307494061X4"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203671232072T"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330201066638672E"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330205764505754M"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203580515966P"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302110961931334"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330204563874131K"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330212563888138G"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330204758859951B"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330204599457794C"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302045511406671"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302003168030883"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330201309033978N"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330211756262836G"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203098767313C"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203309076679A"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203316852832K"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330212720430420Q"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302053406216473"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203322207071E"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203340514534H"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302111443722177"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330201308933587Y"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330211954239709N"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302016102711664"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330200713346909K"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330204747391580L"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","9133020556126249XX"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","9133020156127145XG"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302016102778830"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330201308912778M"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330204790094397W"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302013406175842"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330205753286883G"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302013405312037"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330200736952979U"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302013405263329"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","91330203316874804L"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302013405263329"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302053405570598"); 
				httpURLConnectionPOST5962("330726198810170330","张鹏程","其他-02493-000","其他-02493-193","其他","913302013090553922"); 


	*/			long endTime=System.currentTimeMillis();
				long Time=endTime-starTime;
				System.out.println("请求大数据平台耗时"+Time+"毫秒");
			}
	}
}
