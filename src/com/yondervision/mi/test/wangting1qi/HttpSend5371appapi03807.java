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
//销户提取
public class HttpSend5371appapi03807 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5371(String	payeebankcode,
			String	payeebankaccnum,
			String	accnum,
			String	accname,
			String	certitype,
			String	certinum,
			String	frzflag,
			String	drawreason,
			String	unitaccnum,
			String	handset,
			String	inputamt,
			String	qdapprnum,String appramt,String apprdate) {
		try {
			URL url = new URL(POST_URL + "appapi03807.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330203197401192428";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5371&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+"&payeebankcode="+payeebankcode
						+"&payeebankaccnum="+payeebankaccnum
						+"&accnum="+accnum
						+"&accname="+accname
						+"&certitype="+certitype
						+"&certinum="+certinum
						+"&frzflag="+frzflag
						+"&drawreason="+drawreason
						+"&unitaccnum="+unitaccnum
						+"&handset="+handset
						+"&inputamt="+inputamt
						+"&qdapprnum="+qdapprnum
				 +"&appramt="+appramt
					+"&apprdate="+apprdate;
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
//		httpURLConnectionPOST5371("0101","3433434434","0034888158","张薇","1","330203197401192428","0","101","080000000001","13008938989","13329.37","WT7777");
		httpURLConnectionPOST5371("0101","213edqw3123w","0072745715","袁锐斌","1","330203198403241814","0","103","010100000119","","16293.37","WT345344435464677","","");
		/*httpURLConnectionPOST5370("0101","1111","0072745715","胡晓昕","1",
				"330281198305040022","0","103","WT93454444324391","yyy000","",
				"","","","asdas","",
				"","","","","","","01","","",
				"800","胡晓昕","330281198305040022","2019-01-01","2019-12-03","700","23",
				"010100002496", "","","","","");//出境定居*/
/*		银行卡的所属银行:payeebankcode		<payeebankcode>0101</>
		卡号:payeebankaccnum		<payeebankaccnum>3433434434</>
		个人账号:accnum		<accnum>0034888158</>
		姓名:accname		<accname>张薇</>
		证件类型:certitype		<certitype>1</>
		证件号码:certinum		<certinum>330203197401192428</>
		冻结状态:frzflag		<frzflag>0</>
		提取原因:drawreason		<drawreason>101</>
		单位账号:unitaccnum		<unitaccnum>080000000001</>
		手机号码:handset		<handset>13008938989</>
		inputamt		<inputamt>13329.37</>
		渠道流水号：qdapprnum		<qdapprnum>WT7788</>*/
	}
}
