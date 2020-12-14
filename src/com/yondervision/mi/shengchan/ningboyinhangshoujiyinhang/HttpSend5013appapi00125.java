package com.yondervision.mi.shengchan.ningboyinhangshoujiyinhang;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;
//商贷基本信息查询
public class HttpSend5013appapi00125 {
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "gw78phsl9ffrhmr5uzi6ul4zc4mdzr0j";
	private static final String appId_V = "yondervisionwebservice96";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5013(String 	bankcode,String 	buyhousecerid,
			String buyhousename,String certitype) {
		try {
			URL url = new URL(POST_URL + "appapi00125.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId =buyhousecerid;
			AesTestNingBoYinHang aes = null;
			try {
				aes = new AesTestNingBoYinHang();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5013&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq=&bankcode="+bankcode+"&buyhousecerid="+buyhousecerid
						+"&buyhousename="+buyhousename+"&certitype="+certitype;

			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=96";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=96";
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
//		httpURLConnectionPOST5013("0006","330226196608105627","叶夏娟","1");
//		httpURLConnectionPOST5013("0006","330225197902061571","樊勇","1");
		httpURLConnectionPOST5013("0001","14010319781022571X","王鹏","1");
		
		httpURLConnectionPOST5013("0004","33032119780304002X","吴蓓蕾","1");
		
//		httpURLConnectionPOST5013("0002","330204198506300000","徐某某","1");
//		httpURLConnectionPOST5013("0002","330211199310250046",	"高宁烨","1");
//		httpURLConnectionPOST5013("0002","330224197809206020","吴娟","1");
//		httpURLConnectionPOST5013("0002","130126199011291819","吕冬冬","1");
//		httpURLConnectionPOST5013("0006","南字第0520044","卢丙组","9");
//		httpURLConnectionPOST5013("0006","330227197812310776","张光仁","1");
//		httpURLConnectionPOST5013("0006","330203197812110642","倪益君","1");
		
		
		

		
		
		
//		户名：海警校字第0011     合同号：NBCB5001GG06019    身份证号：海警校字第0011-1
/*		<bankcode>0006</>
		<buyhousecerid>330225197902061571</>
		<buyhousename>樊勇</>
		<certitype>1</>
		<drawreasoncode1>NBCB5001GG06007</>*/
	}
}
