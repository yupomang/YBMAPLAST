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
//U盾信息修改
public class HttpSend5473appapi50035 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
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
			String userId = "010100000682";
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
		httpURLConnectionPOST5473("010100000682","浙江今日行汽车服务有限","3 ","9133020179953116XP","2","2","1","戴路凯","13248662302","2","WT3467844546558");
		/*	
			单位账号  : unitaccnum      010100000682
			单位名称  : unitaccname    浙江今日行汽车服务有限公司
			证件类型  : unitcertitype     3
			证件号  : unitcertinum        9133020179953116XP
			U盾序列号  : ukeynum        2
			修改类型  : chgtype           2
			是否主管盾  : ukflag          1
			单位联系人 ：unitlinkman  戴路凯
			单位经办人联系电话：unitlinkphone  13248662302
			修改后状态：ukeystate     2
			渠道唯一标识：qdapprnum 
		*/
	}
}
