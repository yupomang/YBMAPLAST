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
//（直连省厅）企业备案信息查询接口
public class HttpSend5905appapi00111 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";


	public static String httpURLConnectionPOST5905( String uniscid,
			 String entname) {
		try {
			URL url = new URL(POST_URL + "appapi00111.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5905&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
								+ "&uniscid="+uniscid
								+ "&entname="+entname;
			//System.out.println("Para----------"+Para);
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
		for (int i = 0; i < 10; i++) {
			//httpURLConnectionPOST5905("92330206MA291QWJ4U","宁波市北仑区大碶明强餐馆");
			//httpURLConnectionPOST5905("12330227MB0W75079J","");
			long starTime = System.currentTimeMillis();
			httpURLConnectionPOST5905("91330203MA2AHY6543","");
			//httpURLConnectionPOST5905("91330204340553015C","");
//			httpURLConnectionPOST5905("91330200753276140B","");
//			httpURLConnectionPOST5905("91330212756263249E","");
//			httpURLConnectionPOST5905("9133022675327302XD","");
//			httpURLConnectionPOST5905("91330203758881470W","");
//			httpURLConnectionPOST5905("913302057503839000","");
//			httpURLConnectionPOST5905("91330205747389819T","");
//			httpURLConnectionPOST5905("9133020476148488XY","");
//			httpURLConnectionPOST5905("91330212753278330G","");
//			httpURLConnectionPOST5905("91330204753284319J","");
//			httpURLConnectionPOST5905("91330212736987311J","");
//			httpURLConnectionPOST5905("91330204761462461H","");
			long endTime = System.currentTimeMillis();
			long Time = endTime - starTime;
			System.out.println("请求大数据平台耗时"+Time+"毫秒");
		}
	}
}
