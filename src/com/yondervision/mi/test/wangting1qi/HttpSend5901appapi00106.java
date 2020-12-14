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
//婚姻登记信息查询
public class HttpSend5901appapi00106 {
	//白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存 0
	public static final String POST_URL = "http://61.153.144.77:7006/YBMAPZH/";
	private static final String appKey_V = "b5b1c6938e5d0cef72457bd788ffdef0";
	private static final String appId_V = "yondervisionwebservice40";
	private static final String clientIp_V="172.10.0.1";

	public static String httpURLConnectionPOST5901(String bodyCardNumber,String sex,String birthday,String fullName,
			String powerMatters,String subPowerMatters,String materialName) {
		try {
			URL url = new URL(POST_URL + "appapi00106.json");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("headpara","fullPara,centerId,channel");
			String userId = "330227197812310776";
			AesTest aes = null;
			try {
				aes = new AesTest();
			} catch (Exception e) {
				e.printStackTrace();
			}

		 String Para ="userId="+ userId+ "&usertype=10&deviceType=3&deviceToken=&currenVersion=1.0&buzType=5901&appid="
						+ appId_V+ "&appkey="+ appKey_V+ "&appToken=&clientIp="+clientIp_V+"&clientMAC=E8:39:35:A7:AC:58&brcCode=05740008&tranDate=2017-09-19&channelSeq="
								+ "&bodyCardNumber="+bodyCardNumber
								+ "&sex="+sex
								+ "&birthday="+birthday
								+ "&fullName="+fullName
								+ "&powerMatters="+powerMatters
								+ "&subPowerMatters="+subPowerMatters
								+ "&materialName="+materialName;

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
			String result=change(aes.decrypt(sb.toString()));
//			String result=sb.toString();
//			String result=change(sb.toString());
//			String result=aes.decrypt(sb.toString());
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
		for (int i = 1;i<=1;i++){
		long starTime=System.currentTimeMillis();

/*
		httpURLConnectionPOST5901("330225197902061571", "M", "1979-02-06","樊勇","其他-02491-000","其他-02491-001","其配偶、父母、子女参与提取的，需提供婚姻、直系亲属关系证明");
		httpURLConnectionPOST5901("330225197902061571", "M", "1979-02-06","樊勇","其他-02492-000","其他-02492-002","婚姻登记信息");
		httpURLConnectionPOST5901("330225197902061571", "M", "1979-02-06","樊勇","其他-02492-000","其他-02492-003","婚姻登记信息");
		httpURLConnectionPOST5901("330225197902061571", "M", "1979-02-06","樊勇","其他-02492-000","其他-02492-001","婚姻登记信息");
		httpURLConnectionPOST5901("330225197902061571", "M", "1979-02-06","樊勇","其他-02492-000","其他-02492-003","婚姻登记信息");
		httpURLConnectionPOST5901("330225197902061571", "M", "1979-02-06","樊勇","其他-02491-000","其他-02491-006","其配偶、父母、子女参与提取的，需提供婚姻、直系亲属关系证明");
*/

		/*httpURLConnectionPOST5901("330227197709290025", "F", "1977-09-29","谢桂娜","其他-02491-000","其他-02491-002","其配偶、父母、子女参与提取的，需提供婚姻、直系亲属关系证明");

		httpURLConnectionPOST5901("330204197508036114", "M", "1975-08-03","邵建宁","其他-02491-000","其他-02491-002","其配偶、父母、子女参与提取的，需提供婚姻、直系亲属关系证明");

		httpURLConnectionPOST5901("330225197902061571", "M", "1979-02-06","樊勇","其他-02491-000","其他-02491-002","其配偶、父母、子女参与提取的，需提供婚姻、直系亲属关系证明");

		httpURLConnectionPOST5901("330622196812111932", "M", "1968-12-11","王永根","其他-02492-000","其他-02492-004","婚姻登记信息");

		httpURLConnectionPOST5901("330227197802020038", "M", "1978-02-02","谢科峰","其他-02492-000","其他-02492-004","婚姻登记信息");

	    httpURLConnectionPOST5901("330224197706261430", "M", "1977-06-26","邬培兵","其他-02492-000","其他-02492-004","婚姻登记信息");
		*/
		httpURLConnectionPOST5901("330205198809133314", "M", "1988-09-13","孙特超","其他-02492-000","其他-02492-004","婚姻登记信息");
		httpURLConnectionPOST5901("330204199310164021", "F", "1993-10-16","毕超静","其他-02492-000","其他-02492-004","婚姻登记信息");
		httpURLConnectionPOST5901("330211197512020514", "M", "1975-12-02","陈育人","其他-02492-000","其他-02492-004","婚姻登记信息");
		httpURLConnectionPOST5901("330203197809230029", "F", "1978-09-23","裘雅妮","其他-02492-000","其他-02492-004","婚姻登记信息");
		httpURLConnectionPOST5901("341226198104060842", "F", "1981-04-06","朱振兰","其他-02492-000","其他-02492-004","婚姻登记信息");

		//httpURLConnectionPOST5901("330225197902061571", "M", "1979-02-06","樊勇","其他-02492-000","其他-02492-001","婚姻登记信息");
		//httpURLConnectionPOST5901("330206197211091422", "F", "1972-11-09","乐申峰","","","");
		/*httpURLConnectionPOST5901("330206721109142", "F", "1972-11-09","乐申峰","","","");
		httpURLConnectionPOST5901("33022719720123273X", "M", "1972-01-23","郑辉","","","");
		httpURLConnectionPOST5901("330205197701154215", "M", "1977-01-15","杨华军","","","");
		httpURLConnectionPOST5901("330227198010023710", "M", "1980-10-02","陈军明","","","");
*/
		//httpURLConnectionPOST5901("330227196801057344", "F", "1968-01-05","赵叶琴","","","");
		//httpURLConnectionPOST5901("330227196709165879", "M", "1967-09-16","宣纪国","","","");

		//		httpURLConnectionPOST5901("330227199210255262", "F", "1992-10-25","陈玲");
//		httpURLConnectionPOST5901("330219197611156426", "F", "1976-11-15","戴亚玲");
//		httpURLConnectionPOST5901("330501810819202", "F", "1981-08-19","朱玉虹");
//		httpURLConnectionPOST5901("330226198712283684", "F", "1987-12-83","魏静");
//		httpURLConnectionPOST5901("330211198406250046", "F", "1984-06-25","林君燕");
//		httpURLConnectionPOST5901("330205198311051215", "M", "1983-11-05","庄英晔");
//		httpURLConnectionPOST5901("330205197701300622", "F", "1977-01-30","应华萍");
//		httpURLConnectionPOST5901("342101197105042017", "M", "1971-05-04","张东溟");
//		httpURLConnectionPOST5901("330205770130062", "F", "1977-01-30","应华萍");
//		httpURLConnectionPOST5901("330227730414121", "M", "1973-04-14","陈红辉");
//		httpURLConnectionPOST5901("330227761016108", "F", "1976-10-16","王妙峰");
//		httpURLConnectionPOST5901("330211197411064729", "F", "1974-11-06","励小燕");
//		httpURLConnectionPOST5901("330203197010290012", "M", "1970-10-29","李震");
//		httpURLConnectionPOST5901("330205197901190640", "F", "1979-01-19","洪斐");
//		httpURLConnectionPOST5901("330227731003161", "M", "1973-10-03","何金恩");
		


//		330225197209282587
		
//		9 0000008862   010100000029 戴亚玲 330219197611156426  
//		5 0118783856   010100000567 陈玲 330227199210255262  
//		6 0121295550   010100000022 蔡爱菊 332621196804062785  

		
		
//		httpURLConnectionPOST5901("330203680907152", "M", "1963-06-27","朱涛");
//		httpURLConnectionPOST5901("330203680907152", "F", "1968-09-07","孙红波");		
//		httpURLConnectionPOST5901("330204197606293026", "F", "1976-06-29","吴盛");
//			httpURLConnectionPOST5901("330107660919091", "M", "1966-09-19","李明");
			//httpURLConnectionPOST5901("330726198810170330", "M", "1988-10-17","张鹏程");
//			httpURLConnectionPOST5901("330205197109290322", "F", "1971-09-29","张淑波");
//			httpURLConnectionPOST5901("330203197709181813", "M", "1977-09-18","褚晖");

//		httpURLConnectionPOST5901("330225197902061571", "M", "1979-02-06","樊勇");
//		httpURLConnectionPOST5901("330203197911081816", "M", "1979-11-08","张大可");
//		httpURLConnectionPOST5901("330211198210061024", "F", "1982-10-06","林佳音");
//		httpURLConnectionPOST5901("330227199306052726", "F", "1993-06-05","忻琴茹");

		
//		httpURLConnectionPOST5901("332528197008144425", "F", "1970-08-14","蔡陈香");
		
//		httpURLConnectionPOST5901("330225197307012863", "M", "1973-07-01","张春敏");
//		httpURLConnectionPOST5901("320107198210182633", "M", "1982-10-18","周成泽");
		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("请求大数据平台耗时"+Time+"毫秒");

//			httpURLConnectionPOST5901("330204197209115015", "M", "1972-09-11","朱文俊");
//			httpURLConnectionPOST5901("330204720911501", "M", "1972-09-11","朱文俊");
//			httpURLConnectionPOST5901("33022519870923034X", "F", "1987-09-23","林丹姝");			
//			for (int i = 0; i < 100; i++) {
//				httpURLConnectionPOST5901("330204197508036114", "M", "1975-08-03","邵建宁","其他-02491-000","其他-02491-001","婚姻证明");
//			}
//		
//			httpURLConnectionPOST5901("330281198811137917", "M", "1988-11-13","方涛");		
//			httpURLConnectionPOST5901("330283198911031482", "F", "1989-11-03","张幼蓉");		
//			httpURLConnectionPOST5901("330183198511300013", "M", "1985-11-30","赵程遥");		
//			httpURLConnectionPOST5901("330227760606633", "M", "1976-06-06","吴斌");		
//			httpURLConnectionPOST5901("332625770202342", "F", "1977-02-02","吴春晓");		
//			httpURLConnectionPOST5901("330227196411062076", "M", "1964-11-06","邱宏良");	
//			httpURLConnectionPOST5901("330227641106207", "M", "1964-11-06","邱宏良");	
//			httpURLConnectionPOST5901("330222811101003", "M", "1981-11-01","刘洪");	
//			httpURLConnectionPOST5901("330282198207076", "F", "1982-07-07","朱雪飞");	

//			httpURLConnectionPOST5901("330203620324064", "F", "1962-03-24","黄福妹");	
//			httpURLConnectionPOST5901("330205781222032", "F", "1978-12-22","姚巧娜");		
//			httpURLConnectionPOST5901("330203197811030616", "M", "1978-11-03","肖忠炳");		

			/*		
			 * 身份证号：bodyCardNumber
			 性别：sex
			 生日：birthday（YYYY-MM-DD）
			 姓名：fullName*/
		}
	}
}
