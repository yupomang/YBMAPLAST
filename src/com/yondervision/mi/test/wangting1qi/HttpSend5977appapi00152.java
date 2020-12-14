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
//劳动能力鉴定结论
public class HttpSend5977appapi00152 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5977(
			String certinum,
			String accname,
			String powerMatters,
			String subPowerMatters,
			String materialName,
			String name,
			String cardId) {
		try {
			URL url = new URL(POST_URL + "appapi00152.json");
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

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5977&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&certinum="+certinum
						+ "&accname="+accname
						+ "&powerMatters="+powerMatters
						+ "&subPowerMatters="+subPowerMatters
						+ "&materialName="+materialName
						+"&name="+name
						+"&cardId="+cardId;

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
//				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","喻敬锋","510902198008214651"); 
//				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","王琼","330681198802063307"); 
				/*httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","程岩","6217561400022638578");
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","周宁容","32020121010833125"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","谢春香","6228480318284481872"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","孙建通","6217233901002119560"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","王琼","6217251400028177576"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","毛飞龙","6217251400021919685"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","李杰青","6217251400010075911"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","屈统军","6222620180000942353"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","胡晶婷","6217251400018341331"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","李朝阳","6217251400026264046"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","张祝央","6228480318240224473"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","包春雷","6217233901000939662"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","王来恩","6216611400009380006"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","张王启","6217251400024333660"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","狄鲁敏","6214180000003732131"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","洪水香","6216611400003987293"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","胡余荣","6228480310317013715"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","李冬","6217561400003197339"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","徐真球","6222083901001130387"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","吴建民","6222023901001960372"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","何孟挺","6217233901000105223"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","秦国松","6210811597030039880"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","任明波","6215593901004843728"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","毛琪","30010121031004655"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","张洁","6217561400001604898"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","单和根","6212263901018143806"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","唐亚琴","6228480316143049468"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","姚燕","6214671590014077407"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","戴吉娣","6236681590000173187"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","蔡伦军","6212263901017430550"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","陈建永","6210811596350057233"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","汪水国","6210811592500234970"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","袁忠荣","6210811592200137564"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","吴继光","6222003901101092500"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","王开明","6210811592500234806"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","卓水君","6210811592500229186"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","仇文华","6228480318178853277"); 
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","鲍叶叶","6214180000003936849");
*/
				//httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","李则均","6210811592500229210");
				//httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","胡吉永","6222023901021540931");

				//httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","张国恩","6212263901008484657");
				httpURLConnectionPOST5977("330726198810170330","张鹏程","其他-02491-000","其他-02491-004","劳动部门出具的丧失劳动能力鉴定证明","","330203198508081810");

				long endTime=System.currentTimeMillis();
				long Time=endTime-starTime;
				System.out.println("请求大数据平台耗时"+Time+"毫秒");
			}
	}



}
