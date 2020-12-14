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
//单位资料变更
public class HttpSend5809appapi03701 {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5809(String	unitaccnum,
			String	unitaccname,
			String	unitaddr,
			String	unitareacode,
			String	zip,
			String	mngdept,
			String	unitkind,
			String	economytype,
			String	supsubrelation,
			String	tradetype,
			String	leglaccname,
			String	unitsoicode,
			String	agentdept,
			String	unitlinkman,
			String	unitlinkphone,
			String	unitlinkphone2,
			String	linkmancertitype,
			String	linkmancertinum,
			String	linkmanemail,
			String	unitcustid,
			String	qdapprnum,
			String	UKseq) {
		try {
			URL url = new URL(POST_URL + "appapi03701.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "011500001167";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5809&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+"&unitaccnum="+unitaccnum
						+"&unitaccname="+unitaccname
						+"&unitaddr="+unitaddr
						+"&unitareacode="+unitareacode
						+"&zip="+zip
						+"&mngdept="+mngdept
						+"&unitkind="+unitkind
						+"&economytype="+economytype
						+"&supsubrelation="+supsubrelation
						+"&tradetype="+tradetype
						+"&leglaccname="+leglaccname
						+"&unitsoicode="+unitsoicode
						+"&agentdept="+agentdept
						+"&unitlinkman="+unitlinkman
						+"&unitlinkphone="+unitlinkphone
						+"&unitlinkphone2="+unitlinkphone2
						+"&linkmancertitype="+linkmancertitype
						+"&linkmancertinum="+linkmancertinum
						+"&linkmanemail="+linkmanemail
						+"&unitcustid="+unitcustid
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
		httpURLConnectionPOST5809("011500001167","宁波市住房公积金管理ss中","宁波市解放南路15552666666号","04","315000","01","6",
				"","1","S","陈志明","7777","","豪小","15161178355","87978803","1","341226199208125799","","AU00012202","WT32452244","ukey001");

		/*	
		unitaccnum	011500001167,
		unitaccname	宁波市住房公积金管理ss中,
		unitaddr	宁波市解放南路1666号,
		unitareacode	04,
		zip	315000,
		mngdept	01,
		unitkind	6,
		economytype	,
		supsubrelation	1,
		tradetype	S,
		leglaccname	陈志明,
		unitsoicode	,
		agentdept	,
		unitlinkman	豪小打,
		unitlinkphone	,
		unitlinkphone2	87978803,
		linkmancertitype	1,
		linkmancertinum	341226199208125799,
		linkmanemail	,
		unitcustid	AU00012202,
		qdapprnum 	WT1981,
		UKseq	ukey001
				*/
	}
}
