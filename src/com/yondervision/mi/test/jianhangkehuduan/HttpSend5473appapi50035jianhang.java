package com.yondervision.mi.test.jianhangkehuduan;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;
//U盾信息修改
public class HttpSend5473appapi50035jianhang {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "frb1w5988e5d0cef74457bd888ffdeff";
	private static final String appId_V = "yondervisionwebservice42";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5473(String	unitaccnum,
			String	unitaccname,
			String	unitcertitype,
			String	unitcertinum,
			String	ukeynum,
			String	chgtype,
			String	ukflag,
			String	unitlinkman,
			String	unitlinkphone,
			String	ukeystate,
			String	qdapprnum) {
		try {
			URL url = new URL(POST_URL + "appapi50035.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "010100000222";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

			 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5473&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+"&unitaccnum="+unitaccnum
						+"&unitaccname="+unitaccname
						+"&unitcertitype="+unitcertitype
						+"&unitcertinum="+unitcertinum
						+"&ukeynum="+ukeynum
						+"&chgtype="+chgtype
						+"&ukflag="+ukflag
						+"&unitlinkman="+unitlinkman
						+"&unitlinkphone="+unitlinkphone
						+"&ukeystate="+ukeystate
						+"&qdapprnum="+qdapprnum;
			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=42";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=42";
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
		httpURLConnectionPOST5473("010100000222","123456","A ","123456789012345678","31491293765","1","0","222","176823591630","1","W236234578456841");
		//String	unitaccnum 010100000222,unitaccname 123456,unitcertitype A,unitcertinum 123456789012345678,ukeynum 31491293765,chgtype 1,ukflag 0,unitlinkman 222,unitlinkphone 176823591630,ukeystate,qdapprnum
	}
}
