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
//户籍信息判断（）
public class HttpSend5903appapi00108 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
	public static final String POST_URL = "http://61.153.144.77:7001/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";
	
	public static String httpURLConnectionPOST5903(String bodyCardNumber) {
		try {
			URL url = new URL(POST_URL + "appapi00108.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5903&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
								+ "&bodyCardNumber="+bodyCardNumber;
		 
			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
//			System.out.println("本地参数" + parm);
//			System.out.println("传递参数：" + parm1);
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
			String result=change(aes.decrypt(sb.toString()));
			//String result=change(sb.toString());
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
		for (int i = 0; i < 1; i++) {
			httpURLConnectionPOST5903("330682199408031287");
			/*httpURLConnectionPOST5903("330903199308240613");
			httpURLConnectionPOST5903("330724199101230558");
			httpURLConnectionPOST5903("411082199207223037");
			httpURLConnectionPOST5903("430923198508243223");
			httpURLConnectionPOST5903("362422199510198710");
			httpURLConnectionPOST5903("330203196909272416");
			httpURLConnectionPOST5903("330203195807070018");
			httpURLConnectionPOST5903("330203197811011861");*/
			/*httpURLConnectionPOST5903("330204196904252017");
			httpURLConnectionPOST5903("330205198102150620");
			httpURLConnectionPOST5903("330227197312261509");
			httpURLConnectionPOST5903("330206199410254879");
			httpURLConnectionPOST5903("412821197107086867");*/
			/*httpURLConnectionPOST5903("330203197702220912");
			httpURLConnectionPOST5903("330203198305111813");
			httpURLConnectionPOST5903("330203198112241814");
			httpURLConnectionPOST5903("330203197501250023");
			httpURLConnectionPOST5903("33020419840921301X");
			httpURLConnectionPOST5903("330204198410106027");
			httpURLConnectionPOST5903("330283199212110041");
			httpURLConnectionPOST5903("330204198904151039");
			httpURLConnectionPOST5903("612401199004071688");
			httpURLConnectionPOST5903("330203196304122116");
			httpURLConnectionPOST5903("330203198003052715");
			httpURLConnectionPOST5903("330283198411052724");
			httpURLConnectionPOST5903("330205195808213319");
			httpURLConnectionPOST5903("330224197811032314");
			httpURLConnectionPOST5903("330124197111022021");
			httpURLConnectionPOST5903("330224196609273312");
			httpURLConnectionPOST5903("33020319711005181X");
			httpURLConnectionPOST5903("330682198106047211");
			httpURLConnectionPOST5903("330203197801162412");
			httpURLConnectionPOST5903("330204197002021046");
			httpURLConnectionPOST5903("330224196009032718");
			httpURLConnectionPOST5903("330224196908247720");
			httpURLConnectionPOST5903("330227197103155881");
			httpURLConnectionPOST5903("330204197901111029");
			httpURLConnectionPOST5903("330227198309136509");
			httpURLConnectionPOST5903("210404197408222415");
			httpURLConnectionPOST5903("330203198307182420");
			httpURLConnectionPOST5903("330205198309270320");
			httpURLConnectionPOST5903("330224197605024719");
			httpURLConnectionPOST5903("330203198403220626");
			httpURLConnectionPOST5903("330203198612120613");
			httpURLConnectionPOST5903("330227197302127920");
			httpURLConnectionPOST5903("330204197710210040");
			httpURLConnectionPOST5903("230206197201280023");
			httpURLConnectionPOST5903("321023197508162441");
			httpURLConnectionPOST5903("330227198909024260");
			httpURLConnectionPOST5903("330224196306194318");
			httpURLConnectionPOST5903("330205198404190310");
			httpURLConnectionPOST5903("330203199212173630");
			httpURLConnectionPOST5903("430923198508243223");
			httpURLConnectionPOST5903("330283199310151429");
			httpURLConnectionPOST5903("330205198011140312");
			httpURLConnectionPOST5903("330204198111151012");
			httpURLConnectionPOST5903("332625196810240328");
			httpURLConnectionPOST5903("330205197806110323");
			httpURLConnectionPOST5903("330203198701231868");
			httpURLConnectionPOST5903("330226198810271927");
			httpURLConnectionPOST5903("330206198808271412");
			httpURLConnectionPOST5903("330203196812170923");
			httpURLConnectionPOST5903("33020419700928202X");
			httpURLConnectionPOST5903("330203197009231832");
			httpURLConnectionPOST5903("330205197109100613");
			httpURLConnectionPOST5903("330205196209240918");
			httpURLConnectionPOST5903("330205195804240037");
			httpURLConnectionPOST5903("33020419700725202X");
			httpURLConnectionPOST5903("330203196111141812");
			httpURLConnectionPOST5903("330204197905176014");
			httpURLConnectionPOST5903("330227198801216500");
			httpURLConnectionPOST5903("330205197309243344");
			httpURLConnectionPOST5903("330227198611064710");
			httpURLConnectionPOST5903("330227197107262721");
			httpURLConnectionPOST5903("330203196902251823");
			httpURLConnectionPOST5903("330205198111232724");
			httpURLConnectionPOST5903("330203195810271814");
			httpURLConnectionPOST5903("33020319680629302X");
			httpURLConnectionPOST5903("330205196309060658");
			httpURLConnectionPOST5903("330203196904130013");
			httpURLConnectionPOST5903("330227199311097523");
			httpURLConnectionPOST5903("330227197711136828");
			httpURLConnectionPOST5903("330203197812050627");
			httpURLConnectionPOST5903("330204197703226036");
			httpURLConnectionPOST5903("330203197603160029");
			httpURLConnectionPOST5903("330222197510180038");
			httpURLConnectionPOST5903("612401199202057713");
			httpURLConnectionPOST5903("330203197409223022");
			httpURLConnectionPOST5903("33020419830415202X");
			httpURLConnectionPOST5903("330203196003282714");
			httpURLConnectionPOST5903("330203197902152723");
			httpURLConnectionPOST5903("330206197002174942");
			httpURLConnectionPOST5903("330202195711010030");
			httpURLConnectionPOST5903("330204199311236031");
			httpURLConnectionPOST5903("330227196507097329");
			httpURLConnectionPOST5903("330227197101085250");
			httpURLConnectionPOST5903("330825197511283719");
			httpURLConnectionPOST5903("330203198611280914");
			httpURLConnectionPOST5903("340822197309096023");
			httpURLConnectionPOST5903("330205198710191821");
			httpURLConnectionPOST5903("330205199012103323");*/

//			方君慧   330382198210247153
//			邹青松   330203196111141812 
//			戴明光   330224197608052336 
		}
	}
}
