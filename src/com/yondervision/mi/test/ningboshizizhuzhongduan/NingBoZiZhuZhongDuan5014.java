package com.yondervision.mi.test.ningboshizizhuzhongduan;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.util.security.RSASignature;

public class NingBoZiZhuZhongDuan5014 {

	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "78ce07bdcdf4877f56305895659defc6";
	private static final String appId_V = "yondervisionselfservice53";

	public static String httpURLConnectionPOST5014(String	accnum,
			String	bankcode,
			String	buyhousecerid,
			String	certitype,
			String	buyhousename,
			String	drawreasoncode1) {
		try {
			URL url = new URL(POST_URL + "appapi00126.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","centerId,userId,usertype,deviceType,deviceToken,currenVersion,buzType,devtoken,channel,appid,appkey,appToken,clientIp,brcCode");
			AesTestZiZhu aes = null;
			try {
				aes = new AesTestZiZhu();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String userId = aes.encrypt(buyhousecerid.getBytes("UTF-8"));
			String appKey = aes.encrypt(appKey_V.getBytes("UTF-8"));
			String appId = aes.encrypt(appId_V.getBytes("UTF-8"));
			
			String parm ="centerId=00057400&userId="
			+ userId
			+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5014&devtoken=&channel=53&appid="
			+ appId
			+ "&appkey="
			+ appKey
			+ "&appToken=&clientIp=&brcCode=05740008";
			
			String parm1 = "centerId=00057400&userId="
					+ userId.replace("+", "%2B")
					+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5014&devtoken=&channel=53&appid="
					+ appId.replace("+", "%2B")
					+ "&appkey="
					+ appKey.replace("+", "%2B")
					+ "&appToken=&clientIp=&brcCode=05740008"
					+ "&accnum="+accnum
					+"&bankcode="+bankcode
					+"&buyhousecerid="+buyhousecerid
					+"&certitype="+certitype
					+"&buyhousename="+buyhousename
					+"&drawreasoncode1="+drawreasoncode1;

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
			String result=sb.toString();
			System.out.println(result);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {
		
			long starTime = System.currentTimeMillis();
			
/*			{"result":[{"loancontrnum":"02205GG20120064","buyhousename":"蔡陈香","certitype":"1","buyhousecerid":"332528197008144425","bankcode":"0006","houseaddr":"浙江省宁波市海曙区青林商业中心19、26、27号8-13","buyhousedate":"","contrsigndate":"2012-05-09","buyhouseamt":"486397.00","loanterm":"120","loansum":"243000.00","cleardate":"","state":"0"},
			           {"loancontrnum":"02205GG20120063","buyhousename":"蔡陈香","certitype":"1","buyhousecerid":"332528197008144425","bankcode":"0006","houseaddr":"浙江省宁波市海曙区青林商业中心19、26、27号8-14","buyhousedate":"","contrsigndate":"2012-05-09","buyhouseamt":"516596.00","loanterm":"120","loansum":"258000.00","cleardate":"","state":"0"}],
			           "recode":"000000","count":2,"msg":"成功"}
			{"result":[{"loancontrnum":"NBCB5001GG06007","buyhousename":"樊勇","certitype":"1","buyhousecerid":"330225197902061571","bankcode":"0006","houseaddr":"浙江省宁波市江东区宾果花园4号楼1703","buyhousedate":"","contrsigndate":"2006-09-14","buyhouseamt":"685708.00","loanterm":"240","loansum":"340000.00","cleardate":"","state":"0"}],"recode":"000000","count":1,"msg":"成功"}
*/
//			httpURLConnectionPOST5014("0041334690", "0003","330225197307012863", "1", URLEncoder.encode("张春敏","utf-8"), "358472673006");
			httpURLConnectionPOST5014("0000200684", "0006","332528197008144425", "1", URLEncoder.encode("蔡陈香","utf-8"), "02205GG20120064");
			httpURLConnectionPOST5014("0000201357", "0006","330225197902061571", "1", URLEncoder.encode("樊勇","utf-8"), "NBCB5001GG06007");

			
			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("5014商贷提取金额计算耗时" + Time + "毫秒");
	}
}
