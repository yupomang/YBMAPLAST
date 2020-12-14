package com.yondervision.mi.test.wangting1qi;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
//宁波市失业人员信息
public class HttpSend5973appapi00156 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5973(
			String certinum,
			String accname,
			String powerMatters,
			String subPowerMatters,
			String materialName,
			String aac003,
			String aac002) {
		try {
			URL url = new URL(POST_URL + "appapi00156.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5973&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&certinum="+certinum
						+ "&accname="+accname
						+ "&powerMatters="+powerMatters
						+ "&subPowerMatters="+subPowerMatters
						+ "&materialName="+materialName
						+"&AAC003="+aac003
						+"&AAC002="+aac002;

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

    private static String change(String input) {  
    	String output = null;
        try {  
            output = new String(input.getBytes("iso-8859-1"),"utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }
		return output; 
    }  
	public static void main(String[] args) throws UnknownHostException {
			for (int i = 1; i < 10; i++) {
				long starTime=System.currentTimeMillis();
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-010","失业证明","李优美","330903198212275542");

	/*			httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-010","失业证明","沈磊","330203197310010019");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-006","出境定居提取住房公积金","虞书奇","33021119720128001X");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-007","出境定居提取住房公积金","应品高","330204196907071019");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-008","出境定居提取住房公积金","任治苗","330226196011160017");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-009","出境定居提取住房公积金","娄成贤","33022619581226003X");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-010","出境定居提取住房公积金","蔡金浩","330205196402250317");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-011","出境定居提取住房公积金","秦春丽","410224198702170763");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-012","出境定居提取住房公积金","胡开全","330226195908253678");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-013","出境定居提取住房公积金","俞志行","330226195902240016");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-014","出境定居提取住房公积金","邬美娣","330226196906170021");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-015","出境定居提取住房公积金","孔夏凉","330226195807130011");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-016","出境定居提取住房公积金","周剑伟","330203196505130630");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-017","出境定居提取住房公积金","徐婷","330203196911091526");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-018","出境定居提取住房公积金","徐静","330203198211202722");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-019","出境定居提取住房公积金","洪雪萍","330204197702043027");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-020","出境定居提取住房公积金","邱慧平","330203196312082417");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-021","出境定居提取住房公积金","沈江龙","330219197705224450");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-022","出境定居提取住房公积金","王芳军","330283199006097712");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-023","出境定居提取住房公积金","黄雄杰","330203198304280914");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-024","出境定居提取住房公积金","李存平","330224196412231813");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-025","出境定居提取住房公积金","徐方华","330205196310170053");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-026","出境定居提取住房公积金","陈明宏","330203196003110314");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-027","出境定居提取住房公积金","孙宏耀","33020319661224211X");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-028","出境定居提取住房公积金","汪健飞","330205195604260615");
				httpURLConnectionPOST5973("330726198810170330","张鹏程","其他-02491-000","其他-02491-029","出境定居提取住房公积金","成跃雅","330205197010262428");
*/

				long endTime=System.currentTimeMillis();
				long Time=endTime-starTime;
				System.out.println("请求大数据平台耗时"+Time+"毫秒");
			}
	}



}
