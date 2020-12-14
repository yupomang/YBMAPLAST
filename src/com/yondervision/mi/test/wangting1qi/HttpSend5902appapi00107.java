package com.yondervision.mi.test.wangting1qi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import com.yondervision.mi.common.EncryptionByMD5;
import com.yondervision.mi.test.AesTest;
import com.yondervision.mi.util.security.RSASignature;
//直连房管局 获取房屋登记信息
public class HttpSend5902appapi00107 {
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";


	public static String httpURLConnectionPOST5902(String bodyCardNumber, String accname,
			String certType,String certNumber,String divisionCode,String contractNo) {
		try {
			URL url = new URL(POST_URL + "appapi00107.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330227196002157525";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5902&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
						+ "&bodyCardNumber="+bodyCardNumber
						+ "&accname="+accname
						+ "&certType="+certType
						+ "&certNumber="+certNumber
						+ "&divisionCode="+divisionCode
						+ "&contractNo="+contractNo;
		 

			 String fullPara =aes.encrypt(Para.getBytes("UTF-8"));
			 // 用于数字签名
			String parm ="fullPara="+fullPara+"&centerId=00057400&channel=40";			
			// 用于发送http报文
			String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=40";
			//System.out.println("本地参数" + parm);
			//System.out.println("传递参数：" + parm1);
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
//			String result=change(aes.decrypt(sb.toString()));
			String result=aes.decrypt(sb.toString());
//			String result=change(sb.toString());
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
		httpURLConnectionPOST5902("330225197812114019","杨亨武","1","20170010938","330225","2017330225MM0000863A");
//		httpURLConnectionPOST5902("320722198809020522","吴芳芳","1","20170072535","330203","2017330203MM0001356A");
//		httpURLConnectionPOST5902("33102219850526168X","徐敏燕","1","20170091753","330203","");
//		httpURLConnectionPOST5902("330203199310210618","程睿","1","20170063036","330203","2017330203MM0001070A");
//		httpURLConnectionPOST5902("230402198905130525","王冬霖","1","20170594297","330212","2017330212MM0010040A");
//		httpURLConnectionPOST5902("330225199404020020","姚梦莎","1","20170014632","330225","2017330225MM0000820A");
//		httpURLConnectionPOST5902("330203196702122410","叶芳","1","","330212","20061069");
//		httpURLConnectionPOST5902("330226198712283684","魏静","1","","330226","");


		/*		
Name	ID	                        DivisionCode CertificateNoCalc	        ContractNo
杨亨武	330225197812114019	330225	20170010938	    2017330225MM0000863A
吴芳芳	320722198809020522	330203	20170072535 	2017330203MM0001356A
程睿	    330203199310210618	330203	20170063036	 	2017330203MM0001070A
王冬霖	230402198905130525	330212	20170594297	 	2017330212MM0010040A

黄翠娣	330203193411191826	330203	20170066273	2017330203MM0001073A
单体祥	320921196902152816	330205	20170065754	2017330205MM0000425A
史瑾华	330227196109123317	330212	20170101558	2017330212MM0015101A
姚梦莎	330225199404020020	330225	20170014632	2017330225MM0000820A
姚梦莎	330225199404020020	330225	20170014632	2017330225MM0000829A
史灵倩	330682199302287428	330212	20170577703	2017330212MM0006843A
*/
/*		330203	海曙区
		330205	江北区
		330206	北仑区
		330211	镇海区
		330212	鄞州区
		330225	象山县
		330226	宁海县
		330281	余姚市
		330282	慈溪市
		330283	奉化区
		33028201	杭州湾*/

	}
}
