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
//提前全部还款预约（不上）
public class HttpSend5913appapi03823 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5913(String	accname,
			String	apprnum,
			String	comcialflag,
			String	flag,
			String	handset,
			String	remainterm,
			String	lnundtksubbcode,
			String	loanbal,
			String	loancontrstate,
			String	lnundtkbcode,
			String	qdapprnum	) {
		try {
			URL url = new URL(POST_URL + "appapi03823.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330219197910223297";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5913&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+"&accname="+accname
						+"&apprnum="+apprnum
						+"&comcialflag="+comcialflag
						+"&flag="+flag
						+"&handset="+handset
						+"&remainterm="+remainterm
						+"&lnundtksubbcode="+lnundtksubbcode
						+"&loanbal="+loanbal
						+"&loancontrstate="+loancontrstate
						+"&lnundtkbcode="+lnundtkbcode
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
		httpURLConnectionPOST5913("123","2015008681","0","0","18844190794","180","0401","10235.54","09","0004","WT12123655358725");
		httpURLConnectionPOST5913("于春田","2015008120","0","0","13646682513","80","201","","09","0002","WT12123655358725");

		/*		<accname>123</>
		<apprnum>2015008681</>
		<comcialflag>0</>
		<flag>0</>
		<handset>18844190794</>
		<remainterm>180</>
		<lnundtksubbcode>0401</>
		<loanbal>10235.54</>
		<loancontrstate>09</>
		<lnundtkbcode>0004</>
		<qdapprnum>WT195</>*/
	}
}
